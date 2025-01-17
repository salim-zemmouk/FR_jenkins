package net.bsfconsulting.bibliothequeapi.repository;

import net.bsfconsulting.bibliothequeapi.entity.Book;
import net.bsfconsulting.bibliothequeapi.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    boolean existsByBookAndReturnDateIsNull(Book book);

    List<Loan> findByReturnDateIsNullOrderByLoanDateAsc();
}
