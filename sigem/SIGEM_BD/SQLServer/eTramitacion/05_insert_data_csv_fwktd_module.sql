INSERT INTO csv_apps_seq default values;

--
-- Alta de aplicación para el Registro Telemático
--

INSERT INTO csv_aplicaciones(id, codigo, nombre, info_conexion)
	SELECT (SELECT max(id) FROM csv_apps_seq), 'SGM_REG_TEL', 'SIGM - Registro Telemático', '<?xml version=''1.0'' encoding=''UTF-8''?><connection-config><connector>SIGEM_WEB_SERVICE</connector><parameters><parameter name="WSDL_LOCATION">http://localhost:8080/SIGEM_RegistroTelematicoWS/services/AplicacionExternaCSVConnectorWS?wsdl</parameter></parameters></connection-config>';


INSERT INTO csv_apps_seq default values;

--
-- Alta de aplicación para la Tramitación de Expedientes
--

INSERT INTO csv_aplicaciones(id, codigo, nombre, info_conexion)
	SELECT (SELECT max(id) FROM csv_apps_seq), 'SGM_TRAM_EXP', 'SIGM - Tramitación de Expedientes', '<?xml version=''1.0'' encoding=''UTF-8''?><connection-config><connector>SIGEM_WEB_SERVICE</connector><parameters><parameter name="WSDL_LOCATION">http://localhost:8080/SIGEM_TramitacionWS/services/AplicacionExternaCSVConnectorWS?wsdl</parameter></parameters></connection-config>';
