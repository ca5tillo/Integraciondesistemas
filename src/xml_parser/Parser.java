package xml_parser;

import java.util.ArrayList;
import java.util.Iterator;
import xml_modelos.Atributos;
import xml_modelos.Nodo;
import xml_modelos.Valoratributo;

/**
 *
 * @author neommunications
 */
public class Parser {

    private String XML;

    public Parser(String XML) {
        this.XML = XML;
    }

    /**
     * Genera un Arreglo ordenado de etiquetas y contenido
     *
     * @param s texto en formato XML
     * @return Arreglo que identifica etiquetas y contenido
     */
    private ArrayList getLstContent(String s) {
        ArrayList lst = new ArrayList();
        String[] contenidoArray;//Rompre cadena

        s = s.replaceAll("\n", " "); //Quitar saltos de linea
        s = s.replaceAll("<", ",<");//Poner , para el split
        s = s.replaceAll(">", ">,"); //Poner , para el split

        contenidoArray = s.split(",");
        for (String b : contenidoArray) {
            //Comprueba que el elemento no este vacio o tenga puros espacios
            if (b.matches("(.*[a-zA-Z0-9]+.*)")) {
                if (!b.matches("^(<!--).*")) { // quito los comentarios
                    if (!b.matches("^(<\\?xml).*")) { // quito la primera linea la del <?xml 

                        lst.add(b.trim()); // le quito los espacios al inicio y al fnal del string y lo anado al arreglo
                    }

                }
            }

        }
        return lst;
    }

    /* ====================================================================== */
                        /* funciones para crear el Arbol */
    /* ====================================================================== */
    /**
     * crearArbol()
     *
     * @param lst Arreglo que identifica etiquetas y contenido que es lo que
     * devuelve getLstContentFile
     * @return El arbol XML
     */
    private Nodo crearArbol(ArrayList lst) {
        @SuppressWarnings("UnusedAssignment")

        Nodo arbol = new Nodo(0, 0, "", "document", "");
        int contador = 0;
        String ss;
        String prefijo = "";
        String nombre;
        Iterator it = lst.iterator();
        ArrayList<Atributos> atributos = new ArrayList<>();

        while (it.hasNext()) {

            atributos.clear();
            ss = (String) it.next();

            if (ss.matches(".*>.*")) { //Si es una etiqueta

                if (!ss.matches(".*(/>)$")) {//Si no es etiqueta de vacia 

                    if (!ss.matches(".*</.*")) {//Si no es etiqueta de cierre

                        if (ss.matches(".*=.*")) {
                            //Si tiene atributos
                            // nombre = getDatobetween("<(.*)\\s",ss);
                            nombre = getDatobetween(ss, "<", " ").trim();
                            if (nombre.matches(".*:.*")) {
                                prefijo = nombre.split(":")[0];
                                nombre = nombre.split(":")[1];
                            } else {
                                prefijo = "";
                            }
                            atributos = getAtributos(getDatobetween(ss, " ", ">"));

                        } else {
                            //   nombre = getDatobetween("<(.*)>",ss);
                            nombre = getDatobetween(ss, "<", ">").trim();
                            if (nombre.matches(".*:.*")) {
                                prefijo = nombre.split(":")[0];
                                nombre = nombre.split(":")[1];
                            } else {
                                prefijo = "";
                            }
                            atributos.clear();
                        }
                        contador += 1;
                        Nodo temnodo = new Nodo(contador, contador, prefijo, nombre, "");
                        temnodo.setAtributos(atributos);
                        setobjeto(arbol, contador - 1, temnodo);

                    } else {
                        // Etiqueta de cierre
                        //nombre   =  getDatobetween("<\\/(.*)>",ss);
                        contador = cerrarEtiqueta(arbol, contador);
                    }

                } else {
                    // Es Etiqueta vacia
                    if (ss.matches(".*=.*")) { // tiene atributos
                        nombre = getDatobetween(ss, "<", " ").trim();
                        if (nombre.matches(".*:.*")) {
                            prefijo = nombre.split(":")[0];
                            nombre = nombre.split(":")[1];
                        } else {
                            prefijo = "";
                        }
                        atributos = getAtributos(getDatobetween(ss, " ", "/>"));
                    } else {
                        nombre = getDatobetween(ss, "<", "/>").trim();
                        if (nombre.matches(".*:.*")) {
                            prefijo = nombre.split(":")[0];
                            nombre = nombre.split(":")[1];
                        } else {
                            prefijo = "";
                        }
                    }
                    Nodo temnodo = new Nodo(-1, contador + 1, prefijo, nombre, "");
                    temnodo.setAtributos(atributos);
                    temnodo.setEtiquetavacia(true);
                    setobjeto(arbol, contador, temnodo);

                }

            } else {
                setContent(arbol, ss, contador);
            }
        }

        return arbol;
    }

