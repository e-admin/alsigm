package ieci.tdw.applets.idocscan;

import ieci.tdw.applets.idocscan.privileged.LogPrivilegedAction;

import java.security.AccessController;

public class Debug {

	public Debug() {
	}

	public static void logToFile(Object sourceClass, final String message) {

		String from;

		if (Configurator.getDebug()) {
			if (sourceClass != null) {
				from = " in " + sourceClass.getClass();
			} else {
				from = "";
			}

			log(message + " from: " + from);

			AccessController.doPrivileged(new LogPrivilegedAction(message, from));
		}
	}

	public static void log(String strDebugMessage) {
		System.out.println("Debug Message: " + strDebugMessage);
	}

}