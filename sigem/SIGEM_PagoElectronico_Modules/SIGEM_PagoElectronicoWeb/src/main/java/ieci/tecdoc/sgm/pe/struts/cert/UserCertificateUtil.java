package ieci.tecdoc.sgm.pe.struts.cert;

import ieci.tecdoc.sgm.pe.struts.Constantes;

import javax.servlet.http.HttpServletRequest;

public class UserCertificateUtil {

//	public static CertInfo getUserCertificate(HttpServletRequest request) throws PagoElectronicoExcepcion{
//		CertInfo oCertificado = null;
//		// Buscamos en la sesión.
//		Object oAux = request.getSession().getAttribute(Constantes.KEY_USER_CERTIFICATE);
//		if(oAux == null){
//			try {
//				oCertificado = Certificate.getClientCert(request);
//				request.getSession().setAttribute(Constantes.KEY_USER_CERTIFICATE, oCertificado);
//				return oCertificado;
//			} catch (PagoElectronicoExcepcion e) {
//				e.printStackTrace();
//				throw e;
//			}
//		}else{
//			return (CertInfo)oAux;
//		}
//	}

	public static CertInfo getUserData(HttpServletRequest request){
		// Buscamos en la sesión.
		Object oAux = request.getSession().getAttribute(Constantes.KEY_USER_DATA);
		if(oAux == null){
			return null;
		}else{
			return (CertInfo)oAux;
		}
	}

}
