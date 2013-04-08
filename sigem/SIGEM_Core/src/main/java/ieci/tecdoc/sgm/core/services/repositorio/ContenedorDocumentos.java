package ieci.tecdoc.sgm.core.services.repositorio;

import java.util.ArrayList;

/**
 * Contenedor de documentos asociados a un registro.
 * 
 * @author IECISA
 *
 */
public class ContenedorDocumentos
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
    
}
    
