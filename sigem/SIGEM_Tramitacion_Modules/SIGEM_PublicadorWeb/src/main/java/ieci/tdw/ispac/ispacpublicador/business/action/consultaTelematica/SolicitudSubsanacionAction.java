package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacpublicador.business.action.SigemBaseAction;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.TasaVO;
import ieci.tdw.ispac.ispacpublicador.business.attribute.AttributeContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.Pago;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.consulta.SolicitudAportacionDocumentacion;
import ieci.tecdoc.sgm.core.services.consulta.Subsanacion;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

/**
 * <p>
 * Acción para crear una solicitud de subsanación en la Consulta Telemática.
 * </p>
 * 
 * <p>
 * Se deben especificar los siguientes atributos en el XML de configuración de la regla
 * de publicación:
 * <ul>
 * <li>MENSAJE_SUBSANACION: Mensaje de subsanación que aparecerá en la aplicación Consulta de Expedientes.</li>
 * <li>MENSAJE_PAGO: Mensaje de pago que aparecerá en la aplicación Consulta de Expedientes.</li>
 * <li>TASA_[id]: Información de la tasa de pago.</li>
 * </ul>
 * </p>
 * <p>
 * Este es un ejemplo del XML de configuración de la regla de publicación: 
 * <pre>
 * <?xml version='1.0' encoding='ISO-8859-1'?>
 * <attributes>
 * 	<attribute name='TASA_1'>
 * 		<tax>
 * 			<name>Resguardo del pago de tasa</name>
 * 			<labels>
 * 				<label locale='es'>Resguardo del pago de tasa</label>
 * 				<label locale='eu'>Tasaren ordainagiria</label>
 * 				<label locale='ca'>Protegeixo del pagament de taxa</label>
 * 				<label locale='gl'>Resgardo do pago de taxa</label>
 * 			</labels>
 * 			<import>1000</import>
 * 			<sender_entity_id>000000</sender_entity_id>
 * 			<self_settlement_id>100</self_settlement_id>
 * 		</tax>
 * 	</attribute>
 * 	<attribute name='MENSAJE_SUBSANACION'>
 * 		<labels>
 * 			<label locale='es'>Se le ha notificado la necesidad de realizar una subsanación de la documentación aportada al expediente ${NUMEXP} del procedimiento ${NOMBREPROCEDIMIENTO}.</label>
 * 			<label locale='eu'>${NOMBREPROCEDIMIENTO} prozeduraren ${NUMEXP} espedientean aurkeztutako dokumentazioa zuzentzeko beharra dagoela jakinarazi zaizu.</label>
 * 			<label locale='ca'>Se us ha notificat la necessitat d\u0027esmenar la documentacio aportada a l\u0027expedient ${NUMEXP} del procediment ${NOMBREPROCEDIMIENTO}.</label>
 * 			<label locale='gl'>Notificouselle a necesidade de realizar unha reparación da documentación achegada ao expediente ${NUMEXP} do procedemento ${NOMBREPROCEDIMIENTO}.</label>
 * 		</labels>
 * 	</attribute>
 * 	<attribute name='MENSAJE_PAGO'>
 * 		<labels>
 * 			<label locale='es'>Durante la tramitación de su expediente ${NUMEXP} de ${NOMBREPROCEDIMIENTO} se le comunica que es necesario que acredite el pago de la tasa de ${NOMBRE_PAGO} por un valor de ${IMPORTE_PAGO} euros.</label>
 * 			<label locale='eu'>${NOMBREPROCEDIMIENTO}(e)ko ${NUMEXP} espedientea bideratzen ari dela, jakinarazten zaizu ${NOMBRE_PAGO}ren tasa (${IMPORTE_PAGO} eurokoa) ordaindu izana ziurtatu behar duzula.</label>
 * 			<label locale='ca'>Durant la tramitacio del vostre expedient ${NUMEXP} de ${NOMBREPROCEDIMIENTO} se us comunica que acrediteu el pagament de la taxa de ${NOMBRE_PAGO} per un valor de ${IMPORTE_PAGO} euros.</label>
 * 			<label locale='gl'>Durante a tramitación do seu expediente ${NUMEXP} de ${NOMBREPROCEDIMIENTO} comunicaráselle que &eacute; necesario que acredite o pagamento da taxa de ${NOMBRE_PAGO} por un valor de ${IMPORTE_PAGO} euros.</label>
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
 * <li>NDOC_DEST: NIF/CIF del destinatario.</li>
 * <li>NOMBRE_DEST: Identificación del destinatario.</li>
 * <li>DOMICILIO_DEST: Domicilio del destinatario.</li>
 * <li>DIRECCIONTELEMATICA_DEST: Dirección telemática del destinatario.</li>
 * <li>NOMBRE_PAGO: Nombre del pago.</li>
 * <li>IMPORTE_PAGO: Importe del pago.</li>
 * </ul>
 * 
 * </p>
 * 
 */
