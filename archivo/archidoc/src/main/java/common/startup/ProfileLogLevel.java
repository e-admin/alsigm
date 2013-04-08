package common.startup;

import org.apache.log4j.Level;

public class ProfileLogLevel extends Level {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final Level BEGIN_ACTION = new ProfileLogLevel(DEBUG_INT,
			"BEGIN_ACTION");
	public static final Level END_ACTION = new ProfileLogLevel(DEBUG_INT,
			"END_ACTION");
	public static final Level BEGIN_BI = new ProfileLogLevel(DEBUG_INT,
			"BEGIN_BI");
	public static final Level POPULATE_VO = new ProfileLogLevel(DEBUG_INT,
			"POPULATE_VO");
	public static final Level END_BI = new ProfileLogLevel(DEBUG_INT, "END_BI");
	public static final Level ERROR_BI = new ProfileLogLevel(DEBUG_INT,
			"ERROR_BI");
	public static final Level BEGIN_TX = new ProfileLogLevel(DEBUG_INT,
			"BEGIN_TX");
	public static final Level COMMIT_TX = new ProfileLogLevel(DEBUG_INT,
			"COMMIT_TX");
	public static final Level ROLLBACK_TX = new ProfileLogLevel(DEBUG_INT,
			"ROLLBACK_TX");
	public static final Level BEGIN_QUERY = new ProfileLogLevel(DEBUG_INT,
			"BEGIN_QUERY");
	public static final Level END_QUERY = new ProfileLogLevel(DEBUG_INT,
			"END_QUERY");

	ProfileLogLevel(int level, String str) {
		super(level, str, DEBUG_INT);
	}
}