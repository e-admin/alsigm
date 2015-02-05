/**
 * @author Javier Septien Arceredillo
 *
 * Fecha de Creación: 17-may-2007
 */


package ieci.tecdoc.sgm.ct;

import ieci.tecdoc.sgm.base.guid.Guid;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesException;
import ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.notificaciones.CriterioBusquedaNotificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificacion;
import ieci.tecdoc.sgm.core.services.notificaciones.Notificaciones;
import ieci.tecdoc.sgm.core.services.notificaciones.ServicioNotificaciones;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.GuidIncorrectoRdeExcepcion;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.ct.database.ExpedienteDatos;
import ieci.tecdoc.sgm.ct.database.FicheroHitoDatos;
import ieci.tecdoc.sgm.ct.database.HitoEstadoDatos;
import ieci.tecdoc.sgm.ct.database.HitoHistoricoDatos;
import ieci.tecdoc.sgm.ct.database.InteresadoDatos;
import ieci.tecdoc.sgm.ct.database.NotificacionDatos;
import ieci.tecdoc.sgm.ct.database.PagoDatos;
import ieci.tecdoc.sgm.ct.database.SubsanacionDatos;
import ieci.tecdoc.sgm.ct.database.datatypes.Expediente;
import ieci.tecdoc.sgm.ct.database.datatypes.ExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Expedientes;
import ieci.tecdoc.sgm.ct.database.datatypes.FicheroHitoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito;
import ieci.tecdoc.sgm.ct.database.datatypes.HitoExpediente;
import ieci.tecdoc.sgm.ct.database.datatypes.HitoExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.HitosExpediente;
import ieci.tecdoc.sgm.ct.database.datatypes.InteresadoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.NotificacionImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Pago;
import ieci.tecdoc.sgm.ct.database.datatypes.PagoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Pagos;
import ieci.tecdoc.sgm.ct.database.datatypes.Subsanacion;
import ieci.tecdoc.sgm.ct.database.datatypes.SubsanacionImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.Subsanaciones;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Clase que gestiona toda la aplicación de Consulta Web de expedientes.
 * Pone en contacto la capa de presentacion con la capa de acceso a datos y
 * y tambien contiene todos los metodos para trabajar como API sin nescesidad
 * de trabajar directamente con los objetos que enmascaran los datos.
 */

public class GestorConsulta {

	private static final Logger logger = Logger.getLogger(GestorConsulta.class);

	/**
	 * Constructor de clase
	 *
	 */
	public GestorConsulta(){

	}

	/**
	 * Recupera la información de los expedientes de un interesado por el NIF que entra como parámetro
	 * @param sessionId Identificador de sesión de la aplicación llamante
	 * @param NIF NIF del interesado
	 * @return  Objeto expedientes con todos los expedientes de un interesado
	 * @throws Exception Si se produce algún error.
	 */

