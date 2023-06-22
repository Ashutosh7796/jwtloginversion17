package com.spring.jwt.service.impl;

import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.entity.Userprofile;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.repository.DealerRepo;
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

    private final DealerRepo dealerRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public BaseResponseDTO registerAccount(RegisterDto registerDto) {
        BaseResponseDTO response = new BaseResponseDTO();

        validateAccount(registerDto);

        if ("user".equals(registerDto.getUserType())) {
            User user = insertUser(registerDto);

            try {
                userRepository.save(user);
                response.setCode(String.valueOf(HttpStatus.OK.value()));
                response.setMessage("User account created successfully");
            } catch (Exception e) {
                response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
                response.setMessage("Service unavailable");
            }
        } else if ("dealer".equals(registerDto.getUserType())) {
            Dealer dealer = insertDealer(registerDto);

            try {
                dealerRepo.save(dealer);
                response.setCode(String.valueOf(HttpStatus.OK.value()));
                response.setMessage("Dealer account created successfully");
            } catch (Exception e) {
                response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
                response.setMessage("Service unavailable");
            }
        } else {
            response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            response.setMessage("Invalid user type");
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
        Role userRole = roleRepository.findByName(registerDto.getRoles());
        roles.add(userRole);

        user.setRoles(roles);
        return user;
    }


    private Dealer insertDealer(RegisterDto registerDto) {
        Dealer dealer = new Dealer();
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setMobileNo(registerDto.getMobileNo());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        dealer.setUserUser(user);
        dealer.setAddress(registerDto.getAddress());
        dealer.setAdharShopact(registerDto.getAdharShopact());
        dealer.setArea(registerDto.getArea());
        dealer.setCity(registerDto.getCity());
        dealer.setFristname(registerDto.getFirstName());
        dealer.setLastName(registerDto.getLastName());
        dealer.setMobileNo(registerDto.getMobileNo());
        dealer.setShopName(registerDto.getShopName());
        dealer.setEmail(registerDto.getEmail());
        dealer.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        return dealer;
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
