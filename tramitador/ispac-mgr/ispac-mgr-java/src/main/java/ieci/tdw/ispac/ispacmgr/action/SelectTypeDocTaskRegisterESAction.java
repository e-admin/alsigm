package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectTypeDocTaskRegisterESAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

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
		String registerType = request.getParameter("registerType");
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

        request.setAttribute("numRegister", request.getParameter("numRegister"));
        request.setAttribute("registerType", registerType);
        request.setAttribute("parametersName", request.getParameter("parametersName"));
		request.setAttribute("taskPcdId", taskPcdId);
		request.setAttribute("taskId", request.getParameter("taskId"));
		request.setAttribute("stageId", request.getParameter("stageId"));

		return mapping.findForward("success");
	}

}
