package ieci.tecdoc.sgm.pe.cuadernos.impl;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.pe.CuadernoBase;
import ieci.tecdoc.sgm.pe.Liquidacion;
import ieci.tecdoc.sgm.pe.Pago;
import ieci.tecdoc.sgm.pe.PagoImpl;
import ieci.tecdoc.sgm.pe.Util;
import ieci.tecdoc.sgm.pe.cuadernos.IManejadorCuaderno;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

public abstract class ManejadorCuadernoBase implements IManejadorCuaderno{
	
	protected abstract PagoImpl pagoCuaderno(PagoImpl pago) throws PagoElectronicoExcepcion;
	
	public PagoImpl invocarSistemaPago(PagoImpl poPago) throws PagoElectronicoExcepcion{
		PagoImpl pago;
		// obtener instancia del cuaderno	
		try {
			pago = pagoCuaderno(poPago);
		} catch (PagoElectronicoExcepcion e) {
			StringBuffer sbError = new StringBuffer("Error invocando al servicio de pago. Referencia: ");
			sbError.append(poPago.getReferencia());
			Logger.getLogger(this.getClass()).error(sbError.toString());
			throw e;
		}
//	{
//		StringBuffer sbError = new StringBuffer("Error, tipo de liquidación no soportada: ");
//		sbError.append(poPago.getLiquidacion().getTasa().getTipo());
//		logger.error(sbError.toString());
//		throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_NO_PERMITIDO);
//	}
		pago.setReferencia(poPago.getReferencia());
		pago.setEstado(Pago.ESTADO_PAGADO);
		return pago;
	}
	
	/**
	 * Método que valida los datos de las liquidaciones/autoliquidaciones
	 * @param poLiquidacion Datos de la liquidación
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */
	public void validarLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		Logger logger=Logger.getLogger(this.getClass());
		if(poLiquidacion == null){
			logger.error("Los datos de la liquidación/autoliquidacion son null.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);
		}
				
		// id_tasa
		if(	(poLiquidacion.getIdTasa() == null)
			|| (poLiquidacion.getIdTasa().length() < 1) || (poLiquidacion.getIdTasa().length() > 3)	){
			logger.error("Parámetro IdTasa incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}

		// nif
		if(	(poLiquidacion.getNif() == null)
			|| (poLiquidacion.getNif().length() < 1) || (poLiquidacion.getNif().length() > 9) ){
			logger.error("Parámetro Nif incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}

		// importe
		if(	(poLiquidacion.getImporte() == null)
			|| (poLiquidacion.getImporte().length() < 1) || (poLiquidacion.getImporte().length() > 12) ){
			logger.error("Parámetro Importe incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}else{
			poLiquidacion.setImporte(Util.formatearImporte(poLiquidacion.getImporte(), 12));
		}

		// estado
		if(poLiquidacion.getEstado() != null){
			if(	(!Liquidacion.ESTADO_PAGADO.equals(poLiquidacion.getEstado()))
					&&(!Liquidacion.ESTADO_PENDIENTE.equals(poLiquidacion.getEstado())) ){
				logger.error("Parámetro Estado incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);							
			}
		}else{
			poLiquidacion.setEstado(Liquidacion.ESTADO_PENDIENTE);
		}
	}
	
	/**
	 * Método que valida los datos comunes a pagos correspondientes a liquidaciones
	 * @param poPago Datos del pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación
	 */
	public void validarPago(PagoImpl poPago) throws PagoElectronicoExcepcion{
		Logger logger=Logger.getLogger(this.getClass());
		// fecha
		if(poPago.getFecha() != null){
			if(	(poPago.getFecha().length() < 1) || (poPago.getFecha().length() > 8) ){
				logger.error("Parámetro fecha incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
			}			
		}
		// hora
		if(poPago.getHora() != null){
			if(	(poPago.getHora().length() < 1) || (poPago.getHora().length() > 4) ){
				logger.error("Parámetro Hora incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
			}			
		}			
	}
	
	public abstract String generarNumeroReferencia(DbConnection conn, Liquidacion poLiquidacion) throws PagoElectronicoExcepcion;
	
	protected PagoImpl rellenarPagoComunCuadernos(PagoImpl pago,CuadernoBase cuaderno,PagoImpl datosPagoEnviado){
		if(cuaderno==null){
			if(datosPagoEnviado==null) return pago;
			pago.setIdEntidadEmisora(datosPagoEnviado.getIdEntidadEmisora());
			pago.setIdTasa(datosPagoEnviado.getIdTasa());
			return datosPagoEnviado;
		}
		pago.setFecha(StringUtils.isEmpty(cuaderno.getFechaPago())?cuaderno.getFecha():cuaderno.getFechaPago());
		pago.setHora(StringUtils.isEmpty(cuaderno.getHoraPago())?cuaderno.getHora():cuaderno.getHoraPago());
		pago.setIdioma(cuaderno.getIdioma());
		pago.setImporte(cuaderno.getImporte());
		pago.setNif(cuaderno.getNifCertificado());
		pago.setNrc(cuaderno.getNrc());
		pago.setIdEntidadEmisora(cuaderno.getOrganismoEmisor());
		pago.setReferencia(cuaderno.getReferencia());
		pago.setPeticionPagoPasarelaExternaConRedireccion(cuaderno.getPeticionPagoPasarelaExternaConRedireccion());
		pago.setIdTasa(cuaderno.getCodTributo());
		if(datosPagoEnviado!=null){
			if(StringUtils.isEmpty(pago.getIdTasa())) 
				pago.setIdTasa(datosPagoEnviado.getIdTasa());
		}
		return pago;
	}
}
