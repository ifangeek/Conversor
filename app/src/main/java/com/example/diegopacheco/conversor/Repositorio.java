package com.example.diegopacheco.conversor;

import android.util.Log;

import com.example.diegopacheco.conversor.Entidades.Currencies;
import com.example.diegopacheco.conversor.Entidades.Moneda;
import com.example.diegopacheco.conversor.Entidades.MonedaCambio;
import com.example.diegopacheco.conversor.Entidades.Quotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DIEGO on 5/03/2018.
 */

public class Repositorio {
    APIInterface apiInterface;
    APIClient client = new APIClient();
    HashMap<String,Double> cambiomonedaList;
    List<Quotes> listaQuotes = null;

    public interface CurrrencyResponse{
        void onSuccess(List<Currencies> listCurrencies);
        void onFailed(String mensaje);
    }

    public interface ChangeMoney{
        void onSuccess(List<Quotes> listQuotes);
        void onFailed(String mensaje);
    }
    public void listarMonedas(final CurrrencyResponse currrencyResponse){


        apiInterface = client.getClient().create(APIInterface.class);
        Call<Moneda> call = apiInterface.getMoneda();

        call.enqueue(new Callback<Moneda>() {
            @Override
            public void onResponse(Call<Moneda> call, Response<Moneda> response) {
                List<Currencies> listaCurrencies = new ArrayList<>();
                HashMap<String, String> monedaList;

                Moneda moneda = response.body();
                monedaList = moneda.getMonedas();
                Log.d("TAG", response.code() + "");
                for (String key : monedaList.keySet()) {
                    Currencies currencies = new Currencies();
                    currencies.setCodigo(key);
                    currencies.setNombre(monedaList.get(key));
                    listaCurrencies.add(currencies);
                }
                Collections.sort(listaCurrencies, new Comparator<Currencies>() {
                    @Override
                    public int compare(Currencies o1, Currencies o2) {
                        return o1.getNombre().compareTo(o2.getNombre());
                    }
                });
                currrencyResponse.onSuccess(listaCurrencies);
            }

            @Override
            public void onFailure(Call<Moneda> call, Throwable t) {
                currrencyResponse.onFailed("Error de internet");

            }
        });
    }


    public void ListarQuotes(String dato, final ChangeMoney changeMoney){

        listaQuotes = new ArrayList<>();
        apiInterface = client.getClient().create(APIInterface.class);
        Call<MonedaCambio> call = apiInterface.getCambio(dato);
        call.enqueue(new Callback<MonedaCambio>() {
            @Override
            public void onResponse(Call<MonedaCambio> call, Response<MonedaCambio> response) {
                MonedaCambio monedacambio = response.body();
                cambiomonedaList = monedacambio.getQuotes();
                for (String key : cambiomonedaList.keySet()) {
                    Quotes quotes = new Quotes();
                    quotes.setCodigo(key);
                    quotes.setValor(cambiomonedaList.get(key));
                    listaQuotes.add(quotes);
                }
                changeMoney.onSuccess(listaQuotes);
        }

            @Override
            public void onFailure(Call<MonedaCambio> call, Throwable t) {
                changeMoney.onFailed("Error de conexión");
            }
        });
    }





}
