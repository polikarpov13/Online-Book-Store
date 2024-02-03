package book.store.onlinebookstore;

import book.store.onlinebookstore.dto.book.CreateBookRequestDto;
import book.store.onlinebookstore.service.book.BookService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class OnlineBookStoreApplication {
    private final BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            CreateBookRequestDto book = new CreateBookRequestDto();
            book.setTitle("How to get rich in 1 year");
            book.setAuthor("The Best Author In The World");
            book.setDescription("Very interesting book!");
            book.setIsbn("IFB1313");
            book.setPrice(BigDecimal.valueOf(699));
            book.setCoverImage("100$ bill");
            bookService.save(book);
            System.out.println("Project started successfully");
        };
    }
}