public class SolicitudSubsanacionAction extends SigemBaseAction {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(SolicitudSubsanacionAction.class);

	/** Logger de la clase. */
    private static final Logger CONSULTA_TELEMATICA = 
    	Logger.getLogger("CONSULTA_TELEMATICA");

    /** 
     * Propiedades de la entidad DOCUMENTO. 
     */
    private static final Properties DOC_PROPS = new Properties();
    static {
		int ordinal = 0;
		DOC_PROPS.add( new Property(ordinal++, "ID", Types.VARCHAR));
		DOC_PROPS.add( new Property(ordinal++, "DOCUMENTO", Types.VARCHAR));
		DOC_PROPS.add( new Property(ordinal++, "PENDIENTE", Types.VARCHAR));
    }

    /** Contexto del cliente. */
    private ClientContext clientContext = null;

	/** Lista de solicitudes de subsanación. */
	private List solicitudesSubsanacion = new ArrayList();
	
	/** Lista de solicitudes de pago. */
	private List solicitudesPago = new ArrayList();
	
	/** Mapa de tasas definidas para la regla. */
	private Map tasas = null;

    
    /**
     * Constructor.
     * 
     */
    public SolicitudSubsanacionAction() {
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

        try {

        	// Obtener el mapa de tasas definidas para la regla
        	tasas = createTasasMap(attContext);
        	
        	// Obtener las solicitudes a partir de los documentos requeridos
	        getSolicitudes(rctx, attContext);
        	
        	// Alta de las solicitudes de subsanación
        	altaSolicitudesSubsanacion();
        	
        	// Alta de las solicitudes de pago
        	altaSolicitudesPago();
        	
        } catch (ActionException e) {
        	setInfo("Error en el alta de solicitud de subsanación para el expediente "
        			+ rctx.getIdObjeto() + ": " + e.toString());
            throw e;
        } catch (Throwable e) {
        	setInfo("Error en el alta de solicitud de subsanación para el expediente "
        			+ rctx.getIdObjeto() + ": " + e.toString());
            throw new ActionException(e);
        }
        
        return true;
    }

    private static Map createTasasMap(AttributeContext attContext) {
    	Map tasas = new HashMap();
    	
    	Map properties = attContext.getProperties();
    	if ((properties != null) && !properties.isEmpty()) {
    		String[] attNames = (String[]) properties.keySet().toArray(
    				new String[properties.size()]);
    		TasaVO tasa;
    		for (int i = 0; i < attNames.length; i++) {
    			if (attNames[i].startsWith("TASA_")) {
    				tasa = createTasa((String) properties.get(attNames[i]));
    				if (tasa != null && (StringUtils.isNotBlank(tasa.getNombre()))) {
    					tasas.put(tasa.getNombre().trim().toUpperCase(), tasa);
    				}
    			}
    		}
    	}

    	return tasas;
    }
    
    private static TasaVO createTasa(String attValue) {
    	TasaVO tasa = null;
    	
    	if (StringUtils.isNotBlank(attValue)) {
    		
    		XmlFacade xml = new XmlFacade(attValue);
    		
    		tasa = new TasaVO();
    		tasa.setNombre(xml.get("/tax/name/text()"));
    		tasa.setEtiquetas(XmlFacade.toString((Element)xml.getSingleNode("/tax/labels")));
    		tasa.setImporte(xml.get("/tax/import/text()"));
    		tasa.setIdEntidadEmisora(xml.get("/tax/sender_entity_id/text()"));
    		tasa.setIdAutoLiquidacion(xml.get("/tax/self_settlement_id/text()"));
    	}
    	
    	return tasa;
    }
    
