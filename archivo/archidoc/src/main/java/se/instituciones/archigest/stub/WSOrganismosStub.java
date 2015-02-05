package se.instituciones.archigest.stub;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.log4j.Logger;

public class WSOrganismosStub implements WSOrganismos {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(WSOrganismosStub.class);

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
	public WSOrganismosStub(Service service) {
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

	public OrganoVO[] recuperarHijosDeOrgano(String idOrgPadre)
			throws RemoteException {
		Call call = createCall("recuperarHijosDeOrgano");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_OrganoVO"), OrganoVO[].class);

		return (OrganoVO[]) call.invoke(new Object[] { idOrgPadre });
	}

	public OrganoVO[] recuperarOrganosDependientes(String idOrg, int numNiveles)
			throws RemoteException {
		Call call = createCall("recuperarOrganosDependientes");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_OrganoVO"), OrganoVO[].class);

		return (OrganoVO[]) call.invoke(new Object[] { idOrg,
				new Integer(numNiveles) });
	}

	public OrganoVO[] recuperarOrganosAntecesores(String idOrg, int numNiveles)
			throws RemoteException {
		Call call = createCall("recuperarOrganosAntecesores");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_OrganoVO"), OrganoVO[].class);

		return (OrganoVO[]) call.invoke(new Object[] { idOrg,
				new Integer(numNiveles) });
	}

	public OrganoVO[] recuperarOrganos(String parameter) throws RemoteException {
		Call call = createCall("recuperarOrganos");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_OrganoVO"), OrganoVO[].class);

		return (OrganoVO[]) call.invoke(new Object[] { parameter });
	}

	public OrganoVO recuperarOrgano(short tipoAtrib, String valorAtrib)
			throws RemoteException {
		Call call = createCall("recuperarOrgano");
		call.setReturnType(new QName("urn:BeanService", "OrganoVO"),
				OrganoVO.class);

		return (OrganoVO) call.invoke(new Object[] { new Short(tipoAtrib),
				valorAtrib });
	}

	public OrganoVO recuperarOrganodeUsuario(String idUsr)
			throws RemoteException {
		Call call = createCall("recuperarOrganodeUsuario");
		call.setReturnType(new QName("urn:BeanService", "OrganoVO"),
				OrganoVO.class);

		return (OrganoVO) call.invoke(new Object[] { idUsr });
	}

	public String[] recuperarUsuariosDeOrganos(String[] idOrgs)
			throws RemoteException {
		Call call = createCall("recuperarUsuariosDeOrganos");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_xsd_string"), String[].class);

		return (String[]) call.invoke(new Object[] { idOrgs });
	}

	public OrganoVO[] recuperarInstitucionesProductoras()
			throws RemoteException {
		Call call = createCall("recuperarInstitucionesProductoras");
		call.setReturnType(new QName(
				service.getServiceName().getNamespaceURI(),
				"ArrayOf_tns1_OrganoVO"), OrganoVO[].class);

		return (OrganoVO[]) call.invoke(new Object[] {});
	}

}