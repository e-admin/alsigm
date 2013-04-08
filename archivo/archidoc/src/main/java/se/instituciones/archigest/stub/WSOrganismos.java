package se.instituciones.archigest.stub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WSOrganismos extends Remote {
	public OrganoVO[] recuperarHijosDeOrgano(String idOrgPadre)
			throws RemoteException;

	public OrganoVO[] recuperarOrganosDependientes(String idOrg, int numNiveles)
			throws RemoteException;

	public OrganoVO[] recuperarOrganosAntecesores(String idOrg, int numNiveles)
			throws RemoteException;

	public OrganoVO[] recuperarOrganos(String param) throws RemoteException;

	public OrganoVO recuperarOrgano(short tipoAtrib, String valorAtrib)
			throws RemoteException;

	public OrganoVO recuperarOrganodeUsuario(String idUsr)
			throws RemoteException;

	public String[] recuperarUsuariosDeOrganos(String[] idOrgs)
			throws RemoteException;

	public OrganoVO[] recuperarInstitucionesProductoras()
			throws RemoteException;
}
