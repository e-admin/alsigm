package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.pe.database.LiquidacionDatos;

/*
 * $Id: Cuaderno60Modalidad3.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public class Cuaderno60Modalidad3  extends Cuaderno60 {

	public static final String MODALIDAD_3 = "AL3";
	
	private String fechaDevengo;

    private String expediente;

    private String justificante;

    private String informacionEspecifica;

    private String nifContribuyente;

    private String acreditacionPagos;


    /**
     * Gets the fechaDevengo value for this Cuaderno60_3.
     * 
     * @return fechaDevengo
     */
    public String getFechaDevengo() {
        return fechaDevengo;
    }


    /**
     * Sets the fechaDevengo value for this Cuaderno60_3.
     * 
     * @param fechaDevengo
     */
    public void setFechaDevengo(String fechaDevengo) {
        this.fechaDevengo = fechaDevengo;
    }

    
    /**
     * Gets the expediente value for this Cuaderno60_3.
     * 
     * @return expediente
     */
    public String getExpediente() {
        return expediente;
    }


    /**
     * Sets the expediente value for this Cuaderno60_3.
     * 
     * @param expediente
     */
    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }


    /**
     * Gets the justificante value for this Cuaderno60_3.
     * 
     * @return justificante
     */
    public String getJustificante() {
        return justificante;
    }


    /**
     * Sets the justificante value for this Cuaderno60_3.
     * 
     * @param justificante
     */
    public void setJustificante(String justificante) {
        this.justificante = justificante;
    }


    /**
     * Gets the informacionEspecifica value for this Cuaderno60_3.
     * 
     * @return informacionEspecifica
     */
    public String getInformacionEspecifica() {
        return informacionEspecifica;
    }


    /**
     * Sets the informacionEspecifica value for this Cuaderno60_3.
     * 
     * @param informacionEspecifica
     */
    public void setInformacionEspecifica(String informacionEspecifica) {
        this.informacionEspecifica = informacionEspecifica;
    }

    /**
     * Gets the nifContribuyente value for this Cuaderno60_3.
     * 
     * @return nifContribuyente
     */
    public String getNifContribuyente() {
        return nifContribuyente;
    }


    /**
     * Sets the nifContribuyente value for this Cuaderno60_3.
     * 
     * @param nifContribuyente
     */
    public void setNifContribuyente(String nifContribuyente) {
        this.nifContribuyente = nifContribuyente;
    }


    /**
     * Gets the acreditacionPagos value for this Cuaderno60_3.
     * 
     * @return acreditacionPagos
     */
    public String getAcreditacionPagos() {
        return acreditacionPagos;
    }


    /**
     * Sets the acreditacionPagos value for this Cuaderno60_3.
     * 
     * @param acreditacionPagos
     */
    public void setAcreditacionPagos(String acreditacionPagos) {
        this.acreditacionPagos = acreditacionPagos;
    }
    
    public void set(LiquidacionDatos liquidacion){
		super.set(liquidacion);
		setCodTributo(liquidacion.getIdTasa());
	}
}
