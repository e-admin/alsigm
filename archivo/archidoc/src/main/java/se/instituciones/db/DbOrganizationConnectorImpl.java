package se.instituciones.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.InfoOrgano;
import se.instituciones.archivo.invesdoc.idoc.OrganizationConnectorException;
import se.instituciones.archivo.invesdoc.vo.OrganoVO;
import se.instituciones.exceptions.GestorOrganismosException;

import common.exceptions.SistemaExternoException;

import es.ieci.tecdoc.archidoc.organization.connector.OrganizationConnector;
import es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl;
import es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano;

/**
 * Conector con el Sistema Gestor de Organización desde una base de datos.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class DbOrganizationConnectorImpl implements GestorOrganismos {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(DbOrganizationConnectorImpl.class);

	/**
	 * Conector de organizacion
	 */
	private OrganizationConnector orgConnector = null;

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.Parametrizable#initialize(java.util.Properties)
	 */
	public void initialize(final Properties params)
			throws SistemaExternoException {
		try {
			orgConnector = new OrganizationConnectorImpl();
			orgConnector.initialize(params);
		} catch (Exception e) {
			throw new OrganizationConnectorException(e, this.getClass()
					.getName(), "Error inicializando el conector");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarHijosDeOrgano(java.lang.String)
	 */
	public List recuperarHijosDeOrgano(final String idOrgPadre)
			throws GestorOrganismosException, NotAvailableException {
		List ret = null;
		try {
			List organos = orgConnector.recuperarHijosDeOrgano(idOrgPadre);
			ret = transformOrganos(organos);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarInstitucionesProductoras()
	 */
	public List recuperarInstitucionesProductoras()
			throws GestorOrganismosException, NotAvailableException {
		List ret = null;
		try {
			List instituciones = orgConnector
					.recuperarInstitucionesProductoras();
			ret = transformOrganos(instituciones);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}

	/**
	 * Permite transformar un objeto del conector de organizacion en uno del
	 * interfaz de organos
	 * 
	 * @param organo
	 *            organo a transformar ({@link OrganizationOrgano} )
	 * @return objeto transformado ({@link InfoOrgano})
	 */
	private InfoOrgano transformOrgano(final OrganizationOrgano organo) {
		OrganoVO infoOrgano = null;
		if (organo != null) {
			infoOrgano = new OrganoVO();
			infoOrgano.setCodigo(organo.getCodigo());
			infoOrgano.setId(organo.getId());
			infoOrgano.setIdPadre(organo.getIdPadre());
			infoOrgano.setNombre(organo.getNombre());
			infoOrgano.setNivel(organo.getNivel());
		}
		return infoOrgano;
	}

	/**
	 * Permite transformar una lista de objetos del conector de organizacion en
	 * otra de objetos del interfaz de organos
	 * 
	 * @param organos
	 *            Lista de organos ({@link OrganizationOrgano})
	 * @return Lista de organos ({@link InfoOrgano})
	 */
	private List transformOrganos(final List organos) {
		List infoOrganos = null;
		if ((organos != null) && (!organos.isEmpty())) {
			infoOrganos = new ArrayList();
			OrganizationOrgano organo = null;
			InfoOrgano infoOrgano = null;
			Iterator it = organos.iterator();
			while (it.hasNext()) {
				organo = (OrganizationOrgano) it.next();
				infoOrgano = transformOrgano(organo);
				infoOrganos.add(infoOrgano);
			}
		}
		return infoOrganos;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarOrgano(short,
	 *      java.lang.String)
	 */
	public InfoOrgano recuperarOrgano(final short tipoAtrib,
			final String valorAtrib) throws GestorOrganismosException,
			NotAvailableException {
		InfoOrgano ret = null;
		try {
			OrganizationOrgano organo = orgConnector.recuperarOrgano(tipoAtrib,
					valorAtrib);
			ret = transformOrgano(organo);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarOrganodeUsuario(java.lang.String)
	 */
	public InfoOrgano recuperarOrganodeUsuario(final String idUsr)
			throws GestorOrganismosException, NotAvailableException {
		InfoOrgano ret = null;
		try {
			OrganizationOrgano organo = orgConnector
					.recuperarOrganodeUsuario(idUsr);
			ret = transformOrgano(organo);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarOrganos(java.lang.String)
	 */
	public List recuperarOrganos(final String param)
			throws GestorOrganismosException, NotAvailableException {
		List ret = null;
		try {
			List organos = orgConnector.recuperarOrganos(param);
			ret = transformOrganos(organos);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarOrganosAntecesores(java.lang.String,
	 *      int)
	 */
	public List recuperarOrganosAntecesores(final String idOrg,
			final int numNiveles) throws GestorOrganismosException,
			NotAvailableException {
		List ret = null;
		try {
			List organos = orgConnector.recuperarOrganosAntecesores(idOrg,
					numNiveles);
			ret = transformOrganos(organos);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarOrganosDependientes(java.lang.String,
	 *      int)
	 */
	public List recuperarOrganosDependientes(final String idOrg,
			final int numNiveles) throws GestorOrganismosException,
			NotAvailableException {
		List ret = null;
		try {
			List organos = orgConnector.recuperarOrganosDependientes(idOrg,
					numNiveles);
			ret = transformOrganos(organos);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see se.instituciones.GestorOrganismos#recuperarUsuariosDeOrganos(java.util.List)
	 */
	public List recuperarUsuariosDeOrganos(final List idOrgs)
			throws GestorOrganismosException, NotAvailableException {
		List ret = null;
		try {
			ret = orgConnector.recuperarUsuariosDeOrganos(idOrgs);
		} catch (Exception e) {
			throw new GestorOrganismosException(e);
		}
		return ret;
	}
}