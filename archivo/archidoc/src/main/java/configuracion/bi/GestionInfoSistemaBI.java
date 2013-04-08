package configuracion.bi;

import common.reports.PdfReportPageEventHelper;
import common.vos.DatosGeograficosVO;
import common.vos.PaisesVO;

public interface GestionInfoSistemaBI {

	/**
	 * Obtiene los Datos Geográficos por defecto para los emplazamientos.
	 * 
	 * @return DatosGeograficosVO
	 */
	public abstract DatosGeograficosVO getDatosGeograficosDefecto();

	public abstract PaisesVO getPaises();

	PdfReportPageEventHelper getManejadorIText();
}
