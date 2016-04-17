/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.ArrayList;
import xml_modelos.Nodo;

/**
 *
 * @author admininistrador
 */
public class Calculadora {
    @SuppressWarnings("UnusedAssignment")
    public static String suma(Nodo n) {
        
        String function = n.getNombre() + "Response";
        double a = 0;
        double b = 0;
        double Resultado;
        try {
            a = Double.parseDouble(n.$("A", false).getHijo(0).getContenido());
            b = Double.parseDouble(n.$("B", false).getHijo(0).getContenido());
        } catch (NumberFormatException e) {
            System.out.println("Servicio_test: suma: Error al parsear el double");
            return Servicios_funciones.bodyError();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Servicio_test: suma: Error en get de array");
            return Servicios_funciones.bodyError();
        }
        Resultado = a+b;
        
        // System.out.println(a);
        String body = Servicios_funciones.body(function,""+Resultado);
        return body;
    }
    public static String resta(Nodo n) {
        
        String function = n.getNombre() + "Response";
        double a = 0;
        double b = 0;
        double Resultado;
        try {
            a = Double.parseDouble(n.$("A", false).getHijo(0).getContenido());
            b = Double.parseDouble(n.$("B", false).getHijo(0).getContenido());
        } catch (NumberFormatException e) {
            System.out.println("Servicio_test: suma: Error al parsear el int");
            return Servicios_funciones.bodyError();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Servicio_test: suma: Error en get de array");
            return Servicios_funciones.bodyError();
        }
        Resultado = a-b;
        
        // System.out.println(a);
        String body = Servicios_funciones.body(function,""+Resultado);
        return body;
    }
    public static String multiplicacion(Nodo n) {
        
        String function = n.getNombre() + "Response";
        double a = 0;
        double b = 0;
        double Resultado;
        try {
            a = Double.parseDouble(n.$("A", false).getHijo(0).getContenido());
            b = Double.parseDouble(n.$("B", false).getHijo(0).getContenido());
        } catch (NumberFormatException e) {
            System.out.println("Servicio_test: suma: Error al parsear el int");
            return Servicios_funciones.bodyError();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Servicio_test: suma: Error en get de array");
            return Servicios_funciones.bodyError();
        }
        Resultado = a*b;
        
        // System.out.println(a);
        String body = Servicios_funciones.body(function,""+Resultado);
        return body;
    }
    public static String division(Nodo n) {
        
        String function = n.getNombre() + "Response";
        double a = 0;
        double b = 0;
        double Resultado;
        try {
            a = Double.parseDouble(n.$("A", false).getHijo(0).getContenido());
            b = Double.parseDouble(n.$("B", false).getHijo(0).getContenido());
        } catch (NumberFormatException e) {
            System.out.println("Servicio_test: suma: Error al parsear el int");
            return Servicios_funciones.bodyError();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Servicio_test: suma: Error en get de array");
            return Servicios_funciones.bodyError();
        }
        Resultado = a/b;
        
        // System.out.println(a);
        String body = Servicios_funciones.body(function,""+Resultado);
        return body;
    }
    public static String lista0a10(Nodo n){
        ArrayList<String> lst = new ArrayList<>();
        String function = n.getNombre() + "Response";
        for (int i = 0; i <= 10; i++) {
            lst.add(""+i);
        }
        String body = Servicios_funciones.bodylst(function,lst);
        return body;
    }
}
