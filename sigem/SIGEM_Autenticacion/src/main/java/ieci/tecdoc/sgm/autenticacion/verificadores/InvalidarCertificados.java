package ieci.tecdoc.sgm.autenticacion.verificadores;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoAutenticacionX509Info;

import java.security.cert.X509Certificate;

public class InvalidarCertificados {
	public CertificadoAutenticacionX509Info verifyCertificate(X509Certificate certificate){
		CertificadoAutenticacionX509Info info = new CertificadoAutenticacionX509Info();
		info.setValid(false);
		return info;
	}
}