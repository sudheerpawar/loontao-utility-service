package com.loontao.utilityservice.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.loontao.utilityservice.dto.LoginUserDto;
import com.loontao.utilityservice.dto.RegisterUserDto;
import com.loontao.utilityservice.entity.Role;
import com.loontao.utilityservice.entity.RoleEnum;
import com.loontao.utilityservice.entity.User;
import com.loontao.utilityservice.repository.RoleRepository;
import com.loontao.utilityservice.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User signup(RegisterUserDto input) {
        
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        if (optionalRole.isEmpty()) {
            return null;
        }
          
        Optional<User> phoneExists = userRepository.findByPhoneNumber(input.getPhoneNumber());
        if (phoneExists.isPresent())
        {
            return null;
        }

        Optional<User> emailExists = userRepository.findByEmailId(input.getEmailId());
        if (emailExists.isPresent())
        {
            return null;
        }

        User user = new User()
                .setFullname(input.getFullname())
                .setEmailId(input.getEmailId())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setAddress(input.getAddress())
                .setCity(input.getCity())
                .setCountry(input.getCountry())
                .setPhoneNumber(input.getPhoneNumber())
                .setPincode(input.getPincode())
                .setDob(input.getDateOfBirth())
                .setFirstName(input.getFirstName())
                .setLastName(input.getLastName())
                .setRole(optionalRole.get());
                

        return userRepository.save(user);
    }

    /*
     * Updates the user account with the provided details.
     * @param registerUserDto The DTO containing user details to update.
     * @return The updated User object if successful, otherwise null.
     */
     public User updateAccount(RegisterUserDto registerUserDto) {
        if (registerUserDto == null || registerUserDto.getPhoneNumber() == null || registerUserDto.getPhoneNumber().isEmpty()) {
            return null; // Invalid input
        }
        // Check if the user exists by phone number
        // If phone number is not provided, return null
        Optional<User> userOptional = userRepository.findByPhoneNumber(registerUserDto.getPhoneNumber());
        if (userOptional.isEmpty()) {
            return null; // User not found
        }

        // Update the user details
        // If user is not found, return null
        User user = userOptional.get();
        user.setFullname(registerUserDto.getFullname())
            .setEmailId(registerUserDto.getEmailId())
            .setAddress(registerUserDto.getAddress())
            .setCity(registerUserDto.getCity())
            .setCountry(registerUserDto.getCountry())
            .setPincode(registerUserDto.getPincode())
            .setDob(registerUserDto.getDateOfBirth())
            .setFirstName(registerUserDto.getFirstName())
            .setLastName(registerUserDto.getLastName());

        if (registerUserDto.getPassword() != null && !registerUserDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        }

        // Save the updated user
        return userRepository.save(user);
    }

    /**
     * Authenticates a user based on the provided login credentials.
     * 
     * @param input The login credentials containing email/phone number/first name, and password.
     * @return The authenticated user if successful, otherwise null.
     */
    public User authenticate(LoginUserDto input) throws BadCredentialsException {

        if (input.getEmailId()!=null)
        {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmailId(),
                        input.getPassword()
                )
                
        );
        return userRepository.findByEmailId(input.getEmailId())
                .orElseThrow();
        } else if (input.getPhoneNumber()!=null)
        {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getPhoneNumber(),
                        input.getPassword()
                )
        );
        return userRepository.findByPhoneNumber(input.getPhoneNumber())
                .orElseThrow();
        } else if (input.getFirstName()!=null)
        {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getFirstName(),
                        input.getPassword()
                )
        );
        return userRepository.findByFirstName(input.getFirstName())
                .orElseThrow();
        } else return null;
    }

   
}

