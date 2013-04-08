package valoracion.vos;

import common.vos.BaseVO;

/**
 * Clase que encapusla los datos de la información de boletin de una valoracion
 * de serie
 */
public class PlazoValoracionVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String idValSerie;
	private int plazo;
	private String idNivelOrigen;
	private String idNivelDestino;
	private int orden;

	public String getIdValSerie() {
		return idValSerie;
	}

	public void setIdValSerie(String idValSerie) {
		this.idValSerie = idValSerie;
	}

	public int getPlazo() {
		return plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public String getIdNivelOrigen() {
		return idNivelOrigen;
	}

	public void setIdNivelOrigen(String idNivelOrigen) {
		this.idNivelOrigen = idNivelOrigen;
	}

	public String getIdNivelDestino() {
		return idNivelDestino;
	}

	public void setIdNivelDestino(String idNivelDestino) {
		this.idNivelDestino = idNivelDestino;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PlazoValoracionVO))
			return false;
		PlazoValoracionVO plazoVal = (PlazoValoracionVO) obj;
		return (compareStrings(idValSerie, plazoVal.getIdValSerie())
				&& plazo == plazoVal.getPlazo()
				&& compareStrings(idNivelOrigen, plazoVal.getIdNivelOrigen())
				&& compareStrings(idNivelDestino, plazoVal.getIdNivelDestino()) && orden == plazoVal.orden);
	}

	private boolean compareStrings(String s1, String s2) {
		if (s1 == null)
			return s2 == null;
		if (s2 == null)
			return false;
		return s1.equals(s2);
	}
}
