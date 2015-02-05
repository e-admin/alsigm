package ieci.tecdoc.sgm.ct.struts.form;

import org.apache.struts.action.ActionForm;

/**
 * Clase ActionForm representativa del formulario de BusquedaExpedientes.jsp 
 */
public class BusquedaExpedientesForm extends ActionForm {
	
	private static final long serialVersionUID = -378113822003097203L;
	
	private String CNIF;
	private String fechaDesde;
	private String fechaHasta;
	 
    /**
     * Devuelve el documento de identidad del interesado
     * 
     * @return CNIF
     */
    public String getCNIF() {
            return CNIF;
    }
    
    /**
     * Establece el documento de identidad del interesado
     * 
     * @param CNIF
     */
    
    public void setCNIF (String CNIF) {
        this.CNIF = CNIF;
    }
    
    /**
     * Devuelve fechaDesde para la busqueda
     * 
     * @return fechaDesde
     */
    
    public String getFechaDesde() {
        return fechaDesde;
    }
    
    /**
     * Establece fechaDesde del formulario
     * 
     * @param fechaDesde
     */
    public void setFechaDesde (String fechaDesde) {
            this.fechaDesde = fechaDesde;
    }
    
    /**
     * Devuelve fechaHasta para la busqueda
     * 
     * @return fechaHasta
     */
    public String getFechaHasta() {
        return fechaHasta;
    }
    
    /**
     * Establece fechaHasta del formulario
     * 
     * @param fechaHasta
     */
    public void setFechaHasta (String fechaHasta) {
            this.fechaHasta = fechaHasta;
    }
}
