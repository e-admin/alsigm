package ieci.tecdoc.sgm.cripto.validacion.impl.afirma;

/**
 * $Id: Excepcion.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $
 * 
 * esta excepcion es lanzada por 'hayErrorExcepcion'
 */

public class Excepcion extends Exception {

    public Excepcion(String s) {
	super(s);
    }

    private String descripcion;
    private String detalle;
    private int codigo;

    public void setDescripcion(String d) {
	descripcion=d;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public void setDetalle(String d) {
	detalle=d;
    }

    public String getDetalle() {
	return detalle;
    }

    public void setCodigo(int c) {
	codigo=c;
    }

    public int getCodigo() {
	return codigo;
    }

}
