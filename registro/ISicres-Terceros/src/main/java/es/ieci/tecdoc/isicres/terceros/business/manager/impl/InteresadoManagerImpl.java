package es.ieci.tecdoc.isicres.terceros.business.manager.impl;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.InteresadoDao;
import es.ieci.tecdoc.isicres.terceros.business.manager.InteresadoManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.RepresentanteInteresadoManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 *
 * @author IECISA
 *
 */
public class InteresadoManagerImpl extends
		BaseManagerImpl<InteresadoVO, String> implements InteresadoManager {

	public InteresadoManagerImpl(BaseDao<InteresadoVO, String> aGenericDao) {
		super(aGenericDao);
	}

	/**
	 * Elimina todos los interesados asociados al libro con identificador
	 * <code>idLibro</code> y al registro con identificador
	 * <code>idRegistro</code>.
	 */
	public void deleteAll(String idLibro, String idRegistro) {
		List<InteresadoVO> interesados = ((InteresadoDao) getDao())
				.getInteresados(idLibro, idRegistro);

		deleteAll(interesados);
	}

	@Override
	/**
	 * Elimina al interesado con identificador interno <code>anId</code> y a su representante en caso de tenerlo.
	 */
	public void delete(String anId) {
		InteresadoVO interesado = get(anId);

		if (null != interesado.getRepresentante()) {
			getRepresentanteInteresadoManager().delete(
					interesado.getRepresentante().getId());
		}

		super.delete(anId);
	}

	@Override
	public InteresadoVO save(InteresadoVO anEntity) {
		InteresadoVO interesado = super.save(anEntity);

		if (null != anEntity.getRepresentante()) {
			getRepresentanteInteresadoManager().save(
					anEntity.getRepresentante());
		}

		return interesado;
	}

	public List<InteresadoVO> getAll(String idLibro, String idRegistro) {
		return ((InteresadoDao) getDao()).getInteresados(idLibro, idRegistro);
	}

	public RepresentanteInteresadoManager getRepresentanteInteresadoManager() {
		return representanteInteresadoManager;
	}

	public void setRepresentanteInteresadoManager(
			RepresentanteInteresadoManager representanteInteresadoManager) {
		this.representanteInteresadoManager = representanteInteresadoManager;
	}

	protected RepresentanteInteresadoManager representanteInteresadoManager;

}
