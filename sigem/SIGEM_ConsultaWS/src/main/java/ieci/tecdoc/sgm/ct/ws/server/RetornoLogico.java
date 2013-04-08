package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: RetornoLogico.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
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
