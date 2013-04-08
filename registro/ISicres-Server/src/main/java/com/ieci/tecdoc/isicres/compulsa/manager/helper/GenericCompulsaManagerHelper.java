package com.ieci.tecdoc.isicres.compulsa.manager.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO;
import com.ieci.tecdoc.common.keys.CompulsaKeys;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public class GenericCompulsaManagerHelper {

	public int mappingStrFieldToInt(String field) {
		int result = 0;

		Map optionId = new HashMap();
		optionId
				.put(CompulsaKeys.FIELD_TYPE_STRING_CERTIFICATE, new Integer(1));
		optionId.put(CompulsaKeys.FIELD_TYPE_STRING_HASH, new Integer(2));
		optionId.put(CompulsaKeys.FIELD_TYPE_STRING_FUNCNAME, new Integer(3));
		optionId
				.put(CompulsaKeys.FIELD_TYPE_STRING_XADESFORMAT, new Integer(4));
		optionId.put(CompulsaKeys.FIELD_TYPE_STRING_FILESCAN, new Integer(5));
		optionId.put(CompulsaKeys.FIELD_TYPE_STRING_FILEXADES, new Integer(6));
		if ((Integer) optionId.get(field) != null) {
			result = ((Integer) optionId.get(field)).intValue();
		}
		return result;
	}

	public String getNombreFirmante(String fileName) {
		String funcName = fileName;
		if (funcName.substring(funcName.indexOf("CN=") + 3) != null) {
			funcName = funcName.substring(funcName.indexOf("CN=") + 3);
			if (funcName.indexOf("=") != -1) {
				funcName = funcName.substring(0, funcName.indexOf("="));
				funcName = funcName.substring(0, funcName.lastIndexOf(","));
			}
		} else {
			funcName = " ";
		}

		return funcName;
	}

	public FlushFdrFile getDataFile(ISicresCreateCompulsaVO compulsa, String fi)
			throws Exception {

		StringTokenizer tokens = new StringTokenizer(fi, CompulsaKeys.BARRA);
		String idFile = null;
		String fileScanName = null;
		while (tokens.hasMoreTokens()) {
			idFile = tokens.nextToken();
			fileScanName = tokens.nextToken();
			fileScanName = getFileScanName(fileScanName);
		}

		String orderFScan = idFile.substring(idFile.length() - 1, idFile
				.length());

		FlushFdrFile flushFdrFile = new FlushFdrFile();
		flushFdrFile.setOrder(new Integer(orderFScan).intValue());
		flushFdrFile.setExtension(getExtension(fileScanName));
		String fileName = getFileNameFis(compulsa.getSessionID(), compulsa
				.getFolderId().toString(), fileScanName,
				new Integer(orderFScan).intValue(), false);

		flushFdrFile.setFileNameFis(compulsa.getBeginPath() + File.separator
				+ fileName);

		flushFdrFile.setFileNameLog(fileScanName);
		flushFdrFile.loadFile();
		return flushFdrFile;

	}

	public FlushFdrFile getFdrFileCompulsado(
			ISicresCreateCompulsaVO compulsaVO, FlushFdrFile original) {
		String fileScanName = original.getFileNameLog();
		FlushFdrFile flushFdrFileC = new FlushFdrFile();
		flushFdrFileC.setOrder(original.getOrder());
		flushFdrFileC.setExtension(original.getExtension());

		String fileNameC = getFileNameFis(compulsaVO.getSessionID(), compulsaVO
				.getFolderId().toString(), fileScanName, original.getOrder(),
				true);
		flushFdrFileC.setFileNameFis(compulsaVO.getBeginPath() + File.separator
				+ fileNameC);
		flushFdrFileC.setFileNameLog(getFileNameLogC(fileScanName));

		return flushFdrFileC;
	}

	public OutputStream getOutputStream(ISicresCreateCompulsaVO compulsaVO,
			String fileNameFis) throws FileNotFoundException {
		// String out = fileNameFis.replaceFirst(compulsaVO.getBeginPath()
		// + File.separator, compulsaVO.getBeginPath()
		//		+ CompulsaKeys.SEPARADOR);

		File fNew = new File(fileNameFis);
		fNew.deleteOnExit();

		OutputStream outputStream = new FileOutputStream(fNew);

		return outputStream;
	}

	private String getFileNameFis(String sessionId, String folderId,
			String name, int order, boolean compul) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(sessionId);
		buffer.append(CompulsaKeys.GUIONBAJO);
		buffer.append(folderId);
		buffer.append(CompulsaKeys.GUIONBAJO);
		if (compul == true) {
			buffer.append(name);
		} else {
			buffer.append(order);
			buffer.append(CompulsaKeys.PUNTO);
			buffer.append(getExtension(name));
		}

		return buffer.toString();
	}

	private String getFileNameLogC(String fileName) {
		String result = fileName.substring(0, fileName
				.indexOf(CompulsaKeys.PUNTO));
		String extension = null;
		extension = fileName.substring(fileName.indexOf(CompulsaKeys.PUNTO),
				fileName.length());
		result = result + "_C" + extension;
		return result;
	}

	private String getFileScanName(String name) {
		String aux = null;
		String aux1 = null;
		aux = name.substring(0, name.indexOf(CompulsaKeys.GUIONBAJO) + 1);
		aux1 = name.substring(name.indexOf(CompulsaKeys.GUIONBAJO) + 1, name
				.length());
		aux1 = aux1.substring(aux1.lastIndexOf(CompulsaKeys.DOSPATH) + 1, aux1
				.length());
		return aux + aux1;
	}

	private String getExtension(String name) {
		String extension = name.substring(name
				.lastIndexOf(CompulsaKeys.DOSPATH) + 1, name.length());
		extension = extension.substring(
				extension.indexOf(CompulsaKeys.PUNTO) + 1, extension.length());
		return extension;
	}

}
