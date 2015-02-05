
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;

/**
 * Representa los identificadores que se han podido modificar de un token de documento al
 * crear el documento ó modificarlo
 * /se utiliza de forma interna)
 */

public final class FolderDocUpdIds
{
   
   /**
    * Identificador del fichero asociado al documento
    */
   private int  m_fileId;
   
   /**
    * identificador del fichero de anotaciones
    */
   private int  m_annId;
  
   
   public FolderDocUpdIds(int fileId, int annId)
   {
      m_fileId = fileId;
      m_annId  = annId;
   }
   
   
   public int getFileId()
   {
      return m_fileId;
   }
   
   public int getAnnId()
   {
      return m_annId;
   }  
   
} // class