    private IItem getExpediente(String numExp) throws ISPACException {

		// API de entidades
		IEntitiesAPI entitiesAPI = clientContext.getAPI().getEntitiesAPI();

		// Obtener la información del expediente
		IItem exp = entitiesAPI.getExpedient(numExp);
		if (exp == null) {
			throw new ActionException("No se ha encontrado el expediente: "
					+ numExp);
		}
		
		return exp;
    }
    
    private HitoExpediente getHitoExpediente(String numExp) 
    		throws SigemException, ActionException {

    	// API de acceso a Consulta Telemática
		ServicioConsultaExpedientes consulta = 
			LocalizadorServicios.getServicioConsultaExpedientes();

		// Información del hito actual del expediente en CT
        HitoExpediente hito = consulta.obtenerHitoEstado(numExp, getEntidad());
		if (hito == null) {
			throw new ActionException(
					"No existe hito actual en Consulta Telemática para el expediente " 
					+ numExp);
		}
		
		return hito;
    }
    
    private void getSolicitudes(RuleContext rctx, AttributeContext attContext)
    		throws SigemException, ISPACException {
    	
    	// Información del expediente
    	IItem exp = getExpediente(rctx.getIdObjeto());

		// Información del hito actual del expediente en CT
        HitoExpediente hito = getHitoExpediente(rctx.getIdObjeto());

        // Documentos a subsanar asociados al trámite
        IItemCollection documentos = getDocumentos(rctx);

    	// Componer las solicitudes
        IItem doc = null;
        String docName = null;
        List docNames = new ArrayList();
        String genericDocId = null;
        TasaVO tasa = null;
    	while (documentos.next()) {
    		
    		// Información del documento
    		doc = documentos.value();
    		
    		if ("SI".equalsIgnoreCase(doc.getString("PENDIENTE"))) {
    			docName = doc.getString("DOCUMENTO");
    			if (StringUtils.isNotBlank(docName)) {

    				// Comprobar si se trata de una tasa
	    			tasa = (TasaVO) tasas.get(docName.trim().toUpperCase());
	    			if (tasa != null) {
				    	solicitudesPago.add(createPago(rctx, attContext, doc, 
				    			hito.getGuid(), tasa, getMessageParams(exp, doc, tasa)));
	    			} else {
	    				
//				    	solicitudesSubsanacion.add(createSubsanacion(rctx, attContext, doc,  
//				    			hito.getGuid(), getMessageParams(exp, doc, tasa)));

	    				// Crear una única solicitud de subsanación
	    				if (genericDocId == null) {
	    					genericDocId = doc.getString("ID");
	    				}
	    				docNames.add(docName);
	    			}
    			}
    		}
    	}

    	// Crear la solicitud de subsanación
    	if (!CollectionUtils.isEmpty(docNames)) {
    		solicitudesSubsanacion.add(createSubsanacion(rctx, attContext, genericDocId, docNames, hito.getGuid(), 
    				getMessageParams(exp, null, null)));
    	}
    	
    }
    
//    private Subsanacion createSubsanacion(RuleContext rctx, AttributeContext attContext, 
//    		IItem doc, String idHito, Map params) throws ISPACException {
//    	
//    	Subsanacion subsanacion = new Subsanacion();
//
//    	subsanacion.setIdentificador(new Guid().toString());
//    	subsanacion.setIdDocumento(doc.getString("ID"));
//    	
//    	// Mensaje para el ciudadano
//    	String message = (String) attContext.getProperties().get("MENSAJE_SUBSANACION");
//    	if (StringUtils.isNotBlank(message)) {
//    		subsanacion.setMensajeParaElCiudadano(substituteParams(message, params));
//    	}
//    	
//    	subsanacion.setFecha(TypeConverter.toString(new Date(), TIMESTAMPFORMAT_INV));
//    	subsanacion.setNumeroExpediente(rctx.getIdObjeto());
//    	subsanacion.setIdentificadorHito(idHito);
//
//    	return subsanacion;
//    }

