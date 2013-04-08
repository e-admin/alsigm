package ieci.tdw.ispac.ispacmgr.action;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISearchAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.bean.WLStageBean;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowStagesListAction extends BaseAction
{

    public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     SessionAPI session) throws Exception
    {

	  	ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();

		///////////////////////////////////////////////
		// Cambio del estado de tramitación
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		Map params = request.getParameterMap();
		IState state = managerAPI.enterState(getStateticket(request),ManagerState.STAGESLIST,params);
		storeStateticket(state,response);

	    // Cargar el contexto de la cabecera (miga de pan)
	    SchemeMgr.loadContextHeader(state, request, getResources(request), session);

	    /////////////////////////////////////////////////
		// Lista de fases
	    IWorklist managerwl=managerAPI.getWorklistAPI();
	    IItemCollection itcStage = managerwl.getStages(state);
	    List stagesList = CollectionBean.getBeanList(WLStageBean.class,itcStage);
	    request.setAttribute("StagesList",stagesList);

	    IProcedure iProcedure = invesFlowAPI.getProcedure(state.getPcdId());
	    // TxProcedure con los nodos del procedimiento
	    // fases, trámites y nodos de sincronización en Maps con claves enteras
		Map stages = iProcedure.getStages().toMap();
		Iterator it=stagesList.iterator();
	    while (it.hasNext())
	    {
	        WLStageBean stage = (WLStageBean) it.next();
	        stage.processProperties(iProcedure,stages);
	    }

	    /////////////////////////////////////
		// FORMULARIOS DE BUSQUEDA: EDUARDO
/*		List forms = (List) request.getAttribute("formList");
		ISearchAPI searchAPI = invesFlowAPI.getSearchAPI();
		if (forms == null)
		{
			IItemCollection icForms = searchAPI.getSearchForms();
			forms = CollectionBean.getBeanList(icForms);
			request.setAttribute("formList", forms);
		}

		String formSelect = (String)request.getAttribute("formSelect");
		if (formSelect == null)
		{
			ItemBean bean = (ItemBean)forms.get(0);
			formSelect = ((Long)bean.getProperty("ID")).toString();
		}
		request.setAttribute("formSelect", formSelect);

		//Obtenemos formulario seleccionado

		ISPACRewrite ispacpath=new ISPACRewrite(getServlet().getServletContext());
		String xslurl = ispacpath.rewriteRealPath("xsl/SearchForm.xsl");
		String frm = searchAPI.buildHTMLSearchForm(Integer.parseInt(formSelect), xslurl);
		request.setAttribute("Form", frm);
*/

        /////////////////////////////////////
        // FORMULARIOS DE BUSQUEDA: EDUARDO
        List forms = (List) request.getAttribute("formList");
        ISearchAPI searchAPI = invesFlowAPI.getSearchAPI();
        if (forms == null)
        {
            IItemCollection icForms = searchAPI.getSearchForms();
            forms = CollectionBean.getBeanList(icForms);
            request.setAttribute("formList", forms);
        }

        String formSelect = (String) request.getAttribute("formSelect");
        if (formSelect == null && forms.size()>0 )
        {
            ItemBean bean = (ItemBean) forms.get(0);
            formSelect = bean.getProperty("ID").toString();
        }
        request.setAttribute("formSelect", formSelect);

        //Obtención del formulario seleccionado

        ISPACRewrite ispacpath = new ISPACRewrite(getServlet()
                .getServletContext());
        String xslurl = ispacpath.rewriteRealPath("xsl/SearchForm.xsl");

        String frm ="";
        if (formSelect!=null)
        {
	        try
	        {
	            frm = searchAPI.buildHTMLSearchForm(Integer.parseInt(formSelect), xslurl, ResourceBundle.getBundle(BUNDLE_NAME, cct.getLocale()), cct.getLocale(), request.getParameterMap());
	        }
	        catch(ISPACException e)
	        {
	            frm = "<b>Error:</b>El formulario de consulta no est&aacute; bien definido";
	        }
        }
        request.setAttribute("Form", frm);


		// FIN FORMULARIOS DE BUSQUEDA: EDUARDO

	    return mapping.findForward("success");
    }
    
}