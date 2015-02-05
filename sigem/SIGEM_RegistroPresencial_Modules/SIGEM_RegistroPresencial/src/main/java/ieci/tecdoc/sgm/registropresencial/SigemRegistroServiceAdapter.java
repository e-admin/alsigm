package ieci.tecdoc.sgm.registropresencial;

import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.registro.BookInfo;
import ieci.tecdoc.sgm.core.services.registro.DistributionInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentQuery;
import ieci.tecdoc.sgm.core.services.registro.FieldInfo;
import ieci.tecdoc.sgm.core.services.registro.FieldInfoQuery;
import ieci.tecdoc.sgm.core.services.registro.Interested;
import ieci.tecdoc.sgm.core.services.registro.OfficeInfo;
import ieci.tecdoc.sgm.core.services.registro.PersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterQueryInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfoPersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegistroException;
import ieci.tecdoc.sgm.core.services.registro.ServicioRegistro;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;
import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.config.spring.RegistroPresencialSpringApplicationContext;
import ieci.tecdoc.sgm.registropresencial.exception.AttributesExceptionManager;
import ieci.tecdoc.sgm.registropresencial.exception.BookExceptionManager;
import ieci.tecdoc.sgm.registropresencial.exception.DistributionExceptionManager;
import ieci.tecdoc.sgm.registropresencial.exception.SecurityExceptionManager;
import ieci.tecdoc.sgm.registropresencial.exception.SessionExceptionManager;
import ieci.tecdoc.sgm.registropresencial.exception.ValidationExceptionManager;
import ieci.tecdoc.sgm.registropresencial.info.InfoDistribution;
import ieci.tecdoc.sgm.registropresencial.info.InfoRegister;
import ieci.tecdoc.sgm.registropresencial.manager.DistributionManager;
import ieci.tecdoc.sgm.registropresencial.manager.RegisterManager;
import ieci.tecdoc.sgm.registropresencial.utils.SigemRegistroServiceAdapterUtil;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

public class SigemRegistroServiceAdapter implements ServicioRegistro {

	private static final Logger logger = Logger
			.getLogger(SigemRegistroServiceAdapter.class);

	//Cargamos el contexto de la aplicacion
	private static ApplicationContext contexto = RegistroPresencialSpringApplicationContext
			.getInstance().getApplicationContext();

	private RegisterManager registerManager;

	private DistributionManager distributionManager;

