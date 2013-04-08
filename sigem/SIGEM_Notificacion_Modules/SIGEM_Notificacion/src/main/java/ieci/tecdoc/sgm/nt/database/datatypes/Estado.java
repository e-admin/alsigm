/*
 * Estado.java
 *
 * Created on 15 de junio de 2007, 15:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database.datatypes;

/**
 *
 * @author X73994NA
 */
public interface Estado {
    
     /**
     * Devuelve el id del estado
     * @return String error
     */       
    public abstract String getId ();
    
    /**
     * Establece el id del estado
     * @return String codigo de error.
     */       
    public abstract void setId (String nuevoValor_); 
    
     /**
     * Devuelve la descripcion del sisnot
     * @return String error
     */       
    public abstract String getIdSisnot ();
    
    /**
     * Establece la descripcion del sisnot
     * @return String codigo de error.
     */       
    public abstract void setIdSisnot (String nuevoValor_); 
    
     /**
     * Devuelve la descripcion 
     * @return String error
     */       
    public abstract String getDescripcion ();
    
    /**
     * Establece el id del estado
     * @return String codigo de error.
     */       
    public abstract void setDescripcion (String nuevoValor_); 
    
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
