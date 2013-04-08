package ieci.tecdoc.sgm.cripto.firma.impl.afirma.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

import sun.misc.BASE64Decoder;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class CertificateUtils {

	public static String getCertificateSubjectName(String certificateBase64) throws CertificateException, IOException {

		String name = null;

		if (certificateBase64 != null) {
			
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] certBin = decoder.decodeBuffer(certificateBase64);

			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) certificateFactory
					.generateCertificate(new ByteArrayInputStream(certBin));

			name = getCertificateSubjectName(certificate);
		}

		return name;
	}

	public static String getCertificateSubjectName(X509Certificate certificate) {

		String name = null;

		if (certificate != null) {
			name = getPrincipalName(certificate.getSubjectX500Principal());
		}

		return name;
	}

	public static String getPrincipalName(X500Principal principal) {

		String name = null;

		if (principal != null) {
			name = getCN(principal.getName());
			if ((name != null) && (name.trim().length() > 0)) {
				name = principal.toString();
			}
		}

		return name;
	}

	public static String getCN(String dn) {

		String cn = null;
		
		if (dn != null) {
			
			// Parsear el valor de CN teniendo en cuenta que puede tener comas (\,)
			int ix = dn.indexOf("CN=");
			if (ix >= 0) {
				dn = dn.substring(ix + 3);
			}

			ix = dn.indexOf(",");
			if (ix > 0) {
				boolean found = false;
				while (!found) {
					if (dn.charAt(ix - 1) == '\\') {
						ix = dn.indexOf(",", ix + 1);
					} else {
						found = true;
						dn = dn.substring(0, ix);
					}
				}
			}

			cn = dn;
		}

		return cn;
	}
}
