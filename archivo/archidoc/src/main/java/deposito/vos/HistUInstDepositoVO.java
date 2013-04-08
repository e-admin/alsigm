package deposito.vos;

import java.util.Date;

import common.util.DateUtils;
import common.vos.BaseVO;

/**
 * Gestión de la Tabla: ASGDHISTUINSTALACION
 */
public class HistUInstDepositoVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String idFormato;
	private String signaturaUI;
	private String identificacion;
	private Date feliminacion;
	private Integer motivo;
	private String idArchivo;

	public HistUInstDepositoVO(String id, String idArchivo, String idFormato,
			String signaturaUI, String identificacion, Date feliminacion,
			Integer motivo) {
		this.id = id;
		this.setIdArchivo(idArchivo);
		this.idFormato = idFormato;
		this.signaturaUI = signaturaUI;
		this.identificacion = identificacion;
		this.feliminacion = feliminacion;
		this.motivo = motivo;
	}

	public HistUInstDepositoVO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public String getSignaturaUI() {
		return signaturaUI;
	}

	public void setSignaturaUI(String signaturaUI) {
		this.signaturaUI = signaturaUI;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public Date getFeliminacion() {
		return feliminacion;
	}

	public void setFeliminacion(Date feliminacion) {
		this.feliminacion = feliminacion;
	}

	public Integer getMotivo() {
		return motivo;
	}

	public void setMotivo(Integer motivo) {
		this.motivo = motivo;
	}

	public String getFeliminacionString() {
		return DateUtils.formatTimestamp(feliminacion);
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getIdArchivo() {
		return idArchivo;
	}
}
