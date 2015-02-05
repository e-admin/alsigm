package es.ieci.tecdoc.isicres.terceros.business.vo;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

/**
 * Tercero físico validado.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TerceroValidadoFisicoVO extends TerceroValidadoVO {

	/**
	 * Constructor por defecto del tercero validado.
	 */
	public TerceroValidadoFisicoVO() {
		// nothing to do
	}

	@Override
	public void setNombre(String nombre) {
		super.setNombre(nombre);
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Override
	public String getDescripcion() {

		StringBuffer sb = new StringBuffer();

		if(!StringUtils.isBlank(getApellido1())){
			sb.append(getApellido1()).append(" ");
		}
		if(!StringUtils.isBlank(getApellido2())){
			sb.append(getApellido2()).append(", ");
		}
		if(!StringUtils.isBlank(getNombre())){
			sb.append(getNombre());
		}

		return sb.toString();
	}

	// Members
	private static final long serialVersionUID = -2985933888748086058L;

	/**
	 * Primer apellido del tercero.
	 */
	@Size(max = 30)
	protected String apellido1;

	/**
	 * Segundo apellido del tercero.
	 */
	@Size(max = 30)
	protected String apellido2;

}
