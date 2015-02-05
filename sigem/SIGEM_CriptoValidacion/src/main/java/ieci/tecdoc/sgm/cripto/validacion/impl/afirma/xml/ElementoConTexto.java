package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
  $Id: ElementoConTexto.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $

  elemento con texto de un documento xml. los elementos que no contienen texto no requieren extender de aqui
*/

/*
 * no tocar esta clase si no se sabe que se esta haciendo
 */
public abstract class ElementoConTexto extends Nodo implements Converter {
    
    // elementos que han sido registrados, necesario para evitar llamadas recursivas en el constructor
    static java.util.Set registrados=new java.util.HashSet();


    public ElementoConTexto(String s) {
	super(s);
	if(!registrados.contains(this.getClass())) {
	    registrados.add(this.getClass());
	    //Gestion.getXStream().useAttributeFor(this.getClass());
	    try {
		// registro de este elemento
		try {
		    Gestion.getXStream().registerConverter((Converter)this.getClass().newInstance());
		} catch(InstantiationException ie) {
		    java.lang.reflect.Constructor c=this.getClass().getConstructor(new Class[]{String.class});
		    Gestion.getXStream().registerConverter((Converter)c.newInstance(new String[]{""}));
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * convertir el objeto pasado a xml, uso interno
     *
     * @param obj objeto del que se quiere extraer el valor
     */
    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
	writer.setValue(((ElementoConTexto)obj).get());
    }

    /**
     * crear un objeto nuevo de la clase que lo llama con el valor contenido en reader, uso interno
     */
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
	try { 
	    java.lang.reflect.Constructor c=this.getClass().getConstructor(new Class[]{String.class});
	    return c.newInstance(new Object[]{reader.getValue()});
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * comprueba la equidad de la clase, uso interno
     * @param tipo la clase a comparar
     */
    public boolean canConvert(Class clazz) {
	return this.getClass().equals(clazz);
    }

}
