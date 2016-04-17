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
public class Nodo {
    private boolean etiquetavacia;
    private int id;
    private int nivel;
    private String prefijo;
    private String nombre;
    private String contenido;
    private ArrayList<Atributos> atributos = new ArrayList<>();
    private ArrayList<Nodo> hijos = new ArrayList<>();

    public Nodo(int id, int nivel,String prefijo, String nombre, String contenido) {
        this.etiquetavacia = false;
        this.id = id;
        this.nivel = nivel;
        this.prefijo = prefijo;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public boolean isEtiquetavacia() {
        return etiquetavacia;
    }

    public void setEtiquetavacia(boolean etiquetavacia) {
        this.etiquetavacia = etiquetavacia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public ArrayList<Atributos> getAtributos() {
        return atributos;
    }

    public void setAtributos(ArrayList<Atributos> atributos) {
        this.atributos = (ArrayList<Atributos>) atributos.clone();
    }

    public ArrayList<Nodo> getHijos() {
        return hijos;
    }
    public Nodo getHijo(int indice){
        if (this.hijos.size() >= indice)
            return this.hijos.get(indice);
        return null;
    }
    public int getNumHijos(){
        return this.hijos.size();
    }
    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = (ArrayList<Nodo>) hijos.clone();
    }
    public void setHijo(Nodo objs){
        this.hijos.add(objs);
    }
    
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    /* ====================================================================== */
                        /* Funciones de busqueda */
    /* ====================================================================== */
    /**
     * regresa valores de atributo en un String 
     * @param attr nombre del atributo
     * @param u_u valor true = si importa el prefijo del nombre del atributo
     * @return retorna en una cadena todos los valores del atributo separados por espacio
     * si el atributo no se encuentra retorna NULL
     */
    public String attr(String attr, Boolean u_u){
        String s = "";
        String pre;
        String nom;
        int bandera = 0;

        if (attr.matches(".*:.*")) {
            pre = attr.split(":")[0];
            nom = attr.split(":")[1];
        }else{
            nom = attr;
            pre = "";
        }
        
        for(Atributos a : atributos){
            if(u_u){// si importan los prefijos
                if(a.getNombre().equals(nom) && a.getPrefijo().equals(pre)){
                    if(a.getNumvalores() > 0){
                        for(Valoratributo valoratributo: a.getValores()){
                           
                            if(valoratributo.getPrefijo() == ""){
                                if(0 == bandera){
                                    s = valoratributo.getValor();
                                    bandera = 1;
                                }else
                                    s += " "+valoratributo.getValor();
                            }else{
                                if(0 == bandera){
                                    s = valoratributo.getPrefijo()+":"+valoratributo.getValor();
                                    bandera = 1;
                                }else
                                    s += " "+valoratributo.getPrefijo()+":"+valoratributo.getValor(); 
                            }
                            
                        }
                    }
                }
            }else{
                if(a.getNombre().equals(nom)){
                    if(a.getNumvalores() > 0){
                        for(Valoratributo valoratributo: a.getValores()){
                           
                            if(valoratributo.getPrefijo() == ""){
                                if(0 == bandera){
                                    s = valoratributo.getValor();
                                    bandera = 1;
                                }else
                                    s += " "+valoratributo.getValor();
                            }else{
                                if(0 == bandera){
                                    s = valoratributo.getPrefijo()+":"+valoratributo.getValor();
                                    bandera = 1;
                                }else
                                    s += " "+valoratributo.getPrefijo()+":"+valoratributo.getValor(); 
                            }
                            
                        }
                    }
                }
            }
            
        }
        if(s != "")return s;
        return null;
    }
    /**
     * regresa valor de atributo en un String  toda la cadena sin tratar tal cual estaba en el xml
     * @param attr nombre del atributo
     * @param u_u valor true = si importa el prefijo del nombre del atributo
     * @return retorna en una cadena todos los valores del atributo separados por espacio
     * si el atributo no se encuentra retorna NULL
     */
    public String attrAll(String attr, Boolean u_u){
        String s = "";
        String pre;
        String nom;
        int bandera = 0;

        if (attr.matches(".*:.*")) {
            pre = attr.split(":")[0];
            nom = attr.split(":")[1];
        }else{
            nom = attr;
            pre = "";
        }
        
        for(Atributos a : atributos){
            if(u_u){// si importan los prefijos
                if(a.getNombre().equals(nom) && a.getPrefijo().equals(pre)){
                    s = a.getS_valor();
                    
                }
            }else{
                if(a.getNombre().equals(nom)){
                    s = a.getS_valor();
                }
            }
            
        }
        if(s != "")return s;
        return null;
    }
    /**
     * Busca un atributo y retorna su primer valor 
     * @param nombreAtributo El atributo a desea buscar
     * @return El primer elemento de los valores del atributo buscado.
     *         Si no encuentra el atributo retonara NULL
     */
    
    
    /**
     * Busqueda de nodos por etiqueta
     *
     * @param s Es el nombre de la etiqueta a buscar
     * @return Un arreglo de los nodos que coinciden con el nombre de etiqueta
     */
    public Nodo $(String s, Boolean u_u) {
        class f {

            private Nodo Resulset = new Nodo(0, 0, "", "document", "");
            private int bandera = 0;
            public f() {
            }

            private Nodo busquedaPorEtiqueta(Nodo nodo, String etiqueta, Boolean u_u) {
                String prefijo = nodo.getPrefijo();
                String nombre = nodo.getNombre();
                ArrayList<Nodo> hijos = nodo.getHijos();
                String pre;
                String nom;
                
                if (etiqueta.matches(".*:.*")) {
                    pre = etiqueta.split(":")[0];
                    nom = etiqueta.split(":")[1];
                }else{
                    nom = etiqueta;
                    pre = "";
                }
                
                if(u_u){// si importa los prefijos
                    if (nombre.equals(nom) && prefijo.equals(pre)) {
                       // if(0 == bandera) {Resulset = nodo; bandera=1;}else
                        Resulset.setHijo(nodo);
                    } else {
                        for (Nodo hijo : hijos) {
                            busquedaPorEtiqueta(hijo, s, u_u);
                        }
                    }
                }else{
                    if (nombre.equals(nom)) {
                        //if(0 == bandera) {Resulset = nodo; bandera=1;}else
                        Resulset.setHijo(nodo);
                    } else {
                        for (Nodo hijo : hijos) {
                            busquedaPorEtiqueta(hijo, s, u_u);
                        }
                    }
                }

                return Resulset;
            }
        }

        return new f().busquedaPorEtiqueta(this, s, u_u);
    }
    
    /**
     * Busqueda de nodos por atributo
     *
     * @param By Es el nombre del atributo
     * @param s Es el valor del atributo
     * @return Arreglo de los nodos que coincidan
     */
    public Nodo $(String By, String s, Boolean u_u) {
        class f {

            private Nodo Resulset = new Nodo(0, 0, "", "document", "");

            public Nodo busquedaPorAtributo(Nodo nodo, String By, String valor, Boolean u_u) {
                
                ArrayList<Nodo> hijos = nodo.getHijos();
                ArrayList<Atributos> atributos = nodo.getAtributos();
                String By_pre;
                String By_nom;
                String valor_pre;
                String valor_nom;
                
                if (By.matches(".*:.*")) {
                    By_pre = By.split(":")[0];
                    By_nom = By.split(":")[1];
                }else{
                    By_nom = By;
                    By_pre = "";
                }
                if (valor.matches(".*:.*")) {
                    valor_pre = valor.split(":")[0];
                    valor_nom = valor.split(":")[1];
                }else{
                    valor_nom = valor;
                    valor_pre = "";
                }
                
                
                if (atributos.size() > 0) {
                    for (Atributos atributo : atributos) {
                        if(u_u){
                            if (atributo.getNombre().equals(By_nom)
                                    && atributo.getPrefijo().equals(By_pre)) {
                                if (atributo.getNumvalores() > 0) {
                                    for (Valoratributo nvalor : atributo.getValores()) {
                                        if (nvalor.getValor().equals(valor_nom)
                                                    && nvalor.getPrefijo().equals(valor_pre)) {
                                                Resulset.setHijo(nodo);
                                            }
                                        

                                    }
                                }

                            }
                        
                        }else{
                            if (atributo.getNombre().equals(By_nom)) {
                                if (atributo.getNumvalores() > 0) {
                                    for (Valoratributo nvalor : atributo.getValores()) {
                                        if (nvalor.getValor().equals(valor_nom)) {
                                                Resulset.setHijo(nodo);
                                            }
                                        

                                    }
                                }

                            }
                        
                        }
                    }
                } 
                    for (Nodo hijo : hijos) {
                        busquedaPorAtributo(hijo, By, s, u_u);
                    }
                
                return Resulset;
            }
        }
        return new f().busquedaPorAtributo(this, By, s, u_u);
    }
     /* ====================================================================== */
                        /* ./Funciones de busqueda */
    /* ====================================================================== */   
    
    @Override
    public String toString() {
        String d = "";
        for (int i = 0; i < nivel; i++) {
            d += "--";
        }
        return "\n"+d+"Nodo{" + 
             //   "id=" + id + 
             //   ", nivel=" + nivel + 
                "prefijo=" + prefijo + 
                ", nombre=" + nombre + 
                ", contenido=" + contenido + 
                ", atributos=" + atributos + 
                
                "hijos =" + hijos + 
                
                '}';
    }

    
    public String toXML(int o_o){
        String s="";
        String p="";
        
        String d = "";
        for (int i = 0; i < o_o; i++) {
            d += "    ";
        }
        
        if(!"".equals(prefijo))
            p = ":";  
        else
            p = ""; 
        
        
        s += "\n";
        s += d;
        s += "<"+prefijo+p+nombre;  
        if(atributos.size()>0){
            for(Atributos atributo : atributos){
                String pre = atributo.getPrefijo();
                String nom = atributo.getNombre();
                String cp  = (!"".equals(pre)) ? ":" : "";
                String attr= atributo.getS_valor();
                s += "\n"+d+"    "+pre+cp+nom+"=\""+attr+"\"";
            }
            if (etiquetavacia)
                s += "/>";
            else
                s += ">";
        }else{
            if (etiquetavacia)
                s += "/>";
            else
                s += ">";
        }
        if(!"".equals(contenido)){
            s += "\n"+d+"    "+contenido;
        }
        if(hijos.size()>0){
            for(Nodo h : hijos){
                s += h.toXML(o_o+1);
            }
        }
        if(!etiquetavacia)
            s += "\n"+d+"</"+prefijo+p+nombre+">";
        
        return s;
    }
}
