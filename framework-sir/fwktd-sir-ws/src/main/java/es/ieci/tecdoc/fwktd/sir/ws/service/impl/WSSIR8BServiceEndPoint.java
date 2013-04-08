package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import java.rmi.RemoteException;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8b.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8b.WS_SIR8_B_PortType;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class WSSIR8BServiceEndPoint extends ServletEndpointSupport
		implements WS_SIR8_B_PortType {

	private static final String SERVICE_IMPL_BEAN_NAME = "fwktd_sir_ws_wssir8bService";

	private WS_SIR8_B_PortType wssir8b;

	protected void onInit() {
		this.wssir8b = (WS_SIR8_B_PortType) getWebApplicationContext().getBean(
				SERVICE_IMPL_BEAN_NAME);
	}

	public RespuestaWS envioFicherosAAplicacion(String registro,
			String firmaRegistro) throws RemoteException {
		return wssir8b.envioFicherosAAplicacion(registro, firmaRegistro);
	}

}
