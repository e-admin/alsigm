package deposito.db.common;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbSelectStatement;

import java.util.List;

import common.db.AliasedColumnDef;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.TableDef;
import common.util.ArrayUtils;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.ElementoAsignableDBEntity;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;

public abstract class AsignableDBEntityBase extends ElementoAsignableDBEntity {

	protected static final TableDef TABLE_DEFINITION = new TableDef(TABLE_NAME,
			"asignable");

	public static final DbColumnDef ALIASED_ID = new AliasedColumnDef(null,
			ID_COLUMN_NAME, DbDataType.SHORT_TEXT, false, TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_NOMBRE = new AliasedColumnDef(null,
			NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, false, TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_IDTIPOELEMENTO = new AliasedColumnDef(
			null, IDTIPOELEMENTO_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_NUMORDEN = new AliasedColumnDef(
			null, NUMORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false,
			TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_IDELEMNPADRE = new AliasedColumnDef(
			"idPadre", IDELEMNPADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_IDDEPOSITO = new AliasedColumnDef(
			null, IDDEPOSITO_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_LONGITUD = new AliasedColumnDef(
			null, LONGITUD_COLUMN_NAME, DbDataType.LONG_DECIMAL, false,
			TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_IDFMTHUECO = new AliasedColumnDef(
			null, IDFMTHUECO_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_NUMHUECOS = new AliasedColumnDef(
			null, NUMHUECOS_COLUMN_NAME, DbDataType.LONG_INTEGER, false,
			TABLE_DEFINITION);
	public static final DbColumnDef ALIASED_CODORDEN = new AliasedColumnDef(
			null, CODORDEN_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);

	protected static final String TABLE_LIST = ArrayUtils.join(
			new String[] { TABLE_DEFINITION.getDeclaration(),
					DepositoDBEntityImpl.TABLE_NAME }, ",");

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public AsignableDBEntityBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public AsignableDBEntityBase(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public abstract List getAsignables(String qual, String orderBy);

	// private List getAsignables(String qual, String orderBy) {
	// StringBuffer whereClause = new StringBuffer(qual);
	// if (whereClause.length() > 0)
	// whereClause.append(" AND ");
	// else
	// whereClause.append(" WHERE ");
	// whereClause.append(DBUtils.generateJoinCondition(
	// DepositoDBEntityImpl.CAMPO_ID,
	// ALIASED_IDDEPOSITO));
	// if (orderBy != null)
	// whereClause.append(orderBy);
	// return getVOS(whereClause.toString(), TABLE_LIST, COLUMNS_PATHS_INCLUDED,
	// ElementoAsignableVO.class);
	// }

	public abstract ElementoAsignableVO getAsignable(String qual);

	// private ElementoAsignableVO getAsignable(String qual) {
	// StringBuffer whereClause = new StringBuffer(qual);
	// if (whereClause.length() > 0)
	// whereClause.append(" AND ");
	// else
	// whereClause.append(" WHERE ");
	// whereClause.append(DBUtils.generateJoinCondition(
	// DepositoDBEntityImpl.CAMPO_ID,
	// ALIASED_IDDEPOSITO));
	// return (ElementoAsignableVO) getVO(whereClause.toString(), TABLE_LIST,
	// COLUMNS_PATHS_INCLUDED, ElementoAsignableVO.class);
	// }

	public List loadAsignablesElements(String idElementoPadre) {

		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ALIASED_IDELEMNPADRE, idElementoPadre));
		StringBuffer orderBy = new StringBuffer(" ORDER BY ").append(
				ALIASED_NUMORDEN.getQualifiedName()).append(" ASC ");

		return getAsignables(qual.toString(), orderBy.toString());
	}

	public ElementoAsignableVO loadAsignableElement(String idElementoPadre,
			int numOrden) {

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(ALIASED_IDELEMNPADRE,
						idElementoPadre))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(ALIASED_NUMORDEN, numOrden));

		return getAsignable(qual.toString());
	}

	public ElementoAsignableVO loadElementoAsignable(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ALIASED_ID, id));
		return getAsignable(qual.toString());
	}

	public List loadAsignablesElementsMayorQueOrden(String idElementoPadre,
			int numOrden) {

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(ALIASED_IDELEMNPADRE,
						idElementoPadre))
				.append(" AND ")
				.append(DBUtils
						.generateGTTokenField(ALIASED_NUMORDEN, numOrden));

		StringBuffer orderBy = new StringBuffer(" ORDER BY ").append(
				ALIASED_NUMORDEN.getQualifiedName()).append(" ASC ");

		return getAsignables(qual.toString(), orderBy.toString());
	}

	public List loadAsignablesElementsMayorIgualQueOrden(
			String idElementoPadre, int numOrden) {

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(ALIASED_IDELEMNPADRE,
						idElementoPadre))
				.append(" AND ")
				.append(DBUtils.generateGTEQTokenField(ALIASED_NUMORDEN,
						numOrden));

		StringBuffer orderBy = new StringBuffer(" ORDER BY ").append(
				ALIASED_NUMORDEN.getQualifiedName()).append(" ASC ");

		return getAsignables(qual.toString(), orderBy.toString());
	}

	public ElementoAsignableVO getElementoByCodOrden(String codOrden,
			String idDeposito) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils
						.generateEQTokenField(ALIASED_CODORDEN, codOrden))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ALIASED_IDDEPOSITO,
						idDeposito));

		return getAsignable(qual.toString());
	}

	public List getElementosBetweenByCodOrden(ElementoVO elementoOrigenVO,
			ElementoVO elementoDestinoVO, String idFormato) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						elementoOrigenVO.getIddeposito()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDFMTHUECO,
						idFormato))
				.append(" AND ")
				.append(DBUtils.generateGTEQTokenField(CAMPO_CODORDEN,
						elementoOrigenVO.getCodorden()))
				.append(" AND ")
				.append(DBUtils.generateLTTokenField(CAMPO_CODORDEN,
						elementoDestinoVO.getCodorden()));

		StringBuffer orderBy = new StringBuffer(" ORDER BY ")
				.append(CODORDEN_COLUMN_NAME);

		return getAsignables(qual.toString(), orderBy.toString());
	}

	public ElementoAsignableVO getElementoByMinimoCodOrden(String idDeposito,
			String codOrden, String idFormato) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(CAMPO_CODORDEN.getName()
						+ "=("
						+ createQueryMinimoCodOrden(idDeposito, codOrden,
								idFormato) + ")")
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idDeposito));
		return getAsignable(qual.toString());
	}

	private String createQueryMinimoCodOrden(String idDeposito,
			String codOrden, String idFormato) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils
						.generateGTEQTokenField(CAMPO_CODORDEN, codOrden))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idDeposito))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDFMTHUECO,
						idFormato));

		String col = "min(" + CAMPO_CODORDEN.getName() + ")";
		return DbSelectStatement.getSelectStmtText(TABLE_NAME, col,
				qual.toString(), false);
	}

}