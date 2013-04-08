package ieci.tecdoc.sgm.calendario;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.calendario.Calendario;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioDia;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioDias;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioExcepcion;
import ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario;
import ieci.tecdoc.sgm.core.services.calendario.ServicioCalendario;
import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

import java.util.Date;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class ServicioCalendarioAdapter implements ServicioCalendario {

	private static final Logger logger = Logger.getLogger(ServicioCalendarioAdapter.class);
	
	/**
	 * Método que obtiene el calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 * @return Datos del calenario obtenido
	 */
	public Calendario obtenerCalendario(Entidad entidad) throws CalendarioExcepcion {
		try {	
			return getCalendarioServicio((ieci.tecdoc.sgm.calendario.util.CalendarioImpl)
					CalendarioCore.obtenerCalendario(entidad.getIdentificador())
				);
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al obtener el calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener el calendario.", e);
			throw new CalendarioExcepcion(CalendarioExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que da de alta el calendario
	 * @param calendario Datos del calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public void nuevoCalendario(Calendario calendario, Entidad entidad) throws CalendarioExcepcion {
		try {	
			CalendarioCore.nuevoCalendario(
					getCalendarioImpl(calendario), 
					entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al insertar el calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al insertar el calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Método que actualiza el calendario
	 * @param calendario Datos actualizados del calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public void actualizarCalendario(Calendario calendario, Entidad entidad) throws CalendarioExcepcion {
		try {	
			CalendarioCore.actualizarCalendario(
					getCalendarioImpl(calendario), 
					entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al actualizar el calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al actualizar el calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que elimina el calendario
	 * @param borrarDias Especifica si se deben borrar los días asociados al calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public void eliminarCalendario(boolean borrarDias, Entidad entidad) throws CalendarioExcepcion {
		try {	
			CalendarioCore.eliminarCalendario(borrarDias, entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al eliminar el calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al eliminar el calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que obtiene un día del calendario
	 * @param id Identificador del dia del calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @return Datos del día obtenido
	 * @throws CalendarioExcepcion
	 */
	public CalendarioDia obtenerDiaCalendario(String id, Entidad entidad) throws CalendarioExcepcion {
		try {	
			return getCalendarioDiaServicio((ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl)
					CalendarioCore.obtenerDiaCalendario(id, entidad.getIdentificador())
				);
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al obtener un dia del calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener un dia del calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que da de alta un día en el calendario
	 * @param dia Datos del día a dar de alta
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public CalendarioDia nuevoDiaCalendario(CalendarioDia dia, Entidad entidad) throws CalendarioExcepcion {
		try {	
			return getCalendarioDiaServicio((ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl)
				CalendarioCore.nuevoDiaCalendario(
					getCalendarioDiaImpl(dia), 
					entidad.getIdentificador()));
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al insertar un dia en el calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al insertar un dia en el calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que actualiza un día del calendario
	 * @param dia Datos del día a actualizar
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public void actualizarDiaCalendario(CalendarioDia dia, Entidad entidad) throws CalendarioExcepcion {
		try {	
			CalendarioCore.actualizarDiaCalendario(
					getCalendarioDiaImpl(dia), 
					entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al actualizar un dia en el calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al actualizar un dia en el calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que elimina un día del calendario
	 * @param id Identificador del día a eliminar
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public void eliminarDiaCalendario(String id, Entidad entidad) throws CalendarioExcepcion {
		try {	
			CalendarioCore.eliminarDiaCalendario(id, entidad.getIdentificador());
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al eliminar un dia del calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al eliminar un dia del calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que obtiene el listado de días de un calendario
	 * @param tipo Tipo de festivo (-1 todos, 0 fijos, 1 variables)
	 * @return Listado de días festivos (fijos y/o variables)
	 * @throws CalendarioExcepcion
	 */
	public CalendarioDias obtenerDiasCalendario(int tipo, Entidad entidad) throws CalendarioExcepcion {
		try {	
			return getCalendarioDiasServicio(
					CalendarioCore.obtenerDiasCalendario(tipo, entidad.getIdentificador())
				);
		} catch (ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion e) {
			logger.error("Error al obtener los dias del calendario.", e);
			throw getCalendarioExcepcion(e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener los dias del calendario.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	/**
	 * Método que obtiene el listado de días de un calendario
	 * @param tipo Tipo de festivo (-1 todos, 0 fijos, 1 variables)
	 * @return Listado de días festivos (fijos y/o variables)
	 * @throws CalendarioExcepcion
	 */
	public RetornoCalendario proximoLaborable(Date fecha, Entidad entidad) throws CalendarioExcepcion {
		try {
			return CalendarioCore.proximoLaborable(fecha,entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al obtener el siguiente laborable.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		} catch (Throwable e){
			logger.error("Error inesperado al obtener el siguiente laborable.", e);
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	private CalendarioExcepcion getCalendarioExcepcion(ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion poException){
		if(poException == null){
			return new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_CALENDAR_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new CalendarioExcepcion(Long.valueOf(cCodigo.toString()).longValue(), poException);
		
	}
   
	  
	private ieci.tecdoc.sgm.calendario.util.CalendarioImpl getCalendarioImpl(Calendario poCalendario) {
		ieci.tecdoc.sgm.calendario.util.CalendarioImpl oCalendario = new ieci.tecdoc.sgm.calendario.util.CalendarioImpl();
		if (poCalendario == null) 
			return oCalendario;
		
		StringBuffer dias = new StringBuffer();
		if (poCalendario.getDias() != null)
			for (int i=0; i<poCalendario.getDias().length; i++) {
				dias.append(poCalendario.getDias()[i]);
				if (i<poCalendario.getDias().length-1)
					dias.append(";");
			}
		oCalendario.setDias(dias.toString());
		oCalendario.setHoraInicio(poCalendario.getHoraInicio());
		oCalendario.setHoraFin(poCalendario.getHoraFin());
		oCalendario.setMinutoInicio(poCalendario.getMinutoInicio());
		oCalendario.setMinutoFin(poCalendario.getMinutoFin());
		
		return oCalendario;
	}
	
	
	private Calendario getCalendarioServicio(ieci.tecdoc.sgm.calendario.util.CalendarioImpl poCalendario) {
		Calendario oCalendario = new Calendario();
		if (poCalendario == null) 
			return oCalendario;
		
		StringTokenizer dias = null;
		String[] diasArray = null;
		if (poCalendario.getDias() != null) {
			dias = new StringTokenizer(poCalendario.getDias(), ";");
			diasArray = new String[dias.countTokens()];
			int i = 0;
			while (dias.hasMoreElements()) {
				diasArray[i++] = dias.nextToken();
			}
		}
		
		oCalendario.setDias(diasArray);
		oCalendario.setHoraInicio(poCalendario.getHoraInicio());
		oCalendario.setHoraFin(poCalendario.getHoraFin());
		oCalendario.setMinutoInicio(poCalendario.getMinutoInicio());
		oCalendario.setMinutoFin(poCalendario.getMinutoFin());
		
		return oCalendario;
	}
	
	
	private ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl getCalendarioDiaImpl(CalendarioDia poCalendarioDia) {
		ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl oCalendarioDia = new ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl();
		if (poCalendarioDia == null) 
			return oCalendarioDia;
		
		oCalendarioDia.setId(poCalendarioDia.getId());
		oCalendarioDia.setFecha(poCalendarioDia.getFecha());
		oCalendarioDia.setDescripcion(poCalendarioDia.getDescripcion());
		oCalendarioDia.setFijo(poCalendarioDia.getFijo() ? 0 : 1);
		
		return oCalendarioDia;
	}
	
		
	private CalendarioDia getCalendarioDiaServicio(ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl poCalendarioDia) {
		CalendarioDia oCalendarioDia = new CalendarioDia();
		if (poCalendarioDia == null) 
			return oCalendarioDia;
		
		oCalendarioDia.setId(poCalendarioDia.getId());
		oCalendarioDia.setFecha(poCalendarioDia.getFecha());
		oCalendarioDia.setDescripcion(poCalendarioDia.getDescripcion());
		oCalendarioDia.setFijo((poCalendarioDia.getFijo() == 0) ? true : false);
		
		return oCalendarioDia;
	}
	
	
	private CalendarioDias getCalendarioDiasServicio(ieci.tecdoc.sgm.calendario.util.CalendarioDias poCalendarioDias){
		CalendarioDias oCalendarioDias = new CalendarioDias();
		if (poCalendarioDias == null)
			return oCalendarioDias;
	   
		for(int i=0; i<poCalendarioDias.count(); i++)
			oCalendarioDias.add(getCalendarioDiaServicio((ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl)poCalendarioDias.get(i)));

		return oCalendarioDias;
	}
}
