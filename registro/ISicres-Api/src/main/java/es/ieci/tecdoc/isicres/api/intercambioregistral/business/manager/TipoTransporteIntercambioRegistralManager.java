package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO;

public interface TipoTransporteIntercambioRegistralManager {

	/**
	 * Devuelve un tipo de transporte a partir de la descripcion.
	 *
	 * Primero busca en la descripción por defecto y si no lo encuentra busca en
	 * las tablas de internacionalización.
	 *
	 * @param descripcion
	 *            Descripción del tipo de transporte
	 *
	 * @return Tipo de Transporte
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByDesc(
			String descripcion);

	/**
	 * Devuelve un tipo de transporte a partir del codigo SIR
	 *
	 * @param codigo
	 *            Código SIR
	 *
	 * @return
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByCodigo(
			String codigo);

	/**
	 * Devuelve el número total de tipos de transporte a partir del codigo SIR
	 *
	 * @param codigo
	 *            Código SIR
	 *
	 * @return int
	 */
	public int getCountTipoTransporteByCodigo(
			String codigo);

	/**
	 * Devuelve un tipo de transporte a partir del codigo SIR
	 *
	 * @param codigo
	 *            Código SIR
	 * @param idScrTT
	 *            id del tipo de transporte de ISicres (SCR_TT)
	 *
	 * @return TipoTransporteIntercambioRegistralVO
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByCodigoAndIDScrTT(
			String codigo, Integer idScrTT);

	/**
	 * Devuelve un tipo de transporte a partir del identificador de SICRES
	 *
	 * @param codigo
	 *            Identificador del tipo de transporte en Sicres
	 *
	 * @return
	 */
	public TipoTransporteIntercambioRegistralVO getTipoTransporteByIdSicres(
			Integer idTipoTransporte);

	/**
	 * Actualiza el código SIR del tipo de transporte a partir de la
	 * descripción.
	 *
	 * @param tipoTransporteIntercambioRegistralVO
	 */
	public void updateByIdTipoTransporte(
			TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO);

	/**
	 * Crea un nuevo mapeo de tipo de transporte para intercambio registral.
	 *
	 * Mapea la descripción del tipo de tranporte en sicres con el código del
	 * tipo de transporte en SIR.
	 *
	 * @param tipoTransporteIntercambioRegistralVO
	 * @return
	 */
	public TipoTransporteIntercambioRegistralVO save(
			TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO);

	/**
	 * Elimina el mapeo de tipo de transporte para intercambio registral.
	 *
	 *
	 * @param id Identificador del tipo de transporte
	 */
	public void delete(Long id);

}
