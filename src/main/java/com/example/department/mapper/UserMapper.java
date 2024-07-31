package com.example.department.mapper;

import com.example.department.dto.UserResponseDto;
import com.example.department.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toDto(User user){
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getStatus(),
                user.getRole()
        );
    }
}
