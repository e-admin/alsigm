package ieci.tdw.ispac;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import org.apache.log4j.Logger;

public abstract class ScriptBase {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ScriptBase.class); 

	
	protected static void checkArguments(String[] args) {
		if ((args == null) || (args.length != 4)) {
			logger.error("Argumentos incorrectos (driverClassName url username password).");
			System.exit(1);
		}

		try {
			Class.forName(args[0]);
		} catch (ClassNotFoundException cnfe) {
			logger.error("Driver JDBC '" + args[0] + "' no encontrado", cnfe);
			System.exit(1);
		}
	}
	
	protected static ClientContext getClientContext(String [] args) throws ISPACException {
		
		Connection cnt = null;

		try {
			
			logger.info("Conectando con: driverClassName=[" +  args[0] 
					+ "] url=[" + args[1] 
					+ "] username=[" + args[2] + "]");
			
			cnt = DriverManager.getConnection(args[1], args[2], args[3]);
		} catch (SQLException se) {
			logger.error("No se ha establecido la conexion con la BD - ", se);
			System.exit(1);
		}

		DbCnt dbCnt = new DbCnt();
		dbCnt.setConnection(cnt);

		ClientContext context = new ClientContext(dbCnt);
		context.setAPI(new InvesflowAPI(context));
		context.setLocale(new Locale("es","ES"));
		
		//context.setUserName("SYSSUPERUSER");
		context.setUser(new ScriptUser("SYSSUPERUSER"));
		
		return context;
	}

}
