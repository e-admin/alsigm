package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;

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

/**
 * Action para la notificación de documentos en bloque.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class NotifyDocumentsAction extends BaseAction {

	/**
	 * {@inheritDoc}
	 * 
	 * @see ieci.tdw.ispac.ispacmgr.action.BaseAction#executeAction(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse,
	 *      ieci.tdw.ispac.api.impl.SessionAPI)
	 */
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		EntityForm defaultForm = (EntityForm) form;

		// Identificadores de los documentos
		String[] multibox = defaultForm.getMultibox();

		if (ArrayUtils.isEmpty(multibox)) {
			logger.warn("No se ha seleccionado ningún documento");
			throw new ISPACInfo(getResources(request).getMessage(
					"forms.listdoc.notificarDocumentos.empty"), false);
		}

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();

		List<ItemBean> documents_ok = new ArrayList<ItemBean>();
		List<Map<String, Object>> documents_ko = new ArrayList<Map<String, Object>>();

		// Obtener la información de los documentos seleccionados
		IItemCollection documents = entitiesAPI.queryEntities(
				SpacEntities.SPAC_DT_DOCUMENTOS, new StringBuffer(" WHERE ")
						.append(DBUtil.addOrCondition("ID", multibox))
						.toString());

		@SuppressWarnings("unchecked")
		Iterator<IItem> iterator = documents.iterator();
		while (iterator.hasNext()) {

			// Información del documento
			IItem document = iterator.next();

			try {

				// Iniciar la notificación del documento
				genDocAPI.initNotification(document);

				documents_ok.add(new ItemBean(document));

			} catch (ISPACException e) {
				logger.error(
						"Error al iniciar la notificación del documento:"
								+ document.getKeyInt() + " - "
								+ document.getString("NOMBRE") + " ["
								+ document.getString("DESCRIPCION") + "]", e);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("documento", new ItemBean(document));
				map.put("error", e.getExtendedMessage(request.getLocale()));

				documents_ko.add(map);
			}
		}

		request.setAttribute("DOCUMENTS_OK", documents_ok);
		request.setAttribute("DOCUMENTS_KO", documents_ko);

		return mapping.findForward("success");
	}

}
