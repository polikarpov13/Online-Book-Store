package book.store.onlinebookstore;

import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class OnlineBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("How to get rich in 1 year");
            book.setAuthor("The Best Author In The World");
            book.setDescription("Very interesting book!");
            book.setISBN("IFB1313");
            book.setPrice(BigDecimal.valueOf(699));
            book.setCoverImage("100$ bill");

            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
