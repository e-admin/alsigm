package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.sir.api.manager.InteresadoManager;
import es.ieci.tecdoc.fwktd.sir.api.utils.TestUtils;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class InteresadoManagerImplTest extends BaseManagerTest {

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";

	protected static final String ID_INTERESADO_EXISTENTE = "10000001";
	protected static final String ID_INTERESADO_NO_EXISTENTE = "99999999";

	@Autowired
	private InteresadoManager fwktd_sir_interesadoManager;

	public InteresadoManager getInteresadoManager() {
		return fwktd_sir_interesadoManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseManager<Entity, String> getManager() {
		return (BaseManager) getInteresadoManager();
	}

	public String getIdExistente() {
		return ID_INTERESADO_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_INTERESADO_NO_EXISTENTE;
	}

	@Test
	public void testGet() {

		InteresadoVO interesado = getInteresadoManager().get(getIdExistente());

		Assert.assertNotNull(interesado);
		Assert.assertEquals(getIdExistente(), interesado.getId());
		Assert.assertEquals("10000001", interesado.getIdAsientoRegistral());
		Assert.assertEquals(TipoDocumentoIdentificacionEnum.CIF, interesado.getTipoDocumentoIdentificacionInteresado());
		Assert.assertEquals("A28855260", interesado.getDocumentoIdentificacionInteresado());
		Assert.assertEquals("Informática El Corte Inglés, S. A.", interesado.getRazonSocialInteresado());
		Assert.assertEquals("", interesado.getNombreInteresado());
		Assert.assertEquals("", interesado.getPrimerApellidoInteresado());
		Assert.assertEquals("", interesado.getSegundoApellidoInteresado());
		Assert.assertEquals("0001", interesado.getCodigoPaisInteresado());
		Assert.assertEquals("05", interesado.getCodigoProvinciaInteresado());
		Assert.assertEquals("01544", interesado.getCodigoMunicipioInteresado());
		Assert.assertEquals("c\\Caveda, 4", interesado.getDireccionInteresado());
		Assert.assertEquals("33004", interesado.getCodigoPostalInteresado());
		Assert.assertEquals("", interesado.getCorreoElectronicoInteresado());
		Assert.assertEquals("985227000", interesado.getTelefonoInteresado());
		Assert.assertEquals("", interesado.getDireccionElectronicaHabilitadaInteresado());
		Assert.assertNull(interesado.getCanalPreferenteComunicacionInteresado());
		Assert.assertEquals(TipoDocumentoIdentificacionEnum.NIF, interesado.getTipoDocumentoIdentificacionRepresentante());
		Assert.assertEquals("00000000T", interesado.getDocumentoIdentificacionRepresentante());
		Assert.assertEquals("", interesado.getRazonSocialRepresentante());
		Assert.assertEquals("Homer", interesado.getNombreRepresentante());
		Assert.assertEquals("Simpson", interesado.getPrimerApellidoRepresentante());
		Assert.assertEquals("", interesado.getSegundoApellidoRepresentante());
		Assert.assertEquals("0001", interesado.getCodigoPaisRepresentante());
		Assert.assertEquals("05", interesado.getCodigoProvinciaRepresentante());
		Assert.assertEquals("01544", interesado.getCodigoMunicipioRepresentante());
		Assert.assertEquals("c\\Caveda, 4", interesado.getDireccionRepresentante());
		Assert.assertEquals("33004", interesado.getCodigoPostalRepresentante());
		Assert.assertEquals("homer_simpson@ieci.es", interesado.getCorreoElectronicoRepresentante());
		Assert.assertEquals("985227000", interesado.getTelefonoRepresentante());
		Assert.assertEquals("homer_simpson", interesado.getDireccionElectronicaHabilitadaRepresentante());
		Assert.assertEquals(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA, interesado.getCanalPreferenteComunicacionRepresentante());
		Assert.assertEquals("Observaciones sobre Homer", interesado.getObservaciones());
	}

	@Test
	public void testSaveInteresado() {

		InteresadoFormVO interesadoForm = TestUtils.createDefaultInteresadoFormVO();
		InteresadoVO interesado = getInteresadoManager().saveInteresado("10000001", interesadoForm);

		Assert.assertNotNull(interesado);
		TestUtils.assertEquals(interesadoForm, interesado);
	}

	@Test
	public void testSave() {

		InteresadoVO interesado = TestUtils.createDefaultInteresadoVO();
		interesado.setIdAsientoRegistral("10000001");

		InteresadoVO interesadoCreado = (InteresadoVO) getInteresadoManager().save(interesado);

		Assert.assertNotNull("Interesado creado", interesadoCreado);
		Assert.assertNotNull("Identificador del interesado creado", interesadoCreado.getId());
		Assert.assertEquals(interesado.getIdAsientoRegistral(), interesadoCreado.getIdAsientoRegistral());
		TestUtils.assertEquals(interesado, interesadoCreado);
	}

	@Test
	public void testUpdate() {

		InteresadoVO interesado = getInteresadoManager().get(getIdExistente());
		interesado.setObservaciones("Observaciones modificadas");

		InteresadoVO interesadoModificado = getInteresadoManager().update(interesado);

		Assert.assertNotNull("Interesado modificado", interesadoModificado);
		Assert.assertEquals(interesado.getId(), interesadoModificado.getId());
		Assert.assertEquals(interesado.getIdAsientoRegistral(), interesadoModificado.getIdAsientoRegistral());
		TestUtils.assertEquals(interesado, interesadoModificado);
	}

	@Test
	public void testDeleteByIdAsientoRegistral() {
		int count = getManager().count();
		getInteresadoManager().deleteByIdAsientoRegistral(ID_ASIENTO_REGISTRAL);
		Assert.assertTrue(count > getManager().count());
	}
}
