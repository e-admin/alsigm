package fondos.db;

import fondos.vos.VolumenSerieVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.exception.IeciTdException;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

/**
 * Acceso a los datos de los volúmenes de una serie.
 */
public class VolumenSerieDBEntityImpl extends DBEntity implements
		IVolumenSerieDBEntity {

	// Nombre de la tabla.
	public static final String TABLE_NAME = "ASGFVOLUMENSERIE";

	// Columnas
	public static final String IDSERIE_COLUMN_NAME = "IDSERIE";
	public static final String TIPODOCUMENTAL_COLUMN_NAME = "TIPODOCUMENTAL";
	public static final String CANTIDAD_COLUMN_NAME = "CANTIDAD";

	// Campos
	public static final DbColumnDef CAMPO_IDSERIE = new DbColumnDef(null,
			TABLE_NAME, IDSERIE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPODOCUMENTAL = new DbColumnDef(
			null, TABLE_NAME, TIPODOCUMENTAL_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_CANTIDAD = new DbColumnDef(null,
			TABLE_NAME, CANTIDAD_COLUMN_NAME, DbDataType.LONG_INTEGER, true);

	// Lista de columnas
	private static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_IDSERIE, CAMPO_TIPODOCUMENTAL, CAMPO_CANTIDAD };

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Origen de datos.
	 */
	public VolumenSerieDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public VolumenSerieDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene el volumen de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de una serie.
	 * @param tipoDocumental
	 *            Tipo documental.
	 * @return Volumen de una serie.
	 */
	public VolumenSerieVO getVolumenSerie(String idSerie, String tipoDocumental) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPODOCUMENTAL,
						tipoDocumental)).toString();

		return (VolumenSerieVO) getVO(qual, TABLE_NAME, COL_DEFS,
				VolumenSerieVO.class);
	}

	/**
	 * Obtiene la lista de volúmenes de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de una serie.
	 * @return Lista de volúmenes de una serie.
	 */
	public List getVolumenesSerie(String idSerie) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" ORDER BY ")
				.append(CAMPO_TIPODOCUMENTAL.getQualifiedName()).toString();

		return getVOS(qual, TABLE_NAME, COL_DEFS, VolumenSerieVO.class);
	}

	/**
	 * Incrementa el número de unidades documentales de un determinado tipo
	 * documental que contiene una serie del cuadro de clasificación de fondos
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @param tipoDocumental
	 *            Tipo documental
	 * @param incrementNumUdocs
	 *            Número en el que se incrementa el volumen de la serie
	 */
	public void incrementVolumenSerie(String idSerie, String tipoDocumental,
			final int incrementNumUdocs) {
		final String qual = new StringBuffer("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPODOCUMENTAL,
						tipoDocumental)).toString();

		final VolumenSerieVO volumenSerie = new VolumenSerieVO(idSerie,
				tipoDocumental, incrementNumUdocs);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				try {
					DbSelectFns.selectLongInteger(conn, TABLE_NAME,
							CANTIDAD_COLUMN_NAME, qual);
					DbUpdateFns.incrementField(conn, TABLE_NAME,
							CANTIDAD_COLUMN_NAME, incrementNumUdocs, qual);
				} catch (IeciTdException ieciE) {
					insertVO(TABLE_NAME, COL_DEFS, volumenSerie);
				}
			}
		};
		command.execute();

	}

	public void deleteVolumenesByIdSerie(String idSerie) {
		final String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDSERIE, idSerie))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.db.IVolumenSerieDBEntity#insertOrUpdateVolumnSerie(fondos.vos.VolumenSerieVO)
	 */
	public void insertVolumenSerie(final VolumenSerieVO volumenSerieVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				insertVO(TABLE_NAME, COL_DEFS, volumenSerieVO);
			}
		};
		command.execute();
	}

}
