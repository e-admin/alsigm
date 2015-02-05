package es.ieci.tecdoc.fwktd.dm.business.service.impl;

import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;


/**
 * Implementación abstracta del servicio de gestión documental.
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class DefaultGestionDocumentalServiceImpl implements GestionDocumentalService {

	/**
	 * Información del tipo documental.
	 */
	private ContentType contentType = null;


	/**
	 * Constructor.
	 */
	public DefaultGestionDocumentalServiceImpl() {
		super();
	}

	/**
	 * Constructor.
	 * @param contentType Información del tipo documental.
	 */
	public DefaultGestionDocumentalServiceImpl(ContentType contentType) {
		super();
		setContentType(contentType);
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

}
