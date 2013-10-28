package common.util;

import junit.framework.Assert;

import org.junit.Test;

import test.ArchidocCommonBaseTest;

/**
 * Clase que almacena la información de una fecha.
 */
public class CustomDateTest extends ArchidocCommonBaseTest {

	/**
    * Constructor.
    */
//	public CustomDate(String value, String format, String separator,
//			String qualifier) {
//
//	}

//	public CustomDate(String format, String year, String month, String day,
//			String century) {
//
//	}
//
//	public CustomDate(String format, String year, String month, String day,
//			String hour, String minutes, String seconds, String century) {
//		this(format, year, month, day, hour, minutes, seconds, century, null,
//				null);
//	}

//	public Date getDate() {
//
//	}


//	public CustomDate(String format, String year, String month, String day,
//			String hour, String minutes, String seconds, String century,
//			String separator, String qualifier) {
//
//	}


//	private void formatValue() {
//	}
//
//	public String getTime() {
//
//	}


//	public Date getMinDate() {
//
//	}

//	public Date getMaxDate() {
//
//	}

	@Test
	public void testValidateAAAAMMDDHHMMSS() {
		CustomDate cd = new CustomDate(CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS,"2013","5","3","6","4","3",null);
		Assert.assertTrue(cd.validate());
		Assert.assertEquals("2013/05/03 06:04:03",cd.getValue());

	}

	@Test
	public void testErrorValidateAAAAMMDDHHMMSS() {
		CustomDate cd = new CustomDate(CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS,"2013","25","75","80","90","77",null);
		Assert.assertFalse(cd.validate());
	}

	@Test
	public void testValidateDDMMAAAAHHMMSS() {
		CustomDate cd = new CustomDate(CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS,"2013","5","3","6","4","3",null);
		Assert.assertTrue(cd.validate());


		Assert.assertEquals("03/05/2013 06:04:03",cd.getValue());
	}

	@Test
	public void testErrorValidateDDMMAAAAHHMMSS() {
		CustomDate cd = new CustomDate(CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS,"2013","25","75","80","90","77",null);
		Assert.assertFalse(cd.validate());
	}


	@Test
	public void testValidateConstructorDDMMAAAAHHMMSS() {
		CustomDate cd = new CustomDate( "03/05/2013 06:04:03",CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS,"/",null);
		Assert.assertTrue(cd.validate());

		Assert.assertEquals("03/05/2013 06:04:03",cd.getValue());
	}


	@Test
	public void testValidateConstructorAAAAMMDDHHMMSS() {
		CustomDate cd = new CustomDate( "2013/05/03 06:04:03",CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS,"/",null);
		Assert.assertTrue(cd.validate());
		Assert.assertEquals("2013/05/03 06:04:03",cd.getValue());
	}

	@Test
	public void testValidateConstructorGuionDDMMAAAAHHMMSS() {
		CustomDate cd = new CustomDate( "03-05-2013 06:04:03",CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS,"-",null);
		Assert.assertTrue(cd.validate());

		Assert.assertEquals("03-05-2013 06:04:03",cd.getValue());
	}


	@Test
	public void testValidateConstructorGuionAAAAMMDDHHMMSS() {
		CustomDate cd = new CustomDate( "2013-05-03 06:04:03",CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS,"-",null);
		Assert.assertTrue(cd.validate());
		Assert.assertEquals("2013-05-03 06:04:03",cd.getValue());
	}

	@Test
	public void testValidateConstructorGuionAAAAMMDDHHMMSSConEspacios() {
		CustomDate cd = new CustomDate( "2013-05-03 06:04:03","AAAA MM DD HH MM SS","-",null);
		Assert.assertTrue(cd.validate());
		Assert.assertEquals("2013-05-03 06:04:03",cd.getValue());
	}

	@Test
	public void testValidateConstructorSinFecha(){
		CustomDate cd = new CustomDate(null,null,null,null);
	}
}