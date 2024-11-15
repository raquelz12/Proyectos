package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Servidor que maneja las conexiones y la lógica del servidor.
 */
public class Servidor implements Runnable {
    
    private static final int PORT_SERVICE = 7894;
    private ServerSocket servidor;
    private Thread t;
    private OutputStream output;
    private InputStream input;
    private jugador[] jugadores = new jugador[100];
    private ArrayList<hiloPartida> partidas = new ArrayList<>();
    private int playerCount = 0;
    private int finish = 0;
    //NO IMPLEMENTAR ArrayList<hiloRegistro> hilosRegister = new ArrayList<>();
    
    /**
     * Constructor de la clase Servidor.
     *
     * @throws IOException Si ocurre un error de entrada/salida al crear el ServerSocket.
     */
    public Servidor() throws IOException {
        servidor = new ServerSocket(PORT_SERVICE);
        //hilosRegister = new ArrayList<>();
        t = new Thread(this);
        t.start();
        initserver();  
    }
    
    /**
     * Método que ejecuta el hilo del servidor.
     */
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);

            while (finish == 0) {
                System.out.println("\n\nMENU SERVIDOR");
                System.out.println("-------------");
                System.out.println("1. Lista de Usuarios Conectados");
                System.out.println("2. Parar el servidor");
                System.out.println("3. Informacion Partidas");
                System.out.print("Seleccione una opcion: ");

                int opcion = scanner.nextInt();

                switch (opcion)
                {
                    case 1:
                        int contador = 1;
                        for (jugador j : jugadores)
                        {
                            if (j != null) 
                            {
                                System.out.println(contador + ". " + j.getUsername());
                                contador++;
                            }
                        }
                        if(playerCount == 0)
                        {
                            System.out.println("No hay jugadores conectados");
                        }
                        break;
                    case 2:
                        finish = 1;
                        System.out.println("Parando servidor...\n\n");
                        servidor.close();
                        break;
                    case 3:
                        contador = 1;
                        for(int i = 0; i < partidas.size(); i++)
                        {
                            System.out.println(contador + ". " + partidas.get(i).toString());
                            contador++;
                        }
                        if(contador == 1)
                        {
                            System.out.println("No se han jugado partidas todavia");
                        }
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione una opción del menú.");
                        break;
                }
            }  
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }

    
    /**
     * Obtiene el estado del servidor.
     *
     * @return El valor de finish.
     */
    public int getFinish()
    {
        return finish;
    }
    
    /**
     * Inicializa el servidor y espera conexiones de clientes.
     */
    public void initserver() {
        try {
            System.out.println("Servidor encendido");
            while (true) {
                Socket sck = servidor.accept();
                new Thread(() -> handleClient(sck)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Maneja la conexión de un cliente.
     *
     * @param sck El socket del cliente.
     */
    public void handleClient(Socket sck) {
        try {
            // Sistema de mensajes mediante el socket
            String mensajeEntrante = null;
            byte[] buffer = new byte[1024];
            input = sck.getInputStream();
            output = sck.getOutputStream();
            
            input.read(buffer);
            mensajeEntrante = new String(buffer).trim();
            // Asignamos el
            switch (mensajeEntrante)
            {
                case "register":
                    hiloRegistro hR = new hiloRegistro(sck);
                    hR.run();
                    break;
                    
                case "login":
                    hiloLogin hL = new hiloLogin(sck);
                    hL.run();
                    break;
                    
                case "jugar":
                    //Cogemos como mensaje entrante el username del usuario
                    buffer = new byte[1024];
                    input.read(buffer);
                    mensajeEntrante = new String(buffer).trim();
                    //Creamos el objeto jugador
                    jugador retador = new jugador(sck, mensajeEntrante);
                    jugadores[playerCount] = (retador);
                    playerCount++;
                    StringBuilder arrayJugadoresString = new StringBuilder();
                    for (int i = 0; i < jugadores.length; i++) 
                    {
                        jugador tmp;
                        if (jugadores[i] != null) {
                            tmp = jugadores[i];
                            if (arrayJugadoresString.length() > 0) {
                                arrayJugadoresString.append(",");
                            }
                            arrayJugadoresString.append(tmp.getUsername());
                        }
                    }
                    output.write(arrayJugadoresString.toString().getBytes());
                    //llegará el username del rival a retar
                    boolean accepted = false;
                    while(!accepted)
                    {
                        input.read(buffer);
                        mensajeEntrante = new String(buffer).trim();
                        if(mensajeEntrante.contains("reto aceptado"))
                        {
                            String[] param = mensajeEntrante.split(";");
                            jugador retado = buscarJugador(param[1]);
                            retador = buscarJugador(param[2]);
                            output.write(mensajeEntrante.getBytes());
                            partidas.add(new hiloPartida(retador, retado));
                            accepted = true;
                        }
                        if(mensajeEntrante.contains("reto;"))
                        {
                            String[] param = mensajeEntrante.split(";");
                            jugador retado = buscarJugador(param[1]);
                            OutputStream osR = retado.getOs();
                            osR.write(mensajeEntrante.getBytes());
                        }
                        if(mensajeEntrante.contains("salir"))
                        {
                            //cogemos el usuario a quitar de la lista
                            buffer = new byte[1024];
                            input.read(buffer);
                            mensajeEntrante = new String(buffer).trim();
                            //Eliminamos el objeto
                            for(int i = 0; i<=jugadores.length; i++)
                            {
                                jugador tmp = jugadores[i];
                                if(tmp.getUsername().equals(mensajeEntrante))
                                {
                                    eliminarJugador(jugadores, i);
                                }
                            }
                         }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Busca un jugador por su nombre de usuario.
     *
     * @param username El nombre de usuario del jugador.
     * @return El jugador encontrado.
     * @throws IOException Si el jugador no se encuentra.
     */
    public jugador buscarJugador(String username) throws IOException
    {
        for(int i = 0; i < playerCount; i++)
        {
            //System.out.println(jugadores[i].getUsername() + "," + username);
            if(username.equals(jugadores[i].getUsername()))
            {
                return jugadores[i];
            }
        }
        throw new IOException();
    }
    
    /**
     * Elimina un jugador de la lista.
     *
     * @param jugadores La lista de jugadores.
     * @param index El índice del jugador a eliminar.
     */

    public static void eliminarJugador(jugador[] jugadores, int index) 
    {
        if (index < 0 || index >= jugadores.length)
        {
            return;
        }
        
        for (int i = index; i < jugadores.length - 1; i++)
        {
            jugadores[i] = jugadores[i + 1];
        }
    }

    /**
     * Lee un mensaje del socket del cliente.
     *
     * @param cliente El socket del cliente.
     * @return El mensaje leído.
     */
    private byte[] readSocket(Socket cliente) 
    {
        byte[] socketMessage = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(cliente.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8];
            int nb = 0;
            do {
                nb = bis.read(buffer);
                baos.write(buffer, 0, nb);
            } while (bis.available() > 0);
            socketMessage = baos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return socketMessage;
    }
}

/**
 * Representa un hilo para el registro de usuarios
 */

class hiloRegistro extends Thread {
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    
    /*
    * Crea el fichero necesario o le abre si está creado
    */
    
    public static void crearFichero() {
        File carpeta = new File("./TresEnRaya");
        carpeta.mkdirs();

        File archivo = new File("./TresEnRaya/registro.txt");
        if (!archivo.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                bw.write("A partir de aquí se escribirán todos los usuarios registrados, siguiendo el siguiente formato. login;password\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Busca un usuario en el archivo de registro.
     * @param username El nombre de usuario a buscar.
     * @return El registro del usuario si se encuentra, de lo contrario null.
     * @throws FileNotFoundException Si no se encuentra el archivo.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public String buscarUsuario(String username)throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("./TresEnRaya/registro.txt"));
        String line;
        while ((line = br.readLine()) != null) 
        {
            if(line.contains(username))
            {
                if (line.split(";")[0].equals(username)) 
                {
                    return line;
                }
            }
        }
        return null;
    }
    
    /**
     * Registra un nuevo usuario escribiendo su nombre de usuario y contraseña en el archivo.
     * @param username El nombre de usuario a registrar.
     * @param password La contraseña del usuario.
     * @throws FileNotFoundException Si no se encuentra el archivo.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public void registrarUsuario(String username, String password) throws FileNotFoundException, IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter("./TresEnRaya/registro.txt", true));
        bw.write(username + ";" + password);
        bw.newLine();
        bw.close();
    }
    
    /**
     * Construye un nuevo hilo hiloRegistro.
     * @param _socket El socket asociado con este hilo.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public hiloRegistro(Socket _socket) throws IOException 
    {
        socket = _socket;
        os = socket.getOutputStream();
        is = socket.getInputStream();
        crearFichero();
    }
    
    /**
     * La lógica principal del hilo hiloRegistro.
     * Escucha continuamente las solicitudes de registro, las procesa y envía respuestas.
     */
    @Override
    public void run()
    {
        try 
        {
            while(true)
            {
                byte[] buffer = new byte[1024];
                //Recibimos del socket el usuario y contraseña
                is.read(buffer);
                String login = new String(buffer).trim();
                byte[]buffer2 = new byte[1024];
                is.read(buffer2);
                String password = new String(buffer2).trim();
                //Si encontramos un usuario ya registrado con ese nombre le denegamos la petición de registro
                if(buscarUsuario(login) == null)
                {
                    registrarUsuario(login, password);
                    os.write("okR".getBytes(), 0, "okR".getBytes().length);
                    break;
                }
                else
                {
                    os.write("denied".getBytes(), 0, "denied".getBytes().length);
                }
            }
        } 
        catch (IOException ex) 
        {
            
        }
    }
}

/**
 * Representa un hilo responsable de manejar el inicio de sesión de usuarios.
 */
class hiloLogin extends Thread {
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    
    /**
     * Busca un usuario en el archivo de registro.
     * @param username El nombre de usuario a buscar.
     * @return El registro del usuario si se encuentra, de lo contrario null.
     * @throws FileNotFoundException Si no se encuentra el archivo.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public String buscarUsuario(String username) 
            throws FileNotFoundException, IOException 
    {
        BufferedReader br = new BufferedReader(new FileReader("./TresEnRaya/registro.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.split(";")[0].equals(username)) {
                br.close();
                return line;
            }
        }
        br.close();
        return null;
    }

    
    /**
     * Construye un nuevo hilo hiloLogin.
     * @param _socket El socket asociado con este hilo.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public hiloLogin(Socket _socket) throws IOException 
    {
        socket = _socket;
        os = socket.getOutputStream();
        is = socket.getInputStream();
    }
    
    /**
     * La lógica principal del hilo hiloLogin.
     * Escucha continuamente las solicitudes de inicio de sesión, las procesa y envía respuestas.
     */
    @Override
    public void run()
    {
        try {
            while(true)
            {
                byte[] buffer = new byte[1024];
                //Recibimos del socket el usuario y contraseña
                is.read(buffer);
                String login = new String(buffer).trim();
                byte[]buffer2 = new byte[1024];
                is.read(buffer2);
                String password = new String(buffer2).trim();
                //Si encontramos que no hay un usuario registrado con ese login denegamos la entrada
                String usuario = buscarUsuario(login);
                if (usuario != null) {
                    String[] parts = usuario.split(";");
                    if (parts[1].equals(password)) {
                        os.write("okL".getBytes());
                    } else {
                        os.write("denied".getBytes());
                    }
                } else {
                    os.write("denied".getBytes());
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(hiloRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/**
 * Representa un jugador en el juego.
 */
class jugador{
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private String username;
    
    /**
     * Construye un nuevo jugador.
     * @param _socket El socket asociado con este jugador.
     * @param _username El nombre de usuario de este jugador.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public jugador(Socket _socket, String _username) throws IOException 
    {
        socket = _socket;
        username = _username;
        os = socket.getOutputStream();
        is = socket.getInputStream();
    }

    /**
     * Devuelve el socket asociado con este jugador.
     * @return El socket.
     */
    public Socket getSocket() {
        return socket;
    }
    
    /**
     * Devuelve el flujo de salida asociado con este jugador.
     * @return El flujo de salida.
     */
    public OutputStream getOs() {
        return os;
    }

    /**
     * Devuelve el flujo de salida de objetos asociado con este jugador.
     * @return El flujo de salida de objetos.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public ObjectOutputStream getOOs() throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }
    
    /**
     * Devuelve el flujo de entrada de objetos asociado con este jugador.
     * @return El flujo de entrada de objetos.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public ObjectInputStream getOIs() throws IOException {
        return new ObjectInputStream(socket.getInputStream());
    }
    
    /**
     * Devuelve el flujo de entrada asociado con este jugador.
     * @return El flujo de entrada.
     */
    public InputStream getIs() {
        return is;
    }

    /**
     * Devuelve el nombre de usuario de este jugador.
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }
}

/**
 * Representa un hilo responsable de manejar una partida entre dos jugadores.
 */
class hiloPartida extends Thread {

    private jugador p1;
    private jugador p2;
    private int[][] tablero = new int[3][3];
    private String estado;
    
    /**
     * Construye un nuevo hilo hiloPartida.
     * @param _p1 El primer jugador.
     * @param _p2 El segundo jugador.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public hiloPartida(jugador _p1, jugador _p2) throws IOException 
    {
        p1 = _p1;
        p2 = _p2;
        this.run();
    }
    
    /**
     * La lógica principal del hilo hiloPartida.
     * Alterna continuamente los turnos entre los dos jugadores y verifica si la partida ha terminado.
     */
    @Override
    public void run()
    {
        inicializarTablero();
        while(true)
        {
            try {
                turno(p1);
                if(comprobador() == true)
                {
                    registrarPartida(this);
                    return;
                }
                turno(p2);
                if(comprobador() == true)
                {
                    registrarPartida(this);
                    return;
                }
            } catch (IOException ex) {
                Logger.getLogger(hiloPartida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(hiloPartida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Verifica si hay un ganador o si la partida es un empate.
     * @return True si la partida ha terminado, de lo contrario false.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public boolean comprobador() throws IOException
    {
        if(comprobador(tablero)!= "no")
        {
            switch (comprobador(tablero)){
                case "x":{
                    estado = "Ganó x | " + p1.getUsername();
                    p1.getOs().write("x".getBytes());
                    p2.getOs().write("x".getBytes());
                }
                case "o":{
                    estado = "Ganó o | " + p2.getUsername();
                    p1.getOs().write("o".getBytes());
                    p2.getOs().write("o".getBytes());
                }
            }
            p1.getOOs().writeObject(tablero);
            p2.getOOs().writeObject(tablero);
            return true;
        }
        return false;
    }
    
    /**
     * Gestiona el turno de un jugador.
     * @param player El jugador que está tomando su turno.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ClassNotFoundException Si la clase del objeto serializado no se puede encontrar.
     */
    public void turno (jugador player) throws IOException, ClassNotFoundException
    {
        try
        {
            player.getOs().write("mueve".getBytes());
            player.getOOs().writeObject(tablero);
            tablero = (int[][])player.getOIs().readObject();
        }
        catch(Exception e)
        {
            p1.getSocket().close();
            p2.getSocket().close();
        }
    }
    
    /**
     * Inicializa el tablero de juego con ceros.
     */
    public void inicializarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = 0;
            }
        }
    }
    
    /**
     * Verifica el estado del tablero para determinar si hay un ganador o si el juego continúa.
     * @param tablero El estado actual del tablero de juego.
     * @return "x" si el jugador X ha ganado, "o" si el jugador O ha ganado, "no" si no hay ganador.
     */
    public String comprobador(int[][] tablero)
    {
        
        for(int i = 0; i<3; i++)
        {
            //horizontal
            if(tablero[i][0] == tablero[i][1] && tablero[i][0] == tablero[i][2] && tablero[i][0] != 0)
            {
                if(tablero[i][0] == 1)
                {
                    return "x";
                }
                else{
                    return "o";
                }
            }
            //vertical
            if(tablero[0][i] == tablero[1][i] && tablero[0][i] == tablero[2][i] && tablero[0][i] != 0)
            {
                if(tablero[0][i] == 1)
                {
                    return "x";
                }
                else{
                    return "o";
                }
            }
            //diagonal
            if(tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2] && tablero[2][2] != 0)
            {
                if(tablero[0][0] == 1)
                {
                    return "x";
                }
                else{
                    return "o";
                }
            }
            //diagonal opuesta
            if(tablero[0][2] == tablero[1][1] && tablero[0][2] == tablero[2][0] && tablero[0][2] != 0)
            {
                if(tablero[0][2] == 1)
                {
                    return "x";
                }
                else{
                    return "o";
                }
            }
        }
        return "no";
    }
    
    /**
    * Devuelve una representación en forma de cadena de la partida,
    * incluyendo los nombres de usuario de los dos jugadores y el estado de la partida.
    *
    * @return una cadena con la información de la partida.
    */
    public String toString()
    {
        return "Jugador1:" + p1.getUsername() + "Jugador2:" +  p2.getUsername() + "Estado Partida:" +  estado;
    }
    
    /**
    * Registra la información de una partida en un archivo de texto.
    *
    * @param hp el objeto hiloPartida que contiene la información de la partida.
    */
    public void registrarPartida(hiloPartida hp) 
    {
        String filePath = "./TresEnRaya/partida.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) 
        {
            writer.write(hp.toString());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
    * Registra el resultado de una partida para un usuario en un archivo de texto.
    * Actualiza el número de victorias o derrotas del usuario según el resultado.
    *
    * @param username el nombre de usuario del jugador.
    * @param resultado el resultado de la partida ('V' para victoria, 'L' para derrota).
    */
    public void registrarResultado(String username, char resultado) {
        String filePath = "./TresEnRaya/registro.txt";
        ArrayList<String> lines = new ArrayList<>();

        // Leer el archivo y almacenar su contenido en una lista
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(username)) {
                    int victorias = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
                    int derrotas = parts.length > 3 ? Integer.parseInt(parts[3]) : 0;
                    if (resultado == 'V') {
                        victorias++;
                    } else if (resultado == 'L') {
                        derrotas++;
                    }
                    lines.add(parts[0] + ";" + parts[1] + ";" + victorias + ";" + derrotas);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Escribir el contenido actualizado de nuevo en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}