package fondos.db;

import org.junit.Test;
import org.springframework.util.Assert;

import common.manager.ArchidocDBBaseTest;

import fondos.vos.ProductorSerieVO;

//@DatasetLocation("/data/ProductorSerie-dataset.xml")
public class ProductorSerieDBEntityImplBaseTest extends ArchidocDBBaseTest {

	protected IProductorSerieDbEntity getDAO(){
		return getManager().getProductorSerieDbEntity();
	}

	@Test
	public void getProductorXIdSerieYNombreProductor(){

		ProductorSerieVO productorSerieVO = getDAO().getProductorXIdSerieYNombreProductor(idSerie, nombreDescriptor);

		Assert.notNull(productorSerieVO, "No se ha encontrado el productor de la serie");
		Assert.isTrue(idSerie.equals(productorSerieVO.getIdserie()), "El identificador de la serie no es válido");

	}

	@Test
	public void getProductorXId(){
		ProductorSerieVO productorSerieVO = getDAO().getProductorXId(idSerie, idDescriptor);
		Assert.notNull(productorSerieVO, "No se ha encontrado el productor de la serie");
		Assert.isTrue(idSerie.equals(productorSerieVO.getIdserie()), "El identificador de la serie no es válido");
	}

}
