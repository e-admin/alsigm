package se.instituciones;

import java.io.Serializable;

/**
 * Interfaz para los contenedores de información de órganos.
 */
public interface InfoOrgano extends Serializable {

	/**
	 * Obtiene el identificador del órgano.
	 *
	 * @return identificador del órgano.
	 */
	public String getId();

	/**
	 * Obtiene el nombre del órgano.
	 *
	 * @return nombre del órgano.
	 */
	public String getNombre();

	/**
	 * Obtiene el código del órgano.
	 *
	 * @return código del órgano.
	 */
	public String getCodigo();

	/**
	 * Obtiene el nivel jerárquico al que pertenece el órgano.
	 *
	 * @return nivel jerárquico.
	 */
	public int getNivel();

	/**
	 * Obtiene el identificador del órgano padre al que pertenece el órgano.
	 *
	 * @return identificador del órgano.
	 */
	public String getIdPadre();
}
