package salas.db;

import gcontrol.db.ArchivoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.List;

import salas.EstadoMesa;
import salas.vos.EdificioVO;
import salas.vos.ElementoNavegacionVO;
import salas.vos.MesaVO;

import common.Constants;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class EdificioDBEntityImpl extends DBEntity implements IEdificioDBEntity {

	/**
	 * Nombre de la Tabla
	 */
	public static final String TABLE_NAME = "ASGSEDIFICIO";

	/**
	 * Identificador
	 */
	public static String ID_COLUMN_NAME = "ID";

	/**
	 * Nombre &uacute;nico en el sistema
	 */
	public static String NOMBRE_COLUMN_NAME = "NOMBRE";

	/**
	 * Direcci&oacute;n del edificio
	 */
	public static String UBICACION_COLUMN_NAME = "UBICACION";

	/**
	 * Identificador del archivo responsable de las salas de este edificio
	 */
	public static String IDARCHIVO_COLUMN_NAME = "IDARCHIVO";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NOMBRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef UBICACION_FIELD = new DbColumnDef(null,
			TABLE_NAME, UBICACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, true);

	public static final DbColumnDef IDARCHIVO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD, NOMBRE_FIELD,
			UBICACION_FIELD, IDARCHIVO_FIELD };

	public static final DbColumnDef[] TABLE_COLUMNS_UPDATE = { NOMBRE_FIELD,
			UBICACION_FIELD, IDARCHIVO_FIELD };

	DbColumnDef columnaNombreArchivo = new DbColumnDef("nombreArchivo",
			new TableDef(ArchivoDbEntityImpl.TABLE_NAME,
					ArchivoDbEntityImpl.TABLE_NAME),
			ArchivoDbEntityImpl.NOMBRE_COLUMN_NAME,
			ArchivoDbEntityImpl.NOMBRE_FIELD.getDataType(), true);

	DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils.concat(
			TABLE_COLUMNS, new DbColumnDef[] { columnaNombreArchivo });

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * @param dataSource
	 */
	public EdificioDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public EdificioDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#deleteEdificio(java.lang.String)
	 */
	public void deleteEdificio(String idEdificio) {
		deleteVO(getQualById(idEdificio), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#getEdificioById(java.lang.String)
	 */
	public EdificioVO getEdificioById(String idEdificio) {
		String qual = getQualById(idEdificio);
		return (EdificioVO) getVO(qual, getJoinCondition(), COLS_DEF_QUERY,
				EdificioVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#getEdificioByNombre(java.lang.String)
	 */
	public EdificioVO getEdificioByNombre(String nombre) {
		String qual = getQualByNombre(nombre);
		return (EdificioVO) getVO(qual, getJoinCondition(), COLS_DEF_QUERY,
				EdificioVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#insertEdificio(salas.vos.EdificioVO)
	 */
	public void insertEdificio(EdificioVO edificioVO) {
		edificioVO.setId(getGuid(edificioVO.getId()));
		insertVO(TABLE_NAME, TABLE_COLUMNS, edificioVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#updateEdificio(salas.vos.EdificioVO)
	 */
	public void updateEdificio(EdificioVO edificioVO) {
		updateVO(getQualById(edificioVO.getId()), TABLE_NAME,
				TABLE_COLUMNS_UPDATE, edificioVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#getEdificios(java.lang.String[])
	 */
	public List getEdificiosByIdsArchivo(String[] idsArchivo) {
		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			StringBuffer qual = new StringBuffer().append(
					getQualByIdsArchivo(idsArchivo))
					.append(getDefaultOrderBy());
			return getDistinctVOS(qual.toString(), getJoinCondition(),
					COLS_DEF_QUERY, EdificioVO.class);
		}
		return null;
	}

	private String getQualById(String idEdificio) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idEdificio));

		return qual.toString();
	}

	private String getQualByNombre(String nombre) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(NOMBRE_FIELD, nombre));

		return qual.toString();
	}

	private String getQualByIdsArchivo(String[] idsArchivo) {
		StringBuffer qual = new StringBuffer();

		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateORTokens(IDARCHIVO_FIELD, idsArchivo));
		}

		return qual.toString();
	}

	private DbColumnDef getColumnaNumHijos(String salasConEquipoInformatico) {
		StringBuffer subQuery = new StringBuffer();
		subQuery.append("(")
				.append(DBUtils.SELECT)
				.append(DBUtils.COUNT_ALL)
				.append(DBUtils.FROM)
				.append(MesaDBEntityImpl.TABLE_NAME)
				.append(",")
				.append(SalaDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(
						SalaDBEntityImpl.ID_FIELD, MesaDBEntityImpl.SALA_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						MesaDBEntityImpl.ESTADO_FIELD, EstadoMesa.LIBRE));
		if (StringUtils.isNotEmpty(salasConEquipoInformatico)) {
			subQuery.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(
							SalaDBEntityImpl.EQUIPOINFORMATICO_FIELD,
							salasConEquipoInformatico));
		}
		subQuery.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(
						SalaDBEntityImpl.EDIFICIO_FIELD, ID_FIELD)).append(")");

		DbColumnDef columnaNumHijos = new DbColumnDef("NumHijos",
				subQuery.toString(), DbDataType.SHORT_INTEGER);
		return columnaNumHijos;
	}

	private String getQualByNumHijos(String idArchivo) {
		/*
		 * SELECT ASGSEDIFICIO.ID, ASGSEDIFICIO.NOMBRE, (SELECT COUNT(*) FROM
		 * ASGSMESA ASGSMESA,ASGSSALA ASGSSALA WHERE ASGSMESA.IDSALA =
		 * ASGSSALA.ID AND ASGSMESA.ESTADO='L' AND
		 * ASGSSALA.EQUIPOINFORMATICO='equipoInformatico' AND
		 * ASGSSALA.IDEDIFICIO = ASGSEDIFICIO.ID ) HIJOSEDIFICIO FROM
		 * ASGSEDIFICIO ASGSEDIFICIO WHERE ASGSEDIFICIO.IDARCHIVO='idArchivo'
		 * GROUP BY ASGSEDIFICIO.ID, ASGSEDIFICIO.NOMBRE ORDER BY
		 * ASGSEDIFICIO.NOMBRE
		 */
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(IDARCHIVO_FIELD, idArchivo))
				.append(DBUtils.GROUPBY).append(ID_FIELD.getQualifiedName())
				.append(", ").append(NOMBRE_FIELD.getQualifiedName());

		return qual.toString();
	}

	private String getJoinCondition() {
		/*
		 * SELECT ASGSEDIFICIO.*, AGARCHIVO.NOMBRE FROM ASGSEDIFICIO LEFT OUTER
		 * JOIN AGARCHIVO ON AGARCHIVO.ID = ASGSEDIFICIO.IDARCHIVO;
		 */
		JoinDefinition join = new JoinDefinition(IDARCHIVO_FIELD,
				ArchivoDbEntityImpl.ID_FIELD);
		StringBuffer fromSql = new StringBuffer().append(DBUtils
				.generateLeftOuterJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { join }));

		return fromSql.toString();
	}

	private String getQualByEdificioArchivoEquipoInformatico(String idEdificio,
			String idArchivo, String equipoInformatico) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idEdificio));
		if (StringUtils.isNotEmpty(equipoInformatico)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(
							SalaDBEntityImpl.EQUIPOINFORMATICO_FIELD,
							equipoInformatico));
		}
		qual.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(IDARCHIVO_FIELD, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						MesaDBEntityImpl.ESTADO_FIELD, EstadoMesa.LIBRE))
				.append(DBUtils.ORDER_BY).append(MesaDBEntityImpl.SALA_FIELD)
				.append(Constants.COMMA)
				.append(MesaDBEntityImpl.NUMORDEN_FIELD);

		return qual.toString();
	}

	private String getJoinConditionMesasLibres() {
		JoinDefinition joinEdificio = new JoinDefinition(ID_FIELD,
				SalaDBEntityImpl.EDIFICIO_FIELD);
		JoinDefinition joinMesa = new JoinDefinition(SalaDBEntityImpl.ID_FIELD,
				MesaDBEntityImpl.SALA_FIELD);
		StringBuffer fromSql = new StringBuffer().append(DBUtils
				.generateLeftOuterJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { joinEdificio, joinMesa }));

		return fromSql.toString();
	}

	private String getDefaultOrderBy() {
		return DBUtils.generateOrderBy(NOMBRE_FIELD);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#countEdificiosByIdArchivo(java.lang.String)
	 */
	public int getCountEdificiosByIdArchivo(String idArchivo) {
		if (StringUtils.isNotEmpty(idArchivo)) {
			String[] idsArchivo = new String[] { idArchivo };
			return getVOCount(getQualByIdsArchivo(idsArchivo), TABLE_NAME);
		} else
			return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#getEdificios()
	 */
	public List getEdificios() {
		String qual = getDefaultOrderBy();
		return getDistinctVOS(qual.toString(), getJoinCondition(),
				COLS_DEF_QUERY, EdificioVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#getMesasLibresEdificio(java.lang.String,
	 *      java.lang.String)
	 */
	public List getMesasLibresEdificio(String idArchivo,
			String salasConEquipoInformatico) {
		DbColumnDef[] cols = new DbColumnDef[] { ID_FIELD, NOMBRE_FIELD,
				getColumnaNumHijos(salasConEquipoInformatico) };
		StringBuffer qual = new StringBuffer(getQualByNumHijos(idArchivo))
				.append(getDefaultOrderBy());
		return getVOS(qual.toString(), TABLE_NAME, cols,
				ElementoNavegacionVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IEdificioDBEntity#getMesasLibresByEdificio(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List getMesasLibresByEdificio(final String idArchivo,
			final String idSala, final String salasConEquipoInformatico) {
		/*
		 * SELECT ASGSMESA.* FROM ASGSEDIFICIO LEFT OUTER JOIN ASGSSALA ON
		 * ASGSSALA.IDEDIFICIO=ASGSEDIFICIO.ID LEFT OUTER JOIN ASGSMESA ON
		 * ASGSMESA.IDSALA = ASGSSALA.ID WHERE ASGSEDIFICIO.ID='idEdificio' AND
		 * ASGSMESA.ESTADO='L' AND
		 * ASGSSALA.EQUIPOINFORMATICO='salaConEquipoInformatico' AND
		 * ASGSEDIFICIO.IDARCHIVO='idArchivo' ORDER BY
		 * ASGSSALA.NOMBRE,ASGSMESA.NUMORDEN
		 */
		String qual = getQualByEdificioArchivoEquipoInformatico(idSala,
				idArchivo, salasConEquipoInformatico);
		return getVOS(qual, getJoinConditionMesasLibres(),
				MesaDBEntityImpl.TABLE_COLUMNS, MesaVO.class);
	}
}