package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.FilterVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.OperatorEnum;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml" })
public class TerceroDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void getAllTerceros() {
		List<TerceroValidadoVO> terceros = getTerceroDao().getAll();

		Assert.assertNotNull(terceros);
		Assert.assertEquals(4, terceros.size());
	}

	@Test
	public void getTerceroFisico() {
		TerceroValidadoVO terceroValidadoVO = getTerceroDao().get("1");

		Assert.assertNotNull(terceroValidadoVO);
		Assert.assertTrue(terceroValidadoVO instanceof TerceroValidadoFisicoVO);

		Assert.assertEquals(3, terceroValidadoVO.getDirecciones().size());
	}

	@Test
	public void getTerceroJuridico() {
		TerceroValidadoVO terceroValidadoVO = getTerceroDao().get("2");

		Assert.assertNotNull(terceroValidadoVO);
		Assert.assertTrue(terceroValidadoVO instanceof TerceroValidadoJuridicoVO);

		Assert.assertEquals(0, terceroValidadoVO.getDirecciones().size());
	}

	@Test
	public void addTerceroFisico() {
		TerceroValidadoFisicoVO terceroFisico = new TerceroValidadoFisicoVO();
		terceroFisico.setId("1000");
		terceroFisico.setNombre("Nombre");
		terceroFisico.setApellido1("Apellido1");
		terceroFisico.setApellido2("Apellido2");
		terceroFisico.setNumeroDocumento("12345678Z");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("2");
		terceroFisico.setTipoDocumento(tipoDocumento);

		getTerceroDao().save(terceroFisico);

		TerceroValidadoFisicoVO terceroFisicoSaved = (TerceroValidadoFisicoVO) getTerceroDao()
				.get("1000");

		Assert.assertNotNull(terceroFisicoSaved);
		Assert.assertEquals("1000", terceroFisicoSaved.getId());
		Assert.assertEquals("Nombre", terceroFisico.getNombre());
		Assert.assertEquals("Apellido1", terceroFisico.getApellido1());
		Assert.assertEquals("Apellido2", terceroFisico.getApellido2());
		Assert.assertEquals("12345678Z", terceroFisico.getNumeroDocumento());
		Assert.assertEquals("2", terceroFisicoSaved.getTipoDocumento().getId());
	}

	@Test
	public void addTerceroJuridico() {
		TerceroValidadoJuridicoVO terceroJuridico = new TerceroValidadoJuridicoVO();
		terceroJuridico.setId("1000");
		terceroJuridico.setNombre("Nombre de la empresa");
		terceroJuridico.setNumeroDocumento("A12345678");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("1");
		terceroJuridico.setTipoDocumento(tipoDocumento);

		getTerceroDao().save(terceroJuridico);

		TerceroValidadoJuridicoVO terceroFisicoSaved = (TerceroValidadoJuridicoVO) getTerceroDao()
				.get("1000");

		Assert.assertNotNull(terceroFisicoSaved);
		Assert.assertEquals("1000", terceroFisicoSaved.getId());
		Assert.assertEquals("Nombre de la empresa", terceroJuridico.getNombre());
		Assert.assertEquals("A12345678", terceroJuridico.getNumeroDocumento());
		Assert.assertEquals("1", terceroFisicoSaved.getTipoDocumento().getId());
	}

	@Test
	public void updateTerceroFisico() {
		TerceroValidadoFisicoVO terceroValidadoVO = (TerceroValidadoFisicoVO) getTerceroDao()
				.get("1");

		Assert.assertNotNull(terceroValidadoVO);

		terceroValidadoVO.setNombre("Nombre actualizado");
		terceroValidadoVO.setApellido1("Apellido1 actualizado");
		terceroValidadoVO.setApellido2("Apellido2 actualizado");
		terceroValidadoVO.setNumeroDocumento("X3312234L");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("4");
		terceroValidadoVO.setTipoDocumento(tipoDocumento);

		getTerceroDao().update(terceroValidadoVO);

		TerceroValidadoVO terceroValidadoUpdated = getTerceroDao().get("1");

		Assert.assertNotNull(terceroValidadoUpdated);
		Assert.assertEquals("Nombre actualizado", terceroValidadoVO.getNombre());
		Assert.assertEquals("Apellido1 actualizado",
				terceroValidadoVO.getApellido1());
		Assert.assertEquals("Apellido2 actualizado",
				terceroValidadoVO.getApellido2());
		Assert.assertEquals("X3312234L", terceroValidadoVO.getNumeroDocumento());
		Assert.assertEquals("4", terceroValidadoUpdated.getTipoDocumento()
				.getId());
	}

	@Test
	public void updateTerceroJuridico() {
		TerceroValidadoJuridicoVO terceroValidadoVO = (TerceroValidadoJuridicoVO) getTerceroDao()
				.get("2");

		Assert.assertNotNull(terceroValidadoVO);

		terceroValidadoVO.setNombre("Nombre de empresa actualizado");
		terceroValidadoVO.setNumeroDocumento("X3312234L");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("6");
		terceroValidadoVO.setTipoDocumento(tipoDocumento);

		getTerceroDao().update(terceroValidadoVO);

		TerceroValidadoVO terceroValidadoUpdated = getTerceroDao().get("2");

		Assert.assertNotNull(terceroValidadoUpdated);
		Assert.assertEquals("Nombre de empresa actualizado",
				terceroValidadoVO.getNombre());
		Assert.assertEquals("X3312234L", terceroValidadoVO.getNumeroDocumento());
		Assert.assertEquals("6", terceroValidadoUpdated.getTipoDocumento()
				.getId());
	}

	@Test
	public void deleteTercero() {
		getTerceroDao().delete("1");

		Assert.assertEquals(3, getTerceroDao().count());
	}

	@Test
	@ExpectedException(ObjectRetrievalFailureException.class)
	public void deleteUnkownTercero() {
		getTerceroDao().delete("10");
	}

	@Test
	public void deleteAllTerceros() {
		getTerceroDao().deleteAll();

		Assert.assertEquals(0, getTerceroDao().count());
	}

	@Test
	public void getAllTercerosFisicos() {
		List<TerceroValidadoFisicoVO> all = getTerceroDao()
				.getAllTercerosFisicos();

		Assert.assertNotNull(all);
		Assert.assertEquals(3, all.size());
	}

	@Test
	public void deleteTerceroFisico() {
		getTerceroDao().delete("1");

		Assert.assertEquals(3, getTerceroDao().getAll().size());
	}

	@Test
	@ExpectedException(ObjectRetrievalFailureException.class)
	public void deleteUnknownTercero() {
		getTerceroDao().delete("10");

		Assert.assertEquals(1, getTerceroDao().getAll().size());
	}

	@Test
	public void getAllTercerosJuridicos() {
		List<TerceroValidadoJuridicoVO> all = getTerceroDao()
				.getAllTercerosJuridicos();

		Assert.assertNotNull(all);
		Assert.assertEquals(1, all.size());
	}

	@Test
	public void getTerceroJuricico() {
		TerceroValidadoJuridicoVO terceroJuridico = (TerceroValidadoJuridicoVO) getTerceroDao()
				.get("2");

		Assert.assertNotNull(terceroJuridico);
		Assert.assertEquals("2", terceroJuridico.getId());
		Assert.assertEquals("A28855260", terceroJuridico.getNumeroDocumento());
		Assert.assertEquals("1", terceroJuridico.getTipoDocumento().getId());
		Assert.assertEquals("IECI", terceroJuridico.getNombre());
	}

	@Test
	public void deleteTerceroJuridico() {
		getTerceroDao().delete("2");

		Assert.assertEquals(3, getTerceroDao().getAll().size());
	}

	@Test
	public void findTerceroFisicoByNameCriteria() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);
		List<FilterVO> filters = new ArrayList<FilterVO>();
		FilterVO nameFilter = new FilterVO();
		nameFilter.setField("surname");
		nameFilter.setOperator(OperatorEnum.ES_IGUAL);
		nameFilter.setValue("PABLO");
		filters.add(nameFilter);

		criteria.setFilters(filters);

		List<TerceroValidadoVO> findByCriteria = getTerceroDao().findByCriteria(
				criteria);

		Assert.assertNotNull(findByCriteria);
		Assert.assertEquals(1, findByCriteria.size());
	}

	@Test
	public void findTerceroFisicoByMultipleCriteria() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);
		List<FilterVO> filters = new ArrayList<FilterVO>();

		// Filtro por nombre
		FilterVO nameFilter = new FilterVO();
		nameFilter.setField("surname");
		nameFilter.setOperator(OperatorEnum.ES_IGUAL);
		nameFilter.setValue("PABLO");
		filters.add(nameFilter);

		// Filtro por apellido1
		FilterVO apellido1Filter = new FilterVO();
		apellido1Filter.setField("first_name");
		apellido1Filter.setOperator(OperatorEnum.ES_IGUAL);
		apellido1Filter.setValue("PEREZ");
		filters.add(apellido1Filter);

		// Filtro por apellido2
		FilterVO apellido2Filter = new FilterVO();
		apellido2Filter.setField("second_name");
		apellido2Filter.setOperator(OperatorEnum.ES_IGUAL);
		apellido2Filter.setValue("PEREZ");
		filters.add(apellido2Filter);

		// Filtro por nif
		FilterVO nifFilter = new FilterVO();
		nifFilter.setField("nif");
		nifFilter.setOperator(OperatorEnum.ES_IGUAL);
		nifFilter.setValue("12345678Z");
		filters.add(nifFilter);

		criteria.setFilters(filters);

		List<TerceroValidadoVO> findByCriteria = getTerceroDao().findByCriteria(
				criteria);

		Assert.assertNotNull(findByCriteria);
		Assert.assertEquals(1, findByCriteria.size());

		TerceroValidadoFisicoVO tercero = (TerceroValidadoFisicoVO) findByCriteria
				.get(0);
		Assert.assertEquals("1", tercero.getId());
	}

	@Test
	public void findTerceroComienzaPor() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);

		FilterVO filter = new FilterVO();
		filter.setField("surname");
		filter.setOperator(OperatorEnum.COMIENZA_POR);
		filter.setValue("P");

		List<FilterVO> filters = new ArrayList<FilterVO>();
		filters.add(filter);
		criteria.setFilters(filters);

		List<TerceroValidadoVO> findByCriteria = getTerceroDao().findByCriteria(
				criteria);

		Assert.assertNotNull(findByCriteria);
		Assert.assertEquals(1, findByCriteria.size());

		TerceroValidadoFisicoVO tercero = (TerceroValidadoFisicoVO) findByCriteria
				.get(0);
		Assert.assertEquals("1", tercero.getId());
		Assert.assertEquals("PABLO", tercero.getNombre());
	}

	@Test
	public void findTerceroContiene() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);

		FilterVO filter = new FilterVO();
		filter.setField("surname");
		filter.setOperator(OperatorEnum.CONTIENE);
		filter.setValue("B");

		List<FilterVO> filters = new ArrayList<FilterVO>();
		filters.add(filter);
		criteria.setFilters(filters);

		List<TerceroValidadoVO> findByCriteria = getTerceroDao().findByCriteria(
				criteria);

		Assert.assertNotNull(findByCriteria);
		Assert.assertEquals(1, findByCriteria.size());

		TerceroValidadoFisicoVO tercero = (TerceroValidadoFisicoVO) findByCriteria
				.get(0);
		Assert.assertEquals("1", tercero.getId());
		Assert.assertEquals("PABLO", tercero.getNombre());

		filter.setValue("J");

		filters.clear();
		filters.add(filter);

		Assert.assertEquals(1, getTerceroDao().findByCriteria(criteria).size());
	}

	@Test
	public void findTerceroByCriteriaOrdering() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);
		criteria.setOrderBy("surname DESC");
		List<FilterVO> filters = new ArrayList<FilterVO>();
		FilterVO filter = new FilterVO();
		filter.setField("first_name");
		filter.setOperator(OperatorEnum.CONTIENE);
		filter.setValue("a");
		filters.add(filter);
		criteria.setFilters(filters);

		List<TerceroValidadoVO> findByCriteria = getTerceroDao().findByCriteria(
				criteria);
		Assert.assertNotNull(findByCriteria);
		Assert.assertEquals(2, findByCriteria.size());

		TerceroValidadoFisicoVO silvio = (TerceroValidadoFisicoVO) findByCriteria
				.get(0);
		Assert.assertNotNull(silvio);
		Assert.assertEquals("Silvio", silvio.getNombre());
		TerceroValidadoFisicoVO juan = (TerceroValidadoFisicoVO) findByCriteria
				.get(1);
		Assert.assertEquals("Juan", juan.getNombre());
	}

	@Test
	public void findTerceroByCriteriaWithWhere() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);
		criteria.setWhere("id > 2");
		List<FilterVO> filters = new ArrayList<FilterVO>();
		FilterVO filter = new FilterVO();
		filter.setField("first_name");
		filter.setOperator(OperatorEnum.CONTIENE);
		filter.setValue("i");
		filters.add(filter);
		criteria.setFilters(filters);

		List<TerceroValidadoVO> findByCriteria = getTerceroDao().findByCriteria(
				criteria);
		Assert.assertNotNull(findByCriteria);
		Assert.assertEquals(1, findByCriteria.size());

	}

	@Test
	public void findTerceroJuridicoByCriteria() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.JURIDICO);
		List<FilterVO> filters = new ArrayList<FilterVO>();
		FilterVO cifFilter = new FilterVO();
		cifFilter.setField("cif");
		cifFilter.setOperator(OperatorEnum.ES_IGUAL);
		cifFilter.setValue("A28855260");
		filters.add(cifFilter);
		criteria.setFilters(filters);

		List<TerceroValidadoVO> findByCriteria = getTerceroDao()
				.findByCriteria(criteria);

		Assert.assertNotNull(findByCriteria);
		Assert.assertEquals(1, findByCriteria.size());
	}

	@Test
	public void countByEmptyCriteria() {
		CriteriaVO criteria = new CriteriaVO();
		criteria.setType(SearchType.FISICO);

		Integer numberOfTercerosFisicos = getTerceroDao().countByCriteria(
				criteria);
		Assert.assertEquals(Integer.valueOf(3), numberOfTercerosFisicos);

		criteria.setType(SearchType.JURIDICO);
		Integer numberOfTercerosJuridicos = getTerceroDao().countByCriteria(
				criteria);
		Assert.assertEquals(Integer.valueOf(1), numberOfTercerosJuridicos);

	}

	public TerceroDao getTerceroDao() {
		return terceroDao;
	}

	public void setTerceroDao(TerceroDao terceroDao) {
		this.terceroDao = terceroDao;
	}

	@Autowired
	protected TerceroDao terceroDao;
}
