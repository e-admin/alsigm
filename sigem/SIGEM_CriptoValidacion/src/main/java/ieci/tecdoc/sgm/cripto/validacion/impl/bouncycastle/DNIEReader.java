package ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle;


import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;

import java.security.cert.X509Certificate;

public class DNIEReader implements IReaderCert{

//ASUNTO
//  CN = apellido1 apellido2, nombre (AUTENTICACIÓN)
//  G = NOMBRE
//  SN = APELLIDO1
//  Número de serie = 123456789R
//  C = ES

	public InfoCertificado getInfo(X509Certificate cert) {
	    String asunto = cert.getSubjectDN().toString();
		String issuer = cert.getIssuerDN().toString();
		int pos = -1;
		int pos2 = -1;

		pos = asunto.indexOf(",");
		// En el nombre (AUTENTICACIÓN) o (FIRMA)
		pos2 = asunto.indexOf("(");
	    String nombre=asunto.substring(pos+2,pos2-1);

		pos = asunto.indexOf(", ");
	    String apellidos=asunto.substring(4,pos);

		pos = asunto.indexOf("SERIALNUMBER=");
		pos2 = asunto.indexOf(", C=");

		String dni=asunto.substring(pos+13,pos2);
		String nif=dni;
		String cif=dni;

		InfoCertificado infoCertificado = new InfoCertificado();
	    infoCertificado.setIssuer(issuer);
	    infoCertificado.setName(nombre + " " + apellidos);
	    infoCertificado.setFirstname(nombre);
	    //infoCertificado.setLastname1(apellido1);
	    infoCertificado.setCif(cif);
	    infoCertificado.setNif(nif);
	    infoCertificado.setSubject(asunto);
	    infoCertificado.setSerialNumber(cert.getSerialNumber().toString());
	    return infoCertificado;

	}

	public boolean isTypeOf(X509Certificate cert) {

		int pos = -1;
		int pos2 = -1;

		String issuer = cert.getIssuerDN().toString();
		if (issuer != null) {

			try {
				pos = issuer.indexOf("CN=");
				pos2 = issuer.indexOf("OU=");
				if (pos == -1 || pos2 == -1 )
					return false;
				String CN = issuer.substring(pos + 3, pos2 - 2);

				pos = issuer.indexOf("OU=");
				pos2 = issuer.indexOf("O=");
				if (pos == -1 || pos2 == -1)
					return false;
				String OU = issuer.substring(pos + 3, pos2 - 2);

				pos = issuer.indexOf("O=");
				pos2 = issuer.indexOf("C=");
				if (pos == -1 || pos2 == -1)
					return false;
				String O = issuer.substring(pos + 2, pos2 - 2);

				pos = issuer.indexOf("C=");
				if (pos == -1)
					return false;
				String C = issuer.substring(pos + 2, issuer.length());

				// EMISOR:
				// CN = AC DNIE
				// OU = DNIE
				//  O = DIRECCION GENERAL DE LA POLICIA
				//	C = ES
				boolean okCN = CN!=null && CN.startsWith("AC DNIE");
				boolean okOU = OU != null && OU.equals("DNIE");
				boolean okO = O != null
						&& O.equals("DIRECCION GENERAL DE LA POLICIA");
				boolean okC = C != null && C.equals("ES");

				return okCN && okOU && okO && okC;
			}
			catch (Exception e) {
			}
		}

		return false;
	}

}
