package ieci.tdw.ispac.ispaclib.gendoc.config;

import java.util.Properties;

/** 
 * Contiene la información de los elementos de un mapeo de metadatos.
 */
public class MappingElement {

	private String type = null;
	private String value = null;
	private String format = null;
	private Properties attributes = new Properties();

	public MappingElement() {
		super();
	}
	
	public MappingElement(String type, String value) {
		this();
		this.type = type;
		this.value = value;
	}

	public MappingElement(String type, String value, String format) {
		this(type, value);
		this.format = format;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Properties getAttributes() {
		return attributes;
	}
	
	public void addAttributes(Properties atts) {
		this.attributes.putAll(atts);
	}

	public String getAttribute(String name) {
		return attributes.getProperty(name);
	}

	public String getAttribute(String name, String defaultValue) {
		return attributes.getProperty(name, defaultValue);
	}
	
	public void setAttribute(String name, String value) {
		attributes.put(name, value);
	}
}
