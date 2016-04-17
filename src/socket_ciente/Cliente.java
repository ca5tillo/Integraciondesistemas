/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_ciente;




public class Cliente {
    String respuesta;
    public Cliente (String ip, int puerto, String msg, String Lenguaje_del_servidor){
       respuesta = new Conexion( ip,  puerto,  msg,  Lenguaje_del_servidor).run();
    }
    public String get(){
        return respuesta;
    }
    public static void main(String[] args) {
       String p = new Cliente("127.0.0.1", 10575,"hola mundo", "c").respuesta;
        System.out.println(p);
    }
}
