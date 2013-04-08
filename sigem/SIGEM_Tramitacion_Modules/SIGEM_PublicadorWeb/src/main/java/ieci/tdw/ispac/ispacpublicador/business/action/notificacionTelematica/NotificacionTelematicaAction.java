package ieci.tdw.ispac.ispacpublicador.business.action.notificacionTelematica;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.common.constants.NotifyStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.gendoc.converter.DocumentConverter;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.notificaciones.IdentificadorNotificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.core.services.terceros.ServicioTerceros;
import ieci.tecdoc.sgm.core.services.terceros.dto.Tercero;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * <p>
 * Acción para la Notificación Telemática.
 * </p>
 * 
 * <p>
 * Se deben especificar los siguientes atributos en el XML de configuración de la regla
 * de publicación:
 * <ul>
 * <li>ASUNTO_EMAIL: Asunto del correo electrónico de la notificación.</li>
 * <li>TEXTO_EMAIL: Texto del correo electrónico de la notificación.</li>
 * <li>DESCRIPCION_NOTIFICACION: Descripción de la notificación que aparecerá en la aplicación Consulta de Expedientes.</li>
 * </ul>
 * </p>
 * <p>
 * Este es un ejemplo del XML de configuración de la regla de publicación: 
 * <pre>
 * <?xml version='1.0' encoding='ISO-8859-1'?>
 * <attributes>
 * 	<attribute name='ASUNTO_EMAIL'>
 * 		<labels>
 * 			<label locale='es'>Asunto correo electrónico</label>
 * 			<label locale='eu'>Asunto correo electrónico</label>
 * 			<label locale='ca'>Asunto correo electrónico</label>
 * 			<label locale='gl'>Asunto correo electrónico</label>
 * 		</labels>
 * 	</attribute>
 * 	<attribute name='TEXTO_EMAIL'>
 * 		<labels>
 * 			<label locale='es'>Texto correo electrónico</label>
 * 			<label locale='eu'>Texto correo electrónico</label>
 * 			<label locale='ca'>Texto correo electrónico</label>
 * 			<label locale='gl'>Texto correo electrónico</label>
 * 		</labels>
 * 	</attribute>
 * 	<attribute name='DESCRIPCION_NOTIFICACION'>
 * 		<labels>
 * 			<label locale='es'>Descripción de la notificación</label>
 * 			<label locale='eu'>Descripción de la notificación</label>
 * 			<label locale='ca'>Descripción de la notificación</label>
 * 			<label locale='gl'>Descripción de la notificación</label>
 * 		</labels>
 * 	</attribute>
 * </attributes>
 * </pre>
 * </p>
 * 
 * <p>
 * A la hora de componer los textos, se pueden usar los siguientes parámetros:
 * <ul>
 * <li>NUMEXP: Número de expediente.</li>
 * <li>CODPROCEDIMIENTO: Código del procedimiento.</li>
 * <li>NOMBREPROCEDIMIENTO: Nombre del procedimiento.</li>
 * <li>NREG: Número de registro del expediente.</li>
 * <li>FREG: Fecha de registro del expediente.</li>
 * <li>ASUNTO: Asunto del expediente.</li>
 * <li>FAPERTURA: Fecha de apertura del expediente.</li>
 * <li>NOMBRE_DOC: Nombre del tipo de documento.</li>
 * <li>DESCRIPCION_DOC: Nombre de la plantilla del documento.</li>
 * <li>TITULO_DOC: Título del documento: plantilla o tipo de documento.</li>
 * <li>NREG_DOC: Número de registro de salida del documento.</li>
 * <li>FREG_DOC: Fecha de registro de salida del documento.</li>
 * <li>ORIGEN_DOC: Origen del documento.</li>
 * <li>DESTINO_DOC: Destino del documento.</li>
 * <li>FNOTIFICACION_DOC: Fecha de notificación del documento.</li>
 * <li>NDOC_DEST: NIF/CIF del destinatario.</li>
 * <li>NOMBRE_DEST: Identificación del destinatario.</li>
 * <li>DIRNOT_DEST: Domicilio del destinatario.</li>
 * <li>DEU_DEST: DEU del destinatario.</li>
 * </ul>
 * </p>
 *  
 */
