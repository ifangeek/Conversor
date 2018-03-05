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
import android.widget.Toast;

import com.example.diegopacheco.conversor.Entidades.Currencies;
import com.example.diegopacheco.conversor.Entidades.Quotes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Repositorio.CurrrencyResponse {

    Spinner desde;
    Button convertir;
    EditText cantidad;
    TextView result;
    double exchangePrice;
    List<Quotes> listaQuotes = null;
    public String dato = "";
    Repositorio repositorio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        desde = (Spinner) findViewById(R.id.spnDesde);
        convertir = (Button) findViewById(R.id.btnConvertir);
        cantidad = (EditText) findViewById(R.id.edtCantidad);
        result = (TextView) findViewById(R.id.tv_resultado);
        Convertir();

        listaQuotes = new ArrayList<>();

        repositorio = new Repositorio();
        repositorio.listarMonedas(this);
        repositorio.ListarQuotes(dato, new Repositorio.ChangeMoney() {
            @Override
            public void onSuccess(List<Quotes> listQuotes) {
                listaQuotes = new ArrayList<>();
                listaQuotes = listQuotes;
            }

            @Override
            public void onFailed(String mensaje) {

            }
        });
    }


    public void Convertir() {
        convertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("#.00");
                for (int i = 0; i < listaQuotes.size(); i++) {
                    if (dato.equals(listaQuotes.get(i).getCodigo())) {
                        exchangePrice = listaQuotes.get(i).getValor();
                        Log.d("VALUE", exchangePrice + "");
                    }
                }
                if (cantidad.getText().toString().equals("") == true) {
                    result.setText("");
                    Toast.makeText(getBaseContext(), "Ingrese una cantidad", Toast.LENGTH_LONG).show();
                } else {
                    double cant = Double.parseDouble(String.valueOf(cantidad.getText()));
                    double resultado = Double.parseDouble(df.format(cant * exchangePrice));
                    result.setText(((Double) resultado).toString());
                }


            }
        });
    }

    @Override
    public void onSuccess(final List<Currencies> listCurrencies) {

        ArrayAdapter<Currencies> adapter =
                new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listCurrencies);
        desde.setAdapter(adapter);

        desde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dato = "USD" + listCurrencies.get(i).getCodigo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onFailed(String mensaje) {
        Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}