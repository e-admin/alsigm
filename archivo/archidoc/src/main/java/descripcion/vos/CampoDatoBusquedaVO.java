package descripcion.vos;

import descripcion.model.xml.definition.DefTipos;

public class CampoDatoBusquedaVO extends CampoDatoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de referencia. */
	private short tipoReferencia = DefTipos.TIPO_REFERENCIA_DESCONOCIDO;

	/** Identificadores de las listas descriptoras. */
	private String idsListasDescriptoras = null;

	/**
	 * Constructor.
	 */
	public CampoDatoBusquedaVO() {
		super();
	}

	public String getIdsListasDescriptoras() {
		return idsListasDescriptoras;
	}

	public void setIdsListasDescriptoras(String idsListasDescriptoras) {
		this.idsListasDescriptoras = idsListasDescriptoras;
	}

	public short getTipoReferencia() {
		return tipoReferencia;
	}

	public void setTipoReferencia(short tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

}
