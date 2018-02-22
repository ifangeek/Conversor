package com.example.diegopacheco.conversor;

import java.util.List;

/**
 * Created by diego.pacheco on 19/02/2018.
 */

public class Currencies {
    public String codigo;
    public String nombre;


    @Override
    public String toString() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
