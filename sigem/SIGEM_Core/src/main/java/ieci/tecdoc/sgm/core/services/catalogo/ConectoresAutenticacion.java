package ieci.tecdoc.sgm.core.services.catalogo;

import java.util.ArrayList;


/**
 * Contenedor de conectores de autenticación.
 * 
 * @author IECISA
 *
 */
public class ConectoresAutenticacion
{
   private ArrayList conectoresAutenticacion;

   public ConectoresAutenticacion()
   {
	   conectoresAutenticacion = new ArrayList();
   }

   /**
    * Devuelve el número de conectores de autenticación de la colección
    * @return
    */
   public int count() {
      return conectoresAutenticacion.size();
   }

   /**
    * Devuelve el conector de autenticación que se encuentra 
    * en la posición indicada.
    * @param index Posición del conector de autenticación dentro de la colección.
    * @return ConectorAutenticacion Conector de autenticación.
    */
   public ConectorAutenticacion get(int index) {
      return (ConectorAutenticacion) conectoresAutenticacion.get(index);
   }

   /**
    * Añade un nuevo conector de autenticación a la colección
    * @param document Nuevo conector de autenticación a añadir.
    */
   public void add(ConectorAutenticacion conectorAutenticacion) {
	   conectoresAutenticacion.add(conectorAutenticacion);
   }
   
}