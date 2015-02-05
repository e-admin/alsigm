package ieci.tecdoc.sgm.certificacion.datatype;

/**
 * Interfaz de comportamiento de un elemento certificacion.
 * 
 * @author IECISA
 *
 */
public interface Certificacion 
{

	/**
	 * Devuelve el identifcador del documento.
	 * @return String Identificador del documento.
	 */
   public abstract String getIdFichero();
   
   /**
    * Devuelve el identificador de usuario.
    * @return String Identificador de usuario.
    */   
   public abstract String getIdUsuario();
   
   /**
    * Establece el identificador del documento
    * @param idFichero Identificador del documento.
    */	
   public abstract void setIdFichero(String idFichero);
   
   /**
    * Establece el identificador del usuario.
    * @param idUsuario Identificador del usuario.
    */   
   public abstract void setIdUsuario(String idUsuario);

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