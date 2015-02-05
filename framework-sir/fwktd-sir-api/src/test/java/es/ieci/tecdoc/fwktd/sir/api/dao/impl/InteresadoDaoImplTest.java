package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.sir.api.dao.InteresadoDao;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
	"/beans/fwktd-sir-test-beans-custom.xml" })
public class InteresadoDaoImplTest extends BaseDaoTest {

	protected static final String ID_ASIENTO_REGISTRAL = "10000001";

	protected static final String ID_INTERESADO_EXISTENTE = "10000001";
	protected static final String ID_INTERESADO_NO_EXISTENTE = "99999999";

	@Autowired
	private InteresadoDao fwktd_sir_interesadoDao;

	public InteresadoDao getInteresadoDao() {
		return fwktd_sir_interesadoDao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseDao<Entity, String> getDao() {
		return (BaseDao) getInteresadoDao();
	}

	public String getIdExistente() {
		return ID_INTERESADO_EXISTENTE;
	}

	public String getIdNoExistente() {
		return ID_INTERESADO_NO_EXISTENTE;
	}

	@Test
	public void testGet() {

		InteresadoVO interesado = getInteresadoDao().get(getIdExistente());

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
	public void testSave() {

		InteresadoVO interesado = new InteresadoVO();
		interesado.setId(getIdNoExistente());
		interesado.setIdAsientoRegistral("10000001");
		interesado.setTipoDocumentoIdentificacionInteresado(TipoDocumentoIdentificacionEnum.CIF);
		interesado.setDocumentoIdentificacionInteresado("A28855260");
		interesado.setRazonSocialInteresado("razonSocialInteresado");
		interesado.setNombreInteresado("");
		interesado.setPrimerApellidoInteresado("");
		interesado.setSegundoApellidoInteresado("");
		interesado.setCodigoPaisInteresado("0001");
		interesado.setCodigoProvinciaInteresado("05");
		interesado.setCodigoMunicipioInteresado("01544");
		interesado.setDireccionInteresado("direccionInteresado");
		interesado.setCodigoPostalInteresado("33004");
		interesado.setCorreoElectronicoInteresado("correoElectronico@interesado.es");
		interesado.setTelefonoInteresado("999999999");
		interesado.setDireccionElectronicaHabilitadaInteresado("deu");
		interesado.setCanalPreferenteComunicacionInteresado(CanalNotificacionEnum.DIRECCION_POSTAL);
		interesado.setTipoDocumentoIdentificacionRepresentante(TipoDocumentoIdentificacionEnum.NIF);
		interesado.setDocumentoIdentificacionRepresentante("00000000T");
		interesado.setRazonSocialRepresentante("");
		interesado.setNombreRepresentante("nombreRepresentante");
		interesado.setPrimerApellidoRepresentante("primerApellidoRepresentante");
		interesado.setSegundoApellidoRepresentante("segundoApellidoRepresentante");
		interesado.setCodigoPaisRepresentante("0001");
		interesado.setCodigoProvinciaRepresentante("05");
		interesado.setCodigoMunicipioRepresentante("01544");
		interesado.setDireccionRepresentante("direccionRepresentante");
		interesado.setCodigoPostalRepresentante("33004");
		interesado.setCorreoElectronicoRepresentante("correoElectronico@representante.es");
		interesado.setTelefonoRepresentante("666666666");
		interesado.setDireccionElectronicaHabilitadaRepresentante("deu_repr");
		interesado.setCanalPreferenteComunicacionRepresentante(CanalNotificacionEnum.DIRECCION_ELECTRONICA_HABILITADA);
		interesado.setObservaciones("observaciones");

		InteresadoVO interesadoCreado = (InteresadoVO) getInteresadoDao().save(interesado);

		Assert.assertNotNull("Interesado creado", interesadoCreado);
		Assert.assertEquals(getIdNoExistente(), interesadoCreado.getId());
		Assert.assertEquals(interesado.getIdAsientoRegistral(), interesadoCreado.getIdAsientoRegistral());
		Assert.assertEquals(interesado.getTipoDocumentoIdentificacionInteresado(), interesadoCreado.getTipoDocumentoIdentificacionInteresado());
		Assert.assertEquals(interesado.getDocumentoIdentificacionInteresado(), interesadoCreado.getDocumentoIdentificacionInteresado());
		Assert.assertEquals(interesado.getRazonSocialInteresado(), interesadoCreado.getRazonSocialInteresado());
		Assert.assertEquals(interesado.getNombreInteresado(), interesadoCreado.getNombreInteresado());
		Assert.assertEquals(interesado.getPrimerApellidoInteresado(), interesadoCreado.getPrimerApellidoInteresado());
		Assert.assertEquals(interesado.getSegundoApellidoInteresado(), interesadoCreado.getSegundoApellidoInteresado());
		Assert.assertEquals(interesado.getCodigoPaisInteresado(), interesadoCreado.getCodigoPaisInteresado());
		Assert.assertEquals(interesado.getCodigoProvinciaInteresado(), interesadoCreado.getCodigoProvinciaInteresado());
		Assert.assertEquals(interesado.getCodigoMunicipioInteresado(), interesadoCreado.getCodigoMunicipioInteresado());
		Assert.assertEquals(interesado.getDireccionInteresado(), interesadoCreado.getDireccionInteresado());
		Assert.assertEquals(interesado.getCodigoPostalInteresado(), interesadoCreado.getCodigoPostalInteresado());
		Assert.assertEquals(interesado.getCorreoElectronicoInteresado(), interesadoCreado.getCorreoElectronicoInteresado());
		Assert.assertEquals(interesado.getTelefonoInteresado(), interesadoCreado.getTelefonoInteresado());
		Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaInteresado(), interesadoCreado.getDireccionElectronicaHabilitadaInteresado());
		Assert.assertEquals(interesado.getCanalPreferenteComunicacionInteresado(), interesadoCreado.getCanalPreferenteComunicacionInteresado());
		Assert.assertEquals(interesado.getTipoDocumentoIdentificacionRepresentante(), interesadoCreado.getTipoDocumentoIdentificacionRepresentante());
		Assert.assertEquals(interesado.getDocumentoIdentificacionRepresentante(), interesadoCreado.getDocumentoIdentificacionRepresentante());
		Assert.assertEquals(interesado.getRazonSocialRepresentante(), interesadoCreado.getRazonSocialRepresentante());
		Assert.assertEquals(interesado.getNombreRepresentante(), interesadoCreado.getNombreRepresentante());
		Assert.assertEquals(interesado.getPrimerApellidoRepresentante(), interesadoCreado.getPrimerApellidoRepresentante());
		Assert.assertEquals(interesado.getSegundoApellidoRepresentante(), interesadoCreado.getSegundoApellidoRepresentante());
		Assert.assertEquals(interesado.getCodigoPaisRepresentante(), interesadoCreado.getCodigoPaisRepresentante());
		Assert.assertEquals(interesado.getCodigoProvinciaRepresentante(), interesadoCreado.getCodigoProvinciaRepresentante());
		Assert.assertEquals(interesado.getCodigoMunicipioRepresentante(), interesadoCreado.getCodigoMunicipioRepresentante());
		Assert.assertEquals(interesado.getDireccionRepresentante(), interesadoCreado.getDireccionRepresentante());
		Assert.assertEquals(interesado.getCodigoPostalRepresentante(), interesadoCreado.getCodigoPostalRepresentante());
		Assert.assertEquals(interesado.getCorreoElectronicoRepresentante(), interesadoCreado.getCorreoElectronicoRepresentante());
		Assert.assertEquals(interesado.getTelefonoRepresentante(), interesadoCreado.getTelefonoRepresentante());
		Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaRepresentante(), interesadoCreado.getDireccionElectronicaHabilitadaRepresentante());
		Assert.assertEquals(interesado.getCanalPreferenteComunicacionRepresentante(), interesadoCreado.getCanalPreferenteComunicacionRepresentante());
		Assert.assertEquals(interesado.getObservaciones(), interesadoCreado.getObservaciones());
	}

