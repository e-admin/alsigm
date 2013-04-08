package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;

/**
 *
 * @author IECISA
 *
 */
public interface TipoDocumentoIdentificativoDao extends
		BaseDao<TipoDocumentoIdentificativoTerceroVO, String> {

	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo(
			SearchType type);
}
