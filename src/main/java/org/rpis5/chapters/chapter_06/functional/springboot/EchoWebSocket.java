package org.rpis5.chapters.chapter_06.functional.springboot;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class EchoWebSocket implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(tm -> "Echo: " + tm)
                .map(session::textMessage)
                .as(session::send);


    }
}
