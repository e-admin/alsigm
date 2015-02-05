package deposito.db.ibmdb2;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;

import util.CollectionUtils;

import common.db.AliasedColumnDef;
import common.db.DBEntityFactory;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.IDBEntityFactory;
import common.db.SigiaDbOutputRecordset;
import common.db.TableDef;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.ElementoNoAsignableDBEntity;
import deposito.db.IDepositoDbEntity;
import deposito.db.common.NoAsignableDBEntityBase;
import deposito.db.commonPostgreSQLServer.TuplaIDIDPadreNombre;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoNoAsignableVO;

public class NoAsignableDbEntity extends NoAsignableDBEntityBase {

	private StringBuffer sqlBuff = new StringBuffer();
	private static final DbColumnDef[] IDPADRE_ID_NOMBRE_COL_SET = {
			CAMPO_IDPADRE, CAMPO_ID, CAMPO_NOMBRE };

	public NoAsignableDbEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public NoAsignableDbEntity(DbConnection conn) {
		super(conn);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * deposito.db.common.NoAsignableDBEntityBase#getNoAsignable(java.lang.String
	 * )
	 */
	public ElementoNoAsignableVO getNoAsignable(String qual) {
		List ret = getNoAsignables(qual, null);
		if (!CollectionUtils.isEmpty(ret)) {
			return (ElementoNoAsignableVO) ret.get(0);
		}
		return null;
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

	private StringBuffer getSQLBuff() {
		sqlBuff.setLength(0);
		return sqlBuff;
	}

	// MIGRACION BD - BY PRIOR ( PROBAR CON EL BORRADO DE NO ASIGNABLE
	public List getIdsDescendientes(String idElemento) {

		/*
		 * WITH n(id) AS( SELECT id FROM asgdelemnoasig WHERE idpadre =
		 * '097ee92f667740000000000000000005' UNION ALL SELECT nplus1.id FROM
		 * asgdelemnoasig as nplus1, n WHERE n.id = nplus1.idpadre ) SELECT id
		 * FROM n;
		 */

		TableDef tableDef1 = new TableDef("n");
		TableDef tableDef2 = new TableDef(
				ElementoNoAsignableDBEntity.TABLE_NAME, "nplus1");

		final DbColumnDef TABLA1_CAMPO_ID = new DbColumnDef("value",
				tableDef1.getName(), CAMPO_ID);

		final DbColumnDef TABLA2_CAMPO_ID = new DbColumnDef("value",
				tableDef2.getName(), CAMPO_ID);
		final DbColumnDef TABLA2_CAMPO_IDPADRE = new DbColumnDef(tableDef2,
				CAMPO_IDPADRE);

		final DbColumnDef[] cols = new DbColumnDef[] { TABLA1_CAMPO_ID };

		final StringBuffer sqlCompleta =

		new StringBuffer
		// WITH n(value) AS (
		("WITH ")
				.append(tableDef1.getTableName())
				.append("(")
				.append(TABLA1_CAMPO_ID.getBindPropertyVO())
				.append(") AS (")
				// SELECT id as value
				.append(DBUtils.SELECT)
				.append(ElementoNoAsignableDBEntity.CAMPO_ID.getQualifiedName())
				.append(" AS ")
				.append(TABLA1_CAMPO_ID.getBindPropertyVO())
				// FROM asgdelemnoasig
				.append(DBUtils.FROM)
				.append(ElementoNoAsignableDBEntity.TABLE_NAME)
				// WHERE idpadre = '<idElemento>'
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						ElementoNoAsignableDBEntity.CAMPO_IDPADRE, idElemento))
				// UNION ALL
				.append(DBUtils.UNION_ALL)
				// SELECT nplus1.id FROM
				.append(DBUtils.SELECT)
				.append(TABLA2_CAMPO_ID.getQualifiedName())
				.append(" AS ")
				.append(TABLA2_CAMPO_ID.getBindPropertyVO())
				.append(DBUtils.FROM)
				// asgdelemnoasig as nplus1, n
				.append(tableDef2.getDeclaration())
				.append(", ")
				.append(tableDef1.getDeclaration())
				// WHERE n.id = nplus1.idpadre
				.append(DBUtils.WHERE)
				.append(TABLA1_CAMPO_ID.getBindPropertyVO()).append("=")
				.append(TABLA2_CAMPO_IDPADRE.getQualifiedName()).append(") ")
				.append(DBUtils.SELECT)
				.append(TABLA1_CAMPO_ID.getBindPropertyVO())
				.append(DBUtils.FROM).append(tableDef1.getTableName());

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(cols,
				MutableObject.class);

		List ids = getVOS(sqlCompleta.toString(), MutableObject.class, rowSet);

		List idList = new ArrayList();
		for (int i = 0; i < ids.size(); i++)
			idList.add((String) ((Mutable) ids.get(i)).getValue());
		return idList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.ElementoNoAsignableDBEntity#getElementosAsignablesByAmbitosPadre(java.lang.String[])
	 */
	public List getElementosAsignablesByAmbitosPadre(String[] idsAmbitoDeposito) {

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

		// ConsultaConnectBy consultaConnectBy = DbUtil
		// .generateNativeSQLWithConnectBy(getConnection(), new TableDef(
		// ElementoNoAsignableDBEntity.TABLE_NAME),
		// ElementoNoAsignableDBEntity.CAMPO_ID,
		// ElementoNoAsignableDBEntity.CAMPO_IDPADRE,
		// idsAmbitoDeposito, null);

		// StringBuffer sqlSubqueryCompleta = new StringBuffer(DBUtils.SELECT)
		// .append(ElementoAsignableDBEntity.CAMPO_ID.toString()).append(
		// DBUtils.FROM).append(
		// ElementoAsignableDBEntity.TABLE_NAME).append(
		// DBUtils.WHERE).append(
		// DBUtils.generateInTokenFieldSubQuery(
		// ElementoAsignableDBEntity.CAMPO_IDELEMNPADRE,
		// consultaConnectBy.getSqlCompleta()));

		//
		// StringBuffer qual = new StringBuffer(DBUtils.WHERE)
		// .append(DBUtils.generateInTokenFieldSubQuery(AsignableDBEntity.CAMPO_IDELEMNPADRE,
		// consultaConnectBy.getSqlClause()))
		// .append(DBUtils.OR)
		// .append(DBUtils.generateInTokenField(AsignableDBEntity.CAMPO_ID,
		// idsAmbitoDeposito))
		// ;

		return null;
	}

}
