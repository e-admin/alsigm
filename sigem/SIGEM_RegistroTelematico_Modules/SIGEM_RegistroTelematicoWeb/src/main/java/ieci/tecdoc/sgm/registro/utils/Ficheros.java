package ieci.tecdoc.sgm.registro.utils;

import java.io.File;
import java.io.FileOutputStream;

public class Ficheros {
	public static void storeFile(String dstLoc, byte[] srcData)
    	throws Exception {
		writeBytesToFile(dstLoc, srcData);
	}

	public static void writeBytesToFile(String loc, byte[] data)
    	throws Exception {
		
		File file;
		FileOutputStream fos = null;

		try {
			file = new File(loc);
			fos = new FileOutputStream(file);

			fos.write(data);
			fos.flush();

			fos.close();
			fos = null;

		} catch (Exception e) {
			try {
				if (fos != null) fos.close();
				throw e;
			} catch (Exception e1) {
				throw e;
			}
		}
	}
}
