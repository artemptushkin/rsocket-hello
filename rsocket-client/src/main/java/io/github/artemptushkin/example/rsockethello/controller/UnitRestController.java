package io.github.artemptushkin.example.rsockethello.controller;

import io.github.artemptushkin.example.rsockethello.controller.domain.UnitRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class UnitRestController {

    private final RSocketRequester requester;

    @RequestMapping("/some/{foo}/{baz}")
    public String hi(CompositeObject compositeObject) {
        return "hi";
    }

    @PostMapping(value = "/units")
    public Publisher<Void> sendUnit(@RequestBody UnitRequest unitRequest) {
        return requester
                .route("lets-fire-and-forget")
                .data(unitRequest)
                .send();
    }

    @PostMapping(value = "/units/send")
    public Mono<UnitRequest> create(@RequestBody UnitRequest unitRequest) {
        return requester
                .route("lets-request-response")
                .data(unitRequest)
                .retrieveMono(UnitRequest.class);
    }

    @PostMapping(value = "/units/stream")
    public Flux<UnitRequest> streamIt(@RequestBody UnitRequest unitRequest) {
        return requester
                .route("lets-request-stream")
                .data(unitRequest)
                .retrieveFlux(UnitRequest.class);
    }

    @PostMapping(value = "/units/channel")
    public Flux<UnitRequest> channelMe(@RequestBody IntegerRequest request) {
        return requester
                .route("lets-request-channel")
                .data(Flux
                        .fromIterable(request.getIntegers())
                        .delayElements(Duration.ofSeconds(3))
                ).retrieveFlux(UnitRequest.class);
    }
}
