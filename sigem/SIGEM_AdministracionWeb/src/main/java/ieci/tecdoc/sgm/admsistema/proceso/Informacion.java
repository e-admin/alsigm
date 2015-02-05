package ieci.tecdoc.sgm.admsistema.proceso;

import java.io.File;

public class Informacion {
	private long ficheros = 0;
	private double size = 0.0;
	private long directorios = -1;
	
	public final double BYTE = 8.0;
	public final double KILOBYTE = 1024.0;
	public final double MEGABYTE = KILOBYTE * 1024.0;
	public final double GIGABYTE = MEGABYTE * 1024.0;
	public final double TERABYTE = GIGABYTE * 1024.0;
	public final double MAX_VALUE = 10000.0;
	
	public final String BYTE_DESC = "Bytes";
	public final String KILOBYTE_DESC = "KB";
	public final String MEGABYTE_DESC = "MB";
	public final String GIGABYTE_DESC = "GB";
	public final String TERABYTE_DESC = "TB";
	
	private double divisor = KILOBYTE;
	
	public Info obtenerInformacion(String fichero) {
		File f = new File(fichero);
		return obtenerInformacion(f);
	}
	
	public Info obtenerInformacion(File f) {
		if(f.getAbsolutePath().indexOf("..")!=-1)
			return new Info(ficheros, directorios, size, divisor, getDescripcion());
		else {
			return informacionDirectorio(f);
		}
	}

	private Info informacionDirectorio(File f) {
		if(f.isDirectory()) {
			directorios++;
			File[]ff=f.listFiles();
			for(int i=0;i<ff.length;i++) {
				obtenerInformacion(ff[i]);
			}
		} else ficheros++;
		if (size + (f.length() / divisor) < MAX_VALUE) {
			size = size + (f.length() / divisor);
		} else {
			siguienteDivisor();
			size = (size / divisor) + (f.length() / divisor);
		}
		return new Info(ficheros, directorios, size, divisor, getDescripcion());
	}

	private void siguienteDivisor() {
		if (divisor == BYTE)
			divisor = KILOBYTE;
		else if (divisor == KILOBYTE) 
			divisor = MEGABYTE;
		else if (divisor == MEGABYTE) 
			divisor = GIGABYTE;
		else divisor = TERABYTE;
	}
	
	public String getDescripcion() {
		if (divisor == BYTE)
			return BYTE_DESC;
		else if (divisor == KILOBYTE) 
			return KILOBYTE_DESC;
		else if (divisor == MEGABYTE) 
			return MEGABYTE_DESC;
		else if (divisor == GIGABYTE) 
			return GIGABYTE_DESC;
		return TERABYTE_DESC;
	}
}
