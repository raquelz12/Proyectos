package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class combustion 
extends vehiculo
implements Utility
{
    private int años;
    private double emisionesAnuales;
    
    public combustion(int años, double emisiones, YearMonth fecha){
        this.fechaAdq = fecha;
        this.año = fecha.getYear();
        this.mes = fecha.getMonthValue();
        this.años = años;
        this.type = "combustion";
        this.emisionesAnuales = emisiones;
    }
    
    //////////GETTER////////

    public int getAños() {
        return años;
    }

    public double getEmisionesAnuales() {
        return emisionesAnuales;
    }
    
    //////////END-GETTER////////
    @Override
    public double calcularImpuesto(){
        return emisionesAnuales*años*1.005; 
    }
}
