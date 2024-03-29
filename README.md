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

### Testing Strategy

* Choose your test method [Naming Strategy](https://medium.com/@stefanovskyi/unit-test-naming-conventions-dd9208eadbea)
* Spring Boot tests for Controllers.  Mockito tests for everything else.
* Leveraged `WebTestClient` for Controller tests.

#### Testing Definitions

> [Unit Tests](https://martinfowler.com/bliki/UnitTest.html)

> [Component Tests](https://martinfowler.com/bliki/ComponentTest.html)

> [Integration Tests](https://martinfowler.com/bliki/IntegrationTest.html)

### Notes on Functional Non-reactive Event Messaging

The following are code snipets showing how to consume and publish messages in a non-reactive fashion.
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
