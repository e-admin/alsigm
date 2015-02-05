/*
 * GestorNotificaciones.java
 *
 * Created on 21 de mayo de 2007, 17:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt;
/*
 * $Id: GestorNotificaciones.java,v 1.2.2.6 2008/10/13 09:07:43 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaDocuBean;
import ieci.tecdoc.sgm.nt.bean.CriteriosBusquedaNotiBean;
import ieci.tecdoc.sgm.nt.bean.DetalleEstadoBDBean;
import ieci.tecdoc.sgm.nt.bean.DetalleEstadoBean;
import ieci.tecdoc.sgm.nt.bean.DocumentoInfo;
import ieci.tecdoc.sgm.nt.bean.GenericoBean;
import ieci.tecdoc.sgm.nt.bean.IdentificadorBean;
import ieci.tecdoc.sgm.nt.bean.NotificacionBean;
import ieci.tecdoc.sgm.nt.bean.ResultadoBusquedaBean;
import ieci.tecdoc.sgm.nt.bean.ResultadosBusquedaBean;
import ieci.tecdoc.sgm.nt.conectores.ConectorNotificacion;
import ieci.tecdoc.sgm.nt.conectores.types.ResultadoAltaNotificacion;
import ieci.tecdoc.sgm.nt.config.NotificacionesConfig;
import ieci.tecdoc.sgm.nt.database.DocumentosDatos;
import ieci.tecdoc.sgm.nt.database.EstadosDatos;
import ieci.tecdoc.sgm.nt.database.NotificacionesDatos;
import ieci.tecdoc.sgm.nt.database.datatypes.Documento;
import ieci.tecdoc.sgm.nt.database.datatypes.Documentos;
import ieci.tecdoc.sgm.nt.database.datatypes.Notificacion;
import ieci.tecdoc.sgm.nt.database.datatypes.Notificaciones;
import ieci.tecdoc.sgm.nt.exception.DatosIncorrectosErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.DatosIncorrectosExcepcion;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.DocumentosRepositorioExcepcion;
import ieci.tecdoc.sgm.nt.exception.ServicioWebErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.ServicioWebExcepcion;
import ieci.tecdoc.sgm.nt.exception.SgmException;

import java.util.Date;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * Gestor del conjunto de funciones implementadas para el manejo de las
 * notificaiones
 */
public class GestorNotificaciones {
    
   private static Logger logger = Logger.getLogger(GestorNotificaciones.class);
   protected static boolean isDebugeable = true;
   
   // Organismos fijos
   //public static final String ENTIDAD_EMISORA_SIGEM = "SIGEM";
   
   // Tipos de correspondencia
   //public static final String TIPO_CORRESPONDENCIA_CIFRADA = "NOTSIGEM";
   //public static final String TIPO_CORRESPONDENCIA_SIN_CIFRAR = "COMSIGEM";
   
   // ojo con estas constantes que tienen que tener el mismo valor
   // que la clave primaria de los estado que represnetan en base de datos
   public static final Integer VALOR_ESTADO_FALLO = new Integer(-1);
   public static final Integer VALOR_ESTADO_ERROR_DEVUELTO_CONECTOR = new Integer(-2);
   public static final Integer VALOR_ESTADO_ENVIADO = new Integer(0);
   public static final Integer VALOR_ESTADO_DISPOSICION = new Integer(2);
   public static final Integer VALOR_ESTADO_CADUCADO = new Integer(4);
   
   private static final String MENSAJE_FALLO_BUSQUEDA_BD = "Se produjo un error al realizar la busqueda en base de datos";
   
   private static final String MENSAJE_FALLO_INSERTAR = "No se encuentra el servicio SISNOT";
   
   
   public static final String CON_ERROR_BUSQUEDA = "1";
   public static final String SIN_ERROR_BUSQUEDA = "0";
     
   
   private static Notificaciones buscarNotificacionesSinAcuseRecibo(Integer estado,String entidad) throws DatosIncorrectosExcepcion{
		// buscamos todas las notificaciones que esten en estado de enviado / pendiente
	    NotificacionesDatos bd = new NotificacionesDatos();
	   	CriteriosBusquedaNotiBean parametros = new CriteriosBusquedaNotiBean();
	   	parametros.setEstado(VALOR_ESTADO_DISPOSICION );
	   	Notificaciones valores = null;
	   	try{
	   		valores = bd.getNotificaciones(  NotificacionesDatos.SEARCH_TO_UPDATE,
	                                                       parametros, entidad);
	   	}catch (Exception e){
	   		logger.debug(e);
	   		throw new DatosIncorrectosExcepcion(DatosIncorrectosErrorCodigos.EC_ERROR_ESTADO_SEARCH,e);            
	   	}
	   	return valores;
   }
   
