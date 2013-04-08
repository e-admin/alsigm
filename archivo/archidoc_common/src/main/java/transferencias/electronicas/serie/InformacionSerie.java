package transferencias.electronicas.serie;

import transferencias.electronicas.common.SistemaTramitador;
import transferencias.electronicas.ficha.CamposFicha;

public class InformacionSerie {
	private String idPadre;
	private String idFichaUdocs;
	private String idRepEcmSerie;
	private String idRepEcmUdoc;
	private Procedimiento procedimiento;
	private CamposFicha camposFicha;

	/**
	 * @return el idFichaUdocs
	 */
	public String getIdFichaUdocs() {
		return idFichaUdocs;
	}
	/**
	 * @param idFichaUdocs el idFichaUdocs a fijar
	 */
	public void setIdFichaUdocs(String idFichaUdocs) {
		this.idFichaUdocs = idFichaUdocs;
	}
	/**
	 * @return el procedimiento
	 */
	public Procedimiento getProcedimiento() {
		return procedimiento;
	}
	/**
	 * @param procedimiento el procedimiento a fijar
	 */
	public void setProcedimiento(Procedimiento procedimiento) {
		this.procedimiento = procedimiento;
	}
	public void setCamposFicha(CamposFicha camposFicha) {
		this.camposFicha = camposFicha;
	}
	public CamposFicha getCamposFicha() {
		return camposFicha;
	}

	public void setSistemaTramitador(SistemaTramitador sistemaTramitador){
		if(procedimiento != null){
			procedimiento.setSistemaTramitador(sistemaTramitador);
		}
	}

	public void setCodigoProcedimiento(String codigoProcedimiento){
		if(procedimiento != null){
			procedimiento.setCodigo(codigoProcedimiento);
		}
	}
	/**
	 * @return el objeto idPadre
	 */
	public String getIdPadre() {
		return idPadre;
	}
	/**
	 * @param idPadre el objeto idPadre a fijar
	 */
	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getIdFichaClfDocPrefUdoc() {
		return null;
	}
	/**
	 * @param idRepEcm el objeto idRepEcm a fijar
	 */
	public void setIdRepEcmSerie(String idRepEcm) {
		this.idRepEcmSerie = idRepEcm;
	}
	/**
	 * @return el objeto idRepEcm
	 */
	public String getIdRepEcmSerie() {
		return idRepEcmSerie;
	}
	/**
	 * @param idRepEcmUdoc el objeto idRepEcmUdoc a fijar
	 */
	public void setIdRepEcmUdoc(String idRepEcmUdoc) {
		this.idRepEcmUdoc = idRepEcmUdoc;
	}
	/**
	 * @return el objeto idRepEcmUdoc
	 */
	public String getIdRepEcmUdoc() {
		return idRepEcmUdoc;
	}

}
