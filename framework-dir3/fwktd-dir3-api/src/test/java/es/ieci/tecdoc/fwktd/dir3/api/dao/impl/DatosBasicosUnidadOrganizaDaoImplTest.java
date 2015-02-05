/*package es.ieci.tecdoc.fwktd.dir3.api.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosUnidadOrganicaDao;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosUnidadOrganicaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterio;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;

@ContextConfiguration({ "/beans/cxf.xml", "/beans/fwktd-dir3-api-applicationContext.xml",
		"/beans/fwktd-dir3-test-beans.xml" })
public class DatosBasicosUnidadOrganizaDaoImplTest extends AbstractJUnit4SpringContextTests {

	protected static final String ID_UNIDAD_ORGANICA_EXISTENTE = "L01330447";
	protected static final String ID_UNIDAD_ORGANICA_NO_EXISTENTE = "XXXX";

	@Autowired
	private DatosBasicosUnidadOrganicaDaoImpl fwktd_dir3_datosBasicosUnidadOrganicaDaoImpl;

	protected DatosBasicosUnidadOrganicaDao getDatosBasicosUnidadOrganicaDao() {
		return fwktd_dir3_datosBasicosUnidadOrganicaDaoImpl;
	}

	@Test
	public void testDao() {
		Assert.assertNotNull(getDatosBasicosUnidadOrganicaDao());
	}

//	@Test
//	public void testCount() {
//		int count = getDatosBasicosUnidadOrganicaDao().count();
//		Assert.assertTrue(count > 0);
//	}
//
//	@Test
//	public void testGetAll() {
//
//		List<DatosBasicosUnidadOrganicaVO> unidades = getDatosBasicosUnidadOrganicaDao().getAll();
//
//		Assert.assertNotNull(unidades);
//		Assert.assertTrue(unidades.size() > 0);
//	}
//
//	@Test
//	public void testGetAllDistinct() {
//
//		List<DatosBasicosUnidadOrganicaVO> unidades = getDatosBasicosUnidadOrganicaDao().getAllDistinct();
//
//		Assert.assertNotNull(unidades);
//		Assert.assertTrue(unidades.size() > 0);
//	}

	@Test
	public void testExistsUnidadExistente() {
		Assert.assertTrue(getDatosBasicosUnidadOrganicaDao().exists(ID_UNIDAD_ORGANICA_EXISTENTE));
	}

	@Test
	public void testExistsUnidadNoExistente() {
		Assert.assertFalse(getDatosBasicosUnidadOrganicaDao().exists(ID_UNIDAD_ORGANICA_NO_EXISTENTE));
	}

	@Test
	public void testGetUnidadExistente() {

		DatosBasicosUnidadOrganicaVO unidad = getDatosBasicosUnidadOrganicaDao().get(ID_UNIDAD_ORGANICA_EXISTENTE);
		assertDatosBasicosUnidadOrganica(unidad);
	}

	@Test
	public void testGetUnidadNoExistente() {

		try {
			getDatosBasicosUnidadOrganicaDao().get(ID_UNIDAD_ORGANICA_NO_EXISTENTE);
			Assert.fail("Debería saltar una excepción");
		} catch (ObjectRetrievalFailureException e) {
		}
	}

	@Test
	public void testCountUnidadesOrganicas() {

//		// Test de consulta simple
//		int count = getDatosBasicosUnidadOrganicaDao().countUnidadesOrganicas(null);
//		Assert.assertTrue("No se han obtenido resultados", count > 0);
//
//		// Test de consulta simple
//		count = getDatosBasicosUnidadOrganicaDao().countUnidadesOrganicas(new Criterios<CriterioUnidadOrganicaEnum>());
//		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta con criterios
		int count = getDatosBasicosUnidadOrganicaDao().countUnidadesOrganicas(createCriterios());
		Assert.assertTrue("No se han obtenido resultados", count > 0);
	}

	@Test
	public void testFindUnidadesOrganicas() {

//		// Test de consulta simple
//		List<DatosBasicosUnidadOrganicaVO> unidades = getDatosBasicosUnidadOrganicaDao().findUnidadesOrganicas(null);
//		Assert.assertNotNull("Resultado nulo", unidades);
//		Assert.assertTrue("No se han obtenido resultados", unidades.size() > 0);
//
//		// Test de consulta simple
//		unidades = getDatosBasicosUnidadOrganicaDao().findUnidadesOrganicas(new Criterios<CriterioUnidadOrganicaEnum>());
//		Assert.assertNotNull("Resultado nulo", unidades);
//		Assert.assertTrue("No se han obtenido resultados", unidades.size() > 0);

		// Test de consulta con criterios
		List<DatosBasicosUnidadOrganicaVO> unidades = getDatosBasicosUnidadOrganicaDao().findUnidadesOrganicas(createCriterios());
		Assert.assertNotNull("Resultado nulo", unidades);
		Assert.assertTrue("No se han obtenido resultados", unidades.size() > 0);

		// Test de consulta con criterios
		unidades = getDatosBasicosUnidadOrganicaDao().findUnidadesOrganicas(new Criterios<CriterioUnidadOrganicaEnum>()
	            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
	            		CriterioUnidadOrganicaEnum.UO_ESTADO,
	                    OperadorCriterioEnum.IN,
	                    new String[] { "V", "X" }))
	            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
	            		CriterioUnidadOrganicaEnum.UO_ID_OFICINA_RELACIONADA,
	                    OperadorCriterioEnum.IN,
	                    new String[] { "O00001186" })) //O00001202
	            .addOrderBy(CriterioUnidadOrganicaEnum.UO_ID)
	            .addOrderBy(CriterioUnidadOrganicaEnum.UO_NOMBRE));
		Assert.assertNotNull("Resultado nulo", unidades);
		Assert.assertTrue("No se han obtenido resultados", unidades.size() > 0);
}

	protected void assertDatosBasicosUnidadOrganica(DatosBasicosUnidadOrganicaVO unidad) {

		Assert.assertNotNull(unidad);

		Assert.assertEquals(ID_UNIDAD_ORGANICA_EXISTENTE, unidad.getId());
		Assert.assertEquals("Ayuntamiento de Oviedo", unidad.getNombre());
		Assert.assertEquals("3", unidad.getNivelAdministracion());
		Assert.assertEquals("Administración Local", unidad.getDescripcionNivelAdministracion());
		Assert.assertEquals("N", unidad.getIndicadorEntidadDerechoPublico());
		Assert.assertEquals("01330447", unidad.getIdExternoFuente());

		Assert.assertEquals("L01330447", unidad.getIdUnidadOrganicaSuperior());
		Assert.assertEquals("Ayuntamiento de Oviedo", unidad.getNombreUnidadOrganicaSuperior());
		Assert.assertEquals("L01330447", unidad.getIdUnidadOrganicaPrincipal());
		Assert.assertEquals("Ayuntamiento de Oviedo", unidad.getNombreUnidadOrganicaPrincipal());
		Assert.assertNull(unidad.getIdUnidadOrganicaEntidadDerechoPublico());
		Assert.assertNull(unidad.getNombreUnidadOrganicaEntidadDerechoPublico());
		Assert.assertEquals(Integer.valueOf(1), unidad.getNivelJerarquico());

		Assert.assertEquals("V", unidad.getEstado());
		Assert.assertEquals("Vigente", unidad.getDescripcionEstado());
		Assert.assertNull(unidad.getFechaAltaOficial());
		Assert.assertNull(unidad.getFechaBajaOficial());
		Assert.assertNull(unidad.getFechaExtincion());
		Assert.assertNull(unidad.getFechaAnulacion());
	}

//	protected void assertUnidadOrganica(UnidadOrganicaVO unidad) {
	//
//			Assert.assertNotNull(unidad);
	//
//			Assert.assertEquals(ID_UNIDAD_ORGANICA_EXISTENTE, unidad.getId());
//			Assert.assertEquals("Ayuntamiento de Oviedo", unidad.getNombre());
//			Assert.assertNull(unidad.getSiglas());
	//
//			Assert.assertNotNull(unidad.getNivelAdministracion());
//			Assert.assertEquals("3", unidad.getNivelAdministracion().getId());
//			Assert.assertEquals("Administración Local", unidad.getNivelAdministracion().getNombre());
//			Assert.assertEquals("V", unidad.getNivelAdministracion().getEstado());
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getNivelAdministracion().getFechaAlta()));
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getNivelAdministracion().getFechaModificacion()));
//			Assert.assertNull(unidad.getNivelAdministracion().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getNivelAdministracion().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getTipoEntidadPublica());
//			Assert.assertEquals("AY", unidad.getTipoEntidadPublica().getId());
//			Assert.assertEquals("AYUNTAMIENTO", unidad.getTipoEntidadPublica().getNombre());
//			Assert.assertEquals("V", unidad.getTipoEntidadPublica().getEstado());
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getTipoEntidadPublica().getFechaAlta()));
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getTipoEntidadPublica().getFechaModificacion()));
//			Assert.assertEquals("Tue Mar 08 00:00:00 CET 2011", String.valueOf(unidad.getTipoEntidadPublica().getFechaInicioVigencia()));
//			Assert.assertNull(unidad.getTipoEntidadPublica().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getTipoUnidadOrganica());
//			Assert.assertEquals("UP", unidad.getTipoUnidadOrganica().getId());
//			Assert.assertEquals("UNIDAD PRINCIPAL", unidad.getTipoUnidadOrganica().getNombre());
//			Assert.assertEquals("V", unidad.getTipoUnidadOrganica().getEstado());
//			Assert.assertEquals("Wed Oct 06 00:00:00 CEST 2010", String.valueOf(unidad.getTipoUnidadOrganica().getFechaAlta()));
//			Assert.assertEquals("Wed Oct 06 00:00:00 CEST 2010", String.valueOf(unidad.getTipoUnidadOrganica().getFechaModificacion()));
//			Assert.assertNull(unidad.getTipoUnidadOrganica().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getTipoUnidadOrganica().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getPoder());
//			Assert.assertEquals("2", unidad.getPoder().getId());
//			Assert.assertEquals("Ejecutivo", unidad.getPoder().getNombre());
//			Assert.assertEquals("V", unidad.getPoder().getEstado());
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getPoder().getFechaAlta()));
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getPoder().getFechaModificacion()));
//			Assert.assertNull(unidad.getPoder().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getPoder().getFechaFinVigencia());
	//
//			Assert.assertEquals(Long.valueOf(1), unidad.getNivelJerarquico());
//			Assert.assertEquals("L01330447", unidad.getIdUnidadOrganicaSuperiorJerarquica());
//			Assert.assertEquals("L01330447", unidad.getIdUnidadOrganicaPrincipal());
//			Assert.assertEquals("N", unidad.getEntidadDerechoPublico());
//			Assert.assertNull(unidad.getIdUnidadPrincipalEntidadDerechoPublico());
	//
//			Assert.assertNull(unidad.getObservacionesGeneral());
	//
//			Assert.assertNotNull(unidad.getAmbitoTerritorialCompetencias());
//			Assert.assertEquals("13", unidad.getAmbitoTerritorialCompetencias().getId());
//			Assert.assertEquals("Local", unidad.getAmbitoTerritorialCompetencias().getNombre());
//			Assert.assertEquals("V", unidad.getAmbitoTerritorialCompetencias().getEstado());
//			Assert.assertEquals("3", unidad.getAmbitoTerritorialCompetencias().getIdNivelAdministracion());
//			Assert.assertEquals("01", unidad.getAmbitoTerritorialCompetencias().getIdAmbitoGeografico());
//			Assert.assertEquals("C_ID_LOCALIDAD_COMPET", unidad.getAmbitoTerritorialCompetencias().getTipoEntidadGeografica());
//			Assert.assertEquals(Long.valueOf(1), unidad.getAmbitoTerritorialCompetencias().getIdAmbitoDIR2());
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getAmbitoTerritorialCompetencias().getFechaAlta()));
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getAmbitoTerritorialCompetencias().getFechaModificacion()));
//			Assert.assertNull(unidad.getAmbitoTerritorialCompetencias().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getAmbitoTerritorialCompetencias().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getEntidadGeografica());
//			Assert.assertEquals("01", unidad.getEntidadGeografica().getId());
//			Assert.assertEquals("Municipio", unidad.getEntidadGeografica().getNombre());
//			Assert.assertEquals("V", unidad.getEntidadGeografica().getEstado());
//			Assert.assertEquals(Long.valueOf(5), unidad.getEntidadGeografica().getIdAmbitoDIR2());
//			Assert.assertEquals("Thu Oct 21 00:00:00 CEST 2010", String.valueOf(unidad.getEntidadGeografica().getFechaAlta()));
//			Assert.assertEquals("Thu Oct 21 00:00:00 CEST 2010", String.valueOf(unidad.getEntidadGeografica().getFechaModificacion()));
//			Assert.assertNull(unidad.getEntidadGeografica().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getEntidadGeografica().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getPais());
//			Assert.assertEquals("724", unidad.getPais().getId());
//			Assert.assertEquals("España", unidad.getPais().getNombre());
//			Assert.assertEquals("V", unidad.getPais().getEstado());
//			Assert.assertEquals("ES", unidad.getPais().getAlfa2());
//			Assert.assertEquals("ESP", unidad.getPais().getAlfa3());
//			Assert.assertEquals("Thu May 05 18:03:16 CEST 2011", String.valueOf(unidad.getPais().getFechaAlta()));
//			Assert.assertEquals("Thu May 05 18:03:16 CEST 2011", String.valueOf(unidad.getPais().getFechaModificacion()));
//			Assert.assertEquals("Thu May 05 18:03:16 CEST 2011", String.valueOf(unidad.getPais().getFechaInicioVigencia()));
//			Assert.assertNull(unidad.getPais().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getComunidadAutonoma());
//			Assert.assertEquals("03", unidad.getComunidadAutonoma().getId());
//			Assert.assertEquals("Principado de Asturias", unidad.getComunidadAutonoma().getNombre());
//			Assert.assertEquals("V", unidad.getComunidadAutonoma().getEstado());
//			Assert.assertEquals("AS", unidad.getComunidadAutonoma().getCodigoRCP());
//			Assert.assertEquals("3", unidad.getComunidadAutonoma().getCodigoDIR2());
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getComunidadAutonoma().getFechaAlta()));
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getComunidadAutonoma().getFechaModificacion()));
//			Assert.assertNull(unidad.getComunidadAutonoma().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getComunidadAutonoma().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getProvincia());
//			Assert.assertEquals("33", unidad.getProvincia().getId());
//			Assert.assertEquals("Asturias", unidad.getProvincia().getNombre());
//			Assert.assertEquals("V", unidad.getProvincia().getEstado());
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getProvincia().getFechaAlta()));
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getProvincia().getFechaModificacion()));
//			Assert.assertNull(unidad.getProvincia().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getProvincia().getFechaFinVigencia());
	//
//			Assert.assertNotNull(unidad.getMunicipio());
//			Assert.assertEquals("0447", unidad.getMunicipio().getId());
//			Assert.assertEquals("Oviedo", unidad.getMunicipio().getNombre());
//			Assert.assertEquals("V", unidad.getMunicipio().getEstado());
//			Assert.assertEquals("Fri Mar 25 12:43:25 CET 2011", String.valueOf(unidad.getMunicipio().getFechaAlta()));
//			Assert.assertEquals("Fri Mar 25 12:43:25 CET 2011", String.valueOf(unidad.getMunicipio().getFechaModificacion()));
//			Assert.assertEquals("Tue Mar 08 00:00:00 CET 2005", String.valueOf(unidad.getMunicipio().getFechaInicioVigencia()));
//			Assert.assertNull(unidad.getMunicipio().getFechaFinVigencia());
//			Assert.assertEquals("33", unidad.getMunicipio().getIdProvincia());
//			Assert.assertNull(unidad.getMunicipio().getIdIsla());
//			Assert.assertEquals("01", unidad.getMunicipio().getIdEntidadGeografica());
//			Assert.assertNull(unidad.getMunicipio().getCodigoRCP());
//			Assert.assertEquals("44", unidad.getMunicipio().getCodigoDIR2());
	//
//			Assert.assertNull(unidad.getIsla());
//			Assert.assertNull(unidad.getEntidadLocalMenor());
//			Assert.assertNull(unidad.getIdLocalidadExtranjera());
	//
//			Assert.assertNull(unidad.getCompetenciasUnidad());
//			Assert.assertNull(unidad.getDisposicionLegalCompetenciasUnidad());
//			Assert.assertEquals("S", unidad.getMismaDireccionUnidadSuperior());
//			Assert.assertNull(unidad.getIdDireccion());
	//
//			Assert.assertNotNull(unidad.getEstadoEntidad());
//			Assert.assertEquals("V", unidad.getEstadoEntidad().getId());
//			Assert.assertEquals("Vigente", unidad.getEstadoEntidad().getNombre());
//			Assert.assertEquals("V", unidad.getEstadoEntidad().getEstado());
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getEstadoEntidad().getFechaAlta()));
//			Assert.assertEquals("Mon Sep 27 00:00:00 CEST 2010", String.valueOf(unidad.getEstadoEntidad().getFechaModificacion()));
//			Assert.assertNull(unidad.getEstadoEntidad().getFechaInicioVigencia());
//			Assert.assertNull(unidad.getEstadoEntidad().getFechaFinVigencia());
	//
//			Assert.assertNull(unidad.getFechaAltaOficial());
//			Assert.assertNull(unidad.getFechaBajaOficial());
//			Assert.assertNull(unidad.getFechaExtincion());
//			Assert.assertNull(unidad.getFechaAnulacion());
//			Assert.assertNull(unidad.getObservacionesBaja());
//			Assert.assertEquals("4493", unidad.getIdFuente());
//			Assert.assertEquals("01330447", unidad.getIdExternoUnidadOrganica());
//			Assert.assertEquals("Fri Mar 25 14:14:58 CET 2011", String.valueOf(unidad.getFechaAltaSistema()));
//			Assert.assertEquals("Tue May 17 10:17:38 CEST 2011", String.valueOf(unidad.getFechaUltimaActualizacion()));
//		}

	protected Criterios<CriterioUnidadOrganicaEnum> createCriterios() {
        return new Criterios<CriterioUnidadOrganicaEnum>()

            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_ID,
                    OperadorCriterioEnum.EQUAL,
                    ID_UNIDAD_ORGANICA_EXISTENTE))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_NOMBRE,
                    OperadorCriterioEnum.LIKE,
                    "Oviedo"))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_NIVEL_ADMINISTRACION,
                    OperadorCriterioEnum.IN,
                    new String[] { "1", "2", "3" }))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_DESCRIPCION_NIVEL_ADMINISTRACION,
                    OperadorCriterioEnum.LIKE,
                    "Local"))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_INDICADOR_ENTIDAD_DERECHO_PUBLICO,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_ID_EXTERNO_FUENTE,
                    OperadorCriterioEnum.EQUAL,
                    "01330447"))

            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_ID_UNIDAD_ORGANICA_SUPERIOR,
                    OperadorCriterioEnum.EQUAL,
                    "L01330447"))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_NOMBRE_UNIDAD_ORGANICA_SUPERIOR,
                    OperadorCriterioEnum.LIKE,
                    "Oviedo"))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_ID_UNIDAD_ORGANICA_PRINCIPAL,
                    OperadorCriterioEnum.EQUAL,
                    "L01330447"))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_NOMBRE_UNIDAD_ORGANICA_PRINCIPAL,
                    OperadorCriterioEnum.LIKE,
                    "Oviedo"))
//            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
//                    CriterioUnidadOrganicaEnum.UO_ID_UNIDAD_ORGANICA_EDP,
//                    OperadorCriterioEnum.EQUAL,
//                    "L01330447"))
//            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
//                    CriterioUnidadOrganicaEnum.UO_NOMBRE_UNIDAD_ORGANICA_EDP,
//                    OperadorCriterioEnum.LIKE,
//                    "Oviedo"))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
                    CriterioUnidadOrganicaEnum.UO_NIVEL_JERARQUICO,
                    OperadorCriterioEnum.IN,
                    new int[] { 1, 2} ))

            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
            		CriterioUnidadOrganicaEnum.UO_ESTADO,
                    OperadorCriterioEnum.IN,
                    new String[] { "V", "X" }))
            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
            		CriterioUnidadOrganicaEnum.UO_DESCRIPCION_ESTADO,
                    OperadorCriterioEnum.LIKE,
                    "Vigente"))
//            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
//            		CriterioUnidadOrganicaEnum.UO_FECHA_ALTA,
//                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
//                    new Date()))
//            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
//            		CriterioUnidadOrganicaEnum.UO_FECHA_BAJA,
//                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
//                    new Date()))
//            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
//            		CriterioUnidadOrganicaEnum.UO_FECHA_EXTINCION,
//                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
//                    new Date()))
//            .addCriterio(new Criterio<CriterioUnidadOrganicaEnum>(
//            		CriterioUnidadOrganicaEnum.UO_FECHA_ANULACION,
//                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
//                    new Date()))
            .addOrderBy(CriterioUnidadOrganicaEnum.UO_ID)
            .addOrderBy(CriterioUnidadOrganicaEnum.UO_NOMBRE);
	}

}*/
