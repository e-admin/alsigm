package ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.gendoc.config.ConfigConstants;
import ieci.tdw.ispac.ispaclib.gendoc.config.Mapping;
import ieci.tdw.ispac.ispaclib.gendoc.config.Repositories;
import ieci.tdw.ispac.ispaclib.gendoc.config.RepositoriesCache;
import ieci.tdw.ispac.ispaclib.gendoc.config.Repository;
import ieci.tdw.ispac.ispaclib.gendoc.config.Token;
import ieci.tdw.ispac.ispaclib.gendoc.config.TokenEvaluator;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.holders.ByteArrayHolder;
import javax.xml.rpc.holders.StringHolder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.holders.UnsignedIntHolder;
import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.microsoft.schemas.sharepoint.soap.CopyErrorCode;
import com.microsoft.schemas.sharepoint.soap.CopyLocator;
import com.microsoft.schemas.sharepoint.soap.CopySoap;
import com.microsoft.schemas.sharepoint.soap.FieldInformation;
import com.microsoft.schemas.sharepoint.soap.FieldType;
import com.microsoft.schemas.sharepoint.soap.GetListItemsQuery;
import com.microsoft.schemas.sharepoint.soap.GetListItemsQueryOptions;
import com.microsoft.schemas.sharepoint.soap.GetListItemsResponseGetListItemsResult;
import com.microsoft.schemas.sharepoint.soap.GetListItemsViewFields;
import com.microsoft.schemas.sharepoint.soap.ListsLocator;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;
import com.microsoft.schemas.sharepoint.soap.SiteDataLocator;
import com.microsoft.schemas.sharepoint.soap.SiteDataSoap;
import com.microsoft.schemas.sharepoint.soap.UpdateListItemsResponseUpdateListItemsResult;
import com.microsoft.schemas.sharepoint.soap.UpdateListItemsUpdates;
import com.microsoft.schemas.sharepoint.soap._sProperty;
import com.microsoft.schemas.sharepoint.soap.dws.DwsLocator;
import com.microsoft.schemas.sharepoint.soap.dws.DwsSoap;
import com.microsoft.schemas.sharepoint.soap.holders.ArrayOfStringHolder;
import com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sFPUrlHolder;
import com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sListWithTimeHolder;
import com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sPropertyHolder;
import com.microsoft.schemas.sharepoint.soap.holders.ArrayOf_sWebWithTimeHolder;
import com.microsoft.schemas.sharepoint.soap.holders.CopyResultCollectionHolder;
import com.microsoft.schemas.sharepoint.soap.holders.FieldInformationCollectionHolder;
import com.microsoft.schemas.sharepoint.soap.holders._sListMetadataHolder;
import com.microsoft.schemas.sharepoint.soap.holders._sWebMetadataHolder;

public class MossHelper {

	protected static Logger logger = Logger.getLogger(MossHelper.class);

	/**
	 * Servicios de SharePoint usados por el conector
	 */
	private static SiteDataSoap _siteDataSvc;
	private static ListsSoap _listsSvc;
	private static CopySoap _copySvc;
	private static DwsSoap _dwsSvc;
	//private WebsSoap _websSvc;
	private Repositories _repositories = null;

	private int _repositoryId = 0;
	private String _repositoryAlias = null;
	private String _siteId = null;

	private final static String DWS_PATH_WS ="/_vti_bin/dws.asmx";
	private final static String SITEDATA_PATH_WS = "/_vti_bin/sitedata.asmx";
	private final static String LISTS_PATH_WS = "/_vti_bin/lists.asmx";
	private final static String COPY_PATH_WS = "/_vti_bin/copy.asmx";
	//private final static String WEBS_PATH_WS = "/_vti_bin/webs.asmx";

	/**
	 * Contexto de cliente.
	 */
	private ClientContext m_clientCtx;

//	static {
//		DocumentalConfiguration documentalConfig;
//		try {
//			documentalConfig = new DocumentalConfiguration(MossConstants.DEFAULT_CONFIG_FILENAME);
//			_repositories = documentalConfig.getRepositories();
//			initCommon();
//		} catch (Exception e) {
//			logger.error(e);
//		}
//	}

	public MossHelper(ClientContext ctx) throws Exception {
		m_clientCtx = ctx;
		_repositories = RepositoriesCache.getRepositories(ctx, MossConstants.DEFAULT_CONFIG_FILENAME);

		initCommon();
	}

	public MossHelper(ClientContext ctx, int repId) throws Exception {
		m_clientCtx = ctx;
		_repositories = RepositoriesCache.getRepositories(ctx, MossConstants.DEFAULT_CONFIG_FILENAME);
		_repositoryId = repId;

		initCommon();
	}

	public MossHelper(ClientContext ctx, String repositoryAlias) throws Exception{
		m_clientCtx = ctx;
		_repositories = RepositoriesCache.getRepositories(ctx, MossConstants.DEFAULT_CONFIG_FILENAME);
		_repositoryAlias = repositoryAlias;

		initCommon();
	}

//	public synchronized  static MossHelper getInstance() throws Exception {
//		if (_instance != null)
//			_instance = new MossHelper();
//		return _instance;
//	}
//
//	public synchronized  static MossHelper getInstance(int repositoryId) throws Exception {
//		_repositoryId = repositoryId;
//		return getInstance();
//	}
//
//	public synchronized  static MossHelper getInstance(String repositoryAlias) throws Exception {
//		_repositoryAlias = repositoryAlias;
//		return getInstance();
//	}


