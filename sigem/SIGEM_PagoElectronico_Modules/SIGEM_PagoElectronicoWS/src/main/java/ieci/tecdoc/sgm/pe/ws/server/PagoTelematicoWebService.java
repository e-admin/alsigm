package ieci.tecdoc.sgm.pe.ws.server;
/*
 * $Id: PagoTelematicoWebService.java,v 1.1.4.1 2008/01/25 12:30:46 jconca Exp $
 */
import java.util.Iterator;
import java.util.List;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaTasa;
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Pago;
import ieci.tecdoc.sgm.core.services.pago.PagoTelematicoException;
import ieci.tecdoc.sgm.core.services.pago.ServicioPagoTelematico;
import ieci.tecdoc.sgm.core.services.pago.Tasa;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

public class PagoTelematicoWebService {

	private static final Logger logger = Logger.getLogger(PagoTelematicoWebService.class);
	
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_PAYMENT;
	
	public ieci.tecdoc.sgm.pe.ws.server.Liquidacion altaLiquidacion(ieci.tecdoc.sgm.pe.ws.server.Liquidacion poLiquidacion, Entidad entidad){
		ieci.tecdoc.sgm.pe.ws.server.Liquidacion oLiquidacion =  null;
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();			
			oLiquidacion = getLiquidacionWS(oService.altaLiquidacion(getLiquidacionServicio(poLiquidacion), entidad));
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)ServiciosUtils.completeReturnOK(oLiquidacion);
		} catch (PagoTelematicoException e) {
			logger.error("Error en alta de liquidación.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Liquidacion()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en alta de liquidación.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Liquidacion()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en alta de liquidación.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Liquidacion()));
		}
	}

	public RetornoServicio bajaLiquidacion(ieci.tecdoc.sgm.pe.ws.server.Liquidacion poLiquidacion, Entidad entidad){
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();			
			oService.bajaLiquidacion(getLiquidacionServicio(poLiquidacion), entidad);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)ServiciosUtils.completeReturnOK(poLiquidacion);
		} catch (PagoTelematicoException e) {
			logger.error("Error en baja de liquidación.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Liquidacion()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en baja de liquidación.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Liquidacion()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en baja de liquidación.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Liquidacion()));
		}		
	}

	public ListaLiquidaciones buscarLiquidaciones(ieci.tecdoc.sgm.pe.ws.server.CriterioBusquedaLiquidacion poCriterio, Entidad entidad){
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();			
			ListaLiquidaciones oLista = getListaLiquidaciones(
							oService.buscarLiquidaciones(
									getCriterioBusquedaLiquidacionServicio(poCriterio), entidad));
			return (ListaLiquidaciones)ServiciosUtils.completeReturnOK(oLista);
		} catch (PagoTelematicoException e) {
			logger.error("Error en búsqueda de liquidaciones.", e);
			return (ListaLiquidaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ListaLiquidaciones()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en búsqueda de liquidaciones.", e);
			return (ListaLiquidaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ListaLiquidaciones()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en búsqueda de liquidaciones.", e);
			return (ListaLiquidaciones)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ListaLiquidaciones()));
		}		
	}
	

	public ListaTasas buscarTasas(ieci.tecdoc.sgm.pe.ws.server.CriterioBusquedaTasa poCriterio, Entidad entidad){
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();			
			ListaTasas oTasas = getListaTasas(
										oService.buscarTasas(
												getCriterioBusquedaTasaService(poCriterio), entidad));
			return (ListaTasas)ServiciosUtils.completeReturnOK(oTasas);
		} catch (PagoTelematicoException e) {
			logger.error("Error en búsqueda de tasas.", e);
			return (ListaTasas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ListaTasas()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en búsqueda de tasas.", e);
			return (ListaTasas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ListaTasas()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en búsqueda de tasas.", e);
			return (ListaTasas)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ListaTasas()));
		}		
		
	}
	
	public ieci.tecdoc.sgm.pe.ws.server.Pago detallePago(ieci.tecdoc.sgm.pe.ws.server.Pago poPago, Entidad entidad){
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();			
			ieci.tecdoc.sgm.pe.ws.server.Pago oPago = 
				getPagoWS(oService.detallePago(poPago.getReferencia(), entidad));
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
						ServiciosUtils.completeReturnOK((RetornoServicio)oPago);
		} catch (PagoTelematicoException e) {
			logger.error("Error en detalle de pago.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Pago()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en detalle de pago.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Pago()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en detalle de pagon.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new ieci.tecdoc.sgm.pe.ws.server.Pago()));
		}				
	}
	
	
	public ieci.tecdoc.sgm.pe.ws.server.Liquidacion modificarLiquidacion(ieci.tecdoc.sgm.pe.ws.server.Liquidacion poLiquidacion, Entidad entidad){
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();			
			oService.modificarLiquidacion(getLiquidacionServicio(poLiquidacion), entidad);
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
					ServiciosUtils.completeReturnOK((RetornoServicio)poLiquidacion);
			
		} catch (PagoTelematicoException e) {
			logger.error("Error en modificación de liquidación.", e);
			if(poLiquidacion == null){
				poLiquidacion = new ieci.tecdoc.sgm.pe.ws.server.Liquidacion();
			}
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(poLiquidacion),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en modificación de liquidación.", e);
			if(poLiquidacion == null){
				poLiquidacion = new ieci.tecdoc.sgm.pe.ws.server.Liquidacion();
			}			
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(poLiquidacion),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en modificación de liquidación.", e);
			if(poLiquidacion == null){
				poLiquidacion = new ieci.tecdoc.sgm.pe.ws.server.Liquidacion();
			}			
			return (ieci.tecdoc.sgm.pe.ws.server.Liquidacion)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(poLiquidacion));
		}		
	}

	
	public ieci.tecdoc.sgm.pe.ws.server.Tasa obtenerDatosTasa(ieci.tecdoc.sgm.pe.ws.server.Tasa poTasa, Entidad entidad){
		if(poTasa == null){
			poTasa = new ieci.tecdoc.sgm.pe.ws.server.Tasa();
		}
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();			
			ieci.tecdoc.sgm.pe.ws.server.Tasa oTasa = 
				getTasaWS(oService.obtenerDatosTasa(poTasa.getCodigo(), poTasa.getIdEntidadEmisora(), entidad));
			return(ieci.tecdoc.sgm.pe.ws.server.Tasa)
					ServiciosUtils.completeReturnOK((RetornoServicio)oTasa);
		} catch (PagoTelematicoException e) {
			logger.error("Error en obtención datos tasa.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Tasa)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(poTasa),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en obtención datos tasa.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Tasa)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(poTasa),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en obtención datos tasa.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Tasa)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(poTasa));
		}				
	}
	
	
	public DocumentoPago obtenerDocumentoPago(ieci.tecdoc.sgm.pe.ws.server.Pago poPago, Entidad entidad){
		DocumentoPago odoc = new DocumentoPago();
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();
			odoc.setDocumentoPago(oService.obtenerDocumentoPago(getPagoServicio(poPago), entidad));
			return (DocumentoPago)ServiciosUtils.completeReturnOK((RetornoServicio)odoc);
		} catch (PagoTelematicoException e) {
			logger.error("Error en obtención documento de pago", e);
			return (DocumentoPago)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(odoc),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en obtención documento de pago.", e);
			return (DocumentoPago)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(odoc),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en obtención documento de pago.", e);
			return (DocumentoPago)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(odoc));
		}				
		
	}
	
	public ieci.tecdoc.sgm.pe.ws.server.Pago realizarPago(ieci.tecdoc.sgm.pe.ws.server.Pago poPago, Entidad entidad){
		if(poPago == null){
			poPago = new ieci.tecdoc.sgm.pe.ws.server.Pago();
		}
		try {
			ServicioPagoTelematico oService = getServicioPagoTelematico();
			ieci.tecdoc.sgm.pe.ws.server.Pago oPago = 
				getPagoWS(oService.realizarPago(getPagoServicio(poPago), entidad));
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
					ServiciosUtils.completeReturnOK((RetornoServicio)oPago);
		} catch (PagoTelematicoException e) {
			logger.error("Error en realizar pago.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(poPago),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en realizar pago.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(poPago),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en realizar pago.", e);
			return (ieci.tecdoc.sgm.pe.ws.server.Pago)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(poPago));
		}						
	}
	
	private ServicioPagoTelematico getServicioPagoTelematico() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioPagoTelematico();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioPagoTelematico(sbImpl.toString());
		}
	}

	private Liquidacion getLiquidacionServicio(ieci.tecdoc.sgm.pe.ws.server.Liquidacion poLiquidacion){
		if(poLiquidacion == null){
			return null;
		}
		Liquidacion oLiquidacion = new Liquidacion();
		oLiquidacion.setDatosEspecificos(poLiquidacion.getDatosEspecificos());
		oLiquidacion.setDiscriminante(poLiquidacion.getDiscriminante());
		oLiquidacion.setEjercicio(poLiquidacion.getEjercicio());
		oLiquidacion.setEstado(poLiquidacion.getEstado());
		oLiquidacion.setFechaPago(poLiquidacion.getFechaPago());
		oLiquidacion.setFinPeriodo(poLiquidacion.getFinPeriodo());
		oLiquidacion.setIdEntidadEmisora(poLiquidacion.getIdEntidadEmisora());
		oLiquidacion.setIdTasa(poLiquidacion.getIdTasa());
		oLiquidacion.setImporte(poLiquidacion.getImporte());
		oLiquidacion.setInicioPeriodo(poLiquidacion.getInicioPeriodo());
		oLiquidacion.setNif(poLiquidacion.getNif());
		oLiquidacion.setNombre(poLiquidacion.getNombre());
		oLiquidacion.setNrc(poLiquidacion.getNrc());
		oLiquidacion.setReferencia(poLiquidacion.getReferencia());
		oLiquidacion.setRemesa(poLiquidacion.getRemesa());
		oLiquidacion.setSolicitud(poLiquidacion.getSolicitud());
		oLiquidacion.setTasa(getTasaServicio(poLiquidacion.getTasa()));
		oLiquidacion.setVencimiento(poLiquidacion.getVencimiento());
		
		return oLiquidacion;
	}

	
	private ieci.tecdoc.sgm.pe.ws.server.Pago getPagoWS(Pago poPago){
		if(poPago == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.server.Pago oPago = new ieci.tecdoc.sgm.pe.ws.server.Pago();
		oPago.setAcreditacion(poPago.getAcreditacion());
		oPago.setCcc(poPago.getCcc());
		oPago.setCccDomiciliacion(poPago.getCccDomiciliacion());
		oPago.setDomiciliacion(poPago.getDomiciliacion());
		oPago.setEjercicio(poPago.getEjercicio());
		oPago.setEntidadBancaria(poPago.getEntidadBancaria());
		oPago.setEstado(poPago.getEstado());
		oPago.setExpediente(poPago.getExpediente());
		oPago.setFecha(poPago.getFecha());
		oPago.setFechaCaducidadTarjetaCredito(poPago.getFechaCaducidadTarjetaCredito());
		oPago.setFechaDevengo(poPago.getFechaDevengo());
		oPago.setHora(poPago.getHora());
		oPago.setIdEntidadEmisora(poPago.getIdEntidadEmisora());
		oPago.setIdioma(poPago.getIdioma());
		oPago.setIdTasa(poPago.getIdTasa());
		oPago.setImporte(poPago.getImporte());
		oPago.setInformacionEspecifica(poPago.getInformacionEspecifica());
		oPago.setLiquidacion(getLiquidacionWS(poPago.getLiquidacion()));
		oPago.setMedioPago(poPago.getMedioPago());
		oPago.setNif(poPago.getNif());
		oPago.setNrc(poPago.getNrc());
		oPago.setNumeroTarjetaCredito(poPago.getNumeroTarjetaCredito());
		oPago.setReferencia(poPago.getReferencia());
		oPago.setRemesa(poPago.getRemesa());
		oPago.setTasa(getTasaWS(poPago.getTasa()));

		return oPago;

	}
	
	private Pago getPagoServicio(ieci.tecdoc.sgm.pe.ws.server.Pago poPago){
		if(poPago == null){
			return null;
		}
		Pago oPago = new Pago();
		oPago.setAcreditacion(poPago.getAcreditacion());
		oPago.setCcc(poPago.getCcc());
		oPago.setCccDomiciliacion(poPago.getCccDomiciliacion());
		oPago.setDomiciliacion(poPago.getDomiciliacion());
		oPago.setEjercicio(poPago.getEjercicio());
		oPago.setEntidadBancaria(poPago.getEntidadBancaria());
		oPago.setEstado(poPago.getEstado());
		oPago.setExpediente(poPago.getExpediente());
		oPago.setFecha(poPago.getFecha());
		oPago.setFechaCaducidadTarjetaCredito(poPago.getFechaCaducidadTarjetaCredito());
		oPago.setFechaDevengo(poPago.getFechaDevengo());
		oPago.setHora(poPago.getHora());
		oPago.setIdEntidadEmisora(poPago.getIdEntidadEmisora());
		oPago.setIdioma(poPago.getIdioma());
		oPago.setIdTasa(poPago.getIdTasa());
		oPago.setImporte(poPago.getImporte());
		oPago.setInformacionEspecifica(poPago.getInformacionEspecifica());
		oPago.setLiquidacion(getLiquidacionServicio(poPago.getLiquidacion()));
		oPago.setMedioPago(poPago.getMedioPago());
		oPago.setNif(poPago.getNif());
		oPago.setNrc(poPago.getNrc());
		oPago.setNumeroTarjetaCredito(poPago.getNumeroTarjetaCredito());
		oPago.setReferencia(poPago.getReferencia());
		oPago.setRemesa(poPago.getRemesa());
		oPago.setTasa(getTasaServicio(poPago.getTasa()));

		return oPago;
	}
	private ieci.tecdoc.sgm.pe.ws.server.Liquidacion getLiquidacionWS(Liquidacion poLiquidacion){
		if(poLiquidacion == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.server.Liquidacion oLiquidacion = new ieci.tecdoc.sgm.pe.ws.server.Liquidacion();
		oLiquidacion.setDatosEspecificos(poLiquidacion.getDatosEspecificos());
		oLiquidacion.setDiscriminante(poLiquidacion.getDiscriminante());
		oLiquidacion.setEjercicio(poLiquidacion.getEjercicio());
		oLiquidacion.setEstado(poLiquidacion.getEstado());
		oLiquidacion.setFechaPago(poLiquidacion.getFechaPago());
		oLiquidacion.setFinPeriodo(poLiquidacion.getFinPeriodo());
		oLiquidacion.setIdEntidadEmisora(poLiquidacion.getIdEntidadEmisora());
		oLiquidacion.setIdTasa(poLiquidacion.getIdTasa());
		oLiquidacion.setImporte(poLiquidacion.getImporte());
		oLiquidacion.setInicioPeriodo(poLiquidacion.getInicioPeriodo());
		oLiquidacion.setNif(poLiquidacion.getNif());
		oLiquidacion.setNombre(poLiquidacion.getNombre());
		oLiquidacion.setNrc(poLiquidacion.getNrc());
		oLiquidacion.setReferencia(poLiquidacion.getReferencia());
		oLiquidacion.setRemesa(poLiquidacion.getRemesa());
		oLiquidacion.setSolicitud(poLiquidacion.getSolicitud());
		oLiquidacion.setTasa(getTasaWS(poLiquidacion.getTasa()));
		oLiquidacion.setVencimiento(poLiquidacion.getVencimiento());
		
		return oLiquidacion;
	}

	private ieci.tecdoc.sgm.core.services.pago.Tasa getTasaServicio(ieci.tecdoc.sgm.pe.ws.server.Tasa poTasa){
		if(poTasa == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.pago.Tasa oTasa = new ieci.tecdoc.sgm.core.services.pago.Tasa();
		oTasa.setCaptura(poTasa.getCaptura());
		oTasa.setCodigo(poTasa.getCodigo());
		oTasa.setDatosEspecificos(poTasa.getDatosEspecificos());
		oTasa.setIdEntidadEmisora(poTasa.getIdEntidadEmisora());
		oTasa.setModelo(poTasa.getModelo());
		oTasa.setNombre(poTasa.getNombre());
		oTasa.setTipo(poTasa.getTipo());
		
		return oTasa;
	}

	private ieci.tecdoc.sgm.pe.ws.server.Tasa getTasaWS(Tasa poTasa){
		if(poTasa == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.server.Tasa oTasa = new ieci.tecdoc.sgm.pe.ws.server.Tasa();
		oTasa.setCaptura(poTasa.getCaptura());
		oTasa.setCodigo(poTasa.getCodigo());
		oTasa.setDatosEspecificos(poTasa.getDatosEspecificos());
		oTasa.setIdEntidadEmisora(poTasa.getIdEntidadEmisora());
		oTasa.setModelo(poTasa.getModelo());
		oTasa.setNombre(poTasa.getNombre());
		oTasa.setTipo(poTasa.getTipo());
		
		return oTasa;
	}
	
	private CriterioBusquedaLiquidacion getCriterioBusquedaLiquidacionServicio(ieci.tecdoc.sgm.pe.ws.server.CriterioBusquedaLiquidacion poCriterio){
		if(poCriterio == null){
			return null;
		}
		CriterioBusquedaLiquidacion oCriterio = new CriterioBusquedaLiquidacion();
		oCriterio.setEjercicio(poCriterio.getEjercicio());
		oCriterio.setEstado(poCriterio.getEstado());
		oCriterio.setIdEntidadEmisora(poCriterio.getIdEntidadEmisora());
		oCriterio.setIdTasa(poCriterio.getIdTasa());
		oCriterio.setNif(poCriterio.getNif());
		oCriterio.setNrc(poCriterio.getNrc());
		oCriterio.setReferencia(poCriterio.getReferencia());
		return oCriterio;
	}

	private ListaLiquidaciones getListaLiquidaciones(List poList){
		ListaLiquidaciones oLista = new ListaLiquidaciones();
		if(poList == null){
			return oLista;
		}
		ieci.tecdoc.sgm.pe.ws.server.Liquidacion[] oLiqs = new ieci.tecdoc.sgm.pe.ws.server.Liquidacion[poList.size()];
		int i = 0;
		Iterator oIterador = poList.iterator();
		while(oIterador.hasNext()){
			oLiqs[i++] = getLiquidacionWS((Liquidacion)oIterador.next());
		}
		oLista.setLiquidaciones(oLiqs);
		return oLista;
	}

	private ListaTasas getListaTasas(List poList){
		ListaTasas oLista = new ListaTasas();
		if(poList == null){
			return oLista;
		}
		ieci.tecdoc.sgm.pe.ws.server.Tasa[] oLiqs = new ieci.tecdoc.sgm.pe.ws.server.Tasa[poList.size()];
		int i = 0;
		Iterator oIterador = poList.iterator();
		while(oIterador.hasNext()){
			oLiqs[i++] = getTasaWS((ieci.tecdoc.sgm.core.services.pago.Tasa)oIterador.next());
		}
		oLista.setTasas(oLiqs);
		return oLista;
	}

	private CriterioBusquedaTasa getCriterioBusquedaTasaService(ieci.tecdoc.sgm.pe.ws.server.CriterioBusquedaTasa poCriterio){
		if(poCriterio ==  null){
			return null;
		}
		CriterioBusquedaTasa oCriterio = new CriterioBusquedaTasa();
		oCriterio.setTipo(poCriterio.getTipo());
		return oCriterio;
	}
}
