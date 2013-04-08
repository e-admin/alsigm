package ieci.tecdoc.sgm.registro.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de documentos adjuntos a la solicitud de registro.
 * 
 * @author IECISA
 *
 */
public class PeticionDocumentos implements Serializable 
{
   private ArrayList documents;

   public PeticionDocumentos()
   {
      documents = new ArrayList();
   }
   
   /**
    * Devuelve el número de documentos contenidos en la colección.
    * @return int Número de documentos de la colección.
    */   
   public int count()
   {
      return documents.size();
   }
   
   /**
    * Devuelve el documento de la posición indicada.
    * @param index Posición del documento dentro de la colección.
    * @return RequestDocument Documento solicitado.
    */   
   public PeticionDocumento get(int index)
   {
      return (PeticionDocumento)documents.get(index);
   }
   
   /**
    * Añade un nuevo documento adjunto a la solicitud de registro a la colección.
    * @param document Nuevo documento a añadir a la colección.
    */   
   public void add(PeticionDocumento document) 
   {
      documents.add(document);
   }
   
   /**
    * Devuelve una cadena XMl con los datos de los documentos adjuntos a la solicitud de
    * registro contenidos en la colección.
    * @param headline Indica si el XMl debe lleva cabecera.
    * @return String Cadena XML con los datos de los documentos.
    */   
   public String toXML(boolean headline) 
   {
      XmlTextBuilder bdr;
      String tagName = "Documentos_Solicitud";
      String tagName1 = "Documento";
      PeticionDocumento document;
      
      bdr = new XmlTextBuilder();
      if (headline)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         document = get(i);

         bdr.addOpeningTag(tagName1);
         bdr.addFragment(document.toXML(false));
         bdr.addClosingTag(tagName1);
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con los datos de los documentos de la colección.
    * @return String Cadena XML con los datos de los documentos de la colección.
    */
	public String toString()
	{
      return toXML(false);
   }
    
}