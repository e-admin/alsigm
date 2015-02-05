package fondos.db;

import org.junit.Test;
import org.springframework.util.Assert;

import common.manager.ArchidocDBBaseTest;

import fondos.vos.INivelCFVO;

public class NivelCFDBEntityImplTest extends ArchidocDBBaseTest {

	/* (non-Javadoc)
	 * @see common.manager.ArchidocBaseTest#getDAO()
	 */
	@Override
	protected INivelCFDBEntity getDAO() {
		return getManager().getNivelCFDBEntity();
	}

	@Test
	public void getNivelCFByName(){
		INivelCFVO nivelCF = getDAO().getNivelCFByName(nombreNivel);

		Assert.notNull(nivelCF);
		Assert.isTrue(nombreNivel.equals(nivelCF.getNombre()));
	}

	@Test
	public void getNivelCFByNameAndTipo(){
		INivelCFVO nivelCF = getDAO().getNivelCFByNameAndTipo(nombreNivel, tipoNivel);

		Assert.notNull(nivelCF);
		Assert.isTrue(nombreNivel.equals(nivelCF.getNombre()));
		Assert.isTrue(tipoNivel == nivelCF.getTipo());
	}

}
