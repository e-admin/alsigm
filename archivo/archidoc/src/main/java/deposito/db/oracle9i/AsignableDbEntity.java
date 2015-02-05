/*
 * Created on 22-ene-2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package deposito.db.oracle9i;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.List;

import common.db.DBUtils;
import common.db.DbDataSource;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.common.AsignableDBEntityBase;
import deposito.vos.ElementoAsignableVO;

public class AsignableDbEntity extends AsignableDBEntityBase {
	static final StringBuffer ITEM_PATH_QUERY = new StringBuffer(" 'item' || ")
			.append(DepositoDBEntityImpl.CAMPO_ID.getQualifiedName())
			.append(" || (")
			.append(NoAsignableDBEntity
					.createItemPathSubquery(CAMPO_IDELEMNPADRE
							.getQualifiedName())).append(") || '/item' || ")
			.append(ALIASED_ID.getQualifiedName()).append(" AS itemPath ");

	static final StringBuffer PATH_QUERY = new StringBuffer(
			DepositoDBEntityImpl.CAMPO_NOMBRE.getQualifiedName())
			.append(" || (")
			.append(NoAsignableDBEntity.createPathSubquery(CAMPO_IDELEMNPADRE
					.getQualifiedName())).append(") || '/' || ")
			.append(ALIASED_NOMBRE.getQualifiedName()).append(" AS path ");

	public static final DbColumnDef CAMPO_ITEM_PATH = new DbColumnDef(
			"itemPath", (String) null, ITEM_PATH_QUERY.toString(),
			DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef CAMPO_PATH = new DbColumnDef("pathName",
			(String) null, PATH_QUERY.toString(), DbDataType.SHORT_TEXT, false);

	protected static final DbColumnDef[] COLUMNS_PATHS_INCLUDED = { ALIASED_ID,
			ALIASED_NOMBRE, ALIASED_IDTIPOELEMENTO, ALIASED_NUMORDEN,
			ALIASED_IDELEMNPADRE, ALIASED_IDDEPOSITO, ALIASED_LONGITUD,
			ALIASED_IDFMTHUECO, ALIASED_NUMHUECOS, CAMPO_ITEM_PATH, CAMPO_PATH,
			CAMPO_CODORDEN };

	/**
	 * @param dataSource
	 */
	public AsignableDbEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public AsignableDbEntity(DbConnection conn) {
		super(conn);
	}

	public List getAsignables(String qual, String orderBy) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(" AND ");
		else
			whereClause.append(" WHERE ");
		whereClause.append(DBUtils.generateJoinCondition(
				DepositoDBEntityImpl.CAMPO_ID, ALIASED_IDDEPOSITO));
		if (orderBy != null)
			whereClause.append(orderBy);
		return getVOS(whereClause.toString(), TABLE_LIST,
				COLUMNS_PATHS_INCLUDED, ElementoAsignableVO.class);
	}

	public ElementoAsignableVO getAsignable(String qual) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(" AND ");
		else
			whereClause.append(" WHERE ");
		whereClause.append(DBUtils.generateJoinCondition(
				DepositoDBEntityImpl.CAMPO_ID, ALIASED_IDDEPOSITO));
		return (ElementoAsignableVO) getVO(whereClause.toString(), TABLE_LIST,
				COLUMNS_PATHS_INCLUDED, ElementoAsignableVO.class);
	}

}
