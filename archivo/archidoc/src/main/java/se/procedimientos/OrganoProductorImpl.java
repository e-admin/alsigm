package se.procedimientos;

import java.util.Date;

/**
 * Organo productor de un procedimiento
 */
public class OrganoProductorImpl implements IOrganoProductor {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String id;
	Date inicioProduccion;

	public OrganoProductorImpl() {
	}

	protected OrganoProductorImpl(String id, Date fechaInicio) {
		super();
		this.id = id;
		this.inicioProduccion = fechaInicio;
	}

	public Date getInicioProduccion() {
		return inicioProduccion;
	}

	public void setInicioProduccion(Date fechaInicio) {
		this.inicioProduccion = fechaInicio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}