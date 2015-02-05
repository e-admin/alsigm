package ieci.tecdoc.sgm.calendario;

import ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion;
import ieci.tecdoc.sgm.calendario.util.Calendario;
import ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl;
import ieci.tecdoc.sgm.calendario.util.CalendarioDias;
import ieci.tecdoc.sgm.calendario.util.CalendarioImpl;
import ieci.tecdoc.sgm.calendario.util.CalendarioManager;
import ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;


/**
 * EJB sin estado que proporciona las operaciones sobre el catálogo de trámites
 * 
 * @author IECISA
 * 
 */
public class CalendarioCore {
  
	//private static final Logger logger = Logger.getLogger(CalendarioCore.class);

	static private int TODOS_FESTIVOS=-1; 
	static protected int SOLO_FESTIVOS_FIJOS=0; 
	static protected int SOLO_FESTIVOS_VARIABLES=1;
	
	static private int ORDENADO_ASC_POR_DESCRIPCION=0; 
	static private int ORDENADO_ASC_POR_FECHA=1;
	
	/**
	 * Método que obtiene el calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 * @return Datos del calenario obtenido
	 */
	public static CalendarioImpl obtenerCalendario(String entidad) throws CalendarioExcepcion {
		return (CalendarioImpl)CalendarioManager.getCalendario(entidad);
	}
	
	/**
	 * Método que da de alta el calendario
	 * @param calendario Datos del calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public static void nuevoCalendario(CalendarioImpl calendario, String entidad) throws CalendarioExcepcion {
		CalendarioManager.addCalendario(calendario,entidad);
	}

	/**
	 * Método que actualiza el calendario
	 * @param calendario Datos actualizados del calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public static void actualizarCalendario(CalendarioImpl calendario, String entidad) throws CalendarioExcepcion {	
		CalendarioManager.updateCalendario(calendario,entidad);
	}
	
	/**
	 * Método que elimina el calendario
	 * @param borrarDias Especifica si se deben borrar los días asociados al calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public static void eliminarCalendario(boolean borrarDias, String entidad) throws CalendarioExcepcion {		
			CalendarioManager.deleteCalendario(borrarDias, entidad);
	}
	
	/**
	 * Método que obtiene un día del calendario
	 * @param id Identificador del dia del calendario
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @return Datos del día obtenido
	 * @throws CalendarioExcepcion
	 */
	public static CalendarioDiaImpl obtenerDiaCalendario(String id, String entidad) throws CalendarioExcepcion {
		return (CalendarioDiaImpl)CalendarioManager.getDiaCalendario(id, entidad);
	}
	
	/**
	 * Método que da de alta un día en el calendario
	 * @param dia Datos del día a dar de alta
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public static CalendarioDiaImpl nuevoDiaCalendario(CalendarioDiaImpl dia, String entidad) throws CalendarioExcepcion {
		return (CalendarioDiaImpl)CalendarioManager.addDiaCalendario(dia,entidad);
	}
	
	/**
	 * Método que actualiza un día del calendario
	 * @param dia Datos del día a actualizar
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public static void actualizarDiaCalendario(CalendarioDiaImpl dia, String entidad) throws CalendarioExcepcion {
		CalendarioManager.updateDiaCalendario(dia,entidad);
	}
	
	/**
	 * Método que elimina un día del calendario
	 * @param id Identificador del día a eliminar
	 * @param entidad Entidad sobre la que realizar operaciones
	 * @throws CalendarioExcepcion
	 */
	public static void eliminarDiaCalendario(String id, String entidad) throws CalendarioExcepcion {
		CalendarioManager.deleteDiaCalendario(id, entidad);
	}
	
	/**
	 * Método que obtiene el listado de días de un calendario
	 * @param tipo Tipo de festivo (-1 todos, 0 fijos, 1 variables)
	 * @return Listado de días festivos (fijos y/o variables)
	 * @throws CalendarioExcepcion
	 */
	public static CalendarioDias obtenerDiasCalendario(int tipo, String entidad) throws CalendarioExcepcion {
		return CalendarioManager.getDiasCalendario(tipo, ORDENADO_ASC_POR_DESCRIPCION, entidad);
	}
	
