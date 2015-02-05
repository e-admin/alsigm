package common.db;

public class ConstraintType {

	private static final String[] OPERATORS = { "=", "<", ">", "!=", "like",
			"contains", "<=", ">=", "@@" };

	public static final int EQUAL = 0;
	public static final int LESS = 1;
	public static final int GREATER = 2;
	public static final int DISTINCT = 3;
	public static final int LIKE = 4;
	public static final int CONTAINS = 5;
	// public static final int LIKE_IGNORE_CASE = 6;
	public static final int LESS_OR_EQUAL = 6;
	public static final int GREATER_OR_EQUAL = 7;
	public static final int AT_AT = 8;

	public static String getSymbol(int constraintType) {
		return OPERATORS[constraintType];
	}

	public static int getValue(String operator) {
		for (int i = 0; i < OPERATORS.length; i++) {
			if (OPERATORS[i].equals(operator)) {
				return i;
			}
		}
		return -1;
	}
}
