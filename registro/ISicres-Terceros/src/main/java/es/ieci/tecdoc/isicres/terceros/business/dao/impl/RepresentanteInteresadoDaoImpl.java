package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.RepresentanteInteresadoDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.RepresentanteInteresadoVO;

/**
 *
 * @author IECISA
 *
 */
public class RepresentanteInteresadoDaoImpl extends
		IbatisGenericDaoImpl<RepresentanteInteresadoVO, String> implements
		RepresentanteInteresadoDao {

	public RepresentanteInteresadoDaoImpl(Class<RepresentanteInteresadoVO> aPersistentClass) {
		super(aPersistentClass);
	}

}
