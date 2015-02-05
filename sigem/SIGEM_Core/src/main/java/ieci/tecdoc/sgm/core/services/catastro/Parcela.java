/**
 * ParcelaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.core.services.catastro;

public class Parcela {
    private Localizacion direccion;
    private BienesInmuebles lstBienesInmuebles;
    private String refCatastral;
    private Double superficie;
    private Double superficieConstruida;

    public Parcela() {
    }

    public Parcela(
           Localizacion direccion,
           BienesInmuebles lstBienesInmuebles,
           String refCatastral,
           Double superficie,
           Double superficieConstruida) {
           this.direccion = direccion;
           this.lstBienesInmuebles = lstBienesInmuebles;
           this.refCatastral = refCatastral;
           this.superficie = superficie;
           this.superficieConstruida = superficieConstruida;
    }


    /**
     * Gets the direccion value for this ParcelaOT.
     * 
     * @return direccion
     */
    public Localizacion getDireccion() {
        return direccion;
    }


    /**
     * Sets the direccion value for this ParcelaOT.
     * 
     * @param direccion
     */
    public void setDireccion(Localizacion direccion) {
        this.direccion = direccion;
    }


    /**
     * Gets the lstBienesInmuebles value for this ParcelaOT.
     * 
     * @return lstBienesInmuebles
     */
    public BienesInmuebles getLstBienesInmuebles() {
        return lstBienesInmuebles;
    }


    /**
     * Sets the lstBienesInmuebles value for this ParcelaOT.
     * 
     * @param lstBienesInmuebles
     */
    public void setLstBienesInmuebles(BienesInmuebles lstBienesInmuebles) {
        this.lstBienesInmuebles = lstBienesInmuebles;
    }


    /**
     * Gets the refCatastral value for this ParcelaOT.
     * 
     * @return refCatastral
     */
    public java.lang.String getRefCatastral() {
        return refCatastral;
    }


    /**
     * Sets the refCatastral value for this ParcelaOT.
     * 
     * @param refCatastral
     */
    public void setRefCatastral(java.lang.String refCatastral) {
        this.refCatastral = refCatastral;
    }


    /**
     * Gets the superficie value for this ParcelaOT.
     * 
     * @return superficie
     */
    public java.lang.Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this ParcelaOT.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Double superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the superficieConstruida value for this ParcelaOT.
     * 
     * @return superficieConstruida
     */
    public java.lang.Double getSuperficieConstruida() {
        return superficieConstruida;
    }


    /**
     * Sets the superficieConstruida value for this ParcelaOT.
     * 
     * @param superficieConstruida
     */
    public void setSuperficieConstruida(java.lang.Double superficieConstruida) {
        this.superficieConstruida = superficieConstruida;
    }
}