public class NotificacionTelematicaAction extends SigemBaseAction {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(NotificacionTelematicaAction.class);

	/** Logger de la clase. */
    private static final Logger NOTIFICACION_TELEMATICA = 
    	Logger.getLogger("NOTIFICACION_TELEMATICA");
    
    /** 
     * Propiedades de la entidad DESTINATARIO. 
     */
    private static final Properties DEST_PROPS = new Properties();
    static {
		int ordinal = 0;
	    DEST_PROPS.add( new Property(ordinal++, "ID", Types.INTEGER));
	    DEST_PROPS.add( new Property(ordinal++, "NDOC", Types.VARCHAR));
	    DEST_PROPS.add( new Property(ordinal++, "NOMBRE", Types.VARCHAR));
	    DEST_PROPS.add( new Property(ordinal++, "DIRNOT", Types.VARCHAR));
	    DEST_PROPS.add( new Property(ordinal++, "DEU", Types.VARCHAR));
	    DEST_PROPS.add( new Property(ordinal++, "MOVIL", Types.VARCHAR));
	    DEST_PROPS.add( new Property(ordinal++, "EMAIL", Types.VARCHAR));
    }

    /** Contexto del cliente. */
    private ClientContext clientContext = null;
    
    
    /**
     * Constructor.
     * 
     */
    public NotificacionTelematicaAction() {
    	super();
    	clientContext = new ClientContext();
    	clientContext.setAPI(new InvesflowAPI(clientContext));
    }
    
    /**
     * Ejecuta la acción.
     * @param rctx Contexto de ejecución de la regla
     * @param attContext Atributos con información extra, utilizados dentro de 
     * la ejecución de la regla.
     * @return true si la ejecución termina correctamente, false en caso 
     * contrario.
     * @throws ActionException si ocurre algún error.
     */
    public boolean execute(RuleContext rctx, AttributeContext attContext) 
    		throws ActionException {
    	
        if (logger.isInfoEnabled()) {
            logger.info("Acción [" + this.getClass().getName() + "] en ejecución");
        }

        IItem exp = null;
        IItem doc = null;
        IItem dest = null;
        
        try {
        	
        	// Identificador del documento a notificar
        	String strDocId = (String) rctx.getProperties().get("id_documento");
        	int docId = TypeConverter.parseInt(strDocId, -1);
        	if (docId == -1) {
    			throw new ActionException(
    					"Identificador de documento no válido: " + strDocId);
        	}

    		// Contexto de tramitación
    		IEntitiesAPI entitiesAPI = 
    			clientContext.getAPI().getEntitiesAPI();

    		// Obtener la información del expediente
    		exp = entitiesAPI.getExpedient(rctx.getIdObjeto());
    		if (exp == null) {
    			throw new ActionException("No se ha encontrado el expediente: "
    					+ rctx.getIdObjeto());
    		}

    		// Obtener la información del documento
    		doc = entitiesAPI.getDocument(docId);
    		if (doc == null) {
    			throw new ActionException("No se ha encontrado el documento: "
    					+ docId);
    		}

    		// Obtener la información del destinatario
    		//String destId = doc.getString("DESTINO_ID");
    		dest = getDestinatario(rctx.getIdObjeto(), exp, doc);
    		if (dest == null) {
    			throw new ActionException("No se ha encontrado el destinatario ["
    					+ doc.getString("DESTINO") + "] del documento [" + docId + "].");
    		}
    		
    		// Componer los parámetros para los mensajes
    		Map messageParams = getMessageParams(exp, doc, dest);

	        // Llamar al servicio web de Notificación Telemática
    		Notificacion notificacion = createNotificacionBean(rctx, attContext, 
    				exp, doc, dest, messageParams);
    		IdentificadorNotificacion idNot=null;
	        try{ idNot = altaNotificacion(notificacion); }
	        catch(Exception e){
	        	idNot=null;
	        	logError(exp, doc, dest, e);
	        }
	        if ((idNot != null) && StringUtils.isNotBlank(idNot.getCodigoDeNotificacion())) {
		    
	        	// Guardar el identificador de la notificación
	        	doc.set("ID_NOTIFICACION", idNot.getCodigoDeNotificacion());
	        	doc.store(clientContext);
	        	
	        	// Publicar la notificación en Consulta Telemática
		        publicarNotificacion(rctx, attContext, notificacion, idNot, messageParams);
		        
		        // Log de la notificación
	    		logOk(idNot, exp, doc, dest);

	        } else {
	        	
	        	// Establecer el estado de notificación a ERROR
	        	doc.set("ID_NOTIFICACION", (String)null);
	        	doc.set("ESTADONOTIFICACION", NotifyStatesConstants.ERROR);
	        	doc.set("FNOTIFICACION", new Date());
	        	doc.store(clientContext);
	        	
	        	// Lanzar error del publicador
    			throw new ActionException(
    					"No se ha dado de alta la notificación del documento del expediente " 
    					+ notificacion.getNumeroExpediente());
	        }

        } catch (ActionException e) {
        	setInfo("Error en el alta de expediente: " + e.toString());
        	logError(exp, doc, dest, e);
        	throw e;
        } catch (Throwable e) {
        	setInfo("Error en el alta de expediente: " + e.toString());
        	logError(exp, doc, dest, e);
            throw new ActionException(e);
        }
        
        return true;
    }

