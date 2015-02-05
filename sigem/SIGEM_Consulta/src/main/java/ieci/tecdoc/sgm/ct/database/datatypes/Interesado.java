package ieci.tecdoc.sgm.ct.database.datatypes;


/**
 * Interfaz de comportamiento de un objeto representativo 
 * del interesado de un Expediente.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */

public interface Interesado {

	/**
	 * Devuelve el numero del expediente.
	 * @return String Numero del expediente.
	 */
	public abstract String getNumeroExpediente();


	/**
	 * Devuelve el NIF del interesado.
	 * @return String NIF del interesado.
	 */   
	public abstract String getNIF();


	/**
	 * Devuelve el nombre del interesado.
	 * @return String Nombre del interesado.
	 */   
	public abstract String getNombre();


	/**
	 * Devuelve si el interesado es el Interesado Principal
	 * del expediente. Puede tomar dos valores: S/N.
	 * @return String Si el interesado es el Interesado Principal o no (S/N).
	 */   
	public abstract String getPrincipal ();


	/**
	 * Devuelve informacion auxiliar del interesado
	 * como documento XML
	 * @return String Informacion auxiliar del interesado.
	 */   
	public abstract String getInformacionAuxiliar();


	/**
	 * Establece un numero de expediente.
	 * @param numeroExpediente Numero de expediente. 
	 */	
	public abstract void setNumeroExpediente(String numeroExpediente);

	
	/**
	 * Establece el NIF del interesado.
	 * @param NIF NIF del interesado.
	 */   
	public abstract void setNIF(String NIF);

	
	
	/**
	 * Establece el nombre del interesado.
	 * @param nombre Nombre del interesado.
	 */   
	public abstract void setNombre(String nombre);

	/**
	 * Establece si el interesado es el Interesado Principal
	 * del expediente. Puede tomar dos valores: S/N.
	 * @param principal Si el interesado es el Interesado Principal o no (S/N).
	 */   
	public abstract void setPrincipal(String principal);



	/**
	 * Establece le informacion auxiliar del interesado
	 * que vendra en formato XML
	 * @param informacionAuxiliar Informacion auxiliar del interesado. 
	 */   
	public abstract void setInformacionAuxiliar(String informacionAuxiliar);
	

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