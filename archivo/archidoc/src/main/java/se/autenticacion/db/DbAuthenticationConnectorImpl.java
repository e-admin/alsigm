package se.autenticacion.db;

import ieci.core.base64.Base64Util;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import se.autenticacion.AuthenticationConnector;
import se.autenticacion.AuthenticationConnectorException;
import se.autenticacion.UserInfoImpl;
import util.MultiEntityUtil;

import common.MultiEntityConstants;
import common.exceptions.DBException;
import common.model.UserInfo;

import es.archigest.framework.facilities.security.SecurityConstants;
import es.archigest.framework.facilities.security.exceptions.UnknownUserException;
import es.archigest.framework.facilities.security.exceptions.WrongPasswordException;

/**

 CREATE TABLE AOUSR ( 
 ID   			VARCHAR (64)  NOT NULL, 
 DIRECCION        	VARCHAR (254), 
 EMAIL            	VARCHAR (254), 
 NOMBRE			VARCHAR (64),
 APELLIDOS		VARCHAR (128),
 NOMBREUSUARIO		VARCHAR (64) NOT NULL,
 PASSWORD		VARCHAR (64) NOT NULL ) ; 

 -- Definicion en el archivo-cfg

 <Sistemas_Gestores_Usuarios>
 <Sistema>
 <Id>DB</Id>
 <Nombre>Usuarios de base de datos</Nombre>
 <Clase>se.autenticacion.db.DbAuthenticationConnectorImpl</Clase>  
 <init-param>
 <param-name>DATASOURCE_NAME</param-name>
 <param-value>java:comp/env/jdbc/archivoDS_{0}</param-value>
 </init-param>      
 </Sistema>
 </Sistemas_Gestores_Usuarios>

 */

/**
 * Implementación del interfaz AuthenticationConnector para que obtenga los
 * datos de una base de datos
 */
public class DbAuthenticationConnectorImpl implements AuthenticationConnector {
	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(DbAuthenticationConnectorImpl.class);

	/** Propiedades del conector */
	private final String DATASOURCE_NAME = "DATASOURCE_NAME";

	/** Definición de columnas */
	private final String COL_ID = "ID";
	private final String COL_ADDRESS = "DIRECCION";
	private final String COL_EMAIL = "EMAIL";
	private final String COL_NAME = "NOMBRE";
	private final String COL_SURNAME = "APELLIDOS";
	private final String COL_USERNAME = "NOMBREUSUARIO";
	private final String COL_PASSWORD = "PASSWORD";

	/** Nombre de la tabla de usuarios */
	private static final String TABLE_NAME = "AOUSR";

	/** Objeto de conexión */
	DbConnectionConfig dbConnectionConfig = null;

	/** Objeto para almacenar la conexión */
	Connection connection = null;

