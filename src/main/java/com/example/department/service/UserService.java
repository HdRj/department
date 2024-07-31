package com.example.department.service;

import com.example.department.dto.ChangePasswordDto;
import com.example.department.dto.UserResponseDto;
import com.example.department.exceptions.EmailException;
import com.example.department.exceptions.UserException;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(String email) throws EmailException;

    List<UserResponseDto> getAllUsers();

    String changePassword(ChangePasswordDto changePasswordDto) throws UserException;

    UserResponseDto inactivateUser(Long id);
}
