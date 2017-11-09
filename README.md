
monitors a service process(`service.name`) in local machine, 
if not starts up a process as defined in `service.start.script`

eg. to check tomcat process in local machine,

```
service.name="[org.apache.catalina.startup].Bootstrap"                                                                                 
service.start.script="/usr/local/apache-tomcat-8.5.12/bin/startup.sh"
```

run app
-------

```
sbt run
```

logs
----

```
HealthCheckMonitor# Service is Running()
UiActor : Server is Up
Received CheckHealth 34 times.
MonitorService : ps aux | grep "[org.apache.catalina.startup].Bootstrap" | awk '{print $2}'
HealthCheckMonitor# Service is Running()
UiActor : Server is Up
Received CheckHealth 35 times.
```

build runnable artifact
-----------------------

```
sbt clean assembly
```
