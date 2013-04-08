package gcontrol.vos;

import common.vos.BaseVO;

/**
 * Datos de un permiso asociado a una lista de acceso
 */
public class PermisosListaVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador de lista de acceso */
	String idListCA;
	/** Tipo de destinatario del permiso {@link gcontrol.model.TipoDestinatario} */
	int tipoDest;
	/** Identificador del destinatorio del permiso */
	String idDest;
	/** Identificador de permiso */
	int perm;

	public String getIdDest() {
		return this.idDest;
	}

	public void setIdDest(String idDest) {
		this.idDest = idDest;
	}

	public String getIdListCA() {
		return this.idListCA;
	}

	public void setIdListCA(String idListaCA) {
		this.idListCA = idListaCA;
	}

	public int getPerm() {
		return this.perm;
	}

	public void setPerm(int perm) {
		this.perm = perm;
	}

	public int getTipoDest() {
		return this.tipoDest;
	}

	public void setTipoDest(int tipoDest) {
		this.tipoDest = tipoDest;
	}
}
