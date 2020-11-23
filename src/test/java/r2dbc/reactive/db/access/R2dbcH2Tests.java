package r2dbc.reactive.db.access;

import io.r2dbc.h2.H2ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;
import r2dbc.reactive.db.access.PlayerRepository;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class R2dbcH2Tests {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    DatabaseClient client;

    @Autowired
    H2ConnectionFactory factory;

    @Test
    public void whenDeleteAll_then0IsExpected() {
        playerRepository.deleteAll()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
    }


}
