package es.ieci.tecdoc.fwktd.dm.invesdoc.service.impl;

import ieci.tecdoc.core.db.DbConnectionConfig;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceBuilder;

public class InvesdocServiceBuilder extends GestionDocumentalServiceBuilder {

	protected static final String INVESDOC_BUILDER_TYPE 	= "invesdoc";

    protected static final String JDBC_DATASOURCE 			= "JDBC_DATASOURCE";
    protected static final String JDBC_DRIVER_CLASS_NAME 	= "JDBC_DRIVER_CLASS_NAME";
    protected static final String JDBC_URL 					= "JDBC_URL";
    protected static final String JDBC_USERNAME 			= "JDBC_USERNAME";
    protected static final String JDBC_PASSWORD 			= "JDBC_PASSWORD";

    protected static final String ARCHIVE_ID 				= "ARCHIVE_ID";
    protected static final String USER_ID 					= "USER_ID";
    protected static final String TEMPORARY_PATH 			= "TEMPORARY_PATH";


	public InvesdocServiceBuilder() {
		super(INVESDOC_BUILDER_TYPE);
	}

	public InvesdocServiceBuilder(String type) {
		super(type);
	}

	public GestionDocumentalService createGestionDocumentalService(ContentType contentType) {

		InvesdocServiceImpl service = new InvesdocServiceImpl(contentType);

    	if (contentType != null) {

    		// Establecer el identificador del archivador
    		String strIdArchivador = contentType.getProperty(ARCHIVE_ID);
    		if (StringUtils.isNumeric(strIdArchivador)) {
    			service.setIdArchivador(Integer.parseInt(strIdArchivador));
    		}

    		// Establecer el identificador del usuario en invesDoc
    		String strIdUsuario = contentType.getProperty(USER_ID);
    		if (StringUtils.isNumeric(strIdUsuario)) {
    			service.setIdUsuario(Integer.parseInt(strIdUsuario));
    		}

    		// Establecer el directorio temporal para los ficheros
    		service.setDirectorioTemporal(contentType.getProperty(TEMPORARY_PATH));

    		// Establecer la configuración de la base de datos
    		String datasource = contentType.getProperty(JDBC_DATASOURCE);
    		if (StringUtils.isNotBlank(datasource)) {
    			service.setDbConnectionConfig(new DbConnectionConfig(datasource, null, null));
    		} else {
    			String jdbcUrl = contentType.getProperty(JDBC_URL);
    			if (StringUtils.isNotBlank(jdbcUrl)) {
    				service.setDbConnectionConfig(new DbConnectionConfig(
    						contentType.getProperty(JDBC_DRIVER_CLASS_NAME),
    						jdbcUrl,
    						contentType.getProperty(JDBC_USERNAME),
    						contentType.getProperty(JDBC_PASSWORD)));
    			}
    		}
    	}

    	return service;
	}

}
