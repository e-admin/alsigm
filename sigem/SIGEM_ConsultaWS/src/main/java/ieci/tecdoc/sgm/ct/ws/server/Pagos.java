package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Pagos.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Pagos extends RetornoServicio {

	private Pago[] pagos;

	public Pago[] getPagos() {
		return pagos;
	}

	public void setPagos(Pago[] pagos) {
		this.pagos = pagos;
	}
}
