package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class mecanico 
extends vehiculo
implements Utility
{
    private int años;
    
    public mecanico(int años, YearMonth fecha){
        this.fechaAdq = fecha;
        this.año = fecha.getYear();
        this.mes = fecha.getMonthValue();
        this.años = años;
        this.type = "mecanico";
    }
    
    //////////GETTER////////

    public int getAños() {
        return años;
    }
    
    //////////END-GETTER////////
    
    @Override
    public double calcularImpuesto(){
        return años/1.025; 
    }
    
}
