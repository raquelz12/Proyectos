package com.mycompany.proyectopoo;

import java.time.YearMonth;

public class local 
extends inmueble
implements Utility
{
    private double metrosUtiles;
    private int aforo;
    
//////SETTER//////
    public void setMetrosUtiles(double metrosUtiles) {
        this.metrosUtiles = metrosUtiles;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public void setZona(double zona) {
        this.zona = zona;
    }
 //////END-SETTER//////
    
//////GETTER//////  

    public double getMetrosUtiles() {
        return metrosUtiles;
    }

    public double getZona() {
        return zona;
    }
    
    public int getAforo(){
        return aforo;
    }
    
//////END-GETTER//////         
    
    
    //definición del método abstracto del padre
    @Override
        public double calcularImpuesto(){
            return ((metrosUtiles)*(zona)) + (aforo*0.025);
        }
    //constructor con todos los parámetros
    public local(double metrosUtiles, int aforo, double zona, YearMonth fecha) {
        this.metrosUtiles = metrosUtiles;
        this.aforo = aforo;
        this.zona = zona;
        this.fechaAdq = fecha;
        this.año = fecha.getYear();
        this.mes = fecha.getMonthValue();
        type = "local";
    }
    
    //constructor nulo
    public local() {
        metrosUtiles = 0;
        aforo = 0;
        zona = 0;
        fechaAdq = null;
    }
    
    @Override
    public String toString(){
        return "Tipo: "+ getType()+" Metros: " + getMetrosUtiles()+ " Zona: " + getZona() + " Fecha Adquisicion: " + getFechaAdq() + " Aforo " + getAforo();
    }
}