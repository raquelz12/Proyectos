package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class importes {
    private YearMonth fecha;
    private double zona;
    private String categoria;
    private double recaudado;
    
/////////GETTER//////////
    public YearMonth getFecha() {
        return fecha;
    }

    public double getZona() {
        return zona;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getRecaudado() {
        return recaudado;
    } 
/////////END-GETTER//////////

    public importes(double zona, String categoria, double recaudado) {
        this.fecha = YearMonth.now();
        this.zona = zona;
        this.categoria = categoria;
        this.recaudado = recaudado;
    }
}
