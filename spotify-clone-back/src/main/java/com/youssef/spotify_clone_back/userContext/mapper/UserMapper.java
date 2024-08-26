package com.youssef.spotify_clone_back.userContext.mapper;

import com.youssef.spotify_clone_back.userContext.ReadUserDto;
import com.youssef.spotify_clone_back.userContext.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ReadUserDto readUserDtoToUser(User entiy);
}