	/**
	 * Construye el xml de actualización necesario para crear/actualizar/eliminar documentos en la biblioteca
	 * @param sguid. Identificador del documento.
	 * @param properties. Metadatos.
	 * @param propertyName. Nombre del metadato.
	 * @param propertyValue. Valor del metadato.
	 * @param action. Acción a realizar.
	 * @return
	 * @throws Exception
	 */
	private Element buildUpdatesXml(String sguid, String properties, String propertyName, String propertyValue, String action) throws Exception {
		StringBuffer xml = new StringBuffer();
		String itemID = "";
		String version = "";
		//Contador del metodo actual. Se pueden ejecutar invocar mas de un metodo en una llamada, teniendo cada uno un contador que sera este.
		int methodCount = 1;
		// Obtener sólo los metadatos que se van a necesitar
		// Node node = getItem(sguid);
		GetListItemsViewFields viewFields = buildListItemsViewFields(new String[] {MossConstants.METADATA_ID, MossConstants.METADATA_VERSION, MossConstants.METADATA_FILEREF});
		Node node = getItemByGuid(sguid, viewFields);
		NamedNodeMap attrs = null;
		if (node != null){
			attrs = node.getAttributes();
			itemID = attrs.getNamedItem(MossConstants.PROPERTY_ID).getNodeValue();
			version = attrs.getNamedItem(MossConstants.PROPERTY_VERSION).getNodeValue();
		}else{
			throw new Exception("No se encontró el documento " + sguid);
		}

		xml.append("<Batch ListVersion='").append(version).append("' OnError='Continue' PreCalc='TRUE'>");
		xml.append("<Method Cmd='").append(action).append("' ID='").append(methodCount).append("'>");
		xml.append("<Field Name='ID'>").append(itemID).append("</Field>");
		if (StringUtils.equals(action, MossConstants.ACTION_DELETE)){
			String documentPath = attrs.getNamedItem(MossConstants.PROPERTY_FILEREF).getNodeValue();
			documentPath = cleanLibrary(documentPath);
			String url = getAbsolutePath(documentPath);
			xml.append("<Field Name='FileRef'>").append(url).append("</Field>");
		}

		if (StringUtils.isNotEmpty(properties)){
		    if (!action.equals(MossConstants.ACTION_DELETE)){
				List mappings = getCurrentRepository().getMetaDataMappings().getMappings();
	    		for(int i=0; i < mappings.size(); i++){
	    			Mapping mapping = (Mapping)mappings.get(i);
	    			String value = getPropertyValue(properties, mapping.getSource().getValue());
	    			xml.append("<Field Name='").append(mapping.getDestination().getValue()).append("'>").append(value).append("</Field>");
	    		}
	    	}
	    }else if (StringUtils.isNotEmpty(propertyName) && StringUtils.isNotEmpty(propertyValue)){
			// Eliminar el prefijo ows_ si viene en la propiedad
			int index = propertyName.indexOf(MossConstants.PROPERTY_PREFIX);
			if (index != -1) {
				propertyName = propertyName.substring(index + MossConstants.PROPERTY_PREFIX.length());
			}
			xml.append("<Field Name='").append(propertyName).append("'>").append(propertyValue).append("</Field>");
	    }
		xml.append("</Method>");
		xml.append("</Batch>");

        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new StringReader(xml.toString())));
        return parser.getDocument().getDocumentElement();
	}

	/*
	private Element buildFilterViewFieldsXml(String[] properties)throws Exception{
		DOMParser parser = new DOMParser();
		String viewFiles = "<ViewFields>";
		for (int i = 0; i < properties.length; i++) {
			viewFiles += "<FieldRef Name='" + properties[i] + "'/>";
		}
		viewFiles += "</ViewFields>";
		parser.parse(new InputSource(new StringReader(viewFiles)));
		return parser.getDocument().getDocumentElement();
	}
	*/

	private GetListItemsViewFields buildListItemsViewFields(String[] properties)throws Exception{

		GetListItemsViewFields viewFields = new GetListItemsViewFields();

		String xmlViewFields = "<ViewFields>";
		for (int i = 0; i < properties.length; i++) {
			xmlViewFields += "<FieldRef Name='" + properties[i] + "'/>";
		}
		// Al incluir en la consulta la lista de metadatos a recuperar
		// el metadato de LinkFilename no se retorna
		// luego incluirlo siempre
		xmlViewFields += "<FieldRef Name='" + MossConstants.METADATA_FILENAME + "'/>";
		xmlViewFields += "</ViewFields>";

		final MessageElement[] meArray = {getMeFromString(xmlViewFields)};
		viewFields.set_any(meArray);

		return viewFields;
	}

	/**
	 * Crea una carpeta en la biblioteca
	 * @param properties. Metadatos.
	 * @return Ruta de la carpeta.
	 * @throws Exception
	 */
	private boolean createFolder(String folderPath) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Creando carpeta '"+folderPath+"'");
		}
		String relativePath = getRepositoryLibrary();
		if (!StringUtils.startsWith(folderPath, MossConstants.SLASH))
			relativePath += MossConstants.SLASH;
		relativePath += folderPath;
		checkResultCreateFoler(_dwsSvc.createFolder(relativePath));
		if (logger.isDebugEnabled()){
			logger.debug("Carpeta '"+folderPath+"' creada");
		}
		return true;
	}

	private void createRecursiveFolder(String folderPath) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Creando carpeta de forma recursiva '"+folderPath+"'");
		}
		// En el momento en que no exista la carpeta
		// para el resto de subcarpetas ya no se comprueban si existen
		boolean existFolder = true;
		String recusiveFolder = "";
		String[] folders = StringUtils.split(folderPath, MossConstants.SLASH);
		for (int i = 0; i < folders.length; i++) {
			recusiveFolder += MossConstants.SLASH + folders[i];
			if (existFolder)
				existFolder = existFolder(recusiveFolder);
			if (!existFolder)
				createFolder(recusiveFolder);
		}
		if (logger.isDebugEnabled()){
			logger.debug("Creada carpeta de forma recursiva '"+folderPath+"'");
		}
	}

	private boolean existFolder(String folderPath) throws Exception{
		if (logger.isDebugEnabled()){
			logger.debug("Comprobando la existencia de la carpeta '"+folderPath+"'");
		}

		//Se buscan la carpeta segun su nombre y tipo (Folder)
		GetListItemsQuery queryMoss = getQueryExistFolder(folderPath);

		//Filtramos la carpeta segun su url
		GetListItemsQueryOptions queryOptions = getQueryOptionsUrl(folderPath);

		GetListItemsResponseGetListItemsResult items = getItems(getRepositoryLibrary(), null, queryMoss, null, null, queryOptions);
		Node node = getRowResult(items);
		return node != null;
	}

	private String getLastFolderName(String folderPath) {
		return StringUtils.substring(folderPath, StringUtils.lastIndexOf(folderPath, MossConstants.SLASH)+1);
	}

	private String getAbsolutePath(String resourceName) throws Exception{
		String url = getRepositoryUrl() + MossConstants.SLASH + getRepositoryLibrary();
		if (!StringUtils.startsWith(resourceName, MossConstants.SLASH))
			url += MossConstants.SLASH;
		url += resourceName;
		return url;
	}

	private String getParentFolder(String folderPath) throws Exception {
		return StringUtils.substring(folderPath, 0, StringUtils.lastIndexOf(folderPath, MossConstants.SLASH));
	}

	private void checkResultCreateFoler(String result) throws Exception {
		if (StringUtils.equals(result, MossConstants.RETURN_CODE_CREATE_FOLDER_OK)){
		}else if (StringUtils.indexOf(result, MossConstants.RETURN_CODE_CREATE_FOLDER_ALREADYEXISTS_ERROR_ID)!= -1){
		}else if (StringUtils.indexOf(result, MossConstants.RETURN_CODE_CREATE_FOLDER_FOLDERNOTFOUND)!= -1){
			throw new Exception("La carpeta principal no existe.");
		}
	}

	/**
	 * Elimina un documento de la biblioteca
	 * @param sguid. GUID del documento
	 * @throws Exception
	 */
	public void deleteDocument(String sguid) throws Exception {

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Borrando documento '"+sguid+"'");
			}

			UpdateListItemsUpdates updatesElements = new UpdateListItemsUpdates();
			Element updateXml = buildUpdatesXml(sguid, null , null , null, MossConstants.ACTION_DELETE);
			MessageElement []_any = new MessageElement[]{new MessageElement(updateXml)};
			updatesElements.set_any(_any);

			UpdateListItemsResponseUpdateListItemsResult updatesResponse = _listsSvc.updateListItems(getRepositoryLibrary(), updatesElements);

			//Comprobamos el resultado
			checkResult(updatesResponse);

			// Borrar el path del documento de la tabla auxiliar
			deleteDocumentPath(sguid);

			if (logger.isDebugEnabled()){
				logger.debug("Borrado documento '"+sguid+"'");
			}
		} catch (Throwable t) {
			logger.error("Sharepoint DeleteDocument GUID=" + sguid + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint DeleteDocument GUID=" + sguid + " - Error=" + t.getMessage(), t);
		}
	}

