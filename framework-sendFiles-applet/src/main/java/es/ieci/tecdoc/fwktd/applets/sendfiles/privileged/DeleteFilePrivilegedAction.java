package es.ieci.tecdoc.fwktd.applets.sendfiles.privileged;

import java.io.File;
import java.security.PrivilegedAction;

import es.ieci.tecdoc.fwktd.applets.sendfiles.applet.SendFilesApplet;

public class DeleteFilePrivilegedAction implements PrivilegedAction{

	public Object run() {
		boolean result = true;
		String file = SendFilesApplet.getFileToDelete();
		try{
			File fileToDelete = new File(file);
			fileToDelete.delete();
		}
		catch (SecurityException e) {
			System.out.println("Ha ocurrido un error de permisos al eliminar el fichero: "+file);
			result=false;
		}
		return result;
	}

}
