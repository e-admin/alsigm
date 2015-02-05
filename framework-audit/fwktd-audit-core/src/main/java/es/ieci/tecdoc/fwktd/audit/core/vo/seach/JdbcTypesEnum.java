package es.ieci.tecdoc.fwktd.audit.core.vo.seach;

import org.apache.commons.lang.enums.ValuedEnum;

public class JdbcTypesEnum extends ValuedEnum {

	protected JdbcTypesEnum(String name, int value) {
		super(name, value);
		// TODO Plantilla de constructor auto-generado
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1929527001247571687L;

	private static final int VARCHAR_VALUE = 1;

	public static final JdbcTypesEnum VARCHAR = new JdbcTypesEnum("VARCHAR",
			VARCHAR_VALUE);
	
	private static final int BIG_INT_VALUE = 2;

	public static final JdbcTypesEnum BIG_INT = new JdbcTypesEnum("BIGINT",
			BIG_INT_VALUE);
	
	private static final int TIMESTAMP_VALUE = 3;

	public static final JdbcTypesEnum TIMESTAMP = new JdbcTypesEnum("TIMESTAMP",
			TIMESTAMP_VALUE);
	

}
