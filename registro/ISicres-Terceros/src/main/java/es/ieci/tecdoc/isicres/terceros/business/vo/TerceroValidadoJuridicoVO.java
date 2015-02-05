package es.ieci.tecdoc.isicres.terceros.business.vo;

import javax.validation.constraints.Size;

/**
 * Tercero jurídico validado.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TerceroValidadoJuridicoVO extends TerceroValidadoVO {

	private static final long serialVersionUID = -5803449266680248506L;

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	@Override
	public String getDescripcion() {
		return getRazonSocial();
	}

	@Size(max = 80)
	protected String razonSocial;

}
