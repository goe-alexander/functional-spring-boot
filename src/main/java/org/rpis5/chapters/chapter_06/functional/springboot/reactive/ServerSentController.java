package org.rpis5.chapters.chapter_06.functional.springboot.reactive;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ServerSentController {
    private Map<String, StocksServices> stringStocksServicesMap;

    @GetMapping("/sse/stocks")
    public Flux<ServerSentEvent<?>> streamStocks() {
        stringStocksServicesMap = new HashMap<>();
        stringStocksServicesMap.put("defaultService", new StocksServiceImpl());
        return Flux
                .fromIterable(stringStocksServicesMap.values())
                .delaySequence(Duration.ofMillis(250))
                .flatMap(StocksServices::stream)
                .<ServerSentEvent<?>>map(item ->
                    ServerSentEvent
                            .builder()
                            .event(item.getType())
                            .id(item.getId())
                            .build()
                )
                .startWith(
                        ServerSentEvent
                            .builder()
                            .event("Stocks")
                            .data(stringStocksServicesMap.keySet())
                            .build()
                );
    }

}
