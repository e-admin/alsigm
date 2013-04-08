package ieci.tecdoc.sgm.core.services.catalogo;

import java.util.ArrayList;


/**
 * Contendor de documentos.
 * 
 * @author IECISA
 *
 */
public class Documentos
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
 
}