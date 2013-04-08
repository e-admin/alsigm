package ieci.tecdoc.sgm.certificacion.bean;

import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.util.Defs;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Clase que almacena el certificado de firma de una entidad
 * @author José Antonio Nogales
 */
public class CertificadoFirma {
	private static final Logger logger = Logger.getLogger(CertificadoFirma.class);
	
	/**
	 * Constructor de la clase a partir de la ruta, el password y el alias del certificado
	 * @param key Ruta del certificado
	 * @param pass Password del certificado
	 * @param alias Alias del certificado
	 * @throws CertificacionException En caso de producirse algún error
	 */
	public CertificadoFirma(String key, String pass, String alias) throws CertificacionException {
		this.key = key;
		this.pass = pass;
		this.alias = alias;
		cargarCertificado();
	}
	
	/**
	 * Constructor de la clase a partir del fichero de propiedades certificado.properties
	 * @param propiedades Fichero de propiedades con los datos del certificado
	 * @throws CertificacionException En caso de producirse algún error
	 */
	public CertificadoFirma(Properties propiedades) throws CertificacionException {
		this.key = propiedades.getProperty(Defs.KEYSTORE_KEY);
		this.pass = propiedades.getProperty(Defs.KEYSTORE_PASS);
		this.alias = propiedades.getProperty(Defs.KEYSTORE_ALIAS);
		cargarCertificado();
	}
	
	/**
	 * Métodos get y set de la clase 
	 */
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public X509Certificate getCertificado() {
		return certificado;
	}

	public void setCertificado(X509Certificate certificado) {
		this.certificado = certificado;
	}

	public Certificate[] getCertificates() {
		return certificates;
	}

	public void setCertificates(Certificate[] certificates) {
		this.certificates = certificates;
	}

	public CertStore getCertificateStore() {
		return certificateStore;
	}

	public void setCertificateStore(CertStore certificateStore) {
		this.certificateStore = certificateStore;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * Método que carga el certificado a partir de la ruta, password y alias
	 * @throws CertificacionException En caso de producirse algún error
	 */
	private void cargarCertificado() throws CertificacionException {
		try {
			Security.addProvider(new BouncyCastleProvider());
			KeyStore keystore = null;
			ArrayList certList = new ArrayList();
			java.security.cert.Certificate[] certChain = null;
			keystore = KeyStore.getInstance("PKCS12");
			keystore.load(new FileInputStream(this.key), this.pass.toCharArray());
			certChain = keystore.getCertificateChain(this.alias);
			for (int i = 0; i < certChain.length;i++)
				certList.add(certChain[i]);
			this.certificado = (X509Certificate)keystore.getCertificate(this.alias);
			this.privateKey = (PrivateKey)(keystore.getKey(this.alias, this.pass.toCharArray()));
			this.certificates = certChain;
			this.certificateStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
		}catch(Exception e){
			logger.error("Se ha producido un error al obtener el certificado de firma de la entidad", e);
			throw new CertificacionException(
				CodigosErrorCertificacionException.ERROR_OBTENER_CERTIFICADO,
				e.getMessage(),
				e.getCause());
		}
	}
	
	protected String key;
	protected String pass;
	protected String alias;
	protected X509Certificate certificado;
	protected PrivateKey privateKey;
	protected CertStore certificateStore;
	protected Certificate[] certificates;
}