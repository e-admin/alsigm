package ieci.tecdoc.sgm.tram.ws.server.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Información de un órgano productor.
 */
public class OrganoProductor implements Serializable {

	/** Identificador del órgano productor. */
	private String id = null;

	/** Fecha desde la que el órgano es productor. */
	private Date inicioProduccion = null;

	/**
	 * Constructor.
	 */
	public OrganoProductor() {
		super();
	}

	/**
	 * Obtiene el identificador del órgano. 
	 * @return Identificador del órgano.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del órgano.
	 * @param id Identificador del órgano.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene la fecha desde la que el órgano es productor.
	 * @return Fecha desde la que el órgano es productor.
	 */
	public Date getInicioProduccion() {
		return inicioProduccion;
	}

	/**
	 * Establece la fecha desde la que el órgano es productor.
	 * @param inicioProduccion Fecha desde la que el órgano es productor.
	 */
	public void setInicioProduccion(Date inicioProduccion) {
		this.inicioProduccion = inicioProduccion;
	}
}
