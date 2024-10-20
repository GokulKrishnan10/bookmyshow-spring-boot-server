package com.scheduler.server.webapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.scheduler.server.webapp.dto.UserDto;
import com.scheduler.server.webapp.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // @Mapping(target = "id", ignore = true)
    // @Mapping(target = "password", ignore = true)
    // @Mapping(target = "updatedAt", ignore = true)
    // @Mapping(target = "createdAt", ignore = true)
    // @Mapping(target = "isExpired", ignore = true)
    // @Mapping(target = "isEnabled", ignore = true)
    // @Mapping(target = "isLocked", ignore = true)
    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);

}
