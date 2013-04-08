package es.ieci.tecdoc.isicres.terceros.business.vo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 *
 * @author IECISA
 *
 */
public class OperatorEnum extends ValuedEnum {

	public static int COMIENZA_POR_VALUE = 0;
	public static final OperatorEnum COMIENZA_POR = new OperatorEnum(
			"Empieza por", COMIENZA_POR_VALUE);

	public static int ES_IGUAL_VALUE = 1;
	public static final OperatorEnum ES_IGUAL = new OperatorEnum("Igual a",
			ES_IGUAL_VALUE);

	public static int CONTIENE_VALUE = 2;
	public static final OperatorEnum CONTIENE = new OperatorEnum("Contiene",
			CONTIENE_VALUE);

	protected OperatorEnum(String name, int value) {
		super(name, value);
	}

	public static OperatorEnum getEnum(String name) {
		return (OperatorEnum) getEnum(OperatorEnum.class, name);
	}

	public static OperatorEnum getEnum(int value) {
		return (OperatorEnum) getEnum(OperatorEnum.class, value);
	}

	@SuppressWarnings("unchecked")
	public static Map getEnumMap() {
		return getEnumMap(OperatorEnum.class);
	}

	@SuppressWarnings("unchecked")
	public static List<OperatorEnum> getEnumList() {
		return getEnumList(OperatorEnum.class);
	}

	@SuppressWarnings("unchecked")
	public static Iterator<OperatorEnum> iterator() {
		return iterator(OperatorEnum.class);
	}

	private static final long serialVersionUID = 3387538899057899321L;

}
