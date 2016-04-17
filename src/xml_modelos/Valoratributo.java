/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_modelos;

/**
 *
 * @author neommunications
 */
public class Valoratributo {
    private String prefijo;
    private String valor;

    public Valoratributo(String prefijo, String valor) {
        this.prefijo = prefijo;
        this.valor = valor;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Valoratributo{" + "prefijo=" + prefijo + ", valor=" + valor + '}';
    }
    
    
}
