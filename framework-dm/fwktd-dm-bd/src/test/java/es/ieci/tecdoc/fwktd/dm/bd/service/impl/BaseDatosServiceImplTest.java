/*package es.ieci.tecdoc.fwktd.dm.bd.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.dm.bd.BaseDocumentoTest;
import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;

public class BaseDatosServiceImplTest extends BaseDocumentoTest {

	@Test
	public void testBDCreateDocumento() {

		try {

			List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();
			metadatos.add(createMetadato("id", 1));
			metadatos.add(createMetadato("name", "nombre 1"));
			metadatos.add(createMetadato("date", new Date()));

			InfoDocumentoVO infoDocumento = new InfoDocumentoVO();
			infoDocumento.setNombre("documento 1");
			infoDocumento.setTipoMime("text/plain");
			infoDocumento.setMetadatos(metadatos);

			InfoDocumentoVO infoDocumentoCreado = getService().createDocumento(infoDocumento,
					new ByteArrayInputStream("contenido".getBytes()));

			Assert.assertNotNull("Documento creado", infoDocumentoCreado);
			Assert.assertNotNull("Id de documento creado", infoDocumentoCreado.getId());

			setIdDocumento(infoDocumentoCreado.getId());

			Assert.assertEquals(infoDocumento.getNombre(), infoDocumentoCreado.getNombre());
			Assert.assertEquals(infoDocumento.getTipoMime(), infoDocumentoCreado.getTipoMime());
			Assert.assertTrue(infoDocumentoCreado.getTamano() > 0);
			Assert.assertNotNull("Fecha de creación", infoDocumentoCreado.getFechaCreacion());
			Assert.assertNotNull("Fecha de modificación", infoDocumentoCreado.getFechaModificacion());
			Assert.assertTrue(infoDocumentoCreado.getMetadatos().size() > 0);

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		}
	}

	@Test
	public void testBDExisteDocumento() {

		try {

			boolean existe = getService().existeDocumento(getIdDocumento());
			Assert.assertTrue("Documento existente", existe);

			existe = getService().existeDocumento(ID_DOCUMENTO_NO_EXISTENTE_BD);
			Assert.assertFalse("Documento no existente", existe);

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		}
	}

	@Test
	public void testBDGetInfoDocumento() {

		try {

			InfoDocumentoVO infoDocumento = getService().getInfoDocumento(getIdDocumento());
			Assert.assertNotNull(infoDocumento);
			Assert.assertEquals(getIdDocumento(), infoDocumento.getId());
			Assert.assertEquals("documento 1", infoDocumento.getNombre());
			Assert.assertEquals("text/plain", infoDocumento.getTipoMime());
			Assert.assertEquals(9, infoDocumento.getTamano());
			Assert.assertNotNull("Fecha de creación", infoDocumento.getFechaCreacion());
			Assert.assertNotNull("Fecha de modificación", infoDocumento.getFechaModificacion());

			Assert.assertNotNull(infoDocumento.getMetadatos());
			Assert.assertTrue(infoDocumento.getMetadatos().size() > 0);

			Iterator<MetadatoVO> iterator = infoDocumento.getMetadatos().iterator();
			MetadatoVO metadato = iterator.next();
			Assert.assertEquals("id", metadato.getNombre());
			Assert.assertEquals(1, metadato.getValor());

			metadato = iterator.next();
			Assert.assertEquals("name", metadato.getNombre());
			Assert.assertEquals("nombre 1", metadato.getValor());

			metadato = iterator.next();
			Assert.assertEquals("date", metadato.getNombre());
			Assert.assertNotNull(metadato.getValor());

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		}
	}

	@Test
	public void testBDRetrieveDocumento() {

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			getService().retrieveDocumento(getIdDocumento(), baos);

			byte[] contenido = baos.toByteArray();
			Assert.assertTrue(contenido.length > 0);

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		}
	}

	@Test
	public void testBDUpdateDocumento() {
		try {

			InfoDocumentoVO infoDocumento = getService().getInfoDocumento(getIdDocumento());
			Assert.assertNotNull(infoDocumento);

			infoDocumento.setNombre("documento 1 modificado");

			infoDocumento = getService().updateDocumento(infoDocumento, null);
			Assert.assertNotNull(infoDocumento);

			InfoDocumentoVO updatedDocumento = getService().getInfoDocumento(getIdDocumento());

			Assert.assertEquals(infoDocumento.getNombre(), updatedDocumento.getNombre());

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		}
	}

	@Test
	public void testBDUpdateDocumentoConContenido() {

		try {

			InfoDocumentoVO infoDocumento = getService().getInfoDocumento(getIdDocumento());

			infoDocumento.setNombre("documento 1 modificado");
			getService().updateDocumento(infoDocumento,
					new ByteArrayInputStream("contenido".getBytes()));

			InfoDocumentoVO updatedDocumento = getService().getInfoDocumento(getIdDocumento());

			Assert.assertEquals(infoDocumento.getNombre(), updatedDocumento.getNombre());

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		}
	}

	@Test
	public void testBDDeleteDocumento() {

		try {

			getService().deleteDocumento(getIdDocumento());

			Assert.assertFalse(getService().existeDocumento(getIdDocumento()));

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		}
	}

	protected MetadatoVO createMetadato(String nombre, Object valor) {
		MetadatoVO metadato = new MetadatoVO();
		metadato.setNombre(nombre);
		metadato.setValor(valor);
		return metadato;
	}
}*/
