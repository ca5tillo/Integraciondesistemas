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
public class Banco {
    Nodo DOM_Banco ;
    public Banco(){
        DOM_Banco= new Parser(File.getContentFile("./src/assets/banco.xml")).getDOM();
    }
    public String getAllB(Nodo n){
        String function = n.getNombre() + "Response";
        
        ArrayList<String> lst = new ArrayList<>();
        Nodo personas = DOM_Banco.$("persona", false);
        for (Nodo persona: personas.getHijos()){
            String id, categoria;
            String detalles="";
            id="{id- "+persona.attr("id", false);
            categoria= "_ cat- "+persona.attr("categoria", false);
            for(Nodo info: persona.getHijos()){
                detalles+="_ "+info.getNombre()+"- "+info.getContenido();
            }
            
            lst.add(id+categoria+detalles+"}");
        }
        String body = Servicios_funciones.bodylst(function,lst);
        return body;
    }
    public String getPersona(Nodo n){
        String function = n.getNombre() + "Response";
        Nodo persona = null;
        String id = "0111022203330440";
        ArrayList<String> lst = new ArrayList<>();
        try {
            id = n.$("ID", false).getHijo(0).getContenido();
            persona = DOM_Banco.$("id", id, false).getHijo(0);
            
            
        } catch (NumberFormatException e) {
            System.out.println("Servicio_test: suma: Error al parsear el int");
            return Servicios_funciones.bodyError();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Servicio_test: suma: Error en get de array");
            return Servicios_funciones.bodyError();
        }
        
        String pro="";
        for(Nodo nodo: persona.getHijos()){
            pro+="{"+nodo.getNombre()+"-"+nodo.getContenido()+"}";
        }
        lst.add(pro);
        
        String body = Servicios_funciones.bodylst(function,lst);
        return body;
    }
    public static void main(String[] args) {
        Banco f = new Banco(); 
        f.getPersona(f.DOM_Banco);
    }
}