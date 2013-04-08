
package ieci.tecdoc.sgm.registro.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de beans de registro.
 */
public class Registros implements Serializable {
    
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
   
   /**
    * Devuelve una cadena XML con los datos de los registros de la colección.
    * @param headline Indica si el XML debe llevar cabecera.
    * @return String XML con los datos de los registros.
    */   
   public String toXML(boolean headline) 
   {
      XmlTextBuilder bdr;
      String tagName = "Registries";
      Registro registry;
      
      bdr = new XmlTextBuilder();
      if (headline)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         registry = get(i);

         bdr.addFragment(registry.toXML(false));
      }

      bdr.addClosingTag(tagName);      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con los datos de los registros de la colección.
    * @return String Cadena XML con los datos de los registros.
    */
	public String toString()
	{
      return toXML(false);
   }

}
