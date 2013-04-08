package ieci.tdw.applets.idocscan.exceptions;

import ieci.tdw.applets.idocscan.Messages;

public class UserCancelException extends InstallerException {

    public UserCancelException() {
    	super(Messages.getString("installer.exception.userCancel"));
	}

}
