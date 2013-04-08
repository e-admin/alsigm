package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NaryTree;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Node;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NodeImplBD;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTree;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTreeDept;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Distribucion;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class CargarGruposDistribucionUnidadAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(CargarGruposDistribucionUnidadAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String id = request.getParameter("id");

		boolean borraCookies = false;

		ServiceTree serviceTree = request.getSession(false).getAttribute("serviceTreeGrupos") != null ?
								  (ServiceTree)request.getSession(false).getAttribute("serviceTreeGrupos") : null;

		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource", LocaleFilterHelper.getCurrentLocale(request));
        if (id == null )
        {
        	NaryTree tree = NaryTree.getInstance();

    		Node nodoRaiz = new NodeImplBD();

            tree = new NaryTree(null,nodoRaiz);
            serviceTree = new ServiceTreeDept(tree);

            nodoRaiz.setId(0);
            boolean hasChild = serviceTree.hasChildren(0, oServicio, entidad, Distribucion.TIPO_GRUPO, false );
            nodoRaiz.setHasChild(hasChild);
            nodoRaiz.setTitle(rb.getString("ieci.tecdoc.sgm.rpadmin.unidades.distribucion.grupos")); // Cambiar el properties
            if (hasChild)
                serviceTree.addChildren(nodoRaiz.getId(), oServicio, entidad, Distribucion.TIPO_GRUPO, false);

            borraCookies = true;

            request.getSession(false).setAttribute("serviceTreeGrupos", serviceTree);

        }
        else
        {

            serviceTree.addChildren(Integer.parseInt(id), oServicio, entidad, Distribucion.TIPO_GRUPO, false);

        }

        String arbol = serviceTree.getTreeString(Distribucion.TIPO_GRUPO, false, LocaleFilterHelper.getCurrentLocale(request));
        request.setAttribute("treeStringGrupos",arbol);
        Boolean b = new Boolean(borraCookies);
        request.setAttribute("borraCookiesGrupos",b);

        return mapping.findForward("success");
	}

}
