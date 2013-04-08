package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowIntrayAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		IInvesflowAPI invesflowAPI = session.getAPI();
    	IInboxAPI inboxAPI = invesflowAPI.getInboxAPI();

        // Registro de entrada
		String intrayId = request.getParameter("id");

	    // Menú
	    request.setAttribute("menus", MenuFactory.getIntrayMenu(session.getClientContext(), 
	    		getResources(request)));

        // Datos asociados al registro de entrada
		Intray intray = inboxAPI.getIntray(intrayId);
		if (intray != null) {
			
//			// Comprobar el estado del registro de entrada
//			if (intray.getState() == IInboxAPI.PENDIENTE) {
//				inboxAPI.changeState(register, IInboxAPI.ACEPTADO);
//			}

			// Si la distribución está aceptada, obtener los expedientes relacionados con el
			// registro distribuido.
			if (intray.getState() == IInboxAPI.DIST_ESTADO_ACEPTADA) {
				IItemCollection regs = invesflowAPI.getEntitiesAPI().getExpedientsByRegNum(intray.getRegisterNumber());
				request.setAttribute("registers", CollectionBean.getBeanList(regs));
			}
		}

		request.setAttribute("Intray", intray);

		
		// Obtener la información de los anexos
		List annexes = getAnnexes(inboxAPI, intrayId);
		request.setAttribute("documents", annexes);
		
		return mapping.findForward("success");
	}
	

	private List getAnnexes(IInboxAPI inboxAPI, String intrayId) throws ISPACException {
		
		List annexes = new ArrayList();

		Properties properties = new Properties();
		properties.add(new Property(0,"DOC_ID","DOC_ID",Types.VARCHAR));
		properties.add(new Property(1,"INTRAY_ID", "INTRAY_ID", Types.VARCHAR));
		properties.add(new Property(2, "NAME","NAME", Types.VARCHAR));
		properties.add(new Property(2, "EXT","EXT", Types.VARCHAR));

		Annexe[] anexos = inboxAPI.getAnnexes(intrayId);
		if (!ArrayUtils.isEmpty(anexos)) {
			for (int i = 0; i<anexos.length; i++) {
				Annexe doc = anexos[i];
				if (doc != null) {
					
					IItem itemDoc = new GenericItem(properties, "DOC_ID");
					itemDoc.set("DOC_ID", doc.getId());
					itemDoc.set("INTRAY_ID", intrayId);
					itemDoc.set("NAME", doc.getName());
					itemDoc.set("EXT", doc.getExt());
					
					annexes.add(itemDoc);
				}
			}
		}
		
		ListCollection iitemCollection = new ListCollection(annexes);
		return CollectionBean.getBeanList(iitemCollection);
	}
	
}