	public static Expedientes consultarExpedientes (String sessionId, String NIF, String entidad) throws ConsultaExcepcion {
		Expedientes expedientes = null;
		try {
			InteresadoDatos interesadoDatos = new InteresadoDatos();
			interesadoDatos.setNIF(NIF);
			interesadoDatos.cargarExpedientesPorNIF(true, entidad);
			expedientes = interesadoDatos.getExpedientes();
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al realizar consulta [consultarExpedientes][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al realizar consulta [consultarExpedientes][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES, e.getCause());
		}
		return expedientes;
	}

	/**
	   * Recupera la información del conjunto de expedientes de un interesado
	   * realizando una busqueda por fechas.
	   * <br/>
	   * Existen tres tipos de búsqueda
	   * que vendrán definidas por el cuarto parámetro de entrada ('operadorClausula')
	   * Este puede tomar los siguientes valores:
	   * <br/>
	   * Operador			Significado
	   *<br/>
	   *
	   *  >: Mayor que una fecha<br/>
	   *  =: Igual a una fecha<br/>
	   *  entre: Rango entre dos fechas<br/>
	   *
	   * @param sessionId Identificador de la sesion.
	   * @param NIF Se corresponde con el identificador del interesado.
	   * @param fechaDesde Fecha de búsqueda.
	   * @param fechaHasta Fecha posterior de búsqueda cuando se hace
	   * en un intervalo entre dos fechas (solo cuando el operadorClausula viene con valor 'entre').
	   * @param operadorConsulta Delimita el tipo  de búsqueda
	   * @return Expedientes Lista de expedientes resultante de la consulta.
	   * @throws Exception Si se produce algún error.
	   */

	public static Expedientes consultarExpedientes (String sessionId, String NIF, String fechaDesde, String fechaHasta, String operadorConsulta, String entidad) throws ConsultaExcepcion {
		Expedientes expedientes = null;
		try {
			InteresadoDatos interesadoDatos = new InteresadoDatos();
			ExpedienteDatos expDatos = new ExpedienteDatos();
			interesadoDatos.setNIF(NIF);
			expedientes = expDatos.cargarExpedientesPorNIFYFecha(NIF, fechaDesde, fechaHasta, operadorConsulta, entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al realizar consulta avanzada [consultarExpedientes][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al realizar consulta avanzada [consultarExpedientes][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES, e.getCause());
		}
		return expedientes;
	}

	/**
	 * Metodo de tipo get que devuelve la URL aportación (subsanación) de expedientes
	 * y que es configurable desde el fichero de properties ieci.tecdoc.sgm.ct.resources.application.
	 * @return URL donde se puede hacer una subsanación de un expediente
	 * @throws Exception
	 */
	public static String obtenerURLAportacionExpedientes () throws ConsultaExcepcion {
		try {
			return Configuracion.getURLAportacion();
		} catch(Exception e) {
			logger.error("Error al obtener URL de subsanacion [obtenerURLAportacionExpedientes][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_URL_APORTACION, e.getCause());
		}
	}

	/**
	 * Metodo de tipo get que devuelve la URL para consultar notificaciones de expedientes
	 * y que es configurable desde el fichero de properties ieci.tecdoc.sgm.ct.resources.application.
	 * @return URL para consultar notificaciones de expedientes
	 * @throws Exception
	 */
	public static String obtenerURLNotificacionExpedientes () throws ConsultaExcepcion {
		try {
			return Configuracion.getURLNotificacion();
		} catch(Exception e) {
			logger.error("Error al obtener URL de notificacion  [obtenerURLNotificacionExpedientes][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_URL_NOTIFICACION, e.getCause());
		}
	}


	/**
	 * Metodo de tipo get que devuelve la URL para el pago de tasas
	 * y que es configurable desde el fichero de properties ieci.tecdoc.sgm.ct.resources.application.
	 * @return URL para el pago de tasas
	 * @throws Exception
	 */
	public static String obtenerURLPagoTasas() throws ConsultaExcepcion {
		try {
			return Configuracion.getURLPago();
		} catch(Exception e) {
			logger.error("Error al obtener URL de pago de tasas  [obtenerURLPagoTasas][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_URL_PAGO, e.getCause());
		}
	}


	/**
	 * Recupera la información en detalle de un expediente a partir de su numero de expediente
	 * @param sessionId Identificador de sesión de la aplicación llamante
	 * @param numeroExpediente Numero de expediente que se desea obtener en detalle
	 * @return Expediente con sus datos en detalle
	 * @throws ConsultaExcepcion Si se produce algún error.
	 */
	public static ExpedienteDatos obtenerDetalle(String sessionId, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		ExpedienteDatos expDatos = new ExpedienteDatos();
		try {
			expDatos.setNumero(numeroExpediente);
			expDatos.load(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al obtener detalle de expediente [obtenerDetalle][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al obtener detalle de expediente [obtenerDetalle][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTE, e.getCause());
		}

		return expDatos;
	}



	/**
	 * Recupera los hitos del historico para un expediente
	 * @param sessionId Identificador de sesión de la aplicación llamante
	 * @param numeroExpediente Numero de expediente del que se desea obtener sus hitos historicamente
	 * @return  Objeto HitosExpediente con todos los hitos de un expediente
	 * @throws ConsultaExcepcion Si se produce algún error.
	 */
	public static HitosExpediente obtenerHistoricoExpediente(String sessionId, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		HitosExpediente hitos = null;
		try {
			HitoHistoricoDatos hito = new HitoHistoricoDatos();
			hito.setNumeroExpediente(numeroExpediente);
			hitos = hito.obtenerHitosExpediente(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al obtener historico de expediente [obtenerHistorico][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al obtener historico de expediente [obtenerHistorico][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_HITOS_EXPEDIENTE, e.getCause());
		}
		return hitos;
	}

	/**
	 * Recupera el hito de estado para un expediente
	 * @param sessionId Identificador de sesión de la aplicación llamante
	 * @param numeroExpediente Numero de expediente del que se desea obtener sus hitos historicamente
	 * @return  Objeto HitoExpedienteImpl con todos los hitos de un expediente
	 * @throws ConsultaExcepcion Si se produce algún error.
	 */
	public static HitoEstadoDatos obtenerHitoEstado(String sessionId, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		HitoEstadoDatos hitoEstadoDatos = new HitoEstadoDatos();
		try {
			hitoEstadoDatos.setNumeroExpediente(numeroExpediente);
			hitoEstadoDatos.cargar(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al obtener hito de estado de expediente [obtenerHitoEstado][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al obtener hito de estado de expediente [obtenerHitoEstado][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_OBTENER_HITO_ESTADO_EXPEDIENTE, e.getCause());
		}

		return hitoEstadoDatos;
	}

	/**
	 * Obtiene como Hashtable los ficheros de los hitos.
	 * Como clave vendra el guid del hito y como valor asociado a la clave un objeto de tipo
	 * coleccion con los ficheros de dicho hito
	 *
	 * @param hitos Objeto tipo coleccion con los hitos de los que queremos los ficheros
	 * @return Hashtable con los ficheros de los hitos
	 * @throws ConsultaExcepcion
	 */
	public static Hashtable obtenerFicherosHitos(HitosExpediente hitos, String entidad) throws ConsultaExcepcion {
		Hashtable ficherosHashtable = new Hashtable();
		try {
			for(int a=0; a<hitos.count(); a++){
				HitoExpediente hito = (HitoExpediente)hitos.get(a);
				String guid = hito.getGuid();
				FicherosHito ficheros = GestorConsulta.obtenerFicherosHito(guid, entidad);
				ficherosHashtable.put(guid, ficheros);
			}
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al obtener ficheros de los hitos de un expedinte [obtenerFicherosHito][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al obtener ficheros de los hitos de un expediente [obtenerFicherosHito][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_FICHEROS_HITO, e.getCause());
		}

		return ficherosHashtable;
	}
	/**
	 * Obtiene una colección de los ficheros de un hito.
	 *
	 * @param guidHito Identificador del hito
	 * @return FicherosHito Coleccion de ficheros
	 * @throws ConsultaExcepcion
	 */

	public static FicherosHito obtenerFicherosHito(String guidHito, String entidad) throws ConsultaExcepcion {
		FicherosHito ficherosHito = new FicherosHito();
		try {
			FicheroHitoDatos ficheroDatos = new FicheroHitoDatos();
			ficherosHito = ficheroDatos.obtenerFicherosHito(guidHito, entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al obtener ficheros del hito [obtenerFicherosHito][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al obtener ficheros del hito [obtenerFicherosHito][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_FICHEROS_HITO, e.getCause());
		}

		return ficherosHito;
	}


	/**
	 * Da de alta un nuevo expediente en el modelo de consulta.
	 *
	 * @param expediente
	 *            Información del expediente
	 * @param interesado
	 *            Información del interesado en el expediente
	 * @throws Exception
	 *             Excepción si se produce algún error
	 */
	public static void nuevoExpediente(ExpedienteImpl expediente, InteresadoImpl interesado, String entidad) throws ConsultaExcepcion {
		try {
			ExpedienteDatos exp = new ExpedienteDatos(expediente);
			InteresadoDatos inte = null;
			if (interesado != null)
				inte = new InteresadoDatos(interesado);
			if (interesado != null)
				inte.nuevo(entidad);
			exp.nuevo(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al dar de alta un expediente [nuevoExpediente][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al dar de alta un expediente [nuevoExpediente][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_EXPEDIENTE, e.getCause());
		}
	}

	/**
	 * Elimina un expediente
	 *
	 * @param expediente
	 *            Información del expediente
	 * @throws Exception
	 *             Excepción si se produce algún error
	 */
	public static void eliminarExpediente(ExpedienteImpl expediente, String entidad) throws ConsultaExcepcion {
		try {
			ExpedienteDatos exp = new ExpedienteDatos(expediente);
			exp.eliminar(null, entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al eliminar un expediente [eliminarExpediente][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al eliminar un expediente [eliminarExpediente][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_EXPEDIENTE, e.getCause());
		}
	}

	/**
	 * Da de alta un interesado en un expediente
	 *
	 * @param interesado
	 *            Información del interesado
	 * @throws Exception
	 *             Excepción si se produce algún error
	 */
	public static void nuevoInteresado(InteresadoImpl interesado, String entidad) throws ConsultaExcepcion {
		try {
			InteresadoDatos inte = new InteresadoDatos(interesado);
			inte.nuevo(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al dar de alta un interesado [nuevoInteresado][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al dar de alta un interesado [nuevoInteresado][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_INTERESADO, e.getCause());
		}
	}

	/**
	 * Elimina un interesado en un expediente
	 *
	 * @param interesado
	 *            Información del interesado
	 * @throws Exception
	 *             Excepción si se produce algún error
	 */
	public static void eliminarInteresado(InteresadoImpl interesado, String entidad) throws ConsultaExcepcion {
		try {
			InteresadoDatos inte = new InteresadoDatos(interesado);
			inte.eliminar(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al eliminar un interesado [eliminarInteresado][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al eliminar un interesado [eliminarInteresado][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_INTERESADO, e.getCause());
		}
	}

	/**
	 * Elimina un interesado en un expediente
	 *
	 * @param interesado
	 *            Información del interesado
	 * @throws Exception
	 *             Excepción si se produce algún error
	 */
	public static void eliminarInteresadoExpediente(String numeroExpediente, String entidad) throws ConsultaExcepcion {
		try {
			InteresadoImpl interesado = new InteresadoImpl();
			interesado.setNumeroExpediente(numeroExpediente);
			InteresadoDatos interesadoData = new InteresadoDatos(interesado);
			interesadoData.eliminarPorExpediente(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al eliminar un interesado de un expediente [eliminarInteresadoExpediente][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al eliminar un interesado de un expediente [eliminarInteresadoExpediente][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_INTERESADO, e.getCause());
		}
	}

	/**
	 * Establece el hito actual de un expediente. Si ya existía un hito lo
	 * elimina o lo establece como histórico
	 *
	 * @param hito
	 *            Información del hito actual
	 * @param historico
	 *            Si el hito actual pasa a histórico
	 * @throws Exception
	 *             Excepción si se produce algún error
	 */
	public static void establecerHitoActual(HitoExpedienteImpl hito, boolean historico, String entidad) throws ConsultaExcepcion {
		try {
			HitoEstadoDatos hit = new HitoEstadoDatos(hito);
			if (historico) {
				HitoEstadoDatos hitoPrevio = new HitoEstadoDatos();
				hitoPrevio.setNumeroExpediente(hito.getNumeroExpediente());
				hitoPrevio.cargar(entidad);
				HitoHistoricoDatos hitHist = new HitoHistoricoDatos(hitoPrevio);
				nuevoHitoHistorico(hitHist, entidad);
			}
			eliminarHitoActual(hito.getNumeroExpediente(), entidad);
			hit.nuevo(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al establecer hito actual [establecerHitoActual][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al establecer hito actual [establecerHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_HITO_ESTADO, e.getCause());
		}
	}

	/**
	 * Establece un hito de estado nuevo
	 * @param hito Objeto con todas las propiedades del hito con el que se va a trabajar
	 * @param fichero Si el hito lleva un fichero adjunto
	 * @param historico boleano que será true si se quiere que el hito de estado que se va
	 * a sustituir pase al histórico de hitos
	 * @throws Exception
	 */
	public static void establecerHitoActual(HitoExpedienteImpl hito, FicheroHitoImpl fichero, boolean historico, String entidad) throws ConsultaExcepcion {
		try {
			HitoEstadoDatos hit = new HitoEstadoDatos(hito);
			FicheroHitoDatos ficheroDatos = new FicheroHitoDatos(fichero);

			if (historico) {
				HitoEstadoDatos hitoPrevio = new HitoEstadoDatos();
				hitoPrevio.setNumeroExpediente(hito.getNumeroExpediente());
				hitoPrevio.cargar(entidad);
				HitoHistoricoDatos hitHist = new HitoHistoricoDatos(hitoPrevio);
				nuevoHitoHistorico(hitHist, entidad);
			}
			eliminarHitoActual(hito.getNumeroExpediente(), entidad);
			hit.nuevo(entidad);
			ficheroDatos.nuevo(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al establecer hito actual [establecerHitoActual][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al establecer hito actual [establecerHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_HITO_ESTADO, e.getCause());
		}
	}


	/**
	 * Establece un hito de estado nuevo
	 * @param hito Objeto con todas las propiedades del hito con el que se va a trabajar
	 * @param ficheros Coleccion de ficheros que vienen adjuntos al hito
	 * @param historico boleano que será true si se quiere que el hito de estado que se va
	 * a sustituir pase al histórico de hitos
	 * @throws Exception
	 */
	public static void establecerHitoActual(HitoExpedienteImpl hito, FicherosHito ficheros, boolean historico, String entidad) throws ConsultaExcepcion {
		try {
			HitoEstadoDatos hit = new HitoEstadoDatos(hito);
			if (historico) {
				HitoEstadoDatos hitoPrevio = new HitoEstadoDatos();
				hitoPrevio.setNumeroExpediente(hito.getNumeroExpediente());
				hitoPrevio.cargar(entidad);
				HitoHistoricoDatos hitHist = new HitoHistoricoDatos(hitoPrevio);
				nuevoHitoHistorico(hitHist, entidad);
			}
			eliminarHitoActual(hito.getNumeroExpediente(), entidad);
			hit.nuevo(entidad);
			for(int a = 0; a < ficheros.size(); a++){
				FicheroHitoImpl fichero = (FicheroHitoImpl) ficheros.get(a);
				FicheroHitoDatos ficheroDatos = new FicheroHitoDatos(fichero);
				ficheroDatos.nuevo(entidad);
			}
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al establecer hito actual [establecerHitoActual][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al establecer hito actual [establecerHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_HITO_ESTADO, e.getCause());
		}
	}


	/**
	 * Elimina un hito de estado
	 * @param numExp Numero de Expediente cuyo hito de estado quiere eliminarse
	 * @param dbConn Conexion a base de datos
	 * @throws Exception
	 */
	public static void eliminarHitoActual(String numExp, String entidad) throws ConsultaExcepcion {
		try {
			if (HitoEstadoDatos.existeHitoActual(numExp, entidad)) {
				HitoEstadoDatos hit = new HitoEstadoDatos();
				hit.setNumeroExpediente(numExp);
				hit.eliminar(entidad);
			}
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al eliminar hito actual [eliminarHitoActual][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al eliminar hito actual [eliminarHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_HITO_ESTADO, e.getCause());
		}
	}

	/**
	 * Añade un hito histórico de un expediente
	 *
	 * @param hito
	 *            Información del hito histórico
	 * @throws Exception
	 *             Excepción si se produce algún error
	 */
	public static void nuevoHitoHistorico(HitoExpedienteImpl hito, String entidad) throws ConsultaExcepcion {
		try {
			HitoHistoricoDatos hit = new HitoHistoricoDatos(hito);
			hit.nuevo(entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al crear hito historico [nuevoHitoHistorico][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al crear hito historico [nuevoHitoHistorico][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_HITO_HISTORICO, e.getCause());
		}
	}

	public static void eliminarHitoHistorico(String numExp, String entidad) throws ConsultaExcepcion {
		try {
			if (HitoHistoricoDatos.existeHitoHistorico(numExp, entidad)) {
				HitoHistoricoDatos hit = new HitoHistoricoDatos();
				hit.setNumeroExpediente(numExp);
				hit.eliminar(entidad);
			}
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al eliminar hito historico [eliminarHitoHistorico][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al eliminar hito historico [eliminarHitoHistorico][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ELIMINAR_HITO_HISTORICO, e.getCause());
		}
	}

	/**
	   * Recupera la información del documento cuyo guid es el pasado como parámetro
	   * @param sessionId Identificador de sesión de la aplicación llamante
	   * @param guid Identificador del documento a recuperar
	   * @return String Ruta completa donde se ha almacenado el fichero recuperado
	   * @throws Exception
	   */
	public static String cargarDocumento(String sessionId, String guid, String entidad) throws ConsultaExcepcion {
		try {
			ServicioRepositorioDocumentosTramitacion repositorio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
			String path = repositorio.retrieveDocument(sessionId, guid, Configuracion.getPathDocumentos(), getEntidadServicio(entidad));
			return path;
		} catch(GuidIncorrectoRdeExcepcion gie) {
			logger.error("Error al cargar documento [cargarDocumento][GuidIncorrectoRdeExcepcion]", gie.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_FICHEROS_HITO, gie.getCause());
		} catch(Exception e) {
			logger.error("Error al cargar documento [cargarDocumento][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_FICHEROS_HITO, e.getCause());
		}
	}

	/**
	 * Recoge un documento llamando a la aplicación de registro documental
	 *
	 * @param sessionId Identificador de sesión del usuario
	 * @param guid Identificador del documento
	 * @return DocumentoInfo. Objeto con las propiedades del documento
	 * @throws Exception
	 */

	public static ieci.tecdoc.sgm.rde.database.DocumentoInfo recogerDocumento(String sessionId, String guid, String entidad) throws ConsultaExcepcion {
		try {
			ServicioRepositorioDocumentosTramitacion repositorio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
			ieci.tecdoc.sgm.rde.database.DocumentoInfo documentoInfo = getDocumentoInfoImpl(repositorio.retrieveDocument(sessionId, guid, getEntidadServicio(entidad)));
			return documentoInfo;
		} catch(GuidIncorrectoRdeExcepcion gie) {
			logger.error("Error al cargar documento [recogerDocumento][GuidIncorrectoRdeExcepcion]", gie.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_FICHEROS_HITO, gie.getCause());
		} catch(Exception e) {
			logger.error("Error al cargar documento [recogerDocumento][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_FICHEROS_HITO, e.getCause());
		}
	}

	/**
	 *  Recoge si un expediente tiene notificaciones
	 * @param sessionId Identificador de sesión del usuario
	 * @param NIF Identificador del interesado
	 * @param numeroExpediente Numero del expediente
	 * @return boleano true cuando se detectan notificaciones
	 * @throws Exception
	 */
	public static boolean recogerNotificaciones(String sessionId, String NIF, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		try {
			Notificaciones resultadosBusqueda = obtenerNotificaciones(sessionId, NIF, numeroExpediente, entidad);
			if (resultadosBusqueda == null)
				return false;
			ArrayList lista = (ArrayList)resultadosBusqueda.getNotificaciones();
			if (lista == null)
				return false;
			return (lista.size() > 0);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al obtener notificaciones [recogerNotificaciones][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al obtener notificaciones [recogerNotificaciones][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, e.getCause());
		}
	}


	/**
	 *  Recoge si un expediente tiene subsanaciones
	 * @param sessionId Identificador de sesión del usuario
	 * @param numeroExpediente Numero del expediente
	 * @return boleano true cuando se detectan subsanaciones
	 * @throws Exception
	 */
	/*public static boolean existenSubsanaciones(String sessionId, String numeroExpediente) throws ConsultaExcepcion {
		try {
			try{
				Subsanaciones subsanaciones1 = obtenerSubsanacionesHitoActual(numeroExpediente);
				if (subsanaciones1.count() > 0)
					return true;
			}catch(Exception e1) { }
			HitosExpediente hitos = obtenerHistoricoExpediente(sessionId, numeroExpediente);
			for(int i=0; i<hitos.count(); i++){
				try{
					Subsanaciones subsanaciones2 = obtenerSubsanacionesHito(((HitoExpediente)hitos.get(i)).getGuid());
					if (subsanaciones2.count() > 0)
						return true;
				}catch(Exception e2) { }
			}
			return false;
		} catch(ConsultaExcepcion ce) {
			throw ce;
		} catch(Exception e) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_SUBSANACIONES, e.getCause());
		}
	}*/

	public static boolean existenSubsanaciones(String sessionId, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		try {
			Subsanaciones subsanaciones = obtenerSubsanaciones(numeroExpediente, entidad);
			if (subsanaciones == null || subsanaciones.count() <= 0)
				return false;
			return true;
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al comprobar subsanaciones [existenSubsanaciones][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al comprobar subsanaciones [existenSubsanaciones][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_SUBSANACIONES, e.getCause());
		}
	}


	/**
	 *  Recoge si un expediente tiene pagos
	 * @param sessionId Identificador de sesión del usuario
	 * @param numeroExpediente Numero del expediente
	 * @return boleano true cuando se detectan pagos
	 * @throws Exception
	 */
	/*public static boolean existenPagos(String sessionId, String numeroExpediente) throws ConsultaExcepcion {
		try {
			try{
				Pagos pagos1 = obtenerPagosHitoActual(numeroExpediente);
				if (pagos1.count() > 0)
					return true;
			}catch(Exception e1) { }
			HitosExpediente hitos = obtenerHistoricoExpediente(sessionId, numeroExpediente);
			for(int i=0; i<hitos.count(); i++){
				try{
					Pagos pagos2 = obtenerPagosHito(((HitoExpediente)hitos.get(i)).getGuid());
					if (pagos2.count() > 0)
						return true;
				}catch(Exception e2) { }
			}
			return false;
		} catch(ConsultaExcepcion ce) {
			throw ce;
		} catch(Exception e) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, e.getCause());
		}
	}*/

	public static boolean existenPagos(String sessionId, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		try {
			Pagos pagos = obtenerPagos(numeroExpediente, entidad);
			if (pagos == null || pagos.count() <= 0)
				return false;
			return true;
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al comprobar pagos de tasas [existenPagos][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al comprobar pagos de tasas [existenPagos][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, e.getCause());
		}
	}


	/**
	 *  Recoge si un expediente tiene notificaciones
	 * @param sessionId Identificador de sesión del usuario
	 * @param numeroExpediente Numero del expediente
	 * @return boleano true cuando se detectan notificaciones
	 * @throws Exception
	 */
	/*public static boolean existenNotificaciones(String sessionId, String numeroExpediente) throws ConsultaExcepcion {
		try {
			try{
				ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones notificaciones1 = obtenerNotificionesHitoActual(numeroExpediente);
				if (notificaciones1.count() > 0)
					return true;
			}catch(Exception e1) { }
			HitosExpediente hitos = obtenerHistoricoExpediente(sessionId, numeroExpediente);
			for(int i=0; i<hitos.count(); i++){
				try{
					ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones notificaciones2 = obtenerNotificionesHito(((HitoExpediente)hitos.get(i)).getGuid());
					if (notificaciones2.count() > 0)
						return true;
				}catch(Exception e2) { }
			}
			return false;
		} catch(ConsultaExcepcion ce) {
			throw ce;
		} catch(Exception e) {
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, e.getCause());
		}
	}*/

	public static boolean existenNotificaciones(String sessionId, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		try {
			ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones notificaciones = obtenerNotificiones(numeroExpediente, entidad);
			if (notificaciones == null || notificaciones.count() <= 0)
				return false;
			return true;
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al comprobar notificaciones [existenNotificaciones][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al comprobar notificaciones [existenNotificaciones][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, e.getCause());
		}
	}


	/**
	 *  Recoge si un expediente tiene notificaciones
	 * @param sessionId Identificador de sesión del usuario
	 * @param NIF Identificador del interesado
	 * @param numeroExpediente Numero del expediente
	 * @return boleano true cuando se detectan notificaciones
	 * @throws Exception
	 */
	public static Notificaciones obtenerNotificaciones(String sessionId, String NIF, String numeroExpediente, String entidad) throws ConsultaExcepcion {
		try {
			ServicioNotificaciones oServicio = LocalizadorServicios.getServicioNotificaciones();
			CriterioBusquedaNotificaciones notificacion = new CriterioBusquedaNotificaciones();
			notificacion.setNif(NIF);
			notificacion.setEstado(new Integer(2));
			Entidad oEntidad = new Entidad();
			oEntidad.setIdentificador(entidad);
			Notificaciones resultadosBusqueda = oServicio.consultarNotificaciones(notificacion, true, oEntidad);
			List resultados = resultadosBusqueda.getNotificaciones();
			ArrayList finales = new ArrayList();
			for(int a=0; a <  resultados.size(); a++){
				Notificacion resultadoBusqueda = (Notificacion)resultados.get(a);
				if (resultadoBusqueda.getNumeroExpediente().equals(numeroExpediente)) {
					finales.add(resultadoBusqueda);
				}
			}
			Notificaciones notificaciones = new Notificaciones();
			notificaciones.setNotificaciones(finales);
			return notificaciones;
		} catch(Exception e) {
			logger.error("Error al obtener notificaciones [obtenerNotificaciones][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, e.getCause());
		}
	}

	public static boolean actualizarEstado(String numExpediente, String estado, String entidad) throws ConsultaExcepcion {
		try {
			ExpedienteDatos expDatos = new ExpedienteDatos();
			expDatos.setNumero(numExpediente);
			expDatos.setEstado(estado);
			expDatos.actulizarEstado(entidad);
			return true;
		}catch(Exception e){
			logger.error("Error al actualizar estado de expediente [actualizarEstado][Excepcion]", e.fillInStackTrace());
			return false;
		}
	}

	public static Expedientes busquedaExpedientes (String sessionId, String NIF, String fechaDesde, String fechaHasta, String operadorConsulta, String estado, String entidad) throws ConsultaExcepcion {
		try {
			ExpedienteDatos expDatos = new ExpedienteDatos();
			Expedientes expedientes = null;
			boolean bEstado = true, bFecha = true;

			if (estado == null || "".equals(estado))
				bEstado = false;
			if (operadorConsulta == null || "".equals(operadorConsulta))
				bFecha = false;

			if (bEstado && bFecha)
				expedientes = expDatos.cargarExpedientesPorNIFEstadoYFechas(NIF, fechaDesde, fechaHasta, operadorConsulta, estado, entidad);
			else if (bEstado)
				expedientes = expDatos.cargarExpedientesPorNIFYEstado(NIF, estado, entidad);
			else if (bFecha)
				expedientes = expDatos.cargarExpedientesPorNIFYFecha(NIF, fechaDesde, fechaHasta, operadorConsulta, entidad);
			else if (!bEstado && !bFecha)
				expedientes = expDatos.cargarExpedientesPorNIFYFecha(NIF, fechaDesde, fechaHasta, operadorConsulta, entidad);
			return expedientes;
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al realizar busqueda [busquedaExpedientes][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al realizar busqueda [busquedaExpedientes][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES, e.getCause());
		}
	}

	public static Expedientes busquedaExpedientes (CriterioBusquedaExpedientes criterios, String entidad) throws ConsultaExcepcion {
		try {
			ExpedienteDatos expDatos = new ExpedienteDatos();
			Expedientes expedientes = null;
			boolean bEstado = true, bFecha = true;

			expedientes = expDatos.cargarExpedientesPorNIFYCriterios(criterios.getNIF(), criterios, entidad);

			return expedientes;
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al realizar busqueda [busquedaExpedientes][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al realizar busqueda [busquedaExpedientes][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTES, e.getCause());
		}
	}

	public static Expediente busquedaExpediente(String sessionId, String NIF, String expediente, String entidad) throws ConsultaExcepcion {
		try {
			ExpedienteDatos expDatos = new ExpedienteDatos();
			return expDatos.cargarExpedientePorNIF(NIF, expediente, entidad);
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al realizar busqueda [busquedaExpediente][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al realizar busqueda [busquedaExpediente][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_CARGAR_EXPEDIENTE, e.getCause());
		}
	}

	/**
	 * Establece archivos adjuntos a un hito (no almacena en repositorio los ficheros en si)
	 * @param ficheros Ficheros a adjuntar al hito actual
	 * @throws Exception
	 */
	public static void anexarFicherosHitoActual(FicherosHito ficheros, String entidad) throws ConsultaExcepcion {
		try {
			for(int a = 0; a < ficheros.size(); a++){
				FicheroHitoImpl fichero = (FicheroHitoImpl) ficheros.get(a);
				FicheroHitoDatos ficheroDatos = new FicheroHitoDatos(fichero);
				ficheroDatos.nuevo(entidad);
			}
		} catch(ConsultaExcepcion ce) {
			logger.error("Error al anexar documentos a hito [anexarFicherosHitoActual][ConsultaExcepcion]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al anexar documentos a hito [anexarFicherosHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_ANEXAR_DOCUMENTO_HITO_ESTADO, e.getCause());
		}
	}


	public static ieci.tecdoc.sgm.rde.database.DocumentoInfo getDocumentoInfoImpl(DocumentoInfo info){
		if(info == null){
			return null;
		}
		ieci.tecdoc.sgm.rde.database.DocumentoInfo oDocInfo = new ieci.tecdoc.sgm.rde.database.DocumentoInfo();
		oDocInfo.setContent(info.getContent());
		oDocInfo.setExtension(info.getExtension());
		oDocInfo.setGuid(info.getGuid());
		oDocInfo.setMimeType(info.getMimeType());
		return oDocInfo;
	}

	public static Entidad getEntidadServicio(String entidad){
		if(entidad == null || "".equals(entidad)){
			return null;
		}
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(entidad);
		oEntidad.setNombre(entidad);
		return oEntidad;
	}

	/**
	 * Método que da de alta y asocia al hito actual un nueva solicitud de subsanación.
	 * Se utiliza desde la tramitación de expedientes para publicar en la consulta de expedientes
	 * del ciudadano la necesidad de aportar nueva documentación al expediente.
	 * La publicación de esta solicitud de subsanación suele ir acompañada de una notificación previa.
	 * @param poSubsanacion Objeto que encapsula los datos de la subsanación.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static void altaSolicitudSubsanacion(Subsanacion poSubsanacion, String entidad)throws ConsultaExcepcion{
		try{
			SubsanacionDatos subsanacion = new SubsanacionDatos((SubsanacionImpl)poSubsanacion);
			subsanacion.nuevo(entidad);
		}catch(Exception e){
			logger.error("Error al dar de alta subsanacion [altaSolicitudSubsanacion][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_SUBSANACION, e.getCause());
		}
	}

	/**
	 * Método que devuelve una lista de objetos Subsanacion que estén asociados al hito actual del expediente.
	 * @return List ArrayList con la lista de objetos Subsanación
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static Subsanaciones obtenerSubsanacionesHitoActual(String expediente, String entidad) throws ConsultaExcepcion{
		try{
			HitoEstadoDatos hitoActual = obtenerHitoEstado(null, expediente, entidad);
			return obtenerSubsanacionesHito(hitoActual.getGuid(), entidad);
		}catch(Exception e){
			logger.error("Error al obtener subsanaciones de hito actual [obtenerSubsanacionesHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_SUBSANACIONES, e.getCause());
		}
	}

	/**
	 * Método que devuelve todas las subsanaciones de un expediente
	 * @param expediente Núermo de expediente
	 * @return Subsanaciones Listado de subsanaciones de un expediente
	 * @throws ConsultaExcepcion
	 */
	private static Subsanaciones obtenerSubsanaciones(String expediente, String entidad) throws ConsultaExcepcion{
		try{
			SubsanacionDatos subsanacion = new SubsanacionDatos();
			subsanacion.setNumeroExpediente(expediente);
			return subsanacion.getSubsanacionesPorExpediente(entidad);
		}catch(Exception e){
			logger.error("Error al obtener subsanaciones de expediente [obtenerSubsanaciones][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_SUBSANACIONES, e.getCause());
		}
	}

	/**
	 * Método que devuelve una lista de objetos Subsanacion que estén asociados al hito actual del expediente.
	 * @param guidHito Identificador del hito del que se quiere recuperar la lista de solicitudes de subsanación
	 * @return List ArrayList con la lista de objetos Subsanación
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static Subsanaciones obtenerSubsanacionesHito(String guidHito, String entidad) throws ConsultaExcepcion{
		try{
			SubsanacionDatos subsanacion = new SubsanacionDatos();
			subsanacion.setIdentificadorHito(guidHito);
			return subsanacion.getSubsanacionesPorIdHito(entidad);
		}catch(Exception e){
			logger.error("Error al obtener subsanaciones de hito [obtenerSubsanacionesHito][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_SUBSANACIONES, e.getCause());
		}
	}

	/**
	 * Método que da de alta y asocia al hito actual del procedimiento un solicitud de realización de pago
	 * y aportación de documentación al expediente.
	 * @param poPago Objeto con los datos del pago.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static void altaSolicitudPago(Pago poPago, String entidad) throws ConsultaExcepcion{
		try{
			PagoDatos pago = new PagoDatos((PagoImpl)poPago);
			pago.nuevo(entidad);
		}catch(Exception e){
			logger.error("Error al dar de alta pago de tasas [altaSolicitudPago][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_PAGO, e.getCause());
		}
	}

	/**
	 * Método que devuelve una lista de objetos Pago asociados al hito actual del expediente.
	 * @return List ArrayList con la lista de Pago's
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static Pagos obtenerPagosHitoActual(String expediente, String entidad) throws ConsultaExcepcion{
		try{
			HitoEstadoDatos hitoActual = obtenerHitoEstado(null, expediente, entidad);
			return obtenerPagosHito(hitoActual.getGuid(), entidad);
		}catch(Exception e){
			logger.error("Error al obtener pagos de tasas de hito actual [obtenerPagosHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, e.getCause());
		}
	}

	/**
	 * Método que devuelve una lista de objetos Pago asociados al hito que llega como parámetro.
	 * @return List ArrayList con la lista de Pago's
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static Pagos obtenerPagosHito(String guidHito, String entidad) throws ConsultaExcepcion{
		try{
			PagoDatos pago = new PagoDatos();
			pago.setIdentificadorHito(guidHito);
			return pago.getPagosPorIdHito(entidad);
		}catch(Exception e){
			logger.error("Error al obtener pagos de tasas de hito [obtenerPagosHito][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, e.getCause());
		}
	}


	/**
	 * Método que devuelve todos los pagos de un expediente
	 * @param expediente Número de expediente
	 * @return Pagos Listado de pagos de un expediente
	 * @throws ConsultaExcepcion
	 */
	private static Pagos obtenerPagos(String expediente, String entidad) throws ConsultaExcepcion{
		try{
			PagoDatos pago = new PagoDatos();
			pago.setNumeroExpediente(expediente);
			return pago.getPagosPorExpediente(entidad);
		}catch(Exception e){
			logger.error("Error al obtener pagos de tasas de expediente [obtenerPagos][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_PAGOS, e.getCause());
		}
	}


	/**
	 * Método que da de alta y asocia al hito actual la información referente a una notificación
	 * en curso en el sistema de notificaciones de SIGEM.
	 * Este método es usado desde la tramitación para publicar que se ha realizado una notificación.
	 * Previa a su publicación la notificación se ha dado de alta en el servicio de notificaciones y
	 * el identificador devuelto por este sistema se pasa como atributo de la clase Notificacion.
	 * @param poNotificacion Datos de la notificación para su publicación.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static void altaNotificacion(ieci.tecdoc.sgm.ct.database.datatypes.Notificacion poNotificacion, String entidad) throws ConsultaExcepcion{
		try{
			NotificacionDatos notificacion = new NotificacionDatos((NotificacionImpl)poNotificacion);
			notificacion.nuevo(entidad);
		}catch(Exception e){
			logger.error("Error al dar de alta notificacion [altaNotificacion][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_INSERTAR_NOTIFICACION, e.getCause());
		}
	}

	/**
	 * Devuelve una lista de objetos Notificacion con los datos publicados de las notificaciones en curso
	 * para el hito actual.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones obtenerNotificionesHitoActual(String expediente, String entidad) throws ConsultaExcepcion{
		try{
			HitoEstadoDatos hitoActual = obtenerHitoEstado(null, expediente, entidad);
			NotificacionDatos notificacion = new NotificacionDatos();
			notificacion.setHitoId(hitoActual.getGuid());
			return notificacion.getNotificacionesHitoActual(entidad);
		}catch(Exception e){
			logger.error("Error al obtener notificaciones de hito actual [obtenerNotificionesHitoActual][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, e.getCause());
		}
	}

	/**
	 * Devuelve una lista de objetos Notificacion con los datos publicados de las notificaciones en curso
	 * para el hito actual.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones obtenerNotificiones(String expediente, String entidad) throws ConsultaExcepcion{
		try{
			NotificacionDatos notificacion = new NotificacionDatos();
			notificacion.setExpediente(expediente);
			return notificacion.getNotificacionesPorExpediente(entidad);
		}catch(Exception e){
			logger.error("Error al obtener notificaciones de expediente [obtenerNotificiones][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, e.getCause());
		}
	}

	/**
	 * Devuelve una lista de objetos Notificacion con los datos publicados de las notificaciones en curso
	 * para cuyo identificador se pasa como parámetro.
	 * @throws ConsultaExpedientesException En caso de producirse algún error.
	 */
	public static ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones obtenerNotificionesHito(String guidHito, String entidad) throws ConsultaExcepcion{
		try{
			NotificacionDatos notificacion = new NotificacionDatos();
			return notificacion.getNotificacionesHito(guidHito, entidad);
		}catch(Exception e){
			logger.error("Error al obtener notificaciones de hito [obtenerNotificionesHito][Excepcion]", e.fillInStackTrace());
			throw new ConsultaExcepcion(ConsultaCodigosError.EC_GET_NOTIFICACIONES, e.getCause());
		}
	}



	/******************************************************************
	 *************** METODOS PARA LOCALGIS *****************
	 ******************************************************************/

	/**
	 * Métido que publica información sobre el estado actual de un expediente desde LocalGIS
	 * @param idExpediente Identificador del expediente a actualizar
	 * @param estado Descripción del nuevo estado del expediente
	 * @return boolean True si se ha actualizado la información, false si no se ha podido
	 * @throws ConsultaExcepcion En caso de producirse algún error.
	 */
	public static boolean actualizarEstadoLocalGIS(String idExpediente, String estado, String entidad) throws ConsultaExcepcion{
		try{
			HitoExpedienteImpl hito = new HitoExpedienteImpl();
			hito.setNumeroExpediente(idExpediente);
			hito.setGuid(new Guid().toString());
			hito.setFecha(new Date());
			hito.setDescripcion(estado);
			establecerHitoActual(hito, true, entidad);
			return true;
		}catch(Exception e){
			logger.error("Error al actualizar estado de expedientes para LocalGIS [actualizarEstadoLocalGIS][Excepcion]", e.fillInStackTrace());
			return false;
		}
	}

	/**
	 * Método que publica información de un expediente desde LocalGIS
	 * @param idExpediente Identificador del expediente
	 * @param nif Documento de identidad del solicitante
	 * @param tipoExpediente Descripción del tipo de expediente
	 * @param estado Descripción del estado del expediente
	 * @param fecha Fecha de alta del expediente
	 * @return boolean True si se ha actualizado la información, false si no se ha podido
	 * @throws ConsultaExcepcion
	 */
	public static boolean publicarExpedienteLocalGIS(String idExpediente, String nif, String tipoExpediente, String estado, Date fecha, String entidad) throws ConsultaExcepcion{
		boolean bInt = false, bExp = false, bHito = false;
		InteresadoImpl interesado = new InteresadoImpl();
		ExpedienteImpl expediente = new ExpedienteImpl();

		try{
			interesado.setNIF(nif);
			interesado.setNumeroExpediente(idExpediente);
			interesado.setPrincipal("S");
			try {
				nuevoInteresado(interesado, entidad);
				bInt = true;
			} catch(Exception ex) { }

			expediente.setNumero(idExpediente);
			expediente.setFechaInicio(fecha);
			expediente.setProcedimiento(tipoExpediente);
			expediente.setAportacion("N");
			expediente.setCodigoPresentacion("1");
			expediente.setEstado("0");
			try {
				nuevoExpediente(expediente, null, entidad);
				bExp = true;
			} catch(ConsultaExcepcion ex) {
				eliminarInteresado(interesado, entidad);
				throw ex;
			}

			HitoExpedienteImpl hito = new HitoExpedienteImpl();
			hito.setNumeroExpediente(idExpediente);
			hito.setGuid(new Guid().toString());
			hito.setFecha(new Date());
			hito.setDescripcion(estado);
			establecerHitoActual(hito, false, entidad);
			bHito = true;
		}catch(Exception e){
			if (bExp && !bHito) {
				logger.error("Error al publicar expediente para LocalGIS [publicarExpedienteLocalGIS][Excepcion]", e.fillInStackTrace());
				eliminarExpediente(expediente, entidad);
				eliminarInteresado(interesado, entidad);
			}
		}
		return bInt && bExp && bHito;
	}
}
