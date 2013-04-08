/**
 * BienInmuebleOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.core.services.catastro;

public class BienInmueble {
    private String claseBienInmueble;
    private String claseUso;
    private Localizacion direccionLocalizacion;
    private Construcciones lstConstrucciones;
    private Cultivos lstCultivos;
    private String referencia_catastral;
    private Double superficie;

    public BienInmueble() {
    }

    public BienInmueble(
           String claseBienInmueble,
           String claseUso,
           Localizacion direccionLocalizacion,
           Construcciones lstConstrucciones,
           Cultivos lstCultivos,
           String referencia_catastral,
           Double superficie) {
           this.claseBienInmueble = claseBienInmueble;
           this.claseUso = claseUso;
           this.direccionLocalizacion = direccionLocalizacion;
           this.lstConstrucciones = lstConstrucciones;
           this.lstCultivos = lstCultivos;
           this.referencia_catastral = referencia_catastral;
           this.superficie = superficie;
    }


    /**
     * Gets the claseBienInmueble value for this BienInmuebleOT.
     * 
     * @return claseBienInmueble
     */
    public String getClaseBienInmueble() {
        return claseBienInmueble;
    }


    /**
     * Sets the claseBienInmueble value for this BienInmuebleOT.
     * 
     * @param claseBienInmueble
     */
    public void setClaseBienInmueble(String claseBienInmueble) {
        this.claseBienInmueble = claseBienInmueble;
    }


    /**
     * Gets the claseUso value for this BienInmuebleOT.
     * 
     * @return claseUso
     */
    public String getClaseUso() {
        return claseUso;
    }


    /**
     * Sets the claseUso value for this BienInmuebleOT.
     * 
     * @param claseUso
     */
    public void setClaseUso(String claseUso) {
        this.claseUso = claseUso;
    }


    /**
     * Gets the direccionLocalizacion value for this BienInmuebleOT.
     * 
     * @return direccionLocalizacion
     */
    public Localizacion getDireccionLocalizacion() {
        return direccionLocalizacion;
    }


    /**
     * Sets the direccionLocalizacion value for this BienInmuebleOT.
     * 
     * @param direccionLocalizacion
     */
    public void setDireccionLocalizacion(Localizacion direccionLocalizacion) {
        this.direccionLocalizacion = direccionLocalizacion;
    }


    /**
     * Gets the lstConstrucciones value for this BienInmuebleOT.
     * 
     * @return lstConstrucciones
     */
    public Construcciones getLstConstrucciones() {
        return lstConstrucciones;
    }


    /**
     * Sets the lstConstrucciones value for this BienInmuebleOT.
     * 
     * @param lstConstrucciones
     */
    public void setLstConstrucciones(Construcciones lstConstrucciones) {
        this.lstConstrucciones = lstConstrucciones;
    }


    /**
     * Gets the lstCultivos value for this BienInmuebleOT.
     * 
     * @return lstCultivos
     */
    public Cultivos getLstCultivos() {
        return lstCultivos;
    }


    /**
     * Sets the lstCultivos value for this BienInmuebleOT.
     * 
     * @param lstCultivos
     */
    public void setLstCultivos(Cultivos lstCultivos) {
        this.lstCultivos = lstCultivos;
    }


    /**
     * Gets the referencia_catastral value for this BienInmuebleOT.
     * 
     * @return referencia_catastral
     */
    public String getReferencia_catastral() {
        return referencia_catastral;
    }


    /**
     * Sets the referencia_catastral value for this BienInmuebleOT.
     * 
     * @param referencia_catastral
     */
    public void setReferencia_catastral(String referencia_catastral) {
        this.referencia_catastral = referencia_catastral;
    }


    /**
     * Gets the superficie value for this BienInmuebleOT.
     * 
     * @return superficie
     */
    public Double getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this BienInmuebleOT.
     * 
     * @param superficie
     */
    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }
}
