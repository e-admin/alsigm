package ieci.tdw.ispac.services;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.services.dto.DatosComunesExpediente;
import ieci.tdw.ispac.services.dto.DocumentoExpediente;
import ieci.tdw.ispac.services.dto.Expediente;
import ieci.tdw.ispac.services.dto.InfoBExpediente;
import ieci.tdw.ispac.services.dto.InfoBProcedimiento;
import ieci.tdw.ispac.services.dto.InfoFichero;
import ieci.tdw.ispac.services.dto.InfoOcupacion;
import ieci.tdw.ispac.services.dto.Procedimiento;

import java.util.Date;


/**
 * Interfaz para el servicio de Tramitación.  
 * 
 */
public interface ServicioTramitacion {

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
	public InfoBProcedimiento[] getProcedimientos(int tipoProc, String nombre) 
		throws ISPACException;

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(String[] idProcs) 
		throws ISPACException;

	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 */
	public Procedimiento getProcedimiento(String idProc) 
		throws ISPACException;

    /**
     * Obtiene el contenido del documento.
     * @param guid GUID del documento
     * @return Contenido del documento.
     * @throws ISPACException si ocurre algún error.
     */
    public byte [] getFichero(String guid) throws ISPACException;

	/**
	 * Obtiene la información de origen e integridad del documento.
	 * @param guid GUID del documento.
	 * @return Información del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InfoFichero getInfoFichero(String guid) 
		throws ISPACException;

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * @return Información de ocupación.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InfoOcupacion getInfoOcupacion() throws ISPACException;

	/**
	 * Elimina los documentos determinados por los GUIDs.
	 * @param guids Lista de GUIDs de los documentos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void eliminaFicheros(String[] guids) throws ISPACException;

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
	public String[] getIdsExpedientes(String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws ISPACException;

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 */
	public InfoBExpediente[] getExpedientes(String[] idExps) 
		throws ISPACException;

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(String idExp) throws ISPACException;
		
	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 * @throws ISPACException
	 */
	public void archivarExpedientes(String []idExps )throws ISPACException;
	
	
    /**
     * Crear un expediente.
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param datosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si el expediente se ha creado correctamente.
     * @throws ISPACException si se produce algún error.
     */
    public boolean iniciarExpediente(DatosComunesExpediente datosComunes, 
    		String datosEspecificos, DocumentoExpediente[] documentos) 
    	throws ISPACException;

    /**
     * Crear un expediente.
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param datosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @param initSytem Sistema externo desdel el que se inicia el expediente
     * @return Numero de expediente creado
     * @throws ISPACException si se produce algún error.
     */
    public String iniciarExpediente(DatosComunesExpediente datosComunes, 
    		String datosEspecificos, DocumentoExpediente[] documentos, String initSystem) 
    	throws ISPACException;    
    
    /**
     * Añade documentos al trámite de un expediente.
     * @param numExp Número de expediente.
     * @param numReg Número de registro de entrada.
     * @param fechaReg Fecha de registro de entrada.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     * @throws ISPACException Si se produce algún error.
     */
    public boolean anexarDocsExpediente(String numExp, String numReg, 
    		Date fechaReg, DocumentoExpediente[] documentos) 
    	throws ISPACException;


    /**
     * Cambia el estado administrativo de un expediente
     * @param numExp Número de expediente.
     * @param estadoAdm Nuevo estado administrativo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public boolean cambiarEstadoAdministrativo(String numExp, String estadoAdm) 
    	throws ISPACException;
    
    
    /**
     * Mueve un expedinete a una fase según el identificador de la misma en el catálogo. 
     * @param numExp Número de expediente.
     * @param idFaseCatalogo Identificador de la fase en el catálogo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public boolean moverExpedienteAFase(String numExp, String idFaseCatalogo)
    	throws ISPACException;
    
    
    /**
     * Busqueda avanzada sobre expedientes
     * @param groupName Nombre de grupo
     * @param searchFormName Nombre del formulario de busqueda a utilizar
     * @param searchXML XML con los criterios de busqueda
     * @param domain dominio de busqueda (en funcion de la responsabilidad)
     * @return Resultado de la busqueda
     * @throws ISPACException
     */
    public String busquedaAvanzada(String nombreGrupo, String nombreFrmBusqueda, String xmlBusqueda, int dominio)throws ISPACException;
    
    /**
     * Inserta o actualiza los datos de un registro de una entidad para un expediente
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param xmlDatosEspecificos Datos especificos para completar los campos del registro a crear
     * @return identificador del registro creado
     * @throws ISPACException
     */
    public int establecerDatosRegistroEntidad(String nombreEntidad, String numExp, String xmlDatosEspecificos)throws ISPACException;
    
    /**
     * Obtiene los datos de un registro de una entidad para un expediente
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */
    public String obtenerRegistroEntidad(String nombreEntidad, String numExp, int idRegistro)throws ISPACException;
    
    /**
     * Obtiene los datos de todos los registros de una entidad para un expediente
     * @param nombreEntidad Nombre de entidad
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */
    public String obtenerRegistrosEntidad(String nombreEntidad, String numExp)throws ISPACException;

}
