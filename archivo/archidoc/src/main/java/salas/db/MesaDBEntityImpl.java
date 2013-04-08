package salas.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import salas.EstadoMesa;
import salas.vos.ElementoNavegacionVO;
import salas.vos.MesaVO;

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
public class MesaDBEntityImpl extends DBEntity implements IMesaDBEntity {

	public static final String TABLE_NAME = "ASGSMESA";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String CODIGO_COLUMN_NAME = "CODIGO";
	public static final String NUMORDEN_COLUMN_NAME = "NUMORDEN";
	public static final String SALA_COLUMN_NAME = "IDSALA";
	public static final String ESTADO_COLUMN_NAME = "ESTADO";
	public static final String FECHAESTADO_COLUMN_NAME = "FECHAESTADO";
	public static final String USUARIO_CONSULTA_COLUMN_NAME = "IDUSRCSALA";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CODIGO_FIELD = new DbColumnDef(null,
			TABLE_NAME, CODIGO_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef NUMORDEN_FIELD = new DbColumnDef(null,
			TABLE_NAME, NUMORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef SALA_FIELD = new DbColumnDef(null,
			TABLE_NAME, SALA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef ESTADO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef FECHAESTADO_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHAESTADO_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef USUARIO_CONSULTA_FIELD = new DbColumnDef(
			null, TABLE_NAME, USUARIO_CONSULTA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD, CODIGO_FIELD,
			NUMORDEN_FIELD, SALA_FIELD, ESTADO_FIELD, FECHAESTADO_FIELD,
			USUARIO_CONSULTA_FIELD };

	public static final DbColumnDef[] TABLE_COLUMNS_UPDATE = { CODIGO_FIELD,
			NUMORDEN_FIELD, ESTADO_FIELD, FECHAESTADO_FIELD,
			USUARIO_CONSULTA_FIELD };

	DbColumnDef columnaNombreUsuario = new DbColumnDef("nombreUsuario",
			new TableDef(UsuarioSalaConsultaDBEntityImpl.TABLE_NAME,
					UsuarioSalaConsultaDBEntityImpl.TABLE_NAME),
			UsuarioSalaConsultaDBEntityImpl.NOMBRE_COLUMN_NAME,
			UsuarioSalaConsultaDBEntityImpl.NOMBRE_FIELD.getDataType(), true);

	DbColumnDef columnaNombreApellidos = new DbColumnDef("apellidosUsuario",
			new TableDef(UsuarioSalaConsultaDBEntityImpl.TABLE_NAME,
					UsuarioSalaConsultaDBEntityImpl.TABLE_NAME),
			UsuarioSalaConsultaDBEntityImpl.APELLIDOS_COLUMN_NAME,
			UsuarioSalaConsultaDBEntityImpl.APELLIDOS_FIELD.getDataType(), true);

	DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils.concat(
			TABLE_COLUMNS, new DbColumnDef[] { columnaNombreUsuario,
					columnaNombreApellidos });

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

	private String getQualById(String idMesa) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idMesa));

		return qual.toString();
	}

