package es.ieci.tecdoc.fwktd.csv.ws.helper;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import es.ieci.tecdoc.fwktd.csv.ws.service.DescripcionI18N;
import es.ieci.tecdoc.fwktd.csv.ws.service.DocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.InfoDocumentoCSVForm;
import es.ieci.tecdoc.fwktd.util.date.DateUtils;

/**
 * Utilidad para la adaptación de objetos con la información del documento desde
 * el fwktd-csv-api.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoAdapterHelper {

	public static es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm getCoreInfoDocumentoCSVForm(InfoDocumentoCSVForm infoDocumentoCSVForm) {

		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm coreInfoDocumentoCSVForm = null;

		if (infoDocumentoCSVForm != null) {
			coreInfoDocumentoCSVForm = new es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm();
			coreInfoDocumentoCSVForm.setNombre(infoDocumentoCSVForm.getNombre());
		    coreInfoDocumentoCSVForm.setTipoMime(infoDocumentoCSVForm.getTipoMime());
		    coreInfoDocumentoCSVForm.setFechaCreacion(DateUtils.toDate(infoDocumentoCSVForm.getFechaCreacion()));
		    coreInfoDocumentoCSVForm.setFechaCaducidad(DateUtils.toDate(infoDocumentoCSVForm.getFechaCaducidad()));
		    coreInfoDocumentoCSVForm.setCodigoAplicacion(infoDocumentoCSVForm.getCodigoAplicacion());
		    coreInfoDocumentoCSVForm.setDisponible(infoDocumentoCSVForm.isDisponible());

		    addDescripciones(coreInfoDocumentoCSVForm, infoDocumentoCSVForm);
		}

		return coreInfoDocumentoCSVForm;
	}

	public static InfoDocumentoCSV getInfoDocumentoCSV(es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV coreInfoDocumentoCSV) {

		InfoDocumentoCSV infoDocumentoCSV = null;

		if (coreInfoDocumentoCSV != null) {
			infoDocumentoCSV = new InfoDocumentoCSV();
			infoDocumentoCSV.setId(coreInfoDocumentoCSV.getId());
			infoDocumentoCSV.setNombre(coreInfoDocumentoCSV.getNombre());
		    infoDocumentoCSV.setTipoMime(coreInfoDocumentoCSV.getTipoMime());
		    infoDocumentoCSV.setFechaCreacion(DateUtils.toXMLGregorianCalendar(coreInfoDocumentoCSV.getFechaCreacion()));
		    infoDocumentoCSV.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(coreInfoDocumentoCSV.getFechaCaducidad()));
		    infoDocumentoCSV.setCodigoAplicacion(coreInfoDocumentoCSV.getCodigoAplicacion());
		    infoDocumentoCSV.setDisponible(coreInfoDocumentoCSV.isDisponible());
		    infoDocumentoCSV.setNombreAplicacion(coreInfoDocumentoCSV.getNombreAplicacion());
		    infoDocumentoCSV.setCsv(coreInfoDocumentoCSV.getCsv());
		    infoDocumentoCSV.setFechaCSV(DateUtils.toXMLGregorianCalendar(coreInfoDocumentoCSV.getFechaCSV()));

		    addDescripciones(infoDocumentoCSV, coreInfoDocumentoCSV);
		}

		return infoDocumentoCSV;
	}

	public static es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV getCoreInfoDocumentoCSV(InfoDocumentoCSV infoDocumentoCSV) {

		es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV coreInfoDocumentoCSV = null;

		if (infoDocumentoCSV != null) {
			coreInfoDocumentoCSV = new es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSV();
			coreInfoDocumentoCSV.setId(infoDocumentoCSV.getId());
			coreInfoDocumentoCSV.setNombre(infoDocumentoCSV.getNombre());
		    coreInfoDocumentoCSV.setTipoMime(infoDocumentoCSV.getTipoMime());
		    coreInfoDocumentoCSV.setFechaCreacion(DateUtils.toDate(infoDocumentoCSV.getFechaCreacion()));
		    coreInfoDocumentoCSV.setFechaCaducidad(DateUtils.toDate(infoDocumentoCSV.getFechaCaducidad()));
		    coreInfoDocumentoCSV.setCodigoAplicacion(infoDocumentoCSV.getCodigoAplicacion());
		    coreInfoDocumentoCSV.setDisponible(infoDocumentoCSV.isDisponible());
		    coreInfoDocumentoCSV.setNombreAplicacion(infoDocumentoCSV.getNombreAplicacion());
		    coreInfoDocumentoCSV.setCsv(infoDocumentoCSV.getCsv());
		    coreInfoDocumentoCSV.setFechaCSV(DateUtils.toDate(infoDocumentoCSV.getFechaCSV()));

		    addDescripciones(coreInfoDocumentoCSV, infoDocumentoCSV);
		}

		return coreInfoDocumentoCSV;
	}

	public static DocumentoCSV getDocumentoCSV(es.ieci.tecdoc.fwktd.csv.core.vo.DocumentoCSV coreDocumentoCSV) {

		DocumentoCSV documentoCSV = null;

		if (coreDocumentoCSV != null) {
			documentoCSV = new DocumentoCSV();
			documentoCSV.setId(coreDocumentoCSV.getId());
			documentoCSV.setNombre(coreDocumentoCSV.getNombre());
		    documentoCSV.setTipoMime(coreDocumentoCSV.getTipoMime());
		    documentoCSV.setFechaCreacion(DateUtils.toXMLGregorianCalendar(coreDocumentoCSV.getFechaCreacion()));
		    documentoCSV.setFechaCaducidad(DateUtils.toXMLGregorianCalendar(coreDocumentoCSV.getFechaCaducidad()));
		    documentoCSV.setCodigoAplicacion(coreDocumentoCSV.getCodigoAplicacion());
		    documentoCSV.setDisponible(coreDocumentoCSV.isDisponible());
		    documentoCSV.setNombreAplicacion(coreDocumentoCSV.getNombreAplicacion());
		    documentoCSV.setCsv(coreDocumentoCSV.getCsv());
		    documentoCSV.setFechaCSV(DateUtils.toXMLGregorianCalendar(coreDocumentoCSV.getFechaCSV()));
		    documentoCSV.setContenido(coreDocumentoCSV.getContenido());

		    addDescripciones(documentoCSV, coreDocumentoCSV);
		}

		return documentoCSV;
	}

	protected static void addDescripciones(
			es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm coreInfoDocumentoCSVForm,
			InfoDocumentoCSVForm infoDocumentoCSVForm) {

		List<DescripcionI18N> descripciones = infoDocumentoCSVForm.getDescripcionesI18N();
		if (CollectionUtils.isNotEmpty(descripciones)) {
			for (DescripcionI18N descripcion : descripciones) {
				if (descripcion != null) {
					Locale locale = null;
					if (StringUtils.hasText(descripcion.getLocale())
							&& !"default".equalsIgnoreCase(descripcion.getLocale())) {
						locale = StringUtils.parseLocaleString(descripcion.getLocale());
					}
					coreInfoDocumentoCSVForm.addDescripcion(locale, descripcion.getDescripcion());
				}
			}
		}
	}

	protected static void addDescripciones(
			InfoDocumentoCSVForm infoDocumentoCSVForm,
			es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm coreInfoDocumentoCSVForm) {

		Map<String, String> descripciones = coreInfoDocumentoCSVForm.getDescripciones();
		if (MapUtils.isNotEmpty(descripciones)) {
			for (String key : descripciones.keySet()) {

				DescripcionI18N descripcionI18N = new DescripcionI18N();
				descripcionI18N.setLocale(key);
				descripcionI18N.setDescripcion(descripciones.get(key));

				infoDocumentoCSVForm.getDescripcionesI18N().add(descripcionI18N);
			}
		}
	}
}
