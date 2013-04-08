package descripcion.model.eventos.documentos;

import java.util.Locale;

import org.apache.log4j.Logger;
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
import descripcion.model.xml.card.TipoAccion;
import descripcion.model.xml.card.Valor;

public class EventoActualizarFechaFinalExp implements IEventoFicha {

	private static final Logger logger = Logger
			.getLogger(EventoActualizarFechaFinalExp.class);

	public ActionErrors executeEvent(int tipoEvento, Ficha ficha, Locale locale)
			throws EventoFichaException {

		if (logger.isInfoEnabled())
			logger.info("executeEvent: tipoEvento=[" + tipoEvento + "]");

		ActionErrors errors = new ActionErrors();
		CustomDate customDateFechaDoc = null;

		String idFechaDoc = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getIdFechaDoc();
		String idFechaFinal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaFinal();
		ElementoEtiquetaDato elemFechaFinal = ficha
				.findElementoEtiquetaDatoById(idFechaFinal);

		if (elemFechaFinal != null) {
			customDateFechaDoc = new CustomDate(elemFechaFinal.getValor(0)
					.getFormato(), elemFechaFinal.getValor(0).getAnio(),
					elemFechaFinal.getValor(0).getMes(), elemFechaFinal
							.getValor(0).getDia(), elemFechaFinal.getValor(0)
							.getSiglo(), elemFechaFinal.getValor(0)
							.getSeparador(), elemFechaFinal.getValor(0)
							.getCalificador());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Id Campo Fecha Documento Fisico: " + idFechaDoc);
		}

		boolean idsCorrectos = true;

		if (StringUtils.isBlank(idFechaDoc)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/Fecha_Documento");
		}

		if (idsCorrectos) {
			ElementoEtiquetaDato elementoEtiquetaDato = ficha
					.findElementoEtiquetaDatoById(idFechaDoc);
			if (elementoEtiquetaDato != null) {
				Valor[] valores = elementoEtiquetaDato.getValores();
				if (valores != null && valores.length > 0) {
					for (int i = 0; i < valores.length; i++) {
						Valor valor = (Valor) valores[i];
						CustomDate customDateFecha = new CustomDate(
								valor.getFormato(), valor.getAnio(),
								valor.getMes(), valor.getDia(),
								valor.getSiglo(), valor.getSeparador(),
								valor.getCalificador());

						if (customDateFechaDoc != null
								&& customDateFecha != null
								&& DateUtils.isDate(DateUtils
										.formatDate(customDateFecha.getDate()))) {
							if (DateUtils.isFechaMayor(
									customDateFecha.getDate(),
									customDateFechaDoc.getDate())) {
								customDateFechaDoc = customDateFecha;
							}
						} else {
							errors.add(
									Constants.ERROR_DATE,
									new ActionError(Constants.ERROR_DATE,
											Messages.getString(
													"archigest.archivo.fecha",
													locale)));
						}
					}
				}
			}
			// Se actualiza fecha final del expediente con la mayor de los
			// documentos siempre que tengan el mismo formato
			if (elemFechaFinal.getValor(0).getFormato()
					.equals(customDateFechaDoc.getFormat())) {
				Valor valorFecha = elemFechaFinal.getValor(0);
				valorFecha.setAccion(TipoAccion.MODIFICAR);
				if (valorFecha.getValorAnterior() == null) {
					valorFecha.setValorAnterior(valorFecha);
				}
				valorFecha.setDia(customDateFechaDoc.getDay());
				valorFecha.setMes(customDateFechaDoc.getMonth());
				valorFecha.setAnio(customDateFechaDoc.getYear());
				valorFecha.setSiglo(customDateFechaDoc.getCentury());
				valorFecha.setCalificador(customDateFechaDoc.getQualifier());
				valorFecha.setValor(customDateFechaDoc.getValue());
			} else {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Messages.getString(
										"errors.ficha.evento.fecha.final.formato.incorrecto",
										locale)));
			}
		} else {
			throw new EventoFichaException(
					"En la configuración falta alguno de los identificadores de los campos de documentos fisicos");
		}

		if (logger.isDebugEnabled())
			logger.debug("executeEvent: ficha=" + Constants.NEWLINE
					+ ficha.toString());

		return errors;
	}
}