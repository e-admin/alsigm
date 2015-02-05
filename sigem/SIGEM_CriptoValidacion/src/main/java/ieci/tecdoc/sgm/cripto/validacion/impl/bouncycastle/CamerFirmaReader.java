package ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle;

import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;

import java.security.cert.X509Certificate;

import org.apache.commons.lang.StringUtils;

public class CamerFirmaReader implements IReaderCert {

	private static final String SERIALNUMBER_CAMERFIRMA = "A82743287";

	
	public boolean isTypeOf(X509Certificate cert) {
		return StringUtils.equals(SERIALNUMBER_CAMERFIRMA,getValue("SERIALNUMBER", cert.getIssuerDN().toString()));
	
	}

	public InfoCertificado getInfo(X509Certificate cert) {
	    String nombre;
	    String nif;
	    String numeroSerie;
	    String asunto;
	    String issuer;

	    asunto=cert.getSubjectDN().toString();
	    issuer=cert.getIssuerDN().toString();


	    nif = getValue("SERIALNUMBER", asunto);
	    nombre = getValue("CN", asunto);
	    numeroSerie=cert.getSerialNumber().toString();
	    
        InfoCertificado infoCertificado = new InfoCertificado();
	    infoCertificado.setIssuer(issuer);
	    infoCertificado.setName(nombre);
	    infoCertificado.setFirstname(nombre);
	    infoCertificado.setCif(nif);
	    infoCertificado.setNif(nif);
	    infoCertificado.setSubject(asunto);
	    infoCertificado.setSerialNumber(numeroSerie);	    
	    
		return infoCertificado;
	}
	
	private String getValue(String label, String contenido){
		String value = null;
		int idx, idx2;
	    idx=StringUtils.indexOf(contenido, label+"=");
	    idx2= StringUtils.indexOf(contenido, ",", idx);
	    value = StringUtils.substring(contenido, idx +  (label+"=").length(), idx2);
	    return value;
	}
	

}
