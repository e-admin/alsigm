package docelectronicos.db;

import gcontrol.db.UsuarioDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.exceptions.TooManyResultsException;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.StringUtils;

import docelectronicos.EstadoTareaCaptura;
import docelectronicos.vos.BusquedaTareasVO;
import docelectronicos.vos.DocTCapturaVO;

public class DocTCapturaDBEntityImpl extends DBEntity implements
		IDocTCapturaDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADOCTCAPTURA";
	// ID VARCHAR2 (32) NOT NULL,
	// TIPOOBJ INTEGER NOT NULL,
	// IDOBJ VARCHAR2 (32) NOT NULL,
	// IDUSRCAPTURA VARCHAR2 (32) NOT NULL,
	// CODREFOBJ VARCHAR2 (1024),
	// TITULOOBJ VARCHAR2 (1024) NOT NULL,
	// ESTADO INTEGER NOT NULL,
	// FECHAESTADO DATE NOT NULL,
	// OBSERVACIONES VARCHAR2 (254),
	// MOTIVOERROR VARCHAR2 (254),

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "ID";
	public static final String TIPOOBJ_COLUMN_NAME = "TIPOOBJ";
	public static final String IDOBJ_COLUMN_NAME = "IDOBJ";
	public static final String IDUSRCAPTURA_COLUMN_NAME = "IDUSRCAPTURA";
	public static final String CODREFOBJ_COLUMN_NAME = "CODREFOBJ";
	public static final String TITULOOBJ_COLUMN_NAME = "TITULOOBJ";
	public static final String ESTADO_COLUMN_NAME = "ESTADO";
	public static final String FECHAESTADO_COLUMN_NAME = "FECHAESTADO";
	public static final String OBSERVACIONES_COLUMN_NAME = "OBSERVACIONES";
	public static final String MOTIVOERROR_COLUMN_NAME = "MOTIVOERROR";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPOOBJ = new DbColumnDef(null,
			TABLE_NAME, TIPOOBJ_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_IDOBJ = new DbColumnDef(null,
			TABLE_NAME, IDOBJ_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDUSRCAPTURA = new DbColumnDef(null,
			TABLE_NAME, IDUSRCAPTURA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_CODREFOBJ = new DbColumnDef(null,
			TABLE_NAME, CODREFOBJ_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TITULOOBJ = new DbColumnDef(null,
			TABLE_NAME, TITULOOBJ_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_FECHAESTADO = new DbColumnDef(null,
			TABLE_NAME, FECHAESTADO_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_OBSERVACIONES = new DbColumnDef(null,
			TABLE_NAME, OBSERVACIONES_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_MOTIVOERROR = new DbColumnDef(null,
			TABLE_NAME, MOTIVOERROR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = { CAMPO_ID, CAMPO_TIPOOBJ,
			CAMPO_IDOBJ, CAMPO_IDUSRCAPTURA, CAMPO_CODREFOBJ, CAMPO_TITULOOBJ,
			CAMPO_ESTADO, CAMPO_FECHAESTADO, CAMPO_OBSERVACIONES,
			CAMPO_MOTIVOERROR };

	/** Lista de nombres de columnas. */
	public static final String COL_NAMES = DbUtil.getColumnNames(COL_DEFS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public DocTCapturaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocTCapturaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see docelectronicos.db.IDocTCapturaDBEntity#getTareas()
	 */
	public List fetchTareas() {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		String qualOrder = new StringBuffer().append(" ORDER BY ")
				.append(CAMPO_FECHAESTADO.getQualifiedName()).toString();

		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID)).append(" ")
				.append(qualOrder).toString();

		return getVOS(qual.toString(), pairs, DocTCapturaVO.class);
	}

	public DocTCapturaVO fetchTareasXId(String id) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		return (DocTCapturaVO) getVO(qual, pairs, DocTCapturaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see docelectronicos.db.IDocTCapturaDBEntity#fetchCountTareasXEstados(
	 * docelectronicos.model.EstadoTareaCaptura[])
	 */
	public int fetchCountTareasXEstados(EstadoTareaCaptura[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE 1=1");

		if (!ArrayUtils.isEmpty(estados))
			qual.append(" AND ")
					.append(DBUtils.generateORTokens(CAMPO_ESTADO,
							getArrayEstadosValues(estados))).toString();

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IDocTCapturaDBEntity#fetchTareasXEstados(docelectronicos
	 * .model.EstadoTareaCaptura[])
	 */
	public List fetchTareasXEstados(EstadoTareaCaptura[] estados) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateORTokens(CAMPO_ESTADO,
						getArrayEstadosValues(estados))).toString();

		return getVOS(qual, pairs, DocTCapturaVO.class);
	}

	public List fetchTareasXIds(String[] ids) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID)).append(" AND ")
				.append(DBUtils.generateORTokens(CAMPO_ID, ids)).toString();

		return getVOS(qual, pairs, DocTCapturaVO.class);
	}

	private int[] getArrayEstadosValues(EstadoTareaCaptura[] estadosCapturas) {
		int[] estadosValues = new int[estadosCapturas.length];
		for (int i = 0; i < estadosValues.length; i++) {
			estadosValues[i] = estadosCapturas[i].getValue();
		}
		return estadosValues;
	}

	public int fetchCountTareasXEstadosYUsuario(EstadoTareaCaptura[] estados,
			String idUsuarioCaptura) {

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_ESTADO,
						getArrayEstadosValues(estados)))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRCAPTURA,
						idUsuarioCaptura)).toString();

		return getVOCount(qual, TABLE_NAME);
	}

	public List fetchTareasXEstadosYUsuario(EstadoTareaCaptura[] estados,
			String idUsuarioCaptura) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateORTokens(CAMPO_ESTADO,
						getArrayEstadosValues(estados)))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRCAPTURA,
						idUsuarioCaptura)).toString();

		return getVOS(qual, pairs, DocTCapturaVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IDocTCapturaDBEntity#fetchTareaXId(java.lang.String)
	 */
	public DocTCapturaVO fetchTareaXId(String id) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		return (DocTCapturaVO) getVO(qual, pairs, DocTCapturaVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see docelectronicos.db.IDocTCapturaDBEntity#insert(docelectronicos.vos.
	 * DocTCapturaVO)
	 */
	public DocTCapturaVO insert(DocTCapturaVO tarea) {
		tarea.setId(getGuid(tarea.getId()));
		insertVO(TABLE_NAME, COL_DEFS, tarea);
		return tarea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see docelectronicos.db.IDocTCapturaDBEntity#update(docelectronicos.vos.
	 * DocTCapturaVO)
	 */
	public void update(DocTCapturaVO tarea) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, tarea.getId()))
				.toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, tarea);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see docelectronicos.db.IDocTCapturaDBEntity#removeXId(java.lang.String)
	 */
	public void removeXId(String idTarea) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idTarea))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see docelectronicos.db.IDocTCapturaDBEntity#removeTareasXIdElementoCF()
	 */
	public void removeTareasXIdElementoYTipoObj(String idElemento,
			int idTipoObjeto) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDOBJ, idElemento))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOOBJ,
						idTipoObjeto)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IDocTCapturaDBEntity#getTareas(docelectronicos.vos
	 * .BusquedaTareasVO)
	 */
	public List getTareas(BusquedaTareasVO busquedaVO)
			throws TooManyResultsException {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		// pairs.put(TipoDocumentoVitalDBEntityImpl.TABLE_NAME, new
		// DbColumnDef[] { new DbColumnDef(
		// "nombreTipoDocVit", TipoDocumentoVitalDBEntityImpl.CAMPO_NOMBRE) });
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID));

		// titulo
		if (StringUtils.isNotBlank(busquedaVO.getTitulo())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_TITULOOBJ, busquedaVO
							.getTitulo().toUpperCase()));
		}

		// referencia
		if (StringUtils.isNotBlank(busquedaVO.getReferencia())) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateLikeTokenField(CAMPO_CODREFOBJ, busquedaVO
							.getReferencia().toUpperCase()));
		}

		// Estados
		if (!ArrayUtils.isEmpty(busquedaVO.getEstados())) {
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO,
							busquedaVO.getEstados()));
		}

		// Fecha estado
		if (busquedaVO.getFechaInicioEstado() != null) {
			// qual.append(" AND ")
			// .append(CAMPO_FECHAESTADO.getQualifiedName())
			// .append(">=TO_DATE('")
			// .append(CustomDateFormat.SDF_YYYYMMDD.format(busquedaVO.getFechaInicioEstado()))
			// .append("','")
			// .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
			// .append("')");
			qual.append(" AND ")
					.append(CAMPO_FECHAESTADO.getQualifiedName())
					.append(">=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaVO.getFechaInicioEstado(),
							CustomDateFormat.SDF_YYYYMMDD)).append(" ");

		}

		if (busquedaVO.getFechaFinEstado() != null) {
			// qual.append(" AND ")
			// .append(CAMPO_FECHAESTADO.getQualifiedName())
			// .append("<=TO_DATE('")
			// .append(CustomDateFormat.SDF_YYYYMMDD.format(busquedaVO.getFechaFinEstado()))
			// .append("','")
			// .append(CustomDateFormat.SDF_YYYYMMDD.toPattern())
			// .append("')");
			qual.append(" AND ")
					.append(CAMPO_FECHAESTADO.getQualifiedName())
					.append("<=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaVO.getFechaFinEstado(),
							CustomDateFormat.SDF_YYYYMMDD)).append(" ");

		}

		// Nombre Usuario gestor
		if (StringUtils
				.isNotBlank(busquedaVO.getNombreCompletoUsuarioCaptura()))
			qual.append(" AND UPPER(")
					.append(UsuarioDBEntityImpl.CAMPO_NOMBRE.getQualifiedName())
					// .append("||' '||")
					.append(DBUtils.getNativeConcatSyntax(getConnection()))
					.append("' '")
					.append(DBUtils.getNativeConcatSyntax(getConnection()))
					.append(UsuarioDBEntityImpl.CAMPO_APELLIDOS
							.getQualifiedName())
					.append(") LIKE '%")
					.append(busquedaVO.getNombreCompletoUsuarioCaptura()
							.toUpperCase()).append("%'");

		// id usuasrio gestor
		if (StringUtils.isNotBlank(busquedaVO.getIdUsuarioCaptura()))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(UsuarioDBEntityImpl.CAMPO_ID,
							busquedaVO.getIdUsuarioCaptura()));

		// Ordenación
		if (busquedaVO.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("tipo", CAMPO_TIPOOBJ);
			criteriosOrdenacion.put("titulo", CAMPO_TITULOOBJ);
			criteriosOrdenacion.put("codRefObj", CAMPO_CODREFOBJ);
			criteriosOrdenacion.put("fechaEstado", CAMPO_FECHAESTADO);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("usuario", new DbColumnDef[] {
					UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO,
					UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO });

			return getVOS(qual.toString(),
					busquedaVO.getPageInfo().getOrderBy(criteriosOrdenacion),
					pairs, DocTCapturaVO.class, busquedaVO.getPageInfo());
		} else {
			StringBuffer sbQual = new StringBuffer(qual.toString()).append(
					" ORDER BY ").append(CAMPO_FECHAESTADO.getQualifiedName());

			return getVOS(sbQual.toString(), pairs, DocTCapturaVO.class);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IDocTCapturaDBEntity#updateGestor(java.lang.String,
	 * java.lang.String)
	 */
	public void updateGestor(String[] idTarea, String newGestor) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_ID, idTarea)).toString();

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDUSRCAPTURA, newGestor);

		updateFields(qual, colsToUpdate, TABLE_NAME);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IDocTCapturaDBEntity#fetchXIdElementoYTipoObj(java
	 * .lang.String, int)
	 */
	public List fetchXIdElementoYTipoObj(String idElemento, int idTipoObjeto) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRCAPTURA,
						UsuarioDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDOBJ, idElemento))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOOBJ,
						idTipoObjeto)).toString();

		return getVOS(qual, pairs, DocTCapturaVO.class);
	}

}
