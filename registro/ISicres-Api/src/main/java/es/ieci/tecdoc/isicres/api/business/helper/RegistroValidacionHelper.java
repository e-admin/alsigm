/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;

import es.ieci.tecdoc.isicres.api.business.exception.CampoAdicionalException;
import es.ieci.tecdoc.isicres.api.business.exception.RegistroException;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase que contiene metodos de validacion de los datos de un registro
 *
 */
public class RegistroValidacionHelper {

	private static final Logger logger = Logger
			.getLogger(RegistroValidacionHelper.class);

	/**
	 * Obtenemos un mensaje con la lista de campos no validos
	 *
	 * @param listaErrores
	 * @return
	 */
	public static String getMensajeErrorListaCampos(List listaErrores) {
		String mensaje = "";

		if (!listaErrores.isEmpty()) {
			for (Iterator iterator = listaErrores.iterator(); iterator
					.hasNext();) {
				String error = (String) iterator.next();
				mensaje = mensaje + error + ";\n";
			}
		}

		return mensaje;
	}

	/**
	 * Método para validar los datos de un registro de entrada que se va a crear
	 *
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public static List validateRegistroEntradaCreate(UsuarioVO usuario,
			RegistroEntradaVO registro) {
		List listaErrores = validateRegistroEntrada(usuario, registro, true);

		return listaErrores;
	}

	/**
	 * Método que valida los datos de un registro de salida que se va a crear
	 *
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public static List validateRegistroSalidaCreate(UsuarioVO usuario,
			RegistroSalidaVO registro) {
		List listaErrores = validateRegistroSalida(usuario, registro, true);

		return listaErrores;
	}

	/**
	 * Método que valida los datos de un registro de entrada que se va a
	 * importar
	 *
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public static List validateRegistroEntradaImport(UsuarioVO usuario,
			RegistroEntradaExternoVO registro) {
		List listaErrores = validateRegistroEntrada(usuario, registro, false);

		return listaErrores;
	}

	/**
	 * Método que valida los datos de un registro de salida que se va a importar
	 *
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public static List validateRegistroSalidaImport(UsuarioVO usuario,
			RegistroSalidaExternoVO registro) {
		List listaErrores = validateRegistroSalida(usuario, registro, false);

		return listaErrores;

	}

	/**
	 * Método para validar los datos de un Registro de Entrada
	 *
	 * @param usuario
	 * @param registro
	 * @param isCreate
	 * @return
	 */
	protected static List validateRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaVO registro, boolean isCreate) {

		List listaIdErrores = new ArrayList();

		listaIdErrores
				.addAll(validateRegistroComun(usuario, registro, isCreate));

		Map idsToValidar = new HashMap();
		if (registro.getRegistroOriginal() != null) {
			if (StringUtils.isNotBlank(registro.getRegistroOriginal()
					.getTipoRegistroOriginal())) {
				String tipoRegistroOriginal = registro.getRegistroOriginal()
						.getTipoRegistroOriginal();
				if (!ConstantKeys.EREG_VALUE_FLD_11_ENTRADA
						.equalsIgnoreCase(tipoRegistroOriginal)
						&& !ConstantKeys.EREG_VALUE_FLD_11_ENTRADA_ID
								.equalsIgnoreCase(tipoRegistroOriginal)
						&& !ConstantKeys.EREG_VALUE_FLD_11_SALIDA
								.equalsIgnoreCase(tipoRegistroOriginal)
						&& !ConstantKeys.EREG_VALUE_FLD_11_SALIDA_ID
								.equalsIgnoreCase(tipoRegistroOriginal)) {
					listaIdErrores
							.add(ConstantKeys.ID_FLD_EREG_TIPO_REGISTRO_ORIGINAL);
				}
			}

			if (registro.getRegistroOriginal().getEntidadRegistral() != null) {
				idsToValidar.put(
						ConstantKeys.ID_FLD_EREG_ENTIDAD_REGISTRAL_ORIGINAL,
						registro.getRegistroOriginal().getEntidadRegistral()
								.getCodigoUnidad());
			}
		}

		if (registro.getTipoAsunto() != null
				&& StringUtils.isNotEmpty(registro.getTipoAsunto().getCodigo())) {
			idsToValidar.put(ConstantKeys.ID_FLD_EREG_TIPO_ASUNTO, registro
					.getTipoAsunto().getCodigo());
		}

		try {
			listaIdErrores.addAll(AttributesSession.validateFixedValues(usuario
					.getConfiguracionUsuario().getSessionID(), new Integer(
					registro.getIdLibro()), idsToValidar, true, usuario
					.getConfiguracionUsuario().getIdEntidad()));
		} catch (Exception e) {
			logger
					.debug("Se ha producido un error en la validacion de los datos del registro");
			throw new RegistroException(
					"Se ha producido un error en la validacion de los datos del registro");
		}

		FieldFormat fieldFormat = RegistroHelper.getFieldFormat(usuario,
				registro);

		listaIdErrores.addAll(validateCamposAdicionales(usuario, registro,
				fieldFormat));

		if (!listaIdErrores.isEmpty()) {
			return obtenerNombreCamposError(listaIdErrores, fieldFormat);
		}

		return null;

	}

	/**
	 * Método para validar los datos de un Registro de Salida
	 *
	 * @param usuario
	 * @param registro
	 * @param isCreate
	 * @return
	 */
	protected static List validateRegistroSalida(UsuarioVO usuario,
			RegistroSalidaVO registro, boolean isCreate) {
		List listaIdErrores = new ArrayList();

		listaIdErrores
				.addAll(validateRegistroComun(usuario, registro, isCreate));

		Map idsToValidar = new HashMap();

		if (registro.getTipoAsunto() != null
				&& StringUtils.isNotEmpty(registro.getTipoAsunto().getCodigo())) {
			idsToValidar.put(ConstantKeys.ID_FLD_SREG_TIPO_ASUNTO, registro
					.getTipoAsunto().getCodigo());
		}

		try {
			listaIdErrores.addAll(AttributesSession.validateFixedValues(usuario
					.getConfiguracionUsuario().getSessionID(), new Integer(
					registro.getIdLibro()), idsToValidar, true, usuario
					.getConfiguracionUsuario().getIdEntidad()));
		} catch (Exception e) {
			logger
					.debug("Se ha producido un error en la validacion de los datos del registro");
			throw new RegistroException(
					"Se ha producido un error en la validacion de los datos del registro");
		}

		FieldFormat fieldFormat = RegistroHelper.getFieldFormat(usuario,
				registro);

		listaIdErrores.addAll(validateCamposAdicionales(usuario, registro,
				fieldFormat));

		if (!listaIdErrores.isEmpty()) {
			return obtenerNombreCamposError(listaIdErrores, fieldFormat);
		}

		return null;
	}

	/**
	 * Método que valida los campos comunes en un registro.
	 *
	 * Por campos comunes nos referimos a los que tienen el mismo FLD tanto en
	 * la tabla de registro de entrada como de registro de salida
	 *
	 * @param usuario
	 * @param registro
	 * @param isCreate
	 * @return
	 */
	protected static List validateRegistroComun(UsuarioVO usuario,
			BaseRegistroVO registro, boolean isCreate) {
		List listaErrores = new ArrayList();

		if (!validateFechaRegistro(registro, isCreate)) {
			listaErrores.add(ConstantKeys.ID_FLD_FECHA_REGISTRO);
		}

		listaErrores.addAll(validateCamposValidadosComun(usuario, registro,
				isCreate));

		return listaErrores;

	}

	/**
	 * Método para validad la fecha de registro
	 *
	 * @param registro
	 * @param isCreate
	 * @return
	 */
	protected static boolean validateFechaRegistro(BaseRegistroVO registro,
			boolean isCreate) {

		if (registro.getFechaRegistro() != null) {
			if (!(registro instanceof RegistroEntradaExternoVO)
					&& !(registro instanceof RegistroSalidaExternoVO)) {
				if (registro.getFechaRegistro().after(new Date()) && isCreate) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Método para validar los campos validados del registro.
	 *
	 * Un campo validado es aquel en el que introducimos un codigo y su
	 * descripcion se encuentra en otra tabla
	 *
	 * @param usuario
	 * @param registro
	 * @param isCreate
	 * @return
	 */
	protected static List validateCamposValidadosComun(UsuarioVO usuario,
			BaseRegistroVO registro, boolean isCreate) {

		List listaErrores = new ArrayList();
		Map idsToValidar = new HashMap();

		if (registro.getOficinaRegistro() != null
				&& StringUtils.isNotBlank(registro.getOficinaRegistro()
						.getCodigoOficina())) {
			idsToValidar.put(ConstantKeys.ID_FLD_OFICINA_REGISTRO, registro
					.getOficinaRegistro().getCodigoOficina());
		} else if (isCreate) {
			listaErrores.add(ConstantKeys.ID_FLD_OFICINA_REGISTRO);
		}

		if (registro.getUnidadAdministrativaOrigen() != null
				&& StringUtils.isNotBlank(registro
						.getUnidadAdministrativaOrigen().getCodigoUnidad())) {
			idsToValidar.put(ConstantKeys.ID_FLD_ORIGEN, registro
					.getUnidadAdministrativaOrigen().getCodigoUnidad());
		}

		if (registro.getUnidadAdministrativaDestino() != null
				&& StringUtils.isNotBlank(registro
						.getUnidadAdministrativaDestino().getCodigoUnidad())) {
			idsToValidar.put(ConstantKeys.ID_FLD_DESTINO, registro
					.getUnidadAdministrativaDestino().getCodigoUnidad());
		}

		try {
			listaErrores.addAll(AttributesSession.validateFixedValues(usuario
					.getConfiguracionUsuario().getSessionID(), new Integer(
					registro.getIdLibro()), idsToValidar, true, usuario
					.getConfiguracionUsuario().getIdEntidad()));
		} catch (Exception e) {
			logger
					.debug("Se ha producido un error en la validacion de los datos del registro");
			throw new RegistroException(
					"Se ha producido un error en la validacion de los datos del registro");
		}

		return listaErrores;
	}

	/**
	 * Método para validar los campos adicionales de un registro
	 *
	 * @param usuario
	 * @param registro
	 * @return
	 */
	protected static List validateCamposAdicionales(UsuarioVO usuario,
			BaseRegistroVO registro, FieldFormat fieldFormat) {
		List listaErrores = new ArrayList();

		if (registro.getCamposAdicionales() != null
				&& !registro.getCamposAdicionales().isEmpty()) {
			try {

				String sessionID = usuario.getConfiguracionUsuario()
						.getSessionID();
				Integer bookID = new Integer(registro.getIdLibro());
				String entidad = usuario.getConfiguracionUsuario()
						.getIdEntidad();

				AxSf axsfQ = BookSession.getFormFormat(sessionID, bookID,
						entidad);
				Locale locale = usuario.getConfiguracionUsuario().getLocale();

				for (Iterator iterator = registro.getCamposAdicionales()
						.iterator(); iterator.hasNext();) {
					CampoAdicionalRegistroVO campoAdicional = (CampoAdicionalRegistroVO) iterator
							.next();

					if (!validateCampoAdicional(campoAdicional, axsfQ,
							fieldFormat, sessionID, bookID, locale, entidad)) {
						listaErrores.add(new Integer(campoAdicional.getName()));
					}

				}

			} catch (Exception e) {
				logger
						.debug("Se ha producido un error en la validacion de los datos del registro");
				throw new RegistroException(
						"Se ha producido un error en la validacion de los datos del registro");
			}
		}

		return listaErrores;
	}

	/**
	 * Método que valida el valor y formato de un campo adicional de un registro
	 *
	 * @param campoAdicional
	 * @param axsfQ
	 * @param fieldFormat
	 * @param sessionID
	 * @param bookID
	 * @param entidad
	 * @return
	 */
	protected static boolean validateCampoAdicional(
			CampoAdicionalRegistroVO campoAdicional, AxSf axsfQ,
			FieldFormat fieldFormat, String sessionID, Integer bookID,
			Locale locale, String entidad) {
		int fldid = 0;
		try {
			fldid = Integer.parseInt(campoAdicional.getName());
		} catch (NumberFormatException e) {
			logger.debug("El nombre del campo[" + campoAdicional.getName()
					+ "] no es correcto. Ha de ser un entero", e);
			throw new CampoAdicionalException("El nombre del campo["
					+ campoAdicional.getName()
					+ "] no es correcto. Ha de ser un entero");
		}

		if (!axsfQ.getProposedExtendedFields().contains(new Integer(fldid))) {

			String value = campoAdicional.getValue();

			String campoValidation = null;
			try {
				campoValidation = AttributesSession
						.getExtendedValidationFieldValueWithTVNull(sessionID,
								bookID, fldid, value, locale, entidad);
			} catch (Exception e) {
				logger.debug("El nombre del campo[" + campoAdicional.getName()
						+ "] no es correcto", e);
				throw new CampoAdicionalException("El nombre del campo["
						+ campoAdicional.getName() + "] no es correcto");
			}
			if (StringUtils.isNotEmpty(campoValidation)) {
				FlushFdrField flushFdrField = new FlushFdrField();
				flushFdrField.setFldid(fldid);
				flushFdrField.setValue(value);

				SimpleDateFormat shortFormatter = new SimpleDateFormat(
						ConstantKeys.FECHA_FORMAT_SHORT);
				SimpleDateFormat longFormatter = new SimpleDateFormat(
						ConstantKeys.FECHA_FORMAT_LONG);

				if (!FolderSession.checkValue(flushFdrField, shortFormatter,
						longFormatter, fieldFormat)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Método para obtener un literar con los campos que no son correctos
	 *
	 * El formato del literar sera FLDXX - Nombre o descripcion del campo
	 *
	 * @param listaIdErrores
	 * @param fieldFormat
	 * @return
	 */
	protected static List obtenerNombreCamposError(List listaIdErrores,
			FieldFormat fieldFormat) {
		List result = new ArrayList();

		for (Iterator iterator = listaIdErrores.iterator(); iterator.hasNext();) {
			Integer fldid = (Integer) iterator.next();

			if (fieldFormat.getFlddefs().containsKey(fldid)) {
				String fldName = ConstantKeys.FLD_KEY + fldid.toString()
						+ " - " + fieldFormat.getFFldDef(fldid.intValue()).getName();
				result.add(fldName);
			}
		}

		return result;
	}
}
