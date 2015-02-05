package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.apache.commons.codec.binary.Base64;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.sir.api.dao.AnexoDao;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class AnexoDaoImplTest extends BaseDaoTest { 

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";

	protected static final String ID_ANEXO_EXISTENTE = "10000001";
	protected static final String ID_ANEXO_NO_EXISTENTE = "99999999";

	@Autowired
	private AnexoDao fwktd_sir_anexoDao;

	public AnexoDao getAnexoDao() {
		return fwktd_sir_anexoDao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseDao<Entity, String> getDao() {
		return (BaseDao) getAnexoDao();
	}

	public String getIdExistente() {
		return ID_ANEXO_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_ANEXO_NO_EXISTENTE;
	}


	@Test
	public void testGet() {

		AnexoVO anexo = getAnexoDao().get(getIdExistente());

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

//	@Test
//	public void testGetContenidoAnexo() {
//
//		byte[] contenido = getAnexoDao().getContenidoAnexo(getIdExistente());
//
//		Assert.assertNotNull(contenido);
//		Assert.assertEquals("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", Base64.encodeBase64String(contenido));
//	}
//
//	@Test
//	public void testSetContenidoAnexo() {
//
//		String contenido = "/9j/4AAQSkZJRgABAQEAlgCWAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAAVAB4DASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD6r+LF54O+Ejwxp4b0YQzobfTtEsPDqXlzMVZdzKiKP3aqwBLYAyOckKY/DesfD7XfCEPiKLw/oWo2bymLfb+GgssboSrq8XlM6sHRuCB29ifLP2kda1aL9sDw3a2q3t9Zx6ASttaCR/JkkuHDOQikgNtiX3IUelH7Oceq+Gte+J1r4isZ9FspdVt7y0i1FTbLI7qylo2kADE+Upz1+Tn28jGWhC97W1Pfw8VOEXZO7tt5HtsuheHLu2jubbwnoAjcsq+bo1vGxwcEbXjByPTuPWlXwhoqj5/DPhrI4w+kWoH/AH15WCahtPFFhFJa2qX2nyPfti2a3dWUnDk5wSMfu2w2cHGOvFW7DUrXWg4tNTspmgwJEt5VLRkk8OFJKnKtwcdD6GvJp4vFXTjC/l+R7M8PRS1SJvE0uk3vji9v5/D+nXOowL9kW8uYEklCo+9SHK7hhxuAzgE564I1I9b2RKslpDcdMNKMngY/E+/fNFFfQSSkve1PjozktmQTOlzdCeOOO1ZU2YijQ/jllJ/XFZtxrFzpF1J5JidX5KSwR4B9flVT+Z9aKKdOMddDpdapKNmz/9k=";
//		String hash = "opbAV9GVqKD9baoA0Kpj4hYXSss=";
//
//		getAnexoDao().setContenidoAnexo(getIdExistente(), Base64.decodeToBytes(contenido), Base64.decodeToBytes(hash));
//
//		byte[] contenidoCreado = getAnexoDao().getContenidoAnexo(getIdExistente());
//		Assert.assertEquals(contenido, Base64.encodeBase64String(contenidoCreado));
//
//		AnexoVO anexo = getAnexoDao().get(getIdExistente());
//		Assert.assertEquals(hash, Base64.encodeBase64String(anexo.getHash()));
//	}

	@Test
	public void testSave() {

		AnexoVO anexo = TestUtils.createDefaultAnexoVO();
		anexo.setId(getIdNoExistente());
		anexo.setIdAsientoRegistral("10000001");

		AnexoVO anexoCreado = (AnexoVO) getAnexoDao().save(anexo);

		Assert.assertNotNull("Anexo creado", anexoCreado);
		Assert.assertEquals(getIdNoExistente(), anexoCreado.getId());
		TestUtils.assertEquals(anexo, anexoCreado);
	}

	@Test
	public void testUpdate() {

		AnexoVO anexo = getAnexoDao().get(getIdExistente());
		anexo.setObservaciones("Observaciones modificadas");

		AnexoVO anexoModificado = getAnexoDao().update(anexo);

		Assert.assertNotNull("Anexo modificado", anexoModificado);
		Assert.assertEquals(anexo.getId(), anexoModificado.getId());
		Assert.assertEquals(anexo.getIdAsientoRegistral(), anexoModificado.getIdAsientoRegistral());
		TestUtils.assertEquals(anexo, anexoModificado);
	}

//	@Test
//	public void testDeleteByIdAsientoRegistral() {
//		int count = getDao().count();
//		getAnexoDao().deleteByIdAsientoRegistral(ID_ASIENTO_REGISTRAL);
//		Assert.assertTrue(count > getDao().count());
//	}

	@Test
	public void testGetIdsByIdAsientoRegistral() {
		List<String> ids = getAnexoDao().getIdsByIdAsientoRegistral(ID_ASIENTO_REGISTRAL);
		Assert.assertNotNull(ids);
		Assert.assertTrue(ids.size() > 0);
	}
}
