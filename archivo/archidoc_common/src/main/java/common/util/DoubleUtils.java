package common.util;

import java.math.BigDecimal;

/**
 * Utilidades para trabajar sobre double
 */
public class DoubleUtils {

	/** Redondea un double al número de decimales indicado
	 * @param value Valor a redondear
	 * @param decimalPlace número de decimales
	 * @return
	 */
	public static double round(double value, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
