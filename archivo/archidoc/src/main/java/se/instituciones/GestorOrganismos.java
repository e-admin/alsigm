package se.instituciones;

import java.util.List;

import se.NotAvailableException;
import se.Parametrizable;
import se.instituciones.exceptions.GestorOrganismosException;

/**
 * Interfaz para los conectores con Sistemas Gestores de Organización.
 */
public interface GestorOrganismos extends Parametrizable {

	/**
	 * Recupera la lista de órganos que son hijos del órgano cuyo identificador
	 * es idOrgPadre. Para los órganos que no tienen padre se pasa null.
	 * 
	 * @param idOrgPadre
	 *            Identificador del órgano padre.
	 * @return Lista de órganos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link InfoOrgano}.
	 *         </p>
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarHijosDeOrgano(String idOrgPadre)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Recupera la lista de órganos que dependen del órgano, hasta el nivel
	 * jerárquico especificado en numNiveles; si numNiveles tiene el valor 0, se
	 * recuperan todos los órganos dependientes hasta el último nivel.
	 * 
	 * @param idOrg
	 *            Identificador del órgano.
	 * @param numNiveles
	 *            Número de niveles.
	 * @return Lista de órganos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link InfoOrgano}.
	 *         </p>
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarOrganosDependientes(String idOrg, int numNiveles)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Recupera la lista de órganos antecesores al órgano, hasta el nivel
	 * jerárquico especificado en numNiveles; si numNiveles tiene el valor 0, se
	 * recuperan todos los órganos antecesores hasta el primer nivel.
	 * 
	 * @param idOrg
	 *            Identificador del órgano.
	 * @param numNiveles
	 *            Número de niveles.
	 * @return Lista de órganos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link InfoOrgano}.
	 *         </p>
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarOrganosAntecesores(String idOrg, int numNiveles)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Recupera la lista de órganos que tienen el valor de param como subtexto
	 * dentro de su nombre.
	 * 
	 * @param param
	 *            Texto en el nombre del órgano.
	 * @return Lista de órganos.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link InfoOrgano}.
	 *         </p>
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarOrganos(String param)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Recupera la información básica de un órgano para el atributo cuyo tipo se
	 * pasa en tipoAtrib.
	 * 
	 * @param tipoAtrib
	 *            Tipo de atributo ({@link TipoAtributo}).
	 * @param valorAtrib
	 *            Valor del atributo.
	 * @return Información básica de un órgano.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public InfoOrgano recuperarOrgano(short tipoAtrib, String valorAtrib)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Recupera la información básica del órgano al que pertenece un usuario
	 * cuyo identificador es idUsr.
	 * 
	 * @param idUsr
	 *            Identificador de usuario.
	 * @return Información básica de un órgano.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public InfoOrgano recuperarOrganodeUsuario(String idUsr)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Recupera una lista de identificadores de usuarios que pertenecen a los
	 * órganos cuyos identificadores se pasan en el parámetro idOrgs.
	 * 
	 * @param idOrgs
	 *            Lista de identificadores de órganos.
	 * @return Lista de identificadores de usuarios.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarUsuariosDeOrganos(List idOrgs)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Recupera la lista de instituciones con su información básica.
	 * 
	 * @return Lista de instituciones.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link se.instituciones.InfoOrgano}.
	 *         </p>
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarInstitucionesProductoras()
			throws GestorOrganismosException, NotAvailableException;

}
