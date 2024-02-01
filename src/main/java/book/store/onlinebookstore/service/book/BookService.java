package book.store.onlinebookstore.service.book;

import book.store.onlinebookstore.dto.book.BookDto;
import book.store.onlinebookstore.dto.book.BookSearchParameters;
import book.store.onlinebookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters params);
}
