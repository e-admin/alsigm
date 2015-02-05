package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Colección de procedimientos.
 * 
 * @author IECISA
 *
 */
public class Tramites implements Serializable
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
   
   /**
    * Devuelve una cadena XML con la información de los procedimientos de la colección.
    * @param headline Indica si el XML debe llevar cabecera.
    * @return String Cadena XML
    */   
   public String toXML(boolean headline) 
   {
      XmlTextBuilder bdr;
      String tagName = "Procedures";
      Tramite procedure;
      
      bdr = new XmlTextBuilder();
      if (headline)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
         procedure = get(i);

         bdr.addFragment(procedure.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con la información de los procedimientos de la colección.
    * @return String Cadena XML con la información.
    */
	public String toString()
	{
      return toXML(false);
   }
   
}