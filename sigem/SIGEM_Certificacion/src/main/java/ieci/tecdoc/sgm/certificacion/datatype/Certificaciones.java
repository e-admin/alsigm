package ieci.tecdoc.sgm.certificacion.datatype;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de elementos mime.
 * 
 * @author IECISA
 *
 */
public class Certificaciones implements Serializable
{
   private ArrayList certificaciones;

   public Certificaciones()
   {
	   certificaciones = new ArrayList();
   }
   
   /**
    * Devuelve el número de certificaciones de la colección.
    * @return int Número de certificaciones de la colección.
    */   
   public int count()
   {
      return certificaciones.size();
   }
   
   /**
    * Devuelve la certificacion de la posición indicada dentro de la colección
    * @param index Posición de la certificacion a recuperar.
    * @return Certificacion Certificacion de la posicion indicada.
    */   
   public Certificacion get(int index)
   {
      return (Certificacion)certificaciones.get(index);
   }
   /**
    * Añade una nueva certificacion a la colección.
    * @param certificacion Nueva certificacion a añadir.
    */   
   public void add(Certificacion certificacion) 
   {
	   certificaciones.add(certificacion);
   }
   
   /**
    * Devuelve una cadena XML con los datos de las certificaciones de la colección.
    * @param headline Indica si el XML debe llevar cabecera.
    * @return String Cadena XML con los datos de los mime types.
    */   
   public String toXML(boolean headline) 
   {
      XmlTextBuilder bdr;
      String tagName = "Certificaciones";
      Certificacion certificacion;
      
      bdr = new XmlTextBuilder();
      if (headline)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
    	  certificacion = get(i);

         bdr.addFragment(certificacion.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con los datos de las certificaciones asociados a registro de la colección.
    * @return String Cadena XML con los datos de las certificaciones.
    */
    public String toString()
    {
      return toXML(false);
   }
    
}