package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;

/**
 * DAO para leer y actualizar datos de la bandeja de entrada de intercambio registral
 *
 */
public interface BandejaEntradaIntercambioRegistralDAO {

	/**
	 * Guarda un intercambio registral que acabamos de aceptar
	 * @param intecambioRegistralEntrada
	 */
	public void save(IntercambioRegistralEntradaVO intecambioRegistralEntrada);

	/**
	 * Metodo que obtiene la informacion del estado del intercambio
	 * @param {{@link List} - Listado de objetos IntercambioRegistralEntradaVO
	 */
	public List<IntercambioRegistralEntradaVO> getInfoEstado(IntercambioRegistralEntradaVO intecambioRegistralEntrada);

	/**
	 * Obtiene la bandeja de entrada para el siguiente <code>estado</code>
	 * @param estado
	 * @return
	 */
	public List<BandejaEntradaItemVO> getBandejaEntradaByEstado(Integer estado,Integer idOficina);

	/**
	 * Completa datos del elemento <code>bandejaEntradaItemVO</code> que no se cargan
	 * al leer la bandeja de entrada completa
	 * @param bandejaEntradaItemVO
	 * @return
	 */
	public BandejaEntradaItemVO completarBandejaEntradaItem(BandejaEntradaItemVO bandejaEntradaItemVO);
}
