package se.terceros;

import java.util.List;

import se.NotAvailableException;
import se.Parametrizable;
import se.terceros.exceptions.GestorTercerosException;

/**
 * Interfaz para la gestión de terceros.
 */
public interface GestorTerceros extends Parametrizable {

	/**
	 * Recupera la información de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Información del tercero.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public InfoTercero recuperarTercero(String idTercero)
			throws GestorTercerosException, NotAvailableException;

	/**
	 * Recupera la lista de terceros que tienen el valor valorAtrib como
	 * subtexto en el valor del atributo que se indica en tipoAtrib.
	 * <p>
	 * Los objetos de la lista tienen que implementar el interfaz
	 * {@link InfoTercero}
	 * </p>
	 * 
	 * @param tipoAtrib
	 *            Tipo de atributo ({@TipoAtributo}).
	 * @param valorAtrib
	 *            Valor del atributo.
	 * @return Lista de terceros.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarTerceros(short tipoAtrib, String valorAtrib)
			throws GestorTercerosException, NotAvailableException;

	/**
	 * Recupera la lista de terceros.
	 * <p>
	 * Los objetos de la lista tienen que implementar el interfaz
	 * {@link InfoTercero}
	 * </p>
	 * 
	 * @param nombre
	 *            Nombre del tercero.
	 * @param apellido1
	 *            Primer apellido del tercero.
	 * @param apellido2
	 *            Segundo apellido del tercero.
	 * @return Lista de terceros.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarTerceros(String nombre, String apellido1,
			String apellido2) throws GestorTercerosException,
			NotAvailableException;

}
