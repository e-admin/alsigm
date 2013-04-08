package es.ieci.tecdoc.archidoc.organization.connector;

import java.util.List;
import java.util.Properties;

import es.ieci.tecdoc.archidoc.organization.exception.OrganizationException;
import es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano;

/**
 * Interface del conector de organizacion
 * @author Iecisa
 * @version $Revision: 6586 $
 *
 */
public interface OrganizationConnector {

	/**
     * Inicializa con los parametros de configuracion.
     *
     * @param params Parametros de configuracion.
     * @throws OrganizationException
     */
    void initialize(final Properties params) throws OrganizationException;

	/**
	 * Recupera la lista de organos que son hijos del organo cuyo identificador es idOrgPadre.
	 * Para los organos que no tienen padre se pasa null.
	 *
	 * @param idOrgPadre Identificador del organo padre.
	 * @return Lista de organos.
	 * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
	 * @throws OrganizationException
	 */
	List recuperarHijosDeOrgano(final String idOrgPadre)
			throws OrganizationException;

	/**
	 * Recupera la lista de instituciones con su informacion basica.
	 *
	 * @return Lista de instituciones.
	 * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
	 * @throws OrganizationException
	 */
	List recuperarInstitucionesProductoras()
			throws OrganizationException;

	/**
	 * Recupera la informacion basica de un organo para el atributo cuyo tipo se pasa en tipoAtrib.
	 *
	 * @param tipoAtrib Tipo de atributo.
	 * @param valorAtrib Valor del atributo.
	 * @return Informacion basica de un organo ({@link OrganizationOrgano})
	 * @throws OrganizationException
	 */
	OrganizationOrgano recuperarOrgano(final int tipoAtrib,
			final String valorAtrib) throws OrganizationException;

	/**
	 * Recupera la informacion basica del organo al que pertenece un usuario cuyo identificador es idUsr.
	 *
	 * @param idUsr Identificador de usuario.
	 * @return Informacion basica de un organo ({@link OrganizationOrgano}).
	 * @throws OrganizationException
	 */
	OrganizationOrgano recuperarOrganodeUsuario(final String idUsr)
			throws OrganizationException;

	/**
	 * Recupera la lista de organos que tienen el valor de param como subtexto dentro de su nombre.
	 *
	 * @param param Texto en el nombre del organo.
	 * @return Lista de organos.
	 * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
	 * @throws OrganizationException
	 */
	List recuperarOrganos(final String param)
			throws OrganizationException;

	/**
	 * Recupera la lista de organos antecesores al organo, hasta el nivel jerarquico especificado en numNiveles;
	 * si numNiveles tiene el valor 0, se recuperan todos los organos antecesores hasta el primer nivel.
	 *
	 * @param idOrg Identificador del organo.
	 * @param numNiveles Numero de niveles.
	 * @return Lista de organos antecesores.
	 * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
	 * @throws OrganizationException
	 */
	List recuperarOrganosAntecesores(final String idOrg,
			final int numNiveles) throws OrganizationException;

	/**
	 * Recupera la lista de organos que dependen del organo, hasta el nivel jerarquico especificado en numNiveles;
	 * si numNiveles tiene el valor 0, se recuperan todos los organos dependientes hasta el ultimo nivel.
	 *
	 * @param idOrg Identificador del organo.
	 * @param numNiveles Numero de niveles.
	 * @return Lista de organos dependientes.
	 * 		<p>Los objetos de la lista implementan el interface {@link OrganizationOrgano}.</p>
	 * @throws OrganizationException
	 */
	List recuperarOrganosDependientes(final String idOrg,
			final int numNiveles) throws OrganizationException;

	/**
	 * Recupera una lista de identificadores de usuarios que pertenecen a los
	 * organos cuyos identificadores se pasan en el parametro idOrgs.
	 *
	 * @param idOrgs Lista de identificadores de organos.
	 * @return Lista de identificadores de usuarios.
	 * @throws OrganizationException
	 */
	List recuperarUsuariosDeOrganos(final List idOrgs)
			throws OrganizationException;

}