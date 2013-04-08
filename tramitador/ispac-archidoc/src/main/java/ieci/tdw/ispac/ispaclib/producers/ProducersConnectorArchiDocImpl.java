package ieci.tdw.ispac.ispaclib.producers;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.producers.vo.Producer;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.archidoc.organization.connector.OrganizationConnector;
import es.ieci.tecdoc.archidoc.organization.connector.impl.OrganizationConnectorImpl;
import es.ieci.tecdoc.archidoc.organization.constants.OrganizationConstants;
import es.ieci.tecdoc.archidoc.organization.constants.OrganizationTipoAtributo;
import es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano;

public class ProducersConnectorArchiDocImpl implements IProducersConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ProducersConnectorArchiDocImpl.class);
	
	/**
	 * Identificador por defecto del productor raíz.
	 */
	private static final String DEFAULT_PRODUCER_ID = "-1";

	/**
	 * Nombre por defecto del productor raíz.
	 */
	private static final String DEFAULT_PRODUCER_NAME = "ROOT";

	/**
	 * Nombre del parámetro de configuración que almacena el nombre del productor raíz. 
	 */
	private static final String PRODUCERS_CONNECTOR_ARCHIDOC_ROOT_NAME = "PRODUCERS_CONNECTOR_ARCHIDOC_ROOT_NAME";
	
	/**
	 * Nombre del parámetro de configuración que almacena el nombre del datasource. 
	 */
	private static final String PRODUCERS_CONNECTOR_ARCHIDOC_DATASOURCE_NAME = "PRODUCERS_CONNECTOR_ARCHIDOC_DATASOURCE_NAME";
	
	/**
	 * Nombre por defecto del datasource de archiDoc.
	 */
	private static final String DEFAULT_DATASOURCE_NAME = "java:comp/env/jdbc/archivoDS";
	
	
	/**
	 * Conector de organización.
	 */
	private OrganizationConnector connector = null;

	
	/**
	 * Constructor
	 * @param ctx Contexto de cliente.
	 */
	public ProducersConnectorArchiDocImpl(IClientContext ctx) {
		super();

		Properties params = new Properties();
		
		String datasource = getDataSourceName();
		if (StringUtils.isNotBlank(datasource)) {
			params.put(OrganizationConstants.PARAM_DATASOURCE, datasource);
		}
		
		String entityId = getEntityId();
		if (StringUtils.isNotBlank(entityId)) {
			params.put(OrganizationConstants.PARAM_ENTITY, entityId);
		}
		
		this.connector = new OrganizationConnectorImpl();
		this.connector.initialize(params);
	}
	
	/**
	 * Obtiene el productor principal.
	 * @return Información del productor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Producer getRootProducer() throws ISPACException {
		
		// Obtener el nombre del productor raíz.
		String producerName = ISPACConfiguration.getInstance().get(PRODUCERS_CONNECTOR_ARCHIDOC_ROOT_NAME);
		if (StringUtils.isBlank(producerName)) {
			producerName = DEFAULT_PRODUCER_NAME;
		}

		Producer producer = new Producer();
		producer.setId(DEFAULT_PRODUCER_ID);
		producer.setName(producerName);
		
		return producer;
	}

	/**
	 * Obtiene la información de un productor.
	 * @param id Identificador del productor
	 * @return Información del productor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Producer getProducer(String id) throws ISPACException {
		
		Producer producer = null;
		
		try {
			
			if (StringUtils.equalsNullEmpty(id, DEFAULT_PRODUCER_ID)) {
				producer = getRootProducer();
			} else {
				OrganizationOrgano organo = connector.recuperarOrgano(
						OrganizationTipoAtributo.IDENTIFICADOR_ORGANO, id);
				producer = getProducer(organo);
			}
			
		} catch (Throwable t) {
			logger.error("Error al obtener el productor con id [" + id + "]", t);
			throw new ISPACException("Error al obtener el productor con id [" + id + "]", t);
		}
		
		return producer;
	}

	/**
	 * Obtiene la lista de hijos del productor.
	 * @param id Identificador del productor
	 * @return Lista de productores ({@see Producer}.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getProducerChildren(String id) throws ISPACException {

		List producers = new ArrayList();
		
		try {
			List organos = null;
			
			if (StringUtils.equalsNullEmpty(id, DEFAULT_PRODUCER_ID)) {
				organos = connector.recuperarInstitucionesProductoras();
			} else {
				organos = connector.recuperarHijosDeOrgano(id);
			}
			
			producers.addAll(getProducerList(organos));	
			
		} catch (Throwable t) {
			logger.error("Error al obtener los hijos del productor con id [" + id + "]", t);
			throw new ISPACException("Error al obtener los hijos del productor con id [" + id + "]", t);
		}
		
		return producers;
	}
	
	/**
	 * Obtiene la lista de antecesores del productor.
	 * @param id Identificador del productor
	 * @return Lista de productores ({@see Producer}.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getProducerAncestors(String id) throws ISPACException {
		
		List producers = new ArrayList();
		
		try {
			
			// Añadir el productor actual
			Producer producer = getProducer(id);
			if (producer != null) {
				producers.add(producer);
			}
				
			if (!StringUtils.equalsNullEmpty(id, DEFAULT_PRODUCER_ID)) {

				// Añadir el productor antecesores
				List organos = connector.recuperarOrganosAntecesores(id, 0);
				producers.addAll(getProducerListReverse(organos));

				// Añadir el productor raiz
				producers.add(getRootProducer());
			}

		} catch (Throwable t) {
			logger.error("Error al obtener los antecesores del productor con id [" + id + "]", t);
			throw new ISPACException("Error al obtener los antecesores del productor con id [" + id + "]", t);
		}
		
		return producers;
	}

	private List getProducerList(List organos) throws ISPACException {
		
		List producers = new ArrayList();
		
		if (!CollectionUtils.isEmpty(organos)) {
			for (int i = 0; i < organos.size(); i++) {
				OrganizationOrgano organo = (OrganizationOrgano) organos.get(i);
				if (organo != null) {
					producers.add(getProducer(organo));
				}
			}
 		}
		
		return producers;
	}

	private List getProducerListReverse(List organos) throws ISPACException {
		List producers = new ArrayList();
		
		if (!CollectionUtils.isEmpty(organos)) {
			for (int i = organos.size()-1; i >= 0; i--) {
				OrganizationOrgano organo = (OrganizationOrgano) organos.get(i);
				if (organo != null) {
					producers.add(getProducer(organo));
				}
			}
 		}
		
		return producers;
	}

	private Producer getProducer(OrganizationOrgano organo) {

		Producer producer = null;
		
		if (organo != null) {
			producer = new Producer();
			producer.setId(organo.getId());
			producer.setName(organo.getNombre());
		}
		
		return producer;
	}
	
	private String getDataSourceName() {
		String dataSourceName = null;
		
		try {
			dataSourceName = ISPACConfiguration.getInstance().get(PRODUCERS_CONNECTOR_ARCHIDOC_DATASOURCE_NAME);
		} catch (Exception e) {
			logger.warn("Error al obtener el nombre del datasource", e);
		}
			
		if (StringUtils.isBlank(dataSourceName)) {
			dataSourceName = DEFAULT_DATASOURCE_NAME;
		}
		
		return dataSourceName;
	}
	
	private String getEntityId() {
		String entityId = null;
		
		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			entityId = info.getOrganizationId();
		}

		return entityId;
	}
}
