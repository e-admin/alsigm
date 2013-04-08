package ieci.tecdoc.sgm.tram.ws.client;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.tram.ws.client.dto.Binario;
import ieci.tecdoc.sgm.tram.ws.client.dto.Booleano;
import ieci.tecdoc.sgm.tram.ws.client.dto.Cadena;
import ieci.tecdoc.sgm.tram.ws.client.dto.Entero;
import ieci.tecdoc.sgm.tram.ws.client.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.tram.ws.client.dto.Expediente;
import ieci.tecdoc.sgm.tram.ws.client.dto.InfoFichero;
import ieci.tecdoc.sgm.tram.ws.client.dto.InfoOcupacion;
import ieci.tecdoc.sgm.tram.ws.client.dto.ListaIdentificadores;
import ieci.tecdoc.sgm.tram.ws.client.dto.ListaInfoBExpedientes;
import ieci.tecdoc.sgm.tram.ws.client.dto.ListaInfoBProcedimientos;
import ieci.tecdoc.sgm.tram.ws.client.dto.Procedimiento;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface TramitacionWebService extends Remote {
	
    public ListaInfoBProcedimientos getProcedimientosPorTipo(String idEntidad, int tipoProc,
			String nombre) throws RemoteException;

	public ListaInfoBProcedimientos getProcedimientos(String idEntidad, String[] idProcs)
			throws RemoteException;

	public Procedimiento getProcedimiento(String idEntidad, String idProc) throws RemoteException;

	public Binario getFichero(String idEntidad, String guid) throws RemoteException;

	public InfoFichero getInfoFichero(String idEntidad, String guid) throws RemoteException;

	public InfoOcupacion getInfoOcupacion(String idEntidad) throws RemoteException;

	public RetornoServicio eliminaFicheros(String idEntidad, String[] guids)
			throws RemoteException;

	public ListaIdentificadores getIdsExpedientes(String idEntidad, String idProc,
			Date fechaIni, Date fechaFin, int tipoOrd) throws RemoteException;

	public ListaInfoBExpedientes getExpedientes(String idEntidad, String[] idExps)
			throws RemoteException;

	public Expediente getExpediente(String idEntidad, String idExp) throws RemoteException;

	public Booleano iniciarExpediente(String idEntidad, DatosComunesExpediente datosComunes,
			String datosEspecificos, DocumentoExpediente[] documentos)
			throws RemoteException;

	public Booleano anexarDocsExpediente(String idEntidad, String numExp, String numReg, 
			Date fechaReg, DocumentoExpediente[] documentos) 
		throws RemoteException;

	public RetornoServicio archivarExpedientes(String idEntidad, String[] idExps) 
		throws RemoteException ;

    public Cadena crearExpediente(
			String idEntidad,
			DatosComunesExpediente datosComunes,
			String datosEspecificos,
			DocumentoExpediente[] documentos,
			String initSystem) throws RemoteException;

	public Booleano cambiarEstadoAdministrativo(
			String idEntidad, String numExp,
			String estadoAdm) throws RemoteException;
			
	public Booleano moverExpedienteAFase(
			String idEntidad, String numExp,
			String idFaseCatalogo) throws RemoteException;

    public Cadena busquedaAvanzada(String idEntidad, String groupName,
			String searchFormName, String searchXML, int domain)
			throws RemoteException;

	public Entero establecerDatosRegistroEntidad(String idEntidad,
			String nombreEntidad, String numExp, String xmlDatosEspecificos)
			throws RemoteException;

	public Cadena obtenerRegistroEntidad(String idEntidad,
			String nombreEntidad, String numExp, int idRegistro)
			throws RemoteException;

	public Cadena obtenerRegistrosEntidad(String idEntidad,
			String nombreEntidad, String numExp) throws RemoteException;
}
