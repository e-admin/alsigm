package ieci.tecdoc.sgm.core.services.calendario;

import java.util.Date;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

/**
 * EJB sin estado que proporciona las operaciones sobre el calendario
 * 
 * @author IECISA
 * 
 */
public interface ServicioCalendario {
	
		/**
		 * Método que obtiene el calendario
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @throws CatalogoTramitesExcepcion
		 * @return Datos del calenario obtenido
		 */
		public Calendario obtenerCalendario(Entidad entidad) throws CalendarioExcepcion;
	
		/**
		 * Método que da de alta el calendario
		 * @param calendario Datos del calendario
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @throws CatalogoTramitesExcepcion
		 */
		public void nuevoCalendario(Calendario calendario, Entidad entidad) throws CalendarioExcepcion;
		
		/**
		 * Método que actualiza el calendario
		 * @param calendario Datos actualizados del calendario
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @throws CatalogoTramitesExcepcion
		 */
		public void actualizarCalendario(Calendario calendario, Entidad entidad) throws CalendarioExcepcion;
		
		/**
		 * Método que elimina el calendario
		 * @param borrarDias especifica si se eliminan los días festivos definidos para el calendario
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @throws CatalogoTramitesExcepcion
		 */
		public void eliminarCalendario(boolean borrarDias, Entidad entidad) throws CalendarioExcepcion;
		
		/**
		 * Método que obtiene un día del calendario
		 * @param id Identificador del dia del calendario
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @return Datos del día obtenido
		 * @throws CatalogoTramitesExcepcion
		 */
		public CalendarioDia obtenerDiaCalendario(String id, Entidad entidad) throws CalendarioExcepcion;
		
		/**
		 * Método que da de alta un día en el calendario
		 * @param dia Datos del día a dar de alta
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @throws CatalogoTramitesExcepcion
		 */
		public CalendarioDia nuevoDiaCalendario(CalendarioDia dia, Entidad entidad) throws CalendarioExcepcion;
		
		/**
		 * Método que actualiza un día del calendario
		 * @param dia Datos del día a actualizar
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @throws CatalogoTramitesExcepcion
		 */
		public void actualizarDiaCalendario(CalendarioDia dia, Entidad entidad) throws CalendarioExcepcion;
		
		/**
		 * Método que elimina un día del calendario
		 * @param id Identificador del día a eliminar
		 * @param entidad Entidad sobre la que realizar operaciones
		 * @throws CatalogoTramitesExcepcion
		 */
		public void eliminarDiaCalendario(String id, Entidad entidad) throws CalendarioExcepcion;
	
		/**
		 * Método que obtiene el listado de días de un calendario
		 * @param tipo Tipo de festivo (-1 todos, 0 fijos, 1 variables)
		 * @return Listado de días festivos (fijos y/o variables)
		 * @throws CatalogoTramitesExcepcion
		 */
		public CalendarioDias obtenerDiasCalendario(int tipo, Entidad entidad) throws CalendarioExcepcion;
	
		/**
		 * Método que obtiene el listado de días de un calendario
		 * @param tipo Tipo de festivo (-1 todos, 0 fijos, 1 variables)
		 * @return Listado de días festivos (fijos y/o variables)
		 * @throws CatalogoTramitesExcepcion
		 */
		public RetornoCalendario proximoLaborable(Date fecha, Entidad entidad) throws CalendarioExcepcion;
	
}