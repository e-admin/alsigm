package ieci.tdw.ispac.ispaclib.sign;

import ieci.tdw.ispac.ispaclib.sign.exception.InvalidSignatureValidationException;

import java.io.ByteArrayInputStream;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;



/**
 * @author antoniomaria_sanchez at ieci.es
 * @since 09/01/2009
 */
public class X509CertSimpleReader {
	
	public static final Logger logger = Logger.getLogger(X509CertSimpleReader.class);
	
	public static final String FNMT ="FMNT";
	public static final String DNIE = "DNIE";
	
	
	
	
	public Map read(X509Certificate x509Cert) {
		Map resp = new TreeMap();

		// emisor
		String issuer = x509Cert.getIssuerX500Principal().toString();
		try {
			// OU=FNMT Clase 2 CA, O=FNMT, C=ES
			if (issuer.indexOf(FNMT) > -1){
				resp = new FMNTCertificateParser().parse(x509Cert);
			// CN=AC DNIE 002, OU=DNIE, O=DIRECCION GENERAL DE LA POLICIA, C=ES
			}else if (issuer.indexOf(DNIE) > -1){
				resp = new DNIECertificateParser().parse(x509Cert);
			}else{
				logger.warn("Emisor no soportado: " + issuer);
				ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(x509Cert.getEncoded()));
				resp = new ASN1Parser().readPropertiesOid(aIn.readObject());
			}
		}
		catch (Exception e) {
			logger.warn("Error intentando validar el certificado[" + e + "]");
			throw new InvalidSignatureValidationException("Error intentando validar el certificado"+e.toString());
		} 		
		return resp;
	}

}
