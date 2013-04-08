package es.ieci.tecdoc.isicres.api.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

import com.ieci.tecdoc.common.keys.ISicresKeys;

/**
 * Enumerado que representa los diferentes tipos de libros soportados.
 * 
 * @author IECISA
 * 
 */
public class TipoLibroEnum extends ValuedEnum {

	public static final TipoLibroEnum ENTRADA = new TipoLibroEnum("ENTRADA",
			ISicresKeys.SCRREGSTATE_IN_BOOK);
	public static final TipoLibroEnum SALIDA = new TipoLibroEnum("SALIDA",
			ISicresKeys.SCRREGSTATE_OUT_BOOK);

	protected TipoLibroEnum(String name, int value) {
		super(name, value);
	}

	public static TipoLibroEnum getEnum(int valor) {
		return (TipoLibroEnum) getEnum(TipoLibroEnum.class, valor);
	}

	public static Map getEnumMap() {
		return getEnumMap(TipoLibroEnum.class);
	}

	public static List getEnumList() {
		return getEnumList(TipoLibroEnum.class);
	}

	public static Iterator iterator() {
		return iterator(TipoLibroEnum.class);
	}

	// Members
	private static final long serialVersionUID = -4538231008548477713L;

}
