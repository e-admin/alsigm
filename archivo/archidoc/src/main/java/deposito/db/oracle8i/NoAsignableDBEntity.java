package deposito.db.oracle8i;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;

import common.db.DBCommand;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbOutputRecordset;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.ElementoNoAsignableDBEntity;
import deposito.vos.ElementoNoAsignableVO;

/**
 *
 */
public class NoAsignableDBEntity extends ElementoNoAsignableDBEntity {

	public NoAsignableDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public NoAsignableDBEntity(DbConnection conn) {
		super(conn);
	}

	private List getNoAsignables(String qual) {
		List listaElementos = getVOS(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, ElementoNoAsignableVO.class);
		for (Iterator i = listaElementos.iterator(); i.hasNext();)
			fillPathInfo((ElementoNoAsignableVO) i.next());
		return listaElementos;
	}

	private ElementoNoAsignableVO getNoAsignable(String qual) {
		ElementoNoAsignableVO noAsignable = (ElementoNoAsignableVO) getVO(
				qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				ElementoNoAsignableVO.class);
		if (noAsignable != null)
			fillPathInfo(noAsignable);
		return noAsignable;
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

	private void fillPathInfo(ElementoNoAsignableVO asignable) {
		final StringBuffer qual = getSQLBuff();
		qual.append(" SELECT ")
				.append(ID_NOMBRE_COLUMN_LIST)
				.append(" FROM ")
				.append(TABLE_NAME)
				.append(" CONNECT BY PRIOR ")
				.append(IDPADRE_COLUMN_NAME)
				.append("=")
				.append(ID_COLUMN_NAME)
				.append(" START WITH ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						asignable.getId()))
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

	// public List getAncestors(String idElemento) {
	// StringBuffer qual = new StringBuffer("CONNECT BY PRIOR ")
	// .append(IDPADRE_COLUMN_NAME).append("=").append(ID_COLUMN_NAME)
	// .append(" START WITH ").append(DBUtils.generateEQTokenField(CAMPO_ID,
	// idElemento));
	// return getNoAsignables(qual.toString());
	// }

	public List getByIdPadre(String idPadre, String idUbicacion) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idUbicacion)).append(" ORDER BY ")
				.append(IDTIPOELEMENTO_COLUMN_NAME).append(",")
				.append(NUMORDEN_COLUMN_NAME).append(" ASC ");

		return getNoAsignables(qual.toString());
	}

	public ElementoNoAsignableVO getByIdPadre(String idPadre,
			String idUbicacion, int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idUbicacion)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_NUMORDEN, numOrden))
				.append(" ORDER BY ").append(IDTIPOELEMENTO_COLUMN_NAME)
				.append(",").append(NUMORDEN_COLUMN_NAME).append(" ASC ");

		return getNoAsignable(qual.toString());
	}

	public List getByIdPadreMayorQueOrden(String idPadre, String idUbicacion,
			int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idUbicacion)).append(" AND ")
				.append(DBUtils.generateGTTokenField(CAMPO_NUMORDEN, numOrden))
				.append(" ORDER BY ").append(IDTIPOELEMENTO_COLUMN_NAME)
				.append(",").append(NUMORDEN_COLUMN_NAME).append(" ASC ");

		return getNoAsignables(qual.toString());
	}

	public List getByIdPadreMayorIgualQueOrden(String idPadre,
			String idUbicacion, int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idUbicacion))
				.append(" AND ")
				.append(DBUtils
						.generateGTEQTokenField(CAMPO_NUMORDEN, numOrden))
				.append(" ORDER BY ").append(IDTIPOELEMENTO_COLUMN_NAME)
				.append(",").append(NUMORDEN_COLUMN_NAME).append(" ASC ");

		return getNoAsignables(qual.toString());
	}

	/**
	 * Recupera los elementos del depósito físico que son descendientes del
	 * suministrado
	 * 
	 * @param idPadre
	 *            Identificador de elemento no asignable
	 * @param idUbicacion
	 *            Identificador de ubicación
	 * @param idTipoElemento
	 *            Identificador del tipo de elemento
	 * @return Lista de elementos del depósito físico que pertenecen a la
	 *         ubicación que se indica y tienen como padre el elemento cuyo
	 *         identificador se suministra
	 */
	public List getByIdPadreTipo(String idPadre, String idUbicacion,
			String idTipoElemento) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDPADRE, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idUbicacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDTIPOELEMENTO,
						idTipoElemento)).append(" ORDER BY ")
				.append(IDTIPOELEMENTO_COLUMN_NAME).append(",")
				.append(NUMORDEN_COLUMN_NAME).append(" ASC ");

		return getNoAsignables(qual.toString());

	}

	public ElementoNoAsignableVO loadElementoNoAsignable(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));

		return getNoAsignable(qual.toString());
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

	public ElementoNoAsignableVO getElementoByCodOrden(String codOrden,
			String idDeposito) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_CODORDEN, codOrden))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idDeposito));

		return getNoAsignable(qual.toString());
	}

}