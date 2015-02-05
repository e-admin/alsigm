package es.ieci.tecdoc.fwktd.applets.sendfiles.privileged;

import java.io.File;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;

import es.ieci.tecdoc.fwktd.applets.sendfiles.applet.SendFilesApplet;

public class DeleteFilesPrivilegedAction implements PrivilegedAction{

	public Object run() {
		HashMap<String,File> filesToUpload = SendFilesApplet.getFilesToUpload();
		Iterator<String> itr = filesToUpload.keySet().iterator();
		while(itr.hasNext()){
			String fileName = itr.next();
			File fileToDelete = filesToUpload.get(fileName);
			fileToDelete.delete();
		}

		return null;
	}

}
