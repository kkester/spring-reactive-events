# spring-reactive-events

### Overview

This PoC demonstrates the following architecture, design, and coding strategies:

1. Reactive REST API with Rest Controllers
2. Reactive H2 Persistence Layer
3. Publishing Event Messages to RabbitMQ in a functional reactive style
4. Consuming Event Messages to RabbitMQ in a functional style

### Running the POC
* TBD


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
