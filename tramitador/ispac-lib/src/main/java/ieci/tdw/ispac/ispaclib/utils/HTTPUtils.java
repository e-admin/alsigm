package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


/** 
 * Clase con métodos útiles para realizar peticiones HTTP. 
 *
 */
public class HTTPUtils {

	public static byte[] get(String url) throws HttpException, IOException, ISPACException {
		return get(url, null);
	}

	public static byte[] get(String url, Credentials credentials) throws HttpException, IOException, ISPACException {
		return get(url, credentials, AuthScope.ANY);
	}
		
	public static byte[] get(String url, Credentials credentials, AuthScope authScope) throws HttpException, IOException, ISPACException {
		
		HttpClient client = new HttpClient(); 
		GetMethod get = new GetMethod(url);

		// Comprobar si se han pasado las credenciales 
		if (credentials != null) {
			
			// Establecer las credenciales
			if (authScope == null) {
				authScope = AuthScope.ANY; 
			}
			client.getState().setCredentials(authScope, credentials);
			
			// Indicar que se usen las credenciales
			get.setDoAuthentication(true);
		}
		
		return executeMethod(client, get);
	}

	public static byte[] post(String url) throws HttpException, IOException, ISPACException {
		return post(url, null);
	}

	public static byte[] post(String url, Credentials credentials) throws HttpException, IOException, ISPACException {
		return post(url, credentials, AuthScope.ANY);
	}

	public static byte[] post(String url, Credentials credentials, AuthScope authScope) throws HttpException, IOException, ISPACException {

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);

		// Comprobar si se han pasado las credenciales
		if (credentials != null) {

			// Establecer las credenciales
			if (authScope == null) {
				authScope = AuthScope.ANY;
			}
			client.getState().setCredentials(authScope, credentials);

			// Indicar que se usen las credenciales
			post.setDoAuthentication(true);
		}

		return executeMethod(client, post);
	}

	public static byte[] executeMethod(HttpClient client, HttpMethod method) throws HttpException, IOException, ISPACException {
		
		try {
			
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				throw new IOException(String.valueOf(method.getStatusLine()));
	        }

			return FileUtils.retrieveFile(method.getResponseBodyAsStream());
			
		} finally {
			method.releaseConnection();
		} 
	}
}
