package io.github.artemptushkin.example.rsockethello.controller;

import io.github.artemptushkin.example.rsockethello.controller.domain.UnitRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static reactor.core.publisher.Mono.empty;

@Slf4j
@Controller
public class RSocketUnitController {

    @MessageMapping("lets-fire-and-forget")
    public Mono<Void> fireAndForget(UnitRequest request) {
        log.info("Server got request {}", request);
        return empty();
    }

    @MessageMapping("lets-request-response")
    public Mono<UnitRequest> requestResponse(UnitRequest request) {
        log.info("Server got request {}", request);
        return Mono.just(request);
    }

    @MessageMapping("lets-request-stream")
    public Flux<UnitRequest> requestStream(UnitRequest request) {
        log.info("Server got request {}", request);
        return Flux
                .just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .map(aLong -> {
                    UnitRequest unitRequest = new UnitRequest();
                    unitRequest.setId(aLong.longValue());
                    unitRequest.setName("Artem_" + aLong);
                    return unitRequest;
                });
    }

    @MessageMapping("lets-request-channel")
    public Flux<UnitRequest> channel(Flux<Integer> integerFlux) {
        return integerFlux
                .map(aLong -> {
                    UnitRequest unitRequest = new UnitRequest();
                    unitRequest.setId(aLong.longValue());
                    unitRequest.setName("Artem_" + aLong);
                    return unitRequest;
                });
    }

}
