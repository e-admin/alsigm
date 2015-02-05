package ieci.tdw.ispac.ispaclib.gendoc.config;

import ieci.tdw.ispac.ispaclib.configuration.ConfigurationHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public class DocumentalConfiguration {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DocumentalConfiguration.class);

	/**
	 * Información de los repositorios documentales.
	 */
	private Repositories repositories = new Repositories();

	
	/**
	 * Constructor.
	 * @param filePath Ruta de fichero de configuración.
	 * @throws FileNotFoundException si no se encuentra el fichero de configuración.
	 */
	public DocumentalConfiguration(String filePath) throws FileNotFoundException {
		super();

		if (logger.isDebugEnabled()) {
			logger.debug("Fichero de configuracion del conector documental: '" + filePath + "'");
		}
		
		load(ConfigurationHelper.getConfigFileInputStream(filePath));
	}

	/**
	 * Constructor
	 * @param is Contenido del fichero de configuración.
	 */
	public DocumentalConfiguration(InputStream is) {
		super();
		load(is);
	}

	public void load(InputStream is) {

		if (is != null) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Cargando fichero de configuracion");
			}

			XmlFacade configurationXml = new XmlFacade(is);
			repositories = new Repositories();
			
			NodeIterator nodeIt = configurationXml.getNodeIterator(ConfigConstants.CONFIG_XPATH_REPOSITORY);
			for (Node node = nodeIt.nextNode(); node != null; node = nodeIt.nextNode()) {
	
				// Lectura de propiedades comunes
				Repository repository = new Repository();
				repository.setId(XmlFacade.get(node, ConfigConstants.CONFIG_ATTRIBUTE_ID));
				repository.setAlias(XmlFacade.get(node, ConfigConstants.CONFIG_ATTRIBUTE_ALIAS));
	
				String _default = XmlFacade.get(node, ConfigConstants.CONFIG_ATTRIBUTE_DEFAULT);
				if (StringUtils.equals(_default, "true")) {
					repository.setDefault(true);
				}
	
				// Lectura de propiedades propias de cada gestor documental
				NodeIterator nodeIt2 = XmlFacade.getNodeIterator(node, ConfigConstants.CONFIG_XPATH_PROPERTY);
				for (Node node2 = nodeIt2.nextNode(); node2 != null; node2 = nodeIt2.nextNode()) {
					repository.setProperty(XmlFacade.getAttributeValue(node2,
							ConfigConstants.CONFIG_ATTRIBUTE_PROPERTY_NAME),
							XmlFacade.getNodeValue(node2));
				}
	
				// Lectura de Mappings
				String xpathMappings = ConfigConstants.CONFIG_XPATH_MAPPINGS;
				xpathMappings = StringUtils.replace(xpathMappings, 
						ConfigConstants.CONFIG_XPATH_ALIAS_TO_REPLACE, repository.getAlias());
				nodeIt2 = XmlFacade.getNodeIterator(node, xpathMappings);
				MetaDataMappings mappings = new MetaDataMappings();
				for (Node node2 = nodeIt2.nextNode(); node2 != null; node2 = nodeIt2.nextNode()) {
					
					String sourceValue = XmlFacade.get(node2, ConfigConstants.CONFIG_MAPPING_SOURCE);
					String sourceType = XmlFacade.get(node2, ConfigConstants.CONFIG_MAPPING_SOURCE + "/"
							+  ConfigConstants.CONFIG_ATTRIBUTE_MAPPING_TYPE);
					String sourceFormat = XmlFacade.get(node2, ConfigConstants.CONFIG_MAPPING_SOURCE
							+ "/" + ConfigConstants.CONFIG_ATTRIBUTE_MAPPING_FORMAT);
					
					Source source = new Source(sourceType, sourceValue, sourceFormat);
					source.addAttributes(XmlFacade.getAttributes(node2, ConfigConstants.CONFIG_MAPPING_SOURCE));
	
					String destinationValue = XmlFacade.get(node2, ConfigConstants.CONFIG_MAPPING_DESTINATION);
					String destinationType = XmlFacade.get(node2, ConfigConstants.CONFIG_MAPPING_DESTINATION
							+ "/" + ConfigConstants.CONFIG_ATTRIBUTE_MAPPING_DESTINATION_TYPE);
					String destinationFormat = XmlFacade.get(node2, ConfigConstants.CONFIG_MAPPING_DESTINATION
							+ "/"+ ConfigConstants.CONFIG_ATTRIBUTE_MAPPING_DESTINATION_FORMAT);
					
					Destination destination = new Destination(destinationType, destinationValue, destinationFormat);
					destination.addAttributes(XmlFacade.getAttributes(node2, ConfigConstants.CONFIG_MAPPING_DESTINATION));
	
					Mapping mapping = new Mapping(source, destination);
	
					mappings.addMapping(mapping);
	
				}
				
				repository.setMetaDataMappings(mappings);
	
				// Lectura de Tokens
				String xpathTokens = ConfigConstants.CONFIG_XPATH_TOKENS;
				xpathTokens = StringUtils.replace(xpathTokens,
						ConfigConstants.CONFIG_XPATH_ALIAS_TO_REPLACE, repository.getAlias());
				nodeIt2 = XmlFacade.getNodeIterator(node, xpathTokens);
				Tokens tokens = new Tokens();
				for (Node node2 = nodeIt2.nextNode(); node2 != null; node2 = nodeIt2.nextNode()) {
					String tokenName = XmlFacade.get(node2, ConfigConstants.CONFIG_TOKEN_NAME);
					String tokenValue = XmlFacade.get(node2, ConfigConstants.CONFIG_TOKEN_VALUE);
					String valueType = XmlFacade.get(node2, ConfigConstants.CONFIG_TOKEN_VALUE
							+ "/" + ConfigConstants.CONFIG_ATTRIBUTE_TOKEN_VALUE_TYPE);
					Token token = new Token(tokenName, tokenValue);
					token.setValueType(valueType);
					tokens.addToken(token);
				}
				repository.setTokens(tokens);
	
				// Se lee el path de la carpeta destino
				repository.setFolderPath(XmlFacade.get(node, ConfigConstants.CONFIG_TAG_FOLDERPATH));
				if (logger.isDebugEnabled()) {
					logger.debug("Repositorio '" + repository.getAlias() + "'leido del fichero de configuracion");
				}
				repositories.addRepository(repository);
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("Fichero de configuración cargado");
			}
		}
	}

	public Repositories getRepositories() {
		return repositories;
	}

}
