package com.scheduler.server.webapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.scheduler.server.webapp.dto.UserMFADto;
import com.scheduler.server.webapp.entity.UserMFA;

@Mapper
public interface UserMfaMapper {
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    @Mapping(target = "mfa_enabled", ignore = true)
    UserMFADto toUserMFADto(UserMFA userMFA);
}
