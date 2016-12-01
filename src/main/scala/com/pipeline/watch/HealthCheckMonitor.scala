package com.pipeline.watch

import akka.actor.Actor
import com.typesafe.config.ConfigFactory

/**
  * Created by prayagupd
  * on 11/23/16.
  */

class HealthCheckMonitor extends Actor {

  val service  = new MonitorService
  val config = ConfigFactory.load("pipeline")
  val startService = config.getString("service.start.script")

  var received = 0

  override def receive: Receive = {
    case health: CheckHealthCommand => {
      received = received + 1
      println(s"Received CheckHealth ${received} times.")
      val serviceStatus = service.serviceStatusEvent(health)

      context.system.eventStream.publish(serviceStatus)

      println("HealthCheckMonitor# Service is " + serviceStatus)

      serviceStatus match {
        case st: Stopped => {
          context.system.eventStream.publish(new Starting())
          service.start(new StartServiceCommand(startService))
        }
        case r: Running => {}
      }
    }
  }
}
