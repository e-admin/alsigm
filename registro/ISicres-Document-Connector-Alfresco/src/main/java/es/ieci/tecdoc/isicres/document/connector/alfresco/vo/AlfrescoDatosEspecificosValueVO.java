package es.ieci.tecdoc.isicres.document.connector.alfresco.vo;

import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractBaseConnectorVO;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class AlfrescoDatosEspecificosValueVO extends ISicresAbstractBaseConnectorVO{
	protected String id;
	protected String value; 
	protected String name; 
	protected String type ;
	protected String aspectName;
	protected String contentName;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the aspectName
	 */
	public String getAspectName() {
		return aspectName;
	}
	/**
	 * @param aspectName the aspectName to set
	 */
	public void setAspectName(String aspectName) {
		this.aspectName = aspectName;
	}
	/**
	 * @return the contentName
	 */
	public String getContentName() {
		return contentName;
	}
	/**
	 * @param contentName the contentName to set
	 */
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	
	
}
