package ieci.tecdoc.sgm.core.services.pago;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

import java.util.List;


public interface ServicioPagoTelematico {

	/**
	 * Método que da de alta una liquidación
	 * @param poLiquidacion Datos de la liquidación
	 * @return Datos de la liquidación
	 * @throws PagoTelematicoException
	 */
	public Liquidacion altaLiquidacion(Liquidacion poLiquidacion, Entidad entidad) throws PagoTelematicoException;
	
	/**
	 * Método que da de baja una liquidación
	 * @param poLiquidacion Datos de la liquidación
	 * @throws PagoTelematicoException
	 */
	public void bajaLiquidacion(Liquidacion poLiquidacion, Entidad entidad) throws PagoTelematicoException;
	
	/**
	 * Método que actualiza una liquidación
	 * @param poLiquidacion Datos actualizados de la liquidación
	 * @throws PagoTelematicoException
	 */
	public void modificarLiquidacion(Liquidacion poLiquidacion, Entidad entidad)	throws PagoTelematicoException;
	
	/**
	 * Método que obtiene una lista de liquidaciones a partir de unos parámetros de búsqueda
	 * @param poCriterio Parámetros de búsqueda
	 * @return Lista de liquidaciones
	 * @throws PagoTelematicoException
	 */
	public List buscarLiquidaciones(CriterioBusquedaLiquidacion poCriterio, Entidad entidad) throws PagoTelematicoException;
	
	/**
	 * Método que obtiene detalle de un pago
	 * @param pcIdTasa Identificador de la tasa
	 * @param pcIdEntidadEmisora Identificador de la entidad emisora
	 * @return Datos de la tasa
	 * @throws PagoTelematicoException
	 */
	public Tasa obtenerDatosTasa(String pcIdTasa, String pcIdEntidadEmisora, Entidad entidad) throws PagoTelematicoException;
	
	/**
	 * Método que obtiene una lista de tasas a partir de unos parámetros de búsqueda
	 * @param poCriterio Parámetros de búsqueda
	 * @return Lista de tasas
	 * @throws PagoTelematicoException
	 */
	public List buscarTasas(CriterioBusquedaTasa poCriterio, Entidad entidad) throws PagoTelematicoException;

	/**
	 * Método que obtiene el documento asociado a un pago
	 * @param poPago Datos del pago
	 * @return Ruta del documento
	 * @throws PagoTelematicoException
	 */
	public String obtenerDocumentoPago(Pago poPago, Entidad entidad) throws PagoTelematicoException;
	
	/**
	 * Método que realiza un pago
	 * @param poPago Datos del pago
	 * @return Datos del pago realizado
	 * @throws PagoTelematicoException
	 */
	public Pago realizarPago(Pago poPago, Entidad entidad) throws PagoTelematicoException;
	
	/**
	 * Método que obtiene el detalle de un pago
	 * @param pcNumReferencia Identificador del pago
	 * @return Datos del pago
	 * @throws PagoTelematicoException
	 */
	public Pago detallePago(String pcNumReferencia, Entidad entidad) throws PagoTelematicoException;
	
}
