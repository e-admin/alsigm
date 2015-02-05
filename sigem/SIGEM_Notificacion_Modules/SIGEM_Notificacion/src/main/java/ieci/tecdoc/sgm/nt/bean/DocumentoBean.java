/*
 * DocumentoBean.java
 *
 * Created on 25 de mayo de 2007, 12:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * @author Usuario
 */
public final class DocumentoBean  extends GenericoBean{
    
    private String codigoNotificion;
    private String codigoFichero;
    
    /** Creates a new instance of DocumentoBean */
    public DocumentoBean() {
    }

    /*
     * Devuelve el valor del codigo de notificacion
     * @return String codigo de notificacion
     */
    public String getCodigoNotificion() {
        return codigoNotificion;
    }

    /*
     * Establece el valor del codigo de notificaicon
     * @param codigo de notificacion
     */
    public void setCodigoNotificion(String codigoNotificion) {
        this.codigoNotificion = codigoNotificion;
    }

    /*
     * Devuelve el codigo del fichero
     *
     * @return String codigo del fichero
     */
    public String getCodigoFichero() {
        return codigoFichero;
    }

    /*
     * Establece el valor del codigo del fichero
     *
     * @param codigo del fichero
     */
    public void setCodigoFichero(String codigoFichero) {
        this.codigoFichero = codigoFichero;
    }

   
}
