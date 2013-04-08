package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: HitosExpediente.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class HitosExpediente extends RetornoServicio {

	private HitoExpediente[] hitosExpedientes;

	public HitoExpediente[] getHitosExpedientes() {
		return hitosExpedientes;
	}

	public void setExpedientes(HitoExpediente[] hitosExpedientes) {
		this.hitosExpedientes = hitosExpedientes;
	}
	
	
}
