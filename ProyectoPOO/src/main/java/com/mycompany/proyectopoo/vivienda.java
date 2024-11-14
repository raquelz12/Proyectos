package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class vivienda
extends inmueble
implements Utility
{
    private double metrosTotales;
    private double emisionesAnuales;

//////SETTER//////
    
    public void setMetrosTotales(double metrosTotales) {
        this.metrosTotales = metrosTotales;
    }

    public void setEmisionesAnuales(double emisionesAnuales) {
        this.emisionesAnuales = emisionesAnuales;
    }

    public void setZona(double zona) {
        this.zona = zona;
    }
    
 //////END-SETTER//////

    
//////GETTER//////  

    public double getMetrosTotales() {
        return metrosTotales;
    }

    public double getZona() {
        return zona;
    }
    
    public double getEmisiones(){
        return emisionesAnuales;
    }
    
//////END-GETTER//////     
    
    
    //definición del método abstracto del padre
    @Override
        public double calcularImpuesto(){
            return zona*metrosTotales + emisionesAnuales ;
        }
        
    //constructor con todos los parámetros
    public vivienda(double metrosTotales, double emisionesAnuales, double zona, YearMonth fecha) {
        this.metrosTotales = metrosTotales;
        this.emisionesAnuales = emisionesAnuales;
        this.zona = zona;
        this.fechaAdq = fecha;
        this.año = fecha.getYear();
        this.mes = fecha.getMonthValue();
        type = "vivienda";
    }
    
    @Override
    public String toString(){
        return "Tipo: "+ getType()+" Metros: " + getMetrosTotales()+ " Zona: " + getZona() + " Fecha Adquisicion: " + getFechaAdq();
    }
    
}