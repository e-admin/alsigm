package es.ieci.tecdoc.isicres.document.connector.vo;
/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public class IsicresBasicConnectorConfigurationVO extends
		IsicresAbstractConnectorConfigurationVO {

	protected String configXml;

	public String getConfigXml() {
		return configXml;
	}

	public void setConfigXml(String configXml) {
		this.configXml = configXml;
	}

	public void fromXml(String xml) {
		this.configXml=xml;
		
	}

	public String toXml() {
		return this.configXml;
	}

}
