package transferencias.db;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import transferencias.vos.RelacionEntregaVO;

import common.manager.ArchidocDBBaseTest;

public class RelacionEntregaDBEntityBaseImplTest extends ArchidocDBBaseTest {

	@Override
	protected IRelacionEntregaDBEntity getDAO() {
		return getManager().getRelacionEntregaDBEntity();
	}

	@Test
	public void getRelacionByRegEntrada(){
		List listaRelaciones = getDAO().getRelacionesByRegEntrada(regEntrada);
		Assert.assertNotNull(listaRelaciones);
		Assert.assertEquals(1, listaRelaciones.size());
	}

	@Test
	public void getRelacionVO(){
		RelacionEntregaVO relacionMock = getMockRelacionEntregaElectronica();

		RelacionEntregaVO relacion = getDAO().getRelacionVO(relacionMock);
//		Assert.assertNotNull(relacion);
//
//		Assert.assertEquals(relacionMock.getId(), relacion.getId());
	}

	@Test
	public void testGetCountRelacionesVO(){
		RelacionEntregaVO relacionMock = getMockRelacionEntregaElectronica();
		int numero = getDAO().getCountRelacionesBySerie(relacionMock.getIdseriedestino());
	}

}
