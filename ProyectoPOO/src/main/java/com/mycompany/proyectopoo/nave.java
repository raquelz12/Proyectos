package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class nave 
extends inmueble
implements Utility
{
    private double metrosNave;
    
//////SETTER//////
    public void setMetrosNave(double metrosNave) {
        this.metrosNave = metrosNave;
    }
    
    public void setZona(double zona) {
        this.zona = zona;
    }
//////END-SETTER//////

//////GETTER//////  

    public double getMetrosNave() {
        return metrosNave;
    }

    public double getZona() {
        return zona;
    }
    
//////END-GETTER//////
    
    //definición del método abstracto del padre
    @Override
    public double calcularImpuesto(){
        return zona*metrosNave;
    }
    
    //Constructor con todos los parámetros
    public nave(double metrosNave, double zona, YearMonth fecha) {
        this.metrosNave = metrosNave;
        this.zona = zona;
        this.fechaAdq = fecha;
        this.año = fecha.getYear();
        this.mes = fecha.getMonthValue();
        type = "nave";
    }
    
    //Para imprimir las instancias
    @Override
    public String toString(){
        return "Tipo: "+ getType()+" Metros: " + getMetrosNave()+ " Zona: " + getZona() + " Fecha Adquisicion: " + getFechaAdq();
    }
}