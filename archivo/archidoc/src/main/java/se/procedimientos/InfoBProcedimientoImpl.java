package se.procedimientos;

public class InfoBProcedimientoImpl implements InfoBProcedimiento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String id = null;
	String codigo = null;
	String nombre = null;
	String codSistProductor;
	String nombreSistProductor;

	public InfoBProcedimientoImpl() {
	}

	/**
	 * @param id
	 * @param codigo
	 * @param nombre
	 * @param sistemaProductor
	 */
	public InfoBProcedimientoImpl(String id, String codigo, String nombre) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	/**
	 * Devuelve el identificador del procedimiento.
	 * 
	 * @return Identificador del procedimiento.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Devuelve el código del procedimiento.
	 * 
	 * @return Código del procedimiento.
	 */
	public String getCodigo() {
		return this.codigo;
	}

	/**
	 * Devuelve el nombre del procedimiento.
	 * 
	 * @return Nombre del procedimiento.
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Devuelve el código del sistema productor. Si el procedimiento no es
	 * automatizado este valor será nulo.
	 * 
	 * @return Código del sistema productor.
	 */
	public String getCodSistProductor() {
		return codSistProductor;
	}

	/**
	 * Devuelve el código del sistema productor. Si el procedimiento no es
	 * automatizado este valor será nulo.
	 * 
	 * @return Código del sistema productor.
	 */
	public String getNombreSistProductor() {
		return nombreSistProductor;
	}

	/**
	 * Establece el sistema automatizado mediante el que se realiza la
	 * tramitacion del procedimiento
	 * 
	 * @param cogigo
	 *            Codigo del sistema productor
	 * @param nombre
	 *            Nombre del sistema productor
	 */
	public void setSistemaProductor(String codigo, String nombre) {
		this.codSistProductor = codigo;
		this.nombreSistProductor = nombre;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setCodSistProductor(String codigoSistemaProductor) {
		this.codSistProductor = codigoSistemaProductor;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNombreSistProductor(String nombreSistemaProductor) {
		this.nombreSistProductor = nombreSistemaProductor;
	}

}
