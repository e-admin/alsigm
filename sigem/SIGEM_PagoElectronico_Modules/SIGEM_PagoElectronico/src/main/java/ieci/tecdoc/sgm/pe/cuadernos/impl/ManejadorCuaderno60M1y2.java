package ieci.tecdoc.sgm.pe.cuadernos.impl;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.CriterioBusquedaPago;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad1_2;
import ieci.tecdoc.sgm.pe.Cuaderno60XMLTags;
import ieci.tecdoc.sgm.pe.Liquidacion;
import ieci.tecdoc.sgm.pe.Pago;
import ieci.tecdoc.sgm.pe.PagoImpl;
import ieci.tecdoc.sgm.pe.SistemaPagoElectronicoFactory;
import ieci.tecdoc.sgm.pe.Util;
import ieci.tecdoc.sgm.pe.database.NumeroSecuenciaAutoLiquidacion12;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public abstract class ManejadorCuaderno60M1y2 extends ManejadorCuadernoBase {
	/**
	 * Método de búsqueda de pagos del tipo Cuaderno60 Modalidades 1 o 2.
	 * @param poCriterio Objeto que encapsula los criterios de búsqueda.
	 * @return Cuaderno60Modalidad1_2[] Array de cuadernos resultado de la búsqueda.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public PagoImpl consultaCuadernos(CriterioBusquedaPago poCriterio) throws PagoElectronicoExcepcion{
		Cuaderno60Modalidad1_2[] oResultado = null;
		try {
			oResultado = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().consultaCuaderno60Modalidad1_2(poCriterio);
		} catch (PagoElectronicoExcepcion e) {
			Logger.getLogger(this.getClass()).error("Error realizando consutal de pago AL1-2.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_CONSULTA_PAGOS, e);		
		}
		PagoImpl oPago=null;
		if(	(oResultado != null) && (oResultado.length > 0)	){
			oPago = obtenerPago(oResultado[0]);
		}
		return oPago;
	}
	
	protected abstract Cuaderno60Modalidad1_2 obtenerCuaderno60(Pago poPago);
	
	/**
	 * Método que efectúa una operación de pago de un Cuaderno60 modalidades 1 o 2.
	 * Utiliza el sistema de pago electrónico configurado para el sistema.
	 * @param poCuaderno Datos del pago con el formato de un Cuaderno60 modalidades 1 o 2.
	 * @return Cuaderno60Modalidad1_2 Cuaderno de pago con toda la información después de realizar el pago.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	protected PagoImpl pagoCuaderno(PagoImpl pago) throws PagoElectronicoExcepcion{
		Cuaderno60Modalidad1_2 poCuaderno=obtenerCuaderno60(pago);
		try {
			poCuaderno = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().pagoCuaderno60Modalidad1_2(poCuaderno);
		} catch (PagoElectronicoExcepcion e) {
			Logger.getLogger(this.getClass()).error("Error realizando pago AL1-2.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_REALIZANDO_PAGO, e);
		}		
		return obtenerPago(poCuaderno,pago);
	}

	/**
	 * Método que devuelve los datos de un determinado pago a partir de un cuaderno 60 Modalidad1_2
	 * @param poCuaderno Datos del cuaderno 60
	 * @return Pago Datos del pago.
	 */
	private PagoImpl obtenerPago(Cuaderno60Modalidad1_2 poCuaderno){
		return obtenerPago(poCuaderno,null);
	}
	
	private PagoImpl obtenerPago(Cuaderno60Modalidad1_2 poCuaderno,PagoImpl datosPagoEnviado){
		PagoImpl oPago = new PagoImpl();
		rellenarPagoComunCuadernos(oPago,poCuaderno,datosPagoEnviado);
		if(poCuaderno==null) return oPago;
		
		oPago.setCcc(poCuaderno.getCcc());
		oPago.setCccDomiciliacion(poCuaderno.getCccDomiciliacion());
		oPago.setDomiciliacion(poCuaderno.getCodDomiciliacion());
		oPago.setEntidadBancaria(poCuaderno.getCodEntidad());
		oPago.setFechaCaducidadTarjetaCredito(poCuaderno.getFecCaducidadTarjetaCredito());
		oPago.setMedioPago(poCuaderno.getIdentificadorMedioPago());
		oPago.setNumeroTarjetaCredito(poCuaderno.getPan());
		oPago.setReferencia(poCuaderno.getReferencia());
		return oPago;
	}
	
	/**
	 * Método que completa los datos comunes a un cuaderno 60 modalidad 1 y 2
	 * @param poPago Datos del pago.
	 * @param poCuaderno Cuaderno60Modalidad1_2 con los datos específicos de su tipo informados.
	 * @return Cuaderno60Modalidad1_2 Representación interna dentro del manager de pagos de un cuaderno 60 modalidad 1 2
	 */
	protected Cuaderno60Modalidad1_2 completarDatosCuaderno60Modalidad1_2(Pago poPago, Cuaderno60Modalidad1_2 poCuaderno){
		poCuaderno.setCodTributo(poPago.getLiquidacion().getTasa().getCodigo());
		poCuaderno.setDescripcion(poPago.getLiquidacion().getTasa().getNombre());
		poCuaderno.setOrganismoEmisor(poPago.getLiquidacion().getTasa().getIdEntidadEmisora());
		poCuaderno.setReferencia(poPago.getReferencia());
		poCuaderno.setNifCertificado(poPago.getNif());
		poCuaderno.setCodEntidad(poPago.getEntidadBancaria());
		poCuaderno.setCcc(poPago.getCcc());
		poCuaderno.setCccDomiciliacion(poPago.getCccDomiciliacion());
		poCuaderno.setCodDomiciliacion(poPago.getDomiciliacion());
		poCuaderno.setFecCaducidad(poPago.getLiquidacion().getVencimiento());
		poCuaderno.setFecCaducidadTarjetaCredito(poPago.getFechaCaducidadTarjetaCredito());
		poCuaderno.setIdentificadorMedioPago(poPago.getMedioPago());
		poCuaderno.setIdioma(poPago.getIdioma());
		poCuaderno.setPan(poPago.getNumeroTarjetaCredito());
		// FECHA Si viene vacía se coloca la del sistema
		if(poPago.getFecha() == null){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			poPago.setFecha(Util.getFechaCuaderno60(cal));
			poPago.setHora(Util.getHoraCuaderno60(cal));
		}
		poCuaderno.setFecha(poPago.getFecha());
		poCuaderno.setHora(poPago.getHora());
		poCuaderno.setImporte(poPago.getLiquidacion().getImporte());
		return poCuaderno;
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
		
		// ejercicio
		if(	(poLiquidacion.getEjercicio() == null)
			|| (poLiquidacion.getEjercicio().length() < 1) || (poLiquidacion.getEjercicio().length() > 6) ){
			logger.error("Parámetro Ejercicio incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}					
	}
	
	
	/**
	 * Método que valida los datos comunes a pagos correspondientes a liquidaciones
	 * @param poPago Datos del pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación
	 */
	public void validarPago(PagoImpl poPago) throws PagoElectronicoExcepcion{
		super.validarPago(poPago);
		Logger logger=Logger.getLogger(this.getClass());
		
		// referencia
		if(	(poPago.getReferencia() == null)
				|| (poPago.getReferencia().length() < 1) || (poPago.getReferencia().length() > 12) ){
			logger.error("Parámetro referencia incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
		}
	}
	
	public String obtenerDocumentoPago(PagoImpl pago, Liquidacion poLiquidacion, boolean pbCabeceraEstandar){
		  Cuaderno60Modalidad1_2 poCuaderno=obtenerCuaderno60(pago);  
		
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
			  bdr.addSimpleElement(Cuaderno60XMLTags.COD_DOMICILIACION_TAG, poCuaderno.getCodDomiciliacion());
			  bdr.addSimpleElement(Cuaderno60XMLTags.CCC_DOMICILIACION_TAG, poCuaderno.getCccDomiciliacion());
			  bdr.addSimpleElement(Cuaderno60XMLTags.IDENTIFICADOR1_TAG, poCuaderno.getIdent1());
			  bdr.addSimpleElement(Cuaderno60XMLTags.IDENTIFICADOR2_TAG, poCuaderno.getIdent2());
			  bdr.addSimpleElement(Cuaderno60XMLTags.USAR_PASARELA_TAG, ""+(ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()?1:0));
			  bdr.addOpeningTag(Cuaderno60XMLTags.LIQUIDACION_TAG);
				  bdr.addSimpleElement(Cuaderno60XMLTags.FECHA_TAG, poCuaderno.getFecha());
				  bdr.addSimpleElement(Cuaderno60XMLTags.HORA_TAG, poCuaderno.getHora());
				  bdr.addSimpleElement(Cuaderno60XMLTags.NIF_TAG, poLiquidacion.getNif());
				  bdr.addSimpleElement(Cuaderno60XMLTags.NOMBRE_TAG, poLiquidacion.getNombre(), true);
				  bdr.addSimpleElement(Cuaderno60XMLTags.REFERENCIA_TAG, poLiquidacion.getReferencia());
				  bdr.addSimpleElement(Cuaderno60XMLTags.NRC_TAG, poLiquidacion.getNrc());
				  bdr.addSimpleElement(Cuaderno60XMLTags.COD_ENTIDAD_TAG, poLiquidacion.getIdEntidadEmisora());
				  String cAux = null;
				  if(poLiquidacion.getFechaPago() != null){
					  cAux = DateTimeUtil.getDateTime(poLiquidacion.getFechaPago(), "dd/MM/yyyy HH:mm:ss");
				  }
				  bdr.addSimpleElement(Cuaderno60XMLTags.FECHA_PAGO_TAG, cAux);
				  bdr.addSimpleElement(Cuaderno60XMLTags.IMPORTE_TAG, Util.formatearImporteAEuros(poLiquidacion.getImporte()));
				  bdr.addSimpleElement(Cuaderno60XMLTags.INICIO_PERIODO_TAG, ieci.tecdoc.sgm.base.textutil.Util.formatDate(poLiquidacion.getInicioPeriodo()) );
				  bdr.addSimpleElement(Cuaderno60XMLTags.FIN_PERIODO_TAG, ieci.tecdoc.sgm.base.textutil.Util.formatDate(poLiquidacion.getFinPeriodo()) );				  
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
	 * Método que obtiene el identificador de un pago del tipo AL1 (C60M1). 
	 * 		(OJO: Se usa tambien al generar la referencia para los pagos C60M2
	 * El identificador está formado por:
	 *  - (3) Id. tributo.
	 *  - (2) Ejercicio.
	 *  - (2) Remesa.
	 * @param poLiquidacion Datos de la liquidación.
	 * @return String Identificador del pago.
	 */
	private String obtenerIdentificadorPagoAL1y2(Liquidacion poLiquidacion){
		StringBuffer sbIdent = new StringBuffer(poLiquidacion.getIdTasa());
		sbIdent.append(poLiquidacion.getEjercicio()).append(poLiquidacion.getRemesa());
		return sbIdent.toString();
	}
	
	/**
	 * Método que obtiene el dígito de control para un número de referencia de una liquidación del tipo AL1_2
	 * @param emisora Identificador de la entidad emisora.
	 * @param identificacion Identificación de la liquidación.
	 * @param importe Importe de la liquidación.
	 * @param numSec Número de secuencia.
	 * @return short Dígito de control.
	 */
	private short obtenerDigitoControlAL12(String emisora, String identificacion, String importe, String numSec){
		short ctrlDigit = 0;
		String referencia = null;
		String aux = null;
		String aux1 = null;
		String aux2 = null;
		double param = 0;
		double param1 = 0;
		double param2 = 0;
		double param3 = 0;
		double imp = 0;
		double total = 0;
		int AA = 0;
		
		referencia = numSec;
		
		param1 = new Double(emisora).doubleValue();
		param2 = new Double(referencia).doubleValue();
		param = new Double(identificacion).doubleValue();
		
		aux = importe;
		aux = Util.removeChar(aux, '.');
		aux = Util.removeChar(aux, ',');
		imp = new Double(aux).doubleValue();
		
		param3 = param + imp;
		
		total = ((param1*76) + (param2*9) + ((param3-1)*55)) / 97;
		
		aux1 = new Double(total).toString();
		aux2 = Util.getDecimalsStr(aux1,  2);
		AA = new Integer(aux2).shortValue();
		
		ctrlDigit = (short)(99 - AA);
		
		return ctrlDigit;
   }

	/**
	 * Método que obtiene el número de referencia para una liquidación del tipo AL1 o AL2.
	 * @param conn Conexión a la base de datos con una transacción activa.
	 * @param poLiquidacion Datos de la liquidación de la que se quiere obtener el número de referencia.
	 * @return String Número de referencia.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public String generarNumeroReferencia(DbConnection conn,Liquidacion poLiquidacion) throws PagoElectronicoExcepcion {	
		long lNumeroSecuencia = -1;
		try {
			lNumeroSecuencia = NumeroSecuenciaAutoLiquidacion12.obtenerIdentificador(conn);
		} catch (DbExcepcion e) {
			Logger.getLogger(this.getClass()).error("Error obteniendo número de referencia para liquidación AL1_2", e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_GENERANDO_NREF_AL12, e);
		}
		
		// Se formatea a cadena de 10 posiciones
		String sNumSec = Util.format(lNumeroSecuencia, 10);
		short ctrlDigit = obtenerDigitoControlAL12(poLiquidacion.getIdEntidadEmisora(), 
												obtenerIdentificadorPagoAL1y2(poLiquidacion), 
												poLiquidacion.getImporte(), 
												sNumSec);
		
		StringBuffer sbNumRef = new StringBuffer();
		if (ctrlDigit < 10){
			sbNumRef.append(sNumSec).append("0").append(new Short(ctrlDigit).toString());
		}else{
			sbNumRef.append(sNumSec).append(new Short(ctrlDigit).toString());
		}
		return sbNumRef.toString();
    }
}
