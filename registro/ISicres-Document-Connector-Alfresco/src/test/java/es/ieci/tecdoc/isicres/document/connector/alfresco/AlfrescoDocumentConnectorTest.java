package es.ieci.tecdoc.isicres.document.connector.alfresco;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.alfresco.webservice.util.ContentUtils;

import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoAspectVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoConnectorConfigurationVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;
import es.ieci.tecdoc.isicres.document.test.utils.UtilsTest;
import junit.framework.TestCase;

public class AlfrescoDocumentConnectorTest extends TestCase{
	private AlfrescoDatosEspecificosVO datosEspecificosVO;
	private byte[] contentBytesCretate;
	private byte[] contentBytesUpdate;
	
	public void testUpdate(){
		// Variables
		AlfrescoDocumentConnector connector = new AlfrescoDocumentConnector();
		AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();		
		ISicresAbstractDocumentVO abstractDocumentVO = null;
		ISicresAbstractDocumentVO abstractDocumentUpdateVO = null;
		
		// Datos de conexion
		AlfrescoConnectorConfigurationVO configurationVO = UtilsTest.getConnection();	
		
		// DatosEspecificos y configuracion		
		alfrescoDocumentVO.setDatosEspecificos(datosEspecificosVO);
		alfrescoDocumentVO.setConfiguration(configurationVO);
			
		// Carga de byte[] del contenido
		alfrescoDocumentVO.setContent(contentBytesCretate);
		alfrescoDocumentVO.setName("afrescoDoc.doc");
		
		// Creacion de contenido		
		abstractDocumentVO =connector.create(alfrescoDocumentVO);		
		assertNotNull("uuid - de la carga",abstractDocumentVO.getId());		

		// Carga de byte[] de actualizacion
		alfrescoDocumentVO.setContent(contentBytesUpdate);
		alfrescoDocumentVO.setName("afrescoDoc.doc");	
		alfrescoDocumentVO.setId(abstractDocumentVO.getId());	
		
		// Actualizacion de contenido		
		abstractDocumentUpdateVO = connector.update(alfrescoDocumentVO);		
		assertNotNull("Update de documento",abstractDocumentUpdateVO);	
		
		// Borrado de contenidos
		alfrescoDocumentVO.setId(abstractDocumentVO.getId());
		connector.delete(alfrescoDocumentVO);
	}
	
	
	public void testRetrieve(){
		// Variables
		AlfrescoDocumentConnector connector = new AlfrescoDocumentConnector();
		AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();
		ISicresAbstractDocumentVO documentVO = new AlfrescoDocumentVO();	
		ISicresAbstractDocumentVO documentRetrieveVO = null;
					
		// Datos de conexion
		AlfrescoConnectorConfigurationVO configurationVO = UtilsTest.getConnection();
		assertNotNull("Datos de conexion",configurationVO);

		// Carga de datosEspecificos y Configuracion
		alfrescoDocumentVO.setDatosEspecificos(datosEspecificosVO);
		alfrescoDocumentVO.setConfiguration(configurationVO);
		
		// Carga de Bytes[]
		alfrescoDocumentVO.setContent(contentBytesCretate);
		alfrescoDocumentVO.setName("afrescoDoc.doc");
		
		// Creacion del contenido
		documentVO = connector.create(alfrescoDocumentVO);
		alfrescoDocumentVO.setId(documentVO.getId());	
		
		// Retrieve del contenido
		documentRetrieveVO = connector.retrieve(alfrescoDocumentVO);		
		assertNotNull("Retorno del detalle",documentRetrieveVO);
		
		// Delete del contenido
		connector.delete(documentRetrieveVO);
	}
	
	public void testCreate(){			
		
		// Variables
		AlfrescoDocumentConnector connector = new AlfrescoDocumentConnector();
		AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();		
		
		// Datos de conexion
		AlfrescoConnectorConfigurationVO configurationVO = UtilsTest.getConnection();
		
		// Carga de datosEspecificos y configuracion
		alfrescoDocumentVO.setDatosEspecificos(datosEspecificosVO);
		alfrescoDocumentVO.setConfiguration(configurationVO);
				
		// Carga de Bytes[]
		alfrescoDocumentVO.setContent(contentBytesCretate);
		alfrescoDocumentVO.setName("afrescoDoc.doc");	
		
		// Creacion del contenido		
		ISicresAbstractDocumentVO abstractDocumentVO = connector.create(alfrescoDocumentVO);		
		assertNotNull("Creación de contenido",abstractDocumentVO);	
		
		// Borrado del contenido
		alfrescoDocumentVO.setId(abstractDocumentVO.getId());
		connector.delete(alfrescoDocumentVO);
	}
	
	public void setUp(){
		// Variables
		Map datosEspecificos = new HashMap();
		datosEspecificosVO = new AlfrescoDatosEspecificosVO();
		
		// Metadatos
		AlfrescoDatosEspecificosValueVO metadato_1 = new AlfrescoDatosEspecificosValueVO();
		metadato_1.setAspectName("aspectRegistro");
		metadato_1.setType("text");
		metadato_1.setContentName("isicres.model");
		metadato_1.setName("RegisterUser");		
		metadato_1.setValue("Carlos");
		datosEspecificos.put("RegisterUser", metadato_1);
		
		AlfrescoDatosEspecificosValueVO metadato_2 = new AlfrescoDatosEspecificosValueVO();
		metadato_2.setAspectName("aspectRegistro");
		metadato_2.setType("text");
		metadato_2.setContentName("isicres.model");
		metadato_2.setName("BookType");		
		metadato_2.setValue("Entrada");
		datosEspecificos.put("BookType", metadato_2);
		
		AlfrescoDatosEspecificosValueVO metadato_3 = new AlfrescoDatosEspecificosValueVO();
		metadato_3.setAspectName("aspectRegistro");
		metadato_3.setType("text");
		metadato_3.setContentName("isicres.model");
		metadato_3.setName("FileName");
		metadato_3.setValue("afrescoDoc.doc");
		datosEspecificos.put("FileName", metadato_3);		
		
		// Carga del Aspecto
		AlfrescoAspectVO aspectVO1 = new AlfrescoAspectVO();
		aspectVO1.setNameAspect("aspectRegistro");
		aspectVO1.setNameContent("isicres.model");		
		
		List listAspect = new LinkedList();
		listAspect.add(aspectVO1);
		
		// Carga de metadatos
		datosEspecificosVO.setValues(datosEspecificos);
		datosEspecificosVO.setFileKey("FileName");
		datosEspecificosVO.setListAspects(listAspect);
		datosEspecificosVO.setNameStore("Store");
		datosEspecificosVO.setPathSpace("/app:company_home/cm:isicres");

		// bytes[] create
		InputStream viewStreamCreate;
		contentBytesCretate = null;
		try {
			viewStreamCreate = this.getClass().getClassLoader().getSystemResourceAsStream("afrescoDoc.doc");	
			contentBytesCretate = ContentUtils.convertToByteArray(viewStreamCreate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// byte[] update
		InputStream viewStreamUpdate;
		contentBytesUpdate = null;
		try {
			viewStreamUpdate = this.getClass().getClassLoader().getSystemResourceAsStream("Copia de afrescoDoc.doc");	
			contentBytesUpdate = ContentUtils.convertToByteArray(viewStreamUpdate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
