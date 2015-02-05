package ieci.tdw.ispac.ispaclib.sign;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1InputStream;

/**
 * @author antoniomaria_sanchez at ieci.es
 * @since 08/01/2009
 */
public class DNIECertificateParser extends ASN1Parser{
	
	public static final Logger logger = Logger.getLogger(DNIECertificateParser.class);
	
	/**
	 * NIF
	 */
	public static String SERIAL_NUMBER_OID = "2.5.4.5";
	
	/**
	 * Primer apellido.
	 */
	public static String SURNAME_OID = "2.5.4.4";
	
	/**
	 * Nombre
	 */
	public static String GIVEN_NAME_OID = "2.5.4.42";
	
	/**
	 * Nombre común
	 */
	public static String COMMON_NAME_OID = "2.5.4.3";
	
	/**
	 * Fecha de nacimiento.
	 */
	public static String DATE_OF_BIRTH_OID = "1.3.6.1.5.5.7.9.1";
	
	public Map parse(X509Certificate x509Cert)
			throws CertificateException, IOException {

		Map result = new TreeMap();
		ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(x509Cert.getEncoded()));

		Map oids = super.readPropertiesOid(aIn.readObject());
	    Iterator itr= oids.keySet().iterator();
		while (itr.hasNext()) {
			String oid= (String) itr.next();
			if (oid.equals(GIVEN_NAME_OID)) {
				result.put("nombre", oids.get(GIVEN_NAME_OID));
			} else if (oid.equals(SURNAME_OID)) {
				result.put("apellido1", oids.get(SURNAME_OID));
			} else if (oid.equals(COMMON_NAME_OID)) {
				String commonName = (String) oids.get(COMMON_NAME_OID);
				String apellidos = getApellidos(commonName);
				result.put("apellidos", apellidos);
				result.put("apellido2", getApellido2(apellidos, (String) oids.get(SURNAME_OID)));
			} else if (oid.equals(SERIAL_NUMBER_OID)) {
				result.put("nif", oids.get(SERIAL_NUMBER_OID));
			}else if (oid.equals(DATE_OF_BIRTH_OID)) {
				result.put("fechaNacimiento", oids.get(DATE_OF_BIRTH_OID));
			} 
			else if (StringUtils.isAsciiPrintable(oids.get(oid).toString())) {
				result.put(oid, oids.get(oid));
			}
		}
		return result;
	}
	
	private String getApellido2(String apellidos, String apellido1) {
		return apellidos.length() > apellido1.length() ? apellidos.substring(apellido1.length()+1) : "";
	}

	private String getApellidos(String commonName){
		return commonName.split(",")[0];
	}

}
