/**
 * TipoViaOT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class TipoVia extends RetornoServicio{

	private String descripcion;
    private String patron;

    public TipoVia() {
    }

    public TipoVia(String descripcion, String patron) {
           this.descripcion = descripcion;
           this.patron = patron;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

}
