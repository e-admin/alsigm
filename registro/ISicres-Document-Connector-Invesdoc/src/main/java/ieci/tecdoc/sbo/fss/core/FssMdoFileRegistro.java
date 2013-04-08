package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList; //import ieci.tecdoc.core.database.DbConnection;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.file.FileManager;
import ieci.tecdoc.core.ftp.FtpBasicFns;
import ieci.tecdoc.core.ftp.FtpConnectionInfo;
import ieci.tecdoc.core.ftp.FtpTransferFns;
import ieci.tecdoc.core.textutil.Util;
import ieci.tecdoc.core.types.IeciTdType;

import ieci.tecdoc.sbo.config.CfgFssMdoFile;

import ieci.tecdoc.sbo.fss.core.CfgMdoFile.RepositoryConfig;
import ieci.tecdoc.sbo.util.nextid.NextId;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.utils.SignTiff;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FssMdoFileRegistro {
	// ~ Static fields/initializers
	// ---------------------------------------------

	private static final Logger log = Logger
			.getLogger(FssMdoFileRegistro.class);

	private static final String CONFIG_FILE = "IeciTd_Fss_Config.xml";
	private static boolean configInitialized = false;
	private static Map configurations;

	// ~ Constructors
	// -----------------------------------------------------------

	private FssMdoFileRegistro() {

	}

	// ~ Methods
	// ----------------------------------------------------------------

	public static byte[] retrieveFile(int fileId) throws Exception {

		byte[] fileCont = null;
		FssDaoFileRecA fileRecA = null;
		FssDaoVolRecA volRecA = null;
		FssDaoRepRecA repRecA = null;
		char sep;
		// int os;
		String fileFullPath;
		FssRepInfo repInfo = null;
		FssVolInfo volInfo = null;

		fileRecA = FssDaoFileTbl.selectRecA(fileId);

		volRecA = FssDaoVolTbl.selectRecA(fileRecA.m_volId);
		checkVolIsReady(volRecA.m_stat);

		repRecA = FssDaoRepTbl.selectRecA(volRecA.m_repId);
		checkRepIsNotOffLine(repRecA.m_stat);

		repInfo = FssMdoUtil.decodeRepInfo(repRecA.m_info);
		volInfo = FssMdoUtil.decodeVolInfo(volRecA.m_info);

		sep = FssMdoUtil.getFileNameSeparator(repInfo.m_os);

		fileFullPath = repInfo.m_path + sep + volInfo.m_path + sep
				+ fileRecA.m_loc;
		fileCont = readFile(repInfo, fileFullPath);

		return fileCont;

	}

	public static byte[] retrieveFileForSign(Integer bookID, Integer fdrId,
			int listId, int docId, int fileID, BookTypeConf bookTypeConf)
			throws Exception {

		byte[] fileCont = null;
		FssDaoFileRecA fileRecA = null;
		FssDaoVolRecA volRecA = null;
		FssDaoRepRecA repRecA = null;
		char sep;
		// int os;
		String fileFullPath;
		FssRepInfo repInfo = null;
		FssVolInfo volInfo = null;

		fileRecA = FssDaoFileTbl.selectRecA(fileID);

		volRecA = FssDaoVolTbl.selectRecA(fileRecA.m_volId);
		checkVolIsReady(volRecA.m_stat);

		repRecA = FssDaoRepTbl.selectRecA(volRecA.m_repId);
		checkRepIsNotOffLine(repRecA.m_stat);

		repInfo = FssMdoUtil.decodeRepInfo(repRecA.m_info);
		volInfo = FssMdoUtil.decodeVolInfo(volRecA.m_info);

		sep = FssMdoUtil.getFileNameSeparator(repInfo.m_os);

		fileFullPath = repInfo.m_path + sep + volInfo.m_path + sep
				+ fileRecA.m_loc;
		fileCont = readFile(repInfo, fileFullPath);

		FssFileInfo fileInfo = new FssFileInfo();
		fileInfo.m_extId1 = bookID.intValue();
		fileInfo.m_extId2 = fdrId.intValue();
		fileInfo.m_extId3 = docId;
		SignTiff signTiff = new SignTiff();
		byte[] signBuffer = signTiff.saveTiffWithText(bookTypeConf, fileCont);
		storeFileInVolumeForUpdate(volRecA, repRecA, fileInfo, null, signBuffer,
				fileFullPath);
		return signBuffer;
	}

	public static byte[] updateFile(FssFileInfo fileInfo, int listId,
			int fileID, byte[] fileCont) throws Exception {

		FssDaoFileRecA fileRecA = null;
		FssDaoVolRecA volRecA = null;
		FssDaoRepRecA repRecA = null;
		char sep;
		// int os;
		String fileFullPath;
		FssRepInfo repInfo = null;
		FssVolInfo volInfo = null;

		fileRecA = FssDaoFileTbl.selectRecA(fileID);

		volRecA = FssDaoVolTbl.selectRecA(fileRecA.m_volId);
		checkVolIsReady(volRecA.m_stat);

		repRecA = FssDaoRepTbl.selectRecA(volRecA.m_repId);
		checkRepIsNotOffLine(repRecA.m_stat);

		repInfo = FssMdoUtil.decodeRepInfo(repRecA.m_info);
		volInfo = FssMdoUtil.decodeVolInfo(volRecA.m_info);

		sep = FssMdoUtil.getFileNameSeparator(repInfo.m_os);

		fileFullPath = repInfo.m_path + sep + volInfo.m_path + sep
				+ fileRecA.m_loc;		
		
		storeFileInVolumeForUpdate(volRecA, repRecA, fileInfo, null, fileCont,
				fileFullPath);
		return fileCont;
	}

	// Si el fichero no tiene búsqueda en contenido de documento se pasa
	// ftsInfo como null
	public static void storeFileInVolumeForUpdate(FssDaoVolRecA volRecA,
			FssDaoRepRecA repRecA, FssFileInfo info, FssFtsInfo ftsInfo,
			byte[] fileCont, String fileFullPath) throws Exception {

		char sep;
		FssRepInfo repInfo = null;
		FssVolInfo volInfo = null;
		FssDaoFileRecAc fileRecAc = null;
		FssDaoFtsRecAc ftsRecAc = null;
		FssDaoActDirRecA actDirRecAc = null;
		int fileSize;
		double volMaxSize;

		try {

			if (((info.m_flags & FssMdoUtil.FILE_FLAG_FTS) != 0)
					&& (ftsInfo == null)) {
				throw new IeciTdException(FssError.EC_INVALID_PARAM,
						FssError.EM_INVALID_PARAM);
			}

			volInfo = FssMdoUtil.decodeVolInfo(volRecA.m_info);

			fileSize = fileCont.length;

			volMaxSize = Double.parseDouble(volInfo.m_maxSize);
			checkCanStoreInVol(volRecA.m_stat, volMaxSize, Double
					.parseDouble(volRecA.m_actSize), fileSize);

			checkCanStoreInRep(repRecA.m_stat);

			repInfo = FssMdoUtil.decodeRepInfo(repRecA.m_info);
			sep = FssMdoUtil.getFileNameSeparator(repInfo.m_os);

			writeFile(repInfo, fileFullPath, fileCont);

		} catch (Exception e) {

			log.error(e);
			throw e;

		}
	}

	// Si el fichero no tiene búsqueda en contenido de documento se pasa
	// ftsInfo como null
	public static int storeFileInVolume(int volId, FssFileInfo info,
			FssFtsInfo ftsInfo, byte[] fileCont) throws Exception {

		int fileId = IeciTdType.NULL_LONG_INTEGER;
		FssDaoVolRecA volRecA = null;
		FssDaoRepRecA repRecA = null;
		char sep;
		FssRepInfo repInfo = null;
		FssVolInfo volInfo = null;
		FssDaoFileRecAc fileRecAc = null;
		FssDaoFtsRecAc ftsRecAc = null;
		FssDaoActDirRecA actDirRecAc = null;
		String dirPath = null;
		String fileFullPath = "";
		String dirFullPath;
		int fileSize;
		double volMaxSize;

		if (((info.m_flags & FssMdoUtil.FILE_FLAG_FTS) != 0)
				&& (ftsInfo == null)) {
			throw new IeciTdException(FssError.EC_INVALID_PARAM,
					FssError.EM_INVALID_PARAM);
		}

		volRecA = FssDaoVolTbl.selectRecA(volId);
		volInfo = FssMdoUtil.decodeVolInfo(volRecA.m_info);

		fileSize = fileCont.length;

		volMaxSize = Double.parseDouble(volInfo.m_maxSize);
		checkCanStoreInVol(volRecA.m_stat, volMaxSize, Double
				.parseDouble(volRecA.m_actSize), fileSize);

		repRecA = FssDaoRepTbl.selectRecA(volRecA.m_repId);

		checkCanStoreInRep(repRecA.m_stat);

		repInfo = FssMdoUtil.decodeRepInfo(repRecA.m_info);
		sep = FssMdoUtil.getFileNameSeparator(repInfo.m_os);

		actDirRecAc = FssDaoActDirTbl.selectRecA(volId);
		dirPath = Util.formatLongInteger(actDirRecAc.m_actDir);

		fileRecAc = fillRecAc(volId, dirPath, sep, info, fileSize);
		fileFullPath = repInfo.m_path + sep + volInfo.m_path + sep
				+ fileRecAc.m_loc;

		if ((info.m_flags & FssMdoUtil.FILE_FLAG_FTS) != 0) {
			ftsRecAc = fillFtsRecAc(fileRecAc, info, ftsInfo, repRecA.m_type,
					sep, fileFullPath);
		}

		dirFullPath = repInfo.m_path + sep + volInfo.m_path + sep + dirPath;

		try {

			createDirectory(repInfo, dirFullPath);

		} catch (Exception e) {

			log.error(e.getMessage(), e);

		}

		writeFile(repInfo, fileFullPath, fileCont);

		reflectStoreInTables(volId, actDirRecAc.m_actDir, fileSize, volMaxSize,
				fileRecAc, ftsRecAc);

		fileId = fileRecAc.m_id;

		return fileId;

	}

	public static int storeFileInList(int listId, FssFileInfo info,
			FssFtsInfo ftsInfo, byte[] fileCont) throws Exception {

		IeciTdLongIntegerArrayList volIds;
		int i;
		int fileId = IeciTdType.NULL_LONG_INTEGER;
		boolean store = false;

		if (((info.m_flags & FssMdoUtil.FILE_FLAG_FTS) != 0)
				&& (ftsInfo == null)) {
			throw new IeciTdException(FssError.EC_INVALID_PARAM,
					FssError.EM_INVALID_PARAM);
		}

		volIds = FssDaoListVolTbl.selectVolIds(listId);

		if (volIds.count() == 0) {
			throw new IeciTdException(FssError.EC_NO_VOLS_IN_LIST,
					FssError.EM_NO_VOLS_IN_LIST);
		}

		for (i = 0; i < volIds.count(); i++) {

			try {

				fileId = storeFileInVolume(volIds.get(i), info, ftsInfo,
						fileCont);

				store = true;

				break;

			} catch (Exception e) {

				log.error(e.getMessage(), e);

			}

		}

		if (!store) {

			throw new IeciTdException(FssError.EC_UNABLE_TO_STORE_IN_LIST,
					FssError.EM_UNABLE_TO_STORE_IN_LIST);
		}

		return fileId;

	}

	public static void deleteFile(int fileId) throws Exception {

		FssDaoFileRecUc rec;
		int volId;
		char sep;
		String fileFullPath;
		FssDaoFileRecA fileRecA = null;
		FssDaoVolRecA volRecA = null;
		FssDaoRepRecA repRecA = null;
		FssRepInfo repInfo = null;
		FssVolInfo volInfo = null;
		int fileSize;
		double volMaxSize;
		boolean isFts;

		fileRecA = FssDaoFileTbl.selectRecA(fileId);

		volRecA = FssDaoVolTbl.selectRecA(fileRecA.m_volId);
		checkCanDeleteInVol(volRecA.m_stat);

		repRecA = FssDaoRepTbl.selectRecA(volRecA.m_repId);
		checkCanDeleteInRep(repRecA.m_stat);

		// repInfo = FssMdoUtil.decodeRepInfo(repRecA.m_info);
		repInfo = calculateRepInfo(repRecA);
		volInfo = FssMdoUtil.decodeVolInfo(volRecA.m_info);

		volMaxSize = Double.parseDouble(volInfo.m_maxSize);

		sep = FssMdoUtil.getFileNameSeparator(repInfo.m_os);
		fileFullPath = repInfo.m_path + sep + volInfo.m_path + sep
				+ fileRecA.m_loc;

		fileSize = getFileSize(repInfo, fileFullPath);
		if ((fileRecA.m_flags & FssMdoUtil.FILE_FLAG_FTS) != 0)
			isFts = true;
		else
			isFts = false;

		reflectDeleteInTables(fileId, fileSize, isFts, fileRecA.m_volId,
				volMaxSize);

		deleteFile(repInfo, fileFullPath);

	}

	// **************************************************************************
	private static void checkVolIsReady(int volStat) throws Exception {

		if ((volStat & FssMdoUtil.VOL_STAT_NOT_READY) != 0) {
			throw new IeciTdException(FssError.EC_VOL_NOT_READY,
					FssError.EM_VOL_NOT_READY);
		}

	}

	private static void checkVolIsNotFull(int volStat) throws Exception {

		if ((volStat & FssMdoUtil.VOL_STAT_FULL) != 0) {
			throw new IeciTdException(FssError.EC_VOL_IS_FULL,
					FssError.EM_VOL_IS_FULL);
		}

	}

	private static void checkVolIsNotReadOnly(int volStat) throws Exception {

		if ((volStat & FssMdoUtil.VOL_STAT_READONLY) != 0) {
			throw new IeciTdException(FssError.EC_VOL_IS_READONLY,
					FssError.EM_VOL_IS_READONLY);
		}

	}

	private static void checkCanStoreInVol(int volStat, double maxSize,
			double actSize, int fileSize) throws Exception {

		checkVolIsNotReadOnly(volStat);
		checkVolIsReady(volStat);
		checkVolIsNotFull(volStat);

		if ((actSize + fileSize) > maxSize) {
			throw new IeciTdException(FssError.EC_VOL_IS_FULL,
					FssError.EM_VOL_IS_FULL);
		}

	}

	private static void checkCanDeleteInVol(int volStat) throws Exception {

		checkVolIsNotReadOnly(volStat);
		checkVolIsReady(volStat);

	}

	private static void checkRepIsNotOffLine(int repStat) throws Exception {

		if ((repStat & FssMdoUtil.REP_STAT_OFFLINE) != 0) {
			throw new IeciTdException(FssError.EC_REP_OFF_LINE,
					FssError.EM_REP_OFF_LINE);
		}

	}

	private static void checkRepIsNotReadOnly(int repStat) throws Exception {

		if ((repStat & FssMdoUtil.REP_STAT_READONLY) != 0) {
			throw new IeciTdException(FssError.EC_REP_IS_READONLY,
					FssError.EM_REP_IS_READONLY);
		}

	}

	private static void checkCanStoreInRep(int repStat) throws Exception {

		checkRepIsNotOffLine(repStat);
		checkRepIsNotReadOnly(repStat);

	}

	private static void checkCanDeleteInRep(int repStat) throws Exception {

		checkRepIsNotOffLine(repStat);
		checkRepIsNotReadOnly(repStat);

	}

	private static byte[] readFile(FssRepInfo repInfo, String fileFullPath)
			throws Exception {

		if (repInfo.m_type == FssMdoUtil.RT_PFS)
			return FileManager.retrieveFile(fileFullPath);
		else if (repInfo.m_type == FssMdoUtil.RT_FTP)
			return readFtpFile(repInfo, fileFullPath);
		else {
			throw new IeciTdException(FssError.EC_INVALID_PARAM,
					FssError.EM_INVALID_PARAM);
		}

	}

	private static byte[] readFtpFile(FssRepInfo repInfo, String fileFullPath)
			throws Exception {

		FtpConnectionInfo ftpConnInfo;

		ftpConnInfo = FssMdoUtil.createFtpConnectionInfo(repInfo);

		return FtpTransferFns.retrieveFile(ftpConnInfo, fileFullPath);

	}

	private static int getFileSize(FssRepInfo repInfo, String fileFullPath)
			throws Exception {

		if (repInfo.m_type == FssMdoUtil.RT_PFS)
			return FileManager.getFileSize(fileFullPath);
		else if (repInfo.m_type == FssMdoUtil.RT_FTP)
			return getFtpFileSize(repInfo, fileFullPath);
		else {
			throw new IeciTdException(FssError.EC_INVALID_PARAM,
					FssError.EM_INVALID_PARAM);
		}

	}

	private static int getFtpFileSize(FssRepInfo repInfo, String fileFullPath)
			throws Exception {

		FtpConnectionInfo ftpConnInfo;
		int fileSize = 0;
		String size = null;

		ftpConnInfo = FssMdoUtil.createFtpConnectionInfo(repInfo);

		size = FtpBasicFns.getFileSize(ftpConnInfo, fileFullPath);

		fileSize = Integer.parseInt(size);

		return fileSize;

	}

	private static void writeFile(FssRepInfo repInfo, String fileFullPath,
			byte[] fileCont) throws Exception {

		if (repInfo.m_type == FssMdoUtil.RT_PFS)
			FileManager.storeFile(fileFullPath, fileCont);
		else if (repInfo.m_type == FssMdoUtil.RT_FTP)
			writeFtpFile(repInfo, fileFullPath, fileCont);
		else {
			throw new IeciTdException(FssError.EC_INVALID_PARAM,
					FssError.EM_INVALID_PARAM);
		}

	}

	private static void createDirectory(FssRepInfo repInfo, String dirFullPath)
			throws Exception {

		if (repInfo.m_type == FssMdoUtil.RT_PFS)
			FileManager.createDirectory(dirFullPath);
		else if (repInfo.m_type == FssMdoUtil.RT_FTP)
			createFtpDirectory(repInfo, dirFullPath);
		else {
			throw new IeciTdException(FssError.EC_INVALID_PARAM,
					FssError.EM_INVALID_PARAM);
		}

	}

	private static void createFtpDirectory(FssRepInfo repInfo,
			String dirFullPath) throws Exception {

		FtpConnectionInfo ftpConnInfo;

		ftpConnInfo = FssMdoUtil.createFtpConnectionInfo(repInfo);

		FtpBasicFns.createDirectory(ftpConnInfo, dirFullPath);

	}

	private static void deleteFile(FssRepInfo repInfo, String fileFullPath)
			throws Exception {

		if (repInfo.m_type == FssMdoUtil.RT_PFS)
			FileManager.deleteFile(fileFullPath);
		else if (repInfo.m_type == FssMdoUtil.RT_FTP)
			deleteFtpFile(repInfo, fileFullPath);
		else {
			throw new IeciTdException(FssError.EC_INVALID_PARAM,
					FssError.EM_INVALID_PARAM);
		}

	}

	private static void deleteFtpFile(FssRepInfo repInfo, String fileFullPath)
			throws Exception {

		FtpConnectionInfo ftpConnInfo;

		ftpConnInfo = FssMdoUtil.createFtpConnectionInfo(repInfo);

		FtpBasicFns.deleteFile(ftpConnInfo, fileFullPath);

	}

	private static void writeFtpFile(FssRepInfo repInfo, String fileFullPath,
			byte[] fileCont) throws Exception {

		FtpConnectionInfo ftpConnInfo;

		ftpConnInfo = FssMdoUtil.createFtpConnectionInfo(repInfo);

		FtpTransferFns.storeFile(ftpConnInfo, fileFullPath, fileCont);

	}

	private static String createUniqueFileName() throws Exception {

		String fileName = null;
		FssGuid guid = new FssGuid();

		fileName = guid.toString();

		return fileName;

	}

	private static FssDaoFileRecAc fillRecAc(int volId, String dirPath,
			char sep, FssFileInfo info, double fileSize) throws Exception {

		int fileId;
		String fileName;

		FssDaoFileRecAc rec = new FssDaoFileRecAc();

		fileId = NextId.generateNextId(FssMdoUtil.NEXT_ID_TBL_NAME,
				FssMdoUtil.NEXT_ID_TYPE_FILE);

		rec.m_id = fileId;
		rec.m_volId = volId;

		// Coger un nombre único;
		fileName = createUniqueFileName();
		fileName = fileName + "." + info.m_ext;
		rec.m_loc = dirPath + sep + fileName;

		rec.m_extId1 = info.m_extId1;
		rec.m_extId2 = info.m_extId2;
		rec.m_extId3 = info.m_extId3;
		rec.m_flags = info.m_flags;

		rec.m_stat = FssMdoUtil.STAT_DEF;
		rec.m_ts = DateTimeUtil.getCurrentDateTime();
		rec.m_size = fileSize;

		return rec;

	}

	private static FssDaoVolRecUc fillVolRecUcToStoreFile(int fileSize,
			double volMaxSize, FssDaoVolRecA volRecA) throws Exception {

		double actSize;
		double newActSize;
		int stat;
		DecimalFormat decFrmt = null;
		String aSize;

		FssDaoVolRecUc rec = new FssDaoVolRecUc();

		actSize = Double.parseDouble(volRecA.m_actSize);
		newActSize = actSize + fileSize;

		if (newActSize >= volMaxSize)
			stat = volRecA.m_stat | FssMdoUtil.VOL_STAT_FULL;
		else
			stat = volRecA.m_stat;

		decFrmt = new DecimalFormat("0.");
		aSize = decFrmt.format(newActSize);
		aSize = aSize.replace(',', '.');

		rec.m_actSize = aSize;
		rec.m_stat = stat;

		return rec;

	}

	private static String getFstPath(FssFtsInfo ftsInfo, int repType,
			char fileNameSep, String fileFullPath) throws Exception {

		String ftsPath = null;
		char ftsSep;
		String tempFtsPath;
		int len, idx;

		ftsPath = fileFullPath;

		ftsSep = FssMdoUtil.getFileNameSeparator(ftsInfo.m_ftsPlatform);

		if (ftsInfo.m_ftsPlatform == FssMdoUtil.OS_UNIX) {

			if (ftsSep != fileNameSep) // El repositorio está en plataforma
			// Windows
			{
				/*
				 * Si el path viene de la forma unidad_red:\dir1\...\dirn ó
				 * \\unidad_red\Dir1..\Dir2, se sustituye la unidad de red por
				 * ftsRoot y se cambian los separadores por el que le
				 * corresponde ('\' por '/')
				 */

				len = fileFullPath.length();

				tempFtsPath = fileFullPath;

				if (fileFullPath.startsWith("\\\\")) {
					idx = fileFullPath.indexOf(fileNameSep, 2);
					tempFtsPath = ftsInfo.m_ftsRoot
							+ fileFullPath.substring(idx);
				} else if (fileFullPath.charAt(1) == ':') {
					tempFtsPath = ftsInfo.m_ftsRoot
							+ fileFullPath.substring(2, len - 1);
				}

				ftsPath = tempFtsPath.replace(fileNameSep, ftsSep);

			}

		} else if ((ftsInfo.m_ftsPlatform == FssMdoUtil.OS_NT)
				|| (ftsInfo.m_ftsPlatform == FssMdoUtil.OS_WINDOWS)) {
			// El repositorio está en plataforma Unix o es ftp y hay que
			// concatenarle
			// ftsRoot
			if ((ftsSep != fileNameSep) || (repType == FssMdoUtil.RT_FTP)) {
				/*
				 * se cambian los separadores por el que le corresponde y se
				 * concatena FtsRoot
				 */

				tempFtsPath = fileFullPath;

				if (ftsSep != fileNameSep)
					tempFtsPath = fileFullPath.replace(fileNameSep, ftsSep);

				if (tempFtsPath.charAt(0) == ftsSep) {
					ftsPath = ftsInfo.m_ftsRoot + tempFtsPath;
				} else {
					ftsPath = ftsInfo.m_ftsRoot + ftsSep + tempFtsPath;
				}

			}

		}

		return ftsPath;

	}

	private static FssDaoFtsRecAc fillFtsRecAc(FssDaoFileRecAc fileRecAc,
			FssFileInfo info, FssFtsInfo ftsInfo, int repType,
			char fileNameSep, String fileFullPath) throws Exception {

		String ftsPath;
		FssDaoFtsRecAc rec = new FssDaoFtsRecAc();

		ftsPath = getFstPath(ftsInfo, repType, fileNameSep, fileFullPath);

		rec.m_id = fileRecAc.m_id;
		rec.m_extId1 = info.m_extId1;
		rec.m_extId2 = info.m_extId2;
		rec.m_extId3 = info.m_extId3;
		rec.m_extId4 = info.m_extId4;
		rec.m_path = ftsPath;
		rec.m_ts = DateTimeUtil.getCurrentDateTime();

		return rec;

	}

	private static void reflectStoreInTables(int volId, int actDir,
			int fileSize, double volMaxSize, FssDaoFileRecAc fileRecAc,
			FssDaoFtsRecAc ftsRecAc) throws Exception {

		FssDaoActDirRecA actDirRecA = null;
		FssDaoVolRecA volRecA = null;
		FssDaoVolRecUc volRecUc = null;
		String aSize;

		try {

			DbConnection.beginTransaction();

			// Se incrementa el tamaño y número de ficheros del volumen
			FssDaoVolTbl.incrementNumFiles(volId);

			volRecA = FssDaoVolTbl.selectRecA(volId);

			volRecUc = fillVolRecUcToStoreFile(fileSize, volMaxSize, volRecA);

			FssDaoVolTbl.updateRow(volRecUc, volId);

			// Se inserta la información del fichero
			FssDaoFileTbl.insertRow(fileRecAc);

			if (ftsRecAc != null)
				FssDaoFtsTbl.insertRow(ftsRecAc);

			// Se actualiza la información del directorio en curso
			FssDaoActDirTbl.incrementNumFiles(volId, actDir);

			actDirRecA = FssDaoActDirTbl.selectRecA(volId);

			if ((actDirRecA.m_numFiles >= FssMdoUtil.DIR_MAX_NUM_FILES)
					&& (actDirRecA.m_actDir == actDir))

				FssDaoActDirTbl.incrementActDir(volId);

			DbConnection.endTransaction(true);

		} catch (Exception e) {
			DbConnection.ensureEndTransaction(e);
			throw e;

		}

	}

	private static FssDaoVolRecUc fillVolRecUcToDeleteFile(int fileSize,
			double volMaxSize, FssDaoVolRecA volRecA) throws Exception {

		double actSize;
		double newActSize;
		int stat;
		DecimalFormat decFrmt = null;
		String aSize;

		FssDaoVolRecUc rec = new FssDaoVolRecUc();

		actSize = Double.parseDouble(volRecA.m_actSize);
		newActSize = actSize - fileSize;

		if ((newActSize < volMaxSize)
				&& ((volRecA.m_stat & FssMdoUtil.VOL_STAT_FULL) != 0))
			stat = volRecA.m_stat ^ FssMdoUtil.VOL_STAT_FULL;
		else
			stat = volRecA.m_stat;

		decFrmt = new DecimalFormat("0.");
		aSize = decFrmt.format(newActSize);
		aSize = aSize.replace(',', '.');

		rec.m_actSize = aSize;
		rec.m_stat = stat;

		return rec;

	}

	private static void reflectDeleteInTables(int fileId, int fileSize,
			boolean isFts, int volId, double volMaxSize) throws Exception {

		FssDaoVolRecA volRecA = null;
		FssDaoVolRecUc volRecUc = null;

		try {

			DbConnection.beginTransaction();

			FssDaoFileTbl.deleteRow(fileId);

			if (isFts) {
				// Se borra en la fts
				FssDaoFtsTbl.deleteRow(fileId);
			}

			// Se decrementa el tamaño y número de ficheros del volumen
			FssDaoVolTbl.decrementNumFiles(volId);

			volRecA = FssDaoVolTbl.selectRecA(volId);

			volRecUc = fillVolRecUcToDeleteFile(fileSize, volMaxSize, volRecA);

			FssDaoVolTbl.updateRow(volRecUc, volId);

			DbConnection.endTransaction(true);

		} catch (Exception e) {

			DbConnection.ensureEndTransaction(e);

		}

	}

	public static FssRepInfo calculateRepInfo(FssDaoRepRecA repRecA)
			throws Exception {
		FssRepInfo repInfo = null;

		repInfo = FssMdoUtil.decodeRepInfo(repRecA.m_info);

		if ((repInfo.m_type == FssMdoUtil.RT_PFS)
				|| (repInfo.m_type == FssMdoUtil.RT_FTP)) {

			CfgMdoFile.RepositoryConfig repConfig = getConfig(repRecA.m_id);
			if (repConfig != null) {
				// Se busca en el fichero de configuración el path y el sistema
				// operativo

				repInfo.m_type = FssMdoUtil.RT_PFS;

				repInfo.m_port = -1;
				repInfo.m_srv = null;
				repInfo.m_pwd = null;
				repInfo.m_usr = null;

				repInfo.m_os = repConfig.getOperatingSystem();
				repInfo.m_path = repConfig.getPath();
			}
		}

		return repInfo;
	}

	private static CfgMdoFile.RepositoryConfig getConfig(int repository) {

		CfgMdoFile.RepositoryConfig config;
		if (!configInitialized) {

			synchronized (CONFIG_FILE) {
				if (!configInitialized) {
					configInitialized = true;
					try {
						CfgMdoFile cfgMdoFile = CfgFssMdoFile
								.createConfigFromFile(CONFIG_FILE);
						if (cfgMdoFile != null) {
							List listConfig = cfgMdoFile
									.getRepositoriesConfig();
							if (listConfig.size() > 0) {
								configurations = new HashMap();

								for (int i = 0; i < listConfig.size(); i++) {
									config = (RepositoryConfig) listConfig
											.get(i);
									configurations.put(new Integer(config
											.getId()), config);
								}
							} else {
								configurations = null;
							}
						}
					} catch (Throwable e) {
						log.warn(
								"No se ha leido correctamente el fichero de configuración "
										+ CONFIG_FILE, e);
					}
				}
			}
		}

		if (configurations != null) {
			config = (RepositoryConfig) configurations.get(new Integer(
					repository));
		} else {
			config = null;
		}
		return config;
	}

}
