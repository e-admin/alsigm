package deposito.db.oracle8i;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUtil;

import java.util.Iterator;
import java.util.List;

import common.db.DBCommand;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbOutputRecordset;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.ElementoAsignableDBEntity;
import deposito.db.ElementoNoAsignableDBEntity;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;

/**
 *
 */
public class AsignableDBEntity extends ElementoAsignableDBEntity {

	public AsignableDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public AsignableDBEntity(DbConnection conn) {
		super(conn);
	}

	private static final DbColumnDef[] ID_NOMBRE_COL_SET = { CAMPO_ID,
			CAMPO_NOMBRE };
	private static final String ID_NOMBRE_COLUMN_LIST = DbUtil
			.getColumnNames(ID_NOMBRE_COL_SET);
	private StringBuffer sqlBuff = new StringBuffer();

	private StringBuffer getSQLBuff() {
		sqlBuff.setLength(0);
		return sqlBuff;
	}

	private List getAsignables(String qual) {
		List listaElementos = getVOS(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, ElementoAsignableVO.class);
		for (Iterator i = listaElementos.iterator(); i.hasNext();)
			fillPathInfo((ElementoAsignableVO) i.next());
		return listaElementos;
	}

	private ElementoAsignableVO getAsignable(String qual) {
		ElementoAsignableVO asignable = (ElementoAsignableVO) getVO(
				qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				ElementoAsignableVO.class);
		if (asignable != null)
			fillPathInfo(asignable);
		return asignable;
	}

	private void fillPathInfo(ElementoAsignableVO asignable) {
		final StringBuffer qual = getSQLBuff();
		qual.append(" SELECT ")
				.append(ID_NOMBRE_COLUMN_LIST)
				.append(" FROM ")
				.append(TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						asignable.getId()))
				.append(" UNION ALL ")
				.append(" SELECT ")
				.append(ID_NOMBRE_COLUMN_LIST)
				.append(" FROM ")
				.append(ElementoNoAsignableDBEntity.TABLE_NAME)
				.append(" CONNECT BY PRIOR ")
				.append(ElementoNoAsignableDBEntity.IDPADRE_COLUMN_NAME)
				.append("=")
				.append(ElementoNoAsignableDBEntity.ID_COLUMN_NAME)
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(
						ElementoNoAsignableDBEntity.CAMPO_ID,
						asignable.getIdpadre()))
				.append(" UNION ALL ")
				.append(" SELECT ")
				.append(ID_NOMBRE_COLUMN_LIST)
				.append(" FROM ")
				.append(DepositoDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						DepositoDBEntityImpl.CAMPO_ID,
						asignable.getIddeposito()));

		final SigiaDbOutputRecordset outputRecordSet = new SigiaDbOutputRecordset(
				ID_NOMBRE_COL_SET, ParIDNombre.class);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbOutputRecord outputRecord = null;
				DbSelectStatement stmt = new DbSelectStatement();
				stmt.create(conn, qual.toString());
				stmt.execute();

				while (stmt.next()) {
					outputRecord = outputRecordSet.newRecord();
					outputRecord.getStatementValues(stmt);
				}

				stmt.release();
			}
		};
		command.execute();
		StringBuffer itemPath = new StringBuffer();
		StringBuffer pathName = new StringBuffer();
		ParIDNombre infoAntecesor = null;
		for (Iterator i = outputRecordSet.iterator(); i.hasNext();) {
			infoAntecesor = (ParIDNombre) i.next();
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

	public ElementoAsignableVO loadAsignableElement(String idElementoPadre,
			int numOrden) {

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMNPADRE,
						idElementoPadre)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN, numOrden));

		return getAsignable(qual.toString());
	}

	public List loadAsignablesElements(String idElementoPadre) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMNPADRE,
						idElementoPadre)).append(" ORDER BY ")
				.append(CAMPO_NUMORDEN.getName()).append(" ASC ");

		return getAsignables(qual.toString());
	}

	public List loadAsignablesElementsMayorQueOrden(String idElementoPadre,
			int orden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMNPADRE,
						idElementoPadre)).append(" AND ")
				.append(DBUtils.generateGTTokenField(CAMPO_NUMORDEN, orden))
				.append(" ORDER BY ").append(CAMPO_NUMORDEN.getName())
				.append(" ASC ");

		return getAsignables(qual.toString());
	}

	public List loadAsignablesElementsMayorIgualQueOrden(
			String idElementoPadre, int orden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDELEMNPADRE,
						idElementoPadre)).append(" AND ")
				.append(DBUtils.generateGTEQTokenField(CAMPO_NUMORDEN, orden))
				.append(" ORDER BY ").append(CAMPO_NUMORDEN.getName())
				.append(" ASC ");

		return getAsignables(qual.toString());
	}

	public ElementoAsignableVO loadElementoAsignable(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		return getAsignable(qual.toString());
	}

	public ElementoAsignableVO getElementoByCodOrden(String codOrden,
			String idDeposito) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_CODORDEN, codOrden))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
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
						elementoDestinoVO.getCodorden())).append(" ORDER BY ")
				.append(CODORDEN_COLUMN_NAME);

		return getAsignables(qual.toString());
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