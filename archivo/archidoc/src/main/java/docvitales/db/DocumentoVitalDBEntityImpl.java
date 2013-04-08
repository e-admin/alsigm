package docvitales.db;

import gcontrol.db.UsuarioDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;
import common.exceptions.TooManyResultsException;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.StringUtils;

import docvitales.EstadoDocumentoVital;
import docvitales.vos.BusquedaDocumentosVitalesVO;
import docvitales.vos.InfoBDocumentoVitalExtVO;

/**
 * DBEntity para acceder a la tabla ADPCDOCUMENTOVIT.
 */
public class DocumentoVitalDBEntityImpl extends DBEntity implements
		IDocumentoVitalDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADPCDOCUMENTOVIT";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String ID_BD_TERCEROS_COLUMN_NAME = "idbdterceros";
	public static final String NUM_IDENTIDAD_COLUMN_NAME = "numidentidad";
	public static final String IDENTIDAD_COLUMN_NAME = "identidad";
	public static final String ID_TIPO_DOC_VIT_COLUMN_NAME = "idtipodocvit";
	public static final String FECHA_CAD_COLUMN_NAME = "fechacad";
	public static final String ESTADO_DOC_VIT_COLUMN_NAME = "estadodocvit";
	public static final String FECHA_CR_COLUMN_NAME = "fechacr";
	public static final String ID_USUARIO_CR_COLUMN_NAME = "idusuariocr";
	public static final String FECHA_VIG_COLUMN_NAME = "fechavig";
	public static final String ID_USUARIO_VIG_COLUMN_NAME = "idusuariovig";
	public static final String NUM_ACCESOS_COLUMN_NAME = "numaccesos";
	public static final String FECHA_ULT_ACCESO_COLUMN_NAME = "fechaultacceso";
	public static final String TAMANO_FICH_COLUMN_NAME = "tamanofich";
	public static final String NOMBRE_ORG_FICH_COLUMN_NAME = "nombreorgfich";
	public static final String EXT_FICH_COLUMN_NAME = "extfich";
	public static final String ID_FICH_COLUMN_NAME = "idfich";
	public static final String OBSERVACIONES_COLUMN_NAME = "observaciones";
	public static final String ID_REP_ECM_COLUMN_NAME = "idRepEcm";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ID_BD_TERCEROS = new DbColumnDef(
			null, TABLE_NAME, ID_BD_TERCEROS_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_NUM_IDENTIDAD = new DbColumnDef(null,
			TABLE_NAME, NUM_IDENTIDAD_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);
	public static final DbColumnDef CAMPO_IDENTIDAD = new DbColumnDef(null,
			TABLE_NAME, IDENTIDAD_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_ID_TIPO_DOC_VIT = new DbColumnDef(
			null, TABLE_NAME, ID_TIPO_DOC_VIT_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_FECHA_CAD = new DbColumnDef(null,
			TABLE_NAME, FECHA_CAD_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_ESTADO_DOC_VIT = new DbColumnDef(
			null, TABLE_NAME, ESTADO_DOC_VIT_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_FECHA_CR = new DbColumnDef(null,
			TABLE_NAME, FECHA_CR_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef CAMPO_ID_USUARIO_CR = new DbColumnDef(null,
			TABLE_NAME, ID_USUARIO_CR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_FECHA_VIG = new DbColumnDef(null,
			TABLE_NAME, FECHA_VIG_COLUMN_NAME, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_ID_USUARIO_VIG = new DbColumnDef(
			null, TABLE_NAME, ID_USUARIO_VIG_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_NUM_ACCESOS = new DbColumnDef(null,
			TABLE_NAME, NUM_ACCESOS_COLUMN_NAME, DbDataType.SHORT_INTEGER,
			false);
	public static final DbColumnDef CAMPO_FECHA_ULT_ACCESO = new DbColumnDef(
			null, TABLE_NAME, FECHA_ULT_ACCESO_COLUMN_NAME,
			DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_TAMANO_FICH = new DbColumnDef(null,
			TABLE_NAME, TAMANO_FICH_COLUMN_NAME, DbDataType.SHORT_DECIMAL,
			false);
	public static final DbColumnDef CAMPO_NOMBRE_ORG_FICH = new DbColumnDef(
			null, TABLE_NAME, NOMBRE_ORG_FICH_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_EXT_FICH = new DbColumnDef(null,
			TABLE_NAME, EXT_FICH_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, false);
	public static final DbColumnDef CAMPO_ID_FICH = new DbColumnDef(null,
			TABLE_NAME, ID_FICH_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_OBSERVACIONES = new DbColumnDef(null,
			TABLE_NAME, OBSERVACIONES_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_ID_REP_ECM = new DbColumnDef(null,
			TABLE_NAME, ID_REP_ECM_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_ID_BD_TERCEROS, CAMPO_NUM_IDENTIDAD, CAMPO_IDENTIDAD,
			CAMPO_ID_TIPO_DOC_VIT, CAMPO_FECHA_CAD, CAMPO_ESTADO_DOC_VIT,
			CAMPO_FECHA_CR, CAMPO_ID_USUARIO_CR, CAMPO_FECHA_VIG,
			CAMPO_ID_USUARIO_VIG, CAMPO_NUM_ACCESOS, CAMPO_FECHA_ULT_ACCESO,
			CAMPO_TAMANO_FICH, CAMPO_NOMBRE_ORG_FICH, CAMPO_EXT_FICH,
			CAMPO_ID_FICH, CAMPO_OBSERVACIONES, CAMPO_ID_REP_ECM };

	/** Lista de nombres de columnas. */
	public static final String COL_NAMES = DbUtil.getColumnNames(COL_DEFS);
	/** Lista de columnas extendida. */

	/** Lista de tablas. */
	final String[] EXT_TABLE_NAMES = new String[] { TipoDocumentoVitalDBEntityImpl.TABLE_NAME };

	/** Lista de columnas extendida. */
	static DbColumnDef[] EXT_COL_DEFS = null;

	public static DbColumnDef[] getEXT_COL_DEFS(DbConnection conn) {
		if (EXT_COL_DEFS == null) {
			EXT_COL_DEFS = (DbColumnDef[]) ArrayUtils
					.concat(COL_DEFS,
							new DbColumnDef[] {
									new DbColumnDef(
											"nombreTipoDocVit",
											TipoDocumentoVitalDBEntityImpl.TABLE_NAME,
											TipoDocumentoVitalDBEntityImpl.CAMPO_NOMBRE),
									new DbColumnDef(
											"nombreUsuarioCr",
											DBUtils.getNativeTrimSyntax(
													conn,
													new StringBuffer("UC.")
															.append(UsuarioDBEntityImpl.NOMBRE_COLUMN_NAME)
															.append(DBUtils
																	.getNativeConcatSyntax(conn))
															.append("' '")
															.append(DBUtils
																	.getNativeConcatSyntax(conn))

															.append("UC.")
															.append(UsuarioDBEntityImpl.APELLIDOS_COLUMN_NAME)
															.toString()),
											DbDataType.SHORT_TEXT, true),

									new DbColumnDef(
											"nombreUsuarioVig",
											DBUtils.getNativeTrimSyntax(
													conn,
													new StringBuffer("UV.")
															.append(UsuarioDBEntityImpl.NOMBRE_COLUMN_NAME)
															.append(DBUtils
																	.getNativeConcatSyntax(conn))
															.append("' '")
															.append(DBUtils
																	.getNativeConcatSyntax(conn))
															.append("UV.")
															.append(UsuarioDBEntityImpl.APELLIDOS_COLUMN_NAME)
															.toString()),
											DbDataType.SHORT_TEXT, true), });
		}
		return EXT_COL_DEFS;
	}

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
	public DocumentoVitalDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocumentoVitalDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * docvitales.db.IDocumentoVitalDBEntity#getCountDocumentosVitalesXEstados
	 * (int[])
	 */
	public int getCountDocumentosVitalesXEstados(int[] estados) {
		StringBuffer qual = new StringBuffer().append("WHERE 1=1");

		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO_DOC_VIT, estados));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Realiza la búsqueda de documentos vitales.
	 * 
	 * @param busquedaVO
	 *            Criterios de búsqueda.
	 * @return Lista de documentos vitales ({@link InfoBDocumentoVitalExtVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getDocumentosVitales(BusquedaDocumentosVitalesVO busquedaVO)
			throws TooManyResultsException {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COL_DEFS);
		pairs.put(TipoDocumentoVitalDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { new DbColumnDef("nombreTipoDocVit",
						TipoDocumentoVitalDBEntityImpl.CAMPO_NOMBRE) });

		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateJoinCondition(CAMPO_ID_TIPO_DOC_VIT,
						TipoDocumentoVitalDBEntityImpl.CAMPO_ID));

		// Número de identidad
		if (StringUtils.isNotBlank(busquedaVO.getNumIdentidad())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(CAMPO_NUM_IDENTIDAD,
							busquedaVO.getNumIdentidad().toUpperCase()));
		}

		// Identidad
		if (StringUtils.isNotBlank(busquedaVO.getIdentidad())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(CAMPO_IDENTIDAD, busquedaVO
							.getIdentidad().toUpperCase()));
		}

		// Tipos de documentos
		if (!ArrayUtils.isEmpty(busquedaVO.getTiposDocumentos())) {
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ID_TIPO_DOC_VIT,
							busquedaVO.getTiposDocumentos()));
		}

		// Estados
		if (!ArrayUtils.isEmpty(busquedaVO.getEstados())) {
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO_DOC_VIT,
							busquedaVO.getEstados()));
		}

		// Fecha de caducidad
		if (busquedaVO.getFechaCaducidadIni() != null) {
			qual.append(" AND ")
					.append(CAMPO_FECHA_CAD.getQualifiedName())
					.append(">=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaVO.getFechaCaducidadIni(),
							CustomDateFormat.SDF_YYYYMMDD));

		}
		if (busquedaVO.getFechaCaducidadFin() != null) {
			qual.append(" AND ")
					.append(CAMPO_FECHA_CAD.getQualifiedName())
					.append("<=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaVO.getFechaCaducidadFin(),
							CustomDateFormat.SDF_YYYYMMDD));
		}

		// Número de accesos
		if (StringUtils.isNotBlank(busquedaVO.getNumAccesos())) {
			qual.append(" AND ").append(CAMPO_NUM_ACCESOS.getQualifiedName())
					.append(busquedaVO.getNumAccesosComp())
					.append(busquedaVO.getNumAccesos());
		}

		// Fecha de último acceso
		if (busquedaVO.getFechaUltimoAccesoIni() != null) {
			qual.append(" AND ")
					.append(CAMPO_FECHA_ULT_ACCESO.getQualifiedName())
					.append(">=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaVO.getFechaUltimoAccesoIni(),
							CustomDateFormat.SDF_YYYYMMDD));
		}
		if (busquedaVO.getFechaUltimoAccesoFin() != null) {
			qual.append(" AND ")
					.append(CAMPO_FECHA_ULT_ACCESO.getQualifiedName())
					.append("<=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							busquedaVO.getFechaUltimoAccesoFin(),
							CustomDateFormat.SDF_YYYYMMDD));
		}

		// Ordenación
		if (busquedaVO.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("identidad", CAMPO_IDENTIDAD);
			criteriosOrdenacion.put("numIdentidad", CAMPO_NUM_IDENTIDAD);
			criteriosOrdenacion.put("nombreTipoDocVit",
					TipoDocumentoVitalDBEntityImpl.CAMPO_NOMBRE);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO_DOC_VIT);
			criteriosOrdenacion.put("fechaCaducidad", CAMPO_FECHA_CAD);
			criteriosOrdenacion.put("numAccesos", CAMPO_NUM_ACCESOS);
			criteriosOrdenacion
					.put("fechaUltimoAcceso", CAMPO_FECHA_ULT_ACCESO);

			return getVOS(qual.toString(),
					busquedaVO.getPageInfo().getOrderBy(criteriosOrdenacion),
					pairs, InfoBDocumentoVitalExtVO.class,
					busquedaVO.getPageInfo());
		} else {
			StringBuffer sbQual = new StringBuffer(qual.toString()).append(
					" ORDER BY ").append(CAMPO_IDENTIDAD.getQualifiedName());

			return getVOS(sbQual.toString(), pairs,
					InfoBDocumentoVitalExtVO.class);
		}
	}

	/**
	 * Obtiene la lista de documentos vitales vigentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idProc
	 *            Identificador de procedimiento. Si es nulo, ignora este
	 *            criterio.
	 * @return Lista de documentos vitales ({@link InfoBDocumentoVitalExtVO}.
	 */
	public List getDocumentosVitalesVigentes(String idTercero, String idProc) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_BD_TERCEROS,
						idTercero))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO_DOC_VIT,
						EstadoDocumentoVital.VIGENTE));

		// Identificador de procedimiento
		if (StringUtils.isNotBlank(idProc)) {
			qual.append(" AND ")
					.append(CAMPO_ID_TIPO_DOC_VIT.getQualifiedName())
					.append(" IN (SELECT ")
					.append(TipoDocVitProcedimientoDBEntityImpl.CAMPO_ID_TIPO_DOC)
					.append(" FROM ")
					.append(TipoDocVitProcedimientoDBEntityImpl.TABLE_NAME)
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(
							TipoDocVitProcedimientoDBEntityImpl.CAMPO_ID_PROC,
							idProc)).append(")");
		}
		qual.append(" AND ").append(getJoiningClauses());
		qual.append(" ORDER BY ").append(
				TipoDocumentoVitalDBEntityImpl.CAMPO_NOMBRE.getQualifiedName());

		return getVOS(qual.toString(), getSqlFrom(),
				getEXT_COL_DEFS(getConnection()),
				InfoBDocumentoVitalExtVO.class);
	}

	/**
	 * Obtiene el documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 * @return Documento vital.
	 */
	public InfoBDocumentoVitalExtVO getDocumentoVital(String id) {
		// TODO ZMIGRACION BD - OUTER JOIN (PROBADO
		final StringBuffer qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id))
				.append(" AND ").append(getJoiningClauses());

		return (InfoBDocumentoVitalExtVO) getVO(qual.toString(), getSqlFrom(),
				getEXT_COL_DEFS(getConnection()),
				InfoBDocumentoVitalExtVO.class);
	}

	/**
	 * Obtiene el documento vital.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 * @param estado
	 *            Estado del documento vital.
	 * @return Documento vital.
	 */
	public InfoBDocumentoVitalExtVO getDocumentoVital(String idTercero,
			String idTipoDocVit, int estado) {
		final StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_BD_TERCEROS,
						idTercero))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_TIPO_DOC_VIT,
						idTipoDocVit))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO_DOC_VIT,
						estado)).append(" AND ").append(getJoiningClauses());

		return (InfoBDocumentoVitalExtVO) getVO(qual.toString(), getSqlFrom(),
				getEXT_COL_DEFS(getConnection()),
				InfoBDocumentoVitalExtVO.class);
	}

	/**
	 * Compone las cláusulas de unión de tablas generales.
	 * 
	 * @return Cláusulas de unión de tablas.
	 */
	private String getJoiningClauses() {
		return new StringBuffer().append(
				DBUtils.generateJoinCondition(CAMPO_ID_TIPO_DOC_VIT,
						TipoDocumentoVitalDBEntityImpl.CAMPO_ID)).toString();
	}

	private String getSqlFrom() {
		TableDef tablaDocumentoVital = new TableDef(TABLE_NAME);
		JoinDefinition[] joins = new JoinDefinition[] {

				new JoinDefinition(new DbColumnDef(tablaDocumentoVital,
						CAMPO_ID_USUARIO_CR), new DbColumnDef(new TableDef(
						UsuarioDBEntityImpl.TABLE_NAME, "UC"), new DbColumnDef(
						"UC", UsuarioDBEntityImpl.CAMPO_ID))),
				new JoinDefinition(new DbColumnDef(tablaDocumentoVital,
						CAMPO_ID_USUARIO_VIG), new DbColumnDef(new TableDef(
						UsuarioDBEntityImpl.TABLE_NAME, "UV"), new DbColumnDef(
						"UV", UsuarioDBEntityImpl.CAMPO_ID))) };

		String leftJoin = DBUtils.generateLeftOuterJoinCondition(
				tablaDocumentoVital, joins);
		String extTables = ArrayUtils.join(EXT_TABLE_NAMES, ",");

		return new StringBuffer(leftJoin).append(" , ").append(extTables)
				.toString();
	}

	/**
	 * Inserta un de documento vital.
	 * 
	 * @param documentoVital
	 *            Documento vital.
	 * @return Identificador del documento vital.
	 */
	public String insertDocumentoVital(InfoBDocumentoVitalExtVO documentoVital) {
		documentoVital.setId(getGuid(documentoVital.getId()));
		insertVO(TABLE_NAME, COL_DEFS, documentoVital);
		return documentoVital.getId();
	}

	/**
	 * Modifica la información de un documento vital.
	 * 
	 * @param documentoVital
	 *            Documento vital.
	 */
	public void updateDocumentoVital(InfoBDocumentoVitalExtVO documentoVital) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						documentoVital.getId())).toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, documentoVital);
	}

	/**
	 * Modifica el estado de un documento vital.
	 * 
	 * @param idDocVit
	 *            Identificador del documento vital.
	 * @param estado
	 *            Estado del documento vital.
	 */
	public void updateEstadoDocumentoVital(String idDocVit, int estado) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idDocVit))
				.toString();

		Map cols = Collections.singletonMap(CAMPO_ESTADO_DOC_VIT, new Integer(
				estado));

		updateFields(qual, cols, TABLE_NAME);
	}

	/**
	 * Modifica el identificador de ficher de un documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 * @param idFich
	 *            Identificador del fichero.
	 */
	public void updateIdFichDocumentoVital(String id, String idFich) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		Map cols = Collections.singletonMap(CAMPO_ID_FICH, idFich);

		updateFields(qual, cols, TABLE_NAME);
	}

	/**
	 * Elimina un documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 */
	public void deleteDocumentoVital(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Obtiene el número de documentos vitales de un tipo en concreto.
	 * 
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 * @return Número de documentos vitales.
	 */
	public int getCountDocumentosVitalesByTipo(String idTipoDocVit) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_TIPO_DOC_VIT,
						idTipoDocVit)).toString();

		return getVOCount(qual, TABLE_NAME);
	}

	/**
	 * Pasa a históricos los documentos vigentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 */
	public void pasaAHistoricoDocsVigentes(String idTercero) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_BD_TERCEROS,
						idTercero))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO_DOC_VIT,
						EstadoDocumentoVital.VIGENTE)).toString();

		Map cols = Collections.singletonMap(CAMPO_ESTADO_DOC_VIT, new Integer(
				EstadoDocumentoVital.HISTORICO));

		updateFields(qual, cols, TABLE_NAME);
	}

	/**
	 * Obtiene la lista de documentos vitales vigentes que han caducado.
	 * 
	 * @return Lista de documentos vitales.
	 */
	public List getDocumentosVigentesCaducados() {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO_DOC_VIT,
						EstadoDocumentoVital.VIGENTE)).append(" AND ")
				.append(CAMPO_FECHA_CAD).append("<")
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(" AND ").append(getJoiningClauses())
				.append(" ORDER BY ").append(CAMPO_NUM_IDENTIDAD).append(",")
				.append(TipoDocumentoVitalDBEntityImpl.CAMPO_NOMBRE).toString();

		return getVOS(qual.toString(), getSqlFrom(),
				getEXT_COL_DEFS(getConnection()),
				InfoBDocumentoVitalExtVO.class);
	}

	/**
	 * Obtiene la lista de documentos vitales eliminables, es decir, que han
	 * caducado y no se están usando en ningún expediente.
	 * 
	 * @return Lista de documentos vitales.
	 */
	public List getDocumentosEliminables() {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO_DOC_VIT,
						EstadoDocumentoVital.HISTORICO)).append(" AND ")
				.append(CAMPO_ID).append(" NOT IN (SELECT DISTINCT ")
				.append(UsoDocumentoVitalDBEntityImpl.CAMPO_ID_DOC_VIT)
				.append(" FROM ")
				.append(UsoDocumentoVitalDBEntityImpl.TABLE_NAME).append(")")
				.append(" AND ").append(getJoiningClauses())
				.append(" ORDER BY ").append(CAMPO_NUM_IDENTIDAD).append(",")
				.append(TipoDocumentoVitalDBEntityImpl.CAMPO_NOMBRE);

		return getVOS(qual.toString(), getSqlFrom(),
				getEXT_COL_DEFS(getConnection()),
				InfoBDocumentoVitalExtVO.class);
	}
}
