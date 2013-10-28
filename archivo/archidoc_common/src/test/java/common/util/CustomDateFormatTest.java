package common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.Test;

import test.ArchidocCommonBaseTest;

import common.Constants;

/**
 * Utilidad para formatear fechas.
 */
public class CustomDateFormatTest extends ArchidocCommonBaseTest {


//	public static CustomDateRange getDateRange() {
//	}

//	public static CustomDateRange getDateTimeRange(String operador,
//			CustomDate fecha, CustomDate fechaIni, CustomDate fechaFin) {
//
//	}

	@Test
	public void testGetPattern(){
		String pattern;
		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAA, CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("yyyy", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAAMM, CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("yyyy/MM", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAAMMDD, CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("yyyy/MM/dd", pattern);


		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAA, CustomDateFormat.SEPARADOR_FECHA_GUION);
		Assert.assertEquals("yyyy", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAAMM, CustomDateFormat.SEPARADOR_FECHA_GUION);
		Assert.assertEquals("yyyy-MM", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAAMMDD, CustomDateFormat.SEPARADOR_FECHA_GUION);
		Assert.assertEquals("yyyy-MM-dd", pattern);


		pattern = CustomDateFormat.getPattern("AAAAMMDD HHMMSS", CustomDateFormat.SEPARADOR_FECHA_GUION);
		Assert.assertEquals("yyyy-MM-dd HH:mm:ss", pattern);

		pattern = CustomDateFormat.getPattern("DDMMAAAA HHMMSS", CustomDateFormat.SEPARADOR_FECHA_GUION);
		Assert.assertEquals("dd-MM-yyyy HH:mm:ss", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS, CustomDateFormat.SEPARADOR_FECHA_GUION);
		Assert.assertEquals("yyyy-MM-dd HH:mm:ss", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS, CustomDateFormat.SEPARADOR_FECHA_GUION);
		Assert.assertEquals("dd-MM-yyyy HH:mm:ss", pattern);



		pattern = CustomDateFormat.getPattern("AAAAMMDD HHMMSS", CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("yyyy/MM/dd HH:mm:ss", pattern);

		pattern = CustomDateFormat.getPattern("DDMMAAAA HHMMSS", CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("dd/MM/yyyy HH:mm:ss", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS, CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("yyyy/MM/dd HH:mm:ss", pattern);

		pattern = CustomDateFormat.getPattern(CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS, CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("dd/MM/yyyy HH:mm:ss", pattern);



		pattern = CustomDateFormat.getPattern("VALORERRONEO", CustomDateFormat.SEPARADOR_FECHA_BARRA);
		Assert.assertEquals("VALORERRONEO", pattern);

	}

//	public static SimpleDateFormat getSimpleDateFormat(String formato, String separador){
//
//	}
}
