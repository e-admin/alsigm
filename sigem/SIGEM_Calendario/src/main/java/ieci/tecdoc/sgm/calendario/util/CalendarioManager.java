package ieci.tecdoc.sgm.calendario.util;

import ieci.tecdoc.sgm.calendario.exception.CalendarioCodigosError;
import ieci.tecdoc.sgm.calendario.exception.CalendarioExcepcion;
import ieci.tecdoc.sgm.calendario.util.database.CalendarioDatos;
import ieci.tecdoc.sgm.calendario.util.database.CalendarioDiaDatos;

import org.apache.log4j.Logger;

/**
 * Gestor de calendarios que implementa operaciones de recuperación, almacenaje,
 * actualización y eliminación de días
 *
 */
public class CalendarioManager {
  
	private static final Logger logger = Logger.getLogger(CalendarioManager.class);
  
  //private static final boolean isDebugeable = true;
 
  protected CalendarioManager() {
  }

  /**
   * Recupera la información de un calendario
   * @param hookId Identificador del calendario.
   * @return Calendario El calendario.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static Calendario getCalendario(String entidad) throws CalendarioExcepcion {
     CalendarioDatos calendario = new CalendarioDatos();

     logger.debug("Get Calendario <-- entidadId: " + entidad);
     
     try {
    	 calendario.load(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al obtener el calendario [getCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR);
     } catch (Exception exc) {
    	 logger.error("Error al obtener el calendario [getCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR);
     }
     
     return calendario;
  }


  /**
   * Añade un calendario al catálogo. 
   * @param calendar Información del calendario.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static void addCalendario(Calendario calendar, String entidad) throws CalendarioExcepcion {
	  CalendarioDatos data = new CalendarioDatos(calendar);

     logger.debug("Add Calendario <-- Calendario: " + calendar.toString());
     
     try {
    	 data.insert(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al insertar calendario [addCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_ADD_CALENDAR);
     } catch (Exception exc) {
    	 logger.error("Error al insertar calendario [addCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_ADD_CALENDAR);
     }
  }

  
  /**
   * Elimina el calendario.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static void deleteCalendario(boolean borrarDias, String entidad) throws CalendarioExcepcion {
	 CalendarioDatos calendario = new CalendarioDatos();
	 CalendarioDiaDatos calendarioDia = new CalendarioDiaDatos();
	 
     logger.debug("Delete Calendario <-- entidad: " + entidad);
     
     try{
    	 calendario.delete(entidad);
    	 if (borrarDias) {
    		 calendarioDia.setId(-1);
    		 calendarioDia.delete(entidad);
    	 }
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al eliminar calendario [deleteCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 if (exc.getErrorCode() == CalendarioCodigosError.EC_DELETE_CALENDAR_DAY)
    		 throw new CalendarioExcepcion(CalendarioCodigosError.EC_DELETE_CALENDAR_DAY);
    	 else throw new CalendarioExcepcion(CalendarioCodigosError.EC_DELETE_CALENDAR);
     } catch (Exception exc) {
    	 logger.error("Error al eliminar calendario [deleteCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_DELETE_CALENDAR);
     }
  }

  
  /**
   * Actualiza el calendario que se pasa como parámetro. 
   * @param calendar Calendario con los nuevos datos.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static void updateCalendario(Calendario calendar, String entidad) throws CalendarioExcepcion {
	  CalendarioDatos calendario = new CalendarioDatos(calendar);

     logger.debug("Update Calendario <-- Calendario: " + calendar.toString());

     try {
    	 calendario.update(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al actualizar el calendario [updateCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_UPDATE_CALENDAR);
     } catch (Exception exc) {
    	 logger.error("Error al actualizar el calendario [updateCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_UPDATE_CALENDAR);
     }
  }

  /**
   * Recupera la información de un dia del calendario
   * @param id Identificador del dia.
   * @return CalendarioDia El dia festivo.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static CalendarioDia getDiaCalendario(String id, String entidad) throws CalendarioExcepcion {
     CalendarioDiaDatos calendarioDia = new CalendarioDiaDatos();

     logger.debug("Get Dia Calendario <-- id: " + id);
     
     try {
    	 calendarioDia.setId(new Integer(id).intValue());
    	 calendarioDia.load(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al obtener el dia del calendario [getDiaCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAY);
     } catch (Exception exc) {
    	 logger.error("Error al obtener el dia del calendario [getDiaCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAY);
     }
     
     return calendarioDia;
  }


  /**
   * Añade un dia al calendario. 
   * @param calendarDay Información del dia del calendario.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static CalendarioDia addDiaCalendario(CalendarioDia calendarDay, String entidad) throws CalendarioExcepcion {
	 CalendarioDiaDatos data = new CalendarioDiaDatos(calendarDay);

     logger.debug("Add Calendario <-- Calendario: " + calendarDay.toString());
     
     try {
    	 data.setId(getCalendarioDiaId(entidad));
    	 data.insert(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al insertar dia al calendario [addDiaCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_ADD_CALENDAR_DAY);
     } catch (Exception exc) {
    	 logger.error("Error al insertar dia al calendario [addDiaCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_ADD_CALENDAR_DAY);
     }
     return data;
  }

  /**
   * Obtiene un nuevo id para el día de calendario. 
   * @return Siguiente identificador
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  private static int getCalendarioDiaId(String entidad) throws CalendarioExcepcion {
	 CalendarioDiaDatos data = new CalendarioDiaDatos();

     logger.debug("Get Id Calendar Day <-- entidad: " + entidad);
     
     try {
    	 data.get(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al obtener identificador del dia al calendario [getCalendarioDiaId][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAY_ID);
     } catch (Exception exc) {
    	 logger.error("Error al obtener identificador del dia al calendario [getCalendarioDiaId][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAY_ID);
     }
     return (data.getId() >= 0) ? (data.getId() + 1) : 0;
  }
  
  /**
   * Elimina el calendario.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static void deleteDiaCalendario(String id, String entidad) throws CalendarioExcepcion {
	 CalendarioDiaDatos calendarioDia = new CalendarioDiaDatos();
	 
     logger.debug("Delete Dia Calendario <-- id: " + id);
     
     try{
    	 calendarioDia.setId(new Integer(id).intValue());
    	 calendarioDia.delete(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al eliminar dia del calendario [deleteDiaCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_DELETE_CALENDAR_DAY);
     } catch (Exception exc) {
    	 logger.error("Error al eliminar dia del calendario [deleteDiaCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_DELETE_CALENDAR_DAY);
     }
  }

  
  /**
   * Actualiza el dia del calendario que se pasa como parámetro. 
   * @param calendarDay CDia del calendario con los nuevos datos.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static void updateDiaCalendario(CalendarioDia calendarDay, String entidad) throws CalendarioExcepcion {
	  CalendarioDiaDatos calendario = new CalendarioDiaDatos(calendarDay);

     logger.debug("Update Dia Calendario <-- Dia Calendario: " + calendarDay.toString());

     try {
    	 calendario.update(entidad);
     } catch (CalendarioExcepcion exc) {
    	 logger.error("Error al actualizar el dia del calendario [updateDiaCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_UPDATE_CALENDAR_DAY);
     } catch (Exception exc) {
    	 logger.error("Error al actualizar el dia del calendario [updateDiaCalendario][Excepcion]", exc.fillInStackTrace());
    	 throw new CalendarioExcepcion(CalendarioCodigosError.EC_UPDATE_CALENDAR_DAY);
     }
  }
 
  /**
   * Recupera la lista de dias del calendario.
   * @param type Tipo de festivo (-1 todos, 0 fijos, 1 variables)
   * @param order Tipo de ordenacion (0 descripcion, 1 fecha)
   * @return La lista mencionada.
   * @throws CalendarioExcepcion Si se produce algún error.
   */
  public static CalendarioDias getDiasCalendario(int type, int order, String entidad) throws CalendarioExcepcion {
	  CalendarioDias dias = null;

    logger.debug("getDiasCalendario <-- tipo: " + type);
     
    try {
    	dias = CalendarioDiaDatos.getCalendarDays(type, order, entidad);
    } catch (CalendarioExcepcion exc) {
    	logger.error("Error obtener dias del calendario [getDiasCalendario][CalendarioExcepcion]", exc.fillInStackTrace());
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAYS);
    } catch (Exception exc) {
    	logger.error("Error obtener dias del calendario [getDiasCalendario][Excepcion]", exc.fillInStackTrace());
        throw new CalendarioExcepcion(CalendarioCodigosError.EC_GET_CALENDAR_DAYS);
    }
     
    return dias;
  }
}

