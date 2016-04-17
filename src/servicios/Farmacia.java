/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import file.File;
import java.util.ArrayList;
import xml_modelos.Nodo;
import xml_parser.Parser;

/**
 *
 * @author admininistrador
 */
public class Farmacia {
    Nodo DOM_farmacia ;
    public Farmacia(){
        DOM_farmacia= new Parser(File.getContentFile("./src/assets/farmacia.xml")).getDOM();
    }
    public String getAll(Nodo n){
        String function = n.getNombre() + "Response";
        
        ArrayList<String> lst = new ArrayList<>();
        Nodo productos = DOM_farmacia.$("producto", false);
        for (Nodo producto: productos.getHijos()){
            String id, categoria;
            String detalles="";
            id="{id- "+producto.attr("id", false);
            categoria= "_ cat- "+producto.attr("categoria", false);
            for(Nodo info: producto.getHijos()){
                detalles+="_ "+info.getNombre()+"- "+info.getContenido();
            }
            
            lst.add(id+categoria+detalles+"}");
        }
        String body = Servicios_funciones.bodylst(function,lst);
        return body;
    }
    public String getProducto(Nodo n){
        String function = n.getNombre() + "Response";
        Nodo productos = null;
        String id = "01010100";
        ArrayList<String> lst = new ArrayList<>();
        try {
            id = n.$("ID", false).getHijo(0).getContenido();
            productos = DOM_farmacia.$("id", id, false).getHijo(0);
            
            
        } catch (NumberFormatException e) {
            System.out.println("Servicio_test: suma: Error al parsear el int");
            return Servicios_funciones.bodyError();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Servicio_test: suma: Error en get de array");
            return Servicios_funciones.bodyError();
        }
        
        String pro="";
        for(Nodo nodo: productos.getHijos()){
            pro+="{"+nodo.getNombre()+"-"+nodo.getContenido()+"}";
        }
        lst.add(pro);
        
        String body = Servicios_funciones.bodylst(function,lst);
        return body;
    }
    public static void main(String[] args) {
        Farmacia f = new Farmacia(); 
        f.getProducto(f.DOM_farmacia);
    }
}