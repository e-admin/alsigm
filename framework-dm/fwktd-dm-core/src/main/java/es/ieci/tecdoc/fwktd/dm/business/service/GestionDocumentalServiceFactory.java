package es.ieci.tecdoc.fwktd.dm.business.service;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

public interface GestionDocumentalServiceFactory {

	public GestionDocumentalService getService(String contentTypeId);

	public GestionDocumentalService getService(ContentType contentType);

}
