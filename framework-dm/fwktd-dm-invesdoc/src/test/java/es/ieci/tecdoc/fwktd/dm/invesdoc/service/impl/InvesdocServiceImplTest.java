package es.ieci.tecdoc.fwktd.dm.invesdoc.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dm.business.exception.GestionDocumentalException;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalService;
import es.ieci.tecdoc.fwktd.dm.business.service.GestionDocumentalServiceFactory;
import es.ieci.tecdoc.fwktd.dm.business.vo.InfoDocumentoVO;
import es.ieci.tecdoc.fwktd.dm.business.vo.MetadatoVO;

@ContextConfiguration( {
	"/beans/fwktd-dm-core-applicationContext.xml",
	"/beans/fwktd-dm-invesdoc-applicationContext.xml" })
public class InvesdocServiceImplTest extends AbstractJUnit4SpringContextTests {

	protected static final String CONTENT_TYPE_ID = "1";

	private static ThreadLocal<String> idDocumento = new ThreadLocal<String>();

	@Autowired
	private GestionDocumentalServiceFactory fwktd_dm_serviceFactory = null;

	protected GestionDocumentalService getGestionDocumentalService() {
		return fwktd_dm_serviceFactory.getService(CONTENT_TYPE_ID);
	}

	protected void setIdDocumento(String id) {
		idDocumento.set(id);
	}

	protected String getIdDocumento() {
		return idDocumento.get();
	}


	@Test
	public void testIdocCreateDocumento() {

		GestionDocumentalService service = getGestionDocumentalService();

		try {

			service.createSesion();

			List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();
			metadatos.add(createMetadato("document_id", new Integer(1)));
			metadatos.add(createMetadato("document_name", "Documento.txt"));
			metadatos.add(createMetadato("document_type", new Integer(9)));
			metadatos.add(createMetadato("NO_EXISTE", "vacio"));

			InfoDocumentoVO infoDocumento = new InfoDocumentoVO();
			infoDocumento.setNombre("Documento.txt");
			infoDocumento.setTipoMime("text/plain");
			infoDocumento.setMetadatos(metadatos);

			InfoDocumentoVO infoDocumentoCreado = service.createDocumento(
					infoDocumento, new ByteArrayInputStream("contenido".getBytes()));

			Assert.assertNotNull("No se ha creado el documento", infoDocumentoCreado);
			Assert.assertNotNull(infoDocumentoCreado.getId());

			setIdDocumento(infoDocumentoCreado.getId());

			Assert.assertEquals(infoDocumento.getNombre(), infoDocumentoCreado.getNombre());
			Assert.assertEquals(infoDocumento.getTipoMime(), infoDocumentoCreado.getTipoMime());
			Assert.assertTrue(infoDocumentoCreado.getTamano() > 0);
			//Assert.assertNotNull(infoDocumentoCreado.getFechaCreacion());
			//Assert.assertNotNull(infoDocumentoCreado.getFechaModificacion());
			Assert.assertTrue(infoDocumentoCreado.getMetadatos().size() > 0);

			for (MetadatoVO metadato : infoDocumentoCreado.getMetadatos()) {
				if ("document_id".equals(metadato.getNombre())) {
					Assert.assertEquals(new Integer(1), metadato.getValor());
				} else if ("document_name".equals(metadato.getNombre())) {
					Assert.assertEquals("Documento.txt", metadato.getValor());
				} else if ("document_type".equals(metadato.getNombre())) {
					Assert.assertEquals(new Integer(9), metadato.getValor());
				} else if ("stage_name".equals(metadato.getNombre())) {
					Assert.assertNull(metadato.getValor());
				}
			}

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		} finally {
			try {
				service.releaseSesion();
			} catch (GestionDocumentalException e) {
				Assert.fail("Error: " + e.toString());
			}
		}
	}

	@Test
	public void testIdocExisteDocumento() {

		GestionDocumentalService service = getGestionDocumentalService();

		try {

			service.createSesion();

			boolean existe = service.existeDocumento(getIdDocumento());
			Assert.assertTrue("No existe el documento", existe);

			existe = service.existeDocumento("<guid><archive>1</archive><folder>1</folder><document>0</document></guid>");
			Assert.assertFalse(existe);

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		} finally {
			try {
				service.releaseSesion();
			} catch (GestionDocumentalException e) {
				Assert.fail("Error: " + e.toString());
			}
		}
	}

