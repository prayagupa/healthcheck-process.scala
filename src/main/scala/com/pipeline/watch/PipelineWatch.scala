package com.pipeline.watch

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by prayagupd
  * on 11/29/16.
  */

object PipelineWatch {

  def main(args: Array[String]): Unit = {
    val serviceMonitorSystem = ActorSystem("ServiceMonitor")
    val serviceMonitor = serviceMonitorSystem.actorOf(Props[HealthCheckMonitor], name = "serviceMonitor")
    val uiActor = serviceMonitorSystem.actorOf(Props[UiActor], name = "uiActor")

    val config = ConfigFactory.load("pipeline")

    monitor()

    def monitor(): Unit = {
      new Thread(() => {
        while (true) {
          serviceMonitor ! new CheckHealthCommand(config.getString("service.name"))
        }
      }).start()
    }
  }
}

class UiActor extends Actor {

  override def preStart = context.system.eventStream.subscribe(self, classOf[BaseEvent])

  override def receive: Receive = {
    case event: Starting => println("UiActor : Server is Starting")
    case event: Stopped => println("UiActor : Server is stopped")
    case event: Running => println("UiActor : Server is Up")
    case _ => println("UiActor : I'm dead")
  }
}