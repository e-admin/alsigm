package es.ieci.tecdoc.isiscres.document.test.vo;

import com.thoughtworks.xstream.converters.collections.MapConverter;

import junit.framework.TestCase;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoAspectVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoConfigurationVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoConnectorConfigurationVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoRelationsVO;

public class ISicresAlfrescoConfigurationVOTest extends TestCase{
	
	public void testToXml(){
		ISicresAlfrescoConfigurationVO configurationVO = new ISicresAlfrescoConfigurationVO();
		ISicresAlfrescoRelationsVO alfrescoRelationsVO = new ISicresAlfrescoRelationsVO();
		ISicresAlfrescoConnectorConfigurationVO connectorVO= new ISicresAlfrescoConnectorConfigurationVO();
		
		
		connectorVO.setIp("localhost");
		connectorVO.setPass("admin");
		connectorVO.setUsuario("1234");
		connectorVO.setPuerto("8080");
		
		
		// Aspect 1
		ISicresAlfrescoAspectVO aspectVO1 = new ISicresAlfrescoAspectVO();
		aspectVO1.setNameAspect("isicres:aspectRegistro");
		aspectVO1.setNameContent("http://es.iecisa/isicres");
		
		alfrescoRelationsVO.getListAspects().add(aspectVO1);

		
		
		alfrescoRelationsVO.setPathSpace("/app:company_home/cm:name=isicres");
		alfrescoRelationsVO.setNameStore("Store");
		alfrescoRelationsVO.setFileKey("FileName");
		
		
		// DatosEspecifco 1
		ISicresAlfrescoDatosEspecificosValueVO especificosValueVO1 = new ISicresAlfrescoDatosEspecificosValueVO();		
		especificosValueVO1.setAspectName("isicres:aspectRegistro");
		especificosValueVO1.setContentName("http://es.iecisa/isicres");
		especificosValueVO1.setName("RegisterUser");
		especificosValueVO1.setType("text");
		
		alfrescoRelationsVO.getHashMapDatosEspecificos().put("RegisterUser", especificosValueVO1);

		ISicresAlfrescoDatosEspecificosValueVO especificosValueVO2 = new ISicresAlfrescoDatosEspecificosValueVO();		
		especificosValueVO2.setAspectName("isicres:aspectRegistro");
		especificosValueVO2.setContentName("http://es.iecisa/isicres");
		especificosValueVO2.setName("BookType");
		especificosValueVO2.setType("int");		
		alfrescoRelationsVO.getHashMapDatosEspecificos().put("BookType", especificosValueVO2);
		
		
		configurationVO.setConfigurationVO(connectorVO);
		configurationVO.setSicresAlfrescoRelationsVO(alfrescoRelationsVO);
		
		String xml = configurationVO.toXml();
		
		assertNotNull(xml);
		
		ISicresAlfrescoConfigurationVO configurationVOVOFromXML = new ISicresAlfrescoConfigurationVO();
		
		configurationVOVOFromXML.fromXml(xml);
		
		assertNotNull(configurationVOVOFromXML.getConfigurationVO().getIp());
		
		
	}

}
