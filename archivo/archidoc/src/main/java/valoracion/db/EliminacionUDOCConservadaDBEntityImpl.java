package valoracion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;
import org.apache.log4j.Logger;

import valoracion.vos.EliminacionUDocConservadaVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;

/**
 * Clase que trabaja con trabaja contra la base de datos sobre la tabla de
 * ASGFELIMUDOCCONS.
 */
public class EliminacionUDOCConservadaDBEntityImpl extends DBEntity implements
		IEliminacionUDOCConservadaDBEntity {

	static Logger logger = Logger.getLogger(EliminacionSerieDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGFELIMUDOCCONS";

	private static final String IDELIMINACION_COLUMN_NAME = "IDELIMINACION";
	private static final String IDUDOC_COLUMN_NAME = "IDUDOC";
	private static final String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
	private static final String TITULOUDOC_COLUMN_NAME = "TITULOUDOC";
	private static final String FECHAINIUDOC_COLUMN_NAME = "FECHAINIUDOC";
	private static final String FECHAFINUDOC_COLUMN_NAME = "FECHAFINUDOC";

	public static final DbColumnDef CAMPO_IDELIMINACION = new DbColumnDef(null,
			TABLE_NAME, IDELIMINACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_IDUDOC = new DbColumnDef(null,
			TABLE_NAME, IDUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_SIGNATURAUDOC = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_TITULOUDOC = new DbColumnDef(null,
			TABLE_NAME, TITULOUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024,
			false);
	public static final DbColumnDef CAMPO_FECHAINIUDOC = new DbColumnDef(null,
			TABLE_NAME, FECHAINIUDOC_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef CAMPO_FECHAFINUDOC = new DbColumnDef(null,
			TABLE_NAME, FECHAFINUDOC_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef[] COLS_DEFS = { CAMPO_IDELIMINACION,
			CAMPO_IDUDOC, CAMPO_SIGNATURAUDOC, CAMPO_TITULOUDOC,
			CAMPO_FECHAINIUDOC, CAMPO_FECHAFINUDOC };

	public static final String COLUMN_NAMES = DbUtil.getColumnNames(COLS_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public EliminacionUDOCConservadaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public EliminacionUDOCConservadaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IEliminacionUDOCDBEntity#getUdocsEliminacionConservarXId
	 * (java.lang.String)
	 */
	public List getUdocsEliminacionConservarXId(String idEliminacion) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDELIMINACION, idEliminacion));

		return getVOS(qual.toString(), TABLE_NAME, COLS_DEFS,
				EliminacionUDocConservadaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IEliminacionUDOCDBEntity#getIdsUdocsEliminacionConservarXId
	 * (java.lang.String)
	 */
	public String[] getIdsUdocsEliminacionConservarXId(String idEliminacion) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDELIMINACION, idEliminacion));

		List ids = getVOS(qual.toString(), TABLE_NAME,
				new DbColumnDef[] { new DbColumnDef("value", CAMPO_IDUDOC) },
				MutableObject.class);

		String[] idsArray = new String[ids.size()];
		for (int i = 0; i < ids.size(); i++)
			idsArray[i] = (String) ((Mutable) ids.get(i)).getValue();
		return idsArray;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionUDOCConservadaDBEntity#getSubConsultaIdsUdocsEliminacionConservarXId(java.lang.String)
	 */
	public String getSubConsultaIdsUdocsEliminacionConservarXId(
			String idEliminacion) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDELIMINACION, idEliminacion));

		TableDef tablaElimUdoc = new TableDef(TABLE_NAME, TABLE_NAME);

		return DbSelectStatement.getSelectStmtText(tablaElimUdoc
				.getDeclaration(), DBUtils
				.getQualifiedColumnNames(new DbColumnDef[] { new DbColumnDef(
						"value", CAMPO_IDUDOC) }), qual.toString(), false);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IEliminacionUDOCDBEntity#insertUdocConservar(valoracion
	 * .vos.EliminacionUDocVO)
	 */
	public void insertUdocConservar(
			final EliminacionUDocConservadaVO eliminacionUDocVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, eliminacionUDocVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUMN_NAMES, inputRecord);
			}
		};
		command.execute();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IEliminacionUDOCDBEntity#deleteUdocXIdEliminacion(java.
	 * lang.String, java.lang.String)
	 */
	public void deleteUdocXIdEliminacion(final String idEliminacion,
			final String idUdoc) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(
								CAMPO_IDELIMINACION, idEliminacion))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(CAMPO_IDUDOC,
								idUdoc));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * valoracion.db.IEliminacionUDOCDBEntity#deleteUdocsXIdEliminacion(java
	 * .lang.String, java.lang.String[])
	 */
	public void deleteUdocsXIdEliminacion(final String idEliminacion,
			final String[] idUdocs) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(
								CAMPO_IDELIMINACION, idEliminacion))
						.append(" AND ")
						.append(DBUtils.generateInTokenField(CAMPO_IDUDOC,
								idUdocs));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see valoracion.db.IEliminacionUDOCConservadaDBEntity#deleteUdocsXIdEliminacion(java.lang.String)
	 */
	public void deleteUdocsXIdEliminacion(final String idEliminacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
						.generateEQTokenField(CAMPO_IDELIMINACION,
								idEliminacion));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}
}