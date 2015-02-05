package es.ieci.tecdoc.fwktd.dm.business.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigurationResourceLoader;
import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

public class ConfigurationFactory {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);

	private static final String DEFAULT_FILE_NAME = "fwktd-dm/fwktd-dm-config.xml";
	private static final String MONO_ENTITY_ID = "monoentity";

	protected static final String XPATH_CONFIG = "/config";
	protected static final String XPATH_PROPERTIES = "properties";
	protected static final String XPATH_PROPERTY = "property";
	protected static final String XPATH_MAPPING_GROUPS = "mapping-groups";
	protected static final String XPATH_MAPPING_GROUP = "mapping-group";
	protected static final String XPATH_CONTENT_TYPES = "content-types";
	protected static final String XPATH_CONTENT_TYPE = "content-type";
	protected static final String XPATH_MAPPINGS = "mappings";
	protected static final String XPATH_MAPPING = "mapping";
	protected static final String XPATH_SOURCE = "source";
	protected static final String XPATH_DESTINATION = "destination";
	protected static final String XPATH_TOKENS = "tokens";
	protected static final String XPATH_TOKEN = "token";

	protected static final String XPATH_ATTRIBUTE_ID = "@id";
	protected static final String XPATH_ATTRIBUTE_NAME = "@name";
	protected static final String XPATH_ATTRIBUTE_TYPE = "@type";
	protected static final String XPATH_ATTRIBUTE_FORMAT = "@format";
	protected static final String XPATH_ATTRIBUTE_TOKEN = "@token";
	protected static final String XPATH_ATTRIBUTE_REF = "@ref";


	/**
	 * Configuraciones del gestor documental.
	 */
	private Map<String, Configuration> configMap = new HashMap<String, Configuration>();

	private ConfigurationResourceLoader configurationResourceLoader = null;
	private String fileName = DEFAULT_FILE_NAME;


	/**
	 * Constructor.
	 */
	public ConfigurationFactory() {
		super();
	}

	public ConfigurationResourceLoader getConfigurationResourceLoader() {
		return configurationResourceLoader;
	}

	public void setConfigurationResourceLoader(
			ConfigurationResourceLoader configurationResourceLoader) {
		this.configurationResourceLoader = configurationResourceLoader;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public synchronized Configuration getConfiguration() {

		// Identificador de la entidad (en entornos multientidad)
		String entity = MultiEntityContextHolder.getEntity();
		if (StringUtils.isBlank(entity)) {
			entity = MONO_ENTITY_ID;
		}

		Configuration config = (Configuration)configMap.get(entity);
		if (config == null) {

			// Cargar el fichero de configuración propio de la entidad
			if (!MONO_ENTITY_ID.equals(entity)) {
				config = loadConfiguration(getEntityFileName(entity));
			}

			// Cargar el fichero de configuración general si no hay uno
			// propio de la entidad
			if (config == null) {
				config = loadConfiguration(getFileName());
			}

			configMap.put(entity, config);
		}

		return config;
	}

	/**
	 * Carga la configuración del gestor documental a partir del fichero de configuración.
	 * @param fileName Nombre del fichero de configuración.
	 * @return Configuración del gestor documental.
	 */
	protected Configuration loadConfiguration(String fileName) {

		Configuration config = null;

		if (getConfigurationResourceLoader() != null) {

//			String fullPath = GestionDocumentalConfigFilePathResolver.getInstance().resolveFullPath(fileName);
//			if (logger.isInfoEnabled()) {
//				logger.info("Fichero de configuración: {} => {}", fileName, fullPath);
//			}
//
//			if (StringUtils.isNotBlank(fullPath)) {
//				File file = new File(fullPath);
//				if ((file != null) && file.isFile()){
//					try {
//						InputStream is = new FileInputStream(file);
//						if (is != null) {
//
//							if (logger.isDebugEnabled()) {
//								logger.debug("Cargando fichero de configuración [{}]", file);
//							}
//
//							// Parsear el fichero de configuración del gestor documental
//							config = parseConfiguration(is);
//
//							if (logger.isDebugEnabled()) {
//								logger.debug("Fichero de configuración [{}] cargado", file);
//							}
//						}
//					} catch (Exception e) {
//						logger.error("Error al leer el fichero de configuración: " + file, e);
//					}
//				}
//			}

			Resource resource = getConfigurationResourceLoader().loadResource(fileName, null);
			logger.info("Fichero de configuración: {} => {}", fileName, resource);

			if (resource != null) {
				try {
					InputStream is = resource.getInputStream();
					if (is != null) {

						logger.debug("Cargando fichero de configuración [{}]", resource);

						// Parsear el fichero de configuración del gestor documental
						config = parseConfiguration(is);

						logger.debug("Fichero de configuración [{}] cargado", resource);
					}
				} catch (Exception e) {
					logger.error("Error al leer el fichero de configuración: " + resource, e);
				}
			}
		}

		return config;
	}

	@SuppressWarnings("unchecked")
	protected Configuration parseConfiguration(InputStream is) throws DocumentException {

		Configuration config = new Configuration();

        Document doc = new SAXReader().read(new InputSource(is));

        // Lectura de las propiedades globales
		List<Node> globalPropertyNodes = doc.selectNodes(XPATH_CONFIG + "/" + XPATH_PROPERTIES + "/" + XPATH_PROPERTY);
		for (Node globalPropertyNode : globalPropertyNodes) {
			config.setProperty(
					globalPropertyNode.valueOf(XPATH_ATTRIBUTE_NAME),
					globalPropertyNode.getText().trim());
		}

		// Lectura de los grupos de mapeos
		List<Node> mappingGroupNodes = doc.selectNodes(XPATH_CONFIG + "/" + XPATH_MAPPING_GROUPS + "/" + XPATH_MAPPING_GROUP);
		for (Node mappingGroupNode : mappingGroupNodes) {

			MappingGroup mappingGroup = new MappingGroup(config);

			// Lectura de atributos
			mappingGroup.setId(mappingGroupNode.valueOf(XPATH_ATTRIBUTE_ID));

			// Lectura de mapeos de campos del tipo documental
			List<Node> mappingNodes = mappingGroupNode.selectNodes(XPATH_MAPPING);
			for (Node node : mappingNodes) {
				Mapping mapping = getMappingFromNode(node);
				if (mapping != null) {
					mappingGroup.addMapping(mapping);
				}
			}

			config.addMappingGroup(mappingGroup);
		}

		// Lectura de los tipos documentales
		List<Node> contentTypeNodes = doc.selectNodes(XPATH_CONFIG + "/" + XPATH_CONTENT_TYPES + "/" + XPATH_CONTENT_TYPE);
		for (Node contentTypeNode : contentTypeNodes) {

			ContentType contentType = new ContentType(config);

			// Lectura de atributos
			contentType.setId(contentTypeNode.valueOf(XPATH_ATTRIBUTE_ID));
			contentType.setName(contentTypeNode.valueOf(XPATH_ATTRIBUTE_NAME));
			contentType.setType(contentTypeNode.valueOf(XPATH_ATTRIBUTE_TYPE));

			// Lectura de propiedades propias de cada tipo documental
			List<Node> propertyNodes = contentTypeNode.selectNodes(XPATH_PROPERTIES + "/" + XPATH_PROPERTY);
			for (Node node : propertyNodes) {
				contentType.setProperty(
						node.valueOf(XPATH_ATTRIBUTE_NAME),
						node.getText().trim());
			}

//			// Lectura de mapeos de campos del tipo documental
//			List<Node> mappingNodes = contentTypeNode.selectNodes(XPATH_MAPPINGS + "/" + XPATH_MAPPING);
//			for (Node node : mappingNodes) {
//				Mapping mapping = getMappingFromNode(node);
//				if (mapping != null) {
//					contentType.addMapping(mapping);
//				}
//			}

			// Lectura de mapeos de campos del tipo documental
			List<Node> mappingNodes = contentTypeNode.selectNodes(XPATH_MAPPINGS + "/*");
			for (Node node : mappingNodes) {

				if (XPATH_MAPPING.equalsIgnoreCase(node.getName())) {

					Mapping mapping = getMappingFromNode(node);
					if (mapping != null) {
						contentType.addMapping(mapping);
					}

				} else if (XPATH_MAPPING_GROUP.equalsIgnoreCase(node.getName())) {

					String mappingGroupId = node.valueOf(XPATH_ATTRIBUTE_REF);
					if (StringUtils.isNotBlank(mappingGroupId)) {
						MappingGroup mappingGroup = config.findMappingGroup(mappingGroupId);
						if (mappingGroup != null) {
							contentType.addMappings(mappingGroup.getMappings());
						}
					}
				}
			}

			// Lectura de Tokens
			List<Node> tokenNodes = contentTypeNode.selectNodes(XPATH_TOKENS + "/" + XPATH_TOKEN);
			for (Node node : tokenNodes) {
				contentType.addToken(new Token(
						node.valueOf(XPATH_ATTRIBUTE_NAME),
						node.valueOf(XPATH_ATTRIBUTE_TYPE),
						node.getText().trim()));
			}

			logger.debug("ContentType [{}] - [{}] leído del fichero de configuración",
					contentType.getId(), contentType.getName());

			config.addContentType(contentType);
		}

		return config;
	}

	/**
	 * Obtiene el nombre de fichero de configuración propio de la entidad.
	 * @param entity Entidad.
	 * @return Nombre de fichero de configuración de la entidad.
	 */
	protected String getEntityFileName(String entity) {
		String entityFileName = getFileName();

		int dotIndex = entityFileName.lastIndexOf(".");
		if (dotIndex > 0) {
			entityFileName = entityFileName.substring(0, dotIndex) + "_" + entity + entityFileName.substring(dotIndex);
		} else {
			entityFileName += "_" + entity;
		}

		return entityFileName;
	}

	@SuppressWarnings("unchecked")
	protected Mapping getMappingFromNode(Node node) {

		Mapping mapping = null;

		if (node != null) {

			Source source = null;
			Destination destination = null;

			Node sourceNode = node.selectSingleNode(XPATH_SOURCE);
			if (sourceNode != null) {
				source = new Source(
						sourceNode.valueOf(XPATH_ATTRIBUTE_TYPE),
						sourceNode.getText().trim(),
						sourceNode.valueOf(XPATH_ATTRIBUTE_FORMAT),
						sourceNode.valueOf(XPATH_ATTRIBUTE_TOKEN));
				source.addAttributes(((Element)sourceNode).attributes());
			}

			Node destinationNode = node.selectSingleNode(XPATH_DESTINATION);
			if (destinationNode != null) {
				destination = new Destination(
						destinationNode.valueOf(XPATH_ATTRIBUTE_TYPE),
						destinationNode.getText().trim(),
						destinationNode.valueOf(XPATH_ATTRIBUTE_FORMAT));
				destination.addAttributes(((Element)destinationNode).attributes());
			}

			mapping = new Mapping(source, destination);
		}

		return mapping;
	}
}
