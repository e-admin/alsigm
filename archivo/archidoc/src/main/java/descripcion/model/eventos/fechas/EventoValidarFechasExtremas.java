package descripcion.model.eventos.fechas;

import java.util.Locale;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.util.CustomDate;
import common.util.DateUtils;
import common.util.StringUtils;

import descripcion.model.eventos.EventoFichaException;
import descripcion.model.eventos.IEventoFicha;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.Ficha;

public class EventoValidarFechasExtremas implements IEventoFicha {

	private ActionErrors validateDateFields(String value, String field,
			Locale locale) {

		ActionErrors errors = new ActionErrors();

		if (!common.util.StringUtils.isEmpty(value)
				&& !NumberUtils.isNumber(value)) {
			errors.add(Constants.ERROR_DATE, new ActionError(
					Constants.ERROR_DATE, Messages.getString(field, locale)));
			return errors;
		}
		return errors;
	}

	public ActionErrors executeEvent(int tipoEvento, Ficha ficha, Locale locale)
			throws EventoFichaException {

		ActionErrors errors = new ActionErrors();

		String idFechaInicial = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaInicial();
		String idFechaFinal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaFinal();

		ElementoEtiquetaDato elemFechaInicial = ficha
				.findElementoEtiquetaDatoById(idFechaInicial);
		ElementoEtiquetaDato elemFechaFinal = ficha
				.findElementoEtiquetaDatoById(idFechaFinal);

		String fechaInicial = null;
		String fechaFinal = null;

		CustomDate customDateFechaInicial = null;
		CustomDate customDateFechaFinal = null;

		if (elemFechaInicial.getValores() != null
				&& elemFechaInicial.getValores().length > 0) {

			errors = validateDateFields(elemFechaInicial.getValor(0).getAnio(),
					"archigest.archivo.fechaInicial", locale);
			if (!errors.isEmpty())
				return errors;

			errors = validateDateFields(elemFechaInicial.getValor(0).getMes(),
					"archigest.archivo.fechaInicial", locale);
			if (!errors.isEmpty())
				return errors;

			errors = validateDateFields(elemFechaInicial.getValor(0).getDia(),
					"archigest.archivo.fechaInicial", locale);
			if (!errors.isEmpty())
				return errors;
		}

		if (elemFechaFinal.getValores() != null
				&& elemFechaFinal.getValores().length > 0) {

			errors = validateDateFields(elemFechaFinal.getValor(0).getAnio(),
					"archigest.archivo.fechaFinal", locale);
			if (!errors.isEmpty())
				return errors;

			errors = validateDateFields(elemFechaFinal.getValor(0).getMes(),
					"archigest.archivo.fechaFinal", locale);
			if (!errors.isEmpty())
				return errors;

			errors = validateDateFields(elemFechaFinal.getValor(0).getDia(),
					"archigest.archivo.fechaFinal", locale);
			if (!errors.isEmpty())
				return errors;
		}

		if (elemFechaInicial.getValores() != null
				&& elemFechaInicial.getValores().length > 0) {
			customDateFechaInicial = new CustomDate(elemFechaInicial
					.getValor(0).getFormato(), elemFechaInicial.getValor(0)
					.getAnio(), elemFechaInicial.getValor(0).getMes(),
					elemFechaInicial.getValor(0).getDia(), elemFechaInicial
							.getValor(0).getSiglo());
		}

		if (elemFechaFinal.getValores() != null
				&& elemFechaFinal.getValores().length > 0) {
			customDateFechaFinal = new CustomDate(elemFechaFinal.getValor(0)
					.getFormato(), elemFechaFinal.getValor(0).getAnio(),
					elemFechaFinal.getValor(0).getMes(), elemFechaFinal
							.getValor(0).getDia(), elemFechaFinal.getValor(0)
							.getSiglo());
		}

		if (customDateFechaInicial != null) {
			fechaInicial = DateUtils.formatDate(customDateFechaInicial
					.getMinDate());
		}

		if (customDateFechaFinal != null) {
			fechaFinal = DateUtils
					.formatDate(customDateFechaFinal.getMinDate());
		}

		if (!StringUtils.isEmpty(fechaInicial)
				&& !StringUtils.isEmpty(fechaFinal)) {
			if (DateUtils.isDate(fechaInicial)) {
				if (DateUtils.isDate(fechaFinal)) {
					if (!DateUtils.isFechaMenorOIgual(
							DateUtils.getDate(fechaInicial),
							DateUtils.getDate(fechaFinal))) {
						errors.add(
								Constants.ERROR_DATE_BEFORE,
								new ActionError(
										Constants.ERROR_DATE_BEFORE,
										Messages.getString(
												"archigest.archivo.fechaInicial",
												locale), Messages.getString(
												"archigest.archivo.fechaFinal",
												locale)));
					}
				} else {
					errors.add(
							Constants.ERROR_DATE,
							new ActionError(Constants.ERROR_DATE, Messages
									.getString("archigest.archivo.fechaFinal",
											locale)));
				}
			} else {
				errors.add(
						Constants.ERROR_DATE,
						new ActionError(Constants.ERROR_DATE, Messages
								.getString("archigest.archivo.fechaInicial",
										locale)));
			}
		} else {
			if (!StringUtils.isEmpty(fechaInicial)
					&& !DateUtils.isDate(fechaInicial)) {
				errors.add(
						Constants.ERROR_DATE,
						new ActionError(Constants.ERROR_DATE, Messages
								.getString("archigest.archivo.fechaInicial",
										locale)));
			}
			if (!StringUtils.isEmpty(fechaFinal)
					&& !DateUtils.isDate(fechaFinal)) {
				errors.add(
						Constants.ERROR_DATE,
						new ActionError(Constants.ERROR_DATE, Messages
								.getString("archigest.archivo.fechaFinal",
										locale)));
			}
		}
		return errors;
	}
}
