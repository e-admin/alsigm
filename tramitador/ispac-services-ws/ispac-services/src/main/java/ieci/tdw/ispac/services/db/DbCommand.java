package ieci.tdw.ispac.services.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

/**
 * Patrón command para la ejecución de sentencias SQL.
 *
 */
public abstract class DbCommand {
	
	/** Contexto de cliente. */
	private IClientContext ctx = null;
	

	/**
	 * Constructor.
	 * @param context Contexto de cliente.
	 */
	public DbCommand(IClientContext context) {
		this.ctx = context;
	}

	/**
	 * Realiza la consulta contra la base de datos.
	 * @param cnt Conexión con la base de datos.
	 * @return Resultado de la consulta.
	 * @throws ISPACException si ocurre algún error.
	 */
	public abstract Object logic(DbCnt cnt) throws ISPACException;
	
	/**
	 * Ejecuta la consulta contra la base de datos cerrando la conexión.
	 * @return Resultado de la consulta.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Object exec() throws ISPACException {
		DbCnt cnt = null;
		try {
			cnt = ctx.getConnection();
			return logic(cnt);
		} finally {
			ctx.releaseConnection(cnt);
		}
	}
}
