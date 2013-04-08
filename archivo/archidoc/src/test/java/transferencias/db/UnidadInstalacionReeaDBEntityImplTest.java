package transferencias.db;

import org.junit.Test;

import common.db.IDBEntity;
import common.manager.ArchidocDBBaseTest;

public class UnidadInstalacionReeaDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IUnidadInstalacionReeaDBEntity getDAO() {
		return getManager().getUnidadInstalacionReeaDBEntity();
	}

	@Test
	public void fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(){
		getDAO().fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(signatura, idArchivo, idRelEntrega);
	}

}
