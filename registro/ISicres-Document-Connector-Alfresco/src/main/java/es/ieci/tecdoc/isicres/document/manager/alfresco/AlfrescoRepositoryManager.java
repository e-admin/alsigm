package es.ieci.tecdoc.isicres.document.manager.alfresco;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.ieci.tecdoc.common.repository.GenericRepositoryManager;

import es.ieci.tecdoc.isicres.document.connector.alfresco.AlfrescoDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoConnectorConfigurationVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.IsicresBasicConnectorConfigurationVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoConfigurationVO;
import es.ieci.tecdoc.isicres.document.manager.alfresco.vo.ISicresAlfrescoDatosEspecificosValueVO;

/**
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class AlfrescoRepositoryManager extends GenericRepositoryManager {

	public AlfrescoRepositoryManager() {
		super();
		this.connector = new AlfrescoDocumentConnector();
	}

	protected ISicresAbstractDocumentVO getEspecificRepositoryDataForCreate(
			ISicresBasicDocumentVO documentVO) {

		// Instancias y variables
		Map mapDatosEspecificos = new HashMap();
		AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();
		ISicresAlfrescoConfigurationVO isicresAlfrescoConfigurationVO = new ISicresAlfrescoConfigurationVO();
		AlfrescoDatosEspecificosVO alfrescoDatosEspecifivosVO = new AlfrescoDatosEspecificosVO();

		// Lectura del xml de configuracion
		IsicresBasicConnectorConfigurationVO configurationVO = (IsicresBasicConnectorConfigurationVO) documentVO
				.getConfiguration();
		isicresAlfrescoConfigurationVO.fromXml(configurationVO.getConfigXml());
		Map valuesRelations = isicresAlfrescoConfigurationVO
				.getSicresAlfrescoRelationsVO().getHashMapDatosEspecificos();

		// Se recuperan los metadatos de ISicres
		ISicresBasicDatosEspecificosVO datosEspecificosVO = (ISicresBasicDatosEspecificosVO) documentVO
				.getDatosEspecificos();
		Map values = datosEspecificosVO.getValues();

		// Se recorren todas las keys del mapa de valores de ISicres
		ISicresBasicDatosEspecificosValueVO datosEspecificosValueVO = new ISicresBasicDatosEspecificosValueVO();
		Iterator it = values.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ISicresAlfrescoDatosEspecificosValueVO sicresAlfrescoDatosEspecificosValueVO = (ISicresAlfrescoDatosEspecificosValueVO) valuesRelations
					.get(key);
			// Si la clave de ISicres esta en el xml de mapeo se cargan los
			// metadatos
			if (sicresAlfrescoDatosEspecificosValueVO != null) {
				AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = new AlfrescoDatosEspecificosValueVO();
				ISicresBasicDatosEspecificosValueVO sicresBasicDatosEspecificosValueVO = (ISicresBasicDatosEspecificosValueVO) values
						.get(key);

				// AlfrescoDatosEspecificosValueVO metadato
				alfrescoDatosEspecificosValueVO.setValue(sicresBasicDatosEspecificosValueVO.getValue());
				alfrescoDatosEspecificosValueVO.setType(sicresAlfrescoDatosEspecificosValueVO.getType());
				alfrescoDatosEspecificosValueVO.setName(sicresAlfrescoDatosEspecificosValueVO.getName());
				alfrescoDatosEspecificosValueVO.setAspectName(sicresAlfrescoDatosEspecificosValueVO.getAspectName());
				alfrescoDatosEspecificosValueVO.setContentName(sicresAlfrescoDatosEspecificosValueVO.getContentName());

				// Se carga un map con todos los metadatos
				mapDatosEspecificos.put(key, alfrescoDatosEspecificosValueVO);
			}
		}

		// Se cargan en AlfrescoDatosEspecifivosVO el mapa con los metadatos ya
		// cargados
		alfrescoDatosEspecifivosVO.setValues(mapDatosEspecificos);

		// Se cargan en AlfrescoDatosEspecifivosVO la lista de aspectos
		alfrescoDatosEspecifivosVO.setListAspects(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getListAspects());

		// Se cargan la ubicacion del FileKey, espacio y el nombre del store
		alfrescoDatosEspecifivosVO.setPathSpace(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getPathSpace());
		alfrescoDatosEspecifivosVO.setNameStore(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getNameStore());
		alfrescoDatosEspecifivosVO.setFileKey(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getFileKey());

		// Se carga AlfrescoDocumentVO la configuracion de conexion y de
		// valores. Se retorna AlfrescoDocumentVO
		alfrescoDocumentVO.setDatosEspecificos(alfrescoDatosEspecifivosVO);
		AlfrescoConnectorConfigurationVO alfrescoConnectorConfigurationVO = new AlfrescoConnectorConfigurationVO();
		BeanUtils.copyProperties(isicresAlfrescoConfigurationVO.getConfigurationVO(), alfrescoConnectorConfigurationVO);

		// Carga de informacion del file
		alfrescoDocumentVO.setContent(documentVO.getContent());
		alfrescoDocumentVO.setName(documentVO.getName());

		alfrescoDocumentVO.setConfiguration(alfrescoConnectorConfigurationVO);
		return alfrescoDocumentVO;
	}
	
	protected ISicresAbstractDocumentVO getEspecificRepositoryDataForRetrieve(
			ISicresBasicDocumentVO documentVO) {
		// Instancias y variables
		Map mapDatosEspecificos = new HashMap();
		AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();
		ISicresAlfrescoConfigurationVO isicresAlfrescoConfigurationVO = new ISicresAlfrescoConfigurationVO();
		AlfrescoDatosEspecificosVO alfrescoDatosEspecifivosVO = new AlfrescoDatosEspecificosVO();

		// Lectura del xml de configuracion
		IsicresBasicConnectorConfigurationVO configurationVO = (IsicresBasicConnectorConfigurationVO) documentVO
				.getConfiguration();
		isicresAlfrescoConfigurationVO.fromXml(configurationVO.getConfigXml());
		Map valuesRelations = isicresAlfrescoConfigurationVO
				.getSicresAlfrescoRelationsVO().getHashMapDatosEspecificos();

		// Se recuperan los metadatos de ISicres
		ISicresBasicDatosEspecificosVO datosEspecificosVO = (ISicresBasicDatosEspecificosVO) documentVO
				.getDatosEspecificos();
		

		// Se recorren todas las keys del mapa de valores de ISicres		
		Iterator it = valuesRelations.values().iterator();
		while (it.hasNext()) {
			ISicresAlfrescoDatosEspecificosValueVO sicresAlfrescoDatosEspecificosValueVO = (ISicresAlfrescoDatosEspecificosValueVO)  it.next();;
			AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = new AlfrescoDatosEspecificosValueVO();
			alfrescoDatosEspecificosValueVO.setType(sicresAlfrescoDatosEspecificosValueVO.getType());
			alfrescoDatosEspecificosValueVO.setName(sicresAlfrescoDatosEspecificosValueVO.getName());
			alfrescoDatosEspecificosValueVO.setAspectName(sicresAlfrescoDatosEspecificosValueVO.getAspectName());
			alfrescoDatosEspecificosValueVO.setContentName(sicresAlfrescoDatosEspecificosValueVO.getContentName());

			// Se carga un map con todos los metadatos
			mapDatosEspecificos.put(alfrescoDatosEspecificosValueVO.getName(), alfrescoDatosEspecificosValueVO);
			
		}

		// Se cargan en AlfrescoDatosEspecifivosVO el mapa con los metadatos ya
		// cargados
		alfrescoDatosEspecifivosVO.setValues(mapDatosEspecificos);
		alfrescoDatosEspecifivosVO.setFileKey(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getFileKey());

		// Se carga AlfrescoDocumentVO la configuracion de conexion y de
		// valores. Se retorna AlfrescoDocumentVO
		alfrescoDocumentVO.setDatosEspecificos(alfrescoDatosEspecifivosVO);
		AlfrescoConnectorConfigurationVO alfrescoConnectorConfigurationVO = new AlfrescoConnectorConfigurationVO();
		BeanUtils.copyProperties(isicresAlfrescoConfigurationVO.getConfigurationVO(), alfrescoConnectorConfigurationVO);

		// Carga de informacion del file
		alfrescoDocumentVO.setContent(documentVO.getContent());
		alfrescoDocumentVO.setName(documentVO.getName());

		alfrescoDocumentVO.setConfiguration(alfrescoConnectorConfigurationVO);
		alfrescoDocumentVO.setId(documentVO.getId());
		
		return alfrescoDocumentVO;
	}

	protected ISicresAbstractDocumentVO getEspecificRepositoryDataForUpdate(
			ISicresBasicDocumentVO documentVO) {

		// Instancias y variables
		Map mapDatosEspecificos = new HashMap();
		AlfrescoDocumentVO alfrescoDocumentVO = new AlfrescoDocumentVO();
		ISicresAlfrescoConfigurationVO isicresAlfrescoConfigurationVO = new ISicresAlfrescoConfigurationVO();
		AlfrescoDatosEspecificosVO alfrescoDatosEspecifivosVO = new AlfrescoDatosEspecificosVO();

		// Lectura del xml de configuracion
		IsicresBasicConnectorConfigurationVO configurationVO = (IsicresBasicConnectorConfigurationVO) documentVO
				.getConfiguration();
		isicresAlfrescoConfigurationVO.fromXml(configurationVO.getConfigXml());
		Map valuesRelations = isicresAlfrescoConfigurationVO
				.getSicresAlfrescoRelationsVO().getHashMapDatosEspecificos();

		// Se recuperan los metadatos de ISicres
		ISicresBasicDatosEspecificosVO datosEspecificosVO = (ISicresBasicDatosEspecificosVO) documentVO
				.getDatosEspecificos();
		Map values = datosEspecificosVO.getValues();

		// Se recorren todas las keys del mapa de valores de ISicres
		Iterator it = values.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ISicresAlfrescoDatosEspecificosValueVO sicresAlfrescoDatosEspecificosValueVO = (ISicresAlfrescoDatosEspecificosValueVO) valuesRelations
					.get(key);
			// Si la clave de ISicres esta en el xml de mapeo se cargan los
			// metadatos
			if (sicresAlfrescoDatosEspecificosValueVO != null) {
				AlfrescoDatosEspecificosValueVO alfrescoDatosEspecificosValueVO = new AlfrescoDatosEspecificosValueVO();
				ISicresBasicDatosEspecificosValueVO sicresBasicDatosEspecificosValueVO = (ISicresBasicDatosEspecificosValueVO) values
						.get(key);

				// AlfrescoDatosEspecificosValueVO metadato
				alfrescoDatosEspecificosValueVO.setValue(sicresBasicDatosEspecificosValueVO.getValue());
				alfrescoDatosEspecificosValueVO.setType(sicresAlfrescoDatosEspecificosValueVO.getType());
				alfrescoDatosEspecificosValueVO.setName(sicresAlfrescoDatosEspecificosValueVO.getName());
				alfrescoDatosEspecificosValueVO.setAspectName(sicresAlfrescoDatosEspecificosValueVO.getAspectName());
				alfrescoDatosEspecificosValueVO.setContentName(sicresAlfrescoDatosEspecificosValueVO.getContentName());

				// Se carga un map con todos los metadatos
				mapDatosEspecificos.put(key, alfrescoDatosEspecificosValueVO);
			}
		}

		// Se cargan en AlfrescoDatosEspecifivosVO el mapa con los metadatos ya
		// cargados
		alfrescoDatosEspecifivosVO.setValues(mapDatosEspecificos);

		// Se cargan en AlfrescoDatosEspecifivosVO la lista de aspectos
		alfrescoDatosEspecifivosVO
				.setListAspects(isicresAlfrescoConfigurationVO
						.getSicresAlfrescoRelationsVO().getListAspects());
		// Se cargan la ubicacion del espacio y el nombre del store
		alfrescoDatosEspecifivosVO.setPathSpace(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getPathSpace());
		alfrescoDatosEspecifivosVO.setNameStore(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getNameStore());
		alfrescoDatosEspecifivosVO.setFileKey(isicresAlfrescoConfigurationVO.getSicresAlfrescoRelationsVO().getFileKey());
		
		// Se carga AlfrescoDocumentVO la configuracion de conexion y de
		// valores. Se retorna AlfrescoDocumentVO
		alfrescoDocumentVO.setDatosEspecificos(alfrescoDatosEspecifivosVO);
		AlfrescoConnectorConfigurationVO alfrescoConnectorConfigurationVO = new AlfrescoConnectorConfigurationVO();
		BeanUtils.copyProperties(isicresAlfrescoConfigurationVO
				.getConfigurationVO(), alfrescoConnectorConfigurationVO);

		// Carga de informacion del file
		alfrescoDocumentVO.setContent(documentVO.getContent());
		alfrescoDocumentVO.setName(documentVO.getName());

		alfrescoDocumentVO.setConfiguration(alfrescoConnectorConfigurationVO);
		// UIDD
		alfrescoDocumentVO.setId(documentVO.getId());
		return alfrescoDocumentVO;
	}
}
