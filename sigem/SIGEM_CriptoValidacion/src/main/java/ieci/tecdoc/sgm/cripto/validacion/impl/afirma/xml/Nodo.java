package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml;

/**
  $Id: Nodo.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $

  nodo de un documento xml (atributo o elemento)
*/

public abstract class Nodo {
    String s;

    public Nodo() {
    }

    public Nodo(String s) {
	set(s);
    }

    /**
     * devuelve el valor del nodo
     */
    public String get() {
	return s;
    }

    /*
     * establece el valor del nodo
     *
     * @param s valor de este nodo
     */
    public void set(String s) {
	this.s=s;
    }

}
