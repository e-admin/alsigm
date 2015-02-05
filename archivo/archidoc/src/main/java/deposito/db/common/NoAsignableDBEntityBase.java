package deposito.db.common;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.List;

import common.db.AliasedColumnDef;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.TableDef;
import common.util.ArrayUtils;

import deposito.db.DepositoDBEntityImpl;
import deposito.db.ElementoNoAsignableDBEntity;
import deposito.vos.ElementoNoAsignableVO;

public abstract class NoAsignableDBEntityBase extends
		ElementoNoAsignableDBEntity {

	public NoAsignableDBEntityBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public NoAsignableDBEntityBase(DbConnection conn) {
		super(conn);
	}

	protected static final TableDef TABLE_DEFINITION = new TableDef(TABLE_NAME,
			"noasignable");

	protected static final DbColumnDef ID_ALIASED_COLUMN = new AliasedColumnDef(
			null, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	protected static final DbColumnDef IDDEPOSITO_ALIASED_COLUMN = new AliasedColumnDef(
			null, IDDEPOSITO_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	protected static final DbColumnDef NUMORDEN_ALIASED_COLUMN = new AliasedColumnDef(
			null, NUMORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false,
			TABLE_DEFINITION);
	protected static final DbColumnDef IDPADRE_ALIASED_COLUMN = new AliasedColumnDef(
			null, IDPADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	protected static final DbColumnDef IDTIPOELEMENTO_ALIASED_COLUMN = new AliasedColumnDef(
			null, IDTIPOELEMENTO_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);
	protected static final DbColumnDef CODORDEN_ALIASED_COLUMN = new AliasedColumnDef(
			null, CODORDEN_COLUMN_NAME, DbDataType.SHORT_TEXT, false,
			TABLE_DEFINITION);

	protected static final String TABLE_LIST = ArrayUtils.join(
			new String[] { TABLE_DEFINITION.getDeclaration(),
					DepositoDBEntityImpl.TABLE_NAME }, ",");

	public ElementoNoAsignableVO loadElementoNoAsignable(String id) {

		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ID_ALIASED_COLUMN, id));

		return getNoAsignable(qual.toString());
	}

	/**
	 * Recupera los elementos del depósito físico que son descendientes del
	 * suministrado
	 * 
	 * @param idPadre
	 *            Identificador de elemento no asignable
	 * @param idUbicacion
	 *            Identificador de ubicación
	 * @return Lista de elementos del depósito físico que pertenecen a la
	 *         ubicación que se indica y tienen como padre el elemento cuyo
	 *         identificador se suministra
	 */
	public List getByIdPadre(String idPadre, String idUbicacion) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDPADRE_ALIASED_COLUMN,
						idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDDEPOSITO_ALIASED_COLUMN,
						idUbicacion));
		StringBuffer orderBy = new StringBuffer(" ORDER BY ")
				.append(IDTIPOELEMENTO_ALIASED_COLUMN.getQualifiedName())
				.append(",").append(NUMORDEN_ALIASED_COLUMN.getQualifiedName())
				.append(" ASC ");

		return getNoAsignables(qual.toString(), orderBy.toString());
	}

	public ElementoNoAsignableVO getByIdPadre(String idPadre,
			String idUbicacion, int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDPADRE_ALIASED_COLUMN,
						idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDDEPOSITO_ALIASED_COLUMN,
						idUbicacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(NUMORDEN_ALIASED_COLUMN,
						numOrden));

		return getNoAsignable(qual.toString());
	}

	public List getByIdPadreMayorQueOrden(String idPadre, String idUbicacion,
			int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDPADRE_ALIASED_COLUMN,
						idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDDEPOSITO_ALIASED_COLUMN,
						idUbicacion))
				.append(" AND ")
				.append(DBUtils.generateGTTokenField(NUMORDEN_ALIASED_COLUMN,
						numOrden));
		StringBuffer orderBy = new StringBuffer(" ORDER BY ")
				.append(IDTIPOELEMENTO_ALIASED_COLUMN.getQualifiedName())
				.append(",").append(NUMORDEN_ALIASED_COLUMN.getQualifiedName())
				.append(" ASC ");

		return getNoAsignables(qual.toString(), orderBy.toString());
	}

	public List getByIdPadreMayorIgualQueOrden(String idPadre,
			String idUbicacion, int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDPADRE_ALIASED_COLUMN,
						idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDDEPOSITO_ALIASED_COLUMN,
						idUbicacion))
				.append(" AND ")
				.append(DBUtils.generateGTEQTokenField(NUMORDEN_ALIASED_COLUMN,
						numOrden));
		StringBuffer orderBy = new StringBuffer(" ORDER BY ")
				.append(IDTIPOELEMENTO_ALIASED_COLUMN.getQualifiedName())
				.append(",").append(NUMORDEN_ALIASED_COLUMN.getQualifiedName())
				.append(" ASC ");

		return getNoAsignables(qual.toString(), orderBy.toString());
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
				.append(DBUtils.generateEQTokenField(IDPADRE_ALIASED_COLUMN,
						idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDDEPOSITO_ALIASED_COLUMN,
						idUbicacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						IDTIPOELEMENTO_ALIASED_COLUMN, idTipoElemento));

		StringBuffer orderBy = new StringBuffer(" ORDER BY ")
				.append(IDTIPOELEMENTO_ALIASED_COLUMN.getQualifiedName())
				.append(",").append(NUMORDEN_ALIASED_COLUMN.getQualifiedName())
				.append(" ASC ");

		return getNoAsignables(qual.toString(), orderBy.toString());
	}

	public ElementoNoAsignableVO getElementoByCodOrden(String codOrden,
			String idDeposito) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CODORDEN_ALIASED_COLUMN,
						codOrden))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDDEPOSITO_ALIASED_COLUMN,
						idDeposito));

		return getNoAsignable(qual.toString());
	}

	public abstract ElementoNoAsignableVO getNoAsignable(String qual);

	public abstract List getNoAsignables(String qual, String orderBy);

}
