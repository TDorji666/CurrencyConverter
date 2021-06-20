package com.example.currencyconverter.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetroFitInterface {
    //https://v6.exchangerate-api.com/v6/faef84f3763e9377cdf7158d/latest/USD
    @GET("v6/faef84f3763e9377cdf7158d/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
