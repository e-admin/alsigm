package docelectronicos.vos;

import common.vos.BaseVO;

/**
 * Representa a la Unidad Documental Electrónica en el Cuadro de Clasificación
 */
public class UnidadDocumentalElectronicaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String idElementoCF;
	private int marcasBloqueo = 0;

	/**
	 * Constructor por Defecto.
	 */
	public UnidadDocumentalElectronicaVO() {
	}

	/**
	 * Constructor por Id de Elemento del Cuadro de Clasificación
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento en el cuadro
	 */
	public UnidadDocumentalElectronicaVO(String idElementoCF) {
		this.idElementoCF = idElementoCF;
	}

	/**
	 * Constructor por Id de Elemento del Cuadro de Clasificación y Marcas de
	 * Bloqueo
	 * 
	 * @param idElementoCF
	 *            Identificador del elemento en el cuadro
	 * @param marcasBloqueo
	 *            Marcas de Bloqueo
	 */
	public UnidadDocumentalElectronicaVO(String idElementoCF, int marcasBloqueo) {
		this.idElementoCF = idElementoCF;
		this.marcasBloqueo = marcasBloqueo;
	}

	public String getIdElementoCF() {
		return idElementoCF;
	}

	public void setIdElementoCF(String idElementoCF) {
		this.idElementoCF = idElementoCF;
	}

	public int getMarcasBloqueo() {
		return marcasBloqueo;
	}

	public void setMarcasBloqueo(int marcasBloqueo) {
		this.marcasBloqueo = marcasBloqueo;
	}
}
