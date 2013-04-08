package ieci.tecdoc.sgm.nt.conectores.impl;

import ieci.tecdoc.sgm.nt.ConstantesNotificacion;
import ieci.tecdoc.sgm.nt.bean.DetalleEstadoBean;
import ieci.tecdoc.sgm.nt.bean.NotificacionBean;
import ieci.tecdoc.sgm.nt.conectores.ConectorNotificacion;
import ieci.tecdoc.sgm.nt.conectores.types.ResultadoAltaNotificacion;
import ieci.tecdoc.sgm.nt.config.NotificacionesConfig;
import ieci.tecdoc.sgm.nt.database.DocumentosDatos;
import ieci.tecdoc.sgm.nt.database.datatypes.Notificacion;
import ieci.tecdoc.sgm.nt.exception.ClaveIncorrectaExcepcion;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioExcepcion;
import ieci.tecdoc.sgm.nt.exception.ServicioWebErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.ServicioWebExcepcion;
import ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint;
import ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPointBindingStub;
import ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion;
import ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion;
import ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion;
import ieci.tecdoc.sgm.nt.util.IdUsuarioSisnotGetter;
import ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion;
import ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

public class NotificacionesSisnot implements ConectorNotificacion{

	private Logger logger = Logger.getLogger(NotificacionesSisnot.class);
	protected boolean isDebugeable = true;
	private static Integer SISNOT_ESTADO_FALLO=new Integer(-1);
	private static Integer SISNOT_ESTADO_NOTIFICACION_CREADA=new Integer(0);
	
	public boolean checkConnection(int timeout,String sUrl) {
		//Comprobamos previamente la conexion con correos para evitar un timeout largo
    	try{
    		URL url = new java.net.URL(sUrl);
    		InetAddress direccion = InetAddress.getByName(url.getHost());
    		InetSocketAddress socketAddres = new InetSocketAddress (direccion, url.getPort()  /* puerto */);
    		Socket socket = new Socket();
    		socket.connect(socketAddres, timeout);
    		socket.close();
    	} catch(SocketTimeoutException exc)	{
    		logger.error(exc);
    		return false;
    	}
    	catch (Exception e) {
			return false;
		}
		return true;
	}

	private AltaNotificacion convertirNotificacionBd(Notificacion notificacion,String entidad,String idSession_) 
				throws ServicioWebExcepcion,DocumentosRepositorioExcepcion,ClaveIncorrectaExcepcion,
						RepositorioDocumentosExcepcion,GuidIncorrectoExcepcion{
		AltaNotificacion entrada = new AltaNotificacion();
		try{
            //rellenar los campos del objeto de entrada al servicio
            entrada.setAsunto(notificacion.getAsunto().length()>100?notificacion.getAsunto().substring(0,100).trim():notificacion.getAsunto().trim());               
            entrada.setAsunto(corregirCadena(entrada.getAsunto()));
            entrada.setCodProcedimiento(notificacion.getProcedimientoExpedienteAnterior().trim());        
            
            entrada.setCodSistemaEmisor(NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SISNOT, entidad).getOrganismo().trim());
            entrada.setTipoCorrespondencia(NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SISNOT, entidad).getTipoCorrespondencia().trim());
            
            entrada.setCuerpo(notificacion.getTexto().length()>200?notificacion.getTexto().substring(0,200).trim():notificacion.getTexto().trim());
            entrada.setCuerpo(corregirCadena(entrada.getCuerpo()));
            entrada.setFechaRegistro(notificacion.getFechaEfectuadaEntrega().toString().trim());  
            
            entrada.setIdUsuario(IdUsuarioSisnotGetter.getIdUsuarioFromDEU(notificacion.getDEU(),entidad));
            
            entrada.setNumExpediente(notificacion.getNumeroExpediente().trim());          
            entrada.setNumRegistro((StringUtils.isEmpty(notificacion.getRegistroSalida())?null:notificacion.getRegistroSalida().trim()));              
            
            
            // el documento no esta en la Notificacion por lo que tenemos que ir
            // a buscarlo 
            DocumentosDatos docs = new DocumentosDatos();
     
