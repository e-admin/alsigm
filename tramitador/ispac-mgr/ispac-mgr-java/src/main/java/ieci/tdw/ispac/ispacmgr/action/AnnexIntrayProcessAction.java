package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.SicresConnectorFactory;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.api.impl.states.ExpedientState;

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

public class AnnexIntrayProcessAction extends BaseDispatchAction {


	public ActionForward defaultExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		String register = request.getParameter("register");
		String numexp = request.getParameter("numexp");

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IInboxAPI inbox = invesFlowAPI.getInboxAPI();
		Intray intray = inbox.getIntray(register);
		String registerNumber = intray.getRegisterNumber();


		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());

		if (entitiesAPI.countResultQuery(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "' AND NREG = '" + registerNumber + "' AND TP_REG = '" + RegisterType.ENTRADA+"'") > 0){
			throw new ISPACInfo("exception.register.linked",false);
		}

		// Obtenemos el conector con sicres
		ISicresConnector sicresConnector = SicresConnectorFactory.getInstance().getSicresConnector(session.getClientContext());
		// Se comprueba la existencia del apunte de registro antes de buscar los tramites abiertos
		RegisterInfo registerInfo = new RegisterInfo(null, registerNumber, null, RegisterType.ENTRADA);
		Register registro = sicresConnector.readRegister(registerInfo);
		if (registro == null){
       		throw new ISPACInfo(getResources(request).getMessage("registro.entrada.notfound")+" "+registerNumber,false);
		}


		//Si no hay documentos, no se busca tramite al que anexarlos
		if ((registro.getRegisterData().getInfoDocuments() == null) ||	(registro.getRegisterData().getInfoDocuments().length == 0)) {
			annex(mapping, form, request, response, session);
			return mapping.findForward("redirect");
		}

		IProcess process = invesFlowAPI.getProcess(numexp);
		IItemCollection itemcol = invesFlowAPI.getStagesProcess(process.getKeyInt());
		int stageId = -1;
		if (itemcol.next()){
			IItem item = itemcol.value();
			stageId = item.getKeyInt();
		}

		if (stageId == -1){
			throw new ISPACInfo("No se ha encontrado fase activa para el expediente '"+numexp+"'",false);
		}

		Map params = new HashMap();
		params.put("stageId", new String[]{String.valueOf(stageId)});

		//Listado de trámites ya iniciados
		IState state = new ExpedientState(ManagerState.EXPEDIENT, params, session.getClientContext());
		IWorklist managerwl=managerAPI.getWorklistAPI();
		IItemCollection collection = managerwl.getTasks(state);
		List expTaskList = CollectionBean.getBeanList(collection);

		request.setAttribute("RegisterESTaskList",expTaskList);
		request.setAttribute("stageId", ""+state.getStageId());

		//Añadimos el formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request.setAttribute("Formatter", factory.getFormatter(getISPACPath("/digester/opentaskregesformatter.xml")));


        request.setAttribute("registerType", RegisterType.ENTRADA);
        request.setAttribute("numRegister", registerNumber);
		request.setAttribute("register", register);

		return mapping.findForward("selectTask");

	}

	public ActionForward selectTypeDocTask(ActionMapping mapping,
			   ActionForm form,
             HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception{

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IGenDocAPI gendocAPI = invesFlowAPI.getGenDocAPI();

		String taskPcdId = request.getParameter("taskPcdId");

		// Obtener la lista de tipos de documento que se pueden
		// generar a partir de una tarea del procedimiento
		IItemCollection collection = gendocAPI.getDocTypesFromTaskPCD(Integer.parseInt(taskPcdId));

		List list = new ArrayList();
		if (collection != null) {
			list = CollectionBean.getBeanList(collection);
		}
		// Nos quedamos con los tipos de documentos con tipo de registro
		// el seleccionado
		String registerType = request.getParameter("tp_reg");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			ItemBean itemTypeDoc = (ItemBean) iterator.next();
			if (!StringUtils.equals(itemTypeDoc.getString("TIPOREG"), registerType))
				iterator.remove();
		}

		request.setAttribute("DocumentTypesTaskList", list);

		//Añadimos el formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request.setAttribute("Formatter",
				factory.getFormatter(getISPACPath("/digester/doctypeopentaskregesformatter.xml")));

        request.setAttribute("registerType", registerType);
		request.setAttribute("register", request.getParameter("register"));
		request.setAttribute("taskPcdId", taskPcdId);
		request.setAttribute("taskId", request.getParameter("taskId"));
		request.setAttribute("stageId", request.getParameter("stageId"));
		request.setAttribute("numexp", request.getParameter("numexp"));

		return mapping.findForward("selectTypeDocTask");
	}

	public ActionForward annex(ActionMapping mapping,
             ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response,
             SessionAPI session) throws Exception{

		String register = request.getParameter("register");
		String numexp = request.getParameter("numexp");

		int taskId = 0;
		if (StringUtils.isNotBlank(request.getParameter("taskId"))) {
			taskId = Integer.valueOf(request.getParameter("taskId"));
		}

		int typeDocId = 0;
		if (StringUtils.isNotBlank(request.getParameter("typeDocId"))) {
			typeDocId = Integer.valueOf(request.getParameter("typeDocId"));
		}

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IInboxAPI inbox = invesFlowAPI.getInboxAPI();
		Intray intray = inbox.getIntray(register);

		// Anexar el registro al expediente
		inbox.annexToProcess(intray.getId(), numexp, taskId, typeDocId);

		return mapping.findForward("success");
	}

}
