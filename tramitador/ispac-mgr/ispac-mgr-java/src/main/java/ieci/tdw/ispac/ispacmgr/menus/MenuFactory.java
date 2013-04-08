package ieci.tdw.ispac.ispacmgr.menus;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.ActionBean;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTReportDAO;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacweb.api.IActions;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ExpedientState;
import ieci.tdw.ispac.ispacweb.menu.BeanResourceFilter;
import ieci.tdw.ispac.ispacweb.menu.Menu;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.struts.util.MessageResources;

/**
 * Utilidad para crear menús.
 *
 */
public class MenuFactory {


	// Fichero de recursos de la aplicación
	private static final String BUNDLE_NAME = "ieci.tdw.ispac.ispacmgr.resources.ApplicationResources";

	static ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    /**
     * Compone el menú sencillo.
     * @param resources Fichero de recursos
     * @return una lista con los menús de Inicio y Salir.
     * @throws ISPACException
     */
    public static List getSingleMenu(ClientContext context, MessageResources resources)
    		throws ISPACException {

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(context, resources));
        //listMenus.add(createExitOptionMenu(context, resources));

	    return listMenus;
    }

    public static List getSingHistoric(ClientContext context, MessageResources resources)
	throws ISPACException {

		List listMenus = new ArrayList();

		listMenus.add(createInitOptionMenu(context, resources));

		listMenus.add(createMenu(
				resources.getMessage(context.getLocale(), "menu.volver"),
				"/showBatchSignList.do"));

		//listMenus.add(createExitOptionMenu(context, resources));

		return listMenus;
    }

    /**
     * Compone el menú de la bandeja de entrada.
     * @param context
     * @param resources Fichero de recursos
     * @return una lista con los menús de Inicio y Salir.
     * @throws ISPACException
     */
    public static List getInboxMenu(ClientContext context, MessageResources resources)
    		throws ISPACException {

    	return getInboxMenu(context, resources,null);

    }


    /**
     * Compone el menú de la bandeja de entrada.
     * @param context
     * @param resources Fichero de recursos
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return una lista con los menús de Inicio y Salir.
     * @throws ISPACException
     */
    public static List getInboxMenu(ClientContext context, MessageResources resources, String resp)
	throws ISPACException {

		List listMenus = new ArrayList();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(context);
		IActions actions = managerAPI.getActionsAPI();

		listMenus.add(createMenu(
				resources.getMessage(context.getLocale(), "menu.iniciarExpediente"),
				"/showCreateProcess.do"));

		listMenus.add(createMenu(
				resources.getMessage(context.getLocale(), "menu.busquedaAvanzada"),
				"/showProcedureList.do?search=true"));

		if (actions.hasGlobalReports(resp)) {
			listMenus.add(createMenu(resources.getMessage(context.getLocale(), "menu.reports"),
					"javascript: showFrame(\"workframe\", \"showReports.do?tipo="+CTReportDAO.GLOBAL_TYPE+"\", \"\", \"\", \"\", false);"));
		}


		//  listMenus.add(createExitOptionMenu(context, resources));

		return listMenus;
		}
    /**
     * Compone el menú de la vista de un registro distribuido.
     * @param resources Fichero de recursos
     * @return una lista con los menús de la pantalla de registro distribuido.
     * @throws ISPACException
     */
    public static List getIntrayMenu(ClientContext context, MessageResources resources)
    		throws ISPACException {

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(context, resources));

        listMenus.add(createMenu(
        		resources.getMessage(context.getLocale(), "menu.registrosDistribuidos"),
        		"/showIntrayList.do"));

       // listMenus.add(createExitOptionMenu(context, resources));

	    return listMenus;
    }


    /**
     * Compone el menú del resultado de la búsqueda.
     * @param ctx Contexto del cliente
     * @param resources Fichero de recursos
     * @param properties
     * @param searchActions Lista de acciones para la búsqueda
     * @return una lista con los menús de Inicio, Acciones y Salir.
     * @throws ISPACException
     */
    public static List getSearchResultMenu(ClientContext ctx,
    									   MessageResources resources,
    									   Properties properties, List searchActions,
    									   Map mapParams, int idFormulario) throws ISPACException {
    	return getSearchResultMenu(ctx, resources,searchActions,properties, mapParams,idFormulario,null);
    }

    /**
     * Compone el menú del resultado de la búsqueda.
     * @param ctx Contexto del cliente
     * @param resources Fichero de recursos
     * @param properties
     * @param searchActions Lista de acciones para la búsqueda
      * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return una lista con los menús de Inicio, Acciones y Salir.
     * @throws ISPACException
     */
    public static List getSearchResultMenu(ClientContext ctx,
			   MessageResources resources,
			   Properties properties, List searchActions,
			   Map mapParams, int idFormulario,String resp) throws ISPACException {
    	return getSearchResultMenu(ctx, resources,searchActions,properties, mapParams,idFormulario,resp);
}
    /**
     * Compone el menú del resultado de la búsqueda.
     * @param ctx Contexto del cliente
     * @param resources Fichero de recursos
     * @param searchActions Lista de acciones para la búsqueda
     * @param mapParams Parámetros
     * @param idFormulario Identificador del formulario
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @return una lista con los menús de Inicio, Acciones y Salir.
     * @throws ISPACException
     */
    public static List getSearchResultMenu(ClientContext ctx,
    									   MessageResources resources,
    									   List searchActions,
    									   Properties properties,
    									   Map mapParams,
    									   int idFormulario,
    									   String resp) throws ISPACException {

        List listMenus = new ArrayList();

        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
        IActions actions = managerAPI.getActionsAPI();
        listMenus.add(createInitOptionMenu(ctx, resources));


        listMenus.add(createMenu(
        		resources.getMessage(ctx.getLocale(), "menu.iniciarExpediente"),
        		"/showCreateProcess.do"));

		if ((searchActions != null) &&
			(!searchActions.isEmpty())) {
	        String message = resources.getMessage(ctx.getLocale(), "menu.acciones");
	        Menu menu = new Menu(message, ActionBean.TITLE);
	        menu.addItems(actions.getSearchResultListActions(searchActions, resources, properties, mapParams));
	        listMenus.add(menu);
		}

        listMenus.add(createMenu(
        		resources.getMessage(ctx.getLocale(), "menu.busquedaAvanzada"),
        		"/showProcedureList.do?search=true"));

        if(actions.hasSearchReport(idFormulario,resp)){
			listMenus.add(createMenu(resources.getMessage(ctx.getLocale(), "menu.reports"),
					"javascript: showFrame(\"workframe\", \"showReports.do?idFrm="+idFormulario+"\", \"\", \"\", \"\", false);"));
        }
       // listMenus.add(createExitOptionMenu(ctx, resources));

	    return listMenus;
    }

    /**
     * Compone el menú para la lista de trabajo.
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @return una lista de menús asociados a la lista de trabajo
     * @throws ISPACException
     */
    public static List getWorkListMenu(ClientContext ctx, IState state,
    		MessageResources resources) throws ISPACException {

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(ctx, resources));

        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
        IActions actions = managerAPI.getActionsAPI();
        String message = resources.getMessage(ctx.getLocale(), "menu.acciones");
        Menu menu=new Menu(message,ActionBean.TITLE ,"idsStage", ActionBean.ID);
   	    List stateActions = actions.getStateActions(state, resources);
   	    BeanResourceFilter.translateActionKeys(stateActions, ctx.getLocale(), resources);
        menu.addItems(stateActions);
        listMenus.add(menu);

        List stateDocuments = actions.getStateDocuments(state, resources);
        //Si hay algun documento asociado a la fase/actividad...
        if (!stateDocuments.isEmpty()){
            //Mostramos el menu de documentos asociados a la fase/actividad
            menu = new Menu(resources.getMessage(ctx.getLocale(), "menu.documentos"), "NOMBRE", "documentId",ActionBean.ID);
            menu.addItems(stateDocuments);
            listMenus.add(menu);
        }
        // Quitar porque va dentro de las acciones
