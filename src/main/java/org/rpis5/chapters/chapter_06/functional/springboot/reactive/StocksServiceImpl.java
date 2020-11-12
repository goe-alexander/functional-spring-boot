package org.rpis5.chapters.chapter_06.functional.springboot.reactive;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class StocksServiceImpl implements StocksServices {
    @Override
    public Flux<StockItem> stream() {

        return Flux.fromArray(
                new StockItem[]{
                        new StockItem("1", "Short"),
                        new StockItem("2", "Option"),
                        new StockItem("12", "Trade"),
                        new StockItem("13", "Tax")

                })
                .delayElements(Duration.ofMillis(1000));
    }
}
