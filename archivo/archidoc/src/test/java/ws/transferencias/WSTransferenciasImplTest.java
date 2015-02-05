package ws.transferencias;

import junit.framework.Assert;

import ieci.tecdoc.core.base64.Base64Util;
import common.manager.ArchidocServiceBaseTest;


public class WSTransferenciasImplTest {

	public void transferirExpedienteElectronicoConDocumentos() {

	}


	public void testBase64() throws Exception{
		String password = "archivo";
		String passwordBase64= "YXJjaGl2bw==";

		String newPassword = Base64Util.decodeToString(passwordBase64);


		Assert.assertEquals(password, newPassword);

	}

}
