package es.ieci.tecdoc.isicres.admin.core.db;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.Configurator;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

public class DBSessionManager {

	private static final Logger logger = Logger.getLogger(DBSessionManager.class);

	public static Connection getSession() throws Exception {

		String idEntity = MultiEntityContextHolder.getEntity();

		if(logger.isDebugEnabled()){
			StringBuffer sb = new StringBuffer();
			sb.append("idEntity: [").append(idEntity).append("]");
			logger.debug(sb.toString());
		}


		String jndiName = Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME);

		if(StringUtils.isNotEmpty(idEntity)){
			//Entidad #SIGEM

			//comprobamos si el jdni termina en guión bajo, sino termina en guión bajo se añade
			if(!StringUtils.endsWithIgnoreCase(jndiName, ISicresKeys.GUION_BAJO)){
				jndiName = jndiName + ISicresKeys.GUION_BAJO + idEntity;
			}else{
				jndiName = jndiName + idEntity;
			}

			if(logger.isDebugEnabled()){
				StringBuffer sb = new StringBuffer();
				sb.append("Multientidad - jndiName [").append(jndiName)
						.append("]");
				logger.debug(sb.toString());
			}
		}

		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup(jndiName);
		Connection con = ds.getConnection();

		return con;
	}
}
