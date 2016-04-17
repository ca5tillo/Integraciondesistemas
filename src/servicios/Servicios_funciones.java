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
 * @author neommunications
 */
public class Servicios_funciones {

    public static String body(String function, String Resultado) {
        String body = "<soap:Body>"
                + "  <" + function + ">"
                + "    <Value>" + Resultado + "</Value>"
                + "  </" + function + ">"
                + "</soap:Body>";
        return body;
    }

    public static String bodylst(String function, ArrayList<String> lst) {
        String body = "<soap:Body>"
                + "  <" + function + ">";
        for (String s : lst) {
            body += "    <Value>" + s + "</Value>";
        }

        body += "  </" + function + ">"
                + "</soap:Body>";
        return body;
    }

    public static String bodyError() {
        return "<soap:Body><soap:Fault><errorconpeticion>Error con los parametros que se enviaron<errorconpeticion></soap:Fault></soap:Body>";
    }

}
