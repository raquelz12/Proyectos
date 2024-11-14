package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class electrico 
extends vehiculo
implements Utility
{
    private int kmAutonomia;
    
    public electrico(int autonomia, YearMonth fecha){
        this.fechaAdq = fecha;
        this.año = fecha.getYear();
        this.mes = fecha.getMonthValue();
        this.type = "electrico";
        this.kmAutonomia = autonomia;
    }
    
    //////////GETTER////////

    public int getKmAutonomia() {
        return kmAutonomia;
    }
    
    //////////END-GETTER////////
    @Override
    public double calcularImpuesto(){
        return kmAutonomia*0.0125; 
    }
}
