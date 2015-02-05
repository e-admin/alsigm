package fondos.vos;

import common.vos.BaseVO;

/**
 * Información de volumen de una serie
 */
public class VolumenSerieVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la serie. */
	private String idSerie = null;

	/** Tipo documental. */
	private String tipoDocumental = null;

	/** Cantidad de ítems del tipo documental. */
	private int cantidad = 0;

	/**
	 * Constructor.
	 */
	public VolumenSerieVO() {
		super();
	}

	/**
	 * Constructor.
	 */
	public VolumenSerieVO(String idSerie, String tipo, int cantidad) {
		this();
		setIdSerie(idSerie);
		setTipoDocumental(tipo);
		setCantidad(cantidad);
	}

	/**
	 * Obtiene la cantidad de ítems del tipo documental.
	 * 
	 * @return Cantidad de ítems del tipo documental.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad de ítems del tipo documental.
	 * 
	 * @param cantidad
	 *            Cantidad de ítems del tipo documental.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el identificador de la serie.
	 * 
	 * @return Identificador de la serie.
	 */
	public String getIdSerie() {
		return idSerie;
	}

	/**
	 * Establece el identificador de la serie.
	 * 
	 * @param idSerie
	 *            Identificador de la serie.
	 */
	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	/**
	 * Obtiene el tipo documental.
	 * 
	 * @return Tipo documental.
	 */
	public String getTipoDocumental() {
		return tipoDocumental;
	}

	/**
	 * Establece el tipo documental.
	 * 
	 * @param tipoDocumental
	 *            Tipo documental.
	 */
	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}
}
