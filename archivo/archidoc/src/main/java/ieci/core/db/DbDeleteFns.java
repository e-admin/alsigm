package ieci.core.db;

import org.apache.log4j.Logger;

import common.startup.ProfileLogLevel;

public final class DbDeleteFns {

	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");

	private DbDeleteFns() {
	}

	public static void delete(DbConnection conn, String tblName, String qual)
			throws Exception {

		String stmtText;

		stmtText = getDeleteStmtText(tblName, qual);

		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_QUERY)) {
			StringBuffer logMessage = new StringBuffer()
					.append(System.currentTimeMillis()).append(" [")
					.append(stmtText).append("]");
			PROFILE_LOGGER.log(ProfileLogLevel.BEGIN_QUERY, logMessage);
		}

		DbUtil.executeStatement(conn, stmtText);

		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.END_QUERY))
			PROFILE_LOGGER.log(ProfileLogLevel.END_QUERY,
					String.valueOf(System.currentTimeMillis()));

	}

	// **************************************************************************

	private static String getDeleteStmtText(String tblName, String qual) {

		StringBuffer tbdr;

		tbdr = new StringBuffer();

		tbdr.append("DELETE FROM ").append(tblName);

		if (qual != null)
			tbdr.append(" ").append(qual);

		return tbdr.toString();

	}

} // class
