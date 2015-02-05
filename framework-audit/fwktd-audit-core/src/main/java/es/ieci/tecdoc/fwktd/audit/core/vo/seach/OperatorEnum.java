package es.ieci.tecdoc.fwktd.audit.core.vo.seach;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

public class OperatorEnum extends ValuedEnum {
	

	private static final long serialVersionUID = -7922592069026811037L;
	
	public static int COMIENZA_POR_VALUE = 0;
	public static final OperatorEnum COMIENZA_POR = new OperatorEnum(
			"Empieza por", COMIENZA_POR_VALUE);

	public static int ES_IGUAL_VALUE = 1;
	public static final OperatorEnum ES_IGUAL = new OperatorEnum("Igual a",
			ES_IGUAL_VALUE);

	public static int CONTIENE_VALUE = 2;
	public static final OperatorEnum CONTIENE = new OperatorEnum("Contiene",
			CONTIENE_VALUE);
	
	private static int ES_MAYOR_VALUE = 3;
	public static final OperatorEnum ES_MAYOR = new OperatorEnum("EsMayor",ES_MAYOR_VALUE);
	
	private static int ES_MAYOR_IGUAL_VALUE = 4;
	public static final OperatorEnum ES_MAYOR_IGUAL = new OperatorEnum("EsMayorIgual",ES_MAYOR_IGUAL_VALUE);
	
	private static int ES_MENOR_VALUE = 5;
	public static final OperatorEnum ES_MENOR = new OperatorEnum("EsMenor",ES_MENOR_VALUE);
	
	private static int ES_MENOR_IGUAL_VALUE = 6;
	public static final OperatorEnum ES_MENOR_IGUAL = new OperatorEnum("EsMenorIgual",ES_MENOR_IGUAL_VALUE);
	
	/*
	private static int ES_IGUAL_VALUE = 7;
	public static final OperatorEnum ES_IGUAL = new OperatorEnum("EsIgual",ES_IGUAL_VALUE);
	*/
	
	public static int NO_ES_IGUAL_VALUE = 8;
	public static final OperatorEnum NO_ES_IGUAL = new OperatorEnum("No Igual a",
			NO_ES_IGUAL_VALUE);

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

}
