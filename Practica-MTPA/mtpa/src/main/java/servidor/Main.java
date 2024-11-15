package servidor;

import java.io.IOException;

/**
 * Clase principal para ejecutar el servidor.
 */
public class Main {

    /**
     * Método principal que inicia el servidor.
     *
     * @param args Los argumentos de la línea de comandos.
     * @throws IOException Si ocurre un error de entrada/salida al iniciar el servidor.
     */
    public static void main(String[] args) throws IOException {
        Servidor srv = new Servidor();
        while (srv.getFinish() == 0) {
            // Ciclo infinito para mantener el servidor en ejecución
        }
    }
}
