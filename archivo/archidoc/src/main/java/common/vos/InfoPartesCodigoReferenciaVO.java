package common.vos;

import common.Constants;

/**
 * Clase para tratar el xml del campo ASCAGRUPO.INFO
 * 
 * @author lucas
 * @version 1.0 15/01/2009
 */
public class InfoPartesCodigoReferenciaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final String PAIS_PROVINCIA_KEY = "PaisProvincia";
	private static final String ARCHIVO_CODIGO_CLASIFICADORES_KEY = "ArchivoCodigoClasificadores";

	private boolean ocultarPaisProvincia = false;
	private boolean ocultarArchivoCodigoClasificadores = false;

	public InfoPartesCodigoReferenciaVO(boolean mostrarPaisProvincia,
			boolean mostrarArchivoCodigoClasificadores) {
		this.ocultarPaisProvincia = mostrarPaisProvincia;
		this.ocultarArchivoCodigoClasificadores = mostrarArchivoCodigoClasificadores;
	}

	public InfoPartesCodigoReferenciaVO() {
		this.ocultarPaisProvincia = false;
		this.ocultarArchivoCodigoClasificadores = false;
	}

	public void addParte(ParteCodigoReferenciaVO parteCodigoReferenciaVO) {
		if (parteCodigoReferenciaVO != null
				&& Constants.TRUE_STRING
						.equalsIgnoreCase(parteCodigoReferenciaVO.getOcultar())) {
			if (PAIS_PROVINCIA_KEY.equals(parteCodigoReferenciaVO.getIdParte())) {
				ocultarPaisProvincia = true;
			} else if (ARCHIVO_CODIGO_CLASIFICADORES_KEY
					.equals(parteCodigoReferenciaVO.getIdParte())) {
				ocultarArchivoCodigoClasificadores = true;
			}
		}
	}

	/**
	 * @return the ocultarPaisProvincia
	 */
	public boolean isOcultarPaisProvincia() {
		return ocultarPaisProvincia;
	}

	/**
	 * @param ocultarPaisProvincia
	 *            the ocultarPaisProvincia to set
	 */
	public void setOcultarPaisProvincia(boolean ocultarPaisProvincia) {
		this.ocultarPaisProvincia = ocultarPaisProvincia;
	}

	/**
	 * @return the ocultarArchivoCodigoClasificadores
	 */
	public boolean isOcultarArchivoCodigoClasificadores() {
		return ocultarArchivoCodigoClasificadores;
	}

	/**
	 * @param ocultarArchivoCodigoClasificadores
	 *            the ocultarArchivoCodigoClasificadores to set
	 */
	public void setOcultarArchivoCodigoClasificadores(
			boolean ocultarArchivoCodigoClasificadores) {
		this.ocultarArchivoCodigoClasificadores = ocultarArchivoCodigoClasificadores;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.vos.BaseVO#toString()
	 */
	public String toString() {
		if (ocultarArchivoCodigoClasificadores == false
				&& ocultarPaisProvincia == false) {
			return null;
		}

		StringBuffer str = new StringBuffer("").append("<info>").append(
				"<ocultar_partes_codigo_referencia>");

		if (ocultarPaisProvincia) {
			str.append("<parte idParte=\"" + PAIS_PROVINCIA_KEY
					+ "\" ocultar=\"" + Constants.TRUE_STRING + "\"/>");
		}

		if (ocultarArchivoCodigoClasificadores) {
			str.append("<parte idParte=\"" + ARCHIVO_CODIGO_CLASIFICADORES_KEY
					+ "\" ocultar=\"" + Constants.TRUE_STRING + "\"/>");
		}

		str.append("</ocultar_partes_codigo_referencia>").append("</info>");

		return str.toString();
	}

}
