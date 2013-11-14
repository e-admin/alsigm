package es.ieci.tecdoc.isicres.api.business.dao;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public interface OficinaDAO {

	/**
	 * Obtiene OficinaVO mediante el codigo de la oficina
	 * @param codOficina
	 * @return
	 */
	public OficinaVO getOficinaByCodigo(Locale locale, String codOficina);

	/**
	 * Obtiene las oficinas a las que pertenece un usuario
	 * @param usuario
	 * @return
	 */
	public List<OficinaVO> getOficinasByUsuario(Locale locale, UsuarioVO usuario);

	/**
	 * obtiene la oficina mediante el identificador de oficina
	 * @param idOficina
	 * @return
	 */
	public OficinaVO getOficinaById(Locale locale,String idOficina);

	/**
	 * obtiene la oficina mediante el identificador de departamento
	 * @param idOficina
	 * @return
	 */
	public OficinaVO getOficinaByIdDepartamento(Locale locale,String idDepartamento);

	/**
	 * Devuelve todas las oficinas
	 *
	 * @param Locale localización de las oficinas
	 *
	 * @return
	 */
	public List<OficinaVO> getOficinas(Locale locale);


}
