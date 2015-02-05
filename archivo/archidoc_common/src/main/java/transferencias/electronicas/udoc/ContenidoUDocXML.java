package transferencias.electronicas.udoc;

import transferencias.electronicas.common.SistemaTramitador;
import transferencias.electronicas.serie.InformacionSerie;
import transferencias.electronicas.serie.Productor;

/**
 * Objeto que contiene el mapeo del contenido XML de la transferencia
 * electrónica.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ContenidoUDocXML {
	private InformacionSerie informacionSerie;
	private InformacionUnidadDocumentalElectronica infoUnidadDocumentalElectronica;

	private String version;

	/**
	 * @return el informacionSerie
	 */
	public InformacionSerie getInformacionSerie() {
		return informacionSerie;
	}

	/**
	 * @param informacionSerie
	 *            el informacionSerie a fijar
	 */
	public void setInformacionSerie(InformacionSerie informacionSerie) {
		this.informacionSerie = informacionSerie;
	}

	/**
	 * @return el infoUnidadDocumentalElectronica
	 */
	public InformacionUnidadDocumentalElectronica getInfoUnidadDocumentalElectronica() {
		return infoUnidadDocumentalElectronica;
	}

	/**
	 * @param infoUnidadDocumentalElectronica
	 *            el infoUnidadDocumentalElectronica a fijar
	 */
	public void setInfoUnidadDocumentalElectronica(
			InformacionUnidadDocumentalElectronica infoUnidadDocumentalElectronica) {
		this.infoUnidadDocumentalElectronica = infoUnidadDocumentalElectronica;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setSistemaTramitador(SistemaTramitador sistemaTramitador) {
		if (informacionSerie != null) {
			informacionSerie.setSistemaTramitador(sistemaTramitador);
		}
	}

	public void setCodigoProcedimiento(String codigoProcedimiento) {
		if (informacionSerie != null) {
			informacionSerie.setCodigoProcedimiento(codigoProcedimiento);
		}

		if (infoUnidadDocumentalElectronica != null) {
			infoUnidadDocumentalElectronica
					.setCodigoProcedimiento(codigoProcedimiento);
		}
	}

	public Productor getProductor() {
		if (infoUnidadDocumentalElectronica != null) {
			return infoUnidadDocumentalElectronica.getProductor();
		}

		return null;
	}
}
