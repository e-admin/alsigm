package ieci.tecdoc.sgm.ct.database.datatypes;

/**
 * Interfaz de comportamiento de un objeto representativo 
 * del Fichero de un Hito.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */
public interface FicheroHito {

	/**
	 * Devuelve el Guid del Hito.
	 * @return String Guid del Hito.
	 */
	public abstract String getGuidHito();


	/**
	 * Devuelve el Guid del Fichero.
	 * @return String Guid del Fichero.
	 */   
	public abstract String getGuid();


	/**
	 * Devuelve el titulo. Para presentacion.
	 * @return String Presentacion.
	 */   
	public abstract String getTitulo();

	/**
	 * Establece un Guid de un Hito para el Fichero.
	 * @param guidHito Guid de un Hito. 
	 */	
	public abstract void setGuidHito(String guidHito);

	
	/**
	 * Establece el guid de un Fichero.
	 * @param guid Guid de un Fichero.
	 */   
	public abstract void setGuid(String guid);

	
	
	/**
	 * Establece el titulo de un fichero para presentacion.
	 * @param titulo Titulo de un fichero.
	 */   
	public abstract void setTitulo(String titulo);

	/**
	 * Establece si el interesado es el Interesado Principal
	 * del expediente. Puede tomar dos valores: S/N.
	 * @param principal. Si el interesado es el Interesado Principal o no (S/N).
	 */

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