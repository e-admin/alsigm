package es.ieci.tecdoc.fwktd.dm.alfresco.service.impl;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceBuilder;

public class AlfrescoServiceBuilder extends GestionDocumentalServiceBuilder {

	protected static final String ALFRESCO_BUILDER_TYPE 	= "alfresco";

	protected static final String ALFRESCO_ENDPOINT_ADDRESS	= "ALFRESCO_ENDPOINT_ADDRESS";
	protected static final String ALFRESCO_USERNAME			= "ALFRESCO_USERNAME";
	protected static final String ALFRESCO_PASSWORD			= "ALFRESCO_PASSWORD";
	protected static final String ALFRESCO_TYPE				= "ALFRESCO_TYPE";
	protected static final String ALFRESCO_ASPECTS			= "ALFRESCO_ASPECTS";
	protected static final String ALFRESCO_PATH				= "ALFRESCO_PATH";
	protected static final String ALFRESCO_ENCODING			= "ALFRESCO_ENCODING";
	protected static final String ALFRESCO_STORE_NAMESPACE	= "ALFRESCO_STORE_NAMESPACE";


	public AlfrescoServiceBuilder() {
		super(ALFRESCO_BUILDER_TYPE);
	}

	public AlfrescoServiceBuilder(String type) {
		super(type);
	}

	public GestionDocumentalService createGestionDocumentalService(ContentType contentType) {

		AlfrescoServiceImpl service = new AlfrescoServiceImpl(contentType);

    	if (contentType != null) {

    		// Establecer la URL del servicio de Alfresco
			service.setEndPointAddress(contentType.getProperty(ALFRESCO_ENDPOINT_ADDRESS));

    		// Establecer el usuario de Alfresco
    		service.setUser(contentType.getProperty(ALFRESCO_USERNAME));

    		// Establecer la clave del usuario de Alfresco
    		service.setPassword(contentType.getProperty(ALFRESCO_PASSWORD));

    		// Establecer el tipo de contenido de Alfresco
    		if (StringUtils.isNotBlank(contentType.getProperty(ALFRESCO_TYPE))) {
    			service.setType(contentType.getProperty(ALFRESCO_TYPE));
    		}

    		// Establecer los aspectos para el tipo de contenido
    		service.setAspects(contentType.getProperty(ALFRESCO_ASPECTS));

    		// Establecer el path para el tipo de contenido
    		service.setPath(contentType.getProperty(ALFRESCO_PATH));

    		// Establecer el encoding para el tipo de contenido
    		if (StringUtils.isNotBlank(contentType.getProperty(ALFRESCO_ENCODING))) {
    			service.setEncoding(contentType.getProperty(ALFRESCO_ENCODING));
    		}

    		// Establecer el namespace para el Store
    		if (StringUtils.isNotBlank(contentType.getProperty(ALFRESCO_STORE_NAMESPACE))) {
    			service.setStoreNamespace(contentType.getProperty(ALFRESCO_STORE_NAMESPACE));
    		}
    	}

    	return service;
	}

}
