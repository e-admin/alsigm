package auditoria.db;

import gcontrol.db.GrupoUsuarioDBEntityImpl;
import gcontrol.db.UsuarioDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import auditoria.ArchivoErrorCodes;
import auditoria.vos.BusquedaPistasVO;
import auditoria.vos.TrazaVO;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;

/**
 * Clase que encapsula todas las definiciones de las trazas de auditoría, así
 * como de las operaciones que se pueden realizar sobre ellos.
 */
public class TrazaDBEntity extends DBEntity implements ITrazaDBEntity {

	/** Logger de la clase */
	// private static Logger logger = Logger.getLogger(TrazaDBEntity.class);

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "AATRAZA";

	/** Nombre de las columnas */
	private static final String ID_COLUMN_NAME = "ID";
	private static final String IDMODULO_COLUMN_NAME = "MODULO";
	private static final String IDACCION_COLUMN_NAME = "ACCION";
	private static final String TIMESTAMP_COLUMN_NAME = "TIMESTAMP";
	private static final String DIRIP_COLUMN_NAME = "DIRIP";
	private static final String IDUSUARIO_COLUMN_NAME = "IDUSUARIO";
	private static final String CODERROR_COLUMN_NAME = "CODERROR";

	/** Definicion de las columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDMODULO = new DbColumnDef(null,
			TABLE_NAME, IDMODULO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_IDACCION = new DbColumnDef(null,
			TABLE_NAME, IDACCION_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_TIMESTAMP = new DbColumnDef(
			TIMESTAMP_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_DIRIP = new DbColumnDef(null,
			TABLE_NAME, DIRIP_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDUSUARIO = new DbColumnDef(null,
			TABLE_NAME, IDUSUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef CAMPO_CODERROR = new DbColumnDef(null,
			TABLE_NAME, CODERROR_COLUMN_NAME, DbDataType.LONG_INTEGER, true);

	/** Listado con las definiciones de las columnas */
	public static final DbColumnDef[] COLS_DEFS_TRAZA = { CAMPO_ID,
			CAMPO_IDMODULO, CAMPO_IDACCION, CAMPO_TIMESTAMP, CAMPO_DIRIP,
			CAMPO_IDUSUARIO, CAMPO_CODERROR, };

