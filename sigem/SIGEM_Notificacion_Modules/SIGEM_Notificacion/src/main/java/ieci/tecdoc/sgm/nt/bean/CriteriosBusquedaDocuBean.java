/*
 * CriteriosBusquedaDocuBean.java
 *
 * Created on 25 de mayo de 2007, 8:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * @author Usuario
 */
public final class CriteriosBusquedaDocuBean extends GenericoBean{
    
    private String expediente;
    
    private String nifDestinatario;
    
    private String codigoNoti;
    
    private String codigDoc;
    
    private String notiId;
    
    private Integer tipoDoc;
    
    
    /** Creates a new instance of CriteriosBusquedaDocuBean */
    public CriteriosBusquedaDocuBean() {
    }
    
  
    /**
     * Constructor para inicializar todos los campos del bean
     * 
     * @param expediente_ String valor del expediente de la notificacion asociada al documento
     * @param nif_ String valor del nif destinatario de la notificacion asociada al documento     
     */
    public CriteriosBusquedaDocuBean(String expediente_, String nif_) {
        setExpediente(expediente_);
    
        setNifDestinatario(nif_);
    }
    
    
    /**
     * Constructor para inicializar todos los campos del bean
     * 
     * @param expediente_ String valor del expediente de la notificacion asociada al documento
     * @param nif_ String valor del nif destinatario de la notificacion asociada al documento     
     */
    public CriteriosBusquedaDocuBean(String expediente_, String nif_,  String codigoNoti_, String codigoDoc_) {
        setExpediente(expediente_);
    
        setNifDestinatario(nif_);
        
        setCodigoNoti(codigoNoti_);
    
          setCodigDoc(codigoDoc_);
    }

    /** Devuelve el valor del expediente
     * 
     * @return String expediente
     */
    public String getExpediente() {
        return expediente;
    }

    /** Establece el valor del expediente
     * 
     * @param expediente String valor del expediente
     */
    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    /** Devuelve el valor del nif destinatario
     * 
     * @return String nif destinatario
     */
    public String getNifDestinatario() {
        return nifDestinatario;
    }

    /** Establece el valor del nif destinatario
     * 
     * @param nifDestinatario String valor del nif destinatario
     */
    public void setNifDestinatario(String nifDestinatario) {
        this.nifDestinatario = nifDestinatario;
    }

    /**
     * Devuelve el valor del codigo de notificacion
     * @return String codigo de notificacion
     */
    public String getCodigoNoti() {
        return codigoNoti;
    }

    /**
     * Establece el valor del codigo de notificacion
     * @param codigoNoti codigo de notificacion
     */
    public void setCodigoNoti(String codigoNoti) {
        this.codigoNoti = codigoNoti;
    }

    /**
     * Devuelve el valor del codigo del dcumento
     * @return String codigo del documento
     */
    public String getCodigDoc() {
        return codigDoc;
    }

    /**
     * Establece el valor del codigo del documento
     * @param codigDoc String codigo del documento
     */
    public void setCodigDoc(String codigDoc) {
        this.codigDoc = codigDoc;
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
