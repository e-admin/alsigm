package ieci.tecdoc.sgm.core.services.cripto.firma;

import java.security.PrivateKey;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Clase que encapsula la información asociada a un firma digital X509.
 * 
 * @author IECISA
 *
 */
public class CertificadoX509Info {
	
	   public X509Certificate getCert() {
		return cert;
	}

	public void setCert(X509Certificate cert) {
		this.cert = cert;
	}

	public Certificate[] getCerts() {
		return certs;
	}

	public void setCerts(Certificate[] certs) {
		this.certs = certs;
	}

	public PrivateKey getPrivKey() {
		return privKey;
	}

	public void setPrivKey(PrivateKey privKey) {
		this.privKey = privKey;
	}

	public void setCertStore(CertStore certStore) {
		this.certStore = certStore;
	}

	public CertificadoX509Info()
	   {
	      cert = null;
	      privKey = null;
	      certs = null;
	   }
	   
	   public X509Certificate getCertificate()
	   {
	      return cert;
	   }
	   
	   public PrivateKey getPrivateKey()
	   {
	      return privKey;
	   }
	   
	   public CertStore getCertStore()
	   {
	      return certStore;
	   }
	   
	   public Certificate[] getCertificates()
	   {
	      return certs;
	   }
	   
	   protected X509Certificate cert;
	   protected PrivateKey privKey;
	   protected CertStore certStore;
	   protected Certificate[] certs;
}
