package es.ieci.tecdoc.isicres.terceros.business.manager.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.TerceroDao;
import es.ieci.tecdoc.isicres.terceros.business.manager.DireccionManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;

/**
 *
 * @author IECISA
 *
 */
public class TerceroManagerImpl extends
		BaseManagerImpl<TerceroValidadoVO, String> implements TerceroManager {

	public TerceroManagerImpl(BaseDao<TerceroValidadoVO, String> aGenericDao) {
		super(aGenericDao);
	}

	/**
	 *
	 */
	@Override
	public TerceroValidadoVO save(TerceroValidadoVO anEntity) {
		TerceroValidadoVO tercero = super.save(anEntity);

		for (BaseDireccionVO direccion : tercero.getDirecciones()) {
			direccion.setTercero(tercero);
			getDireccionManager().save(direccion);
		}

		return tercero;
	}

	@Override
	public void delete(String anId) {
		super.delete(anId);

	}

	@SuppressWarnings("unchecked")
	public TerceroValidadoVO updateAll(TerceroValidadoVO anEntity) {
		TerceroValidadoVO tercero = this.update(anEntity);

		TerceroValidadoVO terceroInDb = get(anEntity.getId());

		List<BaseDireccionVO> direcciones = terceroInDb.getDirecciones();

		// Direcciones a eliminar
		List<BaseDireccionVO> direccionesToRemove = (List<BaseDireccionVO>) CollectionUtils
				.subtract(direcciones, anEntity.getDirecciones());
		for (BaseDireccionVO direccion : direccionesToRemove) {
			getDireccionManager().delete(direccion.getId());
		}

		// Direcciones a añadir
		List<BaseDireccionVO> direccionesToAdd = (List<BaseDireccionVO>) CollectionUtils
				.subtract(anEntity.getDirecciones(), direcciones);
		for (BaseDireccionVO direccion : direccionesToAdd) {
			direccion.setTercero(tercero);
			getDireccionManager().save(direccion);
		}

		// Direcciones a actualizar
		List<BaseDireccionVO> direccionesToUpdate = (List<BaseDireccionVO>) CollectionUtils
				.intersection(anEntity.getDirecciones(), direcciones);
		for (BaseDireccionVO direccion : direccionesToUpdate) {
			getDireccionManager().update(direccion);
		}

		return tercero;
	}

	

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager#findTerceroJuridicoByDocumentNumber(java.lang.String, int)
	 */
	public List<TerceroValidadoFisicoVO> findTerceroJuridicoByDocumentNumber(String phisicalNumber,
			int typeDoc) {
		
		return ((TerceroDao) getDao()).findTerceroJuridicoByDocumentNumber(phisicalNumber, typeDoc);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager#findTerceroFisicoByDocumentNumber(java.lang.String, int)
	 */
	public List<TerceroValidadoFisicoVO> findTerceroFisicoByDocumentNumber(String phisicalNumber,
			int typeDoc) {
		return ((TerceroDao) getDao()).findTerceroFisicoByDocumentNumber(phisicalNumber, typeDoc);
	}

	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria) {
		return ((TerceroDao) getDao()).findByCriteria(criteria);
	}

	public Integer countByCriteria(CriteriaVO criteria){
		return ((TerceroDao) getDao()).countByCriteria(criteria);
	}

	public DireccionManager getDireccionManager() {
		return direccionManager;
	}

	public void setDireccionManager(DireccionManager direccionManager) {
		this.direccionManager = direccionManager;
	}

	// Members
	protected DireccionManager direccionManager;

}