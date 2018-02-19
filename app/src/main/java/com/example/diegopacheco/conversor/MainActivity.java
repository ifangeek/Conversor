package com.example.diegopacheco.conversor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner desde;
    HashMap<String,String> monedaList;
    List<Currencies> listaCurrencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        desde = (Spinner)findViewById(R.id.spnDesde);
        monedaList= new HashMap<String,String>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apilayer.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MonedaService service = retrofit.create(MonedaService.class);

        Call<Moneda> call = service.getMoneda();
        Response<Moneda> response ;
        final Currencies currencies = null;

        try {
           call.enqueue(new Callback<Moneda>() {

               @Override
               public void onResponse(Call<Moneda> call, Response<Moneda> response) {
                   Log.d("RESPUESTA", response.toString());

                       Moneda moneda = response.body();
                           monedaList =  moneda.getMonedas();
                           Log.d("RESPUESTA", monedaList.toString());

                           /* for(String key : monedaList.keySet()){
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item,key);
                               // currencies.setCodigo(key.toString());
                                Log.d("RESPUESTA",key);
                            }*/
                  // Log.d("CURRENCIES",currencies.getCodigo());




               }

               @Override
               public void onFailure(Call<Moneda> call, Throwable t) {
                   Log.d("RESPUESTA", t.getMessage());
               }
           });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
