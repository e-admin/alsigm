package es.ieci.tecdoc.fwktd.applets.scan.scanner.mms.constants;

import uk.co.mmscomputing.device.twain.TwainCapability;

/**
 * Constantes con los posibles modos de escaneo
 */
public class ScanModeConstants {

	/**
	 * Escaneo en modo nativo. Valor: {@value}
	 */
	public final static int SCAN_MODE_NATIVE = TwainCapability.TWSX_NATIVE;

	/**
	 * Escaneo en modo fichero. Valor: {@value}
	 */
	public final static int SCAN_MODE_FILE = TwainCapability.TWSX_FILE;

	/**
	 * Escaneo en modo memoria. Valor: {@value}
	 */
	public final static int SCAN_MODE_MEMORY = TwainCapability.TWSX_MEMORY;

	/**
	 * Escaneo en modo memoria con serializacion por bloques. Valor: {@value}
	 */
	public final static int SCAN_MODE_MEMORY_SERIALIZED = 3;

	/**
	 * Escaneo en modo memoria con escritura de bloques en jpg. Valor: {@value}
	 */
	public final static int SCAN_MODE_MEMORY_JPG_BLOCKS = 4;

	private static String SCAN_MODE_NATIVE_DESCRIPTION = "NATIVE";
	private static String SCAN_MODE_FILE_DESCRIPTION = "FILE";
	private static String SCAN_MODE_MEMORY_DESCRIPTION = "MEMORY";
	private static String SCAN_MODE_MEMORY_DESCRIPTION_SERIALIZED = "SERIALIZED";
	private static String SCAN_MODE_MEMORY_DESCRIPTION_JPG_BLOCKS = "JPG_BLOCKS";

	/**
	 * Permite obtener la descripcion de un modo de escaneo
	 * @param mode modo de escaneo
	 * @return descripcion del modo de escaneo
	 */
	public static String getScanModeDescription(int mode){
		String description = null;
		switch(mode){
		case SCAN_MODE_NATIVE: description = SCAN_MODE_NATIVE_DESCRIPTION; break;
		case SCAN_MODE_FILE: description =SCAN_MODE_FILE_DESCRIPTION; break;
		case SCAN_MODE_MEMORY: description = SCAN_MODE_MEMORY_DESCRIPTION; break;
		case SCAN_MODE_MEMORY_SERIALIZED: description = SCAN_MODE_MEMORY_DESCRIPTION_SERIALIZED; break;
		case SCAN_MODE_MEMORY_JPG_BLOCKS: description = SCAN_MODE_MEMORY_DESCRIPTION_JPG_BLOCKS; break;
		}
		return description;
	}
}
