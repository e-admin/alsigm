package ieci.tdw.ispac.services.mgr;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.expedients.CommonData;
import ieci.tdw.ispac.api.expedients.Document;
import ieci.tdw.ispac.api.expedients.Expedients;
import ieci.tdw.ispac.api.expedients.InterestedPerson;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.services.db.DbCommand;
import ieci.tdw.ispac.services.db.TramitadorDAO;
import ieci.tdw.ispac.services.dto.DatosComunesExpediente;
import ieci.tdw.ispac.services.dto.DocumentoExpediente;
import ieci.tdw.ispac.services.dto.Expediente;
import ieci.tdw.ispac.services.dto.InfoBExpediente;
import ieci.tdw.ispac.services.dto.InteresadoExpediente;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

public class TramitacionManager extends ServiceManager {

	/** Logger de la clase. */
	protected static final Logger logger = 
		Logger.getLogger(TramitacionManager.class);

	private final String TAG_RESULTS = "results";
	private final String TAG_ERROR = "error"; 
 	private final String TAG_CODE = "code"; 
 	private final String TAG_DESCRIPTION = "description"; 
	private final String TAG_ITEM = "item";
	private final String ENCONDING = "ISO-8859-1";
	/**
	 * Constructor.
	 */
	private TramitacionManager() {
		super();
	}
	
	/**
	 * Obtiene una instancia del manager.
	 * @return Instancia del manager.
	 */
	public static TramitacionManager getInstance() {
		return new TramitacionManager();
	}
	
