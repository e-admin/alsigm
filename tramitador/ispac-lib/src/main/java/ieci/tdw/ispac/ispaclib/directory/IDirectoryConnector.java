package ieci.tdw.ispac.ispaclib.directory;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Interfaz para los gestores de directorio.
 * <p>
 * Los gestores de directorio se encargan de obtener la información de
 * usuarios, grupos de usuario y unidades organizativas.
 * </p>
 *
 */
public interface IDirectoryConnector {
	
	/**
	 * Realiza la autenticación del usuario.
	 * @param user Login del usuario.
	 * @param password Clave del usuario.
	 * @return Información del usuario autenticado.
	 * @throws ISPACException si ocurre algún error.
	 */
	IDirectoryEntry login(String user, String password) throws ISPACException;
	
	/**
	 * Obtiene la información del usuario.
	 * @param user Login del usuario.
	 * @return Información del usuario autenticado.
	 * @throws ISPACException si ocurre algún error.
	 */
	IDirectoryEntry getEntryFromUserName(String user) throws ISPACException;

	/**
	 * Obtiene la información de la unidad organizativa raíz del directorio.
	 * @return Información de la unidad organizativa.
	 * @throws ISPACException si ocurre algún error.
	 */
	IDirectoryEntry getEntryFromRoot() throws ISPACException;

	/**
	 * Obtiene la información del nodo del directorio (usuario, grupo o unidad 
	 * organizativa).
	 * @param uid UID del nodo del directorio.
	 * @return Información del nodo del directorio.
	 * @throws ISPACException si ocurre algún error.
	 */
	IDirectoryEntry getEntryFromUID(String uid) throws ISPACException;
	
	/**
	 * Obtiene la información de los miembros del nodo del directorio.
	 * @param uid UID del nodo del directorio.
	 * @return Conjunto de miembros ({@link IDirectoryEntry}).
	 * @throws ISPACException si ocurre algún error.
	 */
	Set getMembersFromUID(String uid) throws ISPACException;
	
	/**
	 * Obtiene la información de los padres del nodo del directorio.
	 * @param uid UID del nodo del directorio.
	 * @return Conjunto de nodos padre ({@link IDirectoryEntry}).
	 * @throws ISPACException si ocurre algún error.
	 */
	Set getAncestorsFromUID(String uid) throws ISPACException;
	
	/**
	 * Obtiene un conjunto con todos los grupos de usuarios.
	 * @return Grupos de usuarios ({@link IDirectoryEntry}).
	 * @throws ISPACException si ocurre algún error.
	 */
	Set getAllGroups() throws ISPACException;
	
	/**
	 * Obtiene las unidades organizativas hijas. 
	 * @param uid UID de la unidad organizativa padre.
	 * @return Unidades organizativas ({@link IDirectoryEntry}).
	 * @throws ISPACException si ocurre algún error.
	 */
	Set getOrgUnitsFromUID(String uid) throws ISPACException; 
	
	/**
	 * Obtiene los usuarios de una unidad organizativa.
	 * @param uid UID de la unidad organizativa.
	 * @return Usuarios ({@link IDirectoryEntry}).
	 * @throws ISPACException si ocurre algún error.
	 */
	Set getUsersFromUID( String uid) throws ISPACException;
	
	/**
	 * Obtiene el nombre del sistema de usuarios.
	 * @return Nombre del sistema de usuarios.
	 * @throws ISPACException si ocurre algún error.
	 */
	String getContextRoot() throws ISPACException;
	
	/**
	 * Obtiene las propiedades del nodo del directorio.
	 * @param uid UID del nodo del directorio.
	 * @return Propiedades del nodo del directorio.
	 * @throws ISPACException si ocurre algún error.
	 */
	LinkedHashMap getPropertiesFromUID(String uid) throws ISPACException;
	
	/**
	 * Obtiene la información de personas, grupos grupos, departamentos y dominios cuyo nombre
	 * largo y/o corto contiene el filtro de búsqueda
	 * @param name
	 * @para elementos Array con los elementos que se van a buscar, en caso de nulo se permite busca por todos
	 * @return
	 * @throws ISPACException
	 */
	Set getEntryFromName(String name,String []elementos) throws ISPACException;
	
	
}
