package deposito.forms;

import common.forms.CustomForm;

import deposito.vos.HuecoID;

public class ReubicacionUnidadesDocumentalesForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String[] udocsSeleccionadas;
	String idHuecoOrigen;
	String idUinstalacionOrigen;
	String elementoDestino;
	int numHuecoDestino;
	String idElementoDestino;
	String idTipoElemento;
	int[] posUdocsSeleccionadas;
	String[] idsUdocsSeleccionadas;
	String[] signaturasUdocSeleccionadas;

	public String[] getUdocsSeleccionadas() {
		return udocsSeleccionadas;
	}

	public void setUdocsSeleccionadas(String[] udocsSeleccionadas) {
		this.udocsSeleccionadas = udocsSeleccionadas;
		posUdocsSeleccionadas = null;
		idsUdocsSeleccionadas = null;
		signaturasUdocSeleccionadas = null;
	}

	public String getIdElementoDestino() {
		if (elementoDestino != null) {
			return elementoDestino.split(":")[0];
		}
		return null;
	}

	public String getIdTipoElemento() {
		if (elementoDestino != null) {
			return elementoDestino.split(":")[1];
		}
		return null;
	}

	public void resetInInit() {
		// idHuecoOrigen = null;
		udocsSeleccionadas = null;
		// idUinstalacionOrigen = null;
		numHuecoDestino = -1;
	}

	public String getElementoDestino() {
		return elementoDestino;
	}

	public void setElementoDestino(String elementoDestino) {
		this.elementoDestino = elementoDestino;
	}

	public static HuecoID getHuecoID(String idHueco) {
		if (idHueco != null) {
			String idPadre = idHueco.split(":")[0];
			int numOrden = Integer.parseInt(idHueco.split(":")[1]);
			return new HuecoID(idPadre, numOrden);
		} else
			return null;
	}

	public String[] getSignaturasUdocSeleccionadas() {
		if (signaturasUdocSeleccionadas == null) {
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				signaturasUdocSeleccionadas = new String[udocsSeleccionadas.length];
				for (int i = 0; i < udocsSeleccionadas.length; i++) {
					signaturasUdocSeleccionadas[i] = udocsSeleccionadas[i]
							.split(":")[2];
				}
				return signaturasUdocSeleccionadas;
			}
		}

		return signaturasUdocSeleccionadas;
	}

	public int[] getPosUdocsSeleccionadas() {
		if (posUdocsSeleccionadas == null) {
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				posUdocsSeleccionadas = new int[udocsSeleccionadas.length];
				for (int i = 0; i < udocsSeleccionadas.length; i++) {
					posUdocsSeleccionadas[i] = Integer.valueOf(
							udocsSeleccionadas[i].split(":")[1]).intValue();
				}
				return posUdocsSeleccionadas;
			}
		}
		return posUdocsSeleccionadas;
	}

	public String[] getIdsUdocsSeleccionadas() {
		if (idsUdocsSeleccionadas == null) {
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				idsUdocsSeleccionadas = new String[udocsSeleccionadas.length];
				for (int i = 0; i < udocsSeleccionadas.length; i++) {
					idsUdocsSeleccionadas[i] = udocsSeleccionadas[i].split(":")[0];
				}
				return idsUdocsSeleccionadas;
			}
		}
		return idsUdocsSeleccionadas;
	}

	public int getNumHuecoDestino() {
		return numHuecoDestino;
	}

	public void setNumHuecoDestino(int idUnidadInstalacionDestino) {
		this.numHuecoDestino = idUnidadInstalacionDestino;
	}

	public String getIdUinstalacionOrigen() {
		return idUinstalacionOrigen;
	}

	public void setIdUinstalacionOrigen(String idUnidadInstalacionOrigen) {
		this.idUinstalacionOrigen = idUnidadInstalacionOrigen;
	}

	public String getIdHuecoOrigen() {
		return idHuecoOrigen;
	}

	public void setIdHuecoOrigen(String idHuecoOrigen) {
		this.idHuecoOrigen = idHuecoOrigen;
	}
}
