package com.example.library.controller;

import com.example.library.services.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConversionController {
    @Autowired
    private CurrencyConversion currencyConversion;

    @GetMapping("/convert")
    public ResponseEntity<?> convert(@RequestParam Double amount,@RequestParam String from,@RequestParam String to) throws IOException {
        return ResponseEntity.ok(currencyConversion.convert(amount, from, to));


    }

}
