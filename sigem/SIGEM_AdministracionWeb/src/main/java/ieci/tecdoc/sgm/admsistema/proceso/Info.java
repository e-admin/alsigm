package ieci.tecdoc.sgm.admsistema.proceso;

public class Info {
	private long ficheros;
	private long directorios;
	private double size;
	private double divisor;
	private String descripcion;
	
	public long getDirectorios() {
		return directorios;
	}
	
	public void setDirectorios(long directorios) {
		this.directorios = directorios;
	}
	
	public long getFicheros() {
		return ficheros;
	}
	
	public void setFicheros(long ficheros) {
		this.ficheros = ficheros;
	}
	
	public double getSize() {
		return size;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getDivisor() {
		return divisor;
	}

	public void setDivisor(double divisor) {
		this.divisor = divisor;
	}

	public Info(long ficheros, long directorios, double size, double divisor, String descripcion) {
		this.ficheros = ficheros;
		this.directorios = directorios;
		this.divisor = divisor;
		this.size = size;
		this.descripcion = descripcion;
	}
	
}
