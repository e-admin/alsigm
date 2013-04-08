package descripcion.vos;

import common.vos.BaseVO;
import common.vos.IKeyId;

/**
 * Información de un area
 */
public class AreaVO extends BaseVO implements IKeyId {

	private static final long serialVersionUID = 6501695032744264921L;
	private String id = null;
	private String nombre = null;
	private int tipoNorma = 0;
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(int tipoNorma) {
		this.tipoNorma = tipoNorma;
	}
}