	/**
	 * Método que obtiene el listado de días de un calendario
	 * @param tipo Tipo de festivo (-1 todos, 0 fijos, 1 variables)
	 * @return Listado de días festivos (fijos y/o variables)
	 * @throws CalendarioExcepcion
	 */
	public static RetornoCalendario proximoLaborable(Date fecha, String entidad) throws CalendarioExcepcion {
		RetornoCalendario retorno = new RetornoCalendario();
		Calendario calendario = null;
		CalendarioDias dias = null;
		
		try {
			calendario = CalendarioManager.getCalendario(entidad);
		} catch (CalendarioExcepcion e) {
			calendario = new CalendarioImpl();
		}
		
		try {
			dias = CalendarioManager.getDiasCalendario(TODOS_FESTIVOS, ORDENADO_ASC_POR_FECHA, entidad);
		} catch (CalendarioExcepcion e) {
			dias = new CalendarioDias();
		}
	
		boolean esDiaLaborable = false;
		boolean esDiaFestivo = false;
		int estaEnHora = 0;
		Date fechaTrabajo = fecha;
		
		while (true) {
			//Comprobar si la fecha está dentro del rango de horas
			estaEnHora = estaEnHora(fechaTrabajo,
						componerHora(calendario.getHoraInicio(), calendario.getMinutoInicio()),
						componerHora(calendario.getHoraFin(), calendario.getMinutoFin()));
			
			if (estaEnHora < 1) { //Si la hora esta en el rango o antes del inicio del rango
				if (estaEnHora < 0) { //Si está por debajo del rango se establece la fecha con la de inicio del rango
					fechaTrabajo=getDateInicioHorarioOficina(fechaTrabajo,calendario);
					
					//fechaTrabajo.setHours(new Integer(calendario.getHoraInicio()).intValue());
					//fechaTrabajo.setMinutes(new Integer(calendario.getMinutoInicio()).intValue());
					//fechaTrabajo.setSeconds(0);
				}
				//Se comprueba si es laborable el dia
				esDiaLaborable = esDiaLaborable(fechaTrabajo, calendario.getDias());
				
				if (esDiaLaborable) { //Si es dia laborable
					esDiaFestivo = esDiaFestivo(fechaTrabajo, dias);
					
					if (!esDiaFestivo) { //Si no es día festivo
						retorno.setLaborable(fechaTrabajo.compareTo(fecha) == 0);
						retorno.setProximo(fechaTrabajo);
						return retorno;
					} else { //Si es día festivo
						fechaTrabajo = incrementarFecha(fechaTrabajo,calendario);
						if (estaEnHora == 0) {
							// TODO ¿Tiene algun sentido esta parte?
//							if (componerHora(calendario.getHoraInicio(), calendario.getMinutoInicio()) == null || 
//								componerHora(calendario.getHoraFin(), calendario.getMinutoFin()) == null) {
//								Calendar calFechaTrabajo=Calendar.getInstance();
//								calFechaTrabajo.setTime(fechaTrabajo);
//								calFechaTrabajo.set(Calendar.HOUR_OF_DAY, 0);
//								calFechaTrabajo.set(Calendar.MINUTE, 0);
//								calFechaTrabajo.set(Calendar.SECOND, 0);
//								fechaTrabajo=calFechaTrabajo.getTime();
//								//fechaTrabajo.setHours(0);
//								//fechaTrabajo.setMinutes(0);
//								//fechaTrabajo.setSeconds(0);
//							}
						}
					}
				} else { //Si no es día laborable
					fechaTrabajo = incrementarFecha(fechaTrabajo,calendario);
				}
			} else { //Si la hora es de después de finalizar el rango
				//fechaTrabajo.setHours(new Integer(calendario.getHoraInicio()).intValue());
				//fechaTrabajo.setMinutes(new Integer(calendario.getMinutoInicio()).intValue());
				//fechaTrabajo.setSeconds(0);
				//fechaTrabajo=getDateInicioHorarioOficina(fechaTrabajo,calendario);
				fechaTrabajo = incrementarFecha(fechaTrabajo,calendario);
			}
		}
	}
	   
