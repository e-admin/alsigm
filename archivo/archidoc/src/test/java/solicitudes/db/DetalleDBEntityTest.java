package solicitudes.db;

import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class DetalleDBEntityTest extends ArchidocDBBaseTest {

	/* (non-Javadoc)
	 * @see common.manager.ArchidocBaseTest#getDAO()
	 */
	@Override
	protected IDetalleDBEntity getDAO() {
		return getManager().getDetalleDBEntity();
	}

	@Test
	public void getCountDetallesByEstado(){
		int numDetalles = getDAO().getCountDetallesByEstado(idUdoc,estadosSolicitud);

		Assert.assertEquals(2, numDetalles);
	}
}