	@Test
	public void testUpdate() {

		InteresadoVO interesado = getInteresadoDao().get(getIdExistente());
		interesado.setObservaciones("Observaciones modificadas");

		InteresadoVO interesadoModificado = getInteresadoDao().update(interesado);

		Assert.assertNotNull("Interesado modificado", interesadoModificado);
		Assert.assertEquals(interesado.getId(), interesadoModificado.getId());
		Assert.assertEquals(interesado.getIdAsientoRegistral(), interesadoModificado.getIdAsientoRegistral());
		Assert.assertEquals(interesado.getTipoDocumentoIdentificacionInteresado(), interesadoModificado.getTipoDocumentoIdentificacionInteresado());
		Assert.assertEquals(interesado.getDocumentoIdentificacionInteresado(), interesadoModificado.getDocumentoIdentificacionInteresado());
		Assert.assertEquals(interesado.getRazonSocialInteresado(), interesadoModificado.getRazonSocialInteresado());
		Assert.assertEquals(interesado.getNombreInteresado(), interesadoModificado.getNombreInteresado());
		Assert.assertEquals(interesado.getPrimerApellidoInteresado(), interesadoModificado.getPrimerApellidoInteresado());
		Assert.assertEquals(interesado.getSegundoApellidoInteresado(), interesadoModificado.getSegundoApellidoInteresado());
		Assert.assertEquals(interesado.getCodigoPaisInteresado(), interesadoModificado.getCodigoPaisInteresado());
		Assert.assertEquals(interesado.getCodigoProvinciaInteresado(), interesadoModificado.getCodigoProvinciaInteresado());
		Assert.assertEquals(interesado.getCodigoMunicipioInteresado(), interesadoModificado.getCodigoMunicipioInteresado());
		Assert.assertEquals(interesado.getDireccionInteresado(), interesadoModificado.getDireccionInteresado());
		Assert.assertEquals(interesado.getCodigoPostalInteresado(), interesadoModificado.getCodigoPostalInteresado());
		Assert.assertEquals(interesado.getCorreoElectronicoInteresado(), interesadoModificado.getCorreoElectronicoInteresado());
		Assert.assertEquals(interesado.getTelefonoInteresado(), interesadoModificado.getTelefonoInteresado());
		Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaInteresado(), interesadoModificado.getDireccionElectronicaHabilitadaInteresado());
		Assert.assertEquals(interesado.getCanalPreferenteComunicacionInteresado(), interesadoModificado.getCanalPreferenteComunicacionInteresado());
		Assert.assertEquals(interesado.getTipoDocumentoIdentificacionRepresentante(), interesadoModificado.getTipoDocumentoIdentificacionRepresentante());
		Assert.assertEquals(interesado.getDocumentoIdentificacionRepresentante(), interesadoModificado.getDocumentoIdentificacionRepresentante());
		Assert.assertEquals(interesado.getRazonSocialRepresentante(), interesadoModificado.getRazonSocialRepresentante());
		Assert.assertEquals(interesado.getNombreRepresentante(), interesadoModificado.getNombreRepresentante());
		Assert.assertEquals(interesado.getPrimerApellidoRepresentante(), interesadoModificado.getPrimerApellidoRepresentante());
		Assert.assertEquals(interesado.getSegundoApellidoRepresentante(), interesadoModificado.getSegundoApellidoRepresentante());
		Assert.assertEquals(interesado.getCodigoPaisRepresentante(), interesadoModificado.getCodigoPaisRepresentante());
		Assert.assertEquals(interesado.getCodigoProvinciaRepresentante(), interesadoModificado.getCodigoProvinciaRepresentante());
		Assert.assertEquals(interesado.getCodigoMunicipioRepresentante(), interesadoModificado.getCodigoMunicipioRepresentante());
		Assert.assertEquals(interesado.getDireccionRepresentante(), interesadoModificado.getDireccionRepresentante());
		Assert.assertEquals(interesado.getCodigoPostalRepresentante(), interesadoModificado.getCodigoPostalRepresentante());
		Assert.assertEquals(interesado.getCorreoElectronicoRepresentante(), interesadoModificado.getCorreoElectronicoRepresentante());
		Assert.assertEquals(interesado.getTelefonoRepresentante(), interesadoModificado.getTelefonoRepresentante());
		Assert.assertEquals(interesado.getDireccionElectronicaHabilitadaRepresentante(), interesadoModificado.getDireccionElectronicaHabilitadaRepresentante());
		Assert.assertEquals(interesado.getCanalPreferenteComunicacionRepresentante(), interesadoModificado.getCanalPreferenteComunicacionRepresentante());
		Assert.assertEquals(interesado.getObservaciones(), interesadoModificado.getObservaciones());
	}

	@Test
	public void testDeleteByIdAsientoRegistral() {
		int count = getDao().count();
		getInteresadoDao().deleteByIdAsientoRegistral(ID_ASIENTO_REGISTRAL);
		Assert.assertTrue(count > getDao().count());
	}
}
