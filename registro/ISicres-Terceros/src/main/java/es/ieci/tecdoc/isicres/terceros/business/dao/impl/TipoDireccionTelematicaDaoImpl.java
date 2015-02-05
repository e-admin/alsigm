package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.dao.TipoDireccionTelematicaDao;

/**
 *
 * @author IECISA
 *
 */
public class TipoDireccionTelematicaDaoImpl extends
		IbatisGenericDaoImpl<TipoDireccionTelematicaVO, String> implements
		TipoDireccionTelematicaDao {

	public TipoDireccionTelematicaDaoImpl(
			Class<TipoDireccionTelematicaVO> aPersistentClass) {
		super(aPersistentClass);
	}

}