//	private void renameDocument (String url, String newName) throws SAXException, IOException{
//		String id = "";
//		String rename="<Batch OnError='Continue' PreCalc='TRUE' ListVersion='0'>   <Method ID='1' Cmd='Update'><Field Name='ID'>" + id + "</Field><Field Name='FileRef'>" + url + "</Field><Field Name='BaseName'>" + newName + "</Field>   </Method></Batch>";
//
//        DOMParser parser = new DOMParser();
//        parser.parse(new InputSource(new StringReader(rename)));
//        Element updateXml = parser.getDocument().getDocumentElement();
//
//
//		UpdateListItemsUpdates updatesElements = new UpdateListItemsUpdates();
//		MessageElement []_any = new MessageElement[]{new MessageElement(updateXml)};
//		updatesElements.set_any(_any);
//
//		UpdateListItemsResponseUpdateListItemsResult updatesResponse = _listsSvc.updateListItems(getRepositoryLibrary(), updatesElements);
//		//Comprobamos el resultado
//		checkResult(updatesResponse);
//
//	}

	/**
	 * Comprueba la existencia de un documento en la biblioteca
	 * @param sguid. GUID del documento.
	 * @return
	 * @throws Exception
	 */
	public boolean existDocument(String sguid) throws Exception{

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Comprobando existencia del documento '"+sguid+"'");
			}

			Node node = getItemByGuid(sguid, null);
			return node != null;
		} catch (Throwable t) {
			logger.error("Sharepoint ExistDocument GUID=" + sguid + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint ExistDocument GUID=" + sguid + " - Error=" + t.getMessage(), t);
		}
	}

	/*
	private String getDocumentFolderPath(String sguid) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Obteniendo ruta de carpetas del documento '"+sguid+"'");
		}
		Node node = getItem(sguid);
		String folderPath = getDocumentFolderPath(node);
		if (folderPath == null){
			throw new Exception("No se encontró el documento " + sguid);
		}
		return folderPath;
	}
	*/

	private String getDocumentFolderPath(Node node) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Obteniendo ruta de carpetas del documento");
		}
		NamedNodeMap attrs = null;
		String folderPath = null;
		if (node != null){
			attrs = node.getAttributes();
			folderPath = attrs.getNamedItem(MossConstants.PROPERTY_FILEREF).getNodeValue();
			folderPath = cleanLibrary(folderPath);
			folderPath = cleanDocumentName(folderPath);

		}
		return folderPath;
	}

	/*
	private boolean existDocument(String folderPath, String documentName) throws Exception{
		if (logger.isDebugEnabled()){
			logger.debug("Comprobando existencia de documento segun ruta de carpeta '"+folderPath+"' y nombre de documento '"+documentName+"'");
		}

		Node node = getItem(folderPath, documentName);
		return node != null;
	}
	*/

	/**
	 * Obtiene el documento de la biblioteca
	 * @param sguid. GUID del documento.
	 * @param out. Contenido del documento.
	 * @throws Exception
	 */
	public void getDocument(String sguid, OutputStream out) throws Exception {

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Obteniendo documento segun el identificador '"+sguid+"'");
			}

			// Obtener la URL del documento (tabla auxiliar o directamente a Sharepoint)
			String url = getDocumentUrl(sguid);

			UnsignedIntHolder getItemResult = new UnsignedIntHolder();
			FieldInformationCollectionHolder fields = new FieldInformationCollectionHolder();
			ByteArrayHolder stream = new ByteArrayHolder();
			_copySvc.getItem(url, getItemResult, fields, stream);

			if (stream.value != null) {

				// Obtener el contenido del documento
			    out.write(stream.value);
			    out.flush();
			} else {
				// No existe un documento asociado a la URL
				String message = "El documento con id '" + sguid + "' asociado a la URL '" + url + "' no existe";
				logger.error(message);

				// Eliminar la referencia en la tabla auxiliar si existe
				deleteDocumentPath(sguid);

				throw new ISPACException(message);
			}
		} catch (Throwable t) {
			logger.error("Sharepoint GetDocument GUID=" + sguid + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint GetDocument GUID=" + sguid + " - Error=" + t.getMessage(), t);
		}
	}

