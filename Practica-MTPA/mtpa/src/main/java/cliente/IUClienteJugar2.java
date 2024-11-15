package cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

/**
 * IUClienteJugar es una interfaz gráfica de usuario para el juego de Tres en raya.
 * Permite a los usuarios jugar contra otro usuario conectado al mismo servidor.
 */
public class IUClienteJugar2 extends javax.swing.JFrame {
    private String muevo;
    private int[][] tablero = new int[3][3];
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String usuarioReto;
    private String usuario;
    private int token = 2;
    
    /**
     * Constructor de la clase IUClienteJugar.
     * @param usuario Nombre de usuario del jugador actual.
     * @param usuarioReto Nombre de usuario del oponente.
     * @throws IOException Si ocurre un error de entrada/salida al conectarse al servidor.
     * @throws ClassNotFoundException Si no se encuentra la clase solicitada en la deserialización.
     * @throws InterruptedException Si ocurre una interrupción mientras se espera la conexión.
     */
    public IUClienteJugar2(String usuario,String usuarioReto) throws IOException, ClassNotFoundException, InterruptedException {
        //this.oIs = new ObjectInputStream(socket.getInputStream());
        //this.oOs = new ObjectOutputStream(socket.getOutputStream());
        this.usuarioReto = usuarioReto;
        this.usuario= usuario;
        initComponents();
        connectToServer();
        jLabel3.setText(usuario);
        
        //tablero = (int[][])oIs.readObject();
        // Inicia un hilo para escuchar mensajes del servidor
        new Thread(new Runnable() {
            public void run() {
                try {
                    listenForMessages();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(IUClienteCola.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IUClienteCola.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    /**
     * Método para escuchar los mensajes del servidor.
     * Maneja los tableros.
     * @throws ClassNotFoundException Si ocurre un error al cargar una clase que no existe.
     * @throws InterruptedException Si ocurre una interrupción mientras se espera la respuesta del servidor.
     */
    private void listenForMessages() throws ClassNotFoundException, InterruptedException {
    while (true) {
        muevo = "noMuevo";
        try {
            System.out.println("Escuchando...");
            char[] buffer = new char[1024];
            int length = in.read(buffer);
            String response = new String(buffer, 0, length).trim();
            while (response != null) {
                if (response.contains("mueve")) {
                    // Recibir tablero
                    ObjectInputStream oIs = new ObjectInputStream(socket.getInputStream());
                    tablero  = (int[][]) oIs.readObject();
                    actualizarTableroVisual();
                    habilitarBotones(true);
                    muevo = "mueve";
                    break;
                }
                if (response.contains("X")) {
                    JOptionPane.showMessageDialog(this, "Ganaste!!");
                    IUClienteCola cola = new IUClienteCola(usuario);
                    cola.setVisible(true);
                    this.setVisible(false);
                    response = null;
                    break;
                }
                if (response.contains("O")) {
                    JOptionPane.showMessageDialog(this, "Perdiste :(");
                    IUClienteCola cola = new IUClienteCola(usuario);
                    cola.setVisible(true);
                    this.setVisible(false);
                    response = null;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    
    
    private void actualizarTableroVisual() {
        JToggleButton[] botones = {
            jToggleButton1, jToggleButton2, jToggleButton3,
            jToggleButton5, jToggleButton6,
            jToggleButton7, jToggleButton8, jToggleButton9, jToggleButton10
        };

        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == 1) {
                    botones[index].setText("X");
                    botones[index].setEnabled(false);
                } else if (tablero[i][j] == 2) {
                    botones[index].setText("O");
                    botones[index].setEnabled(false);
                } else {
                    botones[index].setText("");
                    botones[index].setEnabled(true);
                }
                index++;
            }
        }
    }
    
    private void habilitarBotones(boolean habilitar) {
        JToggleButton[] botones = {
            jToggleButton1, jToggleButton2, jToggleButton3,
            jToggleButton5, jToggleButton6,
            jToggleButton7, jToggleButton8, jToggleButton9, jToggleButton10
        };

        for (JToggleButton boton : botones) {
            if (boton.getText().isEmpty()) { // Solo habilitar botones vacíos
                boton.setEnabled(habilitar);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton10 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Tú");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("0/0");

        jLabel3.setText("Otro");

        jLabel4.setText("Tu turno");

        jToggleButton1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton1.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton5.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        jToggleButton8.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton8.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton8ActionPerformed(evt);
            }
        });

        jToggleButton2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton2.setToolTipText("");
        jToggleButton2.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jToggleButton6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton6.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton6ActionPerformed(evt);
            }
        });

        jToggleButton9.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton9.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton9ActionPerformed(evt);
            }
        });

        jToggleButton3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton3.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jToggleButton7.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton7.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton7ActionPerformed(evt);
            }
        });

        jToggleButton10.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jToggleButton10.setPreferredSize(new java.awt.Dimension(71, 71));
        jToggleButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton10ActionPerformed(evt);
            }
        });

        jButton1.setText("Rendirse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(155, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(64, 64, 64))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addContainerGap(14, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addContainerGap(264, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jToggleButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jButton1)
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[0][0] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[0][1] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void jToggleButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton8ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[0][1] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton8ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[1][0] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
        if ("mueve".equals(muevo)) {
                tablero[1][1] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
                muevo = "heMovido";
                habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton6ActionPerformed

    private void jToggleButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton9ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[1][2] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton9ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[2][0] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton7ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[2][1] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton7ActionPerformed

    private void jToggleButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton10ActionPerformed
        if ("mueve".equals(muevo)) {
            tablero[2][2] = token;  
            try {
                enviarMovimiento(0, 0);
            } catch (IOException ex) {
                Logger.getLogger(IUClienteJugar2.class.getName()).log(Level.SEVERE, null, ex);
            }
            muevo = "heMovido";
            habilitarBotones(false);
        }
    }//GEN-LAST:event_jToggleButton10ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            JOptionPane.showMessageDialog(this, "Te rindes...");
            IUClienteCola cola = new IUClienteCola(usuarioReto);
            cola.setVisible(true);
            this.setVisible(false);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(IUClienteJugar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    //quitar fila y columna con nueva implementación
    private void enviarMovimiento(int fila, int columna) throws IOException {
        ObjectOutputStream oOs = new ObjectOutputStream(socket.getOutputStream());
        oOs.writeObject(tablero);
    }
    
    /**
     * Conecta al cliente con el servidor.
     */
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 7894);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    // End of variables declaration//GEN-END:variables
}
