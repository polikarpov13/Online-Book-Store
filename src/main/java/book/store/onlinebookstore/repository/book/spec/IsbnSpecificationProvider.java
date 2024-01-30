package book.store.onlinebookstore.repository.book.spec;

import book.store.onlinebookstore.model.Book;
import book.store.onlinebookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;

public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "isbn";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("isbn").in(Arrays.stream(params).toArray());
    }
}
