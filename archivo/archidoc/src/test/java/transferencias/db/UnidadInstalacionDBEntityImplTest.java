package transferencias.db;

import junit.framework.Assert;

import org.junit.Test;

import transferencias.vos.UnidadInstalacionVO;

import common.manager.ArchidocDBBaseTest;

public class UnidadInstalacionDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IUnidadInstalacionDBEntity getDAO() {
		return getManager().getUnidadInstalacionDBEntity();
	}

	@Test
	public void getUIByIdRelEntregaYOrden(){
		UnidadInstalacionVO ui = getDAO().getUIByIdRelEntregaYOrden(idRelEntrega,ultimoOrden);
		Assert.assertNotNull(ui);
		Assert.assertEquals(id1, ui.getId());

		UnidadInstalacionVO ui2 = getDAO().getUIByIdRelEntregaYOrden(idRelEntrega,25);
		Assert.assertNull(ui2);

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
