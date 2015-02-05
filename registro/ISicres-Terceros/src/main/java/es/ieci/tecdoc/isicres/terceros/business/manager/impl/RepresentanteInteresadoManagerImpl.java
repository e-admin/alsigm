package es.ieci.tecdoc.isicres.terceros.business.manager.impl;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.isicres.terceros.business.manager.RepresentanteInteresadoManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.RepresentanteInteresadoVO;

/**
 *
 * @author IECISA
 *
 */
public class RepresentanteInteresadoManagerImpl extends
		BaseManagerImpl<RepresentanteInteresadoVO, String> implements
		RepresentanteInteresadoManager {

	public RepresentanteInteresadoManagerImpl(BaseDao<RepresentanteInteresadoVO, String> aGenericDao) {
		super(aGenericDao);
	}
}
