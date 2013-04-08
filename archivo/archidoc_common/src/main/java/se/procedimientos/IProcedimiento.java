package se.procedimientos;

import java.io.Serializable;
import java.util.List;

/**
 * Interfaz para la información de un procedimiento.
 */
public interface IProcedimiento extends Serializable {
	public static final int AUTOMATICO = 1;
	public static final int NO_AUTOMATICO = 2;
	public static final int EXTERNO = 3;

	/**
	 * Devuelve la información básica de un procedimiento.
	 *
	 * @return información básica de un procedimiento.
	 */
	public InfoBProcedimiento getInformacionBasica();

	/**
	 * Devuelve el objeto del procedimiento.
	 *
	 * @return Objeto del procedimiento.
	 */
	public String getObjeto();

	/**
	 * Devuelve los trámites de un procedimiento.
	 *
	 * @return Trámites de un procedimiento.
	 */
	public String getTramites();

	/**
	 * Devuelve la normativa de un procedimiento.
	 *
	 * @return Normativa de un procedimiento.
	 */
	public String getNormativa();

	/**
	 * Devuelve los documentos básicos de un procedimiento.
	 *
	 * @return Documentos básicos de un procedimiento.
	 */
	public String getDocumentosBasicos();

	/**
	 * Devuelve la lista de órganos productores del procedimiento.
	 *
	 * @return Lista de órganos productores del procedimiento.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link IOrganoProductor}.
	 *         </p>
	 */
	public List getOrganosProductores();

}
