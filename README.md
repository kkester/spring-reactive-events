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

### Notes on working with R2DBC

* JPA can support ID generation and inserts of entities with an ID. R2DBC will always try to update an entity with an id.
* Turning on sql logging is DB specific.  For H2, the below property enables sql logging:
```text
logging.level.io.r2dbc.h2=TRACE
```

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
