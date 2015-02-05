
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDivider;

/**
 * Contiene la información referente a un clasificador de carpeta. 
 */
public final class FolderDividerObject 
{
   /** Atributo que encapsula la información referente a  un clasificador */
   FolderTokenDivider m_div;
   
   /**
    * Constructor
    * 
    * @param div la información del clasificador
    */
   protected FolderDividerObject(FolderTokenDivider div)
   {
      m_div = div;   
   }
  
   /**
    * Devuelve el identificador del clasificador
    * 
    * @return el identificador del clasificador
    */
   public int getId()
   {
      return m_div.getId();
   }
   
   /**
    * Devuelve el nombre del clasificador
    * 
    * @return el nombre del clasificador
    */
   public String getName()
   {
      return m_div.getName();
   }
   
   /**
    * Devuelve el identificador del clasificador padre
    * @return el identificador del clasificador padre
    */
   public int getParentId()
   {
      return m_div.getParentId();
   }

      
   
} // class
