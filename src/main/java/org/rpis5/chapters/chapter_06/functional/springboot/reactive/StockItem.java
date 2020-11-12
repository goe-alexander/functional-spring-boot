package org.rpis5.chapters.chapter_06.functional.springboot.reactive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockItem {
    public String id;
    public String type;
}
