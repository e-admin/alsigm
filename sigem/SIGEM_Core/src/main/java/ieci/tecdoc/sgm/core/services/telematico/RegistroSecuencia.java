package ieci.tecdoc.sgm.core.services.telematico;

/**
 * Bean con las propiedades de un número de secuencia de registro.
 * 
 * @author IECISA
 *
 */
public class RegistroSecuencia 
{
   public RegistroSecuencia()
   {
   }
   
	/**
	 * Devuelve el año del número de secuencia.
	 * @return String Año del número de secuencia.
	 */      
   public String getYear()
   {
      return year;
   }
	
   /**
    * Devuelve la etiqueta del número de secuencia.
    * @return String Etiqueta del número de secuencia.
    */		   
   public String getLabel()
   {
      return label;
   }
		
   /**
    * Número de secuencia
    * @return int Número de secuencia.
    */   		   
   public int getSequence()
   {
      return sequence;
   }
	
   /**
    * Establece el año del número de secuencia.
    * @param year Año del número de secuencia.
    */   	
   public void setYear(String year)
   {
      this.year = year;
   }
   
   /**
    * Establece la etiqueta del número de secuencia.
    * @param label Etiqueta del número de secuencia.
    */      
   public void setLabel(String label)
   {
      this.label = label;
   }

   /**
    * Establece el número de secuencia.
    * @param sequence Número de secuencia.
    */   
   public void setSequence(int sequence)
   {
      this.sequence = sequence;
   }

   protected String year;
   protected String label;
   protected int sequence;

   
}