package ieci.tdw.ispac.ispaclib.gendoc.config;

import java.util.HashMap;
import java.util.Map;

public class Repository {

	protected MetaDataMappings metaDataMappings;
	protected Tokens tokens;
	protected String alias;
	protected String id;
	protected boolean _default;
	protected Map properties = new HashMap();
	protected String folderPath = null;

	public Repository() {
		super();
	}

	/**
	 * Gets the value of the metaDataMappings property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link MetaDataMappings }
	 *     
	 */
	public MetaDataMappings getMetaDataMappings() {
		return metaDataMappings;
	}

	/**
	 * Sets the value of the metaDataMappings property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link MetaDataMappings }
	 *     
	 */
	public void setMetaDataMappings(MetaDataMappings value) {
		this.metaDataMappings = value;
	}

	/**
	 * Gets the value of the tokens property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Tokens }
	 *     
	 */
	public Tokens getTokens() {
		return tokens;
	}

	/**
	 * Sets the value of the tokens property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Tokens }
	 *     
	 */
	public void setTokens(Tokens value) {
		this.tokens = value;
	}

	/**
	 * Gets the value of the alias property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the value of the alias property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setAlias(String value) {
		this.alias = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setId(String value) {
		this.id = value;
	}

	public boolean isDefault() {
		return _default;
	}

	public void setDefault(boolean _default) {
		this._default = _default;
	}

	public String getProperty(String name) {
		return (String) properties.get(name);
	}

	public void setProperty(String name, String value) {
		this.properties.put(name, value);
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

}