    /**
     * Compone los parámetros para los mensajes de aviso.
     * @param exp Información del expediente.
     * @param doc Información del documento.
     * @param dest Información del destinatario.
     * @return Parámetros para los mensajes de aviso.
     * @throws ISPACException si ocurre algún error.
     */
    private Map getMessageParams(IItem exp, IItem doc, IItem dest) throws ISPACException {
    	Map parameters = new HashMap();
    	
		// Información del EXPEDIENTE
    	parameters.put("NUMEXP", exp.getString("NUMEXP")); // Número de expediente
    	parameters.put("CODPROCEDIMIENTO", exp.getString("CODPROCEDIMIENTO")); // Código del procedimiento
		parameters.put("NOMBREPROCEDIMIENTO", exp.getString("NOMBREPROCEDIMIENTO")); // Nombre del procedimiento
		parameters.put("NREG", exp.getString("NREG")); // Número de registro del expediente
		parameters.put("FREG", exp.getString("FREG")); // Fecha de registro del expediente
		parameters.put("ASUNTO", exp.getString("ASUNTO")); // Asunto del expediente
		parameters.put("FAPERTURA", exp.getString("FAPERTURA")); // Fecha de apertura del expediente
				
		// Información del DOCUMENTO
		parameters.put("NOMBRE_DOC", doc.getString("NOMBRE")); // Nombre del tipo de documento
		parameters.put("DESCRIPCION_DOC", doc.getString("DESCRIPCION")); // Nombre de la plantilla
		parameters.put("TITULO_DOC", getDocTitle(doc)); // Título del documento: plantilla o tipo de documento
		parameters.put("NREG_DOC", doc.getString("NREG")); // Número de registro de salida del documento
		parameters.put("FREG_DOC", doc.getString("FREG")); // Fecha de registro de salida del documento
		parameters.put("ORIGEN_DOC", doc.getString("ORIGEN")); // Origen
		parameters.put("DESTINO_DOC", doc.getString("DESTINO")); // Destino
		parameters.put("FNOTIFICACION_DOC", doc.getString("FNOTIFICACION")); // Fecha de notificación

		// Información del DESTINATARIO
		parameters.put("NDOC_DEST", dest.getString("NDOC")); // NIF/CIF del destinatario
		parameters.put("NOMBRE_DEST", dest.getString("NOMBRE")); // Identificación del destinatario
		parameters.put("DIRNOT_DEST", dest.getString("DIRNOT")); // Domicilio del destinatario
		parameters.put("MOVIL_DEST", dest.getString("MOVIL")); //Movil
		parameters.put("DEU_DEST", dest.getString("DEU"));// DEU del destinatario
	
		    	
		return parameters;
    }

