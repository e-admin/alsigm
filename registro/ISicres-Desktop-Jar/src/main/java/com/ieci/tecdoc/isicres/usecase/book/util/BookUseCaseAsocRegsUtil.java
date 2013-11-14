/**
 * Clase con metodos auxiliares de BookUseCase para el tratamiento
 * de la funcionalidad de Asociacion de Registros
 */
package com.ieci.tecdoc.isicres.usecase.book.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrRegasoc;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.decoder.query.QueryFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderAsocSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.xml.AsocRegsFields;
import com.ieci.tecdoc.isicres.usecase.book.xml.AsocRegsResults;
import com.ieci.tecdoc.isicres.usecase.book.xml.AsocRegsSearchFields;

/**
 * @author 66575267
 *
 * @date 03/06/2009
 *
 */
public class BookUseCaseAsocRegsUtil implements Keys {

	/**
	 * Crea un criterio de busqueda a partir de los datos introducidos en la
	 * pantalla de busqueda de Asociacion de Registros
	 *
	 * @param useCaseConf
	 * @param regWhere
	 * @param locale
	 * @param dataBaseType
	 * @param bookId
	 * @param formatter
	 * @return
	 * @throws Exception
	 */
	public static AxSfQuery getAsocRegsSearchCriteria(UseCaseConf useCaseConf,
			String regWhere, Locale locale, String dataBaseType,
			Integer bookId, QueryFormat formatter) throws Exception {
		AsocRegsSearchFields asocRegsSearchFields = new AsocRegsSearchFields(
				locale, dataBaseType);
		List fieldSearch = asocRegsSearchFields.getResult();
		StringTokenizer tokens = new StringTokenizer(regWhere, ALMOHADILLA);

		AxSfQuery axsfQuery = new AxSfQuery();
		axsfQuery.setBookId(bookId);
		axsfQuery
				.setPageSize(Integer
						.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE)));

		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			StringTokenizer tokens1 = new StringTokenizer(token, PUNTO_COMA);
			while (tokens1.hasMoreTokens()) {
				String token1 = tokens1.nextToken();

				String type = null;
				String fieldId = null;
				String operator = null;
				Object value = null;

				for (int i = 0; i < fieldSearch.size(); i++) {
					AsocRegsFields asocRegsFields = (AsocRegsFields) fieldSearch
							.get(i);
					if (asocRegsFields.getFieldName().equals(token1)) {
						type = asocRegsFields.getFieldType().toString();
						fieldId = asocRegsFields.getFieldId();
						break;
					}

				}

				String operatorRegWhere = tokens1.nextToken();
				operator = translateOperator(operatorRegWhere, locale);

				String valueRegWhere = tokens1.nextToken();
				value = convertValue(valueRegWhere, operatorRegWhere, type,
						locale);

				AxSfQueryField axSfQueryField = new AxSfQueryField();
				axSfQueryField.setBookId(bookId);
				axSfQueryField.setFldId("fld" + fieldId);
				axSfQueryField.setOperator(operator);
				axSfQueryField.setValue(value);

				axsfQuery.addField(axSfQueryField);
			}

		}

		if (formatter.getSelectDef().getType() == 3) {
			axsfQuery.setSelectDefWhere2(formatter.getSelectDef().getWhere2());
		}

		return axsfQuery;
	}

	/**
	 * Comprueba si un registro tiene otros registros asociados. Ya sean
	 * primarios o secundarios
	 *
	 * @param useCaseConf
	 * @param folderId
	 * @param bookId
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static boolean isAsocReg(UseCaseConf useCaseConf, Integer folderId,
			Integer bookId) throws BookException, SessionException,
			ValidationException {
		return FolderAsocSession.isAsocRegsFdr(useCaseConf.getSessionID(), bookId,
				folderId.intValue(), useCaseConf.getEntidadId());
	}

	/**
	 * Comprobamos si el libro de registro introducido es valido. Es decir, si
	 * existe y esta abierto
	 *
	 * @param useCaseConf
	 * @param bookId
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static boolean isBookOpen(UseCaseConf useCaseConf, Integer bookId)
			throws BookException, SessionException, ValidationException {
		if (bookId != null) {
			List listBook = BookSession.getInOutBooksOpen(useCaseConf
					.getSessionID(), useCaseConf.getLocale(), useCaseConf
					.getEntidadId());

			if (listBook != null && !listBook.isEmpty()) {
				for (Iterator iterator = listBook.iterator(); iterator
						.hasNext();) {
					// ScrRegstate book = (ScrRegstate) iterator.next();
					// if (book.getIdocarchhdr().getId().intValue() ==
					// bookId.intValue()) {
					// return true;
					// }

					ScrRegStateByLanguage book = (ScrRegStateByLanguage) iterator
							.next();
					if (book.getIdocarchhdrId().intValue() == bookId.intValue()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Dada una lista de registros, eliminamos de ella los registros que tienen
	 * registros asociados. Ya sean primarios o secuandarios.
	 *
	 * @param queryResults
	 * @param useCaseConf
	 * @param bookId
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static AxSfQueryResults filterRegsResultsByAsocRegs(
			AxSfQueryResults queryResults, UseCaseConf useCaseConf,
			Integer bookId) throws BookException, SessionException,
			ValidationException {

		Collection resultsQueryResults = queryResults.getResults();
		// int size = queryResults.getTotalQuerySize();

		for (Iterator iterator = resultsQueryResults.iterator(); iterator
				.hasNext();) {
			AxSf axSf = (AxSf) iterator.next();

			boolean result = FolderAsocSession.isAsocRegsFdr(useCaseConf
					.getSessionID(), bookId, ((Integer) axSf
					.getAttributeValue("fdrid")).intValue(), useCaseConf
					.getEntidadId());

			if (result) {
				iterator.remove();
				// size--;
			}
		}

		// if (queryResults.getTotalQuerySize() > size) {
		// queryResults.setTotalQuerySize(size);
		// queryResults.setResults(resultsQueryResults);
		// }

		return queryResults;
	}

	/**
	 * Eliminamos de la lista de resultados el registro con el que estamos
	 * trabajando
	 *
	 * @param listaRegs
	 * @param bookId
	 * @param folderId
	 * @return
	 */
	public static List filterRegsResultByCurrent(List listaRegs,
			Integer bookId, Integer folderId) {
		if ((listaRegs != null) && (!listaRegs.isEmpty())) {
			for (Iterator iterator = listaRegs.iterator(); iterator.hasNext();) {
				AsocRegsResults asocRegsResults = (AsocRegsResults) iterator
						.next();

				if ((asocRegsResults.getBookId().intValue() == bookId
						.intValue())
						&& (asocRegsResults.getFolderId().intValue() == folderId
								.intValue())) {
					iterator.remove();
				}
			}
		}

		return listaRegs;

	}

	/**
	 * Transformamos una lista de registros en otra lista con los datos
	 * estrictamente necesarios para mostrar en la pantalla de resultados de la
	 * busqueda de registros asociados.
	 *
	 * @param folderList
	 * @param bookId
	 * @param locale
	 * @return
	 */
	public static List getAsocRegsResults(Collection folderList,
			Integer bookId, Locale locale) {

		List result = new ArrayList();

		if (folderList != null && !folderList.isEmpty()) {
			SimpleDateFormat longFormatter = new SimpleDateFormat(RBUtil
					.getInstance(locale).getProperty(I18N_DATE_LONGFORMAT));

			for (Iterator iterator = folderList.iterator(); iterator.hasNext();) {
				AxSf axSf = (AxSf) iterator.next();

				Integer folderId = new Integer(axSf.getAttributeValue("fdrid").toString());
				String folderNumber = (String) axSf.getAttributeValue("fld1");
				String folderDate = longFormatter.format((Date) axSf
						.getAttributeValue(AxSf.FLD2_FIELD));
				String summary = "";

				if (axSf instanceof AxSfIn) {
					summary = (String) axSf.getAttributeValue("fld17");
				} else if (axSf instanceof AxSfOut) {
					summary = (String) axSf.getAttributeValue("fld13");
				}

				AsocRegsResults asocRegsResults = new AsocRegsResults(bookId,
						folderId, folderNumber, folderDate, summary);

				result.add(asocRegsResults);
			}
		}

		return result;
	}

	/**
	 * Obtenemos el codigo de validacion en funcion de los registros que hemos
	 * seleccionado para asociar
	 *
	 * @param asocRegsSelected
	 * @param useCaseConf
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static String getAsocRegsSelectedCode(String asocRegsSelected,
			UseCaseConf useCaseConf) throws BookException, SessionException,
			ValidationException {
		String code = null;

		// Convertimos los datos de los registros seleccionados.
		List regsSelected = BookUseCaseAsocRegsUtil.getAsocRegsResults(
				asocRegsSelected, useCaseConf.getLocale());

		if ((regsSelected != null) && (!regsSelected.isEmpty())) {
			/*
			 * Si solo hemos seleccionado un registro nos podemos encontrar con
			 * los siguientes casos
			 */
			if (regsSelected.size() == 1) {
				AsocRegsResults asocRegsResults = (AsocRegsResults) regsSelected
						.get(0);

				/*
				 * El registro seleccionado esta asociado por lo que el registro
				 * actual sera un registro secundario del seleccionado, o del
				 * padre del seleccionado.
				 */
				if (BookUseCaseAsocRegsUtil.isAsocReg(useCaseConf,
						asocRegsResults.getFolderId(), asocRegsResults
								.getBookId())) {
					code = "1";
				} else {
					/*
					 * El registro seleccionado no está asociado. Por lo que se
					 * convertira en el padre del registro actual.
					 */
					code = "3";
				}
			} else {
				/*
				 * Si hemos seleccionado mas de un registro podemos encontrarnos
				 * con los siguientes casos
				 */
				code = getAsocRegsSelectedCode(regsSelected, useCaseConf);
			}
		}

		return code;
	}

	/**
	 * Transformamos la lista de registros seleccionados en una lista de
	 * objetos.
	 *
	 * @param asocRegsSelected
	 * @param locale
	 * @return
	 */
	public static List getAsocRegsResults(String asocRegsSelected, Locale locale) {

		List result = new ArrayList();

		if ((asocRegsSelected != null) && (asocRegsSelected.length() > 0)) {
			StringTokenizer tokens = new StringTokenizer(asocRegsSelected,
					ALMOHADILLA);

			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				StringTokenizer tokens1 = new StringTokenizer(token, PUNTO_COMA);

				String bookId = tokens1.nextToken();
				String folderId = tokens1.nextToken();
				String folderNumber = tokens1.nextToken();
				String folderDate = tokens1.nextToken();
				String summary = tokens1.nextToken();

				AsocRegsResults asocRegsResults = new AsocRegsResults(Integer
						.valueOf(bookId), Integer.valueOf(folderId),
						folderNumber, folderDate, summary);

				result.add(asocRegsResults);
			}
		}

		return result;
	}

	/**
	 * Obtenemos la lista de registros no asociados de la lista de registros
	 * seleccionados
	 *
	 * @param useCaseConf
	 * @param listaRegsSelec
	 * @return
	 * @throws ValidationException
	 * @throws SessionException
	 * @throws BookException
	 */
	public static List getNoAsocRegsResults(UseCaseConf useCaseConf,
			List listaRegsSelec) throws BookException, SessionException,
			ValidationException {
		if ((listaRegsSelec != null) && (!listaRegsSelec.isEmpty())) {
			for (Iterator iterator = listaRegsSelec.iterator(); iterator
					.hasNext();) {
				AsocRegsResults asocRegsResults = (AsocRegsResults) iterator
						.next();

				if (isAsocReg(useCaseConf, asocRegsResults.getFolderId(),
						asocRegsResults.getBookId())) {
					iterator.remove();
				}

			}
		}

		return listaRegsSelec;

	}

	/**
	 * Guardamos una lista de registros asociada a otro registro primario
	 *
	 * @param useCaseConf
	 * @param listaRegsSec
	 * @param bookIdPrim
	 * @param folderIdPrim
	 * @throws Exception
	 */
	public static void saveAsocRegsSec(UseCaseConf useCaseConf,
			List listaRegsSec, Integer bookIdPrim, Integer folderIdPrim)
			throws Exception {
		if (listaRegsSec != null && !listaRegsSec.isEmpty()) {
			for (Iterator iterator = listaRegsSec.iterator(); iterator
					.hasNext();) {
				AsocRegsResults asocRegsResults = (AsocRegsResults) iterator
						.next();

				saveAsocRegsSec(useCaseConf, bookIdPrim, folderIdPrim,
						asocRegsResults.getBookId(), asocRegsResults
								.getFolderId());

			}
		}
	}

	/**
	 * Guardamos un registro asociado
	 *
	 * @param useCaseConf
	 * @param bookIdPrim
	 * @param folderIdPrim
	 * @param bookIdSec
	 * @param folderIdSec
	 * @throws Exception
	 */
	public static void saveAsocRegsSec(UseCaseConf useCaseConf,
			Integer bookIdPrim, Integer folderIdPrim, Integer bookIdSec,
			Integer folderIdSec) throws Exception {
		FolderAsocSession.saveAsocRegFdr(useCaseConf.getSessionID(), useCaseConf
				.getEntidadId(), bookIdPrim.intValue(),
				folderIdPrim.intValue(), bookIdSec.intValue(), folderIdSec
						.intValue());
	}

	/**
	 * Eliminamos un registro secundario
	 *
	 * @param useCaseConf
	 * @param bookIdSec
	 * @param folderIdSec
	 * @throws Exception
	 */
	public static void deleteAsocRegsSec(UseCaseConf useCaseConf,
			Integer bookIdSec, Integer folderIdSec) throws Exception {

		List lista = FolderAsocSession.getAsocRegFdr(useCaseConf.getSessionID(), bookIdSec,
				folderIdSec.intValue(), useCaseConf.getEntidadId());

		if (lista != null && !lista.isEmpty()){
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				ScrRegasoc scrRegasoc = (ScrRegasoc) iterator.next();

				if (scrRegasoc.getIdArchsec() == bookIdSec.intValue() && scrRegasoc.getIdFdrsec() == folderIdSec.intValue()){
					FolderAsocSession.deleteAsocRegFdr(useCaseConf.getSessionID(), useCaseConf
							.getEntidadId(), scrRegasoc);
					break;
				}
			}
		}

	}

	/**
	 * Obtenemos el registro primario del registro que se pasa como parametro
	 *
	 * @param useCaseConf
	 * @param bookId
	 * @param folderId
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	public static Integer[] getAsocRegPrimario(UseCaseConf useCaseConf,
			Integer bookId, Integer folderId) throws ValidationException,
			BookException, SessionException {
		List listaAsocRegsCurrent = FolderAsocSession.getAsocRegFdr(useCaseConf
				.getSessionID(), bookId, folderId.intValue(), useCaseConf
				.getEntidadId());

		if (listaAsocRegsCurrent != null && !listaAsocRegsCurrent.isEmpty()) {
			ScrRegasoc scrRegasoc = (ScrRegasoc) listaAsocRegsCurrent.get(0);

			return new Integer[] { new Integer(scrRegasoc.getIdArchprim()),
					new Integer(scrRegasoc.getIdFdrprim()) };
		}

		return null;
	}

	/**
	 * Obtenemos el registro primario comun de la lista de registros
	 * seleccionados
	 *
	 * @param useCaseConf
	 * @param listaRegsSelect
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	public static Integer[] getAsocRegPrimario(UseCaseConf useCaseConf,
			List listaRegsSelect) throws ValidationException, BookException,
			SessionException {
		Integer[] result = null;
		if ((listaRegsSelect != null) && (!listaRegsSelect.isEmpty())) {
			for (Iterator iterator = listaRegsSelect.iterator(); iterator
					.hasNext();) {
				AsocRegsResults asocRegsResults = (AsocRegsResults) iterator
						.next();

				result = getAsocRegPrimario(useCaseConf, asocRegsResults
						.getBookId(), asocRegsResults.getFolderId());

				if (result != null) {
					break;
				}

			}
		}

		return result;

	}

	/**
	 * Traducimos el operador de busqueda de texto a su simbolo correspondiente
	 *
	 * @param operator
	 * @param locale
	 * @return
	 */
	private static String translateOperator(String operator, Locale locale) {

		String result = null;
		if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_NOT_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_GREATER_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_GREATER_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_LESSER_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_LESSER_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_BETWEEN_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_LIKE_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_OR_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_ABC_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_IN_AND_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_IN_OR_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_DEPEND_OF_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_DEPEND_OF_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_BEGIN_BY_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_END_WITH_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_AFTER_TO_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_BEFORE_TO_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_TEXT_VALUE;
		}

		return result;
	}

	/**
	 * Convertimos un parametro al valor necesario para la bsuqueda
	 *
	 * @param param
	 * @param operator
	 * @param type
	 * @param locale
	 * @return
	 * @throws ParseException
	 */
	private static Object convertValue(String param, String operator,
			String type, Locale locale) throws ParseException {
		if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_LIKE_TEXT_VALUE))) {
			String aux = param.replaceAll("%", "");
			aux = "%" + aux + "%";
			return convertValue(type, aux, locale);
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_BEGIN_BY_VALUE))) {
			String aux = param.replaceAll("%", "");
			aux = aux + "%";
			return convertValue(type, aux, locale);
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_END_WITH_VALUE))) {
			String aux = param.replaceAll("%", "");
			aux = "%" + aux;
			return convertValue(type, aux, locale);
		} else {
			return convertValue(type, param, locale);
		}
	}

	/**
	 * Convertimos un parametro al valor necesario para la bsuqueda
	 *
	 * @param type
	 * @param param
	 * @param locale
	 * @return
	 * @throws ParseException
	 */
	private static Object convertValue(String type, String param, Locale locale)
			throws ParseException {
		if ("2".equals(type)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RBUtil
					.getInstance(locale).getProperty(I18N_DATE_SHORTFORMAT));
			simpleDateFormat.setLenient(false);
			return simpleDateFormat.parse(param);
		} else {
			return StringEscapeUtils.escapeSql(param);
		}
	}

	/**
	 * Obtenemos el codigo de validacion en funcion de los registros
	 * seleccionados
	 *
	 * @param regsSelected
	 * @param useCaseConf
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	private static String getAsocRegsSelectedCode(List regsSelected,
			UseCaseConf useCaseConf) throws ValidationException, BookException,
			SessionException {

		List listaPrimarios = new ArrayList();
		List listaSecundarios = new ArrayList();
		List listaNoAsociados = new ArrayList();

		for (Iterator iterator = regsSelected.iterator(); iterator.hasNext();) {
			AsocRegsResults asocRegsResults = (AsocRegsResults) iterator.next();

			List listaAsocRegsCurrent = FolderAsocSession.getAsocRegFdr(useCaseConf
					.getSessionID(), asocRegsResults.getBookId(),
					asocRegsResults.getFolderId().intValue(), useCaseConf
							.getEntidadId());

			/*
			 * Si la lista de registros asociados es vacia es que el registro no
			 * esta asociado. Lo asociamos a la lista correspondiente.
			 *
			 * Si la lista de registros asociados no es vacia nos encontramos
			 * con los siguientes casos.
			 */
			if (listaAsocRegsCurrent != null && !listaAsocRegsCurrent.isEmpty()) {
				/*
				 * Si el numero de registros asociados encontrados es mayor de
				 * 1, es que el registro seleccionado es principal.
				 */
				if (listaAsocRegsCurrent.size() > 1) {
					listaPrimarios.add(asocRegsResults);
				} else {
					/*
					 * Si el numero de registros asociados encontrados es 1,
					 * comprobamos si es principal o secundario.
					 */
					ScrRegasoc scrRegasoc = (ScrRegasoc) listaAsocRegsCurrent
							.get(0);

					if ((scrRegasoc.getIdArchprim() == asocRegsResults
							.getBookId().intValue())
							&& (scrRegasoc.getIdFdrprim() == asocRegsResults
									.getFolderId().intValue())) {
						listaPrimarios.add(asocRegsResults);
					} else {
						listaSecundarios.add(asocRegsResults);
					}
				}
			} else {
				listaNoAsociados.add(asocRegsResults);
			}
		}

		/*
		 * Obtenemos el codigo en funcion de las listas de registros obtenidas
		 */
		return getAsocRegsSelectedCode(useCaseConf, listaPrimarios,
				listaSecundarios, listaNoAsociados);

	}

	/**
	 * Obtenemos el codigo en funcion de las listas de registros obtenidas
	 *
	 * @param useCaseConf
	 * @param listaPrimarios
	 * @param listaSecundarios
	 * @param listaNoAsociados
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	private static String getAsocRegsSelectedCode(UseCaseConf useCaseConf,
			List listaPrimarios, List listaSecundarios, List listaNoAsociados)
			throws ValidationException, BookException, SessionException {

		List listaPrimariosResult = getAsocRegsPrimarios(listaPrimarios);

		/*
		 * Si la lista de registros seleccionados que son primarios es mayor que
		 * 1. La asociacion no es valida. Codigo 5.
		 */
		if ((listaPrimariosResult != null) && (listaPrimariosResult.size() > 1)) {
			return "5";
		} else {
			/*
			 * Obtenemos los padres de los registros secundarios seleccionados
			 */
			List listaSecundariosResult = getAsocRegsSecundarios(
					listaSecundarios, useCaseConf);

			/*
			 * Si la lista de padres de los registros seleccionados que son
			 * secundarios es mayor que 1. La asociacion no es valida. Codigo 5.
			 */
			if ((listaSecundariosResult != null)
					&& (listaSecundariosResult.size() > 1)) {
				return "5";
			} else {
				/*
				 * Si las listas obtenidas de la busqueda de registros asociados
				 * primarios y secundarios de los registros seleccionados no son
				 * vacias (ambas), nos podemos encontrar con los siguientes
				 * casos
				 */
				if ((listaPrimariosResult != null)
						&& (!listaPrimariosResult.isEmpty())
						&& (listaSecundariosResult != null)
						&& (!listaSecundariosResult.isEmpty())) {
					AsocRegsResults regPrimario = (AsocRegsResults) listaPrimariosResult
							.get(0);
					ScrRegasoc regSec = (ScrRegasoc) listaSecundariosResult
							.get(0);

					/*
					 * Si el padre es el mismo. Hay dos opciones: - No hemos
					 * seleccionado registros no asociados: Codigo 1 - Si hemos
					 * seleccionado registros no asociados: Codigo 2
					 */
					if ((regPrimario.getBookId().intValue() == regSec
							.getIdArchprim())
							&& (regPrimario.getFolderId().intValue() == regSec
									.getIdFdrprim())) {
						if (listaNoAsociados != null
								&& !listaNoAsociados.isEmpty()) {
							return "2";
						} else {
							return "1";
						}
					} else {
						/*
						 * Si el padre no es el mismo. Seleccion invalida.
						 * Codigo 5
						 */
						return "5";
					}

					/*
					 * Si una de las listas obtenidas de la busqueda de
					 * registros asociados primarios y secundarios de los
					 * registros seleccionados no es vacia (solo una), nos
					 * podemos encontrar con los siguientes casos: - No hemos
					 * seleccionado registros no asociados: Codigo 1 - Si hemos
					 * seleccionado registros no asociados: Codigo 2
					 */
				} else if ((listaPrimariosResult != null && !listaPrimariosResult
						.isEmpty())
						|| (listaSecundariosResult != null && !listaSecundariosResult
								.isEmpty())) {
					if (listaNoAsociados != null && !listaNoAsociados.isEmpty()) {
						return "2";
					} else {
						return "1";
					}
					/*
					 * Si las listas obtenidas de la busqueda de registros
					 * asociados primarios y secundarios de los registros
					 * seleccionados con vacias (ambas). Y la de registros no
					 * asociados no lo es. Nos podemos encontrar con los
					 * siguientes casos: - Solo hemos seleccionado un registro
					 * no asociado: Codigo 2 - Hemos seleccionado mas de un
					 * registro asociado: Codigo 4
					 *
					 */
				} else if (listaNoAsociados != null
						&& !listaNoAsociados.isEmpty()) {
					if (listaNoAsociados.size() > 1) {
						return "4";
					} else {
						return "2";
					}
				} else {
					return "-1";
				}
			}

		}
	}

	/**
	 * Obtenemos la lista de Registros Seleccionados que son primarios y
	 * distintos
	 *
	 * @param listaPrimarios
	 * @return
	 */
	private static List getAsocRegsPrimarios(List listaPrimarios) {
		List listaPrimariosResult = new ArrayList();
		if (!listaPrimarios.isEmpty()) {
			for (Iterator iterator = listaPrimarios.iterator(); iterator
					.hasNext();) {
				AsocRegsResults asocRegPrimario = (AsocRegsResults) iterator
						.next();

				if (listaPrimariosResult.isEmpty()) {
					listaPrimariosResult.add(asocRegPrimario);
				} else {
					for (Iterator it = listaPrimariosResult.iterator(); it
							.hasNext();) {
						AsocRegsResults asocRegPrimResult = (AsocRegsResults) it
								.next();

						if ((asocRegPrimario.getFolderId().intValue() != asocRegPrimResult
								.getFolderId().intValue())
								|| (asocRegPrimario.getBookId().intValue() != asocRegPrimResult
										.getBookId().intValue())) {
							listaPrimariosResult.add(asocRegPrimario);
							break;
						}
					}
				}
			}
		}
		return listaPrimariosResult;
	}

	/**
	 * Obtenemos los padres distintos de los registros seleccionados que son
	 * secundarios
	 *
	 * @param listaSecundarios
	 * @param useCaseConf
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	private static List getAsocRegsSecundarios(List listaSecundarios,
			UseCaseConf useCaseConf) throws ValidationException, BookException,
			SessionException {
		List listaRegs = new ArrayList();

		if ((listaSecundarios != null) && (!listaSecundarios.isEmpty())) {

			for (Iterator iterator = listaSecundarios.iterator(); iterator
					.hasNext();) {
				AsocRegsResults asocRegSec = (AsocRegsResults) iterator.next();

				List listaAsocRegs = FolderAsocSession.getAsocRegFdr(useCaseConf
						.getSessionID(), asocRegSec.getBookId(), asocRegSec
						.getFolderId().intValue(), useCaseConf.getEntidadId());

				if (listaAsocRegs != null && !listaAsocRegs.isEmpty()) {
					if (listaRegs.isEmpty()) {
						listaRegs.add(listaAsocRegs.get(0));
					} else {
						ScrRegasoc regSec = (ScrRegasoc) listaAsocRegs.get(0);

						for (Iterator it = listaRegs.iterator(); it.hasNext();) {
							ScrRegasoc regPrim = (ScrRegasoc) it.next();

							if ((regPrim.getIdArchprim() != regSec
									.getIdArchprim())
									|| (regPrim.getIdFdrprim() != regSec
											.getIdFdrprim())) {
								listaRegs.add(regSec);
								break;
							}
						}
					}
				}
			}

		}

		return listaRegs;
	}
}
