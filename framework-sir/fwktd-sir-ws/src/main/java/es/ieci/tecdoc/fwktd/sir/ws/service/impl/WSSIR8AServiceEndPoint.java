package es.ieci.tecdoc.fwktd.sir.ws.service.impl;

import java.rmi.RemoteException;

import org.apache.axis.attachments.OctetStream;
import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.RespuestaWS;
import es.ieci.tecdoc.fwktd.sir.ws.service.wssir8a.WS_SIR8_A_PortType;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class WSSIR8AServiceEndPoint extends ServletEndpointSupport
		implements WS_SIR8_A_PortType {

	private static final String SERVICE_IMPL_BEAN_NAME = "fwktd_sir_ws_wssir8aService";

	private WS_SIR8_A_PortType wssir8a;

	protected void onInit() {
		this.wssir8a = (WS_SIR8_A_PortType) getWebApplicationContext().getBean(
				SERVICE_IMPL_BEAN_NAME);
	}

	public RespuestaWS envioFicherosAAplicacion(String registro,
			String firmaRegistro, OctetStream[] documento)
			throws RemoteException {
		return wssir8a.envioFicherosAAplicacion(registro, firmaRegistro,
				documento);
	}

}
