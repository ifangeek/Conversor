package com.example.diegopacheco.conversor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diegopacheco.conversor.CallBack.MonedaService;
import com.example.diegopacheco.conversor.Entidades.Currencies;
import com.example.diegopacheco.conversor.Entidades.Moneda;
import com.example.diegopacheco.conversor.Entidades.MonedaCambio;
import com.example.diegopacheco.conversor.Entidades.Quotes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner desde,hacia;
    Button convertir;
    EditText cantidad;
    TextView result;
    double exchangePrice;
    HashMap<String, String> monedaList;
    HashMap<String,Double> cambiomonedaList;
    List<Currencies> listaCurrencies = null;
    List<Quotes> listaQuotes = null;
    public String dato="";
    public static final String BASE_URL = "http://apilayer.net/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        desde = (Spinner) findViewById(R.id.spnDesde);
        hacia = (Spinner)findViewById(R.id.spnHacia);
        convertir = (Button)findViewById(R.id.btnConvertir);
        cantidad = (EditText)findViewById(R.id.edtCantidad);
        result = (TextView)findViewById(R.id.tv_resultado);

        monedaList = new HashMap<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MonedaService service = retrofit.create(MonedaService.class);
        final Call<Moneda> call = service.getMoneda();

        listaCurrencies = new ArrayList<>();
        try {
            call.enqueue(new Callback<Moneda>() {

                @Override
                public void onResponse(Call<Moneda> call, Response<Moneda> response) {
                    Log.d("URL", response.toString());
                    Moneda moneda = response.body();
                    monedaList = moneda.getMonedas();
                    Log.d("LISTADO", monedaList.toString());
                    for (String key : monedaList.keySet()) {
                        Currencies currencies =new Currencies();
                        currencies.setCodigo(key);
                        currencies.setNombre(monedaList.get(key));
                        listaCurrencies.add(currencies);
                        Log.d("LISTADO", "CODIGO : "+key+"  NOMBRE :"+monedaList.get(key) + "");
                    }
                    //ordenar por nombre el listado
                    Collections.sort(listaCurrencies, new Comparator<Currencies>() {
                        @Override
                        public int compare(Currencies o1, Currencies o2) {
                            return o1.getNombre().compareTo(o2.getNombre());
                        }
                    });


                    ArrayAdapter<Currencies> adapter =
                            new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,listaCurrencies);
                    desde.setAdapter(adapter);

                    desde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            dato = "USD"+listaCurrencies.get(i).getCodigo();
                            Log.d("VALUE",dato);

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });

                    ArrayAdapter<Currencies> adapte1 =
                            new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,listaCurrencies);
                    hacia.setAdapter(adapte1);
                }
                @Override
                public void onFailure(Call<Moneda> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        listaQuotes= new ArrayList<>();

        Call<MonedaCambio> cambio = service.getCambio(dato);
        cambio.enqueue(new Callback<MonedaCambio>() {
            @Override
            public void onResponse(Call<MonedaCambio> call, Response<MonedaCambio> response) {
                Log.d("URL", response.toString());
                MonedaCambio monedacambio = response.body();
                cambiomonedaList = monedacambio.getQuotes();
                for(String key : cambiomonedaList.keySet()){

                    Quotes quotes = new Quotes();
                    quotes.setCodigo(key);
                    quotes.setValor(cambiomonedaList.get(key));

                    listaQuotes.add(quotes);
                    Log.d("LISTADO","COMBINACION: "+key+"  "+"MONEDA :"+cambiomonedaList.get(key).toString());
                }



            }

            @Override
            public void onFailure(Call<MonedaCambio> call, Throwable t) {

            }
        });

        Conversor();





    }


    private void Conversor(){
        convertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("#.00");
                for(int i=0;i<listaQuotes.size();i++){
                    if(dato.equals(listaQuotes.get(i).getCodigo())){
                        exchangePrice = listaQuotes.get(i).getValor();
                        Log.d("VALUE",exchangePrice+"");
                    }
                }
                double cant =Double.parseDouble(String.valueOf(cantidad.getText()));
                Log.d("VALUE",cant+"");
                double resultado = Double.parseDouble(df.format(cant * exchangePrice));
                Log.d("VALUE",resultado+"");
                result.setText(((Double) resultado).toString());




            }
        });
    }

}