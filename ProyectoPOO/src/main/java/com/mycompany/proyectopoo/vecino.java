package com.mycompany.proyectopoo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class vecino {

    private String nombre;
    private String apellido;
    private String direccion;
    private arrayBienes bienes;
    private String[] pago = new String[1500];
    private String[] tmp = new String[1500];
    public int posL;

/////////////GIRO DE IMPUESTOS/////////////
    private giro impuestos = new giro();
    
    public giro getGiro(){
        return impuestos;
    }
    
    public void generarGiro(){
        pago = getGiro().añadirImpuesto(bienes, posL, pago);
    }
    
    public void liquidar(double valor){
        impuestos.liquidar(valor);
    }
    
    public String[] getPagados(){
        return pago;
    }
    
    public void setPagados(String[] pago){
        this.pago = pago;
    }

    public arrayBienes getBienes(){
        return bienes;
    }
/////////////END-GIRO DE IMPUESTOS/////////////
    
    
    
    //Constructor vecino
    public vecino(String nombre, String apellido, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        posL = 0;
    }
    
///////////GETTER/////////////
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
    
    public String getDireccion() {
        return direccion;
    }
 ///////////END-GETTER/////////////
    
    
    //creacion array
    public void crearArray(int tam){
       bienes = new arrayBienes(tam);
    }
    //Inicializamos inmuebles
    public void crearInmuebles(int tam){
        bienes.crearInmuebles(tam);
    }
    
    public void crearAnimales(int tam){
        bienes.crearAnimales(tam);
    }
    
    public void crearVehiculos(int tam){
        bienes.crearVehiculos(tam);
    }
    
    //Asignar local al array
    public void asignarLocal(local a){
        bienes.setLocal(posL, a);
        posL = posL + 1;
    }
    
    //Asignar animales al array
    public void asignarAnimal(animal a){
        bienes.setAnimal(posL, a);
        posL = posL + 1;
    }
    
    //Asignar un vehiculo electrico al array
    public void asignarElectrico(electrico a){
        bienes.setElectrico(posL, a);
        posL = posL + 1;
    }
    
    //Asignar vehiculo mecanico al array
    public void asignarMecanico(mecanico a){
        bienes.setMecanico(posL, a);
        posL = posL + 1;
    }
    
    //Asignar vehiculo combustion al array
    public void asignarCombustion(combustion a){
        bienes.setCombustion(posL, a);
        posL = posL + 1;
    }
    
    //Asignar vivienda al array
    public void asignarVivienda(vivienda a){
        bienes.setVivienda(posL, a);
        posL = posL + 1;
    }
    
    //Asignar nave al array
    public void asignarNave(nave a){
        bienes.setNave(posL, a);
        posL = posL + 1;
    }

    //Funcion listar
    public void listar(){
        int posicion;
        pago = new String[1500];
        //Metemos en el documento csv toda la información del cliente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("vecinos.csv", true))) {
            bw.write(nombre);
            bw.write(";" + apellido);
            bw.write(";" + direccion);
            bw.write(";" + posL);

            //Qué tipo de objeto tenemos en esa posición del array?
            for(posicion = 0; posicion < posL; posicion++){
                //Si tenemos una vivienda
                if(bienes.getPos(posicion) == "v"){
                    bw.write(";vivienda");
                    bw.write(";"+bienes.getVivienda(posicion).getAño());
                    bw.write(";"+bienes.getVivienda(posicion).getMes());
                    bw.write(";"+bienes.getVivienda(posicion).getZona());
                    bw.write(";"+bienes.getVivienda(posicion).getMetrosTotales());
                    bw.write(";"+bienes.getVivienda(posicion).getEmisiones());
                }
                else{
                    //Si tenemos una nave
                    if(bienes.getPos(posicion) == "n"){
                        bw.write(";nave");
                        bw.write(";"+bienes.getNave(posicion).getAño());
                        bw.write(";"+bienes.getNave(posicion).getMes());
                        bw.write(";"+bienes.getNave(posicion).getZona());
                        bw.write(";"+bienes.getNave(posicion).getMetrosNave());
                    }
                    //Si tenemos un local
                    else{
                        if(bienes.getPos(posicion) == "l"){
                            bw.write(";local");
                            bw.write(";"+bienes.getLocal(posicion).getAño());
                            bw.write(";"+bienes.getLocal(posicion).getMes());
                            bw.write(";"+bienes.getLocal(posicion).getZona());
                            bw.write(";"+bienes.getLocal(posicion).getMetrosUtiles());
                            bw.write(";"+bienes.getLocal(posicion).getAforo());
                        }
                        else{
                            //Si tenemos un electrico
                            if(bienes.getPos(posicion) == "e"){
                                bw.write(";electrico");
                                bw.write(";"+bienes.getElectrico(posicion).getAño());
                                bw.write(";"+bienes.getElectrico(posicion).getMes());
                                bw.write(";e");
                                bw.write(";"+bienes.getElectrico(posicion).getKmAutonomia());
                            }
                            else{
                                //Si tenemos un mecanico
                                if(bienes.getPos(posicion) == "m"){
                                    bw.write(";mecanico");
                                    bw.write(";"+bienes.getMecanico(posicion).getAño());
                                    bw.write(";"+bienes.getMecanico(posicion).getMes());
                                    bw.write(";e");
                                    bw.write(";"+bienes.getMecanico(posicion).getAños());
                                }

                                else{
                                    //Si tenemos un vehiculo de combustión
                                    if(bienes.getPos(posicion) == "c"){
                                        bw.write(";combustion");
                                        bw.write(";"+bienes.getCombustion(posicion).getAño());
                                        bw.write(";"+bienes.getCombustion(posicion).getMes());
                                        bw.write(";e");
                                        bw.write(";"+bienes.getCombustion(posicion).getAños());
                                        bw.write(";"+bienes.getCombustion(posicion).getEmisionesAnuales());
                                    }
                                    //Si tenemos un animal
                                    else{
                                        bw.write(";" + bienes.getAnimal(posicion).name());
                                        bw.write(";" + bienes.getAnimal(posicion).getAño());
                                        bw.write(";" + bienes.getAnimal(posicion).getMes());
                                        bw.write(";e");
                                        bw.write(";" + bienes.getAnimal(posicion).getEdad());
                                    }}}}}}}
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//End-listar
    
    @Override
    public String toString(){
        return "Nombre: " + nombre + " " + apellido + " Deuda: " + getGiro().getDeuda();
    }
}