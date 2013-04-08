package ieci.tecdoc.sgm.core.services.repositorio;

import java.util.ArrayList;

/**
 * Contenedor de documentos temporales asociados a un registro.
 * 
 * @author IECISA
 *
 */
public class DocumentosTemporales
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
 
}
    