//        listMenus.add(createMenu(
//        		resources.getMessage(context.getLocale(), "menu.nuevoTramite"),
//        		"javascript:takeElement(\"/selectNewTask.do\", \"\");"));

       // listMenus.add(createExitOptionMenu(ctx, resources));

	    return listMenus;
    }

    /**
     * Compone el menú para la pantalla de trámites
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @param ispacbase Directorio base de ispac
     * @param returnToSearch
     * @return una lista con menús asociados a un trámite
     * @throws ISPACException
     */
    public static List getTaskMenu(ClientContext ctx, IState state, String ispacbase,
    		MessageResources resources, String stageId, String returnToSearch) throws ISPACException{
    return getTaskMenu(ctx, state, ispacbase, resources,stageId,returnToSearch,null);
    }

    /**
     * Compone el menú para la pantalla de trámites
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @param ispacbase Directorio base de ispac
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @param returnToSearch
     * @return una lista con menús asociados a un trámite
     * @throws ISPACException
     */
    public static List getTaskMenu(ClientContext ctx, IState state, String ispacbase,
    		MessageResources resources, String stageId, String returnToSearch,String resp) throws ISPACException{

        List listMenus = new ArrayList();
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
        IActions actions = managerAPI.getActionsAPI();

        listMenus.add(createInitOptionMenu(ctx, resources));
        addReturnToSearchOptionMenu(ctx, resources, returnToSearch, listMenus);

        // Se crean los menús cuando se tiene el control del trámite
        if (state.isLockByCurrentSession()) {
		    String message = resources.getMessage(ctx.getLocale(), "menu.acciones");
		    Menu menu=new Menu(message,ActionBean.TITLE ,"idsTask", ActionBean.ID);
	   	    List stateActions = actions.getStateActions(state, resources);
	   	    BeanResourceFilter.translateActionKeys(stateActions, ctx.getLocale(), resources);
	        menu.addItems(stateActions);
		    listMenus.add(menu);
		}

		listMenus.add(createViewExpedientOptionMenu(ctx, state, resources, stageId));

		listMenus.add(createMenu(resources.getMessage(ctx.getLocale(), "menu.verDocumentos"),
								 "javascript: showFrame(\"workframe\", \"showTask.do?taskId=" + state.getTaskId() + "&numexp=" + state.getNumexp() + "&entity=" + ISPACEntities.DT_ID_DOCUMENTOS + "&block=1&key=-1&form=single&nodefault=true\",800,600, \"\", false);"));



		if (actions.hasReports(state,resp)) {
			listMenus.add(createMenu(resources.getMessage(ctx.getLocale(), "menu.reports"),
					"javascript: showFrame(\"workframe\", \"showReports.do\", \"\", \"\", \"\", false);"));
		}

        listMenus.add(createConfigEditorsOptionMenu(ctx, ispacbase, resources));
        //listMenus.add(createExitOptionMenu(ctx, resources));

        return listMenus;
    }

    /**
     * Compone el menú para la pantalla de tramitación agrupada
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @param ispacbase Directorio base de ispac
     * @return una lista con menús asociados a una tramitación agrupada
     * @throws ISPACException
     */
    public static List getBatchTaskMenu(ClientContext ctx, IState state, String ispacbase,
    		MessageResources resources) throws ISPACException{

        List listMenus = new ArrayList();


        listMenus.add(createInitOptionMenu(ctx, resources));
        listMenus.add(createConfigEditorsOptionMenu(ctx, ispacbase, resources));

        listMenus.add(createMenu(
        		resources.getMessage(ctx.getLocale(), "menu.tramitacionesAgrupadas"),
        		"/showBatchTaskList.do"));

       // listMenus.add(createExitOptionMenu(ctx, resources));

        return listMenus;
    }

    public static List getBatchTaskListMenu(ClientContext ctx, IState state,
    		MessageResources resources) throws ISPACException{

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(ctx, resources));

		//se crean los menús adecuados
