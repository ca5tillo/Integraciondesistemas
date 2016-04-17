/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neommunications
 */
public class Up extends Thread {

    int puerto;
    boolean bandera = true;

    private ServerSocket ss;
    private Socket s;

    public Up(int puerto) {
        this.puerto = puerto;
        System.out.print("Inicializando servidor... ");
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(puerto);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (this.bandera) {
                //Socket socket;
                s = ss.accept();
                System.out.println("Nueva conexión entrante: " + s);
                ((ServidorHilo) new ServidorHilo(s, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            closeServer();
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void closeServer() {
        this.bandera = false;
        
        try {
            //s.close();
           // ss.close();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Throwable ex) {
            Logger.getLogger(Up.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
