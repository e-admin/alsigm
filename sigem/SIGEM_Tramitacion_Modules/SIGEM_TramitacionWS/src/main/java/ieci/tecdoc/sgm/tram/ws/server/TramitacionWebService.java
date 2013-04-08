package ieci.tecdoc.sgm.tram.ws.server;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.TramitacionException;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;
import ieci.tecdoc.sgm.tram.ws.server.dto.Binario;
import ieci.tecdoc.sgm.tram.ws.server.dto.Booleano;
import ieci.tecdoc.sgm.tram.ws.server.dto.Cadena;
import ieci.tecdoc.sgm.tram.ws.server.dto.Entero;
import ieci.tecdoc.sgm.tram.ws.server.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.tram.ws.server.dto.DocElectronico;
import ieci.tecdoc.sgm.tram.ws.server.dto.DocFisico;
import ieci.tecdoc.sgm.tram.ws.server.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.tram.ws.server.dto.Emplazamiento;
import ieci.tecdoc.sgm.tram.ws.server.dto.Expediente;
import ieci.tecdoc.sgm.tram.ws.server.dto.Firma;
import ieci.tecdoc.sgm.tram.ws.server.dto.InfoBExpediente;
import ieci.tecdoc.sgm.tram.ws.server.dto.InfoBProcedimiento;
import ieci.tecdoc.sgm.tram.ws.server.dto.InfoFichero;
import ieci.tecdoc.sgm.tram.ws.server.dto.InfoOcupacion;
import ieci.tecdoc.sgm.tram.ws.server.dto.Interesado;
import ieci.tecdoc.sgm.tram.ws.server.dto.InteresadoExpediente;
import ieci.tecdoc.sgm.tram.ws.server.dto.ListaIdentificadores;
import ieci.tecdoc.sgm.tram.ws.server.dto.ListaInfoBExpedientes;
import ieci.tecdoc.sgm.tram.ws.server.dto.ListaInfoBProcedimientos;
import ieci.tecdoc.sgm.tram.ws.server.dto.OrganoProductor;
import ieci.tecdoc.sgm.tram.ws.server.dto.Procedimiento;

import java.util.Date;

import javax.xml.soap.SOAPException;

import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;

/**
 * Implementación del servicio web de Tramitación.
 *
 */
public class TramitacionWebService {

	/** Logger de la clase. */
	private static final Logger logger = 
		Logger.getLogger(TramitacionWebService.class);
	
