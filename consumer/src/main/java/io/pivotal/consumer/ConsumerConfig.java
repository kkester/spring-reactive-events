package io.pivotal.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class ConsumerConfig {

    @Bean
    public Consumer<String> processDate() {
        return d -> log.info("Consumed Date {}", d);
    }

}