    private Subsanacion createSubsanacion(RuleContext rctx, AttributeContext attContext, 
    		String genericDocId, List docNames, String idHito, Map params) throws ISPACException {
    	
    	Subsanacion subsanacion = new Subsanacion();

    	subsanacion.setIdentificador(new Guid().toString());
    	subsanacion.setIdDocumento(genericDocId);
    	
    	// Nombre del tipo de documento
    	if (!CollectionUtils.isEmpty(docNames)) {
    		params.put("NOMBRE_DOC", ArrayUtils.join((String[])docNames.toArray(new String[docNames.size()]), ", "));
    	} else {
    		params.put("NOMBRE_DOC", "");
    	}
    	
    	// Mensaje para el ciudadano
    	String message = (String) attContext.getProperties().get("MENSAJE_SUBSANACION");
    	if (StringUtils.isNotBlank(message)) {
    		subsanacion.setMensajeParaElCiudadano(substituteParams(message, params));
    	}
    	
    	subsanacion.setFecha(TypeConverter.toString(new Date(), ConstantesServicios.DATE_PATTERN));
    	subsanacion.setNumeroExpediente(rctx.getIdObjeto());
    	subsanacion.setIdentificadorHito(idHito);

    	return subsanacion;
    }
    
    private Pago createPago(RuleContext rctx, AttributeContext attContext, 
    		IItem doc, String idHito, TasaVO tasa, Map params) throws ISPACException {
    	
    	Pago pago = new Pago();

    	pago.setIdentificador(new Guid().toString());
    	pago.setIdDocumento(doc.getString("ID"));
    	
    	// Mensaje para el cuidadano
    	String message = (String) attContext.getProperties().get("MENSAJE_PAGO");
    	if (StringUtils.isNotBlank(message)) {
    		pago.setMensajeParaElCiudadano(substituteParams(message, params));
    	}
    	
    	pago.setFecha(TypeConverter.toString(new Date(), ConstantesServicios.DATE_PATTERN));
    	pago.setNumeroExpediente(rctx.getIdObjeto());
    	pago.setIdentificadorHito(idHito);
        pago.setEntidadEmisoraId(tasa.getIdEntidadEmisora());
        pago.setAutoliquidacionId(tasa.getIdAutoLiquidacion());
        pago.setImporte(tasa.getImporte());
    	
    	return pago;
    }

    private void altaSolicitudesSubsanacion() throws SigemException {
    	
    	if (!CollectionUtils.isEmpty(solicitudesSubsanacion)) {

        	// API de acceso a Consulta Telemática
    		ServicioConsultaExpedientes consulta = 
    			LocalizadorServicios.getServicioConsultaExpedientes();

    		Subsanacion subsanacion;
    		for (int i = 0; i < solicitudesSubsanacion.size(); i++) {
    			subsanacion = (Subsanacion) solicitudesSubsanacion.get(i);
    			
    	    	try {
    				// Alta de la solicitud de subsanación
    				consulta.altaSolicitudSubsanacion(subsanacion, getEntidad());

    		        // Log de la subsanación
    		        logOk(subsanacion);
    		        
    	        } catch (Exception e) {
    	        	logError(subsanacion, e);
    	        }
    		}
    	}
    }

    private void altaSolicitudesPago() throws SigemException {

		if (!CollectionUtils.isEmpty(solicitudesPago)) {

			// API de acceso a Consulta Telemática
			ServicioConsultaExpedientes consulta = LocalizadorServicios
					.getServicioConsultaExpedientes();

			Pago solicitudPago;
			for (int i = 0; i < solicitudesPago.size(); i++) {
				solicitudPago = (Pago) solicitudesPago.get(i);

				try {
					// Alta de la solicitud de pago
					consulta.altaSolicitudPago(solicitudPago, getEntidad());

					// Log del pago
					logOk(solicitudPago);

				} catch (Exception e) {
					logError(solicitudPago, e);
				}
			}
		}
	}

