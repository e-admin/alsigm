/*package es.ieci.tecdoc.fwktd.dir3.api.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dir3.api.dao.DatosBasicosOficinaDao;
import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterio;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;

@ContextConfiguration({"/beans/cxf.xml", "/beans/fwktd-dir3-api-applicationContext.xml",
		"/beans/fwktd-dir3-test-beans.xml" })
public class DatosBasicosOficinaDaoImplTest extends AbstractJUnit4SpringContextTests {

	protected static final String ID_OFICINA_EXISTENTE = "O00001177";
	protected static final String ID_OFICINA_NO_EXISTENTE = "XXXX";

	protected static final String ID_UNIDAD_ORGANICA_EXISTENTE = "L01330447";
	protected static final String ID_UNIDAD_ORGANICA_NO_EXISTENTE = "XXXX";

	@Autowired
	private DatosBasicosOficinaDaoImpl fwktd_dir3_datosBasicosOficinaDaoImpl;

	protected DatosBasicosOficinaDao getDatosBasicosOficinaDao() {
		return fwktd_dir3_datosBasicosOficinaDaoImpl;
	}

	@Test
	public void testDao() {
		Assert.assertNotNull(getDatosBasicosOficinaDao());
	}

//	@Test
//	public void testCount() {
//		int count = getDatosBasicosOficinaDao().count();
//		Assert.assertTrue(count > 0);
//	}
//
//	@Test
//	public void testGetAll() {
//
//		List<DatosBasicosOficinaVO> oficinas = getDatosBasicosOficinaDao().getAll();
//
//		Assert.assertNotNull(oficinas);
//		Assert.assertTrue(oficinas.size() > 0);
//	}
//
//	@Test
//	public void testGetAllDistinct() {
//
//		List<DatosBasicosOficinaVO> oficinas = getDatosBasicosOficinaDao().getAllDistinct();
//
//		Assert.assertNotNull(oficinas);
//		Assert.assertTrue(oficinas.size() > 0);
//	}

	@Test
	public void testExistsOficinaExistente() {
		Assert.assertTrue(getDatosBasicosOficinaDao().exists(ID_OFICINA_EXISTENTE));
	}

	@Test
	public void testExistsOficinaNoExistente() {
		Assert.assertFalse(getDatosBasicosOficinaDao().exists(ID_OFICINA_NO_EXISTENTE));
	}

	@Test
	public void testGetOficinaExistente() {

		DatosBasicosOficinaVO oficina = getDatosBasicosOficinaDao().get(ID_OFICINA_EXISTENTE);
		assertDatosBasicosOficina(oficina);
	}

	@Test
	public void testGetOficinaNoExistente() {

		try {
			getDatosBasicosOficinaDao().get(ID_OFICINA_NO_EXISTENTE);
			Assert.fail("Debería saltar una excepción");
		} catch (ObjectRetrievalFailureException e) {
		}
	}

	@Test
	public void testCountOficinas() {

//		// Test de consulta simple
//		int count = getDatosBasicosOficinaDao().countOficinas(null);
//		Assert.assertTrue("No se han obtenido resultados", count > 0);
//
//		// Test de consulta simple
//		count = getDatosBasicosOficinaDao().countOficinas(new Criterios<CriterioOficinaEnum>());
//		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta con criterios
		int count = getDatosBasicosOficinaDao().countOficinas(createCriterios());
		Assert.assertTrue("No se han obtenido resultados", count > 0);
	}

	@Test
	public void testFindOficinas() {

//		// Test de consulta simple
//		List<DatosBasicosOficinaVO> oficinas = getDatosBasicosOficinaDao().findOficinas(null);
//		Assert.assertNotNull("Resultado nulo", oficinas);
//		Assert.assertTrue("No se han obtenido resultados", oficinas.size() > 0);
//
//		// Test de consulta simple
//		oficinas = getDatosBasicosOficinaDao().findOficinas(new Criterios<CriterioOficinaEnum>());
//		Assert.assertNotNull("Resultado nulo", oficinas);
//		Assert.assertTrue("No se han obtenido resultados", oficinas.size() > 0);

		// Test de consulta con criterios
		List<DatosBasicosOficinaVO> oficinas = getDatosBasicosOficinaDao().findOficinas(createCriterios());
		Assert.assertNotNull("Resultado nulo", oficinas);
		Assert.assertTrue("No se han obtenido resultados", oficinas.size() > 0);

		// Test de consulta con criterios
		oficinas = getDatosBasicosOficinaDao().findOficinas(new Criterios<CriterioOficinaEnum>()
                .addCriterio(new Criterio<CriterioOficinaEnum>(
                        CriterioOficinaEnum.OFICINA_INDICADOR_ADHESION_SIR,
                        OperadorCriterioEnum.EQUAL,
                        "S"))
                .addCriterio(new Criterio<CriterioOficinaEnum>(
                		CriterioOficinaEnum.OFICINA_ESTADO,
                        OperadorCriterioEnum.IN,
                        new String[] { "V", "X" }))
                .addCriterio(new Criterio<CriterioOficinaEnum>(
                		CriterioOficinaEnum.OFICINA_ID_UNIDAD_RELACIONADA,
                        OperadorCriterioEnum.IN,
                        new String[] { ID_UNIDAD_ORGANICA_EXISTENTE, "E00004101" }))
                .addOrderBy(CriterioOficinaEnum.OFICINA_ID)
                .addOrderBy(CriterioOficinaEnum.OFICINA_NOMBRE));

		Assert.assertNotNull("Resultado nulo", oficinas);
		Assert.assertTrue("No se han obtenido resultados", oficinas.size() > 0);
	}

	protected void assertDatosBasicosOficina(DatosBasicosOficinaVO oficina) {

		Assert.assertNotNull(oficina);

		Assert.assertEquals(ID_OFICINA_EXISTENTE, oficina.getId());
		Assert.assertEquals("BIBLIOTECA NACIONAL", oficina.getNombre());
		Assert.assertEquals("000004", oficina.getIdExternoFuente());

		Assert.assertEquals("E04584101", oficina.getIdUnidadResponsable());
		Assert.assertEquals("MINISTERIO DE CULTURA", oficina.getNombreUnidadResponsable());
		Assert.assertNull(oficina.getNivelAdministracion());

		Assert.assertEquals("S", oficina.getIndicadorAdhesionSIR());
		Assert.assertEquals("S", oficina.getIndicadorOficinaRegistro());
		Assert.assertEquals("N", oficina.getIndicadorOficinaInformacion());
		Assert.assertEquals("N", oficina.getIndicadorOficinaTramitacion());
		Assert.assertEquals("N", oficina.getIndicadorRegistroElectronico());
		Assert.assertEquals("N", oficina.getIndicadorIntercambioSinRestriccion());
		Assert.assertEquals("N", oficina.getIndicadorIntercambioLocalEstatal());
		Assert.assertEquals("N", oficina.getIndicadorIntercambioLocalAutonomicoRestringido());
		Assert.assertEquals("N", oficina.getIndicadorIntercambioLocalAutonomicoGeneral());
		Assert.assertEquals("N", oficina.getIndicadorIntercambioLocalLocalRestringido());
		Assert.assertEquals("N", oficina.getIndicadorIntercambioLocalLocalGeneral());
		Assert.assertEquals("N", oficina.getIndicadorIntercambioAytoAytoRestringido());

		Assert.assertEquals("V", oficina.getEstado());
		Assert.assertEquals("Vigente", oficina.getDescripcionEstado());
		Assert.assertNull(oficina.getFechaCreacion());
		Assert.assertNull(oficina.getFechaExtincion());
		Assert.assertNull(oficina.getFechaAnulacion());
	}

	protected Criterios<CriterioOficinaEnum> createCriterios() {
        return new Criterios<CriterioOficinaEnum>()

            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_ID,
                    OperadorCriterioEnum.EQUAL,
                    ID_OFICINA_EXISTENTE))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_NOMBRE,
                    OperadorCriterioEnum.LIKE,
                    "NACIONAL"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_ID_EXTERNO_FUENTE,
                    OperadorCriterioEnum.EQUAL,
                    "000004"))

            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_ID_UNIDAD_RESPONSABLE,
                    OperadorCriterioEnum.EQUAL,
                    "E04584101"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_NOMBRE_UNIDAD_RESPONSABLE,
                    OperadorCriterioEnum.LIKE,
                    "CULTURA"))
//            .addCriterio(new Criterio<CriterioOficinaEnum>(
//                    CriterioOficinaEnum.OFICINA_NIVEL_ADMINISTRACION,
//                    OperadorCriterioEnum.IN,
//                    new String[] { "", ""}))
//            .addCriterio(new Criterio<CriterioOficinaEnum>(
//                    CriterioOficinaEnum.OFICINA_DESCRIPCION_NIVEL_ADMINISTRACION,
//                    OperadorCriterioEnum.LIKE,
//                    ""))

            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_ADHESION_SIR,
                    OperadorCriterioEnum.EQUAL,
                    "S"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_OFICINA_REGISTRO,
                    OperadorCriterioEnum.EQUAL,
                    "S"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_OFICINA_INFORMACION,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_OFICINA_TRAMITACION,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_REGISTRO_ELECTRONICO,
                    OperadorCriterioEnum.EQUAL,
                    "N"))

            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_INTERCAMBIO_SIN_RESTRICCION,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_INTERCAMBIO_LOCAL_ESTATAL,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_INTERCAMBIO_AUTONOMICO_RESTRINGIDO,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_INTERCAMBIO_AUTONOMICO_GENERAL,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_INTERCAMBIO_LOCAL_RESTRINGIDO,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_INTERCAMBIO_LOCAL_GENERAL,
                    OperadorCriterioEnum.EQUAL,
                    "N"))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
                    CriterioOficinaEnum.OFICINA_INDICADOR_INTERCAMBIO_AYTO_AYTO_RESTRINGIDO,
                    OperadorCriterioEnum.EQUAL,
                    "N"))

            .addCriterio(new Criterio<CriterioOficinaEnum>(
            		CriterioOficinaEnum.OFICINA_ESTADO,
                    OperadorCriterioEnum.IN,
                    new String[] { "V", "X" }))
            .addCriterio(new Criterio<CriterioOficinaEnum>(
            		CriterioOficinaEnum.OFICINA_DESCRIPCION_ESTADO,
                    OperadorCriterioEnum.LIKE,
                    "Vigente"))
//            .addCriterio(new Criterio<CriterioOficinaEnum>(
//            		CriterioOficinaEnum.OFICINA_FECHA_CREACION,
//                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
//                    new Date()))
//            .addCriterio(new Criterio<CriterioOficinaEnum>(
//            		CriterioOficinaEnum.OFICINA_FECHA_EXTINCION,
//                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
//                    new Date()))
//            .addCriterio(new Criterio<CriterioOficinaEnum>(
//            		CriterioOficinaEnum.OFICINA_FECHA_ANULACION,
//                    OperadorCriterioEnum.EQUAL_OR_LESS_THAN,
//                    new Date()))
            .addOrderBy(CriterioOficinaEnum.OFICINA_ID)
            .addOrderBy(CriterioOficinaEnum.OFICINA_NOMBRE);
	}
}
*/
