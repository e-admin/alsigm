package ieci.tecdoc.sgm.pe.ws.server;
/*
 * $Id: ListaTasas.java,v 1.1.4.1 2008/01/25 12:30:46 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaTasas extends RetornoServicio {

	Tasa[] tasas;

	public Tasa[] getTasas() {
		return tasas;
	}

	public void setTasas(Tasa[] tasas) {
		this.tasas = tasas;
	}
	
}
