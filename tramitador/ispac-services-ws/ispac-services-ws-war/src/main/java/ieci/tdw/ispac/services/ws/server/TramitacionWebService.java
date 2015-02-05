package ieci.tdw.ispac.services.ws.server;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.services.ServicioTramitacion;
import ieci.tdw.ispac.services.ServicioTramitacionAdapter;
import ieci.tdw.ispac.services.ServiciosUtils;
import ieci.tdw.ispac.services.dto.RetornoServicio;
import ieci.tdw.ispac.services.ws.server.dto.Binario;
import ieci.tdw.ispac.services.ws.server.dto.Booleano;
import ieci.tdw.ispac.services.ws.server.dto.Cadena;
import ieci.tdw.ispac.services.ws.server.dto.DatosComunesExpediente;
import ieci.tdw.ispac.services.ws.server.dto.DocElectronico;
import ieci.tdw.ispac.services.ws.server.dto.DocFisico;
import ieci.tdw.ispac.services.ws.server.dto.DocumentoExpediente;
import ieci.tdw.ispac.services.ws.server.dto.Emplazamiento;
import ieci.tdw.ispac.services.ws.server.dto.Entero;
import ieci.tdw.ispac.services.ws.server.dto.Expediente;
import ieci.tdw.ispac.services.ws.server.dto.Firma;
import ieci.tdw.ispac.services.ws.server.dto.InfoBExpediente;
import ieci.tdw.ispac.services.ws.server.dto.InfoBProcedimiento;
import ieci.tdw.ispac.services.ws.server.dto.InfoFichero;
import ieci.tdw.ispac.services.ws.server.dto.InfoOcupacion;
import ieci.tdw.ispac.services.ws.server.dto.Interesado;
import ieci.tdw.ispac.services.ws.server.dto.InteresadoExpediente;
import ieci.tdw.ispac.services.ws.server.dto.ListaIdentificadores;
import ieci.tdw.ispac.services.ws.server.dto.ListaInfoBExpedientes;
import ieci.tdw.ispac.services.ws.server.dto.ListaInfoBProcedimientos;
import ieci.tdw.ispac.services.ws.server.dto.OrganoProductor;
import ieci.tdw.ispac.services.ws.server.dto.Procedimiento;

import java.util.Date;

