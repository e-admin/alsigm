package deposito.db.oracle9i;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;

import common.db.AliasedColumnDef;
import common.db.DBUtils;
import common.db.DbDataSource;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.common.NoAsignableDBEntityBase;
import deposito.vos.ElementoNoAsignableVO;

public class NoAsignableDBEntity extends NoAsignableDBEntityBase {
	public static String createItemPathSubquery(String selecctionQual) {
		StringBuffer subquery = new StringBuffer()
				.append("SELECT SYS_CONNECT_BY_PATH(").append(ID_COLUMN_NAME)
				.append(",'/item') ").append(" FROM ").append(TABLE_NAME)
				.append(" WHERE ").append(ID_COLUMN_NAME).append("=")
				.append(selecctionQual).append(" CONNECT BY PRIOR ")
				.append(ID_COLUMN_NAME).append("=").append(IDPADRE_COLUMN_NAME)
				.append(" START WITH ").append(IDPADRE_COLUMN_NAME)
				.append(" IS NULL ");
		return subquery.toString();
	}

	public static String createPathSubquery(String selecctionQual) {
		StringBuffer subquery = new StringBuffer()
				.append("SELECT SYS_CONNECT_BY_PATH(")
				.append(NOMBRE_COLUMN_NAME).append(",'/') ").append(" FROM ")
				.append(TABLE_NAME).append(" WHERE ").append(ID_COLUMN_NAME)
				.append("=").append(selecctionQual)
				.append(" CONNECT BY PRIOR ").append(ID_COLUMN_NAME)
				.append("=").append(IDPADRE_COLUMN_NAME).append(" START WITH ")
				.append(IDPADRE_COLUMN_NAME).append(" IS NULL ");
		return subquery.toString();
	}

	static final StringBuffer ITEM_PATH_QUERY = new StringBuffer(" 'item' || ")
			.append(DepositoDBEntityImpl.CAMPO_ID.getQualifiedName())
			.append(" || (")
			.append(createItemPathSubquery(ID_ALIASED_COLUMN.getQualifiedName()))
			.append(") AS itemPath ");

	static final StringBuffer PATH_QUERY = new StringBuffer(
			DepositoDBEntityImpl.CAMPO_NOMBRE.getQualifiedName())
			.append(" || (")
			.append(createPathSubquery(ID_ALIASED_COLUMN.getQualifiedName()))
			.append(" ) AS path ");

	public static final DbColumnDef CAMPO_ITEM_PATH = new DbColumnDef(
			"itemPath", (String) null, ITEM_PATH_QUERY.toString(),
			DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef CAMPO_PATH = new DbColumnDef("pathName",
			(String) null, PATH_QUERY.toString(), DbDataType.SHORT_TEXT, false);

	protected static final DbColumnDef[] COLUMNS_PATHS_INCLUDED = {
			ID_ALIASED_COLUMN,
			IDTIPOELEMENTO_ALIASED_COLUMN,
			NUMORDEN_ALIASED_COLUMN,
			IDDEPOSITO_ALIASED_COLUMN,
			new AliasedColumnDef(null, NOMBRE_COLUMN_NAME,
					DbDataType.SHORT_TEXT, false, TABLE_DEFINITION),
			IDPADRE_ALIASED_COLUMN,
			new AliasedColumnDef(null, MARCAS_COLUMN_NAME,
					DbDataType.LONG_INTEGER, false, TABLE_DEFINITION),
			CAMPO_ITEM_PATH, CAMPO_PATH, CODORDEN_ALIASED_COLUMN };

	public NoAsignableDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public NoAsignableDBEntity(DbConnection conn) {
		super(conn);
	}

	public List getNoAsignables(String qual, String orderBy) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(" AND ");
		else
			whereClause.append(" WHERE ");
		whereClause.append(DBUtils.generateJoinCondition(
				DepositoDBEntityImpl.CAMPO_ID, IDDEPOSITO_ALIASED_COLUMN));

		if (orderBy != null)
			whereClause.append(orderBy);

		return getVOS(whereClause.toString(), TABLE_LIST,
				COLUMNS_PATHS_INCLUDED, ElementoNoAsignableVO.class);
	}

	public ElementoNoAsignableVO getNoAsignable(String qual) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(" AND ");
		else
			whereClause.append(" WHERE ");
		whereClause.append(DBUtils.generateJoinCondition(
				DepositoDBEntityImpl.CAMPO_ID, IDDEPOSITO_ALIASED_COLUMN));
		return (ElementoNoAsignableVO) getVO(whereClause.toString(),
				TABLE_LIST, COLUMNS_PATHS_INCLUDED, ElementoNoAsignableVO.class);
	}

	public List getIdsDescendientes(String idElemento) {
		final StringBuffer qual = new StringBuffer("CONNECT BY PRIOR ")
				.append(ID_COLUMN_NAME)
				.append("=")
				.append(IDPADRE_COLUMN_NAME)
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE, idElemento));

		final DbColumnDef[] cols = new DbColumnDef[] { new DbColumnDef("value",
				CAMPO_ID) };

		List ids = getVOS(qual.toString(), TABLE_NAME, cols,
				MutableObject.class);
		List idList = new ArrayList();
		for (int i = 0; i < ids.size(); i++)
			idList.add((String) ((Mutable) ids.get(i)).getValue());
		return idList;
	}

}