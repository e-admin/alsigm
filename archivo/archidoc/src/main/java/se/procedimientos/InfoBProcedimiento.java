package se.procedimientos;

import java.io.Serializable;

/**
 * Interfaz para la información básica de un procedimiento.
 */
public interface InfoBProcedimiento extends Serializable {

	/**
	 * Devuelve el identificador del procedimiento.
	 * 
	 * @return Identificador del procedimiento.
	 */
	public String getId();

	/**
	 * Devuelve el código del procedimiento.
	 * 
	 * @return Código del procedimiento.
	 */
	public String getCodigo();

	/**
	 * Devuelve el nombre del procedimiento.
	 * 
	 * @return Nombre del procedimiento.
	 */
	public String getNombre();

	/**
	 * Devuelve el código del sistema productor. Si el procedimiento no es
	 * automatizado este valor será nulo.
	 * 
	 * @return Código del sistema productor.
	 */
	public String getCodSistProductor();

	/**
	 * Devuelve el nombre del sistema productor. Si el procedimiento no es
	 * automatizado este valor será nulo.
	 * 
	 * @return Nombre del sistema productor.
	 */
	public String getNombreSistProductor();

}
