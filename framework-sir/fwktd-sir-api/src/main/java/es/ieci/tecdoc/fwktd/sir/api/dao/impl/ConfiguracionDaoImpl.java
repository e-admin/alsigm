package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.ConfiguracionDao;
import es.ieci.tecdoc.fwktd.sir.api.vo.ConfiguracionVO;

/**
 * DAO de parámetros de configuración.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ConfiguracionDaoImpl extends
		IbatisGenericDaoImpl<ConfiguracionVO, String> implements
		ConfiguracionDao {

	protected static final String GET_VALORES_CONFIGURACION = "ConfiguracionVO.getValoresConfiguracion";
	protected static final String GET_VALOR_CONFIGURACION = "ConfiguracionVO.getValorConfiguracion";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 * 
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public ConfiguracionDaoImpl(Class<ConfiguracionVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.ConfiguracionDao#getValorConfiguracion(java.lang.String)
	 */
	public String getValorConfiguracion(String nombre) {

		logger.info("Obteniendo el valor de configuración [{}]", nombre);

		return (String) getSqlMapClientTemplate().queryForObject(
				GET_VALOR_CONFIGURACION, nombre);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.ConfiguracionDao#getValoresConfiguracion(java.lang.String[])
	 */
	public Map<String, String> getValoresConfiguracion(String[] nombres) {

		logger.info(
				"Obteniendo los valores de los parámetros de configuración [{}]",
				StringUtils.join(nombres, ", "));
		
		Map<String, String> valoresMap = new HashMap<String, String>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nombres", nombres);

		@SuppressWarnings("unchecked")
		List<ConfiguracionVO> parametros = (List<ConfiguracionVO>) getSqlMapClientTemplate().queryForList(
				GET_VALORES_CONFIGURACION, map);
		if (!CollectionUtils.isEmpty(parametros)) {
			for (ConfiguracionVO parametro : parametros) {
				valoresMap.put(parametro.getNombre(), parametro.getValor());
			}
		}
		
		return valoresMap;
	}
}
