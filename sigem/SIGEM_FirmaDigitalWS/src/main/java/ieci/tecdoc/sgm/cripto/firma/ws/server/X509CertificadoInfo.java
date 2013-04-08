package ieci.tecdoc.sgm.cripto.firma.ws.server;

import java.security.PrivateKey;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class X509CertificadoInfo extends RetornoServicio {
	private  X509Certificate cert;
	private PrivateKey privKey;
	private CertStore certStore;
	private Certificate[] certs;
	
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

	public CertStore getCertStore() {
		return certStore;
	}

	public void setCertStore(CertStore certStore) {
		this.certStore = certStore;
	}

	public PrivateKey getPrivKey() {
		return privKey;
	}

	public void setPrivKey(PrivateKey privKey) {
		this.privKey = privKey;
	}


}