    private IItemCollection getDocumentos(RuleContext rctx) 
    		throws ISPACException  {

        List documentos = new LinkedList();

    	IItem infoTramite = getInfoTramite(rctx);
    	if (infoTramite != null) {
    		int id = infoTramite.getInt("ID");
    		String xml = infoTramite.getString("INFO");
    		
	    	if (StringUtils.isNotBlank(xml)) {
	    		
		        XmlFacade xmlFacade = new XmlFacade(xml);
		        List idDocs = xmlFacade.getList("/documentos/documento/@id");
	
		        Iterator it = idDocs.iterator();
		        String idDoc;
		        String xmlPath;
		        IItem documento;
		        while (it.hasNext()) {
		            idDoc = (String)it.next();
		            xmlPath = new StringBuffer("/documentos/documento[@id='")
		            	.append(idDoc).append("']/").toString();
		            
		            documento = new GenericItem(DOC_PROPS, "ID");

		        	/*
		        	 * El id del documento va a tener el formato:
		        	 * SPAC_INFOTRAMITE.ID + "-" + ID_DOCUMENTO_EN_XML
		        	 */ 
		            documento.set("ID", new StringBuffer()
		            		.append(id).append("-").append(idDoc).toString());
		            
		            documento.set("DOCUMENTO", StringUtils.trimToEmpty(
		            		xmlFacade.get(xmlPath + "nombre")));
		            documento.set("PENDIENTE", 
		            		xmlFacade.get(xmlPath + "pendiente"));
		            documentos.add(documento);
		        }
	    	}
    	}
        
        return new ListCollection(documentos);
    }

    private IItem getInfoTramite(RuleContext rctx) throws ISPACException {

    	IItem infoTramite = null;
    	
    	// API de entidades
		IEntitiesAPI entitiesAPI = clientContext.getAPI().getEntitiesAPI();

        // Obtener el identificador del trámite
    	String sIdTramite = (String) rctx.getProperties().get("id_tramite");
    	int idTramite = TypeConverter.parseInt(sIdTramite, -1);
    	if (idTramite == -1) {
			throw new ActionException("Identificador de trámite no válido: " 
					+ sIdTramite);
    	}

		// Consultar la documentación pendiente
        String sql = new StringBuffer()
        	.append("WHERE ID = ( SELECT MAX(ID) FROM SPAC_INFOTRAMITE")
        	.append(" WHERE NUMEXP = '")
        	.append(DBUtil.replaceQuotes(rctx.getIdObjeto()))
        	.append("' AND ID_TRAMITE = ").append(idTramite)
        	.append(" )")
        	.toString();
        
        IItemCollection collection = entitiesAPI.queryEntities(
        		SpacEntities.SPAC_INFOTRAMITE, sql);
        if (collection.next()) {
            infoTramite = ((IItem)collection.value());
        }
		
		return infoTramite;
    }
    
    /**
     * Compone los parámetros para los mensajes de aviso.
     * @param exp Información del expediente.
     * @param doc Información del documento.
     * @param tasa Información de la tasa de pago.
     * @return Parámetros para los mensajes de aviso.
     * @throws ISPACException si ocurre algún error.
     */
    protected static Map getMessageParams(IItem exp, IItem doc, TasaVO tasa) throws ISPACException {
    	
    	Map parameters = new HashMap();
    	
    	String nombrePago = null;
    	String importePago = null;
    	
    	if (tasa != null) {
    		nombrePago = tasa.getEtiquetas();
    		importePago = tasa.getImporte();
    	}

    	if (exp != null) {
    		
    		// Información del EXPEDIENTE
	    	parameters.put("NUMEXP", exp.getString("NUMEXP")); // Número de expediente
	    	parameters.put("CODPROCEDIMIENTO", exp.getString("CODPROCEDIMIENTO")); // Código del procedimiento
			parameters.put("NOMBREPROCEDIMIENTO", exp.getString("NOMBREPROCEDIMIENTO")); // Nombre del procedimiento
			parameters.put("NREG", exp.getString("NREG")); // Número de registro del expediente
			parameters.put("FREG", exp.getString("FREG")); // Fecha de registro del expediente
			parameters.put("ASUNTO", exp.getString("ASUNTO")); // Asunto del expediente
			parameters.put("FAPERTURA", exp.getString("FAPERTURA")); // Fecha de apertura del expediente

			// Información del DESTINATARIO
			parameters.put("NDOC_DEST", exp.getString("NIFCIFTITULAR")); // NIF/CIF del destinatario
			parameters.put("NOMBRE_DEST", exp.getString("IDENTIDADTITULAR")); // Identificación del destinatario
			parameters.put("DOMICILIO_DEST", exp.getString("DOMICILIO")); // Domicilio del destinatario
			parameters.put("DIRECCIONTELEMATICA_DEST", exp.getString("DIRECCIONTELEMATICA")); // DEU del destinatario
    	}
				
		// Información del DOCUMENTO
		if (doc != null) {
			parameters.put("NOMBRE_DOC", doc.getString("DOCUMENTO")); // Nombre del tipo de documento
		}

    	// Información del pago
		parameters.put("NOMBRE_PAGO", StringUtils.nullToEmpty(nombrePago)); // Nombre del pago
		parameters.put("IMPORTE_PAGO", StringUtils.nullToEmpty(importePago)); // Importe del pago
		
		return parameters;
    }