            docs.setNifDestinatario(notificacion.getNifDestinatario().trim());
            docs.setExpediente(notificacion.getNumeroExpediente().trim());
            docs.setNotiId(notificacion.getNotiId().trim());
        
            // buscamos por la clave primaria por lo que solo puede haber uno
            docs.load(entidad);                 
            entrada.setNombreArchivo(docs.getCodigo().trim());       
            entrada.setNotificacion64(ieci.tecdoc.sgm.base.base64.Base64Util.encode(new ieci.tecdoc.sgm.rde.ContenedorDocumentosManager().retrieveDocument(idSession_,docs.getGuid().trim(), entidad).getContent()));
        }catch (java.lang.NullPointerException ex){
            throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_INVALID_DATA,ex);
        }
		return entrada;
	}
	
	private AltaNotificacion convertirNotificacionBean(NotificacionBean notificacion,String entidad) 
				throws ServicioWebExcepcion,DocumentosRepositorioExcepcion,ClaveIncorrectaExcepcion,
						RepositorioDocumentosExcepcion,GuidIncorrectoExcepcion {
		AltaNotificacion entrada = new AltaNotificacion();
		try{
	        //rellenar los campos del objeto de entrada al servicio
	        entrada.setAsunto(notificacion.getAsunto().length()>100?notificacion.getAsunto().substring(0,100).trim():notificacion.getAsunto().trim());               
	        entrada.setAsunto(corregirCadena(entrada.getAsunto()));
	        entrada.setCodProcedimiento(notificacion.getProcedimiento().trim());            
	        entrada.setCuerpo(notificacion.getTexto().length()>200?notificacion.getTexto().substring(0,200).trim():notificacion.getTexto().trim());
	        entrada.setCuerpo(corregirCadena(entrada.getCuerpo()));
	        
	        entrada.setCodSistemaEmisor(NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SISNOT, entidad).getOrganismo().trim());
            entrada.setTipoCorrespondencia(NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SISNOT, entidad).getTipoCorrespondencia().trim());
            
	        //entrada.setFechaRegistro(nuevo_.getFechaRegistro().toString());
	        Date date = notificacion.getFechaRegistro();
	        if (date != null) {
	        	entrada.setFechaRegistro(date.toString());
	        }
	          
	        entrada.setIdUsuario(IdUsuarioSisnotGetter.getIdUsuarioFromDEU(notificacion.getDeu(),entidad));            

	        if (notificacion.documentoCount() > 0){
	            entrada.setNombreArchivo(notificacion.getNameDocumento(0).trim());
	            entrada.setNotificacion64(ieci.tecdoc.sgm.base.base64.Base64Util.encode(notificacion.getDataDocumento(0)));
	        }
	      
	        entrada.setNumExpediente(notificacion.getNumeroExpediente().trim());          
	        entrada.setNumRegistro((StringUtils.isEmpty(notificacion.getNumeroRegistro())?null:notificacion.getNumeroRegistro().trim()));              
	    }catch (java.lang.NullPointerException ex){
	         throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_INVALID_DATA,ex);
	    }
		return entrada;
	}
	
	public ResultadoAltaNotificacion crearNotificacion(Object notificacion,String entidad,String idSession) throws Exception{
		ConfirmacionAltaNotificacion salida=null;
		try {
            AltaNotificacion entrada =null;
            try{
            	if(notificacion instanceof Notificacion){
            		entrada=convertirNotificacionBd((Notificacion)notificacion,entidad,idSession);
            	}else if(notificacion instanceof NotificacionBean){
            		entrada=convertirNotificacionBean((NotificacionBean)notificacion,entidad); 
            	}
            }catch (ServicioWebExcepcion ex){
           		logger.debug("El bean de entrada provoco un error: " + ex.getMessage());
           		throw ex;
           	}
                       
            if (isDebugeable)
                logger.debug("PARAMETROS DE LA LLAMADA AL SERVICIO WEB servicioNotificacionesEndPointPort.crearNotificacion: " + 
                        "Asunto: " + entrada.getAsunto() +
                        "Procedimiento: " + entrada.getCodProcedimiento() + 
                        "Sistema emisor: " + entrada.getCodSistemaEmisor() +
                        "Cuerpo: " + entrada.getCuerpo() +
                        "Fecha de registro: " + entrada.getFechaRegistro() +
                        "Usuario: " + entrada.getIdUsuario() +
                        "Archivo: " + entrada.getNombreArchivo() +
                        "Archivo 64: " + entrada.getNotificacion64() +
                        "Expediente: " + entrada.getNumExpediente() +
                        "Registro: " + entrada.getNumRegistro() +  
                        "Correspondecia: " + entrada.getTipoCorrespondencia());
            
            ServicioNotificacionesEndPoint servicioNotificacionesEndPointPort
            	= new ServicioNotificacionesEndPointBindingStub(new java.net.URL(getURLWS(entidad)) , null);
            salida = servicioNotificacionesEndPointPort.crearNotificacion(entrada);
            
            servicioNotificacionesEndPointPort = null;
            System.gc();
        }catch(Exception ex) { throw ex; }
        
        ResultadoAltaNotificacion resultado=new ResultadoAltaNotificacion();
        if(salida==null) return null;
        if(salida.getNcc()==null) resultado.setErrorSistemaExterno(salida.getCodError());
        else resultado.setIdNotificacionSistemaExterno(""+salida.getNcc());
        return resultado;
	}
	
	public DetalleEstadoBean obtenerEstado(Notificacion notificacion,String idEntidad) throws ServicioWebExcepcion{
		DetalleEstadoBean estado = new DetalleEstadoBean();
        try { 
            //ServicioNotificacionesLocator locator = new ServicioNotificacionesLocator();
            ServicioNotificacionesEndPoint servicioNotificacionesEndPointPort 
            	= new ServicioNotificacionesEndPointBindingStub(new java.net.URL(getURLWS(idEntidad)) , null);
            EstadoNotificacion llamada = 
            	servicioNotificacionesEndPointPort.consultaEstadoNotificacion(Long.parseLong(notificacion.getId()));
            																		//idNotificacionSistemaExterno
           
            // una vez resulesta la llamda ya podemos rellenar el xml string de salida
            // ya que siempre se puede rellenar
            estado.setError(llamada.getCodError());
            estado.setMotivoRechazo(llamada.getMotivoRechazo());
            estado.setEstado(llamada.getEstado());
            estado.setFecha(llamada.getFechaEstado());
        } catch(java.rmi.RemoteException ex) {
             if (isDebugeable)
                logger.debug("fallo la comunicacion con el servicio: " + ex.getMessage());
             throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_CONNECT_FAILD,ex);    
        } catch(Exception ex) {
             if (isDebugeable)
                logger.debug("Fallo inesperado llamando al servicio: " + ex.getMessage());
            throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_UNKNOW_ERROR,ex);
        }
        return estado;
	}
	
	public boolean isEstadoFallo(Integer estadoNotificacion){
		return estadoNotificacion.equals(SISNOT_ESTADO_FALLO);
	}
	
	public Integer getEstadoNotificacionCreada(){
		return SISNOT_ESTADO_NOTIFICACION_CREADA;
	}
	
	private String getURLWS(String entidad){
		String url="URL----Incorrecta";
        try{
        	url=NotificacionesConfig.getConectorDefinition(ConstantesNotificacion.ID_SISTEMA_SISNOT,entidad).getUrl();
        }catch (Exception e){
            // que no haga nada para que despues salte una excepcion
        	logger.error(e);
        }
        return url;
	}
	
	private String corregirCadena(String cadenaACorregir){
		if(cadenaACorregir==null) return null;
		final String[][] correcciones=new String[][]{{"á","a"},{"é","e"},{"í","i"},{"ó","o"},{"ú","u"},
													 {"Á","A"},{"É","E"},{"Í","I"},{"Ó","O"},{"Ú","U"},
													 {"nº","num"}};
		String cadena=cadenaACorregir;
		for(int i=0;i<correcciones.length;i++){
			cadena=cadena.replaceAll(correcciones[i][0], correcciones[i][1]);
		}
		return cadena;
	}
}
