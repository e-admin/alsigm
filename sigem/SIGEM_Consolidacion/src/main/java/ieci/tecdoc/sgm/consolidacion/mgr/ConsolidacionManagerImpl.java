package ieci.tecdoc.sgm.consolidacion.mgr;

import ieci.tecdoc.sgm.consolidacion.config.ConfigLoader;
import ieci.tecdoc.sgm.consolidacion.config.ConsolidacionConfig;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.MensajesCortosException;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.FieldInfo;
import ieci.tecdoc.sgm.core.services.registro.PersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterInfo;
import ieci.tecdoc.sgm.core.services.registro.ServicioRegistro;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;
import ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.telematico.Registro;
import ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.RegistroEstado;
import ieci.tecdoc.sgm.core.services.telematico.Registros;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.registropresencial.utils.RBUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jconfig.ConfigurationLoader;

public class ConsolidacionManagerImpl implements ConsolidacionManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ConsolidacionManagerImpl.class);

	/**
	 * Estado de registro presencial: COMPLETO
	 */
	private static final String AXSF_COMPLETE_STATE = "0";

	/**
	 * Servicio de gestión del Registro Telemático.
	 */
	private ServicioRegistroTelematico servicioRegistroTelemático = null;

	/**
	 * Servicio de gestión de documentos del Registro Telemático.
	 */
	private ServicioRepositorioDocumentosTramitacion servicioRde = null;

	/**
	 * Servicio de gestión del Registro Presencial.
	 */
	private ServicioRegistro servicioRegistro = null;

	/**
	 * Configuración con soporte multientidad.
	 */
	protected ConsolidacionConfig config=new ConsolidacionConfig();

	/**
	 * Locale de usuario presencial
	 */
	private Locale registryUserLocale = null;
	private SimpleDateFormat longFormatter = null;
	private SimpleDateFormat shortFormatter = null;

	/**
	 * Constructor.
	 * 
	 * @throws SigemException
	 *             si ocurre algún error.
	 */
	public ConsolidacionManagerImpl() throws SigemException {
		super();

		this.servicioRegistroTelemático = LocalizadorServicios.getServicioRegistroTelematico();
		this.servicioRegistro = LocalizadorServicios.getServicioRegistro();
		this.servicioRde = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();

		// Formateador de fechas en formato largo
		longFormatter = new SimpleDateFormat(RBUtil.getInstance(null).getProperty(
				ieci.tecdoc.sgm.registropresencial.utils.Keys.I18N_DATE_LONGFORMAT));
		longFormatter.setLenient(false);

		// Formateador de fechas en formato corto
		shortFormatter = new SimpleDateFormat(RBUtil.getInstance(null).getProperty(
				ieci.tecdoc.sgm.registropresencial.utils.Keys.I18N_DATE_SHORTFORMAT));
		shortFormatter.setLenient(false);
	}

	/**
	 * Realiza el proceso de consolidación sobre una entidad.
	 * 
	 * @param idEntidad
	 *            Identificador de la entidad.
	 * @throws SigemException
	 *             si ocurre algún error.
	 */
	public void consolidaEntidad(String idEntidad) throws SigemException {

		try {
			logger.info("Comienzo del proceso de consolidación en la entidad " + idEntidad);

			// Información de la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(idEntidad);

			// Criterios de búsqueda
			RegistroConsulta criteria = new RegistroConsulta();
			criteria.setStatus(RegistroEstado.STATUS_NOT_CONSOLIDATED);

			// Obtener los registros telemáticos a consolidar
			Registros registros = servicioRegistroTelemático.query(null, criteria, entidad);
			if (logger.isInfoEnabled()) {
				logger.info(registros.count()
						+ " registro/s telemático/s encontrado/s para la consolidación.");
			}

			// Procesar cada registro telemático
			for (int i = 0; i < registros.count(); i++) {
				consolidaRegistro(registros.get(i), entidad);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Proceso de consolidación finalizado en entidad " + idEntidad);
			}
		} catch (Throwable t) {
			logger.error("Error en la consolidación de la entidad " + idEntidad, t);
		}
	}

	/**
	 * Consolida un registro telemático.
	 * 
	 * @param registro
	 *            Información del registro telemático.
	 * @param entidad
	 *            Información de la entidad.
	 * @throws SigemException
	 *             si ocurre algún error.
	 */
	protected void consolidaRegistro(Registro registro, Entidad entidad) throws SigemException {

		// TODO: Modificar este método para enviar e-mail si ocurre excepción
		try {
			if (logger.isInfoEnabled()) {
				logger.info("Inicio del proceso de consolidación del registro ["
						+ registro.getRegistryNumber() + "] en la entidad ["
						+ entidad.getIdentificador() + "]");
			}

			// Obtener los ficheros del registro telemático
			RegistroDocumentos documentos = servicioRegistroTelemático.obtenerDocumentosRegistro(
					registro.getRegistryNumber(), entidad);
			if (logger.isInfoEnabled()) {
				logger.info("El registro telemático " + registro.getRegistryNumber() + " tiene "
						+ documentos.count() + " documento/s");
			}
			String idEntidad=entidad.getIdentificador();
			cargarConfiguracion(idEntidad);
			
			// Información del usuario de registro presencial
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(config.getUserName());
			userInfo.setPassword(config.getPassword());

			if (userInfo.getLocale() == null) {
				userInfo.setLocale(Locale.getDefault());
			} else {
				userInfo.setLocale(registryUserLocale);
			}

			// Información de los intervinientes
			PersonInfo[] personInfos = getPersons(registro,idEntidad);

			// Consolidar el registro presencial
			RegisterInfo regInfo = servicioRegistro.consolidateFolder(userInfo,
					new Integer(config.getBookId()), getFolderAttributes(registro, personInfos,idEntidad), personInfos,
					getDocuments(documentos, entidad), entidad);

			if (logger.isInfoEnabled()) {
				logger.info("Registro presencial creado correctamente: " + regInfo.getNumber());
			}

			// Cambiar el estado del registro telemático de no consolidado a
			// consolidado
			servicioRegistroTelemático.establecerEstadoRegistro(registro.getRegistryNumber(),
					RegistroEstado.STATUS_CONSOLIDATED, entidad);

			if (logger.isInfoEnabled()) {
				logger.info("Fin del proceso de consolidación del registro ["
						+ registro.getRegistryNumber() + "] en la entidad ["
						+ entidad.getIdentificador() + "]");
			}
		} catch (Throwable t) {
			
			String message = "Error en la consolidación del registro " + registro.getRegistryNumber()
					+ " de la entidad " + entidad.getIdentificador();
			
			
			logger.error(message, t);

			// Se envía el e-mail de error en la consolidación
			sendEmailErrorConsolidacion(message, t);
		}
	}

	private void sendEmailErrorConsolidacion(String message, Throwable t) throws SigemException,
			MensajesCortosException {
		//Nota: El objeto config ya se encuentra cargado (Este metodo es privado, implica que se ha cargado desde uno público).
		if (!isBlank(config.getErrorEnvioEmailOrigen()) && 
			!isBlank(config.getErrorEnvioEmailDestino()) && 
			!isBlank(config.getErrorEnvioEmailAsunto())) {

			StringBuffer content = new StringBuffer();
			content.append(message+"\n");
			content.append("Excepción: \n");
			content.append(t.getMessage()+"\n");
			content.append(t.getCause());
			content.append(getStackTrace(t));
			
			if (logger.isInfoEnabled()) {
				logger.info("Se procede a enviar la notificación del error en la consolidación del registro.\n E-mail Origen: "
						+ config.getErrorEnvioEmailOrigen()
						+ "\n E-mail Destino: "
						+ config.getErrorEnvioEmailDestino()
						+ "\n Asunto E-mail: "
						+ config.getErrorEnvioEmailAsunto()
						+ "\n Contenido E-mail: "
						+content.toString());
			}
			// Enviar el e-mail de error de consolidación
			ServicioMensajesCortos servicioMensajesCortos = LocalizadorServicios
					.getServicioMensajesCortos();

			String[] emailDestinoArray = {config.getErrorEnvioEmailDestino()};
			
			servicioMensajesCortos.sendMail(config.getErrorEnvioEmailOrigen(), 
					emailDestinoArray, null, null,
					config.getErrorEnvioEmailAsunto(), content.toString(), null);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("No se envía el e-mail de notificación del error en la consolidación del registro porque así se ha configurado al dejar vacíos las propiedades emailOrigen, emailDestino y emailAsunto");
			}
		}
	}
	
	public boolean isBlank(String cadena){
		if (cadena == null || cadena.equals(""))
			return true;
		return false;
	}

	protected FieldInfo[] getFolderAttributes(Registro registro, PersonInfo[] personInfos,String idEntidad) {

		// Nombre del interviniente principal
		String personName = null;
		if (!ArrayUtils.isEmpty(personInfos)) {
			personName = personInfos[0].getPersonName();
		}

		// Comprobación de la información adicional
		String additionalInfo = null;
		if (registro.getAdditionalInfo() != null) {
			additionalInfo = new String(registro.getAdditionalInfo());
		}

		// Comprobación de la fecha efectiva
		Date effectiveDate = registro.getEffectiveDate();
		if (effectiveDate == null) {
			effectiveDate = registro.getRegistryDate();
		}

		cargarConfiguracion(idEntidad);
		// Código de asunto del registro
		String topic = registro.getTopic();
		if (StringUtils.isBlank(topic)) {
			topic = config.getDefaultCACode();
		}

		/*
		 * fld1 => Número de registro fld2 => Fecha de registro fld3 => Usuario
		 * fld4 => Fecha de trabajo fld5 => Oficina de registro fld6 => Estado
		 * fld7 => Origen fld8 => Destino fld9 => Remitentes fld10 => Nº.
		 * registro original fld11 => Tipo de registro original fld12 => Fecha
		 * de registro original fld13 => Registro original fld14 => Tipo de
		 * transporte fld15 => Número de transporte fld16 => Tipo de asunto
		 * fld17 => Resumen fld18 => Comentario fld19 => Referencia de
		 * Expediente fld20 => Fecha del documento
		 */
		return new FieldInfo[] {
				getFieldInfo("1", registro.getRegistryNumber()),
				getFieldInfo("2",
						(effectiveDate != null ? longFormatter.format(effectiveDate) : "")),
				getFieldInfo("3", config.getUserName()),
				getFieldInfo(
						"4",
						(registro.getRegistryDate() != null ? shortFormatter.format(registro
								.getRegistryDate()) : "")),
				getFieldInfo("5", registro.getOficina()), getFieldInfo("6", AXSF_COMPLETE_STATE),
				getFieldInfo("8", registro.getAddressee()), getFieldInfo("9", personName),
				getFieldInfo("16", topic), getFieldInfo("18", additionalInfo),
				getFieldInfo("19", registro.getNumeroExpediente()) };
	}

	protected PersonInfo[] getPersons(Registro registro,String idEntidad) {
		PersonInfo[] persons = null;

		if (registro != null) {

			// Componer el nombre del interviniente
			String personName = StringUtils.trim(StringUtils.defaultString(registro.getSenderId(),
					"") + " " + StringUtils.defaultString(registro.getName(), ""));

			cargarConfiguracion(idEntidad);
			if (personName.length() > config.getMaxLength()) {

				String person1Name = StringUtils.trim(StringUtils.substringBeforeLast(
						StringUtils.substring(personName, 0, config.getMaxLength()), " "));
				String person2Name = StringUtils.trim(StringUtils.substringAfterLast(personName,
						person1Name));

				persons = new PersonInfo[] { getPersonInfo(person1Name), getPersonInfo(person2Name) };

			} else {
				persons = new PersonInfo[] { getPersonInfo(personName) };
			}
		}

		return persons;
	}

	protected DocumentInfo[] getDocuments(RegistroDocumentos documentos, Entidad entidad)
			throws SigemException {

		DocumentInfo[] docs = new DocumentInfo[documentos.count()];

		for (int i = 0; i < documentos.count(); i++) {

			// Información del documento en el registro telemático
			RegistroDocumento documento = documentos.get(i);

			// Información del contenedor del documento
			ContenedorDocumento contenedor = servicioRde.retrieveDocumentInfo("",
					documento.getGuid(), entidad);

			// Información del documento en el registro presencial
			docs[i] = new DocumentInfo();
			docs[i].setDocumentName(documento.getCode());
			docs[i].setExtension(contenedor.getExtension().toLowerCase());
			docs[i].setDocumentContent(contenedor.getContent());
			docs[i].setFileName(documento.getRegistryNumber() + "_" + documento.getCode() + "_" + i);
			docs[i].setPageName(documento.getCode() + "." + contenedor.getExtension().toLowerCase());
		}

		return docs;
	}

	protected static PersonInfo getPersonInfo(String personName) {
		PersonInfo person = new PersonInfo();
		person.setPersonName(personName);
		// person.setPersonId(null);
		// person.setDomId(null);
		// person.setDirection(null);
		return person;
	}

	protected static FieldInfo getFieldInfo(String fieldId, String value) {
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo.setFieldId(fieldId);
		fieldInfo.setValue(value);
		return fieldInfo;
	}
	
	/**
	 * Devuelve la pila de la traza de la excepción como un string
	 * @param aThrowable Excepción
	 * @return	La pila de la traza de la excepción como un string
	 */
	 private String getStackTrace(Throwable aThrowable) {
		    final Writer result = new StringWriter();
		    final PrintWriter printWriter = new PrintWriter(result);
		    aThrowable.printStackTrace(printWriter);
		    return result.toString();
		  }
	 
	 private void cargarConfiguracion(String idEntidad){
		 if(idEntidad==null ) return;
		 config=ConfigLoader.getInstance().getConfig(idEntidad);
	 }
}
