package es.ieci.tecdoc.isicres.terceros.business.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

public interface DireccionManager extends BaseManager<BaseDireccionVO, String> {

	/**
	 * Añade una nueva nueva dirección, ya sea física o telemática, al tercero,
	 * físico o jurídico, que recibe como parámetro.
	 *
	 * @param direccion
	 * @param tercero
	 */
	public void addDireccion(BaseDireccionVO direccion, BaseTerceroVO tercero);

	/**
	 * Elimina la dirección <code>direccion</code> del conjunto de direcciones
	 * asociadas al tercero validado <code>tercero</code>.
	 *
	 * @param direccion
	 * @param tercero
	 */
	public void deleteDireccion(BaseDireccionVO direccion, BaseTerceroVO tercero);

	/**
	 * Actualiza la dirección <code>direccion</code> asociada al tercero
	 * <code>tercero</code>.
	 *
	 * @param direccion
	 * @param tercero
	 */
	public void updateDireccion(BaseDireccionVO direccion, BaseTerceroVO tercero);

	/**
	 * Elimina todas las direcciones asociadas al tercero que se recibe como
	 * parámetro.
	 *
	 * @param tercero
	 */
	public void deleteDirecciones(BaseTerceroVO tercero);

	/**
	 * Devuelve todas las direcciones del tercero <code>tercero</code> que se
	 * del tipo especificado en el parametro <code>tipo</code>.
	 *
	 * @param tercero
	 * @param tipo
	 * @return
	 */
	public List<? extends BaseDireccionVO> getDirecciones(
			BaseTerceroVO tercero, DireccionType tipo);

	/**
	 *
	 * @param tercero
	 * @return
	 */
	public List<? extends BaseDireccionVO> getDirecciones(BaseTerceroVO tercero);

	/**
	 * Selecciona <code>direccion</code> como direccion principal para el
	 * tercero que se pasa como parámetro.
	 *
	 * @param direccion
	 * @param tercero
	 */
	public void selectAsPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero);

}
