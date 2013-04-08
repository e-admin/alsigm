package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.ConfiguracionDao;
import es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager;
import es.ieci.tecdoc.fwktd.sir.api.vo.ConfiguracionVO;

/**
 * Implementación del manager de parámetros de configuración.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ConfiguracionManagerImpl extends BaseManagerImpl<ConfiguracionVO, String>
		implements ConfiguracionManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfiguracionManagerImpl.class);

	/**
	 * Constructor.
	 *
	 * @param aGenericDao
	 */
	public ConfiguracionManagerImpl(BaseDao<ConfiguracionVO, String> aGenericDao) {
		super(aGenericDao);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager#
	 *      getValorConfiguracion(java.lang.String)
	 */
	public String getValorConfiguracion(String nombre) {

		Assert.hasText(nombre, "'nombre' must not be empty");

		logger.info("Obteniendo el valor del parámetro de configuración [{}]", nombre);

		String valor = ((ConfiguracionDao)getDao()).getValorConfiguracion(nombre);

		logger.debug("Parámetro [{}] => [{}]", nombre, valor);

		return valor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.ConfiguracionManager#getValorConfiguracion(java.lang.String[])
	 */
	public String getValorConfiguracion(String[] nombres) {
		
		Assert.notNull(nombres, "'nombres' must not be null");
		
		logger.info(
				"Obteniendo los valores de los parámetros de configuración [{}]",
				StringUtils.join(nombres, ", "));

		Map<String, String> valores = ((ConfiguracionDao)getDao()).getValoresConfiguracion(nombres);
		if (!MapUtils.isEmpty(valores)) {
			String valor = null;
			for (String nombre : nombres) {
				valor = valores.get(nombre);
				if (StringUtils.isNotBlank(valor)) {
					logger.debug("Parámetro [{}] => [{}]", nombre, valor);
					return valor;
				}
			}
		}

		return null;
	}

}
