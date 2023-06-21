package com.spring.jwt.service.impl;

import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.entity.Userprofile;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.UserService;
import com.spring.jwt.utils.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public BaseResponseDTO registerAccount(RegisterDto registerDto) {
        BaseResponseDTO response = new BaseResponseDTO();

        validateAccount(registerDto);

        User user = insertUser(registerDto);

        try {
            userRepository.save(user);
            response.setCode(String.valueOf(HttpStatus.OK.value()));
            response.setMessage("Create account successfully");
        } catch (Exception e) {
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service unavailable");
        }
        return response;
    }

    private User insertUser(RegisterDto registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setMobileNo(registerDto.getMobileNo());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Userprofile profile = new Userprofile();
        profile.setAddress(registerDto.getAddress());
        profile.setCity(registerDto.getCity());
        profile.setFirstName(registerDto.getFirstName());
        profile.setLastName(registerDto.getLastName());

        user.setProfile(profile);
        profile.setUser(user);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(registerDto.getRoles()));

        user.setRoles(roles);
        return user;
    }

    private void validateAccount(RegisterDto registerDto) {
        //validate null data
        if (ObjectUtils.isEmpty(registerDto)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Data must not empty");
        }

        //validate duplicate username
        User user = userRepository.findByEmail(registerDto.getEmail());
        if (!ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Username had existed");
        }


        //validate role
        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        if (!roles.contains(registerDto.getRoles())) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
        }
    }
}
