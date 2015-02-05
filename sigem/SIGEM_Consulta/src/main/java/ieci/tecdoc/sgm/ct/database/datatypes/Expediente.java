package ieci.tecdoc.sgm.ct.database.datatypes;

import java.util.Date;


/**
 * Interfaz de comportamiento de un número de secuencia de registro.
 * 
 * @author IECISA
 *
 */
public interface Expediente {

	/**
	 * Devuelve el numero del expediente.
	 * @return String Numero único del expediente.
	 */
	public abstract String getNumero();


	/**
	 * Devuelve el procedimiento del expediente.
	 * @return String Procedimiento del expediente.
	 */   
	public abstract String getProcedimiento();


	/**
	 * Devuelve la fecha y hora de inicio del expediente.
	 * @return Date Fecha inicio del expediente.
	 */   
	public abstract Date getFechaInicio();


	/**
	 * Devuelve el numero de registro que inicia el expediente.
	 * @return String Numero de registro que inicia el expediente.
	 */   
	public abstract String getNumeroRegistro();


	/**
	 * Devuelve fecha y hora del registro anterior
	 * @return Date Fecha del registro anterior.
	 */   
	public abstract Date getFechaRegistro();


	/**
	 * Devuelve informacion auxiliar del expediente
	 * como documento XML
	 * @return String Informacion auxiliar del expediente.
	 */   
	public abstract String getInformacionAuxiliar();


	/**
	 * Devuelve la posibilidad de realizar aportaciones
	 * nuevas al expediente como un boleano
	 * @return String Posibilidad de aportar datos nuevos al expediente.
	 */   
	public abstract String getAportacion();


	/**
	 * Devuelve el codigo de presentacion, que identifica el formato
	 * de presentacion del expediente.
	 * @return String Codigo de presentacion.
	 */   
	public abstract String getCodigoPresentacion();
	
	
	/**
	 * Devuelve el codigo de estado del expediente
	 * @return String Código de estado.
	 */   
	public abstract String getEstado();


	/**
	 * Establece un numero de expediente.
	 * @param numero Numero de expediente. 
	 */	
	public abstract void setNumero(String numero);

	
	/**
	 * Establece el procedimiento del expediente.
	 * @param procedimiento Procedimiento del expediente.
	 */   
	public abstract void setProcedimiento(String procedimiento);

	
	/**
	 * Establece la fecha y hora de inicio del Expediente.
	 * @param fechaInicio Fecha de inicio del Expediente.
	 */   
	public abstract void setFechaInicio(Date fechaInicio);

	
	/**
	 * Establece el numero de registro que inicia el expediente.
	 * @param numeroRegistro Numero de registro que inicia el expediente.
	 */   
	public abstract void setNumeroRegistro(String numeroRegistro);

	
	/**
	 * Establece la fecha y hora del registro anterior
	 * @param fechaRegistro Fecha del registro anterior
	 */   
	public abstract void setFechaRegistro(Date fechaRegistro);

	/**
	 * Establece le informacion auxiliar del expediente
	 * que vendra en formato XML
	 * @param informacionAuxiliar Informacion auxiliar del expediente. 
	 */   
	public abstract void setInformacionAuxiliar(String informacionAuxiliar);

	
	/**
	 * Establece la posibilidad de realizar aportaciones
	 * nuevas al expediente como un boleano
	 * @param aportacion Posibilidad de aportar datos nuevos al expediente.
	 */   

	public abstract void setAportacion(String aportacion);


	/**
	 * Establece el codigo de presentacion, que identifica el formato
	 * de presentacion del expediente.
	 * @param codigoPresentacion Codigo de presentacion.
	 */   

	public abstract void setCodigoPresentacion(String codigoPresentacion);

	
	/**
	 * Establece el codigo de estado del expediente.
	 * @param estado Codigo de estado.
	 */   

	public abstract void setEstado(String estado);
	
	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	
	public abstract String getPagos();

	
	public abstract void setPagos(String pagos);
	
	
	public abstract String toXML(boolean header);

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	
	
	public abstract String toString();

}