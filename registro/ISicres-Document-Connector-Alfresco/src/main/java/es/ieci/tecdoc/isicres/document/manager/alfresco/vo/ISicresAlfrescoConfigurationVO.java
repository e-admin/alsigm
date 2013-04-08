package es.ieci.tecdoc.isicres.document.manager.alfresco.vo;

import com.thoughtworks.xstream.XStream;

import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoAspectVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosValueVO;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class ISicresAlfrescoConfigurationVO extends ISicresAlfrescoAbstractConfigurationVO{
	private ISicresAlfrescoConnectorConfigurationVO connectorConfigurationVO;
	private ISicresAlfrescoRelationsVO sicresAlfrescoRelationsVO;
	
	public ISicresAlfrescoConfigurationVO(){
		connectorConfigurationVO = new ISicresAlfrescoConnectorConfigurationVO();
		sicresAlfrescoRelationsVO = new ISicresAlfrescoRelationsVO();
	}
	
	/**
	 * @return the configurationVO
	 */
	public ISicresAlfrescoConnectorConfigurationVO getConfigurationVO() {
		return connectorConfigurationVO;
	}
	/**
	 * @param configurationVO the configurationVO to set
	 */
	public void setConfigurationVO(
			ISicresAlfrescoConnectorConfigurationVO connectorConfigurationVO) {
		this.connectorConfigurationVO = connectorConfigurationVO;
	}
	/**
	 * @return the sicresAlfrescoRelationsVO
	 */
	public ISicresAlfrescoRelationsVO getSicresAlfrescoRelationsVO() {
		return sicresAlfrescoRelationsVO;
	}
	/**
	 * @param sicresAlfrescoRelationsVO the sicresAlfrescoRelationsVO to set
	 */
	public void setSicresAlfrescoRelationsVO(
			ISicresAlfrescoRelationsVO sicresAlfrescoRelationsVO) {
		this.sicresAlfrescoRelationsVO = sicresAlfrescoRelationsVO;
	}
	public void fromXml(String xml) {
		XStream xstream = configureXstream();
		ISicresAlfrescoConfigurationVO objectFromXml = (ISicresAlfrescoConfigurationVO)xstream.fromXML(xml);
		this.connectorConfigurationVO = objectFromXml.getConfigurationVO();
		this.sicresAlfrescoRelationsVO = objectFromXml.getSicresAlfrescoRelationsVO();
		
	}
	public String toXml() {
		// TODO Auto-generated method stub
		String result="";
		XStream xstream = configureXstream();		
		result=xstream.toXML(this);
		return result;
		
	}
	
	protected XStream configureXstream(){
		XStream result = new XStream();
		//result.addImplicitCollection(HashMap.class, "hashMapDatosEspecificos");
		result.alias("Configuracion", this.getClass());		
		
		result.aliasField("Aspects", ISicresAlfrescoRelationsVO.class, "listAspects");
		result.alias("Aspect", AlfrescoAspectVO.class);
		
		result.aliasField("TiposDeContenido", ISicresAlfrescoRelationsVO.class, "hashMapDatosEspecificos");		
		result.alias("Valores", ISicresAlfrescoDatosEspecificosValueVO.class);
		
		result.alias("Configuracion", ISicresAlfrescoConfigurationVO.class);
		result.alias("Connector", ISicresBasicDatosEspecificosValueVO.class);
		
		result.aliasField("Conexion", ISicresAlfrescoConfigurationVO.class, "connectorConfigurationVO");		
		result.aliasField("Relaciones", ISicresAlfrescoConfigurationVO.class, "sicresAlfrescoRelationsVO");
		
		result.aliasField("espacio", ISicresAlfrescoRelationsVO.class, "pathSpace");
		result.aliasField("store", ISicresAlfrescoRelationsVO.class, "nameStore");
		result.aliasField("FileKey", ISicresAlfrescoRelationsVO.class, "fileKey");
		
		result.alias("Key", String.class);	
		return result;	
	}
	
}
