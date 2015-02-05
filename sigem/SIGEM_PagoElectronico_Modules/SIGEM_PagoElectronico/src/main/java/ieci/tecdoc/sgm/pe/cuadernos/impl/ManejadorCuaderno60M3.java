package ieci.tecdoc.sgm.pe.cuadernos.impl;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.pe.CriterioBusquedaPago;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad3;
import ieci.tecdoc.sgm.pe.Cuaderno60XMLTags;
import ieci.tecdoc.sgm.pe.Liquidacion;
import ieci.tecdoc.sgm.pe.Pago;
import ieci.tecdoc.sgm.pe.PagoImpl;
import ieci.tecdoc.sgm.pe.SistemaPagoElectronicoFactory;
import ieci.tecdoc.sgm.pe.Util;
import ieci.tecdoc.sgm.pe.database.NumeroSecuenciaAutoLiquidacion3;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class ManejadorCuaderno60M3 extends ManejadorCuadernoBase {
	/**
	 * Método de búsqueda de pagos del tipo Cuaderno60 Modalidades 1 o 2.
	 * @param poCriterio Objeto que encapsula los criterios de búsqueda.
	 * @return Cuaderno60Modalidad1_2[] Array de cuadernos resultado de la búsqueda.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public PagoImpl consultaCuadernos(CriterioBusquedaPago poCriterio) throws PagoElectronicoExcepcion{
		Cuaderno60Modalidad3[] oResultado = null;
		try {
			oResultado = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().consultaCuaderno60Modalidad3(poCriterio);
		} catch (PagoElectronicoExcepcion e) {
			Logger.getLogger(this.getClass()).error("Error realizando consutal de pago AL3.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_CONSULTA_PAGOS, e);		
		}
		PagoImpl oPago=null;
		if(	(oResultado != null) && (oResultado.length > 0)	){
			oPago = obtenerPago(oResultado[0]);
		}
		return oPago;
	}
	
	
	
	protected PagoImpl pagoCuaderno(PagoImpl pago) throws PagoElectronicoExcepcion{
		Cuaderno60Modalidad3 poCuaderno=obtenerCuaderno60Modalidad3(pago);
		try {
			poCuaderno = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().pagoCuaderno60Modalidad3(poCuaderno);
		} catch (PagoElectronicoExcepcion e) {
			Logger.getLogger(this.getClass()).error("Error realizando pago autoliquidación AL3.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_REALIZANDO_PAGO, e);
		}		
		return obtenerPago(poCuaderno,pago);
	}
	
	/**
	 * Método que devuelve los datos de un determinado pago a partir de un cuaderno 60 Modalidad3
	 * @param poCuaderno Datos del cuaderno 60
	 * @return Pago Datos del pago.
	 */
	private PagoImpl obtenerPago(Cuaderno60Modalidad3 poCuaderno){
		return obtenerPago(poCuaderno,null);
	}
	
	private PagoImpl obtenerPago(Cuaderno60Modalidad3 poCuaderno,PagoImpl datosPagoEnviado){
		PagoImpl oPago = new PagoImpl();
		rellenarPagoComunCuadernos(oPago,poCuaderno,datosPagoEnviado);
		if(poCuaderno==null) return oPago;
		oPago.setCcc(poCuaderno.getCcc());
		oPago.setEntidadBancaria(poCuaderno.getCodEntidad());
		oPago.setFechaCaducidadTarjetaCredito(poCuaderno.getFecCaducidadTarjetaCredito());
		oPago.setMedioPago(poCuaderno.getIdentificadorMedioPago());
		oPago.setNumeroTarjetaCredito(poCuaderno.getPan());
		oPago.setAcreditacion(poCuaderno.getAcreditacionPagos());
		oPago.setExpediente(poCuaderno.getExpediente());
		oPago.setFechaDevengo(poCuaderno.getFechaDevengo());
		oPago.setInformacionEspecifica(poCuaderno.getInformacionEspecifica());
		oPago.setPeticionPagoPasarelaExternaConRedireccion(null);
		return oPago;
	}
	
	/**
	 * Método que genera un objeto del tipo Cuaderno60Modalidad3 a partir de los datos
	 * asociados a un pago.
	 * @param poPago Datos del pago.
	 * @return Cuaderno60Modalidad3 Representación interna al Manager de pagos de un cuaderno 60 modalidad3.
	 */
	private Cuaderno60Modalidad3 obtenerCuaderno60Modalidad3(Pago poPago){
		Cuaderno60Modalidad3 oCuaderno = new Cuaderno60Modalidad3();		
		oCuaderno.setCodTributo(poPago.getLiquidacion().getTasa().getCodigo());
		oCuaderno.setDescripcion(poPago.getLiquidacion().getTasa().getNombre());
		oCuaderno.setOrganismoEmisor(poPago.getLiquidacion().getTasa().getIdEntidadEmisora());
		oCuaderno.setJustificante(poPago.getReferencia());
		oCuaderno.setNifCertificado(poPago.getNif());
		oCuaderno.setCodEntidad(poPago.getEntidadBancaria());
		oCuaderno.setCcc(poPago.getCcc());
		oCuaderno.setFecCaducidadTarjetaCredito(poPago.getFechaCaducidadTarjetaCredito());
		oCuaderno.setFecCaducidad(poPago.getLiquidacion().getVencimiento());
		oCuaderno.setIdentificadorMedioPago(poPago.getMedioPago());
		oCuaderno.setIdioma(poPago.getIdioma());
		oCuaderno.setPan(poPago.getNumeroTarjetaCredito());
		// FECHA Si viene vacía se coloca la del sistema
		if(poPago.getFecha() == null){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			poPago.setFecha(Util.getFechaCuaderno60(cal));
			poPago.setHora(Util.getHoraCuaderno60(cal));
		}
		oCuaderno.setFecha(poPago.getFecha());
		oCuaderno.setHora(poPago.getHora());
		oCuaderno.setImporte(poPago.getLiquidacion().getImporte());
		
		oCuaderno.setAcreditacionPagos(poPago.getAcreditacion());
		oCuaderno.setCodTributo(poPago.getLiquidacion().getIdTasa());
		oCuaderno.setFechaDevengo(poPago.getFechaDevengo());
		oCuaderno.setInformacionEspecifica(poPago.getInformacionEspecifica());
		oCuaderno.setJustificante(poPago.getLiquidacion().getReferencia());
		oCuaderno.setNifContribuyente(poPago.getLiquidacion().getNif());
		oCuaderno.setExpediente(poPago.getExpediente());
		return oCuaderno;
	}
	
	/**
	 * Método que valida los datos de entrada específicos de un pago del tipo AL3
	 * @param poPago Datos del Pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */	
	public void validarPago(PagoImpl poPago) throws PagoElectronicoExcepcion{
		super.validarPago(poPago);
		Logger logger=Logger.getLogger(this.getClass());
		
		// referencia
		if(	(poPago.getReferencia() == null)
				|| (poPago.getReferencia().length() < 1) || (poPago.getReferencia().length() > 13) ){
			logger.error("Parámetro referencia incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
		}
		
		// acreditación de terceros
		if(	(poPago.getAcreditacion() == null)
			|| (poPago.getAcreditacion().length() < 1) || (poPago.getAcreditacion().length() > 1) ){
			logger.error("Parámetro Acreditación incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
		}else{
			if( (!Pago.ACREDITACION_NO_TERCERO_AUTORIZADO.equals(poPago.getAcreditacion()))
				&&(!Pago.ACREDITACION_TERCERO_AUTORIZADO.equals(poPago.getAcreditacion())) ){
				logger.error("Parámetro Acreditación incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);				
			}
		}
		
		// importe
		if(		(poPago.getImporte() == null)
				|| (poPago.getImporte().length() < 1) || (poPago.getImporte().length() > 12) ){
			logger.error("Parámetro IdTasa incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}else{
			poPago.setImporte(Util.formatearImporte(poPago.getImporte(), 12));
		}
		
		// importe
		if(		(poPago.getInformacionEspecifica() == null)	|| (poPago.getInformacionEspecifica().length() < 1)	){
			logger.error("Parámetro Información Especifica incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}else if(poPago.getInformacionEspecifica().length() > 20){
			poPago.setInformacionEspecifica(poPago.getInformacionEspecifica().substring(0, 20));
		}		
	}
	
	public void validarLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		super.validarLiquidacion(poLiquidacion);
		Logger logger=Logger.getLogger(this.getClass());
		
		// id_entidad emisora
		if(	(poLiquidacion.getIdEntidadEmisora() == null)
			|| (poLiquidacion.getIdEntidadEmisora().length() < 1) || (poLiquidacion.getIdEntidadEmisora().length() > 6)	){
			logger.error("Parámetro IdEntidadEmisora incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}				
	}
	
	public String obtenerDocumentoPago(PagoImpl pago, Liquidacion poLiquidacion, boolean pbCabeceraEstandar){
		Cuaderno60Modalidad3 poCuaderno=obtenerCuaderno60Modalidad3(pago);
		
		XmlTextBuilder bdr;
			
		  bdr = new XmlTextBuilder();
		  
		  if(pbCabeceraEstandar){
			  bdr.setStandardHeader();			  
		  }
		  bdr.addOpeningTag(Cuaderno60XMLTags.PAGO_TAG);
			  bdr.addSimpleElement(Cuaderno60XMLTags.NIF_REPRESENTANTE1_TAG, poCuaderno.getNifRepresentante1());
			  bdr.addSimpleElement(Cuaderno60XMLTags.NIF_REPRESENTANTE2_TAG, poCuaderno.getNifRepresentante2());
			  bdr.addSimpleElement(Cuaderno60XMLTags.NOMBRE_REPRESENTANTE1_TAG, poCuaderno.getNomRepresentante1());
			  bdr.addSimpleElement(Cuaderno60XMLTags.NOMBRE_REPRESENTANTE2_TAG, poCuaderno.getNomRepresentante2());		  
			  bdr.addSimpleElement(Cuaderno60XMLTags.MEDIO_PAGO_TAG, poCuaderno.getIdentificadorMedioPago());		  
			  bdr.addSimpleElement(Cuaderno60XMLTags.CCC_TAG, poCuaderno.getCcc());
			  bdr.addSimpleElement(Cuaderno60XMLTags.IDIOMA_TAG, poCuaderno.getIdioma());
			  bdr.addSimpleElement(Cuaderno60XMLTags.PAN_TAG, poCuaderno.getPan());		  
			  bdr.addSimpleElement(Cuaderno60XMLTags.FECHA_CADUCIDAD, poCuaderno.getFecCaducidadTarjetaCredito());
			  bdr.addSimpleElement(Cuaderno60XMLTags.DEVENGO_TAG, poCuaderno.getFechaDevengo());
			  bdr.addOpeningTag(Cuaderno60XMLTags.LIQUIDACION_TAG);
				  bdr.addSimpleElement(Cuaderno60XMLTags.FECHA_TAG, poCuaderno.getFecha());
				  bdr.addSimpleElement(Cuaderno60XMLTags.HORA_TAG, poCuaderno.getHora());
				  bdr.addSimpleElement(Cuaderno60XMLTags.NIF_TAG, poLiquidacion.getNif());
				  bdr.addSimpleElement(Cuaderno60XMLTags.NOMBRE_TAG, poLiquidacion.getNombre(), true);
				  bdr.addSimpleElement(Cuaderno60XMLTags.REFERENCIA_TAG, poLiquidacion.getReferencia());
				  bdr.addSimpleElement(Cuaderno60XMLTags.NRC_TAG, poLiquidacion.getNrc());
				  bdr.addSimpleElement(Cuaderno60XMLTags.COD_ENTIDAD_TAG, poLiquidacion.getIdEntidadEmisora());				  
				  bdr.addSimpleElement(Cuaderno60XMLTags.IMPORTE_TAG, Util.formatearImporteAEuros(poLiquidacion.getImporte()));
				  String cAux = null;
				  if(poLiquidacion.getFechaPago() != null){
					  cAux = DateTimeUtil.getDateTime(poLiquidacion.getFechaPago(), "dd/MM/yyyy HH:mm:ss");
				  }
				  bdr.addSimpleElement(Cuaderno60XMLTags.FECHA_PAGO_TAG, cAux);
				  cAux = null;
				  if(poLiquidacion.getInicioPeriodo() != null){
					  cAux = ieci.tecdoc.sgm.base.textutil.Util.formatDate(poLiquidacion.getInicioPeriodo());
				  }
				  bdr.addSimpleElement(Cuaderno60XMLTags.INICIO_PERIODO_TAG, cAux);
				  cAux = null;
				  if(poLiquidacion.getFinPeriodo() != null){
					  cAux = ieci.tecdoc.sgm.base.textutil.Util.formatDate(poLiquidacion.getFinPeriodo());
				  }
				  bdr.addSimpleElement(Cuaderno60XMLTags.FIN_PERIODO_TAG, cAux );				  
				  bdr.addOpeningTag(Cuaderno60XMLTags.TASA_TAG);
					  bdr.addSimpleElement(Cuaderno60XMLTags.ID_TASA_TAG, poLiquidacion.getIdTasa());
					  bdr.addSimpleElement(Cuaderno60XMLTags.NOMBRE_TASA_TAG, poLiquidacion.getTasa().getNombre());
					  bdr.addSimpleElement(Cuaderno60XMLTags.TIPO_TASA_TAG, poLiquidacion.getTasa().getTipo());
					  bdr.addSimpleElement(Cuaderno60XMLTags.MODELO_TASA_TAG, poLiquidacion.getTasa().getModelo());
					  bdr.addSimpleElement(Cuaderno60XMLTags.CAPTURA_TASA_TAG, poLiquidacion.getTasa().getCaptura());
					  bdr.addOpeningTag(Cuaderno60XMLTags.DATOS_ESPECIFICOS_TAG);
					  	bdr.addFragment(poLiquidacion.getTasa().getDatosEspecificos());
					  bdr.addClosingTag(Cuaderno60XMLTags.DATOS_ESPECIFICOS_TAG);
				  bdr.addClosingTag(Cuaderno60XMLTags.TASA_TAG);		
				  bdr.addSimpleElement(Cuaderno60XMLTags.EJERCICIO_TAG, poLiquidacion.getEjercicio());
				  bdr.addSimpleElement(Cuaderno60XMLTags.VENCIMIENTO_TAG, poLiquidacion.getVencimiento());
				  bdr.addSimpleElement(Cuaderno60XMLTags.DISCRIMINANTE_TAG, poLiquidacion.getDiscriminante());
				  bdr.addSimpleElement(Cuaderno60XMLTags.REMESA_TAG, poLiquidacion.getRemesa());
				  bdr.addSimpleElement(Cuaderno60XMLTags.ESTADO_TAG, poLiquidacion.getEstado());
				  bdr.addOpeningTag(Cuaderno60XMLTags.DATOS_ESPECIFICOS_TAG);
				  	bdr.addFragment(poLiquidacion.getDatosEspecificos());
				  bdr.addClosingTag(Cuaderno60XMLTags.DATOS_ESPECIFICOS_TAG);
			  bdr.addClosingTag(Cuaderno60XMLTags.LIQUIDACION_TAG);		  
		  bdr.addClosingTag(Cuaderno60XMLTags.PAGO_TAG);
		  
		  return bdr.getText();
	}
	
	/**
	 * Metodo que obtiene el digito de control para el número de referencia de una
	 * liquidación del tipo AL3.
	 * @param modelo Modelo de la tasa.
	 * @param emisora Identificador de la entidad emisora.
	 * @param indicadorCaptura Indicador de captura.
	 * @param numSec Número de secuencia para el modelo.
	 * @return short Dígito de control.
	 */
	private short obtenerDigitoControlAL3(String modelo, String emisora,String indicadorCaptura, String numSec){
		short    ctrlDigit = 0;
		String   num = null;
		double   numJustWDigit = 0;
		double 	 emisor = 0;
		double   sum = 0;
		double   remainder = 0;
		int      factor = 7;
		
		num = modelo + indicadorCaptura + numSec;
		
		numJustWDigit = new Double(num).doubleValue();
		emisor = new Double(emisora).doubleValue();
		
		sum = numJustWDigit + emisor;
		remainder = Math.IEEEremainder(sum, factor);
		if(remainder < 0){
			remainder = remainder * -1;
		}
		if (remainder == 0)
		ctrlDigit = 7;
		else
		ctrlDigit = (short)remainder;
		
		return ctrlDigit;
	}
	
	/**
	 * Método que obtiene el número de referencia para una liquidación del tipo AL3.
	 * @param conn Conexión a la base de datos con una transacción activa.
	 * @param poLiquidacion Datos de la liquidación de la que se quiere obtener el número de referencia.
	 * @return String Número de referencia.
	 * @throws PagoElectronicoExcepcion  En caso de producirse algún error.
	 */
	public String generarNumeroReferencia(DbConnection conn,Liquidacion poLiquidacion) throws PagoElectronicoExcepcion {	
		
		long lNumeroSecuencia = -1;
		try {
			lNumeroSecuencia = NumeroSecuenciaAutoLiquidacion3.obtenerIdentificador(conn, poLiquidacion.getTasa().getModelo());
		} catch (DbExcepcion e) {
			Logger.getLogger(this.getClass()).error("Error obteniendo número de referencia para liquidación AL3", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_GENERANDO_NREF_AL3, e);
		}
		
		// formateamos el número de secuencia
		String cNumeroSecuencia = Util.format(lNumeroSecuencia, 8);
		// obtenemos el digito de control
		short sDigitoControl = obtenerDigitoControlAL3(poLiquidacion.getIdTasa(), 
													poLiquidacion.getTasa().getIdEntidadEmisora(), 
													poLiquidacion.getTasa().getCaptura(), 
													cNumeroSecuencia);
													
		StringBuffer sbReferencia = new StringBuffer(poLiquidacion.getIdTasa());
		sbReferencia.append(poLiquidacion.getTasa().getCaptura());
		sbReferencia.append(cNumeroSecuencia);
		sbReferencia.append(sDigitoControl);

		return sbReferencia.toString();
    }
}
