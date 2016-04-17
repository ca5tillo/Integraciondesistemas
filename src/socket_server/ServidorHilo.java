/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_server;

import static integracion.Integracion.GetResponse;
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorHilo extends Thread {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;

    public ServidorHilo(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            this.desconnectar();
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconnectar() {
        try {
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String LEER_Socket() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException ex) {
            msg = "Error de lectura en el socket.";
            desconnectar();
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

    public void ESCRIBIR_Socket(String line) {
        try {
            dos.writeUTF(line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg = "";
        String tem;
        msg = LEER_Socket();
        
        System.out.println(msg);
        
        tem = GetResponse(msg);
        if(tem == null)
            msg = "Error el buscar la funcion en el servicio";
        else
            msg = tem;
        
        //deve procesar el mensaje
        ESCRIBIR_Socket(msg);
        
        desconnectar();
    }
}
