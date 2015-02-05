package transferencias.db;

import org.junit.Assert;
import org.junit.Test;

import transferencias.vos.UnidadDocumentalVO;

import common.manager.ArchidocDBBaseTest;

public class UDocRelacionDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IUDocRelacionDBEntity getDAO() {
		return getManager().getuDocRelacionDBEntity();
	}

	@Test
	public void getUnidadDocumentalSinProductor(){

		UnidadDocumentalVO filtroUdocVO = new UnidadDocumentalVO("3","4","/");

		UnidadDocumentalVO resultado = null;

		filtroUdocVO.setCodSistProductor(codSistProductor);

		//1 Nº Expediente No Nulo
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id1, resultado.getId());



		//NUMEXP
		filtroUdocVO.setNumeroExpediente(numeroExpediente);

		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id1, resultado.getId());

		resultado = null;
		filtroUdocVO.setCodSistProductor(null);
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id2, resultado.getId());


		//NUMEXP + ASUNTO
		filtroUdocVO.setCodSistProductor(codSistProductor);
		filtroUdocVO.setNumeroExpediente(numeroExpediente);
		filtroUdocVO.setAsunto(asunto);

		resultado = null;
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id1, resultado.getId());

		resultado = null;
		filtroUdocVO.setCodSistProductor(null);
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id2, resultado.getId());


		//NUMEXP + ASUNTO + IDRELENTREGA
		filtroUdocVO.setCodSistProductor(codSistProductor);
		filtroUdocVO.setNumeroExpediente(numeroExpediente);
		filtroUdocVO.setIdRelEntrega(idRelEntrega);
		filtroUdocVO.setAsunto(asunto);

		resultado = null;
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id1, resultado.getId());

		resultado = null;
		filtroUdocVO.setCodSistProductor(null);
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id2, resultado.getId());


		//NUMEXP + ASUNTO + IDRELENTREGA + FECHAINI
		filtroUdocVO.setCodSistProductor(codSistProductor);
		filtroUdocVO.setNumeroExpediente(numeroExpediente);
		filtroUdocVO.setIdRelEntrega(idRelEntrega);
		filtroUdocVO.setAsunto(asunto);
		filtroUdocVO.setFechaInicio(fechaInicio);

		resultado = null;
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id1, resultado.getId());

		resultado = null;
		filtroUdocVO.setCodSistProductor(null);
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id2, resultado.getId());


		//NUMEXP + ASUNTO + IDRELENTREGA + FECHAINI + FECHAFIN
		filtroUdocVO.setCodSistProductor(codSistProductor);
		filtroUdocVO.setNumeroExpediente(numeroExpediente);
		filtroUdocVO.setIdRelEntrega(idRelEntrega);
		filtroUdocVO.setAsunto(asunto);
		filtroUdocVO.setFechaInicio(fechaInicio);
		filtroUdocVO.setFechaFin(fechaFin);

		resultado = null;
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id1, resultado.getId());

		resultado = null;
		filtroUdocVO.setCodSistProductor(null);
		resultado = getDAO().getUnidadDocumental(filtroUdocVO);
		Assert.assertNotNull(resultado);
		Assert.assertEquals(id2, resultado.getId());


	}




}
