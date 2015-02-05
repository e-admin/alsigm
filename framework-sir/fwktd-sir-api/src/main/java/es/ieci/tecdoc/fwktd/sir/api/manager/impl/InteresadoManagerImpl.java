package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.InteresadoDao;
import es.ieci.tecdoc.fwktd.sir.api.manager.InteresadoManager;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 * Implementación del manager de interesados de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InteresadoManagerImpl extends
		BaseManagerImpl<InteresadoVO, String> implements InteresadoManager {

	public InteresadoManagerImpl(BaseDao<InteresadoVO, String> aGenericDao) {
		super(aGenericDao);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.InteresadoManager#saveInteresado(java.lang.String, es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO)
	 */
	public InteresadoVO saveInteresado(String idAsientoRegistral, InteresadoFormVO interesadoForm) {

        InteresadoVO interesado = new InteresadoVO();
        BeanUtils.copyProperties(interesadoForm, interesado);
        interesado.setIdAsientoRegistral(idAsientoRegistral);

        // Guardar la información del interesado
        return save(interesado);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.InteresadoManager#deleteByIdAsientoRegistral(java.lang.String)
	 */
	public void deleteByIdAsientoRegistral(String idAsientoRegistral) {

		Assert.hasText(idAsientoRegistral,
				"'idAsientoRegistral' must not be empty");

		logger.info(
				"Eliminando interesados del asiento registral con identificador [{}]",
				idAsientoRegistral);

		((InteresadoDao) getDao()).deleteByIdAsientoRegistral(idAsientoRegistral);
	}

}
