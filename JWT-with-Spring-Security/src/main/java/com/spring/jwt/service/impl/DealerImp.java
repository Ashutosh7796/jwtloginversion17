package com.spring.jwt.service.impl;



import com.spring.jwt.dto.DealerDto;
import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.dto.ResponseDealerDto;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.DealerRepo;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.IDealer;
import com.spring.jwt.utils.BaseResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class DealerImp implements IDealer {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DealerRepo dealerRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;


    /////////////////////////////////////////////////////////////////////
    //
    //  Method Name :  adDealer
    //  Description   :  Used to Registeration The DealerController And It's Profile
    //  Input         :  DealerDto
    //  Output        :  String
    //  Date 		  :  20/06/2023
    //  Author 		  :  Geetesh Gajanan Kumbalkar
    //
    /////////////////////////////////////////////////////////////////////
    @Override
    public BaseResponseDTO adDealer(RegisterDto registerDto) {
        // Check if the dealer email already exists
        Dealer dealer = dealerRepo.findByEmail(registerDto.getEmail());
        if (dealer != null) {
            return new BaseResponseDTO("500", "Email already exists");
        }

        try {
            // Create a new User instance and set its properties
            User user = new User();
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setEmail(registerDto.getEmail());
            user.setMobileNo(registerDto.getMobileNo());

            // Create a new Dealer instance and set its properties
            Dealer dealerData = new Dealer(registerDto);

            // Set the association between Dealer and User
            dealerData.setUserUser(user);
//            user.setDealers(dealerData);

            // Save the User instance first

//            userRepo.save(user);
//            dealerRepo.save(dealerData);
            // Save the Dealer instance
            dealerRepo.save(dealerData);

            return new BaseResponseDTO("2066", "Dealer added");
        } catch (Exception e) {
            System.err.println(e);
            return new BaseResponseDTO("55555", e.toString());
        }
    }


    /////////////////////////////////////////////////////////////////////
    //
    //  Method Name    :  getDealers
    //  Description   :  Used to Get The all Dealers with pagewise
    //  Input         :  int
    //  Output        :  List<ResponseDealerDto>
    //  Date 		  :  21/06/2023
    //  Author 		  :  Geetesh Gajanan Kumbalkar
    //
    /////////////////////////////////////////////////////////////////////

    @Override
    public List<ResponseDealerDto> getDealers(int pageNo) {
        List<Dealer> listOfDealer = dealerRepo.findAll();
        System.out.println("list of de"+listOfDealer.size());
        List<ResponseDealerDto> listOfDealerDto = new ArrayList<>();
//        System.out.println("2");
        int pageStart=pageNo*10;
        int pageEnd=pageStart+10;
        int diff=(listOfDealer.size()) - pageStart;
//        for(int counter=pageNo*10;counter<(pageNo*10)+10;counter++){
        for(int counter=pageStart,i=1;counter<pageEnd;counter++,i++){
            if(pageStart>listOfDealer.size()){break;}

            System.out.println("*");
            ResponseDealerDto responseDealerDto = new ResponseDealerDto(listOfDealer.get(counter));
//            System.out.println(responseDealerDto.toString());
            listOfDealerDto.add(responseDealerDto);
//            System.out.println(listOfDealerDto.size());
            if(diff == i){
                 break;
            }
        }
//                   ResponseDealerDto responseDealerDto = new ResponseDealerDto(listOfDealer.get(1));

        System.out.println(listOfDealerDto);
        return listOfDealerDto;

    }
    /////////////////////////////////////////////////////////////////
    //
    //  Method Name   :  editDealer
    //  Description   :  Used to edit The DealerController details
    //  Input         :  DealerDto,int
    //  Output        :  String
    //  Date 		  :  21/06/2023
    //  Author 		  :  Geetesh Gajanan Kumbalkar
    //
    /////////////////////////////////////////////////////////////////////

    @Override
    public String editDealer(DealerDto dealerDto,int id) {

        Optional<Dealer> dealer = dealerRepo.findById(id);
        if(dealer.isPresent()){
            dealer.get().setFristname(dealerDto.fristname);
            dealer.get().setLastName(dealerDto.lastName);
            dealer.get().setAddress(dealerDto.address);
            dealer.get().setMobileNo(dealerDto.mobileNo);
            dealer.get().setEmail(dealerDto.email);
            dealer.get().getUserUser().setMobileNo(dealerDto.mobileNo);
            byte encrypt[]= Base64.getEncoder().encode(dealerDto.password.getBytes());
            String encryptPassword=new String(encrypt);
            dealer.get().getUserUser().setPassword(encryptPassword);

            dealer.get().getUserUser().setEmail(dealerDto.email);
            dealer.get().setArea(dealerDto.area);
            dealer.get().setCity(dealerDto.city);

            dealerRepo.save(dealer.get());
            return "Added dealer :"+id;
        }
        return "invalid id";
    }
    /////////////////////////////////////////////////////////////////////
    //
    //  Method Name   :  removeDealers
    //  Description   :  Used to remove The DealerController details
    //  Input         :  int
    //  Output        :  String
    //  Date 		  :  21/06/2023
    //  Author 		  :  Geetesh Gajanan Kumbalkar
    //
    /////////////////////////////////////////////////////////////////////

    @Override
    public String removeDealers(int id) {
        Optional<Dealer> dealer = dealerRepo.findById(id);
        if(dealer.isPresent()){
            try {
                dealerRepo.deleteById(id);
                return "dealer Delete Succesfully :"+ id;
            }catch (Exception e){
                System.err.println(e);
            }

        }
        return "id invalid";
    }
}
