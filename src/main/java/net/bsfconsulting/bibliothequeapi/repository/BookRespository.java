package net.bsfconsulting.bibliothequeapi.repository;

import net.bsfconsulting.bibliothequeapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRespository extends JpaRepository<Book , Long> {
    List<Book> findByAvailable(boolean available);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);
}
