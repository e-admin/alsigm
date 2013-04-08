package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public abstract class AuditoriaManagerBaseImpl {

    private static final Logger logger = LoggerFactory
            .getLogger(AuditoriaManagerBaseImpl.class);

	/**
	 * Gestor de configuración.
	 */
	private ConfiguracionManager configuracionManager = null;

	/**
	 * Indica si la auditoría está activada (por defecto)
	 */
	private boolean defaultActivado = false;

	/**
	 * Identificador del tipo de contenido en el módulo de gestión documental.
	 */
	private String defaultContentTypeId = null;

	/**
	 * Indica si se está ejecutando en un test.
	 */
	private boolean test = false;

	/**
	 * Constructor.
	 */
	public AuditoriaManagerBaseImpl() {
		super();
	}

	public ConfiguracionManager getConfiguracionManager() {
		return configuracionManager;
	}

	public void setConfiguracionManager(ConfiguracionManager configuracionManager) {
		this.configuracionManager = configuracionManager;
	}

	public boolean isDefaultActivado() {
		return defaultActivado;
	}

	public void setDefaultActivado(boolean defaultActivado) {
		this.defaultActivado = defaultActivado;
	}

	public String getDefaultContentTypeId() {
		return defaultContentTypeId;
	}

	public void setDefaultContentTypeId(String defaultContentTypeId) {
		this.defaultContentTypeId = defaultContentTypeId;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
	
	public boolean isActivado() {
		
		boolean activado = isDefaultActivado();
		
		if (getConfiguracionManager() != null) {
			
			// Nombre del parámetro de configuración
			String paramName = getActivadoParamName();
			
			// Comprobar la configuración en base de datos
			String valor = getConfiguracionManager().getValorConfiguracion(
					paramName);
			logger.info("Valor de {} en base de datos: [{}]", paramName,
					valor);
			
			activado = Boolean.valueOf(valor);
		}

		return activado;
	}
	
	public String getContentTypeId() {

		String contentTypeId = getDefaultContentTypeId();
		
		if (getConfiguracionManager() != null) {
			
			// Nombre del parámetro de configuración
			String paramName = getContentTypeIdParamName();
			
			// Comprobar la configuración en base de datos
			String valor = getConfiguracionManager().getValorConfiguracion(
					paramName);
			logger.info("Valor de {} en base de datos: [{}]", paramName,
					valor);
			
			if (StringUtils.isNotBlank(valor)) {
				contentTypeId = valor;
			}
		}

		return contentTypeId;

	}

	/**
	 * Obtiene el nombre del parámetro de configuración que indica si la
	 * auditoría está activada,
	 * 
	 * @return Nombre del parámetro.
	 */
	protected abstract String getActivadoParamName();
	
	/**
	 * Obtiene el nombre del parámetro de configuración que indica el nombre del
	 * repositorio documental para guardar los ficheros de intercambio o
	 * mensajes,
	 * 
	 * @return Nombre del parámetro.
	 */
	protected abstract String getContentTypeIdParamName();
	
}
