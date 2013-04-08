package es.ieci.tecdoc.fwktd.dir3.api.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioConsultaDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.OperadorCriterioEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterio;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;

/**
 * Clase de Test del servicio de consulta en el Directorio Común.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
@ContextConfiguration({"/beans/cxf.xml", "/beans/fwktd-dir3-test-beans.xml",
		"/beans/fwktd-dir3-api-applicationContext.xml" })
public class ServicioConsultaDirectorioComunImplTest extends
		AbstractJUnit4SpringContextTests {

	protected static final String ID_OFICINA_EXISTENTE = "O00001177";
	protected static final String ID_UNIDAD_ORGANICA_EXISTENTE = "L01330447";

	@Autowired
	private ServicioConsultaDirectorioComunImpl fwktd_dir3_api_servicioConsultaDirectorioComunImpl;

	protected ServicioConsultaDirectorioComun getServicioConsultaDirectorioComun() {
		return fwktd_dir3_api_servicioConsultaDirectorioComunImpl;
	}

	@Test
	public void testService() {
		Assert.assertNotNull(getServicioConsultaDirectorioComun());
	}

	@Test
	public void testCountOficinas() {

//		// Test de consulta simple
//		int count = getServicioConsultaDirectorioComun().countOficinas(null);
//		Assert.assertTrue("No se han obtenido resultados", count > 0);
//
//		// Test de consulta simple
//		count = getServicioConsultaDirectorioComun().countOficinas(new Criterios<CriterioOficinaEnum>());
//		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta con criterios
		int count = getServicioConsultaDirectorioComun().countOficinas(createCriteriosOficina());
		Assert.assertTrue("No se han obtenido resultados", count > 0);
	}

	@Test
	public void testFindOficinas() {

//		// Test de consulta simple
//		List<DatosBasicosOficina> oficinas = getServicioConsultaDirectorioComun().findOficinas(null);
//		Assert.assertNotNull("Resultado nulo", oficinas);
//		Assert.assertTrue("No se han obtenido resultados", oficinas.size() > 0);
//
//		// Test de consulta simple
//		oficinas = getServicioConsultaDirectorioComun().findOficinas(new Criterios<CriterioOficinaEnum>());
//		Assert.assertNotNull("Resultado nulo", oficinas);
//		Assert.assertTrue("No se han obtenido resultados", oficinas.size() > 0);

		// Test de consulta con criterios
		List<DatosBasicosOficina> oficinas = getServicioConsultaDirectorioComun().findOficinas(createCriteriosOficina());
		Assert.assertNotNull("Resultado nulo", oficinas);
		Assert.assertTrue("No se han obtenido resultados", oficinas.size() > 0);

		// Test de consulta con criterios
		oficinas = getServicioConsultaDirectorioComun().findOficinas(new Criterios<CriterioOficinaEnum>()
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

	@Test
	public void testGetOficinaExistente() {

		DatosBasicosOficina oficina = getServicioConsultaDirectorioComun().getDatosBasicosOficina(ID_OFICINA_EXISTENTE);
		assertDatosBasicosOficina(oficina);
	}

	@Test
	public void testCountUnidadesOrganicas() {

//		// Test de consulta simple
//		int count = getServicioConsultaDirectorioComun().countUnidadesOrganicas(null);
//		Assert.assertTrue("No se han obtenido resultados", count > 0);
//
//		// Test de consulta simple
//		count = getServicioConsultaDirectorioComun().countUnidadesOrganicas(new Criterios<CriterioUnidadOrganicaEnum>());
//		Assert.assertTrue("No se han obtenido resultados", count > 0);

		// Test de consulta con criterios
		int count = getServicioConsultaDirectorioComun().countUnidadesOrganicas(createCriteriosUnidadOrganica());
		Assert.assertTrue("No se han obtenido resultados", count > 0);
	}

	@Test
	public void testFindUnidadesOrganicas() {

//		// Test de consulta simple
//		List<DatosBasicosUnidadOrganica> unidades = getServicioConsultaDirectorioComun().findUnidadesOrganicas(null);
//		Assert.assertNotNull("Resultado nulo", unidades);
//		Assert.assertTrue("No se han obtenido resultados", unidades.size() > 0);
//
//		// Test de consulta simple
//		unidades = getServicioConsultaDirectorioComun().findUnidadesOrganicas(new Criterios<CriterioUnidadOrganicaEnum>());
//		Assert.assertNotNull("Resultado nulo", unidades);
//		Assert.assertTrue("No se han obtenido resultados", unidades.size() > 0);

		// Test de consulta con criterios
		List<DatosBasicosUnidadOrganica> unidades = getServicioConsultaDirectorioComun().findUnidadesOrganicas(createCriteriosUnidadOrganica());
		Assert.assertNotNull("Resultado nulo", unidades);
		Assert.assertTrue("No se han obtenido resultados", unidades.size() > 0);
	}

	@Test
	public void testGetUnidadExistente() {

		DatosBasicosUnidadOrganica unidad = getServicioConsultaDirectorioComun()
				.getDatosBasicosUnidadOrganica(ID_UNIDAD_ORGANICA_EXISTENTE);
		assertDatosBasicosUnidadOrganica(unidad);
	}

	protected void assertDatosBasicosOficina(DatosBasicosOficina oficina) {

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

	protected void assertDatosBasicosUnidadOrganica(DatosBasicosUnidadOrganica unidad) {

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


	protected Criterios<CriterioOficinaEnum> createCriteriosOficina() {
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

	protected Criterios<CriterioUnidadOrganicaEnum> createCriteriosUnidadOrganica() {
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

}
