package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericReadOnlyDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.ProvinciaDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;

/**
 *
 * @author IECISA
 *
 */
public class ProvinciaDaoImpl extends
		IbatisGenericReadOnlyDaoImpl<ProvinciaVO, String> implements
		ProvinciaDao {

	public ProvinciaDaoImpl(Class<ProvinciaVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public ProvinciaVO findByCodigo(String codigo) {
		return (ProvinciaVO) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"findByCodigo" }, "."), codigo);
	}

	public ProvinciaVO findByNombre(String nombre) {
		return (ProvinciaVO) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"findByNombre" }, "."), nombre);
	}

}
