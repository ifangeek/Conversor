package com.example.diegopacheco.conversor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner desde,hacia;
    HashMap<String, String> monedaList;
    List<String> listaCurrencies = null;
    public static final String BASE_URL = "http://apilayer.net/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        desde = (Spinner) findViewById(R.id.spnDesde);
        hacia = (Spinner)findViewById(R.id.spnHacia);
        monedaList = new HashMap<String, String>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MonedaService service = retrofit.create(MonedaService.class);

        Call<Moneda> call = service.getMoneda();
        Response<Moneda> response;
        listaCurrencies = new ArrayList<>();

        try {
            call.enqueue(new Callback<Moneda>() {

                @Override
                public void onResponse(Call<Moneda> call, Response<Moneda> response) {
                    Log.d("RESPUESTA", response.toString());

                    Moneda moneda = response.body();
                    monedaList = moneda.getMonedas();
                    Log.d("RESPUESTA", monedaList.toString());

                    for (String key : monedaList.keySet()) {
                        Log.d("RESPUESTA", key);
                        monedaList.get(key);
                        listaCurrencies.add(monedaList.get(key));
                        Log.d("RESPUESTA", monedaList.get(key) + "");
                    }


                     Log.d("CURRENCIES",listaCurrencies.toString());
                }

                @Override
                public void onFailure(Call<Moneda> call, Throwable t) {
                    Log.d("RESPUESTA", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,listaCurrencies);
        desde.setAdapter(adapter);

        desde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("posicion", String.valueOf(desde.getItemAtPosition(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,listaCurrencies);
        hacia.setAdapter(adapter1);




    }


}