package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.DireccionTelematicaDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;

/**
 *
 * @author IECISA
 *
 */
class DireccionTelematicaDaoImpl extends
		IbatisGenericDaoImpl<DireccionTelematicaVO, String> implements
		DireccionTelematicaDao {

	public DireccionTelematicaDaoImpl(
			Class<DireccionTelematicaVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public void deleteAll(BaseTerceroVO tercero) {
		getSqlMapClientTemplate().delete(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"deleteDireccionTelematicaVOByTercero" }, "."),
				tercero.getId());
	}

	public void updateDireccionPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero) {
		Assert.notNull(direccion);
		Assert.notNull(tercero);

		getSqlMapClientTemplate().update(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"unassignDireccionPrincipalTercero" }, "."),
				tercero);
		getSqlMapClientTemplate().update(
				StringUtils.join(
						new String[] {
								ClassUtils.getShortName(getPersistentClass()),
								"assignDireccionPrincipalTercero" }, "."),
				direccion);
	}
}
