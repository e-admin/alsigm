package ieci.tecdoc.sgm.mensajes_cortos.helpers;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Clase para el envío de mensajes HTTP. 
 *
 */
public class HttpClientHelper {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(HttpClientHelper.class);
	
	/**
	 * Nombre o dirección IP del proxy HTTP. 
	 */
	private String proxyHost = null;
	
	/**
	 * Puerto del proxy HTTP.
	 */
	private int proxyPort = 0;
	
	/**
	 * Usuario del proxy HTTP.
	 */
	private String proxyUser = null;
	
	/**
	 * Clave del usuario del proxy HTTP.
	 */
	private String proxyPassword = null;

	
	/**
	 * Constructor.
	 */
	public HttpClientHelper() {
		super();
	}
	
	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	
	protected HttpClient initHttpClient(String url , NameValuePair[] parametersBody){
		
	    if (logger.isInfoEnabled()) {
	    	logger.info("Enviando XML a la URL: " + url);
	    	if (logger.isDebugEnabled() && !ArrayUtils.isEmpty(parametersBody)) {
	    		for (int i = 0; i < parametersBody.length; i++) {
	    			logger.debug("Parámetro: name=[" + parametersBody[i].getName() 
	    					+ "], value=[" + parametersBody[i].getValue() + "]");
	    		}
	    	}
	    }

	    // Crear instancia de HttpClient.
	    HttpClient client = new HttpClient();

	    // Configuración del proxy HTTP
	    if (StringUtils.isNotBlank(proxyHost)) {
		    client.getHostConfiguration().setProxy(proxyHost, proxyPort);
		    
		    if (logger.isInfoEnabled()) {
		    	logger.info("Proxy establecido: " + proxyHost + ":" + proxyPort);
		    }
		    
		    if (StringUtils.isNotBlank(proxyUser)) {
			    client.getState().setProxyCredentials(
			    		new AuthScope(proxyHost, proxyPort, AuthScope.ANY_REALM), 
			    		new UsernamePasswordCredentials(proxyUser, proxyPassword));

			    if (logger.isInfoEnabled()) {
			    	logger.info("Credenciales del proxy establecidas: " + proxyUser);
			    }
		    }
	    }
	    return client;
	    
	}
	public String postAsString(String url, NameValuePair[] parametersBody) throws HttpException, IOException {
		
	   HttpClient client= initHttpClient(url, parametersBody);
	    // Crear el método POST
	    PostMethod post = new PostMethod(url);
	    post.setRequestBody(parametersBody);

	    try {
	    	
	      // Ejecutar el método.
	      int statusCode = client.executeMethod(post);
	      
	      if (logger.isInfoEnabled()) {
	    	  logger.info("statusLine: " + post.getStatusLine());
	      }

	      // Obtener la respuesta
	      if (statusCode == HttpStatus.SC_OK) {
		    return post.getResponseBodyAsString();
	    	
	    	
	      } else {
	    	  logger.warn("Error en la llamada (" + url + "): " + post.getStatusLine());
	    	  throw new HttpException(post.getStatusLine().toString());
	      }

	    } finally {
	    	
	    	// Release the connection.
	    	post.releaseConnection();
	    } 
		
	}

	public byte[] post(String url, NameValuePair[] parametersBody) throws HttpException, IOException {
		  HttpClient client= initHttpClient(url, parametersBody);
	    // Crear el método POST
	    PostMethod post = new PostMethod(url);
	    post.setRequestBody(parametersBody);

	    try {
	    	
	      // Ejecutar el método.
	      int statusCode = client.executeMethod(post);
	      
	      if (logger.isInfoEnabled()) {
	    	  logger.info("statusLine: " + post.getStatusLine());
	      }

	      // Obtener la respuesta
	      if (statusCode == HttpStatus.SC_OK) {
		    return post.getResponseBody();
	    	
	    	
	      } else {
	    	  logger.warn("Error en la llamada (" + url + "): " + post.getStatusLine());
	    	  throw new HttpException(post.getStatusLine().toString());
	      }

	    } finally {
	    	
	    	// Release the connection.
	    	post.releaseConnection();
	    } 
	}

}
