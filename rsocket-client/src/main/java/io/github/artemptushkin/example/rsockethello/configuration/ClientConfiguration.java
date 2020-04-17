package io.github.artemptushkin.example.rsockethello.configuration;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class ClientConfiguration {

    RSocket rSocket() {
        return RSocketFactory
                .connect()
                .mimeType(WellKnownMimeType.MESSAGE_RSOCKET_ROUTING.getString(), MimeTypeUtils.APPLICATION_JSON_VALUE)
                .transport(TcpClientTransport.create("127.0.0.1", 7000))
                .start()
                .retry(5)
                .block();
    }

    RSocketRequester rSocketRequester(RSocketStrategies strategies) {
        return RSocketRequester
                .builder()
                .rsocketStrategies(strategies)
                .connectTcp("127.0.0.1", 7000)
                .retry(5)
                .block();
       // return RSocketRequester.wrap(rSocket(), MimeTypeUtils.APPLICATION_JSON, MimeTypeUtils.APPLICATION_JSON, strategies);
    }
}