//	/**
//	 * Devuelve los metadatos del documento
//	 * @param sguid. Identificador del documento.
//	 * @return. Metadatos del documento en formato XML.
//	 * @throws Exception
//	 */
	public String getFields(String sguid) throws Exception{

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Obteniendo metadatos del documento segun el identificador '"+sguid+"'");
			}

			Node node = getItemByGuid(sguid);
			return node.toString();
		} catch (Throwable t) {
			logger.error("Sharepoint GetFields GUID=" + sguid + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint GetFields GUID=" + sguid + " - Error=" + t.getMessage(), t);
		}
	}


	/**
	 * Obtiene el valor de un metadato
	 * @param sguid. GUID del documento
	 * @param property. Nombre del metadato
	 * @return
	 * @throws Exception
	 */
	public String getFieldValue(String sguid, String property) throws Exception {

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Obteniendo propiedad '"+property+"' del documento segun el identificador '"+sguid+"'");
			}

			// Eliminar el prefijo ows_ si viene en la propiedad
			int index = property.indexOf(MossConstants.PROPERTY_PREFIX);
			if (index != -1) {
				property = property.substring(index + MossConstants.PROPERTY_PREFIX.length());
			}

			// Se incluye sólo el metadato a visualizar del conjunto de campos del documento
			GetListItemsViewFields viewFields = buildListItemsViewFields(new String[]{property});

			Node node = getItemByGuid(sguid, viewFields);
			return getFieldValue(node, property);

			/*
			//String result = "";
			Node node = getItem(sguid);
			return getFieldValue(node, property);
			/*
			if (node != null){
				NamedNodeMap attrs = node.getAttributes();
				result = attrs.getNamedItem(property).getNodeValue();
			}
			return result;
			*/
		} catch (Throwable t) {
			logger.error("Sharepoint GetFieldValue GUID=" + sguid + ", Field=" + property + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint GetFieldValue GUID=" + sguid + ", Field=" + property + " - Error=" + t.getMessage(), t);
		}
	}

	public String getFieldValue(Node node, String property) throws Exception {
		if (logger.isDebugEnabled()){
			logger.debug("Obteniendo propiedad '"+property+"' del documento'");
		}
		String result = "";
		if (node != null){
			String nodeName = null;
			// Incluir el prefijo ows_ si no viene en la propiedad
			if (StringUtils.startsWith(property, MossConstants.PROPERTY_PREFIX)) {
				nodeName = property;
			} else {
				nodeName = MossConstants.PROPERTY_PREFIX + property;
			}
			NamedNodeMap attrs = node.getAttributes();
			Node nodeField = attrs.getNamedItem(nodeName);
			if (nodeField != null) {
				result = nodeField.getNodeValue();
			}
		}
		return result;
	}

	/**
	 * Devuelve un conjunto de nodos con los metadatos de los documentos existentes en una biblioteca
	 * @return
	 * @throws Exception
	 */
	protected NodeList getItems() throws Exception {

		NodeList nodeList = null;
		GetListItemsResponseGetListItemsResult items = getItems(getRepositoryLibrary(),"", null, null, null, null);
		if (items.get_any().length > 0){
			Element node = (Element) items.get_any()[0];
			XPath xpath = XPathFactory.newInstance().newXPath();
			String ows_IDsXpath = "//*[local-name() = 'row']";
			nodeList = (NodeList) xpath.evaluate(ows_IDsXpath, node, XPathConstants.NODESET);
		}
		return nodeList;
	}

	private String[] getMetaDatas() throws Exception {

		List mappings = getCurrentRepository().getMetaDataMappings().getMappings();
		if ((mappings != null) && (!mappings.isEmpty())) {
			String[] metaDatas = new String[mappings.size()];
			for (int i = 0; i < mappings.size(); i++) {
				Mapping mapping = (Mapping) mappings.get(i);
				metaDatas[i] = mapping.getDestination().getValue();
			}
			return metaDatas;
		} else {
			return null;
		}
	}

	/**
	 * Devuelve un nodo con los metadatos de un documento
	 */
	private Node getItem(String sguid) throws Exception{
		GetListItemsViewFields viewFields = null;
		String[] properties = getMetaDatas();
		if (properties != null) {
			viewFields = buildListItemsViewFields(properties);
		}
		return getItem(sguid, viewFields);
	}

	/**
	 * Devuelve un nodo con los metadatos de un documento
	 */
	private Node getItem(String sguid, GetListItemsViewFields viewFields) throws Exception{

		//Componemos la consulta para obtener los datos segun el identificador unico
		GetListItemsQuery queryMoss = getQueryByUniqeId(sguid);

		//Indicamos que la busqueda sea sobre todas las carpetas (busqueda recursiva)
		GetListItemsQueryOptions queryMossOptions = getQueryOptionsRecursiveSearch();

		//Realizamos la consulta
		GetListItemsResponseGetListItemsResult items = getItems(getRepositoryLibrary(),"", queryMoss, viewFields, null, queryMossOptions);
		Node result = getRowResult(items);

		return result;
	}

	/**
	 * Devuelve un nodo con los metadatos de un documento
	 */
	private Node getItem(String folderPath, String documentName) throws Exception{
		GetListItemsViewFields viewFields = null;
		String[] properties = getMetaDatas();
		if (properties != null) {
			viewFields = buildListItemsViewFields(properties);
		}
		return getItem(folderPath, documentName, viewFields);
	}

	/**
	 * Devuelve un nodo con los metadatos de un documento
	 */
	private Node getItem(String folderPath, String documentName, GetListItemsViewFields viewFields) throws Exception{

		//Componemos la consulta para obtener los datos segun la url relativa (sin la webAppUrl)
		GetListItemsQuery queryMoss = getQueryRelativeUrl(folderPath, documentName);

		//Indicamos que la busqueda sea sobre todas las carpetas (busqueda recursiva)
		GetListItemsQueryOptions queryMossOptions = getQueryOptionsRecursiveSearch();

		//Realizamos la consulta
		GetListItemsResponseGetListItemsResult items = getItems(getRepositoryLibrary(),"", queryMoss, viewFields, null, queryMossOptions);
		Node result = getRowResult(items);

		return result;
	}

	/**
	 * Devuelve un nodo con los metadatos de un documento
	 */
	private Node getItemByPath(String documentPath) throws Exception{
		GetListItemsViewFields viewFields = null;
		String[] properties = getMetaDatas();
		if (properties != null) {
			viewFields = buildListItemsViewFields(properties);
		}
		return getItemByPath(documentPath, viewFields);
	}

	/**
	 * Devuelve un nodo con los metadatos de un documento
	 */
	private Node getItemByPath(String documentPath, GetListItemsViewFields viewFields) throws Exception{

		//Componemos la consulta para obtener los datos segun la url relativa (sin la webAppUrl)
		GetListItemsQuery queryMoss = getQueryRelativeUrl(documentPath);

		//Indicamos que la busqueda sea sobre todas las carpetas (busqueda recursiva)
		GetListItemsQueryOptions queryMossOptions = getQueryOptionsRecursiveSearch();

		//Realizamos la consulta
		GetListItemsResponseGetListItemsResult items = getItems(getRepositoryLibrary(),"", queryMoss, viewFields, null, queryMossOptions);
		Node result = getRowResult(items);

		return result;
	}

	/**
	 * @param listName
	 * @param viewName Nombre de la vista a utilizar para hacer uso de su configuracion en cuanto a las columnas a retornar (viewFields)
	 * @param query
	 * @param viewFields Columnas a retonar. Si no se informan se hace uso de las indicadas en viewName o vista por defecto.
	 * @param rowLimit
	 * @param queryOptions
	 * @return
	 * @throws RemoteException
	 */
	private GetListItemsResponseGetListItemsResult getItems(String listName, String viewName, GetListItemsQuery query,GetListItemsViewFields viewFields,String rowLimit, GetListItemsQueryOptions queryOptions) throws RemoteException{

		GetListItemsResponseGetListItemsResult result = new GetListItemsResponseGetListItemsResult();
		//viewName: Nombre de la vista a utilizar de la que se obtienen los campos a retonar (viewFields) y limete de filas (rowLimit). Si no se indica nada utiliza la vista por defecto asignada a la biblioteca
		//viewFields: Si no se informa, se obtienen de la vista indicada en viewName. Aqui se indican los campos/columnas a obtener como resultado de la consulta.
		result = _listsSvc.getListItems(listName, viewName, query, viewFields, rowLimit, queryOptions, _siteId);

		return result;
	}

	private MessageElement getMeFromString(final String strMyString){

		DocumentBuilder docBuilder = null;
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (final ParserConfigurationException e) {

			e.printStackTrace();
		} catch (final FactoryConfigurationError e) {

			e.printStackTrace();
		}
		final StringReader reader = new StringReader(strMyString);
		final InputSource inputsource = new InputSource(reader);
		Document doc = null;
		try {
			doc = docBuilder.parse(inputsource);
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		final Element ele = doc.getDocumentElement();
		final MessageElement msg = new MessageElement(ele);

		return msg;
	}

	/**
	 * Obtiene el valor de una propiedad del XML de entrada
	 * @param properties
	 * @param xpathExpr
	 * @return
	 * @throws Exception
	 */
	public String getPropertyValue(String properties, String xpathExpr) throws Exception{
		XmlFacade xmlFacade = new XmlFacade(properties);
		return xmlFacade.get(xpathExpr);
	}

	/***
	 * Devuelve el identificador del sitio
	 * @return
	 * @throws RemoteException
	 */
	public String getWebId() throws RemoteException {

		UnsignedIntHolder getWebResult = new UnsignedIntHolder();
		_sWebMetadataHolder sWebMetadata = new _sWebMetadataHolder();
		ArrayOf_sWebWithTimeHolder vWebs = new ArrayOf_sWebWithTimeHolder();
		ArrayOf_sListWithTimeHolder vLists = new ArrayOf_sListWithTimeHolder();
		ArrayOf_sFPUrlHolder vFPUrls = new ArrayOf_sFPUrlHolder();
		StringHolder strRoles = new StringHolder();
		ArrayOfStringHolder vRolesUsers = new ArrayOfStringHolder();
		ArrayOfStringHolder vRolesGroups = new ArrayOfStringHolder();

		_siteDataSvc.getWeb(getWebResult, sWebMetadata, vWebs, vLists, vFPUrls, strRoles, vRolesUsers, vRolesGroups);
		return sWebMetadata.value.getWebID();
	}

	/**
	 * Inicializa el conector
	 * @throws Exception
	 */
	private void initCommon() throws Exception
	{
		String user = getRepositoryUser();
		String password = getRepositoryPassword();
		//SiteData.asmx
		SiteDataLocator siteDataSvc = new SiteDataLocator();
		siteDataSvc.setUser(user);
		siteDataSvc.setPassword(password);
		siteDataSvc.setSiteDataSoapEndpointAddress(getSiteDataUrlWS());
		siteDataSvc.setSiteDataSoap12EndpointAddress(getSiteDataUrlWS());
		_siteDataSvc = siteDataSvc.getSiteDataSoap();
		_siteId = getWebId();

		//Lists.asmx
		ListsLocator listsSvc = new ListsLocator();
		listsSvc.setUser(user);
		listsSvc.setPassword(password);
		listsSvc.setListsSoapEndpointAddress(getListsUrlWS());
		listsSvc.setListsSoap12EndpointAddress(getListsUrlWS());
		_listsSvc = listsSvc.getListsSoap();

		//Copy.asmx
		CopyLocator copySvc = new CopyLocator();
		copySvc.setUser(user);
		copySvc.setPassword(password);
		copySvc.setCopySoapEndpointAddress(getCopyUrlWS());
		copySvc.setCopySoap12EndpointAddress(getCopyUrlWS());
		_copySvc = copySvc.getCopySoap();

		//Dws.asmx
		DwsLocator dwsSvc = new DwsLocator();
		dwsSvc.setUser(user);
		dwsSvc.setPassword(password);
		dwsSvc.setDwsSoapEndpointAddress(getDwsUrlWS());
		dwsSvc.setDwsSoap12EndpointAddress(getDwsUrlWS());
		_dwsSvc = dwsSvc.getDwsSoap();

		//Webs.asmx
//		WebsLocator websSvc = new WebsLocator();
//		websSvc.setUser(user);
//		websSvc.setPassword(password);
//		_websSvc = websSvc.getWebsSoap();
	}

	private String getSiteDataUrlWS() throws Exception {
		String url = getRepositoryUrl() + SITEDATA_PATH_WS;
		return url;
	}

	private String getListsUrlWS() throws Exception {
		String url = getRepositoryUrl() + LISTS_PATH_WS;
		return url;
	}

	private String getCopyUrlWS() throws Exception {
		String url = getRepositoryUrl() + COPY_PATH_WS;
		return url;
	}

	private String getDwsUrlWS() throws Exception {
		String url = getRepositoryUrl() + DWS_PATH_WS;
		return url;
	}

	// Returns the contents of the file in a byte array.
    public byte[] inputToByteArray(InputStream in, int length) throws IOException {
//        // You cannot create an array using a long type.
//        // It needs to be an int type.
//        // Before converting to an int type, check
//        // to ensure that file is not larger than Integer.MAX_VALUE.
//        if (length > Integer.MAX_VALUE) {
//            // File is too large
//        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=in.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Error al convertir inputStream to ByteArray ");
        }
        // Close the input stream and return bytes
        in.close();
        return bytes;
    }

	/**
	 * Actualiza el valor de un metadato de un documento
	 * @param sguid. GUID del documento.
	 * @param field. Nombre del metadato.
	 * @param value. Valor del metadato.
	 * @throws Exception
	 */
	public void updateField(String sguid, String field, String value) throws Exception{

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Actualizando propiedad '"+field+"' del documento segun el identificdaor '"+sguid+"'");
			}
			UpdateListItemsUpdates updatesElements = new UpdateListItemsUpdates();

			Element updateXml = buildUpdatesXml(sguid, "", field, value, MossConstants.ACTION_UPDATE);
			final MessageElement[] meArray = new MessageElement[]{new MessageElement(updateXml)};
			updatesElements.set_any(meArray);

			//¡¡¡Parece ser que no es obligatorio hacer checkout al actualizar propiedades aunque este configurado asi!!! (Supongo que se realizara de forma implicita el metodo)
			UpdateListItemsResponseUpdateListItemsResult updatesResponse = _listsSvc.updateListItems(getRepositoryLibrary(), updatesElements);
			//Comprobamos el resultado
			checkResult(updatesResponse);
		} catch (Throwable t) {
			logger.error("Sharepoint UpdateField GUID=" + sguid + ", Field=" + field + ", Value=" + value + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint UpdateField GUID=" + sguid + ", Field=" + field + ", Value=" + value + " - Error=" + t.getMessage(), t);
		}
	}

	/**
	 * Actualiza los metadatos de un documento
	 * @param sguid. GUID del documento
	 * @param properties. Metadatos que se reciben de ASINA
	 * @throws Exception
	 */
	public void updateFields(String sguid, String properties) throws Exception{

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Actualizando propiedades del documento segun el identificdaor '"+sguid+"'");
			}
			if (StringUtils.isNotEmpty(properties)){
				UpdateListItemsUpdates updatesElements = new UpdateListItemsUpdates();
				Element updateXml = buildUpdatesXml(sguid, properties, "", "", MossConstants.ACTION_UPDATE);
				final MessageElement[] meArray = new MessageElement[]{new MessageElement(updateXml)};
				updatesElements.set_any(meArray);
				//¡¡¡Parece ser que no es obligatorio hacer checkout al actualizar propiedades aunque este configurado asi!!! (Supongo que se realizara de forma implicita el metodo)
				UpdateListItemsResponseUpdateListItemsResult updatesResponse = _listsSvc.updateListItems(getRepositoryLibrary(), updatesElements);
				//Comprobamos el resultado
				checkResult(updatesResponse);
			}
		} catch (Throwable t) {
			logger.error("Sharepoint UpdateFields GUID=" + sguid + ", Properties=" + properties + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint UpdateFields GUID=" + sguid + ", Properties=" + properties + " - Error=" + t.getMessage(), t);
		}
	}

	public String uploadDocument(InputStream in, String guid, int length, String properties, boolean update) throws Exception {

		try {
			if (logger.isDebugEnabled()){
				if (update){
					logger.debug("Procediendo a sobreescribir documento con identificdaor '"+guid+"'");
				}else{
					logger.debug("Procediendo a subir nuevo documento");
				}
			}

			String docName = null;
			String docPath = null;
			String folderPath = null;
			boolean overwrite = false;

			String oldGuid = guid;

			if (update) {

				// Actualizar documento
				Node node = null;

				// Path del documento en la tabla auxiliar
				docPath = getDocumentPath(guid);
				if (StringUtils.isNotBlank(docPath)) {
					// Obtener el documento a partir de su path
					node = getItemByPath(docPath);
				}

				if (node == null) {
					// Obtener el documento a partir de su guid
					node = getItem(guid);
				}

				if (node != null) {

					docName = getDocumentName(node);
					folderPath = getDocumentFolderPath(node);

					String oldExtension = StringUtils.substring(docName, StringUtils.lastIndexOf(docName, '.')+1);
					String newDocName = getDocName(properties);
					if (newDocName != null) {

						String newExtension = StringUtils.substring(newDocName, StringUtils.lastIndexOf(newDocName, '.')+1);
						overwrite = StringUtils.equals(newExtension, oldExtension);
						if (!overwrite) {
							docName = newDocName;
						}
					} else {
						// En las propiedades del documento a actualizar, si no se recibe el nombre para el nuevo documento
						// se supone que va a ser el mismo que el documento original, por lo que se mantiene la extensión y
						// se puede sobreescribir el documento original
						overwrite = true;
					}
				} else {
					throw new Exception("No se encontró el documento " + guid);
				}
			} else {
				// Nuevo documento a guardar
				docName = getDocName(properties);

				try{
					folderPath = getFolderPath(properties);
				} catch(Exception e){
					logger.error(e.getMessage());
					throw new Exception("Error al obtener la ruta de la carpeta (FolderPath) donde almacenar el documento");
				}

				if (StringUtils.isNotBlank(folderPath) && !existFolder(folderPath)){
					createRecursiveFolder(folderPath);
				}
			}

			docPath = folderPath + MossConstants.SLASH + docName;
			String destinationUrl = getAbsolutePath(docPath);

			/*
			String docName = null;
			if (update){
				docName = getDocumentName(guid);
			}else{
				docName = getDocName(properties);
			}

			String folderPath = null;
			try{
				folderPath = getFolderPath(properties);
			}catch(Exception e){
				logger.error(e.getMessage());
			}

			//Si es un nuevo documento se comprueba la existencia de la carpeta
			if (!update){
				if (folderPath == null)
					throw new Exception("Error al obtener la ruta de la carpeta (FolderPath) donde almacenar el documento");
				if (!existFolder(folderPath)){
					createRecursiveFolder(folderPath);
				}
			}else if (update && folderPath == null){
				folderPath = getDocumentFolderPath(guid);
			}
			*/

			FieldInformation[] fields = getFieldsInformation(properties);

			byte[] stream = inputToByteArray(in, length);
			UnsignedIntHolder copyIntoItemsResult = new UnsignedIntHolder();
			CopyResultCollectionHolder results = new CopyResultCollectionHolder();

			/*
			boolean overwrite = false;
			//Si se quiere actualizar el documento y el antiguo y el nuevo tienen la misma extension entonces se sobrescribira, en caso contrario
			//se eliminara el existente y se insertar el nuevo.
			if (update){
				String newExtension = StringUtils.substring(docName, StringUtils.lastIndexOf(docName, '.')+1);
				String oldExtension = getDocumentExtension(guid);
				overwrite = StringUtils.equals(newExtension,oldExtension );
			}

			//TODO ¿Habria que comprobar si el folderPath del documento anterior y del nuevo son iguales?
			String destinationUrl = null;
			if(overwrite){
				destinationUrl = getAbsolutePath(folderPath + MossConstants.SLASH + getDocumentName(guid));
			}else{
				destinationUrl = getAbsolutePath(folderPath + MossConstants.SLASH + docName);
			}
			*/

			//Si se quiere sobreescribir o ya existe un documento con el mismo nombre se realiza el check out
			boolean checkout = overwrite;
			if (!checkout) {

				Node existNode = getItem(folderPath, docName);
				if (existNode != null) {
					checkout = true;

					// Obtener el UID del documento ya existente con el mismo nombre
					String uniqueId = getFieldValue(existNode, MossConstants.PROPERTY_UNIQUEID);
					if (StringUtils.isNotBlank(uniqueId)) {
						guid = cleanId(uniqueId);
					}
				}
			}

			if (checkout) {
				if (logger.isDebugEnabled()){
					logger.debug("Haciendo check out del documento '"+destinationUrl+"'");
				}
				_listsSvc.checkOutFile(destinationUrl, "true", "");
			}
			/*
			boolean checkout = false;
			if (overwrite || existDocument(folderPath, docName)){
			//if (overwrite){
				if (logger.isDebugEnabled()){
					logger.debug("Haciendo check out del documento '"+destinationUrl+"'");
				}
				_listsSvc.checkOutFile(destinationUrl, "true", "");
				checkout = true;
			}
			*/
			/*
			else if (update){
				deleteDocument(guid);
			}
			*/

			try{
				if (logger.isDebugEnabled()){
					logger.debug("Subiendo documento '"+destinationUrl+"'");
				}
				//_copySvc.copyIntoItems(getRepositoryUrl(), new String[]{destinationUrl}, fields, stream, copyIntoItemsResult, results);
				_copySvc.copyIntoItems(docName, new String[]{destinationUrl}, fields, stream, copyIntoItemsResult, results);

				if (results.value.length > 0){
					if (!StringUtils.equals(CopyErrorCode._Success, results.value[0].getErrorCode().getValue())){
						throw new Exception(results.value[0].getErrorMessage());
					}
				}
			} catch (Exception e) {
				logger.error("Error al copiar el documento: " + e.getMessage(), e);
				throw e;
			} finally {
				//Si se quiere sobreescribir o ya existe un documento con el mismo nombre se debe realizar el check in
				//if (overwrite || existDocument(folderPath, docName)){
				//Si se ha realizado el check out se debe realizar el check in
				if (checkout) {
					if (logger.isDebugEnabled()){
						logger.debug("Haciendo check in del documento '"+destinationUrl+"'");
					}
					_listsSvc.checkInFile(destinationUrl, "CheckIn", "1");
				}
			}

			//Se renombra cuando proceda
			/*
			if (overwrite){
				renameDocument(guid, destinationUrl, docName);
			}
			*/

			if (!checkout && (!update || (update && !overwrite))) {

				// Obtener el UID del nuevo documento creado
				// al subir un nuevo documento o actualizar uno anterior que no se ha podido sobreescribir
				Node node = getItem(folderPath, docName, buildListItemsViewFields(new String[]{MossConstants.METADATA_UNIQUEID}));
				String uniqueId = getFieldValue(node, MossConstants.PROPERTY_UNIQUEID);
				if (StringUtils.isNotBlank(uniqueId)) {

					guid = cleanId(uniqueId);
					// Guardar el path del documento en la tabla auxiliar
					insertDocumentPath(guid, docPath);
				} else {
					logger.error("Error al obtener el UID del nuevo documento");
					throw new Exception("Error al obtener el UID del nuevo documento");
				}
			}

			if (update && !overwrite) {
				// Eliminar el anterior documento
				try {
					deleteDocument(oldGuid);
				} catch (Exception e) {
					logger.error("Error al borrar el documento anterior: " + e.getMessage(), e);
					// Eliminar el nuevo
					deleteDocument(guid);
					throw e;
				}

				// Caso especial que cuando al actualizar sin poder sobreescribir,
				// el nuevo documento creado ya existiese en el gestor documental
				// pero no existe en la tabla auxiliar
				if (checkout && StringUtils.isBlank(getDocumentPath(guid))) {
					// Guardar el path del documento en la tabla auxiliar
					insertDocumentPath(guid, docPath);
				}
			}

			if (logger.isDebugEnabled()){
				logger.debug("Subido documento con identificador '"+guid+"'");
			}

			return guid;

		} catch (Throwable t) {
			logger.error("Sharepoint UploadDocument=" + properties + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint UploadDocument=" + properties + " - Error=" + t.getMessage(), t);
		}
	}

	private Map getFieldDisplayNames() throws Exception {

		UnsignedIntHolder getListResult = new UnsignedIntHolder();
		_sListMetadataHolder sListMetadata = new _sListMetadataHolder();
		ArrayOf_sPropertyHolder vProperties = new ArrayOf_sPropertyHolder();

		_siteDataSvc.getList(getRepositoryLibrary(), getListResult, sListMetadata, vProperties);

		Map fields = new HashMap();
		for (int i = 0; i < vProperties.value.length; i++) {

			_sProperty property = vProperties.value[i];
			fields.put(property.getName(), property.getTitle());
		}

		return fields;
	}

	private FieldInformation[] getFieldsInformation(String properties) throws Exception {
		//Se obtienen los metadatos a actualizar segun las propiedades aportadas
		List fields = new ArrayList();
		if (StringUtils.isNotEmpty(properties)){
			List mappings = getCurrentRepository().getMetaDataMappings().getMappings();
			// Obtener los displayNames de los campos internos
			Map fieldDisplayNames = getFieldDisplayNames();
			for(int i=0; i < mappings.size(); i++){
				Mapping mapping = (Mapping)mappings.get(i);
				String value = "";
				if (StringUtils.equals(mapping.getSource().getType(), ConfigConstants.CONFIG_MAPPING_SOURCE_TYPE_CONSTANT)){
					value = mapping.getSource().getValue();
				}else if (StringUtils.equals(mapping.getSource().getType(), ConfigConstants.CONFIG_MAPPING_SOURCE_TYPE_DYNAMIC)){
					value = TokenEvaluator.evaluateDynamicToken(mapping.getSource().getValue());
				}else if (StringUtils.equals(mapping.getSource().getType(), ConfigConstants.CONFIG_MAPPING_SOURCE_TYPE_TOKEN)){
					value = TokenEvaluator.evaluateToken(getCurrentRepository(), getCurrentRepository().getTokens().getToken(mapping.getSource().getValue()), properties);
				}
				if (StringUtils.isNotEmpty(value)){
					FieldInformation field = new FieldInformation();
					FieldType fieldType = getSPFieldType(mapping.getDestination().getType());
					field.setType(fieldType);
					field.setInternalName(mapping.getDestination().getValue());
					field.setDisplayName((String)fieldDisplayNames.get(field.getInternalName()));
					field.setValue(TokenEvaluator.formatValue(value, mapping.getSource().getFormat(),mapping.getDestination().getFormat(), mapping.getDestination().getType()));
					fields.add(field);
				}
			}
		}
		FieldInformation[] fieldsInf = new FieldInformation[fields.size()];
		int i = 0;
		for (Iterator iterator = fields.iterator(); iterator.hasNext();) {
			FieldInformation field = (FieldInformation) iterator.next();
			fieldsInf[i++] = field;
		}

		return fieldsInf;
	}

	/*
	private void renameDocument(String sguid, String destinationUrl, String newDocumentName) throws Exception{
		//TODO Renombrar fichero. Si la biblioteca esta configurada con check out obligatorio, si no se hace da error, en cambio si se hace al actualizar el nombre
		 //da error indicando que el fichero esta check out.
//		UpdateListItemsUpdates updatesElements = new UpdateListItemsUpdates();
//		Element updateXml = buildUpdatesXml(sguid, null, "BaseName", newDocumentName, MossConstants.ACTION_RENAME);
//		final MessageElement[] meArray = new MessageElement[]{new MessageElement(updateXml)};
//		updatesElements.set_any(meArray);
//
//		//_listsSvc.checkOutFile(destinationUrl, "true", "");
//
//		//¡¡¡Parece ser que no es obligatorio hacer checkout al actualizar propiedades aunque este configurado asi!!! (Supongo que se realizara de forma implicita el metodo)
//		UpdateListItemsResponseUpdateListItemsResult updatesResponse = _listsSvc.updateListItems(getRepositoryLibrary(), updatesElements);
//
//
//		//_listsSvc.checkInFile(destinationUrl, "CheckIn", "1");
//		//Comprobamos el resultado
//		checkResult(updatesResponse);

	}
	*/

	/**
	 * Obtiene el tamaño de un documento
	 * @param sguid. GUID del documento
	 * @return.
	 * @throws Exception
	 */
	public int getDocumentSize(String sguid) throws Exception {

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Consultando dimension del documento segun el identificador '"+sguid+"'");
			}
			int result = 0;

			String value = getFieldValue(sguid, MossConstants.METADATA_FILESIZE);
			if (StringUtils.isNotBlank(value)){
				result = Integer.parseInt(value);
			}
			if (logger.isDebugEnabled()){
				logger.debug("Dimension del del documento segun el identificador '"+sguid+"' obtenida '"+result+"KB'");
			}
			return result;
		} catch (Throwable t) {
			logger.error("Sharepoint GetDocumentSize GUID=" + sguid + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint GetDocumentSize GUID=" + sguid + " - Error=" + t.getMessage(), t);
		}
	}

	/**
	 * Obtiene el MimeType asociado a un documento
	 * @param sguid. GUID del documento
	 * @return Mimetype del documento basandose en la extensión del nombre del documento
	 * @throws Exception
	 */
	public String getMimeType(String sguid) throws Exception {

		try {
			if (logger.isDebugEnabled()){
				logger.debug("Consultando tipo mime del documento segun el identificador '"+sguid+"'");
			}

			String extension = getDocumentExtension(sguid);
			return MimetypeMapping.getMimeType(extension);
		} catch (Throwable t) {
			logger.error("Sharepoint GetMimeType GUID=" + sguid + " - Error=" + t.getMessage() + " - Cause=" + t.getCause(), t);
			throw new Exception("Error in Sharepoint GetMimeType GUID=" + sguid + " - Error=" + t.getMessage(), t);
		}
	}

	/**
	 * Devuelve la configuración de un repositorio
	 * @param repId. Identificador del repositorio en el archivo de configuración
	 * @return
	 * @throws Exception
	 */
	public String getRepositoryInfo(String repId) throws Exception{
		//TODO Retornar estructura del repositorio
//		JAXBContext jc = JAXBContext.newInstance(CONFIG_SCHEMA);
//		Marshaller m = jc.createMarshaller();
//		Repository repFound = null;
//		for (Repository rep : getRepositories().getRepository())
//		{
//			if (rep.getId().equals(repId))
//			{
//			   repFound = rep;
//			   break;
//			}
//		}
//		if (repFound == null){
//			throw new Exception("Repositorio con id: " + repId + " no encontrado");
//		}
//		StringWriter output = new StringWriter();
//		m.marshal(repFound, output);
//		return output.toString();
//		String document = "http://istgodesa01/QUERES/f1/f2/f3/_docTRES.doc";
//		String repositoryInfo = _dwsSvc.getDwsMeta	Data(document, "", true);
//		return repositoryInfo;
		return null;
	}

	private Repository getCurrentRepository(){
		if (_repositoryAlias != null){
			return _repositories.getRepositoryByAlias(_repositoryAlias);
		}else if (_repositoryId != 0){
			return _repositories.getRepositoryById(_repositoryId);
		}
		return _repositories.getDefaultRepository();
	}

	private String getDocName(String properties) throws Exception {
		String value = null;
		String tokenDocumentName = getCurrentRepository().getProperty(MossConstants.CONFIG_PROPERTY_TOKEN_DOCUMENT_NAME);
		Token token = getCurrentRepository().getTokens().getToken(tokenDocumentName);
		value = TokenEvaluator.evaluateToken(getCurrentRepository(), token, properties);
		return value;
	}

	private String getFolderPath(String properties) throws Exception {
		String folderPath = getCurrentRepository().getFolderPath();
		folderPath = TokenEvaluator.evaluateTokenString(getCurrentRepository(), folderPath, properties);
		return folderPath;
	}

