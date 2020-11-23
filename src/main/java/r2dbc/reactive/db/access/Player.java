package r2dbc.reactive.db.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    Integer id;
    String name;
    Integer age;
}