//		if (!state.getReadonly())
//		{
//	        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
//		    IActions actions=managerAPI.getActionsAPI();
//
//		    String message = resources.getMessage(context.getLocale(), "menu.acciones");
//		    Menu menu=new Menu(message,ActionBean.TITLE ,"idBatchTask", ActionBean.ID);
//	   	    List stateActions = actions.getStateActions(state, resources);
//	   	    BeanResourceFilter.translateActionKeys(stateActions, resources);
//	        menu.addItems(stateActions);
//		    listMenus.add(menu);
//
//		    message = resources.getMessage(context.getLocale(), "menu.documentos");
//		    menu=new Menu(message, "NOMBRE", "documentId", ActionBean.ID);
//		    menu.addItems(actions.getStateDocuments(state, resources));
//		    listMenus.add(menu);
//
//		    message = resources.getMessage(context.getLocale(), "menu.tramites");
//		    menu=new Menu(message, "NOMBRE", "documentId", ActionBean.ID);
//		    menu.addItems(actions.getStateDocuments(state, resources));
//		    listMenus.add(menu);
//		}

		//listMenus.add(createExitOptionMenu(ctx, resources));

        return listMenus;
    }

    /**
     * Compone el menú para un nuevo trámite.
     * @param resources Fichero de recursos
     * @param stageId Identificador del expediente.
     * @return una lista con los menús.
     * @throws ISPACException
     */
    public static List getNewTaskMenu(ClientContext ctx, MessageResources resources,
    		int stageId) throws ISPACException {

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(ctx, resources));

        //Si el id del expediente no es 0, incluimos el enlace para
        //situarnos en el expediente
        if (stageId > 0) {
	        listMenus.add(createMenu(
	        		resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
	        		"/showExpedient.do?stageId="+stageId));
        }

        //listMenus.add(createExitOptionMenu(ctx, resources));

	    return listMenus;
    }

    public static List getMilestoneMenu(ClientContext ctx, MessageResources resources,
    		IState state) throws ISPACException {

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(ctx, resources));

        if (state instanceof ExpedientState) {

	        listMenus.add(createMenu(
	        		resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
	        		"/showExpedient.do?stageId=" + state.getStageId()));
        }
        // DataState
        else {
        	listMenus.add(createMenu(
       	    		resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
       	    		"/showData.do?numexp=" + state.getNumexp()));
        }

        /*
        if (state.getReadonly()) {
        	listMenus.add(createMenu(
       	    		resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
       	    		"/showData.do?numexp=" + state.getNumexp()));
        } else {
	        listMenus.add(createMenu(
	        		resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
	        		"/showExpedient.do?stageId=" + state.getStageId()));
        }
        */

       // listMenus.add(createExitOptionMenu(ctx, resources));

	    return listMenus;
    }


    /**
     * Compone el menú para la lista de trámites.
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @return una lista de menús asociados a la lista de trámites de un tipo
     * @throws ISPACException
     */
    public static List getTasksListMenu(ClientContext ctx, IState state,
    		MessageResources resources) throws ISPACException {

        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
        IActions actions = managerAPI.getActionsAPI();

        List listMenus = new ArrayList();

   	    listMenus.add(createInitOptionMenu(ctx, resources));

   	    String message = resources.getMessage(ctx.getLocale(), "menu.acciones");
   	    Menu menu=new Menu(message, ActionBean.TITLE , "idsTask", ActionBean.ID);
   	    List stateActions = actions.getStateActions(state, resources);
   	    BeanResourceFilter.translateActionKeys(stateActions, ctx.getLocale(), resources);
        menu.addItems(stateActions);
   	    listMenus.add(menu);

   	    //listMenus.add(createExitOptionMenu(ctx, resources));

   	    return listMenus;
    }
    
    /**
     * Compone el menú para la lista de trámites cerrados.
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @return una lista de menús asociados a la lista de trámites de un tipo
     * @throws ISPACException
     */
    public static List getClosedTasksListMenu(ClientContext ctx, IState state,
    		MessageResources resources) throws ISPACException {

        List listMenus = new ArrayList();
   	    listMenus.add(createInitOptionMenu(ctx, resources));

   	    return listMenus;
    }    
    

    /**
     * Compone el menú para la pantalla de las fases
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @param returnToSearch
     * @return una lista con menús asociados al expediente
     * @throws ISPACException
     */
    public static List getExpMenu(ClientContext ctx, IState state,
    		MessageResources resources, String returnToSearch) throws ISPACException{
    	return getExpMenu(ctx,state,resources,returnToSearch, null);
    }
    /**
     * Compone el menú para la pantalla de las fases
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @param Retorna a una búsqueda
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
     * @param returnToSearch
     * @return una lista con menús asociados al expediente
     * @throws ISPACException
     */
    public static List getExpMenu(ClientContext ctx, IState state,
    		MessageResources resources, String returnToSearch,String resp) throws ISPACException{

        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
        IActions actions = managerAPI.getActionsAPI();
        IInvesflowAPI invesFlowAPI = ctx.getAPI();
        ISecurityAPI securityAPI = invesFlowAPI.getSecurityAPI();

        List listMenus = new ArrayList();
        Menu menu;

   	    listMenus.add(createInitOptionMenu(ctx, resources));
   	    addReturnToSearchOptionMenu(ctx, resources, returnToSearch, listMenus);

        // Se crean los menús cuando se tiene el control de la fase
        if (state.isLockByCurrentSession() && (!state.getReadonly() || (state.getReadonly() &&(state.getReadonlyReason() == StateContext.READONLYREASON_FORM)) )) {

            menu = new Menu(resources.getMessage(ctx.getLocale(), "menu.acciones"),
            		ActionBean.TITLE, "idsStage",ActionBean.ID);
            menu.addItems(actions.getStateActions(state, resources));
            listMenus.add(menu);

            //Lista de documentos asociados a la fase
            List listStageDocs = actions.getStateDocsActions(state, resources);
            //Si hay algun documento asociado a la fase...
            if ( (listStageDocs != null) && (listStageDocs.size()!= 0) ){
            	//Mostramos el menu de documentos asociados a la fase
            	menu = new Menu(resources.getMessage(ctx.getLocale(), "menu.documentos"),
            			"NOMBRE", "documentId",ActionBean.ID);
            	menu.addItems(listStageDocs);
            	listMenus.add(menu);
            }

            listMenus.add(createMenu(
            		resources.getMessage(ctx.getLocale(), "menu.nuevoTramite"),
            "/selectNewTask.do"));

            listMenus.add(createMenu(
            		resources.getMessage(ctx.getLocale(), "menu.avanzarFase"),
            		"javascript:sure(\"closeStage.do?idsStage="+state.getStageId()
            		+"\", \""+resources.getMessage(ctx.getLocale(), "ispac.action.stage.close.msg")+"\")"));
        }
        //Si no se pueden crear la acciones y el expediente actual esta enviado a la papelera y el usuario que lo está visualizando
        //es el supervisor total entonces se deberán mostrar las acciones de eliminar el expediente o restaurar
        else if(securityAPI.isFunction(ctx.getRespId(), ISecurityAPI.FUNC_TOTALSUPERVISOR)
        		&& invesFlowAPI.isExpedientSentToTrash(state.getNumexp()))
        {

        	  menu = new Menu(resources.getMessage(ctx.getLocale(), "menu.acciones"),
              		ActionBean.TITLE, "idsStage",ActionBean.ID);
              menu.addItems(actions.getStateActions(state, resources));
             menu.addItem(new ActionBean(
            		 resources.getMessage(ctx.getLocale(), "menu.restaurarExpediente"),
             		"javascript:sure(\"showExpedientsSentToTrash.do?method=restoreExp&processId="+state.getProcessId()+"&numexp="+state.getNumexp()
             		+"\", \""+resources.getMessage(ctx.getLocale(), "forms.expTrash.confirm.restore")+"\")"));
             menu.addItem(new ActionBean(
            		 resources.getMessage(ctx.getLocale(), "menu.eliminarExpediente"),
               		"javascript:sure(\"showExpedientsSentToTrash.do?method=deleteExp&processId="+state.getProcessId()+"&numexp="+state.getNumexp()
              		+"\", \""+resources.getMessage(ctx.getLocale(), "forms.expTrash.confirm.delete")+"\")"));
              listMenus.add(menu);


        }

        //Lista de tramies abiertos/cerrados/cancelados de un expediente
        listMenus.add(createMenu(resources.getMessage(ctx.getLocale(), "menu.verTramites"),
		"javascript: showFrame(\"workframe\", \"showTaskListExpedient.do\", \"\", \"\", \"\", false);"));

		if (actions.hasReports(state,resp)) {
			listMenus.add(createMenu(resources.getMessage(ctx.getLocale(), "menu.reports"),
					"javascript: showFrame(\"workframe\", \"showReports.do\", \"\", \"\", \"\", false);"));
		}

        if ("true".equalsIgnoreCase(ISPACConfiguration.getInstance().get("SHOW_DESIGNER"))) {
			if (state.getStageId() > 0) {

				// El expediente está abierto
				listMenus.add(createMenu(resources.getMessage(ctx.getLocale(),
						"menu.estadoExpediente"),
						"javascript:showDesigner(\"workframe\",\"showPcdGUI.do?stageId="
								+ state.getStageId() + "&locale="
								+ ctx.getAppLanguage() + "\",10,10);"));
			} else {

				// El expediente está cerrado o archivado
				listMenus.add(createMenu(resources.getMessage(ctx.getLocale(),
						"menu.estadoExpediente"),
						"javascript:showDesigner(\"workframe\",\"showPcdGUI.do?processId="
								+ state.getProcessId() + "&locale="
								+ ctx.getAppLanguage() + "\",10,10);"));
			}
		}



        listMenus.add(createMenu(
   	    		resources.getMessage(ctx.getLocale(), "menu.historico"),
   	    		"/showMilestone.do"));

       // listMenus.add(createExitOptionMenu(ctx, resources));

        return listMenus;
    }

    /**
     * Compone el menú para la pantalla de actividades
     * @param ctx Contexto del cliente
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @return una lista con menús asociados al expediente
     * @throws ISPACException
     */
    public static List getActivityMenu(ClientContext ctx, IState state,
    		MessageResources resources, String stageId) throws ISPACException{

        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
        IActions actions = managerAPI.getActionsAPI();

        List listMenus = new ArrayList();
        Menu menu;

   	    listMenus.add(createInitOptionMenu(ctx, resources));

        // Se crean los menús cuando se tiene el control de la actividad
        if (state.isLockByCurrentSession()) {

        	menu = new Menu(resources.getMessage(ctx.getLocale(), "menu.acciones"),
            		ActionBean.TITLE, "idsStage",ActionBean.ID);
            menu.addItems(actions.getActivityActions(state, resources));
            listMenus.add(menu);

            //Lista de documentos asociados a la actividad
            List listActivityDocs = actions.getStateDocsActions(state, resources);
            //Si hay algun documento asociado a la actividad...
            if ( (listActivityDocs != null) && (listActivityDocs.size()!= 0) ){
                //Mostramos el menu de documentos asociados a la actividad
                menu = new Menu(resources.getMessage(ctx.getLocale(), "menu.documentos"),
                		"NOMBRE", "documentId",ActionBean.ID);
                menu.addItems(listActivityDocs);
                listMenus.add(menu);
            }
            if(!state.getReadonly()){
       	    listMenus.add(createMenu(
       	    		resources.getMessage(ctx.getLocale(), "menu.avanzarActividad"),
            		"javascript:sure(\"closeStage.do?idsStage="+state.getActivityId()
            			+"\", \""+resources.getMessage(ctx.getLocale(), "ispac.action.activity.close.msg")+"\")"));
            }
        }

        listMenus.add(createViewExpedientOptionMenu(ctx, state, resources, stageId));
       // listMenus.add(createExitOptionMenu(ctx, resources));

        return listMenus;
    }

    /**
	 * @param ctx
	 *            Contexto del cliente
	 * @param state
	 *            Estado actual
	 * @param resources
	 *            Fichero de recursos
	 * @return una lista de menús con las posibles plantilla a utilizar para la
	 *         generación de documentos
	 * @throws ISPACException
	 */
	public static List getListTemplatesMenu(ClientContext ctx, IState state,
			MessageResources resources) throws ISPACException {

		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(
				ctx);
		IActions actions = managerAPI.getActionsAPI();

		List listMenus = new ArrayList();
		Menu menu = new Menu(resources.getMessage(ctx.getLocale(), "menu.plantillas"), "NOMBRE",
				"templateId", ActionBean.ID);
		menu.addItems(actions.getStateTemplates(state, resources,
				state.getEntityId(), state.getEntityRegId()));
		listMenus.add(menu);

		return listMenus;
	}

    /**
	 * @param ctx
	 *            Contexto del cliente
	 * @param state
	 *            Estado actual
	 * @param resources
	 *            Fichero de recursos
	 * @return una lista de menús que incluye un único elemento que sirve de
	 *         enlace para anexar un documento
	 * @throws ISPACException
	 */
	public static List getAttachFileMenu(ClientContext ctx, IState state,
			MessageResources resources) throws ISPACException {
		IInvesflowAPI invesFlowAPI = ctx.getAPI();

		String anexarFichero = resources.getMessage(ctx.getLocale(), "menu.anexarFichero");
		String msg = resources.getMessage(ctx.getLocale(), "msg.sustituirFichero");
		String confirmar = resources.getMessage(ctx.getLocale(), "common.confirm");
		String aceptar = resources.getMessage(ctx.getLocale(), "common.message.ok");
		String cancelar = resources.getMessage(ctx.getLocale(), "common.message.cancel");

		Menu menu = new Menu(anexarFichero);
		List listMenus = new ArrayList();

		// Datos del documento del tramite, si INFOPAG tiene valor, es que ya se
		// ha generado un documento fisico
		IItem docitem = invesFlowAPI.getEntitiesAPI().getEntity(
				state.getEntityId(), state.getEntityRegId());
		if (docitem.get("INFOPAG") != null
				&& !docitem.get("INFOPAG").equals(""))
			menu
					.setMsAction(new ActionBean(anexarFichero,
							"javascript:attachFileMsg(\"/attachFile.do\", \""
									+ msg + " , "+confirmar+","+aceptar+","+cancelar+"\")"));
		else
			menu.setMsAction(new ActionBean(anexarFichero,
					"javascript:attachFile(\"/attachFile.do\")"));
		listMenus.add(menu);

		return listMenus;
	}


    /**
	 * Crea un menú simple con la información determinada.
	 *
	 * @param message
	 *            Cadena con el mensaje a mostrar.
	 * @param action
	 *            Action a ejecutar.
	 * @return Menú.
	 * @throws ISPACException
	 */
    private static Menu createMenu(String message, String action)
			throws ISPACException {
		return createMenu(message, action, null, null);
	}

    /**
	 * Crea un menú simple con la información determinada.
	 *
	 * @param message
	 *            Cadena con el mensaje a mostrar.
	 * @param action
	 *            Action a ejecutar.
	 * @param target
	 * 			  Destino del enlace.
	 * @param jscond
	 * 			  Condición javascript para mostrar el enlace
	 * @return Menú.
	 * @throws ISPACException
	 */
    private static Menu createMenu(String message, String action, String target,
    		String jscond) throws ISPACException {
		Menu menu = new Menu(message);
		menu.setMsAction(new ActionBean(message, action));
		menu.setTarget(target);
		menu.setJscond(jscond);
		return menu;
	}


    public static List getBatchSignListMenu(ClientContext ctx, IState state,
    		MessageResources resources) throws ISPACException {

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(ctx, resources));

        String message = resources.getMessage(ctx.getLocale(), "menu.acciones");
        Menu menu=new Menu(message,ActionBean.TITLE ,"idsStage", ActionBean.ID);

        //TODO Descomentar esto y obtener de esta forma las acciones, para ello
        //posiblemente se deberia crear un estado SIGNLISTSTATE y de ahi sacar las acciones
