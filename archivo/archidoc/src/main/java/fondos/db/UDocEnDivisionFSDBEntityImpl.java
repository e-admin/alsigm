package fondos.db;

import fondos.vos.UDocEnFraccionSerieVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;

import descripcion.db.TextoCortoUDocREDBEntityImpl;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * unidades documentales en divisiones de fracciones de serie
 */
public class UDocEnDivisionFSDBEntityImpl extends DBEntity implements
		IUDocEnDivisionFSDbEntity {
	/** Logger de la clase */
	static Logger logger = Logger.getLogger(UDocEnDivisionFSDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGFUDOCENDIVISIONFS";

	private static final String IDFRACCIONSERIE_COLUMN_NAME = "IDFS";
	private static final String IDUDOC_COLUMN_NAME = "IDUDOC";
	private static final String NUMEXP_COLUMN_NAME = "NUMEXP";
	private static final String FECHAEXTINI_COLUMN_NAME = "FECHAEXTINI";
	private static final String FECHAEXTFIN_COLUMN_NAME = "FECHAEXTFIN";
	private static final String ASUNTO_COLUMN_NAME = "ASUNTO";
	private static final String ORDEN_COLUMN_NAME = "ORDEN";
	private static final String INFO_COLUMN_NAME = "INFO";
	private static final String VALIDADA_COLUMN_NAME = "VALIDADA";

	public static final DbColumnDef CAMPO_IDFS = new DbColumnDef(null,
			TABLE_NAME, IDFRACCIONSERIE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_ID_UDOC = new DbColumnDef(null,
			TABLE_NAME, IDUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NUMEXP = new DbColumnDef(null,
			TABLE_NAME, NUMEXP_COLUMN_NAME, DbDataType.SHORT_TEXT, 256, false);

	public static final DbColumnDef CAMPO_FECHAEXTINI = new DbColumnDef(null,
			TABLE_NAME, FECHAEXTINI_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_FECHAEXTFIN = new DbColumnDef(null,
			TABLE_NAME, FECHAEXTFIN_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_ASUNTO = new DbColumnDef(null,
			TABLE_NAME, ASUNTO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, false);

	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef CAMPO_INFO = new DbColumnDef("xmlInfo",
			INFO_COLUMN_NAME, DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef CAMPO_VALIDADA = new DbColumnDef(
			VALIDADA_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef[] COLS_DEFS_UDOCENDIVISIONFS = {
			CAMPO_IDFS, CAMPO_ID_UDOC, CAMPO_NUMEXP, CAMPO_FECHAEXTINI,
			CAMPO_FECHAEXTFIN, CAMPO_ASUNTO, CAMPO_ORDEN, CAMPO_INFO,
			CAMPO_VALIDADA };

	public static final String COLUMN_NAMES_UDOCENDIVISIONFS = DbUtil
			.getColumnNames(COLS_DEFS_UDOCENDIVISIONFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public UDocEnDivisionFSDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UDocEnDivisionFSDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Recupera la unidad documental en división de fracción de serie que
	 * devuelve la ejecución de la consulta sobre unidades documentales en
	 * división de fracción de serie con la clausula <i>WHERE</i> que se
	 * proporciona
	 * 
	 * @param qual
	 *            Clausula <i>WHERE</i> de la consulta a ejecutar
	 * @return UDocEnFraccionSerie
	 */
	protected UDocEnFraccionSerieVO getUDocEnDivisionFSVO(final String qual) {
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_UDOCENDIVISIONFS);

		return (UDocEnFraccionSerieVO) getVO(qual.toString(),
				pairsTableNameColsDefs, UDocEnFraccionSerieVO.class);
	}

	/**
	 * Recupera las unidades documentales en la división de fracción de serie
	 * cuyo identificador se pasa como parámetro
	 * 
	 * @param qual
	 *            Clausula <i>WHERE</i> de la consulta a ejecutar
	 * @return List de UDocEnFraccionSerie
	 */
	public List getUDocsEnDivisionFSVO(String idFS) {
		HashMap pairsTableNameColsDefs = new HashMap();
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDFS, idFS)).append(
				DBUtils.generateOrderBy(CAMPO_ORDEN));

		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_UDOCENDIVISIONFS);

		return getVOS(qual.toString(), pairsTableNameColsDefs,
				UDocEnFraccionSerieVO.class);
	}

	/**
	 * Recupera la unidad documental en división de fracción de serie cuyo
	 * identificador es el que se suministra
	 * 
	 * @param idUDoc
	 *            Identificador de la unidad documental
	 * @return Datos de la unidad documental en división de fracción de serie
	 */
	public UDocEnFraccionSerieVO getUDocEnDivisionFSXId(final String idUDoc) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_UDOC, idUDoc));

		return getUDocEnDivisionFSVO(qual.toString());
	}

	/**
	 * Inserta una nueva unidad documental en división de fracción de serie en
	 * la base de datos
	 * 
	 * @param UDocEnFraccionSerieVO
	 *            Datos de la unidad documental en división de fracción de serie
	 *            a guardar
	 */
	public void insertUDocEnDivisionFS(
			final UDocEnFraccionSerieVO udocEnFraccionSerieVO) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				udocEnFraccionSerieVO.setIdUDoc(getGuid(udocEnFraccionSerieVO
						.getIdUDoc()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_UDOCENDIVISIONFS, udocEnFraccionSerieVO);
				DbInsertFns.insert(conn, TABLE_NAME,
						COLUMN_NAMES_UDOCENDIVISIONFS, inputRecord);
			}
		};

		command.execute();

		// return udocEnFraccionSerieVO;
	}

	/**
	 * Actualiza los datos de la unidad documental en división de fracciones de
	 * serie que se indica por parámetro
	 * 
	 * @param udocEnFraccionSerieVO
	 *            Datos de la unidad documental en división de fracción de serie
	 *            a actualizar
	 */
	public void updateUDocEnDivisionFS(
			final UDocEnFraccionSerieVO udocEnFraccionSerieVO) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_UDOC,
						udocEnFraccionSerieVO.getIdUDoc()));

		updateVO(qual.toString(), TABLE_NAME, COLS_DEFS_UDOCENDIVISIONFS,
				udocEnFraccionSerieVO);
	}

	/**
	 * Elimina la unidad documental en división de fracción de serie indicada de
	 * la tabla de unidades documentales en divisiones de fracción de serie
	 * 
	 * @param idUDoc
	 *            : identificador de la unidad documental en fracción de serie
	 */
	public void deleteUDocEnDivisionFS(String idUDoc) {
		final String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_UDOC, idUDoc))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

	/**
	 * Elimina las unidades documentales en división de fracción de serie
	 * indicadas de la tabla de unidades documentales en divisiones de fracción
	 * de serie
	 * 
	 * @param idsUDoc
	 *            : identificadores de las unidad documentales en fracción de
	 *            serie a eliminar
	 */
	public void deleteUDocsEnDivisionFS(String[] idsUDoc) {
		final String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID_UDOC, idsUDoc))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

	/**
	 * Elimina las unidades documentales en la división de fracción de serie
	 * indicada de la tabla de unidades documentales en divisiones de fracción
	 * de serie
	 * 
	 * @param idFS
	 *            : identificador de la fracción de serie
	 */
	public void deleteUDocsEnDivisionFS(String idFS) {
		final String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDFS, idFS))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

	/**
	 * Actualiza el asunto de la unidad documental en división de fracciones de
	 * serie que se indica por parámetro
	 * 
	 * @param idUDoc
	 *            identificador de la unidad documental en división de fracción
	 *            de serie a actualizar
	 * @param asunto
	 *            nuevo asunto a establecer para la unidad documental en
	 *            división de fracción de serie
	 */
	public void updateAsunto(String idUDoc, String asunto) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_UDOC, idUDoc));

		Map colToUpdate = Collections.singletonMap(CAMPO_ASUNTO, asunto);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza el número de expediente de la unidad documental en división de
	 * fracciones de serie que se indica por parámetro
	 * 
	 * @param idUDoc
	 *            identificador de la unidad documental en división de fracción
	 *            de serie a actualizar
	 * @param número
	 *            de expediente nuevo número de expediente a establecer para la
	 *            unidad documental en división de fracción de serie
	 */
	public void updateNumeroExpediente(String idUDoc, String numExp) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_UDOC, idUDoc));

		Map colToUpdate = Collections.singletonMap(CAMPO_NUMEXP, numExp);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza el campo info de la unidad documental en división de fracciones
	 * de serie que se indica por parámetro
	 * 
	 * @param idUDoc
	 *            identificador de la unidad documental en división de fracción
	 *            de serie a actualizar
	 * @param xmlInfo
	 *            nueva información para la unidad documental en división de
	 *            fracción de serie
	 */
	public void updateXmlInfo(String idUDoc, String xmlInfo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_UDOC, idUDoc));

		Map colToUpdate = Collections.singletonMap(CAMPO_INFO, xmlInfo);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void setEstadoValidacion(String idFS, boolean validada) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDFS, idFS));
		Map colToUpdate = Collections.singletonMap(CAMPO_VALIDADA,
				validada ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void updateFechaExtIni(String idUdocFS, Date fechaExtIni) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_UDOC, idUdocFS));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_FECHAEXTINI, fechaExtIni);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void updateFechaExtFin(String idUdocFS, Date fechaExtFin) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_UDOC, idUdocFS));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_FECHAEXTFIN, fechaExtFin);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public DbColumnDef getCustomizedField(DbColumnDef column, String alias) {
		return new DbColumnDef(column.getBindPropertyVO(), alias,
				column.getName(), column.getDataType(), column.getMaxLen(),
				column.isNullable());
	}

	/**
	 * Obtiene la lista de rangos de una fracción de serie en división de
	 * fracción de serie
	 * 
	 * @param idUDoc
	 *            Identificador de la fracción de serie en la división de
	 *            fracción de serie
	 * @param idCampoInicial
	 *            Identificador del campo rango inicial
	 * @param idCampoFinal
	 *            Identificador del campo rango final
	 * @return Lista de rangos.
	 */
	public List getRangosUDoc(String idUDoc, String idCampoInicial,
			String idCampoFinal) {

		StringBuffer sqlFrom = new StringBuffer();
		String aliasTextoCorto1 = "TC1", aliasTextoCorto2 = "TC2";
		TableDef tablaTextoCorto1 = new TableDef(
				TextoCortoUDocREDBEntityImpl.TABLE_NAME, aliasTextoCorto1);
		TableDef tablaTextoCorto2 = new TableDef(
				TextoCortoUDocREDBEntityImpl.TABLE_NAME, aliasTextoCorto2);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaTextoCorto1, getCustomizedField(
						TextoCortoUDocREDBEntityImpl.CAMPO_ID_ELEMENTO,
						aliasTextoCorto1)), new DbColumnDef(tablaTextoCorto2,
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_ELEMENTO,
								aliasTextoCorto2))) };

		sqlFrom.append(DBUtils.generateInnerJoinCondition(tablaTextoCorto1,
				joins));

		sqlFrom.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_ELEMENTO,
								aliasTextoCorto1), idUDoc))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ORDEN,
								aliasTextoCorto1),
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ORDEN,
								aliasTextoCorto2)))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_CAMPO,
								aliasTextoCorto1), idCampoInicial))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						getCustomizedField(
								TextoCortoUDocREDBEntityImpl.CAMPO_ID_CAMPO,
								aliasTextoCorto2), idCampoFinal));

		DbColumnDef[] columns = new DbColumnDef[] {
				new DbColumnDef("desde", getCustomizedField(
						TextoCortoUDocREDBEntityImpl.CAMPO_VALOR,
						aliasTextoCorto1)),
				new DbColumnDef("hasta", getCustomizedField(
						TextoCortoUDocREDBEntityImpl.CAMPO_VALOR,
						aliasTextoCorto2)), };

		return getVOS(Constants.STRING_EMPTY, sqlFrom.toString(), columns,
				transferencias.vos.RangoVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IUDocEnDivisionFSDbEntity#getUdocsByInfoDescriptor(java.lang
	 * .String[])
	 */
	public List getUdocsByInfoDescriptor(String[] idsDescriptores) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);

		for (int i = 0; i < idsDescriptores.length; i++) {
			if (i > 0) {
				qual.append(DBUtils.OR);
			}
			qual.append(DBUtils.generateLikeTokenField(CAMPO_INFO,
					idsDescriptores[i], false));
		}

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_UDOCENDIVISIONFS);

		return getVOS(qual.toString(), pairsTableNameColsDefs,
				UDocEnFraccionSerieVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IUDocEnDivisionFSDbEntity#getNextOrdenUdocInDivisionFs(java
	 * .lang.String, int)
	 */
	public int getNextOrdenUdocInDivisionFs(String idDivFs, int orden) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDFS, idDivFs))
				.append(DBUtils.AND)
				.append(DBUtils.generateGTTokenField(CAMPO_ORDEN, orden));

		return getMin(qual.toString(), TABLE_NAME, CAMPO_ORDEN);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IUDocEnDivisionFSDbEntity#getPrevOrdenUdocInDivisionFs(java
	 * .lang.String, int)
	 */
	public int getPrevOrdenUdocInDivisionFs(String idDivFs, int orden) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDFS, idDivFs))
				.append(DBUtils.AND)
				.append(DBUtils.generateLTTokenField(CAMPO_ORDEN, orden));

		return getMax(qual.toString(), TABLE_NAME, CAMPO_ORDEN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IUDocEnDivisionFSDbEntity#getUdocInDivisionFsByOrden(java.lang
	 * .String, int)
	 */
	public UDocEnFraccionSerieVO getUdocInDivisionFsByOrden(String idDivFs,
			int orden) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDFS, idDivFs))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ORDEN, orden));
		return getUDocEnDivisionFSVO(qual.toString());
	}

	public int getCountUDocsEnDivisionFSVO(String idFS) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		qual.append(DBUtils.generateEQTokenField(CAMPO_IDFS, idFS));

		return getVOCount(qual.toString(), getTableName());
	}

}