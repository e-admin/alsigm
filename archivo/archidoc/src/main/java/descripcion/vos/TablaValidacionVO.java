package descripcion.vos;

import common.Constants;
import common.util.TypeConverter;
import common.vos.BaseVO;

/**
 * VO para la información de las tablas de validación.
 */
public class TablaValidacionVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private String descripcion = null;
	private boolean usointerno = false;

	public TablaValidacionVO() {
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

	public boolean isTablaDeSistema() {
		return usointerno;
	}

	/* Metodos para rellenado automatico */
	public String getUsointerno() {
		return TypeConverter.toString(usointerno);
	}

	public void setUsointerno(String usointerno) {
		if (usointerno == null)
			this.usointerno = false;
		else
			this.usointerno = Constants.TRUE_STRING.equals(usointerno);
	}
}
