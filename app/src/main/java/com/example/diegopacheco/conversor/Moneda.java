package com.example.diegopacheco.conversor;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by diego.pacheco on 19/02/2018.
 */

public class Moneda {

    private String success;
    private String terms;
    private String privacy;
    @SerializedName("currencies")
    HashMap<String, String> monedas;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public HashMap<String, String> getMonedas() {
        return monedas;
    }

    public void setMonedas(HashMap<String, String> monedas) {
        this.monedas = monedas;
    }
}
