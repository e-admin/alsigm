
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLink;

/**
 *  Esta clase encapsula la información básica de un enlace a una carpeta
 */

public final class FolderFdrLinkObject 
{
   /**
    * Información básica de un enlace a una carpeta
    */
   FolderTokenFdrLink m_fdrLink;
   
   /**
    * Constructor
    * @param fdrLink - enlace a un objeto de tipo FolderTokenFdrLink que contiene
    * la información básica de un enlace a una carpeta
    */
   protected FolderFdrLinkObject(FolderTokenFdrLink fdrLink)
   {
      m_fdrLink = fdrLink;   
   }
  
   /**
    * Devuelve el identificador de un enlace a una carpeta
    * @return identificador de un enlace a una carpeta
    */
   public int getId()
   {
      return m_fdrLink.getId();
   }
   
   /**
    * Devuelve el nombre de un enlace a una carpeta
    * @return nombre de un enlace a una carpeta
    */
   public String getName()
   {
      return m_fdrLink.getName();
   }
   
   /**
    * Devuelve el identificador del clasificador padre de este 
    * enlace 
    * 
    * @return identificador del clasificador padre 
    */
   public int getParentId()
   {
      return m_fdrLink.getParentId();
   }

   /**
    * Devuelve el identificador del archivador al que pertenece la carpeta enlace
    * @return identificador del archivador al que pertenece la carpeta enlace
    */
   public int getSrvArchId()
   {
      return m_fdrLink.getSrvArchId();
   }
   
   /**
    * Devuelve el nombre del archivador al que pertenece la carpeta enlace
    * @return nombre del archivador al que pertenece la carpeta enlace
    */
   public String getSrvArchName()
   {
      return m_fdrLink.getSrvArchName();
   }
   
   /**
    * Devuelve el identificador de la carpeta enlazada
    * @return identificador de la carpeta enlazada
    */
   public int getSrvFdrId()
   {
      return m_fdrLink.getSrvFdrId();
   }  
   
} // class
