/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_server;

import java.util.Scanner;
import socket_ciente.*;


public class Servidor {
    static private int puerto = 10575;
    public static void main(String args[]){
       Up u = new Up(puerto);
       u.start();
       System.out.println("1 para apagar el server");
       Scanner s = new Scanner(System.in);
       Integer i = (Integer)s.nextInt();
       if(i != null){
           if(i.equals(1)){
                u.closeServer();
                /*
                Se crea un cliente ya que al cerrar el servidor queda un soquet abierto 
                */
                new Cliente("127.0.0.1", 10575,"hola mundo", "j");
           }
       }
       
    }
}