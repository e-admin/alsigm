package transferencias.db;

import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class UIReeaCRDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IUIReeaCRDBEntity getDAO() {
		return getManager().getUiReeaCRDBEntity();
	}

	@Test
	public void fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(){
		getDAO().fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(signatura, idArchivo, idRelEntrega);
	}

	@Test
	public void fetchRowBySignaturaEnRENoValidadaOtraRelacion(){
		getDAO().fetchRowBySignaturaEnRENoValidadaOtraRelacion(signatura, idRelEntrega);
	}

}
