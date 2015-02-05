package ieci.tdw.applets.idocscan.exceptions;

import ieci.tdw.applets.idocscan.Messages;

public class InstallerException extends Exception {

    public InstallerException() {
    	super(Messages.getString("installer.exception"));
	}

	public InstallerException(String message) {
		super(Messages.getString(message));
	}

	public InstallerException(String message, Object params[]) {
		super(Messages.getString(message, params));
	}

	public InstallerException(String message, Throwable cause) {
		super(Messages.getString(message), cause);
	}

	public InstallerException(String message, Object params[], Throwable cause) {
		super(Messages.getString(message, params), cause);
	}

}
