package ieci.tecdoc.sgm.rde.datatypes;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de documentos asociados a un registro.
 * 
 * @author IECISA
 *
 */
public class ContenedorDocumentos implements Serializable
{
   private ArrayList documents;

   public ContenedorDocumentos()
   {
      documents = new ArrayList();
   }
   
   /**
    * Devuelve el número de documentos de la colección.
    * @return int Número de documentos de la colección.
    */   
   public int count()
   {
      return documents.size();
   }
   
   /**
    * Devuelve el documento de la posición indicada dentro de la colección
    * @param index Posición del documento a recuperar.
    * @return Document Documento asociado a registro.
    */   
   public ContenedorDocumento get(int index)
   {
      return (ContenedorDocumento)documents.get(index);
   }
   /**
    * Añade un nuevo documento a la colección.
    * @param document Nuevo documento a añadir.
    */   
   public void add(ContenedorDocumento document) 
   {
      documents.add(document);
   }
   
   /**
    * Devuelve una cadena XML con los datos de los documentos de la colección.
    * @param headline Indica si el XML debe llevar cabecera.
    * @return String Cadena XML con los datos de los documentos.
    */   
   public String toXML(boolean headline) 
   {
      XmlTextBuilder bdr;
      String tagName = "Documents";
      ContenedorDocumento document;
      
      bdr = new XmlTextBuilder();
      if (headline)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         document = get(i);

         bdr.addFragment(document.toXML(false));
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
    
