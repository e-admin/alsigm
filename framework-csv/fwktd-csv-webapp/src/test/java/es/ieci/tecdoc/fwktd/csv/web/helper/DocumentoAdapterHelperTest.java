package es.ieci.tecdoc.fwktd.csv.web.helper;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.web.vo.InfoDocumentoVO;

/**
 * Test de la clase de utilidad para la adaptación de objetos con la información
 * del documento desde el fwktd-csv.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoAdapterHelperTest {

	@Test
	public void testGetInfoDocumentoVO() {

		InfoDocumentoCSV infoDocumentoCSV = null;
		InfoDocumentoVO infoDocumentoVO = DocumentoAdapterHelper
				.getInfoDocumentoVO(infoDocumentoCSV, Locale.getDefault());
		Assert.assertNull("El objeto debería ser nulo", infoDocumentoVO);

		infoDocumentoCSV = new InfoDocumentoCSV();
		infoDocumentoCSV.setId("1");
		infoDocumentoCSV.setNombre("documento.txt");
		infoDocumentoCSV.setTipoMime("text/plain");
		infoDocumentoCSV.setFechaCreacion(new Date());
		infoDocumentoCSV.setFechaCaducidad(new Date());
		infoDocumentoCSV.setCodigoAplicacion("COD_APP");
		infoDocumentoCSV.setNombreAplicacion("Aplicación");
		infoDocumentoCSV.setDisponible(true);
		infoDocumentoCSV.setCsv("CSV000");
		infoDocumentoCSV.setFechaCSV(new Date());

		infoDocumentoVO = DocumentoAdapterHelper.getInfoDocumentoVO(
				infoDocumentoCSV, Locale.getDefault());
		assertEquals(infoDocumentoCSV, infoDocumentoVO);
	}

	protected void assertEquals(InfoDocumentoCSV infoDocumentoCSV,
			InfoDocumentoVO infoDocumentoVO) {

		Assert.assertEquals(infoDocumentoCSV.getId(),
				infoDocumentoVO.getId());
		Assert.assertEquals(infoDocumentoCSV.getNombre(),
				infoDocumentoVO.getNombre());
		Assert.assertEquals(infoDocumentoCSV.getTipoMime(),
				infoDocumentoVO.getTipoMime());
		Assert.assertEquals(infoDocumentoCSV.getFechaCreacion().toString(),
				infoDocumentoVO.getFechaCreacion().toString());
		Assert.assertEquals(infoDocumentoCSV.getFechaCaducidad().toString(),
				infoDocumentoVO.getFechaCaducidad().toString());
		Assert.assertEquals(infoDocumentoCSV.getCodigoAplicacion(),
				infoDocumentoVO.getCodigoAplicacion());
		Assert.assertEquals(infoDocumentoCSV.getNombreAplicacion(),
				infoDocumentoVO.getNombreAplicacion());
		Assert.assertEquals(infoDocumentoCSV.isDisponible(),
				infoDocumentoVO.isDisponible());
		Assert.assertEquals(infoDocumentoCSV.getCsv(), infoDocumentoVO.getCsv());
		Assert.assertEquals(infoDocumentoCSV.getFechaCSV().toString(),
				infoDocumentoVO.getFechaCSV().toString());
	}
}
