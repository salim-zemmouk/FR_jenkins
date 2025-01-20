package net.bsfconsulting.bibliothequeapi.services;

import net.bsfconsulting.bibliothequeapi.dto.BookDto;
import net.bsfconsulting.bibliothequeapi.entity.Book;
import net.bsfconsulting.bibliothequeapi.mapper.BookMapper;
import net.bsfconsulting.bibliothequeapi.repository.BookRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<BookDto> searchBooksByTitle(String title) {
        List<Book> books =bookRespository.findByTitle(title);
        return books.stream()
                .map(BookMapper.INSTANCE::bookToBookDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> searchBooksByAuthor(String author) {
        List<Book> books = bookRespository.findByAuthor(author);
        return books.stream()
                .map(BookMapper.INSTANCE::bookToBookDto)
                .collect(Collectors.toList());
    }
}