//	private String getRepositoryAlias() throws Exception{
//		String value = getCurrentRepository().getAlias();
//		return value;
//	}

	private String getRepositoryLibrary() throws Exception{
		String value = getCurrentRepository().getProperty(MossConstants.REPOSITORY_PROPERTY_LIBRARY);
		return value;
	}

	private String getSitePath() throws Exception{
		String value = getCurrentRepository().getProperty(MossConstants.REPOSITORY_PROPERTY_SITEPATH);
		return value;
	}

	private String getRepositoryUrl() throws Exception{
		// Obtener la URL de la aplicacion Web del sitio
		String value = getCurrentRepository().getProperty(MossConstants.REPOSITORY_PROPERTY_SITEURL);
		String sitePath = getSitePath();
		if (StringUtils.isNotBlank(sitePath)) {
			value += MossConstants.SLASH + sitePath;
		}
		return value;
	}

	private String getRepositoryUser() throws Exception{
		String value = getCurrentRepository().getProperty(MossConstants.REPOSITORY_PROPERTY_USER);
		return value;
	}

	private String getRepositoryPassword() throws Exception{
		String value = getCurrentRepository().getProperty(MossConstants.REPOSITORY_PROPERTY_PASSWORD);
		return value;
	}

	/*
	private String getDocumentUrl(String sguid) throws Exception {
		String documentPath = getFieldValue(sguid, MossConstants.METADATA_FILEREF);
		documentPath = cleanLibrary(documentPath);
		return getAbsolutePath(documentPath);
	}
	*/

	private String cleanLibrary(String documentPath) throws Exception{
		String library = getRepositoryLibrary() + MossConstants.SLASH;
		return StringUtils.substring(documentPath, StringUtils.indexOf(documentPath, library) + library.length() - 1);
	}

	private String cleanDocumentName(String documentPath) {
		return StringUtils.substring(documentPath, 0, StringUtils.lastIndexOf(documentPath, MossConstants.SLASH));

	}

	private String cleanId(String property) {
		return StringUtils.substring(property, StringUtils.lastIndexOf(property, MossConstants.METADATA_SEPARATOR)+1);
	}

	private String getDocumentExtension(String sguid) throws Exception{
		String documentName = getFieldValue(sguid, MossConstants.PROPERTY_FILENAME);
		String extension = StringUtils.substring(documentName, StringUtils.lastIndexOf(documentName, '.')+1);
		return extension;
	}

	/*
	private String getDocumentName(String sguid) throws Exception{
		String documentName = getFieldValue(sguid, MossConstants.PROPERTY_FILENAME);
		return documentName;
	}
	*/

	private String getDocumentName(Node node) throws Exception{
		String documentName = getFieldValue(node, MossConstants.PROPERTY_FILENAME);
		return documentName;
	}

	/**
	 * Se comprueba el codigo de error para detectar si se ha producido un fallo.
	 * @param updatesResponse
	 * @throws Exception
	 */
	private void checkResult(UpdateListItemsResponseUpdateListItemsResult updatesResponse) throws Exception {
		if (updatesResponse.get_any().length > 0){
			Element node = (Element) updatesResponse.get_any()[0];
			String errorCode = XmlFacade.get(node, "//*[local-name() = 'ErrorCode']");
			if (!StringUtils.equals(errorCode, MossConstants.ERRORCODE_OK)){
				String errorText = XmlFacade.get(node, "//*[local-name() = 'ErrorText']");
				throw new Exception("["+errorCode+"]"+errorText);
			}
		}
	}

	private Node getRowResult(GetListItemsResponseGetListItemsResult items){
		Node result = null;
		if (items.get_any().length > 0){
			Element node = (Element) items.get_any()[0];
			String ows_IDsXpath = "//*[local-name() = 'row']";
			result = XmlFacade.getSingleNode(node, ows_IDsXpath);
		}
		return result;
	}

	private GetListItemsQueryOptions getQueryOptions(String queryOptions){
		GetListItemsQueryOptions queryMossOptions = new GetListItemsQueryOptions();
		final MessageElement[] meArrayQO = {getMeFromString(queryOptions)};
		queryMossOptions.set_any(meArrayQO);
		return queryMossOptions;
	}

	private GetListItemsQuery getQuery(String query){
		GetListItemsQuery queryMoss = new GetListItemsQuery();
		final MessageElement[] meArray = {getMeFromString(query)};
		queryMoss.set_any(meArray);
		return queryMoss;
	}

	private GetListItemsQueryOptions getQueryOptionsRecursiveSearch() {
		//String queryOptions = "<QueryOptions><ViewAttributes Scope='Recursive'/></QueryOptions>";
		String queryOptions = "<QueryOptions><IncludeMandatoryColumns>False</IncludeMandatoryColumns><ViewAttributes Scope='Recursive'/></QueryOptions>";
		return getQueryOptions(queryOptions);
	}

	private GetListItemsQuery getQueryByUniqeId(String sguid) {
		//String query = "<Query><Where><Eq><FieldRef Name='UniqueId'/><Value Type='Text'>"+sguid+"</Value></Eq></Where></Query>";
		String query = "<Query><Where><Eq><FieldRef Name='UniqueId'/><Value Type='Lookup'>"+sguid+"</Value></Eq></Where></Query>";
		return getQuery(query);
	}

	private GetListItemsQueryOptions getQueryOptionsUrl(String folderPath) throws Exception {
		String url = getParentFolder(getAbsolutePath(folderPath));
		String queryOptions = "<QueryOptions><Folder>" + url + "</Folder></QueryOptions>";
		return getQueryOptions(queryOptions);
	}

	private GetListItemsQuery getQueryExistFolder(String folderPath) {
		//Nos quedamos solo con las carpetas, las que tienen en la columna 'ContentType' el valor 'Folder'
		String lastFolderName = getLastFolderName(folderPath);

		//He probado a realizar la consulta filtrando por el nombre con las propiedades LinkFilename, Title y la unica por la que funcion es con FileLeafRef
		String query = "<Query><Where><And><Eq><FieldRef Name='FSObjType'/><Value Type='Text'>1</Value></Eq><Eq><FieldRef Name='FileLeafRef'/><Value Type='Text'>"+lastFolderName+"</Value></Eq></And></Where></Query>";
		return getQuery(query);
	}

	private GetListItemsQuery getQueryRelativeUrl(String folderPath, String documentName) throws Exception {

		//Componemos la consulta para obtener los datos segun la url relativa (sin la webAppUrl)
		String documentPath = documentName;
		if (StringUtils.isNotBlank(folderPath)) {
			documentPath = folderPath + MossConstants.SLASH + documentPath;
		}

		return getQueryRelativeUrl(documentPath);
	}

	private GetListItemsQuery getQueryRelativeUrl(String documentPath) throws Exception {
		//Componemos la consulta para obtener los datos segun la url relativa (sin la webAppUrl)
		String relativeUrl = getSitePath();
		if (StringUtils.isNotBlank(relativeUrl)) {
			relativeUrl += MossConstants.SLASH;
		}
		relativeUrl += getRepositoryLibrary();
		if (!StringUtils.startsWith(documentPath, MossConstants.SLASH)){
			relativeUrl += MossConstants.SLASH;
		}
		relativeUrl += documentPath;

		//String query = "<Query><Where><Eq><FieldRef Name='FileRef'/><Value Type='Text'>"+relativeUrl+"</Value></Eq></Where></Query>";
		String query = "<Query><Where><Eq><FieldRef Name='FileRef'/><Value Type='Lookup'>"+relativeUrl+"</Value></Eq></Where></Query>";
		return getQuery(query);
	}

	private FieldType getSPFieldType(String destinationType) {
		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_STRING))
			return FieldType.Text;
		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_INTEGER))
			//return FieldType.Integer;
			return FieldType.Number;
		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_FLOAT))
			return FieldType.Number;
		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_DATE))
			return FieldType.DateTime;
		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_DATETIME))
			return FieldType.DateTime;
		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_BOOLEAN))
			return FieldType.Boolean;
		if (StringUtils.equals(destinationType, ConfigConstants.CONFIG_DESTINATION_TYPE_CURRENCY))
			return FieldType.Currency;

		return null;
	}

	/*
	 * MEJORA DEL RENDIMIENTO PARA CONSULTAR DOCUMENTOS:
	 * TABLA AUXILIAR QUE RELACIONA EL GUID DEL DOCUMENTO CON SU PATH.
	 *
	 */
	//private static int TNL_DOC_PATH_COL_GUID_INDEX = 1;
	private static int TNL_DOC_PATH_COL_PATH_INDEX = 2;

	private String valueFromQuery(String sql, int index) throws ISPACException {
		String result = null;

		DbCnt cnt = null;
		try {
			cnt = m_clientCtx.getConnection();
			DbQuery dbQuery = cnt.executeDbQuery(sql);
			if (dbQuery.next())
				result = dbQuery.getString(index);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}

		return result;
	}

	private void directExec(String sql) throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = m_clientCtx.getConnection();
			cnt.directExec(sql);
		} finally {
			m_clientCtx.releaseConnection(cnt);
		}
	}

	private String getDocumentPath(String guid) throws ISPACException {
		String result = null;

		try {
			result = valueFromQuery("SELECT * FROM DOC_PATH WHERE GUID = '" + guid
					+ "'", TNL_DOC_PATH_COL_PATH_INDEX);
		} catch (Exception e) {
		}

		return result;
	}

	/*
	private String getDocumentGuid(String path) throws ISPACException {
		String result = null;

		try {
			result = valueFromQuery("SELECT * FROM DOC_PATH WHERE PATH = '"
					+ path + "'", TNL_DOC_PATH_COL_GUID_INDEX);
		} catch (Exception e) {
		}

		return result;
	}
	*/

	private void insertDocumentPath(String guid, String path) throws ISPACException {

		try {
			directExec("INSERT INTO DOC_PATH (GUID,PATH) VALUES ('" + guid + "','" + DBUtil.replaceQuotes(path) + "')");
		} catch (Exception e) {
			logger.info("Error al insertar el path del documento en la tabla intermedia DOC_PATH", e);
		}
	}

	private void deleteDocumentPath(String guid) throws ISPACException {
		try {
			directExec("DELETE FROM DOC_PATH WHERE GUID = '" + guid + "'");
		} catch (Exception e) {
			logger.info("Error al eliminar el path del documento en la tabla intermedia DOC_PATH", e);
		}
	}

	/*
	private void updateDocumentRelativePath(String guid, String path) throws ISPACException {
		try {
			directExec("UPDATE DOC_PATH SET PATH = '" + path + "' WHERE GUID = '" + guid + "'");
		} catch (Exception e) {
		}
	}
	*/

	/*
	 * MÉTODOS MODIFICADOS QUE UTILIZAN LA TABLA AUXILIAR
	 */
	private String getDocumentUrl(String sguid) throws Exception {

		// Obtener el path del documento de la tabla auxiliar
		String documentPath = getDocumentPath(sguid);
		if (documentPath == null) {

			// Obtener el documento a partir de su guid
			Node node = getItem(sguid, buildListItemsViewFields(new String[]{MossConstants.METADATA_FILEREF}));
			documentPath = getFieldValue(node, MossConstants.PROPERTY_FILEREF);
			documentPath = cleanLibrary(documentPath);

			// Guardar el path del documento en la tabla auxiliar
			insertDocumentPath(sguid, documentPath);
		}

		return getAbsolutePath(documentPath);
	}

	/*
	 * NUEVOS MÉTODOS
	 */
	private Node getItemByGuid(String sguid) throws Exception {
		GetListItemsViewFields viewFields = null;
		String[] properties = getMetaDatas();
		if (properties != null) {
			viewFields = buildListItemsViewFields(properties);
		}
		return getItemByGuid(sguid, viewFields);
	}

	private Node getItemByGuid(String sguid, GetListItemsViewFields viewFields) throws Exception {

		Node node = null;

		String docPath = getDocumentPath(sguid);
		if (docPath != null) {
			// Obtener el documento a partir de su path
			node = getItemByPath(docPath, viewFields);
		} else {
			// Obtener el documento a partir de su guid
			node = getItem(sguid, viewFields);

			// Guardar el path del documento en la tabla auxiliar
			docPath = getFieldValue(node, MossConstants.PROPERTY_FILEREF);
			docPath = cleanLibrary(docPath);
			insertDocumentPath(sguid, docPath);
		}

		return node;
	}
}