	public RegisterInfo createFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException {
		RegisterInfo rInfo = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			Map documentsMap = SigemRegistroServiceAdapterUtil
					.getDocumentsInfo(documents);
			List personsInfo = SigemRegistroServiceAdapterUtil
					.getPersonsInfo(inter);
			List fieldsInfo = SigemRegistroServiceAdapterUtil
					.getFieldsInfo(atts);
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			InfoRegister folder = wsRegisterManager.createFolder(wsUser,
					bookId, fieldsInfo, personsInfo, documentsMap, entidad
							.getIdentificador());

			rInfo = SigemRegistroServiceAdapterUtil.getRegisterInfo(folder);

		} catch (ValidationException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		} catch (Throwable e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		}
		return rInfo;
	}

	public RegisterInfo consolidateFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException {
		RegisterInfo rInfo = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			Map documentsInfo = SigemRegistroServiceAdapterUtil
					.getDocumentsInfo(documents);
			List personsInfo = SigemRegistroServiceAdapterUtil
					.getPersonsInfo(inter);
			List fieldsInfo = SigemRegistroServiceAdapterUtil
					.getFieldsInfo(atts);
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			InfoRegister folder = wsRegisterManager.consolidateFolder(wsUser,
					bookId, fieldsInfo, personsInfo, documentsInfo, entidad
							.getIdentificador());

			rInfo = SigemRegistroServiceAdapterUtil.getRegisterInfo(folder);

		} catch (ValidationException e) {
			logger.error("Impossible consolidate folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible consolidate folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible consolidate folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible consolidate folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible consolidate folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible consolidate folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		} catch (Throwable e) {
			logger.error("Impossible consolidate folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		}
		return rInfo;
	}

	public RegisterInfo updateFolder(UserInfo user, Integer bookId,
			Integer folderId, FieldInfo[] atts, PersonInfo[] inter,
			DocumentInfo[] documents, Entidad entidad) throws RegistroException {
		RegisterInfo rInfo = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			Map documentsInfo = SigemRegistroServiceAdapterUtil
					.getDocumentsInfo(documents);
			List personsInfo = SigemRegistroServiceAdapterUtil
					.getPersonsInfo(inter);
			List fieldsInfo = SigemRegistroServiceAdapterUtil
					.getFieldsInfo(atts);
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			InfoRegister folder = wsRegisterManager.updateFolder(wsUser,
					bookId, folderId, fieldsInfo, personsInfo, documentsInfo,
					entidad.getIdentificador());

			rInfo = SigemRegistroServiceAdapterUtil.getRegisterInfo(folder);

		} catch (ValidationException e) {
			logger.error("Impossible to update folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to create new folder on book: " + bookId,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to create new folder on book: " + bookId,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to create new folder on book: " + bookId,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to create new folder on book: " + bookId,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to create new folder on book: " + bookId,
					e);
			throw new RegistroException(2100010040);
		} catch (Throwable e) {
			logger.error("Impossible to create new folder on book: " + bookId,
					e);
			throw new RegistroException(2100010040);
		}
		return rInfo;
	}

	public RegisterInfo importFolder(UserInfo user, Integer bookId,
			FieldInfo[] atts, PersonInfo[] inter, DocumentInfo[] documents,
			Entidad entidad) throws RegistroException {
		RegisterInfo rInfo = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			Map documentsInfo = SigemRegistroServiceAdapterUtil
					.getDocumentsInfo(documents);
			List personsInfo = SigemRegistroServiceAdapterUtil
					.getPersonsInfo(inter);
			List fieldsInfo = SigemRegistroServiceAdapterUtil
					.getFieldsInfo(atts);
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			InfoRegister folder = wsRegisterManager.importFolder(wsUser,
					bookId, fieldsInfo, personsInfo, documentsInfo, entidad
							.getIdentificador());
			rInfo = SigemRegistroServiceAdapterUtil.getRegisterInfo(folder);

		} catch (ValidationException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		} catch (Throwable e) {
			logger.error("Impossible create new folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		}
		return rInfo;
	}

	public RegisterInfo[] findFolder(UserInfo user, Integer bookId,
			FieldInfoQuery[] atts, Entidad entidad) throws RegistroException {
		RegisterInfo[] rInfo = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			List fieldQueryInfo = SigemRegistroServiceAdapterUtil
					.getFieldQueryInfo(atts);
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			InfoRegister[] folders = wsRegisterManager.findFolder(wsUser,
					bookId, fieldQueryInfo, entidad.getIdentificador());

			rInfo = SigemRegistroServiceAdapterUtil.getRegistersInfo(folders);
		} catch (ValidationException e) {
			logger.error("Impossible to find folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to find folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to find folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to find folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to find folder on book: " + bookId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to find folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		} catch (Throwable e) {
			logger.error("Impossible to find folder on book: " + bookId, e);
			throw new RegistroException(2100010040);
		}
		return rInfo;
	}

	public RegisterWithPagesInfo getInputFolderForNumber(UserInfo user,
			Integer bookId, String folderNumber, Entidad entidad)
			throws RegistroException {
		RegisterWithPagesInfo result = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			boolean oficAsoc = SigemRegistroServiceAdapterUtil
					.isOficAsoc(bookId);
			Map folders = wsRegisterManager.getInputFolderForNumber(wsUser,
					folderNumber, oficAsoc, entidad.getIdentificador());

			result = SigemRegistroServiceAdapterUtil
					.getRegisterQueryInfo(folders);
		} catch (ValidationException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		} catch (Throwable e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		}
		return result;

	}

	public RegisterWithPagesInfo getOutputFolderForNumber(UserInfo user,
			Integer bookId, String folderNumber, Entidad entidad)
			throws RegistroException {
		RegisterWithPagesInfo result = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			boolean oficAsoc = SigemRegistroServiceAdapterUtil
					.isOficAsoc(bookId);
			Map folders = wsRegisterManager.getOutputFolderForNumber(wsUser,
					folderNumber, oficAsoc, entidad.getIdentificador());

			result = SigemRegistroServiceAdapterUtil
					.getRegisterQueryInfo(folders);
		} catch (ValidationException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		} catch (Throwable e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		}
		return result;

	}

	public DocumentQuery getDocumentFolder(UserInfo user, Integer bookID,
			Integer folderId, Integer docID, Integer pageID, Entidad entidad)
			throws RegistroException {
		DocumentQuery result = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			byte[] documentFolder = wsRegisterManager.getDocumentFolder(wsUser,
					bookID, folderId, docID.intValue(), pageID.intValue(),
					entidad.getIdentificador());

			result = SigemRegistroServiceAdapterUtil
					.getDocumentQuery(documentFolder);
		} catch (ValidationException e) {
			logger.error("Impossible to obtain the document for book: "
					+ bookID + " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain the document for book: "
					+ bookID + " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain the document for book: "
					+ bookID + " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain the document for book: "
					+ bookID + " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to obtain the document for book: "
					+ bookID + " and folder: " + folderId, e);
			throw new RegistroException(2100010028);
		} catch (Throwable e) {
			logger.error("Impossible to obtain the document for book: "
					+ bookID + " and folder: " + folderId, e);
			throw new RegistroException(2100010028);
		}
		return result;

	}

	public DistributionInfo getDistribution(UserInfo user,
			Integer distributionId, Entidad entidad) throws RegistroException {
		DistributionInfo result = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			InfoDistribution distInfo = wsDistributionManager
					.getDistributionForNumber(wsUser, distributionId, entidad
							.getIdentificador());

			result = SigemRegistroServiceAdapterUtil
					.getDistributionInfo(distInfo);
		} catch (DistributionException e) {
			logger.error(
					"Impossible obtain in distribution: " + distributionId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error(
					"Impossible obtain in distribution: " + distributionId, e);
			throw new RegistroException(2100010070);
		} catch (Throwable e) {
			logger.error(
					"Impossible obtain in distribution: " + distributionId, e);
			throw new RegistroException(2100010070);
		}
		return result;
	}

	public DistributionInfo[] getInputDistribution(UserInfo user,
			Integer state, Integer firstRow, Integer maxResults,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		DistributionInfo result[] = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			List listInputDistribucion = wsDistributionManager
					.getInputDistribution(wsUser, state.intValue(), firstRow
							.intValue(), maxResults.intValue(),
							typeBookRegisterDist.intValue(), oficAsoc
									.booleanValue(), entidad.getIdentificador());

			result = SigemRegistroServiceAdapterUtil
					.getDistributionInfo(listInputDistribucion);
		} catch (ValidationException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(2100010070);
		} catch (Throwable e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(2100010070);
		}
		return result;
	}

	public DistributionInfo[] getOutputDistribution(UserInfo user,
			Integer state, Integer firstRow, Integer maxResults,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		DistributionInfo result[] = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			List listOutputDistribution = wsDistributionManager
					.getOutputDistribution(wsUser, state.intValue(), firstRow
							.intValue(), maxResults.intValue(),
							typeBookRegisterDist.intValue(), oficAsoc
									.booleanValue(), entidad.getIdentificador());

			result = SigemRegistroServiceAdapterUtil
					.getDistributionInfo(listOutputDistribution);
		} catch (ValidationException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(2100010070);
		} catch (Throwable e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(2100010070);
		}

		return result;
	}

	public Integer countInputDistribution(UserInfo user, Integer state,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		Integer result = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			result = wsDistributionManager.countInputDistribution(wsUser, state
					.intValue(), typeBookRegisterDist.intValue(), oficAsoc
					.booleanValue(), entidad.getIdentificador());

		} catch (ValidationException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(2100010070);
		} catch (Throwable e) {
			logger.error("Impossible obtain in distribution for user: " + user,
					e);
			throw new RegistroException(2100010070);
		}
		return result;
	}

	public Integer countOutputDistribution(UserInfo user, Integer state,
			Integer typeBookRegisterDist, Boolean oficAsoc, Entidad entidad)
			throws RegistroException {
		Integer result = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			result = wsDistributionManager.countOutputDistribution(wsUser,
					state.intValue(), typeBookRegisterDist.intValue(), oficAsoc
							.booleanValue(), entidad.getIdentificador());

		} catch (ValidationException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error(
					"Impossible obtain out distribution for user: " + user, e);
			throw new RegistroException(2100010070);
		} catch (Throwable e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(2100010070);
		}

		return result;
	}

	public void acceptDistribution(UserInfo user, String registerNumber,
			Integer bookId, Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.acceptDistribution(wsUser, registerNumber,
					bookId, entidad.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(2100010071);
		} catch (Throwable e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(2100010071);
		}
	}

	public void acceptDistribution(UserInfo user, Integer distributionId,
			Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.acceptDistribution(wsUser, distributionId,
					entidad.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(2100010071);
		} catch (Throwable e) {
			logger.error("Impossible accept distribution for user: " + user, e);
			throw new RegistroException(2100010071);
		}
	}

	public void archiveDistribution(UserInfo user, String registerNumber,
			Integer bookId, Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.archiveDistribution(wsUser, registerNumber,
					bookId, entidad.getIdentificador());
		} catch (ValidationException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(2100010079);
		} catch (Throwable e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(2100010079);
		}
	}

	public void archiveDistribution(UserInfo user, Integer distributionId,
			Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.archiveDistribution(wsUser, distributionId,
					entidad.getIdentificador());
		} catch (ValidationException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(2100010079);
		} catch (Throwable e) {
			logger
					.error("Impossible archive distribution for user: " + user,
							e);
			throw new RegistroException(2100010079);
		}
	}

	public void rejectDistribution(UserInfo user, String registerNumber,
			String remarks, Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.rejectDistribution(wsUser, registerNumber,
					remarks, entidad.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(2100010074);
		} catch (Throwable e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(2100010074);
		}
	}

	public void rejectDistribution(UserInfo user, Integer distributionId,
			String remarks, Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.rejectDistribution(wsUser, distributionId,
					remarks, entidad.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(2100010074);
		} catch (Throwable e) {
			logger.error("Impossible reject distribution for user: " + user, e);
			throw new RegistroException(2100010074);
		}
	}

	public void changeInputDistribution(UserInfo user, String registerNumber,
			String code, Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.changeInputDistribution(wsUser,
					registerNumber, code, entidad.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible redistribute in distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible redistribute in distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible redistribute in distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible redistribute in distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible redistribute in distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible redistribute in distribution for user: "
					+ user, e);
			throw new RegistroException(2100010044);
		} catch (Throwable e) {
			logger.error("Impossible redistribute in distribution for user: "
					+ user, e);
			throw new RegistroException(2100010044);
		}
	}

	public void changeOutputDistribution(UserInfo user, String registerNumber,
			String code, Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			DistributionManager wsDistributionManager = getDistributionManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsDistributionManager.changeInputDistribution(wsUser,
					registerNumber, code, entidad.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible redistribute out distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible redistribute out distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (DistributionException e) {
			logger.error("Impossible redistribute out distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible redistribute out distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible redistribute out distribution for user: "
					+ user, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible redistribute out distribution for user: "
					+ user, e);
			throw new RegistroException(2100010044);
		} catch (Throwable e) {
			logger.error("Impossible redistribute out distribution for user: "
					+ user, e);
			throw new RegistroException(2100010044);
		}
	}

	public void addDocument(UserInfo user, Integer bookId, Integer folderId,
			DocumentInfo[] documents, Entidad entidad) throws RegistroException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			Map documentsMap = SigemRegistroServiceAdapterUtil
					.getDocumentsInfo(documents);
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			wsRegisterManager.addDocumentFolder(wsUser, bookId, folderId,
					documentsMap, entidad.getIdentificador());

		} catch (ValidationException e) {
			logger.error("Impossible to add documento on book: " + bookId
					+ " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to add documento on book: " + bookId
					+ " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to add documento on book: " + bookId
					+ " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to add documento on book: " + bookId
					+ " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to add documento on book: " + bookId
					+ " and folder: " + folderId, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to add documento on book: " + bookId
					+ " and folder: " + folderId, e);
			throw new RegistroException(2100010040);
		} catch (Throwable e) {
			logger.error("Impossible to add documento on book: " + bookId
					+ " and folder: " + folderId, e);
			throw new RegistroException(2100010040);
		}
	}

	public RegisterWithPagesInfoPersonInfo getInputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {
		RegisterWithPagesInfoPersonInfo result = null;
		RegisterWithPagesInfo registerWithPagesInfo = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			Map inputFolders = wsRegisterManager.getInputFolderForNumber(
					wsUser, folderNumber, false, entidad.getIdentificador());

			registerWithPagesInfo = SigemRegistroServiceAdapterUtil
					.getRegisterQueryInfo(inputFolders);
			RegisterQueryInfo[] regQueryInfos = registerWithPagesInfo
					.getRqInfo();
			Integer bookId = null;
			Integer folderId = null;
			int values = 0;
			for (int i = 0; i < regQueryInfos.length && values < 2; i++) {
				if ("bookId".equalsIgnoreCase(regQueryInfos[i].getFolderName())) {
					values++;
					String bookIdDesc = regQueryInfos[i].getValue();
					String[] tokens = org.apache.commons.lang.StringUtils
							.split(bookIdDesc, " - ");
					if (tokens != null && tokens.length != 0) {
						int bookIdInt = Integer.parseInt(tokens[0]);
						if (bookIdInt > 0) {
							bookId = new Integer(bookIdInt);
						}
					}
				} else if ("fdrId".equalsIgnoreCase(regQueryInfos[i]
						.getFolderName())) {
					values++;
					folderId = new Integer(regQueryInfos[i].getValue());
				}
			}
			result = new RegisterWithPagesInfoPersonInfo(registerWithPagesInfo);
			Interested[] interestedRegister = wsRegisterManager
					.getInterestedRegister(wsUser, bookId, folderId, entidad
							.getIdentificador());

			result.setPersons(SigemRegistroServiceAdapterUtil
					.getPersonsInfo(interestedRegister));
		} catch (ValidationException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		} catch (Throwable e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		}
		return result;
	}

	public RegisterWithPagesInfoPersonInfo getOutputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {
		RegisterWithPagesInfoPersonInfo result = null;
		RegisterWithPagesInfo registerWithPagesInfo = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			Map outputFolder = wsRegisterManager.getOutputFolderForNumber(
					wsUser, folderNumber, false, entidad.getIdentificador());

			registerWithPagesInfo = SigemRegistroServiceAdapterUtil
					.getRegisterQueryInfo(outputFolder);
			RegisterQueryInfo[] regQueryInfos = registerWithPagesInfo
					.getRqInfo();
			Integer bookId = null;
			Integer folderId = null;
			int values = 0;
			for (int i = 0; i < regQueryInfos.length && values < 2; i++) {
				if ("bookId".equalsIgnoreCase(regQueryInfos[i].getFolderName())) {
					values++;
					String bookIdDesc = regQueryInfos[i].getValue();
					String[] tokens = org.apache.commons.lang.StringUtils
							.split(bookIdDesc, " - ");
					if (tokens != null && tokens.length != 0) {
						int bookIdInt = Integer.parseInt(tokens[0]);
						if (bookIdInt > 0) {
							bookId = new Integer(bookIdInt);
						}
					}
				} else if ("fdrId".equalsIgnoreCase(regQueryInfos[i]
						.getFolderName())) {
					values++;
					folderId = new Integer(regQueryInfos[i].getValue());
				}
			}
			result = new RegisterWithPagesInfoPersonInfo(registerWithPagesInfo);
			Interested[] interestedRegister = wsRegisterManager
					.getInterestedRegister(wsUser, bookId, folderId, entidad
							.getIdentificador());

			result.setPersons(SigemRegistroServiceAdapterUtil
					.getPersonsInfo(interestedRegister));
		} catch (ValidationException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (Exception e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		} catch (Throwable e) {
			logger.error("Impossible to obtain the folder: " + folderNumber, e);
			throw new RegistroException(2100010013);
		}
		return result;
	}

	public PersonInfo[] getInterestedInputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return getInputRegister(user, folderNumber, entidad).getPersons();

	}

	public PersonInfo[] getInterestedOutputRegister(UserInfo user,
			String folderNumber, Entidad entidad) throws RegistroException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return getOutputRegister(user, folderNumber, entidad).getPersons();
	}

	public Boolean canCreateRegister(UserInfo user, Integer bookType,
			Integer oficAsoc, Integer onlyOpenBooks, Entidad entidad)
			throws RegistroException {
		Boolean result = Boolean.FALSE;

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			boolean isOficAsoc = SigemRegistroServiceAdapterUtil
					.isOficAsoc(oficAsoc);
			boolean isOnlyOpenBooks = SigemRegistroServiceAdapterUtil
					.isOnlyOpenBooks(onlyOpenBooks);
			result = wsRegisterManager.canCreateRegister(wsUser, bookType
					.intValue(), isOficAsoc, isOnlyOpenBooks, entidad
					.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		}

		return result;

	}

	public BookInfo[] getBooksForType(UserInfo user, Integer bookType,
			Integer oficAsoc, Integer onlyOpenBooks, Entidad entidad)
			throws RegistroException {
		BookInfo[] result = null;

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			boolean isOficAsoc = SigemRegistroServiceAdapterUtil
					.isOficAsoc(oficAsoc);
			boolean isOnlyOpenBooks = SigemRegistroServiceAdapterUtil
					.isOnlyOpenBooks(onlyOpenBooks);
			List bookList = wsRegisterManager.getBooksCanCreateRegister(wsUser,
					bookType.intValue(), isOficAsoc, isOnlyOpenBooks, entidad
							.getIdentificador());

			result = SigemRegistroServiceAdapterUtil.getBooksInfo(bookList);
		} catch (ValidationException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to obtain the books ", e);
			throw new RegistroException(getCodeRegistroException(e));
		}

		return result;

	}

	public OfficeInfo[] getOfficeCanCreateRegisterByUser(UserInfo user,
			Integer bookID, Entidad entidad) throws RegistroException {
		OfficeInfo[] result = null;

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);

			List officeList = wsRegisterManager.getOfficesCanCreateRegister(
					wsUser, bookID, entidad.getIdentificador());

			result = SigemRegistroServiceAdapterUtil.getOfficesInfo(officeList);
		} catch (ValidationException e) {
			logger.error("Impossible to obtain office ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain office ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (BookException e) {
			logger.error("Impossible to obtain office ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain office ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (AttributesException e) {
			logger.error("Impossible to obtain office ", e);
			throw new RegistroException(getCodeRegistroException(e));
		}

		return result;

	}



	public Boolean existMatterTypeInOffice(UserInfo user, String matterTypeCode,
			String officeCode, Entidad entidad) throws RegistroException {

		try{
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RegisterManager wsRegisterManager = getRegisterManager();
			User wsUser = SigemRegistroServiceAdapterUtil.getWSUser(user);
			 return wsRegisterManager.getMatterTypeInOffice(wsUser, matterTypeCode, officeCode, entidad.getIdentificador());
		} catch (ValidationException e) {
			logger.error("Impossible to obtain matter by office ", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SecurityException e) {
			logger.error("Impossible to obtain matter by office", e);
			throw new RegistroException(getCodeRegistroException(e));
		} catch (SessionException e) {
			logger.error("Impossible to obtain matter by office ", e);
			throw new RegistroException(getCodeRegistroException(e));
		}
	}

	private long getCodeRegistroException(Object obj) {
		long code = 0;
		String key = null;
		Long isicresE = null;
		if (obj instanceof ValidationException) {
			ValidationException vE = (ValidationException) obj;
			ValidationExceptionManager wsValidationExceptionManager = new ValidationExceptionManager();
			key = vE.getCode();
			isicresE = (Long) wsValidationExceptionManager.getOptionId().get(
					key);
			code = isicresE.longValue();

		} else if (obj instanceof BookException) {
			BookException vE = (BookException) obj;
			BookExceptionManager wsBookExceptionManager = new BookExceptionManager();
			key = vE.getCode();
			isicresE = (Long) wsBookExceptionManager.getOptionId().get(key);
			code = isicresE.longValue();

		} else if (obj instanceof SecurityException) {
			SecurityException vE = (SecurityException) obj;
			SecurityExceptionManager wsSecurityExceptionManager = new SecurityExceptionManager();
			key = vE.getCode();
			isicresE = (Long) wsSecurityExceptionManager.getOptionId().get(key);
			code = isicresE.longValue();

		} else if (obj instanceof SessionException) {
			SessionException vE = (SessionException) obj;
			SessionExceptionManager wsSessionExceptionManager = new SessionExceptionManager();
			key = vE.getCode();
			isicresE = (Long) wsSessionExceptionManager.getOptionId().get(key);
			code = isicresE.longValue();

		} else if (obj instanceof AttributesException) {
			AttributesException vE = (AttributesException) obj;
			AttributesExceptionManager wsAttributesExceptionManager = new AttributesExceptionManager();
			key = vE.getCode();
			isicresE = (Long) wsAttributesExceptionManager.getOptionId().get(
					key);
			code = isicresE.longValue();

		} else if (obj instanceof DistributionException) {
			DistributionException vE = (DistributionException) obj;
			DistributionExceptionManager wsDistributionExceptionManager = new DistributionExceptionManager();
			key = vE.getCode();
			isicresE = (Long) wsDistributionExceptionManager.getOptionId().get(
					key);
			code = isicresE.longValue();

		} else {
			code = 2100010000;
		}
		return code;
	}

	private RegisterManager getRegisterManager() {
		return registerManager;
	}

	public void setRegisterManager(RegisterManager registerManager) {
		this.registerManager = registerManager;
	}

	private DistributionManager getDistributionManager() {
		return distributionManager;
	}

	public void setDistributionManager(DistributionManager distributionManager) {
		this.distributionManager = distributionManager;
	}

}
