package com.mycompany.proyectopoo;

import java.time.YearMonth;

public enum animal
implements Utility{
    //realizamos una enumeración donde cada animal tendrá asociado su multiplicador de cara a crear impuestos 
    PERRO(1.63),   
    GATO(1.4),    
    TORTUGA(1.3), 
    HAMSTER(1.2),
    PEZ(1.1),      
    OTROANIMAL(1.5);  

    private int edad;
    private double multiplicador;
    private YearMonth fechAdq;

    animal(double multiplicador) {
        this.multiplicador = multiplicador;
    }
    
    public YearMonth getFechAdq(){
        return YearMonth.of(getAño(),getMes());
    }
    
    public int getEdad() {
        return edad;
    }
    
    public int getAño() {
        return fechAdq.getYear();
    }
    
    public int getMes() {
        return fechAdq.getMonthValue();
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public void setFechAdq(YearMonth fechAdq) {
        this.fechAdq=fechAdq;
    }
    
    @Override
    public double calcularImpuesto(){
        return edad * multiplicador;
    }
}