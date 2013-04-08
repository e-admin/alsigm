package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericReadOnlyDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.PaisDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;

/**
 *
 * @author IECISA
 *
 */
public class PaisDaoImpl extends IbatisGenericReadOnlyDaoImpl<PaisVO, String>
		implements PaisDao {

	public PaisDaoImpl(Class<PaisVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public PaisVO findByCodigo(String codigo) {
		return (PaisVO) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"findByCodigo" }, "."), codigo);
	}

	public PaisVO findByNombre(String nombre) {
		return (PaisVO) getSqlMapClientTemplate().queryForObject(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"findByNombre" }, "."), nombre);
	}

}
