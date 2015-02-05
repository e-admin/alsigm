package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants;

/**
 * Constantes para indicar el tamanio del papel en el escaneo
 */
public class ScanSizeConstants {

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_NONE = 0;

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_A4 = 1;

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_B5 = 2;

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_US_LETTER = 3;

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_US_LEGAL = 4;

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_A5 = 5;

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_A6 = 13;

	/**
	 * Tamanio de papel. Valor {@value}
	 */
	public static final int PAPER_SIZE_A8 = 23;

	private static final String PAPER_SIZE_NONE_DESCRIPTION = "None";
	private static final String PAPER_SIZE_A4_DESCRIPTION = "A4";
	private static final String PAPER_SIZE_B5_DESCRIPTION = "B5 Letter";
	private static final String PAPER_SIZE_US_LETTER_DESCRIPTION = "US Letter";
	private static final String PAPER_SIZE_US_LEGAL_DESCRIPTION = "US Legal";
	private static final String PAPER_SIZE_A5_DESCRIPTION = "A5";
	private static final String PAPER_SIZE_A6_DESCRIPTION = "A6";
	private static final String PAPER_SIZE_A8_DESCRIPTION = "A8";

	/**
	 * Permite obtener la descripcion de un tamanio de escaneo
	 * @param size tamanio del papel
	 * @return descripcion del tamanio del papel
	 */
	public static String getPaperSizeDescription(int size){
		String description = null;
		switch(size){
		case PAPER_SIZE_NONE: description = PAPER_SIZE_NONE_DESCRIPTION; break;
		case PAPER_SIZE_A4: description =PAPER_SIZE_A4_DESCRIPTION; break;
		case PAPER_SIZE_B5: description = PAPER_SIZE_B5_DESCRIPTION; break;
		case PAPER_SIZE_US_LETTER: description = PAPER_SIZE_US_LETTER_DESCRIPTION; break;
		case PAPER_SIZE_US_LEGAL: description = PAPER_SIZE_US_LEGAL_DESCRIPTION; break;
		case PAPER_SIZE_A5: description = PAPER_SIZE_A5_DESCRIPTION; break;
		case PAPER_SIZE_A6: description = PAPER_SIZE_A6_DESCRIPTION; break;
		case PAPER_SIZE_A8: description = PAPER_SIZE_A8_DESCRIPTION; break;
		}
		return description;
	}
}
