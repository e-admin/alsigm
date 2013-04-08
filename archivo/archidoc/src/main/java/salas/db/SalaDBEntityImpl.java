package salas.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import salas.EstadoMesa;
import salas.vos.ElementoNavegacionVO;
import salas.vos.MesaVO;
import salas.vos.SalaVO;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class SalaDBEntityImpl extends DBEntity implements ISalaDBEntity {

	public static final String TABLE_NAME = "ASGSSALA";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";
	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";
	public static final String EDIFICIO_COLUMN_NAME = "IDEDIFICIO";
	public static final String EQUIPOINFORMATICO_COLUMN_NAME = "EQUIPOINFORMATICO";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NOMBRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef DESCRIPCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	public static final DbColumnDef EDIFICIO_FIELD = new DbColumnDef(null,
			TABLE_NAME, EDIFICIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef EQUIPOINFORMATICO_FIELD = new DbColumnDef(
			null, TABLE_NAME, EQUIPOINFORMATICO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD, NOMBRE_FIELD,
			DESCRIPCION_FIELD, EDIFICIO_FIELD, EQUIPOINFORMATICO_FIELD };

	public static final DbColumnDef[] TABLE_COLUMNS_UPDATE = { NOMBRE_FIELD,
			DESCRIPCION_FIELD, EQUIPOINFORMATICO_FIELD };

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

	private String getQualById(String idSala) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idSala));

		return qual.toString();
	}

	private String getQualByIdEdificio(String idEdificio) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(idEdificio)) {
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateEQTokenField(EDIFICIO_FIELD, idEdificio));
		}

		return qual.toString();
	}

	private String getQualByNombreAndEdificio(String nombre, String idEdificio) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(NOMBRE_FIELD, nombre))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(EDIFICIO_FIELD, idEdificio));

		return qual.toString();
	}

	private DbColumnDef getColumnaNumHijos() {
		StringBuffer subQuery = new StringBuffer();
		subQuery.append("(")
				.append(DBUtils.SELECT)
				.append(DBUtils.COUNT_ALL)
				.append(DBUtils.FROM)
				.append(MesaDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateJoinCondition(ID_FIELD,
						MesaDBEntityImpl.SALA_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						MesaDBEntityImpl.ESTADO_FIELD, EstadoMesa.LIBRE))
				.append(")");

		DbColumnDef columnaNumHijos = new DbColumnDef("NumHijos",
				subQuery.toString(), DbDataType.SHORT_INTEGER);
		return columnaNumHijos;
	}

	private String getQualByNumHijos(String idEdificio,
			String salasConEquipoInformatico) {
		/*
		 * SELECT ASGSSALA.ID, ASGSSALA.NOMBRE, (SELECT COUNT(*) FROM ASGSMESA
		 * ASGSMESA WHERE ASGSMESA.IDSALA = ASGSSALA.ID AND ASGSMESA.ESTADO='L'
		 * ) HIJOSSALA FROM ASGSSALA ASGSSALA WHERE
		 * ASGSSALA.IDEDIFICIO='idEdificio' AND
		 * ASGSSALA.EQUIPOINFORMATICO='salasConEquipoInformatico' GROUP BY
		 * ASGSSALA.ID, ASGSSALA.NOMBRE ORDER BY ASGSSALA.NOMBRE
		 */
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(EDIFICIO_FIELD, idEdificio));
		if (StringUtils.isNotEmpty(salasConEquipoInformatico)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(EQUIPOINFORMATICO_FIELD,
							salasConEquipoInformatico));
		}
		qual.append(DBUtils.GROUPBY).append(ID_FIELD.getQualifiedName())
				.append(", ").append(NOMBRE_FIELD.getQualifiedName());

		return qual.toString();
	}

	private String getQualBySalaArchivoEquipoInformatico(String idSala,
			String idArchivo, String equipoInformatico) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idSala));
		if (StringUtils.isNotEmpty(equipoInformatico)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(EQUIPOINFORMATICO_FIELD,
							equipoInformatico));
		}
		qual.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						EdificioDBEntityImpl.IDARCHIVO_FIELD, idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						MesaDBEntityImpl.ESTADO_FIELD, EstadoMesa.LIBRE))
				.append(DBUtils.ORDER_BY)
				.append(MesaDBEntityImpl.NUMORDEN_FIELD);

		return qual.toString();
	}

	private String getJoinConditionMesasLibres() {
		JoinDefinition joinEdificio = new JoinDefinition(EDIFICIO_FIELD,
				EdificioDBEntityImpl.ID_FIELD);
		JoinDefinition joinMesa = new JoinDefinition(ID_FIELD,
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
	 * @param dataSource
	 */
	public SalaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public SalaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#insertSala(salas.vos.SalaVO)
	 */
	public void insertSala(SalaVO salaVO) {
		salaVO.setId(getGuid(salaVO.getId()));
		insertVO(TABLE_NAME, TABLE_COLUMNS, salaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#deleteSala(java.lang.String)
	 */
	public void deleteSala(String idSala) {
		deleteVO(getQualById(idSala), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#updateSala(salas.vos.SalaVO)
	 */
	public void updateSala(SalaVO salaVO) {
		updateVO(getQualById(salaVO.getId()), TABLE_NAME, TABLE_COLUMNS_UPDATE,
				salaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#updateSalaConMesasOcupadas(salas.vos.SalaVO)
	 */
	public void updateSalaConMesasOcupadas(SalaVO salaVO) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(DESCRIPCION_FIELD, salaVO.getDescripcion());
		colsToUpdate
				.put(EQUIPOINFORMATICO_FIELD, salaVO.getEquipoInformatico());
		updateFields(getQualById(salaVO.getId()), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#getSalaById(java.lang.String)
	 */
	public SalaVO getSalaById(String idSala) {
		return (SalaVO) getVO(getQualById(idSala), TABLE_NAME, TABLE_COLUMNS,
				SalaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#getSalasByIdsEdificio(java.lang.String[])
	 */
	public List getSalas(String idEdificio) {
		if (StringUtils.isNotEmpty(idEdificio)) {
			String qual = getQualByIdEdificio(idEdificio) + getDefaultOrderBy();
			return getVOS(qual, TABLE_NAME, TABLE_COLUMNS, SalaVO.class);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#countSalasPorEdificio(java.lang.String)
	 */
	public int countSalasPorEdificio(String idEdificio) {
		if (StringUtils.isNotEmpty(idEdificio)) {
			return getVOCount(getQualByIdEdificio(idEdificio), TABLE_NAME);
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#getSalaByNombreAndEdificio(java.lang.String,
	 *      java.lang.String)
	 */
	public SalaVO getSalaByNombreAndEdificio(final String nombre,
			final String idEdificio) {
		return (SalaVO) getVO(getQualByNombreAndEdificio(nombre, idEdificio),
				TABLE_NAME, TABLE_COLUMNS, SalaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#getMesasLibresSalaByEdificio(java.lang.String,
	 *      java.lang.String)
	 */
	public List getMesasLibresSalaByEdificio(final String idEdificio,
			final String salasConEquipoInformatico) {
		DbColumnDef[] cols = new DbColumnDef[] { ID_FIELD, NOMBRE_FIELD,
				getColumnaNumHijos() };
		StringBuffer qual = new StringBuffer(getQualByNumHijos(idEdificio,
				salasConEquipoInformatico)).append(getDefaultOrderBy());
		return getVOS(qual.toString(), TABLE_NAME, cols,
				ElementoNavegacionVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.ISalaDBEntity#getMesasLibresBySala(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List getMesasLibresBySala(final String idArchivo,
			final String idSala, final String salasConEquipoInformatico) {
		/*
		 * SELECT ASGSMESA.* FROM ASGSSALA LEFT OUTER JOIN ASGSEDIFICIO ON
		 * ASGSEDIFICIO.ID=ASGSSALA.IDEDIFICIO LEFT OUTER JOIN ASGSMESA ON
		 * ASGSMESA.IDSALA = ASGSSALA.ID WHERE ASGSSALA.ID='idSala' AND
		 * ASGSMESA.ESTADO='L' AND
		 * ASGSSALA.EQUIPOINFORMATICO='salaConEquipoInformatico' AND
		 * ASGSEDIFICIO.IDARCHIVO='idArchivo' ORDER BY
		 * ASGSSALA.NOMBRE,ASGSMESA.NUMORDEN
		 */
		String qual = getQualBySalaArchivoEquipoInformatico(idSala, idArchivo,
				salasConEquipoInformatico);
		return getVOS(qual, getJoinConditionMesasLibres(),
				MesaDBEntityImpl.TABLE_COLUMNS, MesaVO.class);
	}
}