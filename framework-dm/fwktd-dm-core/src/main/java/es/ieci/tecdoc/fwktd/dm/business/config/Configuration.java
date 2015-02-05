package es.ieci.tecdoc.fwktd.dm.business.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * Configuración del acceso al gestor documental.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class Configuration {

	/**
	 * Propiedades de configuración.
	 */
	private Properties properties = new Properties();

	/**
	 * Listado de grupos de mapeos.
	 */
	private List<MappingGroup> mappingGroups = new ArrayList<MappingGroup>();

	/**
	 * Listado de tipos documentales.
	 */
	private List<ContentType> contentTypes = new ArrayList<ContentType>();


	/**
	 * Constructor.
	 */
	public Configuration() {
		super();
	}


	public Properties getProperties() {
		return properties;
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	public void setProperty(String name, String value) {
		this.properties.setProperty(name, value);
	}

	public void setProperties(Properties properties) {
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}

	public List<MappingGroup> getMappingGroups() {
		return mappingGroups;
	}

	public void addMappingGroup(MappingGroup mappingGroup) {
		if (mappingGroup != null) {
			mappingGroups.add(mappingGroup);
		}
	}

	public MappingGroup findMappingGroup(String id) {
		for (MappingGroup mappingGroup : mappingGroups) {
			if (StringUtils.equals(mappingGroup.getId(), id))
				return mappingGroup;
		}
		return null;
	}

	public List<ContentType> getContentTypes() {
		return contentTypes;
	}

	public void addContentType(ContentType contentType) {
		if (contentType != null) {
			contentTypes.add(contentType);
		}
	}

	public ContentType findContentType(String key) {
		ContentType contentType = findContentTypeById(key);
		if (contentType == null) {
			contentType = findContentTypeByName(key);
		}
		return contentType;
	}

	public ContentType findContentTypeById(String id) {
		for (ContentType contentType : contentTypes) {
			if (StringUtils.equals(contentType.getId(), id))
				return contentType;
		}
		return null;
	}

	public ContentType findContentTypeByName(String name) {
		for (ContentType contentType : contentTypes) {
			if (StringUtils.equals(contentType.getName(), name))
				return contentType;
		}
		return null;
	}

}
