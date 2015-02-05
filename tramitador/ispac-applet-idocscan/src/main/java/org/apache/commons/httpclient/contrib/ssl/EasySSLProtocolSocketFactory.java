package org.apache.commons.httpclient.contrib.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.net.ssl.SSLContext;
import com.sun.net.ssl.TrustManager;

public class EasySSLProtocolSocketFactory implements SecureProtocolSocketFactory {

	private static final Log logger = LogFactory.getLog(EasySSLProtocolSocketFactory.class);
	
	private SSLContext sslcontext;
	

	public EasySSLProtocolSocketFactory() {
		sslcontext = null;
	}

	private static SSLContext createEasySSLContext() {
		try {
		    SSLContext context = SSLContext.getInstance("SSL");
		    context.init(null, new TrustManager[] { new EasyX509TrustManager(null) }, null);
		    return context;
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		    throw new HttpClientError(e.toString());
		}
	}

	private SSLContext getSSLContext() {
		if (sslcontext == null) {
			sslcontext = createEasySSLContext();
		}
		return sslcontext;
	}

	public Socket createSocket(String s, int i, InetAddress inetaddress, int j)
			throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(s, i,
				inetaddress, j);
	}

	public Socket createSocket(String s, int i, InetAddress inetaddress, int j,
			HttpConnectionParams httpconnectionparams) throws IOException,
			UnknownHostException, ConnectTimeoutException {
		if (httpconnectionparams == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		}
		int k = httpconnectionparams.getConnectionTimeout();
		if (k == 0) {
			return createSocket(s, i, inetaddress, j);
		} else {
			return ControllerThreadSocketFactory.createSocket(this, s, i,
					inetaddress, j, k);
		}
	}

	public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(s, i);
	}

	public Socket createSocket(Socket socket, String s, int i, boolean flag)
			throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(socket, s, i,
				flag);
	}

}