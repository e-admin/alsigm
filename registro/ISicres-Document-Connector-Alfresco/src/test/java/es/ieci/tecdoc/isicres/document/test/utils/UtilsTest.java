package es.ieci.tecdoc.isicres.document.test.utils;

import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoConnectorConfigurationVO;

public class UtilsTest {

	public static AlfrescoConnectorConfigurationVO getConnection(){
		AlfrescoConnectorConfigurationVO configurationVO = new AlfrescoConnectorConfigurationVO();

		// Datos de conexion
		configurationVO.setIp("10.228.20.97");
		configurationVO.setPass("1234");
		configurationVO.setUsuario("ieci");
		configurationVO.setPuerto("8080");

		return configurationVO;
	}
}
