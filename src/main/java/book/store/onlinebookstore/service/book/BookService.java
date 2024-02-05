package book.store.onlinebookstore.service.book;

import book.store.onlinebookstore.dto.book.BookDto;
import book.store.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import book.store.onlinebookstore.dto.book.BookSearchParameters;
import book.store.onlinebookstore.dto.book.CreateBookRequestDto;
import book.store.onlinebookstore.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id);

    BookDto findById(Long id);

    BookDto updateById(Long id, UpdateBookRequestDto updatedBook);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters params);
}
