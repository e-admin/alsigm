package es.ieci.tecdoc.sicres.terceros.web.delegate.impl;

import java.util.List;

import es.ieci.tecdoc.isicres.terceros.business.manager.DireccionManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.MasterValuesManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 *
 * @author IECISA
 *
 */
public class TercerosFacadeImpl implements TercerosFacade {

	public Integer count(CriteriaVO criteria) {
		return ((TerceroManager) getTerceroManager()).countByCriteria(criteria);
	}

	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria) {
		return ((TerceroManager) getTerceroManager()).findByCriteria(criteria);
	}

	public TerceroValidadoVO create(TerceroValidadoVO tercero) {
		return getTerceroManager().save(tercero);
	}

	public TerceroValidadoVO update(TerceroValidadoVO tercero) {
		return getTerceroManager().update(tercero);
	}

	public TerceroValidadoVO get(String id) {
		return getTerceroManager().get(id);
	}

	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentosIdentificacion() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<BaseDireccionVO> getDirecciones(BaseTerceroVO tercero) {
		return (List<BaseDireccionVO>) getDireccionManager().getDirecciones(
				tercero);
	}

	@SuppressWarnings("unchecked")
	public List<BaseDireccionVO> getDirecciones(BaseTerceroVO tercero,
			DireccionType tipoDireccion) {
		return (List<BaseDireccionVO>) getDireccionManager().getDirecciones(
				(TerceroValidadoVO) tercero, tipoDireccion);
	}

	public List<ProvinciaVO> getProvincias() {
		return getMasterValuesManager().getProvincias();
	}

	public List<CiudadVO> getCiudades(ProvinciaVO provincia) {
		return getMasterValuesManager().getCiudades();
	}

	// Members

	public List<TerceroValidadoVO> getAll() {
		return getTerceroManager().getAll();
	}

	public TerceroManager getTerceroManager() {
		return terceroManager;
	}

	public void setTerceroManager(TerceroManager terceroManager) {
		this.terceroManager = terceroManager;
	}

	public DireccionManager getDireccionManager() {
		return direccionManager;
	}

	public void setDireccionManager(DireccionManager direccionManager) {
		this.direccionManager = direccionManager;
	}

	public MasterValuesManager getMasterValuesManager() {
		return masterValuesManager;
	}

	public void setMasterValuesManager(MasterValuesManager masterValuesManager) {
		this.masterValuesManager = masterValuesManager;
	}

	protected TerceroManager terceroManager;

	protected DireccionManager direccionManager;

	protected MasterValuesManager masterValuesManager;
}