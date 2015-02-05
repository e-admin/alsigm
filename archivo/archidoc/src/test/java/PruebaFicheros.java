import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocBaseTest;


public class PruebaFicheros extends ArchidocBaseTest {

	private static final String DOCUMENTO_TXT = "/documentos/texto1.txt";



	private byte[] getFicheroTransferencia() throws Exception {
		return getFicheroAsBase64(getUrlFromClassPath(DOCUMENTO_TXT));
	}

	@Test
	public void pruebaFichero() throws Exception{
		byte[] fichero = getFicheroAsBase64(getUrlFromClassPath(DOCUMENTO_TXT));
		Assert.assertNotNull(fichero);

		byte[] fichero1 = getFicheroFromBase64("RG9jdW1lbnRvIGRlIFRleHRvLg==");

		Assert.assertNotNull(fichero1);


	}

}
