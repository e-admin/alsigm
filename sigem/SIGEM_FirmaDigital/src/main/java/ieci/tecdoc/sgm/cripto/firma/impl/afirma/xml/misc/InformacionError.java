package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc;

/* $Id: InformacionError.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $ */

public class InformacionError {

    static {
	new InformacionError();
	new Codigo("");
	new Descripcion("");
	new Detalle("");
    }

    private Codigo codigo;
    private Descripcion descripcion;
    private Detalle detalle;

    public Codigo getCodigo() {
	return codigo;
    }

    public void setCodigo(Codigo codigo) {
	this.codigo=codigo;
    }

    public Descripcion getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(Descripcion descripcion) {
	this.descripcion=descripcion;
    }

    public Detalle getDetalle() {
	return detalle;
    }

    public void setDetalle(Detalle detalle) {
	this.detalle=detalle;
    }

}
