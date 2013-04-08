package common.db;

import ieci.core.db.DbConnection;
import ieci.core.db.DbError;
import ieci.core.exception.IeciTdException;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import common.exceptions.ColumnNotIndexedException;
import common.exceptions.DBException;
import common.exceptions.SintaxErrorException;
import common.exceptions.TooManyResultsException;
import common.exceptions.UniqueKeyException;
import common.exceptions.WordOmittedException;

public abstract class DBCommand {

	Logger logger;

	DBEntity entity = null;
	boolean executeOk = true;

	public boolean isExecuteOk() {
		return this.executeOk;
	}

	protected void setExecuteOk(boolean ok) {
		this.executeOk = ok;
	}

	public DBCommand(DBEntity entity) {
		this.entity = entity;
		logger = Logger.getLogger(entity.getClass().getName());
	}

	public abstract void codeLogic(DbConnection conn) throws Exception;

	public void execute() {
		DbConnection conn = null;
		try {
			conn = entity.getConnection();
			codeLogic(conn);
		} catch (IeciTdException e) {
			executeOk = false;

			if (!e.getErrorCode().equalsIgnoreCase(DbError.EC_NOT_FOUND)) {
				logger.error(e);
				throw new DBException(e);
			}
		} catch (SQLException e) {
			executeOk = false;
			logger.error(e);

			if (e.getErrorCode() == 1)
				throw new UniqueKeyException(e);
			else if (e.getErrorCode() == 20000
					&& e.getMessage().indexOf("DRG-10599") >= 0)
				throw new ColumnNotIndexedException(e);
			else if (e.getErrorCode() == 29902
					&& ((e.getMessage().indexOf("DRG-50901") >= 0) || (e
							.getMessage().indexOf("DRG-50900") >= 0)))
				throw new SintaxErrorException(e);
			else if (e.getErrorCode() == 7619
					&& e.getMessage().indexOf("Microsoft") >= 0)
				throw new WordOmittedException(e);
			else
				throw new DBException(e);
		} catch (Exception e) {
			executeOk = false;
			logger.error(e);
			throw new DBException(e);
		}
	}

	public void executeWithMaxNumResults() throws TooManyResultsException {
		DbConnection conn = null;
		try {
			conn = entity.getConnection();
			codeLogic(conn);
		} catch (IeciTdException e) {
			executeOk = false;

			if (!e.getErrorCode().equalsIgnoreCase(DbError.EC_NOT_FOUND)) {
				logger.error(e);
				throw new DBException(e);
			}
		} catch (SQLException e) {
			executeOk = false;
			logger.error(e);

			if (e.getErrorCode() == 1)
				throw new UniqueKeyException(e);
			else if (e.getErrorCode() == 20000
					&& e.getMessage().indexOf("DRG-10599") >= 0)
				throw new ColumnNotIndexedException(e);
			else if (e.getErrorCode() == 29902
					&& ((e.getMessage().indexOf("DRG-50901") >= 0) || (e
							.getMessage().indexOf("DRG-50900") >= 0)))
				throw new SintaxErrorException(e);
			else if (e.getErrorCode() == 7619
					&& e.getMessage().indexOf("Microsoft") >= 0)
				throw new WordOmittedException(e);
			else
				throw new DBException(e);
		} catch (TooManyResultsException e) {
			executeOk = false;
			throw e;
		} catch (Exception e) {
			executeOk = false;
			logger.error(e);
			throw new DBException(e);
		}
	}

}