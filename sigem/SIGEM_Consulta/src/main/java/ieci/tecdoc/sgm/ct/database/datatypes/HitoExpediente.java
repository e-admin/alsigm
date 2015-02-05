package ieci.tecdoc.sgm.ct.database.datatypes;

import java.util.Date;


/**
 * Interfaz de comportamiento de un objeto representativo 
 * de un Hito.
 * 
 * @author IECISA
 *
 */
public interface HitoExpediente {

	/**
	 * Devuelve el numero del expediente.
	 * @return String Numero del expediente.
	 */
	public abstract String getNumeroExpediente();


	/**
	 * Devuelve el Guid del hito.
	 * @return String Guid del hito.
	 */   
	public abstract String getGuid();



	/**
	 * Devuelve el codigo del hito.
	 * @return String Codigo del hito.
	 */   
	public abstract String getCodigo();
	
	/**
	 * Devuelve la fecha y hora del hito.
	 * @return Date Fecha del hito.
	 */   
	public abstract Date getFecha();

	
	/**
	 * Devuelve la descripcion del hito
	 * @return String Descripcion del hito.
	 */   
	public abstract String getDescripcion();


	/**
	 * Devuelve informacion auxiliar del hito
	 * como documento XML
	 * @return String Informacion auxiliar del hito.
	 */   
	public abstract String getInformacionAuxiliar();



	/**
	 * Establece un numero de expediente.
	 * @param numeroExpediente Numero de expediente. 
	 */	
	public abstract void setNumeroExpediente(String numeroExpediente);

	
	/**
	 * Establece el Guid del hito.
	 * @param guid Guid del hito.
	 */   
	public abstract void setGuid(String guid);

	
	/**
	 * Establece el codigo del hito.
	 * @return codigo Codigo del hito.
	 */   
	public abstract void setCodigo(String codigo);
	
	
	/**
	 * Establece la fecha del hito.
	 * @param fecha Fecha del hito.
	 */   
	public abstract void setFecha(Date fecha);

	
	/**
	 * Establece la descripcion del hito.
	 * @param descripcion descripcion del hito.
	 */   
	public abstract void setDescripcion(String descripcion);

	
	/**
	 * Establece le informacion auxiliar del expediente
	 * que vendra en formato XML
	 * @param informacionAuxiliar Informacion auxiliar del expediente. 
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