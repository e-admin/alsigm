package es.ieci.tecdoc.isiscres.document.test.vo;

import junit.framework.TestCase;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoConnectorConfigurationVO;

public class ISicresAlfrescoConnectorConfigurationVOTest extends TestCase {
	
	public void testToXml(){		
		ISicresAlfrescoConnectorConfigurationVO connectorVO= new ISicresAlfrescoConnectorConfigurationVO();
			
		connectorVO.setIp("19.228.10.200");
		connectorVO.setPass("ieci");
		connectorVO.setUsuario("ieci");
		connectorVO.setPuerto("8080");
						
		String xml=connectorVO.toXml();
		
		assertNotNull(xml);
		
		ISicresAlfrescoConnectorConfigurationVO connectorVOFromXML = new ISicresAlfrescoConnectorConfigurationVO();
		
		connectorVOFromXML.fromXml(xml);
		
		assertNotNull(connectorVOFromXML.getIp());
		assertNotNull(connectorVOFromXML.getPass());
		assertNotNull(connectorVOFromXML.getPuerto());
		assertNotNull(connectorVOFromXML.getUsuario());
		
		assertEquals(xml, connectorVOFromXML.toXml());		
	}
}
