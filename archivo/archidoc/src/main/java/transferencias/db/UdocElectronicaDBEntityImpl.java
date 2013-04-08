package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import transferencias.EstadoCotejo;
import transferencias.vos.UDocElectronicaVO;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.ArrayUtils;

public class UdocElectronicaDBEntityImpl extends DBEntity implements
		IUdocElectronicaDBEntity {

	public static final String TABLE_NAME = "ASGTUDOCSDF";

	public static final String ID_COLUMNNAME = "id";
	public static final String IDRELENTREGA_COLUMNNAME = "idrelentrega";
	public static final String POSICION_COLUMNNAME = "posicion";
	public static final String ESTADO_COTEJO_COLUMNNAME = "estadocotejo";
	public static final String NOTAS_COTEJO_COLUMNNAME = "notascotejo";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMNNAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDRELENTREGA_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDRELENTREGA_COLUMNNAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef POSICION_FIELD = new DbColumnDef(null,
			TABLE_NAME, POSICION_COLUMNNAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef ESTADO_COTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COTEJO_COLUMNNAME, DbDataType.LONG_INTEGER, 64,
			false);

	public static final DbColumnDef NOTAS_COTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOTAS_COTEJO_COLUMNNAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			IDRELENTREGA_FIELD, POSICION_FIELD, ESTADO_COTEJO_FIELD,
			NOTAS_COTEJO_FIELD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	private static final DbColumnDef[] COLS_TO_SELECT = (DbColumnDef[]) ArrayUtils
			.concat(TABLE_COLUMNS, new DbColumnDef[] {
					new DbColumnDef("asunto",
							UDocRelacionDBEntityImpl.ASUNTO_FIELD),
					new DbColumnDef("expediente",
							UDocRelacionDBEntityImpl.NUMERO_EXPEDIENTE_FIELD),
					new DbColumnDef("fechaInicio",
							UDocRelacionDBEntityImpl.FECHA_INICIO_FIELD),
					new DbColumnDef("fechaFin",
							UDocRelacionDBEntityImpl.FECHA_FIN_FIELD) });
	private static String TABLE_SET = ArrayUtils.join(new String[] {
			TABLE_NAME, UDocRelacionDBEntityImpl.TABLE_NAME }, ",");

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public UdocElectronicaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UdocElectronicaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public void insertRow(final UDocElectronicaVO uDocElectronicaVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				// unidadInstalacion.setId(GuidManager.generateGuid(conn));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, uDocElectronicaVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	public boolean checkUdocsElectronicasConErrores(final String idRelacion) {
		int[] estados = new int[] { EstadoCotejo.ERRORES.getIdentificador() };
		int udocsConErrores = countUdocsElectronicasConEstado(idRelacion,
				estados);

		if (udocsConErrores > 0) {
			return true;
		}

		return false;
	}

	public void deleteXId(String id) {
		if (id != null) {
			final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(ID_FIELD, id));
			DBCommand command = new DBCommand(this) {
				public void codeLogic(DbConnection conn) throws Exception {
					DbDeleteFns.delete(getConnection(), TABLE_NAME,
							qual.toString());
				}
			};
			command.execute();
		}
	}

	public void deleteXIdRelacion(String idRelacionEntrega) {
		if (idRelacionEntrega != null) {

			final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
							idRelacionEntrega));
			DBCommand command = new DBCommand(this) {
				public void codeLogic(DbConnection conn) throws Exception {
					DbDeleteFns.delete(getConnection(), TABLE_NAME,
							qual.toString());
				}
			};
			command.execute();
		}

	}

	private List getUnidadesElectronicas(String qual, String orderBy) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(DBUtils.AND);
		else
			whereClause.append(DBUtils.WHERE);
		whereClause.append(DBUtils.generateJoinCondition(ID_FIELD,
				UDocRelacionDBEntityImpl.ID_FIELD));
		if (StringUtils.isNotBlank(orderBy))
			whereClause.append(Constants.STRING_SPACE).append(orderBy);
		return getVOS(whereClause.toString(), TABLE_SET, COLS_TO_SELECT,
				UDocElectronicaVO.class);
	}

	private List getUnidadesElectronicasEntreArhivos(String qual, String orderBy) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(DBUtils.AND);
		else
			whereClause.append(DBUtils.WHERE);

		if (StringUtils.isNotBlank(orderBy)) {
			whereClause.append(Constants.STRING_SPACE).append(orderBy);
		}
		return getVOS(whereClause.toString(), TABLE_NAME, TABLE_COLUMNS,
				UDocElectronicaVO.class);
	}

	public List fetchRowsByIdRelacion(String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDRELENTREGA_FIELD, idRelacion));
		return getUnidadesElectronicas(qual.toString(),
				DBUtils.generateOrderBy(POSICION_FIELD));
	}

	public void updateFieldEstadoYNotas(String idUDocElectronica, int estado,
			String notas) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idUDocElectronica));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));
		colsToUpdate.put(NOTAS_COTEJO_FIELD, notas);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void updateFieldEstado(String idRelacion,
			String[] idsUDocElectronica, int estado) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ID_FIELD,
						idsUDocElectronica));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public int countUdocsElectronicasConEstado(String id, int[] estadoCotejo) {
		// String[] tables = {TABLE_NAME};
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD, id))
				.append(DBUtils.AND)
				.append(DBUtils.generateORTokens(
						UdocEnUIDBEntityImpl.ESTADO_COTEJO_FIELD, estadoCotejo));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public UDocElectronicaVO fetchRowById(String id) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, id));

		// String tables = DBUtils.generateTableSet(new String [] {TABLE_NAME,
		// RelacionEntregaDBEntityBaseImpl.TABLE_NAME});
		return (UDocElectronicaVO) getVO(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, UDocElectronicaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocElectronicaDBEntity#deleteXIds(java.lang.String,
	 * java.lang.String[])
	 */
	public void deleteXIds(String idRelacion, String[] ids) {
		if (ids != null) {
			final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
					.append(DBUtils.generateInTokenField(ID_FIELD, ids));
			DBCommand command = new DBCommand(this) {
				public void codeLogic(DbConnection conn) throws Exception {
					DbDeleteFns.delete(getConnection(), TABLE_NAME,
							qual.toString());
				}
			};
			command.execute();
		}
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocElectronicaDBEntity#countUdocsElectronicasByRelacion
	 * (java.lang.String)
	 */
	public int countUdocsElectronicasByRelacion(String idRelacion) {
		// String[] tables = {TABLE_NAME};
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDRELENTREGA_FIELD, idRelacion));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocElectronicaDBEntity#fetchRowsByIdRelacionEntreArchivos
	 * (java.lang.String)
	 */
	public List fetchRowsByIdRelacionEntreArchivos(String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDRELENTREGA_FIELD, idRelacion));

		return getUnidadesElectronicasEntreArhivos(qual.toString(),
				DBUtils.generateOrderBy(POSICION_FIELD));
	}

	/**
	 * Actualiza la posicion de las unidades documentales electronicas
	 * pertenecientes a la relacion de entrega
	 * 
	 * @param idRelacionEntrega
	 * @param posicion
	 */
	public void incrementPosUdocSDF(String idRelacionEntrega, int posicion,
			int incremento) {
		final StringBuffer query = new StringBuffer(" UPDATE ")
				.append(TABLE_NAME)
				.append(" SET ")
				.append(POSICION_COLUMNNAME)
				.append(" = ")
				.append(POSICION_COLUMNNAME)
				.append(" + ")
				.append(incremento)
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelacionEntrega))
				.append(" AND ")
				.append(DBUtils
						.generateGTEQTokenField(POSICION_FIELD, posicion));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbInputStatement stmt = new DbInputStatement();
				stmt.create(conn, query.toString());
				stmt.execute();
			}
		};
		command.execute();
	}

}
