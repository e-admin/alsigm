package solicitudes.consultas.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import solicitudes.consultas.vos.TemaVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

/**
 * Clase abstracta se encarga de la persistencia de la tabla Tema
 */
public abstract class TemaDBEntity extends DBEntity implements ITemaDBEntity {

	/** Tipo de entidades */
	public static final String TIPO_ENTIDAD_INVESTIGADOR = "1";
	public static final String TIPO_ENTIDAD_CIUDADANO = "2";
	public static final String TIPO_ENTIDAD_ORGANO_EXTERNO = "3";

	/** Tipo de entidades */
	public static final int TIPO_ENTIDAD_INVESTIGADOR_INT = 1;
	public static final int TIPO_ENTIDAD_CIUDADANO_INT = 2;
	public static final int TIPO_ENTIDAD_ORGANO_EXTERNO_INT = 3;

	public static final String TABLE_NAME = "ASGPTEMA";

	public static final String IDUSUARIO = "IDUSUARIO";

	public static final String TIPOENTIDAD = "TIPOENTIDAD";

	public static final String TEMA = "TEMA";

	public static final String IDUSRCSALA = "IDUSRCSALA";

	public static final DbColumnDef CAMPO_IDUSUARIO = new DbColumnDef(
			IDUSUARIO, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPOENTIDAD = new DbColumnDef(
			TIPOENTIDAD, DbDataType.LONG_INTEGER, 2, false);

	public static final DbColumnDef CAMPO_TEMA = new DbColumnDef(TEMA,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_IDUSRCSALA = new DbColumnDef(
			IDUSRCSALA, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_IDUSUARIO,
			CAMPO_TIPOENTIDAD, CAMPO_TEMA, CAMPO_IDUSRCSALA };

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

	/**
	 * Constrcutor por defecto
	 * 
	 * @param dataSource
	 */
	public TemaDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public TemaDBEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene todos los temas existentes para un determinado tipo de entidad
	 * 
	 * @param tipo
	 *            Tipo de la entidad de la que deseamos obtener los temas: -1
	 *            Investigador -2 Ciudadano -3 Organo Externo
	 * @return Listado de los temas
	 */
	public List getTemasTipoEntidad(String tipo) {
		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOENTIDAD, tipo))
				.append(getDefaultOrderBy()).toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, TemaVO.class);
	}

	public List getTemasUsuario(String id) {
		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSUARIO, id))
				.append(getDefaultOrderBy()).toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, TemaVO.class);
	}

	public List getTemasUsuarioSala(String idUsrCSala) {
		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(" ((")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRCSALA,
						idUsrCSala))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOENTIDAD,
						TIPO_ENTIDAD_INVESTIGADOR))
				.append(")")

				.append(DBUtils.OR)

				.append(" (")
				.append(DBUtils.generateIsNullCondition(getConnection(),
						CAMPO_IDUSRCSALA))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOENTIDAD,
						TIPO_ENTIDAD_INVESTIGADOR))
				.append("))")

				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						CAMPO_IDUSUARIO)).append(getDefaultOrderBy());

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				TemaVO.class);

	}

	public List getTemasUsuarioInvestigador(String idUsuarioInvestigador) {
		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(" ((")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSUARIO,
						idUsuarioInvestigador))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOENTIDAD,
						TIPO_ENTIDAD_INVESTIGADOR))
				.append(")")

				.append(DBUtils.OR)

				.append(" (")
				.append(DBUtils.generateIsNullCondition(getConnection(),
						CAMPO_IDUSUARIO))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOENTIDAD,
						TIPO_ENTIDAD_INVESTIGADOR))
				.append("))")

				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						CAMPO_IDUSRCSALA)).append(getDefaultOrderBy());

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				TemaVO.class);

	}

	public void insertTema(final TemaVO tema) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, tema);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}

		};

		command.execute();
	}

	private String getDefaultOrderBy() {
		return DBUtils.generateOrderBy(CAMPO_TEMA);
	}

}
