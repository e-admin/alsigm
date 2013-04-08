package ieci.tecdoc.sgm.pe;
/*
 * $Id: SigemPagoElectronicoServiceAdapter.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaTasa;
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Pago;
import ieci.tecdoc.sgm.core.services.pago.PagoTelematicoException;
import ieci.tecdoc.sgm.core.services.pago.ServicioPagoTelematico;
import ieci.tecdoc.sgm.core.services.pago.Tasa;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SigemPagoElectronicoServiceAdapter implements
		ServicioPagoTelematico {

	private static final Logger logger = Logger.getLogger(SigemPagoElectronicoServiceAdapter.class);
	
	private PagoElectronicoManager manager;
	
	public Liquidacion altaLiquidacion(Liquidacion poLiquidacion, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			return getLiquidacion(oManager.altaLiquidacion(getLiquidacionImpl(poLiquidacion), entidad.getIdentificador()));
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error en alta de liquidación.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en alta de liquidación.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void bajaLiquidacion(Liquidacion poLiquidacion, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			oManager.bajaLiquidacion(getLiquidacionImpl(poLiquidacion), entidad.getIdentificador());
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error en baja de liquidación.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en baja de liquidación.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public List buscarLiquidaciones(CriterioBusquedaLiquidacion poCriterio, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			return getLiquidaciones(oManager.buscarLiquidaciones(getCriterioBusquedaLiquidacionImpl(poCriterio), entidad.getIdentificador()));
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error en búsqueda de liquidaciones.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en búsqueda de liquidaciones.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public List buscarTasas(CriterioBusquedaTasa poCriterio, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			return getTasas(oManager.buscarTasas(getCriterioBusquedaTasaImpl(poCriterio), entidad.getIdentificador()));
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error en búsqueda de tasas.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en búsqueda de tasas.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Pago detallePago(String pcNumReferencia, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			return getPago(oManager.detallePago(pcNumReferencia, entidad.getIdentificador()));
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error en obteniendo detalle de pago.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo detalle de pago.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void modificarLiquidacion(Liquidacion poLiquidacion, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			oManager.modificarLiquidacion(getLiquidacionImpl(poLiquidacion), entidad.getIdentificador());
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error en modificando datos de liquidacion.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error modificando datos de liquidación.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Tasa obtenerDatosTasa(String pcIdTasa, String pcIdEntidadEmisora, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			return getTasa(oManager.obtenerDatosTasa(pcIdTasa, pcIdEntidadEmisora, entidad.getIdentificador()));
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error al obtener datos de tasa.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error al obtener datos de tasa.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String obtenerDocumentoPago(Pago poPago, Entidad entidad)
			throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			return oManager.obtenerDocumentoPago(getPagoImpl(poPago), entidad.getIdentificador());
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error obteniendo documento de pago.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error obteniendo documento de pago.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Pago realizarPago(Pago poPago, Entidad entidad) throws PagoTelematicoException {
		try {
			PagoElectronicoManager oManager = getManager();			
			return getPago(oManager.realizarPago(getPagoImpl(poPago), entidad.getIdentificador()));
		} catch (PagoElectronicoExcepcion e) {
			logger.error("Error realizando pago.", e);
			throw getPagoTelematicoException(e);
		} catch (Throwable e){
			logger.error("Error realizando pago.", e);
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	private PagoTelematicoException getPagoTelematicoException(PagoElectronicoExcepcion poException){
		if(poException == null){
			return new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_PAYMENT_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new PagoTelematicoException(Long.valueOf(cCodigo.toString()).longValue(), poException);
		
	}
	private PagoImpl getPagoImpl(Pago poPago){
		if(poPago == null){
			return null;
		}
		PagoImpl oPago = new PagoImpl();
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
		oPago.setLiquidacion(getLiquidacionImpl(poPago.getLiquidacion()));
		oPago.setMedioPago(poPago.getMedioPago());
		oPago.setNif(poPago.getNif());
		oPago.setNrc(poPago.getNrc());
		oPago.setNumeroTarjetaCredito(poPago.getNumeroTarjetaCredito());
		oPago.setReferencia(poPago.getReferencia());
		oPago.setRemesa(poPago.getRemesa());
		return oPago;
	}
	
	
	private LiquidacionImpl getLiquidacionImpl(Liquidacion oLiqServicio){
		if(oLiqServicio == null){
			return null;
		}
		LiquidacionImpl oImpl = new LiquidacionImpl();
		oImpl.setDatosEspecificos(oLiqServicio.getDatosEspecificos());
		oImpl.setDiscriminante(oLiqServicio.getDiscriminante());
		oImpl.setEjercicio(oLiqServicio.getEjercicio());
		oImpl.setEstado(oLiqServicio.getEstado());
		oImpl.setFechaPago(oLiqServicio.getFechaPago());
		oImpl.setFinPeriodo(oLiqServicio.getFinPeriodo());
		oImpl.setIdEntidadEmisora(oLiqServicio.getIdEntidadEmisora());
		oImpl.setIdTasa(oLiqServicio.getIdTasa());
		oImpl.setImporte(oLiqServicio.getImporte());
		oImpl.setInicioPeriodo(oLiqServicio.getInicioPeriodo());
		oImpl.setNif(oLiqServicio.getNif());
		oImpl.setNombre(oLiqServicio.getNombre());
		oImpl.setNrc(oLiqServicio.getNrc());
		oImpl.setReferencia(oLiqServicio.getReferencia());
		oImpl.setRemesa(oLiqServicio.getRemesa());
		oImpl.setSolicitud(oLiqServicio.getSolicitud());
		oImpl.setTasa(getTasaImpl(oLiqServicio.getTasa()));
		oImpl.setVencimiento(oLiqServicio.getVencimiento());
		return oImpl;
	}
	
	private TasaImpl getTasaImpl(Tasa poTasaServicio){
		if(poTasaServicio == null){
			return null;
		}
		TasaImpl oTasa = new TasaImpl();
		oTasa.setCaptura(poTasaServicio.getCaptura());
		oTasa.setCodigo(poTasaServicio.getCodigo());
		oTasa.setDatosEspecificos(poTasaServicio.getDatosEspecificos());
		oTasa.setIdEntidadEmisora(poTasaServicio.getIdEntidadEmisora());
		oTasa.setModelo(poTasaServicio.getModelo());
		oTasa.setNombre(poTasaServicio.getNombre());
		oTasa.setTipo(poTasaServicio.getTipo());
		return oTasa;
	}
	
	private Pago getPago(PagoImpl poPago){
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
		oPago.setLiquidacion(getLiquidacion(poPago.getLiquidacion()));
		oPago.setMedioPago(poPago.getMedioPago());
		oPago.setNif(poPago.getNif());
		oPago.setNrc(poPago.getNrc());
		oPago.setNumeroTarjetaCredito(poPago.getNumeroTarjetaCredito());
		oPago.setReferencia(poPago.getReferencia());
		oPago.setRemesa(poPago.getRemesa());
		oPago.setPeticionPagoPasarelaExternaConRedireccion(poPago.getPeticionPagoPasarelaExternaConRedireccion());
		return oPago;
	}

	
	private Liquidacion getLiquidacion(LiquidacionImpl oLiqImpl){
		if(oLiqImpl == null){
			return null;
		}
		Liquidacion oImpl = new Liquidacion();
		oImpl.setDatosEspecificos(oLiqImpl.getDatosEspecificos());
		oImpl.setDiscriminante(oLiqImpl.getDiscriminante());
		oImpl.setEjercicio(oLiqImpl.getEjercicio());
		oImpl.setEstado(oLiqImpl.getEstado());
		oImpl.setFechaPago(oLiqImpl.getFechaPago());
		oImpl.setFinPeriodo(oLiqImpl.getFinPeriodo());
		oImpl.setIdEntidadEmisora(oLiqImpl.getIdEntidadEmisora());
		oImpl.setIdTasa(oLiqImpl.getIdTasa());
		oImpl.setImporte(oLiqImpl.getImporte());
		oImpl.setInicioPeriodo(oLiqImpl.getInicioPeriodo());
		oImpl.setNif(oLiqImpl.getNif());
		oImpl.setNombre(oLiqImpl.getNombre());
		oImpl.setNrc(oLiqImpl.getNrc());
		oImpl.setReferencia(oLiqImpl.getReferencia());
		oImpl.setRemesa(oLiqImpl.getRemesa());
		oImpl.setSolicitud(oLiqImpl.getSolicitud());
		oImpl.setTasa(getTasa(oLiqImpl.getTasa()));
		oImpl.setVencimiento(oLiqImpl.getVencimiento());
		return oImpl;
	}
	
	private Tasa getTasa(TasaImpl poTasaImpl){
		if(poTasaImpl == null){
			return null;
		}
		Tasa oTasa = new Tasa();
		oTasa.setCaptura(poTasaImpl.getCaptura());
		oTasa.setCodigo(poTasaImpl.getCodigo());
		oTasa.setDatosEspecificos(poTasaImpl.getDatosEspecificos());
		oTasa.setIdEntidadEmisora(poTasaImpl.getIdEntidadEmisora());
		oTasa.setModelo(poTasaImpl.getModelo());
		oTasa.setNombre(poTasaImpl.getNombre());
		oTasa.setTipo(poTasaImpl.getTipo());
		return oTasa;
	}
	
	private ieci.tecdoc.sgm.pe.CriterioBusquedaLiquidacion getCriterioBusquedaLiquidacionImpl(CriterioBusquedaLiquidacion poCriterio){
		if(poCriterio == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.CriterioBusquedaLiquidacion oLiquidacion = new ieci.tecdoc.sgm.pe.CriterioBusquedaLiquidacion();
		oLiquidacion.setEjercicio(poCriterio.getEjercicio());
		oLiquidacion.setEstado(poCriterio.getEstado());
		oLiquidacion.setIdEntidadEmisora(poCriterio.getIdEntidadEmisora());
		oLiquidacion.setIdTasa(poCriterio.getIdTasa());
		oLiquidacion.setNif(poCriterio.getNif());
		oLiquidacion.setNrc(poCriterio.getNrc());
		oLiquidacion.setReferencia(poCriterio.getReferencia());
		return oLiquidacion;
	}

	private List getLiquidaciones(LiquidacionImpl[] poLiquidaciones){
		if(poLiquidaciones == null){
			return new ArrayList();
		}
		ArrayList oLista = new ArrayList(poLiquidaciones.length);
		for(int i = 0; i < poLiquidaciones.length; i++){
			oLista.add(getLiquidacion(poLiquidaciones[i]));
		}
		return oLista;
	}
	
	private List getTasas(TasaImpl[] poTasas){
		if(poTasas == null){
			return new ArrayList();
		}
		ArrayList oLista = new ArrayList(poTasas.length);
		for(int i = 0; i < poTasas.length; i++){
			oLista.add(getTasa(poTasas[i]));
		}
		return oLista;
	}
	
	private ieci.tecdoc.sgm.pe.CriterioBusquedaTasa getCriterioBusquedaTasaImpl(CriterioBusquedaTasa poCriterio){
		if(poCriterio == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.CriterioBusquedaTasa oCriterio = new ieci.tecdoc.sgm.pe.CriterioBusquedaTasa();
		oCriterio.setTipo(poCriterio.getTipo());
		return oCriterio;
	}

	private PagoElectronicoManager getManager() {
		return manager;
	}

	public void setManager(PagoElectronicoManager manager) {
		this.manager = manager;
	}

}
