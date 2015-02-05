package es.ieci.tecdoc.fwktd.csv.api.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionHelperTest {

	@Test
	public void testGetListaAplicacionCSV() {

		List<AplicacionVO> aplicacionesVO = new ArrayList<AplicacionVO>();
		aplicacionesVO
				.add(createAplicacionVO(
						"1",
						"C1",
						"Nombre1",
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));
		aplicacionesVO
				.add(createAplicacionVO(
						"2",
						"C2",
						"Nombre2",
						"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>"));

		List<AplicacionCSV> aplicacionesCSV = AplicacionHelper
				.getListaAplicacionCSV(aplicacionesVO);
		assertEquals(aplicacionesVO, aplicacionesCSV);
	}

	@Test
	public void testGetAplicacionCSV() {

		AplicacionVO aplicacionVO = createAplicacionVO(
				"1",
				"C1",
				"Nombre1",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionCSV aplicacionCSV = AplicacionHelper
				.getAplicacionCSV(aplicacionVO);

		assertEquals(aplicacionVO, aplicacionCSV);
	}

	@Test
	public void testGetAplicacionVOFromAplicacionCSVForm() {

		AplicacionCSV aplicacionCSVForm = createAplicacionCSVForm(
				"C1",
				"Nombre1",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionVO aplicacionVO = AplicacionHelper.getAplicacionVO(aplicacionCSVForm);
		assertEquals(aplicacionVO, aplicacionCSVForm);
	}

	@Test
	public void testGetAplicacionVOFromAplicacionCSV() {

		AplicacionCSV aplicacionCSV = createAplicacionCSV(
				"1",
				"C1",
				"Nombre1",
				"<?xml version='1.0' encoding='UTF-8'?><connection-config><connector>MOCK</connector></connection-config>");

		AplicacionVO aplicacionVO = AplicacionHelper.getAplicacionVO(aplicacionCSV);
		assertEquals(aplicacionVO, aplicacionCSV);
	}

	protected AplicacionVO createAplicacionVO(String id, String codigo,
			String nombre, String infoConexion) {
		AplicacionVO aplicacionVO = new AplicacionVO();
		aplicacionVO.setId(id);
		aplicacionVO.setCodigo(codigo);
		aplicacionVO.setNombre(nombre);
		aplicacionVO.setInfoConexion(infoConexion);
		return aplicacionVO;
	}

	protected AplicacionCSV createAplicacionCSVForm(String codigo,
			String nombre, String infoConexion) {
		AplicacionCSV aplicacionCSVForm = new AplicacionCSV();
		aplicacionCSVForm.setCodigo(codigo);
		aplicacionCSVForm.setNombre(nombre);
		aplicacionCSVForm.setInfoConexion(infoConexion);
		return aplicacionCSVForm;
	}

	protected AplicacionCSV createAplicacionCSV(String id, String codigo,
			String nombre, String infoConexion) {
		AplicacionCSV aplicacionCSV = new AplicacionCSV();
		aplicacionCSV.setId(id);
		aplicacionCSV.setCodigo(codigo);
		aplicacionCSV.setNombre(nombre);
		aplicacionCSV.setInfoConexion(infoConexion);
		return aplicacionCSV;
	}

	protected void assertEquals(List<AplicacionVO> aplicacionesVO, List<AplicacionCSV> aplicacionesCSV) {

		Assert.assertTrue(CollectionUtils.size(aplicacionesVO) == CollectionUtils.size(aplicacionesCSV));

		for (int i = 0; i < aplicacionesVO.size(); i++) {
			assertEquals(aplicacionesVO.get(i), aplicacionesCSV.get(i));
		}
	}

	protected void assertEquals(AplicacionVO aplicacionVO, AplicacionCSV aplicacionCSVForm) {

		Assert.assertNotNull(aplicacionVO);
		Assert.assertNotNull(aplicacionCSVForm);

		if (aplicacionCSVForm instanceof AplicacionCSV) {
			Assert.assertEquals(aplicacionVO.getId(), ((AplicacionCSV)aplicacionCSVForm).getId());
		}
		Assert.assertEquals(aplicacionVO.getCodigo(), aplicacionCSVForm.getCodigo());
		Assert.assertEquals(aplicacionVO.getNombre(), aplicacionCSVForm.getNombre());
		Assert.assertEquals(aplicacionVO.getInfoConexion(), aplicacionCSVForm.getInfoConexion());
	}
}
