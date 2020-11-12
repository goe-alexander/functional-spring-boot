package org.rpis5.chapters.chapter_06.functional.springboot.reactive;

import reactor.core.publisher.Flux;

public interface StocksServices {

    Flux<StockItem> stream();
}
