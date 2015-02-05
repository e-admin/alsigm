package ieci.tdw.ispac.ispacmgr.components.worklist;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;
import ieci.tdw.ispac.ispacweb.components.DefaultWebComponent;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Muestra la información de las fases bajo la responsabilidad del usuario .
 *
 */
public class StagesComponent extends DefaultWebComponent { 

	/**
	 * Constructor.
	 */
	public StagesComponent() {
		super();
		setId("stages");
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
    	setTitle(getMessage(ctx.getLocale(), "stages.titulo"));

		// Responsabilidades del usuario conectado
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		String resp = workListAPI.getRespString();

		// Obtenemos el listado de los procedimientos
		IItemCollection itcPcd = workListAPI.getProcedures(resp);
		if (itcPcd.next()) {

			// Lista de procedimientos
			List pcds = itcPcd.toList();
			
			// Mapa de procedimientos con los expedientes en las fases para cada procedimiento
			Map mapStages = SpacMgr.getStagesProcs(sessionAPI, itcPcd, resp);

			html += "<ul>";

			for (int pcdCont = 0; pcdCont < pcds.size(); pcdCont++) {
				
				IItem procedure = (IItem) pcds.get(pcdCont);
				
				html += "<li class=\"menu11Bold\" style=\"list-style-image: url("
					+ rewriteHref(context, request, "img/procedimiento.gif")
					+ ");\">";
		    	html += getMessage(ctx.getLocale(), "stages.label.procedimiento", new Object[] {
		    		procedure.getString("NAME"),
		    		procedure.getString("NVERSION")
		    	});
				html += "</li>";
				
				List stages = (List) mapStages.get(procedure.getString("ID"));
				if (!CollectionUtils.isEmpty(stages)) {
					
					html += "<ul>";
					
					for (int i = 0; i < stages.size(); i++) {
						
						ItemBean stage = (ItemBean) stages.get(i);
						
						html += "<li style=\"list-style-image: url("
							+ rewriteHref(context, request, "img/fase.gif")
							+ ");\">";
						
						html += "<a href=\"" + request.getContextPath() + "/showProcessList.do?stagePcdId=" + stage.getString("ID_FASE") + "\" class=\"menu\">"
							+ stage.getString("NOMBRE") + "&nbsp;(" + stage.getString("COUNT") + ")"
							+ "</a>";
						
						html += "</li>";
					}
					
					html += "</ul>";
				}
			}
			
			html += "</ul>";
		}
    	
    	setContent(html);
    }
}