    /*
     * Actualiza todos los estados pendientes de una respuesta de sisnot. 
     *
     * @param String id de sesion
     * @throws  ServicioWebExcepcion,
     *          DatosIncorrectosExcepcion
     */
   public static GenericoBean actualizaEstados (String idSession_, String entidad)
   throws ServicioWebExcepcion,
          DatosIncorrectosExcepcion{
	   return actualizaEstados (idSession_, entidad, 0);
   }
   
    public static GenericoBean actualizaEstados (String idSession_, String entidad, int timeout)
    throws ServicioWebExcepcion,
           DatosIncorrectosExcepcion{
    	ConectorNotificacion conector=null;    
    	
    	Notificaciones valores = buscarNotificacionesSinAcuseRecibo(VALOR_ESTADO_DISPOSICION,entidad);
    	if(valores==null) return null;
    	// ahora para todas las notificaciones
    	for (int i = 0; i < valores.count(); i++){
    		Notificacion actual = valores.get(i);
    		boolean altaCorrecta = true;	
	    	
    		String idSistema=ConstantesNotificacion.ID_SISTEMA_SISNOT;
    		if(!StringUtils.isEmpty(actual.getSistemaId())){
    			idSistema=actual.getSistemaId();   				
    		}
    		
    		conector=NotificacionesConfig.getConector(idSistema,entidad);
    		
    		if(!NotificacionesConfig.getConectorDefinition(idSistema,entidad).getActualizacionEstadoBool())
				continue; 
    		String url=NotificacionesConfig.getConectorDefinition(idSistema,entidad).getUrl();
    		if(conector==null || !conector.checkConnection(timeout,url)){
        		logger.error("No se ha podido conectar al sistema externo de notificaciones "+
        				idSistema+" para la notificacion "+actual.getNotiId());
        		continue;
        	}
    		
    		boolean cambiaError=false;
    		String nuevoError=MENSAJE_FALLO_INSERTAR;
    		Integer estadoFallo=VALOR_ESTADO_FALLO;
    		if (conector.isEstadoFallo(actual.getEstado())){       
    			DetalleEstadoBean salida=null;
            	try{
            		salida = conector.obtenerEstado(actual,entidad);
            	}catch(Exception e){
            		logger.error("error al obtener el estado:" +e);
            		salida=null;
            	}
            	
            	if(salida==null){
    				// si fallo en su momento lo que hay que hacer es intentar insertarlo en sisnot
            		try {
            			ResultadoAltaNotificacion resultado=conector.crearNotificacion(actual, entidad, idSession_);
            			String idNotificacionExternoConector=null;
            			if(resultado!=null) idNotificacionExternoConector=resultado.getIdNotificacionSistemaExterno();
            			if ( idNotificacionExternoConector== null)
            				altaCorrecta = false;
	            			if (resultado!=null){
	        	            	nuevoError=resultado.getErrorSistemaExterno();
	        	            	estadoFallo=VALOR_ESTADO_ERROR_DEVUELTO_CONECTOR; 
	        	            }
            				if(nuevoError!=null && !nuevoError.equals(actual.getError())) cambiaError=true;
            			else{
            				actual.setId(idNotificacionExternoConector);
            				actual.setEstado(conector.getEstadoNotificacionCreada()); 
               			}
            		} catch(java.rmi.RemoteException ex) {
            			logger.debug(ex);
            			altaCorrecta = false;
            		} catch(Exception ex) {
            			logger.debug(ex);
            			altaCorrecta = false;
            		}
            	}
    		} 
            
    		if (altaCorrecta || cambiaError){
    			DetalleEstadoBean salida=null;
    			Integer estado=null;
    			if(!cambiaError){
	            	try{
	            		salida = conector.obtenerEstado(actual,entidad);
	            	}catch(Exception e){
	            		logger.error("error al obtener el estado:" +e);
	            		salida=null;
	            	}
    			           	
	                if (salida!=null && salida.getEstado() != null && !salida.getEstado().equals("")){
	                    estado = new Integer(salida.getEstado());
	                }/*else if(salida!=null && salida.getError()!=null ){
	                	estado=VALOR_ESTADO_ERROR_DEVUELTO_CONECTOR;
	                	nuevoError=salida.getError();
	                	cambiaError=true;
	                }*/
    			}
	                
                // ahora ya podemos actualizar (si procede)
                if (estado != null || cambiaError){
                	try{
	                	NotificacionesDatos bd = new NotificacionesDatos();
	                	bd.setNotiId(actual.getNotiId().trim());
	                    bd.setEstado(estado!=null?estado:estadoFallo);
	                    if(actual.getId()!=null) bd.setId(actual.getId().trim());
	                    bd.setSistemaId(idSistema);
	                    bd.setNifDestinatario(actual.getNifDestinatario().trim());
	                    bd.setNumeroExpediente(actual.getNumeroExpediente().trim());
	                    bd.setFechaActualizacionEstado(DateTimeUtil.getCurrentDateTime());
	                    bd.setError(cambiaError?nuevoError:"");
                        bd.update(entidad);        
                    }catch (Exception e){
                    	logger.debug(e);
                    	continue;
                        //throw new DatosIncorrectosExcepcion(DatosIncorrectosErrorCodigos.EC_UPDATE_ERROR);            
                    }
                }
    		}
    	}
        return null;
    }

