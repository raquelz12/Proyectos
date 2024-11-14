package com.mycompany.proyectopoo;
        
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;


public class giro {
    
    private double deuda;
    private double impuesto;
    private int finalArray;
    
    
    //saldar parte o totalidad de la deuda
    public void liquidar(double pago){
        if(deuda == 0){
            System.out.println("No existe deuda que saldar");    
        }
        else{
            deuda = deuda - pago;
            if(deuda<0){
                deuda = 0;
            }
        }
    }
    
    public String[] añadirImpuesto(arrayBienes bienes, int tamaño, String[] pagos){
        int posicion;
        YearMonth fecha;
        float multi;
        //Hallamos hasta donde está escrito el array pagos
        
        
        //Añadimos los impuestos
        for(posicion = 0; posicion<tamaño; posicion++){
            //Si tenemos una vivienda
                if(bienes.getPos(posicion).compareTo("v")==0){
                    fecha = bienes.getVivienda(posicion).getFechaAdq();
                    //calculamos la diferencia de meses
                    multi = fecha.until(YearMonth.now(), ChronoUnit.MONTHS);
                    //Por cada més sin pagar hacemos un calcularImpuesto
                    impuesto = impuesto + (bienes.getVivienda(posicion).calcularImpuesto()*multi);
                    pagos[finalArray+posicion] = ("NOABONADO;v;" + String.valueOf(bienes.getVivienda(posicion).calcularImpuesto()*multi) + String.valueOf(bienes.getVivienda(posicion).getZona()));
                }
                else{
                    //Si tenemos una nave
                    if(bienes.getPos(posicion).compareTo("n")==0){
                        fecha = bienes.getNave(posicion).getFechaAdq();
                        multi = fecha.until(YearMonth.now(), ChronoUnit.MONTHS);
                        impuesto = impuesto + (bienes.getNave(posicion).calcularImpuesto()*multi);
                        pagos[finalArray+posicion] = ("NOABONADO;n;" + String.valueOf(bienes.getNave(posicion).calcularImpuesto()*multi) + String.valueOf(bienes.getNave(posicion).getZona()));
                    }
                    //Si tenemos un local
                    else{
                        if(bienes.getPos(posicion).compareTo("l")==0){
                            fecha = bienes.getLocal(posicion).getFechaAdq();
                            multi = fecha.until(YearMonth.now(), ChronoUnit.MONTHS);
                            impuesto = impuesto + (bienes.getLocal(posicion).calcularImpuesto()*multi);
                            pagos[finalArray+posicion] = ("NOABONADO;l;" + String.valueOf(bienes.getLocal(posicion).calcularImpuesto()*multi) + String.valueOf(bienes.getLocal(posicion).getZona()));
                        }
                        else{
                            //Si tenemos un electrico
                            if(bienes.getPos(posicion).compareTo("e")==0){
                                fecha = bienes.getElectrico(posicion).getFechaAdq();
                                multi = fecha.until(YearMonth.now(), ChronoUnit.MONTHS);
                                impuesto = impuesto + (bienes.getElectrico(posicion).calcularImpuesto()*multi);
                                pagos[finalArray+posicion] = ("NOABONADO;e;" + String.valueOf(bienes.getElectrico(posicion).calcularImpuesto()*multi)+";"+0);
                            }
                            else{
                                //Si tenemos un mecanico
                                if(bienes.getPos(posicion).compareTo("m")==0){
                                    fecha = bienes.getMecanico(posicion).getFechaAdq();
                                    multi = fecha.until(YearMonth.now(), ChronoUnit.MONTHS);
                                    impuesto = impuesto + (bienes.getMecanico(posicion).calcularImpuesto()*multi);
                                    pagos[finalArray+posicion] = ("NOABONADO;m;" + String.valueOf(bienes.getMecanico(posicion).calcularImpuesto()*multi)+";"+0);
                                }
                                else{
                                    //Si tenemos un vehiculo de combustión
                                    if(bienes.getPos(posicion).compareTo("c")==0){
                                        fecha = bienes.getCombustion(posicion).getFechaAdq();
                                        multi = fecha.until(YearMonth.now(), ChronoUnit.MONTHS);
                                        impuesto = impuesto + (bienes.getCombustion(posicion).calcularImpuesto()*multi);
                                        pagos[posicion] = ("NOABONADO;c;" + String.valueOf(bienes.getCombustion(posicion).calcularImpuesto()*multi)+";"+0);
                                    }
                                    //Si tenemos un animal
                                    else{
                                        fecha = bienes.getAnimal(posicion).getFechAdq();
                                        multi = fecha.until(YearMonth.now(), ChronoUnit.MONTHS);
                                        impuesto = impuesto + (bienes.getAnimal(posicion).calcularImpuesto()*multi);
                                        pagos[finalArray+posicion] = ("NOABONADO;a;" + String.valueOf(bienes.getAnimal(posicion).calcularImpuesto()*multi)+";"+0);
                                    }}}}}}}
        //cuando termine la rotación de calcular los impuestos de todos las propiedades lo asumimos como deuda del vecino
        deuda = impuesto;
        return pagos;
    }
    
    public double getDeuda(){
        return deuda;
    }
    
    public double getImpuesto(){
        return deuda;
    }
    
    public void resetImpuesto(){
        this.impuesto = 0;
    }
}
