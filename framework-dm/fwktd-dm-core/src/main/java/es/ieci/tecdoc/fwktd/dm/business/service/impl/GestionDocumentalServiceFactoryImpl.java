package es.ieci.tecdoc.fwktd.dm.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.dm.business.config.Configuration;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceBuilder;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceBuilderRegistry;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;

public class GestionDocumentalServiceFactoryImpl implements
		GestionDocumentalServiceFactory {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(GestionDocumentalServiceFactory.class);


    /**
     * Configuración de tipos documentales
     */
    private Configuration configuration = null;


    /**
     * Constructor
     */
    public GestionDocumentalServiceFactoryImpl() {
        super();
    }

    /**
     * Constructor.
     * @param configuration Configuración de tipos documentales
     */
    public GestionDocumentalServiceFactoryImpl(Configuration configuration) {
        super();
        setConfiguration(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public GestionDocumentalService getService(String contentTypeId) {
        GestionDocumentalService service = null;

        logger.debug("Buscando el servicio del tipo documental: {}", contentTypeId);

        if (getConfiguration() != null) {
            ContentType contentType = getConfiguration().findContentType(contentTypeId);
            service = getService(contentType);
        }

        return service;
    }

    public GestionDocumentalService getService(ContentType contentType) {
        GestionDocumentalService service = null;

        logger.debug("Buscando el servicio del tipo documental: {}", contentType);

		if (contentType != null) {

			logger.debug("Tipo del tipo documental: {}", contentType.getType());

			GestionDocumentalServiceBuilder builder = GestionDocumentalServiceBuilderRegistry
					.getInstance().getBuilder(contentType.getType());
			logger.debug("Builder: {}", builder);

			if (builder != null) {
				service = builder.createGestionDocumentalService(contentType);
			}
		}

		logger.debug("GestionDocumentalService: {}", service);

        return service;
    }

}
