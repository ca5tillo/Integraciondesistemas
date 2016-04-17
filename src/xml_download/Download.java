/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_download;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author neommunications
 */
public class Download {
    
    public Download(String url_WSDL) {}
    
    /**
     * download()
     *
     * @param path direccion de en el servidor del archivo
     * @param name direccion donde se guardara el archivo descargado
     */
    public static void download(String path, String name) {

        try {

            URL url = new URL(path);

            // establecemos conexion
            URLConnection urlCon = url.openConnection();

            // Se obtiene el inputStream y se abre el fichero
            InputStream is = urlCon.getInputStream();
            FileOutputStream fos = new FileOutputStream(name);

            // Lectura y escritura en el fichero local
            byte[] array = new byte[10000]; // buffer temporal de lectura.
            int leido = is.read(array);
            while (leido > 0) {
                fos.write(array, 0, leido);
                leido = is.read(array);
            }

            // cierre de conexion y fichero.
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * getContentFile() Leer archivo
     *
     * @param path Direccion del archivo que tendra que leer
     * @return El contenido del archivo en un String
     */
    public static String getContentFile(String path) {
        String contenidoArchivo = "";
        String c2 = "";
        String archivo = path;
        FileReader f;
        try {
            f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while ((contenidoArchivo = b.readLine()) != null) {
                c2 += contenidoArchivo;
                //  System.out.println(contenidoArchivo);
            }
            b.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }

        return c2;
    }
    
    private static String getContenidoHTML(String url_wsdl) {
        String contenido = "";
        try {
        URL url = new URL(url_wsdl);
        URLConnection uc = url.openConnection();
        uc.connect();
        //Creamos el objeto con el que vamos a leer
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String inputLine;
        
        while ((inputLine = in.readLine()) != null) {
           // System.out.println(inputLine);
            contenido += inputLine;
        }
        in.close();
        }catch(Exception e){
        }
        
        
        return contenido;
    }
    
    public static String getXML(String url_WSDL){
        String s;
        if(! url_WSDL.equals("")){
            download(url_WSDL, "./src/assets/wsdl.wsdl");  
        }
        
        s = getContentFile("./src/assets/wsdl.wsdl");
        return s;
    }
    
}
