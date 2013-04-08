package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml" })
public class TipoDocumentoIdentificativoDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void testGetAllTiposDocumentoIdentificativo() {
		List<TipoDocumentoIdentificativoTerceroVO> all = getTipoDocumentoIdentificativoDao()
				.getAll();

		Assert.assertNotNull(all);
		Assert.assertEquals(6, all.size());
	}

	@Test
	public void testGetTipoDocumentoIdentificativoById() {
		TipoDocumentoIdentificativoTerceroVO tipoDocumentoIdentificativoTerceroVO = getTipoDocumentoIdentificativoDao()
				.get("1");

		Assert.assertNotNull(tipoDocumentoIdentificativoTerceroVO);
		Assert.assertEquals("1", tipoDocumentoIdentificativoTerceroVO.getId());
		Assert.assertEquals("C",
				tipoDocumentoIdentificativoTerceroVO.getCodigo());
		Assert.assertEquals("CIF",
				tipoDocumentoIdentificativoTerceroVO.getDescripcion());
	}

	@Test
	public void testGetTipoDocumentoIdentificativoByTipo() {
		List<TipoDocumentoIdentificativoTerceroVO> tiposDocumentoIdentificativo = getTipoDocumentoIdentificativoDao()
				.getTiposDocumentoIdentificativo(SearchType.FISICO);

		Assert.assertNotNull(tiposDocumentoIdentificativo);
		Assert.assertEquals(5, tiposDocumentoIdentificativo.size());

		tiposDocumentoIdentificativo = getTipoDocumentoIdentificativoDao()
				.getTiposDocumentoIdentificativo(SearchType.JURIDICO);

		Assert.assertNotNull(tiposDocumentoIdentificativo);
		Assert.assertEquals(3, tiposDocumentoIdentificativo.size());
	}

	public TipoDocumentoIdentificativoDao getTipoDocumentoIdentificativoDao() {
		return tipoDocumentoIdentificativoDao;
	}

	public void setTipoDocumentoIdentificativoDao(
			TipoDocumentoIdentificativoDao tipoDocumentoIdentificativoDao) {
		this.tipoDocumentoIdentificativoDao = tipoDocumentoIdentificativoDao;
	}

	// Members
	@Autowired
	protected TipoDocumentoIdentificativoDao tipoDocumentoIdentificativoDao;
}
