package es.ieci.tecdoc.fwktd.csv.web.helper;

import java.util.Locale;

import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.web.vo.InfoDocumentoVO;

/**
 * Utilidad para la adaptación de objetos con la información del documento desde
 * el fwktd-csv.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoAdapterHelper {

	/**
	 * Adapta la información del documento de fwktd-csv al VO de la aplicación.
	 *
	 * @param infoDocumentoCSV
	 *            Información del documento de fwktd-csv
	 * @return Información del documento.
	 */
	public static InfoDocumentoVO getInfoDocumentoVO(
			InfoDocumentoCSV infoDocumentoCSV, Locale locale) {

		InfoDocumentoVO infoDocumentoVO = null;

		if (infoDocumentoCSV != null) {
			infoDocumentoVO = new InfoDocumentoVO();
			infoDocumentoVO.setId(infoDocumentoCSV.getId());
			infoDocumentoVO.setNombre(infoDocumentoCSV.getNombre());
			infoDocumentoVO.setDescripcion(infoDocumentoCSV.getDescripcion(locale));
			infoDocumentoVO.setTipoMime(infoDocumentoCSV.getTipoMime());
			infoDocumentoVO.setFechaCreacion(infoDocumentoCSV.getFechaCreacion());
			infoDocumentoVO.setFechaCaducidad(infoDocumentoCSV.getFechaCaducidad());
			infoDocumentoVO.setCodigoAplicacion(infoDocumentoCSV.getCodigoAplicacion());
			infoDocumentoVO.setNombreAplicacion(infoDocumentoCSV.getNombreAplicacion());
			infoDocumentoVO.setDisponible(infoDocumentoCSV.isDisponible());
			infoDocumentoVO.setCsv(infoDocumentoCSV.getCsv());
			infoDocumentoVO.setFechaCSV(infoDocumentoCSV.getFechaCSV());
		}

		return infoDocumentoVO;
	}
}
