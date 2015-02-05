package ieci.tecdoc.sgm.pe.ws.client;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaTasa;
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Pago;
import ieci.tecdoc.sgm.core.services.pago.PagoTelematicoException;
import ieci.tecdoc.sgm.core.services.pago.ServicioPagoTelematico;
import ieci.tecdoc.sgm.core.services.pago.Tasa;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ServicioPagoTelematicoRemoteClient implements
		ServicioPagoTelematico {

	private PagoTelematicoWebService service;
	
	public Liquidacion altaLiquidacion(Liquidacion poLiquidacion, Entidad entidad)
			throws PagoTelematicoException {
		try{
			ieci.tecdoc.sgm.pe.ws.client.Liquidacion oLiquidacion = service.altaLiquidacion(getLiquidacionWS(poLiquidacion), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oLiquidacion)){
				return getLiquidacionServicio(oLiquidacion);
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oLiquidacion);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void bajaLiquidacion(Liquidacion poLiquidacion, Entidad entidad)
			throws PagoTelematicoException {
		try{
			RetornoServicio oRetorno = service.bajaLiquidacion(getLiquidacionWS(poLiquidacion), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public List buscarLiquidaciones(CriterioBusquedaLiquidacion poCriterio, Entidad entidad)
			throws PagoTelematicoException {
		try{
			ListaLiquidaciones oLista = service.buscarLiquidaciones(getCriterioBusquedaLiquidacionWS(poCriterio), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oLista)){
				return getListaLiquidaciones(oLista);
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oLista);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public List buscarTasas(CriterioBusquedaTasa poCriterio, Entidad entidad)
			throws PagoTelematicoException {
		try{
			ListaTasas oLista = service.buscarTasas(getCriterioBusquedaTasaWS(poCriterio), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oLista)){
				return getListaTasas(oLista);
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oLista);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public Pago detallePago(String pcNumReferencia, Entidad entidad)
			throws PagoTelematicoException {
		try{
			ieci.tecdoc.sgm.pe.ws.client.Pago oPago = new ieci.tecdoc.sgm.pe.ws.client.Pago();
			oPago.setReferencia(pcNumReferencia);
			oPago = service.detallePago(oPago, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oPago)){
				return getPagoServicio(oPago);
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oPago);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void modificarLiquidacion(Liquidacion poLiquidacion, Entidad entidad)
			throws PagoTelematicoException {
		try{
			ieci.tecdoc.sgm.pe.ws.client.Liquidacion oLiquidacion = service.modificarLiquidacion(getLiquidacionWS(poLiquidacion), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oLiquidacion)){
				return;
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oLiquidacion);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public Tasa obtenerDatosTasa(String pcIdTasa, String pcIdEntidadEmisora, Entidad entidad)
			throws PagoTelematicoException {
		try{
			ieci.tecdoc.sgm.pe.ws.client.Tasa oTasa = new ieci.tecdoc.sgm.pe.ws.client.Tasa();
			oTasa.setCodigo(pcIdTasa);
			oTasa.setIdEntidadEmisora(pcIdEntidadEmisora);
			oTasa = service.obtenerDatosTasa(oTasa, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oTasa)){
				return getTasaServicio(oTasa);
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oTasa);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public String obtenerDocumentoPago(Pago poPago, Entidad entidad)
			throws PagoTelematicoException {
		try{
			DocumentoPago odocumento = service.obtenerDocumentoPago(getPagoWS(poPago), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)odocumento)){
				return odocumento.getDocumentoPago();
			}else{
				throw getPagoTelematicoException((IRetornoServicio)odocumento);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public Pago realizarPago(Pago poPago, Entidad entidad) throws PagoTelematicoException {
		// TODO Auto-generated method stub
		try{
			ieci.tecdoc.sgm.pe.ws.client.Pago oPago = service.realizarPago(getPagoWS(poPago), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oPago)){
				return getPagoServicio(oPago);
			}else{
				throw getPagoTelematicoException((IRetornoServicio)oPago);
			}
		} catch (RemoteException e) {
			throw new PagoTelematicoException(PagoTelematicoException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	
	public void setService(PagoTelematicoWebService service) {
		this.service = service;
	}
	
	private PagoTelematicoException getPagoTelematicoException(IRetornoServicio oReturn){
		return new PagoTelematicoException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	
	
	private ieci.tecdoc.sgm.pe.ws.client.Pago getPagoWS(Pago poPago){
		if(poPago == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.client.Pago oPago = new ieci.tecdoc.sgm.pe.ws.client.Pago();
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

	
	private Pago getPagoServicio(ieci.tecdoc.sgm.pe.ws.client.Pago poPago){
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

	private ieci.tecdoc.sgm.pe.ws.client.Liquidacion getLiquidacionWS(Liquidacion poLiquidacion){
		if(poLiquidacion == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.client.Liquidacion oLiquidacion = new ieci.tecdoc.sgm.pe.ws.client.Liquidacion();
		oLiquidacion.setDatosEspecificos(poLiquidacion.getDatosEspecificos());
		oLiquidacion.setDiscriminante(poLiquidacion.getDiscriminante());
		oLiquidacion.setEjercicio(poLiquidacion.getEjercicio());
		oLiquidacion.setEstado(poLiquidacion.getEstado());
		if(poLiquidacion.getFechaPago() != null){
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(poLiquidacion.getFechaPago());			
			oLiquidacion.setFechaPago(cal);
		}
		if(poLiquidacion.getFinPeriodo() != null){
			Calendar cal2 = GregorianCalendar.getInstance();
			cal2.setTime(poLiquidacion.getFinPeriodo());
			oLiquidacion.setFinPeriodo(cal2);			
		}
		oLiquidacion.setIdEntidadEmisora(poLiquidacion.getIdEntidadEmisora());
		oLiquidacion.setIdTasa(poLiquidacion.getIdTasa());
		oLiquidacion.setImporte(poLiquidacion.getImporte());
		if(poLiquidacion.getInicioPeriodo() != null){
			Calendar cal3 = GregorianCalendar.getInstance();
			cal3.setTime(poLiquidacion.getInicioPeriodo());		
			oLiquidacion.setInicioPeriodo(cal3);			
		}
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
	
	private Liquidacion getLiquidacionServicio(ieci.tecdoc.sgm.pe.ws.client.Liquidacion poLiquidacion){
		if(poLiquidacion == null){
			return null;
		}
		Liquidacion oLiquidacion = new Liquidacion();
		oLiquidacion.setDatosEspecificos(poLiquidacion.getDatosEspecificos());
		oLiquidacion.setDiscriminante(poLiquidacion.getDiscriminante());
		oLiquidacion.setEjercicio(poLiquidacion.getEjercicio());
		oLiquidacion.setEstado(poLiquidacion.getEstado());
		if(poLiquidacion.getFechaPago() != null){
			oLiquidacion.setFechaPago(poLiquidacion.getFechaPago().getTime());
		}
		if(poLiquidacion.getFinPeriodo() != null){
			oLiquidacion.setFinPeriodo(poLiquidacion.getFinPeriodo().getTime());
		}
		if(poLiquidacion.getInicioPeriodo() != null){
			oLiquidacion.setInicioPeriodo(poLiquidacion.getInicioPeriodo().getTime());
		}
		oLiquidacion.setIdEntidadEmisora(poLiquidacion.getIdEntidadEmisora());
		oLiquidacion.setIdTasa(poLiquidacion.getIdTasa());
		oLiquidacion.setImporte(poLiquidacion.getImporte());

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
	
	
	private Tasa getTasaServicio(ieci.tecdoc.sgm.pe.ws.client.Tasa poTasa){
		if(poTasa == null){
			return null;
		}
		Tasa oTasa = new Tasa();
		oTasa.setCaptura(poTasa.getCaptura());
		oTasa.setCodigo(poTasa.getCodigo());
		oTasa.setDatosEspecificos(poTasa.getDatosEspecificos());
		oTasa.setIdEntidadEmisora(poTasa.getIdEntidadEmisora());
		oTasa.setModelo(poTasa.getModelo());
		oTasa.setNombre(poTasa.getNombre());
		oTasa.setTipo(poTasa.getTipo());
		
		return oTasa;
	}

	
	private ieci.tecdoc.sgm.pe.ws.client.Tasa getTasaWS(Tasa poTasa){
		if(poTasa == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.client.Tasa oTasa = new ieci.tecdoc.sgm.pe.ws.client.Tasa();
		oTasa.setCaptura(poTasa.getCaptura());
		oTasa.setCodigo(poTasa.getCodigo());
		oTasa.setDatosEspecificos(poTasa.getDatosEspecificos());
		oTasa.setIdEntidadEmisora(poTasa.getIdEntidadEmisora());
		oTasa.setModelo(poTasa.getModelo());
		oTasa.setNombre(poTasa.getNombre());
		oTasa.setTipo(poTasa.getTipo());
		
		return oTasa;
	}

	
	private ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion getCriterioBusquedaLiquidacionWS(CriterioBusquedaLiquidacion poCriterio){
		if(poCriterio == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion oCriterio = new ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion();
		oCriterio.setEjercicio(poCriterio.getEjercicio());
		oCriterio.setEstado(poCriterio.getEstado());
		oCriterio.setIdEntidadEmisora(poCriterio.getIdEntidadEmisora());
		oCriterio.setIdTasa(poCriterio.getIdTasa());
		oCriterio.setNif(poCriterio.getNif());
		oCriterio.setNrc(poCriterio.getNrc());
		oCriterio.setReferencia(poCriterio.getReferencia());
		return oCriterio;
	}

	private List getListaLiquidaciones(ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones poList){
		List oLista = new ArrayList();
		if((poList == null) || (poList.getLiquidaciones() == null)){
			return oLista;
		}
		for(int i = 0; i < poList.getLiquidaciones().length; i++){
			oLista.add(getLiquidacionServicio(poList.getLiquidaciones()[i]));
		}
		return oLista;
	}

	private List getListaTasas(ListaTasas poList){
		List oLista = new ArrayList();
		if((poList == null) || (poList.getTasas() == null)){
			return oLista;
		}
		for(int i = 0; i < poList.getTasas().length; i++){
			oLista.add(getTasaServicio(poList.getTasas()[i]));
		}
		return oLista;
	}

	private ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa getCriterioBusquedaTasaWS(CriterioBusquedaTasa poCriterio){
		if(poCriterio ==  null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa oCriterio = new ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaTasa();
		oCriterio.setTipo(poCriterio.getTipo());
		return oCriterio;
	}

	private ieci.tecdoc.sgm.pe.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		ieci.tecdoc.sgm.pe.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.pe.ws.client.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		return oEntidad;
	}
}
