package transferencias.electronicas.documentos;

import common.vos.BaseVO;

public class Repositorio extends BaseVO implements IRepositorio {
	/**
	 *
	 */
	private static final long serialVersionUID = -2721305753759108076L;
	private String idRepositorio;
	private String localizador;

	/**
	 * @return el idRepositorio
	 */
	public String getIdRepositorio() {
		return idRepositorio;
	}
	/**
	 * @param idRepositorio el idRepositorio a fijar
	 */
	public void setIdRepositorio(String idRepositorio) {
		this.idRepositorio = idRepositorio;
	}
	/**
	 * @return el localizadorDocumento
	 */
	public String getLocalizador() {
		return localizador;
	}
	/**
	 * @param localizadorDocumento el localizadorDocumento a fijar
	 */
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	public boolean isDepositoElectronico() {
		return false;
	}
	public boolean isEcm() {
		return true;
	}
}
