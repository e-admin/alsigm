package common.manager;

import ieci.core.base64.Base64Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import ws.transferencias.vo.TramitadorInfo;
import ws.transferencias.vo.TransferenciaInfo;

import common.util.FileHelper;


@ContextConfiguration({ "/beans/datasource-beans-test.xml",
		"/beans/transaction-beans-test.xml"

})
public abstract class ArchidocBaseTest extends AbstractJUnit4SpringContextTests {


	protected URL getUrlFromClassPath(String file){
		return this.getClass().getResource(file);
	}

	protected String getContenidoFichero(URL url) throws IOException {
		return FileHelper.getTextFileContent(url.getFile());
	}

	protected byte[] getFicheroAsBase64(URL url) throws Exception{
		String contenidoXmlBase64 = Base64Util.encodeString(getContenidoFichero(url));
		return Base64Util.decode(contenidoXmlBase64);
	}

	protected byte[] getFicheroFromBase64(String binario) throws Exception{
		return Base64Util.decode(binario);
	}


	protected InputStream getInputStream(String path) throws FileNotFoundException{
		return new FileInputStream(new File(path));
	}


	protected Integer ANIO = 2012;
	protected String CODPROCEDIMIENTO = "PCD1";
	protected TramitadorInfo SISTEMA_TRAMITADOR = new TramitadorInfo("TE","NOMBRE TRAMITADOR");


	protected TransferenciaInfo getMockTransferenciaElectronicaInfo(byte[] contenidoXml){
		TransferenciaInfo transferenciaInfo = new TransferenciaInfo(ANIO, CODPROCEDIMIENTO,SISTEMA_TRAMITADOR,contenidoXml,0);
		return transferenciaInfo;
	}

}
