package fondos.db;

import fondos.model.JerarquiaNivelCF;
import fondos.model.NivelCF;
import fondos.model.TipoNivelCF;
import fondos.vos.INivelCFVO;
import fondos.vos.JerarquiaNivelCFVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputStatement;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.AbstractDbOutputRecordSet;
import common.db.ConstraintType;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbOutputRecordset;
import common.exceptions.DBException;
import common.util.StringUtils;

public class NivelCFDBEntityImpl extends DBEntity implements INivelCFDBEntity {

	public static final String NIVELCF_TABLE_NAME = "ASGFNIVELCF";

	public static final String ID_NIVEL_COLUMN_NAME = "ID";
	public static final String NOMBRE_NIVEL_COLUMN_NAME = "NOMBRE";
	public static final String TIPO_NIVEL_COLUMN_NAME = "TIPO";
	public static final String ID_FICHA_COLUMN_NAME = "IDFICHADESCRPREF";
	public static final String IDFICHACLFDOCPREF_COLUMN_NAME = "IDFICHACLFDOCPREF";
	public static final String IDREPECMPREF_COLUMN_NAME = "IDREPECMPREF";
	public static final String SUBTIPO_NIVEL_COLUMN_NAME = "SUBTIPO";

	public static final DbColumnDef ID_NIVEL_FIELD = new DbColumnDef(null,
			NIVELCF_TABLE_NAME, ID_NIVEL_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);

