package ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle;

import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;

import java.security.cert.X509Certificate;

public class CEXCertReader implements IReaderCert{

	//final static String CADENA="C=ES,O=FNMT,OU=FNMT Clase 2 CA";
    final static String CN="CN=NOMBRE ";
    final static String CEDX="CN=CEX TD-WF";
    final static String NIF_="NIF ";

	public InfoCertificado getInfo(X509Certificate cert) {
	    String nombre;
	    String apellido1;
	    String nif;
	    String numeroSerie;
	    String asunto;
	    String issuer;

	    asunto=cert.getSubjectDN().toString();
	    issuer=cert.getIssuerDN().toString();

		int idx, idx2, idx3;
	    idx=asunto.indexOf(NIF_);
	    idx2=asunto.indexOf(",", idx);
	    	nif=asunto.substring(idx+NIF_.length());
	    idx=asunto.indexOf(CN);
	    nombre=asunto.substring(idx+CN.length());
	    numeroSerie=cert.getSerialNumber().toString();
	    idx3 = nombre.indexOf(" ");
	    idx2 = nombre.indexOf(" -");
        apellido1 = nombre.substring(idx3 + 1, idx2);
        nombre = nombre.substring(0, idx3);

        InfoCertificado infoCertificado = new InfoCertificado();
	    infoCertificado.setIssuer(issuer);
	    infoCertificado.setName(nombre + " " + apellido1);
	    infoCertificado.setFirstname(nombre);
	    //infoCertificado.setLastname1(apellido1);
	    infoCertificado.setCif(nif);
	    infoCertificado.setNif(nif);
	    infoCertificado.setSubject(asunto);
	    infoCertificado.setSerialNumber(numeroSerie);

		return infoCertificado;
	}

	public boolean isTypeOf(X509Certificate cert) {
		return CEDX.equals(cert.getIssuerDN().toString());
	}

}