    /**
     * Obtiene el mensaje de log.
     * @param exp Información del expediente.
     * @param doc Información del documento.
     * @param dest Información del destinatario.
     * @return Mensaje de log.
     */
    private static String getLogMessage(IItem exp, IItem doc, IItem dest) {

    	StringBuffer msg = new StringBuffer();
        
        if (exp != null) {
        	try {
				msg.append("NumExp=[")
					.append(exp.getString("NUMEXP"))
					.append("] ");
			} catch (ISPACException e) {}
        }
        
        if (dest != null) {
        	try {
				msg.append("NIFCIFInt=[")
					.append(dest.getString("NDOC"))
					.append("] ");
			} catch (ISPACException e) {}

        	try {
				msg.append("NombreInt=[")
					.append(dest.getString("NOMBRE"))
					.append("] ");
			} catch (ISPACException e) {}

        	try {
				msg.append("DirInt=[")
					.append(dest.getString("DIRNOT"))
					.append("] ");
			} catch (ISPACException e) {}
        }
        	
        if (doc != null) {
        	try {
				msg.append("NombreDoc=[")
					.append(doc.getString("NOMBRE"))
					.append("] ");
			} catch (ISPACException e) {}

        	try {
				msg.append("GUID=[")
					.append(doc.getString("INFOPAG"))
					.append("] ");
			} catch (ISPACException e) {}
        }
        
        return msg.toString();
    }
    
    /**
     * Muestra un log del resultado de la acción.
     * @param idNot Identificador de la notificación creada.
     * @param exp Información del expediente.
     * @param doc Información del documento.
     * @param dest Información del destinatario.
     */
    private static void logOk(IdentificadorNotificacion idNot, IItem exp, 
    		IItem doc, IItem dest) {
    	
    	if (NOTIFICACION_TELEMATICA.isInfoEnabled()) {
    		
    		StringBuffer msg = new StringBuffer("Notificación Telemática ");
    		
    		if (idNot != null) {
    			msg.append("(")
    				.append(idNot.getCodigoDeNotificacion())
    				.append(")");
    		}
    		
    		msg.append(": ").append(getLogMessage(exp, doc, dest));
    		
	        // Log del resultado de la acción
	        NOTIFICACION_TELEMATICA.info(msg.toString());
    	}
    }

    /**
     * Muestra un log del error producido.
     * @param exp Información del expediente.
     * @param doc Información del documento.
     * @param dest Información del destinatario.
     * @param e Excepción capturada.
     */
    private static void logError(IItem exp, IItem doc, IItem dest, 
    		Throwable e) {

        // Log del error producido
        NOTIFICACION_TELEMATICA.error("Error en la acción " 
        		+ NotificacionTelematicaAction.class.getName() + ": " 
        		+ getLogMessage(exp, doc, dest), e);
    }

