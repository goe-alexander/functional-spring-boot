package chapter_07.database.reactive;


import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

//DAO implemented with the jdbi library
public interface BookJdbiDao {

    @SqlQuery("SELECT * FROM book order by id DESC")
    @RegisterBeanMapper(Book.class)
    List<Book> listBooks();

    @SqlUpdate("INSERT INTO book(id, title) VALUES(:id, :title)")
    void insertBook(@BindBean Book book);
}
