/**
 *
 */
package se.instituciones.archivo.invesdoc.idoc;

import org.junit.Assert;
import org.junit.Test;

import se.instituciones.archivo.invesdoc.vo.OrganoVO;

import common.db.IDBEntity;
import common.manager.ArchidocDBBaseTest;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InvesDocOrganizationConnectorTest extends ArchidocDBBaseTest {

	@Test
	public void getOrganoFromXmlTest() {

		String codigo = "CODIGO";
		int nivel = 1;
		String institucion = "S";
		String nombre="Nombre Organo";
		String id = "id";
		String idPadre = "idPadre";


		String xml = getXml(codigo, nivel, institucion);

		OrganoVO organoVO = InvesDocOrganizationConnector.getOrganoVO(id, nombre, idPadre, xml);

		Assert.assertNotNull(organoVO);
		Assert.assertEquals(codigo, organoVO.getCodigo());
		Assert.assertEquals(nivel, organoVO.getNivel());
		Assert.assertEquals(institucion, organoVO.getInstitucion());
		Assert.assertEquals(id, organoVO.getId());
		Assert.assertEquals(idPadre, organoVO.getIdPadre());
		Assert.assertEquals(nombre, organoVO.getNombre());


		xml = getXmlWithOtherInfo(codigo, nivel, institucion);

		organoVO = InvesDocOrganizationConnector.getOrganoVO(id, nombre, idPadre, xml);

		Assert.assertNotNull(organoVO);
		Assert.assertEquals(codigo, organoVO.getCodigo());
		Assert.assertEquals(nivel, organoVO.getNivel());
		Assert.assertEquals(institucion, organoVO.getInstitucion());
		Assert.assertEquals(id, organoVO.getId());
		Assert.assertEquals(idPadre, organoVO.getIdPadre());
		Assert.assertEquals(nombre, organoVO.getNombre());

		organoVO =InvesDocOrganizationConnector.getOrganoVO(id, nombre, idPadre, getBadXml());

		Assert.assertNull(organoVO.getCodigo());

	}

	@Test
	public void getIdTest(){
		int idOriginal = 3;

		//"3"
		String idSimple = "" + idOriginal;

		int id = InvesDocOrganizationConnector.getId(idSimple);
		Assert.assertEquals(idOriginal, id);

		//"2-3"
		String idDepartamento = InvesDocOrganizationConnector.TIPO_DEPARTAMENTO + InvesDocOrganizationConnector.SEPARADOR_TIPOS + idOriginal;
		id = InvesDocOrganizationConnector.getId(idDepartamento);
		Assert.assertEquals(idOriginal, id);


		id = InvesDocOrganizationConnector.getId("56-654681-1351303.251");
		Assert.assertEquals(-1, id);
	}

	private String getXml(String codigo, int nivel, String institucion) {
		StringBuilder str = new StringBuilder().append("<ORGANO>")
				.append("<CODIGO>").append(codigo).append("</CODIGO>")
				.append("<NIVEL>").append(nivel).append("</NIVEL>")
				.append("<INSTITUCION>").append(institucion)
				.append("</INSTITUCION>").append("</ORGANO>");

		return str.toString();
	}

//	private String getBadXml(String codigo, int nivel, String institucion) {
//		StringBuilder str = new StringBuilder().append("<ORGANO>")
//				.append("<CODIGO>").append(codigo).append("</CODIGO>")
//				.append("<NIV>").append(nivel).append("</NIVEL>")
//				.append("<INSTCION>").append(institucion)
//				.append("</INSTITUCION>").append("</ORGANO>");
//
//		return str.toString();
//	}

	private String getXmlWithOtherInfo(String codigo, int nivel, String institucion){
		StringBuilder str = new StringBuilder()
		.append("asdfasdfasdfasdfasasdf")
			.append("<ORGANO>")
				.append("<CODIGO>").append(codigo).append("</CODIGO>")
				.append("<NIVEL>").append(nivel).append("</NIVEL>")
				.append("<INSTITUCION>").append(institucion).append("</INSTITUCION>")
			.append("</ORGANO>")

			.append("<OTRA_INFO>")
				.append("<TAG1>").append("</TAG1>")
			.append("</OTRA_INFO>")
		.append("dfdfdfd")
		;
		return str.toString();
	}

	private String getBadXml(){
		StringBuilder str = new StringBuilder()
		.append("asdfasdfasdfasdfasasdf")

		;
		return str.toString();
	}


	@Override
	protected IDBEntity getDAO() {
		// TODO Plantilla de método auto-generado
		return null;
	}

}
