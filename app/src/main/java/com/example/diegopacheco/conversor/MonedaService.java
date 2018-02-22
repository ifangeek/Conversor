package com.example.diegopacheco.conversor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MonedaService {


    @GET("list?access_key=bd025437f1f1f4b0fd9299526c12e8d5&format=1")
    Call<Moneda> getMoneda();

    @GET("live?access_key=bd025437f1f1f4b0fd9299526c12e8d5&format=1")
    Call<MonedaCambio> getCambio(@Query("currencies") String moneda);

}
