package ieci.tecdoc.sgm.core.services.notificaciones;

import java.util.Date;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

public interface ServicioNotificaciones {
	
	/**
	 * Método que actualiza el estado de las notificaciones
	 * @throws ServicioNotificacionesException
	 */
	 public void actualizaEstados(Entidad entidad) throws ServicioNotificacionesException;

	/**
	 * Método que actualiza el estado de una notificacion
	 * @param numeroExpediente Número de expediente de la notificacion
	 * @param estado Estado nuevo de la notificacion
	 * @param fechaActualizacion Fecha en la que se actualiza la notificacion
	 * @param nifDestino Nif del destinatario de la notificación
	 * @throws ServicioNotificacionesException
	 */
	public void actualizaEstado(String numeroExpediente, Integer estado, 
    		Date fechaActualizacion, String nifDestino, String notiId, Entidad entidad) throws ServicioNotificacionesException;

	 /**
	  * Método que recupera un documento a partir de unos parámetros de b´suqueda
	  * @param poCriterio Parámetros de búsqueda
	  * @return Datos del documento
	  * @throws ServicioNotificacionesException
	  */
	 public InfoDocumento recuperaDocumento(CriterioBusquedaDocumentos poCriterio, Entidad entidad)	throws ServicioNotificacionesException;
	 
	 /**
	  * Método que devuelve el detalle de una notificación
	  * @param poIdentificador Identificador de la notificación
	  * @return Datos de la notificación
	  * @throws ServicioNotificacionesException
	  */
	 public Notificacion detalleNotificacion (IdentificadorNotificacion poIdentificador, Entidad entidad) throws ServicioNotificacionesException;
	 public Notificacion detalleNotificacionByNotiId (String notiId, Entidad entidad) throws ServicioNotificacionesException;
	 
	 /**
	  * Método que devuelve una lista de notificaciones a partir de unos parámetros de búsqueda
	  * @param poCriterio Parámetros de búsqueda
	  * @param pbConDetalle Determina el detalle de los datos devueltos (true para mayor detalle)
	  * @return Lista de notificaciones
	  * @throws ServicioNotificacionesException
	  */
	 public Notificaciones consultarNotificaciones (CriterioBusquedaNotificaciones poCriterio, boolean pbConDetalle, Entidad entidad) throws ServicioNotificacionesException;
	 
	 /**
	  * Método que obtiene el estado de una notificación
	  * @param psIdNotificacion Identificador de la notificación
	  * @return Estado de la notificación
	  * @throws ServicioNotificacionesException
	  */
	 public EstadoNotificacion obtenerEstado(String psIdNotificacion, Entidad entidad) throws ServicioNotificacionesException;
	 
	 /**
	  * Método que obtiene los datos de un estado de notificación
	  * @param idEstado Identificador del estado de notificación
	  * @return Estado de una notificacion
	  * @throws ServicioNotificacionesException
	  */
	 public EstadoNotificacionBD obtenerEstadoBD(Integer idEstado, Entidad entidad) throws ServicioNotificacionesException;
	 
	 /**
	  * Método que da de alta una notificación
	  * @param poNotificacion Datos de la notificación
	  * @return Identificador de la notificación
	  * @throws ServicioNotificacionesException
	  */
	 public IdentificadorNotificacion altaNotificacion(Notificacion poNotificacion, Entidad entidad) throws ServicioNotificacionesException;
	
}