	public static final String COLUMN_NAMES_TRAZA = DbUtil
			.getColumnNames(COLS_DEFS_TRAZA);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param dataSource
	 *            TransactionManager de la entidad
	 */
	public TrazaDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public TrazaDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Inserta una nueva linea de traza de auditoria en la base de datos
	 * 
	 * @param traza
	 *            Linea de auditoría a insertar.
	 * @return {@link TrazaVO} insertada
	 */
	public TrazaVO insertTraza(final TrazaVO traza) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				traza.setId(getGuid(traza.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_TRAZA, traza);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES_TRAZA,
						inputRecord);
			}
		};

		command.execute();

		return traza;
	}

	/**
	 * Obtiene un listado de las pistas de auditoría existentes que cumplen los
	 * requisitos de la búsqueda.
	 * 
	 * @param busqueda
	 *            Información del formulario de búsqueda.
	 * @param pageInfo
	 *            Informacion de paginacion de la tabla de resultados
	 * @return Lista de las pistas de auditoría que cumplen el filtro.
	 * @throws TooManyResultsException
	 *             si se excede del número máximo de resultados.
	 */
	public Collection getPistas(BusquedaPistasVO busqueda, PageInfo pageInfo)
			throws TooManyResultsException {
		// Map pairsTableNameColsDefs = new HashMap();
		// pairsTableNameColsDefs.put(
		// TABLE_NAME, COLS_DEFS_TRAZA );
		// pairsTableNameColsDefs.put(
		// UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
		// UsuarioDBEntityImpl.CAMPO_NOMBRE,
		// UsuarioDBEntityImpl.CAMPO_APELLIDOS } );

		String qual = getBusquedaWhereClause(busqueda);

		// MIGRACION BD - OUTER JOIN (SIN PROBAR
		// Mapeo de campos y tablas
		TableDef tablaTraza = new TableDef(TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaTraza, CAMPO_IDUSUARIO), new DbColumnDef(
						new TableDef(UsuarioDBEntityImpl.TABLE_NAME),
						UsuarioDBEntityImpl.CAMPO_ID)) };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils
				.generateLeftOuterJoinCondition(tablaTraza, joins));
		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils.concat(
				new DbColumnDef[] {
						new DbColumnDef(new TableDef(
								UsuarioDBEntityImpl.TABLE_NAME),
								UsuarioDBEntityImpl.CAMPO_NOMBRE),
						new DbColumnDef(new TableDef(
								UsuarioDBEntityImpl.TABLE_NAME),
								UsuarioDBEntityImpl.CAMPO_APELLIDOS) },
				COLS_DEFS_TRAZA);

		if (pageInfo != null) {
			// Añadimos ordenacion
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("modulo", CAMPO_IDMODULO);
			criteriosOrdenacion.put("accion", CAMPO_IDACCION);
			criteriosOrdenacion.put("fecha", CAMPO_TIMESTAMP);
			criteriosOrdenacion.put("dirIP", CAMPO_DIRIP);
			criteriosOrdenacion.put("usuario", new DbColumnDef[] {
					UsuarioDBEntityImpl.CAMPO_APELLIDOS,
					UsuarioDBEntityImpl.CAMPO_NOMBRE });

			// return getVOS(qual,
			// pageInfo.getOrderBy(criteriosOrdenacion),
			// pairsTableNameColsDefs, TrazaVO.class, pageInfo);

			return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
					pageInfo.getOrderBy(criteriosOrdenacion), TrazaVO.class,
					pageInfo);
		} else {
			StringBuffer sbQual = new StringBuffer(qual).append(" ORDER BY ")
					.append(CAMPO_IDMODULO.getQualifiedName());

			// return getVOS(sbQual.toString(),
			// pairsTableNameColsDefs, TrazaVO.class);
			return getVOS(sbQual.toString(), sqlFrom.toString(),
					COLS_DEF_QUERY, TrazaVO.class);
		}
	}

	/**
	 * Obtiene la clásula WHERE para la búsqueda de las pistas de auditoria.
	 * 
	 * @param busqueda
	 *            Información del formulario de búsqueda.
	 * @return Cláususla WHERE.
	 */
	private String getBusquedaWhereClause(BusquedaPistasVO busqueda) {
		final StringBuffer clausulaWhere = new StringBuffer();

		compruebaModulo(busqueda, clausulaWhere);

		compruebaAccion(busqueda, clausulaWhere);

		compruebaGrupo(busqueda, clausulaWhere);

		compruebaIP(busqueda, clausulaWhere);

		compruebaFecha(busqueda, clausulaWhere);

		// compruebaUsuario(busqueda, clausulaWhere);

		return clausulaWhere.toString();
	}

	/**
	 * Realiza la comprobacion de la fecha en los criterios de busqueda para
	 * añadirlo a la clausuala where para restringir la busqueda
	 * 
	 * @param busqueda
	 *            Criterios de Busqueda
	 * @param clausulaWhere
	 *            Clausula where
	 */
	private void compruebaFecha(BusquedaPistasVO busqueda,
			final StringBuffer clausulaWhere) {
		// FECHA
		if ((busqueda.getFechaIni() != null)
				|| (busqueda.getFechaFin() != null)) {
			// Añadir where
			if (clausulaWhere.length() <= 0)
				clausulaWhere.append(" WHERE ");
			else
				clausulaWhere.append(" AND ");

			if (busqueda.getFechaIni() != null) {
				// clausulaWhere.append(DBUtils.getQualifiedColumnName(TABLE_NAME,
				// CAMPO_TIMESTAMP))
				// .append(">=TO_DATE('")
				// .append(CustomDateFormat.SDF_YYYYMMDD.format(busqueda.getFechaIni()))
				// .append("','")
				// .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
				// .append("')");
				Calendar fechaIniABuscar = Calendar.getInstance();
				fechaIniABuscar.setTime(busqueda.getFechaIni());
				fechaIniABuscar.set(Calendar.HOUR_OF_DAY, 0);
				fechaIniABuscar.set(Calendar.MINUTE, 0);
				fechaIniABuscar.set(Calendar.SECOND, 0);
				clausulaWhere
						.append(DBUtils.getQualifiedColumnName(TABLE_NAME,
								CAMPO_TIMESTAMP))
						.append(">=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								fechaIniABuscar.getTime(),
								CustomDateFormat.SDF_YYYYMMDD_HHMMSS));

			}

			if (busqueda.getFechaFin() != null) {
				if (busqueda.getFechaIni() != null)
					clausulaWhere.append(" AND ");

				// compruebo si la fecha de inicio y fin son iguales. De ser asi
				// incrmento en uno la fecha de fin para
				// hacer otro tipo de consulta diferente al que se hace
				// normalmente ya que las fechas estan almacenadas
				// con la hora

				if (busqueda.getFechaIni() != null
						&& busqueda.getFechaIni()
								.equals(busqueda.getFechaFin())) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(busqueda.getFechaFin());
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					busqueda.setFechaFin(calendar.getTime());
					clausulaWhere.append(
							DBUtils.getQualifiedColumnName(TABLE_NAME,
									TrazaDBEntity.CAMPO_TIMESTAMP)).append("<");
					// .append("<TO_DATE('");

				} else {
					clausulaWhere.append(
							DBUtils.getQualifiedColumnName(TABLE_NAME,
									TrazaDBEntity.CAMPO_TIMESTAMP))
							.append("<=");
					// .append("<=TO_DATE('");

				}

				// clausulaWhere
				// .append(CustomDateFormat.SDF_YYYYMMDD.format(busqueda.getFechaFin()))
				// .append("','")
				// .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
				// .append("')");
				Calendar fechaFinABuscar = Calendar.getInstance();
				// Modificado Alicia - Error fechaIni cuando debería ser
				// fechaFin
				// fechaFinABuscar.setTime(busqueda.getFechaIni());
				fechaFinABuscar.setTime(busqueda.getFechaFin());
				fechaFinABuscar.set(Calendar.HOUR_OF_DAY, 23);
				fechaFinABuscar.set(Calendar.MINUTE, 59);
				fechaFinABuscar.set(Calendar.SECOND, 59);
				clausulaWhere.append(DBUtils.getNativeToDateSyntax(
						getConnection(), fechaFinABuscar.getTime(),
						CustomDateFormat.SDF_YYYYMMDD_HHMMSS));
			}
		}
	}

	/**
	 * Realiza la comprobacion de la direccion IP en los criterios de busqueda
	 * para añadirlo a la clausuala where para restringir la busqueda
	 * 
	 * @param busqueda
	 *            Criterios de Busqueda
	 * @param clausulaWhere
	 *            Clausula where
	 */
	private void compruebaIP(BusquedaPistasVO busqueda,
			final StringBuffer clausulaWhere) {
		// IP
		if (busqueda != null && StringUtils.isNotBlank(busqueda.getIp())) {
			// Añadir where
			if (clausulaWhere.length() <= 0)
				clausulaWhere.append(" WHERE ");
			else
				clausulaWhere.append(" AND ");

			clausulaWhere.append(DBUtils.generateTokenFieldQualified(
					CAMPO_DIRIP, busqueda.getIp(), TABLE_NAME,
					ConstraintType.EQUAL));
		}
	}

	/**
	 * Realiza la comprobacion del grupo en los criterios de busqueda para
	 * añadirlo a la clausuala where para restringir la busqueda
	 * 
	 * @param busqueda
	 *            Criterios de Busqueda
	 * @param clausulaWhere
	 *            Clausula where
	 */
	private void compruebaGrupo(BusquedaPistasVO busqueda,
			final StringBuffer clausulaWhere) {
		if (busqueda != null && !busqueda.getGrupo().equalsIgnoreCase("-1")) {
			// Añadir where
			if (clausulaWhere.length() <= 0)
				clausulaWhere.append(" WHERE ");
			else
				clausulaWhere.append(" AND ");

			clausulaWhere.append(IDUSUARIO_COLUMN_NAME).append(" IN ( ");

			// Generamos la select del in
			clausulaWhere.append("SELECT ")
					.append(GrupoUsuarioDBEntityImpl.TABLE_NAME).append(".")
					.append(GrupoUsuarioDBEntityImpl.ID_USUARIO_COLUMN_NAME)
					.append(" FROM ")
					.append(GrupoUsuarioDBEntityImpl.TABLE_NAME)
					.append(" WHERE ");
			clausulaWhere.append(DBUtils.generateTokenFieldQualified(
					GrupoUsuarioDBEntityImpl.CAMPO_ID_GRUPO,
					busqueda.getGrupo(), GrupoUsuarioDBEntityImpl.TABLE_NAME,
					ConstraintType.EQUAL));

			clausulaWhere.append(" ) ");
		}
	}

	/**
	 * Realiza la comprobacion de la accion en los criterios de busqueda para
	 * añadirlo a la clausuala where para restringir la busqueda
	 * 
	 * @param busqueda
	 *            Criterios de Busqueda
	 * @param clausulaWhere
	 *            Clausula where
	 */
	private void compruebaAccion(BusquedaPistasVO busqueda,
			final StringBuffer clausulaWhere) {
		// ACCION
		if (busqueda != null && busqueda.getAccion() > 0) {
			// Añadir where
			if (clausulaWhere.length() <= 0)
				clausulaWhere.append(" WHERE ");
			else
				clausulaWhere.append(" AND ");

			clausulaWhere.append(DBUtils.generateTokenFieldQualified(
					CAMPO_IDACCION, busqueda.getAccion(), TABLE_NAME,
					ConstraintType.EQUAL));
		}
	}

	/**
	 * Realiza la comprobacion del modulo en los criterios de busqueda para
	 * añadirlo a la clausuala where para restringir la busqueda
	 * 
	 * @param busqueda
	 *            Criterios de Busqueda
	 * @param clausulaWhere
	 *            Clausula where
	 */
	private void compruebaModulo(BusquedaPistasVO busqueda,
			final StringBuffer clausulaWhere) {
		// MODULO
		if (busqueda != null && busqueda.getModulo() > 0) {
			clausulaWhere.append("WHERE ").append(
					DBUtils.generateTokenFieldQualified(CAMPO_IDMODULO,
							busqueda.getModulo(), TABLE_NAME,
							ConstraintType.EQUAL));
		}
	}

	// /**
	// * Realiza la comprobacion del usuario en los criterios de busqueda para
	// añadirlo a la clausuala where para restringir la busqueda
	// * @param busqueda Criterios de Busqueda
	// * @param clausulaWhere Clausula where
	// */
	// private void compruebaUsuario(BusquedaPistasVO busqueda, final
	// StringBuffer clausulaWhere) {
	// // USUARIO
	// //Añadir where
	// if (clausulaWhere.length()<=0)
	// clausulaWhere.append(" WHERE ");
	// else
	// clausulaWhere.append(" AND ");
	//
	// clausulaWhere.append( DBUtils.generateJoinCondition(
	// TABLE_NAME,
	// CAMPO_IDUSUARIO,
	// UsuarioDBEntityImpl.TABLE_NAME,
	// UsuarioDBEntityImpl.CAMPO_ID) ).append("(+) ").toString();
	// }

	/**
	 * Obtiene los datos de una pista de auditoria a partir de su identificador
	 * 
	 * @param id
	 *            Identificador de la pista de auditoria que deseamos recuperar
	 * @return Pista de auditoria asociada al id
	 */
	public TrazaVO getPista(String id) {

		TableDef tablaUsuario = new TableDef(UsuarioDBEntityImpl.TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] {

		new JoinDefinition(new DbColumnDef(tablaUsuario,
				UsuarioDBEntityImpl.CAMPO_ID), new DbColumnDef(new TableDef(
				TABLE_NAME), CAMPO_IDUSUARIO)) };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(tablaUsuario,
				joins));

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_ID, id,
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		DbColumnDef[] columns = { UsuarioDBEntityImpl.CAMPO_NOMBRE,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS, CAMPO_ID, CAMPO_IDMODULO,
				CAMPO_IDACCION, CAMPO_TIMESTAMP, CAMPO_DIRIP, CAMPO_IDUSUARIO,
				CAMPO_CODERROR };

		TrazaVO traza = (TrazaVO) getVO(qual, sqlFrom.toString(), columns,
				TrazaVO.class);
		if (traza != null) {
			traza.setAccionName(ArchivoActions.getActionName(traza.getAccion()));
			traza.setModuloName(ArchivoModules.getModuleName(traza.getModulo()));
			traza.setError(ArchivoErrorCodes.getErrorName(traza.getCodError()));
		}

		return traza;
	}

	/**
	 * Obtiene todas las pistas de auditoria para un determinado objeto
	 * 
	 * @param type
	 *            Tipo del objeto
	 * @param id
	 *            Identificador del objeto
	 * @return Listado de las pistas asociadas a ese objeto
	 */
	public Collection getPistasXObject(int type, String id) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(TABLE_NAME, CAMPO_ID,
						DatosTrazaDBEntity.TABLE_NAME,
						DatosTrazaDBEntity.CAMPO_IDPISTA))
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(
						DatosTrazaDBEntity.CAMPO_IDOBJETO, id,
						DatosTrazaDBEntity.TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateTokenFieldQualified(
						DatosTrazaDBEntity.CAMPO_IDTIPOOBJETO, type,
						DatosTrazaDBEntity.TABLE_NAME, ConstraintType.EQUAL))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(TABLE_NAME,
						CAMPO_IDUSUARIO, UsuarioDBEntityImpl.TABLE_NAME,
						UsuarioDBEntityImpl.CAMPO_ID)).toString();

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_TRAZA);
		pairsTableNameColsDefs.put(DatosTrazaDBEntity.TABLE_NAME,
				new DbColumnDef[0]);
		pairsTableNameColsDefs.put(UsuarioDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { UsuarioDBEntityImpl.CAMPO_NOMBRE,
						UsuarioDBEntityImpl.CAMPO_APELLIDOS });

		return getVOS(qual, pairsTableNameColsDefs, TrazaVO.class);
	}
}
