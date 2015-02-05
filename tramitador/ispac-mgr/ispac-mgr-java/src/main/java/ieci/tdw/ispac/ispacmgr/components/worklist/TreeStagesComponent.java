package ieci.tdw.ispac.ispacmgr.components.worklist;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.TreeItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Muestra la información de las fases bajo la responsabilidad del usuario .
 *
 */
public class TreeStagesComponent extends AbstractProcedureTreeComponent { 

	/**
	 * Constructor.
	 */
	public TreeStagesComponent() {
		super();
		setId("treeStages");
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

			// Mapa de procedimientos con los expedientes en las fases para cada procedimiento
			@SuppressWarnings("unchecked")
			Map<String, List<ItemBean>> stagesMap = SpacMgr.getStagesProcs(sessionAPI, itcPcd, resp);
			
			// Árbol de procedimientos
			String html = drawPcdTree(ctx, context, request, getProcedureTree(ctx), stagesMap);
		
			setContent(html);
		}
    }
    
	protected String drawPcdTree(ClientContext ctx, ServletContext context,
			HttpServletRequest request, TreeItemBean pcdTree, Map<String, List<ItemBean>> stagesMap)
			throws ISPACException {

    	String html = "";
    	
    	if (pcdTree != null) {
    		
    		String childrenHTML = "";
    		
			@SuppressWarnings("unchecked")
			List<TreeItemBean> children = pcdTree.getChildren();
			if (!CollectionUtils.isEmpty(children)) {
				for (int i = 0; i < children.size(); i++) {
					childrenHTML += drawPcdTree(ctx, context, request, children.get(i), stagesMap);	
				}
			}

    		IItem item = pcdTree.getItem();
    		if (item != null) {
    			
    			String stagesHTML = "";
				List<ItemBean> stages = stagesMap.get(item.getString("SPAC_P_PROCEDIMIENTO:ID"));
				if (!CollectionUtils.isEmpty(stages)) {
					
					stagesHTML += "<ul>";
					
					for (int i = 0; i < stages.size(); i++) {
						
						ItemBean stage = stages.get(i);
						
						stagesHTML += "<li style=\"list-style-image: url("
							+ rewriteHref(context, request, "img/fase.gif")
							+ ");\">";
						
						stagesHTML += "<a href=\"" + request.getContextPath() + "/showProcessList.do?stagePcdId=" + stage.getString("ID_FASE") + "\" class=\"menu\">"
							+ stage.getString("NOMBRE") + "&nbsp;(" + stage.getString("COUNT") + ")"
							+ "</a>";
						
						stagesHTML += "</li>";
					}
					
					stagesHTML += "</ul>";
				}
				
				if (StringUtils.isNotBlank(stagesHTML) || StringUtils.isNotBlank(childrenHTML)) {
			    	html += "<li class=\"menu11Bold\" style=\"list-style-image: url("
							+ rewriteHref(context, request, "img/procedimiento.gif")
							+ ");\">";
			    	
			    	html += getMessage(ctx.getLocale(), "stages.label.procedimiento", new Object[] {
			    		item.getString("SPAC_P_PROCEDIMIENTO:NOMBRE"),
			    		item.getString("SPAC_P_PROCEDIMIENTO:NVERSION")
			    	});
					html += "</li>";
					html += stagesHTML;
				}
				
    		}
    		
    		if (StringUtils.isNotBlank(childrenHTML)) {
    			html += "<ul>" + childrenHTML + "</ul>";
    		}
    	}

		return html;
    }
}
