package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.book.BookRepository;
import book.store.onlinebookstore.repository.category.CategoryRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    public void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/remove-all-books-and-categories.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Get all Books by VALID Category ID")
    @Sql(scripts = {
        "classpath:database/book/insert-book.sql",
        "classpath:database/book/insert-category.sql",
        "classpath:database/book/insert-books-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
        "classpath:database/book/remove-book.sql",
        "classpath:database/book/remove-books-categories.sql",
        "classpath:database/book/remove-category.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllBooksByCategoryId_ValidId_ReturnsValidBooks() {
        List<Book> actual = bookRepository.findAllByCategoryId(1L);

        Assertions.assertEquals(1, actual.size());
    }
}
