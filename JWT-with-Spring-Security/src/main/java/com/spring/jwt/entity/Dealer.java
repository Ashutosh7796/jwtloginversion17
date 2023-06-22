package com.spring.jwt.entity;



import com.spring.jwt.dto.RegisterDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "dealer")
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dealer_id")
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "adhar_shopact", nullable = false, length = 250)
    private String adharShopact;

    @Column(name = "area", nullable = false, length = 45)
    private String area;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Column(name = "fristname", length = 45)
    private String fristname;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "mobile_no", nullable = false, length = 45)
    private String mobileNo;

    @Column(name = "shop_name", nullable = false, length = 250)
    private String shopName;
    @Column(name = "Email",nullable = false)
    private String email;
    @Column(name = "password", length = 250)
    private String password;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_user_id")
    private User userUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidding_bidding_id")
    private Bidding biddingBidding;

    @OneToMany(mappedBy = "dealerVendor")
    private Set<Biddingbuy> biddingbuys = new LinkedHashSet<>();

    @OneToMany(mappedBy = "dealerVendor")
    private Set<Car> cars = new LinkedHashSet<>();

    public Dealer(RegisterDto dealerDto) {
        this.address = dealerDto.getAddress();
        this.adharShopact = dealerDto.getAdharShopact();
        this.area = dealerDto.getArea();
        this.city = dealerDto.getCity();
        this.fristname = dealerDto.getFirstName();
        this.lastName = dealerDto.getLastName();
        this.mobileNo = dealerDto.getMobileNo();
        this.shopName = dealerDto.getShopName();
        this.email = dealerDto.getEmail();
        this.password = dealerDto.getPassword(); // Set the password field
        User user = new User();
        user.setEmail(dealerDto.getEmail());
        user.setPassword(dealerDto.getPassword());
        // Set any other necessary fields for the User object

        this.userUser = user;
        user.setDealers(this);
    }

}