package es.ieci.plusvalias.util;

public interface Constants {
	static String DATE_PATTERN = "dd/MM/yyyy";
	static String YEAR_PATTERN = "yyyy";
	static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; // Milisegundos al día
	static String USER_CONTEXT = "USER_CONTEXT";

	String PROCEDURES_CODE[] = new String[] { "0104", "0105", "0107", "0207",
			"0313", "0413", "0501", "0503", "0504", "0505", "0507", "0508",
			"0510", "0511", "0512", "0513", "0514", "0515", "0517", "0604",
			"0701", "0702", "0704", "0705", "0707", "0708", "1019", "1021",
			"1103", "1106", "1107", "1938", "1943", "1945", "1952", "1957",
			"1972" };
}
