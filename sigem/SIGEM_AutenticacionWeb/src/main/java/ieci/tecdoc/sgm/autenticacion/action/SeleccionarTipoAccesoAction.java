package ieci.tecdoc.sgm.autenticacion.action;

import ieci.tecdoc.sgm.autenticacion.util.TipoAutenticacionCodigos;
import ieci.tecdoc.sgm.autenticacion.form.TipoAccesoForm;
import ieci.tecdoc.sgm.autenticacion.utils.Defs;
//import ieci.tecdoc.sgm.registro.utils.Defs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionError;
//import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SeleccionarTipoAccesoAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		TipoAccesoForm listTiposBean = (TipoAccesoForm)form;
		
		try{
			int tipoAceptado;
			try {
				tipoAceptado = new Integer(listTiposBean.getSelTipoAcceso()).intValue();
			} catch(NumberFormatException e) {
				tipoAceptado = new Integer((String)request.getSession().getAttribute(Defs.ACCESO_SEL)).intValue();
			}
			if (tipoAceptado == TipoAutenticacionCodigos.WEB_USER){
				request.getSession().setAttribute(Defs.ACCESO_SEL, ""+TipoAutenticacionCodigos.WEB_USER);
				return mapping.findForward("login");
			}else if (tipoAceptado == TipoAutenticacionCodigos.X509_CERTIFICATE){
				request.getSession().setAttribute(Defs.ACCESO_SEL, ""+TipoAutenticacionCodigos.X509_CERTIFICATE);
				return mapping.findForward("certificado");
			}else return mapping.findForward("failure");
		}catch(Exception e){
	   		return mapping.findForward("failure");
	   	}
	}
}
