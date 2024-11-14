#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pvm3.h>
#include <math.h>

int main(int argc, char** argv) {
	
//Abrimos los ficheros en formato de lectura
    	FILE* valoraciones = fopen("Valoraciones.txt", "r");
    	if (valoraciones == NULL) {
        	perror("Error al abrir el archivo");
        	return 1;
    	}

    	FILE* compras = fopen("Compras.txt", "r");
    	if (compras == NULL) {
        	perror("Error al abrir el archivo");
        return 1;
    	}
//Definimos las variables a utilizar
    	char linea[50];
    	char *palabra;
    	char nombre[50];
	char nombres[50][50];
	int num_nombres = 0;
    	int *valoracion = NULL;
	int num_apetecibilidades = 0;
	float *apetecibilidad = NULL;
    	int num_valoraciones = 0;
	int *compra = NULL;
	int num_compras = 0;
	int i;
	int x;
	int proceso = 0;
//leemos un documento y lo dividimos en líneas usando el strtok
    	while (fgets(linea, sizeof(linea), valoraciones) != NULL) {
        	palabra = strtok(linea, ";");
//Guardamos en nuestro array de nombres el nombre de la persona
		strcpy(nombres[num_nombres], palabra);
		num_nombres++;
//Guardamos las valoraciones	
		do{
            		num_valoraciones++;
			//Se trata de un array dinámico así que al no saber el número de valoraciones que vamos a tener tenemos que asignarle más memoria segun las guardamos
            		valoracion = (int*)realloc(valoracion, num_valoraciones * sizeof(int));
			//El texto es un string, lo almacenaremos con un int
                	valoracion[num_valoraciones - 1] = atoi(palabra);
        	}while (palabra = strtok(NULL, ";"));
    	}


//Repetimos el proceso para guardar las compras del otro fichero, en este caso no hará falta guardar los nombres pues ya los tenemos	
    	while (fgets(linea, sizeof(linea), compras) != NULL) {
        	palabra = strtok(linea, ";");
        	strcpy(nombre, palabra);

        	do{
            		num_compras++;
            		compra = (int*)realloc(compra, num_compras * sizeof(int));
                	compra[num_compras - 1] = atoi(palabra);
        	}while (palabra = strtok(NULL, ";"));
    	}
	
//Dividimos los arrays de valoraciones y compras para el esclavo 1 y el esclavo 2
//Uno será de longitud tamaño/2 y otro tamaño - tamaño/2 para permitir los tamaños impares
	int v1[num_valoraciones/2];
	int v2[num_valoraciones - num_valoraciones/2];
	int c1[num_compras/2];
	int c2[num_compras - num_compras/2];
	
	for(i = 0; i < num_valoraciones; i++){

		if(i < num_valoraciones/2){
			v1[i] = valoracion[i];
			c1[i] = compra[i];
		}
		else{
			v2[i - num_valoraciones/2] = valoracion[i];
			c2[i - num_valoraciones/2] = compra[i];
		}
	}
//Cerramos los documentos
    	fclose(valoraciones);
	fclose(compras);
	
//Configuramos la máquina virtual para mandar los arrays, primero con esclavo1
	int info=pvm_mytid();

   
	int cc1, tid, tarea = 1;
	cc1 = pvm_spawn("esclavo1", NULL, 1, "ubuntu-nodo1", 1, &tid);

	if (cc1 < 1) {
    		fprintf(stderr, "Error al spawnear el proceso Esclavo1\n");
    		pvm_exit();
    		return 1;
	}

	if (tid < 0) {
    		fprintf(stderr, "Error al obtener el ID de tarea del proceso Esclavo1\n");
    		pvm_exit();
    		return 1;
	}
//Mandamos los arrays, mandamos el tamaño que tienen y los propios arrays para que lo pueda interpretar el receptor
	pvm_initsend(PvmDataDefault);
	pvm_pkint(&proceso, 1, 0);
	pvm_pkint(&num_valoraciones, 1, 0);
	pvm_pkint(v1, num_valoraciones/2, 1);
	pvm_pkint(&num_compras, 1, 0);
	pvm_pkint(c1, num_compras/2, 1);
	pvm_send(tid, tarea);

//definimos el array de los resultados
    	float *a1;
	a1 = (float*)malloc(num_valoraciones/2 * sizeof(float));
    	pvm_recv(tid, -1);
    	pvm_upkfloat(a1, num_valoraciones/2, 1);
	
//Creamos/abrimos el archivo donde guardaremos la apetecibilidad
//Usamos w, es decir en caso de no existir el archivo se crea
 	FILE* apetecibilidades = fopen("Apetecibilidad.txt", "w");

	for(i = 1; i < num_valoraciones/2; i = i+=4){
		//vamos escribiendo por líneas siguiendo la estructura de una tabla dividiendo los parámetros por ";"
		fprintf(apetecibilidades, "%s;%f;%f;%f\n", nombres[x], a1[i],  a1[i+1],  a1[i+2]);
		x++;
	}

//repetimos el proceso pero para el esclavo 2, asignando los tamaños requeridos


	cc1 = pvm_spawn("esclavo2", NULL, 1, "ubuntu-nodo2", 1, &tid);

	if (cc1 < 1) {
    		fprintf(stderr, "Error al spawnear el proceso Esclavo2\n");
    		pvm_exit();
    		return 1;
	}

	if (tid < 0) {
    		fprintf(stderr, "Error al obtener el ID de tarea del proceso Esclavo2\n");
    		pvm_exit();
    		return 1;
	}	

	pvm_initsend(PvmDataDefault);
	pvm_pkint(&proceso, 1, 0);
	pvm_pkint(&num_valoraciones, 1, 0);
	pvm_pkint(v2, (num_valoraciones - num_valoraciones/2), 1);
	pvm_pkint(&num_compras, 1, 0);
	pvm_pkint(c2, (num_compras - num_compras/2), 1);
	pvm_send(tid, tarea);


    	float *a2;
	a2 = (float*)malloc((num_valoraciones - num_valoraciones/2) * sizeof(float));
    	pvm_recv(tid, -1);
    	pvm_upkfloat(a2, (num_valoraciones - num_valoraciones/2), 1);
	for(i = 1; i < num_valoraciones - num_valoraciones/2; i+=4){
		fprintf(apetecibilidades, "%s;%f;%f;%f\n", nombres[x], a2[i],  a2[i+1],  a2[i+2]);
		x++;
	}
	//cerramos el fichero
 	fclose(apetecibilidades);

    	
	//liberamos memoria de los arrays dinámicos
    	free(valoracion);
	free(compra);
	free(a1);
	free(a2);


///////////////////////////////////////////PARTE 2//////////////////////////////////////////////////


	//Leemos el fichero apetecibilidades para sacar los valores
	apetecibilidades = fopen("Apetecibilidad.txt", "r");

	//como anteriormente lo seguimos separando en lineas, palabras y numeros
    	while (fgets(linea, sizeof(linea), apetecibilidades) != NULL) {
        	palabra = strtok(linea, ";");	
		do{
            		num_apetecibilidades++;
            		apetecibilidad = (float*)realloc(apetecibilidad, num_apetecibilidades * sizeof(float));
                	apetecibilidad[num_apetecibilidades - 1] = atof(palabra);
        	}while (palabra = strtok(NULL, ";"));
    	}
	

	a1 = (float*)malloc(num_apetecibilidades/2 * sizeof(float));
	a2 = (float*)malloc((num_apetecibilidades - num_apetecibilidades/2) * sizeof(float));
	

	for(i = 0; i < num_apetecibilidades; i++){

		if(i < num_apetecibilidades/2){
			a1[i] = apetecibilidad[i];		
		}
		else{
			a2[i - num_apetecibilidades/2] = apetecibilidad[i];
		}
	}


	FILE* cosenos = fopen("Cosenos2a2.txt", "w");

	x = 0;

	//Una vez leido el fichero les mandamos a los esclavos su trabajo a realizar
	
	proceso = 1;
	cc1 = pvm_spawn("esclavo1", NULL, 1, "ubuntu-nodo1", 1, &tid);

	if (cc1 < 1) {
    		fprintf(stderr, "Error al spawnear el proceso Esclavo1\n");
    		pvm_exit();
    		return 1;
	}

	if (tid < 0) {
    		fprintf(stderr, "Error al obtener el ID de tarea del proceso Esclavo1\n");
    		pvm_exit();
    		return 1;
	}

	pvm_initsend(PvmDataDefault);
	pvm_pkint(&proceso, 1, 0);
	pvm_pkint(&num_apetecibilidades, 1, 0);
	pvm_pkfloat(a1, (num_apetecibilidades/2), 1);
	pvm_send(tid, tarea);
	
	float *ap1;
	ap1 = (float*)malloc(num_apetecibilidades/2 * sizeof(float));
    	pvm_recv(tid, -1);
    	pvm_upkfloat(ap1, (num_apetecibilidades/2), 1);
	
	for(i = 0; i < (num_apetecibilidades/2); i+=4){
		fprintf(cosenos, "%s;%f;%f;%f\n", nombres[x], ap1[i+1],  ap1[i+2],  ap1[i+3]);
		x++;
	}

	//Repetimos el proceso para esclavo 2
	cc1 = pvm_spawn("esclavo2", NULL, 1, "ubuntu-nodo2", 1, &tid);

	if (cc1 < 1) {
    		fprintf(stderr, "Error al spawnear el proceso Esclavo2\n");
    		pvm_exit();
    		return 1;
	}

	if (tid < 0) {
    		fprintf(stderr, "Error al obtener el ID de tarea del proceso Esclavo2\n");
    		pvm_exit();
    		return 1;
	}

	pvm_initsend(PvmDataDefault);
	pvm_pkint(&proceso, 1, 0);
	pvm_pkint(&num_apetecibilidades, 1, 0);
	pvm_pkfloat(a2, (num_apetecibilidades - num_apetecibilidades/2), 1);
	pvm_send(tid, tarea);
	
	float *ap2;
	ap2 = (float*)malloc((num_apetecibilidades - num_apetecibilidades/2) * sizeof(float));
    	pvm_recv(tid, -1);
    	pvm_upkfloat(ap2, (num_apetecibilidades - num_apetecibilidades/2), 1);
	
	for(i = 0; i < (num_apetecibilidades - num_apetecibilidades/2); i+=4){
		fprintf(cosenos, "%s;%f;%f;%f\n", nombres[x], ap2[i+1],  ap2[i+2],  ap2[i+3]);
		x++;
	}

	int A1 = 0;
	int A2 = 0;
	int A3 = 0;
	
	for(i = 1; i < num_apetecibilidades; i+=4){
		if(i > num_apetecibilidades/2){
			A1 = A1 + ap1[i];
			A2 = A2 + ap1[i+1];
			A3 = A3 + ap1[i+2];
		}else{
			A1 = A1 + ap1[i-num_apetecibilidades];
			A2 = A2 + ap1[i-num_apetecibilidades+1];
			A3 = A3 + ap1[i-num_apetecibilidades+2];
		}
	}/* En nuestro caso no funciona la cabecera Math.h, por ende no podemos acceder a la función sqrt y completar esta parte del código, de normal quedaría de la siguiente forma:
	fprintf(cosenos, "\n");
	fprintf(cosenos, " ;Articulo2;Articulo3\n");
	fprintf(cosenos, "Articulo 1;%f;%f;\n", (A1*A2)/(sqrt(A1)*sqrt(A2), (A1*A3)/(sqrt(A1)*sqrt(A3))));
	fprintf(cosenos, "Articulo 2; ;%f;\n",(A2*A3)/(sqrt(A2)*sqrt(A3)));
	*/
	//cerramos pvm para este código

	pvm_exit();	
    	return 0;
}

