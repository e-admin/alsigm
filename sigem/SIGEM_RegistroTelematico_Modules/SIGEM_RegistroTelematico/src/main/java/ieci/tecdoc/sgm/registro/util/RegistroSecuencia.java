package ieci.tecdoc.sgm.registro.util;

/**
 * Interfaz de comportamiento de un número de secuencia de registro.
 * 
 * @author IECISA
 *
 */
public interface RegistroSecuencia 
{

	/**
	 * Devuelve el año del número de secuencia.
	 * @return String Año del número de secuencia.
	 */
   public abstract String getYear();
   
   /**
    * Devuelve la etiqueta del número de secuencia.
    * @return String Etiqueta del número de secuencia.
    */   
   public abstract String getLabel();
   
   /**
    * Número de secuencia
    * @return int Número de secuencia.
    */   
   public abstract int getSequence();
	
   /**
    * Establece el año del número de secuencia.
    * @param year Año del número de secuencia.
    */	
   public abstract void setYear(String year);
   
   /**
    * Establece la etiqueta del número de secuencia.
    * @param label Etiqueta del número de secuencia.
    */   
   public abstract void setLabel(String label);
   
   /**
    * Establece el número de secuencia.
    * @param sequence Número de secuencia.
    */   
   public abstract void setSequence(int sequence);

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public abstract String toXML(boolean header);

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public abstract String toString();

}