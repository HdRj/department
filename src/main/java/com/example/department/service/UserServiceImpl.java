package com.example.department.service;

import com.example.department.dto.ChangePasswordDto;
import com.example.department.dto.UserResponseDto;
import com.example.department.enums.UserRole;
import com.example.department.enums.UserStatus;
import com.example.department.exceptions.EmailException;
import com.example.department.exceptions.UserException;
import com.example.department.mapper.UserMapper;
import com.example.department.model.User;
import com.example.department.repository.UserRepository;
import com.example.department.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailSender emailSender;

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.address}")
    private String serviceAddress;

    @Override
    public UserResponseDto createUser(String email) throws EmailException {
        String password = randomString(12);
        User user = new User(email, passwordEncoder.encode(password));
        user = userRepository.save(user);

        String text = emailText(password);
        String subject = "Реєстрація на сервісі "+serviceName;
        emailSender.sendSimpleMail(email,text,subject);

        return userMapper.toDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map(user->userMapper.toDto(user)).toList();
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) throws UserException {
        if(!changePasswordDto.newPassword().equals(changePasswordDto.newPassword2())){
            throw new UserException("New passwords don't match each other");
        }
        if(changePasswordDto.newPassword().length()<10){
            throw new UserException("New password is too short");
        }
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(login).get();
        if(passwordEncoder.matches(changePasswordDto.oldPassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
            userRepository.save(user);
            return "Password changed successfully";
        }else {
            throw new UserException("Old password is wrong");
        }
    }

    @Override
    public UserResponseDto inactivateUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getRole().equals(UserRole.ADMIN)){
                new RuntimeException("You can't inactivate admin");
            }
            user.setStatus(UserStatus.INACTIVE);
            return userMapper.toDto(userRepository.save(user));
        }else{
            throw new RuntimeException("User with id="+id+" doesn't exist");
        }
    }

    private String randomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    private String emailText(String password){
        String text = "Ви зареєстровані на сайті "+serviceName+"\n"
                +"Ваш пароль: " + password +"\n"
                +"Щоб зайти на сервіс перейдіть за посиланням:" +serviceAddress +"/login \n"
                +"З метою безпеки, будь-ласка, змініть цей пароль на новий";
        return text;
    }
    

}
