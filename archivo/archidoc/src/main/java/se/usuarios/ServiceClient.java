package se.usuarios;

import ieci.core.db.DbEngine;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.MultiEntityUtil;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DataSourceEngine;
import common.exceptions.ArchivoModelException;
import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 * Clase que almacena la información del cliente de los servicios de negocio.
 */
public class ServiceClient extends GenericUserInfo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Propiedades del cliente del servicio. */
	private Map properties = null;

	/** Engine de acceso a base de datos */
	private DbEngine engine = null;

	/** Entidad */
	private String entity = null;

	/** Locale del usuario */
	private Locale locale = null;

	/**
	 * Constructor.
	 */
	public ServiceClient() {
		super();
		this.properties = new HashMap();
	}

	/**
	 * Obtiene las propiedades del cliente de servicio.
	 * 
	 * @return Propiedades.
	 */
	public Map getProperties() {
		return properties;
	}

	/**
	 * Crea un cliente de servicio con la información extraída del usuario de la
	 * aplicación.
	 * 
	 * @param user
	 *            Usuario de la aplicación.
	 * @return Cliente de servicio.
	 */
	public static ServiceClient create(AppUser user) {
		ServiceClient sc = new ServiceClient();

		if (user != null) {
			sc.setId(user.getId());
			sc.setUserType(user.getUserType());
			sc.setIp(user.getIp());
			sc.setLogLevel(user.getLogLevel());
			sc.setPermissions(user.getPermissions());
			sc.setCustodyArchiveList(user.getCustodyArchiveList());
			sc.setOrganization(user.getOrganization());
			sc.setDependentOrganizationList(user.getDependentOrganizationList());
			sc.setGroupList(user.getGroupList());
			sc.setAcls(user.getAcls());
			sc.setEngine(user.getEngine());
			sc.setEntity(user.getEntity());
			sc.setLocale(user.getLocale());
			sc.setMostrarPaisProvinciaBackUp(user.isMostrarPaisProvincia());
			sc.setMostrarArchivoCodigoClasificadoresBackUp(user
					.isMostrarArchivoCodigoClasificadores());
		}

		return sc;
	}

	public static ServiceClient createWithEntity(String entity)
			throws ArchivoModelException {
		ServiceClient sc = new ServiceClient();

		DbEngine engine = null;

		String dataSourceName = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
				.getDataSourceName();

		String dbFactoryClass = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
				.getDBFactoryClass();

		engine = getMultiEntityDbEngine(dataSourceName, entity, dbFactoryClass);

		// Establecer la engine de base de datos
		if (engine != null) {
			sc.setEngine(engine);
		}

		sc.setEntity(entity);

		return sc;
	}

	/**
	 * Obtiene un DBEngine a partir de un dataSource
	 * 
	 * @param dataSourceName
	 * @param dbFactoryClass
	 * @return
	 */
	protected static DbEngine getDBEngine(String dataSourceName,
			String dbFactoryClass) {
		DbEngine engine = null;
		engine = DataSourceEngine.getDbEngine(dataSourceName, dbFactoryClass);
		return engine;
	}

	public static DbEngine getDBEngine(String dataSourceName) {
		return getDBEngine(dataSourceName, null);
	}

	public static DbEngine getDBEngineWithFactory(String dataSourceName,
			String dbFactoryClass) {
		return getDBEngine(dataSourceName, dbFactoryClass);
	}

	protected static DbEngine getMultiEntityDbEngine(String dataSourceName,
			String entity, String dbFactoryClass) {

		if (StringUtils.isNotEmpty(entity)) {
			dataSourceName = MultiEntityUtil.composeDsName(dataSourceName,
					entity);
		}

		try {
			if (dataSourceName != null) {
				Context context = new InitialContext();
				if (context != null) {
					DataSource dataSource = (DataSource) context
							.lookup(dataSourceName);
					if (dataSource != null) {
						return getDBEngine(dataSourceName, dbFactoryClass);
					}
				}
			}
		} catch (NamingException e) {
			throw new ArchivoModelException(DbEngine.class, "createWithEntity",
					e.getMessage());
		} catch (ArchivoModelException e) {
			throw e;
		}

		return null;
	}

	public static DbEngine getMultiEntityDbEngine(String dataSourceName,
			String entity) {
		return getMultiEntityDbEngine(dataSourceName, entity, null);
	}

	public static DbEngine getMultiEntityDbEngineWithFactory(
			String dataSourceName, String entity, String dbFactoryClass) {
		return getMultiEntityDbEngine(dataSourceName, entity, dbFactoryClass);
	}

	// protected static DbEngine getDBEngine1(String dataSourceName, String
	// dbFactoryClass){
	// // Obtener el datasource
	// DbEngine engine = null;
	//
	//
	// // Añadirle al datasource la entidad
	// if (StringUtils.isNotEmpty(entity))
	// dataSourceName = MultiEntityUtil.composeDsName(dataSourceName, entity);
	//
	// try {
	// if (ConfigConstants.getInstance().getEntidadRequerida()) {
	// // Buscar el datasource en el contexto
	// if (dataSourceName != null) {
	// Context context = new InitialContext();
	// if (context != null) {
	// DataSource dataSource = (DataSource) context.lookup(dataSourceName);
	// if (dataSource!=null) {
	// engine = new DbEngine(dataSource,dbFactoryClass);
	// }
	// }
	// }
	// } else {
	// engine = DataSourceEngine.getDbEngine(dataSourceName,dbFactoryClass);
	// }
	// } catch (NamingException e) {
	// throw new ArchivoModelException(DbEngine.class, "createWithEntity", e
	// .getMessage());
	// } catch (ArchivoModelException e){
	// throw e;
	// }
	//
	// return engine;
	// }
	//
	// public static DbEngine getDBEngine(String dataSourceName){
	// return getDBEngine(dataSourceName,null,null);
	// }
	// public static DbEngine getDBMultiEntityEngine(String dataSourceName,
	// String entity) {
	// return getDBEngine(dataSourceName, entity, null);
	// }
	//
	// public static DbEngine getDBMultiEntityEngine(String dataSourceName,
	// String entity, String dbFactoryClass) {
	// return getDBEngine(dataSourceName, entity, dbFactoryClass);
	// }
	//
	// public static DbEngine getDBEngine(String dataSourceName,String
	// dbFactoryClass) {
	// return getDBEngine(dataSourceName, null, dbFactoryClass);
	// }

	/**
	 * @return el engine
	 */
	public DbEngine getEngine() {
		return engine;
	}

	/**
	 * @param engine
	 *            el engine a establecer
	 */
	public void setEngine(DbEngine engine) {
		this.engine = engine;
	}

	/**
	 * @return el entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            el entity a establecer
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String toString() {
		return new StringBuffer()
				.append("<ServiceClient>")
				.append(Constants.NEWLINE)
				.append("  <Id>")
				.append(getId() != null ? getId() : "")
				.append("</Id>")
				.append(Constants.NEWLINE)
				.append("  <UserType>")
				.append(getUserType())
				.append("</UserType>")
				.append(Constants.NEWLINE)
				.append("  <IP>")
				.append(getIp() != null ? getIp() : "")
				.append("</IP>")
				.append(Constants.NEWLINE)
				.append("  <LogLevel>")
				.append(getLogLevel())
				.append("</LogLevel>")
				.append(Constants.NEWLINE)
				.append("  <Permissions>")
				.append(getPermissions() != null ? ArrayUtils.join(
						getPermissions(), ",") : "").append("</Permissions>")
				.append(Constants.NEWLINE).append("</ServiceClient>")
				.toString();
	}

	public String getDBFactoryClass() {
		if (engine != null) {
			return engine.getDbFactoryClass();
		}
		return null;
	}

}
