/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_ciente;

import java.io.*;
import java.net.Socket;
import java.util.stream.Stream;

class Conexion {

     Socket sk;
     DataOutputStream dos;
     DataInputStream dis;
     String ip;
     int puerto;
     String msg;
     String respuesta;
     String Lenguaje_del_servidor;
     private BufferedReader entradaC;
     private PrintStream salidaC;
    

    public Conexion(String ip, int puerto, String msg, String Lenguaje_del_servidor) {
        this.ip = ip;
        this.puerto = puerto;
        this.msg = msg;
        this.Lenguaje_del_servidor = Lenguaje_del_servidor;
    }

    public String run() {
        try {
            sk = new Socket(ip, puerto);
            
            if (Lenguaje_del_servidor.equals("c")){
                
                entradaC = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                salidaC =new PrintStream(sk.getOutputStream());
                respuesta ="";
                
                salidaC.println(msg);
                    Stream<String> ret = entradaC.lines();
                    try {
                        Object x[] = ret.toArray();
                        for (Object x1 : x) {
                            System.out.println(x1);
                            respuesta += x1;
                        }
                        //System.out.println();
                    } catch (Exception e) {
                        //System.out.println("Error stream" + e);
                        respuesta = null;
                    }

                    salidaC.close();
                    entradaC.close();
            }else{
                dos = new DataOutputStream(sk.getOutputStream());
                dis = new DataInputStream(sk.getInputStream());

                dos.writeUTF(msg);

                respuesta = dis.readUTF();


                dis.close();
                dos.close();
                
            }
            
            sk.close();
            
            return respuesta;
        } catch (IOException ex) {
            return null;
        }
    }
}
