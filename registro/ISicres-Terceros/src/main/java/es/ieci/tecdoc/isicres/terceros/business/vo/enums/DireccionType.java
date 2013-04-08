package es.ieci.tecdoc.isicres.terceros.business.vo.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerado para distinguir los posibles tipos de direcciones: físicas y
 * telemáticas.
 *
 * @author IECISA
 *
 */
public class DireccionType extends ValuedEnum {

	public static final int FISICA_VALUE = 0;

	public static final int TELEMATICA_VALUE = 1;

	public static final DireccionType FISICA = new DireccionType("FISICA",
			FISICA_VALUE);

	public static final DireccionType TELEMATICA = new DireccionType(
			"TELEMATICA", TELEMATICA_VALUE);

	protected DireccionType(String name, int value) {
		super(name, value);
	}

	public static DireccionType getEnum(String javaVersion) {
		return (DireccionType) getEnum(DireccionType.class, javaVersion);
	}

	public static DireccionType getEnum(int javaVersion) {
		return (DireccionType) getEnum(DireccionType.class, javaVersion);
	}

	public static Map getEnumMap() {
		return getEnumMap(DireccionType.class);
	}

	public static List<DireccionType> getEnumList() {
		return getEnumList(DireccionType.class);
	}

	public static Iterator<DireccionType> iterator() {
		return iterator(DireccionType.class);
	}

	@Override
	public String toString() {
		return this.getName();
	}

	private static final long serialVersionUID = -8842470385526494315L;

}
