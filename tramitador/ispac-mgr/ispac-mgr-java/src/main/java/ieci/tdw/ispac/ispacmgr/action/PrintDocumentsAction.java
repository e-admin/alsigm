package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacmgr.action.form.DocumentsForm;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que imprime los documentos del expediente seleccionados por el usuario.
 *
 */
public class PrintDocumentsAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		List documents = new ArrayList();
		
		String[] documentIds = ((DocumentsForm) form).getMultibox();
		if (!ArrayUtils.isEmpty(documentIds)) {

			IItem doc;
			int docId;

			IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();

			for (int i = 0; i < documentIds.length; i++) {
				
				docId = TypeConverter.parseInt(documentIds[i], -1);
				if (docId > 0) {
					
					// Obtener la información del documento
					doc = entitiesAPI.getDocument(docId);
					if (doc != null) {
						documents.add(new ItemBean(doc));
					}
				}
			}
		}

		request.setAttribute("Documents", documents);

		return mapping.findForward("success");
	}

}
