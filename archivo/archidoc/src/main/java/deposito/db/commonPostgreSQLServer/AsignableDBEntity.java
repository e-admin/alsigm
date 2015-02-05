package deposito.db.commonPostgreSQLServer;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import util.CollectionUtils;

import common.db.DBEntityFactory;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.IDBEntityFactory;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.IDepositoDbEntity;
import deposito.db.sqlserver2000.NoAsignableDBEntity;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;

public class AsignableDBEntity extends deposito.db.common.AsignableDBEntityBase {

	/**
	 * @param dataSource
	 */
	public AsignableDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	// private static final DbColumnDef[] IDPADRE_ID_NOMBRE_COL_SET = {
	// CAMPO_IDELEMNPADRE, CAMPO_ID, CAMPO_NOMBRE };

	public AsignableDBEntity(DbConnection conn) {
		super(conn);
	}

	protected static final DbColumnDef[] COLUMNS_TO_QUERY = { ALIASED_ID,
			ALIASED_NOMBRE, ALIASED_IDTIPOELEMENTO, ALIASED_NUMORDEN,
			ALIASED_IDELEMNPADRE, ALIASED_IDDEPOSITO, ALIASED_LONGITUD,
			ALIASED_IDFMTHUECO, ALIASED_NUMHUECOS, ALIASED_CODORDEN };

	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.db.common.AsignableDBEntity#getAsignables(java.lang.String,
	 * java.lang.String)
	 */
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

		List listaElementos = getVOS(whereClause.toString(), TABLE_LIST,
				COLUMNS_TO_QUERY, ElementoAsignableVO.class);
		for (Iterator i = listaElementos.iterator(); i.hasNext();)
			fillPathInfo((ElementoAsignableVO) i.next());
		return listaElementos;
	}

	private void fillPathInfo(ElementoAsignableVO asignable) {
		// obtener padres
		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(getDbFactoryClass());
		NoAsignableDBEntity noasignableDBEntity = new NoAsignableDBEntity(
				getDataSource());
		IDepositoDbEntity depositoEntity = dbEntityFactory
				.getDepositoDbEntity(getDataSource());

		List padresRecursivos = new LinkedList();
		padresRecursivos.add(new TuplaIDIDPadreNombre(asignable.getId(),
				asignable.getIdpadre(), asignable.getNombre()));
		padresRecursivos.addAll(noasignableDBEntity
				.getPadresRecusivos(asignable.getIdpadre()));

		// obtener deposito
		DepositoVO depositoDeAsignable = depositoEntity.loadDeposito(asignable
				.getIddeposito());
		if (depositoDeAsignable != null)
			padresRecursivos.add(new TuplaIDIDPadreNombre(depositoDeAsignable
					.getId(), depositoDeAsignable.getIdpadre(),
					depositoDeAsignable.getNombre()));

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
		asignable.setItemPath(itemPath.toString());
		asignable.setPathName(pathName.toString());
	}

	// private DepositoVO getDeposito(String idDeposito){
	// final StringBuffer qual = new StringBuffer();
	// // qual.append(" SELECT ").append(ID_COLUMN_NAME).append(",")
	// // .append(NOMBRE_COLUMN_NAME).append(" ")
	// // .append(" FROM ").append(DepositoDBEntityImpl.TABLE_NAME)
	// qual.append(" WHERE ")
	// .append(DBUtils.generateEQTokenField(DepositoDBEntityImpl.CAMPO_ID,
	// idDeposito));
	// DepositoVO depositoVO = (DepositoVO) getVO(qual.toString(),
	// DepositoDBEntityImpl.TABLE_NAME,
	// new DbColumnDef[] { DepositoDBEntityImpl.CAMPO_ID,
	// DepositoDBEntityImpl.CAMPO_NOMBRE},
	// DepositoVO.class);
	// return depositoVO;
	// }

	// private List getPadresRecusivos(String idPadre){
	// final StringBuffer qual = new StringBuffer();
	// qual.append(" WHERE ").append(DBUtils.generateEQTokenField(ALIASED_ID,
	// idPadre));
	// List listaPadres = new LinkedList();
	// List padreDirecto =
	// getVOS(qual.toString(),TABLE_DEFINITION.getDeclaration(),
	// IDPADRE_ID_NOMBRE_COL_SET, TuplaIDIDPadreNombre.class );
	// for (Iterator itPadreDirecto = padreDirecto.iterator();
	// itPadreDirecto.hasNext();) {
	// TuplaIDIDPadreNombre padre = (TuplaIDIDPadreNombre)
	// itPadreDirecto.next();
	// listaPadres.add(padre);
	// List abuelos = getPadresRecusivos(padre.getIdPadre());
	// listaPadres.addAll(abuelos);
	// }
	// return listaPadres;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see deposito.db.common.AsignableDBEntity#getAsignable(java.lang.String)
	 */
	public ElementoAsignableVO getAsignable(String qual) {
		List asignables = getAsignables(qual, null);
		if (!CollectionUtils.isEmpty(asignables)) {
			return (ElementoAsignableVO) asignables.get(0);
		}
		return null;
	}

}
