
package ieci.tecdoc.sbo.idoc.folder.base;

/**
 * Definición de los flags de estado de los nodos de un clasificador
 * También es utilizado para marcar el estado de los campos de una carpeta
 * 
 * @author 
 *
 */
public final class FolderEditFlag
{
   
   // **************************************************************************
       
   /** 
    * Ningún estado en el nodo, estado por defecto 
    * o el campo no ha sido modificado  
    */
   public static final int NONE   = 0;
   /** Nodo nuevo o nuevo valor (campos multivalor) */
   public static final int NEW    = 1;  
   /** Nodo modificado o el campo ha sido modificado */
   public static final int UPDATE = 2;
   /** Nodo eliminado o valor eliminado (campos multivalor) */
   public static final int REMOVE = 3;   
   
   // **************************************************************************
 
   private FolderEditFlag()
   {
   }
   
} // class
