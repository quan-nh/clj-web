logging

```
[org.clojure/tools.logging "1.1.0"]
[ch.qos.logback/logback-classic "1.2.3"]
```

`resources\logback.xml`
```xml
<configuration>

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>
                %d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n
            </pattern>
        </encoder>
    </appender>

    <logger name="clj-web" level="debug" additivity="false">
        <appender-ref ref="CONSOLE"/>
    </logger>

    <root level="error">
        <appender-ref ref="CONSOLE"/>
    </root>

</configuration>
```

then we can use that
```clj
  (:require [clojure.tools.logging :as log])

  (log/debug req)
```
