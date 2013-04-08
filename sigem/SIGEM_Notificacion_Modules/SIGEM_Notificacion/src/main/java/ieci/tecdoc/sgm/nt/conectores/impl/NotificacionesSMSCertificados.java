package ieci.tecdoc.sgm.nt.conectores.impl;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.nt.ConstantesNotificacion;
import ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaDocuBean;
import ieci.tecdoc.sgm.nt.bean.DetalleEstadoBean;
import ieci.tecdoc.sgm.nt.bean.NotificacionBean;
import ieci.tecdoc.sgm.nt.conectores.ConectorNotificacion;
import ieci.tecdoc.sgm.nt.conectores.types.ResultadoAltaNotificacion;
import ieci.tecdoc.sgm.nt.config.NotificacionesConfig;
import ieci.tecdoc.sgm.nt.config.beans.ConectorDefinition;
import ieci.tecdoc.sgm.nt.database.DocumentosDatos;
import ieci.tecdoc.sgm.nt.database.datatypes.Documentos;
import ieci.tecdoc.sgm.nt.database.datatypes.Notificacion;
import ieci.tecdoc.sgm.nt.exception.ServicioWebErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.ServicioWebExcepcion;
import ieci.tecdoc.sgm.nt.util.ConstTiposDocumento;

import java.util.Calendar;

import org.apache.log4j.Logger;

public class NotificacionesSMSCertificados implements ConectorNotificacion{
	private Logger logger = Logger.getLogger(NotificacionesSisnot.class);

	private final int CONST_ESTADO_NOTIFICACION_ENVIADA=5;
	private final Integer SMS_ESTADO_FALLO=new Integer(0);
	private final Integer SMS_ESTADO_FALLO_SIGEM=new Integer(-1);
	private final Integer SMS_ESTADO_NOTIFICACION_CREADA=new Integer(1);
	
	public boolean checkConnection(int timeout,String url) {
		return true;
	}

	public ResultadoAltaNotificacion crearNotificacion(Object notificacion,String entidad,String idSession) throws Exception{
		String mensaje=null;
		String movil=null;
		ResultadoAltaNotificacion resultado=null;
		try{
        	if(notificacion instanceof Notificacion){
        		mensaje=((Notificacion)notificacion).getTexto();
        		movil=((Notificacion)notificacion).getMovil();
        	}else if(notificacion instanceof NotificacionBean){
        		mensaje=((NotificacionBean)notificacion).getTexto();
        		movil=((NotificacionBean)notificacion).getMovil();
        	}
        	
        	ConectorDefinition conector=NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SMS,entidad);
		
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			String smsId=servicio.sendCertSMS(conector.getUser(), conector.getPassword(),"+34600000000",movil, mensaje,"ES");
			
			resultado=new ResultadoAltaNotificacion();
	        if(smsId==null) return null;
	        resultado.setIdNotificacionSistemaExterno(""+smsId);
		} catch (Exception e) {
			logger.info("Error en el envio del SMS", e);
			throw e;
		}
		return resultado;
	}
	
	private byte[] obtenerJustificante(String smsId,String entidad) throws ServicioWebExcepcion{
		try { 
			ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
			ConectorDefinition conector=NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SMS,entidad);
			
			return servicio.getCertSMSSignatureDocument(conector.getUser(), conector.getPassword(), smsId);
		}catch(Exception e){
			logger.debug("Fallo inesperado llamando al servicio: " + e.getMessage());
            throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_UNKNOW_ERROR);
		}
	}
	
	private void compruebaEstadoYAlmacenaJustificanteDeEnvio(int estado,
			Notificacion notificacion,String entidad) throws ServicioWebExcepcion{
		//Si el estado se corresponde con estado enviado
		if(estado==CONST_ESTADO_NOTIFICACION_ENVIADA){
			
			CriteriosBusquedaDocuBean criterios=new CriteriosBusquedaDocuBean();
			criterios.setNotiId(notificacion.getNotiId());
			criterios.setTipoDoc(ConstTiposDocumento.TIPO_DOCUMENTO_ACUSE_NOTIFICACION);
			
			Documentos documentos=null;
			DocumentosDatos docNoti=new DocumentosDatos();
			try{
				documentos=docNoti.getDocumentos('s',criterios,entidad);
	    	}catch(Exception e){
	    		logger.debug("Se ha producido un error "+e);
	    	}
	    	
	    	if(documentos==null || documentos.count()==0){
		    	//y aún no esta el justificante de envio de esa notificacion 
				//en la tabla de documentos de notificaciones
		    	byte[] justificante=obtenerJustificante(notificacion.getId(),entidad);
		    		
	    		//obtener del sistema externo el justificante de envio
	    		//e insertarlo en la tabla de documentos asociados a notificaciones
	    		try{
		    		DocumentosDatos bdDoc = new DocumentosDatos();
	                
	                bdDoc.setCodigo("justificanteSMS");               
	                bdDoc.setExpediente(notificacion.getNumeroExpediente());
	                bdDoc.setNifDestinatario(notificacion.getNifDestinatario()); 
	                bdDoc.setTipoDoc(ConstTiposDocumento.TIPO_DOCUMENTO_ACUSE_NOTIFICACION); 
	          
	                bdDoc.setGuid(new ieci.tecdoc.sgm.rde.ContenedorDocumentosManager()
	                	.storeDocument(null,justificante,"pdf", entidad));
	                bdDoc.setNotiId(notificacion.getNotiId());
	                // y lo añadimos a la base de dato
	                bdDoc.add(entidad);
	    		}catch(Exception ex) {
	               logger.debug("Fallo inesperado al dar de alta la notificacion: " + ex.getMessage());
	               throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_UNKNOW_ERROR);
	            }
			}
		}
			
	}
	
	public DetalleEstadoBean obtenerEstado(Notificacion notificacion,String entidad) throws ServicioWebExcepcion{		
		DetalleEstadoBean estado = new DetalleEstadoBean();
        try { 
        	ServicioMensajesCortos servicio = LocalizadorServicios.getServicioMensajesCortos();
        	
        	ConectorDefinition conector=NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SMS,entidad);
			int res= servicio.getCertSMSSignatureStatus(conector.getUser(), conector.getPassword(), notificacion.getId());

			compruebaEstadoYAlmacenaJustificanteDeEnvio(res,notificacion,entidad);
			
            //estado.setError(null);
            //estado.setMotivoRechazo(null);
            estado.setEstado(""+res);
            Calendar fechaActualizacionEstado=Calendar.getInstance();
            fechaActualizacionEstado.setTime(DateTimeUtil.getCurrentDateTime());
            estado.setFecha(fechaActualizacionEstado);
        } catch(Exception ex) {
             logger.debug("Fallo inesperado llamando al servicio: " + ex.getMessage());
             throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_UNKNOW_ERROR);
        }
        return estado;
	}
	
	public boolean isEstadoFallo(Integer estadoNotificacion){
		return estadoNotificacion.equals(SMS_ESTADO_FALLO)
			|| estadoNotificacion.equals(SMS_ESTADO_FALLO_SIGEM);
	}
	
	public Integer getEstadoNotificacionCreada(){
		return SMS_ESTADO_NOTIFICACION_CREADA;
	}
}
