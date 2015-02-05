package ieci.tecdoc.sgm.nt.ws.server;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificacionesException;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;
import ieci.tecdoc.sgm.nt.bean.EstadoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

public class NotificacionesWebService{

	private static final Logger logger = Logger.getLogger(NotificacionesWebService.class);
	
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_NOTIFICATIONS;

	/**
	 * Método que actualiza los estados de las notificaciones en el modelo intermedio.
	 * Se conecta con el servidor de sisnot.
	 * @return RetornoServicio con el codigo de retorno y el identificador del error.
	 */
	public RetornoServicio actualizaEstados(Entidad entidad){
		 try {
			getServicioNotificaciones().actualizaEstados(entidad);
			return ServiciosUtils.createReturnOK();
		} catch (ServicioNotificacionesException e) {
			logger.error("Error actualizando notificaciones.", e);			
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error actualizando notificaciones.", e);			
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado actualizando notificaciones.", e);			
			return ServiciosUtils.createReturnError();			
		}
	 }

	public RetornoServicio actualizaEstado(String numeroExpediente, Integer estado, 
    		Date fechaActualizacion, String nifDestino,String notiId, Entidad entidad) {
		try {		
			getServicioNotificaciones().actualizaEstado(numeroExpediente, estado, fechaActualizacion,
					nifDestino,notiId, entidad);
			return ServiciosUtils.createReturnOK();			
		} catch (ServicioNotificacionesException e) {
			logger.error("Error actualizando notificacione.", e);			
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error actualizando notificacione.", e);			
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado actualizando notificacione.", e);			
			return ServiciosUtils.createReturnError();			
		}
	}
	
	/**
	 * Recupera el documento de una notificación.
	 * @param poCriterio Criterio de búsqueda del documento.
	 * @return InfoDocumento Contenido y metadatos del documento, con el código de retorno y el identificador del error.
	 */
	public InfoDocumento recuperaDocumento(CriterioBusquedaDocumentos poCriterio, Entidad entidad){
		 try {
			 InfoDocumento oDoc =
				 getInfoDocumentoWS(
						 getServicioNotificaciones()
						 	.recuperaDocumento(
						 			getCriterioBusquedaDocumentosService(poCriterio), entidad
						 	)
				 );
			   return (InfoDocumento)ServiciosUtils.completeReturnOK((RetornoServicio) oDoc);					 
		 } catch (ServicioNotificacionesException e) {
			logger.error("Error recuperando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		 } catch (SigemException e) {
			logger.error("Error recuperando documento.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error en proceso de login de usuario externo.", e);
			return (InfoDocumento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new InfoDocumento()));					
		}
	 }
	 
