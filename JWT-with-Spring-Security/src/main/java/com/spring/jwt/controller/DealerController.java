//package com.spring.jwt.controller;
//
//import com.spring.jwt.dto.DealerDto;
//import com.spring.jwt.dto.ResponseDealerDto;
//import com.spring.jwt.service.IDealer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/dealer")
//public class DealerController {
//
//    @Autowired
//    private IDealer iDealer;
//    @PostMapping("/add")
//    public ResponseEntity<?> addDealer(@RequestBody DealerDto dealerDto){
//        System.out.println("1");
//        iDealer.adDealer(dealerDto);
//        System.out.println("2");
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Dealer has been Added");
//
//    }
//    @GetMapping("/getAllDealers")
//    public List<ResponseDealerDto> getAllDealer(@RequestParam int pageNo){
//        return iDealer.getDealers(pageNo);
//
//    }
//    @PutMapping("/edit/{id}")
//    public ResponseEntity<?> editDealer(@RequestBody DealerDto dealerDto, @PathVariable int id){
//        iDealer.editDealer(dealerDto,id);
//        return ResponseEntity.status(HttpStatus.OK).body(iDealer.editDealer(dealerDto,id));
//
//    }
//    @RequestMapping(value = "/delete/{id}" ,method = RequestMethod.DELETE)
//    public ResponseEntity<?> deleteDealer(@PathVariable int id){
//
//
//        return ResponseEntity.status(HttpStatus.OK).body(iDealer.removeDealers(id));
//
//    }
//}

