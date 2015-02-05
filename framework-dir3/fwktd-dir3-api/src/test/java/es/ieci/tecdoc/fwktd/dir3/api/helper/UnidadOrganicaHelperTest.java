package es.ieci.tecdoc.fwktd.dir3.api.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosUnidadOrganicaVO;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;

public class UnidadOrganicaHelperTest {

	@Test
	public void testGetDatosBasicosUnidadOrganicas() {

		List<DatosBasicosUnidadOrganicaVO> listaDatosBasicosUnidadOrganicaVO = new ArrayList<DatosBasicosUnidadOrganicaVO>();
		listaDatosBasicosUnidadOrganicaVO.add(createDatosBasicosUnidadOrganicaVO("1"));
		listaDatosBasicosUnidadOrganicaVO.add(createDatosBasicosUnidadOrganicaVO("2"));

		List<DatosBasicosUnidadOrganica> listaDatosBasicosUnidadOrganica = UnidadOrganicaHelper.getDatosBasicosUnidadesOrganicas(listaDatosBasicosUnidadOrganicaVO);
		assertEquals(listaDatosBasicosUnidadOrganicaVO, listaDatosBasicosUnidadOrganica);
	}

	@Test
	public void testGetDatosBasicosUnidadOrganica() {

		DatosBasicosUnidadOrganicaVO datosBasicosUnidadOrganicaVO = createDatosBasicosUnidadOrganicaVO("1");

		DatosBasicosUnidadOrganica datosBasicosUnidadOrganica = UnidadOrganicaHelper.getDatosBasicosUnidadOrganica(datosBasicosUnidadOrganicaVO);
		assertEquals(datosBasicosUnidadOrganicaVO, datosBasicosUnidadOrganica);
	}

	protected DatosBasicosUnidadOrganicaVO createDatosBasicosUnidadOrganicaVO(String id) {

		DatosBasicosUnidadOrganicaVO datosBasicosUnidadOrganicaVO = new DatosBasicosUnidadOrganicaVO();

		datosBasicosUnidadOrganicaVO.setId(id);
		datosBasicosUnidadOrganicaVO.setNombre("Nombre " + id);
		datosBasicosUnidadOrganicaVO.setNivelAdministracion("NivelAdministracion_" + id);
		datosBasicosUnidadOrganicaVO.setDescripcionNivelAdministracion("DescripcionNivelAdministracion_" + id);
		datosBasicosUnidadOrganicaVO.setIdExternoFuente("IdExternoFuente_" + id);
		datosBasicosUnidadOrganicaVO.setIndicadorEntidadDerechoPublico("IndicadorEntidadDerechoPublico_" + id);

		datosBasicosUnidadOrganicaVO.setIdUnidadOrganicaSuperior("IdUnidadOrganicaSuperior_" + id);
		datosBasicosUnidadOrganicaVO.setNombreUnidadOrganicaSuperior("NombreUnidadOrganicaSuperior_" + id);
		datosBasicosUnidadOrganicaVO.setIdUnidadOrganicaPrincipal("IdUnidadOrganicaPrincipal_" + id);
		datosBasicosUnidadOrganicaVO.setNombreUnidadOrganicaPrincipal("NombreUnidadOrganicaPrincipal_" + id);
		datosBasicosUnidadOrganicaVO.setIdUnidadOrganicaEntidadDerechoPublico("IdUnidadOrganicaEntidadDerechoPublico_" + id);
		datosBasicosUnidadOrganicaVO.setNombreUnidadOrganicaEntidadDerechoPublico("NombreUnidadOrganicaEntidadDerechoPublico_" + id);
		datosBasicosUnidadOrganicaVO.setNivelJerarquico(1);

		datosBasicosUnidadOrganicaVO.setEstado("V");
		datosBasicosUnidadOrganicaVO.setDescripcionEstado("Vigente");
		datosBasicosUnidadOrganicaVO.setFechaAltaOficial(new Date());
		datosBasicosUnidadOrganicaVO.setFechaBajaOficial(new Date());
		datosBasicosUnidadOrganicaVO.setFechaExtincion(new Date());
		datosBasicosUnidadOrganicaVO.setFechaAnulacion(new Date());

		return datosBasicosUnidadOrganicaVO;
	}

