/**
 *
 */
package fondos.vos;

import common.Constants;
import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class InteresadoUdocVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String nombre = null;
	private String numeroIdentificacion = null;
	private String validado = null;
	private String rol = null;
	private String principal = null;
	private String idelementocf = null;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getValidado() {
		return validado;
	}

	public void setValidado(String validado) {
		this.validado = validado;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public void setIdelementocf(String idelementocf) {
		this.idelementocf = idelementocf;
	}

	public String getIdelementocf() {
		return idelementocf;
	}

	public boolean isPrincipal() {
		if (Constants.TRUE_FULL_STRING.equals(this.principal)) {
			return true;
		}
		return false;
	}

	public boolean isValidado() {
		if (Constants.TRUE_FULL_STRING.equals(this.validado)) {
			return true;
		}
		return false;
	}
}
