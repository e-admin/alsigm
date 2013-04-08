package es.ieci.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Implementación mock de un <code>ValuedEnum</code> con fines de test.
 * 
 * @author IECISA
 * @see ValuedEnum
 */
public class MockValuedEnum extends ValuedEnum {

	public static final int MOCK_VALUE_1 = 1;
	public static final int MOCK_VALUE_2 = 2;
	public static final int MOCK_VALUE_3 = 3;

	public static final MockValuedEnum MOCK_ENUM_1 = new MockValuedEnum(
			"ENUM_1", MOCK_VALUE_1);
	public static final MockValuedEnum MOCK_ENUM_2 = new MockValuedEnum(
			"ENUM_2", MOCK_VALUE_2);
	public static final MockValuedEnum MOCK_ENUM_3 = new MockValuedEnum(
			"ENUM_3", MOCK_VALUE_3);

	protected MockValuedEnum(String name, int value) {
		super(name, value);
	}

	// Members
	private static final long serialVersionUID = -561548148409572733L;

}
