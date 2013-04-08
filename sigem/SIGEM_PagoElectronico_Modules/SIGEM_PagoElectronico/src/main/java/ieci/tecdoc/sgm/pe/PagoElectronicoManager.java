package ieci.tecdoc.sgm.pe;
/*
 * $Id: PagoElectronicoManager.java,v 1.1.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.pe.cuadernos.ManejadorCuadernosFactory;
import ieci.tecdoc.sgm.pe.database.DBSessionManager;
import ieci.tecdoc.sgm.pe.database.LiquidacionDatos;
import ieci.tecdoc.sgm.pe.database.LiquidacionTabla;
import ieci.tecdoc.sgm.pe.database.TasaDatos;
import ieci.tecdoc.sgm.pe.database.TasaTabla;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.util.List;

import org.apache.log4j.Logger;

public class PagoElectronicoManager {

	/** 
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger.getLogger(PagoElectronicoManager.class);

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
		
		ManejadorCuadernosFactory.getInstance().obtenerManejadorCuaderno(poLiquidacion.getTasa().getTipo())
			.validarLiquidacion(poLiquidacion);
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
		
		ManejadorCuadernosFactory.getInstance().obtenerManejadorCuaderno(poLiquidacion.getTasa().getTipo())
			.validarLiquidacion(poLiquidacion);
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

		// comprobar si es un pago pendiente
		LiquidacionImpl oLiquidacion = null;
		if( poPago.getLiquidacion() != null){
			oLiquidacion = poPago.getLiquidacion();				
		}else{
			if(poPago.getReferencia() != null){
				oLiquidacion = obtenerLiquidacion(poPago.getReferencia(), entidad);	
			}
		}
		
		if(oLiquidacion == null){
			if((Tasa.TIPO_AL1.equalsIgnoreCase(oTasa.getTipo()) ||
					 Tasa.TIPO_AL2.equalsIgnoreCase(oTasa.getTipo()))){
				StringBuffer sbError = new StringBuffer("Se ha intentado realizar un pago de una liquidación inexistente.");
				sbError.append(" Número de referencia: ").append(poPago.getReferencia());
				logger.error(sbError.toString());
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_LIQUIDACION_SIN_LIQUIDACION);
			}
			// Crear liquidación asociada.
			oLiquidacion = new LiquidacionImpl();
			oLiquidacion.setIdTasa(oTasa.getCodigo());
			oLiquidacion.setIdEntidadEmisora(oTasa.getIdEntidadEmisora());
			oLiquidacion.setImporte(poPago.getImporte());
			oLiquidacion.setNif(poPago.getNif());
			oLiquidacion.setDatosEspecificos(poPago.getInformacionEspecifica());
			//altaLiquidacion(oLiquidacion);
		}
		
		oLiquidacion.setTasa(oTasa);
		poPago.setLiquidacion(oLiquidacion);
		
		// obtener instancia del cuaderno
		cRetorno = ManejadorCuadernosFactory.getInstance().obtenerManejadorCuaderno(oTasa.getTipo())
			 .obtenerDocumentoPago(poPago, oLiquidacion, false);
		
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
		
		//validar datos de liquidacion
		ManejadorCuadernosFactory.getInstance().obtenerManejadorCuaderno(oTasa.getTipo())
		 	.validarPago(poPago);
		
		LiquidacionImpl oLiquidacion = null;
		if(poPago.getLiquidacion()== null ){
			oLiquidacion = obtenerLiquidacion(poPago.getReferencia(), entidad);	
		}else{
			oLiquidacion = poPago.getLiquidacion();
		}
		
		if(oLiquidacion == null){
			if((Tasa.TIPO_AL1.equalsIgnoreCase(oTasa.getTipo()))
				||(Tasa.TIPO_AL2.equalsIgnoreCase(oTasa.getTipo()))){
				StringBuffer sbError = new StringBuffer("Se ha intentado realizar un pago de una liquidación inexistente.");
				sbError.append(" Número de referencia: ").append(poPago.getReferencia());
				logger.error(sbError.toString());
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_LIQUIDACION_SIN_LIQUIDACION);
			}
			// Crear liquidación asociada.
			oLiquidacion = new LiquidacionImpl();
			oLiquidacion.setIdTasa(oTasa.getCodigo());
			oLiquidacion.setIdEntidadEmisora(oTasa.getIdEntidadEmisora());
			oLiquidacion.setImporte(poPago.getImporte());
			oLiquidacion.setNif(poPago.getNif());
			altaLiquidacion(oLiquidacion, entidad);
		}
		
		oLiquidacion.setTasa(oTasa);
		// Comprobar que no este ya pagada
		if(oLiquidacion.getEstado().equals(Liquidacion.ESTADO_PAGADO)){
			StringBuffer sbError = new StringBuffer("Se ha intentado realizar una liquidación ya satisfecha.");
			sbError.append(" Número de referencia: ").append(poPago.getReferencia());
			logger.error(sbError.toString());
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_LIQUIDACION_YA_REALIZADA);
		}
		
		poPago.setLiquidacion(oLiquidacion);
		
		poPago = ManejadorCuadernosFactory.getInstance().obtenerManejadorCuaderno(oTasa.getTipo())
		 	.invocarSistemaPago(poPago);
		if(!ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()){
			oLiquidacion.setNrc(poPago.getNrc());
			oLiquidacion.setEstado(poPago.getEstado());
			oLiquidacion.setFechaPago(Util.parsearFechaCuaderno69(poPago.getFecha(), poPago.getHora()));
		
			// actualizar estado de la liquidacion
			actualizarLiquidacion(oLiquidacion, entidad);
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
		
		CriterioBusquedaPago oCriterio = new CriterioBusquedaPago();
		oCriterio.setReferencia(pcNumReferencia);
		oCriterio.setTipo(oTasa.getTipo());
		oCriterio.setEntidad(entidad);
	
		try{ 
			oPago = ManejadorCuadernosFactory.getInstance().obtenerManejadorCuaderno(oTasa.getTipo())
					.consultaCuadernos(oCriterio); 
		}catch(Exception e){ logger.info(e); }
		
		
		if(	oPago==null	){
			oPago = new PagoImpl();
			oPago.setReferencia(pcNumReferencia);
		}	
		
		oLiquidacion.setTasa(oTasa);
		if(oPago!=null) oPago.setLiquidacion(oLiquidacion);
		
		// devolver respuesta
		return oPago;
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
		       //String cNumeroReferencia = NumeroReferenciaManager.obtenerNumeroReferencia(dbConn, poLiquidacion, oTasa);
		       String cNumeroReferencia=ManejadorCuadernosFactory.getInstance()
		       				.obtenerManejadorCuaderno(oTasa.getTipo())
		       				.generarNumeroReferencia(dbConn, poLiquidacion);
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
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ACTUALIZANDO_LIQUIDACION,e);
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
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_BUSCANDO_LIQUIDACION,e);
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
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_BUSCANDO_LIQUIDACION,e);
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
	
	
}
