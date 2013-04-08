package common.db;

import ieci.core.db.AutoDbColumnDef;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbTableDef;
import ieci.core.db.DbUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import common.exceptions.TooManyResultsException;
import common.exceptions.UncheckedArchivoException;
import common.pagination.PageInfo;
import common.util.StringUtils;

public abstract class AutoDbEntity extends DBEntity {

	static boolean initialized = false;

	/**
	 * @param dataSource
	 */
	public AutoDbEntity(DbDataSource dataSource) {
		super(dataSource);
		if (!initialized)
			init();
	}

	public class ColumnSelector implements Predicate {
		String columnTableName = null;

		public ColumnSelector(String columnTableName) {
			this.columnTableName = columnTableName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object arg0) {
			DbColumnDef colDef = (DbColumnDef) arg0;
			// si la columna pertence a la tabla y no se han introducido la
			// informacion del metadata
			if (colDef.getTableName().equalsIgnoreCase(columnTableName)
					&& colDef.getDataType() <= 0) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Metodo que inicializa la clase
	 * 
	 * @param conn
	 */
	public abstract void ClassInitialization(Connection conn);

	/**
	 * Inicializacion de DbColumns staticos (con obtener valdra)
	 */
	private void init() {
		Connection conn = dataSource.obtenerConexion().getJdbcConnection();

		ClassInitialization(conn);

		DbColumnDef[] columnas = getColumnsDefs();
		try {
			DbTableDef[] tableNames = getTablesDefs();
			for (int i = 0; i < tableNames.length; i++) {
				String tableName = (String) (tableNames[i].getName());
				String columnTableName = (String) ((tableNames[i].getAlias() != null && tableNames[i]
						.getAlias().length() > 0) ? tableNames[i].getAlias()
						: tableNames[i].getName());

				List allColumns = new ArrayList();
				CollectionUtils.addAll(allColumns, columnas);

				// obtencion de metadata de las columnas de la tabla
				ResultSet rs = getColumns(conn, tableName);
				while (rs.next()) {
					// seleccion de los coldefs de la tabla para completar su
					// informacion con el metadata de la tabla de BD
					Collection columnasDeTabla = CollectionUtils.select(
							allColumns, new ColumnSelector(columnTableName));
					if (columnasDeTabla != null && columnasDeTabla.size() > 0) {
						String columnName = rs.getString("COLUMN_NAME");
						// busqueda de columna por nombre
						DbColumnDef colDef = findDbColumn(columnasDeTabla,
								columnName);
						if (colDef != null) {
							colDef.setDataType(getDataType(rs
									.getInt("DATA_TYPE")));
							colDef.setMaxLen(rs.getInt("COLUMN_SIZE"));
							// if
							// ((rs.getBoolean("NULLABLE").compareToIgnoreCase("YES")
							// == 0)||
							colDef.setIsNullable(rs.getBoolean("NULLABLE"));
							// rs.getInt("DECIMAL_DIGITS");
							// rs.getString("REMARKS");
						}
					}
				}
			}

		} catch (SQLException e) {
			logger.error(e);
		} finally {
			dataSource.liberarConexion();
		}

		initialized = true;
	}

	/**
	 * @param int1
	 * @return
	 */
	private int getDataType(int dbColType) {
		try {
			return DbDataType.getDbDataType(dbColType);
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	/**
	 * @param columnName
	 * @return
	 */
	private DbColumnDef findDbColumn(Collection columnas, String columnName) {
		for (Iterator iter = columnas.iterator(); iter.hasNext();) {
			DbColumnDef element = (DbColumnDef) iter.next();
			if (element.getName().equalsIgnoreCase(columnName)) {
				return element;
			}
		}
		return null;
	}

	public ResultSet getColumns(Connection conn, String sTable) {
		ResultSet rs = null;
		DatabaseMetaData dbdata = null;
		if (sTable == null)
			throw new UncheckedArchivoException(
					"No se ha proporcionado un nombre de tabla o vista");

		try {
			dbdata = conn.getMetaData();
			/*
			 * Reglas de obtención de tablas y columnas:
			 * 
			 * ORACLE: No existe catálogo y se deben buscar en el esquema del
			 * usuario
			 * 
			 * SQLSERVER: El esquema corresponde al propietario de las tablas.
			 * Un usuario puede tener acceso a tablas en una base de datos de
			 * las que no tiene porqué ser el propietario. La forma de buscarlas
			 * será restringiendo por BBDD que en el caso de SQLSERVER
			 * corresponde al catálogo.
			 */
			String catalog = conn.getCatalog();
			// String user = null;
			// if (catalog==null)
			// user = dbdata.getUserName();
			// rs = dbdata.getColumns(catalog, user,
			// sTable.trim().toUpperCase(), null);
			rs = dbdata.getColumns(catalog, null, sTable.trim().toUpperCase(),
					null);

		} catch (SQLException e) {
			throw new UncheckedArchivoException(e);
		}
		return rs;
	}

	/**
	 * 
	 * @return Lista de las definiciones de columna de la entidad
	 */
	public abstract DbColumnDef[] getColumnsDefs();

	/**
	 * 
	 * @return El nombre de la tabla que mapea el dbEntity
	 */
	public abstract DbTableDef[] getTablesDefs();

	public static String generateJoinCondition(DbTableDef table1,
			DbColumnDef colDef1, DbTableDef table2, DbColumnDef colDef2) {
		String nameTable1 = table1.getAlias() != null ? table1.getAlias()
				: table1.getName();
		String nameTable2 = table2.getAlias() != null ? table2.getAlias()
				: table2.getName();
		return new StringBuffer().append(nameTable1).append(".")
				.append(colDef1.getName()).append("=").append(nameTable2)
				.append(".").append(colDef2.getName()).toString();
	}

	protected List getVOS(final String qual, final DbTableDef[] TABLE_NAMES,
			final DbColumnDef[] COLS_DEFS, final Class classObjectVO) {

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(
				COLS_DEFS, classObjectVO);
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer tablesNames = new StringBuffer(
						TABLE_NAMES[0].getAliasedName());

				for (int i = 1; i < TABLE_NAMES.length; i++) {
					tablesNames.append(",").append(
							TABLE_NAMES[i].getAliasedName());
				}

				DbSelectFns.select(conn, tablesNames.toString(),
						DbUtil.getColumnNames(COLS_DEFS), qual, false, rowSet);
			}
		};

		command.execute();

		return rowSet;
	}

	protected List getVOS(final String qual, final String orderBy,
			final DbTableDef[] TABLE_NAME, final DbColumnDef[] COLS_DEFS,
			final Class classObjectVO, final PageInfo pageInfo)
			throws TooManyResultsException {

		final StringBuffer tablesNamesBuff = new StringBuffer(
				TABLE_NAME[0].getAliasedName());
		for (int i = 1; i < TABLE_NAME.length; i++) {
			tablesNamesBuff.append(",").append(TABLE_NAME[i].getAliasedName());
		}
		final String tablesNames = tablesNamesBuff.toString();

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
											tablesNames,
											DbUtil.getColumnNames(COLS_DEFS),
											qual, false) + ")", null);
				else
					size = DbSelectFns.selectCount(conn, tablesNames, qual);

