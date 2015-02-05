package fondos.db;

import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocDBBaseTest;
import fondos.vos.ElementoCuadroClasificacionVO;

public class ElementoCuadroClasificacionDBEntityImplBaseTest extends
		ArchidocDBBaseTest {

	@Override
	protected IElementoCuadroClasificacionDbEntity getDAO() {
		return getManager().getElementoCuadroClasificacionDbEntity();
	}

	@Test
	public void getElementoCFXCodigoYTipo(){
		ElementoCuadroClasificacionVO elemento = getDAO().getElementoCFXCodigoYTipo(codigoElemento, tipoElemento);
		Assert.assertNull(elemento);
	}
}
