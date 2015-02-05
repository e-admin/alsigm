package com.ieci.tecdoc.isicres.desktopweb.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.Keys;

public class BBDDUtils {

	private static Logger _logger = Logger.getLogger(BBDDUtils.class);

	public static Connection getConnection(String entidad)
			throws NamingException, SQLException {
		try {
			Context ctx = new InitialContext();
			DataSource ds = null;

			if (_logger.isDebugEnabled()) {
				_logger
						.debug("Intentando conexion a BBDD "
								+ Configurator
										.getInstance()
										.getProperty(
												ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME));
			}
			String jndiName = Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME);

			_logger.debug("jndiName = " + jndiName);

			if (entidad.equals(Keys.KEY_BUILD_TYPE_INVESICRES)) {
				ds = (DataSource) ctx
						.lookup(jndiName);
			} else {
				StringBuffer sb = new StringBuffer(jndiName);
				//comprobamos si la cadena termina con GUION BAJO, si no se lo añadimos
				if(!StringUtils.endsWithIgnoreCase(jndiName, Keys.GUION_BAJO)){
					sb.append(Keys.GUION_BAJO);
				}
				sb.append(entidad);
				ds = (DataSource) ctx.lookup(sb.toString());
			}

			return ds.getConnection();
		} catch (NamingException e1) {
			_logger
					.fatal(
							"Imposible crear una conexion a la base de datos."
									+ Configurator
											.getInstance()
											.getProperty(
													ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME),
							e1);
			throw e1;
		} catch (SQLException e1) {
			_logger
					.fatal(
							"Imposible crear una conexion a la base de datos."
									+ Configurator
											.getInstance()
											.getProperty(
													ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME),
							e1);
			throw e1;
		}
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// Ignored
			}
		}
	}

	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// Ignored
			}
		}
	}

	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// Ignored
			}
		}
	}

}