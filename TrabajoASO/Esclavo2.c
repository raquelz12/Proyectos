#include <stdio.h>
#include <stdlib.h>
#include <pvm3.h>

int main() {
	//iniciamos las variables
    	int mytid, ptid, tarea, num_valoraciones, num_compras, num_apetecibilidades;
    	int *v2;
	int *c2;
	float *a2;
	float *ap2;
    	int i;
	int proceso;
	float tmp;

    	mytid = pvm_mytid();
    	ptid = pvm_parent();
	pvm_recv(-1, 1); 
	pvm_upkint(&proceso, 1, 0);
	
	if(proceso == 0){ 
		//recibimos los datos enviados por maestro, en este caso 2 arrays con su tamaño dado por 2 ints que también se reciben
		//una vez recibidos tenemos que asignarles memoria dentro de esclavo2

		pvm_upkint(&num_valoraciones, 1, 0);
		v2 = (int*)malloc((num_valoraciones - num_valoraciones/2) * sizeof(int));
		pvm_upkint(v2, (num_valoraciones - num_valoraciones/2), 1);
		pvm_upkint(&num_compras, 1, 0);
		c2 = (int*)malloc((num_valoraciones - num_valoraciones/2) * sizeof(int));
		pvm_upkint(c2, (num_valoraciones - num_valoraciones/2), 1);
		ptid = pvm_parent(); 
	
		//reservamos memoria del array que vamos a enviar con el tamaño correspondiente
		a2 = (float*)malloc((num_valoraciones - num_valoraciones/2) * sizeof(float));
	
		//realizamos los cálculos
		for (i = 0; i < num_valoraciones - num_valoraciones/2; i++) {
    			a2[i] = (0.7*v2[i]) + (0.3*c2[i]);
		}

		//enviamos a maestro los resultados
		pvm_initsend(PvmDataDefault);
		pvm_pkfloat(a2, (num_valoraciones - num_valoraciones/2), 1);
		pvm_send(ptid, 1);
		//liberamos memoria de los arrays dinámicos
		free(v2);
		free(c2);

	}else{
		if(proceso == 1){
			pvm_upkint(&num_apetecibilidades, 1, 0);
			a2 = (float*)malloc((num_apetecibilidades - num_apetecibilidades/2)* sizeof(float));
			pvm_upkfloat(a2, (num_apetecibilidades - num_apetecibilidades/2), 1);
			ptid = pvm_parent(); 
			ap2 = (float*)malloc((num_apetecibilidades - num_apetecibilidades/2) * sizeof(float));
			for (i = 0; i < (num_apetecibilidades - num_apetecibilidades/2); i++) {
    				ap2[i] = (a2[i] * a2[i]);
			}

			pvm_initsend(PvmDataDefault);
			pvm_pkfloat(ap2, (num_apetecibilidades - num_apetecibilidades/2), 1);
			pvm_send(ptid, 1);
			//liberamos memoria
			free(a2);
			free(ap2);
		}
	}
	pvm_exit();
    	return 0;
}
