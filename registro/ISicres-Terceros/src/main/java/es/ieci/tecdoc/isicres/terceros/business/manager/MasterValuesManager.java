package es.ieci.tecdoc.isicres.terceros.business.manager;

import java.util.List;

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
public interface MasterValuesManager {

	/**
	 *
	 * @return
	 */
	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo();

	/**
	 *
	 * @param type
	 * @return
	 */
	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentoIdentificativo(
			SearchType type);

	/**
	 *
	 * @return
	 */
	public List<TipoDireccionTelematicaVO> getTiposDireccionesTelematicas();

	/**
	 *
	 * @return
	 */
	public List<CiudadVO> getCiudades();

	/**
	 *
	 * @param codigoProvincia
	 * @return
	 */
	public List<CiudadVO> getCiudades(ProvinciaVO provincia);

	/**
	 *
	 * @return
	 */
	public CiudadVO getCiudad(String codigo);

	/**
	 *
	 * @return
	 */
	public CiudadVO getCiudadById(Long id);
	
	/**
	 *
	 *Devuelve una ciudad a partir de su nombre
	 *
	 * @return
	 */
	public CiudadVO getCiudadByNombre(String nombreCiudad);

	/**
	 *
	 * @return
	 */
	public List<ProvinciaVO> getProvincias();

	/**
	 *
	 * @param codigo
	 * @return
	 */
	public ProvinciaVO getProvincia(String codigo);

	public ProvinciaVO getProvinciaById(Long id);
	
	/**
	 * Devuelve una provincia a partir de su nombre
	 * @param nombre
	 * @return
	 */
	public ProvinciaVO getProvinciaByNombre(String nombre);

	/**
	 *
	 * @return
	 */
	public List<PaisVO> getPaises();

	/**
	 *
	 * @param codigo
	 * @return
	 */
	public PaisVO getPais(String codigo);
}
