package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.AnexoManager;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class AnexoManagerImplTest extends BaseManagerTest {

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";

	protected static final String ID_ANEXO_EXISTENTE = "10000001";
	protected static final String ID_ANEXO_NO_EXISTENTE = "99999999";

	@Autowired
	private AnexoManager fwktd_sir_anexoManager;

	public AnexoManager getAnexoManager() {
		return fwktd_sir_anexoManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseManager<Entity, String> getManager() {
		return (BaseManager) getAnexoManager();
	}

	public String getIdExistente() {
		return ID_ANEXO_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_ANEXO_NO_EXISTENTE;
	}

	@Test
	public void testGet() {

		AnexoVO anexo = getAnexoManager().get(getIdExistente());

		Assert.assertNotNull(anexo);
		Assert.assertEquals(getIdExistente(), anexo.getId());
		Assert.assertEquals("10000001", anexo.getIdAsientoRegistral());
		Assert.assertEquals("documento.gif", anexo.getNombreFichero());
		Assert.assertEquals(ValidezDocumentoEnum.COPIA, anexo.getValidezDocumento());
		Assert.assertEquals(TipoDocumentoEnum.DOCUMENTO_ADJUNTO, anexo.getTipoDocumento());
		Assert.assertEquals("MIICnDCCAgWgAwIBAgIBADANBgkqhkiG9w0BAQQFADCBkzELMAkGA1UEBhMCRVMxEDAOBgNVBAgTB1NFVklMTEExEDAOBgNVBAcTB1NFVklMTEExDzANBgNVBAoTBklFQ0lTQTELMAkGA1UECxMCVEQxJTAjBgNVBAMUHElORk9STUFUSUNBIEVMIENPUlRFIElOR0xFUwkxGzAZBgkqhkiG9w0BCQEWDGllY2lAaWVjaS5lczAeFw0wODExMTAxMDA5NDhaFw0xNDExMDkxMDA5NDhaMIGTMQswCQYDVQQGEwJFUzEQMA4GA1UECBMHU0VWSUxMQTEQMA4GA1UEBxMHU0VWSUxMQTEPMA0GA1UEChMGSUVDSVNBMQswCQYDVQQLEwJURDElMCMGA1UEAxQcSU5GT1JNQVRJQ0EgRUwgQ09SVEUgSU5HTEVTCTEbMBkGCSqGSIb3DQEJARYMaWVjaUBpZWNpLmVzMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjeLe3lwKX2qsZpAt5IiwZq1ap+W1ZwxenMaAk492nrs1q5bYkn0q7eEb/ktRMjdAIGN982L04ctGuTgQKbBeA+rNtsXMTzjRtVUTugvEnJUVI2I+DzuQuFko1XlFOoyDkZH1nvrFYJdfe6+3cjpsECFQBHQ+/9NNfIpdsEGtNCQIDAQABMA0GCSqGSIb3DQEBBAUAA4GBAFD1A8j0ig4MRFlqAgF7I0NXfZbuONgdjT9mscfL7rE548WpTQ2Qf3f/Ysk/x4OVKfyN0PiBqktYMy6dKieBTcj7/CuP0veJuo9In12Em/nG2Yb5pgfBvC9o/UP28sTXD1zCPCCU09aPZeFhaI9Jw6QjoNFqhWcfxY8HkpjjvQKN", Base64.encodeBase64String(anexo.getCertificado()));
		Assert.assertEquals(Base64.encodeBase64String("***firma***".getBytes()), Base64.encodeBase64String(anexo.getFirma()));
		Assert.assertEquals(Base64.encodeBase64String("***timestamp***".getBytes()), Base64.encodeBase64String(anexo.getTimestamp()));
		Assert.assertEquals(Base64.encodeBase64String("***ocsp***".getBytes()), Base64.encodeBase64String(anexo.getValidacionOCSPCertificado()));
		Assert.assertEquals("r0gYoAcaJaVUTnslJx7RWc0Y1Ys=", Base64.encodeBase64String(anexo.getHash()));
		Assert.assertEquals("image/gif", anexo.getTipoMIME());
		Assert.assertNull(anexo.getIdentificadorFicheroFirmado());
		Assert.assertEquals("Observaciones al anexo", anexo.getObservaciones());
	}

	@Test
	public void testSave() {

		AnexoVO anexo = TestUtils.createDefaultAnexoVO();
		anexo.setIdAsientoRegistral("10000001");

		AnexoVO anexoCreado = (AnexoVO) getAnexoManager().save(anexo);

		Assert.assertNotNull("Anexo creado", anexoCreado);
		Assert.assertNotNull("Identificador del anexo creado", anexoCreado.getId());
		Assert.assertEquals(anexo.getIdAsientoRegistral(), anexoCreado.getIdAsientoRegistral());
		TestUtils.assertEquals(anexo, anexoCreado);
	}

	@Test
	public void testSaveAnexo() {

		AnexoFormVO anexoForm = TestUtils.createDefaultAnexoFormVO();
		AnexoVO anexo = getAnexoManager().saveAnexo("10000001", anexoForm);

		Assert.assertNotNull(anexo);
		TestUtils.assertEquals(anexoForm, anexo);
		Assert.assertEquals(Base64.encodeBase64String(anexoForm.getContenido()), Base64.encodeBase64String(getAnexoManager().getContenidoAnexo(anexo.getId())));

		getAnexoManager().delete(anexo.getId());
	}

	@Test
	public void testGetContenidoAnexo() {

		AnexoFormVO anexoForm = TestUtils.createDefaultAnexoFormVO();
		AnexoVO anexo = getAnexoManager().saveAnexo("10000001", anexoForm);

		Assert.assertNotNull(anexo);

		byte[] contenido = getAnexoManager().getContenidoAnexo(anexo.getId());

		Assert.assertNotNull(contenido);
		Assert.assertEquals(Base64.encodeBase64String(anexoForm.getContenido()), Base64.encodeBase64String(contenido));

		getAnexoManager().delete(anexo.getId());
	}

	@Test
	public void testSetContenidoAnexo() {

		AnexoFormVO anexoForm = TestUtils.createDefaultAnexoFormVO();
		AnexoVO anexo = getAnexoManager().saveAnexo("10000001", anexoForm);

		Assert.assertNotNull(anexo);

		byte[] contenido = "contenido2".getBytes();
		getAnexoManager().setContenidoAnexo(anexo.getId(), contenido);

		byte[] contenidoCreado = getAnexoManager().getContenidoAnexo(anexo.getId());
		Assert.assertEquals(Base64.encodeBase64String(contenido), Base64.encodeBase64String(contenidoCreado));

		getAnexoManager().delete(anexo.getId());
	}

	@Test
	public void testUpdate() {

		AnexoVO anexo = getAnexoManager().get(getIdExistente());
		anexo.setObservaciones("Observaciones modificadas");

		AnexoVO anexoModificado = getAnexoManager().update(anexo);

		Assert.assertNotNull("Anexo modificado", anexoModificado);
		Assert.assertEquals(anexo.getId(), anexoModificado.getId());
		Assert.assertEquals(anexo.getIdAsientoRegistral(), anexoModificado.getIdAsientoRegistral());
		TestUtils.assertEquals(anexo, anexoModificado);
	}

	@Test
	public void testDeleteByIdAsientoRegistral() {
		int count = getManager().count();
		getAnexoManager().deleteByIdAsientoRegistral(ID_ASIENTO_REGISTRAL);
		Assert.assertTrue(count > getManager().count());

	}
}