    /*
     * Actualiza el estado de una notificacion. 
     *
     * @param String id de sesion
     * @throws  ServicioWebExcepcion,
     *          DatosIncorrectosExcepcion
     */
    public static GenericoBean actualizaEstado (String numeroExpediente, Integer estado, 
    		Date fechaActualizacion, String nifDestino, String notiId, String entidad)
    throws DatosIncorrectosExcepcion{
    
        NotificacionesDatos bd = new NotificacionesDatos();
        //bd.setEstado(estado);
        bd.setNotiId(notiId);
        bd.setNifDestinatario(nifDestino);
        bd.setNumeroExpediente(numeroExpediente);
        bd.setEstado(estado);
        bd.setFechaActualizacionEstado(fechaActualizacion);
        try{
            bd.update(entidad);        
        }catch (Exception e){
            throw new DatosIncorrectosExcepcion(DatosIncorrectosErrorCodigos.EC_UPDATE_ERROR,e);            
        }
        return null;
    }

    /* Rellena los datos de un objeto NotificacionBean a partir de los datos
     * de un objeto Notificacion
     *
     * @param Notificacion fuente de datos
     * @result NotificacionBean rellenado con los datos de la notificacion de entrada
     */
    private static NotificacionBean parseaNotificacionANotificacionBean (Notificacion actual, String entidad)
    throws Exception{
        NotificacionBean detalle = new NotificacionBean();
        
        detalle.setOrganismo(actual.getOrganismo());
        detalle.setAsunto(actual.getAsunto());
        detalle.setCodigoNoti(actual.getId());
        detalle.setDescripcionEstado(actual.getDescripcionEstado());
        detalle.setEstado(actual.getEstado());
        detalle.setTipo(actual.getTipo());
        detalle.setTexto(actual.getTexto());
        detalle.setNifDest(actual.getNifDestinatario());
        detalle.setNombreDest(actual.getNombreDestinatario());
        detalle.setApellidosDest(actual.getApellidosDestinatario());
        detalle.setCorreoDest(actual.getCorreoDestinatario());
        detalle.setNumeroExpediente(actual.getNumeroExpediente());
        detalle.setIdioma(actual.getIdioma());
        detalle.setTipoViaDireccion(actual.getTipoVia());
        detalle.setViaDireccion(actual.getNombreVia());
        detalle.setNumeroDireccion(actual.getNumeroVia());
        detalle.setEscaleraDireccion(actual.getEscaleraVia());
        detalle.setPisoDireccion(actual.getPisoVia());
        detalle.setPuertaDireccion(actual.getPuertaVia());
        detalle.setTelefono(actual.getTelefono());
        detalle.setMunicipio(actual.getMunicipio());
        detalle.setProvincia(actual.getProvincia());
        detalle.setCodigoPostal(actual.getCodigoPostal());
        detalle.setUsuario(actual.getUsuario());
        detalle.setFechaEntrega(actual.getFechaEfectuadaEntrega());
        detalle.setProcedimiento(actual.getProcedimientoExpedienteAnterior());
        detalle.setFechaRegistro(actual.getFechaRegistroAnterior());
        detalle.setTipoCorrespondencia(actual.getTipoCorrespondencia());
        detalle.setNumeroRegistro(actual.getRegistroSalida());
        detalle.setNotiId(actual.getNotiId());
        detalle.setSistemaId(actual.getSistemaId());
                        
        // falta para cada documento buscar todas sus documentos y ponerlos en el resultado
        // pero solo la informacion de la bd local
                        
        DocumentosDatos bdDoc = new DocumentosDatos();
                        
        CriteriosBusquedaDocuBean criterios = new CriteriosBusquedaDocuBean(actual.getNumeroExpediente(),
                                                                                            actual.getNifDestinatario());
        Documentos docsRelacionados = bdDoc.getDocumentos(DocumentosDatos.SEARCH, criterios, entidad);
                        
        Documento aux;
                        
        for (int j = 0; j < docsRelacionados.count() ; j++){
            aux = docsRelacionados.get(j);
            // nos quedamos solo con el nombre
            detalle.addDocumento(aux.getCodigo(),null,null,aux.getGuid());
        }        
        
        return detalle;
    }
    
