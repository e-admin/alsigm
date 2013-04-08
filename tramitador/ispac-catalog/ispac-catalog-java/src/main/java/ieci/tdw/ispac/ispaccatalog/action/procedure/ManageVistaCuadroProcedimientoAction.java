package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaccatalog.action.TreeViewManager;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.CuadroEntidad;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.CuadroEntidadTreeView;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.ElementoCuadro;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.NodeSelectionHandlerAction;
import ieci.tdw.ispac.ispaclib.bean.TreeModel;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.util.ActionRedirect;
import ieci.tdw.ispac.ispacweb.webdav.util.URLEncoder;
import ieci.tdw.ispac.ispacweb.wizard.WizardButton;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ManageVistaCuadroProcedimientoAction extends TreeViewManager implements
		NodeSelectionHandlerAction {
	private static final String SHOW_CONTENT_URL = "showContentURL";
	public final static String CUADRO_PROCEDIMIENTO = "CUADRO_PROCEDIMIENTO";
	public final static String REFRESH_VIEW_KEY = "REFRESH_VIEW_KEY";
	
	

	public ActionForward inithome(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionAPI sessionApi)
			throws ISPACException {
		request.getSession().removeAttribute(CUADRO_PROCEDIMIENTO);
		
		int entityId = Integer.parseInt(request.getParameter("entityId"));
		
		// Comprobar las credenciales del usuario conectado
		FunctionHelper.checkReadOnlyFunctions(request, sessionApi.getClientContext(), entityId);
		
		ActionRedirect ret = new ActionRedirect(mappings.findForward("home"));
		String path = ret.getPath();
		path = path +"&entityId="+request.getParameter("entityId") + "&";
		path = path +"regId="+request.getParameter("regId");
		ret.setPath(path);
		ret.setRedirect(true);
		return ret;
	}
	
	public ActionForward home(ActionMapping mappings, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI sessionApi) throws ISPACException {
		
		// Comprobar si hay que recargar la información del procedimiento
		String reload = request.getParameter("reload");
		if ("true".equals(reload)) {
			request.getSession().removeAttribute(CUADRO_PROCEDIMIENTO);
		}
		
		TreeView tree = loadTreeView(request, sessionApi);
		String pEntityId = request.getParameter("entityId");
		String pRegId = request.getParameter("entityId");
		if (StringUtils.isNotBlank(pEntityId) && StringUtils.isNotBlank(pRegId) ){
			TreeNode treeNode =  tree.getSelectedNode();
			if (treeNode==null){
				Collection nodes = tree.getRootNodes();
				for (Iterator iter = nodes.iterator(); iter.hasNext();) {
					TreeNode nodeToSelect = (TreeNode) iter.next();
					tree.setSelectedNode(nodeToSelect);
					break;
				}
			}
		}
		ElementoCuadro nodoSeleccionado = (ElementoCuadro)tree.getSelectedNode().getModelItem();
		request.setAttribute(SHOW_CONTENT_URL, getNodeUrl(nodoSeleccionado));
		
		createButtons(request, sessionApi, tree);
		
		return mappings.findForward("success");
	}

	private TreeView loadTreeView(HttpServletRequest request, ISessionAPI sessionApi) throws ISPACException{
		TreeView treeView = (TreeView) request.getSession().getAttribute(CUADRO_PROCEDIMIENTO);
		if (treeView == null) {
			int entityId = Integer.parseInt(request.getParameter("entityId"));
			int regId = Integer.parseInt(request.getParameter("regId"));
			String language = sessionApi.getClientContext().getAppLanguage();
			
			// Comprobar las credenciales del usuario conectado
			FunctionHelper.checkReadOnlyFunctions(request, sessionApi.getClientContext(), entityId);

			//obtener jerarquia del procedimiento
			CuadroEntidad cuadro = new CuadroEntidad(entityId, regId, sessionApi, language);
			treeView = new CuadroEntidadTreeView((TreeModel) cuadro, language);
			treeView.setNodeSelectionHandler((NodeSelectionHandlerAction) this);
			request.getSession().setAttribute(CUADRO_PROCEDIMIENTO, treeView);
		}
		return treeView;
	}
	
	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionAPI sessionApi) throws ISPACException  {
		TreeView tree = loadTreeView(request, sessionApi);
        String id = request.getParameter("id");
    	TreeNode node = tree.getSelectedNode();
    	if (node!=null) {
    		createButtons(request, sessionApi, tree);
    	}
        if (StringUtils.isNotBlank(id))
        {
            ActionRedirect view = new ActionRedirect(mappings.findForward("verElemento"));
            view.addParameter("id", id);
            return view;
        }else{
    		return mappings.findForward("done");
        }
	}

	public ActionForward getForwardVistaNodo(TreeNode node, ActionMapping mappings,
			HttpServletRequest request, SessionAPI sessionApi) throws ISPACException  {
		
		return null;
	}

	public ActionForward verNodo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, SessionAPI sessionApi)
			throws Exception {
		return verNodo(mappings, form, request, response, (ISessionAPI)sessionApi);
	}

	public ActionForward verNodo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, ISessionAPI sessionApi)
			throws Exception {

		request.setAttribute(REFRESH_VIEW_KEY, Boolean.TRUE);

		TreeView treeView = loadTreeView(request, sessionApi);
		if (treeView != null && request.getParameter("node") != null) {
			String pNode = request.getParameter("node");
			if (isRootNode(pNode)) {
				ActionForward ret = new ActionForward();
				ret.setPath("/manageVistaCuadroProcedimiento.do?method=vistaproc");
				ret.setRedirect(true);
				return ret;
			} else {
				try {
					TreeNode node = treeView.getNode(URLDecoder.decode(pNode, "ISO-8859-1"));
					if (node != null) {
						treeView.setSelectedNode(node);
						node.setVisible();
						createButtons(request, sessionApi, treeView);
						ElementoCuadro elem = (ElementoCuadro) node.getModelItem();
						if (elem.isEntityPlantillaTipoDoc() || elem.isEntityPlantillaStageTipoDoc()) {
							Boolean bool = new Boolean(sessionApi.getAPI().getTemplateAPI()
									.isProcedureTemplate(Integer.parseInt(elem.getRegId())));
							request.setAttribute("PLANT_ESPECIFICA", bool);						
						}
						return getForwardVistaNodo(node, mappings, request);
					} else {
						String id = request.getParameter("id");
						if (StringUtils.isNotBlank(id)) {
							ActionRedirect view = new ActionRedirect(mappings
									.findForward("verElemento"));
							view.addParameter("id", id);
							return view;
						}
					}
				} catch (UnsupportedEncodingException e) {
				}
			}
		}

		return mappings.findForward("viewRefresher");

	}
    
	protected String composeJScriptShowPcdGuiURL(ClientContext ctx, HttpServletRequest req, int pcdId) throws ISPACException {
	
		
	
		return "javascript:window.top.execDesigner(\"showPcdGUI.do?pcdId=" + pcdId+"&locale="+ctx.getAppLanguage()+ "\");";	
	}

	protected String composeJScriptShowExtendedInfoURL(HttpServletRequest request, ISessionAPI session, boolean show) {
		
		ClientContext cct = session.getClientContext();
    	return new StringBuffer()
			.append("javascript:execConfirm(\"") 
			.append(show ?
					getMessage(request, cct.getLocale(), "procedure.confirm.showExtendedInfo")
					: getMessage(request, cct.getLocale(), "procedure.confirm.hideExtendedInfo"))
			.append("\", \"")
			.append(request.getContextPath())
			.append("/showPcdExtendedInfo.do?show=").append(show)
			.append("\");")
			.toString();
	}

    protected String composeJScriptCreateProcURL(int pcdId){
        return "javascript:window.top.execFromFrame(\"newProcedure.do?action=begin&pcdtype=clone&pcdId=" + pcdId +"&parameters=CLONEPCD\");";
    }

    protected String composeJScriptCreateSubProcURL(int pcdId){
        return "javascript:window.top.execFromFrame(\"newSubProcedure.do?action=begin&pcdtype=clone&pcdId=" + pcdId +"&parameters=CLONESUBPCD\");";
    }
    
    protected String composeJScriptExportProcURL(int pcdId,HttpServletRequest request){
    	// Exportar sin ventana de diálogo
        return "javascript:execRedirect(\"" + request.getContextPath() + "/exportProcedure.do?method=export&pcdId=" + pcdId +"\");";
        // Ventana modal para seleccionar los datos que se van a exportar
        //return "javascript:window.top.execFromFrame(\"exportProcedure.do?method=enter&pcdId=" + pcdId +"\");";
    }

    protected String composeJScriptAddPcdStageURL(int pcdId){
    	URLEncoder encoder = new URLEncoder();

    	String sql = new StringBuffer()
    		.append("WHERE ID NOT IN (SELECT ID_CTFASE FROM SPAC_P_FASES WHERE ID_PCD=")
    		.append(pcdId)
    		.append(")")
    		.append(" ORDER BY ORDEN")
    		.toString();
    	
    	return new StringBuffer()
    		.append("javascript:window.top.execFromFrame(\"selectObject.do")
    		.append("?codetable=SPAC_CT_FASES")
    		.append("&codefield=ID")
    		.append("&valuefield=NOMBRE")
    		.append("&decorator=/formatters/pcdstage/choosepcdstageformatter.xml")
    		.append("&pcdId=").append(pcdId)
    		.append("&caption=catalog.choose.ctstage")
    		.append("&noSearch=s")
    		.append("&textValue=s")
    		.append("&textValueAction=/addPcdStage.do")
    		.append("&sqlquery=").append(encoder.encode(sql))
    		.append("\");")
    		.toString();
    }

    protected String composeJScriptAddActivityURL(int pcdId){
    	return new StringBuffer()
    		.append("javascript:window.top.execFromFrame(\"selectObject.do")
    		.append("?pcdId=").append(pcdId)
    		.append("&caption=catalog.choose.activity")
    		.append("&noSearch=s")
    		.append("&textValue=s")
    		.append("&textValueAction=/addActivity.do")
    		.append("\");")
    		.toString();
    }

    protected String composeJScriptAddTPDocForStage(HttpServletRequest req, int pcdStageId, int pcdId){
    	URLEncoder encoder = new URLEncoder();
    	
    	String sql = new StringBuffer()
			.append("WHERE ID NOT IN (SELECT ID_TPDOC FROM SPAC_P_FSTD WHERE ID_FASE=")
			.append(pcdStageId)
			.append(")")
			.append(" ORDER BY NOMBRE")
			.toString();
	
		return new StringBuffer()
			.append("javascript:window.top.execFromFrame(\"selectObject.do")
			.append("?codetable=SPAC_CT_TPDOC")
			.append("&codefield=ID")
			.append("&valuefield=NOMBRE")
			.append("&decorator=/formatters/choosectstagetpdoc.xml")
			.append("&pcdId=").append(pcdId)
			.append("&pcdStageId=").append(pcdStageId)
			.append("&caption=catalog.choose.cttpdoc")
			.append("&noSearch=s")
			.append("&sqlquery=").append(encoder.encode(sql))
			.append("\");")
			.toString();
    }

    protected String composeJScriptDeletePcdStageURL(HttpServletRequest request, ISessionAPI session, 
    		int stageId, int pcdId) {
    	
    	ClientContext cct = session.getClientContext();
    	return new StringBuffer()
    		.append("javascript:execConfirm(\"")
    		.append(getMessage(request, cct.getLocale(), "procedure.confirm.deleteStage"))
    		.append("\", \"")
    		.append(request.getContextPath())
    		.append("/removePcdStage.do?stageId=").append(stageId)
    		.append("&pcdId=").append(pcdId)
    		.append("\");")
    		.toString();
    }

    protected String composeJScriptDeleteActivityURL(HttpServletRequest request, ISessionAPI session,
    		int activityId, int subpcdId) {
    	
    	ClientContext cct = session.getClientContext();
    	return new StringBuffer()
    		.append("javascript:execConfirm(\"")
    		.append(getMessage(request, cct.getLocale(), "procedure.confirm.deleteActivity"))
    		.append("\", \"")
    		.append(request.getContextPath())
    		.append("/removeActivity.do?activityId=").append(activityId)
    		.append("&pcdId=").append(subpcdId)
    		.append("\");")
    		.toString();
    }

    protected String composeJScriptDeletePcdSyncNodeURL(HttpServletRequest request,	ISessionAPI session,
    		int syncnodeId, int pcdId) {
    	
    	ClientContext cct = session.getClientContext();
    	return new StringBuffer()
    		.append("javascript:execConfirm(\"")
    		.append(getMessage(request, cct.getLocale(), "procedure.confirm.deleteSyncNode"))
    		.append("\", \"")
    		.append(request.getContextPath())
    		.append("/removePcdSyncNode.do?syncnodeId=").append(syncnodeId)
    		.append("&pcdId=").append(pcdId)
    		.append("\");")
    		.toString();
    }

    protected String composeJScriptCreateInputFlowURL(HttpServletRequest req, 
    		int nodeId, int pcdId) {

    	return new StringBuffer()
		.append("javascript:window.top.execFromFrame(\"selectPcdFlow.do")
		.append("?pcdId=").append(pcdId)
		.append("&nodeId=").append(nodeId)
		.append("&flowType=IN")
		.append("\");")
		.toString();
    }

    protected String composeJScriptCreateOutputFlowURL(HttpServletRequest req, 
    		int nodeId, int pcdId) {

    	return new StringBuffer()
			.append("javascript:window.top.execFromFrame(\"selectPcdFlow.do")
			.append("?pcdId=").append(pcdId)
			.append("&nodeId=").append(nodeId)
			.append("&flowType=OUT")
			.append("\");")
			.toString();
    }

    protected String composeJScriptDeleteFlowURL(HttpServletRequest request, ISessionAPI session,
    		int flowId, int pcdId) {
    	
    	ClientContext cct = session.getClientContext();
    	return new StringBuffer()
    		.append("javascript:execConfirm(\"")
    		.append(getMessage(request, cct.getLocale(), "procedure.confirm.deleteFlow"))
    		.append("\", \"")
    		.append(request.getContextPath())
    		.append("//removePcdFlow.do?flowId=").append(flowId)
    		.append("&pcdId=").append(pcdId)
    		.append("\");")
    		.toString();
    }

    protected String composeJScriptAddSyncNodeURL(int pcdId){

    	return new StringBuffer()
			.append("javascript:window.top.execFromFrame(\"showPcdSyncNode.do")
			.append("?pcdId=").append(pcdId)
			.append("\");")
			.toString();
    }

    protected String composeJScriptAddTemplateURL(int idTpDoc, int idProc){
    	URLEncoder encoder = new URLEncoder();
    	String sql = new StringBuffer("WHERE ID_TPDOC =" + idTpDoc + " AND ID IN (SELECT ID_P_PLANTDOC FROM SPAC_P_PLANTILLAS WHERE ID_PCD!=")
    	.append(idProc).append(" AND ID_P_PLANTDOC NOT IN (SELECT ID_P_PLANTDOC FROM SPAC_P_PLANTILLAS WHERE ID_PCD=").append(idProc).append(" ))").toString();
     	return "javascript:window.top.execFromFrame(\"selectObject.do?codetable=SPAC_P_PLANTDOC&codefield=ID&valuefield=NOMBRE"
    	+"&idProc="+idProc 
    	+"&decorator=/formatters/chooseplantdocformatter.xml&caption=catalog.choose.ctplant&sqlquery="
				+ encoder.encode(sql) + "\");";
    }
    
    protected String composeJScriptCreateTemplateURL(int tpDocId){
    	return "javascript:window.top.execFromFrame(\"showTemplateFromCuadroView.do?tipo=especifica&entityId=9&regId=-1&type=" + tpDocId + "\")";
    }
    
    protected String composeJScriptDeleteTemplateURL(HttpServletRequest request, ISessionAPI session, int templateId, int procId)
    throws ISPACException
    {
    	ClientContext cct = session.getClientContext();
		ITemplateAPI templateAPI = session.getAPI().getTemplateAPI();
 		//boolean procedureTemplate = templateAPI.isProcedureTemplate(templateId);
		int numProc = templateAPI.countTemplatesProcedure(templateId);
 		
		if (numProc==1){
 			return "javascript:execConfirm(\"" + getMessage(request, cct.getLocale(), "procedure.posibleBorradoPlantillaUnica")+ "\", \"" + request.getContextPath() + "/removeTemplateFromProc.do?idTemplate=" + templateId + "&pcdId=" + String.valueOf(procId) + "\");";		
 		}
 		else{
 			return "javascript:execRedirect(\"" + request.getContextPath() + "/removeTemplateFromProc.do?idTemplate=" + templateId + "&pcdId=" + String.valueOf(procId) + "\");";
 		}
    	
    }
    
    protected String composeJScriptCreateTaskURL(int idStage, int idPcd){
    	URLEncoder encoder = new URLEncoder();
    	String sql = new StringBuffer("WHERE ID IN (SELECT ID_TRAMITE FROM SPAC_CT_FSTR WHERE ID_FASE IN (SELECT ID_CTFASE FROM SPAC_P_FASES WHERE ID = ")
    	.append(idStage)
    	.append(")) AND ID NOT IN (SELECT ID_CTTRAMITE FROM SPAC_P_TRAMITES WHERE ID_PCD = ")
    	.append(idPcd)
    	.append(" AND ID_FASE = ")
    	.append(idStage)
    	.append(")").toString();
    	return "javascript:window.top.execFromFrame(\"selectObject.do?codetable=SPAC_CT_TRAMITES&codefield=ID&valuefield=NOMBRE&decorator=/formatters/pcdstage/choosepcdstagecttaskformatter.xml&pstageId="
				+ idStage
				+ "&parameters=SPAC_CT_TRAMITES&noSearch=s&caption=catalog.choose.cttask&sqlquery="
				+ encoder.encode(sql) + "\");";
    }
    protected String composeJScriptDeleteStageTPDocURL(HttpServletRequest req,int isfstd){
    	return req.getContextPath()+"/deleteCTStageTPDoc.do?idfstd="+isfstd;
    }

    protected String composeJScriptDeleteTaskURL(HttpServletRequest req,int idTask, int idStage){
    	return req.getContextPath()+"/removePcdStageTask.do?ptaskId="+idTask+"&pstageId=" + idStage;
    }

    protected String composeJScriptTaskUpURL(HttpServletRequest req,int idTask, int idStage){
    	return req.getContextPath()+"/movePcdStageTask.do?ptaskId="+idTask+"&pstageId=" + idStage + "&dir=up";
    }

    protected String composeJScriptTaskDownURL(HttpServletRequest req,int idTask, int idStage){
    	return req.getContextPath()+"/movePcdStageTask.do?ptaskId="+idTask+"&pstageId=" + idStage + "&dir=down";
    }


    
    /**
     * Crea los botones adecuados a la pantalla 
     * @param request Información de la petición del usuario.
     * @param sessionAPI API de sesión.
     * @param treeView Vista del árbol.
     * @throws ISPACException si ocurre algún error.
     */
	protected void createButtons(HttpServletRequest request, 
			ISessionAPI sessionAPI, TreeView treeView) throws ISPACException {
		
		// Lista de botones del árbol
		List buttons = new ArrayList();
		
		// Información del procedimiento
		ElementoCuadro pcd = getPcdElementoCuadro(treeView);
		int pcdId = Integer.parseInt(pcd.getRegId());
		int pcdState = TypeConverter.parseInt(pcd.getProperty("ESTADO"), -1);

		// Obtener las credenciales del usuario conectado
		boolean editionMode = FunctionHelper.userHasFunction(request,
				sessionAPI.getClientContext(),
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT);
		
		// Botón para mostrar la GUI del procedimiento
		//Solo se mostrara si el valor de SHOW_DESIGNER en ispac.properties es true
		if (editionMode
				&& "true".equalsIgnoreCase(ISPACConfiguration.getInstance()
						.get("SHOW_DESIGNER"))) {
			buttons.add(new WizardButton("procedure.button.showPcdGui",
					composeJScriptShowPcdGuiURL(sessionAPI.getClientContext(),
							request, pcdId)));
		}
		
		// Información del cuadro
		CuadroEntidad cuadro = (CuadroEntidad) treeView.getTreeModel();
		if (cuadro.isShowExtendedInfo()) {

			// Botón para ocultar la información extendida
			buttons.add(new WizardButton("procedure.button.hideExtInfo", 
					composeJScriptShowExtendedInfoURL(request, sessionAPI, false)));

		} else {

			// Botón para mostrar la información extendida
			buttons.add(new WizardButton("procedure.button.showExtInfo", 
					composeJScriptShowExtendedInfoURL(request, sessionAPI, true)));

		}
		
		// Elemento seleccionado del cuadro
		ElementoCuadro item = (ElementoCuadro) treeView.getSelectedNode()
			.getModelItem();
		
		if ( item.isEntityProcedimiento()) {

			if (editionMode) {

				// Botón para clonar el procedimiento
				buttons.add(new WizardButton("procedure.button.copy", 
						composeJScriptCreateProcURL(pcdId)));
			}
			
			// Botón para exportar el procedimiento
			buttons.add(new WizardButton("procedure.button.export", 
					composeJScriptExportProcURL(pcdId,request)));
			
			if (editionMode && (pcdState == IProcedure.PCD_STATE_DRAFT)) {
				
				// Botón para añadir fase
				buttons.add(new WizardButton("procedure.button.addFase", 
						composeJScriptAddPcdStageURL(pcdId)));

				// Botón para añadir nodo de sincronización
				if (cuadro.isShowExtendedInfo()) {
					buttons.add(new WizardButton("procedure.button.addSyncNode", 
							composeJScriptAddSyncNodeURL(pcdId)));
				}
			}
        
        } else if (item.isEntitySubProceso()) {

        	if (editionMode) {

        		// Botón para clonar el procedimiento
				buttons.add(new WizardButton("procedure.button.copy", 
						composeJScriptCreateSubProcURL(pcdId)));
			
	        	if (pcdState == IProcedure.PCD_STATE_DRAFT) {
					
					// Botón para añadir fase
					buttons.add(new WizardButton("procedure.button.addActivity", 
							composeJScriptAddActivityURL(pcdId)));
	
					// Botón para añadir nodo de sincronización
					if (cuadro.isShowExtendedInfo()) {
						buttons.add(new WizardButton("procedure.button.addSyncNode", 
								composeJScriptAddSyncNodeURL(pcdId)));
					}
				}
        	}

        } else if( item.isEntityFaseProcedimiento()) {
        	
        	if (editionMode) {
        		
				if (pcdState == IProcedure.PCD_STATE_DRAFT) {
					
					// Botón para eliminar fase
					buttons.add(new WizardButton("procedure.button.deleteFase", 
							composeJScriptDeletePcdStageURL(request, sessionAPI,
									Integer.parseInt(item.getRegId()),
									pcdId)));
				}
				
				//si la fase es del catalogo 
				boolean faseDelCatalago = item.getItem().getString("ID_CTFASE")!="-1";
				if (faseDelCatalago){
					buttons.add(new WizardButton("procedure.button.addTramite", 
		        			composeJScriptCreateTaskURL(
		        					Integer.parseInt(item.getRegId()),
		        					pcdId)));	
				}
	
				//Botón para añadir el tipo de documento
				buttons.add(new WizardButton("procedure.button.addTipoDoc", 
						composeJScriptAddTPDocForStage(request, 
								Integer.parseInt(item.getRegId()),
								pcdId)));
        	}

        } else if( item.isEntityActividad()) {
        	
        	if (editionMode) {
        		
				if (pcdState == IProcedure.PCD_STATE_DRAFT) {
					
					// Botón para eliminar actividad
					buttons.add(new WizardButton("procedure.button.deleteActivity", 
							composeJScriptDeleteActivityURL(request, sessionAPI,
									Integer.parseInt(item.getRegId()),
									pcdId)));
				}
	
				// Botón para añadir el tipo de documento
				buttons.add(new WizardButton("procedure.button.addTipoDoc", 
						composeJScriptAddTPDocForStage(request, 
								Integer.parseInt(item.getRegId()),
								pcdId)));
        	}

        } else if (item.isEntityNodoSincronizacion()) {
        	
			if (editionMode 
					&& (pcdState == IProcedure.PCD_STATE_DRAFT)
					&& cuadro.isShowExtendedInfo()) {
				
				// Botón para eliminar el nodo de sincronización
				buttons.add(new WizardButton("procedure.button.deleteSyncNode", 
						composeJScriptDeletePcdSyncNodeURL(request, sessionAPI,
								Integer.parseInt(item.getRegId()),
								pcdId)));
			}

        } else if (item.isEntityFlujosEntradaFase() 
        		|| item.isEntityFlujosEntradaNodoSinc()) {

			if (editionMode 
					&& (pcdState == IProcedure.PCD_STATE_DRAFT) 
					&& cuadro.isShowExtendedInfo() ) {
				
				// Botón para añadir flujo de entrada
				buttons.add(new WizardButton("procedure.button.addFlow", 
						composeJScriptCreateInputFlowURL(request, 
								Integer.parseInt(item.getParentElement().getRegId()),
								pcdId)));
			}

        } else if (item.isEntityFlujosSalidaFase() 
        		|| item.isEntityFlujosSalidaNodoSinc()) {

			if (editionMode 
					&& (pcdState == IProcedure.PCD_STATE_DRAFT)
					&& cuadro.isShowExtendedInfo()) {
				
				// Botón para añadir flujo de salida
				buttons.add(new WizardButton("procedure.button.addFlow", 
						composeJScriptCreateOutputFlowURL(request, 
								Integer.parseInt(item.getParentElement().getRegId()),
								pcdId)));
			}

        } else if( item.isEntityFlujoEntradaFase() 
        			|| item.isEntityFlujoSalidaFase()
        			|| item.isEntityFlujoEntradaNodoSinc()
        			|| item.isEntityFlujoSalidaNodoSinc()) {

			if (editionMode 
					&& (pcdState == IProcedure.PCD_STATE_DRAFT) 
					&& cuadro.isShowExtendedInfo() ) {
				
				// Botón para eliminar el flujo
				buttons.add(new WizardButton("procedure.button.deleteFlow", 
						composeJScriptDeleteFlowURL(request, sessionAPI, 
								Integer.parseInt(item.getRegId()),
								pcdId)));
			}

		} else if( item.isEntityTramiteFase()
				|| item.isEntityTramiteComplejo() ) {
			
			if (editionMode) {

				buttons.add(new WizardButton("procedure.button.deleteTramite", 
						composeJScriptDeleteTaskURL(request, 
								Integer.parseInt(item.getRegId()),
								Integer.parseInt(item.getParentElement().getRegId()))));
	
				buttons.add(new WizardButton("procedure.button.taskUp", 
						composeJScriptTaskUpURL(request, 
								Integer.parseInt(item.getRegId()),
								Integer.parseInt(item.getParentElement().getRegId()))));
	
				buttons.add(new WizardButton("procedure.button.taskDown", 
						composeJScriptTaskDownURL(request, 
								Integer.parseInt(item.getRegId()),
								Integer.parseInt(item.getParentElement().getRegId()))));
			}

        }else if (item.isEntityTipoDocFase()){
        	
        	if (editionMode) {
        		
				CuadroEntidadTreeView tree = (CuadroEntidadTreeView) request
						.getSession().getAttribute(CUADRO_PROCEDIMIENTO);
				int idTpDoc = Integer.parseInt(item.getItem().getString(
						"CT_TPDOC:ID"));
				ElementoCuadro proc = (ElementoCuadro) ((TreeNode) tree
						.getRootNodes().iterator().next()).getModelItem();

				buttons.add(new WizardButton("procedure.button.addTemplate",
						composeJScriptAddTemplateURL(idTpDoc,
								Integer.parseInt(proc.getRegId()))));

				buttons.add(new WizardButton("procedure.button.createTemplate",
						composeJScriptCreateTemplateURL(idTpDoc)));
				int idFsTd = Integer.parseInt(item.getItem().getString(
						"STAGETPDOC:ID"));
				buttons.add(new WizardButton("procedure.button.delTpDoc",
						composeJScriptDeleteStageTPDocURL(request, idFsTd)));
        	}
        	
        } else if(item.isEntityTipoDocTramite()||item.isEntityTipoDocFase()) {
        	
        	if (editionMode) {
        		
				CuadroEntidadTreeView tree = (CuadroEntidadTreeView) request
						.getSession().getAttribute(CUADRO_PROCEDIMIENTO);
				ElementoCuadro proc = (ElementoCuadro) ((TreeNode) tree
						.getRootNodes().iterator().next()).getModelItem();

				buttons.add(new WizardButton("procedure.button.addTemplate",
						composeJScriptAddTemplateURL(
								Integer.parseInt(item.getRegId()),
								Integer.parseInt(proc.getRegId()))));

				buttons.add(new WizardButton("procedure.button.createTemplate",
						composeJScriptCreateTemplateURL(Integer.parseInt(item
								.getRegId()))));
        	}

        } else if(item.isEntityPlantillaTipoDoc() || item.isEntityPlantillaStageTipoDoc()) {
        	
        	if (editionMode) {
        		
				CuadroEntidadTreeView tree = (CuadroEntidadTreeView) request
						.getSession().getAttribute(CUADRO_PROCEDIMIENTO);
				ElementoCuadro proc = (ElementoCuadro) ((TreeNode) tree
						.getRootNodes().iterator().next()).getModelItem();
				boolean plantillaEspecifica = sessionAPI.getAPI()
						.getTemplateAPI()
						.isProcedureTemplate(Integer.parseInt(item.getRegId()));
				if (plantillaEspecifica) {
					buttons.add(new WizardButton(
							"procedure.button.deleteTemplate",
							composeJScriptDeleteTemplateURL(request,
									sessionAPI,
									Integer.parseInt(item.getRegId()),
									Integer.parseInt(proc.getRegId()))));
				}
        	}
        }
		
        request.getSession().setAttribute("TREE_BUTTONS", (WizardButton[])
        		buttons.toArray(new WizardButton[buttons.size()]));
	}
	
	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request) {
		
		// Información del elemento del cuadro 
		ElementoCuadro elem = (ElementoCuadro) node.getModelItem();
      
		// Información de la página a mostrar
		ActionForward ret = new ActionForward();
		ret.setRedirect(false);
		ret.setPath(getNodeUrl(elem));

		return ret;
	}
	
	public String getHandlerPath() {
		return "/manageVistaCuadroProcedimiento.do?method=verNodo";
	}

	protected ElementoCuadro getPcdElementoCuadro(TreeView treeView) {
		TreeNode pcdTreeNode = (TreeNode) treeView.getRootNodes().iterator().next();
		return (ElementoCuadro) pcdTreeNode.getModelItem();
	}
	
	protected String getNodeUrl(ElementoCuadro elem) {

		String url = null;
		
		if (elem.isEntityFlujosEntradaFase()
				|| elem.isEntityFlujosEntradaNodoSinc()) {
			url = new StringBuffer()
				.append("/showProcedureEntity.do?method=flows&nodeId=")
				.append(elem.getParentElement().getRegId())
				.append("&flowType=IN")
				.toString();
		} else if (elem.isEntityFlujosSalidaFase()
				|| elem.isEntityFlujosSalidaNodoSinc()) {
			url = new StringBuffer()
				.append("/showProcedureEntity.do?method=flows&nodeId=")
				.append(elem.getParentElement().getRegId())
				.append("&flowType=OUT")
				.toString();
		} else if ( elem.isEntityFlujoEntradaFase()
				|| elem.isEntityFlujoSalidaFase()
				|| elem.isEntityFlujoEntradaNodoSinc()
				|| elem.isEntityFlujoSalidaNodoSinc()) {
			url = new StringBuffer()
				.append("/showProcedureEntity.do?method=properties&entityId=")
				.append(elem.getEntityId())
				.append("&regId=")
				.append(elem.getRegId())
				.toString();
		} else {
			url = new StringBuffer()
				.append("/showProcedureEntity.do?method=card&entityId=")
				.append(elem.getEntityId())
				.append("&regId=")
				.append(elem.getRegId())
				.toString();
		}
		
		return url;
	}

}
