package docelectronicos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.util.StringUtils;

import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocDocumentoVO;

/**
 * DBEntity para acceder a la tabla ADOCDOCUMENTODESCR.
 */
public class DocDocumentoDescrDBEntityImpl extends DBEntity implements
		IDocDocumentoDescrDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADOCDOCUMENTODESCR";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String ID_CLF_PADRE_COLUMN_NAME = "idclfpadre";
	public static final String ID_DESCR_COLUMN_NAME = "iddescr";
	public static final String TAMANO_FICH_COLUMN_NAME = "tamanofich";
	public static final String NOMBRE_ORG_FICH_COLUMN_NAME = "nombreorgfich";
	public static final String EXT_FICH_COLUMN_NAME = "extfich";
	public static final String ID_FICH_COLUMN_NAME = "idfich";
	public static final String ESTADO_COLUMN_NAME = "estado";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";
	public static final String ID_REP_ECM_COLUMN_NAME = "idRepEcm";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
	public static final DbColumnDef CAMPO_ID_CLF_PADRE = new DbColumnDef(null,
			TABLE_NAME, ID_CLF_PADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);
	public static final DbColumnDef CAMPO_ID_DESCR = new DbColumnDef(
			"idobjeto", TABLE_NAME, ID_DESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TAMANO_FICH = new DbColumnDef(null,
			TABLE_NAME, TAMANO_FICH_COLUMN_NAME, DbDataType.LONG_DECIMAL, false);
	public static final DbColumnDef CAMPO_NOMBRE_ORG_FICH = new DbColumnDef(
			null, TABLE_NAME, NOMBRE_ORG_FICH_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_EXT_FICH = new DbColumnDef(null,
			TABLE_NAME, EXT_FICH_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, false);
	public static final DbColumnDef CAMPO_ID_FICH = new DbColumnDef(null,
			TABLE_NAME, ID_FICH_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_ID_REP_ECM = new DbColumnDef(null,
			TABLE_NAME, ID_REP_ECM_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = { CAMPO_ID, CAMPO_NOMBRE,
			CAMPO_ID_CLF_PADRE, CAMPO_ID_DESCR, CAMPO_TAMANO_FICH,
			CAMPO_NOMBRE_ORG_FICH, CAMPO_EXT_FICH, CAMPO_ID_FICH, CAMPO_ESTADO,
			CAMPO_DESCRIPCION, CAMPO_ID_REP_ECM };

	/** Lista de nombres de columnas. */
	public static final String COL_NAMES = DbUtil.getColumnNames(COL_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public DocDocumentoDescrDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocDocumentoDescrDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idDescr, String idClfPadre, int[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idDescr))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
					.append(" AND ");

		qual.append(DBUtils
				.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre));

		if ((estados != null) && (estados.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		List documentos = getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				DocDocumentoVO.class);
		for (int i = 0; i < documentos.size(); i++)
			((DocDocumentoVO) documentos.get(i))
					.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return documentos;
	}

	public List getDocumentos(String idDescr, int[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idDescr))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr));

		if ((estados != null) && (estados.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		List documentos = getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				DocDocumentoVO.class);
		for (int i = 0; i < documentos.size(); i++)
			((DocDocumentoVO) documentos.get(i))
					.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return documentos;
	}

	/**
	 * Obtiene la lista de documentos electrónicos de un descriptor.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idDescr) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.toString();

		List documentos = getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				DocDocumentoVO.class);
		for (int i = 0; i < documentos.size(); i++)
			((DocDocumentoVO) documentos.get(i))
					.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return documentos;
	}

	/**
	 * Obtiene el documento electrónico.
	 * 
	 * @param id
	 *            Identificador del documento electrónico.
	 * @return Documento electrónico.
	 */
	public DocDocumentoVO getDocumento(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		DocDocumentoVO documento = (DocDocumentoVO) getVO(qual, TABLE_NAME,
				COL_DEFS, DocDocumentoVO.class);
		if (documento != null)
			documento.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return documento;
	}

	/**
	 * Crea un documento electrónico.
	 * 
	 * @param documento
	 *            Documento electrónico.
	 * @return Documento.S
	 */
	public DocDocumentoVO insertDocumento(DocDocumentoVO documento) {
		documento.setId(getGuid(documento.getId()));
		insertVO(TABLE_NAME, COL_DEFS, documento);
		return documento;
	}

	/**
	 * Modifica un documento electrónico.
	 * 
	 * @param documento
	 *            Documento electrónico.
	 */
	public void updateDocumento(DocDocumentoVO documento) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						documento.getId())).toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, documento);
	}

	/**
	 * Elimina un documento electrónico.
	 * 
	 * @param id
	 *            Identificador del documento electrónico.
	 */
	public void deleteDocumento(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina los documentos de un descriptor.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public void deleteDocumentos(String idDescr) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Cuenta el número de documentos asociados a un descriptor del cuadro de
	 * clasificación
	 * 
	 * @param idElementoCF
	 *            Identificador de descriptor
	 * @return Número de documentos asociados al descriptor
	 */
	public int countNumDocumentos(String id) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_DESCR, id));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IDocDocumentoDescrDBEntity#update(java.lang.String,
	 * int[], int)
	 */
	public void update(String idObj, int[] estadosAActualizar, int nuevoEstado) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idObj))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idObj));

		if ((estadosAActualizar != null) && (estadosAActualizar.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosAActualizar));

		HashMap colToUpdate = new HashMap();
		colToUpdate.put(CAMPO_ESTADO, new Integer(nuevoEstado));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);

	}

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return número de documentos electrónicos del descriptor cuyo padre es el
	 *         clasificador indicado
	 */
	public int getCountDocumentosByIdClfPadre(String idDescr, String idClfPadre) {
		int ret = 0;

		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idDescr))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
					.append(" AND ");

		qual.append(DBUtils
				.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre));

		ret = getVOCount(qual.toString(), TABLE_NAME);

		return ret;
	}

	public void deleteDescriptores(String[] idsDescriptores) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID_DESCR,
						idsDescriptores)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

}
