/**
 * ConstruccionOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Construccion extends RetornoServicio {
    private String codigoUso;
    private String escalera;
    private String identificador;
    private String planta;
    private String puerta;
    private Double superficieTotal;

    public Construccion() {
    }

    public Construccion(
           String codigoUso,
           String escalera,
           String identificador,
           String planta,
           String puerta,
           Double superficieTotal) {
           this.codigoUso = codigoUso;
           this.escalera = escalera;
           this.identificador = identificador;
           this.planta = planta;
           this.puerta = puerta;
           this.superficieTotal = superficieTotal;
    }


    /**
     * Gets the codigoUso value for this ConstruccionOT.
     * 
     * @return codigoUso
     */
    public java.lang.String getCodigoUso() {
        return codigoUso;
    }


    /**
     * Sets the codigoUso value for this ConstruccionOT.
     * 
     * @param codigoUso
     */
    public void setCodigoUso(java.lang.String codigoUso) {
        this.codigoUso = codigoUso;
    }


    /**
     * Gets the escalera value for this ConstruccionOT.
     * 
     * @return escalera
     */
    public java.lang.String getEscalera() {
        return escalera;
    }


    /**
     * Sets the escalera value for this ConstruccionOT.
     * 
     * @param escalera
     */
    public void setEscalera(java.lang.String escalera) {
        this.escalera = escalera;
    }


    /**
     * Gets the identificador value for this ConstruccionOT.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this ConstruccionOT.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }


    /**
     * Gets the planta value for this ConstruccionOT.
     * 
     * @return planta
     */
    public java.lang.String getPlanta() {
        return planta;
    }


    /**
     * Sets the planta value for this ConstruccionOT.
     * 
     * @param planta
     */
    public void setPlanta(java.lang.String planta) {
        this.planta = planta;
    }


    /**
     * Gets the puerta value for this ConstruccionOT.
     * 
     * @return puerta
     */
    public java.lang.String getPuerta() {
        return puerta;
    }


    /**
     * Sets the puerta value for this ConstruccionOT.
     * 
     * @param puerta
     */
    public void setPuerta(java.lang.String puerta) {
        this.puerta = puerta;
    }


    /**
     * Gets the superficieTotal value for this ConstruccionOT.
     * 
     * @return superficieTotal
     */
    public java.lang.Double getSuperficieTotal() {
        return superficieTotal;
    }


    /**
     * Sets the superficieTotal value for this ConstruccionOT.
     * 
     * @param superficieTotal
     */
    public void setSuperficieTotal(java.lang.Double superficieTotal) {
        this.superficieTotal = superficieTotal;
    }
}
