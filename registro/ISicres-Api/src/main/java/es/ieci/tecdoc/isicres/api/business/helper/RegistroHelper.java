/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderDataSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;

import es.ieci.tecdoc.isicres.api.business.exception.RegistroException;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoRegistroEnum;

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase que contiene metodos tipicos a realizar con la informacion de
 *          registro
 */
public class RegistroHelper {

	private static final Logger logger = Logger.getLogger(RegistroHelper.class);

	/**
	 * Método que se comunica con las clases que realizan las operaciones de
	 * crear un registro
	 *
	 * @param usuario
	 * @param registro
	 * @param axsfNew
	 * @return
	 */
	public static BaseRegistroVO createRegistro(UsuarioVO usuario,
			BaseRegistroVO registro, AxSf axsfNew) {
		Integer launchDistOutRegister = RegistroHelper
				.getEjecucionDistribucionRegistros(usuario);

		List listaInteresados = TercerosHelper
				.getListaInteresadosISicres(registro);
		Map listaDocumentos = DocumentosHelper.getDocumentosISicres(registro
				.getDocumentos());

		FolderDataSession data = null;
		try {
			boolean isImport = false;
			if ((registro instanceof RegistroEntradaExternoVO)
					|| (registro instanceof RegistroSalidaExternoVO)) {
				isImport = true;
			}

			String sessionID = usuario.getConfiguracionUsuario().getSessionID();
			Integer bookID = new Integer(registro.getIdLibro());
			Locale locale = usuario.getConfiguracionUsuario().getLocale();
			String entidad = usuario.getConfiguracionUsuario().getIdEntidad();

			data = FolderSession.createFolderWithDocuments(sessionID, bookID,
					axsfNew, listaInteresados, listaDocumentos,
					launchDistOutRegister, locale, entidad, false, isImport);

		} catch (Exception e) {
			logger.debug("No se puede crear el registro", e);
			throw new RegistroException("No se puede crear el registro");
		}

		return returnRegistro(registro, data);

	}

	/**
	 * Método para obtener el formato de los atributos que tendrán los registros
	 * de un libro
	 *
	 * @param sessionID
	 * @param bookID
	 * @return
	 */
	public static FieldFormat getFieldFormat(UsuarioVO usuario,
			BaseRegistroVO registro) {
		Idocarchdet idocarchdet = null;
		try {
			idocarchdet = BookSession.getIdocarchdetFld(usuario
					.getConfiguracionUsuario().getSessionID(), new Integer(
					registro.getIdLibro()));
		} catch (Exception e) {
			logger
					.debug("Se ha producido un error en la validacion de los datos del registro");
			throw new RegistroException(
					"Se ha producido un error en la validacion de los datos del registro");
		}
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

		return fieldFormat;
	}

