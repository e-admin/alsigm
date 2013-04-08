package es.ieci.tecdoc.fwktd.dir3.api.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosOficinaVO;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;

public class OficinaHelperTest {

	@Test
	public void testGetDatosBasicosOficinas() {

		List<DatosBasicosOficinaVO> listaDatosBasicosOficinaVO = new ArrayList<DatosBasicosOficinaVO>();
		listaDatosBasicosOficinaVO.add(createDatosBasicosOficinaVO("1"));
		listaDatosBasicosOficinaVO.add(createDatosBasicosOficinaVO("2"));

		List<DatosBasicosOficina> listaDatosBasicosOficina = OficinaHelper.getDatosBasicosOficinas(listaDatosBasicosOficinaVO);
		assertEquals(listaDatosBasicosOficinaVO, listaDatosBasicosOficina);
	}

	@Test
	public void testGetDatosBasicosOficina() {

		DatosBasicosOficinaVO datosBasicosOficinaVO = createDatosBasicosOficinaVO("1");

		DatosBasicosOficina datosBasicosOficina = OficinaHelper.getDatosBasicosOficina(datosBasicosOficinaVO);
		assertEquals(datosBasicosOficinaVO, datosBasicosOficina);
	}

	protected DatosBasicosOficinaVO createDatosBasicosOficinaVO(String id) {

		DatosBasicosOficinaVO datosBasicosOficinaVO = new DatosBasicosOficinaVO();

		datosBasicosOficinaVO.setId(id);
		datosBasicosOficinaVO.setNombre("Nombre " + id);
		datosBasicosOficinaVO.setIdExternoFuente("IdExternoFuente_" + id);
		datosBasicosOficinaVO.setIdUnidadResponsable("IdUnidadResponsable_" + id);
		datosBasicosOficinaVO.setNombreUnidadResponsable("NombreUnidadResponsable_" + id);
		datosBasicosOficinaVO.setNivelAdministracion("NivelAdministracion_" + id);
		datosBasicosOficinaVO.setDescripcionNivelAdministracion("DescripcionNivelAdministracion_" + id);

		datosBasicosOficinaVO.setIndicadorAdhesionSIR("S");
		datosBasicosOficinaVO.setIndicadorOficinaRegistro("S");
		datosBasicosOficinaVO.setIndicadorOficinaInformacion("S");
		datosBasicosOficinaVO.setIndicadorOficinaTramitacion("S");
		datosBasicosOficinaVO.setIndicadorRegistroElectronico("S");
		datosBasicosOficinaVO.setIndicadorIntercambioSinRestriccion("S");
		datosBasicosOficinaVO.setIndicadorIntercambioLocalEstatal("S");
		datosBasicosOficinaVO.setIndicadorIntercambioLocalAutonomicoRestringido("S");
		datosBasicosOficinaVO.setIndicadorIntercambioLocalAutonomicoGeneral("S");
		datosBasicosOficinaVO.setIndicadorIntercambioLocalLocalRestringido("S");
		datosBasicosOficinaVO.setIndicadorIntercambioLocalLocalGeneral("S");
		datosBasicosOficinaVO.setIndicadorIntercambioAytoAytoRestringido("S");

		datosBasicosOficinaVO.setEstado("V");
		datosBasicosOficinaVO.setDescripcionEstado("Vigente");
		datosBasicosOficinaVO.setFechaCreacion(new Date());
		datosBasicosOficinaVO.setFechaExtincion(new Date());
		datosBasicosOficinaVO.setFechaAnulacion(new Date());

		return datosBasicosOficinaVO;
	}

	protected void assertEquals(List<DatosBasicosOficinaVO> listaDatosBasicosOficinaVO, List<DatosBasicosOficina> listaDatosBasicosOficina) {

		Assert.assertTrue(CollectionUtils.size(listaDatosBasicosOficinaVO) == CollectionUtils.size(listaDatosBasicosOficina));

		for (int i = 0; i < listaDatosBasicosOficinaVO.size(); i++) {
			assertEquals(listaDatosBasicosOficinaVO.get(i), listaDatosBasicosOficina.get(i));
		}
	}

	protected void assertEquals(DatosBasicosOficinaVO datosBasicosOficinaVO, DatosBasicosOficina datosBasicosOficina) {

		Assert.assertNotNull(datosBasicosOficinaVO);
		Assert.assertNotNull(datosBasicosOficina);

		Assert.assertEquals(datosBasicosOficinaVO.getId(), datosBasicosOficina.getId());
		Assert.assertEquals(datosBasicosOficinaVO.getNombre(), datosBasicosOficina.getNombre());
		Assert.assertEquals(datosBasicosOficinaVO.getIdExternoFuente(), datosBasicosOficina.getIdExternoFuente());

		Assert.assertEquals(datosBasicosOficinaVO.getIdUnidadResponsable(), datosBasicosOficina.getIdUnidadResponsable());
		Assert.assertEquals(datosBasicosOficinaVO.getNombreUnidadResponsable(), datosBasicosOficina.getNombreUnidadResponsable());
		Assert.assertEquals(datosBasicosOficinaVO.getNivelAdministracion(), datosBasicosOficina.getNivelAdministracion());
		Assert.assertEquals(datosBasicosOficinaVO.getDescripcionNivelAdministracion(), datosBasicosOficina.getDescripcionNivelAdministracion());

		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorAdhesionSIR(), datosBasicosOficina.getIndicadorAdhesionSIR());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorOficinaRegistro(), datosBasicosOficina.getIndicadorOficinaRegistro());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorOficinaInformacion(), datosBasicosOficina.getIndicadorOficinaInformacion());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorOficinaTramitacion(), datosBasicosOficina.getIndicadorOficinaTramitacion());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorRegistroElectronico(), datosBasicosOficina.getIndicadorRegistroElectronico());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorIntercambioSinRestriccion(), datosBasicosOficina.getIndicadorIntercambioSinRestriccion());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorIntercambioLocalEstatal(), datosBasicosOficina.getIndicadorIntercambioLocalEstatal());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorIntercambioLocalAutonomicoRestringido(), datosBasicosOficina.getIndicadorIntercambioLocalAutonomicoRestringido());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorIntercambioLocalAutonomicoGeneral(), datosBasicosOficina.getIndicadorIntercambioLocalAutonomicoGeneral());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorIntercambioLocalLocalRestringido(), datosBasicosOficina.getIndicadorIntercambioLocalLocalRestringido());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorIntercambioLocalLocalGeneral(), datosBasicosOficina.getIndicadorIntercambioLocalLocalGeneral());
		Assert.assertEquals(datosBasicosOficinaVO.getIndicadorIntercambioAytoAytoRestringido(), datosBasicosOficina.getIndicadorIntercambioAytoAytoRestringido());

		Assert.assertEquals(datosBasicosOficinaVO.getEstado(), datosBasicosOficina.getEstado());
		Assert.assertEquals(datosBasicosOficinaVO.getDescripcionEstado(), datosBasicosOficina.getDescripcionEstado());
		Assert.assertEquals(String.valueOf(datosBasicosOficinaVO.getFechaCreacion()), String.valueOf(datosBasicosOficina.getFechaCreacion()));
		Assert.assertEquals(String.valueOf(datosBasicosOficinaVO.getFechaExtincion()), String.valueOf(datosBasicosOficina.getFechaExtincion()));
		Assert.assertEquals(String.valueOf(datosBasicosOficinaVO.getFechaAnulacion()), String.valueOf(datosBasicosOficina.getFechaAnulacion()));
	}
}
