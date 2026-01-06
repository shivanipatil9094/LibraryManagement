package com.example.library.services.impl;

import com.example.library.services.CurrencyConversion;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.springframework.stereotype.Service;
import tools.jackson.core.JsonParser;

import java.io.IOException;
import java.sql.SQLOutput;

@Service
public class CurrencyConversionImpl implements CurrencyConversion {
private final String APP_ID="02763a30a1-039d18d5a2-t8fj7r";
    @Override
    public String convert(Double amount, String from , String to) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.fastforex.io/convert?from="+from+"&to="+to+"&amount="+amount+"&api_key="+APP_ID)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        return  response.body().string();
    }
}
