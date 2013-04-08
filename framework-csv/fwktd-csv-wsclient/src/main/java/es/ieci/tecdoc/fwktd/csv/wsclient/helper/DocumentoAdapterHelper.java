package es.ieci.tecdoc.fwktd.csv.wsclient.helper;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.DescripcionI18N;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;


/**
 * Utilidad para la adaptación de objetos con la información del documento desde
 * el fwktd-csv-ws.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoAdapterHelper {

	public static es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm getWSInfoDocumentoCSVForm(
			InfoDocumentoCSVForm infoDocumentoCSVForm) {

		es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm wsInfoDocumentoCSVForm = null;

		if (infoDocumentoCSVForm != null) {
			wsInfoDocumentoCSVForm = new es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm();
			wsInfoDocumentoCSVForm.setNombre(infoDocumentoCSVForm.getNombre());
			wsInfoDocumentoCSVForm.setTipoMime(infoDocumentoCSVForm
					.getTipoMime());
			wsInfoDocumentoCSVForm.setFechaCreacion(DateUtils
					.toXMLGregorianCalendar(infoDocumentoCSVForm
							.getFechaCreacion()));
			wsInfoDocumentoCSVForm.setFechaCaducidad(DateUtils
					.toXMLGregorianCalendar(infoDocumentoCSVForm
							.getFechaCaducidad()));
			wsInfoDocumentoCSVForm.setCodigoAplicacion(infoDocumentoCSVForm
					.getCodigoAplicacion());
			wsInfoDocumentoCSVForm.setDisponible(infoDocumentoCSVForm
					.isDisponible());

			addDescripciones(wsInfoDocumentoCSVForm, infoDocumentoCSVForm);
		}

		return wsInfoDocumentoCSVForm;
	}

	public static InfoDocumentoCSV getInfoDocumentoCSV(
			es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV wsInfoDocumentoCSV) {

		InfoDocumentoCSV infoDocumentoCSV = null;

		if (wsInfoDocumentoCSV != null) {
			infoDocumentoCSV = new InfoDocumentoCSV();
			infoDocumentoCSV.setId(wsInfoDocumentoCSV.getId());
			infoDocumentoCSV.setNombre(wsInfoDocumentoCSV.getNombre());
			infoDocumentoCSV.setTipoMime(wsInfoDocumentoCSV.getTipoMime());
			infoDocumentoCSV.setFechaCreacion(DateUtils
					.toDate(wsInfoDocumentoCSV.getFechaCreacion()));
			infoDocumentoCSV.setFechaCaducidad(DateUtils
					.toDate(wsInfoDocumentoCSV.getFechaCaducidad()));
			infoDocumentoCSV.setCodigoAplicacion(wsInfoDocumentoCSV
					.getCodigoAplicacion());
			infoDocumentoCSV.setDisponible(wsInfoDocumentoCSV.isDisponible());
			infoDocumentoCSV.setNombreAplicacion(wsInfoDocumentoCSV
					.getNombreAplicacion());
			infoDocumentoCSV.setCsv(wsInfoDocumentoCSV.getCsv());
			infoDocumentoCSV.setFechaCSV(DateUtils.toDate(wsInfoDocumentoCSV
					.getFechaCSV()));

			addDescripciones(infoDocumentoCSV, wsInfoDocumentoCSV);
		}

		return infoDocumentoCSV;
	}

	public static es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV getWSInfoDocumentoCSV(
			InfoDocumentoCSV infoDocumentoCSV) {

		es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV wsInfoDocumentoCSV = null;

		if (infoDocumentoCSV != null) {
			wsInfoDocumentoCSV = new es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV();
			wsInfoDocumentoCSV.setId(infoDocumentoCSV.getId());
			wsInfoDocumentoCSV.setNombre(infoDocumentoCSV.getNombre());
			wsInfoDocumentoCSV.setTipoMime(infoDocumentoCSV.getTipoMime());
			wsInfoDocumentoCSV
					.setFechaCreacion(DateUtils
							.toXMLGregorianCalendar(infoDocumentoCSV
									.getFechaCreacion()));
			wsInfoDocumentoCSV.setFechaCaducidad(DateUtils
					.toXMLGregorianCalendar(infoDocumentoCSV
							.getFechaCaducidad()));
			wsInfoDocumentoCSV.setCodigoAplicacion(infoDocumentoCSV
					.getCodigoAplicacion());
			wsInfoDocumentoCSV.setDisponible(infoDocumentoCSV.isDisponible());
			wsInfoDocumentoCSV.setNombreAplicacion(infoDocumentoCSV
					.getNombreAplicacion());
			wsInfoDocumentoCSV.setCsv(infoDocumentoCSV.getCsv());
			wsInfoDocumentoCSV.setFechaCSV(DateUtils
					.toXMLGregorianCalendar(infoDocumentoCSV.getFechaCSV()));

			addDescripciones(wsInfoDocumentoCSV, infoDocumentoCSV);
		}

		return wsInfoDocumentoCSV;
	}

	public static DocumentoCSV getDocumentoCSV(
			es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV wsDocumentoCSV) {

		DocumentoCSV documentoCSV = null;

		if (wsDocumentoCSV != null) {
			documentoCSV = new DocumentoCSV();
			documentoCSV.setId(wsDocumentoCSV.getId());
			documentoCSV.setNombre(wsDocumentoCSV.getNombre());
			documentoCSV.setTipoMime(wsDocumentoCSV.getTipoMime());
			documentoCSV.setFechaCreacion(DateUtils.toDate(wsDocumentoCSV
					.getFechaCreacion()));
			documentoCSV.setFechaCaducidad(DateUtils.toDate(wsDocumentoCSV
					.getFechaCaducidad()));
			documentoCSV.setCodigoAplicacion(wsDocumentoCSV
					.getCodigoAplicacion());
			documentoCSV.setDisponible(wsDocumentoCSV.isDisponible());
			documentoCSV.setNombreAplicacion(wsDocumentoCSV
					.getNombreAplicacion());
			documentoCSV.setCsv(wsDocumentoCSV.getCsv());
			documentoCSV.setFechaCSV(DateUtils.toDate(wsDocumentoCSV
					.getFechaCSV()));
			documentoCSV.setContenido(wsDocumentoCSV.getContenido());

			addDescripciones(documentoCSV, wsDocumentoCSV);
		}

		return documentoCSV;
	}

	protected static void addDescripciones(
			es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm wsInfoDocumentoCSVForm,
			InfoDocumentoCSVForm infoDocumentoCSVForm) {

		Map<String, String> descripciones = infoDocumentoCSVForm.getDescripciones();
		if (MapUtils.isNotEmpty(descripciones)) {
			for (String key : descripciones.keySet()) {

				DescripcionI18N descripcionI18N = new DescripcionI18N();
				descripcionI18N.setLocale(key);
				descripcionI18N.setDescripcion(descripciones.get(key));

				wsInfoDocumentoCSVForm.getDescripcionesI18N().add(descripcionI18N);
			}
		}
	}

	protected static void addDescripciones(
			InfoDocumentoCSVForm infoDocumentoCSVForm,
			es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm wsInfoDocumentoCSVForm) {

		List<DescripcionI18N> descripciones = wsInfoDocumentoCSVForm.getDescripcionesI18N();
		if (CollectionUtils.isNotEmpty(descripciones)) {
			for (DescripcionI18N descripcion : descripciones) {
				if (descripcion != null) {
					Locale locale = null;
					if (StringUtils.hasText(descripcion.getLocale())
							&& !"default".equalsIgnoreCase(descripcion.getLocale())) {
						locale = StringUtils.parseLocaleString(descripcion.getLocale());
					}
					infoDocumentoCSVForm.addDescripcion(locale, descripcion.getDescripcion());
				}
			}
		}
	}

}
