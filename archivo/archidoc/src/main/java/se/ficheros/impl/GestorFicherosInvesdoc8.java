package se.ficheros.impl;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbDataType;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.fss.core.FssBnoFileEx;
import ieci.tecdoc.sbo.fss.core.FssFileInfo;
import ieci.tecdoc.sbo.fss.core.FssFtsInfo;
import ieci.tecdoc.sbo.fss.core.FssMdoUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.ficheros.IGestorFicheros;
import se.ficheros.exceptions.GestorFicherosException;
import se.repositoriosECM.vo.MotorDocumental;
import se.repositoriosECM.vo.RepositorioEcmInternoVO;
import util.MultiEntityUtil;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.MultiEntityConstants;
import common.util.ArrayUtils;
import common.util.NumberUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import docelectronicos.vos.DocDocumentoExtVO;
import docelectronicos.vos.IRepositorioEcmVO;

public class GestorFicherosInvesdoc8 implements IGestorFicheros {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorFicherosInvesdoc8.class);

	/** Configuración de la conexión con InvesDoc. */
	protected DbConnectionConfig dbConnectionConfig = null;

	/** Conexión con la bbdd de InvesDoc. */
	protected Connection connection = null;

	/** Tipo de base de datos de InvesDoc **/
	protected int dbEngine = -1;

	/** Definición de la columna identificador de documento **/
	protected DbColumnDef ID = new DbColumnDef("ID", DbDataType.LONG_TEXT,
			false);

	protected RepositorioEcmInternoVO listaVolumenesVO = null;

	public GestorFicherosInvesdoc8(IRepositorioEcmVO repositorioEcm) {
		this.listaVolumenesVO = (RepositorioEcmInternoVO) repositorioEcm;
	}

	public void initialize(Properties params) {
		try {

			String entity = null;
			// Buscar la propiedad entidad
			if ((params != null)
					&& (params.containsKey(MultiEntityConstants.ENTITY_PARAM))) {
				entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
			}

			// Obtener el dataSource de almacenamiento
			String dataSource = ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionAlmacenamiento().getDataSourceNameAlmacenamiento();
			if (StringUtils.isNotEmpty(entity))
				dataSource = MultiEntityUtil.composeDsName(dataSource, entity);
			dbConnectionConfig = new DbConnectionConfig(dataSource, null, null);

		} catch (Exception e) {
			logger.error("Error leyendo el nombre del dataSource", e);
			throw new GestorFicherosException(e, this.getClass().getName(),
					"Error leyendo el nombre del dataSource");
		}
	}

	public byte[] retrieveFile(String id) throws Exception {
		return FssBnoFileEx.retrieveFile(dbConnectionConfig, getId(id));
	}

	public String storeFile(DocDocumentoExtVO docDocumentoExtVO)
			throws Exception {

		String ext = docDocumentoExtVO.getExtFich();
		String idListaVolumenes = listaVolumenesVO.getIdReal();

		FssFtsInfo ftsInfo = null;
		FssFileInfo fileInfo = new FssFileInfo();

		fileInfo.m_ext = ext;

		MotorDocumental motorDocumental = listaVolumenesVO.getMotorDocumental();

		// Comprobar si hay que hacer indexado por el motor documental
		if ((motorDocumental != null)
				&& (StringUtils.isNotEmpty(motorDocumental
						.getPlataformaMotorDocumental()))) {

			// Comprobar si la extensión del fichero está entre las extensiones
			// que requieren indexado
			// Si no hay ninguna extensión definida por defecto no se indexa.
			String[] extensiones = motorDocumental
					.getExtensionesArrayMotorDocumental();

			if (ArrayUtils.isNotEmpty(extensiones)
					&& ArrayUtils.contains(extensiones, ext.toUpperCase())) {
				fileInfo.m_flags = FssMdoUtil.FILE_FLAG_FTS;

				ftsInfo = new FssFtsInfo();
				ftsInfo.m_ftsPlatform = motorDocumental
						.getPlataformaIntMotorDocumental();
				ftsInfo.m_ftsRoot = motorDocumental
						.getRutaPlataformaMotorDocumental();
			}
		}

		int idDocumento = FssBnoFileEx.storeFileInList(dbConnectionConfig,
				getId(idListaVolumenes), fileInfo, ftsInfo,
				docDocumentoExtVO.getContenido());
		return "" + idDocumento;
	}

	public void deleteFile(String idFichero) throws Exception {
		int iFileId = TypeConverter.toInt(idFichero, -1);
		if (iFileId > 0) {
			FssBnoFileEx.deleteFile(dbConnectionConfig, iFileId);
		}
	}

	public List<String> findFileByContent(String searchString, List docsIds)
			throws Exception {
		return null;
	}

	private int getId(String id) {
		if (NumberUtils.isInteger(id)) {
			return Integer.parseInt(id);
		}
		return -1;
	}

	protected int getIdDocumento(String idDocumento) {
		if (NumberUtils.isInteger(idDocumento)) {
			return Integer.parseInt(idDocumento);
		}
		return -1;
	}
}
