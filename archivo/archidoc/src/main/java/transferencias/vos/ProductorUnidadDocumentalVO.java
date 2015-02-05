package transferencias.vos;

/**
 * Identificación de un productor de unidad documental
 */
public class ProductorUnidadDocumentalVO {

	String id = null;
	String nombre = null;
	String codigoOrgano = null;

	public ProductorUnidadDocumentalVO(String id, String nombre,
			String codigoOrgano) {
		this.id = id;
		this.nombre = nombre;
		this.codigoOrgano = codigoOrgano;
	}

	public String getCodigoOrgano() {
		return codigoOrgano;
	}

	public void setCodigoOrgano(String codigoOrgano) {
		this.codigoOrgano = codigoOrgano;
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
}