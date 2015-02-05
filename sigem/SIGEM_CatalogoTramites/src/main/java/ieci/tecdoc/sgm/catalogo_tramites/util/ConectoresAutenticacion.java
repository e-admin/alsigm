package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Contenedor de conectores de autenticación.
 * 
 * @author IECISA
 *
 */
public class ConectoresAutenticacion implements Serializable
{
   private ArrayList conectoresAutenticacion;

   public ConectoresAutenticacion()
   {
	   conectoresAutenticacion = new ArrayList();
   }

   /**
    * Devuelve el número de conectores de autenticación de la colección
    * @return
    */
   public int count() {
      return conectoresAutenticacion.size();
   }

   /**
    * Devuelve el conector de autenticación que se encuentra 
    * en la posición indicada.
    * @param index Posición del conector de autenticación dentro de la colección.
    * @return ConectorAutenticacion Conector de autenticación.
    */
   public ConectorAutenticacion get(int index) {
      return (ConectorAutenticacion) conectoresAutenticacion.get(index);
   }

   /**
    * Añade un nuevo conector de autenticación a la colección
    * @param document Nuevo conector de autenticación a añadir.
    */
   public void add(ConectorAutenticacion conectorAutenticacion) {
	   conectoresAutenticacion.add(conectorAutenticacion);
   }
   
   /**
    * Devuelve una cadena XML con la información de los conectores de 
    * autenticación contenidos en la colección.
    * @param header Establece si el XML debe llevar cabecera.
    * @return String XML con la información.
    */   
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "Catalogo_ConectorAutenticacion";
      ConectorAutenticacion conectorAutenticacion;

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++) {
    	 conectorAutenticacion = get(i);
         bdr.addFragment(conectorAutenticacion.toXML(false));
      }

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Devuelve la información de los documentos contenidos en la colección.
    * @return String Cadena XML con la información de los documentos.
    */
   public String toString() {
      return toXML(false);
   }
}