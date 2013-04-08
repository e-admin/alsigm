package ieci.tdw.ispac.ispacmgr.components.worklist;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.TreeItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Muestra la información de los trámites bajo la responsabilidad del usuario.
 * 
 */
public class TreeClosedTasksComponent extends AbstractProcedureTreeComponent {

	/**
	 * Constructor.
	 */
	public TreeClosedTasksComponent() {
		super();
		setId("treeClosedTasks");
	}

	/**
	 * Renderiza el componente.
	 * 
	 * @param context
	 *            Contexto de servlets.
	 * @param request
	 *            Petición del cliente.
	 * @param sessionAPI
	 *            API de sesión.
	 * @param params
	 *            Parámetros de configuración.
	 * @return Código HTML para mostrar en pantalla.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void render(ServletContext context, HttpServletRequest request,
			ISessionAPI sessionAPI) throws ISPACException {

		// Contexto del cliente
		ClientContext ctx = sessionAPI.getClientContext();

		// Título
		setTitle(getMessage(ctx.getLocale(), "closed.tasks.titulo"));

		// Responsabilidades del usuario conectado
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		String resp = workListAPI.getRespString();

		// Mapa de procedimientos con trámites cerrados
		@SuppressWarnings("unchecked")
		Map<String, List<ItemBean>> tasksMap = SpacMgr.getPcdClosedTasksMap(sessionAPI, resp);
		
		if (!MapUtils.isEmpty(tasksMap)) {

			// Árbol de procedimientos
			String html = drawPcdTree(ctx, context, request, getProcedureTree(ctx), tasksMap);
	
			setContent(html);
		}
	}
    
	protected String drawPcdTree(ClientContext ctx, ServletContext context,
			HttpServletRequest request, TreeItemBean pcdTree,
			Map<String, List<ItemBean>> tasksMap) throws ISPACException {

		String html = "";

		if (pcdTree != null) {

			String childrenHTML = "";

			@SuppressWarnings("unchecked")
			List<TreeItemBean> children = pcdTree.getChildren();
			if (!CollectionUtils.isEmpty(children)) {
				for (int i = 0; i < children.size(); i++) {
					childrenHTML += drawPcdTree(ctx, context, request,
							(TreeItemBean) children.get(i), tasksMap);
				}
			}

			IItem item = pcdTree.getItem();
			if (item != null) {

				String tasksHTML = "";
				List<ItemBean> tasks = tasksMap.get(item
						.getString("SPAC_P_PROCEDIMIENTO:ID"));
				if (!CollectionUtils.isEmpty(tasks)) {
					for (int i = 0; i < tasks.size(); i++) {
						tasksHTML += drawTaskLink(context, request,
								tasks.get(i));
					}
				}

				if (StringUtils.isNotBlank(tasksHTML)
						|| StringUtils.isNotBlank(childrenHTML)) {
					html += "<li class=\"menu11Bold\" style=\"list-style-image: url("
							+ rewriteHref(context, request,
									"img/procedimiento.gif") + ");\">";

					html += getMessage(
							ctx.getLocale(),
							"closed.tasks.label.procedimiento",
							new Object[] {
									item.getString("SPAC_P_PROCEDIMIENTO:NOMBRE"),
									item.getString("SPAC_P_PROCEDIMIENTO:NVERSION") });
					html += "</li>";
					html += "<ul>";
					html += tasksHTML;
					html += "</ul>";
				}
			}

			if (StringUtils.isNotBlank(childrenHTML)) {
				html += "<ul>" + childrenHTML + "</ul>";
			}
		}

		return html;
	}	
	
	protected String drawTaskLink(ServletContext context,
			HttpServletRequest request, ItemBean task
			) throws ISPACException {

		String html = "";

		IItem taskItem = task.getItem();
		if (taskItem != null) {

				html += "<li style=\"list-style-image: url("
						+ rewriteHref(context, request, "img/tramiteestado3.gif")
						+ ");\">";

				html += "<a href=\"" + request.getContextPath()
						+ "/showClosedTasksList.do?taskCtlId="
						+ taskItem.getInt("ID_CTTASK")
						+ "&pcdId="
						+ taskItem.getInt("ID_PROC")
						+ "\" class=\"menu\">"
						+ task.getString("NOMBRE") + "&nbsp;("
						+ taskItem.getInt("COUNT") + ")" + "</a>";

				html += "</li>";

		}

		return html;
	}
}
