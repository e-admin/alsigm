package ieci.tecdoc.sgm.nt.ws.client;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificacionesException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class NotificacionesWebServiceRemoteClient implements
		ServicioNotificaciones {

	private static final Logger logger = Logger.getLogger(NotificacionesWebServiceRemoteClient.class);
	
	private NotificacionesWebService service;
	
	public void actualizaEstados(Entidad entidad) throws ServicioNotificacionesException {
		try{
			RetornoServicio oRetorno = 
				getService().actualizaEstados(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void actualizaEstado(String numeroExpediente, Integer estado, 
    		Date fechaActualizacion, String nifDestino, String notiId, Entidad entidad) throws ServicioNotificacionesException{
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActualizacion);
			RetornoServicio oRetorno = 
				getService().actualizaEstado(numeroExpediente, estado.intValue(),
						calendar, nifDestino,notiId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion altaNotificacion(
			ieci.tecdoc.sgm.core.services.notificaciones.Notificacion poNotificacion, Entidad entidad) throws ServicioNotificacionesException {
		try{
			IdentificadorNotificacion oId = getService().altaNotificacion(getNotificacionWS(poNotificacion), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oId)){
				return getIdentificadorServicio(oId);
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oId);
			}
		} catch (RemoteException e) {
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones consultarNotificaciones(
			ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones poCriterio, boolean pbConDetalle,
			Entidad entidad)
			throws ServicioNotificacionesException {
			Notificaciones oNots;
			try {
				oNots = getService().consultarNotificaciones(getCriterioWS(poCriterio, pbConDetalle), getEntidadWS(entidad));
			} catch (RemoteException e) {
			 	throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
			} catch (Exception e1) {
				logger.error("Error durante transformación de parámetros.");
				throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e1.getMessage(), e1);
			}
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oNots)){
				try {
					return getNotificacionesServicio(oNots);
				} catch (Exception e) {
					logger.error("Error durante transformación de parámetros.");
					throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
				}
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oNots);
			}
	}
	
	public ieci.tecdoc.sgm.core.services.notificaciones.Notificacion detalleNotificacion(
			ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion poIdentificador,
			Entidad entidad)
			throws ServicioNotificacionesException {
		try{
			Notificacion oNot = getService().detalleNotificacion(getIdentificadorWS(poIdentificador), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oNot)){
				try {
					return getNotificacionServicio(oNot);
				} catch (RemoteException e) {
				 	throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
				} catch (Exception e) {
					logger.error("Error durante transformación de parámetros.");
					throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
				}
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oNot);
			}
		} catch (RemoteException e) {
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.notificaciones.Notificacion detalleNotificacionByNotiId(
			String notiId, Entidad entidad) throws ServicioNotificacionesException {
		try{
			Notificacion oNot = getService().detalleNotificacionByNotiId(notiId, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oNot)){
				try {
					return getNotificacionServicio(oNot);
				} catch (RemoteException e) {
				 	throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
				} catch (Exception e) {
					logger.error("Error durante transformación de parámetros.");
					throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
				}
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oNot);
			}
		} catch (RemoteException e) {
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion obtenerEstado(String psIdNotificacion, Entidad entidad)
			throws ServicioNotificacionesException {
		try{
			Notificacion oNot = new Notificacion();
			oNot.setCodigoNoti(psIdNotificacion);
			EstadoNotificacion oEstado = getService().obtenerEstado(oNot, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oEstado)){
				try {
					return getEstadoServicio(oEstado);
				} catch (Exception e) {
					logger.error("Error durante transformación de parámetros.");
					throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
				}
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oEstado);
			}
		} catch (RemoteException e) {
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacionBD obtenerEstadoBD(Integer idEstado, Entidad entidad)
		throws ServicioNotificacionesException {
		try{
			EstadoNotificacionBD oEstado = getService().obtenerEstadoBD(idEstado.intValue(), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oEstado)){
				try {
					return getEstadoServicioBD(oEstado);
					} catch (Exception e) {
						logger.error("Error durante transformación de parámetros.");
						throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
						}
					}else{
						throw getServicioNotificacionesException((IRetornoServicio)oEstado);
						}
			} catch (RemoteException e) {
				throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
			}
	}
	
	public ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento recuperaDocumento(ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos poCriterio,
			Entidad entidad)
			throws ServicioNotificacionesException {
		try{
			InfoDocumento oDoc = getService().recuperaDocumento(getCriterioBusquedaDocumentos(poCriterio), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oDoc)){
				return getInfoDocumentoServicio(oDoc);
			}else{
				throw getServicioNotificacionesException((IRetornoServicio)oDoc);
			}
		} catch (RemoteException e) {
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public NotificacionesWebService getService() {
		return service;
	}

	public void setService(NotificacionesWebService service) {
		this.service = service;
	}

	private ServicioNotificacionesException getServicioNotificacionesException(IRetornoServicio oReturn){
		if(oReturn==null) return new ServicioNotificacionesException("Resultado nulo");
		return new ServicioNotificacionesException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private Notificacion getNotificacionWS(ieci.tecdoc.sgm.core.services.notificaciones.Notificacion poNotificacion){
		if(poNotificacion == null){
			return null;
		}
		Notificacion oNotificacion = new Notificacion();
		oNotificacion.setApellidosDest(poNotificacion.getApellidosDest());
		oNotificacion.setAsunto(poNotificacion.getAsunto());
		oNotificacion.setCodigoNoti(poNotificacion.getCodigoNoti());
		oNotificacion.setCodigoPostal(poNotificacion.getCodigoPostal());
		oNotificacion.setCorreoDest(poNotificacion.getCorreoDest());
		oNotificacion.setDescripcionEstado(poNotificacion.getDescripcionEstado());
		oNotificacion.setEscaleraDireccion(poNotificacion.getEscaleraDireccion());
		oNotificacion.setEstado(poNotificacion.getEstado()==null?null:String.valueOf((poNotificacion.getEstado()).intValue()));
		oNotificacion.setFechaActualiEstado(poNotificacion.getFechaActualiEstado()==null?null:DateTimeUtil.getDateTime(poNotificacion.getFechaActualiEstado(), ConstantesServicios.DATE_PATTERN));
		oNotificacion.setFechaRegistro(poNotificacion.getFechaRegistro()==null?null:DateTimeUtil.getDateTime(poNotificacion.getFechaRegistro(), ConstantesServicios.DATE_PATTERN));
		oNotificacion.setIdioma(poNotificacion.getIdioma());
		oNotificacion.setMunicipio(poNotificacion.getMunicipio());
		oNotificacion.setNifDest(poNotificacion.getNifDest());
		oNotificacion.setNombreDest(poNotificacion.getNombreDest());
		oNotificacion.setNumeroDireccion(poNotificacion.getNumeroDireccion());
		oNotificacion.setNumeroExpediente(poNotificacion.getNumeroExpediente());
		oNotificacion.setNumeroRegistro(poNotificacion.getNumeroRegistro());
		oNotificacion.setOrganismo(poNotificacion.getOrganismo());
		oNotificacion.setPisoDireccion(poNotificacion.getPisoDireccion());
		oNotificacion.setProcedimiento(poNotificacion.getProcedimiento());
		oNotificacion.setProvincia(poNotificacion.getProvincia());
		oNotificacion.setPuertaDireccion(poNotificacion.getPuertaDireccion());
		oNotificacion.setTelefono(poNotificacion.getTelefono());
		oNotificacion.setTexto(poNotificacion.getTexto());
		oNotificacion.setTipo(poNotificacion.getTipo());
		oNotificacion.setTipoCorrespondencia(poNotificacion.getTipoCorrespondencia());
		oNotificacion.setTipoViaDireccion(poNotificacion.getTipoViaDireccion());
		oNotificacion.setUsuario(poNotificacion.getUsuario());
		oNotificacion.setViaDireccion(poNotificacion.getViaDireccion());
		oNotificacion.setDeu(poNotificacion.getDeu());
		oNotificacion.setMovil(poNotificacion.getMovil());
		oNotificacion.setSistemaId(poNotificacion.getSistemaId());
		
		if(poNotificacion.getDocumentos() != null){
			List oLista = poNotificacion.getDocumentos();
			Iterator oIterador = oLista.iterator();
			byte[] obytes = null;
			String[] aLista = new String[poNotificacion.getDocumentos().size()];
			int i = 0;
			while(oIterador.hasNext()){
				obytes = (byte[])oIterador.next();
				aLista[i] = Base64.encodeBytes(obytes);
				i++;
			}
			oNotificacion.setDocumentos(aLista);
		}

		if(poNotificacion.getExtension() != null){
			List oLista = poNotificacion.getExtension();
			Iterator oIterador = oLista.iterator();
			String[] aLista = new String[poNotificacion.getExtension().size()];
			int i = 0;
			while(oIterador.hasNext()){
				aLista[i] = (String)oIterador.next();
				i++;
			}
			oNotificacion.setExtension(aLista);
		}

		if(poNotificacion.getGuid() != null){
			List oLista = poNotificacion.getGuid();
			Iterator oIterador = oLista.iterator();
			String[] aLista = new String[poNotificacion.getGuid().size()];
			int i = 0;
			while(oIterador.hasNext()){
				aLista[i] = (String)oIterador.next();
				i++;
			}
			oNotificacion.setGuid(aLista);
		}

		if(poNotificacion.getNombreDocumentos() != null){
			List oLista = poNotificacion.getNombreDocumentos();
			Iterator oIterador = oLista.iterator();
			String[] aLista = new String[poNotificacion.getNombreDocumentos().size()];
			int i = 0;
			while(oIterador.hasNext()){
				aLista[i] = (String)oIterador.next();
				i++;
			}
			oNotificacion.setNombreDocumentos(aLista);
		}

		return oNotificacion;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion getIdentificadorServicio(IdentificadorNotificacion poId){
		if(poId == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion oId = new ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion();
		oId.setCodigoDeNotificacion(poId.getCodigoDeNotificacion());
		return oId;
	}
	
	private CriterioBusquedaNotificaciones getCriterioWS(ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones poCriterio, boolean pbConDetalle) throws Exception{
		if(poCriterio == null){
			return null;
		}
		CriterioBusquedaNotificaciones oCriterio = new CriterioBusquedaNotificaciones();
		if(poCriterio.getEstado() != null){
			oCriterio.setEstado(poCriterio.getEstado().toString());			
		}
		oCriterio.setFechaDesde(DateTimeUtil.getDateTime(poCriterio.getFechaDesde(), ConstantesServicios.DATE_PATTERN));
		oCriterio.setFechaHasta(DateTimeUtil.getDateTime(poCriterio.getFechaHasta(), ConstantesServicios.DATE_PATTERN));
		oCriterio.setNif(poCriterio.getNif());
		oCriterio.setNotificacion(poCriterio.getNotificacion());
		oCriterio.setTipo(poCriterio.getTipo());
		oCriterio.setConDetalle(Boolean.valueOf(pbConDetalle).toString());
		return oCriterio;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones getNotificacionesServicio(Notificaciones poNots) throws Exception{
		if(poNots == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones oNots = new ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones();
		if(poNots.getNotificaciones() != null){
			List lNots = new ArrayList(poNots.getNotificaciones().length);
			for(int i=0; i < poNots.getNotificaciones().length; i++){
				lNots.add(getNotificacionServicio(poNots.getNotificaciones()[i]));
			}
			oNots.setNotificaciones(lNots);
		}else{
			oNots.setNotificaciones(new ArrayList());
		}
		return oNots;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.Notificacion getNotificacionServicio(Notificacion poNot) throws Exception{
		if(poNot == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.Notificacion oNot = new ieci.tecdoc.sgm.core.services.notificaciones.Notificacion();
		oNot.setApellidosDest(poNot.getApellidosDest());
		oNot.setAsunto(poNot.getAsunto());
		oNot.setCodigoNoti(poNot.getCodigoNoti());
		oNot.setCodigoPostal(poNot.getCodigoPostal());
		oNot.setCorreoDest(poNot.getCorreoDest());
		oNot.setEscaleraDireccion(poNot.getEscaleraDireccion());
		try{
			oNot.setEstado(new Integer(poNot.getEstado()));
		}catch(NumberFormatException e){
			logger.error("Error calculando el estado");
		}
		
		oNot.setFechaActualiEstado(DateTimeUtil.getDate(poNot.getFechaActualiEstado(), ConstantesServicios.DATE_PATTERN));
		oNot.setFechaEntrega(DateTimeUtil.getDate(poNot.getFechaEntrega(), ConstantesServicios.DATE_PATTERN));
		oNot.setFechaRegistro(DateTimeUtil.getDate(poNot.getFechaRegistro(), ConstantesServicios.DATE_PATTERN));
		oNot.setIdioma(poNot.getIdioma());
		oNot.setMunicipio(poNot.getMunicipio());
		oNot.setNifDest(poNot.getNifDest());
		oNot.setNombreDest(poNot.getNombreDest());
		oNot.setNumeroDireccion(poNot.getNumeroDireccion());
		oNot.setNumeroExpediente(poNot.getNumeroExpediente());
		oNot.setNumeroRegistro(poNot.getNumeroRegistro());
		oNot.setOrganismo(poNot.getOrganismo());
		oNot.setPisoDireccion(poNot.getPisoDireccion());
		oNot.setProcedimiento(poNot.getProcedimiento());
		oNot.setPuertaDireccion(poNot.getPuertaDireccion());
		oNot.setTelefono(poNot.getTelefono());
		oNot.setTexto(poNot.getTexto());
		oNot.setTipo(poNot.getTipo());
		oNot.setTipoCorrespondencia(poNot.getTipoCorrespondencia());
		oNot.setTipoViaDireccion(poNot.getTipoViaDireccion());
		oNot.setUsuario(poNot.getUsuario());
		oNot.setViaDireccion(poNot.getViaDireccion());
		oNot.setNotiId(poNot.getNotiId());
		oNot.setDeu(poNot.getDeu());
		oNot.setMovil(poNot.getMovil());
		oNot.setSistemaId(poNot.getSistemaId());
		
		if(poNot.getDocumentos() != null){
			String[] oLista = poNot.getDocumentos();
			byte[] obytes = null;
			ArrayList aLista = new ArrayList(oLista.length);
			for(int i=0; i<oLista.length; i++){
				if(oLista[i]==null)
					obytes = null;
				else
					obytes = Base64.decode(oLista[i]);
				aLista.add(obytes);
			}
			oNot.setDocumentos(aLista);
		}
		
		if(poNot.getExtension() != null){
			String[] oLista = poNot.getExtension();
			ArrayList aLista = new ArrayList(oLista.length);
			for(int i=0; i<oLista.length; i++){
				aLista.add(oLista[i]);
			}			
			oNot.setExtension(aLista);
		}

		if(poNot.getGuid() != null){
			String[] oLista = poNot.getGuid();
			ArrayList aLista = new ArrayList(oLista.length);
			for(int i=0; i<oLista.length; i++){
				aLista.add(oLista[i]);
			}			
			oNot.setGuid(aLista);
		}
		
		if(poNot.getNombreDocumentos() != null){
			String[] oLista = poNot.getNombreDocumentos();
			ArrayList aLista = new ArrayList(oLista.length);
			for(int i=0; i<oLista.length; i++){
				aLista.add(oLista[i]);
			}			
			oNot.setNombreDocumentos(aLista);
		}
		
		return oNot;		
	}
	
	private IdentificadorNotificacion getIdentificadorWS(ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion poId){
		if(poId == null){
			return null;
		}
		IdentificadorNotificacion oId = new IdentificadorNotificacion();
		oId.setCodigoDeNotificacion(poId.getCodigoDeNotificacion());
		return oId;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion getEstadoServicio(EstadoNotificacion poEstado) throws Exception{
		if(poEstado == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion oEstado = new ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion();
		oEstado.setEstado(poEstado.getEstado());
		if(poEstado.getFecha() != null){
			Calendar oCal = GregorianCalendar.getInstance();
			Date oFecha = DateTimeUtil.getDate(poEstado.getFecha(), ConstantesServicios.DATE_PATTERN);
			oCal.setTime(oFecha);
			oEstado.setFecha(oCal);
		}
		oEstado.setError(poEstado.getError());
		oEstado.setMotivoRechazo(poEstado.getMotivoRechazo());
		return oEstado;
	}

	private ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacionBD getEstadoServicioBD(EstadoNotificacionBD poEstado) throws Exception{
		if(poEstado == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacionBD oEstado = new ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacionBD();
		oEstado.setDescripcion(poEstado.getDescripcion());
		oEstado.setId(poEstado.getId());
		oEstado.setIdSisnot(poEstado.getIdSisnot());
		return oEstado;
	}
	
	private CriterioBusquedaDocumentos getCriterioBusquedaDocumentos(ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos poCriterio){
		if(poCriterio == null){
			return null;
		}
		CriterioBusquedaDocumentos oCriterio = new CriterioBusquedaDocumentos();
		oCriterio.setCodigDoc(poCriterio.getCodigDoc());
		oCriterio.setCodigoNoti(poCriterio.getCodigoNoti());
		oCriterio.setExpediente(poCriterio.getExpediente());
		oCriterio.setNifDestinatario(poCriterio.getNifDestinatario());
		return oCriterio;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento getInfoDocumentoServicio(InfoDocumento poInfo){
		if(poInfo == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento oInfo = new ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento();
		oInfo.setContent(Base64.decode(poInfo.getContent()));
		oInfo.setExtension(poInfo.getExtension());
		oInfo.setGuid(poInfo.getGuid());
		oInfo.setMimeType(poInfo.getMimeType());
		return oInfo;
	}
	
	private ieci.tecdoc.sgm.nt.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad ==  null){
			return null;
		}
		ieci.tecdoc.sgm.nt.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.nt.ws.client.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombre(poEntidad.getNombre());
		return oEntidad;
	}	

}
