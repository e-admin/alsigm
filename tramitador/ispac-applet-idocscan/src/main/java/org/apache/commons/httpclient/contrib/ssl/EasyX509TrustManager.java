package org.apache.commons.httpclient.contrib.ssl;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.net.ssl.TrustManager;
import com.sun.net.ssl.TrustManagerFactory;
import com.sun.net.ssl.X509TrustManager;

public class EasyX509TrustManager implements X509TrustManager {

	private X509TrustManager standardTrustManager;
	private static final Log logger = LogFactory.getLog(EasyX509TrustManager.class);
	

	public EasyX509TrustManager(KeyStore keystore)
			throws NoSuchAlgorithmException, KeyStoreException {
		
		standardTrustManager = null;
		TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance("SunX509");
		trustMgrFactory.init(keystore);
		
		TrustManager trustMgrs[] = trustMgrFactory.getTrustManagers();
		if (trustMgrs.length == 0) {
			throw new NoSuchAlgorithmException("SunX509 trust manager not supported");
		} else {
			standardTrustManager = (X509TrustManager) trustMgrs[0];
		}
	}

	public boolean isClientTrusted(X509Certificate certs[]) {
		return standardTrustManager.isClientTrusted(certs);
	}

	public boolean isServerTrusted(X509Certificate certs[]) {
		if (certs != null && logger.isDebugEnabled()) {
			logger.debug("Server certificate chain:");
			for (int i = 0; i < certs.length; i++)
				logger.debug("X509Certificate[" + i + "]=" + certs[i]);

		}
		if (certs != null && certs.length == 1) {
			X509Certificate x509certificate = certs[0];
			try {
				x509certificate.checkValidity();
			} catch (CertificateException certificateexception) {
				logger.error(certificateexception.toString());
				return false;
			}
			return true;
		} else {
			return standardTrustManager.isServerTrusted(certs);
		}
	}

	public X509Certificate[] getAcceptedIssuers() {
		return standardTrustManager.getAcceptedIssuers();
	}

}