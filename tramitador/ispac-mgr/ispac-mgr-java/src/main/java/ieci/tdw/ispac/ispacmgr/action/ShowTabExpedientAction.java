package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.ActionBean;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.GenericFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacmgr.bean.scheme.RegEntityBean;
import ieci.tdw.ispac.ispacmgr.bean.scheme.SchemeEntityBean;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IActions;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;
import ieci.tdw.ispac.ispacweb.menu.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * Acción similar a ShowExpedientAction.
 *
 * @author marisa
 *
 */
public class ShowTabExpedientAction extends BaseAction
{

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception
    {

        ClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(
                cct);
        MessageResources resources = getResources(request);

        //Se cambia el estado de tramitación
        Map params = request.getParameterMap();
        IState state = managerAPI.enterState(getStateticket(request),
                ManagerState.EXPEDIENT, params);

        IWorklist managerwl = managerAPI.getWorklistAPI();
		
		// Responsabilidades del usuario conectado
		String resp = managerwl.getRespString(state);
        ///////////////////////////////////////////////
        // Acceso al esquema
        IScheme scheme = managerAPI.getSchemeAPI();

        IItemCollection catalogent = scheme.getCatalogEntityScheme(state);
        List schemebeanlist = CollectionBean.getBeanList(
                SchemeEntityBean.class, catalogent);

        for (Iterator ite = schemebeanlist.iterator(); ite.hasNext();)
        {
            SchemeEntityBean scheEntBean = (SchemeEntityBean) ite.next();
            int entityId = scheEntBean.getItem().getKeyInt();
            IItemCollection itemcol = scheme.getEntitySchemeFilter(state,
                    entityId);

            List reglist = CollectionBean.getBeanList(RegEntityBean.class,
                    itemcol);
            for (Iterator iter = reglist.iterator(); iter.hasNext();)
            {
                RegEntityBean regEntBean = (RegEntityBean) iter.next();
                regEntBean.getImage(entityId);
                regEntBean.addParamsId();
                if (entityId == ISPACEntities.DT_ID_TRAMITES)
                {
                    regEntBean.setUrl("showTask.do");
                    regEntBean.addParams(ManagerState.PARAM_STAGEID, regEntBean
                            .getProperty("ID_TRAM_EXP"));
                } else
                {
                    regEntBean.setUrl(mapping.findForward(state.getLabel())
                            .getPath());
                    regEntBean.addParams(state.getParameters());
                }
                scheEntBean.addRegEntity(regEntBean);
                scheEntBean.addParameterId();
            }

        }

        request.setAttribute("SchemeList", schemebeanlist);

        // Expediente
        ItemBean itemBean = new ItemBean(entitiesAPI.getExpedient(state
                .getNumexp()));
        request.setAttribute("Expedient", itemBean);

        if (state.isResponsible()) {

            // Cargar el contexto de la cabecera (miga de pan)
            SchemeMgr.loadContextHeader(state, request, getResources(request), session);
        }

        // Ahora el formulario de búsqueda está en sesión y se mantienen los parámetros de la última búsqueda realizada
        String returnToSearch = null;
        SearchForm searchForm = (SearchForm) request.getSession().getAttribute(ActionsConstants.FORM_SEARCH_RESULTS);
        if (searchForm != null) {
        	
        	returnToSearch = searchForm.getDisplayTagParams();
        }
        
        // Menús
        request.setAttribute("menus", MenuFactory.getTaskMenu(cct, state, (String) servlet.getServletContext().getAttribute("ispacbase"), resources, null, returnToSearch,resp));
        
        // Formateadores
        // FormatterManager.storeBFShowExpedientAction(request, getPath());
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory
                .getFormatter(getISPACPath("/digester/infoformatter.xml"));
        request.setAttribute("FormatterInfo", formatter);

        // enviamos un mapa con parámetros para el enlace de los hitos y la otra
        // vista//
        Map linkParams = new HashMap();
        linkParams.put("stageId", String.valueOf(state.getStageId()));
        request.setAttribute("Params", linkParams);

        //////////////////////////////////////////////////////////////////////
        // Formulario asociado a la acción
        String strURL="";
        String path = getRealPath("");
        
        try
        {
	        EntityForm defaultForm = (EntityForm) form;
	        EntityApp entityapp = scheme.getDefaultEntityApp(state, path, false);

	        strURL = entityapp.getURL();
	        // Permite modificar los datos del formulario
	        defaultForm.setReadonly(Boolean.toString(state.getReadonly()));

	        // Visualiza los datos de la entidad
	        if (defaultForm.getActions() == null
	                || defaultForm.getActions().equals("success"))
	            defaultForm.setEntityApp(entityapp, cct.getLocale());

	        //Se está pidiendo el esquema con las propiedades
	        //por defecto las cuales incluyen la propiedad ESTADO en la entidad de
	        // documentos.
	        /*
	         * if (state.getEntityId() == ISPACEntities.DT_ID_DOCUMENTOS) { if
	         * (entityapp.getString("ESTADO").equals("FINALIZADO"))
	         * defaultForm.setReadonly(Boolean.toString(true)); }
	         */
	        if ((state.getEntityId() == ISPACEntities.DT_ID_DOCUMENTOS)
	                && !state.getReadonly())
	        {
	            List listMenus = new ArrayList();
	            IActions actions = managerAPI.getActionsAPI();

	            Menu menu = new Menu("Plantillas", "NOMBRE", "templateId",
	                    ActionBean.ID);
	            menu.addItems(actions.getStateTemplates(state, resources,
	            		state.getEntityId(), state.getEntityRegId()));
	            listMenus.add(menu);

	            request.setAttribute("menusToolbarDoc", listMenus);
	        }

        }catch(ISPACNullObject e)
        {
            //No existe formulario por defecto.
            //TODO: Realizar el tratamiento adecuadamente, por ahora
            //no se muestra nada.
            ISPACRewrite ispacpath = new ISPACRewrite(getServlet().getServletContext());
            strURL=ispacpath.rewriteRelativePath("common/empty.jsp");
        }

        //Se actualiza el estado de tramitación.
        storeStateticket(state, response);

        // mandamos los parámetros entity y regentity
        request.setAttribute("entityid", Integer.toString(state.getEntityId()));
        request.setAttribute("entityregid", Integer.toString(state
                .getEntityRegId()));

        /*
         * Se comprueba si la entidad seleccionada tiene una lista de registros, si es
         * así se creará un formateador genérico para mostrarlo.
         */

        // EJEMPLO 1:
        //
        // Muestra el resumen de los registros de la entidad tal y como los devuelve
        // el API de tramitación del esquema de expediente.
/*
        BeanFormatter beanformatter = new BeanFormatter();
        List regentlist = new ArrayList();
        for (Iterator ite = schemebeanlist.iterator(); ite.hasNext();)
        {
            SchemeEntityBean scheEntBean = (SchemeEntityBean) ite.next();
            int entityId = scheEntBean.getItem().getKeyInt();
            if (entityId == state.getEntityId()
                    && scheEntBean.getRegs().size() > 1)
            {
                GenericFormatterFactory formatterfactory = new GenericFormatterFactory();
                ItemBean bean = (ItemBean) scheEntBean.getRegs().get(0);
                beanformatter = formatterfactory.getBeanFormatter(bean
                        .getItem());
                regentlist = scheEntBean.getRegs();
            }
        }
        request.setAttribute("RegEntityListFormatter", beanformatter);
        request.setAttribute("RegEntityList", regentlist);


*/

        // EJEMPLO 2:
        //
        // Muestra la lista de entidades del expediente

        BeanFormatter beanformatter = new BeanFormatter();
        List regentlist = new ArrayList();



        if (state.getEntityId()!=0)
        {
            IItemCollection itc=entitiesAPI.getEntities(state.getEntityId(),state.getNumexp(),null);
            if (itc.next())
            {
                //Se necesita un IItem de la colección para crear un formateador por defecto.
                GenericFormatterFactory formatterfactory = new GenericFormatterFactory();
                beanformatter = formatterfactory.getBeanFormatter(itc.value());
                regentlist=CollectionBean.getBeanList(itc);
            }
        }

        request.setAttribute("RegEntityListFormatter", beanformatter);
        request.setAttribute("RegEntityList", regentlist);

        ///////////////////////////////////////////////////////


        // Se cambia la página jsp  asociada al formulario de la entidad
        /*
        ComponentDefinition componentDefinition = TilesUtil.getDefinition(
                "body.tab", request, servlet.getServletContext());
        componentDefinition.putAttribute("tabform", strURL);
        */
		request.setAttribute("application", strURL);
        return mapping.findForward("success");
    }
    
}