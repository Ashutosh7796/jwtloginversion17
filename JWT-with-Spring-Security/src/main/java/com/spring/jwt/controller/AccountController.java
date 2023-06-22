package com.spring.jwt.controller;

import com.spring.jwt.dto.DealerDto;
import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.service.IDealer;
import com.spring.jwt.service.UserService;
import com.spring.jwt.utils.BaseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    private final IDealer iDealer;
    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok(userService.registerAccount(registerDto));

    }

    @PostMapping("/dalla")
    public ResponseEntity<BaseResponseDTO> registerr(@RequestBody RegisterDto dealerDto) {
        return ResponseEntity.ok(iDealer.adDealer(dealerDto));
    }
}
