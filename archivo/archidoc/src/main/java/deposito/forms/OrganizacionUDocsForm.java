package deposito.forms;

import common.Constants;
import common.forms.CustomForm;

public class OrganizacionUDocsForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idUinstalacion;
	String signaturaui;
	String idHueco;

	String[] udocsSeleccionadas;
	int[] posUdocsSeleccionadas;
	String[] idsUdocsSeleccionadas;
	String[] signaturasUdocSeleccionadas;
	int[] nuevasPosUdocsSeleccionadas;

	/**
	 * @return el idUinstalacion
	 */
	public String getIdUinstalacion() {
		return idUinstalacion;
	}

	/**
	 * @param idUinstalacion
	 *            el idUinstalacion a establecer
	 */
	public void setIdUinstalacion(String idUinstalacion) {
		this.idUinstalacion = idUinstalacion;
	}

	/**
	 * @return el signaturaUinstalacion
	 */
	public String getSignaturaui() {
		return signaturaui;
	}

	/**
	 * @param signaturaUinstalacion
	 *            el signaturaUinstalacion a establecer
	 */
	public void setSignaturaui(String signaturaui) {
		this.signaturaui = signaturaui;
	}

	/**
	 * @return el idHueco
	 */
	public String getIdHueco() {
		return idHueco;
	}

	/**
	 * @param idHueco
	 *            el idHueco a establecer
	 */
	public void setIdHueco(String idHueco) {
		this.idHueco = idHueco;
	}

	public void resetInInit() {
		udocsSeleccionadas = null;
	}

	public String[] getUdocsSeleccionadas() {
		return udocsSeleccionadas;
	}

	public void setUdocsSeleccionadas(String[] udocsSeleccionadas) {
		this.udocsSeleccionadas = udocsSeleccionadas;
		posUdocsSeleccionadas = null;
		idsUdocsSeleccionadas = null;
		signaturasUdocSeleccionadas = null;
		nuevasPosUdocsSeleccionadas = null;
	}

	/**
	 * Este metodo devuelve los elementos que estaban seleccionados con sus
	 * nuevas posiciones.
	 * 
	 * @param ids
	 * @param posUdocs
	 * @param signaturas
	 * @param nuevasPosiciones
	 * @return
	 */
	public String[] getUdocsSelect(String[] ids, int[] posUdocs,
			String[] signaturas, int[] nuevasPosiciones) {
		String[] udocsSel = new String[ids.length];

		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				String codigo = new StringBuffer(ids[i])
						.append(Constants.DELIMITER_IDS).append(posUdocs[i])
						.append(Constants.DELIMITER_IDS).append(signaturas[i])
						.append(Constants.DELIMITER_IDS)
						.append(nuevasPosiciones[i]).toString();
				udocsSel[i] = codigo;
			}
		}

		return udocsSel;
	}

	public String[] getIdsUdocsSeleccionadas() {
		if (idsUdocsSeleccionadas == null) {
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				idsUdocsSeleccionadas = new String[udocsSeleccionadas.length];
				for (int i = 0; i < udocsSeleccionadas.length; i++) {
					idsUdocsSeleccionadas[i] = udocsSeleccionadas[i]
							.split(Constants.DELIMITER_IDS)[0];
				}
				return idsUdocsSeleccionadas;
			}
		}
		return idsUdocsSeleccionadas;
	}

	public int[] getPosUdocsSeleccionadas() {
		if (posUdocsSeleccionadas == null) {
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				posUdocsSeleccionadas = new int[udocsSeleccionadas.length];
				for (int i = 0; i < udocsSeleccionadas.length; i++) {
					posUdocsSeleccionadas[i] = Integer.valueOf(
							udocsSeleccionadas[i]
									.split(Constants.DELIMITER_IDS)[1])
							.intValue();
				}
				return posUdocsSeleccionadas;
			}
		}
		return posUdocsSeleccionadas;
	}

	public String[] getSignaturasUdocSeleccionadas() {
		if (signaturasUdocSeleccionadas == null) {
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				signaturasUdocSeleccionadas = new String[udocsSeleccionadas.length];
				for (int i = 0; i < udocsSeleccionadas.length; i++) {
					signaturasUdocSeleccionadas[i] = udocsSeleccionadas[i]
							.split(Constants.DELIMITER_IDS)[2];
				}
				return signaturasUdocSeleccionadas;
			}
		}

		return signaturasUdocSeleccionadas;
	}

	public int[] getNuevasPosUdocsSeleccionadas() {
		if (nuevasPosUdocsSeleccionadas == null) {
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				nuevasPosUdocsSeleccionadas = new int[udocsSeleccionadas.length];
				for (int i = 0; i < udocsSeleccionadas.length; i++) {
					nuevasPosUdocsSeleccionadas[i] = Integer.valueOf(
							udocsSeleccionadas[i]
									.split(Constants.DELIMITER_IDS)[3])
							.intValue();
				}
				return nuevasPosUdocsSeleccionadas;
			}
		}
		return nuevasPosUdocsSeleccionadas;
	}

}
