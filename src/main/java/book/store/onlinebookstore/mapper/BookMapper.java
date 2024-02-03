package book.store.onlinebookstore.mapper;

import book.store.onlinebookstore.config.MapperConfig;
import book.store.onlinebookstore.dto.book.BookDto;
import book.store.onlinebookstore.dto.book.CreateBookRequestDto;
import book.store.onlinebookstore.dto.book.UpdateBookRequestDto;
import book.store.onlinebookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    Book toModel(UpdateBookRequestDto updateBookRequestDto);
}
