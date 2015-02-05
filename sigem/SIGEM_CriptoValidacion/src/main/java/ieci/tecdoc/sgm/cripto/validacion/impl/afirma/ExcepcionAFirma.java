package ieci.tecdoc.sgm.cripto.validacion.impl.afirma;

/**
 * $Id: ExcepcionAFirma.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $
 *
 * excepcion que representa un error remoto en @firma
 */

public class ExcepcionAFirma extends Exception {

    private String codigoError;
    private String excepcion;

    ExcepcionAFirma(String desc, String c, String ex) {
	super(desc);
	setCodigoError(c);
	setExcepcion(ex);
    }

    public String getCodigoError() {
	return codigoError;
    }

    public void setCodigoError(String c) {
	codigoError=c;
    }

    public String getExcepcion() {
	return excepcion;
    }

    public void setExcepcion(String e) {
	excepcion=e;
    }
}
