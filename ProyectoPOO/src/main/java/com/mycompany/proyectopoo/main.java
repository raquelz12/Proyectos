package com.mycompany.proyectopoo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.YearMonth;
import java.util.Scanner;

public class main {
    
    public static void main(String[] args){
        
        byte op;
        byte op2;
        byte op3;
        double zonaMin = 0;
        double zonaMax = 0;
        boolean control = false;
        //Contador que representa el numero de vecinos en el sistema
        int contV = 0;
        //Numero de importes realizados en el sistema
        int contI = 0;
        //IDEA: importe1(Recaudado, fecha, zona, categoria);
        importes[] importes = new importes[1500];
        double importeTot = 0;
        //array con los vecinos del sistema
        vecino[] vecinos = new vecino[435];
        Scanner sc = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        //Parametros del importe
        YearMonth fechaIm = null;
        String categoria = null;
        double recaudado = 0;
        double zonaIm = 0;
        
        //////////LECTURA DE FICHERO///////////
        
        try (BufferedReader br = new BufferedReader(new FileReader("vecinos.csv"))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                //dividimos la linea que cogemos en cada parametro
                String[] parametros = linea.split(";");          
                if(parametros.length > 2){
                    vecino v1 = new vecino(parametros[0],parametros[1],parametros[2]);
                    vecinos[contV] = v1;
                    contV++;
                    
                        v1.crearArray(1500);
                        v1.crearInmuebles(1500);
                        v1.crearVehiculos(1500);
                        v1.crearAnimales(1500);
                
                //bucle para añadir al vecino todas sus posesiones
                    for(int x = 3; x < parametros.length; x++){
                    
                        if(parametros[x].compareTo("vivienda")==0){
                            vivienda vi1 = new vivienda(Double.parseDouble(parametros[x + 4]), Integer.parseInt(parametros[x + 5]), Double.parseDouble(parametros[x + 3]), YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                            v1.asignarVivienda(vi1);
                        }
                        else{
                            if(parametros[x].compareTo("local")==0){
                                local l1 = new local(Double.parseDouble(parametros[x + 4]), Integer.parseInt(parametros[x + 5]), Double.parseDouble(parametros[x + 3]), YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                v1.asignarLocal(l1);
                            }
                            else{
                                if(parametros[x].compareTo("nave")==0){
                                    nave n1 = new nave(Double.parseDouble(parametros[x + 4]), Double.parseDouble(parametros[x + 3]), YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                    v1.asignarNave(n1);
                                }
                                else{
                                    if(parametros[x].compareTo("electrico")==0){
                                        electrico e1 = new electrico(Integer.parseInt(parametros[x + 4]), YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                        v1.asignarElectrico(e1);
                                    }
                                    else{
                                        if(parametros[x].compareTo("mecanico")==0){
                                            mecanico m1 = new mecanico(Integer.parseInt(parametros[x + 4]), YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                            v1.asignarMecanico(m1);
                                        }
                                    else{
                                        if(parametros[x].compareTo("combustion")==0){
                                            combustion c1 = new combustion( Integer.parseInt(parametros[x + 4]), Double.parseDouble(parametros[x + 5]), YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                            v1.asignarCombustion(c1);
                                        }
                                        else{
                                            switch(parametros[x]){
                                                case "GATO": {
                                                    animal gato = animal.GATO;
                                                    gato.setFechAdq(YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                                    gato.setEdad(Integer.parseInt(parametros[x+4]));  
                                                    v1.asignarAnimal(gato);
                                                    break;
                                                }
                                                case "PERRO": {
                                                    animal perro = animal.PERRO;
                                                    perro.setFechAdq(YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                                    perro.setEdad(Integer.parseInt(parametros[x+4]));  
                                                    v1.asignarAnimal(perro);
                                                    break;
                                                }
                                                case "HAMSTER": {
                                                    animal hamster = animal.HAMSTER;
                                                    hamster.setFechAdq(YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                                    hamster.setEdad(Integer.parseInt(parametros[x+4]));  
                                                    v1.asignarAnimal(hamster);
                                                    break;
                                                }
                                                case "TORTUGA": {
                                                    animal tortuga = animal.TORTUGA; 
                                                    tortuga.setFechAdq(YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                                    tortuga.setEdad(Integer.parseInt(parametros[x+4]));  
                                                    v1.asignarAnimal(tortuga);
                                                    break;
                                                }
                                                case "PEZ": {
                                                    animal pez = animal.PEZ; 
                                                    pez.setFechAdq(YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                                    pez.setEdad(Integer.parseInt(parametros[x+4]));   
                                                    v1.asignarAnimal(pez);
                                                    break;
                                                }
                                                case "OTROANIMAL": {
                                                    animal otro = animal.OTROANIMAL; 
                                                    otro.setFechAdq(YearMonth.of(Integer.parseInt(parametros[x+1]), Integer.parseInt(parametros[x+2])));
                                                    otro.setEdad(Integer.parseInt(parametros[x+4]));   
                                                    v1.asignarAnimal(otro);
                                                    break;
                                                }}}}}}}}}}}
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //////////END-LECTURA DE FICHERO///////////
        
        //////MENU principal///////
        do {
            System.out.println("1.Importe recaudado por fecha");
            System.out.println("2.Importe recaudado por zonas");
            System.out.println("3.Importe recaudado por categoria");
            System.out.println("4.Lista de vecinos deudores");
            System.out.println("5.Generar giro de impuestos");
            System.out.println("6.Liquidar pago");
            System.out.println("7.Registro de vecino y propiedades");
            System.out.println("0.Salir");
            System.out.println("Seleccione opcion");
            op = sc.nextByte();
            switch (op) {
                case 1: {
                    System.out.println("Introduce el varemo de fechas: ");
                    System.out.println("Año a partir del cual filtrar:");
                    scanner.nextLine();
                    int añoMin = scanner.nextInt();
                    System.out.println("Mes a partir del cual filtrar:");
                    scanner.nextLine();
                    int mesMin = scanner.nextInt();
                    System.out.println("Año tope superior:");
                    scanner.nextLine();
                    int añoMax = scanner.nextInt();
                    System.out.println("Mes tope superior:");
                    scanner.nextLine();
                    int mesMax = scanner.nextInt();
                    YearMonth min = YearMonth.of(añoMin, mesMin);
                    YearMonth max = YearMonth.of(añoMax, mesMax);                    
                    for(int i = 0; i < contI; i++){
                        if(importes[i].getFecha().compareTo(min)>0 && importes[i].getFecha().compareTo(max)<0){
                            importeTot = importeTot + importes[i].getRecaudado();
                        }
                    }
                    break;
                }//Importe fecha
                
                case 2: {
                    if(contI == 0){
                        System.out.println("No se han producido importes todavía");
                    }
                    else{
                        do{
                            System.out.println("Introduce el varemo de zona sobre el cual buscar el importe recaudado: ");
                            System.out.println("Introduce el valor mínimo del varemo (mayor o igual a 2): ");
                            zonaMin = scanner.nextDouble();
                            System.out.println("Introduce el valor máximo del varemo (menor o igual a 10): ");
                            zonaMax = scanner.nextDouble();
                        }while(zonaMin <= 2 && zonaMax >= 10);
                        
                        for(int i = 0; i < contI; i++){
                            importes i1 = importes[i];
                            if(i1.getZona() <= zonaMax && i1.getZona() >= zonaMin){
                                importeTot = importeTot + importes[i].getRecaudado();
                            }
                        }
                        System.out.println("Lo recaudado en esa zona ha sido: " + importeTot);
                        importeTot = 0;
                    }
                    break;
                }//Importe zona
                
                case 3: {
                    if(contI == 0){
                        System.out.println("No se han producido importes todavía");
                    }
                    else{
                        scanner.nextLine();
                        System.out.println("Introduce la categoria sobre la cual filtrar los importes (vehiculo, inmueble, animal): ");
                        String categoria2 = scanner.nextLine();
                        for(int i = 0; i < contI; i++){
                            importes i1 = importes[i];
                            switch(categoria2){
                                case "vehiculo": {
                                    if(importes[i].getCategoria().compareTo("vehiculo") == 0){
                                        importeTot = importeTot + importes[i].getRecaudado();
                                    }
                                    break;
                                }
                                case "inmueble": {
                                    if(importes[i].getCategoria().compareTo("inmueble") == 0){
                                        importeTot = importeTot + importes[i].getRecaudado();
                                    }
                                    break;
                                }
                                case "animal": {
                                    if(importes[i].getCategoria().compareTo("animal") == 0){
                                        importeTot = importeTot + importes[i].getRecaudado();
                                    }
                                    break;
                                }
                                default: {
                                    System.out.println("No es una categoria correcta");
                                    break;
                                }}
                            
                            if(importeTot == 0){
                                System.out.println("No se ha recaudado nada");
                            }
                        }
                        if(importeTot > 0){
                            System.out.println("Lo recaudado en esa zona ha sido: " + importeTot);
                            importeTot = 0;
                        }
                        else{
                            importeTot = 0;
                        }
                    }
                    break;
                }//Importe categoria
                
                case 4: {
                    boolean vacio = true;
                    for(int i = 0; i<contV; i++){
                        if(vecinos[i].getGiro().getDeuda() > 0){
                            System.out.println(vecinos[i]);
                            vacio = false;
                        }
                    }
                    if(vacio == true){
                        System.out.println("No hay ningun vecino deudor");
                    }
                    break;
                }//Vecinos deudores
                
                case 5: {
                    System.out.println("¿Qué cliente quiere realizar su giro de impuestos?:\n");
                    int cont = 0;
                    //Lista de clientes, sacada desde vecinos.csv
                    try (BufferedReader reader = new BufferedReader(new FileReader("vecinos.csv"))) {
                        String linea;
                        
                        //Mientras se sigan leyendo nuevas lineas del documento
                        while ((linea = reader.readLine()) != null) {
                                // Dividmos la línea utilizando el punto y coma como delimitador al ser un csv
                                String[] parametros = linea.split(";");
                                System.out.println((cont+1) +": " + parametros[0] + " " + parametros[1]);
                                cont++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println("\nIntroduce tu eleccion:");
                    int seleccion = scanner.nextInt();
                    if(seleccion > cont || seleccion <= 0){
                        System.out.println("No se ha introducido una seleccion valida");
                        break;
                    }
                    //Seleccionamos el vecino soobre el que generar el giro
                    vecino v2 = vecinos[seleccion-1];
                    //generamos el giro de ese año, asumimos el costo como deuda desde este momento
                    v2.generarGiro();
                    break;
                }//Giro de impuestos
                
                case 6: {
                    System.out.println("¿Qué cliente quiere realizar su giro de impuestos?:\n");
                    int cont = 0;
                    
                    //Lista de clientes, sacada desde vecinos.csv
                    try (BufferedReader reader = new BufferedReader(new FileReader("vecinos.csv"))) {
                        String linea;
                        
                        //Mientras se sigan leyendo nuevas lineas del documento
                        while ((linea = reader.readLine()) != null) {
                                // Dividmos la línea utilizando el punto y coma como delimitador al ser un csv
                                String[] parametros = linea.split(";");
                                System.out.println((cont+1) +": " + parametros[0] + " " + parametros[1]);
                                cont++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println("\nIntroduce tu eleccion:");
                    int seleccion = scanner.nextInt();
                    //Seleccionamos el vecino soobre el cual vamos a liquidar 
                    vecino v2 = vecinos[seleccion-1];
                    String[] pagados = v2.getPagados();
                    String line;
                    String[] parametros;
                    do{
                    System.out.println("Propiedades del vecino sin abonar:");
                    for(int i = 0; i < pagados.length; i++){
                        line = pagados[i];
    
                    if(line != null){
                        parametros = line.split(";");
                        
                        if(parametros[0].compareTo("NOABONADO") == 0){
                            
                            System.out.print((i+1) +": ");
                                //Si tenemos una vivienda
                                if(parametros[1].compareTo("v")==0){
                                    System.out.println("Vivienda");
                                }
                                else{
                                    //Si tenemos una nave
                                    if(parametros[1].compareTo("n") == 0){
                                        System.out.println("Nave");
                                    }
                                    //Si tenemos un local
                                    else{
                                        if(parametros[1].compareTo("l") == 0){
                                            System.out.println("Local");
                                        }
                                        else{
                                            //Si tenemos un electrico
                                            if(parametros[1].compareTo( "e") == 0){
                                                System.out.println("Vehiculo Electrico");
                                            }
                                            else{
                                                //Si tenemos un mecanico
                                                if(parametros[1].compareTo("m") == 0){
                                                    System.out.println("Vehiculo Mecanico");
                                                }
                                                else{
                                                    //Si tenemos un vehiculo de combustión
                                                    if(parametros[1].compareTo("c") == 0){
                                                        System.out.println("Vehiculo de Combustion");
                                                    }
                                                    //Si tenemos un animal
                                                    else{
                                                        System.out.println("Animal");
                                                }}}}}}}}}
                    
                    //SELECCION de la posesion a abonar
                    System.out.println("Introduce una propiedad a abonar");
                    int seleccion3 = scanner.nextInt();
                    line = pagados[seleccion3-1];
                    parametros = line.split(";");
                        
                    //Liquidar la posesion
                    v2.liquidar(Double.parseDouble(parametros[2]));
                    pagados[0] = "ABONADO;"+parametros[1]+";"+parametros[2]+parametros[3];
                    v2.setPagados(pagados);

                    //Creamos el importe
                                if(parametros[1].compareTo("v")==0){
                                    System.out.println("Nave");
                                    categoria = "inmueble";
                                }
                                else{
                                    //Si tenemos una nave
                                    if(parametros[1].compareTo("n") == 0){
                                        System.out.println("Nave");
                                        categoria = "inmueble";
                                    }
                                    //Si tenemos un local
                                    else{
                                        if(parametros[1].compareTo("l") == 0){
                                            System.out.println("Local");
                                            categoria = "inmueble";
                                        }
                                        else{
                                            //Si tenemos un electrico
                                            if(parametros[1].compareTo( "e") == 0){
                                                System.out.println("Vehiculo Electrico");
                                                categoria = "vehiculo";
                                            }
                                            else{
                                                //Si tenemos un mecanico
                                                if(parametros[1].compareTo("m") == 0){
                                                    System.out.println("Vehiculo Mecanico");
                                                    categoria = "vehiculo";
                                                }
                                                else{
                                                    //Si tenemos un vehiculo de combustión
                                                    if(parametros[1].compareTo("c") == 0){
                                                        System.out.println("Vehiculo de Combustion");
                                                        categoria = "vehiculo";
                                                    }
                                                    //Si tenemos un animal
                                                    else{
                                                        System.out.println("Animal");
                                                        categoria = "animal";
                                                }}}}}}
                    recaudado = Double.parseDouble(parametros[2]);
                    zonaIm = Double.parseDouble(parametros[3]);
                    importes[contI] = new importes(zonaIm, categoria, recaudado);
                    contI++;

                    //Condicion salida de bucle
                    System.out.println("Se desea abonar algún impuesto más?(0-no, 1-si)");
                    scanner.nextLine();
                    int seleccion2 = scanner.nextInt();
                    if(seleccion2 == 0){
                        control = true;
                    }
                    }while(control == false);
                    break;
                }//Liquidar pago
                  
                case 7: {
                    //Introducimos los datos del vecino
                    System.out.println("Introduce el apellido del vecino");
                    String apellido = scanner.nextLine();
                    System.out.println("Introduce el nombre del vecino");
                    String nombre = scanner.nextLine();
                    System.out.println("Introduce la direccion habitual del vecino");
                    String direccion = scanner.nextLine();
                    
                    //creamos el vecino
                    vecino v1 = new vecino(nombre, apellido, direccion);
                    vecinos[contV] = v1;
                    contV++;
                    
                    //Asignamos en el array de bienes del vecino las propiedades que se necesiten
                    System.out.println("Cuantos bienes se van a asignar al vecino?");
                    int finish = scanner.nextInt();
                    int cont = finish;
                    //Creamos el array con las posiciones seleccionadas
                    v1.crearArray(finish);
                    
                    //A CAMBIAR
                    v1.crearInmuebles(finish);
                    v1.crearVehiculos(finish);
                    v1.crearAnimales(finish);
                    //A CAMBIAR
                    
                    
                    //Bucle para asignar los objetos al array
                    do{
                        System.out.println("////////Menu bienes a asignar////////");
                        System.out.println("1.Asignar Inmueble");
                        System.out.println("2.Asignar Vehículo de transporte");
                        System.out.println("3.Asignar Animal de compañía");
                        op2 = sc.nextByte();
                        
                        switch(op2){
                            //Bucle para asignar inmuebles
                            case 1: {
                                System.out.println("////////Menu inmuebles a asignar////////");
                                System.out.println("1.Asignar Vivienda");
                                System.out.println("2.Asignar Local");
                                System.out.println("3.Asignar Nave");
                                op3 = sc.nextByte();
                                
                                switch(op3){
                                    case 1: {
                                        System.out.println("Introduce los metros útiles de la vivienda");
                                        double metros = scanner.nextDouble();
                                        System.out.println("Introduce las emisiones anuales de la vivienda");
                                        double emisiones = scanner.nextDouble();
                                        System.out.println("Introduce el valor de la zona de la vivienda");
                                        double zona = scanner.nextDouble();
                                        System.out.println("Introduce el año de adquisición de la vivienda");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición de la vivienda");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        
                                        vivienda vi1 = new vivienda(metros, emisiones, zona, YearMonth.of(año,mes));
                                        v1.asignarVivienda(vi1);
                                        break;
                                    }
                                    //locales
                                    case 2: {
                                        System.out.println("Introduce los metros útiles del local");
                                        double metros = scanner.nextDouble();
                                        System.out.println("Introduce el aforo del local");
                                        int aforo = scanner.nextInt();
                                        System.out.println("Introduce el valor de la zona del local");
                                        double zona = scanner.nextDouble();
                                        System.out.println("Introduce el año de adquisición del local");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del local");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        
                                        local l1 = new local(metros, aforo, zona, YearMonth.of(año,mes));
                                        v1.asignarLocal(l1);
                                        break;
                                    }//End-locales
                                    
                                    case 3: {//nave
                                        System.out.println("Introduce los metros útiles de la nave");
                                        double metros = scanner.nextDouble();
                                        System.out.println("Introduce el valor de la zona de la nave");
                                        double zona = scanner.nextDouble();
                                        System.out.println("Introduce el año de adquisición de la nave");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición de la nave");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        
                                        nave n1 = new nave(metros, zona, YearMonth.of(año,mes));
                                        v1.asignarNave(n1); 
                                        break;
                                    }//End-nave  
                                }
                                System.out.println("\nInmueble introducido con esito\n");
                                break;
                            }//End-Asignar inmueble
                            
                            //Asignar vehiculo
                            case 2:{
                                System.out.println("////////Menu vehiculos a asignar////////");
                                System.out.println("1.Asignar vehiculo electrico");
                                System.out.println("2.Asignar vehiculo de combustion");
                                System.out.println("3.Asignar vehiculo mecanico");
                                op3 = sc.nextByte();
                                
                                switch(op3){
                                    //Electrico
                                    case 1: {
                                        System.out.println("Introduce la autonomía del vehiculo electrico (entero)");
                                        int autonomia = scanner.nextInt();
                                        System.out.println("Introduce el año de adquisición del vehiculo electrico");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del vehiculo electrico");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        
                                        electrico e1 = new electrico(autonomia, YearMonth.of(año,mes));
                                        v1.asignarElectrico(e1);
                                        break;
                                    }//END-Electrico
                                    
                                    //Combustion
                                    case 2: {
                                        System.out.println("Introduce los años del vehiculo");
                                        int años = scanner.nextInt();
                                        System.out.println("Introduce las emisiones del vehiculo");
                                        double emisiones = scanner.nextDouble();
                                        System.out.println("Introduce el año de adquisición del vehiculo");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del vehiculo");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        
                                        combustion c1 = new combustion(años, emisiones, YearMonth.of(año,mes));
                                        v1.asignarCombustion(c1);
                                        break;
                                    }//End-Combustion
                                    
                                    //Mecanico
                                    case 3: {
                                        System.out.println("Introduce los años del vehículo");
                                        int años = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("Introduce el año de adquisición del vehiculo");
                                        int año = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("Introduce el mes de adquisición del vehiculo");
                                        int mes = scanner.nextInt();
                                        
                                        mecanico m1 = new mecanico(años, YearMonth.of(año,mes));
                                        v1.asignarMecanico(m1); 
                                        break;
                                    }//End-Mecanico
                                }
                                System.out.println("\nVehículo introducido con esito\n");
                                break;
                            }//End-Asignar Vehículo
                            
                            case 3:{//Asignar Animal
                                
                                System.out.println("Introduce el tipo de animal a registrar: (Debe escribirse en minúsculas, ej: gato)");
                                scanner.nextLine();
                                String tipo = scanner.nextLine();
                                switch(tipo){
                                    case "gato": {
                                        animal gato = animal.GATO; 
                                        System.out.println("Introduce la edad del gato(en meses)");
                                        int edad = scanner.nextInt();
                                        System.out.println("Introduce el año de adquisición del gato");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del gato");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        gato.setFechAdq(YearMonth.of(año,mes));
                                        gato.setEdad(edad);
                                        v1.asignarAnimal(gato);
                                        break;
                                    }
                                    case "perro": {
                                        animal perro = animal.PERRO; 
                                        System.out.println("Introduce la edad del perro(en meses)");
                                        int edad = scanner.nextInt();
                                        System.out.println("Introduce el año de adquisición del perro");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del perro");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        perro.setFechAdq(YearMonth.of(año,mes));
                                        perro.setEdad(edad);  
                                        v1.asignarAnimal(perro);
                                        break;
                                    }
                                    case "tortuga": {
                                        animal tortuga = animal.TORTUGA; 
                                        System.out.println("Introduce la edad de la tortuga(en meses)");
                                        int edad = scanner.nextInt();
                                        System.out.println("Introduce el año de adquisición del animal");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del animal");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        tortuga.setFechAdq(YearMonth.of(año,mes));
                                        tortuga.setEdad(edad);  
                                        v1.asignarAnimal(tortuga);
                                        break;
                                    }
                                    case "hamster": {
                                        animal hamster = animal.HAMSTER; 
                                        System.out.println("Introduce la edad del hamster(en meses)");
                                        int edad = scanner.nextInt();
                                        System.out.println("Introduce el año de adquisición del animal");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del animal");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        hamster.setFechAdq(YearMonth.of(año,mes));
                                        hamster.setEdad(edad); 
                                        v1.asignarAnimal(hamster);
                                        break;
                                    }
                                    case "pez": {
                                        animal pez = animal.PEZ; 
                                        System.out.println("Introduce la edad del pez(en meses)");
                                        int edad = scanner.nextInt();
                                        System.out.println("Introduce el año de adquisición  del animal");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del animal");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        pez.setFechAdq(YearMonth.of(año,mes));
                                        pez.setEdad(edad);  
                                        v1.asignarAnimal(pez);
                                        break;
                                    }
                                    default:{
                                        animal otro = animal.OTROANIMAL; 
                                        System.out.println("Introduce la edad del animal(en meses)");
                                        int edad = scanner.nextInt();
                                        System.out.println("Introduce el año de adquisición del animal");
                                        scanner.nextLine();
                                        int año = scanner.nextInt();
                                        System.out.println("Introduce el mes de adquisición del animal");
                                        scanner.nextLine();
                                        int mes = scanner.nextInt();
                                        otro.setFechAdq(YearMonth.of(año,mes));
                                        otro.setEdad(edad);
                                        v1.asignarAnimal(otro);
                                        break;
                                    }
                                }
                                break;
                            }//End-Asignar Animal
                            
                            default : {
                                System.out.println("No se ha introducido un parametro correcto");
                                break;
                            }
                        }
                        cont--;
                    }while(cont > 0);
                    //End-Asignar propiedades
                    
                    //listamos las propiedades, para que se introduzcan en el documento
                    v1.listar();
                    break;
                }//End-Registro de Vecino y propiedades del mismo
                
                case 0: {
                    System.out.println("Fin del programa");
                    break;
                }//fin de programa
                
                default: {
                    System.out.println("Seleccione una opcion correcta");
                    break;
                }
            }
        } while (op != 0);
        //////END-MENU///////
        
    }//Clase principal
    
}//main