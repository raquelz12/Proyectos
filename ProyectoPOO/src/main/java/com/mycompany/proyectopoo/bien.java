package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class bien {
    protected YearMonth fechaAdq;
    protected int año;
    protected int mes;
    protected String type;
    
    public void setFechaAdq(int Año, int mes){
        fechaAdq = YearMonth.of(Año,mes);
    }
    
    public int getMes(){
        return mes;
    }
    
    public int getAño(){
        return año;
    }    
        
    public YearMonth getFechaAdq(){
        return fechaAdq;
    }
    
    public String getType(){
        return type;
    }
}
