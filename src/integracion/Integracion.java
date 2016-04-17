/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracion;

import static file.File.getContentFile;
import static xml_download.Download.getXML;
import xml_parser.*;
import java.util.ArrayList;
import servicios.*;
import xml_modelos.Nodo;

public class Integracion {

    private static Nodo WSDL;
    private static Nodo SOAP_Response;
    private String nombreOperacionElegida;
    private String nomEtiquetaREsultado;

    public Integracion(String url_WSDL) {
        Integracion.WSDL = new Parser(getXML(url_WSDL)).getDOM();

    }

    public String get_XML_WSDL() {
        String wsdl;
        wsdl = "<?xml version=\"1.0\"?>";
        wsdl += WSDL.getHijo(0).toXML(0);
        return wsdl;
    }

    /**
     * get valor del atributo name de la etiqueta definitions
     *
     * @return El valor de la etiqueta name
     */
    public String getNombreServicio() {
        String s;
        try {
            s = WSDL.$("definitions", false).getHijo(0).attr("name", false);
        } catch (IndexOutOfBoundsException e) {
            s = null;
        }
        return s;
    }

    /**
     * Obteine la ip y puerto que deben de estar en el WSDL
     *
     * @return Un string en el formato 172.16.10.164:4950 de no encontrarlo
     * Retorna NULL
     */
    public String getIPP() {
        String dir;
        String puerto = null;
        String ip;
        try {
            ip = WSDL.$("service", false).$("address", false).getHijo(0).attrAll("location", false);
            if (ip.matches(".*:.*")) {
                dir = ip;
            } else {
                puerto = WSDL.$("service", false).$("port", false).getHijo(0).attrAll("name", false);
                if (puerto != null) {
                    dir = ip + ":" + puerto;
                } else {
                    dir = "Direccion mal formada en el WSDL";
                }
            }
        } catch (IndexOutOfBoundsException e) {
            dir = null;
        }
        return dir;
    }

    public String get_Lenguaje_del_servidor() {
        try {
            return WSDL.$("binding", false).getHijo(0).attr("languaje", false);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Servicio_test: suma: Error en get de array");
            return null;
        }
    }

    /**
     * Usado en la GUI
     *
     * @return retorna una cadena con los metodos ubicados en <portType>
     * separador por una , Si no hay ningun metodo retorna una cadena vacia;
     */
    public String getStringMetodos() {
        String s = "";
        boolean z = true;
        for (Nodo n : WSDL.$("portType", false).$("operation", false).getHijos()) {
            String name = n.attr("name", false);
            if (z) {
                s = name;
                z = false;
            } else {
                s += "," + name;
            }
        }

        return s;
    }

    /**
     *
     * @param n Es el nombre de la operacion <operation name="n">
     * @return lista de los nombres de los parametros
     */
    public ArrayList<String> getParametros(String n) {
        // obtener la etiqueta input y el nombre de su mensaje 
        ArrayList<String> lst_Parametros = new ArrayList<>();
        String inputAttrMessage;
        this.nombreOperacionElegida = n;
        
        try{
        inputAttrMessage
                = WSDL.$("portType", false).$("name", n, false).$("input", false).getHijo(0).attr("message", false);
        nomEtiquetaREsultado
                = WSDL.$("portType", false).
                $("name", n, false).
                $("output", false).getHijo(0).
                attr("message", false);
        nomEtiquetaREsultado
                = WSDL.$("message", false).
                $("name", nomEtiquetaREsultado, false).
                getHijo(0).getHijo(0).attr("name", false);
        //System.out.println(nomEtiquetaREsultado);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error al buscar el portype input e output");
            return null;
        }
        Nodo NMessage = WSDL.$("message", false).$("name", inputAttrMessage, false).getHijo(0);
        //System.out.println(NMessage);
        if (null != NMessage) {
            if (NMessage.getNumHijos() > 0) {
                for (Nodo part : NMessage.getHijos()) {
                    if (null != part.attr("type", false)) {
                        String message_part_name = part.attr("name", false);

                        lst_Parametros.add(message_part_name);
                    } else if (null != part.attr("element", false)) {
                        if (part.attr("element", false).matches(".*string.*")) {
                            String message_part_name = part.attr("name", false);

                            lst_Parametros.add(message_part_name);
                        }
                    }
                }

            }
        }

        return lst_Parametros;
    }

