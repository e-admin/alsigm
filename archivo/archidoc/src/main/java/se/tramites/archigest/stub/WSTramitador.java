package se.tramites.archigest.stub;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface WSTramitador extends Remote {
	public String[] recuperarIdsExpedientes(String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws RemoteException;

	public InfoBExpedienteVO[] recuperarInfoBExpedientes(String[] idExps)
			throws RemoteException;

	public ExpedienteVO recuperarExpediente(String idExp)
			throws RemoteException;
}