	protected void assertEquals(List<DatosBasicosUnidadOrganicaVO> listaDatosBasicosUnidadOrganicaVO, List<DatosBasicosUnidadOrganica> listaDatosBasicosUnidadOrganica) {

		Assert.assertTrue(CollectionUtils.size(listaDatosBasicosUnidadOrganicaVO) == CollectionUtils.size(listaDatosBasicosUnidadOrganica));

		for (int i = 0; i < listaDatosBasicosUnidadOrganicaVO.size(); i++) {
			assertEquals(listaDatosBasicosUnidadOrganicaVO.get(i), listaDatosBasicosUnidadOrganica.get(i));
		}
	}

	protected void assertEquals(DatosBasicosUnidadOrganicaVO datosBasicosUnidadOrganicaVO, DatosBasicosUnidadOrganica datosBasicosUnidadOrganica) {

		Assert.assertNotNull(datosBasicosUnidadOrganicaVO);
		Assert.assertNotNull(datosBasicosUnidadOrganica);

		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getId(), datosBasicosUnidadOrganica.getId());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getNombre(), datosBasicosUnidadOrganica.getNombre());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getNivelAdministracion(), datosBasicosUnidadOrganica.getNivelAdministracion());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getDescripcionNivelAdministracion(), datosBasicosUnidadOrganica.getDescripcionNivelAdministracion());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getIndicadorEntidadDerechoPublico(), datosBasicosUnidadOrganica.getIndicadorEntidadDerechoPublico());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getIdExternoFuente(), datosBasicosUnidadOrganica.getIdExternoFuente());

		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getIdUnidadOrganicaSuperior(), datosBasicosUnidadOrganica.getIdUnidadOrganicaSuperior());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getNombreUnidadOrganicaSuperior(), datosBasicosUnidadOrganica.getNombreUnidadOrganicaSuperior());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getIdUnidadOrganicaPrincipal(), datosBasicosUnidadOrganica.getIdUnidadOrganicaPrincipal());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getNombreUnidadOrganicaPrincipal(), datosBasicosUnidadOrganica.getNombreUnidadOrganicaPrincipal());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getIdUnidadOrganicaEntidadDerechoPublico(), datosBasicosUnidadOrganica.getIdUnidadOrganicaEntidadDerechoPublico());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getNombreUnidadOrganicaEntidadDerechoPublico(), datosBasicosUnidadOrganica.getNombreUnidadOrganicaEntidadDerechoPublico());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getNivelJerarquico(), datosBasicosUnidadOrganica.getNivelJerarquico());

		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getEstado(), datosBasicosUnidadOrganica.getEstado());
		Assert.assertEquals(datosBasicosUnidadOrganicaVO.getDescripcionEstado(), datosBasicosUnidadOrganica.getDescripcionEstado());
		Assert.assertEquals(String.valueOf(datosBasicosUnidadOrganicaVO.getFechaAltaOficial()), String.valueOf(datosBasicosUnidadOrganica.getFechaAltaOficial()));
		Assert.assertEquals(String.valueOf(datosBasicosUnidadOrganicaVO.getFechaBajaOficial()), String.valueOf(datosBasicosUnidadOrganica.getFechaBajaOficial()));
		Assert.assertEquals(String.valueOf(datosBasicosUnidadOrganicaVO.getFechaExtincion()), String.valueOf(datosBasicosUnidadOrganica.getFechaExtincion()));
		Assert.assertEquals(String.valueOf(datosBasicosUnidadOrganicaVO.getFechaAnulacion()), String.valueOf(datosBasicosUnidadOrganica.getFechaAnulacion()));
	}
}
