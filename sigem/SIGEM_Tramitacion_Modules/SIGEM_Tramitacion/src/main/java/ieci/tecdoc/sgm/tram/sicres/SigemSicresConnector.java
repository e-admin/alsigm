package ieci.tecdoc.sgm.tram.sicres;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.Organization;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterBook;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterData;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOffice;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOrigin;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.Subject;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.sicres.vo.Transport;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.registro.BookInfo;
import ieci.tecdoc.sgm.core.services.registro.DistributionInfo;
import ieci.tecdoc.sgm.core.services.registro.Document;
import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentQuery;
import ieci.tecdoc.sgm.core.services.registro.FieldInfo;
import ieci.tecdoc.sgm.core.services.registro.OfficeInfo;
import ieci.tecdoc.sgm.core.services.registro.Page;
import ieci.tecdoc.sgm.core.services.registro.PersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterQueryInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo;
import ieci.tecdoc.sgm.core.services.registro.RegistroException;
import ieci.tecdoc.sgm.core.services.registro.ServicioRegistro;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;
import ieci.tecdoc.sgm.core.services.rpadmin.Libro;
import ieci.tecdoc.sgm.core.services.rpadmin.OrganizacionBean;
import ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Implementación del conector con SICRES en SIGEM.
 *
 */
public class SigemSicresConnector implements ISicresConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(SigemSicresConnector.class);

	/**
	 * Autenticación LDAP + SSO: variable global
	 * USER_PROPERTY_SICRES_CONNECTOR_LOGIN en SPAC_VARS que contenga el nombre
	 * de la propiedad del usuario a buscar, para establecerse como login en la
	 * funcionalidad de registro a invocar por el conector.
	 */
	public static final String CONFIG_VAR_USER_PROPERTY_SICRES_CONNECTOR_LOGIN = "USER_PROPERTY_SICRES_CONNECTOR_LOGIN";

	/**
	 * Valor por defecto en LDAP para la variable global anterior de
	 * USER_PROPERTY_SICRES_CONNECTOR_LOGIN en SPAC_VARS.
	 */
	protected static final String DEFAULT_USER_PROPERTY_SICRES_CONNECTOR_LOGIN_VALUE = "sAMAccountName";

	/**
	 * Formateador de fechas.
	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd-MM-yyyy");

	/**
	 * Identificador del libro de salida por defecto.
	 */
	private static final int DEFAULT_OUTPUT_BOOK_ID = 2;

	/**
	 * Contexto de cliente.
	 */
	private ClientContext ctx = null;

	/**
	 * Servicio de acceso a SICRES.
	 */
	private ServicioRegistro servicioRegistro = null;

	/**
	 * Servicio de acceso a la administración de SICRES.
	 */
	private ServicioRPAdmin servicioAdmRegistro = null;

	/**
	 * Constructor.
	 *
	 * @param ctx
	 *            Contexto de cliente.
	 * @exception ISPACException
	 *                si ocurre algún error.
	 */
	public SigemSicresConnector(ClientContext ctx) throws ISPACException {
		super();
		this.ctx = ctx;

		try {
			// Servicio de Registro Presencial
			servicioRegistro = LocalizadorServicios.getServicioRegistro();
		} catch (SigemException e) {
			logger.error("Error al crear el servicio de registro", e);
			throw new ISPACException("Error al crear el servicio de registro",
					e);
		}

		try {
			// Servicio de Administración de Registro Presencial
			servicioAdmRegistro = LocalizadorServicios.getServicioRPAdmin();
		} catch (SigemException e) {
			logger.error(
					"Error al crear el servicio de administración de registro",
					e);
			throw new ISPACException(
					"Error al crear el servicio de administración de registro",
					e);
		}
	}

	/**
	 * Obtiene el número de registros distribuidos asociados al usuario
	 * conectado.
	 *
	 * @return Número de registros distribuidos.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public int countIntrais() throws ISPACException {
		int count = 0;

		try {

			UserInfo userInfo = getUserInfo();
			Entidad entidad = getEntidad();

			Integer countPendingIntrais = servicioRegistro
					.countInputDistribution(userInfo, new Integer(1),
							new Integer(Libro.LIBRO_ENTRADA), Boolean.FALSE,
							entidad);

			Integer countAcceptedIntrais = servicioRegistro
					.countInputDistribution(userInfo, new Integer(2),
							new Integer(Libro.LIBRO_ENTRADA), Boolean.FALSE,
							entidad);

			count = TypeConverter.toInt(countPendingIntrais, 0)
					+ TypeConverter.toInt(countAcceptedIntrais, 0);

		} catch (RegistroException e) {
			logger.error("Error al contar los registros distribuidos", e);
			// throw new
			// ISPACException("Error al contar los registros distribuidos", e);
		}

		return count;
	}

	/**
	 * Obtiene la lista de registros distribuidos asociados al usuario
	 * conectado.
	 *
	 * @return Lista de registros distribuidos ({@link Intray}).
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public List getIntrays() throws ISPACException {
		try {

			UserInfo userInfo = getUserInfo();
			Entidad entidad = getEntidad();

			DistributionInfo[] distInfos1 = servicioRegistro
					.getInputDistribution(userInfo, new Integer(1),
							new Integer(0), new Integer(Integer.MAX_VALUE),
							new Integer(Libro.LIBRO_ENTRADA), Boolean.FALSE,
							entidad);

			DistributionInfo[] distInfos2 = servicioRegistro
					.getInputDistribution(userInfo, new Integer(2),
							new Integer(0), new Integer(Integer.MAX_VALUE),
							new Integer(Libro.LIBRO_ENTRADA), Boolean.FALSE,
							entidad);

			DistributionInfo[] intrais = (DistributionInfo[]) ArrayUtils
					.concat(distInfos1, distInfos2);

			return getIntrais(intrais);

		} catch (RegistroException e) {
			logger.error("Error al obtener los registros distribuidos", e);
			throw new ISPACInfo(e.getExtendedMessage(ctx.getLocale()));
		}
	}

	/**
	 * Obtiene la información del registro distribuido.
	 *
	 * @param intrayId
	 *            Identificador del registro distribuido.
	 * @return Información del registro distribuido.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public Intray getIntray(String intrayId) throws ISPACException {
		Intray intray = null;

		try {
			intray = getIntray(getDistributionInfo(intrayId));
		} catch (RegistroException e) {
			logger.error("Error al obtener el registro distribuido ["
					+ intrayId + "]", e);
			throw new ISPACInfo(e.getExtendedMessage(ctx.getLocale()));
		}

		return intray;
	}

	/**
	 * Acepta un registro distribuido.
	 *
	 * @param intrayId
	 *            Identificador del registro distribuido.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void acceptIntray(String intrayId) throws ISPACException {
		try {
			if (StringUtils.isNotBlank(intrayId)) {
				servicioRegistro.acceptDistribution(getUserInfo(), new Integer(
						TypeConverter.parseInt(intrayId, -1)), getEntidad());
			}
		} catch (RegistroException e) {
			logger.error("Error al aceptar el registro distribuido ["
					+ intrayId + "]", e);
			throw new ISPACInfo(e.getExtendedMessage(ctx.getLocale()));
		}
	}

	/**
	 * Rechaza un registro distribuido.
	 *
	 * @param intrayId
	 *            Identificador del registro distribuido.
	 * @param reason
	 *            Motivo del rechazo.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void rejectIntray(String intrayId, String reason)
			throws ISPACException {
		try {
			if (StringUtils.isNotBlank(intrayId)) {
				servicioRegistro.rejectDistribution(getUserInfo(), new Integer(
						TypeConverter.parseInt(intrayId, -1)), reason,
						getEntidad());
			}
		} catch (RegistroException e) {
			logger.error("Error al rechazar el registro distribuido ["
					+ intrayId + "]", e);
			throw new ISPACInfo(e.getExtendedMessage(ctx.getLocale()));
		}
	}

	/**
	 * Archiva un registro distribuido.
	 *
	 * @param intrayId
	 *            Identificador del registro distribuido.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void archiveIntray(String intrayId) throws ISPACException {
		try {
			if (StringUtils.isNotBlank(intrayId)) {
				servicioRegistro.archiveDistribution(getUserInfo(),
						new Integer(TypeConverter.parseInt(intrayId, -1)),
						getEntidad());
			}
		} catch (RegistroException e) {
			logger.error("Error al archivar el registro distribuido ["
					+ intrayId + "]", e);
			throw new ISPACInfo(e.getExtendedMessage(ctx.getLocale()));
		}
	}

	/**
	 * Obtiene los anexos del registro de entrada.
	 *
	 * @param intrayId
	 *            Identificador del registro distribuido.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public Annexe[] getAnnexes(String intrayId) throws ISPACException {
		Annexe[] annexes = null;

		try {
			DistributionInfo distInfo = getDistributionInfo(intrayId);
			if (distInfo != null) {
				RegisterWithPagesInfo regInfo = null;
				if (distInfo.getBookType().intValue() == Libro.LIBRO_SALIDA) {
					regInfo = getRegisterWithPagesInfo(RegisterType.SALIDA,
							distInfo.getRegisterNumber());
				} else {
					regInfo = getRegisterWithPagesInfo(RegisterType.ENTRADA,
							distInfo.getRegisterNumber());
				}
				annexes = getAnnexes(regInfo);
			}
		} catch (RegistroException e) {
			logger.error(
					"Error al obtener los anexos del registro distribuido ["
							+ intrayId + "]", e);
			throw new ISPACException(
					"Error al obtener los anexos del registro distribuido", e);
		}

		return annexes;
	}

	/**
	 * Descarga un anexo del registro de entrada.
	 *
	 * @param intrayId
	 *            Identificador del registro distribuido.
	 * @param annexeId
	 *            identificador del anexo.
	 * @param out
	 *            stream de salida.
	 * @throws ISPACException
	 */
	public void getAnnexe(String intrayId, String annexeId, OutputStream out)
			throws ISPACException {
		try {

			Annexe annexe = getAnnexe(intrayId, annexeId);
			if (annexe != null) {
				DocumentQuery doc = servicioRegistro.getDocumentFolder(
						getUserInfo(), TypeConverter.parseInteger(annexe
								.getBookId(), new Integer(0)), TypeConverter
								.parseInteger(annexe.getFolderId(),
										new Integer(0)),
						TypeConverter.parseInteger(annexe.getDocId(),
								new Integer(0)), TypeConverter.parseInteger(
								annexe.getPageId(), new Integer(0)),
						getEntidad());
				if (doc != null) {
					out.write(doc.getContent());
					out.flush();
					out.close();
				}
			}
		} catch (ISPACException e) {
			logger.error("Error al obtener el anexo del registro distribuido ["
					+ intrayId + "] - [" + annexeId + "]", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al obtener el anexo del registro distribuido ["
					+ intrayId + "] - [" + annexeId + "]", e);
			throw new ISPACException(
					"Error al obtener el anexo del registro distribuido", e);
		}
	}

	public Annexe[] getAnnexes(String registerNumber, String registerType)
			throws ISPACException {

		RegisterWithPagesInfo regInfo;
		try {
			regInfo = getRegisterWithPagesInfo(registerType, registerNumber);
		} catch (RegistroException e) {
			logger.error("Error al obtener los anexos del registro de '"
					+ registerType + "' [" + registerNumber + "]", e);
			throw new ISPACException(
					"Error al obtener los anexos del registro de '"
							+ registerType + "' [" + registerNumber + "]", e);
		}
		return getAnnexes(regInfo);
	}

	public void getAnnexe(Annexe annexe, OutputStream out)
			throws ISPACException {
		try {
			if (annexe != null) {
				DocumentQuery doc = servicioRegistro.getDocumentFolder(
						getUserInfo(), TypeConverter.parseInteger(annexe
								.getBookId(), new Integer(0)), TypeConverter
								.parseInteger(annexe.getFolderId(),
										new Integer(0)),
						TypeConverter.parseInteger(annexe.getDocId(),
								new Integer(0)), TypeConverter.parseInteger(
								annexe.getPageId(), new Integer(0)),
						getEntidad());
				if (doc != null) {
					out.write(doc.getContent());
					out.flush();
					out.close();
				}
			}
		} catch (ISPACException e) {
			logger.error("Error al obtener el anexo del registro ["
					+ annexe.getName() + "] - [" + annexe.getId() + "]", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al obtener el anexo del registro ["
					+ annexe.getName() + "] - [" + annexe.getId() + "]", e);
			throw new ISPACException("Error al obtener el anexo del registro",
					e);
		}
	}

	protected Annexe getAnnexe(String intrayId, String annexeId)
			throws ISPACException {
		Annexe[] annexes = getAnnexes(intrayId);
		if (!ArrayUtils.isEmpty(annexes)) {
			for (int i = 0; i < annexes.length; i++) {
				if (StringUtils.equalsNullEmpty(annexes[i].getId(), annexeId)) {
					return annexes[i];
				}
			}
		}
		return null;
	}

	/**
	 * Indica si el usuario conectado tiene permisos para registrar.
	 *
	 * @param registerType
	 *            Tipo de registro: @see RegisterType
	 * @return true si el usuario puede registrar.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public boolean canRegister(String registerType) throws ISPACException {

		Boolean res = null;

		try {
			if (RegisterType.SALIDA.equalsIgnoreCase(registerType)) {
				res = servicioRegistro.canCreateRegister(getUserInfo(),
						new Integer(Libro.LIBRO_SALIDA), new Integer(0),
						new Integer(1), getEntidad());
			} else {
				res = servicioRegistro.canCreateRegister(getUserInfo(),
						new Integer(Libro.LIBRO_ENTRADA), new Integer(0),
						new Integer(1), getEntidad());
			}
		} catch (RegistroException e) {
			logger.error(
					"Error al comprobar si el usuario tiene permisos para registrar ["
							+ registerType + "]", e);
			throw new ISPACException(
					"Error al comprobar si el usuario tiene permisos para registrar ["
							+ registerType + "]", e);
		}

		return TypeConverter.toBoolean(res, false);
	}

	public RegisterInfo insertRegister(Register register) throws ISPACException {

		RegisterInfo registerInfo = null;

		try {

			if (register != null) {

				PersonInfo addressee = null;

				// Destinatario
				if ((register.getRegisterData() != null)
						&& !ArrayUtils.isEmpty(register.getRegisterData()
								.getParticipants())) {

					ThirdPerson destiny = register.getRegisterData()
							.getParticipants()[0];
					addressee = new PersonInfo();
					addressee.setPersonId(destiny.getId());
					addressee.setPersonName(destiny.getName());
				}

				// Identificador del libro de salida
				int bookId = DEFAULT_OUTPUT_BOOK_ID;
				if (register.getOriginalRegister() != null) {
					bookId = TypeConverter.parseInt(register
							.getOriginalRegister().getBookId(),
							DEFAULT_OUTPUT_BOOK_ID);
				}

				// Campos
				List fieldInfoList = new ArrayList();

				// Oficina de registro
				RegisterOffice registerOffice = register.getOriginalRegister()
						.getRegisterOffice();
				if (registerOffice != null) {
					fieldInfoList.add(createFieldInfo("5", registerOffice
							.getCode()));
				}

				// Origen del registro
				// fieldInfoList.add(createFieldInfo("7", origUnitCode)); //
				// Origen
				Organization orig = register.getOriginOrganization();
				if (orig != null) {
					fieldInfoList.add(createFieldInfo("7", orig.getCode()));
				}

				// Destino del registro
				// fieldInfoList.add(createFieldInfo("8", destUnitCode)); //
				// Destino
				Organization dest = register.getDestinationOrganization();
				if (dest != null) {
					fieldInfoList.add(createFieldInfo("8", dest.getCode()));
				}

				// Destinatario del registro
				if (addressee != null) {
					fieldInfoList.add(createFieldInfo("9", addressee
							.getPersonName()));
				}

				// Resumen del registro
				if (register.getRegisterData() != null) {
					fieldInfoList.add(createFieldInfo("13", register
							.getRegisterData().getSummary()));
				}

				// Destinatarios
				PersonInfo[] personInfos = null;
				if (addressee != null) {
					personInfos = new PersonInfo[] { addressee };
				}

				// Documentos
				ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo[] docs = register
						.getRegisterData().getInfoDocuments();

				DocumentInfo[] docInfos = convertDocumentInfo(docs);

				ieci.tecdoc.sgm.core.services.registro.RegisterInfo sgmRegisterInfo = servicioRegistro
						.createFolder(getUserInfo(), new Integer(bookId),
								(FieldInfo[]) fieldInfoList
										.toArray(new FieldInfo[fieldInfoList
												.size()]), personInfos,
								docInfos, getEntidad());

				registerInfo = getRegisterInfo(sgmRegisterInfo);
			}
		} catch (RegistroException e) {
			logger.warn("Error al insertar el registro", e);
		} catch (Throwable e) {
			logger.error("Error al insertar el registro", e);
			throw new ISPACException("Error al insertar el registro", e);
		}

		return registerInfo;
	}

	private DocumentInfo[] convertDocumentInfo(
			ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo[] docs) {
		if (docs == null || docs.length == 0) {
			return null;
		}
		DocumentInfo[] result = new DocumentInfo[docs.length];
		for (int i = 0; i < docs.length; i++) {
			DocumentInfo doc = new DocumentInfoAdapter(docs[i]);
			result[i] = doc;
		}
		return result;
	}

	private ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo[] convertDocument2DocumentInfo(
			Document[] docs) {
		if (docs == null || docs.length == 0) {
			return null;
		}
		List<ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo> documents = new ArrayList<ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo>();
		for (int i = 0; i < docs.length; i++) {
			List<Page> pages = docs[i].getPages();
			if (pages == null || pages.size() == 0) {
				continue;
			}
			for (Iterator iterator = pages.iterator(); iterator.hasNext();) {
				Page page = (Page) iterator.next();
				ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo doc = new DocumentInfoVOAdapter(
						page);
				documents.add(doc);

			}
		}

		return (ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo[]) documents
				.toArray(new ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo[documents
						.size()]);
	}

	private static FieldInfo createFieldInfo(String fieldId, String value) {
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo.setFieldId(fieldId);
		fieldInfo.setValue(value);
		return fieldInfo;
	}

	private static RegisterInfo getRegisterInfo(
			ieci.tecdoc.sgm.core.services.registro.RegisterInfo regInfo) {
		RegisterInfo registerInfo = null;

		if (regInfo != null) {

			RegisterOffice regOffice = null;
			if (StringUtils.isNotBlank(regInfo.getOffice())) {
				regOffice = new RegisterOffice();
				regOffice.setCode(regInfo.getOffice());
				regOffice.setName(regInfo.getOfficeName());
			}

			registerInfo = new RegisterInfo(regOffice, regInfo.getNumber(),
					DateUtil.getCalendar(regInfo.getDate(),
							"dd-MM-yyyy HH:mm:ss"), RegisterType.SALIDA);
		}

		return registerInfo;
	}

	/**
	 * Obtiene la información completa de un registro.
	 *
	 * @param registerInfo
	 *            Información básica del registro.
	 * @return Información completa del registro.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public Register readRegister(RegisterInfo registerInfo)
			throws ISPACException {

		Register register = null;

		try {
			if ((registerInfo != null)
					&& StringUtils.isNotBlank(registerInfo.getRegisterNumber())) {

				RegisterWithPagesInfo regInfo = null;
				if (RegisterType.SALIDA.equalsIgnoreCase(registerInfo
						.getRegisterType())) {
					regInfo = servicioRegistro.getOutputFolderForNumber(
							getUserInfo(), null, registerInfo
									.getRegisterNumber(), getEntidad());
					register = getRegister(regInfo, RegisterType.SALIDA);
				} else {
					regInfo = servicioRegistro.getInputFolderForNumber(
							getUserInfo(), null, registerInfo
									.getRegisterNumber(), getEntidad());
					register = getRegister(regInfo, RegisterType.ENTRADA);
				}
			}

		} catch (RegistroException e) {
			logger.warn("No se ha encontrado el registro ["
					+ registerInfo.getRegisterNumber() + "]", e);
		} catch (Exception e) {
			logger.error("Error al leer el registro ["
					+ registerInfo.getRegisterNumber() + "]", e);
			throw new ISPACException("Error al leer el registro", e);
		}

		return register;
	}

	protected RegisterWithPagesInfo getRegisterWithPagesInfo(
			String registerType, String registerNumber)
			throws RegistroException {

		RegisterWithPagesInfo regInfo = null;

		if (StringUtils.isNotBlank(registerNumber)) {
			if (RegisterType.SALIDA.equalsIgnoreCase(registerType)) {
				regInfo = servicioRegistro.getOutputFolderForNumber(
						getUserInfo(), null, registerNumber, getEntidad());
			} else {
				regInfo = servicioRegistro.getInputFolderForNumber(
						getUserInfo(), null, registerNumber, getEntidad());
			}
		}

		return regInfo;
	}

	protected Register getRegister(RegisterWithPagesInfo regWithPagesInfo,
			String registerType) throws Exception {
		Register register = null;

		if (regWithPagesInfo != null) {

			register = new Register();

			RegisterOffice registerOffice = new RegisterOffice();

			RegisterOrigin registerOrigin = new RegisterOrigin();
			registerOrigin.setRegisterOffice(registerOffice);
			registerOrigin.setRegisterType(registerType);

			RegisterInfo originalRegister = new RegisterInfo();

			Organization originOrganization = new Organization();
			Organization destinationOrganization = new Organization();

			RegisterData registerData = new RegisterData();

			registerData
					.setInfoDocuments(convertDocument2DocumentInfo(regWithPagesInfo
							.getDocInfo()));

			Transport transport = new Transport();

			register.setRegisterOrigin(registerOrigin);
			register.setOriginalRegister(originalRegister);
			register.setOriginOrganization(originOrganization);
			register.setDestinationOrganization(destinationOrganization);
			register.setRegisterData(registerData);
			register.setTransport(transport);

			// Integer bookId = null;
			// int fdrId = 0;

			// Información del registro
			RegisterQueryInfo[] regQueryInfos = regWithPagesInfo.getRqInfo();
			for (int i = 0; i < regQueryInfos.length; i++) {

				// if
				// ("bookId".equalsIgnoreCase(regQueryInfos[i].getFolderName()))
				// {
				//
				// String bookIdDesc = regQueryInfos[i].getValue();
				// String[] tokens = StringUtils.split(bookIdDesc, " - ");
				// if (!ArrayUtils.isEmpty(tokens)) {
				// int bookIdInt = TypeConverter.parseInt(tokens[0], 0);
				// if (bookIdInt > 0) {
				// bookId = new Integer(bookIdInt);
				// }
				// }
				//
				// } else if
				// ("fdrId".equalsIgnoreCase(regQueryInfos[i].getFolderName()))
				// {
				//
				// fdrId = TypeConverter.parseInt(regQueryInfos[i].getValue(),
				// 0);

				if (RegisterType.ENTRADA.equalsIgnoreCase(registerType)) {

					if ("fld1".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Número de registro
						registerOrigin.setRegisterNumber(regQueryInfos[i]
								.getValue());

					} else if ("fld2".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Fecha de registro
						registerOrigin
								.setRegisterDate(getRegisterDate(regQueryInfos[i]
										.getValue()));

					} else if ("fld3".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Usuario
						registerOrigin.setUser(regQueryInfos[i].getValue());

					} else if ("fld4".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Fecha trabajo
						registerOrigin
								.setCreationDate(getRegisterDate(regQueryInfos[i]
										.getValue()));

					} else if ("fld5".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							// Código de la oficina de registro
							registerOffice.setCode(tokens[0].trim());

							// Nombre de la oficina de registro
							if (tokens.length > 1) {
								registerOffice.setName(tokens[1].trim());
							}
						}

					} else if ("fld6".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Estado de registro

					} else if ("fld7".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							// Código del origen de registro
							originOrganization.setCode(tokens[0].trim());

							// Nombre del origen de registro
							if (tokens.length > 1) {
								originOrganization.setName(tokens[1].trim());
							}
						}

					} else if ("fld8".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							// Código del destino de registro
							destinationOrganization.setCode(tokens[0].trim());

							// Nombre del destino de registro
							if (tokens.length > 1) {
								destinationOrganization.setName(tokens[1]
										.trim());
							}

						}
					} else if ("fld9".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Remitentes

					} else if ("fld10".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Numero de Registro Original
						originalRegister.setRegisterNumber(regQueryInfos[i]
								.getValue());

					} else if ("fld11".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Tipo de Registro Original
						String type = regQueryInfos[i].getValue();
						if (String.valueOf(Libro.LIBRO_SALIDA)
								.equalsIgnoreCase(type)) {
							originalRegister.setRegisterType("SALIDA");
						} else if (String.valueOf(Libro.LIBRO_ENTRADA)
								.equalsIgnoreCase(type)) {
							originalRegister.setRegisterType("ENTRADA");
						}

					} else if ("fld12".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Fecha de Registro Original
						originalRegister
								.setRegisterDate(getRegisterDate(regQueryInfos[i]
										.getValue()));

					} else if ("fld13".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Entidad Registral Original

					} else if ("fld14".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Tipo de transporte
						transport.setType(regQueryInfos[i].getValue());

					} else if ("fld15".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Número de transporte
						transport.setNumber(regQueryInfos[i].getValue());

					} else if ("fld16".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							Subject subject = new Subject();

							// Código del tipo de asunto
							subject.setCode(tokens[0].trim());

							// Nombre del tipo de asunto
							if (tokens.length > 1) {
								subject.setName(tokens[1].trim());
							}

							// Asunto
							registerData.setSubject(subject);
						}

					} else if ("fld17".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Resumen
						registerData.setSummary(regQueryInfos[i].getValue());

					} else if ("fld18".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Comentario

					} else if ("fld19".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Referencia de Expediente

					} else if ("fld20".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Fecha del documento
					}

				} else if (RegisterType.SALIDA.equalsIgnoreCase(registerType)) {

					if ("fld1".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Número de registro
						registerOrigin.setRegisterNumber(regQueryInfos[i]
								.getValue());

					} else if ("fld2".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Fecha de registro
						registerOrigin
								.setRegisterDate(getRegisterDate(regQueryInfos[i]
										.getValue()));

					} else if ("fld3".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Usuario
						registerOrigin.setUser(regQueryInfos[i].getValue());

					} else if ("fld4".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Fecha trabajo
						registerOrigin
								.setCreationDate(getRegisterDate(regQueryInfos[i]
										.getValue()));

					} else if ("fld5".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							// Código de la oficina de registro
							registerOffice.setCode(tokens[0].trim());

							// Nombre de la oficina de registro
							if (tokens.length > 1) {
								registerOffice.setName(tokens[1].trim());
							}
						}

					} else if ("fld6".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Estado de registro

					} else if ("fld7".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							// Código del origen de registro
							originOrganization.setCode(tokens[0].trim());

							// Nombre del origen de registro
							if (tokens.length > 1) {
								originOrganization.setName(tokens[1].trim());
							}
						}

					} else if ("fld8".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							// Código del destino de registro
							destinationOrganization.setCode(tokens[0].trim());

							// Nombre del destino de registro
							if (tokens.length > 1) {
								destinationOrganization.setName(tokens[1]
										.trim());
							}
						}

					} else if ("fld9".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Destinatarios

					} else if ("fld10".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Tipo de transporte
						transport.setType(regQueryInfos[i].getValue());

					} else if ("fld11".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Número de transporte
						transport.setNumber(regQueryInfos[i].getValue());

					} else if ("fld12".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						String[] tokens = StringUtils.split(regQueryInfos[i]
								.getValue(), "-");
						if (!ArrayUtils.isEmpty(tokens)) {

							Subject subject = new Subject();

							// Código del tipo de asunto
							subject.setCode(tokens[0].trim());

							// Nombre del tipo de asunto
							if (tokens.length > 1) {
								subject.setName(tokens[1].trim());
							}

							// Asunto
							registerData.setSubject(subject);
						}

					} else if ("fld13".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Resumen
						registerData.setSummary(regQueryInfos[i].getValue());

					} else if ("fld14".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Comentario

					} else if ("fld15".equalsIgnoreCase(regQueryInfos[i]
							.getFolderName())) {

						// Fecha del documento
					}

				}
			}

			registerData.setParticipants(getParticipants(registerOrigin
					.getRegisterNumber(), registerType));
		}

		return register;
	}

	protected ThirdPerson[] getParticipants(String registerNumber,
			String registerType) throws RegistroException {

		ThirdPerson[] participants = null;

		if (StringUtils.isNotBlank(registerNumber)) {

			PersonInfo[] personInfos = null;

			if (RegisterType.SALIDA.equalsIgnoreCase(registerType)) {
				personInfos = servicioRegistro.getInterestedOutputRegister(
						getUserInfo(), registerNumber, getEntidad());
			} else {
				personInfos = servicioRegistro.getInterestedInputRegister(
						getUserInfo(), registerNumber, getEntidad());
			}

			if (!ArrayUtils.isEmpty(personInfos)) {
				participants = new ThirdPerson[personInfos.length];
				for (int i = 0; i < personInfos.length; i++) {
					participants[i] = new ThirdPerson(personInfos[i]
							.getPersonId(), personInfos[i].getPersonName(),
							personInfos[i].getDomId());
				}
			}
		}

		return participants;
	}

	protected Calendar getRegisterDate(String date) {
		Calendar cal = null;

		if (StringUtils.isNotBlank(date)) {
			try {
				cal = DateUtil.getCalendar(date, "dd-MM-yyyy HH:mm:ss");
			} catch (Throwable t) {
				try {
					cal = DateUtil.getCalendar(date, "dd-MM-yyyy");
				} catch (Throwable t2) {
					logger
							.warn("No se ha podido parsear la fecha: " + date,
									t2);
				}
			}
		}

		return cal;
	}

	protected DistributionInfo getDistributionInfo(String intrayId)
			throws RegistroException {

		DistributionInfo distInfo = servicioRegistro
				.getDistribution(getUserInfo(), new Integer(TypeConverter
						.parseInt(intrayId, -1)), getEntidad());

		return distInfo;
	}

	protected List getIntrais(DistributionInfo[] distInfos)
			throws ISPACException {
		List intrais = new ArrayList();

		if (!ArrayUtils.isEmpty(distInfos)) {
			for (int i = 0; i < distInfos.length; i++) {
				DistributionInfo distInfo = distInfos[i];
				if (distInfo != null) {
					intrais.add(getIntray(distInfo));
				}
			}
		}

		return intrais;
	}

	protected Intray getIntray(DistributionInfo distInfo) throws ISPACException {
		Intray intray = null;

		if (distInfo != null) {
			try {
				intray = new Intray();
				intray.setId(String.valueOf(distInfo.getDtrId()));
				intray.setRegisterNumber(distInfo.getRegisterNumber());
				intray.setRegisterDate(StringUtils.isNotBlank(distInfo
						.getRegisterDate()) ? DATE_FORMAT.parse(distInfo
						.getRegisterDate()) : null);
				intray.setState(distInfo.getState().intValue());
				intray.setStateDate(StringUtils.isNotBlank(distInfo
						.getStateDate()) ? DATE_FORMAT.parse(distInfo
						.getStateDate()) : null);
				intray.setDate(StringUtils.isNotBlank(distInfo
						.getDistributionDate()) ? DATE_FORMAT.parse(distInfo
						.getDistributionDate()) : null);
				intray.setMessage(distInfo.getMessage());
				intray.setMatter(distInfo.getRegisterMatter());
				intray.setMatterTypeName(distInfo.getRegisterMatterTypeName());

				// origen y destino de la distribucion MAL!! => senderName y
				// destinationName
				// No se corresponden con los type/id correspondientes
				intray.setSender(distInfo.getSenderName());
				intray.setDestination(distInfo.getDestinationName());

				intray.setRegisterOffice(distInfo.getRegisterOffice());
				intray.setRegisterOrigin(distInfo.getRegisterSenderName());
				intray.setRegisterDestination(distInfo
						.getRegisterDestinationName());

				PersonInfo[] sendersOrReceivers = distInfo
						.getSendersOrReceivers();
				if (String.valueOf(Libro.LIBRO_SALIDA).equalsIgnoreCase(
						distInfo.getRegisterType())) {
					intray
							.setRegisterAddressee(getThirdPersons(sendersOrReceivers));
				} else {
					intray
							.setRegisterSender(getThirdPersons(sendersOrReceivers));
				}

			} catch (Exception e) {
				logger
						.error(
								"Error al componer la información del registro distribuido",
								e);
				throw new ISPACException(
						"Error al componer la información del registro distribuido",
						e);
			}
		}

		return intray;
	}

	protected ThirdPerson[] getThirdPersons(PersonInfo[] infos) {
		ThirdPerson[] thirdPersons = null;
		if (infos != null) {
			thirdPersons = new ThirdPerson[infos.length];
			for (int i = 0; i < infos.length; i++) {
				thirdPersons[i] = new ThirdPerson(infos[i].getPersonId(),
						infos[i].getPersonName(), infos[i].getDomId(), infos[i]
								.getDirection());
			}
		}
		return thirdPersons;
	}

	protected Annexe[] getAnnexes(RegisterWithPagesInfo regInfo) {
		List annexes = new ArrayList();

		if (regInfo != null) {
			Document[] documents = regInfo.getDocInfo();

			if (!ArrayUtils.isEmpty(documents)) {
				for (int i = 0; i < documents.length; i++) {
					Document doc = documents[i];
					if (doc != null) {
						List pages = doc.getPages();
						if (pages != null) {
							for (int p = 0; p < pages.size(); p++) {
								Page page = (Page) pages.get(p);
								if (page != null) {
									Annexe annexe = new Annexe();
									annexe.setId(page.getFileID());
									annexe.setName(page.getPageName());
									annexe.setExt(page.getLoc());
									annexe.setBookId(doc.getBookId());
									annexe.setFolderId(page.getFolderId());
									annexe.setDocId(page.getDocID());
									annexe.setPageId(page.getPageID());
									annexes.add(annexe);
								}
							}
						}
					}
				}
			}
		}

		return (Annexe[]) annexes.toArray(new Annexe[annexes.size()]);
	}

	protected Entidad getEntidad() {

		Entidad entidad = null;

		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			entidad = new Entidad();
			entidad.setIdentificador(info.getOrganizationId());
			entidad.setNombre(info.getOrganizationName());
		}

		return entidad;
	}

	protected UserInfo getUserInfo() {
		UserInfo userInfo = new UserInfo();

		userInfo.setUserName(ctx.getUser().getName());
		userInfo.setPassword("xxx"); // No se autentica
		userInfo.setLocale(ctx.getLocale());

		// Para la autenticación LDAP + SSO existe la posibilidad de especificar
		// en una variable global, USER_PROPERTY_SICRES_CONNECTOR_LOGIN en
		// SPAC_VARS, el nombre de la propiedad del usuario a buscar que se
		// establecerá como login en la funcionalidad de registro a invocar por
		// el conector
		String userPropertyLogin = null;

		try {
			IInvesflowAPI invesFlowAPI = ctx.getAPI();
			IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();

			// Propiedades del responsable
			Map properties = respAPI.getRespProperties(ctx.getUser().getUID());
			if (properties != null) {

				userPropertyLogin = ConfigurationMgr.getVarGlobal(ctx,
						CONFIG_VAR_USER_PROPERTY_SICRES_CONNECTOR_LOGIN);
				if (StringUtils.isBlank(userPropertyLogin)) {
					userPropertyLogin = DEFAULT_USER_PROPERTY_SICRES_CONNECTOR_LOGIN_VALUE;
				}

				Object respPropertyLoginValue = properties
						.get(userPropertyLogin);
				if (respPropertyLoginValue != null) {

					String login = null;

					if (respPropertyLoginValue instanceof String) {
						login = (String) respPropertyLoginValue;
					} else if (respPropertyLoginValue instanceof List) {
						List logins = (List) respPropertyLoginValue;
						if (!logins.isEmpty()) {
							login = (String) logins.get(0);
						}
					}

					if (login != null) {
						userInfo.setUserName(login);
					}
				}
			}
		} catch (ISPACException e) {
			logger.error("Error al obtener el valor de la propiedad '"
					+ userPropertyLogin + "' para el responsable '"
					+ ctx.getUser().getName() + "': " + e.getMessage(), e);
		}

		return userInfo;
	}

	public void linkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
		// TODO Futuras implementaciones

	}

	public void unlinkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
		// TODO Futuras implementaciones

	}

	/**
	 * Obtiene los libros de registro por tipo de libro.
	 *
	 * @param bookType
	 *            Tipo de libro (@see IRegisterAPI).
	 * @return Libros de registro.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public List getBooks(int bookType) throws ISPACException {
		try {

			BookInfo[] bookInfos = servicioRegistro.getBooksForType(
					getUserInfo(),
					(bookType == IRegisterAPI.BOOK_TYPE_OUTPUT ? new Integer(
							Libro.LIBRO_SALIDA) : new Integer(
							Libro.LIBRO_ENTRADA)), new Integer(0), new Integer(
							1), getEntidad());

			return getRegisterBooks(bookInfos);

		} catch (RegistroException e) {
			logger.error("Error al obtener los libros de registro", e);
			throw new ISPACException("Error al obtener los libros de registro",
					e);
		}
	}

	protected List getRegisterBooks(BookInfo[] bookInfos) {
		List books = new ArrayList();
		if (bookInfos != null) {
			for (int i = 0; i < bookInfos.length; i++) {
				if (bookInfos[i] != null) {
					books.add(getRegisterBook(bookInfos[i]));
				}
			}
		}
		return books;
	}

	protected RegisterBook getRegisterBook(BookInfo bookInfo) {
		RegisterBook book = null;
		if (bookInfo != null) {
			book = new RegisterBook();
			book.setId(String.valueOf(bookInfo.getIdocArchId()));
			book.setName(bookInfo.getName());

			if (bookInfo.getType() == BookInfo.LIBRO_SALIDA) {
				book.setType(IRegisterAPI.BOOK_TYPE_OUTPUT);
			} else {
				book.setType(IRegisterAPI.BOOK_TYPE_INPUT);
			}
		}
		return book;
	}

	/**
	 * Obtiene las oficinas de registro dependientes de un libro.
	 *
	 * @param bookId
	 *            Identificador del libro.
	 * @return Oficinas de registro
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public List getOffices(String bookId) throws ISPACException {
		try {

			if (StringUtils.isBlank(bookId)) {
				return new ArrayList();
			}

			OfficeInfo[] officeInfos = servicioRegistro
					.getOfficeCanCreateRegisterByUser(getUserInfo(),
							new Integer(bookId), getEntidad());

			return getRegisterOffices(officeInfos);

		} catch (RegistroException e) {
			logger.error(
					"Error al obtener las oficinas del libro de registro ["
							+ bookId + "]", e);
			throw new ISPACException(
					"Error al obtener las oficinas del libro de registro", e);
		}
	}

	protected List getRegisterOffices(OfficeInfo[] officeInfos) {
		List offices = new ArrayList();
		if (officeInfos != null) {
			for (int i = 0; i < officeInfos.length; i++) {
				if (officeInfos[i] != null) {
					offices.add(getRegisterOffice(officeInfos[i]));
				}
			}
		}
		return offices;
	}

	protected RegisterOffice getRegisterOffice(OfficeInfo officeInfo) {
		RegisterOffice office = null;
		if (officeInfo != null) {
			office = new RegisterOffice();
			office.setCode(officeInfo.getCode());
			office.setName(officeInfo.getName());
		}
		return office;
	}

	/**
	 * Obtiene la información de una organización a partir de su código.
	 *
	 * @param code
	 *            Código de la organización.
	 * @return Información de la organización.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public Organization getOrganizationByCode(String code)
			throws ISPACException {

		Organization organization = null;

		try {
			if (StringUtils.isNotBlank(code)) {
				organization = getOrganization(servicioAdmRegistro
						.obtenerOrganizacionByCode(code, getEntidad()));
			}
		} catch (RPAdminException e) {
			logger.error(
					"Error al obtener la unidad administrativa por código ["
							+ code + "]", e);
			throw new ISPACException(
					"Error al obtener la unidad administrativa", e);
		}

		return organization;
	}

	protected Organization getOrganization(OrganizacionBean orgBean) {
		Organization organization = null;
		if (orgBean != null) {
			organization = new Organization();
			organization.setId(String.valueOf(orgBean.getId()));
			organization.setCode(orgBean.getUid());
			organization.setName(orgBean.getNombre());
			organization.setAcronym(orgBean.getAbreviatura());
			organization.setCreationDate(TypeConverter.toCalendar(orgBean
					.getFechaAlta()));
			organization.setDisabledDate(TypeConverter.toCalendar(orgBean
					.getFechaBaja()));
		}
		return organization;
	}

}
