package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Subsanaciones.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Subsanaciones extends RetornoServicio {

	private Subsanacion[] subsanaciones;

	public Subsanacion[] getSubsanaciones() {
		return subsanaciones;
	}

	public void setSubsanaciones(Subsanacion[] subsanaciones) {
		this.subsanaciones = subsanaciones;
	}
}
