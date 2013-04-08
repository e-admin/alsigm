package fondos.vos;

import descripcion.vos.DescriptorVO;

/**
 * Información de entidad productora
 */
public class EntidadProductoraVO extends DescriptorVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int tipoentidad;
	// String sistGestorOrg;
	// String idProductorEnSistGestor;
	String codigo;

	public String getIdProductorEnSistGestor() {
		return getIdDescrSistExt();
	}

	public void setIdProductorEnSistGestor(String idProductorEnSistGestor) {
		// this.idProductorEnSistGestor = idProductorEnSistGestor;
		setIdDescrSistExt(idProductorEnSistGestor);
	}

	public String getSistGestorOrg() {
		return getIdSistExt();
	}

	public void setSistGestorOrg(String sistGestorOrg) {
		// this.sistGestorOrg = sistGestorOrg;
		setIdSistExt(sistGestorOrg);
	}

	public boolean isFrombdexterna() {
		return getIdDescrSistExt() != null;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getTipoentidad() {
		return this.tipoentidad;
	}

	public void setTipoentidad(int tipo) {
		this.tipoentidad = tipo;
	}

}
