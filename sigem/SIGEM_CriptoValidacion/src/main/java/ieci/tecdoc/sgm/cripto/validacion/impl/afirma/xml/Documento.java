package ieci.tecdoc.sgm.cripto.validacion.impl.afirma.xml;

/**
   $Id: Documento.java,v 1.1 2007/10/01 18:33:38 cnavas Exp $

   clase base de todos los documentos xml
*/

abstract public class Documento {

    // tipos de documentos registrados
    static java.util.Set registrados=new java.util.HashSet();

    public Documento() {
	if(!registrados.contains(this.getClass())) {
	    registrados.add(this.getClass());
	    int idx=this.getClass().getName().lastIndexOf('.');
	    String nombre=this.getClass().getName().substring(idx+1);
	    //StringBuffer sb=new StringBuffer(nombre);
	    //sb=sb.substring(idx+2);
	    // sb.insert()
	    // System.out.println("nombre: "+nombre);
	    Gestion.getXStream().alias(nombre, this.getClass());
	}
    }
}

