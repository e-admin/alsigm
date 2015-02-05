package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: RetornoLogico.java,v 1.1.2.1 2008/01/25 12:25:06 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class RetornoLogico extends RetornoServicio {

	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
}
