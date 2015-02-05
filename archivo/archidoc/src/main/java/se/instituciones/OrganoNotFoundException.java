package se.instituciones;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción que se produce cuando un sistema gestor de organización dice no
 * disponer de la información de un órgano que supuestamente es gestionado por
 * él.
 */
public class OrganoNotFoundException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}