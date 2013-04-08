package es.ieci.tecdoc.fwktd.dm.bd.service.impl;

import es.ieci.tecdoc.fwktd.dm.bd.manager.BaseDatosManagerFactory;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceBuilder;

public class BaseDatosServiceBuilder extends GestionDocumentalServiceBuilder {

	private static final String DATABASE_TYPE = "bd";

    private BaseDatosManagerFactory baseDatosManagerFactory = null;


	public BaseDatosServiceBuilder() {
		super(DATABASE_TYPE);
	}

	public BaseDatosServiceBuilder(String type) {
		super(type);
	}

	public BaseDatosManagerFactory getBaseDatosManagerFactory() {
		return baseDatosManagerFactory;
	}

	public void setBaseDatosManagerFactory(
			BaseDatosManagerFactory baseDatosManagerFactory) {
		this.baseDatosManagerFactory = baseDatosManagerFactory;
	}

	public GestionDocumentalService createGestionDocumentalService(ContentType contentType) {

		BaseDatosServiceImpl service = new BaseDatosServiceImpl(contentType);

		if (baseDatosManagerFactory != null) {
			service.setBaseDatosManager(baseDatosManagerFactory.getBaseDatosManager(contentType));
		}

		return service;
	}
}
