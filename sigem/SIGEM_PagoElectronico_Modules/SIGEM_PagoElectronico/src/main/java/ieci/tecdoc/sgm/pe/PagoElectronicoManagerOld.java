package ieci.tecdoc.sgm.pe;
/*
 * $Id: PagoElectronicoManager.java,v 1.1.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.pe.database.DBSessionManager;
import ieci.tecdoc.sgm.pe.database.LiquidacionDatos;
import ieci.tecdoc.sgm.pe.database.LiquidacionTabla;
import ieci.tecdoc.sgm.pe.database.TasaDatos;
import ieci.tecdoc.sgm.pe.database.TasaTabla;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

public class PagoElectronicoManagerOld {

	/** 
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(PagoElectronicoManagerOld.class);

	/**
	 * Método que da de alta en el sistema una nueva liquidaciónpendiente para un determinado ciudadano
	 * y asociada a una determinada tasa.
	 * @param poLiquidacion Datos de la liquidación.
	 * @return Liquidacion Datos completos de la liquidacion.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public LiquidacionImpl altaLiquidacion(LiquidacionImpl poLiquidacion, String entidad) throws PagoElectronicoExcepcion{
		TasaImpl oTasa = obtenerDatosTasa(poLiquidacion.getIdTasa(), poLiquidacion.getIdEntidadEmisora(), entidad);
		poLiquidacion.setTasa(oTasa);
		
		validarAltaLiquidacion(poLiquidacion);
		// Crear pago
		try {
			poLiquidacion = crearLiquidacion(poLiquidacion, entidad);
		} catch (DbExcepcion e) {
			logger.error("Error creando liquidación.", e);
			throw e;
		}
		// devolver respuesta
		return poLiquidacion;
	}
	
	/**
	 * Método que da de baja de la base de datos una liquidación a partir de su número de referencia
	 * @param poLiquidacion Datos de la liquidación, obligatorio el número de referencia.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public void bajaLiquidacion(LiquidacionImpl poLiquidacion, String entidad) throws PagoElectronicoExcepcion{
		validarBajaLiquidacion(poLiquidacion);
		// eliminar liquidación
		try {
			eliminarLiquidacion(poLiquidacion, entidad);
		} catch (DbExcepcion e) {
			logger.error("Error creando liquidación.", e);
			throw e;
		}		
	}
	
	
	/**
	 * Método que modifica la información asociada a una liquidación.
	 * @param poLiquidacion Datos de la liquidación
	 * @return Liquidacion Con los datos modificados.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public void modificarLiquidacion(LiquidacionImpl poLiquidacion, String entidad) throws PagoElectronicoExcepcion{
		TasaImpl oTasa = obtenerDatosTasa(poLiquidacion.getIdTasa(), poLiquidacion.getIdEntidadEmisora(), entidad);
		poLiquidacion.setTasa(oTasa);		
		
		validarModificacionLiquidacion(poLiquidacion);
		// eliminar liquidación
		try {
			actualizarLiquidacion(poLiquidacion, entidad);
		} catch (DbExcepcion e) {
			logger.error("Error creando liquidación.", e);
			throw e;
		}				
	}
	

	/**
	 * Método que realiza una búsqueda de liquidaciones según los criterios que le llegan como parámetro
	 * @param poCriterio Criterios de búsqueda
	 * @return List Lista de liquidaciones que cumplen el criterio de búsqueda.
	 * @throws PagoElectronicoExcepcion En caso de produc
	 */
	public LiquidacionImpl[] buscarLiquidaciones(CriterioBusquedaLiquidacion poCriterio, String entidad) throws PagoElectronicoExcepcion{
		List olLista = busquedaLiquidaciones(poCriterio, entidad);
		LiquidacionImpl[] aLiquidaciones = null;
		if( olLista != null ){
			aLiquidaciones = new LiquidacionImpl[olLista.size()];
			for(int eContador = 0; eContador < olLista.size(); eContador++){
				aLiquidaciones[eContador] = (LiquidacionImpl)olLista.get(eContador);
			}
		}else{
			aLiquidaciones = new LiquidacionImpl[0];
		}
		return aLiquidaciones;
	}
	
	
	/**
	 * Método que realiza una búsqueda de liquidaciones según los criterios que le llegan como parámetro
	 * @param poCriterio Criterios de búsqueda
	 * @return List Lista de liquidaciones que cumplen el criterio de búsqueda.
	 * @throws PagoElectronicoExcepcion En caso de produc
	 */
	public TasaImpl[] buscarTasas(CriterioBusquedaTasa poCriterio, String entidad) throws PagoElectronicoExcepcion{
		List olLista = busquedaTasas(poCriterio, entidad);
		TasaImpl[] aTasas = null;
		if( olLista != null ){
			aTasas = new TasaImpl[olLista.size()];
			for(int eContador = 0; eContador < olLista.size(); eContador++){
				aTasas[eContador] = (TasaImpl)olLista.get(eContador);
			}
		}else{
			aTasas = new TasaImpl[0];
		}
		return aTasas;
	}
	

	/**
	 * Método que se encarga de realizar una operación de pago electrónico.
	 * Tipos de pagos permitidos:
	 * - AL1.
	 * - AL2.
	 * - AL3.
	 * @param poPago Datos del pago a realizar.
	 * @return Pago Datos completos del pago una vez realizado.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public String obtenerDocumentoPago(PagoImpl poPago, String entidad) throws PagoElectronicoExcepcion{
		// validar datos de entrada.
		if(poPago == null){
			return "";
		}
//		validaRealizarPago(poPago);
		String cRetorno = "";
		TasaImpl oTasa = null;
		if(		(poPago.getLiquidacion() != null) && (poPago.getLiquidacion().getTasa() != null) ){
			oTasa = poPago.getLiquidacion().getTasa();
		}else{
			oTasa = obtenerDatosTasa(poPago.getIdTasa(), poPago.getIdEntidadEmisora(), entidad);			
		}

		// Comprobamos si se trata de una liquidacion o una autoliquidacion
		if(		(Tasa.TIPO_AL1.equalsIgnoreCase(oTasa.getTipo()))
				||(Tasa.TIPO_AL2.equalsIgnoreCase(oTasa.getTipo())) ){
			// Es una liquidacion
			//validar datos de liquidacion
//			if(Tasa.TIPO_AL1.equals(oTasa.getTipo())){
//				validaPagoAL1(poPago);
//			}else if(Tasa.TIPO_AL2.equals(oTasa.getTipo())){
//				validaPagoAL2(poPago);
//			}
			
			// comprobar si es un pago pendiente
			LiquidacionImpl oLiquidacion = null;
			if( poPago.getLiquidacion() != null){
				oLiquidacion = poPago.getLiquidacion();				
			}else{
				oLiquidacion = obtenerLiquidacion(poPago.getReferencia(), entidad);
			}

			if(oLiquidacion == null){
				StringBuffer sbError = new StringBuffer("Se ha intentado realizar un pago de una liquidación inexistente.");
				sbError.append(" Número de referencia: ").append(poPago.getReferencia());
				logger.error(sbError.toString());
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_LIQUIDACION_SIN_LIQUIDACION);
			}else{
				oLiquidacion.setTasa(oTasa);
				// Comprobar que no este ya pagada
//				if(oLiquidacion.getEstado().equals(Liquidacion.ESTADO_PAGADO)){
//					StringBuffer sbError = new StringBuffer("Se ha intentado realizar una liquidación ya satisfecha.");
//					sbError.append(" Número de referencia: ").append(poPago.getReferencia());
//					logger.error(sbError.toString());
//					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_LIQUIDACION_YA_REALIZADA);
//				}
				// realizar el pago
				poPago.setLiquidacion(oLiquidacion);
				// obtener instancia del cuaderno
				Cuaderno60Modalidad1_2 oCuaderno = obtenerCuaderno60Modalidad1_2(poPago);
				cRetorno = obtenerDocumentoPagoCuaderno60_1_2(oCuaderno, oLiquidacion, false);
			}

		}else if(Tasa.TIPO_AL3.equalsIgnoreCase(oTasa.getTipo())){
			// es una autoliquidacion
			//validar datos de liquidacion
//			validaPagoAL3(poPago);
			
			// comprobar si es un pago pendiente
			LiquidacionImpl oLiquidacion = null;
			if(poPago.getReferencia() != null){
				oLiquidacion = obtenerLiquidacion(poPago.getReferencia(), entidad);	
			}
			
			if(oLiquidacion == null){
				// Crear liquidación asociada.
				oLiquidacion = new LiquidacionImpl();
				oLiquidacion.setIdTasa(oTasa.getCodigo());
				oLiquidacion.setIdEntidadEmisora(oTasa.getIdEntidadEmisora());
				oLiquidacion.setImporte(poPago.getImporte());
				oLiquidacion.setNif(poPago.getNif());
				oLiquidacion.setDatosEspecificos(poPago.getInformacionEspecifica());
//				altaLiquidacion(oLiquidacion);
			}
			// realizar el pago
			oLiquidacion.setTasa(oTasa);
			poPago.setLiquidacion(oLiquidacion);
			Cuaderno60Modalidad3 oCuaderno = obtenerCuaderno60Modalidad3(poPago);
			cRetorno = obtenerDocumentoPagoCuaderno60_3(oCuaderno, oLiquidacion, false);
		}
	
		// devolver respuesta
		return cRetorno;
	}

	
	/**
	 * Método que se encarga de realizar una operación de pago electrónico.
	 * Tipos de pagos permitidos:
	 * - AL1.
	 * - AL2.
	 * - AL3.
	 * @param poPago Datos del pago a realizar.
	 * @return Pago Datos completos del pago una vez realizado.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public PagoImpl realizarPago(PagoImpl poPago, String entidad) throws PagoElectronicoExcepcion{

		// validar datos de entrada.
		validaRealizarPago(poPago);
		
		TasaImpl oTasa = obtenerDatosTasa(poPago.getIdTasa(), poPago.getIdEntidadEmisora(), entidad);
		// Comprobamos si se trata de una liquidacion o una autoliquidacion
		if(		(Tasa.TIPO_AL1.equalsIgnoreCase(oTasa.getTipo()))
				||(Tasa.TIPO_AL2.equalsIgnoreCase(oTasa.getTipo())) ){
			// Es una liquidacion
			//validar datos de liquidacion
			if(Tasa.TIPO_AL1.equals(oTasa.getTipo())){
				validaPagoAL1(poPago);
			}else if(Tasa.TIPO_AL2.equals(oTasa.getTipo())){
				validaPagoAL2(poPago);
			}
			
			// comprobar si es un pago pendiente
			LiquidacionImpl oLiquidacion = obtenerLiquidacion(poPago.getReferencia(), entidad);
			
			if(oLiquidacion == null){
				StringBuffer sbError = new StringBuffer("Se ha intentado realizar un pago de una liquidación inexistente.");
				sbError.append(" Número de referencia: ").append(poPago.getReferencia());
				logger.error(sbError.toString());
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_LIQUIDACION_SIN_LIQUIDACION);
			}else{
				oLiquidacion.setTasa(oTasa);
				
				// Comprobar que no este ya pagada
				if(oLiquidacion.getEstado().equals(Liquidacion.ESTADO_PAGADO)){
					StringBuffer sbError = new StringBuffer("Se ha intentado realizar una liquidación ya satisfecha.");
					sbError.append(" Número de referencia: ").append(poPago.getReferencia());
					logger.error(sbError.toString());
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_LIQUIDACION_YA_REALIZADA);
				}
				// realizar el pago
				poPago.setLiquidacion(oLiquidacion);
				poPago = invocarSistemaPago(poPago);
				if(!ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()){
					oLiquidacion.setNrc(poPago.getNrc());
					oLiquidacion.setEstado(poPago.getEstado());
					oLiquidacion.setFechaPago(Util.parsearFechaCuaderno69(poPago.getFecha(), poPago.getHora()));
				
					// actualizar estado de la liquidacion
					actualizarLiquidacion(oLiquidacion, entidad);
				}
			}

		}else if(Tasa.TIPO_AL3.equalsIgnoreCase(oTasa.getTipo())){
			// es una autoliquidacion
			//validar datos de liquidacion
			validaPagoAL3(poPago);
			
			// comprobar si es un pago pendiente
			LiquidacionImpl oLiquidacion = null;
			if(poPago.getLiquidacion()== null ){
				oLiquidacion = obtenerLiquidacion(poPago.getReferencia(), entidad);	
			}else{
				oLiquidacion = poPago.getLiquidacion();
			}
			
			if(oLiquidacion == null){
				// Crear liquidación asociada.
				oLiquidacion = new LiquidacionImpl();
				oLiquidacion.setIdTasa(oTasa.getCodigo());
				oLiquidacion.setIdEntidadEmisora(oTasa.getIdEntidadEmisora());
				oLiquidacion.setImporte(poPago.getImporte());
				oLiquidacion.setNif(poPago.getNif());
				altaLiquidacion(oLiquidacion, entidad);
			}else{
				oLiquidacion.setTasa(oTasa);
				// Comprobar que no este ya pagada
				if(oLiquidacion.getEstado().equals(Liquidacion.ESTADO_PAGADO)){
					StringBuffer sbError = new StringBuffer("Se ha intentado realizar una liquidación ya satisfecha.");
					sbError.append(" Número de referencia: ").append(poPago.getReferencia());
					logger.error(sbError.toString());
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_LIQUIDACION_YA_REALIZADA);
				}
			}
			// realizar el pago
			poPago.setLiquidacion(oLiquidacion);
			poPago = invocarSistemaPago(poPago);
			if(!ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()){
				oLiquidacion.setNrc(poPago.getNrc());
				oLiquidacion.setEstado(poPago.getEstado());
				oLiquidacion.setFechaPago(Util.parsearFechaCuaderno69(poPago.getFecha(), poPago.getHora()));
			
				// actualizar estado de la liquidacion
				actualizarLiquidacion(oLiquidacion, entidad);
			}
		}
		
		// devolver respuesta
		return poPago;
	}
	
	
	/**
	 * Método que se encarga de realizar la búsqueda de pagos según los criterios que llegan como parámetro.
	 * @param poPago Datos del pago a realizar.
	 * @return Pago Datos completos del pago una vez realizado.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public PagoImpl detallePago(String pcNumReferencia, String entidad) throws PagoElectronicoExcepcion{
		PagoImpl oPago = null;
		// validar datos de entrada.
		if(		(pcNumReferencia == null) || ("".equals(pcNumReferencia)) ){
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);
		}
		LiquidacionImpl oLiquidacion = obtenerLiquidacion(pcNumReferencia, entidad);
		TasaImpl oTasa = obtenerDatosTasa(oLiquidacion.getIdTasa(), oLiquidacion.getIdEntidadEmisora(), entidad);
		// Comprobamos si se trata de una liquidacion o una autoliquidacion
		if(	(Tasa.TIPO_AL1.equalsIgnoreCase(oTasa.getTipo()))
				||(Tasa.TIPO_AL2.equalsIgnoreCase(oTasa.getTipo())) ){	
				// Realizamos la búsqueda por la referencia.
				CriterioBusquedaPago oCriterio = new CriterioBusquedaPago();
				oCriterio.setReferencia(pcNumReferencia);
				oCriterio.setTipo(oTasa.getTipo());
				oCriterio.setEntidad(entidad);
				Cuaderno60Modalidad1_2[] oCuadernos=null;
				try{ oCuadernos = consutaCuadernos60Modalidad1_2(oCriterio); }
				catch(Exception e){ logger.info(e); }
				if(	(oCuadernos != null) && (oCuadernos.length > 0)	){
					oPago = obtenerPago(oCuadernos[0]);
				}else{
					oPago = new PagoImpl();
					oPago.setReferencia(pcNumReferencia);
				}	
		}else if(Tasa.TIPO_AL3.equalsIgnoreCase(oTasa.getTipo())){
			// Realizamos la búsqueda por la referencia.
			CriterioBusquedaPago oCriterio = new CriterioBusquedaPago();
			oCriterio.setReferencia(pcNumReferencia);
			oCriterio.setTipo(oTasa.getTipo());
			Cuaderno60Modalidad3[] oCuadernos = consutaCuadernos60Modalidad3(oCriterio);
			if(	(oCuadernos != null) &&(oCuadernos.length > 0)	){
				oPago = obtenerPago(oCuadernos[0]);
			}
		}
		oLiquidacion.setTasa(oTasa);
		if(oPago!=null) oPago.setLiquidacion(oLiquidacion);
		
		// devolver respuesta
		return oPago;
	}
	
	
	/**
	 * Método que obtiene los datos de una tasa de la base de datos
	 * @param pcIdTasa Identificador de la tasa
	 * @param pcIdEntidadEmisora Identificador de la entidad emisora
	 * @return Tasa Datos de la tasa
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	public TasaImpl obtenerDatosTasa(String pcIdTasa, String pcIdEntidadEmisora, String entidad) throws PagoElectronicoExcepcion{
		TasaDatos oTasa =  new TasaDatos();
		try {
			oTasa.load(pcIdTasa, pcIdEntidadEmisora, entidad);
		} catch (Exception e) {
			StringBuffer sbError = new StringBuffer("Error obteniendo datos de la tasa. Id. Tasa: ");
			sbError.append(pcIdTasa).append(" Id. Entidad Emisora: ").append(pcIdEntidadEmisora);
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_RECUPERANDO_TASA, e);
		}
		return oTasa;
	}
	
	/**
	 * Método que se encarga de la invocación al sistema de pago electrónico
	 * configurado para el sistema.
	 * @param poPago Datos del pago.
	 * @return Pago Con los datos completos después de la operación.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private PagoImpl invocarSistemaPago(PagoImpl poPago) throws PagoElectronicoExcepcion{

		if(	(Tasa.TIPO_AL1.equals(poPago.getLiquidacion().getTasa().getTipo()))
			||(Tasa.TIPO_AL2.equals(poPago.getLiquidacion().getTasa().getTipo()))
		){
			// obtener instancia del cuaderno	
			Cuaderno60Modalidad1_2 oCuaderno = obtenerCuaderno60Modalidad1_2(poPago);
			try {
				oCuaderno = pagoCuaderno60_1_2(oCuaderno);
			} catch (PagoElectronicoExcepcion e) {
				StringBuffer sbError = new StringBuffer("Error invocando al servicio de pago. Referencia: ");
				sbError.append(poPago.getReferencia());
				logger.error(sbError.toString());
				throw e;
			}
			poPago.setNrc(oCuaderno.getNrc());
			poPago.setPeticionPagoPasarelaExternaConRedireccion(oCuaderno.getPeticionPagoPasarelaExternaConRedireccion());
		}else if(Tasa.TIPO_AL3.equals(poPago.getLiquidacion().getTasa().getTipo())){
			Cuaderno60Modalidad3 oCuaderno = obtenerCuaderno60Modalidad3(poPago);
			try {
				oCuaderno = pagoCuaderno60_3(oCuaderno);
				
			} catch (PagoElectronicoExcepcion e) {
				StringBuffer sbError = new StringBuffer("Error invocando al servicio de pago. Referencia: ");
				sbError.append(poPago.getReferencia());
				logger.error(sbError.toString());
				throw e;
			}
			poPago.setNrc(oCuaderno.getNrc());			
			poPago.setPeticionPagoPasarelaExternaConRedireccion("");
		}else{
			StringBuffer sbError = new StringBuffer("Error, tipo de liquidación no soportada: ");
			sbError.append(poPago.getLiquidacion().getTasa().getTipo());
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_NO_PERMITIDO);
		}
	
		
		poPago.setEstado(Pago.ESTADO_PAGADO);
		return poPago;
	}
	
	
	/**
	 * Método que devuelve un Cuaderno60Modalidad1_2 a partir de los datos de un pago.
	 * @param poPago Datos del pago.
	 * @return Cuaderno60Modalidad1_2 Representación interna al manager de un Cuaderno60 Modalidad 1_2
	 */
	private Cuaderno60Modalidad1_2 obtenerCuaderno60Modalidad1_2(Pago poPago){
		// Tipo de cuaderno
		if(Tasa.TIPO_AL1.equals(poPago.getLiquidacion().getTasa().getTipo())){
			return obtenerCuaderno60Modalidad1(poPago);
		}else if(Tasa.TIPO_AL2.equals(poPago.getLiquidacion().getTasa().getTipo())){
			return obtenerCuaderno60Modalidad2(poPago);
		}
		return null;
	}
	
	
	/**
	 * Método que devuelve los datos de un determinado pago a partir de un cuaderno 60 Modalidad1_2
	 * @param poCuaderno Datos del cuaderno 60
	 * @return Pago Datos del pago.
	 */
	private PagoImpl obtenerPago(Cuaderno60Modalidad1_2 poCuaderno){
		PagoImpl oPago = new PagoImpl();
		
		oPago.setCcc(poCuaderno.getCcc());
		oPago.setCccDomiciliacion(poCuaderno.getCccDomiciliacion());
		oPago.setDomiciliacion(poCuaderno.getCodDomiciliacion());
		oPago.setEntidadBancaria(poCuaderno.getCodEntidad());
		oPago.setFechaCaducidadTarjetaCredito(poCuaderno.getFecCaducidadTarjetaCredito());
		oPago.setFecha(poCuaderno.getFecha());
		oPago.setHora(poCuaderno.getHora());
		oPago.setMedioPago(poCuaderno.getIdentificadorMedioPago());
		oPago.setIdioma(poCuaderno.getIdioma());
		oPago.setImporte(poCuaderno.getImporte());
		oPago.setNif(poCuaderno.getNifCertificado());
		oPago.setNrc(poCuaderno.getNrc());
		oPago.setIdEntidadEmisora(poCuaderno.getOrganismoEmisor());
		oPago.setNumeroTarjetaCredito(poCuaderno.getPan());
		oPago.setReferencia(poCuaderno.getReferencia());
		
		return oPago;
	}

	
	/**
	 * Método que devuelve los datos de un determinado pago a partir de un cuaderno 60 Modalidad3
	 * @param poCuaderno Datos del cuaderno 60
	 * @return Pago Datos del pago.
	 */
	private PagoImpl obtenerPago(Cuaderno60Modalidad3 poCuaderno){
		PagoImpl oPago = new PagoImpl();
		
		oPago.setCcc(poCuaderno.getCcc());
		oPago.setEntidadBancaria(poCuaderno.getCodEntidad());
		oPago.setFechaCaducidadTarjetaCredito(poCuaderno.getFecCaducidadTarjetaCredito());
		oPago.setFecha(poCuaderno.getFecha());
		oPago.setHora(poCuaderno.getHora());
		oPago.setMedioPago(poCuaderno.getIdentificadorMedioPago());
		oPago.setIdioma(poCuaderno.getIdioma());
		oPago.setImporte(poCuaderno.getImporte());
		oPago.setNif(poCuaderno.getNifCertificado());
		oPago.setNrc(poCuaderno.getNrc());
		oPago.setIdEntidadEmisora(poCuaderno.getOrganismoEmisor());
		oPago.setNumeroTarjetaCredito(poCuaderno.getPan());
		oPago.setReferencia(poCuaderno.getJustificante());
		oPago.setAcreditacion(poCuaderno.getAcreditacionPagos());
		oPago.setIdTasa(poCuaderno.getCodTributo());
		oPago.setExpediente(poCuaderno.getExpediente());
		oPago.setFechaDevengo(poCuaderno.getFechaDevengo());
		oPago.setInformacionEspecifica(poCuaderno.getInformacionEspecifica());
		return oPago;
	}

	
	/**
	 * Método que devuelve un Cuaderno60Modalidad1 a partir de los datos de un pago.
	 * @param poPago Datos del pago.
	 * @return Cuaderno60Modalidad1_2 Representación interna al manager de un Cuaderno60 Modalidad 1_2
	 */
	private Cuaderno60Modalidad1_2 obtenerCuaderno60Modalidad1(Pago poPago){
		Cuaderno60Modalidad1_2 oCuaderno = new Cuaderno60Modalidad1_2();
		
		oCuaderno.setEjercicio(poPago.getLiquidacion().getEjercicio());
		
		// IDENT_1 : Se rellena con 3 ceros
		oCuaderno.setIdent1(ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_CUADERNO60_M1_IDENT1));
		oCuaderno.setTipo(Cuaderno60Modalidad1_2.MODALIDAD_1);
		
		// IDENT_2: (3)Codigo del tributo (2)Ejercicio (2)Remesa
		StringBuffer sbAux = null;
		if(poPago.getLiquidacion().getTasa() != null){
			sbAux = new StringBuffer(poPago.getLiquidacion().getTasa().getCodigo());
		}else{
			sbAux = new StringBuffer(poPago.getIdTasa());
		}
		sbAux.append(poPago.getLiquidacion().getEjercicio().substring(2)).append(poPago.getLiquidacion().getRemesa());
		oCuaderno.setIdent2(sbAux.toString());
			
		oCuaderno = completarDatosCuaderno60Modalidad1_2(poPago, oCuaderno);
		return oCuaderno;
	}
	
	
	/**
	 * Método que devuelve un Cuaderno60Modalidad2 a partir de los datos de un pago.
	 * @param poPago Datos del pago.
	 * @return Cuaderno60Modalidad1_2 Representación interna al manager de un Cuaderno60 Modalidad 1_2
	 */
	private Cuaderno60Modalidad1_2 obtenerCuaderno60Modalidad2(Pago poPago){
		Cuaderno60Modalidad1_2 oCuaderno = new Cuaderno60Modalidad1_2();

		// IDENT_1: (3)Código del concepto
		oCuaderno.setIdent1(poPago.getIdTasa());
		oCuaderno.setTipo(Cuaderno60Modalidad1_2.MODALIDAD_2);

		String cAnoVencimiento = poPago.getLiquidacion().getVencimiento().substring(0, 4);		
		String cMesVencimiento = poPago.getLiquidacion().getVencimiento().substring(4, 6);
		String cDiaVencimiento = poPago.getLiquidacion().getVencimiento().substring(6, 8);
		Calendar cal = GregorianCalendar.getInstance();
		((GregorianCalendar)cal).setGregorianChange(new Date(Long.MAX_VALUE));
		cal.set(Integer.valueOf(cAnoVencimiento).intValue(),
				Integer.valueOf(cMesVencimiento).intValue() - 1, 
				Integer.valueOf(cDiaVencimiento).intValue(), 
				23, 59, 59);
		String fechaJuliana = String.valueOf(cal.get(Calendar.DAY_OF_YEAR));
		fechaJuliana=Util.rellenarConCerosIzquierda(fechaJuliana, 3);
		
		StringBuffer sbAux = new StringBuffer();
		if(ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()){
			//la pasarela externa utiliza un formato del identificador del periodo distinta a red.es
			// IDENT_2: (1)discriminante periodo (3) tributo (2)Ejercicio (1)Último digito año vencimiento (3)Fecha juliana vencimiento tributo 
			sbAux.append(poPago.getLiquidacion().getDiscriminante());
			sbAux.append(poPago.getLiquidacion().getIdTasa());
			sbAux.append(poPago.getLiquidacion().getEjercicio().substring(poPago.getLiquidacion().getEjercicio().length() -2));		
			sbAux.append(cAnoVencimiento.charAt(3));
			sbAux.append(fechaJuliana);
		}else{
			// IDENT_2: (2)Ejercicio (1)Último digito año vencimiento (3)Fecha juliana vencimiento tributo (1)discriminante periodo
			sbAux.append(poPago.getLiquidacion().getEjercicio().substring(poPago.getLiquidacion().getEjercicio().length() -2));	
			sbAux.append(cAnoVencimiento.charAt(3));
			sbAux.append(fechaJuliana);
			sbAux.append(poPago.getLiquidacion().getDiscriminante());
		}
		oCuaderno.setIdent2(sbAux.toString());
		oCuaderno = completarDatosCuaderno60Modalidad1_2(poPago, oCuaderno);
		return oCuaderno;
	}
	
	
	/**
	 * Método que completa los datos comunes a un cuaderno 60 modalidad 1 y 2
	 * @param poPago Datos del pago.
	 * @param poCuaderno Cuaderno60Modalidad1_2 con los datos específicos de su tipo informados.
	 * @return Cuaderno60Modalidad1_2 Representación interna dentro del manager de pagos de un cuaderno 60 modalidad 1 2
	 */
	private Cuaderno60Modalidad1_2 completarDatosCuaderno60Modalidad1_2(Pago poPago, Cuaderno60Modalidad1_2 poCuaderno){
		poCuaderno.setCodTributo(poPago.getLiquidacion().getTasa().getCodigo());
		poCuaderno.setDescripcion(poPago.getLiquidacion().getTasa().getNombre());
		poCuaderno.setOrganismoEmisor(poPago.getLiquidacion().getTasa().getIdEntidadEmisora());
		poCuaderno.setReferencia(poPago.getReferencia());
		poCuaderno.setNifCertificado(poPago.getNif());
		poCuaderno.setCodEntidad(poPago.getEntidadBancaria());
		poCuaderno.setCcc(poPago.getCcc());
		poCuaderno.setCccDomiciliacion(poPago.getCccDomiciliacion());
		poCuaderno.setCodDomiciliacion(poPago.getDomiciliacion());
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
	 * Método que efectúa una operación de pago de un Cuaderno60 modalidades 1 o 2.
	 * Utiliza el sistema de pago electrónico configurado para el sistema.
	 * @param poCuaderno Datos del pago con el formato de un Cuaderno60 modalidades 1 o 2.
	 * @return Cuaderno60Modalidad1_2 Cuaderno de pago con toda la información después de realizar el pago.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private Cuaderno60Modalidad1_2 pagoCuaderno60_1_2(Cuaderno60Modalidad1_2 poCuaderno) throws PagoElectronicoExcepcion{
		try {
			poCuaderno = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().pagoCuaderno60Modalidad1_2(poCuaderno);
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error realizando pago AL1-2.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_REALIZANDO_PAGO, e);
		}		
		return poCuaderno;
	}
	
	
	/**
	 * Método de búsqueda de pagos del tipo Cuaderno60 Modalidades 1 o 2.
	 * @param poCriterio Objeto que encapsula los criterios de búsqueda.
	 * @return Cuaderno60Modalidad1_2[] Array de cuadernos resultado de la búsqueda.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private Cuaderno60Modalidad1_2[] consutaCuadernos60Modalidad1_2(CriterioBusquedaPago poCriterio) throws PagoElectronicoExcepcion{
		Cuaderno60Modalidad1_2[] oResultado = null;
		try {
			oResultado = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().consultaCuaderno60Modalidad1_2(poCriterio);
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error realizando consutal de pago AL1-2.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_CONSULTA_PAGOS, e);		}
		return oResultado;
	}
	

	/**
	 * Método que efectúa una operación de pago de un Cuaderno60 modalidades 1 o 2.
	 * Utiliza el sistema de pago electrónico configurado para el sistema.
	 * @param poCuaderno Datos del pago con el formato de un Cuaderno60 modalidades 1 o 2.
	 * @return Cuaderno60Modalidad1_2 Cuaderno de pago con toda la información después de realizar el pago.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private Cuaderno60Modalidad3 pagoCuaderno60_3(Cuaderno60Modalidad3 poCuaderno) throws PagoElectronicoExcepcion{
		try {
			poCuaderno = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().pagoCuaderno60Modalidad3(poCuaderno);
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error realizando pago autoliquidación AL3.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_REALIZANDO_PAGO, e);
		}		
		return poCuaderno;
	}

	
	/**
	 * Método de búsqueda de pagos del tipo Cuaderno60 Modalidades 1 o 2.
	 * @param poCriterio Objeto que encapsula los criterios de búsqueda.
	 * @return Cuaderno60Modalidad1_2[] Array de cuadernos resultado de la búsqueda.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private Cuaderno60Modalidad3[] consutaCuadernos60Modalidad3(CriterioBusquedaPago poCriterio) throws PagoElectronicoExcepcion{
		Cuaderno60Modalidad3[] oResultado = null;
		try {
			oResultado = SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico().consultaCuaderno60Modalidad3(poCriterio);
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error realizando consutal de pago AL3.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_CONSULTA_PAGOS, e);		}
		return oResultado;
	}

	
	/**
	 * Método que da de alta una nueva liquidación en la base de datos.
	 * @param poLiquidacion Datos de la liquidación.
	 * @return Liquidacion Datos completos de la liquidación
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private LiquidacionImpl crearLiquidacion(LiquidacionImpl poLiquidacion, String entidad) throws PagoElectronicoExcepcion{
		Tasa oTasa = obtenerDatosTasa(poLiquidacion.getIdTasa(), poLiquidacion.getIdEntidadEmisora(), entidad);
	    DbConnection dbConn = new DbConnection();
	    try {
//		       dbConn.open(Configuracion.getDatabaseConnection());
//	    	   dbConn.open(DataSourceManager.getInstance().getConnection(Configuracion.getConfiguracion()));
	    	   dbConn.open(DBSessionManager.getSession(entidad));
		       dbConn.beginTransaction();
		       String cNumeroReferencia = NumeroReferenciaManagerOld.obtenerNumeroReferencia(dbConn, poLiquidacion, oTasa);
		       poLiquidacion.setEstado(Liquidacion.ESTADO_PENDIENTE);
		       poLiquidacion.setReferencia(cNumeroReferencia);
		       guardarDatosLiquidacion(dbConn, poLiquidacion);
		       dbConn.endTransaction(true);
	    } catch (Exception e) {
	       logger.error("Error creando liquidación", e);
	       throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_CREANDO_LIQUIDACION, e);
	    } finally {
	      try{
	        if (dbConn.existConnection())
	          dbConn.close();
	      }catch(Exception ee){
	       throw new DbExcepcion(DbCodigosError.EC_CLOSE_CONNECTION, ee);
	      }
	    }
	    return poLiquidacion;
	}
	
	/**
	 * Método que elimina una liquidación en la base de datos.
	 * @param poLiquidacion Datos de la liquidación.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private void eliminarLiquidacion(Liquidacion poLiquidacion, String entidad) throws PagoElectronicoExcepcion{
		LiquidacionDatos oLiqDatos = null;
		try {
	    	oLiqDatos = new LiquidacionDatos();
	    	oLiqDatos.setReferencia(poLiquidacion.getReferencia());
	    	oLiqDatos.delete(entidad);
	    } catch (Exception e) {
	       logger.error("Error elimnando liquidación", e);
	       throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ELIMINANDO_LIQUIDACION, e);
	    } 
	}
	
	
	/**
	 * Método que recupara los datos de una liquidación a partir del número de refencia
	 * @param pcNumRef Número de referencia de la liquidación
	 * @return Liquidacion Datos de la liquidación.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private LiquidacionImpl obtenerLiquidacion(String pcNumRef, String entidad) throws PagoElectronicoExcepcion{
		if(	(pcNumRef == null) || ("".equals(pcNumRef))	){
			return null;
		}
		LiquidacionDatos oLiquidacionDatos = new LiquidacionDatos();
		try {
			oLiquidacionDatos.load(pcNumRef, entidad);
		} catch (Exception e) {
			StringBuffer sbError = new StringBuffer("Error obteniendo datos de la liquidación. Número de referencia: ");
			sbError.append(pcNumRef);
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_RECUPERANDO_LIQUIDACION, e);
		} 
		return oLiquidacionDatos;
	}
	
	
	/**
	 * Método que actualiza en la base de datos una determinada liquidación
	 * @param poLiquidacion Datos de la liquidación
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private void actualizarLiquidacion(Liquidacion poLiquidacion, String entidad) throws PagoElectronicoExcepcion{
		LiquidacionDatos oLiquidacionDatos = new LiquidacionDatos();
		oLiquidacionDatos.setEjercicio(poLiquidacion.getEjercicio());
		oLiquidacionDatos.setEstado(poLiquidacion.getEstado());
		oLiquidacionDatos.setIdEntidadEmisora(poLiquidacion.getIdEntidadEmisora());
		oLiquidacionDatos.setIdTasa(poLiquidacion.getIdTasa());
		oLiquidacionDatos.setImporte(poLiquidacion.getImporte());
		oLiquidacionDatos.setNif(poLiquidacion.getNif());
		oLiquidacionDatos.setNrc(poLiquidacion.getNrc());
		oLiquidacionDatos.setReferencia(poLiquidacion.getReferencia());
		oLiquidacionDatos.setRemesa(poLiquidacion.getRemesa());
		oLiquidacionDatos.setVencimiento(poLiquidacion.getVencimiento());
		oLiquidacionDatos.setDiscriminante(poLiquidacion.getDiscriminante());
		oLiquidacionDatos.setNombre(poLiquidacion.getNombre());
		oLiquidacionDatos.setDatosEspecificos(poLiquidacion.getDatosEspecificos());
		oLiquidacionDatos.setInicioPeriodo(poLiquidacion.getInicioPeriodo());
		oLiquidacionDatos.setFinPeriodo(poLiquidacion.getFinPeriodo());
		oLiquidacionDatos.setSolicitud(poLiquidacion.getSolicitud());
		oLiquidacionDatos.setFechaPago(poLiquidacion.getFechaPago());
		try {
			oLiquidacionDatos.update(entidad);
		}catch (DbExcepcion e) {
			StringBuffer sbError = new StringBuffer("Error actualizando datos de liquidación. Referencia: ");
			sbError.append(poLiquidacion.getReferencia());
			logger.error(sbError.toString(), e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ACTUALIZANDO_LIQUIDACION);
		}
	}
	

	/**
	 * Método que busca liquidaciones según el criterio que le llega como parámetro
	 * @param poLiquidacion Datos de
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private List busquedaTasas(CriterioBusquedaTasa poCriterio, String entidad) throws PagoElectronicoExcepcion{
		TasaTabla oTasaTabla = new TasaTabla();
		List resultado = null;
		try {
			resultado = oTasaTabla.buscarTasas(poCriterio, null, entidad);
		}catch (DbExcepcion e) {
			StringBuffer sbError = new StringBuffer("Error ejecutando busqueda de Tasaes.");
			logger.error(sbError.toString(), e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_BUSCANDO_LIQUIDACION);
		}
		return resultado;
	}

	
	/**
	 * Método que busca liquidaciones según el criterio que le llega como parámetro
	 * @param poLiquidacion Datos de
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private List busquedaLiquidaciones(CriterioBusquedaLiquidacion poCriterio, String entidad) throws PagoElectronicoExcepcion{
		LiquidacionTabla oLiquidacionTabla = new LiquidacionTabla();
		List resultado = null;
		try {
			resultado = oLiquidacionTabla.buscarLiquidaciones(poCriterio, null, entidad);
		}catch (DbExcepcion e) {
			StringBuffer sbError = new StringBuffer("Error ejecutando busqueda de liquidaciones.");
			logger.error(sbError.toString(), e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_BUSCANDO_LIQUIDACION);
		}
		return resultado;
	}
	
	/**
	 * Método que guarda los datos correspondientes a una liquidación
	 * @param dbConn Conexión a base de datos con una transacción activa.
	 * @param poLiquidacion Datos de la liquidación.
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private void guardarDatosLiquidacion(DbConnection dbConn, Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		LiquidacionDatos oLiquidacionDatos = new LiquidacionDatos();
		oLiquidacionDatos.setEjercicio(poLiquidacion.getEjercicio());
		oLiquidacionDatos.setEstado(poLiquidacion.getEstado());
		oLiquidacionDatos.setIdEntidadEmisora(poLiquidacion.getIdEntidadEmisora());
		oLiquidacionDatos.setIdTasa(poLiquidacion.getIdTasa());
		oLiquidacionDatos.setImporte(poLiquidacion.getImporte());
		oLiquidacionDatos.setNif(poLiquidacion.getNif());
		oLiquidacionDatos.setNrc(poLiquidacion.getNrc());
		oLiquidacionDatos.setReferencia(poLiquidacion.getReferencia());
		oLiquidacionDatos.setRemesa(poLiquidacion.getRemesa());
		oLiquidacionDatos.setVencimiento(poLiquidacion.getVencimiento());
		oLiquidacionDatos.setDiscriminante(poLiquidacion.getDiscriminante());
		oLiquidacionDatos.setNombre(poLiquidacion.getNombre());
		oLiquidacionDatos.setDatosEspecificos(poLiquidacion.getDatosEspecificos());
		oLiquidacionDatos.setInicioPeriodo(poLiquidacion.getInicioPeriodo());
		oLiquidacionDatos.setFinPeriodo(poLiquidacion.getFinPeriodo());
		oLiquidacionDatos.setSolicitud(poLiquidacion.getSolicitud());
		try {
			oLiquidacionDatos.add(dbConn);
		}catch (DbExcepcion e) {
			StringBuffer sbError = new StringBuffer("Error guardando datos de liquidación. Referencia: ");
			sbError.append(oLiquidacionDatos.getReferencia());
			logger.error(sbError.toString(), e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_CREANDO_LIQUIDACION, e);
		}
	}

	
	/**
	 * Método que valida los datos de entrada de un alta de liquidación
	 * @param poLiquidacion Datos de la liquidación
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */
	private void validarAltaLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		validarLiquidacionAutoliquidacion(poLiquidacion);
	}
	
	/**
	 * Método que valida los datos de las liquidaciones/autoliquidaciones
	 * @param poLiquidacion Datos de la liquidación
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */
	private void validarLiquidacionAutoliquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
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
		
		// id_entidad emisora
		if(	(poLiquidacion.getIdEntidadEmisora() == null)
			|| (poLiquidacion.getIdEntidadEmisora().length() < 1) || (poLiquidacion.getIdEntidadEmisora().length() > 8)	){
			logger.error("Parámetro IdEntidadEmisora incorrecto");
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
		
		if(	(Tasa.TIPO_AL1.equals(poLiquidacion.getTasa().getTipo()))
			||(Tasa.TIPO_AL2.equals(poLiquidacion.getTasa().getTipo())) ){
			// ejercicio
			if(	(poLiquidacion.getEjercicio() == null)
				|| (poLiquidacion.getEjercicio().length() < 1) || (poLiquidacion.getEjercicio().length() > 6) ){
				logger.error("Parámetro Ejercicio incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
			}
			
			if(Tasa.TIPO_AL1.equals(poLiquidacion.getTasa().getTipo())){
				// remesa
				if(	(poLiquidacion.getRemesa() == null)
						|| (poLiquidacion.getRemesa().length() < 1)	|| (poLiquidacion.getRemesa().length() > 2) ){
					logger.error("Parámetro Remesa incorrecto");
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
				}			
			}else if(Tasa.TIPO_AL2.equals(poLiquidacion.getTasa().getTipo())){
				// vencimiento
				if(	(poLiquidacion.getVencimiento() == null)
						|| (poLiquidacion.getVencimiento().length() < 1) || (poLiquidacion.getVencimiento().length() > 8) ){
					logger.error("Parámetro Vencimiento incorrecto");
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
				}			

				// discriminante periodo
				if(poLiquidacion.getDiscriminante() != null){
					if( (!Liquidacion.DISCRIMINANTE_UN_PERIODO.equals(poLiquidacion.getDiscriminante()))
						&&(!Liquidacion.DISCRIMINANTE_DOS_PERIODOS_PRIMER_IMPORTE.equals(poLiquidacion.getDiscriminante()))
						&&(!Liquidacion.DISCRIMINANTE_DOS_PERIODOS_SEGUNDO_IMPORTE.equals(poLiquidacion.getDiscriminante())) ){
						logger.error("Parámetro Discriminante incorrecto.");
						throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);
					}
				}else{
					poLiquidacion.setDiscriminante(Liquidacion.DISCRIMINANTE_UN_PERIODO);
				}
				
			}					
		}
	}

	/**
	 * Método que valida los datos de entrada de una modifición de liquidación
	 * @param poLiquidacion Datos de la liquidación
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */
	private void validarModificacionLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		validarLiquidacionAutoliquidacion(poLiquidacion);

		// referencia
		if(	(poLiquidacion.getIdTasa() == null)
				|| (poLiquidacion.getIdTasa().length() < 1) || (poLiquidacion.getIdTasa().length() > 13) ){
			logger.error("Parámetro Referencia incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_MODIFICACION_LIQUIDACION_BAD_PARAMS);			
		}
	}


	/**
	 * Método que valida los datos de entrada de una baja de liquidación
	 * @param poLiquidacion Datos de la liquidación
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */
	private void validarBajaLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		if(poLiquidacion == null){
			logger.error("Los datos de la liquidación son null.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);
		}
				
		// id_tasa
		if(	(poLiquidacion.getReferencia() == null)
				|| (poLiquidacion.getReferencia().length() < 1) || (poLiquidacion.getReferencia().length() > 13) ){
			logger.error("Parámetro Referencia incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_BAJA_LIQUIDACION_BAD_PARAMS);			
		}		
	}

	/**
	 * Método que valida los datos de entrada de un pago
	 * @param poPago Datos del pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error.
	 */
	private void validaRealizarPago(Pago poPago) throws PagoElectronicoExcepcion{
		if(poPago == null){
			logger.error("Los datos de la liquidación son null.");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);
		}
		
		// id_tasa
		if(	(poPago.getIdTasa() == null)
				|| (poPago.getIdTasa().length() < 1) || (poPago.getIdTasa().length() > 3) ){
			logger.error("Parámetro IdTasa incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
		}
		
		// id_entidad emisora
		if(	(poPago.getIdEntidadEmisora() == null)
				|| (poPago.getIdEntidadEmisora().length() < 1) || (poPago.getIdEntidadEmisora().length() > 8) ){
			logger.error("Parámetro IdEntidadEmisora incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
		}		

		// NIF
		if(	(poPago.getNif()== null)
				|| (poPago.getNif().length() < 1) || (poPago.getNif().length() > 9)	){
			logger.error("Parámetro NIF incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
		}		

		// idioma
		if(poPago.getIdioma() != null){
			if(	(poPago.getIdioma().length() < 1) || (poPago.getIdioma().length() > 2)	){
				logger.error("Parámetro idioma incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
			}			
		}else{
			poPago.setIdioma(Pago.IDIOMA_CASTELLANO);
		}
		
		if(!ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()){
			
			// medio de pago
			if(	(poPago.getMedioPago() == null)
					|| (poPago.getMedioPago().length() < 1) || (poPago.getMedioPago().length() > 1)	){
				logger.error("Parámetro Medio Pago incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
			}		
			// Según el medio de pago
			if(Pago.MEDIO_PAGO_CARGO_CUENTA.equals(poPago.getMedioPago())){
				// ccc
				if(	(poPago.getCcc() == null)
						|| (poPago.getCcc().length() < 1) || (poPago.getCcc().length() > 20) ){
					logger.error("Parámetro CCC incorrecto");
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
				}
				// entidad
				if(	(poPago.getEntidadBancaria() == null)
						|| (poPago.getEntidadBancaria().length() < 1) || (poPago.getEntidadBancaria().length() > 4)	){
					logger.error("Parámetro Entidad Bancaria incorrecto");
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
				}					
				// Si es una domiciliacion
				if((poPago.getDomiciliacion() != null) && (!"".equals(poPago.getDomiciliacion()))){
					if(	(poPago.getDomiciliacion().length() < 1) || (poPago.getDomiciliacion().length() > 1) ){
						logger.error("Parámetro Domiciliación incorrecto");
						throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
					}else{
						if(Pago.DOMICILIACION_SI.equals(poPago.getDomiciliacion())){
							//	ccc domiciliacion
							if(	(poPago.getCccDomiciliacion() == null)
								|| (poPago.getCccDomiciliacion().length() < 1) || (poPago.getCccDomiciliacion().length() > 20)	){
								logger.error("Parámetro CCC Domiciliación incorrecto");
								throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
							}						
						}else{
							poPago.setDomiciliacion(Pago.DOMICILIACION_NO);
						}
					}
				}else{
					poPago.setDomiciliacion(Pago.DOMICILIACION_NO);
				}
			}else if(Pago.MEDIO_PAGO_TARJETA.equals(poPago.getMedioPago())){
				//	numero tarjeta
				if(	(poPago.getNumeroTarjetaCredito() == null)
					|| (poPago.getNumeroTarjetaCredito().length() < 1)	|| (poPago.getNumeroTarjetaCredito().length() > 16) ){
					logger.error("Parámetro Número Tarjeta Credito incorrecto");
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
				}
				//	numero tarjeta
				if(		(poPago.getFechaCaducidadTarjetaCredito() == null)
					|| (poPago.getFechaCaducidadTarjetaCredito().length() < 1) || (poPago.getFechaCaducidadTarjetaCredito().length() > 4) ){
					logger.error("Parámetro Fecha Caducidad Tarjeta Credito incorrecto");
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
				}			
			}else{
				logger.error("Parámetro Medio Pago incorrecto");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);
			}
		}
	}
	
	/**
	 * Método que valida los datos de entrada específicos de un pago del tipo AL3
	 * @param poPago Datos del Pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */	
	private void validaPagoAL3(Pago poPago) throws PagoElectronicoExcepcion{
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

	
	/**
	 * Método que valida los datos de entrada específicos de un pago del tipo AL1
	 * @param poPago Datos del Pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */	
	private void validaPagoAL1(Pago poPago) throws PagoElectronicoExcepcion{
		validaPagoLiquidacion(poPago);
	}
	
	/**
	 * Método que valida los datos de entrada específicos de un pago del tipo AL2
	 * @param poPago Datos del Pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación.
	 */
	private void validaPagoAL2(Pago poPago) throws PagoElectronicoExcepcion{
		validaPagoLiquidacion(poPago);
	}

	/**
	 * Método que valida los datos comunes a pagos correspondientes a liquidaciones
	 * @param poPago Datos del pago
	 * @throws PagoElectronicoExcepcion En caso de producirse algún error de validación
	 */
	private void validaPagoLiquidacion(Pago poPago) throws PagoElectronicoExcepcion{
		// referencia
		if(	(poPago.getReferencia() == null)
				|| (poPago.getReferencia().length() < 1) || (poPago.getReferencia().length() > 12) ){
			logger.error("Parámetro referencia incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_BAD_PARAMS);			
		}
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
	
	private static String obtenerDocumentoPagoCuaderno60_1_2(Cuaderno60Modalidad1_2 poCuaderno, Liquidacion poLiquidacion, boolean pbCabeceraEstandar){
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
				  bdr.addSimpleElement(Cuaderno60XMLTags.NOMBRE_TAG, poLiquidacion.getNombre());
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
	
	private static String obtenerDocumentoPagoCuaderno60_3(Cuaderno60Modalidad3 poCuaderno, Liquidacion poLiquidacion, boolean pbCabeceraEstandar){
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
				  bdr.addSimpleElement(Cuaderno60XMLTags.NOMBRE_TAG, poLiquidacion.getNombre());
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
	
}
