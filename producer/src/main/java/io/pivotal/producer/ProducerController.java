package io.pivotal.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final Consumer<String> publishMessage;

    @GetMapping("/date")
    public Mono<String> getDate() {
        return Mono.just(LocalDateTime.now())
                .map(d -> "Current Date is " + d)
                .doOnSuccess(publishMessage);
    }
 }
