package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.InteresadoDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 *
 * @author IECISA
 *
 */
public class InteresadoDaoImpl extends
		IbatisGenericDaoImpl<InteresadoVO, String> implements InteresadoDao {

	public InteresadoDaoImpl(Class<InteresadoVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public List<InteresadoVO> getInteresados(String idLibro, String idRegistro) {
		Assert.hasText(idRegistro);
		Assert.hasText(idLibro);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idLibro", idLibro);
		parameters.put("idRegistro", idRegistro);

		return getSqlMapClientTemplate().queryForList(
				ClassUtils.getShortName(getPersistentClass())
						+ ".getInteresadosByRegistro", parameters);
	}

}
