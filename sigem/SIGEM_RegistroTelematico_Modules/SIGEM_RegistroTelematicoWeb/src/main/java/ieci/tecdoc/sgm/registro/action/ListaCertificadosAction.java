package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.registro.form.ListaCertificadosForm;
import ieci.tecdoc.sgm.registro.utils.Defs;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListaCertificadosAction extends RegistroWebAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		Collection certificados = new ArrayList();
		ListaCertificadosForm listCertBean = (ListaCertificadosForm)form;
		//String tramiteId = request.getParameter(Defs.TRAMITE_ID);
		//String tramiteId = (String)request.getSession().getAttribute(Defs.TRAMITE_ID);
		BigInteger certId = new BigInteger("-1");
		//String description = "";
		
		try{

			String attributeName = "javax.servlet.request.X509Certificate";
			Object obj = request.getAttribute(attributeName);
	      	if (obj instanceof java.security.cert.X509Certificate[]) {
	        	java.security.cert.X509Certificate[] certArray = (java.security.cert.X509Certificate[]) obj;
				certificados.add(new ListaCertificadosForm(certArray[0].getSubjectDN().toString(), certArray[0].getSerialNumber()));
				certId = certArray[0].getSerialNumber();
				//description = certArray[0].getSubjectDN().toString();
			}
			if (obj instanceof java.security.cert.X509Certificate) {
	        	certificados.add(((X509Certificate) obj).getSubjectDN().toString());
	        	certId = ((X509Certificate) obj).getSerialNumber();
	        	//description = ((X509Certificate) obj).getSubjectDN().toString();
			}
			listCertBean.setCertificadoId("");
	   	}catch(Exception e){
	   		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_LISTA_CERTIFICADOS);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	   		return mapping.findForward("failure");
	   	}
	   	if(certificados!=null && certificados.size()>0){
	   		request.getSession().setAttribute(Defs.CERTIFICADO_ID, ""+certId);
	   	}
	   	return mapping.findForward("success");
	}
}
