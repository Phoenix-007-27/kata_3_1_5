package ru.kata.spring.boot_security.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;


@Mapper
public interface UserMapper {


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);

    User toUser(UserDTO userDTO);

    Set<Role> map(Set<String> value);
    Role map(String value);
}