	/**
	 * Recupera los identificadores de los expedientes,  del procedimiento identificado 
	 * por idProc, que hayan finalizado en el rango de fechas comprendido entre fechaIni
	 * y fechaFin ordenados por lo que indique el parámetro tipoOrd.
	 * @param idProc Identificador del procedimiento.
	 * @param fechaIni Fecha de inicio.
	 * @param fechaFin Fecha de fin.
	 * @param tipoOrd Tipo de ordenación.
	 * <p>Valores posibles:
	 * <li>1 - Número de expediente</li>
	 * <li>2 - Fecha finalización</li>
	 * </p>
	 * @return Lista de identificadores de expedientes.
	 */
	public String[] getIdsExpedientes(final String idProc, 
				final Date fechaIni, final Date fechaFin, final int tipoOrd) 
			throws ISPACException {
		DbCommand dbCommand = new DbCommand(getContext()){
			public Object logic(DbCnt cnt) throws ISPACException {
				return TramitadorDAO.recuperarIdsExpedientes(cnt, idProc, 
						fechaIni, fechaFin, tipoOrd);
		}};
		List ids = (List)dbCommand.exec();
		return (String []) ids.toArray(new String[ids.size()]);
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 */
	public InfoBExpediente[] getExpedientes(final String[] idExps) 
			throws ISPACException {
		List expedientes = null;
		DbCommand dbCommand = new DbCommand(getContext()){
			public Object logic(DbCnt cnt) throws ISPACException {
				return TramitadorDAO.recuperarInfoBExpedientes(cnt, idExps);
			}};
		expedientes = (List)dbCommand.exec();
		return (InfoBExpediente[]) expedientes.toArray(
				new InfoBExpediente[expedientes.size()]);
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(final String idExp) 
			throws ISPACException {
		DbCommand dbCommand = new DbCommand(getContext()){
			public Object logic(DbCnt cnt) throws ISPACException {
				return TramitadorDAO.recuperarExpediente(cnt, idExp);
		}};
		return (Expediente) dbCommand.exec();
	}
	
	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 * @throws ISPACException
	 */

	public void archivarExpedientes(String[] idExps) throws ISPACException
	{
		IInvesflowAPI invesflowAPI = getContext().getAPI();
		ITXTransaction transactionAPI = invesflowAPI.getTransactionAPI();
		
        if (!ArrayUtils.isEmpty(idExps)) {
	        for(int i=0; i< idExps.length; i++) {
	        	if (StringUtils.isNotBlank(idExps[i])) {
	        		IProcess process = invesflowAPI.getProcess(idExps[i]);
	        		transactionAPI.archiveProcess(process.getKeyInt());
	        	}
	        }
        }
	}
	
    /**
     * Iniciar un expediente.
     * 
     * @param commonData Datos comunes para todos los expedientes.
     * @param specificDataXML XML con los datos específicos del expediente.
     * @param documents Lista de documentos asociados al expediente.
     * @return Cierto si el expediente se ha iniciado correctamente.
     * @throws ISPACException Si se produce algún error al iniciar el expediente.
     */
    public boolean iniciarExpediente(DatosComunesExpediente commonData, 
    			String specificDataXML, DocumentoExpediente[] documents) 
    		throws ISPACException {

    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Iniciar el expediente
        return expedientsAPI.initExpedient(getCommonData(commonData), 
        		specificDataXML, getDocumentList(documents));
    }

    
    /**
     * Busqueda avanzada sobre expedientes
     * @param groupName Nombre de grupo
     * @param searchFormName Nombre del formulario de busqueda a utilizar
     * @param searchXML XML con los criterios de busqueda
     * @param domain dominio de busqueda (en funcion de la responsabilidad)
     * @return Resultado de la busqueda
     * @throws ISPACException
     */
    public String busquedaAvanzada(String nombreGrupo, String nombreFrmBusqueda, String xmlBusqueda, int dominio)throws ISPACException{
    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
        if (logger.isInfoEnabled()){
        	logger.info("Realizando busqueda avanzada: '" + xmlBusqueda + "'");
        }
    	
		SearchResultVO searchResultVO = expedientsAPI.limitedSearch(nombreGrupo, nombreFrmBusqueda, xmlBusqueda, dominio); 
        if (logger.isInfoEnabled()){
        	logger.info("Obtenidos resultados de busqueda avanzada");
        }
        return prepareSearchResults(searchResultVO);
    }
    

	/**
     * Iniciar un expediente.
     * 
     * @param commonData Datos comunes para todos los expedientes.
     * @param specificDataXML XML con los datos específicos del expediente.
     * @param documents Lista de documentos asociados al expediente.
     * @param initSystem Sistema externo desde el que se inicia el expediente 
     * @return Numero de expediente creado
     * @throws ISPACException Si se produce algún error al iniciar el expediente.
     */
    public String iniciarExpediente(final DatosComunesExpediente commonData, 
    		final String specificDataXML, final DocumentoExpediente[] documents, final String initSystem) 
    		throws ISPACException {

    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Iniciar el expediente
        return expedientsAPI.initExpedient(getCommonData(commonData), 
        		specificDataXML, getDocumentList(documents), initSystem);
    }
    
    /**
     * Cambia el estado administrativo de un expediente
     * @param numExp Número de expediente.
     * @param estadoAdm Nuevo estado administrativo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException 
     */
    public boolean cambiarEstadoAdministrativo(final String numExp, final String estadoAdm) throws ISPACException {
    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Cambiar el estado administrativo
        return expedientsAPI.changeAdmState(numExp, estadoAdm);
	}    
    
    /**
     * Mueve un expedinete a una fase según el identificador de la misma en el catálogo. 
     * @param numExp Número de expediente.
     * @param idFaseCatalogo Identificador de la fase en el catálogo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public boolean moverExpedienteAFase(final String numExp, final String idFaseCatalogo) throws ISPACException {
    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Mueve el expediente de fase
        return expedientsAPI.moveExpedientToStage(numExp, idFaseCatalogo);
	}
    
    
    /**
     * Añade documentos al trámite de un expediente.
     * @param numExp Número de expediente.
     * @param regNum Número de registro de entrada.
     * @param regDate Fecha de registro de entrada.
     * @param documents Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     * @throws ISPACException Si se produce algún error.
     */
    public boolean anexarDocsExpediente(String numExp, String regNum, 
    			Date regDate, DocumentoExpediente[] documents) 
    		throws ISPACException {

    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Iniciar el expediente
        return expedientsAPI.addDocuments(numExp, regNum, regDate, 
        		getDocumentList(documents));
    }

    /**
     * Inserta o actualiza los datos de un registro de una entidad para un expediente
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param xmlDatosEspecificos Datos especificos para completar los campos del registro a crear
     * @return identificador del registro creado
     * @throws ISPACException
     */	
    public int establecerDatosRegistroEntidad(String nombreEntidad, String numExp, String xmlDatosEspecificos)throws ISPACException{
    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Establecer datos registro
        return expedientsAPI.setRegEntity(nombreEntidad, numExp, xmlDatosEspecificos);
    }
    
    /**
     * Obtiene los datos de un registro de una entidad
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */    
    public String obtenerRegistroEntidad(String nombreEntidad, String numExp,
			int idRegistro)throws ISPACException{
    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Obtener datos de un registro
        IItem item = expedientsAPI.getRegEntity(nombreEntidad, numExp, idRegistro);
    	
        List list = new ArrayList();
        list.add(new ItemBean(item));
        return prepareResults(list);
    }
    
    /**
     * Obtiene los datos de todos los registros de una entidad
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */    
    public String obtenerRegistrosEntidad(String nombreEntidad, String numExp)throws ISPACException{
    	// Crear el API de expedientes
    	Expedients expedientsAPI = new Expedients();
    	
        // Obtencion de todos los registros
        List list = expedientsAPI.getAllRegsEntity(nombreEntidad, numExp);
    	return prepareResults(list);
    }

    
    
    
    private CommonData getCommonData(DatosComunesExpediente datosComunes) {
    	CommonData commonData = null;
    	
    	if (datosComunes != null) {
    		commonData = new CommonData();
    		commonData.setOrganismId(datosComunes.getIdOrganismo());
    		commonData.setSubjectType(datosComunes.getTipoAsunto());
    		commonData.setRegisterNumber(datosComunes.getNumeroRegistro());
    		commonData.setRegisterDate(datosComunes.getFechaRegistro());
    		commonData.setInterested(
    				getInterestedList(datosComunes.getInteresados()));
    	}
    	
    	return commonData;
    }
    
    private List getInterestedList(InteresadoExpediente[] interesados) {
    	List interestedList = new ArrayList();
    	
    	if (interesados != null) {
    		for (int i = 0; i < interesados.length; i++) {
    			interestedList.add(getInterestedPerson(interesados[i]));
    		}
    	}
    	return interestedList;
    }
    
    private InterestedPerson getInterestedPerson(InteresadoExpediente inter) {
    	InterestedPerson interested = null;
    	
    	if (inter != null) {
    		interested = new InterestedPerson();
    		interested.setThirdPartyId(inter.getThirdPartyId());
    		interested.setIndPrincipal(inter.getIndPrincipal());
    		interested.setNifcif(inter.getNifcif());
    		interested.setName(inter.getName());
    		interested.setPostalAddress(inter.getPostalAddress());
    		interested.setPostalCode(inter.getPostalCode());
    		interested.setPlaceCity(inter.getPlaceCity());
    		interested.setRegionCountry(inter.getRegionCountry());
    		interested.setTelematicAddress(inter.getTelematicAddress());
    		interested.setNotificationAddressType(
    				inter.getNotificationAddressType());
    		interested.setPhone(inter.getPhone());
    		interested.setMobilePhone(inter.getMobilePhone());
    	}
    	
    	return interested;
    }
    
    private List getDocumentList(DocumentoExpediente[] documentos) {
    	List documentList = new ArrayList();
    	
    	if (documentos != null) {
    		for (int i = 0; i < documentos.length; i++) {
    			documentList.add(getDocument(documentos[i]));
    		}
    	}
    	
    	return documentList;
    }
    
    private Document getDocument(DocumentoExpediente doc) {
    	Document document = null;
    	
    	if (doc != null) {
    		document = new Document();
    		document.setId(doc.getId());
    		document.setCode(doc.getCode());
    		document.setName(doc.getName());
    		document.setExtension(doc.getExtension());
    		document.setLength(doc.getLenght());

    		if (doc.getContent() != null) {
	    		document.setContent(new ByteArrayInputStream(doc.getContent()));
    		}
    	}
    	
    	return document;
    }
    private String prepareResults(List list) throws ISPACException {
		StringBuffer buffer = new StringBuffer("<?xml version='1.0' encoding='" + ENCONDING + "'?>");
		buffer.append("<"+TAG_RESULTS+">");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			ItemBean item = (ItemBean) iterator.next();
			buffer.append("<"+TAG_ITEM+">"+item.getItem().getXmlValues()+"</"+TAG_ITEM+">");
		}
		buffer.append("</"+TAG_RESULTS+">");
		return buffer.toString();        
	}

    private String prepareSearchResults(SearchResultVO searchResultVO ) throws ISPACException{
    	List list= CollectionBean.getBeanList(searchResultVO.getResultados());
    	StringBuffer buffer = new StringBuffer("<?xml version='1.0' encoding='" + ENCONDING + "'?>");
		buffer.append("<"+TAG_RESULTS+">");
			if(list.size()< searchResultVO.getNumTotalRegistros()){ 
		 			buffer.append("<"+TAG_ERROR+">"); 
		 			buffer.append("<"+TAG_CODE+">04-005"); 
		 			buffer.append("</"+TAG_CODE+">"); 
		 			buffer.append("<"+TAG_DESCRIPTION+">"); 
		 			buffer.append(  new ISPACException("exception.searchforms.maxResultSearch" , new Object []{searchResultVO.getNumMaxRegistros()+"" , searchResultVO.getNumTotalRegistros()+""}, true).getExtendedMessage(Locale.getDefault())); 
		 			buffer.append("</"+TAG_DESCRIPTION+">"); 
		 			buffer.append("</"+TAG_ERROR+">"); 
		   
		 	 }  
		 	 
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			ItemBean item = (ItemBean) iterator.next();
			buffer.append("<"+TAG_ITEM+">"+item.getItem().getXmlValues()+"</"+TAG_ITEM+">");
		}
		buffer.append("</"+TAG_RESULTS+">");
		return buffer.toString();   
		
    }
}
