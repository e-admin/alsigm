package transferencias.db;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import transferencias.vos.DetallePrevisionVO;

import common.manager.ArchidocDBBaseTest;

/**
 * Clase con los metodos encargados de recuperar y almacenar en la base de datos
 * la informacion referente a un detalle de prevision
 *
 */
public class DetallePrevisionDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IDetallePrevisionDBEntity getDAO() {
		return getManager().getDetallePrevisionDBEntity();
	}

	protected void getDetallesPrevision(String qual) {


	}

	protected void getDetallePrevision(String qual) {
	}

	public void fetchRowsByCodigoPrevision(String codigoPrevision) {
	}

	public void insertRow(final DetallePrevisionVO detallePrevision) {
	}

	public void updateRow(final DetallePrevisionVO detallePrevision) {
	}

	public void selectRow(String prevision, int numeroDetalle) {
	}

	public void selectRow(String idDetalle) {
	}

	public void dropRow(final String prevision, final String idDetalle) {
	}

	public void selectLastRowByProcedimiento(String procedimiento) {
	}

	public void selectRowsWithoutRelacion(String prevision) {
	}

	public void selectRowsWithoutRelacionXFormato(String prevision,
			String idFormato) {
	}

	public void selectLastRowBySerie(String serie) {
	}

	public void numeroDetallesPrevision(final String idPrevision) {
	}

	public void incNRelacionesAsociadasADetalle(final String idDetalle) {
	}

	public void decNRelacionesAsociadasADetalle(final String idDetalle) {
	}

	public void checkAllDetallesPrevisionCerrados(final String idPrevision) {
	}

	public void cerrarDetalle(String idDetalle) {
	}

	public void getUInstXUdoc(List idsUnidadesDocumentales) {
	}

	public void getUInstXUdoc(List idsUnidadesDocumentales,
			List idsUnidadesExcluir) {
	}

	@Test
	public void getDetallePrestamoVO() {
		DetallePrevisionVO detalleMock = getMockDetallePrevisionVO();

		DetallePrevisionVO detalleBD = getDAO().getDetallePrevisionVO(detalleMock);

		Assert.assertNotNull(detalleBD);
		Assert.assertEquals(detalleMock.getId(), detalleBD.getId());
		Assert.assertEquals(detalleMock.getNombreSistProductor(), detalleBD.getNombreSistProductor());

	}

}