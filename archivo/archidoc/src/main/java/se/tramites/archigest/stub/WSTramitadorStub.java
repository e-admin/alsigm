package se.tramites.archigest.stub;

import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.log4j.Logger;

public class WSTramitadorStub implements WSTramitador {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(WSTramitadorStub.class);

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
	public WSTramitadorStub(Service service) {
		try {
			this.service = service;
			this.servicePort = (QName) service.getPorts().next();
		} catch (Exception e) {
			logger.error(
					"Error al cargar el Stub del Servicio Web de Tramitaci\u00F3n",
					e);
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

	public String[] recuperarIdsExpedientes(String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws RemoteException {
		Call call = createCall("recuperarIdsExpedientes");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_xsd_string"), String[].class);

		return (String[]) call.invoke(new Object[] { idProc, fechaIni,
				fechaFin, new Integer(tipoOrd) });
	}

	public InfoBExpedienteVO[] recuperarInfoBExpedientes(String[] idExps)
			throws RemoteException {
		Call call = createCall("recuperarInfoBExpedientes");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_InfoBExpedienteVO"), InfoBExpedienteVO[].class);

		return (InfoBExpedienteVO[]) call.invoke(new Object[] { idExps });
	}

	public ExpedienteVO recuperarExpediente(String idExp)
			throws RemoteException {
		Call call = createCall("recuperarExpediente");
		call.registerTypeMapping(InfoBExpedienteVO.class, new QName(
				"urn:BeanService", "InfoBExpedienteVO"),
				BeanSerializerFactory.class, BeanDeserializerFactory.class);
		call.registerTypeMapping(DocElectronicoVO.class, new QName(
				"urn:BeanService", "DocElectronicoVO"),
				BeanSerializerFactory.class, BeanDeserializerFactory.class);
		call.registerTypeMapping(DocFisicoVO.class, new QName(
				"urn:BeanService", "DocFisicoVO"), BeanSerializerFactory.class,
				BeanDeserializerFactory.class);
		call.registerTypeMapping(EmplazamientoVO.class, new QName(
				"urn:BeanService", "EmplazamientoVO"),
				BeanSerializerFactory.class, BeanDeserializerFactory.class);
		call.registerTypeMapping(InteresadoVO.class, new QName(
				"urn:BeanService", "InteresadoVO"),
				BeanSerializerFactory.class, BeanDeserializerFactory.class);
		call.setReturnType(new QName("urn:BeanService", "ExpedienteVO"),
				ExpedienteVO.class);

		return (ExpedienteVO) call.invoke(new Object[] { idExp });
	}

}