    public String getSOAP_Request(String metodo, String param) {
        String T_SOAP = "";
        String ubicacion = "";
        Nodo n;
        try {
            n = WSDL.$("binding", false).$("operation", false).$("name", metodo, false).$("input", false).getHijo(0).getHijo(0);
        } catch (IndexOutOfBoundsException e) {
            return "Operacion no encontrada en el binding del WSDL";
        }

        ubicacion = n.getNombre();

        if (ubicacion.matches(".*body.*")) {
            T_SOAP = _getSOAP_Request(metodo, param);

            Nodo SOAP_Request = new Parser(T_SOAP).getDOM();

            T_SOAP = "<?xml version=\"1.0\"?>";
            T_SOAP += SOAP_Request.getHijo(0).toXML(0);
        }
        return T_SOAP;
    }

    private String _getSOAP_Request(String metodo, String param) {
        String T_SOAP = getContentFile("./src/assets/SOAP.xml");
        if (!"".equals(param)) {
            String body = _getBodySoap(metodo, param);
            T_SOAP = T_SOAP.replaceAll("<!--soap:Body-->", body); //Quitar saltos de linea
        }

        return T_SOAP;
    }

    private static String _getBodySoap(String metodo, String d) {
        String[] params = d.split(",");

        String s = ""
                + "<soap:Body>"
                + "<" + metodo.trim() + ">";
        for (String param : params) {
            String x = param.split(":")[0].trim();
            String z = param.split(":")[1].trim();
            s += "<" + x + ">" + z + "</" + x + ">";
        }
        s
                += "</" + metodo.trim() + ">"
                + "</soap:Body>";
        return s;
    }

    public String get_XML_Response(String XML) {
        String s ="";
        try{
            SOAP_Response = new Parser(XML).getDOM();

            s = "<?xml version=\"1.0\"?>";
            s += SOAP_Response.getHijo(0).toXML(0);
        }catch (IndexOutOfBoundsException e) {
            return XML;
        }
        return s;
    }

    public String get_resultado_response() {
        String n = "El resultado es : ";
        String g = "";
        try {
            //System.out.println(SOAP_Response.$("Body", false).$(nomEtiquetaREsultado, false));
            ///*
            if(SOAP_Response.$("Body", false).$(nomEtiquetaREsultado, false).getNumHijos() > 0 ){
                for(Nodo nodo : SOAP_Response.$("Body", false).$(nomEtiquetaREsultado, false).getHijos()){
                    g += "{"+nodo.getContenido()+"} \n";
                }
            }else{
                for(Nodo nodo : SOAP_Response.$("Body", false).getHijo(0).getHijos()){
                    g += "{"+nodo.getContenido()+"} \n";
                }
                //g = "SoapResponseMalFormado";
            }
            
            //*/
            //g = SOAP_Response.$("Body", false).$(nomEtiquetaREsultado, false).getHijo(0).getContenido();
            
        } catch (IndexOutOfBoundsException e) {
            g = "SoapResponseMalFormado";
        }catch(NullPointerException e){
            g = "SoapResponseMalFormado";
        }
        if (g.equals("SoapResponseMalFormado")) {
            try {
                g = SOAP_Response.$("Body", false).$("soap:Fault", false).getHijo(0).getHijo(0).getContenido();

            } catch (IndexOutOfBoundsException e) {
                g = "SoapResponseMalFormado";
            }
        }

        return n+g;
    }

