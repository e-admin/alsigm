package ieci.tecdoc.sgm.admsistema.proceso;

import java.io.File;

public class Limpiar {

	public static boolean borrar(String directorio) {
		File f = new File(directorio);
		return borrar(f);
	}

	public static boolean borrar(File f) {
		if(f.getAbsolutePath().indexOf("..")!=-1)
			return false;
		else
			return _borrar(f);
	}

	private static boolean _borrar(File f) {
		boolean aux = true;
		if(f.isDirectory()) {
			File[]ff=f.listFiles();
			for(int i=0;i<ff.length;i++) {
				aux = borrar(ff[i]);
			}
		}
		//System.out.println("BORRADO: " + f.getAbsolutePath());
		return f.delete() && aux;
	}

}