    private IItem getDestinatario(String numExp, IItem expediente,
    		IItem doc) throws ISPACException {
    	
    	IItem destinatario = null;
    	
    	String destId= doc.getString("DESTINO_ID");
    	String destName=doc.getString("DESTINO");
        if (expediente != null) {
        	
	        // Datos del interesado principal
	        String id = expediente.getString("IDTITULAR");
	        if ((StringUtils.isNotBlank(id) && id.equals(destId) && !"0".equals(destId))
	        		|| ((StringUtils.isBlank(id) || "0".equals(id))
	        				&& (StringUtils.isBlank(destId) || "0".equals(destId))
	        				&& StringUtils.equals(expediente.getString("IDENTIDADTITULAR"), destName))) {
	        	
	            destinatario = new GenericItem(DEST_PROPS, "ID");
	        	destinatario.set("ID", id);
	        	destinatario.set("NDOC", 
	        			expediente.getString("NIFCIFTITULAR"));
	        	destinatario.set("NOMBRE", 
	        			expediente.getString("IDENTIDADTITULAR"));
	        	destinatario.set("DIRNOT", 
	        			expediente.getString("DOMICILIO"));
//	        	destinatario.set("DEU", expediente.getString("DIRECCIONTELEMATICA"));
//	        	destinatario.set("MOVIL", expediente.getString("TFNOMOVIL"));
//	        	destinatario.set("EMAIL", expediente.getString("DIRECCIONTELEMATICA"));
	        	
	        	// En la dirección telemática podemos tener un móvil o un DEU.
	        	String direccionTelematica = expediente.getString("DIRECCIONTELEMATICA");
	        	if (isPhone(direccionTelematica)) {
	        		destinatario.set("MOVIL", direccionTelematica);
	        	} else {
	        		destinatario.set("DEU", direccionTelematica);
	        	}
	        }
        }

        if (destinatario == null) {
        	
        	String sql = null;
        	if (StringUtils.isNotBlank(destId) && !"0".equals(destId)) {
        		sql ="ID_EXT = '" + DBUtil.replaceQuotes(destId) + "'";
        	} else  {
        		sql ="(ID_EXT = '0' OR ID_EXT IS NULL) AND NOMBRE='"+DBUtil.replaceQuotes(destName)+"'";
        	}
        	
        	IEntitiesAPI entitiesAPI = clientContext.getAPI().getEntitiesAPI();
        	IItemCollection destinatarios = entitiesAPI.getParticipants(numExp, sql, null);
        	if (destinatarios.next()) {
        		IItem dest = destinatarios.value();
        		
        		destinatario = new GenericItem(DEST_PROPS, "ID");
	        	destinatario.set("ID", destId);
	        	destinatario.set("NDOC", dest.getString("NDOC"));
	        	destinatario.set("NOMBRE", dest.getString("NOMBRE"));
	        	destinatario.set("DIRNOT",  dest.getString("DIRNOT"));
//	        	destinatario.set("DEU", dest.getString("DIRECCIONTELEMATICA"));
//	        	destinatario.set("MOVIL",  dest.getString("TFNO_MOVIL"));
//	        	destinatario.set("EMAIL", dest.getString("EMAIL"));

	        	// En la dirección telemática podemos tener un móvil o un DEU.
	        	String direccionTelematica = expediente.getString("DIRECCIONTELEMATICA");
	        	if (isPhone(direccionTelematica)) {
	        		destinatario.set("MOVIL", direccionTelematica);
	        	} else {
	        		destinatario.set("DEU", direccionTelematica);
	        	}
        	}
        }
    	return destinatario;
    }
    private Notificacion createNotificacionBean(RuleContext rctx, AttributeContext attContext,
    		IItem exp, IItem doc, IItem dest, Map msgParams) throws ISPACException {

        Notificacion notification = new Notificacion();
        
        notification.setCodigoNoti(new Guid().toString()); // Identificador de la notificación
        
        notification.setNumeroExpediente(exp.getString("NUMEXP")); // Número del expediente
	    notification.setProcedimiento(exp.getString("CODPROCEDIMIENTO")); // Código del Procedimiento
	    notification.setOrganismo(getEntidad().getNombre());
	    
	    /*
	    	SISNOT no soporta la longitud del número de registro de SIGEM
	     */
        //notification.setNumeroRegistro(doc.getString("NREG")); // Número de registro
        notification.setFechaRegistro(doc.getDate("FREG")); // Fecha del registro
        
        notification.setFechaEntrega(new Date()); //Fecha de entrega
	    notification.setNifDest(dest.getString("NDOC")); // NIF del destinatario
	    
	    // Asunto del mensaje de correo electrónico
//	    notification.setAsunto(Messages.getString("notificacion.asunto", 
//	    		msgParams)); 
	    String asuntoEmail = (String) attContext.getProperties().get("ASUNTO_EMAIL");
	    if (StringUtils.isNotBlank(asuntoEmail)) {
	    	notification.setAsunto(substituteParams(asuntoEmail, msgParams));
	    }
	    
	    // Dirección telematica (DEU)
	    notification.setCorreoDest(dest.getString("EMAIL"));
	    notification.setDeu(dest.getString("DEU")); 
	    notification.setMovil(dest.getString("MOVIL"));
	    
	    // Texto del mensaje de correo electrónico
//		notification.setTexto(Messages.getString("notificacion.texto",
//				msgParams));
	    String textoEmail = (String) attContext.getProperties().get("TEXTO_EMAIL");
	    if (StringUtils.isNotBlank(textoEmail)) {
	    	notification.setTexto(substituteParams(textoEmail, msgParams));
	    }
		
	    rellenarDatosDestinatario(dest,notification);
	    
	    //notification.setUsuario(notification.getNifDest());
		notification.setUsuario(notification.getCorreoDest());
		notification.setNumeroRegistro(null); // Número de registro

		// Añadir la información del documento
		addDocumentInfo(notification, doc);

		return notification;
    }
    
