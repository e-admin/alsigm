package ieci.tecdoc.sgm.rde.datatypes;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de documentos temporales asociados a un registro.
 * 
 * @author IECISA
 *
 */
public class DocumentosTemporales implements Serializable
{
   private ArrayList documentTmps;

   public DocumentosTemporales()
   {
     documentTmps = new ArrayList();
   }
   
   /**
    * Devuelve el número de documentos de la colección.
    * @return int Número de documentos de la colección.
    */   
   public int count()
   {
      return documentTmps.size();
   }
   
   /**
    * Devuelve el documento de la posición indicada dentro de la colección
    * @param index Posición del documento a recuperar.
    * @return Document Documento asociado a registro.
    */   
   public DocumentoTemporal get(int index)
   {
      return (DocumentoTemporal)documentTmps.get(index);
   }
   /**
    * Añade un nuevo documento a la colección.
    * @param document Nuevo documento a añadir.
    */   
   public void add(DocumentoTemporal documentTmp) 
   {
     documentTmps.add(documentTmp);
   }
   
   /**
    * Devuelve una cadena XML con los datos de los documentos de la colección.
    * @param headline Indica si el XML debe llevar cabecera.
    * @return String Cadena XML con los datos de los documentos.
    */   
   public String toXML(boolean headline) 
   {
      XmlTextBuilder bdr;
      String tagName = "DocumentTmps";
      DocumentoTemporal documentTmp;
      
      bdr = new XmlTextBuilder();
      if (headline)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         documentTmp = get(i);

         bdr.addFragment(documentTmp.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con los datos de los documentos asociados a registro de la colección.
    * @return String Cadena XML con los datos de los documentos.
    */
    public String toString()
    {
      return toXML(false);
    }
    
}
    