	private static Date incrementarFecha(Date fecha,Calendario calendarioSIGEM) {
		fecha=getDateInicioHorarioOficina(fecha,calendarioSIGEM);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		//calendar.set(fecha.getYear() + 1900, 
		//			fecha.getMonth(), 
		//			fecha.getDate(), 
		//			fecha.getHours(), 
		//			fecha.getMinutes(), 
		//			fecha.getSeconds());
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	private static Date getDateInicioHorarioOficina(Date fecha,Calendario calendarioSIGEM){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		
		int hour = 0;
		int minute = 0;
		
		if (!calendarioSIGEM.getHoraInicio().equals("")) {
			hour = new Integer(calendarioSIGEM.getHoraInicio()).intValue();
		}
		if (!calendarioSIGEM.getMinutoInicio().equals("")) {
			minute = new Integer(calendarioSIGEM.getMinutoInicio()).intValue();
		}
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar.getTime();
	}
	
	private static String componerHora (String hora, String minutos) {
		if (hora == null || minutos == null || hora.equals("") || minutos.equals(""))
			return null;
		return hora + ":" + minutos;
	}

	private static boolean esDiaLaborable(Date fecha, String dias) throws CalendarioExcepcion {
		try {
			if (dias == null || dias.equals(""))
				return true;
			
			StringTokenizer st = new StringTokenizer(dias, ";");
			Calendar cal=Calendar.getInstance();
			cal.setTime(fecha);
			cal.get(Calendar.DAY_OF_WEEK);
			int diaSemana = ((cal.get(Calendar.DAY_OF_WEEK) + 5) % 7);
			while (diaSemana > 0) {
				st.nextElement();
				diaSemana--;
			}
			return st.nextElement().equals("0") ? true : false;
		} catch (Exception e) {
			return true;
		}
	}
	
	private static boolean esDiaFestivo(Date fecha, ieci.tecdoc.sgm.calendario.util.CalendarioDias dias) throws CalendarioExcepcion {
		try {
			if (dias == null || dias.count() == 0)
				return false;
			
			Calendar cal=Calendar.getInstance();
			cal.setTime(fecha);
			
			String strDia = (((cal.get(Calendar.MONTH) + 1) > 9) ? "" : "0") + (cal.get(Calendar.MONTH) + 1) + 
							"/" + ((cal.get(Calendar.DAY_OF_MONTH) > 9) ? "" : "0") + cal.get(Calendar.DAY_OF_MONTH);
			ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl dia = null;
			for(int i=0; i<dias.count(); i++) {
				dia = (ieci.tecdoc.sgm.calendario.util.CalendarioDiaImpl)dias.get(i);
				if (strDia.equals(dia.getFecha()))
					return true;
				else if (strDia.compareTo(dia.getFecha()) < 0)
					return false;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	private static int estaEnHora(Date fecha, String horaInicio, String horaFin) throws CalendarioExcepcion {
		try {
			if (horaInicio == null || horaInicio.equals("") ||
					horaFin == null || horaFin.equals(""))
				return 0;
			
			StringBuffer hora = new StringBuffer();
			
			Calendar cal=Calendar.getInstance();
			cal.setTime(fecha);
			hora.append(((cal.get(Calendar.HOUR_OF_DAY)) > 9) ? "" : "0").append(cal.get(Calendar.HOUR_OF_DAY));
			hora.append(":").append((cal.get(Calendar.MINUTE) > 9) ? "" : "0").append(cal.get(Calendar.MINUTE));
			
			if (hora.toString().compareTo(horaInicio) < 0) //Antes de la hora de inicio
				return -1;
			else if (hora.toString().compareTo(horaFin) > 0) //Después de la hora de finalización
				return 1;
			else											//Dentro del rango de horas
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}
}
