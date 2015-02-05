package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacmgr.action.form.CustomBatchForm;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ComprobarDocumentacionAction extends BaseAction {
    
    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form,
    								   HttpServletRequest request, 
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {
        
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		
		// Estado de tramitación
    	IState state = managerAPI.currentState(getStateticket(request));    

    	//Obtener Lista de Documentos en Formato XML
    	String xmlDocumentos = getXmlDocumentos(cct, entitiesAPI, state, request);
    	
    	//Obtener la Lista de Documentos como una colección
    	IItemCollection documentos = getDocumentosFromXML(xmlDocumentos);
    	    	
    	//Si existen documentos para mostrar
    	if (documentos.toList().size() > 0) {
    		
	    	//Guardar el bean con la lista de documentos
	    	request.setAttribute("listDocumentos", documentos);
	    	
	    	//Guardar el Documento XML. El Documento XML se guarda para recuperarlo
	    	//en la Accion de Crear Solicitud de Inscripcion y asi no tener
	    	//que estar generandolo de nuevo.
	    	request.setAttribute("XMLDocumentos", xmlDocumentos);
	    	
	    	//Guardar los documentos seleccionados en el form del jsp
	    	//if (bInfoTramite) setRequestDocSelected(request, xmlDocumentos);
	    	if (request.getAttribute("bInfoTramite") != null) {
	    		setRequestDocSelected(request, xmlDocumentos);
	    	}
    	}
    	else {
	    	ActionErrors actionErrors = new ActionErrors();
	    	actionErrors.add(ActionMessages.GLOBAL_MESSAGE,	new ActionMessage("error.noExisteListaDocsSubsanacion"));
	    	saveMessages(request,actionErrors);
    	}

    	// Comprobamos si ya existe un tramite de 'Solicitud de Subsanación' creado
    	String idTask = ConfigurationMgr.getVarGlobal(cct,  ConfigurationMgr.ID_TASK_SOLICITUD_SUBSANACION);
    	
		IItemCollection itemCol = entitiesAPI.queryEntities(
				SpacEntities.SPAC_TRAMITES,
				new StringBuffer()
					.append("WHERE NUMEXP = '")
					.append(DBUtil.replaceQuotes(state.getNumexp()))
					.append("' AND ID_TRAMITE IN ( SELECT ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE = ")
					.append(idTask)
					.append(" AND ID_PCD = ")
					.append(state.getPcdId())
					.append(" )").toString());
		
		if (itemCol.next()) {
			request.setAttribute("subsanacionCreado",itemCol.value().getKey());
		}
		
    	//Cargamos el contexto de la cabecera (miga de pan)
    	SchemeMgr.loadContextHeader(state, request, getResources(request), session);
		
	    //Menu
	    request.setAttribute("menus", MenuFactory.getCheckDocumentsMenu(cct, state, getResources(request)));
	    
    	//Carga de los datos del Esquema
    	SchemeMgr.loadScheme(mapping, request, session, state);
    	
        //Cargamos el expediente
        //SpacMgr.loadExpedient(session, state, request);
	    
        //Cargamos enlaces para los expedientes relacionados
    	SpacMgr.loadRelatedExpedient(session, request, state.getNumexp(), SpacMgr.ALL_EXPEDIENTS );
	    
    	return mapping.findForward("success");  	
    }
    
    /**
     * Guarda en la request el formulario con los documentos seleccionados
     * @param request
     * @param xmlDocumentos Documento XML que contiene la lista de documentos (Completados y Pendientes).
     */
    private void setRequestDocSelected(HttpServletRequest request, 
    		String xmlDocumentos) {
    	
        String ids = "";
        XmlFacade xmlFacade = new XmlFacade(xmlDocumentos);
        List docIds = xmlFacade.getList("/documentos/documento/@id");
        Iterator it = docIds.iterator();
        while (it.hasNext()) {
            String docId = (String)it.next();
            String pendiente = xmlFacade.get("/documentos/documento[@id='" 
            		+ docId + "']/pendiente");
            if (pendiente.equals("SI")) {
                if (ids.length() > 0) ids += ",";
                ids += docId;
            }
        }
        if (ids.length() > 0) {
            CustomBatchForm customBatchForm = new CustomBatchForm();
            customBatchForm.setMultibox(ids.split(","));
            request.setAttribute("customBatchForm", customBatchForm);
        }
    }
    
    /**
     * Obtiene la lista de Documentos en formato XML.
     * <p>
     * Primero se comprueba si existe una entrada en SPIGA_INFOTRAMITE para un acto de solicitud de subsanación para
     * el Expediente actual. Si existe se obtiene de esta tabla el documento XML con la lista de documentos a subsanar.
     * <p>
     * Si no se encuntra una entra en SPIGA_INFOTRAMITE se obtiene la lista de documentos a subsanar del Catálogo de Procedimientos.
     * @param cct
     * @param entitiesAPI
     * @param state
     * @param request
     * @return
     * @throws ISPACException
     */
    private String getXmlDocumentos(ClientContext cct, IEntitiesAPI entitiesAPI, 
    		IState state, HttpServletRequest request) throws ISPACException {
    	
        String xmlDocumentos = null;
        
        //Obtener Identificador del Tramite de Solicitud de Subsanación
        String idTaskCTStr = ConfigurationMgr.getVarGlobal(cct, 
        		ConfigurationMgr.ID_TASK_SOLICITUD_SUBSANACION);
        
        //Comprobar si existe entrada en InfoTramite para un Acto de Solicitud de Subsanacion para la Fase actual
        String strQuery = "WHERE ID = ( SELECT MAX(ID) FROM SPAC_INFOTRAMITE WHERE NUMEXP = '" + DBUtil.replaceQuotes(state.getNumexp()) + 
        				  "' AND ID_CT_TRAMITE = " + idTaskCTStr + 
        				  " AND ID_PCD = " + state.getPcdId() + 
        				  " AND ID_P_FASE = " + state.getStagePcdId() + " )";
        
        IItemCollection collection = entitiesAPI.queryEntities(
        		SpacEntities.SPAC_INFOTRAMITE, strQuery);
        Iterator it = collection.iterator();
        if (it.hasNext()) {
            xmlDocumentos = ((IItem)it.next()).getString("INFO");
            request.setAttribute("bInfoTramite","true");
            //bInfoTramite = true;
        }
        
        //Si no se ha obtenido el xml de InfoTramite obtener la lista de documentos del catalogo
        if (xmlDocumentos == null) {
            IItem pcd = entitiesAPI.getEntity(
            		SpacEntities.SPAC_CT_PROCEDIMIENTOS,state.getPcdId());
            String documentacion = pcd.getString("DOCUMENTACION");
            String[] arrayDocumentos = null;
            if (documentacion != null) arrayDocumentos = documentacion.split("\n");
            xmlDocumentos = getXmlDocumentosFromArray(arrayDocumentos);
        }
        
        //Si no se ha obtenido lista de documentos devolver el documento XML sin datos
        if (xmlDocumentos == null) xmlDocumentos = "<documentos/>";
        
        return xmlDocumentos;
    }
    
    /**
     * Devuelve las propiedades que tendrá cada Item Documento.
     * <p>
     * Propiedades:
     * <ul>
     *    <li>ID --> Identificador único del documento</li>
     *    <li>DOCUMENTO --> Nombre del Documento</li>
     *    <li>PENDIENTE --> [SI/NO] Indica si el Documento esta pendiente.</li>
     * </ul>
     * @return
     */
    private Properties getProperties() {
        int ordinal = 0;
        Properties propertiesRet = new Properties();
        propertiesRet.add(new Property(ordinal++, "ID", Types.VARCHAR));
        propertiesRet.add(new Property(ordinal++, "DOCUMENTO", Types.VARCHAR));
        propertiesRet.add(new Property(ordinal++, "PENDIENTE", Types.VARCHAR));
        return propertiesRet;
    }
    
    /**
     * Devuelve un bean con una colección de documentos dado un XML de documentos obtenido de INFOTRAMITE
     * @param xmlDocumentos XML con la lista de Documentos.
     * @return
     * @throws ISPACException
     */
    private IItemCollection getDocumentosFromXML(String xmlDocumentos) throws ISPACException  {
        XmlFacade xmlFacade = new XmlFacade(xmlDocumentos);
        List idDocs = xmlFacade.getList("/documentos/documento/@id");
        Iterator it = idDocs.iterator();
        
        Properties properties = getProperties();
        List documentos = new LinkedList();
        while (it.hasNext()) {
            String idDoc = (String)it.next();
            String xmlPath = "/documentos/documento[@id='" + idDoc + "']/";
            IItem documento = new GenericItem(properties, "ID");
            documento.set("ID", idDoc);
            documento.set("DOCUMENTO", xmlFacade.get(xmlPath+"nombre"));
            documento.set("PENDIENTE", xmlFacade.get(xmlPath+"pendiente"));
            documentos.add(documento);
        }
        
        return new ListCollection(documentos);
    }
    
    /**
     * Devuelve un array con nombres de documentos como documento XML.
     * <p>
     * <code>
     * &lt;documentos&gt;</br>
     *    &nbsp;&nbsp;&lt;documento id='[ID]'&gt;</br>
     *       &nbsp;&nbsp;&nbsp;&nbsp;&lt;pendiente>[SI/NO]&lt;/pendiente&gt;</br>
     *       &nbsp;&nbsp;&nbsp;&nbsp;&lt;nombre>[Nombre del Documento]&lt;/nombre&gt;</br>
     *    &nbsp;&nbsp;&lt;/documento&gt;</br>
     * &lt;/documentos&gt;
	 * </code>
	 * 
     * @param listDocumentos Array con los nombres de los documentos
     * @return
     * @throws ISPACException
     */
    private String getXmlDocumentosFromArray(String[] listDocumentos){
        XmlFacade xmlFacade = new XmlFacade("<documentos/>");
        if (listDocumentos == null) return xmlFacade.toString();
        for (int i = 0; i < listDocumentos.length; i++) {
            String xPath = "/documentos/documento[@id='"+Integer.toString(i)+"']";	
            xmlFacade.set(xPath,"");
            xmlFacade.set(xPath+"/pendiente", "NO");
            xmlFacade.set(xPath+"/nombre", listDocumentos[i]);
        }
        
        return xmlFacade.toString();
    }
    
}