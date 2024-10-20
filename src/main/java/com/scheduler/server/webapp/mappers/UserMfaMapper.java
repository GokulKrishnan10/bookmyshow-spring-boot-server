package com.scheduler.server.webapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.scheduler.server.webapp.dto.UserMFADto;
import com.scheduler.server.webapp.entity.UserMFA;

@Mapper
public interface UserMfaMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "mfaEnabled", ignore = true)
    UserMFADto toUserMFADto(UserMFA userMFA);
}