				rowSet.setSize(size);

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, tablesNames, DbUtil
								.getColumnNames(COLS_DEFS), new StringBuffer(
								qual).append(orderBy).toString(), false,
								rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns
								.select(conn, tablesNames, DbUtil
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

	protected List getVOS(final DbColumnDef[] COLS_DEFS, final String sqlQuery,
			final String orderBy, final Class classObjectVO,
			final PageInfo pageInfo) throws TooManyResultsException {

		final SigiaDbOutputPaginatedRecordset rowSet = new SigiaDbOutputPaginatedRecordset(
				COLS_DEFS, classObjectVO, pageInfo);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				int size;

				if (StringUtils.contains(sqlQuery, "GROUP BY"))
					size = DbSelectFns.selectCount(conn, "(" + sqlQuery + ")",
							null);
				else if (StringUtils.contains(sqlQuery, "UNION"))
					size = DbSelectFns.selectCount(conn,
							"(" + sqlQuery + ") b", null);
				else
					size = DbSelectFns.selectCount(conn, sqlQuery, null);
				rowSet.setSize(size);

				new StringBuffer(sqlQuery).append(" " + orderBy).toString();

				if (pageInfo != null) {
					if (size > pageInfo.getMaxNumItems()
							&& pageInfo.getMaxNumItems() >= 0)
						throw new TooManyResultsException(size,
								pageInfo.getMaxNumItems());

					if (size > 0) {
						DbSelectFns.select(conn, sqlQuery, rowSet, pageInfo);
					}
				} else {
					if (size > 0) {
						DbSelectFns.select(conn, sqlQuery, rowSet);
					}
				}
			}
		};

		command.executeWithMaxNumResults();

		return rowSet;
	}

	/**
	 * Genera de forma automatica los autoColumnsDef
	 * 
	 * @param tableDef
	 * @return
	 */
	public AutoDbColumnDef[] generateAutoColumnsDefs(Connection conn,
			DbTableDef tableDef) {

		List auxRet = new ArrayList();

		try {
			// obtencion de metadata de las columnas de la tabla
			ResultSet rs = getColumns(conn, tableDef.getName());
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				// busqueda de columna por nombre
				DbColumnDef colDef = new AutoDbColumnDef(null, tableDef,
						columnName);
				colDef.setDataType(getDataType(rs.getInt("DATA_TYPE")));
				colDef.setMaxLen(rs.getInt("COLUMN_SIZE"));
				colDef.setIsNullable(rs.getBoolean("NULLABLE"));
				auxRet.add(colDef);
			}

		} catch (SQLException e) {
			logger.error(e);
		}

		return (AutoDbColumnDef[]) auxRet.toArray(new AutoDbColumnDef[auxRet
				.size()]);

	}

}
