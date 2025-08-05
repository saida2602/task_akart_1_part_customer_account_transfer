package com.saida.register_customer.controller;

import com.saida.register_customer.dto.request.TransferRequestDto;
import com.saida.register_customer.dto.response.TransferResponseDto;
import com.saida.register_customer.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/operation")
    public TransferResponseDto transfer(@RequestBody @Valid TransferRequestDto requestDto) {
        return transferService.transfer(requestDto);
    }

    @PostMapping("/refund")
    public TransferResponseDto refund(@RequestBody @Valid TransferRequestDto requestDto,
                                      @RequestParam BigDecimal refundAmount) {
        return transferService.refund(requestDto, refundAmount);
    }

}