    /**
     * getDatobetween()
     *
     * @param ss texto principal
     * @param i caracter inicial
     * @param f caracter final
     * @return Un substring que va de i a f sin contener i
     */
    private String getDatobetween(String ss, String i, String f) {

        // Fuente:
        // http://www.w3api.com/wiki/Java:String.indexOf()
        return ss.substring(ss.indexOf(i) + 1, ss.indexOf(f));
        /*
        Matcher matcher = Pattern.compile(exp).matcher(ss);
        if (matcher.find()) {
            dato = matcher.group(1);
        }
        //*/
        //return dato;
    }

    /**
     * Funcion recursiva que inserta un nodo hijo a un nodo padre
     *
     * @param item Nodo que se compara con id para verificar si es el padre
     * @param id Es el identificador del padre
     * @param objs Es el nodo hijo a insertar
     */
    private void setobjeto(Nodo item, int id, Nodo objs) {
        if (item.getId() == id) {
            item.setHijo(objs);
        } else {
            for (int i = 0; i < item.getNumHijos(); i++) {
                if( null != item.getHijo(i))
                    setobjeto(item.getHijo(i), id, objs);
            }
        }
    }

    /**
     * Funcion recursiva Cierra el nodo creado y disminuye en 1 a el nivel
     *
     * @param item Es el nodo con el cual se compara con el id para cerrar el
     * nodo
     * @param id Es el id del nodo a buscar para cerrarlo
     * @return El nuevo valor de nivel del arbol
     */
    private int cerrarEtiqueta(Nodo item, int id) {
        if (item.getId() == id) {
            item.setId(-1); // poner el id en -1 significara que ya fue cerrado, ya se encontro su etiqueta de cierre
            id -= 1;
        } else {
            for (int i = 0; i < item.getNumHijos(); i++) {
                if( null != item.getHijo(i))
                    id = cerrarEtiqueta(item.getHijo(i), id);
            }

        }
        return id;
    }

    /**
     * Funcion recursiva que inserta el contenido entre etiquetas a su nodo
     * correspondiente
     *
     * @param item Es el nodo con el cual se compara si ese contenido pertenece
     * a esa etiqueta
     * @param cadena Es el contenido de la etiqueta
     * @param contador Es el id de la etiqueta que se creo previamente
     */
    private void setContent(Nodo item, String cadena, int contador) {
        if (item.getId() == contador) {
            item.setContenido(cadena);
        } else {
            for (int i = 0; i < item.getNumHijos(); i++) {
                if( null != item.getHijo(i))
                    setContent(item.getHijo(i), cadena, contador);
            }
        }
    }

    /**
     * Obtiene arreglo de obj Atributos
     *
     * @param cadena tipo nombre="valor"
     * @return Arreglo de objetos Atributos
     */
    private ArrayList<Atributos> getAtributos(String cadena) {

        ArrayList<Atributos> attr = new ArrayList<>();
        String[] c;
        String[] d;
        String nombre = "";
        String prefijo = "";

        cadena = cadena.trim();
        cadena = cadena.replaceAll("=", "");
        c = cadena.split("\"");//Rompre cadena

        for (int i = 0; i < c.length; i++) {
            c[i] = c[i].trim();
            if (i % 2 == 0) {// Si es par es el nombre del atributo
                //attr.add(new Atributos(c[i]));
                nombre = c[i];
                if (nombre.matches(".*:.*")) {
                    prefijo = nombre.split(":")[0];
                    nombre = nombre.split(":")[1];
                } else {
                    prefijo = "";
                }
            } else {  // Este es el contenido del atributo
                Atributos f = new Atributos(prefijo, nombre);
                f.setS_valor(c[i]);
                d = c[i].split(" ");
                if (d.length > 0) {

                    for (String e : d) {
                        String pre;
                        String nom;
                        if (e.matches(".*:.*")) {
                            pre = e.split(":")[0];
                            nom = e.split(":")[1];
                        } else {
                            pre = "";
                            nom = e;
                        }
                        f.setValor(new Valoratributo(pre, nom));
                    }

                }

                attr.add(f);
            }

        }
        return attr;
    }
    /* ====================================================================== */
                   /* ./funciones para crear el Arbol */
    /* ====================================================================== */    
    
    /**
     * Crea un arbol de objetos Nodo apartir de codigo XML o lenguajede marcado
     * @return El arbol de nodos 
     */
    public Nodo getDOM(){
        return  crearArbol(getLstContent(XML));
    }
    
}
