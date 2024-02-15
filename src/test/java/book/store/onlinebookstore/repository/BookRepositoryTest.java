package book.store.onlinebookstore.repository;

import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.book.BookRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

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
