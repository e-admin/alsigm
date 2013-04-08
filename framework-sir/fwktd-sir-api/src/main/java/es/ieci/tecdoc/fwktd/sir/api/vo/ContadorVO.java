package es.ieci.tecdoc.fwktd.sir.api.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;

/**
 * Información de un contador.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ContadorVO extends BaseValueObject {

	private static final long serialVersionUID = -629435055321516642L;

	/**
	 * Código de la entidad registral.
	 */
	private String codigoEntidadRegistral = null;

	/**
	 * Tipo de contador
	 */
	private TipoContadorEnum tipoContador = null;

	/**
	 * Contador
	 */
	private long contador = 0;

	/**
	 * Constructor.
	 */
	public ContadorVO() {
		super();
	}

	public String getCodigoEntidadRegistral() {
		return codigoEntidadRegistral;
	}

	public void setCodigoEntidadRegistral(String codigoEntidadRegistral) {
		this.codigoEntidadRegistral = codigoEntidadRegistral;
	}

	public TipoContadorEnum getTipoContador() {
		return tipoContador;
	}

	public void setTipoContador(TipoContadorEnum tipoContador) {
		this.tipoContador = tipoContador;
	}

	public long getContador() {
		return contador;
	}

	public void setContador(long contador) {
		this.contador = contador;
	}

}
