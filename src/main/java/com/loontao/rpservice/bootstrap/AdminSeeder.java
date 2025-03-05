package com.loontao.rpservice.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.loontao.rpservice.dto.RegisterUserDto;
import com.loontao.rpservice.entity.Role;
import com.loontao.rpservice.entity.RoleEnum;
import com.loontao.rpservice.entity.User;
import com.loontao.rpservice.repository.RoleRepository;
import com.loontao.rpservice.repository.UserRepository;


import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
        RoleRepository roleRepository,
        UserRepository  userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @SuppressWarnings("null")
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFullname("SuperAdmin");
        userDto.setEmailId("super.admin@email.com");
        userDto.setPassword("123456");
        userDto.setAddress("Address");
        userDto.setCity("City");
        userDto.setCountry("Country");
        userDto.setPhoneNumber("1111111111");
        userDto.setPincode("11111");
        userDto.setFirstName("Super");
        userDto.setLastName("Admin");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmailId(userDto.getEmailId());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User()
            .setFullname(userDto.getFullname())
            .setEmailId(userDto.getEmailId())
            .setPassword(passwordEncoder.encode(userDto.getPassword()))
            .setRole(optionalRole.get())
            .setAddress(userDto.getAddress())
            .setCity(userDto.getCity())
            .setCountry(userDto.getCountry())
            .setPhoneNumber(userDto.getPhoneNumber())
            .setPincode(userDto.getPincode())
            .setFirstName(userDto.getFirstName())
            .setLastName(userDto.getLastName());

        userRepository.save(user);
    }
}