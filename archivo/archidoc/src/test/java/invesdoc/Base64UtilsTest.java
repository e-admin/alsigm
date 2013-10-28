package invesdoc;

import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocBaseTest;

public class Base64UtilsTest extends ArchidocBaseTest{

	String encData = "P10+fNqOyHw=";

	//byte[] respuesta = new byte[8]{63, 93, 62, 124, -38, -114, -56, 124};
	@Test
	public void testDecodeI8(){
			try {
			byte[] decodificado = ieci.tecdoc.core.base64.Base64Util.decode(encData);

			valida(decodificado);

			//Assert.assertEquals(respuesta, decodificado);
			//63, 93, 62, 124, -38, -114, -56, 124
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDecodeArchidoc(){
			try {
			byte[] decodificado = ieci.core.base64.Base64Util.decode(encData);
			//Assert.assertEquals(respuesta, decodificado);
			//63, 93, 62, 124, -38, -114, -56, 124

			valida(decodificado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void valida(byte[] array){
		Assert.assertTrue(array.length == 8);
		Assert.assertTrue(array[0] == 63);
		Assert.assertTrue(array[1] == 93);
		Assert.assertTrue(array[2] == 62);
		Assert.assertTrue(array[3] == 124);
		Assert.assertTrue(array[4] == -38);
		Assert.assertTrue(array[5] == -114);
		Assert.assertTrue(array[6] == -56);
		Assert.assertTrue(array[7] == 124);
	}



	//byte[] respuesta = new byte[8]{63, 93, 62, 124, -38, -114, -56, 124};
	@Test
	public void testDecodeI8String(){
			try {
			String decodificado = ieci.tecdoc.core.base64.Base64Util.decodeToString(encData);
			Assert.assertEquals("?]>|ÚŽÈ|", decodificado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDecodeArchidocString(){
			try {
			String decodificado = ieci.core.base64.Base64Util.decodeToString(encData);
			//Assert.assertEquals(respuesta, decodificado);
			//63, 93, 62, 124, -38, -114, -56, 124
			Assert.assertEquals("?]>|ÚŽÈ|", decodificado);
			//valida(decodificado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
