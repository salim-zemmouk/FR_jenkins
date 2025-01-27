package net.bsfconsulting.bibliothequeapi.mapper;

import net.bsfconsulting.bibliothequeapi.dto.BookDto;
import net.bsfconsulting.bibliothequeapi.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookDto bookToBookDto(Book book);
    Book bookDtoToBook(BookDto bookDto);
}
