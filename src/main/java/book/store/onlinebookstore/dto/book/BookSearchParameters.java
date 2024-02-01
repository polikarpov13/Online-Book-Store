package book.store.onlinebookstore.dto.book;

public record BookSearchParameters(String[] authors,
                                   String[] titles,
                                   String[] isbnS) {
}