	private String getQualByIdSala(String idSala) {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(SALA_FIELD, idSala));
		return qual.toString();
	}

	private String getQualByIdSalaAndCodigo(String idSala, String codigo) {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(SALA_FIELD, idSala))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CODIGO_FIELD, codigo));

		return qual.toString();
	}

	private String getQualByIdSalaAndEstados(String idSala, String[] estados) {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(SALA_FIELD, idSala))
				.append(DBUtils.AND)
				.append(DBUtils.generateORTokens(ESTADO_FIELD, estados));

		return qual.toString();
	}

	private String getQualByIdsMesa(String[] idsMesa) {
		StringBuffer qual = new StringBuffer();
		if (ArrayUtils.isNotEmpty(idsMesa)) {
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateORTokens(ID_FIELD, idsMesa));
		}
		return qual.toString();
	}

	/**
	 * Obtiene la subconsulta para cruzar con edificio.
	 * 
	 * @param idEdificio
	 * @param estados
	 * @return
	 */
	private String getQualByIdEdificio(String idEdificio, String[] estados) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotEmpty(idEdificio)) {
			String subquery = new StringBuffer(DBUtils.SELECT)
					.append(SalaDBEntityImpl.ID_FIELD.getQualifiedName())
					.append(DBUtils.FROM)
					.append(SalaDBEntityImpl.TABLE_NAME)
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(
							SalaDBEntityImpl.EDIFICIO_FIELD, idEdificio))
					.toString();
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateInTokenFieldSubQuery(SALA_FIELD, subquery));

			if (ArrayUtils.isNotEmpty(estados)) {
				qual.append(DBUtils.AND).append(
						DBUtils.generateInTokenField(ESTADO_FIELD, estados));
			}
		}

		return qual.toString();
	}

	private String getQualByIdSalaAndIdsMesa(String idSala, String[] idsMesa) {
		StringBuffer qual = new StringBuffer();

		qual.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(SALA_FIELD, idSala))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ID_FIELD, idsMesa));

		return qual.toString();
	}

	private String getQualByIdUsrCSalaArch(String idUsuario, String[] idsArchivo) {
		StringBuffer qual = new StringBuffer();

		qual.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(USUARIO_CONSULTA_FIELD,
						idUsuario))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						EstadoMesa.OCUPADA))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(
						EdificioDBEntityImpl.IDARCHIVO_FIELD, idsArchivo));

		return qual.toString();
	}

	private String getQualByIdUsrCSala(String idUsuario) {
		StringBuffer qual = new StringBuffer();

		qual.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(USUARIO_CONSULTA_FIELD,
						idUsuario));

		return qual.toString();
	}

	private String getJoinCondition() {
		/*
		 * SELECT ASGSMESA.*, ASGSUSRCSALA.NOMBRE, ASGSUSRCSALA.APELLIDO2 FROM
		 * ASGSMESA LEFT OUTER JOIN ASGSUSRCSALA ON ASGSMESA.IDUSRCSALA =
		 * ASGSUSRCSALA.ID;
		 */

		JoinDefinition join = new JoinDefinition(USUARIO_CONSULTA_FIELD,
				UsuarioSalaConsultaDBEntityImpl.ID_FIELD);

		StringBuffer fromSql = new StringBuffer().append(DBUtils
				.generateLeftOuterJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { join }));

		return fromSql.toString();
	}

	private String getDefaultOrderBy() {
		return DBUtils.generateOrderBy(NUMORDEN_FIELD);
	}

	/**
	 * @param dataSource
	 */
	public MesaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public MesaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#insertMesa(salas.vos.MesaVO)
	 */
	public void insertMesa(MesaVO mesaVO) {
		mesaVO.setId(getGuid(mesaVO.getId()));
		insertVO(TABLE_NAME, TABLE_COLUMNS, mesaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#deleteMesa(java.lang.String)
	 */
	public void deleteMesa(String idMesa) {
		if (StringUtils.isNotEmpty(idMesa)) {
			deleteVO(getQualById(idMesa), TABLE_NAME);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#updateMesa(salas.vos.MesaVO)
	 */
	public void updateMesa(MesaVO mesaVO) {
		updateVO(getQualById(mesaVO.getId()), TABLE_NAME, TABLE_COLUMNS_UPDATE,
				mesaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMesaById(java.lang.String)
	 */
	public MesaVO getMesaById(String idMesa) {
		if (StringUtils.isNotEmpty(idMesa)) {
			return (MesaVO) getVO(getQualById(idMesa), TABLE_NAME,
					TABLE_COLUMNS, MesaVO.class);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMesasById(java.lang.String[])
	 */
	public List getMesasById(String[] idsMesa) {
		if (ArrayUtils.isNotEmpty(idsMesa)) {
			StringBuffer qual = new StringBuffer().append(
					getQualByIdsMesa(idsMesa)).append(getDefaultOrderBy());
			return getDistinctVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
					MesaVO.class);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMesas(java.lang.String)
	 */
	public List getMesas(String idSala) {
		if (StringUtils.isNotEmpty(idSala)) {
			StringBuffer qual = new StringBuffer().append(
					getQualByIdSala(idSala)).append(getDefaultOrderBy());
			return getDistinctVOS(qual.toString(), getJoinCondition(),
					COLS_DEF_QUERY, MesaVO.class);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#countMesasPorSala(java.lang.String)
	 */
	public int countMesasPorSala(String idSala) {
		if (StringUtils.isNotEmpty(idSala)) {
			return getVOCount(getQualByIdSala(idSala), TABLE_NAME);
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#counMesasBySalaYEstados(java.lang.String,
	 *      java.lang.String[])
	 */
	public int counMesasBySalaYEstados(String idSala, String[] estados) {
		if (StringUtils.isNotEmpty(idSala)) {
			return getVOCount(getQualByIdSalaAndEstados(idSala, estados),
					TABLE_NAME);
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMaxNumOrden(java.lang.String)
	 */
	public int getMaxNumOrden(final String idSala) {
		if (StringUtils.isNotEmpty(idSala)) {
			return getMax(getQualByIdSala(idSala), TABLE_NAME, NUMORDEN_FIELD);
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMesaBySalaAndCodigo(java.lang.String,
	 *      java.lang.String)
	 */
	public MesaVO getMesaBySalaAndCodigo(final String idSala,
			final String codigo) {
		return (MesaVO) getVO(getQualByIdSalaAndCodigo(idSala, codigo),
				TABLE_NAME, TABLE_COLUMNS, MesaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#cambiarEstado(java.lang.String[],
	 *      java.lang.String)
	 */
	public void cambiarEstado(String[] idsMesa, String estadoAEstablecer) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_FIELD, estadoAEstablecer);
		colsToUpdate.put(FECHAESTADO_FIELD, new Date());
		updateFields(getQualByIdsMesa(idsMesa), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#countMesasByEdificiosYEstados(java.lang.String,
	 *      java.lang.String[])
	 */
	public int countMesasByEdificiosYEstados(String idEdificio, String[] estados) {
		if (StringUtils.isNotEmpty(idEdificio)) {
			return getVOCount(getQualByIdEdificio(idEdificio, estados),
					TABLE_NAME);
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#deleteMesas(java.lang.String,
	 *      java.lang.String[])
	 */
	public void deleteMesas(String idSala, String[] idsMesa) {
		if (StringUtils.isNotEmpty(idSala) && idsMesa != null) {
			deleteVO(getQualByIdSalaAndIdsMesa(idSala, idsMesa), TABLE_NAME);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#deleteMesasByIdSala(java.lang.String)
	 */
	public void deleteMesasByIdSala(final String idSala) {
		if (StringUtils.isNotEmpty(idSala)) {
			deleteVO(getQualByIdSala(idSala), TABLE_NAME);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#updateMesa(java.lang.String,
	 *      java.lang.String)
	 */
	public void cambiarCodigo(final String idMesa, final String codigoMesa) {
		if (StringUtils.isNotEmpty(idMesa)
				&& StringUtils.isNotEmpty(codigoMesa)) {
			Map colsToUpdate = new HashMap();
			colsToUpdate.put(CODIGO_FIELD, codigoMesa);
			updateFields(getQualById(idMesa), colsToUpdate, TABLE_NAME);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMesasNavegacion(java.lang.String)
	 */
	public List getMesasNavegacion(final String idSala) {
		DbColumnDef nombreMesa = new DbColumnDef("nombre", CODIGO_FIELD);
		DbColumnDef[] cols = { ID_FIELD, ESTADO_FIELD, nombreMesa };

		if (StringUtils.isNotEmpty(idSala)) {
			StringBuffer qual = new StringBuffer().append(
					getQualByIdSala(idSala)).append(
					DBUtils.generateOrderBy(NUMORDEN_FIELD));
			return getVOS(qual.toString(), TABLE_NAME, cols,
					ElementoNavegacionVO.class);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#updateBloqueo(java.lang.String)
	 */
	public void updateBloqueo(final String idMesa) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ID_FIELD, idMesa);
		updateFields(getQualById(idMesa), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#registrarEntrada(java.lang.String,
	 *      java.lang.String)
	 */
	public int registrarEntrada(String idMesa, String idUsuario) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_FIELD, EstadoMesa.OCUPADA);
		colsToUpdate.put(FECHAESTADO_FIELD, new Date());
		colsToUpdate.put(USUARIO_CONSULTA_FIELD, idUsuario);
		return updateFields(getQualById(idMesa), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#registrarSalida(java.lang.String)
	 */
	public void registrarSalida(String idUsuarioSala) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_FIELD, EstadoMesa.LIBRE);
		colsToUpdate.put(FECHAESTADO_FIELD, new Date());
		colsToUpdate.put(USUARIO_CONSULTA_FIELD, null);
		updateFields(getQualByIdUsrCSala(idUsuarioSala), colsToUpdate,
				TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMesasByIdUsuarioSala(java.lang.String,
	 *      java.lang.String[])
	 */
	public List getMesasByIdUsuarioSalaArchivos(String idUsuario,
			String[] idsArchivo) {
		/**
		 * SELECT * FROM ASGSMESA INNER JOIN ASGSSALA ON
		 * ASGSSALA.ID=ASGSMESA.IDSALA INNER JOIN ASGSEDIFICIO ON
		 * ASGSEDIFICIO.ID=ASGSSALA.IDEDIFICIO WHERE ASGSMESA.ESTADO='O' AND
		 * ASGSMESA.IDUSRCSALA='idUsuario' AND ASGSEDIFICIO.IDARCHIVO
		 * IN('idsArchivo')
		 */
		JoinDefinition joinSala = new JoinDefinition(SALA_FIELD,
				SalaDBEntityImpl.ID_FIELD);
		JoinDefinition joinEdificio = new JoinDefinition(
				SalaDBEntityImpl.EDIFICIO_FIELD, EdificioDBEntityImpl.ID_FIELD);
		StringBuffer fromSql = new StringBuffer().append(DBUtils
				.generateInnerJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { joinSala, joinEdificio }));
		return getVOS(getQualByIdUsrCSalaArch(idUsuario, idsArchivo),
				fromSql.toString(), TABLE_COLUMNS, MesaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IMesaDBEntity#getMesasByIdUsuarioSala(java.lang.String)
	 */
	public List getMesasByIdUsuarioSala(String idUsuario) {
		return getDistinctVOS(getQualByIdUsrCSala(idUsuario), TABLE_NAME,
				TABLE_COLUMNS, MesaVO.class);
	}
}