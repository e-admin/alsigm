package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;


/**
 * Información de un procedimiento.
 */
public class InfoOcupacion extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	/** Espacio total del repositorio (en bytes). */
	protected long espacioTotal = 0;

	/** Espacio ocupado en el repositorio (en bytes). */
	protected long espacioOcupado = 0;

	/** Número de ficheros contenidos en el repositorio. */
	protected long numeroFicheros = 0;

	/**
	 * Constructor.
	 */
	public InfoOcupacion() {
		super();
	}

	/**
	 * Obtiene el espacio ocupado en el repositorio (en bytes).
	 * @return Espacio ocupado en el repositorio (en bytes).
	 */
	public long getEspacioOcupado() {
		return espacioOcupado;
	}

	/**
	 * Establece el espacio ocupado en el repositorio (en bytes).
	 * @param espacioOcupado Espacio ocupado en el repositorio (en bytes).
	 */
	public void setEspacioOcupado(long espacioOcupado) {
		this.espacioOcupado = espacioOcupado;
	}

	/**
	 * Obtiene el espacio total del repositorio (en bytes).
	 * @return Espacio total del repositorio (en bytes).
	 */
	public long getEspacioTotal() {
		return espacioTotal;
	}

	/**
	 * Establece el espacio total del repositorio (en bytes).
	 * @param espacioTotal Espacio total del repositorio (en bytes).
	 */
	public void setEspacioTotal(long espacioTotal) {
		this.espacioTotal = espacioTotal;
	}

	/**
	 * Obtiene el número de ficheros contenidos en el repositorio.
	 * @return Número de ficheros contenidos en el repositorio.
	 */
	public long getNumeroFicheros() {
		return numeroFicheros;
	}

	/**
	 * Establece el número de ficheros contenidos en el repositorio.
	 * @param numeroFicheros Número de ficheros contenidos en el repositorio.
	 */
	public void setNumeroFicheros(long numeroFicheros) {
		this.numeroFicheros = numeroFicheros;
	}

}