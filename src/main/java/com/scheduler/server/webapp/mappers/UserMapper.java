package com.scheduler.server.webapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.scheduler.server.webapp.dto.UserDto;
import com.scheduler.server.webapp.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "is_expired", ignore = true)
    UserDto toUserDto(User user);

}
