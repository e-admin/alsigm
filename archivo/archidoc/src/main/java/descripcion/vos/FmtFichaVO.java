package descripcion.vos;

import common.vos.BaseVO;

/**
 * Información de una definición de formato de una ficha.
 */
public class FmtFichaVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private String definicion = null;
	private String idFicha = null;
	private int tipo = 0;
	private int nivelAcceso = 0;
	private String idlca = null;
	private String descripcion = null;

	/**
	 * Constructor.
	 */
	public FmtFichaVO() {
		super();
	}

	/**
	 * @return Returns the definicion.
	 */
	public String getDefinicion() {
		return definicion;
	}

	/**
	 * @param definicion
	 *            The definicion to set.
	 */
	public void setDefinicion(String definicion) {
		this.definicion = definicion;
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idFicha.
	 */
	public String getIdFicha() {
		return idFicha;
	}

	/**
	 * @param idFicha
	 *            The idFicha to set.
	 */
	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * @return Returns the idlca.
	 */
	public String getIdlca() {
		return idlca;
	}

	/**
	 * @param idlca
	 *            The idlca to set.
	 */
	public void setIdlca(String idlca) {
		this.idlca = idlca;
	}

	/**
	 * @return Returns the nivelAcceso.
	 */
	public int getNivelAcceso() {
		return nivelAcceso;
	}

	/**
	 * @param nivelAcceso
	 *            The nivelAcceso to set.
	 */
	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
