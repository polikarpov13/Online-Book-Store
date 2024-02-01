package book.store.onlinebookstore.repository.book;

import book.store.onlinebookstore.dto.book.BookSearchParameters;
import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.SpecificationBuilder;
import book.store.onlinebookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("author")
                            .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("title")
                            .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.isbnS() != null && searchParameters.isbnS().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.isbnS()));
        }
        return spec;
    }
}
