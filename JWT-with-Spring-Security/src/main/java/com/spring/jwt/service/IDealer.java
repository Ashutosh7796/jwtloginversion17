package com.spring.jwt.service;



import com.spring.jwt.dto.DealerDto;
import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.dto.ResponseDealerDto;
import com.spring.jwt.utils.BaseResponseDTO;

import java.util.List;

public interface IDealer {
    public BaseResponseDTO adDealer(RegisterDto registerDto);

    public List<ResponseDealerDto> getDealers(int pageNo);

    public String editDealer(DealerDto dealerDto,int id);

    public String removeDealers(int id);
}
