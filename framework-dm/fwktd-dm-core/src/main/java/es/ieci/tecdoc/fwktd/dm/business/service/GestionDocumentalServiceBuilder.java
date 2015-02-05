package es.ieci.tecdoc.fwktd.dm.business.service;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

public abstract class GestionDocumentalServiceBuilder {

	protected GestionDocumentalServiceFactory gestionDocumentalServiceFactory = null;

	protected GestionDocumentalServiceBuilder(String type) {
		registerMe(type);
	}

	public void registerMe(String type) {
		GestionDocumentalServiceBuilderRegistry.getInstance().register(type, this);
	}

	public abstract GestionDocumentalService createGestionDocumentalService(ContentType contentType);

}
