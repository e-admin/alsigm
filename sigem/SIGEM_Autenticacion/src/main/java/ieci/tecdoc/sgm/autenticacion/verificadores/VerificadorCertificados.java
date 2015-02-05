package ieci.tecdoc.sgm.autenticacion.verificadores;

import java.security.cert.X509Certificate;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoAutenticacionX509Info;

public class VerificadorCertificados {
	public CertificadoAutenticacionX509Info verifyCertificate(X509Certificate certificate){
		CertificadoAutenticacionX509Info info = new CertificadoAutenticacionX509Info();
		info.setAdditionalInfo("");
		info.setCaResponse("PruTecDoc");
		info.setCIF("12345678X");
		info.setEmail("ieci@tecdoc.es");
		info.setIssuer(certificate.getIssuerDN().toString());
		info.setLegalEntity(false); //TipoSolicitante.INDIVIDUAL
		info.setName("MIGUEL CERVANTES SAAVEDRA");
		info.setNIF("12345678X");
		info.setSerialNumber(certificate.getSerialNumber().toString());
		info.setSocialName("");
		info.setStatus("VALID");
		info.setFirstName("MIGUEL");
		info.setSurName("CERVANTES");
		info.setSurName2("SAAVEDRA");
		info.setValid(true);
		return info;
	}
}