	public static final DbColumnDef NOMBRE_NIVEL_FIELD = new DbColumnDef(null,
			NIVELCF_TABLE_NAME, NOMBRE_NIVEL_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef TIPO_NIVEL_FIELD = new DbColumnDef(null,
			NIVELCF_TABLE_NAME, TIPO_NIVEL_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef ID_FICHA_FIELD = new DbColumnDef(null,
			NIVELCF_TABLE_NAME, ID_FICHA_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);

	public static final DbColumnDef IDFICHACLFDOCPREF_FIELD = new DbColumnDef(
			null, NIVELCF_TABLE_NAME, IDFICHACLFDOCPREF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDREPECMPREF_FIELD = new DbColumnDef(null,
			NIVELCF_TABLE_NAME, IDREPECMPREF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef SUBTIPO_NIVEL_FIELD = new DbColumnDef(null,
			NIVELCF_TABLE_NAME, SUBTIPO_NIVEL_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef[] TABLA_NIVEL_COLUMNS = { ID_NIVEL_FIELD,
			NOMBRE_NIVEL_FIELD, TIPO_NIVEL_FIELD, ID_FICHA_FIELD,
			IDFICHACLFDOCPREF_FIELD, IDREPECMPREF_FIELD, SUBTIPO_NIVEL_FIELD };

	public static final String NIVEL_COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLA_NIVEL_COLUMNS);

	// JERARQUIA
	public static final String JERARQUIA_NIVELES_TABLE_NAME = "ASGFESTRUCTJNIVCF";

	public static final String NIVEL_PADRE_COLUMN_NAME = "IDNIVELPADRE";
	public static final String TIPO_NIVEL_PADRE_COLUMN_NAME = "TIPONIVELPADRE";
	public static final String NIVEL_HIJO_COLUMN_NAME = "IDNIVELHIJO";
	public static final String TIPO_NIVEL_HIJO_COLUMN_NAME = "TIPONIVELHIJO";

	public static final DbColumnDef NIVEL_PADRE_FIELD = new DbColumnDef(null,
			JERARQUIA_NIVELES_TABLE_NAME, NIVEL_PADRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef TIPO_NIVEL_PADRE_FIELD = new DbColumnDef(
			null, JERARQUIA_NIVELES_TABLE_NAME, TIPO_NIVEL_PADRE_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef NIVEL_HIJO_FIELD = new DbColumnDef(null,
			JERARQUIA_NIVELES_TABLE_NAME, NIVEL_HIJO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef TIPO_NIVEL_HIJO_FIELD = new DbColumnDef(
			null, JERARQUIA_NIVELES_TABLE_NAME, TIPO_NIVEL_HIJO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef[] TABLA_JERARQUIA_NIVEL_COLUMNS = {
			NIVEL_PADRE_FIELD, TIPO_NIVEL_PADRE_FIELD, NIVEL_HIJO_FIELD,
			TIPO_NIVEL_HIJO_FIELD };

	public static final String JERARQUIA_NIVEL_COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLA_JERARQUIA_NIVEL_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return NIVELCF_TABLE_NAME;
	}

	public NivelCFDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public NivelCFDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	private List getNivelesCfVOS(String qual) {
		return getVOS(qual, NIVELCF_TABLE_NAME, TABLA_NIVEL_COLUMNS,
				NivelCF.class);
	}

	private INivelCFVO getNivelCfVO(String qual) {
		return (INivelCFVO) getVO(qual, NIVELCF_TABLE_NAME,
				TABLA_NIVEL_COLUMNS, NivelCF.class);
	}

	private String getDefaultOrder() {
		return DBUtils.generateOrderBy(new DbColumnDef[] { TIPO_NIVEL_FIELD,
				NOMBRE_NIVEL_FIELD });
	}

	public List getNivelesCF(String idNivelPadre) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(NIVELCF_TABLE_NAME,
						ID_NIVEL_FIELD, JERARQUIA_NIVELES_TABLE_NAME,
						NIVEL_HIJO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateTokenFieldQualified(NIVEL_PADRE_FIELD,
						idNivelPadre, JERARQUIA_NIVELES_TABLE_NAME,
						ConstraintType.EQUAL));

		try {
			DbConnection conn = getConnection();
			final ArrayList rows = new ArrayList();
			DbSelectFns
					.select(conn,
							DBUtils.generateTableSet(new String[] {
									JERARQUIA_NIVELES_TABLE_NAME,
									NIVELCF_TABLE_NAME }),
							NIVEL_COLUM_NAMES_LIST, qual.toString(), false,
							new AbstractDbOutputRecordSet() {
								DbOutputRecord aRow = new DbOutputRecord() {
									public void getStatementValues(
											DbOutputStatement stmt)
											throws Exception {
										INivelCFVO unNivel = new NivelCF(null);
										DBUtils.fillVoWithDbOutputStament(
												TABLA_NIVEL_COLUMNS, stmt,
												unNivel);
										rows.add(unNivel);
										JerarquiaNivelesCF.addNivelCF(unNivel);
									}
								};

								public DbOutputRecord newRecord()
										throws Exception {
									return aRow;
								}
							});
			return rows;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public List getNivelesNoSerieCF(String idNivelPadre) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(NIVELCF_TABLE_NAME,
						ID_NIVEL_FIELD, JERARQUIA_NIVELES_TABLE_NAME,
						NIVEL_HIJO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateTokenFieldQualified(NIVEL_PADRE_FIELD,
						idNivelPadre, JERARQUIA_NIVELES_TABLE_NAME,
						ConstraintType.EQUAL))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(TIPO_NIVEL_FIELD,
						TipoNivelCF.SERIE.getIdentificador()));

		try {
			DbConnection conn = getConnection();
			final ArrayList rows = new ArrayList();
			DbSelectFns
					.select(conn,
							DBUtils.generateTableSet(new String[] {
									JERARQUIA_NIVELES_TABLE_NAME,
									NIVELCF_TABLE_NAME }),
							NIVEL_COLUM_NAMES_LIST, qual.toString(), false,
							new AbstractDbOutputRecordSet() {
								DbOutputRecord aRow = new DbOutputRecord() {
									public void getStatementValues(
											DbOutputStatement stmt)
											throws Exception {
										INivelCFVO unNivel = new NivelCF(null);
										DBUtils.fillVoWithDbOutputStament(
												TABLA_NIVEL_COLUMNS, stmt,
												unNivel);
										rows.add(unNivel);
										JerarquiaNivelesCF.addNivelCF(unNivel);
									}
								};

								public DbOutputRecord newRecord()
										throws Exception {
									return aRow;
								}
							});
			return rows;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public List getNivelesCF(int tipoNivel, String idNivelPadre) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(TIPO_NIVEL_FIELD, tipoNivel));
		String[] tableSet = { NIVELCF_TABLE_NAME };
		if (StringUtils.isNotBlank(idNivelPadre)) {
			qual.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(NIVELCF_TABLE_NAME,
							ID_NIVEL_FIELD, JERARQUIA_NIVELES_TABLE_NAME,
							NIVEL_HIJO_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateTokenFieldQualified(
							NIVEL_PADRE_FIELD, idNivelPadre,
							JERARQUIA_NIVELES_TABLE_NAME, ConstraintType.EQUAL));

			tableSet = new String[] { NIVELCF_TABLE_NAME,
					JERARQUIA_NIVELES_TABLE_NAME };
		}

		try {
			DbConnection conn = getConnection();
			final ArrayList rows = new ArrayList();
			DbSelectFns.select(conn, DBUtils.generateTableSet(tableSet),
					NIVEL_COLUM_NAMES_LIST, qual.toString(), false,
					new AbstractDbOutputRecordSet() {
						DbOutputRecord aRow = new DbOutputRecord() {
							public void getStatementValues(
									DbOutputStatement stmt) throws Exception {
								INivelCFVO unNivel = new NivelCF(null);
								DBUtils.fillVoWithDbOutputStament(
										TABLA_NIVEL_COLUMNS, stmt, unNivel);
								rows.add(unNivel);
								JerarquiaNivelesCF.addNivelCF(unNivel);
							}
						};

						public DbOutputRecord newRecord() throws Exception {
							return aRow;
						}
					});
			return rows;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public INivelCFVO getNivelCF(String idNivel) {
		INivelCFVO nivelCF = JerarquiaNivelesCF.getNivel(idNivel);
		if (nivelCF != null)
			return nivelCF;

		try {

			StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(ID_NIVEL_FIELD,
							idNivel));
			final NivelCF nivel = new NivelCF(idNivel);
			DbConnection conn = getConnection();
			DbSelectFns.select(conn, NIVELCF_TABLE_NAME,
					NIVEL_COLUM_NAMES_LIST, qual.toString(),
					new DbOutputRecord() {
						public void getStatementValues(DbOutputStatement stmt)
								throws Exception {
							DBUtils.fillVoWithDbOutputStament(
									TABLA_NIVEL_COLUMNS, stmt, nivel);
						}
					});
			JerarquiaNivelesCF.addNivelCF(nivel);
			return nivel;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	static Map jerarquiaNiveles = new HashMap();

	public List getNivelesPadre(String idNivel, int tipoNivel) {
		SigiaDbOutputRecordset output = new SigiaDbOutputRecordset(
				TABLA_NIVEL_COLUMNS, NivelCF.class);
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(NIVEL_HIJO_FIELD, idNivel))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(TIPO_NIVEL_HIJO_FIELD,
						tipoNivel))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(NIVEL_PADRE_FIELD,
						ID_NIVEL_FIELD));

		try {
			DbConnection conn = getConnection();
			DbSelectFns
					.select(conn,
							DBUtils.generateTableSet(new String[] {
									JERARQUIA_NIVELES_TABLE_NAME,
									NIVELCF_TABLE_NAME }),
							NIVEL_COLUM_NAMES_LIST, qual.toString(), false,
							output);
			return output;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	/**
	 * Obtiene la lista de niveles de descripción (excepto los clasificadores de
	 * fondos).
	 *
	 * @return Lista de niveles de descripción.
	 */
	public List getNivelesCF() {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(TIPO_NIVEL_FIELD.getName()).append(">1")
				.append(getDefaultOrder());

		return getNivelesCfVOS(qual.toString());
	}

	/**
	 * Obtiene la lista de niveles de descripción (incluidos los clasificadores
	 * de fondos).
	 *
	 * @return Lista de niveles de descripción.
	 */
	public List getAllNivelesCF() {
		StringBuffer qual = new StringBuffer().append(DBUtils
				.generateOrderBy(new DbColumnDef[] { TIPO_NIVEL_FIELD,
						NOMBRE_NIVEL_FIELD }));

		return getNivelesCfVOS(qual.toString());
	}

	public INivelCFVO getNivelCF(int tipo, int subtipo) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(TIPO_NIVEL_FIELD, tipo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SUBTIPO_NIVEL_FIELD,
						subtipo));

		return getNivelCfVO(qual.toString());
	}

	public INivelCFVO getNivelCFById(String idNivel) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_NIVEL_FIELD, idNivel));

		return getNivelCfVO(qual.toString());
	}

	public INivelCFVO getNivelCFByName(String nombreNivel) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(NOMBRE_NIVEL_FIELD, nombreNivel));

		return getNivelCfVO(qual.toString());
	}

	public INivelCFVO getNivelCFByNameAndTipo(String nombreNivel, int tipoNivel) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(NOMBRE_NIVEL_FIELD,
						nombreNivel))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(TIPO_NIVEL_FIELD,
						tipoNivel));

		return getNivelCfVO(qual.toString());
	}

	public List getNivelesCFByTipo(int[] tiposHijo, List nivelesHijo) {

		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE);

		qual.append(DBUtils.generateInTokenField(TIPO_NIVEL_FIELD, tiposHijo));

		if (!nivelesHijo.isEmpty()) {
			qual.append(DBUtils.AND).append(ID_NIVEL_FIELD).append(" NOT IN (");
			for (int i = 0; i < nivelesHijo.size(); i++) {
				qual.append("'").append(((NivelCF) nivelesHijo.get(i)).getId())
						.append("'");
				if (i + 1 < nivelesHijo.size())
					qual.append(",");
			}
			qual.append(")");
		}
		return getDistinctVOS(qual.toString(), NIVELCF_TABLE_NAME,
				TABLA_NIVEL_COLUMNS, NivelCF.class);
	}

	public List getNivelesCFByTipo(int[] tiposNivel, String[] niveles) {

		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE);

		if (tiposNivel != null && tiposNivel.length > 0 && niveles != null
				&& niveles.length > 0) {
			qual.append(DBUtils.generateInTokenField(TIPO_NIVEL_FIELD,
					tiposNivel));
			qual.append(DBUtils.AND);
			qual.append(DBUtils.generateInTokenField(ID_NIVEL_FIELD, niveles));
		}
		return getDistinctVOS(qual.toString(), NIVELCF_TABLE_NAME,
				TABLA_NIVEL_COLUMNS, NivelCF.class);
	}

	public INivelCFVO insertNivel(INivelCFVO nivelCF) {
		nivelCF.setId(getGuid(nivelCF.getId()));
		insertVO(NIVELCF_TABLE_NAME, TABLA_NIVEL_COLUMNS, nivelCF);
		return nivelCF;
	}

	public INivelCFVO updateNivel(INivelCFVO nivelCF) {
		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_NIVEL_FIELD,
						nivelCF.getId()));

		updateVO(qual.toString(), NIVELCF_TABLE_NAME, TABLA_NIVEL_COLUMNS,
				nivelCF);
		return nivelCF;
	}

	public void deleteNivelCF(INivelCFVO nivelCF) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_NIVEL_FIELD,
						nivelCF.getId())).toString();

		deleteVO(qual, NIVELCF_TABLE_NAME);
	}

	public JerarquiaNivelCFVO insertJerarquiaNivelCF(
			JerarquiaNivelCFVO jerarquiaNivelCF) {
		insertVO(JERARQUIA_NIVELES_TABLE_NAME, TABLA_JERARQUIA_NIVEL_COLUMNS,
				jerarquiaNivelCF);
		return jerarquiaNivelCF;
	}

	public void deleteJerarquiaNivelCF(JerarquiaNivelCFVO jerarquiaNivelCF) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(NIVEL_PADRE_FIELD,
						jerarquiaNivelCF.getIdNivelPadre()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(NIVEL_HIJO_FIELD,
						jerarquiaNivelCF.getIdNivelHijo())).toString();

		deleteVO(qual, JERARQUIA_NIVELES_TABLE_NAME);
	}

	public boolean isNivelInicial(String idNivel) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(NIVEL_HIJO_FIELD, idNivel))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(NIVEL_PADRE_FIELD,
						TipoNivelCF.ID_NIVEL_RAIZ)).toString();

		JerarquiaNivelCFVO jerarquiaNivelCF = (JerarquiaNivelCFVO) getVO(qual,
				JERARQUIA_NIVELES_TABLE_NAME, TABLA_JERARQUIA_NIVEL_COLUMNS,
				JerarquiaNivelCF.class);

		if (jerarquiaNivelCF != null)
			return true;
		else
			return false;
	}

	public boolean isNivelHijoNoRaiz(String idNivelHijo) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(NIVEL_HIJO_FIELD,
						idNivelHijo))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(NIVEL_PADRE_FIELD,
						TipoNivelCF.ID_NIVEL_RAIZ)).toString();

		if (getVOCount(qual, JERARQUIA_NIVELES_TABLE_NAME) > 0)
			return false;

		return true;
	}
}