    public static String GetResponse(String XML) {
        Nodo Response = null;
        String function;
        String body = null;
        try {
            Response = new Parser(XML).getDOM().$("Body", false).getHijo(0).getHijo(0);
        } catch (IndexOutOfBoundsException e) {
            Response = null;
        }
        if (Response == null) {
            return "Error funcion Integracion.GetResponse   ("+XML+")";
        }
        
        function = Response.getNombre();
        switch (function) {
            case "Calculadora_suma":
                body = Calculadora.suma(Response);
                break;
            case "Calculadora_resta":
                body = Calculadora.resta(Response);
                break;
            case "Calculadora_multiplicacion":
                body = Calculadora.multiplicacion(Response);
                break;
            case "Calculadora_division":
                body = Calculadora.division(Response);
                break;
            case "Calculadora_lista0a10":
                body = Calculadora.lista0a10(Response);
                
                break;
            case "getAll_Farmacia":
                body = new Farmacia().getAll(Response);
                break;
            case "getProducto_Farmacia":
                body = new Farmacia().getProducto(Response);
                break;
            case "getAll_Banco":
                body = new Banco().getAllB(Response);
                break;
            case "getPersona_Banco":
                body = new Banco().getPersona(Response);
                break;
                
            default:

                body = "<soap:Body><soap:Fault>Error Nunguna funcion coincide</soap:Fault></soap:Body>";

            break;
        }

        String T_SOAP = getContentFile("./src/assets/SOAP.xml");
        T_SOAP = T_SOAP.replaceAll("<!--soap:Body-->", body);
        T_SOAP = T_SOAP.trim();

        return T_SOAP;
    }

    public static void main(String[] args) {
        String A = "";
        String B = "http://16i.890m.com/wsdl/Armando/Farmacia.wsdl";
        Integracion i = new Integracion(A);
        //System.out.println(i.getIPP());
        i.getParametros("Calculadora_lista0a10");

        i.get_XML_Response(soapResponse);
        String ssssss = i.get_resultado_response();
        System.out.println(ssssss);
        // System.out.println(i.get_XML_WSDL());

        //System.out.println(i.getSOAP_Request("suma","a:b,c:d"));
        //System.out.println(i.GetResponse(XML));
    }
    private static String XML = "<?xml version=\"1.0\"?>\n"
            + "<soap:Envelope\n"
            + "    xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n"
            + "    encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n"
            + "    hola\n"
            + "    <soap:Header\n"
            + "        hj=\"hjk\"\n"
            + "        ghjhj=\"yuiyui\">\n"
            + "    </soap:Header>\n"
            + "    <soap:Body>\n"
            + "        <suma>\n"
            + "            <A>\n"
            + "                4\n"
            + "            </A>\n"
            + "            <B>\n"
            + "                8\n"
            + "            </B>\n"
            + "        </suma>\n"
            + "    </soap:Body>\n"
            + "</soap:Envelope>";
    private static String soapResponse = "<?xml version=\"1.0\"?>\n"
            + "<soap:Envelope\n"
            + "    xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n"
            + "    encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n"
            + "    <soap:Header>\n"
            + "    </soap:Header>\n"
            + "    <soap:Body>\n"
            + "        <sumaResponse>\n"
            + "            <value>\n"
            + "                16\n"
            + "            </value>\n"
            + "        </sumaResponse>\n"
            + "    </soap:Body>\n"
            + "</soap:Envelope>";
    private static String soapresponseError = "<?xml version=\"1.0\"?>\n"
            + "<soap:Envelope\n"
            + "    xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n"
            + "    encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n"
            + "    <soap:Header>\n"
            + "    </soap:Header>\n"
            + "    <soap:Body>\n"
            + "        <soap:Fault>\n"
            + "            Error con los parametros que se enviaron\n"
            + "        </soap:Fault>\n"
            + "    </soap:Body>\n"
            + "</soap:Envelope>";
    private static String algo = "<?xml version=\"1.0\"?>\n" +
"<soap:Envelope\n" +
"    xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n" +
"    encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
"    <soap:Header>\n" +
"    </soap:Header>\n" +
"    <soap:Body>\n" +
"        <Calculadora_lista0a10Response>\n" +
"            <Value>\n" +
"                0\n" +
"            </Value>\n" +
"            <Value>\n" +
"                1\n" +
"            </Value>\n" +
"            <Value>\n" +
"                2\n" +
"            </Value>\n" +
"            <Value>\n" +
"                3\n" +
"            </Value>\n" +
"            <Value>\n" +
"                4\n" +
"            </Value>\n" +
"            <Value>\n" +
"                5\n" +
"            </Value>\n" +
"            <Value>\n" +
"                6\n" +
"            </Value>\n" +
"            <Value>\n" +
"                7\n" +
"            </Value>\n" +
"            <Value>\n" +
"                8\n" +
"            </Value>\n" +
"            <Value>\n" +
"                9\n" +
"            </Value>\n" +
"        </Calculadora_lista0a10Response>\n" +
"    </soap:Body>\n" +
"</soap:Envelope>";
}
