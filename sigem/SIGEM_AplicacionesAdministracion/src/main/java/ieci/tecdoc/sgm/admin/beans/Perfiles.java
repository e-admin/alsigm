package ieci.tecdoc.sgm.admin.beans;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.admin.interfaces.Perfil;

import java.io.Serializable;
import java.util.ArrayList;

public class Perfiles implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3147339398592040574L;
	private ArrayList perfiles;

	   public Perfiles() {
		   perfiles = new ArrayList();
	   }
	   
	   /**
	    * Devuelve el número de mime types de la colección.
	    * @return int Número de mime types de la colección.
	    */   
	   public int count(){
	      return perfiles.size();
	   }
	   
	   /**
	    * Devuelve el mime type de la posición indicada dentro de la colección
	    * @param index Posición del mime type a recuperar.
	    * @return MimeType MimeType asociado a registro.
	    */   
	   public Perfil get(int index){
	      return (Perfil)perfiles.get(index);
	   }
	   /**
	    * Añade un nuevo mime type a la colección.
	    * @param mimeType Nuevo mime type a añadir.
	    */   
	   public void add(Perfil perfil) {
		   perfiles.add(perfil);
	   }
	   
	   /**
	    * Devuelve una cadena XML con los datos de los mime types de la colección.
	    * @param headline Indica si el XML debe llevar cabecera.
	    * @return String Cadena XML con los datos de los mime types.
	    */   
	   public String toXML(boolean headline) {
	      XmlTextBuilder bdr;
	      String tagName = "Perfíles";
	      Perfil mimeType;
	      
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
	    * Devuelve una cadena con los datos de los mime types asociados al registro de la colección.
	    * @return String Cadena XML con los datos de los mime types.
	    */
	    public String toString(){
	      return toXML(false);
	   }

}
