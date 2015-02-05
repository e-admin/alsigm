package auditoria.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Iterator;
import java.util.List;

import auditoria.vos.DatosTrazaVO;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.definitions.ArchivoObjects;

/**
 * Clase que encapsula todas las definiciones de los datos de las trazas de
 * auditoría, así como de las operaciones que se pueden realizar sobre ellos.
 */
public abstract class DatosTrazaDBEntity extends DBEntity implements
		IDatosTrazaDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "AADATOSTRAZA";

	/** Nombre de los campos de la tabla */
	private static final String ID_COLUMN_NAME = "ID";
	private static final String IDPISTA_COLUMN_NAME = "IDTRAZA";
	private static final String CONTENIDO_COLUMN_NAME = "CONTENIDO";
	private static final String IDTIPOOBJETO_COLUMN_NAME = "TIPOOBJETO";
	private static final String IDOBJETO_COLUMN_NAME = "IDOBJETO";

	/** Definicion de las columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDPISTA = new DbColumnDef(null,
			TABLE_NAME, IDPISTA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_CONTENIDO = new DbColumnDef(null,
			TABLE_NAME, CONTENIDO_COLUMN_NAME, DbDataType.LONG_TEXT, 254, true);

	public static final DbColumnDef CAMPO_IDTIPOOBJETO = new DbColumnDef(null,
			TABLE_NAME, IDTIPOOBJETO_COLUMN_NAME, DbDataType.LONG_INTEGER, true);

	public static final DbColumnDef CAMPO_IDOBJETO = new DbColumnDef(null,
			TABLE_NAME, IDOBJETO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	/** Listado con las definiciones de las columnas */
	public static final DbColumnDef[] COLS_DEFS_DATOSTRAZA = { CAMPO_ID,
			CAMPO_IDPISTA, CAMPO_CONTENIDO, CAMPO_IDTIPOOBJETO, CAMPO_IDOBJETO, };

	public static final String COLUMN_NAMES_DATOSTRAZA = DbUtil
			.getColumnNames(COLS_DEFS_DATOSTRAZA);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor por defecto de la clase.
	 * 
	 * @param dataSource
	 *            Gestor de transacciones
	 */
	public DatosTrazaDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public DatosTrazaDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Inserta una nueva linea de datos traza de una traza de auditoria en la
	 * base de datos
	 * 
	 * @param datos
	 *            Linea con los datos de traza de una traza de auditoría a
	 *            insertar.
	 * @return {@link auditoria.vos.DatosTrazaVO} insertada
	 */
	public DatosTrazaVO insertDatosTraza(final DatosTrazaVO datosTraza) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				datosTraza.setId(getGuid(datosTraza.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_DATOSTRAZA, datosTraza);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES_DATOSTRAZA,
						inputRecord);
			}
		};

		command.execute();

		return datosTraza;
	}

	/**
	 * Obtiene los detalles de una pista de auditoria a partir del identificador
	 * de la pista
	 * 
	 * @param id
	 *            Identificador de la pista de la que deseamos recuperar los
	 *            detalles.
	 * @return Listado de los detalles de la pista de auditoria
	 */
	public List getDatosPista(String id) {
		List datos = null;
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_IDPISTA, id,
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		datos = getVOS(qual, TABLE_NAME, COLS_DEFS_DATOSTRAZA,
				DatosTrazaVO.class);

		if (datos != null) {
			Iterator it = datos.iterator();
			while (it.hasNext()) {
				DatosTrazaVO dato = (DatosTrazaVO) it.next();

				dato.setObjeto(ArchivoObjects.getObjectName(dato
						.getTipoObjeto()));
			}
		}

		return datos;
	}
}