	/** Nombre del servicio. */
	private static final String SERVICE_NAME = 
		ConstantesServicios.SERVICE_PROCESSING;
	
	
	private ServicioTramitacion getServicioTramitacion() 
			throws SOAPException, SigemException {
		
		String cImpl = UtilAxis.getImplementacion(
				MessageContext.getCurrentContext());
		
		if ((cImpl == null) || ("".equals(cImpl))) {
			return LocalizadorServicios.getServicioTramitacion();
		} else {
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".")
					.append(cImpl);
			return LocalizadorServicios.getServicioTramitacion(
					sbImpl.toString());
		}
	}

	/**
	 * Recupera la lista de procedimientos del tipo que se indica en 
	 * tipoProc, con su información básica.
	 * @param tipoProc Tipo de procedimiento.
	 * <p>Valores posibles de tipoProc (@see InfoBProcedimiento):
	 * <li>1 - Todos</li>
	 * <li>2 - Procedimientos  automatizados</li>
	 * <li>3 - Procedimientos no automatizados</li>
	 * </p>
	 * @param nombre Nombre del procedimiento.
	 * @return Lista de información de procedimientos.
	 */
	public ListaInfoBProcedimientos getProcedimientosPorTipo(String idEntidad, int tipoProc, 
			String nombre) {
		
		ListaInfoBProcedimientos lista = new ListaInfoBProcedimientos();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento[] procs = 
				service.getProcedimientos(idEntidad, tipoProc, nombre);
			
			lista.setProcedimientos(getInfoBProcedimientos(procs));
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener los procedimientos por tipo.", e);
			return (ListaInfoBProcedimientos) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los procedimientos por tipo.", e);
			return (ListaInfoBProcedimientos) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los procedimientos por tipo.", e);
			return (ListaInfoBProcedimientos) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaInfoBProcedimientos) ServiciosUtils.completeReturnOK(lista);	
	}

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 */
	public ListaInfoBProcedimientos getProcedimientos(String idEntidad, String[] idProcs) {
		
		ListaInfoBProcedimientos lista = new ListaInfoBProcedimientos();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento[] procs = 
				service.getProcedimientos(idEntidad, idProcs);
			
			lista.setProcedimientos(getInfoBProcedimientos(procs));
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener los procedimientos.", e);
			return (ListaInfoBProcedimientos) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los procedimientos.", e);
			return (ListaInfoBProcedimientos) 
				ServiciosUtils.completeReturnError(lista, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los procedimientos.", e);
			return (ListaInfoBProcedimientos) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaInfoBProcedimientos) ServiciosUtils.completeReturnOK(lista);	
	}

	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 */
	public Procedimiento getProcedimiento(String idEntidad, String idProc) {
		Procedimiento procedimiento = new Procedimiento();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento proc = 
				service.getProcedimiento(idEntidad, idProc);
			
			copyProcedimiento(procedimiento, proc);
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener el procedimiento.", e);
			return (Procedimiento) 
				ServiciosUtils.completeReturnError(procedimiento, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el procedimiento.", e);
			return (Procedimiento) 
				ServiciosUtils.completeReturnError(procedimiento, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener el procedimiento.", e);
			return (Procedimiento) 
				ServiciosUtils.completeReturnError(procedimiento);
		}
		
		return (Procedimiento) ServiciosUtils.completeReturnOK(procedimiento);	
	}

    /**
     * Obtiene el contenido del documento.
     * @param guid GUID del documento
     * @return Contenido del documento.
     */
    public Binario getFichero(String idEntidad, String guid) {
		Binario binario = new Binario();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			binario.setContenido(service.getFichero(idEntidad, guid));
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener el fichero.", e);
			return (Binario) 
				ServiciosUtils.completeReturnError(binario, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el fichero.", e);
			return (Binario) 
				ServiciosUtils.completeReturnError(binario, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener el fichero.", e);
			return (Binario) ServiciosUtils.completeReturnError(binario);
		}
		
		return (Binario) ServiciosUtils.completeReturnOK(binario);	
    }

	/**
	 * Obtiene la información de origen e integridad del documento.
	 * @param guid GUID del documento.
	 * @return Información del documento.
	 */
	public InfoFichero getInfoFichero(String idEntidad, String guid) {
		
		InfoFichero infoFichero = new InfoFichero();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero info = 
				service.getInfoFichero(idEntidad, guid);
			
			copyInfoFichero(infoFichero, info);
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener la información del fichero.", e);
			return (InfoFichero) 
				ServiciosUtils.completeReturnError(infoFichero, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la información del fichero.", e);
			return (InfoFichero) 
				ServiciosUtils.completeReturnError(infoFichero, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener la información del fichero.", e);
			return (InfoFichero) ServiciosUtils.completeReturnError(infoFichero);
		}
		
		return (InfoFichero) ServiciosUtils.completeReturnOK(infoFichero);	
	}

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * @return Información de ocupación.
	 */
	public InfoOcupacion getInfoOcupacion(String idEntidad) {
		
		InfoOcupacion infoOcupacion = new InfoOcupacion();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion info = 
				service.getInfoOcupacion(idEntidad);
			
			copyInfoOcupacion(infoOcupacion, info);
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener la información de ocupación.", e);
			return (InfoOcupacion) 
				ServiciosUtils.completeReturnError(infoOcupacion, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener la información de ocupación.", e);
			return (InfoOcupacion) 
				ServiciosUtils.completeReturnError(infoOcupacion, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener la información de ocupación.", e);
			return (InfoOcupacion) ServiciosUtils.completeReturnError(infoOcupacion);
		}
		
		return (InfoOcupacion) ServiciosUtils.completeReturnOK(infoOcupacion);	
	}

	/**
	 * Elimina los documentos determinados por los GUIDs.
	 * @param guids Lista de GUIDs de los documentos.
	 */
	public RetornoServicio eliminaFicheros(String idEntidad, String[] guids) {
		
		RetornoServicio ret = new RetornoServicio();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			service.eliminaFicheros(idEntidad, guids);
			
		} catch (TramitacionException e) {
			logger.error("Error al eliminar ficheros.", e);
			return (RetornoServicio) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al eliminar ficheros.", e);
			return (RetornoServicio) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al eliminar ficheros.", e);
			return (RetornoServicio) ServiciosUtils.completeReturnError(ret);
		}
		
		return (RetornoServicio) ServiciosUtils.completeReturnOK(ret);	
	}

	/**
	 * Recupera los identificadores de los expedientes,  del procedimiento identificado 
	 * por idProc, que hayan finalizado en el rango de fechas comprendido entre fechaIni
	 * y fechaFin ordenados por lo que indique el parámetro tipoOrd.
	 * @param idProc Identificador del procedimiento.
	 * @param fechaIni Fecha de inicio.
	 * @param fechaFin Fecha de fin.
	 * @param tipoOrd Tipo de ordenación.
	 * <p>Valores posibles:
	 * <li>1 - Número de expediente</li>
	 * <li>2 - Fecha finalización</li>
	 * </p>
	 * @return Lista de identificadores de expedientes.
	 */
	public ListaIdentificadores getIdsExpedientes(String idEntidad, String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) {

		ListaIdentificadores lista = new ListaIdentificadores();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			String [] ids = service.getIdsExpedientes(idEntidad, idProc, fechaIni, 
					fechaFin, tipoOrd);
			
			lista.setIdentificadores(ids);
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener los identificadores de expedientes.", e);
			return (ListaIdentificadores) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los identificadores de expedientes.", e);
			return (ListaIdentificadores) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los identificadores de expedientes.", e);
			return (ListaIdentificadores) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaIdentificadores) ServiciosUtils.completeReturnOK(lista);	
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 */
	public ListaInfoBExpedientes getExpedientes(String idEntidad, String[] idExps) {
		
		ListaInfoBExpedientes lista = new ListaInfoBExpedientes();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente[] exps = 
				service.getExpedientes(idEntidad, idExps);
			
			lista.setExpedientes(getInfoBExpedientes(exps));
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener los expedientes.", e);
			return (ListaInfoBExpedientes) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los expedientes.", e);
			return (ListaInfoBExpedientes) 
				ServiciosUtils.completeReturnError(lista,e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener los expedientes.", e);
			return (ListaInfoBExpedientes) 
				ServiciosUtils.completeReturnError(lista);
		}
		
		return (ListaInfoBExpedientes) ServiciosUtils.completeReturnOK(lista);	
	}

	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 * @return 
	 */
	public RetornoServicio archivarExpedientes(String idEntidad, String[] idExps) 
	{
		RetornoServicio ret = new RetornoServicio();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();
			service.archivarExpedientes(idEntidad, idExps);

		} catch (TramitacionException e) {
			logger.error("Error inesperado al archivar los expedientes", e);
			return (RetornoServicio) ServiciosUtils.completeReturnError(ret);
		} catch (SigemException e) {
			logger.error("Error inesperado al archivar los expedientes", e);
			return (RetornoServicio) ServiciosUtils.completeReturnError(ret);
		} catch (Throwable e){
			logger.error("Error inesperado al archivar los expedientes", e);
			return (RetornoServicio) ServiciosUtils.completeReturnError(ret);
		}
		
		return (RetornoServicio) ServiciosUtils.completeReturnOK(ret);	
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(String idEntidad, String idExp) {

		Expediente expediente = new Expediente();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente exp = 
				service.getExpediente(idEntidad, idExp);
			
			copyExpediente(expediente, exp);
			
		} catch (TramitacionException e) {
			logger.error("Error al obtener el expediente.", e);
			return (Expediente) 
				ServiciosUtils.completeReturnError(expediente, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el expediente.", e);
			return (Expediente) 
				ServiciosUtils.completeReturnError(expediente, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al obtener el expediente.", e);
			return (Expediente) 
				ServiciosUtils.completeReturnError(expediente);
		}
		
		return (Expediente) ServiciosUtils.completeReturnOK(expediente);	
	}
		
    /**
     * Iniciar un expediente.
     * 
     * @param commonData Datos comunes para todos los expedientes.
     * @param specificDataXML XML con los datos específicos del expediente.
     * @param documents Lista de documentos asociados al expediente.
     * @return Cierto si el expediente se ha iniciado correctamente.
     * @Si se produce algún error al iniciar el expediente.
     */
    public Booleano iniciarExpediente(String idEntidad, DatosComunesExpediente commonData, 
    		String specificDataXML, DocumentoExpediente[] documents) {

		Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.iniciarExpediente(idEntidad, 
					getCoreCommonData(commonData), 
					specificDataXML, 
					getCoreDocumentosExpediente(documents)));
			
		} catch (TramitacionException e) {
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	
    }

    /**
     * Crear un expediente.
     * @param idEntidad Identificador de la entidad.
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param datosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @param initSytem Sistema externo desdel el que se inicia el expediente
     * @return Numero de expediente creado
     * @throws ISPACException si se produce algún error.
     */
    public Cadena crearExpediente(String idEntidad, DatosComunesExpediente commonData, 
    		String specificDataXML, DocumentoExpediente[] documents, String initSystem) {

    	Cadena ret = new Cadena();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.iniciarExpediente(idEntidad, 
					getCoreCommonData(commonData), 
					specificDataXML, 
					getCoreDocumentosExpediente(documents), 
					initSystem));
			
		} catch (TramitacionException e) {
			logger.error("Error al iniciar el expediente.", e);
			return (Cadena) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al iniciar el expediente.", e);
			return (Cadena) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Cadena) ServiciosUtils.completeReturnOK(ret);	
    }

    /**
     * Cambia el estado administrativo de un expediente
     * @param idEntidad Identificador de la entidad.
     * @param numExp Número de expediente.
     * @param estadoAdm Nuevo estado administrativo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public Booleano cambiarEstadoAdministrativo(String idEntidad, String numExp, String estadoAdm){
		Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.cambiarEstadoAdministrativo(idEntidad, numExp, estadoAdm));
		} catch (TramitacionException e) {
			logger.error("Error al cambiar estado administrativo.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al cambiar estado administrativo.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al cambiar estado administrativo.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	    	
    }
    
    
    /**
     * Mueve un expedinete a una fase según el identificador de la misma en el catálogo. 
     * @param idEntidad Identificador de la entidad.
     * @param numExp Número de expediente.
     * @param idFaseCatalogo Identificador de la fase en el catálogo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public Booleano moverExpedienteAFase(String idEntidad, String numExp, String idFaseCatalogo){
		Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.moverExpedienteAFase(idEntidad, numExp, idFaseCatalogo));
		} catch (TramitacionException e) {
			logger.error("Error al mover expediente de fase.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al mover expediente de fase.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al mover expediente de fase.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	       	
    }
    
    /**
     * Añade documentos al trámite de un expediente.
     * @param idEntidad Identificador de la entidad.
     * @param numExp Número de expediente.
     * @param regNum Número de registro de entrada.
     * @param regDate Fecha de registro de entrada.
     * @param documents Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     */
    public Booleano anexarDocsExpediente(String idEntidad, String numExp, String regNum, 
    		Date regDate, DocumentoExpediente[] documents) {

		Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.anexarDocsExpediente(idEntidad, numExp, regNum, regDate, 
					getCoreDocumentosExpediente(documents)));
			
		} catch (TramitacionException e) {
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) 
				ServiciosUtils.completeReturnError(ret, e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	
    }


    
    /**
     * Busqueda avanzada sobre expedientes
     * @param idEntidad Identificador de la entidad.
     * @param groupName Nombre de grupo
     * @param searchFormName Nombre del formulario de busqueda a utilizar
     * @param searchXML XML con los criterios de busqueda
     * @param domain dominio de busqueda (en funcion de la responsabilidad)
     * @return Resultado de la busqueda
     * @throws ISPACException
     */
    public Cadena busquedaAvanzada(String idEntidad, String nombreGrupo, String nombreFrmBusqueda, String xmlBusqueda, int dominio){
		Cadena ret = new Cadena();
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.busquedaAvanzada(idEntidad, nombreGrupo, nombreFrmBusqueda, xmlBusqueda, dominio));
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		return (Cadena) ServiciosUtils.completeReturnOK(ret);	
    }
    
    /**
     * Inserta o actualiza los datos de un registro de una entidad para un expediente
     * @param idEntidad Identificador de la entidad.
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param xmlDatosEspecificos Datos especificos para completar los campos del registro a crear
     * @return identificador del registro creado
     * @throws ISPACException
     */	
	public Entero establecerDatosRegistroEntidad(String idEntidad, String nombreEntidad, String numExp, String xmlDatosEspecificos){
		Entero ret = new Entero();
		try {
			ServicioTramitacion service = getServicioTramitacion();
			ret.setValor(service.establecerDatosRegistroEntidad(idEntidad, nombreEntidad, numExp, xmlDatosEspecificos));
		} catch (Throwable e){
			logger.error("Error al insertar registro en la entidad '" + nombreEntidad + "'", e);
			return (Entero) ServiciosUtils.completeReturnError(ret);
		}
		return (Entero) ServiciosUtils.completeReturnOK(ret);
    }
    
    /**
     * Obtiene los datos de un registro de una entidad para un expediente
     * @param idEntidad Identificador de la entidad.
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */ 
    public Cadena obtenerRegistroEntidad(String idEntidad, String nombreEntidad, String numExp,int idRegistro){
		Cadena ret = new Cadena();
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.obtenerRegistroEntidad(idEntidad, nombreEntidad, numExp, idRegistro));
		} catch (Throwable e){
			logger.error("Error al obtener registro de la entidad '" + nombreEntidad + "'", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		return (Cadena) ServiciosUtils.completeReturnOK(ret);
	}
    
    
    /**
     * Obtiene los datos de todos los registros de una entidad para un expediente
     * @param idEntidad Identificador de la entidad.
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */
	public Cadena obtenerRegistrosEntidad(String idEntidad, String nombreEntidad, String numExp){
		Cadena ret = new Cadena();
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.obtenerRegistrosEntidad(idEntidad, nombreEntidad, numExp));
		} catch (Throwable e){
			logger.error("Error al obtener los registros de la entidad '" + nombreEntidad + "'", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		return (Cadena) ServiciosUtils.completeReturnOK(ret);		
	}    
    
    
        
    
    private InfoBProcedimiento[] getInfoBProcedimientos(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento[] procs) {
    	
		InfoBProcedimiento [] procedimientos = null;
		
		if (procs != null) {
			procedimientos = new InfoBProcedimiento[procs.length];
			for (int i = 0; i < procs.length; i++) {
				procedimientos[i] = getInfoBProcedimiento(procs[i]);
			}
		}

		return procedimientos;
    }

    
    private InfoBProcedimiento getInfoBProcedimiento(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento proc) {
    	
		InfoBProcedimiento procedimiento = null;
		
		if (proc != null) {
			procedimiento = new InfoBProcedimiento();
			procedimiento.setId(proc.getId());
			procedimiento.setCodigo(proc.getCodigo());
			procedimiento.setNombre(proc.getNombre());
			procedimiento.setCodSistProductor(proc.getCodSistProductor());
			procedimiento.setNombreSistProductor(proc.getNombreSistProductor());
		}

		return procedimiento;
    }

    private OrganoProductor[] getOrganosProductores(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.OrganoProductor[] orgs) {
    	
    	OrganoProductor [] organos = null;
		
		if (orgs != null) {
			organos = new OrganoProductor[orgs.length];
			for (int i = 0; i < orgs.length; i++) {
				organos[i] = getOrganoProductor(orgs[i]);
			}
		}

		return organos;
    }

    private OrganoProductor getOrganoProductor(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.OrganoProductor org) {
    	
		OrganoProductor organo = null;
		
		if (org != null) {
			organo = new OrganoProductor();
			organo.setId(org.getId());
			organo.setInicioProduccion(org.getInicioProduccion());
		}

		return organo;
    }

    private void copyProcedimiento(Procedimiento procedimiento, 
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento proc) {
    	if ( (procedimiento != null) && (proc != null) ) {

    		// Información básica
			procedimiento.getInformacionBasica().setId(
					proc.getInformacionBasica().getId());
			procedimiento.getInformacionBasica().setCodigo(
					proc.getInformacionBasica().getCodigo());
			procedimiento.getInformacionBasica().setNombre(
					proc.getInformacionBasica().getNombre());
			procedimiento.getInformacionBasica().setCodSistProductor(
					proc.getInformacionBasica().getCodSistProductor());
			procedimiento.getInformacionBasica().setNombreSistProductor(
					proc.getInformacionBasica().getNombreSistProductor());

    		procedimiento.setObjeto(proc.getObjeto());
    		procedimiento.setTramites(proc.getTramites());
    		procedimiento.setNormativa(proc.getNormativa());
    		procedimiento.setDocumentosBasicos(proc.getDocumentosBasicos());
    		procedimiento.setOrganosProductores(
    				getOrganosProductores(proc.getOrganosProductores()));
    	}
    }
    
    private Firma[] getFirmas(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Firma[] signs) {
    	
    	Firma [] firmas = null;
		
		if (signs != null) {
			firmas = new Firma[signs.length];
			for (int i = 0; i < signs.length; i++) {
				firmas[i] = getFirma(signs[i]);
			}
		}

		return firmas;
    }

    private Firma getFirma(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Firma sign) {
    	
		Firma firma = null;
		
		if (sign != null) {
			firma = new Firma();
			firma.setAutor(sign.getAutor());
			firma.setAutenticada(sign.isAutenticada());
		}

		return firma;
    }

    private void copyInfoFichero(InfoFichero infoFichero, 
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero info) {
    	if ( (infoFichero != null) && (info != null) ) {
    		infoFichero.setNombre(info.getNombre());
    		infoFichero.setFechaAlta(info.getFechaAlta());
    		infoFichero.setFirmas(getFirmas(info.getFirmas()));
    	}
    }

    private void copyInfoOcupacion(InfoOcupacion infoOcupacion, 
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion info) {
    	if ( (infoOcupacion != null) && (info != null) ) {
    		infoOcupacion.setEspacioTotal(info.getEspacioTotal());
    		infoOcupacion.setEspacioOcupado(info.getEspacioOcupado());
    		infoOcupacion.setNumeroFicheros(info.getNumeroFicheros());
    	}
    }

    private InfoBExpediente[] getInfoBExpedientes(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente[] exps) {
    	
		InfoBExpediente [] expedientes = null;
		
		if (exps != null) {
			expedientes = new InfoBExpediente[exps.length];
			for (int i = 0; i < exps.length; i++) {
				expedientes[i] = getInfoBExpediente(exps[i]);
			}
		}

		return expedientes;
    }

    private InfoBExpediente getInfoBExpediente(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente exp) {
    	
		InfoBExpediente expediente = null;
		
		if (exp != null) {
			expediente = new InfoBExpediente();
			expediente.setId(exp.getId());
			expediente.setNumExp(exp.getNumExp());
			expediente.setDatosIdentificativos(exp.getDatosIdentificativos());
		}

		return expediente;
    }

    private DocFisico[] getDocumentosFisicos(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.DocFisico[] doc) {
    	
    	DocFisico [] documentos = null;
		
		if (doc != null) {
			documentos = new DocFisico[doc.length];
			for (int i = 0; i < doc.length; i++) {
				documentos[i] = getDocFisico(doc[i]);
			}
		}

		return documentos;
    }

    private DocFisico getDocFisico(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.DocFisico doc) {
    	
		DocFisico documento = null;
		
		if (doc != null) {
			documento = new DocFisico();
			documento.setTipoDocumento(doc.getTipoDocumento());
			documento.setAsunto(doc.getAsunto());
		}

		return documento;
    }

    private DocElectronico[] getDocumentosElectronicos(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.DocElectronico[] doc) {
    	
    	DocElectronico [] documentos = null;
		
		if (doc != null) {
			documentos = new DocElectronico[doc.length];
			for (int i = 0; i < doc.length; i++) {
				documentos[i] = getDocElectronico(doc[i]);
			}
		}

		return documentos;
    }

    private DocElectronico getDocElectronico(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.DocElectronico doc) {
    	
		DocElectronico documento = null;
		
		if (doc != null) {
			documento = new DocElectronico();
			documento.setTipoDocumento(doc.getTipoDocumento());
			documento.setAsunto(doc.getAsunto());
			documento.setRepositorio(doc.getRepositorio());
			documento.setLocalizador(doc.getLocalizador());
			documento.setExtension(doc.getExtension());
		}

		return documento;
    }

    private Interesado[] getInteresados(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado[] inters) {
    	
    	Interesado [] interesados = null;
		
		if (inters != null) {
			interesados = new Interesado[inters.length];
			for (int i = 0; i < inters.length; i++) {
				interesados[i] = getInteresado(inters[i]);
			}
		}

		return interesados;
    }

    private Interesado getInteresado(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado inter) {
    	
		Interesado interesado = null;
		
		if (inter != null) {
			interesado = new Interesado();
			interesado.setNombre(inter.getNombre());
			interesado.setNumIdentidad(inter.getNumIdentidad());
			interesado.setRol(inter.getRol());
			interesado.setInteresadoPrincipal(inter.isInteresadoPrincipal());
			interesado.setIdEnTerceros(inter.getIdEnTerceros());
		}

		return interesado;
    }

    private Emplazamiento[] getEmplazamientos(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento[] emps) {
    	
    	Emplazamiento [] emplazamientos = null;
		
		if (emps != null) {
			emplazamientos = new Emplazamiento[emps.length];
			for (int i = 0; i < emps.length; i++) {
				emplazamientos[i] = getEmplazamiento(emps[i]);
			}
		}

		return emplazamientos;
    }

    private Emplazamiento getEmplazamiento(
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento emp) {
    	
		Emplazamiento emplazamiento = null;
		
		if (emp != null) {
			emplazamiento = new Emplazamiento();
			emplazamiento.setPais(emp.getPais());
			emplazamiento.setComunidad(emp.getComunidad());
			emplazamiento.setConcejo(emp.getConcejo());
			emplazamiento.setPoblacion(emp.getPoblacion());
			emplazamiento.setLocalizacion(emp.getLocalizacion());
		}

		return emplazamiento;
    }

    private void copyExpediente(Expediente expediente, 
    		ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente exp) {
    	if ( (expediente != null) && (exp != null) ) {

    		// Información básica
			expediente.getInformacionBasica().setId(
					exp.getInformacionBasica().getId());
			expediente.getInformacionBasica().setNumExp(
					exp.getInformacionBasica().getNumExp());
			expediente.getInformacionBasica().setDatosIdentificativos(
					exp.getInformacionBasica().getDatosIdentificativos());

			expediente.setFechaInicio(exp.getFechaInicio());
			expediente.setFechaFinalizacion(exp.getFechaFinalizacion());
			expediente.setIdOrgProductor(exp.getIdOrgProductor());
			expediente.setNombreOrgProductor(exp.getNombreOrgProductor());
			expediente.setAsunto(exp.getAsunto());

			expediente.setDocumentosFisicos(
					getDocumentosFisicos(exp.getDocumentosFisicos()));
			expediente.setDocumentosElectronicos(
					getDocumentosElectronicos(exp.getDocumentosElectronicos()));
			expediente.setInteresados(getInteresados(exp.getInteresados()));
			expediente.setEmplazamientos(
					getEmplazamientos(exp.getEmplazamientos()));
    	}
    }
    
    private ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente[] getInteresadosExpediente(
    		InteresadoExpediente[] inters) {
    	
    	ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente [] interesados = null;
		
		if (inters != null) {
			interesados = new ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente[inters.length];
			for (int i = 0; i < inters.length; i++) {
				interesados[i] = getInteresadoExpediente(inters[i]);
			}
		}

		return interesados;
    }

    private ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente getInteresadoExpediente(
    		InteresadoExpediente inter) {
    	
    	ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente interesado = null;
		
		if (inter != null) {
			interesado = new ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente();

			interesado.setThirdPartyId(inter.getThirdPartyId());
			interesado.setIndPrincipal(inter.getIndPrincipal());
			interesado.setNifcif(inter.getNifcif());
			interesado.setName(inter.getName());
			interesado.setPostalAddress(inter.getPostalAddress());
			interesado.setPostalCode(inter.getPostalCode());
			interesado.setPlaceCity(inter.getPlaceCity());
			interesado.setRegionCountry(inter.getRegionCountry());
			interesado.setTelematicAddress(inter.getTelematicAddress());
			interesado.setNotificationAddressType(inter.getNotificationAddressType());
			interesado.setPhone(inter.getPhone());
			interesado.setMobilePhone(inter.getMobilePhone());
		}

		return interesado;
    }

    private ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente 
    		getCoreCommonData(DatosComunesExpediente datos) {
    	
    	ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente 
    		datosComunes = null;
		
		if (datos != null) {
			datosComunes = new ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente();
			datosComunes.setIdOrganismo(datos.getIdOrganismo());
			datosComunes.setTipoAsunto(datos.getTipoAsunto());
			datosComunes.setNumeroRegistro(datos.getNumeroRegistro());
			datosComunes.setFechaRegistro(datos.getFechaRegistro());
			datosComunes.setInteresados(
					getInteresadosExpediente(datos.getInteresados()));
		}

		return datosComunes;
    }
    
    private ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente[] 
            getCoreDocumentosExpediente(DocumentoExpediente[] docs) {
    	
    	ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente [] documentos = null;
		
		if (docs != null) {
			documentos = new ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente[docs.length];
			for (int i = 0; i < docs.length; i++) {
				documentos[i] = getCoreDocumentoExpediente(docs[i]);
			}
		}

		return documentos;
    }

    private ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente getCoreDocumentoExpediente(
    		DocumentoExpediente doc) {
    	
    	ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente documento = null;
		
		if (doc != null) {
			documento = new ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente();
			documento.setId(doc.getId());
			documento.setCode(doc.getCode());
			documento.setName(doc.getName());
			documento.setContent(doc.getContent());
			documento.setExtension(doc.getExtension());
			documento.setLenght(doc.getLenght());
		}

		return documento;
    }
}
