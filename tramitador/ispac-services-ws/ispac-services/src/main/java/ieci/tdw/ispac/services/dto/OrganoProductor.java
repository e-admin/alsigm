package ieci.tdw.ispac.services.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Información de un órgano productor.
 */
public class OrganoProductor implements Serializable {

	private static final long serialVersionUID = 7525315187235918605L;

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
	 * Constructor.
	 * @param id Identificador del órgano.
	 * @param inicioProduccion Fecha desde la que el órgano es productor.
	 */
	public OrganoProductor(String id, Date inicioProduccion) {
		this();
		setId(id);
		setInicioProduccion(inicioProduccion);
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
