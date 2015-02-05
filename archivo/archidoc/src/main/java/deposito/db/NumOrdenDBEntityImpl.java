package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.lang.MutableInt;

public class NumOrdenDBEntityImpl extends DBEntity implements INumOrdenDBEntity {

	// private static final Logger logger =
	// Logger.getLogger(NumOrdenDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGDSIGNUMORDEN";

	public static final String TIPO_PADRE_COLUMN_NAME = "idtipoelempadre";
	public static final String ID_PADRE_COLUMN_NAME = "idelempadre";
	public static final String TIPO_ELEMENTO_COLUMN_NAME = "idtipoelemento";
	public static final String NUM_ORDEN_COLUMN_NAME = "numorden";

	public static final DbColumnDef CAMPO_TIPO_PADRE = new DbColumnDef(
			"tipoPadre", TABLE_NAME, TIPO_PADRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_PADRE = new DbColumnDef("idPadre",
			TABLE_NAME, ID_PADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO_ELEMENTO = new DbColumnDef(
			"tipoElemento", TABLE_NAME, TIPO_ELEMENTO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NUM_ORDEN = new DbColumnDef(
			"numeroOrden", TABLE_NAME, NUM_ORDEN_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, true);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID_PADRE,
			CAMPO_TIPO_PADRE, CAMPO_TIPO_ELEMENTO, CAMPO_NUM_ORDEN };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public NumOrdenDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NumOrdenDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public class OrdenElementoDeposito {
		String idPadre = null;
		String tipoPadre = null;
		String tipoElemento = null;
		int numeroOrden;

		OrdenElementoDeposito(String idPadre, String tipoPadre,
				String tipoElemento, int numeroOrden) {
			this.idPadre = idPadre;
			this.tipoPadre = tipoPadre;
			this.tipoElemento = tipoElemento;
			this.numeroOrden = numeroOrden;
		}

		public String getIdPadre() {
			return idPadre;
		}

		public void setIdPadre(String idPadre) {
			this.idPadre = idPadre;
		}

		public int getNumeroOrden() {
			return numeroOrden;
		}

		public void setNumeroOrden(int numeroOrden) {
			this.numeroOrden = numeroOrden;
		}

		public String getTipoElemento() {
			return tipoElemento;
		}

		public void setTipoElemento(String tipoElemento) {
			this.tipoElemento = tipoElemento;
		}

		public String getTipoPadre() {
			return tipoPadre;
		}

		public void setTipoPadre(String tipoPadre) {
			this.tipoPadre = tipoPadre;
		}
	}

	public int incrementarNumeroOrden(final String idPadre,
			final String tipoPadre, final String tipoElemento,
			final int cantidad) {

		final String qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_PADRE, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_PADRE,
						tipoPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_ELEMENTO,
						tipoElemento)).toString();

		final MutableInt numeroOrden = new MutableInt(cantidad);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				try {
					//Comprobar si existe el registro en ASGDSIGNUMORDEN
					DbSelectFns.selectLongInteger(conn, TABLE_NAME,
							NUM_ORDEN_COLUMN_NAME, qual);

				} catch (IeciTdException ieciE) {
					logger.debug("El elemento no existe y se va a proceder a crearlo", ieciE);
					//Si no existe se crea.
					OrdenElementoDeposito ordenElemento = new OrdenElementoDeposito(
							idPadre, tipoPadre, tipoElemento,0);
					DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
							new SigiaDbInputRecord(COLUMN_DEFINITIONS,
									ordenElemento));
				}

				//Incrementar el valor
				DbUpdateFns.incrementField(conn, TABLE_NAME,
						NUM_ORDEN_COLUMN_NAME, cantidad, qual);
				numeroOrden.setValue(DbSelectFns.selectLongInteger(conn,
						TABLE_NAME, NUM_ORDEN_COLUMN_NAME, qual));
			}
		};
		command.execute();
		return numeroOrden.getValue();
	}

	public int getNumeroOrden(final String idPadre, final String tipoPadre,
			final String tipoElemento) {

		final String qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_PADRE, idPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_PADRE,
						tipoPadre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_ELEMENTO,
						tipoElemento)).toString();

		int numOrden = -1;
		try {
			numOrden = DbSelectFns.selectLongInteger(getConnection(),
					TABLE_NAME, NUM_ORDEN_COLUMN_NAME, qual);
		} catch (Exception ieciE) {
			// logger.debug(ieciE);
			numOrden = 0;
		}
		return numOrden;
	}
}
