package org.rpis5.chapters.chapter_06.functional.springboot.json.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;

import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;


@Slf4j
public class LargeReactiveJsonParser {

  public static void main(String[] args) throws InterruptedException {
      System.out.println("####Started reading Json reactively");
    Jackson2JsonDecoder decoder = new Jackson2JsonDecoder();
        decoder.decode(
            DataBufferUtils.readAsynchronousFileChannel(
                    () ->
                        AsynchronousFileChannel.open(
                            Paths.get(ClassLoader.getSystemResource("LargeJsonFile.json").getPath()),
                            StandardOpenOption.READ),
                    new DefaultDataBufferFactory(),
                    10)
                .delayElements(Duration.ofMillis(100)),
            ResolvableType.forClass(Message.class),
            null,
            null)
            .subscribe(e -> log.info("some message", e));
    System.out.println("#### Finished Json");

    Thread.sleep(10000);
  }
}