	@Test
	public void testIdocGetInfoDocumento() {

		GestionDocumentalService service = getGestionDocumentalService();

		try {

			service.createSesion();

			InfoDocumentoVO infoDocumento = service.getInfoDocumento(getIdDocumento());
			Assert.assertNotNull("No se ha encontrado el documento", infoDocumento);
			Assert.assertEquals(getIdDocumento(), infoDocumento.getId());
			Assert.assertEquals("Documento.txt", infoDocumento.getNombre());
			Assert.assertEquals("text/plain", infoDocumento.getTipoMime());
			Assert.assertEquals(9, infoDocumento.getTamano());
//			Assert.assertEquals("Sun May 16 00:00:00 CEST 2010", String.valueOf(infoDocumento.getFechaCreacion()));
//			Assert.assertEquals("Sun May 16 00:00:00 CEST 2010", String.valueOf(infoDocumento.getFechaModificacion()));

			Assert.assertNotNull(infoDocumento.getMetadatos());
			Assert.assertTrue(infoDocumento.getMetadatos().size() > 0);

			Iterator<MetadatoVO> metadatos = infoDocumento.getMetadatos().iterator();
			while (metadatos.hasNext()) {
				MetadatoVO metadato = metadatos.next();
				Assert.assertNotNull(metadato.getNombre());
			}

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		} finally {
			try {
				service.releaseSesion();
			} catch (GestionDocumentalException e) {
				Assert.fail("Error: " + e.toString());
			}
		}
	}

	@Test
	public void testIdocRetrieveDocumento() {

		GestionDocumentalService service = getGestionDocumentalService();

		try {

			service.createSesion();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			service.retrieveDocumento(getIdDocumento(), baos);

			byte[] contenido = baos.toByteArray();
			Assert.assertTrue("No se ha encontrado el documento", contenido.length > 0);

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		} finally {
			try {
				service.releaseSesion();
			} catch (GestionDocumentalException e) {
				Assert.fail("Error: " + e.toString());
			}
		}
	}


	@Test
	public void testIdocUpdateDocumento() {

		GestionDocumentalService service = getGestionDocumentalService();

		try {

			service.createSesion();

			InfoDocumentoVO infoDocumento = service.getInfoDocumento(getIdDocumento());
			Assert.assertNotNull("No se ha encontrado el documento a modificar", infoDocumento);

			infoDocumento.setNombre("Documento1_modificado.txt");

			InfoDocumentoVO infoDocumentoModificado = service.updateDocumento(infoDocumento, null);
			Assert.assertNotNull("No se ha modificado el documento", infoDocumentoModificado);
			Assert.assertNotNull(infoDocumentoModificado.getId());
			Assert.assertEquals("Documento1_modificado.txt", infoDocumentoModificado.getNombre());
			Assert.assertEquals(infoDocumento.getTipoMime(), infoDocumentoModificado.getTipoMime());
			Assert.assertEquals(infoDocumento.getTamano(), infoDocumentoModificado.getTamano());
			Assert.assertTrue(infoDocumentoModificado.getMetadatos().size() > 0);

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		} finally {
			try {
				service.releaseSesion();
			} catch (GestionDocumentalException e) {
				Assert.fail("Error: " + e.toString());
			}
		}
	}

	@Test
	public void testIdocUpdateDocumentoConContenido() {

		GestionDocumentalService service = getGestionDocumentalService();

		try {

			service.createSesion();

			InfoDocumentoVO infoDocumento = service.getInfoDocumento(getIdDocumento());
			Assert.assertNotNull("No se ha encontrado el documento a modificar", infoDocumento);

			infoDocumento.setNombre("Documento1_modificado_2.txt");

			InfoDocumentoVO infoDocumentoModificado = service.updateDocumento(infoDocumento,
					new ByteArrayInputStream("contenido_modificado".getBytes()));
			Assert.assertNotNull("No se ha modificado el documento", infoDocumentoModificado);
			Assert.assertNotNull(infoDocumentoModificado.getId());
			Assert.assertEquals("Documento1_modificado_2.txt", infoDocumentoModificado.getNombre());
			Assert.assertEquals(infoDocumento.getTipoMime(), infoDocumentoModificado.getTipoMime());
			Assert.assertEquals(20, infoDocumentoModificado.getTamano());
			Assert.assertTrue(infoDocumentoModificado.getMetadatos().size() > 0);

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		} finally {
			try {
				service.releaseSesion();
			} catch (GestionDocumentalException e) {
				Assert.fail("Error: " + e.toString());
			}
		}
	}

	@Test
	public void testIdocDeleteDocumento() {

		GestionDocumentalService service = getGestionDocumentalService();

		try {

			service.createSesion();

			service.deleteDocumento(getIdDocumento());

			Assert.assertNull(service.getInfoDocumento(getIdDocumento()));

		} catch (GestionDocumentalException e) {
			Assert.fail("Error: " + e.toString());
		} finally {
			try {
				service.releaseSesion();
			} catch (GestionDocumentalException e) {
				Assert.fail("Error: " + e.toString());
			}
		}
	}

	protected MetadatoVO createMetadato(String nombre, Object valor) {
		MetadatoVO metadato = new MetadatoVO();
		metadato.setNombre(nombre);
		metadato.setValor(valor);
		return metadato;
	}
}
