package solicitudes.db;

import junit.framework.Assert;

import org.junit.Test;

import solicitudes.consultas.ConsultasConstants;

import common.manager.ArchidocDBBaseTest;

public class DetalleDBEntityTest extends ArchidocDBBaseTest {

	/*
    * (non-Javadoc)
    *
	 * @see common.manager.ArchidocBaseTest#getDAO()
	 */
	@Override
	protected IDetalleDBEntity getDAO() {
		return getManager().getDetalleDBEntity();
	}

	@Test
	public void getCountDetallesByEstado() {
		int numDetalles = getDAO().getCountDetallesByEstado(idUdoc,
				estadosSolicitud);

		Assert.assertEquals(2, numDetalles);
	}

	@Test
	public void cambiarEstadosASolicitadoDetallesAutorizados() {
		getDAO().cambiarEstadosASolicitadoDetallesAutorizados(id1,
				ConsultasConstants.TIPO_SOLICITUD_CONSULTA,
				new int[] { ConsultasConstants.ESTADO_DETALLE_AUTORIZADA },
				ConsultasConstants.ESTADO_DETALLE_PENDIENTE);
	}
}
