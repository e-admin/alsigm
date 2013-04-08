package es.ieci.tecdoc.fwktd.sir.wsclient.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.sir.core.types.TipoRechazoEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AnexoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoBAsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AnexoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.AsientoRegistralFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.CriteriosDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoBAsientoRegistralDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoRechazoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InfoReenvioDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.InteresadoFormDTO;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;
import es.ieci.tecdoc.fwktd.sir.wsclient.TestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
public class MapperImplTest {

	protected MapperImpl getMapper() {
		return new MapperImpl();
	}

	@Test
	public void testGetCriteriosDTO() {

		CriteriosVO criteriosVO = TestUtils.createCriteriosVO();

		CriteriosDTO criteriosDTO = getMapper().getCriteriosDTO(criteriosVO);

		Assert.notNull(criteriosDTO, "criteriosDTO es nulo");
		TestUtils.assertEquals(criteriosVO, criteriosDTO);
	}

	@Test
	public void testGetTrazabilidadVOList() {

		List<TrazabilidadDTO> trazasDTO = new ArrayList<TrazabilidadDTO>();
		trazasDTO.add(TestUtils.createTrazabilidadDTO("1"));
		trazasDTO.add(TestUtils.createTrazabilidadDTO("2"));

		List<TrazabilidadVO> trazasVO = getMapper().getTrazabilidadVOList(
				trazasDTO);

		Assert.notNull(trazasVO, "trazasVO es nulo");
		TestUtils.assertEqualsTrazas(trazasDTO, trazasVO);
	}

	@Test
	public void testGetAsientoRegistralVOList() {

		List<AsientoRegistralDTO> asientosDTO = new ArrayList<AsientoRegistralDTO>();
		asientosDTO.add(TestUtils.createAsientoRegistralDTO("1"));
		asientosDTO.add(TestUtils.createAsientoRegistralDTO("2"));

		List<AsientoRegistralVO> asientosVO = getMapper()
				.getAsientoRegistralVOList(asientosDTO);

		TestUtils.assertEqualsAsientos(asientosDTO, asientosVO);
	}

	@Test
	public void testGetAsientoRegistralFormDTO() {

		AsientoRegistralFormVO asientoFormVO = TestUtils
				.createAsientoRegistralFormVO("1");

		AsientoRegistralFormDTO asientoFormDTO = getMapper()
				.getAsientoRegistralFormDTO(asientoFormVO);

		TestUtils.assertEquals(asientoFormVO, asientoFormDTO);
	}

	@Test
	public void testGetInfoBAsientoRegistralDTO() {

		InfoBAsientoRegistralVO infoBAsientoVO = TestUtils
				.createInfoBAsientoRegistralVO("1");

		InfoBAsientoRegistralDTO infoBAsientoDTO = getMapper()
				.getInfoBAsientoRegistralDTO(infoBAsientoVO);

		TestUtils.assertEquals(infoBAsientoDTO, infoBAsientoVO);
	}

	@Test
	public void testGetAsientoRegistralVO() {

		AsientoRegistralDTO asientoDTO = TestUtils
				.createAsientoRegistralDTO("1");

		AsientoRegistralVO asientoVO = getMapper().getAsientoRegistralVO(
				asientoDTO);

		TestUtils.assertEquals(asientoDTO, asientoVO);
	}

	@Test
	public void testGetAnexoFormDTO() {

		AnexoFormVO anexoFormVO = TestUtils.createAnexoFormVO("1");

		AnexoFormDTO anexoFormDTO = getMapper().getAnexoFormDTO(anexoFormVO);

		TestUtils.assertEquals(anexoFormVO, anexoFormDTO);
	}

	@Test
	public void testGetAnexoVO() {

		AnexoDTO anexoDTO = TestUtils.createAnexoDTO("1", "1");

		AnexoVO anexoVO = getMapper().getAnexoVO(anexoDTO);

		TestUtils.assertEquals(anexoDTO, anexoVO);
	}

	@Test
	public void testGetAnexoDTO() {

		AnexoVO anexoVO = TestUtils.createAnexoVO("1", "1");

		AnexoDTO anexoDTO = getMapper().getAnexoDTO(anexoVO);

		TestUtils.assertEquals(anexoDTO, anexoVO);
	}

	@Test
	public void testGetInteresadoFormDTO() {

		InteresadoFormVO interesadoFormVO = TestUtils
				.createInteresadoFormVO("1");

		InteresadoFormDTO interesadoFormDTO = getMapper().getInteresadoFormDTO(
				interesadoFormVO);

		TestUtils.assertEquals(interesadoFormVO, interesadoFormDTO);
	}

	@Test
	public void testGetInteresadoVO() {

		InteresadoDTO interesadoDTO = TestUtils.createInteresadoDTO("1", "1");

		InteresadoVO interesadoVO = getMapper().getInteresadoVO(interesadoDTO);

		TestUtils.assertEquals(interesadoDTO, interesadoVO);
	}

	@Test
	public void testGetInteresadoDTO() {

		InteresadoVO interesadoVO = TestUtils.createInteresadoVO("1", "1");

		InteresadoDTO interesadoDTO = getMapper().getInteresadoDTO(interesadoVO);

		TestUtils.assertEquals(interesadoDTO, interesadoVO);
	}

//	@Test
//	public void testGetListaValidacionAnexoVO() {
//
//		List<ValidacionAnexoDTO> validacionesAnexoDTO = new ArrayList<ValidacionAnexoDTO>();
//		validacionesAnexoDTO.add(TestUtils.createValidacionAnexoDTO("1", "1"));
//		validacionesAnexoDTO.add(TestUtils.createValidacionAnexoDTO("2", "1"));
//
//		List<ValidacionAnexoVO> validacionesAnexoVO = getMapper().getListaValidacionAnexoVO(validacionesAnexoDTO);
//
//		TestUtils.assertEquals(validacionesAnexoDTO, validacionesAnexoVO);
//	}

	@Test
	public void testGetInfoRechazoDTO() {

		InfoRechazoVO infoRechazoVO = TestUtils
				.createInfoRechazoVO(TipoRechazoEnum.RECHAZO_A_ENTIDAD_REGISTRAL_INICIAL);

		InfoRechazoDTO infoRechazoDTO = getMapper()
				.getInfoRechazoDTO(infoRechazoVO);

		TestUtils.assertEquals(infoRechazoDTO, infoRechazoVO);
	}

	@Test
	public void testGetInfoReenvioDTO() {

		InfoReenvioVO infoReenvioVO = new InfoReenvioVO();
		infoReenvioVO.setCodigoEntidadRegistralDestino("COD_ENT_REG");
		infoReenvioVO.setDescripcionEntidadRegistralDestino("DESC_ENT_REG");
		infoReenvioVO.setDescripcion("Motivo del reenvío");
		infoReenvioVO.setUsuario("usuario");
		infoReenvioVO.setContacto("contacto");
		infoReenvioVO.setAplicacion("app1");
		

		InfoReenvioDTO infoReenvioDTO = getMapper()
				.getInfoReenvioDTO(infoReenvioVO);

		TestUtils.assertEquals(infoReenvioDTO, infoReenvioVO);
	}
}
