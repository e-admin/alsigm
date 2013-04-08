package ieci.tecdoc.sgm.pe.database;
/*
 * $Id: NumeroSecuenciaAutoLiquidacion3.java,v 1.2.2.2 2008/03/14 11:22:22 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbSelectStatement;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.pe.database.exception.DbCodigosError;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;

import org.apache.log4j.Logger;

public class NumeroSecuenciaAutoLiquidacion3 {

	/**
	 * Instancia del logger.
	 */
	private static final Logger logger = Logger
			.getLogger(NumeroSecuenciaAutoLiquidacion3.class);

	private static final String TABLE_NAME = "sgm_pe_al3nsec";

	private static final String CN_MODELO = "modelo";

	private static final String CN_NUMSEC = "numsec";

	private static final String CN_MINIMO = "minimo";

	private static final String CN_MAXIMO = "maximo";

	private static final String ALL_COLUMN_NAMES = CN_MODELO + ", " + CN_NUMSEC + "," + CN_MINIMO
			+ "," + CN_MAXIMO;

	/**
	 * Indica si al alcanzar el máximo permito la secuencia debe
	 * empezar desde el mínimo.
	 */
	private static final boolean RESET_SEQUENCE = true;

	private NumeroSecuenciaAutoLiquidacion3(){
		super();
	}

	public static long obtenerIdentificador(String pcModelo, String entidad) throws DbExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo número de secuencia...");
		}

		DbConnection dbConn = new DbConnection();
		double retorno = 0;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Abriendo conexión a base de datos...");
			}
//			dbConn.open(Configuracion.getDatabaseConnection());
			dbConn.open(DBSessionManager.getSession(entidad));

			synchronized (NumeroSecuenciaAutoLiquidacion3.class) {
				dbConn.beginTransaction();

				retorno = getNextNumSec(dbConn, pcModelo);

				dbConn.endTransaction(true);
			}
		} catch (Exception e) {
			logger.error("Error obteniendo siguiente número de secuencia", e);
			throw new DbExcepcion(
					DbCodigosError.EC_GET_NEXT_SEQUENCE_NUMBER_EXCEPTION, e);
		} finally {
			try {
				if (dbConn.existConnection())
					if (logger.isDebugEnabled()) {
						logger.debug("Cerrando conexión a base de datos...");
					}
				dbConn.close();
			} catch (Exception e) {
				logger.error("Error cerrando base de datos.");
				throw new DbExcepcion(DbCodigosError.EC_CLOSE_CONNECTION, e);
			}
		}

		if (logger.isDebugEnabled()) {
			StringBuffer sbMensaje = new StringBuffer(
					"Numero de secuencia obtenido: ").append(retorno).append(
					".");
			logger.debug(sbMensaje.toString());
		}
		return new Double(retorno).longValue();
	}

	public static long obtenerIdentificador(DbConnection dbConn, String pcModelo)
			throws DbExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("Obteniendo número de secuencia...");
		}

		double retorno = 0;
		if (!dbConn.inTransaction()) {
			logger.error("La conexión no tiene una transacción activa.");
			throw new DbExcepcion(DbCodigosError.EC_TRANSACTION_REQUIRED);
		}

		synchronized (NumeroSecuenciaAutoLiquidacion3.class) {
			retorno = getNextNumSec(dbConn, pcModelo);
		}

		if (logger.isDebugEnabled()) {
			StringBuffer sbMensaje = new StringBuffer(
					"Numero de secuencia obtenido: ").append(retorno).append(
					".");
			logger.debug(sbMensaje.toString());
		}

		return new Double(retorno).longValue();
	}

	private static double getNextNumSec(DbConnection conn, String pcModelo) throws DbExcepcion {
		double numSec = 0;
		double numeroActual = 0;
		double minimo = 0;
		double maximo = 0;
		String modelo = null;

		if (!conn.inTransaction())
			throw new DbExcepcion(DbCodigosError.EC_TRANSACTION_REQUIRED);

		DbSelectStatement stmt = null;
		boolean found;
		try {

			stmt = new DbSelectStatement(conn);
			stmt.create(TABLE_NAME, ALL_COLUMN_NAMES, getByModeloQuad(pcModelo), false);
			stmt.execute();
			found = stmt.next();
			if (found) {
				int index = 1;
				modelo = stmt.getShortText(index++);
				numeroActual = stmt.getLongDecimal(index++);
				minimo = stmt.getLongDecimal(index++);
				maximo = stmt.getLongDecimal(index++);

				if (numeroActual >= maximo) {
					// error superado el límite
					if(RESET_SEQUENCE){
						resetSequence(conn, minimo);
					}else{
						logger.fatal("Alcanzado el límite máximo para la secuencia.");
						throw new DbExcepcion(DbCodigosError.EC_MAX_SEQUENCE_NUMBER);
					}
				} else {
					getNextId(conn, pcModelo);
					numSec = ++numeroActual;
				}
			}
			stmt.release();
		} catch (Exception e) {
			throw new DbExcepcion(DbCodigosError.EC_SELECT_NUMSEC_VALUE, e);
		}
		return numSec;

	}

	/**
	 * Actualiza la fila de la tabla con el valor del número de secuencia + 1
	 *
	 * @param conn
	 *            Conexión a la base de datos.
	 * @param row
	 *            Nuevos valores de la fila.
	 * @throws Exception
	 *             si se produce algún error.
	 */

	private static void getNextId(DbConnection conn, String pcModelo) throws Exception {

		StringBuffer stmtText = new StringBuffer();
		stmtText.append("UPDATE ").append(TABLE_NAME).append(" SET ").append(CN_NUMSEC).append("=").append(CN_NUMSEC).append("+1 ");
		stmtText.append(getByModeloQuad(pcModelo));

		DbUtil.executeStatement(conn, stmtText.toString());

	}

	private static void resetSequence(DbConnection conn, double minimo) throws Exception {

		String stmtText;

		stmtText = "UPDATE " + TABLE_NAME + " SET " + CN_NUMSEC + "="
				+ minimo;

		DbUtil.executeStatement(conn, stmtText);

	}

	private static String getByModeloQuad(String pcModelo){
		StringBuffer sbQuad = new StringBuffer("WHERE ").append(CN_MODELO).append(" = '").append(DbUtil.replaceQuotes(pcModelo)).append("'");
		return sbQuad.toString();
	}
}
