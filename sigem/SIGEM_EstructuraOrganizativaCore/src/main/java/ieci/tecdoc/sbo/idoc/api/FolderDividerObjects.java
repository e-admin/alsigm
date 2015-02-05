
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers;

/**
 * Esta clase encapsula un árbol de clasificadores para una carpeta.
 * 
 */
public final class FolderDividerObjects 
{
   /** Contiene la lógica de negocio del arbol */
   FolderTokenDividers m_divs;
   
   /**
    * Constructor
    * 
    * @param divs el objeto FolderTokenDividers
    */
   protected FolderDividerObjects(FolderTokenDividers divs)
   {
      m_divs = divs;    
   } 

   /**
    * Devuelve el numero de clasificadores de la lista
    * 
    * @return el numero de clasificadores de la lista
    */
   public int count()
   {
      return m_divs.count();
   }   
   
   /**
    * Devuelve el clasificador con el índice especificado  
    * 
    * @param index índice del clasificador
    * @return
    */
   public FolderDividerObject get(int index)
   {
      FolderDividerObject div = new FolderDividerObject(m_divs.get(index));
      
      return div;
   } 
   
      
} // class
