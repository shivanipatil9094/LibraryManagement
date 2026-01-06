package com.example.library.services;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public interface CurrencyConversion {

    String convert(Double amount, String from, String to) throws IOException;



}
