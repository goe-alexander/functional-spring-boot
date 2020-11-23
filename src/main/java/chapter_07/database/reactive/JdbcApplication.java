package chapter_07.database.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

@EnableJdbcRepositories
@EnableAsync
@SpringBootApplication
@Import({JdbcConfiguration.class})
@Slf4j
public class JdbcApplication implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    BookJdbcRepository bookJdbcRepository;

    @Autowired
    BookSpringDataJdbcRepository bookSpringDataJdbcRepository;

    public static void main(String[] args){
        SpringApplication.run(JdbcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("--- Spring JDBC ---------------------------------------------");
        log.info("Book with id 13: {}", bookJdbcRepository.findById(13));

        List<Book> booksWithTitle = bookJdbcRepository.findByTitle("Blue Mars");
        log.info("Books with title 'Mars': {}", toString(booksWithTitle.stream()));

        List<Book> booksAll = bookJdbcRepository.findAll();
        log.info("All books in DB: {}", toString(booksAll.stream()));

        // Spring Data JDBC
        log.info("--- Spring Data JDBC------------------------------------------");
        Iterable<Book> booksFromDataJdbc = bookSpringDataJdbcRepository.findAllById(asList(11, 13));
        List<Book> booksFromDataJdbcList = new ArrayList<>();
        booksFromDataJdbc.iterator().forEachRemaining(booksFromDataJdbcList::add);
        log.info("Books (id:11), (id:13) from Spring Data JDBC: {}",
                toString(booksFromDataJdbcList.stream()));

        log.info("Book with the longest title: {}",
                toString(bookSpringDataJdbcRepository.findByLongestTitle().stream()));

        log.info("Book with the shortest title: {}",
                toString(bookSpringDataJdbcRepository.findByShortestTitle()));

        bookSpringDataJdbcRepository.findBookByTitleAsync("The Martian")
                .thenAccept(b -> log.info("Book by title, async: {}", toString(Stream.of(b))));

        bookSpringDataJdbcRepository.findBooksByIdBetweenAsync(10, 14)
                .thenAccept(bookStream -> log.info("Book by id (11..13), async: {}", toString(bookStream)));
    }

    private Object toString(Stream<Book> books) {
        try {
            return books
                    .map(Book::toString)
                    .collect(joining("\n - ", "\n - ", ""));
        } finally {
            // We have to close the stream when finished to free the resources used by the query.
            books.close();
        }
    }
}
