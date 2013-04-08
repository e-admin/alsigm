package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.TipoDocumentoIdentificativoDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;

/**
 *
 * @author IECISA
 *
 */
public class TipoDocumentoIdentificativoDaoImpl extends
		IbatisGenericDaoImpl<TipoDocumentoIdentificativoTerceroVO, String>
		implements TipoDocumentoIdentificativoDao {

	public TipoDocumentoIdentificativoDaoImpl(
			Class<TipoDocumentoIdentificativoTerceroVO> aPersistentClass) {
		super(aPersistentClass);
	}

	@SuppressWarnings("unchecked")
	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo(
			SearchType type) {
		return getSqlMapClientTemplate()
				.queryForList(
						"TipoDocumentoIdentificativoTerceroVO.getTipoDocumentoIdentificativoTerceroByTypePerson",
						type);
	}

}
