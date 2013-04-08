/**
 * ProvinciaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.core.services.geolocalizacion;

public class Provincia  {

	private int codigoINE;
    private String comunidadAutonoma;
    private String nombreCoOficial;
    private String nombreOficial;

    public Provincia() {
    }

    public Provincia(int codigoINE, String comunidadAutonoma, String nombreCoOficial, String nombreOficial) {
           this.codigoINE = codigoINE;
           this.comunidadAutonoma = comunidadAutonoma;
           this.nombreCoOficial = nombreCoOficial;
           this.nombreOficial = nombreOficial;
    }

    public int getCodigoINE() {
        return codigoINE;
    }

    public void setCodigoINE(int codigoINE) {
        this.codigoINE = codigoINE;
    }

    public String getComunidadAutonoma() {
        return comunidadAutonoma;
    }

    public void setComunidadAutonoma(String comunidadAutonoma) {
        this.comunidadAutonoma = comunidadAutonoma;
    }

    public String getNombreCoOficial() {
        return nombreCoOficial;
    }

    public void setNombreCoOficial(String nombreCoOficial) {
        this.nombreCoOficial = nombreCoOficial;
    }

    public String getNombreOficial() {
        return nombreOficial;
    }

    public void setNombreOficial(String nombreOficial) {
        this.nombreOficial = nombreOficial;
    }
}
