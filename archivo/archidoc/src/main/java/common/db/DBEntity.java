package common.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInputRecord;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.guid.GuidManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.mutable.MutableDouble;
import org.apache.commons.lang.mutable.MutableLong;
import org.apache.log4j.Logger;

import common.Constants;
import common.exceptions.DBException;
import common.exceptions.TooManyResultsException;
import common.lang.MutableDate;
import common.lang.MutableInt;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

/**
 * Clase base para las implementaciones de los interfaces de acceso a datos
 */
public abstract class DBEntity implements IDBEntity {

	/** Logger de la clase. */
	protected static Logger logger = Logger.getLogger(DBEntity.class);

	public static final DbColumnDef[] EMPTY_COLDEF_ARRAY = new DbColumnDef[0];

	/**
	 * Constante con el prefijo de la columna de búsqueda documental para
	 * postgres
	 */
	public static final String LONG_TEXT_POSTGRES_PREFFIX = "idx";

	protected DbDataSource dataSource = null;

	protected DbConnection dbConnection = null;

	public DBEntity(DbDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DBEntity(DbConnection conn) {
		this.dbConnection = conn;
	}

	public DbDataSource getDataSource() {
		return this.dataSource;
	}

	protected DbConnection getConnection() {
		if (dataSource != null) {
			return dataSource.obtenerConexion();
		} else if (dbConnection != null) {
			return dbConnection;
		}
		throw new DBException("DBEntity DataSource no establecido");
	}

	public int updateFields(String qual, final Map columnsToUpdate,
			final String TABLE_NAME) {
		return updateFields(qual, columnsToUpdate, TABLE_NAME, Constants.BLANK);
	}

	/**
	 * Lleva a cabo la actualización de las columnas de una tabla
	 * 
	 * @param qual
	 *            cualificador de la actualizacion
	 * @param columnsToUpdate
	 *            : Map con key de tipo @link DbColumnDef y value String. La key
	 *            indica la columna a actualizar y el value con el valor a
	 *            establecer.
	 * @param TABLE_NAME
	 *            Nombre de la tabla sobre la que se realiza la actualización
	 */
	public int updateFields(String qual, final Map columnsToUpdate,
			final String TABLE_NAME, String withQuery) {
		DbConnection conn = null;
		try {
			conn = getConnection();
			final DbColumnDef[] colsToUpdate = (DbColumnDef[]) columnsToUpdate
					.keySet().toArray(EMPTY_COLDEF_ARRAY);
			return DbUpdateFns.update(conn, TABLE_NAME,
					DbUtil.getColumnNames(colsToUpdate), new DbInputRecord() {
						public void setStatementValues(DbInputStatement stmt)
								throws Exception {
							for (int i = 0; i < colsToUpdate.length; i++) {
								// DbColumnDef colToUpdate = colsToUpdate[i];
								Object value = columnsToUpdate
										.get(colsToUpdate[i]);
								switch (colsToUpdate[i].getDataType()) {
								case DbDataType.LONG_INTEGER:
									int intValue = value == null ? DbDataType.NULL_LONG_INTEGER
											: Integer.parseInt(value.toString());
									stmt.setLongInteger(i + 1, intValue);
									break;
								case DbDataType.SHORT_INTEGER:
									int shortValue = value == null ? DbDataType.NULL_SHORT_INTEGER
											: (short) Integer.parseInt(value
													.toString());
									stmt.setLongInteger(i + 1, shortValue);
									break;
								case DbDataType.SHORT_TEXT:
									String shortTextValue = value == null ? DbDataType.NULL_SHORT_TEXT
											: value.toString();
									stmt.setShortText(i + 1, shortTextValue);
									break;
								case DbDataType.LONG_TEXT:
									String longTextValue = value == null ? DbDataType.NULL_LONG_TEXT
											: value.toString();
									stmt.setShortText(i + 1, longTextValue);
									// stmt.setLongText(i+1,longTextValue);//GRABAR
									// COMO SHORT TEXT PIERDE LOS ACENTOS PQ
									// HACE UNA MOVIDA DENTRO DEL LONG TEXT
									break;
								case DbDataType.LONG_DECIMAL:
									double longDecimalValue = value == null ? DbDataType.NULL_LONG_DECIMAL
											: Double.parseDouble(value
													.toString());
									stmt.setLongDecimal(i + 1, longDecimalValue);
									break;
								case DbDataType.SHORT_DECIMAL:
									float shortDecimalValue = value == null ? DbDataType.NULL_SHORT_DECIMAL
											: Float.parseFloat(value.toString());
									stmt.setShortDecimal(i + 1,
											shortDecimalValue);
									break;
								case DbDataType.DATE_TIME:
									Date dateValue = value == null ? DbDataType.NULL_DATE_TIME
											: (Date) value;
									stmt.setDateTime(i + 1, dateValue);
									break;
								default:
									stmt.setObject(i + 1, value);
								}
								/*
								 * if (columnsToUpdate.get()!= null)
								 * stmt.setObject(i+1,); else{ if
								 * (colsToUpdate[i
								 * ].getDataType()==DbDataType.DATE_TIME){
								 * stmt.setDateTime
								 * (i+1,DbDataType.NULL_DATE_TIME); }else if
								 * (colsToUpdate
								 * [i].getDataType()==DbDataType.SHORT_TEXT){
								 * stmt
								 * .setShortText(i+1,DbDataType.NULL_SHORT_TEXT
								 * ); }else if
								 * (colsToUpdate[i].getDataType()==DbDataType
								 * .LONG_DECIMAL){
								 * stmt.setLongDecimal(i+1,DbDataType
								 * .NULL_LONG_DECIMAL); }else if
								 * (colsToUpdate[i]
								 * .getDataType()==DbDataType.LONG_INTEGER){
								 * stmt
								 * .setLongInteger(i+1,DbDataType.NULL_LONG_INTEGER
								 * ); }else if
								 * (colsToUpdate[i].getDataType()==DbDataType
								 * .LONG_TEXT){
								 * stmt.setLongText(i+1,DbDataType.NULL_LONG_TEXT
								 * ); }else if
								 * (colsToUpdate[i].getDataType()==DbDataType
								 * .SHORT_INTEGER){
								 * stmt.setShortInteger(i+1,DbDataType
								 * .NULL_SHORT_INTEGER); }else if
								 * (colsToUpdate[i
								 * ].getDataType()==DbDataType.SHORT_DECIMAL){
								 * stmt.setShortDecimal(i+1,DbDataType.
								 * NULL_SHORT_DECIMAL); } }
								 */
							}
						}
					}, qual.toString(), withQuery);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * Construye un objeto del tipo que se indica con sus variables de instancia
	 * rellenos con los valores del resultado de la ejecución de la consulta
	 * construida con la información suministrada
	 * 
	 * @param qual
	 *            Clausula WHERE de la consulta a ejecutar
	 * @param pairsTABLENAME_COLSDEFS
	 *            Conjunto de pares 'Nombre de tabla' - 'Columnas de ese tabla'
	 *            del que se obtienen las columnas a obtener y el conjunto de
	 *            tablas sobre las que se realiza la consulta
	 * @param classObjectVO
	 *            Clase del objeto a generar
	 * @return Objecto generado
	 */
	protected Object getVO(final String qual,
			final Map pairsTABLENAME_COLSDEFS, final Class classObjectVO) {
		try {
			final Object objectVO;
			objectVO = classObjectVO.newInstance();
			DBCommand command = new DBCommand(this) {

				public void codeLogic(DbConnection conn) throws Exception {
					final StringBuffer allColumns = new StringBuffer(), allTableNames = new StringBuffer();

					DbColumnDef[] allColsDefs = getColumnsDefsYFillStrColumnsNamesYstrTablesNames(
							pairsTABLENAME_COLSDEFS, allColumns, allTableNames);

					SigiaDbOutputRecord row = new SigiaDbOutputRecord(objectVO,
							allColsDefs);

					DbSelectFns.select(conn, allTableNames.toString(),
							allColumns.toString(), qual, row);

				}

			};

			command.execute();

			return !command.isExecuteOk() ? null : objectVO;

		} catch (InstantiationException e) {
			throw new DBException(e);
		} catch (IllegalAccessException e) {
			throw new DBException(e);
		}
	}

	protected List getUnionVOS(final String qual1, final String TABLE_NAME1,
			final DbColumnDef[] COLS_DEFS1, final Class classObjectVO,
			final String qual2, final String TABLE_NAME2,
			final DbColumnDef[] COLS_DEFS2, final String orderBy) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS1, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.selectUnion(conn, TABLE_NAME1, TABLE_NAME2,
						DbUtil.getColumnNames(COLS_DEFS1),
						DbUtil.getColumnNames(COLS_DEFS2), qual1, qual2,
						orderBy, false, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getUnionAllVOS(final String qual1, final String TABLE_NAME1,
			final DbColumnDef[] COLS_DEFS1, final Class classObjectVO,
			final String qual2, final String TABLE_NAME2,
			final DbColumnDef[] COLS_DEFS2, final String orderBy) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS1, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.selectUnionAll(conn, TABLE_NAME1, TABLE_NAME2,
						DbUtil.getColumnNames(COLS_DEFS1),
						DbUtil.getColumnNames(COLS_DEFS2), qual1, qual2,
						orderBy, false, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected Object getVO(final String qual, final String TABLE_NAME,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO) {
		try {
			final Object objectVO;
			objectVO = classObjectVO.newInstance();
			DBCommand command = new DBCommand(this) {

				public void codeLogic(DbConnection conn) throws Exception {

					SigiaDbOutputRecord row = new SigiaDbOutputRecord(objectVO,
							COLS_DEFS);

					DbSelectFns.select(conn, TABLE_NAME,
							DbUtil.getColumnNames(COLS_DEFS), qual, row);
				}

			};

			command.execute();

			return !command.isExecuteOk() ? null : objectVO;

		} catch (InstantiationException e) {
			throw new DBException(e);
		} catch (IllegalAccessException e) {
			throw new DBException(e);
		}
	}

	protected Object getVO(final String qual, final String TABLE_NAME,
			final String hint, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO) {
		try {
			final Object objectVO;
			objectVO = classObjectVO.newInstance();
			DBCommand command = new DBCommand(this) {

				public void codeLogic(DbConnection conn) throws Exception {

					SigiaDbOutputRecord row = new SigiaDbOutputRecord(objectVO,
							COLS_DEFS);

					DbSelectFns.select(conn, TABLE_NAME,
							DbUtil.getColumnNames(COLS_DEFS), qual, hint, row);
				}

			};

			command.execute();

			return !command.isExecuteOk() ? null : objectVO;

		} catch (InstantiationException e) {
			throw new DBException(e);
		} catch (IllegalAccessException e) {
			throw new DBException(e);
		}
	}

	private DbColumnDef[] getColumnsDefsYFillStrColumnsNamesYstrTablesNames(
			final Map pairsTABLENAME_COLSDEFS,
			StringBuffer outParamAllColumnsNames,
			StringBuffer outParamAllTablesNames) {
		DbColumnDef[] outParamAllColsDefs = null;
		DbColumnDef[] colsDefs = null;
		String tableName;
		for (Iterator itPairs = pairsTABLENAME_COLSDEFS.keySet().iterator(); itPairs
				.hasNext();) {
			tableName = (String) itPairs.next();
			colsDefs = (DbColumnDef[]) pairsTABLENAME_COLSDEFS.get(tableName);
			if (colsDefs != null) {
				outParamAllColsDefs = (DbColumnDef[]) ArrayUtils.concat(
						outParamAllColsDefs, colsDefs);
				// obtencion de todos los nombres de columna
				for (int i = 0; i < colsDefs.length; i++) {
					if (outParamAllColumnsNames.length() > 0)
						outParamAllColumnsNames.append(",");
					outParamAllColumnsNames.append(DBUtils
							.getQualifiedColumnName(tableName, colsDefs[i]));
				}
			}

			// obtencion de todos los nombres de tabla
			if (StringUtils.isNotBlank(tableName)) {
				if (outParamAllTablesNames.length() > 0)
					outParamAllTablesNames.append(",");
				outParamAllTablesNames.append(tableName);
			}

		}
		return outParamAllColsDefs;
	}

	protected List getVOS(final String qual, final Map pairsTABLENAME_COLSDEFS,
			final Class classObjectVO) {

		// obtencion de allColsDefs, allColumns, allTableNames
		final StringBuffer allColumns = new StringBuffer(), allTableNames = new StringBuffer();
		DbColumnDef[] allColsDefs = getColumnsDefsYFillStrColumnsNamesYstrTablesNames(
				pairsTABLENAME_COLSDEFS, allColumns, allTableNames);

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				allColsDefs, classObjectVO);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				DbSelectFns.select(conn, allTableNames.toString(),
						allColumns.toString(), qual, false, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	/**
	 * Obtiene los VOS especificados por la consulta.
	 * 
	 * @param COLS_DEFS
	 *            Definición de Columnas
	 * @param sql
	 *            Sentencia SELECT a Ejecutar
	 * @param classObjectVO
	 *            Objeto a Devolver
	 * @return
	 */
	protected List getVOS(final DbColumnDef[] COLS_DEFS, final String sql,
			final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, sql, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected Object getVO(final DbColumnDef[] COLS_DEFS,
			final ConsultaConnectBy consultaConnectBy, final Class classObjectVO) {
		try {
			final Object objectVO;
			objectVO = classObjectVO.newInstance();
			DBCommand command = new DBCommand(this) {

				public void codeLogic(DbConnection conn) throws Exception {

					SigiaDbOutputRecord row = new SigiaDbOutputRecord(objectVO,
							COLS_DEFS);

					DbSelectFns.select(conn,
							consultaConnectBy.getSqlCompuesta(), row);
				}

			};

			command.execute();

			return !command.isExecuteOk() ? null : objectVO;
		} catch (InstantiationException e) {
			throw new DBException(e);
		} catch (IllegalAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Obtiene los VOS especificados por la consulta.
	 * 
	 * @param COLS_DEFS
	 *            Definición de Columnas
	 * @param sql
	 *            Sentencia SELECT a Ejecutar
	 * @param classObjectVO
	 *            Objeto a Devolver
	 * @return
	 */
	protected Object getVO(final DbColumnDef[] COLS_DEFS, final String sql,
			final Class classObjectVO) {
		try {
			final Object objectVO;
			objectVO = classObjectVO.newInstance();
			DBCommand command = new DBCommand(this) {

				public void codeLogic(DbConnection conn) throws Exception {

					final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
							COLS_DEFS, classObjectVO);
					DbSelectFns.select(conn, sql, rowSet);
				}

			};

			command.execute();

			return !command.isExecuteOk() ? null : objectVO;

		} catch (InstantiationException e) {
			throw new DBException(e);
		} catch (IllegalAccessException e) {
			throw new DBException(e);
		}
	}

	/**
	 * Obtiene los VOS especificados por la consulta.
	 * 
	 * @param COLS_DEFS
	 *            Definición de Columnas
	 * @param sql
	 *            Sentencia SELECT a Ejecutar
	 * @param classObjectVO
	 *            Objeto a Devolver
	 * @return
	 */
	protected List getVOS(final DbColumnDef[] COLS_DEFS,
			final ConsultaConnectBy sql, final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, sql.getSqlCompuestaWith(), rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getVOS(final String qual, final String orderBy,
			final Map pairsTABLENAME_COLSDEFS, final Class classObjectVO,
			final PageInfo pageInfo) throws TooManyResultsException {

		// obtencion de allColsDefs, allColumns, allTableNames
		final StringBuffer allColumns = new StringBuffer();
		final StringBuffer allTableNames = new StringBuffer();
		final DbColumnDef[] allColsDefs = getColumnsDefsYFillStrColumnsNamesYstrTablesNames(
				pairsTABLENAME_COLSDEFS, allColumns, allTableNames);

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				allColsDefs, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size;
				if (StringUtils.contains(qual, "GROUP BY"))
					size = DbSelectFns.selectCount(
							conn,
							"("
									+ DbSelectStatement.getSelectStmtText(
											allTableNames.toString(),
											allColumns.toString(), qual, false)
									+ ")", null);
				else
					size = DbSelectFns.selectCount(conn,
							allTableNames.toString(), qual);
				rowSet.setSize(size);

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, allTableNames.toString(),
								allColumns.toString(), new StringBuffer(qual)
										.append(orderBy).toString(), false,
								rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns.select(conn, allTableNames.toString(),
								allColumns.toString(), new StringBuffer(qual)
										.append(orderBy).toString(), false,
								rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected int getVOCount(final String qual, final String TABLE_NAME) {
		final MutableInt voCount = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				voCount.setValue(DbSelectFns
						.selectCount(conn, TABLE_NAME, qual));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	protected int getVOCountGeneral(final ConsultaConnectBy consultaConnectBy) {
		final MutableInt voCount = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				voCount.setValue(DbSelectFns.selectCount(conn,
						consultaConnectBy));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	protected double getVOSumGeneral(final ConsultaConnectBy consultaConnectBy) {
		final MutableDouble voCount = new MutableDouble(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				voCount.setValue(DbSelectFns.selectSum(conn, consultaConnectBy));
			}
		};

		command.execute();

		return ((Double) voCount.getValue()).doubleValue();
	}

	/**
	 * Obtiene el valor máximo de una columna de tipo String.
	 * 
	 * @param qual
	 * @param tableName
	 * @param colName
	 * @return
	 */
	protected long getMaxString(final String qual, final String tableName,
			final DbColumnDef colDef) {
		final MutableLong voCount = new MutableLong(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				if (DbDataType.SHORT_TEXT == colDef.getDataType()
						|| DbDataType.LONG_TEXT == colDef.getDataType())
					voCount.setValue(DbSelectFns.selectMaxString(conn,
							tableName, colDef.getName(), qual));
				else
					voCount.setValue(DbSelectFns.selectMaxLong(conn, tableName,
							colDef.getName(), qual));
			}
		};

		command.execute();

		return voCount.longValue();
	}

	/**
	 * Obtiene el valor máximo de una columna de tipo Date.
	 * 
	 * @param qual
	 * @param tableName
	 * @param colName
	 * @return
	 */
	protected Date getMaxDate(final String qual, final String tableName,
			final DbColumnDef colDef) {
		final MutableDate voCount = new MutableDate(new Date());

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				voCount.setValue(DbSelectFns.selectMaxDate(conn, tableName,
						colDef.getName(), qual));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	/**
	 * Obtiene el valor máximo de una columna.
	 * 
	 * @param qual
	 * @param tableName
	 * @param colName
	 * @return
	 */
	protected int getMax(final String qual, final String tableName,
			final DbColumnDef colDef) {
		final MutableInt voCount = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				voCount.setValue(DbSelectFns.selectMaxInt(conn, tableName,
						colDef.getName(), qual));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	/**
	 * Obtiene el valor mínimo de una columna de tipo String.
	 * 
	 * @param qual
	 * @param tableName
	 * @param colName
	 * @return
	 */
	protected long getMinString(final String qual, final String tableName,
			final DbColumnDef colDef) {
		final MutableLong voCount = new MutableLong(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				if (DbDataType.SHORT_TEXT == colDef.getDataType()
						|| DbDataType.LONG_TEXT == colDef.getDataType())
					voCount.setValue(DbSelectFns.selectMinString(conn,
							tableName, colDef.getName(), qual));
				else
					voCount.setValue(DbSelectFns.selectMinLong(conn, tableName,
							colDef.getName(), qual));
			}
		};

		command.execute();

		return voCount.longValue();
	}

	/**
	 * Obtiene el valor mínimo de una columna de tipo Date.
	 * 
	 * @param qual
	 * @param tableName
	 * @param colName
	 * @return
	 */
	protected Date getMinDate(final String qual, final String tableName,
			final DbColumnDef colDef) {
		final MutableDate voCount = new MutableDate(new Date());

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				voCount.setValue(DbSelectFns.selectMinDate(conn, tableName,
						colDef.getName(), qual));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	/**
	 * Obtiene el valor mínimo de una columna.
	 * 
	 * @param qual
	 * @param tableName
	 * @param colName
	 * @return
	 */
	protected int getMin(final String qual, final String tableName,
			final DbColumnDef colDef) {
		final MutableInt voCount = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				voCount.setValue(DbSelectFns.selectMinInt(conn, tableName,
						colDef.getName(), qual));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	protected int getVOCount(final String qual, final String TABLE_NAME,
			final String hint) {
		final MutableInt voCount = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				voCount.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual, hint));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	protected int getDistintVOCount(final DbColumnDef[] COLS_DEFS,
			final String qual, final String TABLE_NAME) {
		final MutableInt voCount = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				voCount.setValue(DbSelectFns.selectCountDistinct(conn,
						TABLE_NAME, qual, DbUtil.getColumnNames(COLS_DEFS)));
			}
		};

		command.execute();

		return voCount.getValue();
	}

	protected List getVOS(final String qual, final String TABLE_NAME,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO,
			final String orderBy, final int numMaxResultados)
			throws TooManyResultsException {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				// int size = DbSelectFns.selectCount(conn, TABLE_NAME, qual);
				int size = 0;
				if (numMaxResultados > 0) {
					if (size > numMaxResultados) {
						throw new TooManyResultsException(size,
								numMaxResultados);
					}
				}

				// Si hay algún resultado, hacer la select completa
				if (size > 0 || numMaxResultados == 0)
					DbSelectFns.select(conn, TABLE_NAME,
							DbUtil.getColumnNames(COLS_DEFS), qual + orderBy,
							false, rowSet);
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getVOS(final String qual, final String TABLE_NAME,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO,
			final String orderBy, final int numMaxResultados,
			final String TABLE_NAME_COUNT, final String distinctFields)
			throws TooManyResultsException {

		return getVOS(null, qual, TABLE_NAME, COLS_DEFS, classObjectVO,
				orderBy, numMaxResultados, TABLE_NAME_COUNT, distinctFields);
	}

	protected List getVOS(final String with, final String qual,
			final String TABLE_NAME, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO, final String orderBy,
			final int numMaxResultados, final String TABLE_NAME_COUNT,
			final String distinctFields) throws TooManyResultsException {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				// int size = DbSelectFns.selectCount(conn, TABLE_NAME_COUNT,
				// qual);
				int size = 0;
				// Si numMaxResultados == 0 no hay restricción de número de
				// resultados
				if (numMaxResultados > 0) {
					// size = DbSelectFns.selectCount(conn, TABLE_NAME_COUNT,
					// qual);
					size = DbSelectFns.selectCountDistinct(conn, with,
							TABLE_NAME_COUNT, qual, distinctFields);

					if (size > numMaxResultados)
					// if (size > numMaxResultados && numMaxResultados >= 0)
					{
						throw new TooManyResultsException(size,
								numMaxResultados);
					}
				}

				// Si hay algún resultado o no hay restricción de número de
				// resultados, hacer la select completa
				if (size > 0 || numMaxResultados == 0)
					DbSelectFns.select(conn, with, TABLE_NAME,
							DbUtil.getColumnNames(COLS_DEFS), qual + orderBy,
							true, rowSet);
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getVOS(final String with, final String qual,
			final String TABLE_NAME, final DbColumnDef[] COLS_DEFS_QUERY,
			final DbColumnDef[] COLS_DEFS_FILL, final Class classObjectVO,
			final String orderBy, final int numMaxResultados,
			final String TABLE_NAME_COUNT, final String distinctFields)
			throws TooManyResultsException {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS_QUERY, COLS_DEFS_FILL, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				// int size = DbSelectFns.selectCount(conn, TABLE_NAME_COUNT,
				// qual);
				int size = 0;
				// Si numMaxResultados == 0 no hay restricción de número de
				// resultados
				if (numMaxResultados > 0) {
					// size = DbSelectFns.selectCount(conn, TABLE_NAME_COUNT,
					// qual);
					size = DbSelectFns.selectCountDistinct(conn, with,
							TABLE_NAME_COUNT, qual, distinctFields);

					if (size > numMaxResultados)
					// if (size > numMaxResultados && numMaxResultados >= 0)
					{
						throw new TooManyResultsException(size,
								numMaxResultados);
					}
				}

				// Si hay algún resultado o no hay restricción de número de
				// resultados, hacer la select completa
				if (size > 0 || numMaxResultados == 0) {
					if (ArrayUtils.isNotEmpty(COLS_DEFS_FILL))
						DbSelectFns.select(conn, with, TABLE_NAME,
								DbUtil.getColumnNames(COLS_DEFS_QUERY), qual
										+ orderBy, true, rowSet);
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	/**
	 * Obtiene los VOs indicados teniendo en cuenta que puede haber diferentes
	 * columnas a seleccionar en el SELECT COUNT y en el SELECT
	 * 
	 * @param qual
	 * @param TABLE_NAME
	 * @param TOTAL_COLS_DEFS
	 * @param classObjectVO
	 * @param orderBy
	 * @param numMaxResultados
	 * @param TABLE_NAME_COUNT
	 * @param COLS_DEFS_COUNT
	 * @return Lista de VOs del tipo indicado
	 * @throws TooManyResultsException
	 */
	protected List getVOS(final String qual, final String TABLE_NAME,
			final DbColumnDef[] TOTAL_COLS_DEFS, final Class classObjectVO,
			final String orderBy, final int numMaxResultados,
			final String TABLE_NAME_COUNT, final DbColumnDef[] COLS_DEFS_COUNT)
			throws TooManyResultsException {
		return getVOS(null, qual, TABLE_NAME, TOTAL_COLS_DEFS, classObjectVO,
				orderBy, numMaxResultados, TABLE_NAME_COUNT, COLS_DEFS_COUNT);
	}

	protected List getVOS(final String withQuery, final String qual,
			final String TABLE_NAME, final DbColumnDef[] TOTAL_COLS_DEFS,
			final Class classObjectVO, final String orderBy,
			final int numMaxResultados, final String TABLE_NAME_COUNT,
			final DbColumnDef[] COLS_DEFS_COUNT) throws TooManyResultsException {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				TOTAL_COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size = 0;
				// Si numMaxResultados == 0 no hay restricción de número de
				// resultados
				if (numMaxResultados > 0) {
					size = DbSelectFns.selectCountDistinct(conn, withQuery,
							TABLE_NAME_COUNT, qual,
							DbUtil.getColumnNames(COLS_DEFS_COUNT));

					if (size > numMaxResultados)
						throw new TooManyResultsException(size,
								numMaxResultados);
				}

				// Si hay algún resultado o no hay restricción de número de
				// resultados, hacer la select completa
				if (size > 0 || numMaxResultados == 0)
					DbSelectFns.select(conn, withQuery, TABLE_NAME,
							DbUtil.getColumnNames(TOTAL_COLS_DEFS), qual
									+ orderBy, true, rowSet);
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getVOS(final String qual, final String TABLE_NAME,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO,
			final String orderBy, final int numMaxResultados,
			final String TABLE_NAME_COUNT) throws TooManyResultsException {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				// int size = DbSelectFns.selectCount(conn, TABLE_NAME_COUNT,
				// qual);
				int size = 0;
				// Si numMaxResultados == 0 no hay restricción de número de
				// resultados
				if (numMaxResultados > 0) {
					size = DbSelectFns
							.selectCount(conn, TABLE_NAME_COUNT, qual);
					// size = DbSelectFns.selectCountDistinct (conn,
					// TABLE_NAME_COUNT, qual, distinctFields);

					if (size > numMaxResultados)
					// if (size > numMaxResultados && numMaxResultados >= 0)
					{
						throw new TooManyResultsException(size,
								numMaxResultados);
					}
				}

				// Si hay algún resultado o no hay restricción de número de
				// resultados, hacer la select completa
				if (size > 0 || numMaxResultados == 0)
					DbSelectFns.select(conn, TABLE_NAME,
							DbUtil.getColumnNames(COLS_DEFS), qual + orderBy,
							false, rowSet);
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getVOS(final String qual, final String TABLE_NAME,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), qual, false, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	/**
	 * Obtiene los VOS a partir de la SQL completa
	 * 
	 * @param sqlCompleta
	 *            Sentencia SQL Completa
	 * @param classObjectVO
	 *            Tipo de Objeto a Devolver
	 * @param rowSet
	 *            Recorset de Salida.
	 * @return Lista de Objetos de la clas classObjectVO
	 */
	protected List getVOS(final String sqlCompleta, final Class classObjectVO,
			final SigiaDbOutputRecordset rowSet) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, sqlCompleta, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getVOS(final String qual, final String TABLE_NAME,
			final DbColumnDef[] COLS_DEFS, final String orderBy,
			final Class classObjectVO, final PageInfo pageInfo)

	throws TooManyResultsException {

		// obtencion de allColsDefs, allColumns, allTableNames
		final StringBuffer allColumns = new StringBuffer(
				DbUtil.getColumnNames(COLS_DEFS));

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				COLS_DEFS, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size;
				if (StringUtils.contains(qual, "GROUP BY"))
					size = DbSelectFns.selectCount(
							conn,
							"("
									+ DbSelectStatement.getSelectStmtText(
											TABLE_NAME.toString(),
											allColumns.toString(), qual, false)
									+ ")", null);
				else
					size = DbSelectFns.selectCount(conn, TABLE_NAME, qual);
				rowSet.setSize(size);

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, TABLE_NAME, allColumns
								.toString(),
								new StringBuffer(qual).append(orderBy)
										.toString(), false, rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns.select(conn, TABLE_NAME, allColumns
								.toString(),
								new StringBuffer(qual).append(orderBy)
										.toString(), false, rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getVOS(final String qual, final String TABLE_NAME,
			final String hint, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), qual, false, hint,
						rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getVOS(final String qual, final String orderBy,
			final String TABLE_NAME, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO, final PageInfo pageInfo)
			throws TooManyResultsException {

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				COLS_DEFS, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size;

				if (StringUtils.contains(qual, "GROUP BY"))
					size = DbSelectFns.selectCount(
							conn,
							"("
									+ DbSelectStatement.getSelectStmtText(
											TABLE_NAME,
											DbUtil.getColumnNames(COLS_DEFS),
											qual, false) + ") alias ", null);
				else
					size = DbSelectFns.selectCount(conn, TABLE_NAME, qual);

				rowSet.setSize(size);

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, TABLE_NAME, DbUtil
								.getColumnNames(COLS_DEFS), new StringBuffer(
								qual).append(orderBy).toString(), false,
								rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns
								.select(conn, TABLE_NAME, DbUtil
										.getColumnNames(COLS_DEFS),
										new StringBuffer(qual).append(orderBy)
												.toString(), false, rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getFieldValues(final String qual, final String TABLE_NAME,
			final DbColumnDef colDef) {
		final SingleValueDBOutputRecordSet recordSet = new SingleValueDBOutputRecordSet(
				colDef);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME, colDef.getQualifiedName(),
						qual, false, recordSet);
			}
		};
		command.execute();

		return recordSet.getValues();
	}

	protected List getMultipleFieldValues(final String qual,
			final String TABLE_NAME, final DbColumnDef[] colDefs) {
		final MultipleDBOutputRecordSet recordSet = new MultipleDBOutputRecordSet(
				colDefs);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME,
						DBUtils.getQualifiedColumnNames(colDefs), qual, false,
						recordSet);
			}
		};
		command.execute();

		return recordSet.getValues();
	}

	// Devuelve una hashMap de hashMaps
	// donde la key de la hash de primer nivel es la concatenacion de los
	// valores de los de las columnas ids
	// Ej colsDef={ID,IDPADRE,CODIGO,NOMBRE,TITULO} e posColsInt={0,2}
	// bajo este supuesto si queremos acceder a los valores de la tupla con el
	// id="345345" y codigo="dfg"
	// HashMap
	// hashMapSegundoNivel=(HashMap)[hashMap].get("345345"+separador+"dfg")
	//
	// La hashMap de segundo nivel tiene como key el bindProperty de DbColumnDef
	// y la key el valor para esa
	// columna devuelto por la consulta.
	protected HashMap getHashMapFieldValues(final String qual,
			final String TABLE_NAME, final DbColumnDef[] colDefs,
			int[] posColsIds, String separador) {
		final MapsDBOutputRecordSet recordSet = new MapsDBOutputRecordSet(
				colDefs, posColsIds, separador);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME,
						DBUtils.getQualifiedColumnNames(colDefs), qual, false,
						recordSet);
			}
		};
		command.execute();

		return recordSet.getValues();
	}

	// Devuelve una lista de hashMap por cada tupla, en la que la key de cada
	// registro de la hashMap es la BindProperty del DbColumnDef
	protected List getListMapsFieldValues(final String qual,
			final String TABLE_NAME, final DbColumnDef[] colDefs) {
		final ListMapsDBOutputRecordSet recordSet = new ListMapsDBOutputRecordSet(
				colDefs);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME,
						DBUtils.getQualifiedColumnNames(colDefs), qual, false,
						recordSet);
			}
		};
		command.execute();

		return recordSet.getValues();
	}

	protected HashMap getPairsIdValue(final String qual,
			final String TABLE_NAME, final DbColumnDef colKey,
			final DbColumnDef colValue) {
		final PairDBOutputRecordSet recordSet = new PairDBOutputRecordSet(
				colKey, colValue);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(
						conn,
						TABLE_NAME,
						DBUtils.getQualifiedColumnNames(new DbColumnDef[] {
								colKey, colValue }), qual, false, recordSet);
			}
		};
		command.execute();

		return recordSet.getValues();
	}

	protected List getDistinctFieldValues(final String qual,
			final String TABLE_NAME, final DbColumnDef colDef) {
		final SingleValueDBOutputRecordSet recordSet = new SingleValueDBOutputRecordSet(
				colDef);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME, colDef.getQualifiedName(),
						qual, true, recordSet);
			}
		};
		command.execute();

		return recordSet.getValues();
	}

	protected void insertVO(final String TABLE_NAME,
			final DbColumnDef[] colsDefs, Object objectVO) {
		final SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(colsDefs,
				objectVO);

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(colsDefs), inputRecord);
			}
		};

		command.execute();
	}

	protected void updateVO(final String qual, final String TABLE_NAME,
			final DbColumnDef[] colsDefs, Object objectVO) {
		final SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(colsDefs,
				objectVO);

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbUpdateFns.update(conn, TABLE_NAME,
						DbUtil.getColumnNames(colsDefs), inputRecord, qual);

			}
		};

