package ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle;

import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;

import java.security.cert.X509Certificate;

public class FnmtCertReader implements IReaderCert {
	
    final static String CADENA = "FNMT";
    final static String CN_PERSONA_FISICA = "CN=NOMBRE ";
    final static String CN_PERSONA_JURIDICA = "CN=ENTIDAD ";
    final static String SEPARADOR = " - ";
    final static String PERSONA_JURIDICA_NOMBRE = SEPARADOR + "NOMBRE ";
    final static String NIF_= "NIF ";
    final static String CIF_= "CIF ";

	public InfoCertificado getInfo(X509Certificate cert) {
		
		String nombre;
	    String nif;
	    String cif;
	    String razonSocial;
	    String numeroSerie;
	    	    
	    String issuer = cert.getIssuerDN().toString();
	    String asunto = cert.getSubjectDN().toString();

    	int idx, idx2;
    	
    	// Certificado de persona fisica
    	idx = asunto.indexOf(CN_PERSONA_FISICA);
    	if (idx != -1) {
    		
    		// Nombre
    		idx2 = asunto.indexOf(SEPARADOR, idx);
    		nombre = asunto.substring(idx + CN_PERSONA_FISICA.length(), idx2);
    		
        	// NIF
    	    idx = asunto.indexOf(NIF_);
    	    idx2 = asunto.indexOf(",", idx);
    	    nif = asunto.substring(idx + NIF_.length(), idx2);
    	    
    	    cif = null;
    	    razonSocial = null;
    	}
    	// Certificado de persona juridica
    	else {
    		// Razon social
    		idx = asunto.indexOf(CN_PERSONA_JURIDICA);
    		idx2 = asunto.indexOf(SEPARADOR, idx);
    		razonSocial = asunto.substring(idx + CN_PERSONA_JURIDICA.length(), idx2);
    		
    		// CIF
    	    idx = asunto.indexOf(CIF_);
    	    idx2 = asunto.indexOf(SEPARADOR, idx);
    	    cif = asunto.substring(idx + NIF_.length(), idx2);
    	    
    		// Nombre
    		idx = asunto.indexOf(PERSONA_JURIDICA_NOMBRE);
    		idx2 = asunto.indexOf(SEPARADOR, idx + SEPARADOR.length());
    		nombre = asunto.substring(idx + PERSONA_JURIDICA_NOMBRE.length(), idx2);
    		
        	// NIF
    	    idx = asunto.indexOf(NIF_);
    	    idx2 = asunto.indexOf(",", idx);
    	    nif = asunto.substring(idx + NIF_.length(), idx2);
    	}
    	
	    numeroSerie = cert.getSerialNumber().toString();

        InfoCertificado infoCertificado = new InfoCertificado();
        
        infoCertificado.setCif(cif);
        infoCertificado.setCorporateName(razonSocial);
	    infoCertificado.setIssuer(issuer);
	    infoCertificado.setName(nombre);
	    infoCertificado.setNif(nif);
	    infoCertificado.setSerialNumber(numeroSerie);
	    infoCertificado.setSubject(asunto);
	    
	    return infoCertificado;
	}

	public boolean isTypeOf(X509Certificate cert) {
		return cert.getIssuerDN().toString().toLowerCase().contains(CADENA.toLowerCase());
	}

}
