/*
 * Documento.java
 *
 * Created on 23 de mayo de 2007, 15:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database.datatypes;

/**
 *
 * @author Usuario
 */
public interface Documento { 
   
    
    public abstract String getExpediente ();
    
    public abstract String getNifDestinatario (); 
    
    public abstract String getCodigo(); 
    
    public abstract String getGuid();     
    
    public abstract String getNotiId();
    
    public abstract void setExpediente (String nuevoValor_);
    
    public abstract void setNifDestinatario (String nuevoValor_); 
    
    public abstract void setCodigo(String nuevoValor_); 
    
    public abstract void setGuid(String nuevoValor_);
    
    public abstract void setNotiId(String notiid);
    /**
     * Recoge los valores de la instancia en una cadena xml
     * @param header Si se incluye la cabecera
     * @return los datos en formato xml
     */	
	
    public abstract String toXML(boolean header);

    /**
     * Devuelve los valores de la instancia en una cadena de caracteres.
     */
	
    public abstract String toString();    
}
