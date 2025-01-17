package net.bsfconsulting.bibliothequeapi.services;

import net.bsfconsulting.bibliothequeapi.entity.Book;
import net.bsfconsulting.bibliothequeapi.repository.BookRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRespository bookRespository;

    public BookService(BookRespository bookRespository) {
        this.bookRespository = bookRespository;
    }

    public Book saveBook(Book book) {
        return bookRespository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRespository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRespository.findByAvailable(true);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRespository.findByTitle(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookRespository.findByAuthor(author);
    }
}
