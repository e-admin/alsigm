package ieci.tecdoc.sgm.rde.datatypes;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de elementos mime.
 * 
 * @author IECISA
 *
 */
public class TiposMime implements Serializable
{
   private ArrayList mimeTypes;

   public TiposMime()
   {
      mimeTypes = new ArrayList();
   }
   
   /**
    * Devuelve el número de mime types de la colección.
    * @return int Número de mime types de la colección.
    */   
   public int count()
   {
      return mimeTypes.size();
   }
   
   /**
    * Devuelve el mime type de la posición indicada dentro de la colección
    * @param index Posición del mime type a recuperar.
    * @return MimeType MimeType asociado a registro.
    */   
   public TipoMime get(int index)
   {
      return (TipoMime)mimeTypes.get(index);
   }
   /**
    * Añade un nuevo mime type a la colección.
    * @param mimeType Nuevo mime type a añadir.
    */   
   public void add(TipoMime mimeType) 
   {
      mimeTypes.add(mimeType);
   }
   
   /**
    * Devuelve una cadena XML con los datos de los mime types de la colección.
    * @param headline Indica si el XML debe llevar cabecera.
    * @return String Cadena XML con los datos de los mime types.
    */   
   public String toXML(boolean headline) 
   {
      XmlTextBuilder bdr;
      String tagName = "MimeTypes";
      TipoMime mimeType;
      
      bdr = new XmlTextBuilder();
      if (headline)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         mimeType = get(i);

         bdr.addFragment(mimeType.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con los datos de los mime types asociados a registro de la colección.
    * @return String Cadena XML con los datos de los mime types.
    */
    public String toString()
    {
      return toXML(false);
   }
    
}