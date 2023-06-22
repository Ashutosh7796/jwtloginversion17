package com.spring.jwt.dto;



import com.spring.jwt.entity.Dealer;
import lombok.ToString;

@ToString
public class ResponseDealerDto {
    public String address;

    public String adharShopact;

    public String area;

    public String city;

    public String fristname;

    public String lastName;

    public String mobileNo;

    public String shopName;
    public String email;

    public ResponseDealerDto(Dealer dealer) {
        this.address = dealer.getAddress();
        this.adharShopact = dealer.getAdharShopact();
        this.area = dealer.getArea();
        this.city = dealer.getCity();
        this.fristname = dealer.getFristname();
        this.lastName =dealer.getLastName();
        this.mobileNo = dealer.getMobileNo();
        this.shopName = dealer.getShopName();
        this.email = dealer.getEmail();
    }


}