    /* Recupera un documento relacionado con una notificacion
     *
     * @param String id de la sesion
     * @param CriteriosBusquedaDocuBean objeto que contiene los criterios de busqueda que son
     *          el codigo de notificacion y el codigo del documento
     *
     * @return DocumentoInfo objeto de la libreria relacionada RDE que contiene el documento
     * @throw  GuidIncorrectoExcepcion
     *         RepositorioDocumentosExcepcion
     *         DocumentosRepositorioExcepcion
     *         DatosIncorrectosExcepcion
     *
     */
    public static DocumentoInfo recuperaDocumento(String idSession_, 
                                                CriteriosBusquedaDocuBean param_, String entidad)
			    throws ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion, 
			           ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion,
			           DocumentosRepositorioExcepcion,
			           DatosIncorrectosExcepcion   {
        
         Documentos docsRelacionados = null;
        // vamos a buscar el expediente y el nif que nos indique el 
        DocumentosDatos bd = new DocumentosDatos();
        try{
            docsRelacionados = bd.getDocumentos(DocumentosDatos.SEARCH_DOC_FROM_ID_NOT ,param_, entidad);
        }catch (Exception e){
            throw new DatosIncorrectosExcepcion(DatosIncorrectosErrorCodigos.EC_UNKNOW_ERROR,e);
        }
        
        // vamos a coger el primero de los documentos ya que solo tiene que haber uno
        if (docsRelacionados.count() == 0)
               throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_FILE_NOT_FOUND);
        
