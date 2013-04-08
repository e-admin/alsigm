package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class TransporteVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -6327716985033208684L;

	protected String id;

	protected TipoTransporteVO tipoTransporte;

	protected String numeroTransporte;

	public TipoTransporteVO getTipoTransporte() {
		return tipoTransporte;
	}

	public void setTipoTransporte(TipoTransporteVO tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}

	public String getNumeroTransporte() {
		return numeroTransporte;
	}

	public void setNumeroTransporte(String numeroTransporte) {
		this.numeroTransporte = numeroTransporte;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
