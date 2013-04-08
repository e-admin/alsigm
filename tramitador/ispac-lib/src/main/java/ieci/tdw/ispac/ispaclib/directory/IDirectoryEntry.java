package ieci.tdw.ispac.ispaclib.directory;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.Serializable;
import java.util.Set;

/**
 * Interfaz para la información de los usuarios autenticados.
 *
 */
public interface IDirectoryEntry extends Serializable {

	// =======================================================================
	// Constantes para los tipos de elementos
	// =======================================================================
	public static final int ET_UNDEFINED = -1;
	public static final int ET_PERSON = 1;
	public static final int ET_GROUP = 3;
	public static final int ET_UNIT = 2;
	public static final int ET_ORGANIZATION = 4;
	public static final int ET_DOMAIN = 5;
	// =======================================================================

	
	/**
	 * Obtiene el nombre del elemento.
	 * @return Nombre del elemento.
	 * @throws ISPACException si ocurre algún error.
	 */
	String getName() throws ISPACException;
	
	
	/**
	 * Obtiene el identificador único dentro de la organización del elemento.
	 * @return Identificador del elemento.
	 * @throws ISPACException si ocurre algún error.
	 */
	String getUID() throws ISPACException;
	
	/**
	 * Obtiene el tipo de entrada del elemento.
	 * @return Tipo de entrada
	 * @throws ISPACException si ocurre algún error.
	 */
	int getEntryType() throws ISPACException;
	
	/**
	 * Obtiene el padre del elemento.
	 * @return Padre del elemento.
	 * @throws ISPACException si ocurre algún error.
	 */
	IDirectoryEntry getParentEntry() throws ISPACException;
	
	/**
	 * Obtiene los grupos a los que pertenece el usuario.
	 * @return Grupos del usuario ({@link IDirectoryEntry}).
	 * @throws ISPACException si ocurre algún error.
	 */
	Set getGroupEntries() throws ISPACException;
	
	/**
	 * Obtiene el nombre largo del elemento.
	 * @return Nombre largo del elemento.
	 * @throws ISPACException si ocurre algún error.
	 */
	String getLongName() throws ISPACException;
	
}
