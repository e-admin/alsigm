package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbTableDef;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;
import org.apache.log4j.Logger;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.TooManyResultsException;
import common.util.ArrayUtils;
import common.vos.ConsultaConnectBy;

import deposito.db.common.HuecoDbEntityImplBase;
import deposito.db.commonPostgreSQLServer.AsignableDBEntity;
import deposito.vos.ElementoAsignableVO;

public abstract class ElementoAsignableDBEntity extends DBEntity implements
		IElementoAsignableDBEntity {

	static Logger logger = Logger.getLogger(ElementoAsignableDBEntity.class);

	public static final String TABLE_NAME = "ASGDELEMASIG";

	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String IDTIPOELEMENTO_COLUMN_NAME = "idtipoelemento";
	public static final String NUMORDEN_COLUMN_NAME = "numorden";
	public static final String IDELEMNPADRE_COLUMN_NAME = "idelemnapadre";
	public static final String IDDEPOSITO_COLUMN_NAME = "iddeposito";
	public static final String LONGITUD_COLUMN_NAME = "longitud";
	public static final String IDFMTHUECO_COLUMN_NAME = "idformato";
	public static final String NUMHUECOS_COLUMN_NAME = "numhuecos";
	public static final String CODORDEN_COLUMN_NAME = "codorden";

	public static final String NUMHIJOS_COLUMN_NAME = "numHijos";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_IDTIPOELEMENTO = new DbColumnDef(
			null, IDTIPOELEMENTO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NUMORDEN = new DbColumnDef(null,
			NUMORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_IDELEMNPADRE = new DbColumnDef(
			"idPadre", IDELEMNPADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_IDDEPOSITO = new DbColumnDef(null,
			IDDEPOSITO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_LONGITUD = new DbColumnDef(null,
			LONGITUD_COLUMN_NAME, DbDataType.LONG_DECIMAL, false);

	public static final DbColumnDef CAMPO_IDFMTHUECO = new DbColumnDef(null,
			IDFMTHUECO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NUMHUECOS = new DbColumnDef(null,
			NUMHUECOS_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_CODORDEN = new DbColumnDef(null,
			CODORDEN_COLUMN_NAME, DbDataType.SHORT_TEXT, 50, false);

	// Para el calculo del volumen de la serie
	public static final DbColumnDef CAMPO_NUMHIJOS = new DbColumnDef(null,
			NUMHIJOS_COLUMN_NAME, DbDataType.LONG_INTEGER, 50, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { CAMPO_ID, CAMPO_NOMBRE,
			CAMPO_IDTIPOELEMENTO, CAMPO_NUMORDEN, CAMPO_IDELEMNPADRE,
			CAMPO_IDDEPOSITO, CAMPO_LONGITUD, CAMPO_IDFMTHUECO,
			CAMPO_NUMHUECOS, CAMPO_CODORDEN };

	protected static final String ALL_COLUMN_NAMES = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public ElementoAsignableDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public ElementoAsignableDBEntity(DbConnection conn) {
		super(conn);
	}

	public void insertElemento(final ElementoAsignableVO asignable) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				asignable.setId(getGuid(asignable.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, asignable);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(TABLE_COLUMNS), inputRecord);
			}
		};
		command.execute();
	}

	public void updateAsignable(ElementoAsignableVO asignableVO) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, asignableVO.getId()));
		updateVO(qual.toString(), TABLE_NAME, TABLE_COLUMNS, asignableVO);
	}

	public void delete(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	public void delete(String[] ids) {
		if (!ArrayUtils.isEmpty(ids)) {
			StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
					.generateInTokenField(CAMPO_ID, ids));
			deleteVO(qual.toString(), TABLE_NAME);
		}
	}

	public String[] getIdsElementosAsignables(String[] idsPadres) {
		final StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateInTokenField(CAMPO_IDELEMNPADRE, idsPadres));

		final DbColumnDef[] cols = new DbColumnDef[] { new DbColumnDef("value",
				CAMPO_ID) };

		List ids = getVOS(qual.toString(), TABLE_NAME, cols,
				MutableObject.class);
		String[] idsArray = new String[ids.size()];
		for (int i = 0; i < ids.size(); i++)
			idsArray[i] = (String) ((Mutable) ids.get(i)).getValue();
		return idsArray;
	}

	public List getLongitudElementosXRelacionEntrega(String idRelacion) {
		/*
		 * select asgdelemasig.id, asgdelemasig.numhuecos,
		 * asgdelemasig.longitud, hijos.numhijos from asgdelemasig asgdelemasig,
		 * (select asgdhueco.idelemapadre, asgdhueco.idformato,
		 * count(asgdhueco.idelemapadre) as numhijos from asgdhueco asgdhueco,
		 * agformato agformato, asgdelemasig asgdelemasig where
		 * asgdhueco.idformato=agformato.id and asgdelemasig.id =
		 * asgdhueco.idelemapadre and asgdhueco.idrelentrega='idRelacion' group
		 * by asgdhueco.idelemapadre,asgdhueco.idformato) hijos where
		 * asgdelemasig.id = hijos.idelemapadre;
		 */
		DbColumnDef col_numHijos = new DbColumnDef("numhijos",
				DbUtil.getCountColumn(HuecoDbEntityImplBase.CAMPO_IDELEMPADRE));

		DbTableDef tables[] = new DbTableDef[] {
				new DbTableDef(HuecoDbEntityImplBase.TABLE_NAME,
						HuecoDbEntityImplBase.TABLE_NAME),
				new DbTableDef(FormatoDBEntity.TABLE_NAME,
						FormatoDBEntity.TABLE_NAME),
				new DbTableDef(ElementoAsignableDBEntity.TABLE_NAME,
						ElementoAsignableDBEntity.TABLE_NAME) };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append("(")
				.append(DBUtils.SELECT)
				.append(HuecoDbEntityImplBase.CAMPO_IDELEMPADRE
						.getQualifiedName())
				.append(",")
				.append(DbUtil
						.getColumnNamesWithAlias(new DbColumnDef[] { col_numHijos }))
				.append(DBUtils.FROM)
				.append(DbUtil.getTableNames(tables))
				.append(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(
						HuecoDbEntityImplBase.TABLE_NAME,
						HuecoDbEntityImplBase.CAMPO_FORMATO,
						FormatoDBEntity.TABLE_NAME, FormatoDBEntity.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(TABLE_NAME, CAMPO_ID,
						HuecoDbEntityImplBase.TABLE_NAME,
						HuecoDbEntityImplBase.CAMPO_IDELEMPADRE))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						HuecoDbEntityImplBase.CAMPO_IDRELACION, idRelacion))
				.append(DBUtils.GROUPBY)
				.append(HuecoDbEntityImplBase.CAMPO_IDELEMPADRE
						.getQualifiedName()).append(", ")
				.append(HuecoDbEntityImplBase.CAMPO_FORMATO).append(")");

		DbTableDef table_hijos = new DbTableDef(sqlFrom.toString(), "hijos");
		DbColumnDef[] cols = new DbColumnDef[] { CAMPO_ID, CAMPO_NUMHUECOS,
				CAMPO_LONGITUD, CAMPO_NUMHIJOS };
		DbTableDef table_set[] = new DbTableDef[] {
				new DbTableDef(TABLE_NAME, TABLE_NAME), table_hijos };
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(DBUtils.WHERE)
				.append(DBUtils.getQualifiedColumnName(TABLE_NAME, CAMPO_ID))
				.append(" = ")
				.append(DBUtils.getQualifiedColumnName(table_hijos.getAlias(),
						HuecoDbEntityImplBase.CAMPO_IDELEMPADRE));

		return getVOS(sqlWhere.toString(), DbUtil.getTableNames(table_set),
				cols, ElementoAsignableVO.class);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IeciTdException
	 * @throws TooManyResultsException
	 * @see deposito.db.ElementoNoAsignableDBEntity#getElementosAsignablesByAmbitosPadre(java.lang.String[])
	 */
	public List getElementosAsignablesByAmbitosPadre(String[] idsAmbitoDeposito)
			throws IeciTdException, TooManyResultsException {

		// WITH IDS_CTE (id) AS
		// (SELECT id FROM asgdelemnoasig WHERE
		// (
		// (id IN ('0b98683a171830000000000000000405')
		// )
		// )
		// UNION ALL SELECT A.id FROM asgdelemnoasig A,IDS_CTE R WHERE
		// A.idpadre=R.id
		// )
		// SELECT id FROM asgdelemasig
		// WHERE idelemnapadre IN (
		// SELECT IDS_CTE.id FROM IDS_CTE
		// )
		// OR id IN ('0b98683a171830000000000000000405')

		ConsultaConnectBy consultaConnectBy = DbUtil
				.generateNativeSQLWithConnectBy(getConnection(), new TableDef(
						ElementoNoAsignableDBEntity.TABLE_NAME),
						ElementoNoAsignableDBEntity.CAMPO_ID,
						ElementoNoAsignableDBEntity.CAMPO_IDPADRE,
						idsAmbitoDeposito, null);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenFieldSubQuery(
						AsignableDBEntity.CAMPO_IDELEMNPADRE,
						consultaConnectBy.getSqlClause()))
				.append(DBUtils.OR)
				.append(DBUtils.generateInTokenField(
						AsignableDBEntity.CAMPO_ID, idsAmbitoDeposito));

		return getVOS(consultaConnectBy.getWithClause(), qual.toString(),
				TABLE_NAME, TABLE_COLUMNS, ElementoAsignableVO.class, "", 0,
				"", "");

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.IElementoAsignableDBEntity#isNumOrdenEnUso(java.lang.String,
	 *      int)
	 */
	public boolean isNumOrdenEnUso(String idElemPadre, int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN, numOrden))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMNPADRE,
						idElemPadre));

		if (getVOCount(qual.toString(), TABLE_NAME) > 0)
			return true;
		return false;
	}

	public int getCountByTipoElemento(String idTipoElemento) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDTIPOELEMENTO, idTipoElemento));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

}