package com.example.diegopacheco.conversor;

import com.example.diegopacheco.conversor.Entidades.Moneda;
import com.example.diegopacheco.conversor.Entidades.MonedaCambio;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {


    @GET("/api/list")
    Call<Moneda> getMoneda();

    @GET("/api/live")
    Call<MonedaCambio> getCambio(@Query("currencies") String moneda);

}
