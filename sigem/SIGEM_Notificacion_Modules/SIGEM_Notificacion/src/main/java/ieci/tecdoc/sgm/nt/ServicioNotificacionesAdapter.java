package ieci.tecdoc.sgm.nt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesException;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaDocumentos;
import ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.EstadoNotificacionBD;
import ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.InfoDocumento;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificacionesException;
import ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaDocuBean;
import ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaNotiBean;
import ieci.tecdoc.sgm.nt.bean.DetalleEstadoBDBean;
import ieci.tecdoc.sgm.nt.bean.DetalleEstadoBean;
import ieci.tecdoc.sgm.nt.bean.DocumentoInfo;
import ieci.tecdoc.sgm.nt.bean.EstadoBean;
import ieci.tecdoc.sgm.nt.bean.IdentificadorBean;
import ieci.tecdoc.sgm.nt.bean.NotificacionBean;
import ieci.tecdoc.sgm.nt.bean.ResultadoBusquedaBean;
import ieci.tecdoc.sgm.nt.bean.ResultadosBusquedaBean;
import ieci.tecdoc.sgm.nt.exception.SgmException;

public class ServicioNotificacionesAdapter implements ServicioNotificaciones{

	private static final Logger logger = Logger.getLogger(ServicioNotificacionesAdapter.class);
	
