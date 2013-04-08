package ieci.tecdoc.sgm.core.services.telematico;

import java.util.ArrayList;

/**
 * Contenedor de documentos asociados a un registro.
 * 
 * @author IECISA
 *
 */
public class RegistroDocumentos
{
   private ArrayList registryDocuments;

   /**
    * Constructor de la clase RegistoDocumentos
    *
    */
   public RegistroDocumentos()
   {
     registryDocuments = new ArrayList();
   }
   
   /**
    * Devuelve el número de documentos de la colección.
    * @return int Número de documentos de la colección.
    */   
   public int count()
   {
      return registryDocuments.size();
   }
   
   /**
    * Devuelve el documento de la posición indicada dentro de la colección
    * @param index Posición del documento a recuperar.
    * @return Document Documento asociado a registro.
    */   
   public RegistroDocumento get(int index)
   {
      return (RegistroDocumento)registryDocuments.get(index);
   }
   /**
    * Añade un nuevo documento a la colección.
    * @param document Nuevo documento a añadir.
    */   
   public void add(RegistroDocumento document) 
   {
     registryDocuments.add(document);
   }
  
}
    