	/**
	 * Constructor.
	 */
	public DbAuthenticationConnectorImpl() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws AuthenticationConnectorException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params)
			throws AuthenticationConnectorException {
		try {
			// Obtener el nombre del datasource
			String dataSource = params.getProperty(DATASOURCE_NAME);
			if ((dataSource == null) || ("".equals(dataSource))) {
				throw new AuthenticationConnectorException(
						"Datasource no definido");
			}

			String entity = null;

			// Buscar la propiedad entidad
			if ((params != null)
					&& (params.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
				entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
			}

			// Obtener el dataSource de autenticación
			if (entity != null)
				dataSource = MultiEntityUtil.composeDsName(dataSource, entity);

			// Obtener el objeto de conexión
			dbConnectionConfig = new DbConnectionConfig(dataSource, null, null);
		} catch (Exception e) {
			logger.error(e);
			throw new AuthenticationConnectorException(e.getMessage());
		}
	}

	/**
	 * Realiza la autenticación del usuario.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 * @param password
	 *            Clave del usuario.
	 * @param ip
	 *            Dirección IP del usuario.
	 * @return Identificador único del usuario.
	 * @throws LoginException
	 *             si se produce algún error.
	 */
	public String authenticate(String username, String password, String ip)
			throws LoginException {
		logger.warn(" PRUEBA de autenticaci\u00F3n del usuario [" + username
				+ "]");

		String ret = null;
		UserInfo userInfo = getUserInfo(COL_USERNAME, username, true);

		if (userInfo != null) {

			// Codificamos la password introducida por el usuario para
			// compararla con la de la base de datos
			String encryptedPwd = Base64Util.encodeString(password);
			UserPassInfoImpl userPassInfoImpl = (UserPassInfoImpl) userInfo;
			if (encryptedPwd.equals(userPassInfoImpl.getPassword()))
				ret = userPassInfoImpl.getOrganizationUserId();
			else {
				WrongPasswordException ex = new WrongPasswordException(
						username, password);
				throw ex;
			}
		} else {
			UnknownUserException ex = new UnknownUserException(
					SecurityConstants.USERNAME);
			ex.setContextValue(SecurityConstants.USERNAME, username);
			throw ex;
		}

		return ret;
	}

	private UserInfo getUserInfo(String col, String value, boolean getPassword)
			throws NotImplementedException {

		List usuarios = new ArrayList();
		ResultSet rs = null;
		try {

			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a getUserInfo(" + col + " = " + value
						+ ")");

			try {
				StringBuffer query = new StringBuffer().append("SELECT ")
						.append(COL_ID).append(", ").append(COL_ADDRESS)
						.append(", ").append(COL_EMAIL).append(", ")
						.append(COL_NAME).append(", ").append(COL_SURNAME)
						.append(", ").append(COL_USERNAME);

				if (getPassword)
					query.append(", ").append(COL_PASSWORD);

				query.append(" FROM ").append(TABLE_NAME).append(" WHERE ")
						.append(col).append(" = '").append(value).append("'");

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				usuarios = dbToUsuarioVOList(rs, getPassword);
			} catch (Exception e) {
				logger.error("Error al leer los usuarios coincidentes con "
						+ col + " = " + value, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}

			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}

		return usuarios.size() > 0 ? (UserInfo) usuarios.get(0) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.autenticacion.AuthenticationConnector#getUserInfo(java.lang.String)
	 */
	public UserInfo getUserInfo(String idUser) throws NotImplementedException {

		return getUserInfo(COL_ID, idUser, false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.autenticacion.AuthenticationConnector#findUserByName(java.lang.String)
	 */
	public List findUserByName(String name) throws NotImplementedException {
		List usuarios = new ArrayList();
		ResultSet rs = null;

		try {

			connection = DbConnection.open(dbConnectionConfig);

			if (logger.isDebugEnabled())
				logger.debug("Llamada a findUserByUsername(" + name + ")");

			try {
				StringBuffer query = new StringBuffer().append("SELECT ")
						.append(COL_ID).append(", ").append(COL_ADDRESS)
						.append(", ").append(COL_EMAIL).append(", ")
						.append(COL_NAME).append(", ").append(COL_SURNAME)
						.append(", ").append(COL_USERNAME).append(" FROM ")
						.append(TABLE_NAME).append(" WHERE ").append("UPPER(")
						.append(COL_NAME).append(") like '%")
						.append(name.toUpperCase()).append("%'").append(" OR ")
						.append(COL_SURNAME).append(" like '%")
						.append(name.toUpperCase()).append("%'");

				if (logger.isDebugEnabled())
					logger.debug("Query: " + query.toString());

				Statement statement = connection.createStatement();
				rs = statement.executeQuery(query.toString());

				usuarios = dbToUsuarioVOList(rs, false);
			} catch (Exception e) {
				logger.error("Error al leer los usuarios coincidentes con  "
						+ name, e);
				throw new DBException(e.toString());
			} finally {
				closeResultSet(rs);
			}

			DbConnection.close();

		} catch (Exception e) {

			try {
				DbConnection.ensureClose(e);
			} catch (Exception e1) {

			}

		}

		return usuarios.size() > 0 ? usuarios : null;
	}

	/**
	 * Cierra el conjunto de resultados de una consulta realizada.
	 * 
	 * @param rs
	 *            Resultados que deseamos cerrar
	 */
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				Statement stmt = rs.getStatement();

				// Cerrar el ResultSet
				rs.close();

				// Cerrar el Statement
				if (stmt != null)
					stmt.close();
			}
		} catch (SQLException e) {
			logger.error("Error al cerrar el ResultSet", e);
		}
	}

	/**
	 * Recoge los resultados de un resulset y los almacena en una lista
	 * 
	 * @param rs
	 *            Resultados de la consulta
	 * @param getPassword
	 *            Especifica si se devuelve o no la contraseña del usuario
	 * @return Lista de usuarios
	 * @throws Exception
	 *             Si se produce alguna excepción
	 */
	public ArrayList dbToUsuarioVOList(ResultSet rs, boolean getPassword)
			throws Exception {
		ArrayList ret = new ArrayList();
		UserInfoImpl userInfo = null;

		if (rs != null) {
			while (rs.next()) {

				String externalUserId = rs.getString(COL_ID);
				String organizationUserId = rs.getString(COL_ID);
				String address = rs.getString(COL_ADDRESS);
				String email = rs.getString(COL_EMAIL);
				String name = rs.getString(COL_NAME);
				String surname = rs.getString(COL_SURNAME);
				String username = rs.getString(COL_USERNAME);
				String password = null;

				if (getPassword)
					password = rs.getString(COL_PASSWORD);

				userInfo = new UserPassInfoImpl(externalUserId,
						organizationUserId, address, email, name, surname,
						username, password);
				ret.add(userInfo);
			}
		}

		return ret;
	}

}