    /**
     * Muestra un log terminación correcta.
     * @param solicitud Información de la solicitud.
     */
    private static void logOk(SolicitudAportacionDocumentacion solicitud) {
    	
    	if (CONSULTA_TELEMATICA.isInfoEnabled()) {
    		
    		if (solicitud instanceof Subsanacion) {

    			// Información de la subsanación
		        StringBuffer info = new StringBuffer()
		        	.append("- SUBSANACIÓN: ")
		        	.append(toString(solicitud));
		        
		        // Log del resultado de la acción
		        CONSULTA_TELEMATICA.info("Solicitud de Subsanación realizada:\n" 
		        		+ info.toString());

    		} else if (solicitud instanceof Pago) {

    			// Información del pago
		        StringBuffer info = new StringBuffer()
		        	.append("- PAGO: ")
		        	.append(toString(solicitud));
		        
		        // Log del resultado de la acción
		        CONSULTA_TELEMATICA.info("Solicitud de Pago realizada:\n" 
		        		+ info.toString());

    		}
    	}
    }

    /**
     * Muestra un log de error.
     * @param solicitud Información de la solicitud.
     * @param e Excepción capturada.
     */
    private static void logError(SolicitudAportacionDocumentacion solicitud, 
    		Exception e) {
    	
    	if (solicitud instanceof Subsanacion) {

    		// Información de la subsanación
	        StringBuffer info = new StringBuffer()
	        	.append("- SUBSANACIÓN: ")
	        	.append(toString(solicitud));
	        
	        // Log del error
	        CONSULTA_TELEMATICA.error("Error en la Solicitud de Subsanación:\n" 
	        		+ info.toString(), e);
	        
    	} else if (solicitud instanceof Pago) {

    		// Información del pago
	        StringBuffer info = new StringBuffer()
	        	.append("- PAGO: ")
	        	.append(toString(solicitud));
	        
	        // Log del error
	        CONSULTA_TELEMATICA.error("Error en la Solicitud de Pago:\n" 
	        		+ info.toString(), e);
    	}
    }

    private static String toString(SolicitudAportacionDocumentacion solicitud) {
    	if (solicitud != null) {
    		StringBuffer info = new StringBuffer()
	    		.append("id=[")
	    		.append(solicitud.getIdentificador()).append("]")
	    		.append(", documentoId=[")
	    		.append(solicitud.getIdDocumento()).append("]")
	    		.append(", mensaje=[")
	    		.append(solicitud.getMensajeParaElCiudadano()).append("]")
	    		.append(", hitoId=[")
	    		.append(solicitud.getIdentificadorHito()).append("]")
	    		.append(", fechaSubsanacion=[")
	    		.append(solicitud.getFecha()).append("]")
	    		.append(", expediente=[")
	    		.append(solicitud.getNumeroExpediente()).append("]");
    		
    		if (solicitud instanceof Pago) {
    		    info.append(", entidadEmisoraId=[")
		    		.append(((Pago) solicitud).getEntidadEmisoraId()).append("]")
		    		.append(", autoliquidacionId=[")
		    		.append(((Pago) solicitud).getAutoliquidacionId()).append("]")
		    		.append(", importe=[")
		    		.append(((Pago) solicitud).getImporte()).append("]");
    		}
    		
    		return info.toString();
    	} else {
    		return null;
    	}
    }

}