    private void rellenarDatosDestinatario(IItem dest,Notificacion notificacion){
    	String idTercero=null;
    	try{
    		idTercero=dest.getString("ID");
    		if(idTercero==null) return; 
    		Tercero persona=getTercero(idTercero);
    		if(persona!=null){
    			notificacion.setNombreDest(persona.getNombre());
    	    	notificacion.setApellidosDest(persona.getApellidos());
    	    	notificacion.setTipo(persona.getTipoPersona());
    	    	
    	    	if(persona.getDireccionesPostales()!=null && 
    	    	   persona.getDireccionesPostales().length>0 && persona.getDireccionesPostales()[0]!=null){
    	    		//notificacion.setTipoViaDireccion(persona.getDireccionesPostales()[0].getTipoVia());
    		    	notificacion.setViaDireccion(persona.getDireccionesPostales()[0].getDireccionPostal());
    		    	//notificacion.setViaDireccion(persona.getDireccionesPostales()[0].getVia());
    		    	//notificacion.setNumeroDireccion(persona.getDireccionesPostales()[0].getBloque());
    		    	//notificacion.setPisoDireccion(persona.getDireccionesPostales()[0].getPiso());
    		    	//notificacion.setEscaleraDireccion(persona.getDireccionesPostales()[0].getBloque());
    		    	//notificacion.setPuertaDireccion(persona.getDireccionesPostales()[0].getPuerta());
    		    	notificacion.setCodigoPostal(persona.getDireccionesPostales()[0].getCodigoPostal());
    		    	notificacion.setTelefono(persona.getDireccionesPostales()[0].getTelefono());
    		    	notificacion.setProvincia(persona.getDireccionesPostales()[0].getProvincia());
    		    	notificacion.setMunicipio(persona.getDireccionesPostales()[0].getMunicipio());	
    	    	}
    		}
    	}catch(ISPACException e){
    		logger.warn("Error al tratar de recuperar la información del tercero"+idTercero,e);
    	}
		
    	
    }
    
    private void addDocumentInfo(Notificacion notification, IItem doc) 
    		throws ISPACException {

    	// Identificador del documento
    	String docId = doc.getString("ID");
		
		// Obtener el GUID del documento
		String guid = doc.getString("INFOPAG");
		if (StringUtils.isBlank(guid)) {
			throw new ActionException("El documento [" + docId 
					+ "] no tiene GUID asignado.");
		}
		notification.setGuid(CollectionUtils.createList(guid));

		// Nombre del documento
		String nombreDoc = getDocTitle(doc);
		if ((nombreDoc != null) && (nombreDoc.length() > 8) ) { 
			nombreDoc = nombreDoc.substring(0,6) + "~1.pdf";
		}
		notification.setNombreDocumentos(CollectionUtils.createList(nombreDoc));

		// Extensión del documento
    	String docExt = getDocExt(guid);
    	notification.setExtension(CollectionUtils.createList(docExt));
    	
    	// Contenido del documento
    	byte [] docContent = null;
    	if ("pdf".equalsIgnoreCase(docExt)) {
    		docContent = getDocContent(guid);
    	} else {
    		
    		// Convertir el documento a pdf
    		String docFilePath = DocumentConverter.convert2PDF(clientContext.getAPI(), guid, docExt);
    		
    		// Obtener la información del fichero convertido
    		File docFile = new File(docFilePath);
    		if (!docFile.exists()) {
    			throw new ActionException(
    					"No se ha podido convertir el documento a PDF");
    		}
    		
    		// Actualizar el valor de la extensión
    		docExt = "pdf";
    		
    		// Obtener el contenido del fichero PDF
			docContent = FileUtils.retrieveFile(docFile);
			
			// Eliminar el fichero PDF
			docFile.delete();
    	}
		notification.setDocumentos(CollectionUtils.createList(docContent));

    }
    
