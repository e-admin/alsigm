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

	public List<CiudadVO> getCiudades(int from, int to);

	public Integer getCiudadesCount();



	public Integer getCiudadesCount(ProvinciaVO provincia);
	/**
	 *
	 * @param codigoProvincia
	 * @return
	 */
	public List<CiudadVO> getCiudades(ProvinciaVO provincia);

	public List<CiudadVO> getCiudades(ProvinciaVO provincia, int from, int to);

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

	public List<ProvinciaVO> getProvincias(int from, int to);

	public Integer getProvinciasCount();

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
