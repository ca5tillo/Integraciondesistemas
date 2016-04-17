/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_modelos;

import java.util.ArrayList;

/**
 *
 * @author administrador
 */
public class Atributos {
    private String prefijo;
    private String nombre;
    private ArrayList<Valoratributo> valores = new ArrayList<>();
    private String s_valor;

    public Atributos() {
        
    }

    public String getS_valor() {
        return s_valor;
    }

    public void setS_valor(String s_valor) {
        this.s_valor = s_valor;
    }

    public Atributos(String prefijo, String nombre) {
        this.prefijo = prefijo;
        this.nombre = nombre;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Valoratributo> getValores() {
        return valores;
    }

    public void setValores(ArrayList<Valoratributo> valores) {
        this.valores = (ArrayList<Valoratributo>) valores.clone();
    }
    
    public void setValor(Valoratributo v){
        this.valores.add(v);
    }
    public Valoratributo getValor(int indice){
        return this.valores.get(indice);
    }
    public int getNumvalores(){
        return this.valores.size();
    }

    @Override
    public String toString() {
        return "Atributos{" + "prefijo=" + prefijo + ", nombre=" + nombre + ", valores=" + valores + ", s_valor=" + s_valor + '}';
    }

    
    
    
    
    
}
