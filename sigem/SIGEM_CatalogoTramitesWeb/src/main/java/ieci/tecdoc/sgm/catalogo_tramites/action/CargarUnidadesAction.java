package ieci.tecdoc.sgm.catalogo_tramites.action;

import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl.NaryTree;
import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl.Node;
import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl.NodeImplBD;
import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl.ServiceTree;
import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl.UnidadesAdministrativasServiceTree;
import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl.UnidadesAdministrativasTreeConstants;
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.rpadmin.Distribucion;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * Action para cargar las unidades administrativas
 * @author IECISA
 *
 */
public class CargarUnidadesAction extends CatalogoTramitesWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Entidad entidad = SesionAdminHelper.obtenerEntidad(request);
		String id = request.getParameter("id");
		boolean borraCookies = false;

		ServiceTree serviceTree = request.getSession(false).getAttribute(UNIDADES_ADMINISTRATIVAS_SERVICE_TREE) != null ?
				(ServiceTree)request.getSession(false).getAttribute(UNIDADES_ADMINISTRATIVAS_SERVICE_TREE) : null;

		try {
			ServicioRPAdmin oServicio = LocalizadorServicios.getServicioRPAdmin();
			
			if (id == null )
			{				
				NaryTree tree = NaryTree.getInstance();
				Node nodoRaiz = new NodeImplBD();

				tree = new NaryTree(null,nodoRaiz);
				serviceTree = new UnidadesAdministrativasServiceTree(tree);

				nodoRaiz.setId(UnidadesAdministrativasTreeConstants.UNIDADES_ADMINISTRATIVAS_ROOT_NODE);
				boolean hasChild = serviceTree.hasChildren(UnidadesAdministrativasTreeConstants.UNIDADES_ADMINISTRATIVAS_ROOT_NODE, oServicio, entidad, Distribucion.TIPO_ORGANIZACION, false );
				nodoRaiz.setHasChild(hasChild);
				ResourceBundle rb = ResourceBundle.getBundle("resources/application", LocaleFilterHelper.getCurrentLocale(request));
				nodoRaiz.setTitle(rb.getString("ieci.tecdoc.sgm.rpadmin.unidades.titulo"));

				if (hasChild)
					serviceTree.addChildren(nodoRaiz.getId(), oServicio, entidad, Distribucion.TIPO_ORGANIZACION, false);

				borraCookies = true;
				request.getSession(false).setAttribute(UNIDADES_ADMINISTRATIVAS_SERVICE_TREE, serviceTree);

			}
			else {
				serviceTree.addChildren(Integer.parseInt(id), oServicio, entidad, Distribucion.TIPO_ORGANIZACION, false);
			}
		} catch (Exception e) {
	   		  request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_UNIDADES_ADMINISTRATIVAS);
	 		  request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	 		  logger.error(e);
	 		  return mapping.findForward(FAILURE_FORWARD);
		}

		String arbol = serviceTree.getTreeStringOrg(Distribucion.TIPO_ORGANIZACION, false, LocaleFilterHelper.getCurrentLocale(request));
		request.setAttribute(UNIDADES_ADMINISTRATIVAS_SERVICE_TREE_STRING, arbol);
		Boolean b = new Boolean(borraCookies);
		request.setAttribute(UNIDADES_ADMINISTRATIVAS_BORRA_COOKIES, b);

		return mapping.findForward(SUCCESS_FORWARD);
	}

	private static final Logger logger = Logger.getLogger(CargarUnidadesAction.class);
	private final String UNIDADES_ADMINISTRATIVAS_SERVICE_TREE = "serviceTreeUnidades";
	private final String UNIDADES_ADMINISTRATIVAS_SERVICE_TREE_STRING = "treeString";
	private final String UNIDADES_ADMINISTRATIVAS_BORRA_COOKIES = "borraCookies";
	
	private static final String SUCCESS_FORWARD = "success";
	private static final String FAILURE_FORWARD = "failure";
}
