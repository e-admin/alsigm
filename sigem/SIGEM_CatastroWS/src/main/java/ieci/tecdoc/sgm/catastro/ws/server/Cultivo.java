/**
 * CultivoOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Cultivo extends RetornoServicio {
    private String calificacion;
    private String denominacion;
    private String identificador;
    private Integer intensidadProductiva;
    private Double superficie;

    public Cultivo() {
    }

    public Cultivo(
           String calificacion,
           String denominacion,
           String identificador,
           Integer intensidadProductiva,
           Double superficie) {
           this.calificacion = calificacion;
           this.denominacion = denominacion;
           this.identificador = identificador;
           this.intensidadProductiva = intensidadProductiva;
           this.superficie = superficie;
    }


    /**
     * Gets the calificacion value for this CultivoOT.
     * 
     * @return calificacion
     */
    public String getCalificacion() {
        return calificacion;
    }


    /**
     * Sets the calificacion value for this CultivoOT.
     * 
     * @param calificacion
     */
    public void setCalificacion(java.lang.String calificacion) {
        this.calificacion = calificacion;
    }


    /**
     * Gets the denominacion value for this CultivoOT.
     * 
     * @return denominacion
     */
    public String getDenominacion() {
        return denominacion;
    }


    /**
     * Sets the denominacion value for this CultivoOT.
     * 
     * @param denominacion
     */
    public void setDenominacion(java.lang.String denominacion) {
        this.denominacion = denominacion;
    }


    /**
     * Gets the identificador value for this CultivoOT.
     * 
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this CultivoOT.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the intensidadProductiva value for this CultivoOT.
     * 
     * @return intensidadProductiva
     */
    public Integer getIntensidadProductiva() {
        return intensidadProductiva;
    }


    /**
     * Sets the intensidadProductiva value for this CultivoOT.
     * 
     * @param intensidadProductiva
     */
    public void setIntensidadProductiva(java.lang.Integer intensidadProductiva) {
        this.intensidadProductiva = intensidadProductiva;
    }


    /**
     * Gets the superficie value for this CultivoOT.
     * 
     * @return superficie
     */
    public Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CultivoOT.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Double superficie) {
        this.superficie = superficie;
    }
}
