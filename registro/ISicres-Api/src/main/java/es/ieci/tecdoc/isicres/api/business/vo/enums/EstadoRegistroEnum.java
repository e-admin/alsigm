package es.ieci.tecdoc.isicres.api.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerado que modela los diferentes estados en los que se puede encontrar un
 * registro. Estos son:
 * 
 * <ul>
 * <li>0: Completo</li>
 * <li>1: Incompleto</li>
 * <li>2: Reservado</li>
 * <li>4: Anulado</li>
 * <li>5: Cerrado</li>
 * </ul>
 * 
 * @author IECISA
 * 
 */
public class EstadoRegistroEnum extends ValuedEnum {

	public final static EstadoRegistroEnum COMPLETO = new EstadoRegistroEnum(
			"COMPLETO", 0);
	public final static EstadoRegistroEnum INCOMPLETO = new EstadoRegistroEnum(
			"INCOMPLETO", 1);
	public final static EstadoRegistroEnum RESERVADO = new EstadoRegistroEnum(
			"RESERVADO", 2);
	public final static EstadoRegistroEnum ANULADO = new EstadoRegistroEnum(
			"ANULADO", 4);
	public final static EstadoRegistroEnum CERRADO = new EstadoRegistroEnum(
			"CERRADO", 5);

	public static EstadoRegistroEnum getEnum(int valor) {
		return (EstadoRegistroEnum) getEnum(EstadoRegistroEnum.class, valor);
	}

	public static Map getEnumMap() {
		return getEnumMap(EstadoRegistroEnum.class);
	}

	public static List getEnumList() {
		return getEnumList(EstadoRegistroEnum.class);
	}

	public static Iterator iterator() {
		return iterator(EstadoRegistroEnum.class);
	}

	protected EstadoRegistroEnum(String name, int value) {
		super(name, value);
	}

	private static final long serialVersionUID = -6370035961372888107L;

}
