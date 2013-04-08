package transferencias.vos;

/**
 * Value Object con la informacion referente a cada uno de los documentos
 * fisicos que pueden formar parte de la lista de documentos que contiene una
 * unidad documental
 * 
 */
public class DocumentoVO {

	public static final int DOCUMENTO_FISICO = 1;
	public static final int DOCUMENTO_ELECTRONICO = 2;

	String nombre;
	String descripcion;

	boolean incluidoEnTransferencia;

	public DocumentoVO() {
	}

	public DocumentoVO(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.incluidoEnTransferencia = true;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIncluidoEnTransferencia(boolean incluidoEnTransferencia) {
		this.incluidoEnTransferencia = incluidoEnTransferencia;
	}

	public boolean isIncluidoEnTransferencia() {
		return incluidoEnTransferencia;
	}

	public boolean isElectronico() {
		return false;
	}
}