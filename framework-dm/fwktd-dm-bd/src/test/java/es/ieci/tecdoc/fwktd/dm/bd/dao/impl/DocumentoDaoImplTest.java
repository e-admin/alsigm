/*package es.ieci.tecdoc.fwktd.dm.bd.dao.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.dm.bd.BaseDocumentoTest;
import es.ieci.tecdoc.fwktd.dm.bd.vo.DocumentoVO;

public class DocumentoDaoImplTest extends BaseDocumentoTest {

	@Test
	public void testInsertDocumento() {

		DocumentoVO documento = new DocumentoVO();
		documento.setId("999");
		documento.setNombre("documento 1");
		documento.setTipoMime("text/plain");
		documento.setTamano(4096);
		documento.setFechaCreacion(new Date());
		documento.setFechaCreacion(documento.getFechaCreacion());
		documento.setMetadatos("<?xml version=\"1.0\" encoding=\"UTF-8\"?><metadatos version=\"1.0\"><metadato id=\"1\"><nombre><![CDATA[m1]]></nombre><tipo><![CDATA[t1]]></tipo><tipoDocumental><![CDATA[td1]]></tipoDocumental><valor><![CDATA[v1]]></valor></metadato><metadato id=\"2\"><nombre><![CDATA[m2]]></nombre><tipo><![CDATA[t2]]></tipo><tipoDocumental><![CDATA[td2]]></tipoDocumental><valor><![CDATA[v2]]></valor></metadato></metadatos>");
		documento.setContenido("contenido".getBytes());

		documento = getDocumentoDao().save(documento);

		setIdDocumento(documento.getId());

		Assert.assertNotNull("No se ha insertado el documento",
				getDocumentoDao().get(getIdDocumento()));
	}

	@Test
	public void testExisteDocumento() {
		boolean existe = getDocumentoDao().exists(getIdDocumento());
		Assert.assertTrue(existe);

		existe = getDocumentoDao().exists(ID_DOCUMENTO_NO_EXISTENTE_BD);
		Assert.assertFalse(existe);
	}

	@Test
	public void testGetDocumento() {
		DocumentoVO documento = getDocumentoDao().get(getIdDocumento());
		Assert.assertNotNull(documento);
		Assert.assertEquals(getIdDocumento(), documento.getId());
		Assert.assertEquals("documento 1", documento.getNombre());
		Assert.assertEquals("text/plain", documento.getTipoMime());
		Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><metadatos version=\"1.0\"><metadato id=\"1\"><nombre><![CDATA[m1]]></nombre><tipo><![CDATA[t1]]></tipo><tipoDocumental><![CDATA[td1]]></tipoDocumental><valor><![CDATA[v1]]></valor></metadato><metadato id=\"2\"><nombre><![CDATA[m2]]></nombre><tipo><![CDATA[t2]]></tipo><tipoDocumental><![CDATA[td2]]></tipoDocumental><valor><![CDATA[v2]]></valor></metadato></metadatos>", documento.getMetadatos());
	}

	@Test
	public void testGetContenidoDocumento() {
		byte[] contenido = getDocumentoDao().getContenidoDocumento(getIdDocumento());
		Assert.assertNotNull(contenido);
		Assert.assertTrue(contenido.length > 0);
	}


	@Test
	public void testUpdateDocumento() {
		DocumentoVO documento = getDocumentoDao().get(getIdDocumento());

		documento.setNombre("documento 1 modificado");
		documento.setFechaModificacion(new Date());

		documento = getDocumentoDao().update(documento);

		Assert.assertNotNull("Documento nulo", documento);

		DocumentoVO updatedDocumento = getDocumentoDao().get(getIdDocumento());

		Assert.assertEquals(documento.getNombre(), updatedDocumento.getNombre());
	}

	@Test
	public void testUpdateDocumentoConContenido() {
		DocumentoVO documento = getDocumentoDao().get(getIdDocumento());

		documento.setNombre("documento 1 modificado");
		documento.setContenido("contenido modificado".getBytes());

		documento = getDocumentoDao().update(documento);

		Assert.assertNotNull("Documento nulo", documento);

		DocumentoVO updatedDocumento = getDocumentoDao().get(getIdDocumento());

		Assert.assertEquals(documento.getNombre(), updatedDocumento.getNombre());
	}

	@Test
	public void testDeleteDocumento() {
		int count = getDocumentoDao().count();
		getDocumentoDao().delete(getIdDocumento());
		Assert.assertEquals(count -1 , getDocumentoDao().count());
	}

}*/
