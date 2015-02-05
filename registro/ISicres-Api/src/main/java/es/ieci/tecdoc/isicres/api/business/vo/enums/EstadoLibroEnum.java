package es.ieci.tecdoc.isicres.api.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 
 * @author IECISA
 * 
 */
public class EstadoLibroEnum extends ValuedEnum {

	private static final long serialVersionUID = -6279914592994255931L;

	public static final EstadoLibroEnum ABIERTO = new EstadoLibroEnum("0", 0);

	public static final EstadoLibroEnum CERRADO = new EstadoLibroEnum("1", 1);

	public static EstadoLibroEnum getEnum(int valor) {
		return (EstadoLibroEnum) getEnum(EstadoLibroEnum.class, valor);
	}

	public static Map getEnumMap() {
		return getEnumMap(EstadoLibroEnum.class);
	}

	public static List getEnumList() {
		return getEnumList(EstadoLibroEnum.class);
	}

	public static Iterator iterator() {
		return iterator(EstadoLibroEnum.class);
	}

	protected EstadoLibroEnum(String name, int value) {
		super(name, value);
	}

}
