package ieci.tdw.ispac.ispaclib.sign;

import ieci.tdw.ispac.api.ISignAPI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;


/**
 * @author antoniomaria_sanchez at ieci.es
 * @since 08/01/2009
 * 
 */
public class FMNTCertificateParser extends ASN1Parser {
	
	/**
	 * Nombre
	 */
	public static final String FIRST_NAME_OID = "1.3.6.1.4.1.5734.1.1";
	
	/**
	 * Primer apellido
	 */
	public static final String SURNAME_OID = "1.3.6.1.4.1.5734.1.2";
	
	/**
	 * Segundo apellido
	 */
	public static final String SECOND_SURNAME_OID = "1.3.6.1.4.1.5734.1.3";
	
	/**
	 * Nif
	 */
	public static final String DNI_OID = "1.3.6.1.4.1.5734.1.4";
	
	/**
	 * Persona Física/Jurídica
	 */
	public static final String TIPO_CERTIFICATE_OID = "1.3.6.1.4.1.5734.1.33";
	
	
	

	
	
	public Map parse(X509Certificate x509Cert) throws IOException{
		
		Map result = new TreeMap();
		Map oids = this.readPropertiesOid(x509Cert);

		  Iterator itr= oids.keySet().iterator();
			while (itr.hasNext()) {
				String oid= (String) itr.next();
			if (oid.equals(FIRST_NAME_OID)){
				result.put(ISignAPI.NOMBRE, oids.get(FIRST_NAME_OID));
			}else if (oid.equals(SURNAME_OID)){
				result.put(ISignAPI.PRIMER_APELLIDO, oids.get(SURNAME_OID));
			}else if (oid.equals(SECOND_SURNAME_OID)){
				result.put(ISignAPI.SEGUNDO_APELLIDO, oids.get(SECOND_SURNAME_OID));
				
			}else if (oid.equals(DNI_OID)){
				result.put(ISignAPI.NIF, oids.get(DNI_OID));
			}else if (oid.equals(TIPO_CERTIFICATE_OID)){
				result.put(ISignAPI.TIPO_CERTIFICADO, oids.get(TIPO_CERTIFICATE_OID));
			}else if (StringUtils.isAsciiPrintable( (String) oids.get(oid))){
				result.put(oid, oids.get(oid));
			}
		}
		
		String apellidos = "";
		apellidos.concat((String) oids.get(SURNAME_OID));
		
		if (StringUtils.isNotBlank((String)oids.get(SECOND_SURNAME_OID))){
			apellidos.concat(" " + oids.get(SECOND_SURNAME_OID));
		}
		result.put(ISignAPI.APELLIDOS, apellidos);
		return result;	
	}
	
	/***
	 * Parsea un certificado X509 para extraer todos sus oids
	 * 
	 * @param certificadoX509
	 * @return
	 * @throws IOException 
	 */
	public Map readPropertiesOid(X509Certificate certificadoX509) throws IOException {
		Map propiedadesOid = new HashMap();
		// obtengo los Oids
		Set oids = certificadoX509.getNonCriticalExtensionOIDs();
		
		if (oids != null) {
			// iteramos sobre los Oids // TODO ( este es el mecanismo para FNMT)
			  Iterator itr= oids.iterator();
				while (itr.hasNext()) {
					String oid= (String) itr.next();
				ASN1InputStream aIn = new ASN1InputStream(
							new ByteArrayInputStream(certificadoX509.getExtensionValue(oid)));
				ASN1OctetString extValue = (ASN1OctetString) aIn.readObject();
				aIn = new ASN1InputStream(new ByteArrayInputStream(extValue.getOctets()));
					
				super.readPropiedadesOid(oid, extValue, propiedadesOid);
			}
		}

		// retornamos el conjunto de oids recuperados.
		return propiedadesOid;
	}

	
}
