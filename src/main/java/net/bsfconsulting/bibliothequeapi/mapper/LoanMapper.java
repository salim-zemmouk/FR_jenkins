package net.bsfconsulting.bibliothequeapi.mapper;

import net.bsfconsulting.bibliothequeapi.dto.LoanDto;
import net.bsfconsulting.bibliothequeapi.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanMapper {
    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);
    LoanDto loanToLoanDto(Loan loan);
    Loan loanDtoToLoan(LoanDto loanDto);
}