	/**
	 * Método para obtener la traducion de los campos validados del registro de
	 * entrada
	 *
	 * Un campo validado es aquel en el que introducimos un codigo y su
	 * descripcion se encuentra en otra tabla
	 *
	 *
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public static Map obtenerCamposValidadosRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaVO registro) {
		Map idsToTranslate = new HashMap();

		idsToTranslate.putAll(obtenerCamposValidadosComun(registro));

		if ((registro.getRegistroOriginal() != null)
				&& (registro.getRegistroOriginal().getEntidadRegistral() != null)) {
			idsToTranslate.put(
					ConstantKeys.ID_FLD_EREG_ENTIDAD_REGISTRAL_ORIGINAL,
					registro.getRegistroOriginal().getEntidadRegistral()
							.getCodigoUnidad());
		}

		if (registro.getTipoAsunto() != null
				&& StringUtils.isNotEmpty(registro.getTipoAsunto().getCodigo())) {
			idsToTranslate.put(ConstantKeys.ID_FLD_EREG_TIPO_ASUNTO, registro
					.getTipoAsunto().getCodigo());
		}

		return traducirValoresCamposValidados(usuario, registro, idsToTranslate);
	}

	public static Map obtenerCamposValidadosRegistroSalida(UsuarioVO usuario,
			RegistroSalidaVO registro) {
		Map idsToTranslate = new HashMap();

		idsToTranslate.putAll(obtenerCamposValidadosComun(registro));

		if (registro.getTipoAsunto() != null
				&& StringUtils.isNotEmpty(registro.getTipoAsunto().getCodigo())) {
			idsToTranslate.put(ConstantKeys.ID_FLD_SREG_TIPO_ASUNTO, registro
					.getTipoAsunto().getCodigo());
		}

		return traducirValoresCamposValidados(usuario, registro, idsToTranslate);
	}

	/**
	 * Método para completar los datos de un registro
	 *
	 * @param axSfNew
	 * @param registro
	 * @param translatedIds
	 * @return
	 */
	public static AxSf completarRegistroAxSf(AxSf axSf, UsuarioVO usuario,
			BaseRegistroVO registro, Map translatedIds) {
		axSf = completarRegistroCamposTraducidos(axSf, translatedIds);

		if (StringUtils.isNotBlank(registro.getNumeroRegistro())) {
			if ((registro instanceof RegistroEntradaExternoVO)
					|| (registro instanceof RegistroSalidaExternoVO)) {
				axSf.addAttributeName(ConstantKeys.FLD_KEY_NUMERO_REGISTRO
						.toLowerCase());
				axSf.addAttributeValue(ConstantKeys.FLD_KEY_NUMERO_REGISTRO
						.toLowerCase(), registro.getNumeroRegistro());
			}
		}

		axSf
				.addAttributeName(ConstantKeys.FLD_KEY_FECHA_REGISTRO
						.toLowerCase());
		axSf.addAttributeValue(ConstantKeys.FLD_KEY_FECHA_REGISTRO
				.toLowerCase(), registro.getFechaRegistro());

		axSf.addAttributeName(ConstantKeys.FLD_KEY_FECHA_TRABAJO.toLowerCase());
		axSf.addAttributeValue(
				ConstantKeys.FLD_KEY_FECHA_TRABAJO.toLowerCase(), registro
						.getFechaAlta());

		if (axSf instanceof AxSfIn) {
			axSf = completarRegistroAxSfIn(axSf, (RegistroEntradaVO) registro);
		} else if (axSf instanceof AxSfOut) {
			axSf = completarRegistroAxSfOut(axSf, (RegistroSalidaVO) registro);
		}

		axSf = completarRegistroCamposAdicionales(axSf, usuario, registro);

		axSf.setLocale(usuario.getConfiguracionUsuario().getLocale());

		return axSf;

	}

	/**
	 * Método que obtiene el tipo de ejecucion de la distribucion
	 *
	 * @param usuario
	 * @return
	 */
	public static Integer getEjecucionDistribucionRegistros(UsuarioVO usuario) {
		InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
				usuario.getConfiguracionUsuario().getIdEntidad())
				.getInvesicresConf();
		Integer launchDistOutRegister = null;
		if (invesicresConf != null) {
			launchDistOutRegister = new Integer(invesicresConf
					.getDistSRegister());
		} else {
			launchDistOutRegister = new Integer(0);
		}

