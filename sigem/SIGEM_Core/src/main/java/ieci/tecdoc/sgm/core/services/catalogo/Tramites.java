package ieci.tecdoc.sgm.core.services.catalogo;

import java.util.ArrayList;


/**
 * Colección de procedimientos.
 * 
 * @author IECISA
 *
 */
public class Tramites 
{
   private ArrayList procedures;

   public Tramites()
   {
      procedures = new ArrayList();
   }
   
   /**
    * Devuelve el número de procedimientos de la colección.
    * @return int Número de procedimientos.
    */   
   public int count()
   {
      return procedures.size();
   }
   
   /**
    * Recupera el procedimiento de la posición indicada.
    * @param index Posición del procedimiento a recuperar.
    * @return Procedure Procedimiento.
    */   
   public Tramite get(int index)
   {
      return (Tramite)procedures.get(index);
   }
   
   /**
    * Añade un nuevo procedimiento a la colección.
    * @param procedure Nuevo procedimiento a añadir.
    */   
   public void add(Tramite procedure) 
   {
      procedures.add(procedure);
   }
   
}