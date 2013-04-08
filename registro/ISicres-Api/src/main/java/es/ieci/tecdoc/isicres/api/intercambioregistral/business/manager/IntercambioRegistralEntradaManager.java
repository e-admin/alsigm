package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

/**
 * Interfaz relacionada con las operaciones referentes a un intercambio registral de entrada
 *
 */
public interface IntercambioRegistralEntradaManager {


	/**
	 * Obtiene la bandeja de entrada de Intercambio Registral para el <code>estado</code> e <code>idOficina</code> pasados como parámetros.
	 * @param estado
	 * @param idOficina
	 * @return Lista con todos los intercambios de la bandeja de enrada que cumplan los criterios
	 */
	public List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistral(Integer estado, Integer idOficina);


	/**
	 * metodo que acepta un intercambio registral de entrada
	 * @param idIntercambioRegistralEntrada
	 * @param idLibro
	 * @param user
	 * @param password
	 * @param idOficina
	 * @param codOficina
	 * @param llegoDocFisica
	 */
	public void aceptarIntercambioRegistralEntradaById(String idIntercambioRegistralEntrada, String idLibro, String user, Integer idOficina, String codOficina, boolean llegoDocFisica);

	/**
	 * Método que rechaza un intercambio registral de entrada
	 * @param idIntercambioRegistralEntrada
	 * @param tipoREchazo
	 * @param observaciones
	 */
	public void rechazarIntercambioRegistralEntradaById(String idIntercambioRegistralEntrada, String tipoRechazo, String observaciones);

	/**
	 * Método que reenvia un intercambio registral de entrada
	 * 
	 * Almacena el asiento en SICRES con el estado REENVIADO y actualiza el 
	 * estado del intercambio en el módulo SIR.
	 * 
	 * @param id
	 * @param usuario
	 * @param contacto
	 * @param descripcionReenvio
	 * @param nuevoDestino
	 */
	public void reenviarIntercambioRegistralEntradaById(
			String idIntercambioRegistralEntrada,
			UnidadTramitacionIntercambioRegistralVO nuevoDestino,
			String observaciones);
	
	/**
	 * Guardar el intercambio registral en scr_exregaccept indicando que ya se ha aceptado el intermcabio y se ha creado el registro correspondiente
	 */
	public void guardarIntercambioRegistralEntrada(IntercambioRegistralEntradaVO intercambioRegistralEntrada);


	/**
	 * Metodo que a partir de un elemento de la bandeja de entrada con los datos de SCR_EXREGACCEPT, completa los datos
	 * necesarios de otras tablas (AXSF) como numero de registro o unidad de origen.
	 * @param bandejaEntradaItemVO
	 * @return
	 */
	public BandejaEntradaItemVO completarBandejaEntradaItem(BandejaEntradaItemVO bandejaEntradaItemVO);

	/**
	 * Metodo que obtiene el historial con el intermcabio registral de un registro
	 * @param idLibro
	 * @param idRegistro
	 * @param idOficina
	 * @return Listado de objetos {@link IntercambioRegistralEntradaVO}
	 */
	public List<IntercambioRegistralEntradaVO> getHistorialIntercambioRegistralEntrada(String idLibro, String idRegistro,  String idOficina);
}