//        List stateActions = actions.getStateActions(state, resources);

        List stateActions = new ArrayList();
        //Obtener los mensajes a mostrar en caso de error.

		stateActions.add(new ActionBean(resources.getMessage(ctx.getLocale(), "ispac.action.batchsign.sign"),
				"javascript:takeElementInFormWorkFrame" +
				"(\"batchSign.do?" + ActionsConstants.PARAMETER_METHOD + "=" + ActionsConstants.CALCULATE_DOCUMENTS_HASH + "\", \"\"," +
						" \"defaultForm\"  , \""+bundle.getString("element.noSelect")+"\" , \""+bundle.getString("common.alert")+"\", \""+bundle.getString("common.message.ok")+"\", \""+bundle.getString("common.message.cancel")+"\");"));
	    BeanResourceFilter.translateActionKeys(stateActions, ctx.getLocale(), resources);
        menu.addItems(stateActions);
        listMenus.add(menu);

        listMenus.add(createMenu(
   	    		resources.getMessage(ctx.getLocale(), "ispac.action.sing.historic"),
   				"/showSignHistoric.do"));

        //listMenus.add(createExitOptionMenu(ctx, resources));

	    return listMenus;
    }

	public static Object getSignedDocumentListMenu(ClientContext cct, IState state, MessageResources resources) throws ISPACException {
		return getSingleMenu(cct, resources);
	}

	public static Object getCloneExpedientMenu(ClientContext cct, IState state, MessageResources resources) throws ISPACException {
		List listMenus = getSingleMenu(cct, resources);
        listMenus.add(createMenu(
        		resources.getMessage(cct.getLocale(), "menu.verExpediente"),
        		"/showExpedient.do?stageId="+state.getStageId()));
//		listMenus.add(createMenu(
//        		resources.getMessage(cct.getLocale(), "menu.clonar"),
//        		"javascript:clone()"));
		return listMenus;
	}

    /**
     * Compone el menú para la vista de delegación.
     * @param resources Fichero de recursos
     * @return Lista de menús.
     * @throws ISPACException si ocurre algún error.
     */
    public static List getDelegateMenu(ClientContext ctx, MessageResources resources)
    		throws ISPACException {

        List listMenus = new ArrayList();

        listMenus.add(createInitOptionMenu(ctx, resources));
        //listMenus.add(createExitOptionMenu(ctx, resources));

	    return listMenus;
    }

    /**
     * Compone el menú para la pantalla de comprobar documentación
     * @param state Estado actual
     * @param resources Fichero de recursos
     * @return una lista con menús asociados al expediente
     * @throws ISPACException
     */
    public static List getCheckDocumentsMenu(ClientContext ctx, IState state,
    		MessageResources resources) throws ISPACException{

        List listMenus = new ArrayList();

   	    listMenus.add(createInitOptionMenu(ctx, resources));

       	listMenus.add(createMenu(
   	    		resources.getMessage(ctx.getLocale(), "menu.volverExpediente"),
   	    		"/showExpedient.do?stageId=" + state.getStageId()));

        //listMenus.add(createExitOptionMenu(ctx, resources));

        return listMenus;
    }

    private static Menu createInitOptionMenu(ClientContext ctx, MessageResources resources) throws ISPACException {

    	return createMenu(resources.getMessage(ctx.getLocale(), "menu.inicio"),
    					  "/showProcedureList.do");
    }

    private static void addReturnToSearchOptionMenu(ClientContext ctx, MessageResources resources, String returnToSearch, List listMenus) throws ISPACException {

        if (returnToSearch != null) {

        	String action = "/searchForm.do";
        	if (StringUtils.isNotBlank(returnToSearch)) {

        		action += "?" + returnToSearch;
        	}

        	listMenus.add(createMenu(resources.getMessage(ctx.getLocale(), "menu.retornarBusqueda"), action));
        }
    }

   /* private static Menu createExitOptionMenu(ClientContext ctx, MessageResources resources) throws ISPACException {

    	return createMenu(resources.getMessage(ctx.getLocale(), "menu.salir"),
    					  "javascript:sure(\"exit.do\", \""+resources.getMessage(ctx.getLocale(), "ispac.action.application.close")+"\")");
    }*/

    private static Menu createViewExpedientOptionMenu(ClientContext ctx, IState state,
    												  MessageResources resources, String stageId) throws ISPACException {
    	String readonly="0";
        if (state.getReadonlyReason() == ManagerState.READONLY_REASON_EXPEDIENT_CLOSED) {

        	return createMenu(resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
        					  "/showData.do?numexp=" + state.getNumexp());
        }
        if(state.getReadonly()){
        	readonly=state.getReadonlyReason()+"";
        }
        if (StringUtils.isNotEmpty(stageId)) {

        	int iStageId = 0;
        	try {
				iStageId = Integer.parseInt(stageId);
			} catch (Exception e) {}

			if (iStageId > 0) {
				
	        	return createMenu(resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
		    				  "/showExpedient.do?stageId=" + iStageId+"&"+ManagerState.PARAM_READONLY+"="+readonly);
			}
        }

        if (state.getStageId() > 0) {

       	    return createMenu(resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
       	    				  "/showExpedient.do?stageId=" + state.getStageId()+"&"+ManagerState.PARAM_READONLY+"="+readonly);
        }

   	    return createMenu(resources.getMessage(ctx.getLocale(), "menu.verExpediente"),
 				  "/selectAnActivity.do?numexp=" + state.getNumexp());
    }

    private static Menu createConfigEditorsOptionMenu(ClientContext ctx, String ispacbase,
    						   						  MessageResources resources) throws ISPACException {
    	
    	//En funcion de si se permite el uso plantillas en formato ODT se controla la presentacion de este menu 
    	String jsFunction = "!isIEWord()";
    	boolean useOdtTemplates = ConfigurationMgr.getVarGlobalBoolean(ctx, ConfigurationMgr.USE_ODT_TEMPLATES, false);
    	if (useOdtTemplates){
    		jsFunction = null;
    	}
    	
    	return createMenu(resources.getMessage(ctx.getLocale(), "menu.configEditores"),
    					  StaticContext.rewritePage(ispacbase, "config.jsp"), "configFrame", jsFunction);
    }

}