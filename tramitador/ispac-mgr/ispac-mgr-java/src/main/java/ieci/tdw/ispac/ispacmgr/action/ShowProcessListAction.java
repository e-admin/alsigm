package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.manager.ProcessListFormatFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowProcessListAction extends BaseAction  {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     SessionAPI session) throws Exception {

  	ClientContext cct = session.getClientContext();

	ICatalogAPI catalogAPI = cct.getAPI().getCatalogAPI();

	///////////////////////////////////////////////
	// Cambio del estado de tramitación
	IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
	Map params = request.getParameterMap();
	IState state = managerAPI.enterState(getStateticket(request),ManagerState.PROCESSESLIST,params);
	storeStateticket(state,response);

    /////////////////////////////////////////////////
	// Lista de expedientes
	IItem ctPcd = catalogAPI.getCTProcedure(state.getPcdId());
	IItem pcdStage = cct.getAPI().getProcedureStage(state.getStagePcdId());
	IItem ctStage = catalogAPI.getCTStage(pcdStage.getInt("ID_CTFASE"));

	// Obtención del formato de la lista de procesos
	// teniendo en cuenta los códigos y los identificadores del procedimiento y la fase
	ProcessListFormatFactory proclistfactory=new ProcessListFormatFactory(getISPACPath());
	InputStream istream=proclistfactory.getProcessListFormat(state.getPcdId(), ctPcd.getString("COD_PCD"),
			state.getStagePcdId(), ctStage.getString("COD_FASE"));

	IWorklist managerwl=managerAPI.getWorklistAPI();
    IItemCollection itc = managerwl.getProcesses(state,istream);

    // Lista de expedientes en la peticion
    List expStageList = CollectionBean.getBeanList(itc);
    request.setAttribute("ExpStageList", expStageList);

    // Cargar el contexto de la cabecera (miga de pan)
    SchemeMgr.loadContextHeader(state, request, getResources(request), session);

    // Establecer el menu
	request.setAttribute("menus", MenuFactory.getWorkListMenu(cct, state, getResources(request)));

    ///////////////////////////////////////////////
    // Formateador
	istream.reset();
	// Propiedades del formateador para la vista
	request.setAttribute("Formatter", proclistfactory.getBeanFormatter(istream));

    return mapping.findForward("success");
  }

}