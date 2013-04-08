package ieci.tdw.ispac.ispacmgr.components.worklist;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.components.DefaultWebComponent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Muestra la información de los sucesos en la bandeja de entrada.
 *
 */
public class NoticesComponent extends DefaultWebComponent {

	/**
	 * Constructor.
	 */
	public NoticesComponent() {
		super();
		setId("sucesos");
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
    	setTitle(getMessage(ctx.getLocale(), "sucesos.titulo"));

		// Responsabilidades del usuario conectado
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();
		String resp = workListAPI.getRespString();
		
		// Bandeja de entrada
		IInboxAPI inboxAPI = invesflowAPI.getInboxAPI();
		IItemCollection icInbox = inboxAPI.getInbox(resp);
		
		if (icInbox.next()) {
			html += "<ul>";

			while (icInbox.next()) {
				IItem inboxItem = (IItem) icInbox.value();
				html += "<li style=\"list-style-image: url("
					+ rewriteHref(context, request, "img/inform.gif")
					+ ");\">";
				html += "<a href=\"" + request.getContextPath() + "/" + inboxItem.getString("URL") + "\" class=\"menu\">"
					+ getMessage(ctx.getLocale(), inboxItem.getString("NOMBRE"), 
							new Object[] { inboxItem.getString("COUNT") })
					+ "</a>";
				html += "</li>";
			}
			
			html += "</ul>";
		}
    	
    	setContent(html);
	}
    
}
