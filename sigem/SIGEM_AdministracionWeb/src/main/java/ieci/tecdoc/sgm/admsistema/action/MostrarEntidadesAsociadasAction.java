package ieci.tecdoc.sgm.admsistema.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MostrarEntidadesAsociadasAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(MostrarEntidadesAsociadasAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
