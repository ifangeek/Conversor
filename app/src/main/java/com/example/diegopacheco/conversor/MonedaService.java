package com.example.diegopacheco.conversor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MonedaService {


    @GET("/api/list?access_key=bd025437f1f1f4b0fd9299526c12e8d5&format=1")
    Call<Moneda> getMoneda();

}
