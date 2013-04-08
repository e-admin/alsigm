package es.ieci.tecdoc.isicres.admin.core.db;

import org.apache.log4j.Logger;

public class DbGrantStatement extends DbOutputStatement {
	public static Logger logger = Logger.getLogger(DbGrantStatement.class);
	private String userGranted = null;
	private String smtp = new String();

	public DbGrantStatement() {
		super();
	}

	public void create(int perms, String tblName, String userGranted)
			throws Exception {
		this.smtp = getSelectStmtText(perms, tblName, userGranted);
		super.create(smtp);
	}

	private String getSelectStmtText(int perms, String tblName,
			String userGranted) {
		StringBuffer buff = new StringBuffer();
		if (userGranted != null && !userGranted.equals("")) {
			this.userGranted = userGranted;

			buff.append("GRANT ");
			String s[] = new String[4];
			int i = 0;
			if ((perms & 0x8) == 0x8) {
				s[i++] = "SELECT";
			}
			if ((perms & 0x4) == 0x4) {
				s[i++] = "INSERT";
			}
			if ((perms & 0x2) == 0x2) {
				s[i++] = "UPDATE";
			}
			if ((perms & 0x1) == 0x1) {
				s[i++] = "DELETE";
			}
			int j = 0;
			for (j = 0; j < i - 1; j++)
				buff.append(s[j] + ", ");
			buff.append(s[j]);
			buff.append(" ON ");
			buff.append(" " + tblName + " ");
			buff.append("TO ");
			buff.append(userGranted);
		}
		return buff.toString();
	}

	public void execute() throws Exception {
		if (this.userGranted != null){
			if (logger.isDebugEnabled())
				logger.debug(smtp);
			super.execute();
		}
			
	}

}
