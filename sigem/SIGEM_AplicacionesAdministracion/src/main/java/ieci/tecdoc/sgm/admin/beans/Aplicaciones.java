package ieci.tecdoc.sgm.admin.beans;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.admin.interfaces.Aplicacion;


import java.io.Serializable;
import java.util.ArrayList;

public class Aplicaciones implements Serializable{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList aplicaciones;

	   public Aplicaciones() {
		   aplicaciones = new ArrayList();
	   }
	   
	   /**
	    * Devuelve el número de obejtos aplicación de la colección.
	    * @return int Número de objetos aplicación de la colección.
	    */   
	   public int count(){
	      return aplicaciones.size();
	   }
	   
	   /**
	    * Devuelve el objeto aplicación de la posición indicada dentro de la colección
	    * @param index Posición del objeto aplicación a recuperar.
	    * @return Aplicacion Aplicacion asociada al registro.
	    */   
	   public Aplicacion get(int index){
	      return (Aplicacion)aplicaciones.get(index);
	   }
	   /**
	    * Añade una nueva aplicacion a la colección.
	    * @param mimeType Nuevo mime type a añadir.
	    */   
	   public void add(Aplicacion aplicacion) {
		   aplicaciones.add(aplicacion);
	   }
	   
	   /**
	    * Devuelve una cadena XML con los datos de las aplicaciones de la colección.
	    * @param headline Indica si el XML debe llevar cabecera.
	    * @return String Cadena XML con los datos de los mime types.
	    */   
	   public String toXML(boolean headline) {
	      XmlTextBuilder bdr;
	      String tagName = "Aplicaciones";
	      Aplicacion mimeType;
	      
	      bdr = new XmlTextBuilder();
	      if (headline)
	         bdr.setStandardHeader();

	      bdr.addOpeningTag(tagName);

	      for (int i = 0; i < count(); i++){
	         mimeType = get(i);

	         bdr.addFragment(mimeType.toXML(false));
	      }

	      bdr.addClosingTag(tagName);
	      
	      return bdr.getText();
	   }

	   /**
	    * Devuelve una cadena con los datos de las aplicaciones.
	    * @return String Cadena tipo XML con los datos de las aplicaciones.
	    */
	    public String toString(){
	      return toXML(false);
	   }
	 

}
