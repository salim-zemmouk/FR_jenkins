package net.bsfconsulting.bibliothequeapi.services;

import net.bsfconsulting.bibliothequeapi.entity.Book;
import net.bsfconsulting.bibliothequeapi.entity.Loan;
import net.bsfconsulting.bibliothequeapi.entity.User;
import net.bsfconsulting.bibliothequeapi.repository.BookRespository;
import net.bsfconsulting.bibliothequeapi.repository.LoanRepository;
import net.bsfconsulting.bibliothequeapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRespository bookRespository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRespository bookRespository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRespository = bookRespository;
    }

    public Loan borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non existant"));
        Book book = bookRespository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Livre non existant"));
        if (!book.getAvailable()) {
            throw new IllegalStateException("Le livre n'est pas dispo!");
        }
        boolean isLoaned = loanRepository.existsByBookAndReturnDateIsNull(book);
        if (isLoaned) {
            throw new IllegalStateException("Le livre est déja en cours d'emprunt");
        }
        book.setAvailable(false);
        bookRespository.save(book);
        Loan loan = Loan.builder()
                .user(user)
                .book(book)
                .loanDate(LocalDate.now())
                .build();
        return loanRepository.save(loan);
    }

    public Loan returnBook(Long loanId){
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(()-> new IllegalStateException("Emprunt introuvable"));
        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Le livre a déjà été retourné.");
        }
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRespository.save(book);
        loan.setReturnDate(LocalDate.now());
        return loanRepository.save(loan);
    }

    public List<Loan> getLoans() {
        return loanRepository.findByReturnDateIsNullOrderByLoanDateAsc();
    }
}
