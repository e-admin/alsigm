package es.ieci.tecdoc.isiscres.document.test.vo;

import com.thoughtworks.xstream.converters.collections.MapConverter;

import junit.framework.TestCase;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoAspectVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoConfigurationVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoRelationsVO;

public class ISicresAlfrescoRelationsVOTest extends TestCase{
	
	public void testToXml(){
		ISicresAlfrescoRelationsVO alfrescoConRelationsVO = new ISicresAlfrescoRelationsVO();
		
		// Aspect 1
		ISicresAlfrescoAspectVO aspectVO1 = new ISicresAlfrescoAspectVO();
		aspectVO1.setNameAspect("Persona");
		aspectVO1.setNameContent("http://ieci/persona");
		
		alfrescoConRelationsVO.getListAspects().add(aspectVO1);
		
		// Aspect 2
		ISicresAlfrescoAspectVO aspectVO2 = new ISicresAlfrescoAspectVO();
		aspectVO2.setNameAspect("Persona");
		aspectVO2.setNameContent("http://ieci/persona");
		
		alfrescoConRelationsVO.getListAspects().add(aspectVO2);
		
		
		// DatosEspecifco 1
		ISicresAlfrescoDatosEspecificosValueVO especificosValueVO1 = new ISicresAlfrescoDatosEspecificosValueVO();		
		especificosValueVO1.setAspectName("asas");
		especificosValueVO1.setContentName("asdasd");
		especificosValueVO1.setName("asdafas");
		especificosValueVO1.setType("int");		
		alfrescoConRelationsVO.getHashMapDatosEspecificos().put("1312", especificosValueVO1);

		ISicresAlfrescoDatosEspecificosValueVO especificosValueVO2 = new ISicresAlfrescoDatosEspecificosValueVO();		
		especificosValueVO2.setAspectName("asas");
		especificosValueVO2.setContentName("asdasd");
		especificosValueVO2.setName("asdafas");
		especificosValueVO2.setType("int");		
		alfrescoConRelationsVO.getHashMapDatosEspecificos().put("123123", especificosValueVO2);
		
		// DatosEspecifco 2		
		String xml = alfrescoConRelationsVO.toXml();
		
		assertNotNull(xml);
		
		ISicresAlfrescoRelationsVO alfrescoConRelationsVOFromXML = new ISicresAlfrescoRelationsVO();
		
		alfrescoConRelationsVOFromXML.fromXml(xml);
		
		assertNotNull(((ISicresAlfrescoAspectVO)alfrescoConRelationsVOFromXML.getListAspects().get(0)).getNameAspect());
		
		
	}

}
