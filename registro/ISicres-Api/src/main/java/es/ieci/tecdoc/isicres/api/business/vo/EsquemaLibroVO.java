package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.ArrayList;
import java.util.List;

public class EsquemaLibroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = 1751297471111004445L;

	/**
	 * Lista de {@link EsquemaLibroFieldVO}
	 */
	protected List camposLibro;

	public EsquemaLibroVO() {
		this.camposLibro = new ArrayList();
	}

	public List getCamposLibro() {
		return camposLibro;
	}

	public void setCamposLibro(List camposLibro) {
		this.camposLibro = camposLibro;
	}

}