		return launchDistOutRegister;

	}

	/**
	 * Método que obtiene los campos validados que son comunes a los registro
	 * tanto de entrada como de salida, que se han introducido en el registro
	 * recibido.
	 *
	 *
	 * Por campos comunes nos referimos a los que tienen el mismo FLD tanto en
	 * la tabla de registro de entrada como de registro de salida
	 *
	 * @param registro
	 * @return
	 */
	protected static Map obtenerCamposValidadosComun(BaseRegistroVO registro) {
		Map idsToTranslate = new HashMap();

		if (registro.getOficinaRegistro() != null
				&& StringUtils.isNotBlank(registro.getOficinaRegistro()
						.getCodigoOficina())) {
			idsToTranslate.put(ConstantKeys.ID_FLD_OFICINA_REGISTRO, registro
					.getOficinaRegistro().getCodigoOficina());
		}

		if (registro.getUnidadAdministrativaOrigen() != null
				&& StringUtils.isNotBlank(registro
						.getUnidadAdministrativaOrigen().getCodigoUnidad())) {
			idsToTranslate.put(ConstantKeys.ID_FLD_ORIGEN, registro
					.getUnidadAdministrativaOrigen().getCodigoUnidad());
		}

		if (registro.getUnidadAdministrativaDestino() != null
				&& StringUtils.isNotBlank(registro
						.getUnidadAdministrativaDestino().getCodigoUnidad())) {
			idsToTranslate.put(ConstantKeys.ID_FLD_DESTINO, registro
					.getUnidadAdministrativaDestino().getCodigoUnidad());
		}

		return idsToTranslate;
	}

	/**
	 * Método para traducir los valores de los campos validados
	 *
	 * @param usuario
	 * @param registro
	 * @param idsToTranslate
	 * @return
	 */
	protected static Map traducirValoresCamposValidados(UsuarioVO usuario,
			BaseRegistroVO registro, Map idsToTranslate) {
		Map translatedIds = new HashMap();
		try {
			translatedIds = AttributesSession
					.translateFixedValuesForSaveOrUpdate(usuario
							.getConfiguracionUsuario().getSessionID(),
							new Integer(registro.getIdLibro()), idsToTranslate,
							usuario.getConfiguracionUsuario().getIdEntidad());
		} catch (Exception e) {
			logger
					.debug(
							"Se ha producido un error al traducir los valores de los campos validados",
							e);
			throw new RegistroException(
					"Se ha producido un error al traducir los valores de los campos validados");
		}

		return translatedIds;
	}

	/**
	 * Método para completar el registro con los datos de los campos validados
	 *
	 * @param axsf
	 * @param translatedIds
	 * @return
	 */
	protected static AxSf completarRegistroCamposTraducidos(AxSf axsf,
			Map translatedIds) {
		for (Iterator iterator = translatedIds.keySet().iterator(); iterator
				.hasNext();) {
			Integer id = (Integer) iterator.next();
			axsf.addAttributeName(ConstantKeys.FLD_KEY.toLowerCase()
					+ id.toString());
			axsf.addAttributeValue(ConstantKeys.FLD_KEY.toLowerCase()
					+ id.toString(), translatedIds.get(id));
		}

		return axsf;

	}

	/**
	 * Método para completar los datos adicionales de un registro
	 *
	 * @param axsf
	 * @param usuario
	 * @param registro
	 * @return
	 */
	protected static AxSf completarRegistroCamposAdicionales(AxSf axsf,
			UsuarioVO usuario, BaseRegistroVO registro) {
		Map camposExtendidos = new HashMap();
		try {
			if (registro.getCamposAdicionales() != null
					&& !registro.getCamposAdicionales().isEmpty()) {

				String sessionID = usuario.getConfiguracionUsuario()
						.getSessionID();
				Integer bookID = new Integer(registro.getIdLibro());
				String entidad = usuario.getConfiguracionUsuario()
						.getIdEntidad();

				AxSf axsfQ = BookSession.getFormFormat(sessionID, bookID,
						entidad);

				FieldFormat fieldFormat = getFieldFormat(usuario, registro);

				// Se recupera la definición del libro para extraer los campos
				// adicionales
				Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(usuario
						.getConfiguracionUsuario().getSessionID(), Integer
						.valueOf(bookID));


				// Obtenemos los campos EXTENDIDOS
				List idsExtendedFields = UtilsSession.getExtendedFields(
						idocarchdet,
						(axsf instanceof AxSfIn) ? Keys.EREG_FDR_MATTER
								: Keys.SREG_FDR_MATTER);

				// recorremos el array de campos adicionales
				for (Iterator iterator = registro.getCamposAdicionales()
						.iterator(); iterator.hasNext();) {
					CampoAdicionalRegistroVO campoAdicional = (CampoAdicionalRegistroVO) iterator
							.next();

					// validamos si es un campo extendido o un campo adicional
					if (isExtendField(idsExtendedFields,
							Integer.parseInt(campoAdicional.getName()))) {
						// campo extendido
						AxXf axxf = new AxXf();
						axxf.setFldId(Integer.parseInt(campoAdicional.getName()));
						axxf.setText(campoAdicional.getValue());
						camposExtendidos.put(axxf.getFldId(), axxf);
					} else {
						// campo adicional
						axsf = completarCampoAdicional(axsf, campoAdicional,
								axsfQ, fieldFormat);
					}
				}
			}

			// insertamos el comentario como un campo EXTENDIDO
			if (registro.getComentario() != null) {
				AxXf axxf = new AxXf();
				axxf.setFldId((axsf instanceof AxSfIn) ? AxSf.FLD18_FIELD_ID
						: AxSf.FLD14_FIELD_ID);
				axxf.setText(registro.getComentario());
				camposExtendidos.put(axxf.getFldId(), axxf);
			}

			if (camposExtendidos.size() > 0) {
				// añadimos los campos extendidos al objeto AxSF
				axsf.setExtendedFields(camposExtendidos);
			}

		} catch (Exception e) {
			logger.error("Se ha producido un error en la validacion de los datos del registro",e);
			throw new RegistroException(
					"Se ha producido un error en la validacion de los datos del registro");
		}

		return axsf;
	}

	/**
	 * Metodo que valida sin un campo es extendido o no
	 *
	 * @param idsExtendedFields - Array de ids de los campos extendidos
	 * @param nameCampo - id del campo a validar
	 * @return boolean - TRUE: si es campo EXTENDIDO - FALSE: no es campo EXTENDIDO
	 */
	public static boolean isExtendField(List idsExtendedFields, int nameCampo){
		boolean result = false;

		for (Iterator iterator = idsExtendedFields.iterator(); iterator.hasNext();){
			Integer idExtendField = (Integer) iterator.next();
			if(idExtendField.intValue() == nameCampo){
				return true;
			}
		}

		return result;

	}

	/**
	 * Método para completar el registro con los datos de los campos especificos
	 * de un registro de entrada
	 *
	 * @param axSfNew
	 * @param registro
	 * @return
	 */
	protected static AxSf completarRegistroAxSfIn(AxSf axSfNew,
			RegistroEntradaVO registro) {

		if (registro.getRegistroOriginal() != null) {
			axSfNew
					.addAttributeName(ConstantKeys.FLD_KEY_EREG_NUMERO_REGISTRO_ORIGINAL
							.toLowerCase());
			axSfNew.addAttributeValue(
					ConstantKeys.FLD_KEY_EREG_NUMERO_REGISTRO_ORIGINAL
							.toLowerCase(), registro.getRegistroOriginal()
							.getNumeroRegistroOriginal());

			String tipoRegistroOriginal = registro.getRegistroOriginal()
					.getTipoRegistroOriginal();
			if (StringUtils.isNotBlank(tipoRegistroOriginal)) {
				axSfNew
						.addAttributeName(ConstantKeys.FLD_KEY_EREG_TIPO_REGISTRO_ORIGINAL
								.toLowerCase());
				if (ConstantKeys.EREG_VALUE_FLD_11_ENTRADA
						.equalsIgnoreCase(tipoRegistroOriginal)
						|| ConstantKeys.EREG_VALUE_FLD_11_ENTRADA_ID
								.equalsIgnoreCase(tipoRegistroOriginal)) {
					axSfNew.addAttributeValue(
							ConstantKeys.FLD_KEY_EREG_TIPO_REGISTRO_ORIGINAL
									.toLowerCase(), new BigDecimal("1"));
				} else if (ConstantKeys.EREG_VALUE_FLD_11_SALIDA
						.equalsIgnoreCase(tipoRegistroOriginal)
						|| ConstantKeys.EREG_VALUE_FLD_11_SALIDA_ID
								.equalsIgnoreCase(tipoRegistroOriginal)) {
					axSfNew.addAttributeValue(
							ConstantKeys.FLD_KEY_EREG_TIPO_REGISTRO_ORIGINAL
									.toLowerCase(), new BigDecimal("2"));
				} else {
					axSfNew.addAttributeValue(
							ConstantKeys.FLD_KEY_EREG_TIPO_REGISTRO_ORIGINAL
									.toLowerCase(), tipoRegistroOriginal);
				}
			}

			axSfNew
					.addAttributeName(ConstantKeys.FLD_KEY_EREG_FECHA_REGISTRO_ORIGINAL
							.toLowerCase());
			axSfNew.addAttributeValue(
					ConstantKeys.FLD_KEY_EREG_FECHA_REGISTRO_ORIGINAL
							.toLowerCase(), registro.getRegistroOriginal()
							.getFechaRegistroOriginal());

		}

		if (registro.getTransporte() != null) {
			axSfNew.addAttributeName(ConstantKeys.FLD_KEY_EREG_TIPO_TRANSPORTE
					.toLowerCase());
			if (registro.getTransporte().getTipoTransporte() != null) {
				axSfNew
						.addAttributeValue(
								ConstantKeys.FLD_KEY_EREG_TIPO_TRANSPORTE
										.toLowerCase(), registro
										.getTransporte().getTipoTransporte()
										.getDescripcion());
			} else {
				axSfNew
						.addAttributeValue(
								ConstantKeys.FLD_KEY_EREG_TIPO_TRANSPORTE
										.toLowerCase(), "");
			}

			axSfNew
					.addAttributeName(ConstantKeys.FLD_KEY_EREG_NUMERO_TRANSPORTE
							.toLowerCase());
			axSfNew.addAttributeValue(
					ConstantKeys.FLD_KEY_EREG_NUMERO_TRANSPORTE.toLowerCase(),
					registro.getTransporte().getNumeroTransporte());
		}

		axSfNew.addAttributeName(ConstantKeys.FLD_KEY_EREG_RESUMEN
				.toLowerCase());
		axSfNew.addAttributeValue(ConstantKeys.FLD_KEY_EREG_RESUMEN
				.toLowerCase(), registro.getResumen());

		//Referencia de Expediente
		axSfNew.addAttributeName(ConstantKeys.FLD_KEY_EREG_REF_EXPEDIENTE
				.toLowerCase());

		axSfNew.addAttributeValue(ConstantKeys.FLD_KEY_EREG_REF_EXPEDIENTE
				.toLowerCase(), registro.getReferenciaExpediente());

		return axSfNew;
	}

	/**
	 * Método para completar el registro con los datos de los campos especificos
	 * de un registro de salida
	 *
	 * @param axSfNew
	 * @param registro
	 * @return
	 */
	protected static AxSf completarRegistroAxSfOut(AxSf axSfNew,
			RegistroSalidaVO registro) {

		if (registro.getTransporte() != null) {
			axSfNew.addAttributeName(ConstantKeys.FLD_KEY_SREG_TIPO_TRANSPORTE
					.toLowerCase());
			if (registro.getTransporte().getTipoTransporte() != null) {
				axSfNew
						.addAttributeValue(
								ConstantKeys.FLD_KEY_SREG_TIPO_TRANSPORTE
										.toLowerCase(), registro
										.getTransporte().getTipoTransporte()
										.getDescripcion());
			} else {
				axSfNew
						.addAttributeValue(
								ConstantKeys.FLD_KEY_SREG_TIPO_TRANSPORTE
										.toLowerCase(), "");
			}

			axSfNew
					.addAttributeName(ConstantKeys.FLD_KEY_SREG_NUMERO_TRANSPORTE
							.toLowerCase());
			axSfNew.addAttributeValue(
					ConstantKeys.FLD_KEY_SREG_NUMERO_TRANSPORTE.toLowerCase(),
					registro.getTransporte().getNumeroTransporte());
		}

		axSfNew.addAttributeName(ConstantKeys.FLD_KEY_SREG_RESUMEN
				.toLowerCase());
		axSfNew.addAttributeValue(ConstantKeys.FLD_KEY_SREG_RESUMEN
				.toLowerCase(), registro.getResumen());

		return axSfNew;
	}

	/**
	 * Método para añadir al registro de invesicres el valor de un campo
	 * adicional
	 *
	 * @param axsfNew
	 * @param campoAdicional
	 * @param axsfQ
	 * @param fieldFormat
	 * @return
	 */
	protected static AxSf completarCampoAdicional(AxSf axSf,
			CampoAdicionalRegistroVO campoAdicional, AxSf axsfQ,
			FieldFormat fieldFormat) {
		int fldid = 0;
		try {
			fldid = Integer.parseInt(campoAdicional.getName());
		} catch (NumberFormatException e) {
			logger.debug("El nombre del campo[" + campoAdicional.getName()
					+ "] no es correcto. Ha de ser un entero", e);
			return axSf;
		}

		String value = campoAdicional.getValue();
		if (axsfQ.getProposedExtendedFields().contains(new Integer(fldid))
				&& (value == null)) {
			value = "";
		}

		FlushFdrField flushFdrField = new FlushFdrField();
		flushFdrField.setFldid(fldid);
		flushFdrField.setValue(value);

		SimpleDateFormat shortFormatter = new SimpleDateFormat(
				ConstantKeys.FECHA_FORMAT_SHORT);
		SimpleDateFormat longFormatter = new SimpleDateFormat(
				ConstantKeys.FECHA_FORMAT_LONG);

		Object valueConvertido = FolderSession.convertValue(flushFdrField,
				shortFormatter, longFormatter, fieldFormat);

		if (valueConvertido != null) {
			axSf.addAttributeName(ConstantKeys.FLD_KEY.toLowerCase()
					+ String.valueOf(fldid));
			axSf.addAttributeValue(ConstantKeys.FLD_KEY.toLowerCase()
					+ String.valueOf(fldid), valueConvertido);
		}

		return axSf;
	}

	/**
	 * Método para incluir los datos que nos devuelve la aplicacion al crear un
	 * registro en los datos de un registro
	 *
	 * @param registro
	 * @param folderDataSession
	 * @return
	 */
	private static BaseRegistroVO returnRegistro(BaseRegistroVO registro,
			FolderDataSession folderDataSession) {
		if (folderDataSession != null) {
			String numeroRegistro = folderDataSession
					.getNewAttributeValueAsString(ConstantKeys.FLD_KEY_NUMERO_REGISTRO
							.toLowerCase());
			registro.setNumeroRegistro(numeroRegistro);

			if (folderDataSession.getScrofic() != null) {
				ScrOfic oficina = folderDataSession.getScrofic();
				registro.getOficinaRegistro().setCodigoOficina(
						oficina.getCode());
				registro.getOficinaRegistro().setName(oficina.getName());
			}

			registro.setEstado(EstadoRegistroEnum.getEnum(folderDataSession
					.getNewAttributeValueAsInteger(
							ConstantKeys.FLD_KEY_ESTADO.toLowerCase())
					.intValue()));

			registro.setFechaRegistro((Date) folderDataSession.getAxsfNew()
					.getAttributeValue(AxSf.FLD2_FIELD));
			registro.setFechaAlta((Date) folderDataSession.getAxsfNew()
					.getAttributeValue(AxSf.FLD4_FIELD));
			registro.setIdRegistro(String.valueOf(folderDataSession.getAxsfNew()
					.getAttributeValue(AxSf.FDRID_FIELD)));

			registro.getUsuarioRegistro().setLoginName(
					(String) folderDataSession.getAxsfNew().getAttributeValue(
							AxSf.FLD3_FIELD));

		}

		return registro;
	}

}
