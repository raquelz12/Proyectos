#include <stdio.h>
#include <stdlib.h>
#include <pvm3.h>
#include <math.h>

int main() {
	//iniciamos las variables
    	int mytid, ptid, tarea, num_valoraciones, num_compras, num_apetecibilidades;
    	int *v1;
	int *c1;
	float *a1;
	float *ap1;
    	int i;
	int proceso;
	float tmp;

    	mytid = pvm_mytid();
    	ptid = pvm_parent();
 	pvm_recv(-1, 1); 
	pvm_upkint(&proceso, 1, 0);

	if(proceso == 0){
		//recibimos los datos enviados por maestro, en este caso 2 arrays con su tamaño dado por 2 ints que también se reciben
		//una vez recibidos tenemos que asignarles memoria dentro de esclavo
		pvm_upkint(&num_valoraciones, 1, 0);
		v1 = (int*)malloc(num_valoraciones/2 * sizeof(int));
		pvm_upkint(v1, num_valoraciones/2, 1);
		pvm_upkint(&num_compras, 1, 0);
		c1 = (int*)malloc(num_compras/2 * sizeof(int));
		pvm_upkint(c1, num_compras/2, 1);
		ptid = pvm_parent(); 
	
		//reservamos memoria del array que vamos a enviar con el tamaño correspondiente
		a1 = (float*)malloc(num_valoraciones/2 * sizeof(float));

		//realizamos los cálculos
		for (i = 0; i < num_valoraciones/2; i++) {
    			a1[i] = (0.7*v1[i]) + (0.3*c1[i]);
		}

		//enviamos a maestro los resultados
		pvm_initsend(PvmDataDefault);
		pvm_pkfloat(a1, num_valoraciones/2, 1);
		pvm_send(ptid, 1);
		//liberamos memoria de los arrays dinámicos
		free(v1);
		free(c1);

	}
	else{
		if(proceso == 1){
			pvm_upkint(&num_apetecibilidades, 1, 0);
			a1 = (float*)malloc(num_apetecibilidades/2 * sizeof(float));
			pvm_upkfloat(a1, num_apetecibilidades/2, 1);
			ptid = pvm_parent(); 
			ap1 = (float*)malloc((num_apetecibilidades/2) * sizeof(float));
			for (i = 0; i < (num_apetecibilidades/2); i++) {
    				ap1[i] = (a1[i] * a1[i]);
			}

			pvm_initsend(PvmDataDefault);
			pvm_pkfloat(ap1, (num_valoraciones/2), 1);
			pvm_send(ptid, 1);
			//liberamos memoria
			free(a1);
			free(ap1);
		}
	}
	pvm_exit();
    	return 0;
}

