package es.ieci.tecdoc.fwktd.dm.business.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ContentType {

	protected Configuration configuration = null;

	protected String id = null;
	protected String name = null;
	protected String type = null;

	protected Properties properties = new Properties();
	protected List<Mapping> mappings = new ArrayList<Mapping>();
	protected List<Token> tokens = new ArrayList<Token>();


	/**
	 * Constructor.
	 */
	public ContentType() {
		super();
	}

	/**
	 * Constructor.
	 */
	public ContentType(Configuration configuration) {
		super();
		setConfiguration(configuration);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getProperty(String name) {
		String value = properties.getProperty(name);
		if ((value == null) && (configuration != null)) {
			value = configuration.getProperty(name);
		}

		return value;
	}

	public void setProperty(String name, String value) {
		this.properties.setProperty(name, value);
	}

	public void setProperties(Properties properties) {
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	public Mapping findMappingBySourceValue(String sourceValue) {

		for (Mapping mapping : mappings) {
			Source source = mapping.getSource();
			if (source != null) {
				if (StringUtils.equalsIgnoreCase(sourceValue, source.getValue())) {
					return mapping;
				}
			}
		}

		return null;
	}

	public Mapping findMappingByDestinationValue(String destinationValue) {

		for (Mapping mapping : mappings) {
			Destination destination = mapping.getDestination();
			if (destination != null) {
				if (StringUtils.equalsIgnoreCase(destinationValue, destination.getValue())) {
					return mapping;
				}
			}
		}

		return null;
	}

	public void addMapping(Mapping mapping) {
		if (mapping != null) {
			mappings.add(mapping);
		}
	}

	public void addMappings(List<Mapping> mappings) {
		if (mappings != null) {
			this.mappings.addAll(mappings);
		}
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public Token findToken(String tokenName) {

		for (Token token : tokens) {
			if (StringUtils.equalsIgnoreCase(tokenName, token.getName())) {
				return token;
			}
		}

		return null;
	}

	public void addToken(Token token) {
		if (token != null) {
			tokens.add(token);
		}
	}

}