	public void actualizaEstados(Entidad entidad) throws ServicioNotificacionesException {
		try {		
			GestorNotificaciones.actualizaEstados(null, entidad.getIdentificador());
		} catch (SgmException e) {
			logger.error("Error actualizando datos de notificaciones.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado actualizando datos de notificaciones.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void actualizaEstado(String numeroExpediente, Integer estado, 
    		Date fechaActualizacion, String nifDestino, String notiid, Entidad entidad) throws ServicioNotificacionesException {
		try {		
			GestorNotificaciones.actualizaEstado(numeroExpediente, estado, fechaActualizacion,
					nifDestino, notiid, entidad.getIdentificador());
		} catch (SgmException e) {
			logger.error("Error actualizando el estado de la notificacion.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado actualizando actualizando el estado de la notificacion.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public IdentificadorNotificacion altaNotificacion(Notificacion poNotificacion, Entidad entidad) throws ServicioNotificacionesException {
		try {		
			return getIdentificadorServicio(GestorNotificaciones.altaNotificacion(null, getNotificacionImpl(poNotificacion), entidad.getIdentificador()));
		} catch (SgmException e) {
			logger.error("Error en alta de notificación.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en alta de notificación.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Notificaciones consultarNotificaciones(CriterioBusquedaNotificaciones poCriterio, boolean pbConDetalle, Entidad entidad) throws ServicioNotificacionesException {
		try {		
			return getNotificaciones(
					GestorNotificaciones
						.consultarNotificaciones(
								null, 
								getCriterioBusquedaNotificacionesImpl(poCriterio), 
								pbConDetalle, entidad.getIdentificador()));
		}  catch (Throwable e){
			logger.error("Error inesperado en consulta de notificaciones.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Notificacion detalleNotificacion(IdentificadorNotificacion poIdentificador, Entidad entidad) throws ServicioNotificacionesException {
		try {		
			return getNotificacionServicio(
					GestorNotificaciones.detalleNotificacion(
							null, 
							getIdentificadorServicio(poIdentificador),
							entidad.getIdentificador()));
		} catch (SgmException e) {
			logger.error("Error recuperando detalle de notificación.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado recuperando detalle de notificación.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public Notificacion detalleNotificacionByNotiId(String notiId, Entidad entidad) throws ServicioNotificacionesException {
		try {		
			return getNotificacionServicio(
					GestorNotificaciones.detalleNotificacionByNotiId(
							null, 
							notiId,
							entidad.getIdentificador()));
		} catch (SgmException e) {
			logger.error("Error recuperando detalle de notificación.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado recuperando detalle de notificación.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public EstadoNotificacion obtenerEstado(String psIdNotificacion, Entidad entidad) throws ServicioNotificacionesException {
		try {
			return getEstadoNotificacion(
					GestorNotificaciones.obtenerEstado(psIdNotificacion, entidad.getIdentificador()));
		} catch (SgmException e) {
			logger.error("Error recuperando estado de notificación.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado recuperando estado de notificación.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public EstadoNotificacionBD obtenerEstadoBD(Integer idEstado, Entidad entidad) throws ServicioNotificacionesException {
		try {		
			EstadoBean entrada = new EstadoBean();
			entrada.setEstado(idEstado.intValue());
			return getEstadoNotificacionBD(
					GestorNotificaciones.obtenerEstadoBD(idEstado, entidad.getIdentificador()));
		} catch (SgmException e) {
			logger.error("Error recuperando estado de notificación.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado recuperando estado de notificación.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public InfoDocumento recuperaDocumento(CriterioBusquedaDocumentos poCriterio, Entidad entidad) throws ServicioNotificacionesException {
		try {		
			return getInfoDocumento(
					GestorNotificaciones
						.recuperaDocumento(null, getCriterioBusquedaDocsImpl(poCriterio), entidad.getIdentificador()));
		} catch (SgmException e) {
			logger.error("Error recuperando documento de notificación.", e);
			throw getServicioNotificacionesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado recuperando documento de notificación.", e);
			throw new ServicioNotificacionesException(ServicioNotificacionesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	private ServicioNotificacionesException getServicioNotificacionesException(SgmException poException){
		if(poException == null){
			return new ServicioNotificacionesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_NOTIFICATIONS_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new ServicioNotificacionesException(Long.valueOf(cCodigo.toString()).longValue(), poException);
		
	}

	private NotificacionBean getNotificacionImpl(Notificacion poNot){
		if(poNot == null){
			return null;
		}
		NotificacionBean oNot = new NotificacionBean();
		oNot.setApellidosDest(poNot.getApellidosDest());
		oNot.setAsunto(poNot.getAsunto());
		oNot.setCodigoNoti(poNot.getCodigoNoti());
		oNot.setCodigoPostal(poNot.getCodigoPostal());
		oNot.setCorreoDest(poNot.getCorreoDest());
		oNot.setDeu(poNot.getDeu());
		oNot.setDescripcionEstado(poNot.getDescripcionEstado());
		
//		List documentos = new ArrayList(poNot.getDocumentos());
//		List nombreDocumentos = new ArrayList(poNot.getNombreDocumentos());
//		List extension = new ArrayList(poNot.getExtension());
//		List guid = new ArrayList(poNot.getGuid());
		
		List documentos = poNot.getDocumentos();
		List nombreDocumentos = poNot.getNombreDocumentos();
		List extension = poNot.getExtension();
		List guid = poNot.getGuid();
		
		for(int i = 0; i < poNot.getDocumentos().size(); i++){
			oNot.addDocumento(	(String)nombreDocumentos.get(i), 
								(byte[])documentos.get(i), 
								(String)extension.get(i), 
								(String)guid.get(i));
		}
		oNot.setEscaleraDireccion(poNot.getEscaleraDireccion());
		oNot.setEstado(poNot.getEstado());
		oNot.setFechaActualiEstado(poNot.getFechaActualiEstado());
		oNot.setFechaEntrega(poNot.getFechaEntrega());
		oNot.setFechaRegistro(poNot.getFechaRegistro());
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
		oNot.setProvincia(poNot.getProvincia());
		oNot.setPuertaDireccion(poNot.getPuertaDireccion());
		oNot.setTelefono(poNot.getTelefono());
		oNot.setMovil(poNot.getMovil());
		oNot.setTexto(poNot.getTexto());
		oNot.setTipo(poNot.getTipo());
		oNot.setTipoCorrespondencia(poNot.getTipoCorrespondencia());
		oNot.setTipoViaDireccion(poNot.getTipoViaDireccion());
		oNot.setUsuario(poNot.getUsuario());
		oNot.setViaDireccion(poNot.getViaDireccion());
		oNot.setNotiId(poNot.getNotiId());
		return oNot;
	}

	private Notificacion getNotificacionServicio(NotificacionBean poNot){
		if(poNot == null){
			return null;
		}
		Notificacion oNot = new Notificacion();
		oNot.setApellidosDest(poNot.getApellidosDest());
		oNot.setAsunto(poNot.getAsunto());
		oNot.setCodigoNoti(poNot.getCodigoNoti());
		oNot.setCodigoPostal(poNot.getCodigoPostal());
		oNot.setCorreoDest(poNot.getCorreoDest());
		List documentos = new ArrayList(poNot.documentoCount());
		List nombreDocumentos = new ArrayList(poNot.documentoCount());
		List extension = new ArrayList(poNot.documentoCount());
		List guid = new ArrayList(poNot.documentoCount());
		for(int i = 0; i < poNot.documentoCount(); i++){
			documentos.add(poNot.getDataDocumento(i));
			nombreDocumentos.add(poNot.getNameDocumento(i));
			extension.add(poNot.getExtDocumento(i));
			guid.add(poNot.getUIDDocumento(i));
		}
		oNot.setDocumentos(documentos);
		oNot.setNombreDocumentos(nombreDocumentos);
		oNot.setExtension(extension);
		oNot.setGuid(guid);
		oNot.setDescripcionEstado(poNot.getDescripcionEstado());
		oNot.setEscaleraDireccion(poNot.getEscaleraDireccion());
		oNot.setEstado(poNot.getEstado());
		oNot.setFechaActualiEstado(poNot.getFechaActualiEstado());
		oNot.setFechaEntrega(poNot.getFechaEntrega());
		oNot.setFechaRegistro(poNot.getFechaRegistro());
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
		oNot.setProvincia(poNot.getProvincia());
		oNot.setPuertaDireccion(poNot.getPuertaDireccion());
		oNot.setTelefono(poNot.getTelefono());
		oNot.setTexto(poNot.getTexto());
		oNot.setTipo(poNot.getTipo());
		oNot.setTipoCorrespondencia(poNot.getTipoCorrespondencia());
		oNot.setTipoViaDireccion(poNot.getTipoViaDireccion());
		oNot.setUsuario(poNot.getUsuario());
		oNot.setViaDireccion(poNot.getViaDireccion());
		oNot.setNotiId(poNot.getNotiId());
		oNot.setSistemaId(poNot.getSistemaId());
		return oNot;
	}
	
	private IdentificadorNotificacion getIdentificadorServicio(IdentificadorBean poIdent){
		if(poIdent == null){
			return null;
		}
		IdentificadorNotificacion oIdent = new IdentificadorNotificacion();
		oIdent.setCodigoDeNotificacion(poIdent.getCodigoDeNotificacion());
		return oIdent;
	}
	
	private IdentificadorBean getIdentificadorServicio(IdentificadorNotificacion poIdent){
		if(poIdent == null){
			return null;
		}
		IdentificadorBean oIdent = new IdentificadorBean();
		oIdent.setCodigoDeNotificacion(poIdent.getCodigoDeNotificacion());
		return oIdent;
	}
	
	private CriteriosBusquedaNotiBean getCriterioBusquedaNotificacionesImpl(CriterioBusquedaNotificaciones poCriterio){
		if(poCriterio == null){
			return null;
		}
		CriteriosBusquedaNotiBean oCriterio = new CriteriosBusquedaNotiBean();
		oCriterio.setEstado(poCriterio.getEstado());
		oCriterio.setFechaDesde(poCriterio.getFechaDesde());
		oCriterio.setFechaHasta(poCriterio.getFechaHasta());
		oCriterio.setNif(poCriterio.getNif());
		oCriterio.setNotificacion(poCriterio.getNotificacion());
		oCriterio.setTipo(poCriterio.getTipo());
		oCriterio.setNumExp(poCriterio.getNumExp());
		return oCriterio;
	}
	
	private Notificaciones getNotificaciones(ResultadosBusquedaBean poNots){
		if(poNots == null){
			return null;
		}
		Notificaciones oNots = new Notificaciones();
		List notificaciones = new ArrayList();
		for(int i = 0; i < poNots.count(); i++){
			notificaciones.add(getNotificacionServicio(((ResultadoBusquedaBean)poNots.get(i)).getDetalle()));
		}
		oNots.setNotificaciones(notificaciones);
		return oNots;
	}
	
	private EstadoNotificacion getEstadoNotificacion(DetalleEstadoBean poEstado){
		if(poEstado ==  null){
			return null;
		}
		EstadoNotificacion oEstado = new EstadoNotificacion();
		oEstado.setError(poEstado.getError());
		oEstado.setEstado(poEstado.getEstado());
		oEstado.setFecha(poEstado.getFecha());
		oEstado.setMotivoRechazo(poEstado.getMotivoRechazo());
		return oEstado;
	}

	private EstadoNotificacionBD getEstadoNotificacionBD(DetalleEstadoBDBean poEstado){
		if(poEstado ==  null){
			return null;
		}
		EstadoNotificacionBD oEstado = new EstadoNotificacionBD();
		oEstado.setDescripcion(poEstado.getDescripcion());
		oEstado.setId(poEstado.getId());
		oEstado.setIdSisnot(poEstado.getIdSisnot());
		return oEstado;
	}
	
	private CriteriosBusquedaDocuBean getCriterioBusquedaDocsImpl(CriterioBusquedaDocumentos poCriterio){
		if(poCriterio ==  null){
			return null;
		}
		CriteriosBusquedaDocuBean oCriterio = new CriteriosBusquedaDocuBean();
		oCriterio.setCodigDoc(poCriterio.getCodigDoc());
		oCriterio.setCodigoNoti(poCriterio.getCodigoNoti());
		oCriterio.setExpediente(poCriterio.getExpediente());
		oCriterio.setNifDestinatario(poCriterio.getNifDestinatario());
		return oCriterio;
	}
	
	private InfoDocumento getInfoDocumento(DocumentoInfo poDoc){
		if(poDoc == null){
			return null;
		}
		InfoDocumento oDoc = new InfoDocumento();
		oDoc.setContent(poDoc.getContent());
		oDoc.setExtension(poDoc.getExtension());
		oDoc.setGuid(poDoc.getGuid());
		oDoc.setMimeType(poDoc.getMimeType());
		return oDoc;
	}
}
