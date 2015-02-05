package ieci.tecdoc.sgm.core.services.consulta;

import java.util.Date;

import ieci.tecdoc.sgm.core.services.dto.Entidad;

public interface ServicioConsultaExpedientes {

	/**
	 * Método que obtien los expedientes pertenecientes a un usuario
	 * @param NIF Documento de identidad del usuario
	 * @return Lista de expedientes del usuario dados de alta en el sistema
	 * @throws ConsultaExpedientesException
	 */
	public Expedientes consultarExpedientesNIF(String NIF, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que realiza un búsqueda de expedientes a partir de determinados parámetros de búsqueda
	 * @param poCriterio Parámetros de búsqueda
	 * @return Lista de expedeintes resultantes de la búsqueda
	 * @throws ConsultaExpedientesException
	 */
	public Expedientes consultarExpedientes(CriterioBusquedaExpedientes poCriterio, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que obtiene la url del enlace de aportación de documentos (subsanación)
	 * @return URL del enlace de subsanación
	 * @throws ConsultaExpedientesException
	 */
	public String obtenerURLAportacionExpedientes() throws ConsultaExpedientesException;

	/**
	 * Método que obtiene la url del enlace de notificación
	 * @return URL del enlace de notificación
	 * @throws ConsultaExpedientesException
	 */
	public String obtenerURLNotificacionExpedientes () throws ConsultaExpedientesException;

	/**
	 * Método que obtiene la url del enlace de pago de tasas
	 * @return URL del enlace de pago de tasas
	 * @throws ConsultaExpedientesException
	 */
	public String obtenerURLPagoTasas() throws ConsultaExpedientesException;

	/**
	 * Método que obtiene los datos detallados de un expediente
	 * @param numeroExpediente Identificador de expedeinte
	 * @return Datos del expediente
	 * @throws ConsultaExpedientesException
	 */
	public Expediente obtenerDetalle(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que obtiene el histórico de hitos de un expediente
	 * @param numeroExpediente Identificador de expediente
	 * @return Listado de hitos históricos
	 * @throws ConsultaExpedientesException
	 */
	public HitosExpediente obtenerHistoricoExpediente(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que obtiene le hito actual de un expediente
	 * @param numeroExpediente Identificador de expediente
	 * @return Datos del hito actual del expediente
	 * @throws ConsultaExpedientesException
	 */
	public HitoExpediente obtenerHitoEstado(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que obtien los ficheros asociados a un hito
	 * @param guidHito Identificador de hito
	 * @return Listado de ficheros asociados al hito
	 * @throws ConsultaExpedientesException
	 */
	public FicherosHito obtenerFicherosHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que obtiene los ficheros asociados a un listado de hitos
	 * @param hitos Listado de hitos de los que obtener sus ficheros
	 * @return Listado de ficheros asociados a los hitos
	 * @throws ConsultaExpedientesException
	 */
	public FicherosHitos obtenerFicherosHitos(HitosExpediente hitos, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que da de alta un expediente
	 * @param expediente Datos del expediente
	 * @param interesado Datos del interesado (si es null no se asocia al expediente un intereado)
	 * @throws ConsultaExpedientesException
	 */
	public void nuevoExpediente(Expediente expediente, Interesado interesado, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que elimina un expediente
	 * @param expediente Datos del expediente a eliminar
	 * @throws ConsultaExpedientesException
	 */
	public void eliminarExpediente(Expediente expediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que da de alta un interesado
	 * @param interesado Datos del interesado
	 * @throws ConsultaExpedientesException
	 */
	public void nuevoInteresado(Interesado interesado, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Métodoque elimina un interesado
	 * @param interesado Datos del interesado
	 * @throws ConsultaExpedientesException
	 */
	public void eliminarInteresado(Interesado interesado, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que elimina el interesado de un expediente
	 * @param numeroExpediente Identificador de expediente
	 * @throws ConsultaExpedientesException
	 */
	public void eliminarInteresadoExpediente(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que establece un nuevo hito actual para un expediente
	 * @param hito Datos del hito a establecer como actual
	 * @param fichero Ficheros asociados al hito
	 * @param historico Determina si se deben pasar el hito al hitórico o no
	 * @throws ConsultaExpedientesException
	 */
	public void establecerHitoActual(HitoExpediente hito, FicherosHito fichero, boolean historico, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que elimina el hito actual de un expediente
	 * @param numExp Identificador de expediente
	 * @throws ConsultaExpedientesException
	 */
	public void eliminarHitoActual(String numExp, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que da de alta un hito histórico para un expediente
	 * @param hito Datos del hito histórico
	 * @throws ConsultaExpedientesException
	 */
	public void nuevoHitoHistorico(HitoExpediente hito, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que elimina el listado de hitos históricos para un expediente
	 * @param numExp Identificador del expediente
	 * @throws ConsultaExpedientesException
	 */
	public void eliminarHitoHistorico(String numExp, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que carga un documento
	 * @param guid Identificador del documento
	 * @return Ruta del documento
	 * @throws ConsultaExpedientesException
	 */
	public String cargarDocumento(String guid, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que carga un documento
	 * @param guid Identificador del documento
	 * @return Datos del socumento
	 * @throws ConsultaExpedientesException
	 */
	public InfoDocumento recogerDocumento(String guid, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que busca expedientes determinado por unos parámetros
	 * @param oCriterio Parámetros de búsqueda
	 * @return Lista de expedientes
	 * @throws ConsultaExpedientesException
	 */
	public Expedientes busquedaExpedientes(CriterioBusquedaExpedientes oCriterio, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que busca un expediente para un determinado interesado
	 * @param NIF Identificador del interesado
	 * @param expediente Número de expediente
	 * @return Expediente
	 * @throws ConsultaExpedientesException
	 */
	public Expediente busquedaExpediente(String NIF, String expediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que anexa ficheros al hito actual
	 * @param ficheros Lista de ficheros a anexar
	 * @throws ConsultaExpedientesException
	 */
	public void anexarFicherosHitoActual(FicherosHito ficheros, Entidad entidad)	throws ConsultaExpedientesException;

	/**
	 * Método que comprueba si existen notificaciones para un expediente
	 * @param numeroExpediente Identificador del expediente
	 * @return true si existen notificaciones, false si no
	 * @throws ConsultaExpedientesException
	 */
	public boolean existenNotificaciones(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que comprueba si existen subsanaciones para un expediente
	 * @param numeroExpediente Identificador del expediente
	 * @return true si existen subsanaciones, false si no
	 * @throws ConsultaExpedientesException
	 */
	public boolean existenSubsanaciones(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que comprueba si existen pagos pendientes para un expediente
	 * @param numeroExpediente Identificadr del expediente
	 * @return true si existen pagos pendiente, false si no
	 * @throws ConsultaExpedientesException
	 */
	public boolean existenPagos(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que utiliza el servicio de notificaciones de SIGEM para conocer si el expediente tiene alguna notificación en curso.
	 * @param NIF
	 * @param numeroExpediente
	 * @return
	 * @throws ConsultaExpedientesException
	 */
	public boolean recogerNotificaciones(String NIF, String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que da de alta y asocia al hito actual un nueva solicitud de subsanación.
	 * Se utiliza desde la tramitación de expedientes para publicar en la consulta de expedientes
	 * del ciudadano la necesidad de aportar nueva documentación al expediente.
	 * La publicación de esta solicitud de subsanación suele ir acompañada de una notificación previa.
	 * @param poSubsanacion Objeto que encapsula los datos de la subsanación.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public void altaSolicitudSubsanacion(Subsanacion poSubsanacion, Entidad entidad)throws ConsultaExpedientesException;

	/**
	 * Método que devuelve una lista de objetos Subsanacion que estén asociados al hito actual del expediente.
	 * @return List ArrayList con la lista de objetos Subsanación
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Subsanaciones obtenerSubsanacionesHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que devuelve una lista de objetos Subsanacion que estén asociados al hito actual del expediente.
	 * @param guidHito Identificador del hito del que se quiere recuperar la lista de solicitudes de subsanación
	 * @return List ArrayList con la lista de objetos Subsanación
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Subsanaciones obtenerSubsanacionesHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que da de alta y asocia al hito actual del procedimiento un solicitud de realización de pago
	 * y aportación de documentación al expediente.
	 * @param poPago Objeto con los datos del pago.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public void altaSolicitudPago(Pago poPago, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que devuelve una lista de objetos Pago asociados al hito actual del expediente.
	 * @return List ArrayList con la lista de Pago's
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Pagos obtenerPagosHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que devuelve una lista de objetos Pago asociados al hito que llega como parámetro.
	 * @return List ArrayList con la lista de Pago's
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Pagos obtenerPagosHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Método que da de alta y asocia al hito actual la información referente a una notificación
	 * en curso en el sistema de notificaciones de SIGEM.
	 * Este método es usado desde la tramitación para publicar que se ha realizado una notificación.
	 * Previa a su publicación la notificación se ha dado de alta en el servicio de notificaciones y
	 * el identificador devuelto por este sistema se pasa como atributo de la clase Notificacion.
	 * @param poNotificacion Datos de la notificación para su publicación.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public void altaNotificacion(Notificacion poNotificacion, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Devuelve una lista de objetos Notificacion con los datos publicados de las notificaciones en curso
	 * para el hito actual.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Notificaciones obtenerNotificionesHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Devuelve una lista de objetos Notificacion con los datos publicados de las notificaciones en curso
	 * para cuyo identificador se pasa como parámetro.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public Notificaciones obtenerNotificionesHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Actualiza e lestado de un expediente de LocalGIS
	 * @param idExpediente Identificador del expediente a actualizar
	 * @param estado Descripción del nuevo estado del expediente
	 * @param entidad Entidad
	 * @return true en caso de actualizarlo, false si no ha podido
	 * @throws ConsultaExpedientesException En caso de producirse algún error
	 */
	public boolean actualizarEstadoLocalGIS(String idExpediente, String estado, Entidad entidad) throws ConsultaExpedientesException;

	/**
	 * Publica información de un expediente de LocalGIS
	 * @param idExpediente Identificador del expediente a publicar
	 * @param nif NIF del ciudadano
	 * @param tipoExpediente Descripción del tipo de expediente
	 * @param estado Descripción del estado del expediente
	 * @param fecha Fecha de generación del expediente
	 * @param entidad Entidad
	 * @return true en caso de publicarse, false si no ha podido
	 * @throws ConsultaExpedientesException En caso de producirse algún error
	 */
	public boolean publicarExpedienteLocalGIS(String idExpediente, String nif, String tipoExpediente, String estado, Date fecha, Entidad entidad) throws ConsultaExpedientesException;
}
