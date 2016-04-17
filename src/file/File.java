/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neommunications
 */
public class File {

    public static String getContentFile(String archivo) {
        FileReader f = null;
        String cadena;
        String ss = "";
        try {

            f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                ss += cadena;
            }
            b.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ss;
    }

    public static void main(String[] args) throws IOException {
        String s = getContentFile("./src/assets/SOAP.xml");
        System.out.println(s);
    }
}
