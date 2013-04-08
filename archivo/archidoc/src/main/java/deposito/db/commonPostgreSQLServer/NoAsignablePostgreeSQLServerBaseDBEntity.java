package deposito.db.commonPostgreSQLServer;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import util.CollectionUtils;

import common.db.AliasedColumnDef;
import common.db.DBEntityFactory;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.IDBEntityFactory;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.IDepositoDbEntity;
import deposito.db.common.NoAsignableDBEntityBase;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoNoAsignableVO;

public abstract class NoAsignablePostgreeSQLServerBaseDBEntity extends
		NoAsignableDBEntityBase {

	// private static final DbColumnDef[] ID_NOMBRE_COL_SET = { CAMPO_ID,
	// CAMPO_NOMBRE };
	private static final DbColumnDef[] IDPADRE_ID_NOMBRE_COL_SET = {
			CAMPO_IDPADRE, CAMPO_ID, CAMPO_NOMBRE };
	// private static final String ID_NOMBRE_COLUMN_LIST =
	// DbUtil.getColumnNames(ID_NOMBRE_COL_SET);
	private StringBuffer sqlBuff = new StringBuffer();

	private StringBuffer getSQLBuff() {
		sqlBuff.setLength(0);
		return sqlBuff;
	}

	/**
	 * @param dataSource
	 */
	public NoAsignablePostgreeSQLServerBaseDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public NoAsignablePostgreeSQLServerBaseDBEntity(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.db.common.NoAsignableDBEntityBase#getNoAsignable(java.lang.String
	 * )
	 */
	// TODO ZMIGRACION BD - PROBADO
	public ElementoNoAsignableVO getNoAsignable(String qual) {
		List ret = getNoAsignables(qual, null);
		if (!CollectionUtils.isEmpty(ret)) {
			return (ElementoNoAsignableVO) ret.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.db.common.NoAsignableDBEntityBase#getNoAsignables(java.lang.
	 * String, java.lang.String)
	 */
	// TODO ZMIGRACION BD - PROBADO
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

		DbColumnDef[] COLUMNS = {
				ID_ALIASED_COLUMN,
				IDTIPOELEMENTO_ALIASED_COLUMN,
				NUMORDEN_ALIASED_COLUMN,
				IDDEPOSITO_ALIASED_COLUMN,
				new AliasedColumnDef(null, NOMBRE_COLUMN_NAME,
						DbDataType.SHORT_TEXT, false, TABLE_DEFINITION),
				IDPADRE_ALIASED_COLUMN,
				new AliasedColumnDef(null, MARCAS_COLUMN_NAME,
						DbDataType.LONG_INTEGER, false, TABLE_DEFINITION),
				CODORDEN_ALIASED_COLUMN };

		List listaElementos = getVOS(whereClause.toString(), TABLE_LIST,
				COLUMNS, ElementoNoAsignableVO.class);
		for (Iterator i = listaElementos.iterator(); i.hasNext();)
			fillPathInfo((ElementoNoAsignableVO) i.next());
		return listaElementos;
	}

	private void fillPathInfo(ElementoNoAsignableVO noAsignable) {
		// obtener padres
		List padresRecursivos = new LinkedList();
		padresRecursivos.add(new TuplaIDIDPadreNombre(noAsignable.getId(),
				noAsignable.getIdpadre(), noAsignable.getNombre()));
		padresRecursivos.addAll(getPadresRecusivos(noAsignable.getIdpadre()));

		// obtener deposito
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(getDbFactoryClass());
		IDepositoDbEntity depositoEntity = dbEntityFactory
				.getDepositoDbEntity(getDataSource());
		DepositoVO depositoDeNoAsignable = depositoEntity
				.loadDeposito(noAsignable.getIddeposito());
		if (depositoDeNoAsignable != null)
			padresRecursivos.add(new TuplaIDIDPadreNombre(depositoDeNoAsignable
					.getId(), depositoDeNoAsignable.getIdpadre(),
					depositoDeNoAsignable.getNombre()));

		// composicion del itemPath
		StringBuffer itemPath = new StringBuffer();
		StringBuffer pathName = new StringBuffer();
		TuplaIDIDPadreNombre infoAntecesor = null;
		for (Iterator i = padresRecursivos.iterator(); i.hasNext();) {
			infoAntecesor = (TuplaIDIDPadreNombre) i.next();
			itemPath.insert(0, infoAntecesor.getId()).insert(0, "item");
			pathName.insert(0, infoAntecesor.getNombre());
			if (i.hasNext()) {
				itemPath.insert(0, "/");
				pathName.insert(0, "/");
			}
		}
		noAsignable.setItemPath(itemPath.toString());
		noAsignable.setPathName(pathName.toString());
	}

	// TODO ZMIGRACION BD - PROBADO
	public List getPadresRecusivos(String idPadre) {
		final StringBuffer qual = getSQLBuff();
		qual.append(" WHERE ").append(
				DBUtils.generateEQTokenField(ID_ALIASED_COLUMN, idPadre));
		List listaPadres = new LinkedList();
		List padreDirecto = getVOS(qual.toString(),
				TABLE_DEFINITION.getDeclaration(), IDPADRE_ID_NOMBRE_COL_SET,
				TuplaIDIDPadreNombre.class);
		for (Iterator itPadreDirecto = padreDirecto.iterator(); itPadreDirecto
				.hasNext();) {
			TuplaIDIDPadreNombre padre = (TuplaIDIDPadreNombre) itPadreDirecto
					.next();
			listaPadres.add(padre);
			List abuelos = getPadresRecusivos(padre.getIdPadre());
			listaPadres.addAll(abuelos);
		}
		return listaPadres;
	}

	// private DepositoVO getDeposito(String idDeposito){
	// final StringBuffer qual = getSQLBuff();
	// //
	// qual.append(" SELECT ").append(ID_NOMBRE_COLUMN_LIST).append(" FROM ").append(DepositoDBEntityImpl.TABLE_NAME)
	// // .append(" WHERE ")
	// qual.append(" WHERE ")
	// .append(DBUtils.generateEQTokenField(DepositoDBEntityImpl.CAMPO_ID,
	// idDeposito));
	// DepositoVO depositoVO = (DepositoVO) getVO(qual.toString(),
	// DepositoDBEntityImpl.TABLE_NAME,
	// new DbColumnDef[] { DepositoDBEntityImpl.CAMPO_ID,
	// DepositoDBEntityImpl.CAMPO_NOMBRE },
	// DepositoVO.class);
	// return depositoVO;
	//
	//
	// }
	// MIGRACION BD - PROBADO CON ELIMINAR NO ASIGABLE
	// public List getIdsDescendientes(String idElemento)
	// {
	// final StringBuffer qual = getSQLBuff();
	// qual.append(" WHERE ").append(DBUtils.generateEQTokenField(CAMPO_IDPADRE,
	// idElemento));
	//
	// final DbColumnDef [] cols = new DbColumnDef [] {
	// new DbColumnDef("value", CAMPO_ID) };
	//
	// List ids = getVOS(qual.toString(), TABLE_NAME, cols,
	// MutableObject.class);
	// List idList = new ArrayList();
	// for (int i = 0; i < ids.size(); i++){
	// idList.add((String) ((Mutable) ids.get(i)).getValue());
	// List nietos = getIdsDescendientes((String) ((Mutable)
	// ids.get(i)).getValue());
	// if (!CollectionUtils.isEmpty(nietos))
	// idList.addAll(nietos);
	// }
	// return idList;
	// }

	public abstract List getIdsDescendientes(String idNoAsignablePadre);

}
