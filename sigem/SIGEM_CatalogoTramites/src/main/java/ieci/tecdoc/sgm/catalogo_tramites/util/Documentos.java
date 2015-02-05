package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Contendor de documentos.
 * 
 * @author IECISA
 *
 */
public class Documentos implements Serializable
{
   private ArrayList documents;

   public Documentos()
   {
      documents = new ArrayList();
   }

   /**
    * Devuelve el número de docuentos de la colección
    * @return
    */
   public int count() {
      return documents.size();
   }

   /**
    * Devuelve el documento que se encuentra en la posición indicada.
    * @param index Posición del documento dentro de la colección.
    * @return DocumentExt Documento.
    */
   public DocumentoExtendido get(int index) {
      return (DocumentoExtendido) documents.get(index);
   }

   /**
    * Añade un nuevo documento a la colección
    * @param document Nuevo documento a añadir.
    */
   public void add(DocumentoExtendido document) {
      documents.add(document);
   }
   
   /**
    * Devuelve una cadena XML con la información de los documentos contenidos en la colección.
    * @param header Establece si el XML debe llevar cabecera.
    * @return String XML con la información.
    */   
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "Catalogo_Documentos";
      DocumentoExtendido document;

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++) {
         document = get(i);
         bdr.addFragment(document.toXML(false));
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