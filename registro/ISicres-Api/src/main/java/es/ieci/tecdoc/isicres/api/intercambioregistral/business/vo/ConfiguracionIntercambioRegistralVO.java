package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

/**
 * Clase para almacenar la configuracion de intercambio registral
 * de una unidad de tramitacion y su entidad registral.
 * Puede darse el caso que solo tenga entidad registral
 *
 */
public class ConfiguracionIntercambioRegistralVO {
	protected EntidadRegistralVO entidadRegistral;
	protected UnidadTramitacionIntercambioRegistralVO unidadTramitacionRegistral;
	
	public EntidadRegistralVO getEntidadRegistral() {
		return entidadRegistral;
	}
	public void setEntidadRegistral(EntidadRegistralVO entidadRegistral) {
		this.entidadRegistral = entidadRegistral;
	}
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionRegistral() {
		return unidadTramitacionRegistral;
	}
	public void setUnidadTramitacionRegistral(
			UnidadTramitacionIntercambioRegistralVO unidadTramitacionRegistral) {
		this.unidadTramitacionRegistral = unidadTramitacionRegistral;
	}

}
