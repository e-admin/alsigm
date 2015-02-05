package docelectronicos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.StringUtils;

import deposito.db.DepositoElectronicoDBEntityImpl;
import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocDocumentoVO;

/**
 * DBEntity para acceder a la tabla ADOCDOCUMENTOCF.
 */
public class DocDocumentoCFDBEntityImpl extends DBEntity implements
		IDocDocumentoCFDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADOCDOCUMENTOCF";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String ID_CLF_PADRE_COLUMN_NAME = "idclfpadre";
	public static final String ID_ELEMENTO_CF_COLUMN_NAME = "idelementocf";
	public static final String TAMANO_FICH_COLUMN_NAME = "tamanofich";
	public static final String NOMBRE_ORG_FICH_COLUMN_NAME = "nombreorgfich";
	public static final String EXT_FICH_COLUMN_NAME = "extfich";
	public static final String ID_EXT_DEPOSITO_COLUMN_NAME = "idextdeposito";
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
	public static final DbColumnDef CAMPO_ID_ELEMENTO_CF = new DbColumnDef(
			"idobjeto", TABLE_NAME, ID_ELEMENTO_CF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TAMANO_FICH = new DbColumnDef(null,
			TABLE_NAME, TAMANO_FICH_COLUMN_NAME, DbDataType.LONG_DECIMAL, false);
	public static final DbColumnDef CAMPO_NOMBRE_ORG_FICH = new DbColumnDef(
			null, TABLE_NAME, NOMBRE_ORG_FICH_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_EXT_FICH = new DbColumnDef(null,
			TABLE_NAME, EXT_FICH_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, false);
	public static final DbColumnDef CAMPO_ID_EXT_DEPOSITO = new DbColumnDef(
			null, TABLE_NAME, ID_EXT_DEPOSITO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
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
			CAMPO_ID_CLF_PADRE, CAMPO_ID_ELEMENTO_CF, CAMPO_TAMANO_FICH,
			CAMPO_NOMBRE_ORG_FICH, CAMPO_EXT_FICH, CAMPO_ID_EXT_DEPOSITO,
			CAMPO_ID_FICH, CAMPO_ESTADO, CAMPO_DESCRIPCION, CAMPO_ID_REP_ECM

	};

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
	public DocDocumentoCFDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocDocumentoCFDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idElementoCF, String idClfPadre,
			int[] estados) {

		// HashMap pairs = new HashMap();
		// pairs.put(TABLE_NAME,COL_DEFS);
		// pairs.put(DepositoElectronicoDBEntityImpl.TABLE_NAME,
		// new DbColumnDef [] {
		// new DbColumnDef("nombreDeposito",
		// DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE) });
		// TODO ZMIGRACION BD - OUTER JOIN (PROBADO
		StringBuffer qual = new StringBuffer().append(" WHERE ");
		// .append(DBUtils.generateOuterJoinCondition(
		// DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT,
		// CAMPO_ID_EXT_DEPOSITO))
		// .append(" AND ");

		if (StringUtils.isNotBlank(idElementoCF))
			qual.append(
					DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
							idElementoCF)).append(" AND ");

		qual.append(DBUtils
				.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre));

		if ((estados != null) && (estados.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		TableDef tablaDocElectronicos = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDocElectronicos, CAMPO_ID_EXT_DEPOSITO),
				new DbColumnDef(new TableDef(
						DepositoElectronicoDBEntityImpl.TABLE_NAME),
						DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT)), };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
				tablaDocElectronicos, joins));

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] { DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE },
						COL_DEFS);

		// List documentos = getVOS(qual.toString(), pairs,
		// DocDocumentoVO.class);
		List documentos = getVOS(qual.toString(), sqlFrom.toString(),
				COLS_DEF_QUERY, DocDocumentoVO.class);
		for (int i = 0; i < documentos.size(); i++)
			((DocDocumentoVO) documentos.get(i))
					.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

		return documentos;
	}

	/**
	 * Cuenta el número de documentos asociados a un elemento del cuadro de
	 * clasificación
	 *
	 * @param idElementoCF
	 *            Identificador de elemento del cuadro de clasificación
	 * @return Número de documentos asociados al elemento
	 */
	public int countNumDocumentos(String idElementoCF) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID_ELEMENTO_CF, idElementoCF));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idElementoCF) {
		// HashMap pairs = new HashMap();
		// pairs.put(TABLE_NAME,COL_DEFS);
		// pairs.put(DepositoElectronicoDBEntityImpl.TABLE_NAME,
		// new DbColumnDef [] {
		// new DbColumnDef("nombreDeposito",
		// DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE) });
		// TODO ZMIGRACION BD - OUTER JOIN (SIN PROBAR - HAY QUE TESTEARLO CON
		// EL PROCESO BATCH DE BORRAR U.DOC. NO DEVUELTAS ... ¿IMPLEMENTADO?
		StringBuffer qual = new StringBuffer().append(" WHERE ")
		// .append(DBUtils.generateOuterJoinCondition(DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT,
		// CAMPO_ID_EXT_DEPOSITO))
		// .append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
						idElementoCF));

		TableDef tablaDocElectronicos = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDocElectronicos, CAMPO_ID_EXT_DEPOSITO),
				new DbColumnDef(new TableDef(
						DepositoElectronicoDBEntityImpl.TABLE_NAME),
						DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT)), };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
				tablaDocElectronicos, joins));

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] { DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE },
						COL_DEFS);

		List documentos = getVOS(qual.toString(), sqlFrom.toString(),
				COLS_DEF_QUERY, DocDocumentoVO.class);
		//
		// List documentos = getVOS(qual.toString(), pairs,
		// DocDocumentoVO.class);
		for (int i = 0; i < documentos.size(); i++)
			((DocDocumentoVO) documentos.get(i))
					.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

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
		// HashMap pairs = new HashMap();
		// pairs.put(TABLE_NAME,COL_DEFS);
		// pairs.put(DepositoElectronicoDBEntityImpl.TABLE_NAME,
		// new DbColumnDef [] {
		// new DbColumnDef("nombreDeposito",
		// DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE) });
		// TODO ZMIGRACION BD - OUTER JOIN (PROBADO
		String qual = new StringBuffer().append(" WHERE ")
		// .append(DBUtils.generateOuterJoinCondition(DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT,
		// CAMPO_ID_EXT_DEPOSITO))
		// .append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		TableDef tablaDocElectronicos = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDocElectronicos, CAMPO_ID_EXT_DEPOSITO),
				new DbColumnDef(new TableDef(
						DepositoElectronicoDBEntityImpl.TABLE_NAME),
						DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT)), };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
				tablaDocElectronicos, joins));

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] { DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE },
						COL_DEFS);

		DocDocumentoVO documento = (DocDocumentoVO) getVO(qual,
				sqlFrom.toString(), COLS_DEF_QUERY, DocDocumentoVO.class);

		// DocDocumentoVO documento = (DocDocumentoVO) getVO(qual, pairs,
		// DocDocumentoVO.class);
		if (documento != null)
			documento.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

		return documento;
	}

	/**
	 * Crea un documento electrónico.
	 *
	 * @param documento
	 *            Documento electrónico.
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
	 * Elimina los documentos de un elemento del cuadro de clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 */
	public void deleteDocumentos(String idElementoCF) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
						idElementoCF)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * docelectronicos.db.IDocDocumentoCFDBEntity#getDocumentos(java.lang.String
	 * , int[])
	 */
	public List getDocumentos(String idElementoCF, int[] estados) {
		// HashMap pairs = new HashMap();
		// pairs.put(TABLE_NAME,COL_DEFS);
		// pairs.put(DepositoElectronicoDBEntityImpl.TABLE_NAME,
		// new DbColumnDef [] {
		// new DbColumnDef("nombreDeposito",
		// DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE) });
		// // TODO ZMIGRACION BD - OUTER JOIN (PROBADO
		StringBuffer qual = new StringBuffer().append(" WHERE 1=1 ");
		// .append(DBUtils.generateOuterJoinCondition(DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT,
		// CAMPO_ID_EXT_DEPOSITO));

		if (StringUtils.isNotBlank(idElementoCF))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
							idElementoCF));

		if ((estados != null) && (estados.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		TableDef tablaDocElectronicos = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDocElectronicos, CAMPO_ID_EXT_DEPOSITO),
				new DbColumnDef(new TableDef(
						DepositoElectronicoDBEntityImpl.TABLE_NAME),
						DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT)), };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
				tablaDocElectronicos, joins));

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] { DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE },
						COL_DEFS);

		// List documentos = getVOS(qual.toString(), pairs,
		// DocDocumentoVO.class);
		List documentos = getVOS(qual.toString(), sqlFrom.toString(),
				COLS_DEF_QUERY, DocDocumentoVO.class);

		// List documentos = getVOS(qual.toString(), pairs,
		// DocDocumentoVO.class);
		for (int i = 0; i < documentos.size(); i++)
			((DocDocumentoVO) documentos.get(i))
					.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

		return documentos;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see docelectronicos.db.IDocDocumentoCFDBEntity#update(java.lang.String,
	 * int[], int)
	 */
	public void update(String idObj, int[] estadosAActualizar, int nuevoEstado) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idObj))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
					idObj));

		if ((estadosAActualizar != null) && (estadosAActualizar.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosAActualizar));

		HashMap colToUpdate = new HashMap();
		colToUpdate.put(CAMPO_ESTADO, new Integer(nuevoEstado));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);

	}

	/**
	 * Obtiene el número de documentos almacenados en un depósito electrónico.
	 *
	 * @param idExtDeposito
	 *            Identificador externo del depósito electrónico.
	 * @return Número de documentos.
	 */
	public int getCountDocumentosByIdExtDeposito(String idExtDeposito) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_EXT_DEPOSITO,
						idExtDeposito)).toString();

		return getVOCount(qual, TABLE_NAME);
	}

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Listas de documentos electrónicos.
	 */
	public int getCountDocumentosByIdClfPadre(String idElementoCF,
			String idClfPadre) {
		int ret = 0;

		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idElementoCF))
			qual.append(
					DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
							idElementoCF)).append(" AND ");

		qual.append(DBUtils
				.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre));

		ret = getVOCount(qual.toString(), TABLE_NAME);

		return ret;
	}

	/**
	 * Obtiene un Map cuyas claves son los ids de elementos del cuadro y sus
	 * valores la lista de identificadores de documentos electrónicos asociados
	 * a una lista de elementos del cuadro de clasificación.
	 *
	 * @param idsElementosCF
	 *            Identificador de la lista de ids de elementos del cuadro de
	 *            clasificación.
	 * @return Listas de identificadores de documentos electrónicos.
	 */
	public Map getDocumentos(List idsElementosCF) {
		Map idsDocumentos = new HashMap();

		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateInTokenField(CAMPO_ID_ELEMENTO_CF,
						idsElementosCF));

		TableDef tablaDocElectronicos = new TableDef(TABLE_NAME);

		StringBuffer sqlFrom = new StringBuffer().append(tablaDocElectronicos
				.getDeclaration());

		DbColumnDef[] COLS_DEF_QUERY = new DbColumnDef[] {
				CAMPO_ID_ELEMENTO_CF, CAMPO_ID_FICH };

		List documentos = getVOS(qual.toString(), sqlFrom.toString(),
				COLS_DEF_QUERY, DocDocumentoVO.class);

		for (int i = 0; i < documentos.size(); i++) {
			DocDocumentoVO documento = ((DocDocumentoVO) documentos.get(i));

			Object obj = idsDocumentos.get(documento.getIdObjeto());
			ArrayList elem = null;
			if (obj != null)
				elem = (ArrayList) obj;
			else
				elem = new ArrayList();

			elem.add(documento.getIdFich());
			idsDocumentos.put(documento.getIdObjeto(), elem);
		}

		return idsDocumentos;
	}

	public DocDocumentoVO getDocumentoByIdInterno(String idInterno) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_FICH, idInterno))
				.toString();

		TableDef tablaDocElectronicos = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaDocElectronicos, CAMPO_ID_EXT_DEPOSITO),
				new DbColumnDef(new TableDef(
						DepositoElectronicoDBEntityImpl.TABLE_NAME),
						DepositoElectronicoDBEntityImpl.CAMPO_ID_EXT)), };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
				tablaDocElectronicos, joins));

		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] { DepositoElectronicoDBEntityImpl.CAMPO_NOMBRE },
						COL_DEFS);

		DocDocumentoVO documento = (DocDocumentoVO) getVO(qual,
				sqlFrom.toString(), COLS_DEF_QUERY, DocDocumentoVO.class);
		if (documento != null)
			documento.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

		return documento;
	}

	public static String getCountSQL(DbColumnDef columna, String nombreColumna) {
		/*
		 * SELECT COUNT(1) FROM adocdocumentocf adocdocumentocf WHERE
		 * ELEMENTOCF.id=adocdocumentocf.idelementocf
		 */

		StringBuffer sql = new StringBuffer(DBUtils.ABRIR_PARENTESIS)
				.append(DBUtils.SELECT)
				.append(DBUtils.getCountDefault())
				.append(DBUtils.FROM)
				.append(new TableDef(TABLE_NAME).getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField1(columna,
						CAMPO_ID_ELEMENTO_CF))
				.append(DBUtils.CERRAR_PARENTESIS)
				.append(Constants.STRING_SPACE)
		// .append(nombreColumna)
		;

		return sql.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see docelectronicos.db.IDocDocumentoCFDBEntity#updateIdElementocf(java.lang.String,
	 *      java.lang.String, java.lang.String[])
	 */
	public void updateIdElementocf(String idElementocfAntiguo,
			String idElementoCfNuevo, String[] idsInternosDocumentos) {

		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
						idElementocfAntiguo))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(CAMPO_ID,
						idsInternosDocumentos));

		HashMap colToUpdate = new HashMap();
		colToUpdate.put(CAMPO_ID_ELEMENTO_CF, idElementoCfNuevo);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

}