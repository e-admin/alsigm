package ieci.tecdoc.sgm.cripto.firma.impl.afirma;

/**
 * $Id: ExcepcionAFirma.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $
 *
 * excepcion que representa un error remoto en @firma
 */

public class ExcepcionAFirma extends Exception {

    private String codigoError;
    private String excepcion;

    public ExcepcionAFirma(String desc, String c, String ex) {
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