import org.apache.axis.Constants;
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
	
	private ServicioTramitacion getServicioTramitacion() {
		return new ServicioTramitacionAdapter();
	}

	/**
	 * 
	 * Establece la ip del cliente que hace la llamada al WS en el contexto de la auditoría
	 * 
	 */
	private void setAuditContext() {
		
		MessageContext messageContext = MessageContext.getCurrentContext(); 
		String userIP = messageContext.getStrProp(Constants.MC_REMOTE_ADDR);
		
		AuditContext auditContext = new AuditContext();
		
		//Establecemos la misma en los dos porque no encontramoe el método para otbtener la IP
		auditContext.setUserHost(userIP);
		auditContext.setUserIP(userIP);		
		AuditContextHolder.setAuditContext(auditContext);
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
	public ListaInfoBProcedimientos getProcedimientosPorTipo(int tipoProc, 
			String nombre) {

    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		ListaInfoBProcedimientos lista = new ListaInfoBProcedimientos();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tdw.ispac.services.dto.InfoBProcedimiento[] procs = 
				service.getProcedimientos(tipoProc, nombre);
			
			lista.setProcedimientos(getInfoBProcedimientos(procs));
			
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
	public ListaInfoBProcedimientos getProcedimientos(String[] idProcs) {
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		ListaInfoBProcedimientos lista = new ListaInfoBProcedimientos();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tdw.ispac.services.dto.InfoBProcedimiento[] procs = 
				service.getProcedimientos(idProcs);
			
			lista.setProcedimientos(getInfoBProcedimientos(procs));
			
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
	public Procedimiento getProcedimiento(String idProc) {
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		Procedimiento procedimiento = new Procedimiento();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tdw.ispac.services.dto.Procedimiento proc = 
				service.getProcedimiento(idProc);
			
			copyProcedimiento(procedimiento, proc);
			
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
    public Binario getFichero(String guid) {
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
    	Binario binario = new Binario();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			binario.setContenido(service.getFichero(guid));
			
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
	public InfoFichero getInfoFichero(String guid) {
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		InfoFichero infoFichero = new InfoFichero();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tdw.ispac.services.dto.InfoFichero info = 
				service.getInfoFichero(guid);
			
			copyInfoFichero(infoFichero, info);
			
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
	public InfoOcupacion getInfoOcupacion() {
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		InfoOcupacion infoOcupacion = new InfoOcupacion();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tdw.ispac.services.dto.InfoOcupacion info = 
				service.getInfoOcupacion();
			
			copyInfoOcupacion(infoOcupacion, info);
			
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
	public RetornoServicio eliminaFicheros(String[] guids) {
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		RetornoServicio ret = new RetornoServicio();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			service.eliminaFicheros(guids);
			
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
	public ListaIdentificadores getIdsExpedientes(String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) {

    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		ListaIdentificadores lista = new ListaIdentificadores();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			String [] ids = service.getIdsExpedientes(idProc, fechaIni, 
					fechaFin, tipoOrd);
			
			lista.setIdentificadores(ids);
			
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
	public ListaInfoBExpedientes getExpedientes(String[] idExps) {
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		ListaInfoBExpedientes lista = new ListaInfoBExpedientes();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tdw.ispac.services.dto.InfoBExpediente[] exps = 
				service.getExpedientes(idExps);
			
			lista.setExpedientes(getInfoBExpedientes(exps));
			
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
	public RetornoServicio archivarExpedientes(String[] idExps) 
	{
		//Establecer la ip en el contexto de auditoría
		setAuditContext();
		
		RetornoServicio ret = new RetornoServicio();
		
		try {
			ServicioTramitacion service= getServicioTramitacion();
			service.archivarExpedientes(idExps);
			
		} catch (Throwable e){
			logger.error("Error inesperado al archivar los expedientes[idExps="
					+ idExps + "]", e);
			return (RetornoServicio) ServiciosUtils.completeReturnError(ret);
		}
		
		return (RetornoServicio) ServiciosUtils.completeReturnOK(ret);	
		
		
	}
	
	
	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(String idExp) {

    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		Expediente expediente = new Expediente();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			
			ieci.tdw.ispac.services.dto.Expediente exp = 
				service.getExpediente(idExp);
			
			copyExpediente(expediente, exp);
			
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
    public Booleano iniciarExpediente(DatosComunesExpediente commonData, 
    		String specificDataXML, DocumentoExpediente[] documents) {

    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.iniciarExpediente(
					getCoreCommonData(commonData), 
					specificDataXML, 
					getCoreDocumentosExpediente(documents)));
			
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	
    }

    
    /**
     * Iniciar un expediente.
     * 
     * @param commonData Datos comunes para todos los expedientes.
     * @param specificDataXML XML con los datos específicos del expediente.
     * @param documents Lista de documentos asociados al expediente.
     * @param initSytem Sistema externo desde el que se inicia el expediente
     * @return Numero de expediente creado
     * @Si se produce algún error al iniciar el expediente.
     */
    public Cadena crearExpediente(DatosComunesExpediente commonData, 
    		String specificDataXML, DocumentoExpediente[] documents, String initSystem) {
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		Cadena ret = new Cadena();
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.iniciarExpediente(
					getCoreCommonData(commonData), 
					specificDataXML, 
					getCoreDocumentosExpediente(documents), 
					initSystem));
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Cadena) ServiciosUtils.completeReturnOK(ret);	
    }    
    
    /**
     * Cambia el estado administrativo de un expediente
     * @param numExp Número de expediente.
     * @param estadoAdm Nuevo estado administrativo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public Booleano cambiarEstadoAdministrativo (String numExp, String estadoAdm){
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
    	Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.cambiarEstadoAdministrativo(numExp, estadoAdm));
		} catch (Throwable e){
			logger.error("Error al cambiar estado administratrivo del expediente '"+numExp+"'.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	    	
    }
    
    
    public Booleano moverExpedienteAFase(String numExp, String idFaseCatalogo){
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
    	Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.moverExpedienteAFase(numExp, idFaseCatalogo));
		} catch (Throwable e){
			logger.error("Error al mover el expediente '"+numExp+" a la fase cuyo identificador en el catálogo es '" + idFaseCatalogo + "'.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	    	
    }
    
    
    /**
     * Añade documentos al trámite de un expediente.
     * @param numExp Número de expediente.
     * @param regNum Número de registro de entrada.
     * @param regDate Fecha de registro de entrada.
     * @param documents Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     */
    public Booleano anexarDocsExpediente(String numExp, String regNum, 
    		Date regDate, DocumentoExpediente[] documents) {

    	setAuditContext();
    	
		Booleano ret = new Booleano();
		
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.anexarDocsExpediente(numExp, regNum, regDate, 
					getCoreDocumentosExpediente(documents)));
			
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Booleano) ServiciosUtils.completeReturnError(ret);
		}
		
		return (Booleano) ServiciosUtils.completeReturnOK(ret);	
    }

    
    /**
     * Busqueda avanzada sobre expedientes
     * @param groupName Nombre de grupo
     * @param searchFormName Nombre del formulario de busqueda a utilizar
     * @param searchXML XML con los criterios de busqueda
     * @param domain dominio de busqueda (en funcion de la responsabilidad)
     * @return Resultado de la busqueda
     * @throws ISPACException
     */
    public Cadena busquedaAvanzada(String nombreGrupo, String nombreFrmBusqueda, String xmlBusqueda, int dominio){
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	Cadena ret = new Cadena();
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.busquedaAvanzada(nombreGrupo, nombreFrmBusqueda, xmlBusqueda, dominio));
		} catch (Throwable e){
			logger.error("Error al iniciar el expediente.", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		return (Cadena) ServiciosUtils.completeReturnOK(ret);	
    }
    
    /**
     * Inserta o actualiza los datos de un registro de una entidad para un expediente
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param xmlDatosEspecificos Datos especificos para completar los campos del registro a crear
     * @return identificador del registro creado
     * @throws ISPACException
     */	
	public Entero establecerDatosRegistroEntidad(String nombreEntidad, String numExp, String xmlDatosEspecificos){
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		Entero ret = new Entero();
		try {
			ServicioTramitacion service = getServicioTramitacion();
			ret.setValor(service.establecerDatosRegistroEntidad(nombreEntidad, numExp, xmlDatosEspecificos));
		} catch (Throwable e){
			logger.error("Error al insertar registro en la entidad '" + nombreEntidad + "'", e);
			return (Entero) ServiciosUtils.completeReturnError(ret);
		}
		return (Entero) ServiciosUtils.completeReturnOK(ret);
    }
    
    /**
     * Obtiene los datos de un registro de una entidad para un expediente
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */ 
    public Cadena obtenerRegistroEntidad(String nombreEntidad, String numExp,int idRegistro){
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
    	Cadena ret = new Cadena();
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.obtenerRegistroEntidad(nombreEntidad, numExp, idRegistro));
		} catch (Throwable e){
			logger.error("Error al obtener registro de la entidad '" + nombreEntidad + "'", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		return (Cadena) ServiciosUtils.completeReturnOK(ret);
	}
    
    
    /**
     * Obtiene los datos de todos los registros de una entidad para un expediente
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */
	public Cadena obtenerRegistrosEntidad(String nombreEntidad, String numExp){
		
    	//Establecer la ip en el contexto de auditoría
    	setAuditContext();
    	
		Cadena ret = new Cadena();
		try {
			ServicioTramitacion service = getServicioTramitacion();	
			ret.setValor(service.obtenerRegistrosEntidad(nombreEntidad, numExp));
		} catch (Throwable e){
			logger.error("Error al obtener los registros de la entidad '" + nombreEntidad + "'", e);
			return (Cadena) ServiciosUtils.completeReturnError(ret);
		}
		return (Cadena) ServiciosUtils.completeReturnOK(ret);		
	}
    
    
    private InfoBProcedimiento[] getInfoBProcedimientos(
    		ieci.tdw.ispac.services.dto.InfoBProcedimiento[] procs) {
    	
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
    		ieci.tdw.ispac.services.dto.InfoBProcedimiento proc) {
    	
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
    		ieci.tdw.ispac.services.dto.OrganoProductor[] orgs) {
    	
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
    		ieci.tdw.ispac.services.dto.OrganoProductor org) {
    	
		OrganoProductor organo = null;
		
		if (org != null) {
			organo = new OrganoProductor();
			organo.setId(org.getId());
			organo.setInicioProduccion(org.getInicioProduccion());
		}

		return organo;
    }

    private void copyProcedimiento(Procedimiento procedimiento, 
    		ieci.tdw.ispac.services.dto.Procedimiento proc) {
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
    		ieci.tdw.ispac.services.dto.Firma[] signs) {
    	
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
    		ieci.tdw.ispac.services.dto.Firma sign) {
    	
		Firma firma = null;
		
		if (sign != null) {
			firma = new Firma();
			firma.setAutor(sign.getAutor());
			firma.setAutenticada(sign.isAutenticada());
		}

		return firma;
    }

    private void copyInfoFichero(InfoFichero infoFichero, 
    		ieci.tdw.ispac.services.dto.InfoFichero info) {
    	if ( (infoFichero != null) && (info != null) ) {
    		infoFichero.setNombre(info.getNombre());
    		infoFichero.setFechaAlta(info.getFechaAlta());
    		infoFichero.setFirmas(getFirmas(info.getFirmas()));
    	}
    }

    private void copyInfoOcupacion(InfoOcupacion infoOcupacion, 
    		ieci.tdw.ispac.services.dto.InfoOcupacion info) {
    	if ( (infoOcupacion != null) && (info != null) ) {
    		infoOcupacion.setEspacioTotal(info.getEspacioTotal());
    		infoOcupacion.setEspacioOcupado(info.getEspacioOcupado());
    		infoOcupacion.setNumeroFicheros(info.getNumeroFicheros());
    	}
    }

    private InfoBExpediente[] getInfoBExpedientes(
    		ieci.tdw.ispac.services.dto.InfoBExpediente[] exps) {
    	
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
    		ieci.tdw.ispac.services.dto.InfoBExpediente exp) {
    	
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
    		ieci.tdw.ispac.services.dto.DocFisico[] doc) {
    	
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
    		ieci.tdw.ispac.services.dto.DocFisico doc) {
    	
		DocFisico documento = null;
		
		if (doc != null) {
			documento = new DocFisico();
			documento.setTipoDocumento(doc.getTipoDocumento());
			documento.setAsunto(doc.getAsunto());
		}

		return documento;
    }

    private DocElectronico[] getDocumentosElectronicos(
    		ieci.tdw.ispac.services.dto.DocElectronico[] doc) {
    	
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
    		ieci.tdw.ispac.services.dto.DocElectronico doc) {
    	
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
    		ieci.tdw.ispac.services.dto.Interesado[] inters) {
    	
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
    		ieci.tdw.ispac.services.dto.Interesado inter) {
    	
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
    		ieci.tdw.ispac.services.dto.Emplazamiento[] emps) {
    	
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
    		ieci.tdw.ispac.services.dto.Emplazamiento emp) {
    	
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
    		ieci.tdw.ispac.services.dto.Expediente exp) {
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
    
    private ieci.tdw.ispac.services.dto.InteresadoExpediente[] getInteresadosExpediente(
    		InteresadoExpediente[] inters) {
    	
    	ieci.tdw.ispac.services.dto.InteresadoExpediente [] interesados = null;
		
		if (inters != null) {
			interesados = new ieci.tdw.ispac.services.dto.InteresadoExpediente[inters.length];
			for (int i = 0; i < inters.length; i++) {
				interesados[i] = getInteresadoExpediente(inters[i]);
			}
		}

		return interesados;
    }

    private ieci.tdw.ispac.services.dto.InteresadoExpediente getInteresadoExpediente(
    		InteresadoExpediente inter) {
    	
    	ieci.tdw.ispac.services.dto.InteresadoExpediente interesado = null;
		
		if (inter != null) {
			interesado = new ieci.tdw.ispac.services.dto.InteresadoExpediente();

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

    private ieci.tdw.ispac.services.dto.DatosComunesExpediente 
    		getCoreCommonData(DatosComunesExpediente datos) {
    	
    	ieci.tdw.ispac.services.dto.DatosComunesExpediente 
    		datosComunes = null;
		
		if (datos != null) {
			datosComunes = new ieci.tdw.ispac.services.dto.DatosComunesExpediente();
			datosComunes.setIdOrganismo(datos.getIdOrganismo());
			datosComunes.setTipoAsunto(datos.getTipoAsunto());
			datosComunes.setNumeroRegistro(datos.getNumeroRegistro());
			datosComunes.setFechaRegistro(datos.getFechaRegistro());
			datosComunes.setInteresados(
					getInteresadosExpediente(datos.getInteresados()));
		}

		return datosComunes;
    }
    
    private ieci.tdw.ispac.services.dto.DocumentoExpediente[] 
            getCoreDocumentosExpediente(DocumentoExpediente[] docs) {
    	
    	ieci.tdw.ispac.services.dto.DocumentoExpediente [] documentos = null;
		
		if (docs != null) {
			documentos = new ieci.tdw.ispac.services.dto.DocumentoExpediente[docs.length];
			for (int i = 0; i < docs.length; i++) {
				documentos[i] = getCoreDocumentoExpediente(docs[i]);
			}
		}

		return documentos;
    }

    private ieci.tdw.ispac.services.dto.DocumentoExpediente getCoreDocumentoExpediente(
    		DocumentoExpediente doc) {
    	
    	ieci.tdw.ispac.services.dto.DocumentoExpediente documento = null;
		
		if (doc != null) {
			documento = new ieci.tdw.ispac.services.dto.DocumentoExpediente();
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