		command.execute();
	}

	protected void updateVO(final String qual,
			final Map pairsTABLENAME_COLSDEFS, Object objectVO) {

		final StringBuffer allColumns = new StringBuffer(), allTableNames = new StringBuffer();
		DbColumnDef[] allColsDefs = getColumnsDefsYFillStrColumnsNamesYstrTablesNames(
				pairsTABLENAME_COLSDEFS, allColumns, allTableNames);
		final SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
				allColsDefs, objectVO);

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbUpdateFns.update(conn, allTableNames.toString(),
						allColumns.toString(), inputRecord, qual);

			}
		};

		command.execute();
	}

	protected void deleteVO(final String qual, final String TABLE_NAME) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};

		command.execute();
	}

	protected List getDistinctVOS(final String qual,
			final Map pairsTABLENAME_COLSDEFS, final Class classObjectVO) {

		final StringBuffer allColumns = new StringBuffer(), allTableNames = new StringBuffer();
		DbColumnDef[] allColsDefs = getColumnsDefsYFillStrColumnsNamesYstrTablesNames(
				pairsTABLENAME_COLSDEFS, allColumns, allTableNames);

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				allColsDefs, classObjectVO);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {

				DbSelectFns.select(conn, allTableNames.toString(),
						allColumns.toString(), qual, true, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getDistinctVOS(final String qual, final String orderBy,
			final Map pairsTABLENAME_COLSDEFS, final Class classObjectVO,
			final PageInfo pageInfo) throws TooManyResultsException {

		// obtencion de allColsDefs, allColumns, allTableNames
		final StringBuffer allColumns = new StringBuffer();
		final StringBuffer allTableNames = new StringBuffer();

		DbColumnDef[] allColsDefs = getColumnsDefsYFillStrColumnsNamesYstrTablesNames(
				pairsTABLENAME_COLSDEFS, allColumns, allTableNames);

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				allColsDefs, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size = DbSelectFns.selectCount(conn,
						allTableNames.toString(), qual);
				rowSet.setSize(size);

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, allTableNames.toString(),
								allColumns.toString(), new StringBuffer(qual)
										.append(orderBy).toString(), true,
								rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns.select(conn, allTableNames.toString(),
								allColumns.toString(), new StringBuffer(qual)
										.append(orderBy).toString(), true,
								rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getDistinctVOS(final String qual, final String TABLE_NAME,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), qual, true, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getDistinctVOS_WITH(final String with, final String qual,
			final String TABLE_NAME, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, with, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), qual, true, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getDistinctVOS(final String qual, final String TABLE_NAME,
			final String hint, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), qual, true, hint,
						rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getDistinctVOS(final String qual, final String orderBy,
			final String TABLE_NAME, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO, final PageInfo pageInfo)
			throws TooManyResultsException {

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				COLS_DEFS, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size = DbSelectFns.selectCount(conn, TABLE_NAME, qual);
				rowSet.setSize(size);

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, TABLE_NAME, DbUtil
								.getColumnNames(COLS_DEFS), new StringBuffer(
								qual).append(orderBy).toString(), true, rowSet,
								pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns.select(conn, TABLE_NAME, DbUtil
								.getColumnNames(COLS_DEFS), new StringBuffer(
								qual).append(orderBy).toString(), true, rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	protected List getDistinctVOS(final String qual, final String orderBy,
			final String TABLE_NAME, final String hint,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO,
			final PageInfo pageInfo) throws TooManyResultsException {

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				COLS_DEFS, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size = DbSelectFns.selectCount(conn, TABLE_NAME, qual);
				rowSet.setSize(size);

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, TABLE_NAME, DbUtil
								.getColumnNames(COLS_DEFS), new StringBuffer(
								qual).append(orderBy).toString(), true, hint,
								rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns.select(conn, TABLE_NAME, DbUtil
								.getColumnNames(COLS_DEFS), new StringBuffer(
								qual).append(orderBy).toString(), true, hint,
								rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	public String appendDelimiter(String field, String delimiter) {
		return field != null && !StringUtils.isBlank(field) ? delimiter : "";
	}

	public String concatDelimiter(String inicio, String delimitador, String fin) {
		StringBuffer cadena = new StringBuffer();

		if (StringUtils.isNotEmpty(inicio)) {
			cadena.append(inicio);
		}

		if (StringUtils.isNotEmpty(inicio) && StringUtils.isNotEmpty(fin)) {
			cadena.append(delimitador);
		}

		if (StringUtils.isNotBlank(fin)) {
			cadena.append(fin);
		}

		return cadena.toString();
	}

	public String appendField(String field) {
		return field != null && !StringUtils.isBlank(field) ? field : "";
	}

	protected String getGuid(String id) {
		try {
			if (StringUtils.isEmpty(id)) {
				id = GuidManager.generateGuid(getConnection());
			}
			return id;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public int getCount() {
		final MutableInt voCount = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				voCount.setValue(DbSelectFns.selectCount(conn, getTableName(),
						null));
			}
		};

		command.execute();

		return voCount.getValue();

	}

	public String getDbFactoryClass() {
		return getDataSource().getDbFactoryClass();
	}
}
