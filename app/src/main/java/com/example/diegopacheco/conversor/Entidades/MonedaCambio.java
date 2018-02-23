package com.example.diegopacheco.conversor.Entidades;

import java.util.HashMap;

/**
 * Created by diego.pacheco on 20/02/2018.
 */

public class MonedaCambio {
    public String success;
    public String terms;
    public String privacy;
    public int timestamp;
    public String source;
    HashMap<String, Double> quotes;

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

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public HashMap<String, Double> getQuotes() {
        return quotes;
    }

    public void setQuotes(HashMap<String, Double> quotes) {
        this.quotes = quotes;
    }
}
