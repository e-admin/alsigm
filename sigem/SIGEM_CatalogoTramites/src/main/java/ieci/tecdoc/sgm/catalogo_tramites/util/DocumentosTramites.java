package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de documentos asociados a un trámite.
 * 
 * @author IECISA
 *
 */
public class DocumentosTramites implements Serializable {
   private ArrayList documents;

   public DocumentosTramites() {
      documents = new ArrayList();
   }

   /**
    * Devuelve el número de procedimientos de la colección.
    * @return int Número de procedimientos.
    */
   public int count() {
      return documents.size();
   }

   /**
    * Recupera el procedimiento de la posición indicada.
    * @param index Posición dentro de la colección.
    * @return ProcedureDocumento Documento de procedimiento.
    */
   public DocumentoTramite get(int index) {
      return (DocumentoTramite) documents.get(index);
   }

   /**
    * Añade un nuevo documento a la colección.
    * @param document Nuevo documento a añadir.
    */
   public void add(DocumentoTramite document) {
      documents.add(document);
   }

   /**
    * Devuelve una cadena XML con la información de los documentos de la colección.
    * @param header Establece si el XMl debe llevar cabecera.
    * @return String Cadena XMl con la información.
    */
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "Procedure_Documents";
      DocumentoTramite document;

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
    * Devuelve una cadena con la información de los documentos de la colección.
    * @return String Cadena XML con la información.
    */
   public String toString() {
      return toXML(false);
   }

}