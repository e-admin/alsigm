package se.procedimientos.archigest.stub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WSCatalogo extends Remote {
	public InfoBProcedimientoVO[] recuperarInfoBProcedimientos(int tipoProc,
			String nombre) throws RemoteException;

	public InfoBProcedimientoVO[] recuperarInfoBProcedimientos(String[] idProcs)
			throws RemoteException;

	public ProcedimientoVO recuperarProcedimiento(String idProc)
			throws RemoteException;
}
