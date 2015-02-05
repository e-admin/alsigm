
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLinks;

/**
 * Esta clase encapsula una colección de enlaces a carpetas y 
 * métodos para obtener cada uno de sus enlaces 
 */

public final class FolderFdrLinkObjects 
{
   
   /**
    * Colección de enlaces a carpetas
    */
   FolderTokenFdrLinks m_fdrLinks;
   
   
   /**
    * Constructor
    * @param fdrLinks colección de enlaces a carpetas
    */
   protected FolderFdrLinkObjects(FolderTokenFdrLinks fdrLinks)
   {
      m_fdrLinks = fdrLinks;    
   } 

   /**
    * Devuelve el número de enlaces a carpetas
    * @return
    */
   public int count()
   {
      return m_fdrLinks.count();
   }   
   
   /**
    * Devuelve el enlace i-ésimo de la colección 
    * @param index índice del enlace dentro de la colección
    * @return referencia a un objeto de tipo FolderFdrLinkObject que contiene
    * información sobre un enlace a una carpeta
    */
   public FolderFdrLinkObject get(int index)
   {
      FolderFdrLinkObject fdrLink = new FolderFdrLinkObject(m_fdrLinks.get(index));
      
      return fdrLink;
   } 
   
      
} // class
