package ieci.tdw.ispac.ispacmgr.components.worklist;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;
import ieci.tdw.ispac.ispacweb.components.DefaultWebComponent;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Muestra la información de los trámites bajo la responsabilidad del usuario.
 *
 */
public class TasksComponent extends DefaultWebComponent { 

	/**
	 * Constructor.
	 */
	public TasksComponent() {
		super();
		setId("tasks");
	}

	/**
	 * Renderiza el componente.
	 * @param context Contexto de servlets.
	 * @param request Petición del cliente.
	 * @param sessionAPI API de sesión.
	 * @param params Parámetros de configuración.
	 * @return Código HTML para mostrar en pantalla.
	 * @throws ISPACException si ocurre algún error.
	 */
    public void render(ServletContext context, HttpServletRequest request, ISessionAPI sessionAPI) 
    		throws ISPACException { 
		
    	String html = "";

    	// Contexto del cliente
    	ClientContext ctx = sessionAPI.getClientContext();

		// Título
    	setTitle(getMessage(ctx.getLocale(), "tasks.titulo"));

		// Responsabilidades del usuario conectado
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		String resp = workListAPI.getRespString();
		
		// Obtenemos el listado de los trámites
		IItemCollection itcTask = workListAPI.getProcedureTasks(resp);
		if (itcTask.next()) {

			// Mapa de subprocesos con las actividades para cada subproceso
			Map mapActivities = null;
			
			// Obtenemos el listado de los subprocesos
			IItemCollection itcSubPcd = workListAPI.getSubProcedures(resp);
			if (itcSubPcd.next()) {
				mapActivities = SpacMgr.getStagesProcs(sessionAPI, itcSubPcd, resp);
			}

			html += "<ul>";

			while (itcTask.next()) {
				
				IItem task = (IItem) itcTask.value();
				
				if (task.getInt("TIPO") == ITask.SIMPLE_TASK_TYPE) {

					html += "<li style=\"list-style-image: url("
						+ rewriteHref(context, request, "img/tramite.gif")
						+ ");\">";

					html += "<a href=\"" + request.getContextPath() + "/showTasksList.do?taskCtlId=" + task.getInt("ID_CTTASK") + "\" class=\"menu\">"
						+ task.getString("NAME") + "&nbsp;(" + task.getInt("COUNT") + ")"
						+ "</a>";
					
					html += "</li>";

				} else if (task.getInt("TIPO") == ITask.COMPLEX_TASK_TYPE) {
					
					String subpcdId = task.getString("ID_SUBPCD");
					
					if (!MapUtils.isEmpty(mapActivities)) {
						List activities = (List) mapActivities.get(subpcdId);
						if (!CollectionUtils.isEmpty(activities)) {

							html += "<li class=\"menu\" style=\"list-style-image: url("
								+ rewriteHref(context, request, "img/procedimiento.gif")
								+ ");\">";
							html += task.getString("NAME");
							html += "</li>";

							html += "<ul>";
							
							for (int i = 0; i < activities.size(); i++) {
								
								ItemBean activity = (ItemBean) activities.get(i);
								
								html += "<li style=\"list-style-image: url("
									+ rewriteHref(context, request, "img/fase.gif")
									+ ");\">";

								html += "<a href=\"" + request.getContextPath() + "/showSubProcessList.do?activityPcdId=" 
									+ activity.getString("ID_FASE") + "&taskCtlId=" + task.getString("ID_CTTASK") + "\" class=\"menu\">"
									+ activity.getString("NOMBRE") + "&nbsp;(" + activity.getString("COUNT") + ")"
									+ "</a>";
								
								html += "</li>";
								
							}
							
							html += "</ul>";
						}
					}
				}
			}
			
			html += "</ul>";
		}
    	
    	setContent(html);
    }
}
