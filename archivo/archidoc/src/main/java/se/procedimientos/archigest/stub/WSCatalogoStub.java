package se.procedimientos.archigest.stub;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.log4j.Logger;

public class WSCatalogoStub implements WSCatalogo {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(WSCatalogoStub.class);

	/** Información del servicio web. */
	private Service service = null;

	/** Puerto del servicio web. */
	private QName servicePort = null;

	/**
	 * Constructor.
	 * 
	 * @param service
	 *            Servicio.
	 * @throws AxisFault
	 *             si ocurre algún error.
	 */
	public WSCatalogoStub(Service service) {
		try {
			this.service = service;
			this.servicePort = (QName) service.getPorts().next();
		} catch (Exception e) {
			logger.error(
					"Error al cargar el Stub del Servicio Web de Organismos", e);
		}
	}

	protected Call createCall(String operationName) throws RemoteException {
		try {
			Call call = (Call) service.createCall(servicePort, operationName);
			call.setOperationStyle(Style.RPC);
			call.setOperationUse(Use.ENCODED);

			return call;
		} catch (Throwable t) {
			throw new AxisFault("Error al crear la llamada al servicio web", t);
		}
	}

	public InfoBProcedimientoVO[] recuperarInfoBProcedimientos(int tipoProc,
			String nombre) throws RemoteException {
		Call call = createCall("recuperarInfoBProcedimientosByTipo");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_InfoBProcedimientoVO"),
				InfoBProcedimientoVO[].class);

		return (InfoBProcedimientoVO[]) call.invoke(new Object[] {
				new Integer(tipoProc), nombre });
	}

	public InfoBProcedimientoVO[] recuperarInfoBProcedimientos(String[] idProcs)
			throws RemoteException {
		Call call = createCall("recuperarInfoBProcedimientos");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_InfoBProcedimientoVO"),
				InfoBProcedimientoVO[].class);

		return (InfoBProcedimientoVO[]) call.invoke(new Object[] { idProcs });
	}

	public ProcedimientoVO recuperarProcedimiento(String idProc)
			throws RemoteException {
		Call call = createCall("recuperarProcedimiento");
		call.registerTypeMapping(InfoBProcedimientoVO.class, new QName(
				"urn:BeanService", "InfoBProcedimientoVO"),
				BeanSerializerFactory.class, BeanDeserializerFactory.class);
		call.registerTypeMapping(OrganoProductorVO.class, new QName(
				"urn:BeanService", "OrganoProductorVO"),
				BeanSerializerFactory.class, BeanDeserializerFactory.class);
		call.setReturnType(new QName("urn:BeanService", "ProcedimientoVO"),
				ProcedimientoVO.class);

		return (ProcedimientoVO) call.invoke(new Object[] { idProc });
	}

}