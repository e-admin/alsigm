package transferencias.electronicas.documentos;

import docelectronicos.EstadoDocumento;
import transferencias.TransferenciasElectronicasConstants;

public class Almacenamiento {

	private String tipo;

	private String nombre;
	private String extension;
	private String descripcion;
	private String clasificador;
	private int estado = EstadoDocumento.PUBLICADO;

	private Repositorio repositorio;


	/**
	 * @return el tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo el tipo a fijar
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension el extension a fijar
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion el descripcion a fijar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return el clasificador
	 */
	public String getClasificador() {
		return clasificador;
	}

	/**
	 * @param clasificador el clasificador a fijar
	 */
	public void setClasificador(String clasificador) {
		this.clasificador = clasificador;
	}

	/**
	 * @return el repositorio
	 */
	public Repositorio getRepositorio() {
		return repositorio;
	}

	/**
	 * @param repositorio el repositorio a fijar
	 */
	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public boolean isRepositorio(){
		return TransferenciasElectronicasConstants.REPOSITORIO.equalsIgnoreCase(tipo);
	}

	/**
	 * @param estado el objeto estado a fijar
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return el objeto estado
	 */
	public int getEstado() {
		return estado;
	}
}
