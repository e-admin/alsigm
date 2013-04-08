package ieci.tecdoc.sgm.core.services.tramitacion;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento;

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
	public InfoBProcedimiento[] getProcedimientos(String idEntidad, int tipoProc, String nombre) 
		throws TramitacionException;

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(String idEntidad, String[] idProcs) 
		throws TramitacionException;

	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 */
	public Procedimiento getProcedimiento(String idEntidad, String idProc) 
		throws TramitacionException;

    /**
     * Obtiene el contenido del documento.
     * @param guid GUID del documento
     * @return Contenido del documento.
     * @throws TramitacionException si ocurre algún error.
     */
    public byte [] getFichero(String idEntidad, String guid) throws TramitacionException;

	/**
	 * Obtiene la información de origen e integridad del documento.
	 * @param guid GUID del documento.
	 * @return Información del documento.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public InfoFichero getInfoFichero(String idEntidad, String guid) 
		throws TramitacionException;

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * @return Información de ocupación.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public InfoOcupacion getInfoOcupacion(String idEntidad) throws TramitacionException;

	/**
	 * Elimina los documentos determinados por los GUIDs.
	 * @param guids Lista de GUIDs de los documentos.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public void eliminaFicheros(String idEntidad, String[] guids) throws TramitacionException;

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
	public String[] getIdsExpedientes(String idEntidad, String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws TramitacionException;

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 */
	public InfoBExpediente[] getExpedientes(String idEntidad, String[] idExps) 
		throws TramitacionException;

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(String idEntidad, String idExp) throws TramitacionException;
		
	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 * @throws ISPACException
	 */
	public void archivarExpedientes(String identidad, String []idExps )throws TramitacionException;

	/**
     * Crear un expediente.
     * @param idEntidad Identificador de la entidad.
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param datosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si el expediente se ha creado correctamente.
     * @throws TramitacionException si se produce algún error.
     */
    public boolean iniciarExpediente(String idEntidad, DatosComunesExpediente datosComunes, 
    		String datosEspecificos, DocumentoExpediente[] documentos) 
    	throws TramitacionException;

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
    public String iniciarExpediente(String idEntidad, DatosComunesExpediente datosComunes, 
    		String datosEspecificos, DocumentoExpediente[] documentos, String initSystem) 
    	throws TramitacionException;        
    
    
    /**
     * Añade documentos al trámite de un expediente.
     * @param idEntidad Identificador de la entidad.
     * @param numReg Número de registro de entrada.
     * @param fechaReg Fecha de registro de entrada.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     * @throws TramitacionException Si se produce algún error.
     */
    public boolean anexarDocsExpediente(String idEntidad, String numExp, String numReg, 
    		Date fechaReg, DocumentoExpediente[] documentos) 
    	throws TramitacionException;


    /**
     * Cambia el estado administrativo de un expediente
     * @param idEntidad Identificador de la entidad.
     * @param numExp Número de expediente.
     * @param estadoAdm Nuevo estado administrativo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public boolean cambiarEstadoAdministrativo(String idEntidad, String numExp, String estadoAdm) 
    	throws TramitacionException;
    
    
    /**
     * Mueve un expedinete a una fase según el identificador de la misma en el catálogo. 
     * @param idEntidad Identificador de la entidad.
     * @param numExp Número de expediente.
     * @param idFaseCatalogo Identificador de la fase en el catálogo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public boolean moverExpedienteAFase(String idEntidad, String numExp, String idFaseCatalogo)
    	throws TramitacionException;

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
    public String busquedaAvanzada(String idEntidad, String nombreGrupo, String nombreFrmBusqueda, String xmlBusqueda, int dominio)throws TramitacionException;
    
    /**
     * Inserta o actualiza los datos de un registro de una entidad para un expediente
     * @param idEntidad Identificador de la entidad.
     * @param nombreEntidad Nombre de entidad en BBDD
     * @param numExp Número de expediente
     * @param xmlDatosEspecificos Datos especificos para completar los campos del registro a crear
     * @return identificador del registro creado
     * @throws ISPACException
     */
    public int establecerDatosRegistroEntidad(String idEntidad, String nombreEntidad, String numExp, String xmlDatosEspecificos)throws TramitacionException;
    
    /**
     * Obtiene los datos de un registro de una entidad para un expediente
     * @param idEntidad Identificador de la entidad.
     * @param nombreEntidad Nombre de entidad en BBDD
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */
    public String obtenerRegistroEntidad(String idEntidad, String nombreEntidad, String numExp, int idRegistro)throws TramitacionException;
    
    /**
     * Obtiene los datos de todos los registros de una entidad para un expediente
     * @param idEntidad Identificador de la entidad.
     * @param nombreEntidad Nombre de entidad en BBDD
     * @param numExp Número de expediente
     * @param idRegistro Identificdaor del registro
     * @return Información del registro obtenido
     * @throws ISPACException
     */
    public String obtenerRegistrosEntidad(String idEntidad, String nombreEntidad, String numExp)throws TramitacionException;

}
