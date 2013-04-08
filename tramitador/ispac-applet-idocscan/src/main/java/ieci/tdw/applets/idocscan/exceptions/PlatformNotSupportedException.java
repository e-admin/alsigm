package ieci.tdw.applets.idocscan.exceptions;

import ieci.tdw.applets.idocscan.Messages;

public class PlatformNotSupportedException extends InstallerException {

	public PlatformNotSupportedException(String osName) {
    	super(Messages.getString("installer.exception.platformNotSupported",
    			new Object[] { osName }));
	}
}
