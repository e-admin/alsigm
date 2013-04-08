package es.ieci.tecdoc.sicres.terceros.web.delegate.impl;

import java.util.List;

import es.ieci.tecdoc.isicres.terceros.business.manager.MasterValuesManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;
import es.ieci.tecdoc.sicres.terceros.web.delegate.MasterValuesDelegate;

/**
 *
 * @author IECISA
 *
 */
public class MasterValuesDelegateImpl implements MasterValuesDelegate {

	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo() {
		return getMasterValuesManager().getTiposDocumentoIdentificativo();
	}

	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo(
			SearchType type) {
		return getMasterValuesManager().getTiposDocumentoIdentificativo(type);
	}

	public List<TipoDireccionTelematicaVO> getTiposDireccionesTelematicas() {
		return getMasterValuesManager().getTiposDireccionesTelematicas();
	}

	public List<CiudadVO> getCiudades() {
		return getMasterValuesManager().getCiudades();
	}

	public List<CiudadVO> getCiudades(ProvinciaVO provincia) {
		return getMasterValuesManager().getCiudades(provincia);
	}

	public List<ProvinciaVO> getProvincias() {
		return getMasterValuesManager().getProvincias();
	}

	public List<PaisVO> getPaises() {
		return getMasterValuesManager().getPaises();
	}

	public CiudadVO getCiudad(String codigo) {
		return getMasterValuesManager().getCiudad(codigo);
	}

	public ProvinciaVO getProvincia(String codigo) {
		return getMasterValuesManager().getProvincia(codigo);
	}

	public PaisVO getPais(String codigo) {
		return getMasterValuesManager().getPais(codigo);
	}

	public MasterValuesManager getMasterValuesManager() {
		return masterValuesManager;
	}

	public void setMasterValuesManager(MasterValuesManager masterValuesManager) {
		this.masterValuesManager = masterValuesManager;
	}

	protected MasterValuesManager masterValuesManager;

}