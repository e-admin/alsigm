package ieci.tdw.ispac.services.ws.client;

import ieci.tdw.ispac.services.dto.RetornoServicio;
import ieci.tdw.ispac.services.ws.client.dto.Binario;
import ieci.tdw.ispac.services.ws.client.dto.Booleano;
import ieci.tdw.ispac.services.ws.client.dto.Cadena;
import ieci.tdw.ispac.services.ws.client.dto.DatosComunesExpediente;
import ieci.tdw.ispac.services.ws.client.dto.DocumentoExpediente;
import ieci.tdw.ispac.services.ws.client.dto.Entero;
import ieci.tdw.ispac.services.ws.client.dto.Expediente;
import ieci.tdw.ispac.services.ws.client.dto.InfoFichero;
import ieci.tdw.ispac.services.ws.client.dto.InfoOcupacion;
import ieci.tdw.ispac.services.ws.client.dto.ListaIdentificadores;
import ieci.tdw.ispac.services.ws.client.dto.ListaInfoBExpedientes;
import ieci.tdw.ispac.services.ws.client.dto.ListaInfoBProcedimientos;
import ieci.tdw.ispac.services.ws.client.dto.Procedimiento;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface TramitacionWebService extends Remote {
	
    public ListaInfoBProcedimientos getProcedimientosPorTipo(int tipoProc,
			String nombre) throws RemoteException;

	public ListaInfoBProcedimientos getProcedimientos(String[] idProcs)
			throws RemoteException;

	public Procedimiento getProcedimiento(String idProc) throws RemoteException;

	public Binario getFichero(String guid) throws RemoteException;

	public InfoFichero getInfoFichero(String guid) throws RemoteException;

	public InfoOcupacion getInfoOcupacion() throws RemoteException;

	public RetornoServicio eliminaFicheros(String[] guids)
			throws RemoteException;

	public ListaIdentificadores getIdsExpedientes(String idProc,
			Date fechaIni, Date fechaFin, int tipoOrd) throws RemoteException;

	public ListaInfoBExpedientes getExpedientes(String[] idExps)
			throws RemoteException;

	public Expediente getExpediente(String idExp) throws RemoteException;

	public Booleano iniciarExpediente(DatosComunesExpediente datosComunes,
			String datosEspecificos, DocumentoExpediente[] documentos)
			throws RemoteException;

	public Booleano anexarDocsExpediente(String numExp, String numReg, 
			Date fechaReg, DocumentoExpediente[] documentos) 
		throws RemoteException;
	
	public RetornoServicio archivarExpedientes(String[] idExps) throws RemoteException ;

	public Cadena crearExpediente(DatosComunesExpediente datosComunes,
			String datosEspecificos, DocumentoExpediente[] documentos,
			String initSystem) throws java.rmi.RemoteException;	

	public Booleano cambiarEstadoAdministrativo(String numExp, String estadoAdm)
			throws RemoteException;
	
    public Booleano moverExpedienteAFase(String numExp, String idFaseCatalogo)
    	throws RemoteException;

    public Cadena busquedaAvanzada(String nombreGrupo,
			String nombreFrmBusqueda, String xmlBusqueda, int dominio)
			throws RemoteException;    
    
    public Entero establecerDatosRegistroEntidad(String nombreEntidad,
			String numExp, String xmlDatosEspecificos) throws RemoteException;

	public Cadena obtenerRegistroEntidad(String nombreEntidad, String numExp,
			int idRegistro) throws RemoteException;

	public Cadena obtenerRegistrosEntidad(String nombreEntidad, String numExp)
			throws RemoteException;

}
