package ieci.tecdoc.sgm.core.services.certificacion;

import java.util.ArrayList;

/**
 * Contenedor de elementos mime.
 * 
 * @author IECISA
 *
 */
public class Certificaciones
{
   private ArrayList certificaciones;

   public Certificaciones()
   {
	   certificaciones = new ArrayList();
   }
   
   /**
    * Devuelve el número de certificaciones de la colección.
    * @return int Número de certificaciones de la colección.
    */   
   public int count()
   {
      return certificaciones.size();
   }
   
   /**
    * Devuelve la certificacion de la posición indicada dentro de la colección
    * @param index Posición de la certificacion a recuperar.
    * @return Certificacion Certificacion de la posicion indicada.
    */   
   public Certificacion get(int index)
   {
      return (Certificacion)certificaciones.get(index);
   }
   /**
    * Añade una nueva certificacion a la colección.
    * @param certificacion Nueva certificacion a añadir.
    */   
   public void add(Certificacion certificacion) 
   {
	   certificaciones.add(certificacion);
   }
    
}