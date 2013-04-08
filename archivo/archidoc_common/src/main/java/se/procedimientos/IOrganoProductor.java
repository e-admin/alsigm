package se.procedimientos;

import java.io.Serializable;
import java.util.Date;

/**
 * Interfaz para la información de un órgano productor.
 */
public interface IOrganoProductor extends Serializable {

	/**
	 * Devuelve el identificador del órgano.
	 *
	 * @return Identificador del órgano.
	 */
	public String getId();

	/**
	 * Devuelve la fecha desde la que el órgano es productor.
	 *
	 * @return Fecha.
	 */
	public Date getInicioProduccion();

}
