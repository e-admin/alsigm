package se.instituciones.archigest;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

import se.instituciones.GestorOrganismos;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.archigest.stub.WSOrganismos;
import se.instituciones.archigest.stub.WSOrganismosServiceLocator;
import se.instituciones.exceptions.GestorOrganismosException;
import util.CollectionUtils;

import common.exceptions.SistemaExternoException;
import common.util.StringUtils;

/**
 * Conector con el Sistemas Gestor de Organización
 */
public class GestorOrganismoArchigest implements GestorOrganismos {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(GestorOrganismoArchigest.class);

	/** Stub del servicio web de organismos. */
	protected WSOrganismos wsOrganismos = null;

	/** Constante para la localización del WSDL del Servicio Web. */
	private static final String WSDL_LOCATION = "WSDL_LOCATION";
	private static final String SYSPROP_HTTP_PROXY_HOST="http.proxyHost";
	private static final String SYSPROP_HTTP_PROXY_PORT="http.proxyPort";
	private static final String PROXY_HOST_KEY="PROXY_HOST";
	private static final String PROXY_PORT_KEY="PROXY_PORT";

	/**
	 * Constructor.
	 */
	public GestorOrganismoArchigest() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 *
	 * @param params
	 *            Parámetros de configuración.
	 * @throws SistemaExternoException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) throws SistemaExternoException {
		try {

			String host = params.getProperty(PROXY_HOST_KEY);
			String port = params.getProperty(PROXY_PORT_KEY);

    		if(StringUtils.isNotEmpty(host)){
    			if(logger.isInfoEnabled()){
    				logger.info("Estableciendo la propiedad del proxy: host=" +  host);
    			}

    			System.setProperty(SYSPROP_HTTP_PROXY_HOST, host);
    		}
    		if(StringUtils.isNotEmpty(port)){
    			if(logger.isInfoEnabled()){
    				logger.info("Estableciendo la propiedad del proxy: port=" +  port);
    			}

    			System.setProperty(SYSPROP_HTTP_PROXY_PORT, port);
    		}

			String wsdlLocation = params.getProperty(WSDL_LOCATION);
			wsOrganismos = WSOrganismosServiceLocator
					.getOrganismosService(wsdlLocation);

		} catch (Exception e) {
			logger.error("Error en la creaci\u00F3n del proxy de Organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

	/**
	 * Recupera la lista de órganos que son hijos del órgano cuyo identificador
	 * es idOrgPadre. Para los órganos que no tienen padre se pasa null.
	 *
	 * @param idOrgPadre
	 *            Identificador del órgano padre.
	 * @return Lista de órganos.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public List recuperarHijosDeOrgano(String idOrgPadre)
			throws GestorOrganismosException {
		try {
			return CollectionUtils.createList(wsOrganismos
					.recuperarHijosDeOrgano(idOrgPadre));
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

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
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public List recuperarOrganosDependientes(String idOrg, int numNiveles)
			throws GestorOrganismosException {
		try {
			return CollectionUtils.createList(wsOrganismos
					.recuperarOrganosDependientes(idOrg, numNiveles));
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

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
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public List recuperarOrganosAntecesores(String idOrg, int numNiveles)
			throws GestorOrganismosException {
		try {
			if (StringUtils.isBlank(idOrg))
				return new ArrayList();

			return CollectionUtils
					.createList(wsOrganismos.recuperarOrganosAntecesores(idOrg,
							numNiveles), new Predicate() {
						public boolean evaluate(Object obj) {
							return (((InfoOrgano) obj).getNivel() > 1);
						}
					});
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

	/**
	 * Recupera la lista de órganos que tienen el valor de param como subtexto
	 * dentro de su nombre.
	 *
	 * @param param
	 *            Texto en el nombre del órgano.
	 * @return Lista de órganos.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public List recuperarOrganos(String param) throws GestorOrganismosException {
		try {
			if (param == null)
				return new ArrayList();

			return CollectionUtils.createList(wsOrganismos
					.recuperarOrganos(param));
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

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
	 */
	public InfoOrgano recuperarOrgano(short tipoAtrib, String valorAtrib)
			throws GestorOrganismosException {
		try {
			if (StringUtils.isBlank(valorAtrib))
				return null;

			return wsOrganismos.recuperarOrgano(tipoAtrib, valorAtrib);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

	/**
	 * Recupera la información básica del órgano al que pertenece un usuario
	 * cuyo identificador es idUsr.
	 *
	 * @param idUsr
	 *            Identificador de usuario.
	 * @return Información básica de un órgano.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public InfoOrgano recuperarOrganodeUsuario(String idUsr)
			throws GestorOrganismosException {
		try {
			if (StringUtils.isBlank(idUsr))
				return null;

			return wsOrganismos.recuperarOrganodeUsuario(idUsr);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

	/**
	 * Recupera una lista de identificadores de usuarios que pertenecen a los
	 * órganos cuyos identificadores se pasan en el parámetro idOrgs.
	 *
	 * @param idOrgs
	 *            Lista de identificadores de órganos.
	 * @return Lista de identificadores de usuarios.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public List recuperarUsuariosDeOrganos(List idOrgs)
			throws GestorOrganismosException {
		try {
			if (CollectionUtils.isEmpty(idOrgs))
				return new ArrayList();

			return CollectionUtils.createList(wsOrganismos
					.recuperarUsuariosDeOrganos((String[]) idOrgs
							.toArray(new String[idOrgs.size()])));
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

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
	 */
	public List recuperarInstitucionesProductoras()
			throws GestorOrganismosException {
		try {
			return CollectionUtils.createList(wsOrganismos
					.recuperarInstitucionesProductoras());
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web de organismos", e);
			throw new GestorOrganismosException(e);
		}
	}

}
