/**
 *
 */
package com.ieci.tecdoc.isicres.compulsa.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.ICompulsaManager;
import com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO;
import com.ieci.tecdoc.common.compulsa.vo.ISicresReturnCompulsaVO;
import com.ieci.tecdoc.common.keys.CompulsaKeys;
import com.ieci.tecdoc.common.utils.ISFileUtil;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;
import com.ieci.tecdoc.isicres.compulsa.manager.helper.GenericCompulsaManagerHelper;

import es.ieci.tecdoc.isicres.compulsa.connector.ISicresCompulsaConnector;
import es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresAbstractCompulsaVO;
import es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresBasicCompulsaVO;
import es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresBasicDatosEspecificosCompulsaVO;

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public abstract class GenericCompulsaManager implements ICompulsaManager {

	private static final Logger log = Logger.getLogger(GenericCompulsaManager.class);

	protected GenericCompulsaManagerHelper compulsaHelper = new GenericCompulsaManagerHelper();

	protected ISicresCompulsaConnector connector;

	/**
	 * Método que transforma los datos especificos basicos del conector generico
	 * a los datos especificos del conector implementados
	 *
	 * @param compulsaVO
	 * @return
	 */
	public abstract ISicresAbstractCompulsaVO getDatosEspecificosCompulsa(
			ISicresAbstractCompulsaVO compulsaVO);

	/**
	 * {@inheritDoc}
	 *
	 * @see com.ieci.tecdoc.common.adapter.ICompulsaManager#compulsarDocuments(com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO)
	 */
	public ISicresReturnCompulsaVO compulsarDocuments(
			ISicresCreateCompulsaVO compulsaVO) {

		ISicresReturnCompulsaVO returnVO = new ISicresReturnCompulsaVO();
		returnVO.setBookId(compulsaVO.getBookId());
		returnVO.setEntidad(compulsaVO.getEntidad());
		returnVO.setFolderId(compulsaVO.getFolderId());
		returnVO.setLocale(compulsaVO.getLocale());
		returnVO.setSessionID(compulsaVO.getSessionID());

		if ((compulsaVO.getFileItems() == null)
				|| (compulsaVO.getFileItems().isEmpty())) {
			return returnVO;
		}

		ISicresAbstractCompulsaVO iSicresAbstractCompulsaVO = getISicresAbstractCompulsaVO(compulsaVO);
		iSicresAbstractCompulsaVO = getDatosEspecificosCompulsa(iSicresAbstractCompulsaVO);
		iSicresAbstractCompulsaVO = connector
				.generateLocator(iSicresAbstractCompulsaVO);

		compulsaVO.setLocator(iSicresAbstractCompulsaVO.getLocator());
		returnVO.setLocator(iSicresAbstractCompulsaVO.getLocator());

		try {

			Iterator itr = compulsaVO.getFileItems().iterator();
			// FlushFdrFile flushFdrFile = null;
			// FlushFdrFile flushFdrFileC = null;
			List filesInfo = new ArrayList();
			while (itr.hasNext()) {
				FileItem fi = (FileItem) itr.next();

				//si es un campo del formulario y no un fichero
				if (fi.isFormField()) {
					int intField = 0;
					intField = compulsaHelper.mappingStrFieldToInt(fi
							.getFieldName());

					switch (intField) {
					
					case CompulsaKeys.FIELD_TYPE_XADESFORMAT: {
						//indicamos el resultado de la firma xades del fichero a compulsar
						//corresponde con el chorro de la firma xades se genera el fichero fisico xades
						compulsaVO.setXadesFormat(fi.getString());
						break;
					}
					
					case CompulsaKeys.FIELD_TYPE_CERTIFICATE: {
						//indicamos el certificado usado en la firma
						compulsaVO.setCertificate(fi.getString());
						break;
					}
					case CompulsaKeys.FIELD_TYPE_HASH: {
						//indicamos la hash del fichero //TODO no esta siendo seteado,se pone siempre ###HASH (FIRMA)###
						compulsaVO.setHash(fi.getString());
						break;
					}
					case CompulsaKeys.FIELD_TYPE_FUNCNAME: {
						//indicamos el nombre de la persona que firmo
						compulsaVO.setFuncName(compulsaHelper.getNombreFirmante(fi.getString()));
						break;
					}
					case CompulsaKeys.FIELD_TYPE_FILEXADES: {

						//contiene entre otros el nombre del fichero fisico del cliente donde se almaceno el resultado de la firma
						//se usa para componer nombre
						// contiene 0|1XDS_C:\Users\66194663\AppData\Local\Temp\IMG6D35.xades
						
						iSicresAbstractCompulsaVO = getISicresAbstractCompulsaVO(compulsaVO);
						iSicresAbstractCompulsaVO = getDatosEspecificosCompulsa(iSicresAbstractCompulsaVO);
						connector.generateXadesDocument(iSicresAbstractCompulsaVO);
						
						//se añade el fichero generado a filesInfo
						
						FlushFdrFile flushFdrFile = compulsaHelper.getDataFile(compulsaVO, fi.getString());
						filesInfo.add(flushFdrFile);

						break;
					}
					case CompulsaKeys.FIELD_TYPE_FILESCAN: {
						//si el campo es de tipo fichero escaneado se procedera a compulsar el fichero generando uno nuevo
						//fichero escaneado
						
						//obtiene datos del fichero a compulsar, el pdf q se escaneo
						FlushFdrFile flushFdrFile = compulsaHelper.getDataFile(compulsaVO, fi.getString());
						
						//obtiene datos para la generación del fichero compulsado
						FlushFdrFile flushFdrFileC = compulsaHelper.getFdrFileCompulsado(compulsaVO, flushFdrFile);

						//se prepara el vo para la lectura del fichero a compulsar y la salida del fichero compulsado
						compulsaVO.setInputStream(ISFileUtil.getInputStream(flushFdrFile.getBuffer(), flushFdrFile.getFileNameFis()));
						compulsaVO.setOutputStream(compulsaHelper.getOutputStream(compulsaVO, flushFdrFileC.getFileNameFis()));

						
						iSicresAbstractCompulsaVO = getISicresAbstractCompulsaVO(compulsaVO);
						iSicresAbstractCompulsaVO = getDatosEspecificosCompulsa(iSicresAbstractCompulsaVO);
						iSicresAbstractCompulsaVO = connector.compulsar(iSicresAbstractCompulsaVO);
						flushFdrFileC.loadFile();
						
						filesInfo.add(flushFdrFile);
						filesInfo.add(flushFdrFileC);
						break;
					}
					default: {
						break;
					}
					}

				}
			}

			returnVO.setFilesInfo(filesInfo);
			return returnVO;

		} catch (Exception e) {
			log.error("Resulta imposible compulsar el documento", e);
			return null;
		}

	}

	protected ISicresAbstractCompulsaVO getISicresAbstractCompulsaVO(
			ISicresCreateCompulsaVO compulsaVO) {
		ISicresBasicCompulsaVO basicVO = new ISicresBasicCompulsaVO();

		basicVO.setBookId(compulsaVO.getBookId());
		basicVO.setEntidadId(compulsaVO.getEntidad());
		basicVO.setFechaCompulsa(compulsaVO.getDateDataBaseServer());
		basicVO.setFolderId(compulsaVO.getFolderId());
		basicVO.setFolderNumber(compulsaVO.getFolderNumber());
		basicVO.setLocator(compulsaVO.getLocator());

		Map mapEspecificos = new HashMap();
		mapEspecificos.put(CompulsaKeys.KEY_SESSIONID, compulsaVO
				.getSessionID());

		mapEspecificos.put(CompulsaKeys.KEY_XADES_FORMAT, compulsaVO
				.getXadesFormat());
		mapEspecificos.put(CompulsaKeys.KEY_FIRMANTE, compulsaVO.getFuncName());
		mapEspecificos.put(CompulsaKeys.KEY_CERTIFICATE, compulsaVO
				.getCertificate());
		mapEspecificos.put(CompulsaKeys.KEY_HASH, compulsaVO.getHash());

		mapEspecificos
				.put(CompulsaKeys.KEY_PATH_TEMP, compulsaVO.getTempPath());
		mapEspecificos.put(CompulsaKeys.KEY_PATH_BEGIN, compulsaVO
				.getBeginPath());
		mapEspecificos.put(CompulsaKeys.KEY_PATH_FONDO, compulsaVO
				.getFondoPath());
		mapEspecificos.put(CompulsaKeys.KEY_PATH_DATOS, compulsaVO
				.getDatosPath());

		mapEspecificos.put(CompulsaKeys.KEY_MARGEN, String.valueOf(compulsaVO
				.getMargen()));
		mapEspecificos.put(CompulsaKeys.KEY_POSITION_Y, String
				.valueOf(compulsaVO.getPositionY()));
		mapEspecificos.put(CompulsaKeys.KEY_FONT, compulsaVO.getFont());
		mapEspecificos.put(CompulsaKeys.KEY_ENCODING, compulsaVO.getEncoding());
		mapEspecificos.put(CompulsaKeys.KEY_FONT_SIZE, String
				.valueOf(compulsaVO.getFontSize()));
		mapEspecificos.put(CompulsaKeys.KEY_BAND, String.valueOf(compulsaVO
				.getBand()));
		mapEspecificos.put(CompulsaKeys.KEY_BAND_SIZE, String
				.valueOf(compulsaVO.getBandSize()));

		mapEspecificos.put(CompulsaKeys.KEY_INPUTSTREAM, compulsaVO
				.getInputStream());
		mapEspecificos.put(CompulsaKeys.KEY_OUTPUTSTREAM, compulsaVO
				.getOutputStream());

		ISicresBasicDatosEspecificosCompulsaVO datosEspecificos = new ISicresBasicDatosEspecificosCompulsaVO();
		datosEspecificos.setValues(mapEspecificos);

		basicVO.setDatosEspecificos(datosEspecificos);

		return basicVO;

	}

	/**
	 * @return el compulsaHelper
	 */
	public GenericCompulsaManagerHelper getCompulsaHelper() {
		return compulsaHelper;
	}

	/**
	 * @param compulsaHelper
	 *            el compulsaHelper a fijar
	 */
	public void setCompulsaHelper(GenericCompulsaManagerHelper compulsaHelper) {
		this.compulsaHelper = compulsaHelper;
	}

	/**
	 * @return el connector
	 */
	public ISicresCompulsaConnector getConnector() {
		return connector;
	}

	/**
	 * @param connector
	 *            el connector a fijar
	 */
	public void setConnector(ISicresCompulsaConnector connector) {
		this.connector = connector;
	}

}
