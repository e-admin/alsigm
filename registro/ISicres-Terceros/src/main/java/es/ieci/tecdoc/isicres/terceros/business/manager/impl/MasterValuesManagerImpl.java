package es.ieci.tecdoc.isicres.terceros.business.manager.impl;

import java.util.List;

import es.ieci.tecdoc.isicres.terceros.business.dao.CiudadDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.PaisDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.ProvinciaDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.TipoDireccionTelematicaDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.TipoDocumentoIdentificativoDao;
import es.ieci.tecdoc.isicres.terceros.business.manager.MasterValuesManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;

/**
 *
 * @author IECISA
 *
 */
public class MasterValuesManagerImpl implements MasterValuesManager {

	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo() {
		return getTipoDocumentoIdentificativoDao().getAll();
	}

	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo(
			SearchType type) {
		return getTipoDocumentoIdentificativoDao()
				.getTiposDocumentoIdentificativo(type);
	}

	public List<TipoDireccionTelematicaVO> getTiposDireccionesTelematicas() {
		return getTipoDireccionTelematicaDao().getAll();
	}

	public List<CiudadVO> getCiudades() {
		return getCiudadDao().getAll();
	}

	public List<CiudadVO> getCiudades(int from, int to){
		return getCiudadDao().getCiudades(from, to);
	}

	public Integer getCiudadesCount(){
		return getCiudadDao().getCiudadesCount();
	}


	public Integer getCiudadesCount(ProvinciaVO provincia) {
		return (getCiudadDao()).getCiudadesByProvinciaCount(provincia);
	}

	public List<CiudadVO> getCiudades(ProvinciaVO provincia) {
		return ((CiudadDao) getCiudadDao()).getCiudadesByProvincia(provincia);
	}

	public List<CiudadVO> getCiudades(ProvinciaVO provincia, int from, int to) {
		return ((CiudadDao) getCiudadDao()).getCiudadesByProvincia(provincia, from, to);
	}


	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.manager.MasterValuesManager#getCiudadByNombre(java.lang.String)
	 */
	public CiudadVO getCiudadByNombre(String nombreCiudad) {
		//TODO: ¿Podría haber más de una ciudad con el mismo nombre?
		return ((CiudadDao) getCiudadDao()).findByNombre(nombreCiudad);
	}

	public List<ProvinciaVO> getProvincias() {
		return getProvinciaDao().getAll();
	}

	public List<ProvinciaVO> getProvincias(int from, int to) {
		return getProvinciaDao().getProvincias(from, to);
	}

	public Integer getProvinciasCount() {
		return getProvinciaDao().getProvinciasCount();
	}

	public List<PaisVO> getPaises() {
		return getPaisDao().getAll();
	}

	public CiudadVO getCiudad(String codigo) {
		return getCiudadDao().findByCodigo(codigo);
	}



	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.manager.MasterValuesManager#getCiudad(java.lang.Long)
	 */
	public CiudadVO getCiudadById(Long id) {

		return ciudadDao.get(String.valueOf(id));
	}

	public ProvinciaVO getProvincia(String codigo) {
		return getProvinciaDao().findByCodigo(codigo);
	}



	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.manager.MasterValuesManager#getProvinciaById(java.lang.Long)
	 */
	public ProvinciaVO getProvinciaById(Long id) {
		return getProvinciaDao().get(String.valueOf(id));
	}

	public ProvinciaVO getProvinciaByNombre(String nombre) {
		return getProvinciaDao().findByNombre(nombre);
	}

	public PaisVO getPais(String codigo) {
		return getPaisDao().findByCodigo(codigo);
	}

	public TipoDocumentoIdentificativoDao getTipoDocumentoIdentificativoDao() {
		return tipoDocumentoIdentificativoDao;
	}

	public void setTipoDocumentoIdentificativoDao(
			TipoDocumentoIdentificativoDao tipoDocumentoIdentificativoDao) {
		this.tipoDocumentoIdentificativoDao = tipoDocumentoIdentificativoDao;
	}

	public TipoDireccionTelematicaDao getTipoDireccionTelematicaDao() {
		return tipoDireccionTelematicaDao;
	}

	public void setTipoDireccionTelematicaDao(
			TipoDireccionTelematicaDao tipoDireccionTelematicaDao) {
		this.tipoDireccionTelematicaDao = tipoDireccionTelematicaDao;
	}

	public CiudadDao getCiudadDao() {
		return ciudadDao;
	}

	public void setCiudadDao(CiudadDao ciudadDao) {
		this.ciudadDao = ciudadDao;
	}

	public ProvinciaDao getProvinciaDao() {
		return provinciaDao;
	}

	public void setProvinciaDao(ProvinciaDao provinciaDao) {
		this.provinciaDao = provinciaDao;
	}

	public PaisDao getPaisDao() {
		return paisDao;
	}

	public void setPaisDao(PaisDao paisDao) {
		this.paisDao = paisDao;
	}

	protected TipoDocumentoIdentificativoDao tipoDocumentoIdentificativoDao;

	protected TipoDireccionTelematicaDao tipoDireccionTelematicaDao;

	protected CiudadDao ciudadDao;

	protected ProvinciaDao provinciaDao;

	protected PaisDao paisDao;

}
