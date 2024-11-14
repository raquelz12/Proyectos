package com.mycompany.proyectopoo;

public class arrayBienes{
    //Creamos un array de objetos varios
    private local[] local;
    private vivienda[] vivienda;
    private nave[] nave;
    private String[] bienes;
    private electrico[] electrico;
    private combustion[] combustion;
    private mecanico[] mecanico;
    private animal[] animal;
    
    //En el constructor permitimos variar el tamaño del mismo
    public arrayBienes(int tam){
        bienes = new String[tam];
    }
    
    //Inicializamos los inmuebles
    public void crearInmuebles(int tam){
        local = new local[tam];
        vivienda = new vivienda[tam];
        nave = new nave[tam];
    }
    
    //Inicializamos los vehículos
    public void crearVehiculos(int tam){
        mecanico = new mecanico[tam];
        combustion = new combustion[tam];
        electrico = new electrico[tam];
    }
    
    //Inicializamos los animales
    public void crearAnimales(int tam){
        animal = new animal[tam];
    }
    
    public void setAnimal(int posicion, animal valor){
        animal[posicion] = valor;
        bienes[posicion] = "a";
    }
    
    //Asignar valores a cierta posición del array
    public void setLocal(int posicion, local valor){
        local[posicion] = valor;
        bienes[posicion] = "l";
    }
    
    public void setElectrico(int posicion, electrico valor){
        electrico[posicion] = valor;
        bienes[posicion] = "e";
    }
    
    public void setMecanico(int posicion, mecanico valor){
        mecanico[posicion] = valor;
        bienes[posicion] = "m";
    }

    public void setCombustion(int posicion, combustion valor){
        combustion[posicion] = valor;
        bienes[posicion] = "c";
    }
    
    public void setVivienda(int posicion, vivienda valor){
        vivienda[posicion] = valor;
        bienes[posicion] = "v";
    }
    
    public void setNave(int posicion, nave valor){
        nave[posicion] = valor;
        bienes[posicion] = "n";
    }
    
    public local getLocal(int posicion){
        return local[posicion];
    }
    
    public combustion getCombustion(int posicion){
        return combustion[posicion];
    }
    
    public electrico getElectrico(int posicion){
        return electrico[posicion];
    }
    
    public mecanico getMecanico(int posicion){
        return mecanico[posicion];
    }
    
    public nave getNave(int posicion){
        return nave[posicion];
    }
 
    public vivienda getVivienda(int posicion){
        return vivienda[posicion];
    }
    
    public animal getAnimal(int posicion){
        return animal[posicion];
    }
    
    public String getPos(int posicion){
        return bienes[posicion];
    }
}