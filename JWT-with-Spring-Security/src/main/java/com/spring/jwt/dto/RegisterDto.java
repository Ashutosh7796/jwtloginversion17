package com.spring.jwt.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {


    private String email;
    private String password;
    private String mobileNo;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String roles;
}
