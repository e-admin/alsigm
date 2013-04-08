package es.ieci.tecdoc.fwktd.core.util.mime;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.ieci.tecdoc.fwktd.util.mime.MimeUtil;

@RunWith(JUnit4.class)
public class MimeUtilTests {

	@Test
	public void testMimeFromExtension() {
		Assert.assertEquals("application/msword", MimeUtil
				.getExtensionMimeType("doc"));
		Assert.assertEquals("application/pdf", MimeUtil
				.getExtensionMimeType("pdf"));
		Assert.assertEquals("text/plain", MimeUtil.getExtensionMimeType("txt"));
		Assert.assertEquals("application/x-excel", MimeUtil
				.getExtensionMimeType("xls"));
	}

	@Test
	public void testMimeFromFilename() {
		Assert.assertEquals("application/msword", MimeUtil
				.getExtensionMimeType("foo.doc"));
		Assert.assertEquals("application/pdf", MimeUtil
				.getExtensionMimeType("foo.pdf"));
		Assert.assertEquals("text/plain", MimeUtil
				.getExtensionMimeType("foo.txt"));
		Assert.assertEquals("application/x-excel", MimeUtil
				.getExtensionMimeType("foo.xls"));
	}

	@Test
	public void testMimeFromContent() {
		Assert.assertEquals("application/octet-stream", MimeUtil
				.getMimeType("Contenido de fichero de texto".getBytes()));
	}

	@Test
	public void testMimeFromFile() {
		Assert.assertEquals("text/plain", MimeUtil.getMimeType(new File(
				getClass().getClassLoader().getResource(
						"es/ieci/tecdoc/fwktd/core/util/mime/sample.txt")
						.getFile())));
	}

	@Test
	public void testMimeFromStream() {
		Assert.assertEquals("application/octet-stream", MimeUtil
				.getMimeType(getClass().getClassLoader().getResourceAsStream(
						"es/ieci/tecdoc/fwktd/core/util/mime/sample.txt")));
	}

}
