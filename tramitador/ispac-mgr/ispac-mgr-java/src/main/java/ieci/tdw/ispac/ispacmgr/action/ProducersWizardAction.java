package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.producers.IProducersConnector;
import ieci.tdw.ispac.ispaclib.producers.ProducersConnectorFactory;
import ieci.tdw.ispac.ispaclib.producers.vo.Producer;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.ProducersWizardForm;
import ieci.tdw.ispac.ispacweb.webdav.util.URLEncoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para seleccionar un productor.
 * 
 * @author 65783177
 * 
 */
public class ProducersWizardAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		// Conector de productores
		IProducersConnector producersConnector = ProducersConnectorFactory
				.getInstance()
				.getProducersConnector(session.getClientContext());

		ProducersWizardForm producersWizardForm = (ProducersWizardForm) form;

		// Comprobar si se ha seleccionado un productor
		if (StringUtils.isNotBlank(producersWizardForm.getFinalId())) {

			// Información del productor seleccionado
			Producer producer = producersConnector
					.getProducer(producersWizardForm.getFinalId());

			ItemBean itemBean = new ItemBean();
			itemBean.setProperty("ID", producer.getId());
			itemBean.setProperty("NAME", producer.getName());
			request.setAttribute("Substitute", itemBean);

			// Obtener los parámetros con los campos del formulario en los que
			// se establecerá el responsable
			String parameters = request.getParameter("parameters");
			if (StringUtils.isEmpty(parameters)) {
				parameters = (String) request.getAttribute("parameters");
			}

			// Obtiene los parametros del tag y los salva en la request
			Map sParameters = (Map) request.getSession().getAttribute(parameters);
			if (sParameters != null) {
				request.setAttribute("parameters", sParameters);
			}

            // No refrescar la pantalla desde la que se llama 
            request.setAttribute("Refresh", "false");
            
			if (request.getParameter("formName") != null) {
				request.setAttribute("formName", request.getParameter("formName"));
			}
			if (request.getParameter("workframe") != null) {
				request.setAttribute("workframe", request.getParameter("workframe"));
			}

			return mapping.findForward("success");

		} else { // Se está navegando por el árbol de productores

			String producerId = producersWizardForm.getNavigationId();
			if (StringUtils.isBlank(producerId)) {
				Producer producer = producersConnector.getRootProducer();
				if (producer != null) {
					producerId = producer.getId();
				}
			}

			// Ancestros del productor
			request.setAttribute("ancestors",
					producersConnector.getProducerAncestors(producerId));

			// Hijos del productor
			request.setAttribute("children",
					producersConnector.getProducerChildren(producerId));

            // Navegación por la estructura organizativa
            request.setAttribute("URLParams", buildURLParams(request.getParameterMap()));           

			return mapping.findForward("nav");
		}
	}

    protected String buildURLParams(Map<String, String[]> parameters) {
    	
        StringBuffer url = new StringBuffer();
        Map urlKeys = new HashMap();
        URLEncoder encoder = new URLEncoder();
        
        for (String key : parameters.keySet()) {
        	
            if ((!urlKeys.containsKey(key)) && 
            	(!key.equals("finalId") && !key.equals("navigationId") && (key.indexOf("property") == -1))) {
            	
                if (url.length() != 0) {
                	url.append("&");
                }
                
               	url.append(key).append("=").append(encoder.encode(((String[])parameters.get(key))[0]));
               	
                urlKeys.put(key, null);
            }
        }

        return  url.toString();
    }

}