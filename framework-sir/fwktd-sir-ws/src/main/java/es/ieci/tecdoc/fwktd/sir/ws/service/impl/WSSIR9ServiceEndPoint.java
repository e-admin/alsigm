package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import java.rmi.RemoteException;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir9.WS_SIR9_PortType;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class WSSIR9ServiceEndPoint extends ServletEndpointSupport implements
		WS_SIR9_PortType {

	private static final String SERVICE_IMPL_BEAN_NAME = "fwktd_sir_ws_wssir9Service";

	private WS_SIR9_PortType wssir9;

	protected void onInit() {
		this.wssir9 = (WS_SIR9_PortType) getWebApplicationContext().getBean(
				SERVICE_IMPL_BEAN_NAME);
	}

	public RespuestaWS envioMensajeDatosControlAAplicacion(String mensaje,
			String firma) throws RemoteException {
		return wssir9.envioMensajeDatosControlAAplicacion(mensaje, firma);
	}

}
