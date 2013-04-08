package ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml;

import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
  $Id: Atributo.java,v 1.1 2007/09/11 10:39:16 cnavas Exp $

  atributo de un documento xml
 */

/*
 * no tocar esta clase si no se sabe que se esta haciendo
 */
public abstract class Atributo extends Nodo implements SingleValueConverter {

    // atributos que han sido registrados, necesario para evitar llamadas recursivas en el constructor
    static java.util.Set registrados=new java.util.HashSet();


    public Atributo(String s) {
	super(s);
	if(!registrados.contains(this.getClass())) {
	    registrados.add(this.getClass());
	    Gestion.getXStream().useAttributeFor(this.getClass());
	    try {
		// registro de este atributo
		try {
		    Gestion.getXStream().registerConverter((SingleValueConverter)this.getClass().newInstance());
		} catch(InstantiationException ie) {
		    java.lang.reflect.Constructor c=this.getClass().getConstructor(new Class[]{String.class});
		    Gestion.getXStream().registerConverter((SingleValueConverter)c.newInstance(new String[]{""}));
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /*
     * @param obj clase atributo del que se extraera el valor, uso interno
     */
    public String toString(Object obj) {
	return ((Atributo)obj).get();
    }

    /*
     * @param name valor del atributo creado, uso interno
     */
    public Object fromString(String name) {
	try {
	    java.lang.reflect.Constructor c=this.getClass().getConstructor(new Class[]{String.class});
	    return c.newInstance(new Object[]{name});
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /*
     * @param tipo comprueba la equidad de la clase, uso interno
     */
    public boolean canConvert(Class tipo) {
	return this.getClass().equals(tipo);
    }

}

