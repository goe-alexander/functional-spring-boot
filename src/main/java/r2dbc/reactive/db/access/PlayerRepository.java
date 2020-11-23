package r2dbc.reactive.db.access;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Integer> {
    @Query("select id, name, age from player where name = $1")
    Flux<Player> findAllByName(String name);

    @Query("Select * from player where age = $1")
    Flux<Player> findByAge(int age);
}