    private static String getDocTitle(IItem doc) throws ISPACException {
		String nombre = doc.getString("NOMBRE");
		String descripcion = doc.getString("DESCRIPCION");
		return (StringUtils.isNotBlank(descripcion) ? descripcion : nombre);
	}

    private String getDocExt(String guid) throws ISPACException {

		String ext = null;

		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();

		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
			String mimeType = genDocAPI.getMimeType(connectorSession, guid);
			if (StringUtils.isNotBlank(mimeType)) {
				ext = MimetypeMapping.getExtension(mimeType);
			}
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}		        	

		return ext;
	}

	private byte[] getDocContent(String guid)
			throws ISPACException {

		IGenDocAPI genDocAPI = clientContext.getAPI().getGenDocAPI();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
			genDocAPI.getDocument(connectorSession, guid, out);
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}		        	

		return out.toByteArray();
	}
	
    private IdentificadorNotificacion altaNotificacion(
    		Notificacion notificacion) throws Exception {

        // Llamada al API de Notificación Telemática
   		ServicioNotificaciones notificaciones = 
   			LocalizadorServicios.getServicioNotificaciones();
   		IdentificadorNotificacion idNot = 
   			notificaciones.altaNotificacion(notificacion, getEntidad());
    
        return idNot;
    }
    
    private Tercero getTercero(String id)  {
        // Llamada al API de Terceros
    	try{
	        ServicioTerceros terceros = 
				LocalizadorServicios.getServicioTerceros();
	        Tercero persona = 
	        	terceros.lookupById(getEntidad().getIdentificador(), id);
	        return persona;
    	}catch(Exception e){
    		logger.debug(e); return null;
    	} 
    }
    
    private void publicarNotificacion(RuleContext rctx, AttributeContext attContext,
    		Notificacion notificacion, IdentificadorNotificacion idNot, Map msgParams) 
    			throws Exception {

        // API de Consulta Telemática
        ServicioConsultaExpedientes consulta = 
        	LocalizadorServicios.getServicioConsultaExpedientes();

        // Información del hito actual del expediente
        HitoExpediente hito = consulta.obtenerHitoEstado(
        		notificacion.getNumeroExpediente(), getEntidad());
        
        // Componer la información de la notificacion
		ieci.tecdoc.sgm.core.services.consulta.Notificacion ctNot = 
			new ieci.tecdoc.sgm.core.services.consulta.Notificacion();
//	    ctNot.setNotificacionId(new Guid().toString());
	    ctNot.setFechaNotificacion(TypeConverter.toString(
	    		notificacion.getFechaEntrega(), ConstantesServicios.DATE_PATTERN));
	    //ctNot.setDEU(notificacion.getCorreoDest());
	    ctNot.setDEU(notificacion.getDeu());
	    ctNot.setNotificacionId(idNot.getCodigoDeNotificacion());
	    ctNot.setExpediente(notificacion.getNumeroExpediente());
	    
	    // Descripción de la notificación
	    String descripcion = (String) attContext.getProperties().get("DESCRIPCION_NOTIFICACION");
	    if (StringUtils.isNotBlank(descripcion)) {
	    	ctNot.setDescripcion(substituteParams(descripcion, msgParams));
	    }
	    
	    ctNot.setHitoId(hito.getGuid());

		// Publicar la notificación
        consulta.altaNotificacion(ctNot, getEntidad());
    }
    
    private static boolean isPhone(String value) {
    	
    	if (StringUtils.isNotBlank(value) 
    			&& (StringUtils.isNumeric(value) 
    					|| ((value.length() > 1) 
    							&& value.startsWith("+") 
    							&& StringUtils.isNumeric(value.substring(1))))) {
            return true;
    	}
    	
    	return false;
    }
}