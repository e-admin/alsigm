package ieci.tecdoc.sgm.tram;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.services.mgr.CatalogoManager;
import ieci.tdw.ispac.services.mgr.CustodiaManager;
import ieci.tdw.ispac.services.mgr.TramitacionManager;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.TramitacionException;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento;
import ieci.tecdoc.sgm.tram.vo.SGMExpedienteVO;
import ieci.tecdoc.sgm.tram.vo.SGMInfoBExpedienteVO;
import ieci.tecdoc.sgm.tram.vo.SGMInfoBProcedimientoVO;
import ieci.tecdoc.sgm.tram.vo.SGMInfoFicheroVO;
import ieci.tecdoc.sgm.tram.vo.SGMInfoOcupacionVO;
import ieci.tecdoc.sgm.tram.vo.SGMProcedimientoVO;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Implementación del servicio de Tramitación de SIGEM.
 *
 */
public class SigemTramitacionServiceAdapter implements ServicioTramitacion {

	/** Logger de la clase. */
	private static final Logger logger = 
		Logger.getLogger(SigemTramitacionServiceAdapter.class);

	
	private void setOrganizationUserInfo(String idEntidad){
		OrganizationUserInfo info = new OrganizationUserInfo();
		info.setOrganizationId(idEntidad);
		info.getSpacPoolName();
		OrganizationUser.setOrganizationUserInfo(info);
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
	public InfoBProcedimiento[] getProcedimientos(String idEntidad, 
			int tipoProc, String nombre) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return SGMInfoBProcedimientoVO.getInstances(
					CatalogoManager.getInstance().getProcedimientos(tipoProc, nombre));
		} catch (Throwable e){
			logger.error("Error inesperado al obtener los procedimientos [tipoProc="
					+ tipoProc + ", nombre=" + nombre + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(String idEntidad, String[] idProcs) 
			throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);	
			return SGMInfoBProcedimientoVO.getInstances(
					CatalogoManager.getInstance().getProcedimientos(idProcs));
		} catch (Throwable e){
			logger.error("Error inesperado al obtener los procedimientos [idProcs="
					+ idProcs + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 */
	public Procedimiento getProcedimiento(String idEntidad, String idProc) 
			throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return SGMProcedimientoVO.getInstance(CatalogoManager.getInstance().getProcedimiento(idProc));
		} catch (Throwable e){
			logger.error("Error inesperado al obtener el procedimiento [idProc="
					+ idProc + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

    /**
     * Obtiene el contenido del documento.
     * @param guid GUID del documento
     * @return Contenido del documento.
     * @throws TramitacionException si ocurre algún error.
     */
    public byte [] getFichero(String idEntidad, String guid) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return CustodiaManager.getInstance().getFichero(guid);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener el contenido del fichero[guid="
					+ guid + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
    }

	/**
	 * Obtiene la información de origen e integridad del documento.
	 * @param guid GUID del documento.
	 * @return Información del documento.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public InfoFichero getInfoFichero(String idEntidad, String guid) 
			throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return SGMInfoFicheroVO.getInstance(CustodiaManager.getInstance().getInfoFichero(guid));
		} catch (Throwable e){
			logger.error("Error inesperado al obtener la información del fichero[guid="
					+ guid + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * @return Información de ocupación.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public InfoOcupacion getInfoOcupacion(String idEntidad) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return SGMInfoOcupacionVO.getInstance(CustodiaManager.getInstance().getInfoOcupacion());
		} catch (Throwable e){
			logger.error("Error inesperado al obtener la información de ocupación", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Elimina los documentos determinados por los GUIDs.
	 * @param guids Lista de GUIDs de los documentos.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public void eliminaFicheros(String idEntidad, String[] guids) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			CustodiaManager.getInstance().eliminaFicheros(guids);
		} catch (Throwable e){
			logger.error("Error inesperado al eliminar los ficheros [guids="
					+ guids + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
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
	public String[] getIdsExpedientes(String idEntidad, String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().getIdsExpedientes(idProc, 
					fechaIni, fechaFin, tipoOrd);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener los identificadores de expedientes [idProc="
					+ idProc + ", fechaIni=" + fechaIni + ", fechaFin=" 
					+ fechaFin + ", tipoOrd=" + tipoOrd + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 */
	public InfoBExpediente[] getExpedientes(String idEntidad, String[] idExps) 
			throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return SGMInfoBExpedienteVO.getInstances(TramitacionManager.getInstance().getExpedientes(idExps));
		} catch (Throwable e){
			logger.error("Error inesperado al obtener los expedientes [idExps="
					+ idExps + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(String idEntidad, String idExp) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return SGMExpedienteVO.getInstance(TramitacionManager.getInstance().getExpediente(idExp));
		} catch (Throwable e){
			logger.error("Error inesperado al obtener el expediente [idExp="
					+ idExp + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 */
	public void archivarExpedientes(String idEntidad, String[] idExps) throws TramitacionException {
		
		try {
			setOrganizationUserInfo(idEntidad);
			TramitacionManager.getInstance().archivarExpedientes(idExps);
		} catch (Throwable e){
			logger.error("Error inesperado al archivar los expedientes[idExps="
					+ StringUtils.join(idExps, ",") + "]", e);
			throw new TramitacionException(TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}
		
    /**
     * Iniciar un expediente.
     * 
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param datosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si el expediente se ha iniciado correctamente.
     * @throws TramitacionException Si se produce algún error al iniciar el expediente.
     */
    public boolean iniciarExpediente(String idEntidad, DatosComunesExpediente datosComunes, 
    			String datosEspecificos, DocumentoExpediente[] documentos) 
    		throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().iniciarExpediente(
					toInternalForm(datosComunes), datosEspecificos, toInternalForm(documentos));
		} catch (Throwable e){
			logger.error("Error inesperado al iniciar el expediente [datosComunes="
					+ datosComunes + ", datosEspecificos=" + datosEspecificos 
					+ ", documentos=" + documentos + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
    }
    
    
    /**
     * Iniciar un expediente.
     * 
     * @param commonData Datos comunes para todos los expedientes.
     * @param specificDataXML XML con los datos específicos del expediente.
     * @param documents Lista de documentos asociados al expediente.
     * @param initSystem Sistema externo desde el que se inicia el expediente 
     * @return Numero de expediente creado
     * @throws ISPACException Si se produce algún error al iniciar el expediente.
     */
    public String iniciarExpediente(String idEntidad, DatosComunesExpediente datosComunes, 
			String datosEspecificos, DocumentoExpediente[] documentos, String initSystem) 
		throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().iniciarExpediente(
					toInternalForm(datosComunes), datosEspecificos, toInternalForm(documentos),initSystem);
		} catch (Throwable e){
			logger.error("Error inesperado al iniciar el expediente [datosComunes="
					+ datosComunes + ", datosEspecificos=" + datosEspecificos 
					+ ", documentos=" + documentos + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
    }

    
    
    
    /**
     * Añade documentos al trámite de un expediente.
     * @param numExp Número de expediente.
     * @param numReg Número de registro de entrada.
     * @param fechaReg Fecha de registro de entrada.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     * @throws TramitacionException Si se produce algún error.
     */
    public boolean anexarDocsExpediente(String idEntidad, String numExp, String numReg, 
    			Date fechaReg, DocumentoExpediente[] documentos) 
    		throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().anexarDocsExpediente(
					numExp, numReg, fechaReg, toInternalForm(documentos));
		} catch (Throwable e){
			logger.error("Error inesperado al anexar documentos al expediente [numExp="
					+ numExp + ", numReg=" + numReg + ", fechaReg=" + fechaReg 
					+ ", documentos=" + documentos + "]", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
    	
    }
    
	private static ieci.tdw.ispac.services.dto.DatosComunesExpediente toInternalForm(DatosComunesExpediente obj) {
		ieci.tdw.ispac.services.dto.DatosComunesExpediente res = null;
		
		if (obj != null) {
			res = new ieci.tdw.ispac.services.dto.DatosComunesExpediente();

			res.setIdOrganismo(obj.getIdOrganismo());
			res.setTipoAsunto(obj.getTipoAsunto());
			res.setNumeroRegistro(obj.getNumeroRegistro());
			res.setFechaRegistro(obj.getFechaRegistro());
			res.setInteresados(toInternalForm(obj.getInteresados()));
		}
		
		return res;
	}

    private static ieci.tdw.ispac.services.dto.InteresadoExpediente[] toInternalForm(InteresadoExpediente[] objs) {
    	ieci.tdw.ispac.services.dto.InteresadoExpediente[] res = null;
		
		if (objs != null) {
			res = new ieci.tdw.ispac.services.dto.InteresadoExpediente[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = toInternalForm(objs[i]);
			}
		}
		
		return res;
    }

	private static ieci.tdw.ispac.services.dto.InteresadoExpediente toInternalForm(InteresadoExpediente obj) {
		ieci.tdw.ispac.services.dto.InteresadoExpediente res = null;
		
		if (obj != null) {
			res = new ieci.tdw.ispac.services.dto.InteresadoExpediente();
			res.setThirdPartyId(obj.getThirdPartyId());
			res.setIndPrincipal(obj.getIndPrincipal());
			res.setNifcif(obj.getNifcif());
			res.setName(obj.getName());
			res.setPostalAddress(obj.getPostalAddress());
			res.setPostalCode(obj.getPostalCode());
			res.setPlaceCity(obj.getPlaceCity());
			res.setRegionCountry(obj.getRegionCountry());
			res.setTelematicAddress(obj.getTelematicAddress());
			res.setNotificationAddressType(obj.getNotificationAddressType());
			res.setPhone(obj.getPhone());
			res.setMobilePhone(obj.getMobilePhone());
		}
		
		return res;
	}

    private static ieci.tdw.ispac.services.dto.DocumentoExpediente[] toInternalForm(DocumentoExpediente[] objs) {
    	ieci.tdw.ispac.services.dto.DocumentoExpediente[] res = null;
		
		if (objs != null) {
			res = new ieci.tdw.ispac.services.dto.DocumentoExpediente[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = toInternalForm(objs[i]);
			}
		}
		
		return res;
    }
    
	private static ieci.tdw.ispac.services.dto.DocumentoExpediente toInternalForm(DocumentoExpediente obj) {
		ieci.tdw.ispac.services.dto.DocumentoExpediente res = null;
		
		if (obj != null) {
			res = new ieci.tdw.ispac.services.dto.DocumentoExpediente();
			res.setId(obj.getId());
			res.setCode(obj.getCode());
			res.setName(obj.getName());
			res.setExtension(obj.getExtension());
			res.setLenght(obj.getLenght());
			res.setContent(obj.getContent());
		}
		
		return res;
	}

	public boolean cambiarEstadoAdministrativo(String idEntidad, String numExp,
			String estadoAdm) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance()
					.cambiarEstadoAdministrativo(numExp, estadoAdm);
		} catch (Throwable e) {
			logger.error("Error inesperado al cambiar estado administrativo ("
					+ estadoAdm + ") del expediente '" + numExp + "'", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public boolean moverExpedienteAFase(String idEntidad, String numExp,
			String idFaseCatalogo) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().moverExpedienteAFase(
					numExp, idFaseCatalogo);
		} catch (Throwable e) {
			logger.error("Error inesperado al mover el expediente '" + numExp
					+ "' a la fase cuyo identificador en catalogo es '"
					+ idFaseCatalogo + "'", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String busquedaAvanzada(String idEntidad, String nombreGrupo,
			String nombreFrmBusqueda, String xmlBusqueda, int dominio)
			throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().busquedaAvanzada(nombreGrupo, nombreFrmBusqueda, xmlBusqueda, dominio);
		} catch (Throwable e) {
			logger.error("Error al realizar busqueda avanzada", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public int establecerDatosRegistroEntidad(String idEntidad,
			String nombreEntidad, String numExp, String xmlDatosEspecificos)
			throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().establecerDatosRegistroEntidad(nombreEntidad, numExp, xmlDatosEspecificos);
		} catch (Throwable e) {
			logger.error("Error al establecer datos de registro en la entidad '" + nombreEntidad + "'", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String obtenerRegistroEntidad(String idEntidad,
			String nombreEntidad, String numExp, int idRegistro)
			throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().obtenerRegistroEntidad(nombreEntidad, numExp, idRegistro);
		} catch (Throwable e) {
			logger.error("Error al obtener datos de registro de la entidad '" + nombreEntidad + "'", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String obtenerRegistrosEntidad(String idEntidad,
			String nombreEntidad, String numExp) throws TramitacionException {
		try {
			setOrganizationUserInfo(idEntidad);
			return TramitacionManager.getInstance().obtenerRegistrosEntidad(nombreEntidad, numExp);
		} catch (Throwable e) {
			logger.error("Error al obtener registros de la entidad '" + nombreEntidad + "'", e);
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, e);
		}
	}

}