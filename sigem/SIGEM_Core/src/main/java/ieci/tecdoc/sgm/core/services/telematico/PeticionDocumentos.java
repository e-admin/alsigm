package ieci.tecdoc.sgm.core.services.telematico;

import java.util.ArrayList;

/**
 * Contenedor de documentos adjuntos a la solicitud de registro.
 * 
 * @author IECISA
 *
 */
public class PeticionDocumentos
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
    
}