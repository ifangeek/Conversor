package com.example.diegopacheco.conversor;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MonedaService {


    @GET("list?access_key=bd025437f1f1f4b0fd9299526c12e8d5&format=1")
    Call<Moneda> getMoneda();

}
