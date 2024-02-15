DELETE FROM books_categories;
DELETE FROM books WHERE EXISTS (SELECT * FROM books WHERE id = 1);
DELETE FROM categories WHERE EXISTS (SELECT * FROM categories WHERE id = 1);