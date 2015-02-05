package es.ieci.tecdoc.fwktd.dm.bd.manager.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import es.ieci.tecdoc.fwktd.dm.bd.manager.BaseDatosManager;
import es.ieci.tecdoc.fwktd.dm.bd.manager.BaseDatosManagerFactory;
import es.ieci.tecdoc.fwktd.dm.business.config.ContentType;

/**
 * Implementación de la factoría que obtiene el manager de gestión documental
 * en base de datos configurado para un tipo documental.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseDatosManagerFactoryImpl implements BaseDatosManagerFactory,
		ApplicationContextAware {

	private String baseDatosManagerBean = null;

	private ApplicationContext applicationContext = null;


	/**
	 * Constructor.
	 */
	public BaseDatosManagerFactoryImpl() {
	}

	public String getBaseDatosManagerBean() {
		return baseDatosManagerBean;
	}

	public void setBaseDatosManagerBean(String baseDatosManagerBean) {
		this.baseDatosManagerBean = baseDatosManagerBean;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Obtiene el gestor a partir de la configuración del
	 * tipo de contenido.
	 * @param contentType Tipo de contenido.
	 * @return Gestor.
	 */
	public BaseDatosManager getBaseDatosManager(ContentType contentType) {

		BaseDatosManager baseDatosManager = null;

		if (getApplicationContext() != null) {
			baseDatosManager = (BaseDatosManager) getApplicationContext().getBean(getBaseDatosManagerBean());
			if (baseDatosManager != null) {
				baseDatosManager.setContentType(contentType);
			}
		}

		return baseDatosManager;
	}
}
