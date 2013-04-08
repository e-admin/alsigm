package ieci.tecdoc.sgm.entidades.ws.server;

import java.util.List;

import javax.xml.soap.SOAPException;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.entidades.EntidadesException;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import org.apache.log4j.Logger;

public class EntidadesWebService {
	
	private static final Logger logger = Logger.getLogger(EntidadesWebService.class);
	
	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_ENTITIES;
	
	/**
	 * Método que da de alta una nueva entidad
	 * @param poEntidad Datos de la entidad
	 * @return Entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidad nuevaEntidad(Entidad poEntidad) {
		Entidad oEntidad = null;
		try {
			ServicioEntidades oService = getServicioEntidades();			
			oEntidad = getEntidadWS(oService.nuevaEntidad(getEntidadServicio(poEntidad)));
			return (Entidad)ServiciosUtils.completeReturnOK(oEntidad);
		} catch (EntidadesException e) {
			logger.error("Error en alta de entidad.", e);
			return (Entidad)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidad()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en alta de entidad.", e);
			return (Entidad)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidad()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado en alta de entidad.", e);
			return (Entidad)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Entidad()));
		}
	}
	
	/**
	 * Método que elimina una entidad
	 * @param poEntidad Datos de la entidad a eliminar
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public RetornoServicio eliminarEntidad(Entidad poEntidad) {
		try {
			ServicioEntidades oService = getServicioEntidades();			
			oService.eliminarEntidad(getEntidadServicio(poEntidad));
			return ServiciosUtils.createReturnOK();
		} catch (EntidadesException e) {
			logger.error("Error al eliminar una entidad.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al eliminar una entidad.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al eliminar una entidad.", e);
			return ServiciosUtils.createReturnError();
		}
	}
	
	/**
	 * Método que actualiza los datos de una entidad
	 * @param poEntidad Datos de la entidad a actualizar
	 * @return Entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidad actualizarEntidad(Entidad poEntidad) {
		Entidad oEntidad = null;
		try {
			ServicioEntidades oService = getServicioEntidades();			
			oEntidad = getEntidadWS(oService.actualizarEntidad(getEntidadServicio(poEntidad)));
			return (Entidad)ServiciosUtils.completeReturnOK(oEntidad);
		} catch (EntidadesException e) {
			logger.error("Error al actualizar una entidad.", e);
			return (Entidad)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidad()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al actualizar una entidad.", e);
			return (Entidad)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidad()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al actualizar una entidad.", e);
			return (Entidad)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Entidad()));
		}
	}
	
	/**
	 * Método que obtiene los datos de una entidad a partir de su código
	 * @param identificador Identificador de la entidad a obtener
	 * @return Datos de la entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidad obtenerEntidad(String identificador) {
		Entidad oEntidad = null;
		try {
			ServicioEntidades oService = getServicioEntidades();			
			oEntidad = getEntidadWS(oService.obtenerEntidad(identificador));
			return (Entidad)ServiciosUtils.completeReturnOK(oEntidad);
		} catch (EntidadesException e) {
			logger.error("Error al obtener una entidad.", e);
			return (Entidad)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidad()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener una entidad.", e);
			return (Entidad)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidad()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al obtener una entidad.", e);
			return (Entidad)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Entidad()));
		}
	}
	
	/**
	 * Método que obtiene un listado de entidad a partir de un criterio de búsqueda
	 * @param poCriterio Criterio de búsqueda
	 * @return Listado de entidades
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidades buscarEntidades(CriterioBusquedaEntidades poCriterio) {
		Entidades oEntidades = null;
		try {
			ServicioEntidades oService = getServicioEntidades();			
			oEntidades = getEntidadesWS(oService.buscarEntidades(getCriterioBusquedaEntidadesServicio(poCriterio)));
			return (Entidades)ServiciosUtils.completeReturnOK(oEntidades);
		} catch (EntidadesException e) {
			logger.error("Error al buscar entidades.", e);
			return (Entidades)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidades()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al buscar entidades.", e);
			return (Entidades)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidades()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al buscar entidades.", e);
			return (Entidades)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Entidades()));
		}
	}

	/**
	 * Método que obtiene un listado de todas las entidades del sistema
	 * @return Listado de entidades
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public Entidades obtenerEntidades() {
		Entidades oEntidades = null;
		try {
			ServicioEntidades oService = getServicioEntidades();			
			oEntidades = getEntidadesWS(oService.obtenerEntidades());
			return (Entidades)ServiciosUtils.completeReturnOK(oEntidades);
		} catch (EntidadesException e) {
			logger.error("Error al obtener entidades.", e);
			return (Entidades)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidades()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener entidades.", e);
			return (Entidades)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Entidades()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al obtener entidades.", e);
			return (Entidades)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Entidades()));
		}
	}
	
	
	/**
	 * Método que obtiene el identificador de la nueva entidad
	 * @return Identificador nueva entidad
	 * @throws EntidadesException En caso de producirse algún error
	 */
	public RetornoCadena obtenerIdentificadorEntidad() {
		Entidades oEntidades = null;
		try {
			ServicioEntidades oService = getServicioEntidades();			
			RetornoCadena oIdentificador = getRetornoCadenaWS(
					oService.obtenerIdentificadorEntidad()
				);
			return (RetornoCadena)ServiciosUtils.completeReturnOK(oIdentificador);
		} catch (EntidadesException e) {
			logger.error("Error al obtener identificador de entidad.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener identificador de entidad.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error inesperado al obtener identificador de entidad.", e);
			return (RetornoCadena)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoCadena()));
		}
	}
	
	
	private ServicioEntidades getServicioEntidades() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioEntidades();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioEntidades(sbImpl.toString());
		}
	}
	
	private ieci.tecdoc.sgm.core.services.entidades.Entidad getEntidadServicio(Entidad oEntidad) {
		if (oEntidad == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.entidades.Entidad poEntidad = new ieci.tecdoc.sgm.core.services.entidades.Entidad();
		
		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombreCorto(oEntidad.getNombreCorto());
		poEntidad.setNombreLargo(oEntidad.getNombreLargo());
		poEntidad.setCodigoINE(oEntidad.getCodigoINE());
		
		return poEntidad;
	}
	
	private Entidad getEntidadWS(ieci.tecdoc.sgm.core.services.entidades.Entidad oEntidad) {
		if (oEntidad == null)
			return null;
		
		Entidad poEntidad = new Entidad();
		
		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombreCorto(oEntidad.getNombreCorto());
		poEntidad.setNombreLargo(oEntidad.getNombreLargo());
		poEntidad.setCodigoINE(oEntidad.getCodigoINE());
		
		return poEntidad;
	}
	
	private ieci.tecdoc.sgm.core.services.entidades.CriterioBusquedaEntidades getCriterioBusquedaEntidadesServicio(CriterioBusquedaEntidades oCriterio) {
		if (oCriterio == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.entidades.CriterioBusquedaEntidades poCriterio = new ieci.tecdoc.sgm.core.services.entidades.CriterioBusquedaEntidades();
		
		poCriterio.setNombreCorto(oCriterio.getNombreCorto());
		poCriterio.setNombreLargo(oCriterio.getNombreLargo());
		
		return poCriterio;
	}
	
	private Entidades getEntidadesWS(List oEntidades) {
		if (oEntidades == null)
			return new Entidades();
		
		Entidades poEntidades = new Entidades();
		Entidad[] poEntidadesArray = new Entidad[oEntidades.size()];
		for(int i=0; i<oEntidades.size(); i++) {
			poEntidadesArray[i] = getEntidadWS((ieci.tecdoc.sgm.core.services.entidades.Entidad)oEntidades.get(i));
		}
		poEntidades.setEntidades(poEntidadesArray);
		
		return poEntidades;
	}
	
	private RetornoCadena getRetornoCadenaWS(String oIdentificador) {
		if (oIdentificador == null)
			return null;
		
		RetornoCadena poRetornoCadena = new RetornoCadena();
		
		poRetornoCadena.setValor(oIdentificador);
		
		return poRetornoCadena;
	}
}
