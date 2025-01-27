package net.bsfconsulting.bibliothequeapi.mapper;

import net.bsfconsulting.bibliothequeapi.dto.UserDto;
import net.bsfconsulting.bibliothequeapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "loans", target = "loans")
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);

}