	/**
	 * Obtiene toda la información relacionada con una notificación.
	 * @param poIdentificador Identificador de la notificación.
	 * @return Notificación Datos de la notificación.
	 */
	 public Notificacion detalleNotificacion (IdentificadorNotificacion poIdentificador, Entidad entidad){
		 try {
			 Notificacion oNotificacion =
				 getNotificacionWS(
						 getServicioNotificaciones()
						 	.detalleNotificacion(
						 			getIdNotificacionServicio(poIdentificador), entidad
						 	)
				 );
			 return (Notificacion)ServiciosUtils.completeReturnOK((RetornoServicio) oNotificacion);					 
		 } catch (ServicioNotificacionesException e) {
			logger.error("Error obteniendo detalle de notificación.", e);
			return (Notificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificacion()),
								e.getErrorCode());
		 } catch (SigemException e) {
			logger.error("Error obteniendo detalle de notificación.", e);
			return (Notificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificacion()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo detalle de notificación.", e);
			return (Notificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificacion()));					
		}
	 }
	 
	 /**
		 * Obtiene toda la información relacionada con una notificación.
		 * @param poIdentificador Identificador de la notificación.
		 * @return Notificación Datos de la notificación.
		 */
		 public Notificacion detalleNotificacionByNotiId (String notiId, Entidad entidad){
			 try {
				 Notificacion oNotificacion =
					 getNotificacionWS(
							 getServicioNotificaciones()
							 	.detalleNotificacionByNotiId(
							 			notiId, entidad
							 	)
					 );
				 return (Notificacion)ServiciosUtils.completeReturnOK((RetornoServicio) oNotificacion);					 
			 } catch (ServicioNotificacionesException e) {
				logger.error("Error obteniendo detalle de notificación.", e);
				return (Notificacion)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new Notificacion()),
									e.getErrorCode());
			 } catch (SigemException e) {
				logger.error("Error obteniendo detalle de notificación.", e);
				return (Notificacion)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new Notificacion()),
									e.getErrorCode());
			} catch (Throwable e) {
				logger.error("Error inesperado obteniendo detalle de notificación.", e);
				return (Notificacion)
						ServiciosUtils.completeReturnError(
									(RetornoServicio)(new Notificacion()));					
			}
		 }
	 
	 /**
	  * Búsqueda de notificaciones según un criterio que llega como parámetro.
	  * @param poCriterio Criterio de búsqueda.
	  * @return Notificaciones Lista de notificaciones.
	  */
	 public Notificaciones consultarNotificaciones (CriterioBusquedaNotificaciones poCriterio, Entidad entidad){
		 try {
			 ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones oCriterio 
			 	= getCriterioBusquedaNotsService(poCriterio);
			 Notificaciones oNots =
				 getNotificacionesWS(
						 getServicioNotificaciones()
						 	.consultarNotificaciones(
						 			oCriterio, 
						 			getConDetalleService(poCriterio.getConDetalle()),
						 			entidad)
				 );
			 return (Notificaciones)ServiciosUtils.completeReturnOK((RetornoServicio) oNots);					 
		 } catch (ServicioNotificacionesException e) {
			logger.error("Error obteniendo notificaciones.", e);
			return (Notificaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificaciones()),
								e.getErrorCode());
		 } catch (SigemException e) {
			logger.error("Error obteniendo notificaciones.", e);
			return (Notificaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificaciones()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo notificaciones.", e);
			return (Notificaciones)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Notificaciones()));					
		}
	 }
	 
	 /**
	  * Devuelve el estado de una notificación.
	  * @param poIdNotificacion Objeto Notificación con el id de la notificación.
	  * @return EstadoNotificacion Estado de la notificacion.
	  */
	 public EstadoNotificacion obtenerEstado(Notificacion poIdNotificacion, Entidad entidad){
		 try {
			 EstadoNotificacion oEstado =
				 getEstadoNotificacionWS(
						 getServicioNotificaciones()
						 	.obtenerEstado(poIdNotificacion.getCodigoNoti(), entidad)
				 );
			 return (EstadoNotificacion)ServiciosUtils.completeReturnOK((RetornoServicio) oEstado);					 
		 } catch (ServicioNotificacionesException e) {
			logger.error("Error obteniendo notificaciones.", e);
			return (EstadoNotificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new EstadoNotificacion()),
								e.getErrorCode());
		 } catch (SigemException e) {
			logger.error("Error obteniendo notificaciones.", e);
			return (EstadoNotificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new EstadoNotificacion()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo notificaciones.", e);
			return (EstadoNotificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new EstadoNotificacion()));					
		}
	 }
	 
	 public EstadoNotificacionBD obtenerEstadoBD(Integer idEstado, Entidad entidad) {
			try {		
				EstadoBean entrada = new EstadoBean();
				entrada.setEstado(idEstado.intValue());
				EstadoNotificacionBD estado=getEstadoNotificacionBDWS(
						getServicioNotificaciones().obtenerEstadoBD(idEstado, entidad));
				return (EstadoNotificacionBD)ServiciosUtils.completeReturnOK((RetornoServicio) estado);
			 } catch (ServicioNotificacionesException e) {
					logger.error("Error obteniendo notificaciones.", e);
					return (EstadoNotificacionBD)
							ServiciosUtils.completeReturnError(
										(RetornoServicio)(new EstadoNotificacionBD()),
										e.getErrorCode());
				 } catch (SigemException e) {
					logger.error("Error obteniendo notificaciones.", e);
					return (EstadoNotificacionBD)
							ServiciosUtils.completeReturnError(
										(RetornoServicio)(new EstadoNotificacionBD()),
										e.getErrorCode());
				} catch (Throwable e) {
					logger.error("Error inesperado obteniendo notificaciones.", e);
					return (EstadoNotificacionBD)
							ServiciosUtils.completeReturnError(
										(RetornoServicio)(new EstadoNotificacionBD()));					
				}
		}
		
	 /**
	  * Da de alta una nueva notificación pendiente en el modelo intermedio.
	  * @param poNotificacion Datos de la notificación.
	  * @return IdentificadorNotificacion Identificador de la notificación.
	  */
	 public IdentificadorNotificacion altaNotificacion(Notificacion poNotificacion, Entidad entidad){
		 try {
			 IdentificadorNotificacion oId = 
				 getIdNotificacionWS(
						 getServicioNotificaciones()
						 	.altaNotificacion(
						 			getNotificacionServicio(poNotificacion), entidad
						 	)
				 );
			 return (IdentificadorNotificacion)ServiciosUtils.completeReturnOK((RetornoServicio) oId);					 
		 } catch (ServicioNotificacionesException e) {
			logger.error("Error obteniendo notificaciones.", e);
			return (IdentificadorNotificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorNotificacion()),
								e.getErrorCode());
		 } catch (SigemException e) {
			logger.error("Error obteniendo notificaciones.", e);
			return (IdentificadorNotificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorNotificacion()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado obteniendo notificaciones.", e);
			return (IdentificadorNotificacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorNotificacion()));					
		}
	 }

	private ServicioNotificaciones getServicioNotificaciones() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioNotificaciones();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioNotificaciones(sbImpl.toString());
		}
	}
	
	private InfoDocumento getInfoDocumentoWS(ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento poDoc){
		if(poDoc == null){
			return null;
		}
		InfoDocumento oDoc = new InfoDocumento();
		oDoc.setContent(Base64.encodeBytes(poDoc.getContent()));
		oDoc.setExtension(poDoc.getExtension());
		oDoc.setGuid(poDoc.getGuid());
		oDoc.setMimeType(poDoc.getMimeType());
		return oDoc;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos getCriterioBusquedaDocumentosService(CriterioBusquedaDocumentos poCriterio){
		if(poCriterio == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos oCriterio = new ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos();
		oCriterio.setCodigDoc(poCriterio.getCodigDoc());
		oCriterio.setCodigoNoti(poCriterio.getCodigoNoti());
		oCriterio.setExpediente(poCriterio.getExpediente());
		oCriterio.setNifDestinatario(poCriterio.getNifDestinatario());
		return oCriterio;
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
		oNotificacion.setEstado(String.valueOf((poNotificacion.getEstado()).intValue()));
		oNotificacion.setFechaActualiEstado(DateTimeUtil.getDateTime(poNotificacion.getFechaActualiEstado(), ConstantesServicios.DATE_PATTERN));
		oNotificacion.setFechaRegistro(DateTimeUtil.getDateTime(poNotificacion.getFechaRegistro(), ConstantesServicios.DATE_PATTERN));
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
		oNotificacion.setNotiId(poNotificacion.getNotiId());
		oNotificacion.setSistemaId(poNotificacion.getSistemaId());
		oNotificacion.setMovil(poNotificacion.getMovil());
		oNotificacion.setDeu(poNotificacion.getDeu());
		
		if(poNotificacion.getDocumentos() != null){
			List oLista = poNotificacion.getDocumentos();
			Iterator oIterador = oLista.iterator();
			byte[] obytes = null;
			String[] aLista = new String[poNotificacion.getDocumentos().size()];
			int i = 0;
			while(oIterador.hasNext()){
				obytes = (byte[])oIterador.next();
				if(obytes!=null)aLista[i] = Base64.encodeBytes(obytes);
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
				String aux = (String)oIterador.next();
				if(aux!=null) aLista[i] = aux;
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
				String aux = (String)oIterador.next();
				if(aux!=null) aLista[i] = aux;
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
				String aux = (String)oIterador.next();
				if(aux!=null) aLista[i] = aux;
				i++;
			}
			oNotificacion.setNombreDocumentos(aLista);
		}

		return oNotificacion;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion getIdNotificacionServicio(IdentificadorNotificacion poId){
		if(poId == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion oId = new ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion();
		oId.setCodigoDeNotificacion(poId.getCodigoDeNotificacion());
		return oId;
	}

	private IdentificadorNotificacion getIdNotificacionWS(ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion poId){
		if(poId == null){
			return null;
		}
		IdentificadorNotificacion oId = new IdentificadorNotificacion();
		oId.setCodigoDeNotificacion(poId.getCodigoDeNotificacion());
		return oId;
	}

	private Notificaciones getNotificacionesWS(ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones poNots){
		if(poNots == null){
			return null;
		}
		Notificaciones oNots = new Notificaciones();
		if(poNots.getNotificaciones() != null){
			Notificacion[] lNots = new Notificacion[poNots.getNotificaciones().size()];
			Iterator oIterator = poNots.getNotificaciones().iterator();
			int i = 0;
			while(oIterator.hasNext()){
				lNots[i] = getNotificacionWS((ieci.tecdoc.sgm.core.services.notificaciones.Notificacion)oIterator.next());
				i++;
			}
			oNots.setNotificaciones(lNots);
		}
		return oNots;
	}
	
	private ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones getCriterioBusquedaNotsService(CriterioBusquedaNotificaciones poCriterio) throws Exception{
		if(poCriterio == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones oCriterio = new ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones();
		Integer estado = null;
		if(poCriterio.getEstado() != null){
			estado = Integer.valueOf(poCriterio.getEstado());
		}
		oCriterio.setEstado(estado);
		oCriterio.setFechaDesde(DateTimeUtil.getDate(poCriterio.getFechaDesde(), ConstantesServicios.DATE_PATTERN));
		oCriterio.setFechaHasta(DateTimeUtil.getDate(poCriterio.getFechaHasta(), ConstantesServicios.DATE_PATTERN));
		oCriterio.setNif(poCriterio.getNif());
		oCriterio.setNotificacion(poCriterio.getNotificacion());
		oCriterio.setTipo(poCriterio.getTipo());
		return oCriterio;
	}
	
	private boolean getConDetalleService(String psConDetalle){
		if( (psConDetalle == null) || ("".equals(psConDetalle)) || (ConstantesServicios.LABEL_FALSE.equals(psConDetalle))){
			return false;
		}else{
			return true;
		}
	}
	
	private EstadoNotificacion getEstadoNotificacionWS(ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion poEstado){
		if(poEstado == null){
			return null;
		}
		EstadoNotificacion oEstado = new EstadoNotificacion();
		oEstado.setEstado(poEstado.getEstado());
		if(poEstado.getFecha() != null){
			oEstado.setFecha(DateTimeUtil.getDateTime(poEstado.getFecha().getTime(), ConstantesServicios.DATE_PATTERN));			
		}
		oEstado.setMotivoRechazo(poEstado.getMotivoRechazo());
		return oEstado;
	}

	private EstadoNotificacionBD getEstadoNotificacionBDWS(ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacionBD poEstado){
		if(poEstado == null){
			return null;
		}
		EstadoNotificacionBD oEstado = new EstadoNotificacionBD();
		oEstado.setDescripcion(poEstado.getDescripcion());
		oEstado.setId(poEstado.getId());
		oEstado.setIdSisnot(poEstado.getIdSisnot());
		return oEstado;
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
		oNot.setNotiId(poNot.getNotiId());
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
		oNot.setDeu(poNot.getDeu());
		oNot.setSistemaId(poNot.getSistemaId());
		oNot.setMovil(poNot.getMovil());
		
		if(poNot.getDocumentos() != null){
			String[] oLista = poNot.getDocumentos();
			byte[] obytes = null;
			ArrayList aLista = new ArrayList(oLista.length);
			for(int i=0; i<oLista.length; i++){
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
}
