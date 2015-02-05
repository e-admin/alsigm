--
-- Alta de aplicación para el Registro Telemático
--

INSERT INTO csv_aplicaciones(id, codigo, nombre, info_conexion)
	VALUES (nextval('csv_apps_seq'), 'SGM_REG_TEL', 'SIGM - Registro Telemático', '<?xml version=''1.0'' encoding=''UTF-8''?><connection-config><connector>SIGEM_WEB_SERVICE</connector><parameters><parameter name="WSDL_LOCATION">http://localhost:8080/SIGEM_RegistroTelematicoWS/services/AplicacionExternaCSVConnectorWS?wsdl</parameter></parameters></connection-config>');


--
-- Alta de aplicación para la Tramitación de Expedientes
--

INSERT INTO csv_aplicaciones(id, codigo, nombre, info_conexion)
	VALUES (nextval('csv_apps_seq'), 'SGM_TRAM_EXP', 'SIGM - Tramitación de Expedientes', '<?xml version=''1.0'' encoding=''UTF-8''?><connection-config><connector>SIGEM_WEB_SERVICE</connector><parameters><parameter name="WSDL_LOCATION">http://localhost:8080/SIGEM_TramitacionWS/services/AplicacionExternaCSVConnectorWS?wsdl</parameter></parameters></connection-config>');
