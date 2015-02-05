package auditoria.model;

import gcontrol.vos.GrupoVO;
import gcontrol.vos.RolVO;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import se.usuarios.AppUser;
import se.usuarios.TipoUsuario;
import auditoria.ArchivoDetails;
import auditoria.ArchivoLogLevels;
import auditoria.AuditoriaConstants;
import auditoria.db.ICritAccionesDBEntity;
import auditoria.db.ICritUsuariosDBEntity;
import auditoria.db.IDatosTrazaDBEntity;
import auditoria.db.ITrazaDBEntity;
import auditoria.logger.LoggingEvent;
import auditoria.vos.BusquedaPistasVO;
import auditoria.vos.CritAccionVO;
import auditoria.vos.CritUsuarioVO;
import auditoria.vos.TrazaVO;

import common.bi.GestionAuditoriaBI;
import common.bi.ServiceBase;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.util.TypeConverter;

/**
 * Implementacion con los servicio s de negocio para el módulo de auditoría.
 */
public class GestionAuditoriaBImpl extends ServiceBase implements
		GestionAuditoriaBI {

	/** Entidad para su uso en el servicio */
	private ITrazaDBEntity trazaDBEntity = null;
	private IDatosTrazaDBEntity datosTrazaDBEntity = null;
	private ICritAccionesDBEntity critAccionesDBEntity = null;
	private ICritUsuariosDBEntity critUsuariosDBEntity = null;

	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param userVO
	 *            Usuario del servicio
	 * @param tm
	 *            TransactionManager
	 */
	public GestionAuditoriaBImpl(ITrazaDBEntity trazaDBE,
			IDatosTrazaDBEntity datosTrazaDBE,
			ICritAccionesDBEntity critAccionesDBE,
			ICritUsuariosDBEntity critUsuariosDBE) {
		this.trazaDBEntity = trazaDBE;
		this.datosTrazaDBEntity = datosTrazaDBE;
		this.critAccionesDBEntity = critAccionesDBE;
		this.critUsuariosDBEntity = critUsuariosDBE;
	}

	public int getLogLevel(AppUser user) {
		int lowestLevel = Integer.MAX_VALUE;
		int globalLevel = Integer.MAX_VALUE;
		int superuserLevel = Integer.MAX_VALUE;
		int rolLogLevel = Integer.MAX_VALUE;
		int groupLogLevel = Integer.MAX_VALUE;
		int userLogLevel = Integer.MAX_VALUE;

		// Obtenemos los grupos del usuario
		Iterator grupos = user.getGroupList().iterator();

		while (grupos.hasNext()) {
			GrupoVO g = (GrupoVO) grupos.next();

			groupLogLevel = critUsuariosDBEntity.getGroupLogLevel(g.getId());
			if (groupLogLevel != Integer.MIN_VALUE)
				if (groupLogLevel < lowestLevel)
					lowestLevel = groupLogLevel;
		}

		// Obtenemos los roles del usuario
		Iterator roles = user.getRolesList().iterator();

		while (roles.hasNext()) {
			RolVO r = (RolVO) roles.next();

			rolLogLevel = critUsuariosDBEntity.getRolLogLevel(r.getId());
			if (rolLogLevel != Integer.MIN_VALUE)
				if (rolLogLevel < lowestLevel)
					lowestLevel = groupLogLevel;
		}

		// Comparamos el menor de los niveles de los grupos con el nivel
		// específico del usuario
		userLogLevel = critUsuariosDBEntity.getUserLogLevel(user.getId());
		if (userLogLevel != Integer.MAX_VALUE)
			if (userLogLevel < lowestLevel)
				lowestLevel = userLogLevel;

		// Comparamos el menor de los niveles de los grupos y del usuario con el
		// nivel global de la aplicacion
		globalLevel = this.getGlobalLogLevel();
		if (globalLevel != Integer.MIN_VALUE)
			if (globalLevel <= lowestLevel)
				lowestLevel = globalLevel;

		// Si el usuario es un administrador comprobar si tiene nivel de
		// auditoría
		if (TipoUsuario.ADMINISTRADOR.equals(user.getUserType())) {
			superuserLevel = this.getGlobalAdmLogLevel();
			if (superuserLevel != Integer.MIN_VALUE)
				if (superuserLevel <= lowestLevel)
					lowestLevel = superuserLevel;
		}

		return lowestLevel;
	}

	public int getGlobalLogLevel() {
		return critUsuariosDBEntity
				.getGroupLogLevel(CritUsuarioVO.GLOBAL_GROUP);
	}

	public int getGlobalAdmLogLevel() {
		return critUsuariosDBEntity
				.getGroupLogLevel(CritUsuarioVO.GLOBAL_GROUP_ADM);
	}

	public Collection getModules() {
		return ArchivoModules.getModuleNames();
	}

	public Collection getActions() {
		return ArchivoActions.getActions();
	}

	public Collection getActions(int module) {
		return ArchivoActions.getActions(module);
	}

	/**
	 * Obtiene un listado de las pistas de auditoría para el filtro de búsqueda
	 * aplicado
	 * 
	 * @param busqueda
	 *            Filtro de búsqueda aplicado
	 * @param pageInfo
	 *            Informacion de paginacion de la tabla de resultados
	 * @return Listado de las pistas de auditoría que cumplen el filtro.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getPistas(BusquedaPistasVO busqueda, PageInfo pageInfo)
			throws TooManyResultsException {
		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.AUDITORIA_MODULE_CONSULTAS);
		Locale locale = getServiceClient().getLocale();

		String grupo = null;
		if (busqueda.getGrupo() != null
				&& !busqueda.getGrupo().equalsIgnoreCase(
						AuditoriaConstants.TODOS))
			grupo = getGestionControlUsusarios().getGrupo(busqueda.getGrupo())
					.getNombre();
		else
			grupo = "Todos";

		// registro de la creacion
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_CONSULTA_AUDITORIA,
				null)
				.addDetalle(
						locale,
						ArchivoDetails.AUDITORIA_MODULO,
						busqueda.getModulo() != -1 ? ArchivoModules
								.getModuleName(busqueda.getModulo()) : "Todos")
				.addDetalle(
						locale,
						ArchivoDetails.AUDITORIA_ACTION,
						busqueda.getAccion() != -1 ? ArchivoActions
								.getActionName(busqueda.getAccion()) : "Todas")
				.addDetalle(locale, ArchivoDetails.AUDITORIA_GRUPO, grupo)
				.addDetalle(locale, ArchivoDetails.AUDITORIA_IP,
						busqueda.getIp())
				.addDetalle(locale, ArchivoDetails.AUDITORIA_FECHA_INI,
						TypeConverter.toString(busqueda.getFechaIni()))
				.addDetalle(locale, ArchivoDetails.AUDITORIA_FECHA_FIN,
						TypeConverter.toString(busqueda.getFechaFin()));

		return trazaDBEntity.getPistas(busqueda, pageInfo);
	}

	public Collection getActionsWithLevels() {
		return critAccionesDBEntity.getActionsWithLevels(ArchivoActions
				.getActions());
	}

	public Collection getActionsWithLevels(int module) {
		return critAccionesDBEntity.getActionsWithLevels(ArchivoActions
				.getActions(module));
	}

	public Collection getLogLevels() {
		Locale locale = getServiceClient().getLocale();
		return ArchivoLogLevels.getLogLevelNames(locale);
	}

	public TrazaVO getPista(String id) {
		return trazaDBEntity.getPista(id);
	}

	public List getDatosPista(String id) {
		return datosTrazaDBEntity.getDatosPista(id);
	}

	public void setActionLogLevel(int module, int action, int logLevel) {
		CritAccionVO ca = critAccionesDBEntity.getAction(-1, action);

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_ACTION);
		Locale locale = getServiceClient().getLocale();

		if (ca != null) {
			if (logLevel != ArchivoLogLevels.LOGLEVEL_NONE)
				critAccionesDBEntity
						.setActionLogLevel(module, action, logLevel);
			else
				critAccionesDBEntity.deleteActionLogLevel(module, action);
		} else
			critAccionesDBEntity.insertActionLogLevel(module, action, logLevel);

		// registro de la creacion
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ACTION, "" + action)
				.addDetalle(locale, ArchivoDetails.AUDITORIA_ACTION,
						ArchivoActions.getActionName(action))
				.addDetalle(locale, ArchivoDetails.AUDITORIA_NIVEL_LOG,
						ArchivoLogLevels.getLogLevelName(locale, logLevel));
	}

	public Collection getUsersWithLevels() {
		return critUsuariosDBEntity.getUsersWithLevels();
	}

	public Collection getGroupsWithLevels() {
		return critUsuariosDBEntity.getGroupsWithLevels();
	}

	public void setGroupLogLevel(String id, int logLevel) {
		CritUsuarioVO cu = critUsuariosDBEntity.getGroup(id, 1);

		// Creamos el evento de Loggin de auditoria
		LoggingEvent event = getLogginEvent(ArchivoActions.AUDITORIA_MODULE_MODIFICACION_LOGLEVEL_GROUP);
		Locale locale = getServiceClient().getLocale();

		if (cu != null) {
			if (logLevel != ArchivoLogLevels.LOGLEVEL_NONE)
				critUsuariosDBEntity.setGroupLogLevel(id, logLevel, 1);
			else
				critUsuariosDBEntity.deleteGroupLogLevel(id, 1);
		} else {
			if (logLevel != ArchivoLogLevels.LOGLEVEL_NONE) {
				critUsuariosDBEntity.insertGroupLogLevel(id, logLevel, 1);
			}
		}

		// registro de la creacion
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_GROUP, id)
				.addDetalle(locale, ArchivoDetails.AUDITORIA_GRUPO,
						getGestionControlUsusarios().getGrupo(id).getNombre())
				.addDetalle(locale, ArchivoDetails.AUDITORIA_NIVEL_LOG,
						ArchivoLogLevels.getLogLevelName(locale, logLevel));
	}

	public void setUserLogLevel(String id, int logLevel) {
		critUsuariosDBEntity.setUserLogLevel(id, logLevel);
	}

	/**
	 * Metodo para la creacion de un evento específico para el modulo de
	 * préstamos
	 */
	public LoggingEvent getLogginEvent(int action) {
		LoggingEvent le = new LoggingEvent(ArchivoModules.AUDITORIA_MODULE,
				action, getServiceClient(), false);

		// Lo añadimos a la pila
		getLogger().add(le);

		return le;
	}

	/**
	 * Obtiene un listado de los objetos de auditoria disponibles en la
	 * aplicacion
	 * 
	 * @return Listado de objetos auditables en la aplicacion
	 */
	public Collection getObjects() {
		return ArchivoObjects.getObjects();
	}

	/**
	 * Obtiene todas las pistas de auditoria para un determinado objeto
	 * 
	 * @param type
	 *            Tipo del objeto
	 * @param id
	 *            Identificador del objeto
	 * @return Listado de las pistas asociadas a ese objeto
	 */
	public Collection getPistasXObject(int type, String id) {
		return trazaDBEntity.getPistasXObject(type, id);
	}

	/**
	 * Elimina un elemento del conjunto de elementos que están siendo auditados
	 * 
	 * @param tipo
	 *            Tipo de elemento auditado. En principio usuario, grupo o rol
	 * @param id
	 *            Identificador del elemento auditado
	 */
	public void removeTipoAuditado(int tipo, String[] id) {
		iniciarTransaccion();
		critUsuariosDBEntity.deleteLogLevel(tipo, id);
		commit();
	}
}