        if (docsRelacionados.count() > 1)
               throw new DocumentosRepositorioExcepcion(DocumentosRepositorioErrorCodigos.EC_TOO_MANY_ROWS);
        
        
        return new DocumentoInfo((new ieci.tecdoc.sgm.rde.ContenedorDocumentosManager()).retrieveDocument(idSession_,docsRelacionados.get(0).getGuid(), entidad ) );
    }
     
    public static DocumentoInfo recuperaJustificanteEntregaSMSCertificado(String idSession_, 
			            CriteriosBusquedaDocuBean param_, String entidad)
			throws ieci.tecdoc.sgm.rde.exception.GuidIncorrectoExcepcion, 
			ieci.tecdoc.sgm.rde.exception.RepositorioDocumentosExcepcion,
			DocumentosRepositorioExcepcion,
			DatosIncorrectosExcepcion   {
    	return null;
    }
    
    /*
     * Muestra el detalle de una notificacion
     *
     * @param String id de la sesion
     * @param IdentificadorBean objeto que tiene los campos necesarios para identificar una notificacion.
     *          estos campos son el codigo de notificacion
     *
     * @return NotificacionBean obejto que contiene todos los campos que forman el detalle de una notificacion
     *
     * @throw Exception
     */
    public static NotificacionBean detalleNotificacion (String idSession_, 
                                                IdentificadorBean param_, String entidad)
    throws Exception{
       
        NotificacionBean salida = null;
        
        NotificacionesDatos bd = new NotificacionesDatos();
        
        Notificaciones valores = bd.getNotificaciones(NotificacionesDatos.SEARCH,param_, entidad);
        
        // cogemos el primero porque, entre otras cosas, deberia solo haber uno
        if (valores.count() > 0 ){
            salida = parseaNotificacionANotificacionBean(valores.get(0), entidad);
        }
          
        return salida;
    }
    
    public static NotificacionBean detalleNotificacionByNotiId (String idSession_, 
            String notiId_, String entidad)
		throws Exception{
		
		NotificacionBean salida = null;
		
		NotificacionesDatos bd = new NotificacionesDatos();
		bd.setNotiId(notiId_);
		
		bd.load(entidad);
		
		// cogemos el primero porque, entre otras cosas, deberia solo haber uno
		salida = parseaNotificacionANotificacionBean(bd, entidad);
		//EstadosDatos bdEstados=new EstadosDatos();
		return salida;
}
    
  /**
     * Obtiene una lista de notificaciones que cumplen unos determinados criterios de 
     *    busqueda. Para que tenga en cuenta un criterio de busueda solo hace falta inicializarlo
     * en caso de no ser inicializado no se tendra en cuenta a la hora de buscar.
     * 
     * Si el proceso de notificacion en sisnot produce un fallo no controlado se eleva
     * una excepcion de tipo ServicioWebExcepcion
     * 
     * 
     * 
     * @param idSession_ String Identificador de la sesion
     * @param nuevo_ EstadoBean Datos de la notificacion necesarios para poder identificar el estado
     * @return DetalleEstadoBean que indica el estado de la notificacion
     */   
    public static ResultadosBusquedaBean consultarNotificaciones (String idSession_, 
                                                           CriteriosBusquedaNotiBean parametros_,
                                                           boolean conDetalle_, String entidad){
        ResultadosBusquedaBean salida = new ResultadosBusquedaBean();
        
        NotificacionesDatos bd = new NotificacionesDatos();
        try{
            Notificaciones valores = bd.getNotificaciones(NotificacionesDatos.SEARCH,parametros_, entidad);
            salida.setError(SIN_ERROR_BUSQUEDA);
            salida.setDetalle(null);
            salida.setDescripcion(null);
            
            Notificacion actual;
            ResultadoBusquedaBean parteSalida;
            
            for (int i = 0; i < valores.count() ; i++){
                actual = valores.get(i);
                parteSalida = new ResultadoBusquedaBean();                
                parteSalida.setIdentificadorNotificacion(actual.getId());
                NotificacionBean detalle = null;
                
                // si hay detalle entonces tenemos que inicializar
                // la notificacion y ponerle todos los valores que hemos
                // obtenido de la consulta
                if (conDetalle_)
                    detalle = parseaNotificacionANotificacionBean(actual, entidad);
                                
                parteSalida.setDetalle(detalle);
                salida.add(parteSalida);
            }
            
        }catch (Exception e){
            salida.setError(CON_ERROR_BUSQUEDA);
            salida.setDetalle(e.toString());
            salida.setDescripcion(MENSAJE_FALLO_BUSQUEDA_BD);
            logger.debug(e);
        }
        
        
        
        return salida;
    }
     /**
     * Obtiene el estado de una notifiocacion .
     * 
     * Si el proceso de notificacion en sisnot produce un fallo no controlado se eleva
     * una excepcion de tipo ServicioWebExcepcion
     * 
     * @param idSession_ String Identificador de la sesion
     * @param nuevo_ EstadoBean Datos de la notificacion necesarios para poder identificar el estado
     * @return DetalleEstadoBean que indica el estado de la notificacion
     */
    public static DetalleEstadoBean obtenerEstado(String idNotificacion,String idEntidad)
    throws ServicioWebExcepcion{
        
    	NotificacionesDatos bd=new NotificacionesDatos();
    	bd.setNotiId(idNotificacion);
    	//bd.setId(idNotificacion);
    	try{
    			bd.load(idEntidad);
    	}catch(Exception e){
    		logger.debug("Se ha producido un error "+e);
    		return null;
    	}
        
    	ConectorNotificacion conector=NotificacionesConfig.getConector(bd.getSistemaId(),idEntidad);
    	if(conector==null) return null;
    	DetalleEstadoBean estado=conector.obtenerEstado(bd,idEntidad);
    	return estado;
    }
    
    /**
     * Obtiene  .
     * 
     * Si el proceso de notificacion en sisnot produce un fallo no controlado se eleva
     * una excepcion de tipo ServicioWebExcepcion
     * 
     * @param idSession_ String Identificador de la sesion
     * @param nuevo_ EstadoBean Datos de la notificacion necesarios para poder identificar el estado
     * @return DetalleEstadoBean que indica el estado de la notificacion
     */
    public static DetalleEstadoBDBean obtenerEstadoBD(Integer idEstado, String entidad)
    throws SgmException{
        
        EstadosDatos estado = new EstadosDatos();
    	DetalleEstadoBDBean salida = new DetalleEstadoBDBean();
        try { 
        	
        	estado.setId(idEstado.toString());
        	estado.load(entidad);
        	
        	salida.setId(estado.getId());
        	salida.setIdSisnot(estado.getIdSisnot());
        	salida.setDescripcion(estado.getDescripcion());
        } catch(Exception ex) {
             //if (isDebugeable)
                logger.debug("Fallo inesperado llamando al servicio: " + ex.getMessage());
            throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_UNKNOW_ERROR,ex);
        }

        
        return salida;
    }
    
    //seleccion implicita del conector a utilizar segun los campos rellenos en la notificacion
    //en caso de estar rellenos la DEU y el movil se usa la DEU
    private static ConectorNotificacion getConectorString(NotificacionBean notificacion,String entidad){
    	if(!StringUtils.isEmpty(notificacion.getDeu())){
    		notificacion.setSistemaId(ConstantesNotificacion.ID_SISTEMA_SISNOT);
    	}else if(!StringUtils.isEmpty(notificacion.getMovil())){
    		notificacion.setSistemaId(ConstantesNotificacion.ID_SISTEMA_SMS);
    	}
    	return NotificacionesConfig.getConector(notificacion.getSistemaId(),entidad);
    }
   
    /**
     * Crea una notifiocacion relacionada con un expediente y un destinatario.
     * El proceso tiene dos partes:
     * - Dar de alta la notifiacion en sisnot llamando al metodo crearNotificacion del
     *   servicio web servicioNotificacionesEndPointPort.
     * - Guardar una representacion local de la notificacion en la base de datos
     *   local
     * 
     * Si el proceso de notificacion en sisnot produce un fallo no controlado se eleva
     * una excepcion de tipo ServicioWebExcepcion
     * 
     * @param idSession_ String Identificador de la sesion
     * @param nuevo_ NotificacionBean Datos de la notificacion
     * @return IdentificadorBean Identificador de la notificacion en sisnot
     */
    public static IdentificadorBean altaNotificacion(String idSession_, NotificacionBean nuevo_, String entidad)
    throws ServicioWebExcepcion, DatosIncorrectosExcepcion{
          
        String idNotificacionExternoConector = null;
        NotificacionesDatos bd = new NotificacionesDatos();
        
        // el nif y el expediente siempre han de estar presente
        if (StringUtils.isEmpty(nuevo_.getNumeroExpediente()) || StringUtils.isEmpty(nuevo_.getNifDest()) )
            throw new DatosIncorrectosExcepcion(DatosIncorrectosErrorCodigos.EC_UNKNOW_DATA);
        
        ConectorNotificacion conector=getConectorString(nuevo_,entidad);
        ResultadoAltaNotificacion resultado=null;
        
        if(conector!=null){
	        try { 
	        	resultado=conector.crearNotificacion(nuevo_, entidad, idSession_);
	        } catch(java.rmi.RemoteException ex) {
	             //if (isDebugeable)
	               logger.debug("fallo la comunicacion con el servicio: " + ex.getMessage());
	             throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_CONNECT_FAILD,ex);
	        } catch(Exception ex) {
	             //if (isDebugeable)
	                logger.debug("Fallo inesperado llamando al servicio: " + ex.getMessage());
	            throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_UNKNOW_ERROR,ex);
	        }
    	}
	        
            if (resultado==null){
            	bd.setError(MENSAJE_FALLO_INSERTAR);
            	bd.setEstado(VALOR_ESTADO_FALLO);
            }else if(StringUtils.isEmpty(resultado.getIdNotificacionSistemaExterno())){ 
                 bd.setError(resultado.getErrorSistemaExterno());
                 bd.setEstado(VALOR_ESTADO_ERROR_DEVUELTO_CONECTOR);
            }else{            
            	 bd.setEstado(conector.getEstadoNotificacionCreada());  
            	 idNotificacionExternoConector=resultado.getIdNotificacionSistemaExterno();
            }
            
            bd.setId(idNotificacionExternoConector);
            bd.setNotiId(new Guid().toString());
            bd.setFechaRegistroAnterior(nuevo_.getFechaRegistro());            
            bd.setFechaEfectuadaEntrega(nuevo_.getFechaEntrega());            
            bd.setNifDestinatario(nuevo_.getNifDest());
            bd.setNumeroExpediente(nuevo_.getNumeroExpediente());
            bd.setProcedimientoExpedienteAnterior(nuevo_.getProcedimiento());
            bd.setRegistroSalida(nuevo_.getNumeroRegistro());
            //bd.setUsuario(entrada.getIdUsuario());
            bd.setUsuario(nuevo_.getNifDest());
            bd.setTipoCorrespondencia(nuevo_.getTipoCorrespondencia());
            bd.setOrganismo(nuevo_.getOrganismo());
            bd.setAsunto(nuevo_.getAsunto());
            bd.setTipo(nuevo_.getTipo());
            bd.setTexto(nuevo_.getTexto());
            bd.setNombreDestinatario(nuevo_.getNombreDest());
            bd.setApellidosDestinatario(nuevo_.getApellidosDest());
            bd.setCorreoDestinatario(nuevo_.getCorreoDest());
            bd.setDEU(nuevo_.getDeu());
            bd.setIdioma(nuevo_.getIdioma());
            bd.setTipoVia(nuevo_.getTipoViaDireccion());
            bd.setNombreVia(nuevo_.getViaDireccion());
            bd.setNumeroVia(nuevo_.getNumeroDireccion());
            bd.setEscaleraVia(nuevo_.getEscaleraDireccion());
            bd.setPisoVia(nuevo_.getPisoDireccion());
            bd.setPuertaVia(nuevo_.getPuertaDireccion());
            bd.setTelefono(nuevo_.getTelefono());
            bd.setMunicipio(nuevo_.getMunicipio());
            bd.setProvincia(nuevo_.getProvincia());
            bd.setCodigoPostal(nuevo_.getCodigoPostal());
            bd.setDescripcionEstado(nuevo_.getDescripcionEstado());
            bd.setFechaActualizacionEstado(nuevo_.getFechaActualiEstado());
            bd.setSistemaId(nuevo_.getSistemaId());
            bd.setMovil(nuevo_.getMovil());
            
            try{
                // ya la podemos incluir en nuestra base datos local
                bd.add(entidad);     
            
                // ahora los documentos
                if (nuevo_.documentoCount() > 0){
                
                    DocumentosDatos bdDoc = new DocumentosDatos();
                
                    bdDoc.setCodigo(nuevo_.getNameDocumento(0));               
                    bdDoc.setExpediente(nuevo_.getNumeroExpediente());
                    bdDoc.setNifDestinatario(nuevo_.getNifDest());                
              
                    bdDoc.setGuid(new ieci.tecdoc.sgm.rde.ContenedorDocumentosManager().storeDocument(idSession_,nuevo_.getDataDocumento(0),nuevo_.getExtDocumento(0), entidad));
                    bdDoc.setNotiId(bd.getNotiId());
                    // y lo añadimos a la base de dato
                    bdDoc.add(entidad);
                }            
            } catch(Exception ex) {
                //if (isDebugeable)
                    logger.debug("Fallo inesperado al dar de alta la notificacion: " + ex.getMessage());
                throw new ServicioWebExcepcion(ServicioWebErrorCodigos.EC_UNKNOW_ERROR,ex);
            }    
       
        if(StringUtils.isEmpty(idNotificacionExternoConector)) return null;
        return new IdentificadorBean(bd.getNotiId()); 
    }
}
