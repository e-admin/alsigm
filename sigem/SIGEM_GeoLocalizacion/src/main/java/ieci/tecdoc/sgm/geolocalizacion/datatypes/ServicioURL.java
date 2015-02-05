package ieci.tecdoc.sgm.geolocalizacion.datatypes;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class ServicioURL {
	private HttpClient consulta;
	private PostMethod metodo;
	
	public ServicioURL(HttpClient consulta, PostMethod metodo) {
		this.consulta = consulta;
		this.metodo = metodo;
	}
	
	public HttpClient getConsulta() {
		return consulta;
	}
	
	public void setConsulta(HttpClient consulta) {
		this.consulta = consulta;
	}
	
	public PostMethod getMetodo() {
		return metodo;
	}
	
	public void setMetodo(PostMethod metodo) {
		this.metodo = metodo;
	}

}
