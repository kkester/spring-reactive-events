# spring-reactive-events

### Overview

This PoC demonstrates the following architecture, design, and coding strategies:

1. Reactive REST API with Rest Controllers
2. Reactive H2 Persistence Layer
3. Publishing Event Messages to RabbitMQ in a functional reactive style
4. Consuming Event Messages to RabbitMQ in a functional style
5. Custom Enum JSON Serialization

### Running the POC
* Launch _ConsumerApplication_
* Launch _ProducerApplication_
* Open http://localhost:8080/pyl/games/new-game in a browser

### Notes on working with Spring Data R2DBC
There are a few caveats to using Spring Data R2DBC compared to Spring Data JPA
1. Currently, R2DBC cannot auto generate the DB schema.
2. If an Entity has an ID, R2DBC will always attempt to perform a SQL update.
3. Turning on sql logging is DB specific.  For H2, the below property enables sql logging:
```text
logging.level.io.r2dbc.h2=TRACE
```
For the first two items, this POC contains a `DbConfig` class that handles schema and ID generation.

### Notes on Functional Non-reactive Event Messaging

```java
@Bean
public Supplier<LocalDateTime> publishDate() {
    return () -> {
        log.info("Publishing Date");
        return LocalDateTime.now();
    };
}
```

```java
streamBridge.send("pylMessage-played", pylMessage);
```
