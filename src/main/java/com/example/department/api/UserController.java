package com.example.department.api;

import com.example.department.dto.ChangePasswordDto;
import com.example.department.dto.UserResponseDto;
import com.example.department.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin_permission')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        try {
            String result = userService.changePassword(changePasswordDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{email}")
    @PreAuthorize("hasAuthority('admin_permission')")
    public ResponseEntity<UserResponseDto> createUser(@PathVariable String email){
        try {
            return new ResponseEntity<>(userService.createUser(email), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/inactivate/{id}")
    @PreAuthorize("hasAuthority('admin_permission')")
    public ResponseEntity<UserResponseDto> inactivateUser(@PathVariable Long id){
        try {
            return new ResponseEntity<>(userService.inactivateUser(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
