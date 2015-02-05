package es.ieci.tecdoc.isicres.api.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerado para representar los diferentes estados en los que puede
 * encontrarse una distribución. Se trata de una especialización de
 * <code>org.apache.commons.lang.enums.Enum</code>.
 * 
 * Los diferentes valores que se pueden tomar:
 * 
 * <ul>
 * <li>1 - Pendiente</li>
 * <li>2 - Aceptado</li>
 * <li>3 - Archivado</li>
 * <li>4 - Rechazado</li>
 * <li>5 - Redistribuido</li>
 * <li>6: pendiente distribución ???</li>
 * </ul>
 * 
 * @see ValuedEnum
 * 
 * @author IECISA
 * 
 */
public class EstadoDistribucionEnum extends ValuedEnum {

	public static final EstadoDistribucionEnum PENDIENTE = new EstadoDistribucionEnum(
			"PENDIENTE", 1);
	public static final EstadoDistribucionEnum ACEPTADO = new EstadoDistribucionEnum(
			"ACEPTADO", 2);
	public static final EstadoDistribucionEnum ARCHIVADO = new EstadoDistribucionEnum(
			"ARCHIVADO", 3);
	public static final EstadoDistribucionEnum RECHAZADO = new EstadoDistribucionEnum(
			"RECHAZADO", 4);
	public static final EstadoDistribucionEnum REDISTRIBUIDO = new EstadoDistribucionEnum(
			"REDISTRIBUIDO", 5);
	public static final EstadoDistribucionEnum PENDIENTE_DISTRIBUCION = new EstadoDistribucionEnum(
			"PENDIENTE_DISTRIBUCION", 6);

	protected EstadoDistribucionEnum(String name, int value) {
		super(name, value);
	}

	public static EstadoDistribucionEnum getEnum(int valor) {
		return (EstadoDistribucionEnum) getEnum(EstadoDistribucionEnum.class,
				valor);
	}

	public static Map getEnumMap() {
		return getEnumMap(EstadoDistribucionEnum.class);
	}

	public static List getEnumList() {
		return getEnumList(EstadoDistribucionEnum.class);
	}

	public static Iterator iterator() {
		return iterator(EstadoDistribucionEnum.class);
	}

	// Members
	private static final long serialVersionUID = 6827742973433783546L;

}
