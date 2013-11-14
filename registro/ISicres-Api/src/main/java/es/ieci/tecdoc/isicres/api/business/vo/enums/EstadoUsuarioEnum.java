/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.vo.enums;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class EstadoUsuarioEnum extends ValuedEnum{


	public static final EstadoUsuarioEnum DESBLOQUEADO = new EstadoUsuarioEnum("Desbloqueado" , 0);
	public static final EstadoUsuarioEnum BLOQUEADO = new EstadoUsuarioEnum("Bloqueado" , 1);


	public static EstadoUsuarioEnum getEnum(int valor) {
		return (EstadoUsuarioEnum) getEnum(EstadoUsuarioEnum.class, valor);
	}

	/**
	 * @param name
	 * @param value
	 */
	protected EstadoUsuarioEnum(String name, int value) {
		super(name, value);
	}

	private static final long serialVersionUID = 6208430016686176385L;



}
