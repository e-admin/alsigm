/*
 * DocumentoImpl.java
 *
 * Created on 23 de mayo de 2007, 15:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database.datatypes;

/**
 *
 * @author Usuario
 */
public class DocumentoImpl implements Documento{
    
    /** Creates a new instance of DocumentoImpl */
    public DocumentoImpl() {
    }  
    
    private String expediente;
    
    private String nifDestinatario;
     
    private String codigo;
    
    private String guid;
    
    private String notiId;
    
    private Integer tipoDoc;
     /**
     * Recoge los valores de la instancia en una cadena xml
     * @param header Si se incluye la cabecera
     * @return los datos en formato xml
     */		
    public String toXML(boolean header){
        return null;
    }

    /**
     * Devuelve los valores de la instancia en una cadena de caracteres.
     */	
    public String toString(){
        return toXML (false);
    }
  
    /**
     * Devuelve el valor del numero de expediente
     * @return String numero de expediente
     */
    public String getExpediente() {
        return expediente;
    }

    /**
     * Establece el numero de expediente
     * @param expediente String numero de expediente
     */
    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    /**
     * Devuelve el nif del destinatario
     * @return String nif del destinatario
     */
    public String getNifDestinatario() {
        return nifDestinatario;
    }

    /**
     * Establece el valor del nif del destinatario
     * @param nifDestinatario String nif del destinatario
     */
    public void setNifDestinatario(String nifDestinatario) {
        this.nifDestinatario = nifDestinatario;
    }

    /**
     * Devuelve el codigo del documento
     * @return String codigo del documento
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el codigo del documento
     * @param codigo String codigo del documento
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el codigo guid del documento
     * @return String codigo guid del documento
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Establece el codigo guid del documento 
     * @param guid String codigo guid del documento
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public String getNotiId() {
        return notiId;
    }

    public void setNotiId(String notiId) {
        this.notiId = notiId;
    }
    
    public Integer getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Integer tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
    
    public void setTipoDoc(int tipoDoc) {
        this.tipoDoc = new Integer(tipoDoc);
    }
    
}
