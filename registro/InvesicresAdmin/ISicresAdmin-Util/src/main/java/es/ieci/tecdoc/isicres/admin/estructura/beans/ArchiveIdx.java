
package es.ieci.tecdoc.isicres.admin.estructura.beans;

/**
 * Bean de índice. 
 */


import java.util.ArrayList;

public class ArchiveIdx {
   private int        id;
   private String     name;   
   private boolean    isUnique;
   private ArrayList  fldsId;
   
   public ArchiveIdx(){
      this.id         = Integer.MAX_VALUE - 1;;
      this.name       = null;
      this.isUnique   = false;   
      this.fldsId     = new ArrayList();
      
   }
   
   protected ArchiveIdx(int id, String name, boolean isUnique, ArrayList fldsId){
      this.id       = id;
      this.name     = name;
      this.isUnique = isUnique;
      this.fldsId   = fldsId;
   }
   
   /**
    * Obtiene el identificador del índice
    * 
    * @return El identificador
    */
   public int getId(){
      return (id);
   }
   
   /**
    * Obtiene el nombre del índice
    * 
    * @return El nombre
    */
   public String getName(){
      return (name);
   }
   
   /**
    * Obtiene si el índice es único ó no
    * 
    * @return (true/false)
    */
   public boolean isUnique(){
      return (isUnique);
   }
   
   /**
    * Obtiene una lista de enteros (Integer) con los identificadores
    * de los campos que componen el índice
    * 
    * @return Lista de identificadores de campos
    */
   public ArrayList getFldsId(){
      return (fldsId);
   }
   
   /**
    * Establece una lista de enteros (Integer) con los identificadores
    * de los campos que componene el índice
    * 
    * @param idsFld Lista de identificadores de campos
    */
   public void setFldsId(ArrayList fldsId){
      fldsId.clear();
      
      for (int i = 0; i < fldsId.size(); i++){
         fldsId.add(fldsId.get(i));
      }
   }
   

   
}

