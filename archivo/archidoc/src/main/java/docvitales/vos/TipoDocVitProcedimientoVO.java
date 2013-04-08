package docvitales.vos;

import common.vos.BaseVO;

/**
 * Información de la relación entre un tipo de documento vital y un
 * procedimiento.
 */
public class TipoDocVitProcedimientoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del tipo de documento vital. */
	private String idTipoDoc = null;

	/** Identificador del procedimiento. */
	private String idProc = null;

	/**
	 * Constructor.
	 */
	public TipoDocVitProcedimientoVO() {
	}

	/**
	 * Constructor.
	 * 
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 * @param idProc
	 *            Identificador del procedimiento.
	 */
	public TipoDocVitProcedimientoVO(String idProc, String idTipoDocVit) {
		setIdProc(idProc);
		setIdTipoDoc(idTipoDocVit);
	}

	public String getIdProc() {
		return idProc;
	}

	public void setIdProc(String idProc) {
		this.idProc = idProc;
	}

	public String getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
}
