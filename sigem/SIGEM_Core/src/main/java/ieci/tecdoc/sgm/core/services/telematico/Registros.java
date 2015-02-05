
package ieci.tecdoc.sgm.core.services.telematico;

import java.util.ArrayList;

/**
 * Contenedor de beans de registro.
 */
public class Registros {
    
   private ArrayList regs;

   /**
    * Constructor de la clase Registros
    *
    */
   public Registros()
   {
      regs = new ArrayList();
   }
   
   /**
    * Devuelve el número de registro de la colección.
    * @return int Número de registros de la colección.
    */   
   public int count()
   {
      return regs.size();
   }
   
   /**
    * Devuelve el registro de la posición indicada.
    * @param index Posición del registro en la colección.
    * @return Registry Registro.
    */   
   public Registro get(int index)
   {
      return (Registro)regs.get(index);
   }
   
   /**
    * Añade un nuevo registro a la colección.
    * @param registry Nuevo registro a añadir.
    */   
   public void add(Registro registry) 
   {
   	  regs.add(registry);
   }

}
