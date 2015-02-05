package solicitudes.db;

import gcontrol.db.UsuarioDBEntityImpl;
import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import solicitudes.prestamos.EstadoRevDoc;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.TooManyResultsException;
import common.util.StringUtils;

/**
 * Clase que encapsula todas las definiciones de los detalles asi como de las
 * operaciones que se pueden realizar sobre ellos.
 */
public class RevisionDocumentacionDBEntityImpl extends DBEntity implements
		IRevisionDocumentacionDBEntity {

	/** Nombre de la tabla */
	public static final String TABLE_NAME = "ASGPREVDOCUDOC";
	/** Campos de la tabla */
	public static final String IDREVDOC_COLUMN_NAME = "IDREVDOC";
	public static final String IDUDOC_COLUMN_NAME = "IDUDOC";
	public static final String TITULO_COLUMN_NAME = "TITULO";
	public static final String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
	public static final String EXPEDIENTEUDOC_COLUMN_NAME = "EXPEDIENTEUDOC";
	public static final String ESTADO_COLUMN_NAME = "ESTADO";
	public static final String FESTADO_COLUMN_NAME = "FESTADO";
	public static final String OBSERVACIONES_COLUMN_NAME = "OBSERVACIONES";
	public static final String IDUSRGESTOR_COLUM_NAME = "IDUSRGESTOR";
	public static final String IDALTA_COLUMN_NAME = "IDALTA";
	public static final String MOTIVORECHAZO_COLUMN_NAME = "MOTIVORECHAZO";

	public static final DbColumnDef IDREVDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDREVDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef IDUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef TITULO_FIELD = new DbColumnDef(null,
			TABLE_NAME, TITULO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);
	public static final DbColumnDef EXPEDIENTEUDOC_FIELD = new DbColumnDef(
			null, TABLE_NAME, EXPEDIENTEUDOC_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 128, true);
	public static final DbColumnDef ESTADO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 2, false);
	public static final DbColumnDef FESTADO_FIELD = new DbColumnDef(null,
			TABLE_NAME, FESTADO_COLUMN_NAME, DbDataType.DATE_TIME, false);
	public static final DbColumnDef OBSERVACIONES_FIELD = new DbColumnDef(null,
			TABLE_NAME, OBSERVACIONES_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024,
			true);
	public static final DbColumnDef MOTIVORECHAZO_FIELD = new DbColumnDef(null,
			TABLE_NAME, MOTIVORECHAZO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef IDUSRGESTOR_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUSRGESTOR_COLUM_NAME, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef IDALTA_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDALTA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef[] TABLE_COLUMNS = { IDREVDOC_FIELD,
			IDUDOC_FIELD, TITULO_FIELD, SIGNATURAUDOC_FIELD,
			EXPEDIENTEUDOC_FIELD, ESTADO_FIELD, FESTADO_FIELD,
			OBSERVACIONES_FIELD, MOTIVORECHAZO_FIELD, IDUSRGESTOR_FIELD,
			IDALTA_FIELD };

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
	 * Constructor por defecto de la clase
	 * 
	 * @param dataSource
	 */
	public RevisionDocumentacionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RevisionDocumentacionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public void insertRevisionDocumentacion(
			final RevisionDocumentacionVO revUDocVO) {

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				revUDocVO.setIdRevDoc(getGuid(revUDocVO.getIdRevDoc()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, revUDocVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solicitudes.db.IRevisionDocumentacionDBEntity#getAllRevisionDocumentacion
	 * ()
	 */
	public List getAllRevisionDocumentacion() {
		StringBuffer qual = new StringBuffer("ORDER BY ").append(TITULO_FIELD);
		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				RevisionDocumentacionVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solicitudes.db.IRevisionDocumentacionDBEntity#
	 * getRevisionDocumentacionByEstado(java.lang.String, int)
	 */
	public List getRevisionDocumentacionByEstado(String idUserGestor, int estado) {
		StringBuffer qual = new StringBuffer("WHERE")
				.append(DBUtils.generateEQTokenField(IDUSRGESTOR_FIELD,
						idUserGestor)).append(" AND ")
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD, estado))
				.append("ORDER BY ").append(TITULO_FIELD);
		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				RevisionDocumentacionVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solicitudes.db.IRevisionDocumentacionDBEntity#getRevisionDocumentacionById
	 * (java.lang.String)
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionById(String idRevDoc) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDREVDOC_FIELD, idRevDoc))
				.toString();

		return (RevisionDocumentacionVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS,
				RevisionDocumentacionVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solicitudes.db.IRevisionDocumentacionDBEntity#getRevisionDocumentacionById
	 * (java.lang.String)
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionByIdAlta(
			String idAlta) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDALTA_FIELD, idAlta))
				.toString();

		return (RevisionDocumentacionVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS,
				RevisionDocumentacionVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see solicitudes.db.IRevisionDocumentacionDBEntity#
	 * getRevisionesDocumentacionByIdUdocYEstado(java.lang.String, int[])
	 */
	public List getRevisionesDocumentacionByIdUdocYEstado(String idUdoc,
			int[] estados) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUDOC_FIELD, idUdoc))
				.append(" AND ")
				.append(DBUtils.generateInTokenField(ESTADO_FIELD, estados))
				.toString();

		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS,
				RevisionDocumentacionVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solicitudes.db.IRevisionDocumentacionDBEntity#update(solicitudes.prestamos
	 * .vos.DocumentacionUDocsVO)
	 */
	public void update(RevisionDocumentacionVO revDocVO) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDREVDOC_FIELD,
						revDocVO.getIdRevDoc())).toString();

		updateVO(qual, TABLE_NAME, TABLE_COLUMNS, revDocVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solicitudes.db.IRevisionDocumentacionDBEntity#updateIdAlta(java.lang.
	 * String, java.lang.String)
	 */
	public void updateIdAlta(String idRevDoc, String idAlta) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDREVDOC_FIELD, idRevDoc));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(IDALTA_FIELD, idAlta);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solicitudes.db.IRevisionDocumentacionDBEntity#removeIdAlta(java.lang.
	 * String)
	 */
	public void removeIdAlta(String idAlta) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDALTA_FIELD, idAlta));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(IDALTA_FIELD, null);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * solicitudes.db.IRevisionDocumentacionDBEntity#getCountRevisionDocXEstados
	 * (int, solicitudes.prestamos.model.EstadoRevDoc[])
	 */
	public int getCountRevisionDocXEstados(String idUser, int[] estados) {
		StringBuffer qual = new StringBuffer("WHERE").append(DBUtils
				.generateEQTokenField(IDUSRGESTOR_FIELD, idUser));
		if (estados != null)
			qual.append(" AND (")
					.append(DBUtils.generateInTokenField(ESTADO_FIELD, estados))
					.append(")");
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Obtiene la lista de gestores con préstamos.
	 * 
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConRevisionesDocumentacion() {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, null);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, UsuarioDBEntityImpl.COL_DEFS);

		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(ESTADO_FIELD,
						EstadoRevDoc.ABIERTA.getIdentificador()));

		qual.append(" AND ").append(
				DBUtils.generateJoinCondition(IDUSRGESTOR_FIELD,
						UsuarioDBEntityImpl.CAMPO_ID));

		qual.append(" ORDER BY ")
				.append(UsuarioDBEntityImpl.CAMPO_APELLIDOS.getQualifiedName())
				.append(",")
				.append(UsuarioDBEntityImpl.CAMPO_NOMBRE.getQualifiedName());

		return getDistinctVOS(qual.toString(), pairs, UsuarioVO.class);
	}

	public List getRevisionesDocumentacion(RevisionDocumentacionVO revDocVO)
			throws TooManyResultsException {
		// DbColumnDef USRGESTOR_FIELD = new DbColumnDef("UsrGestor",
		// null, DBUtils.getNativeIfNullSintax(getConnection(),
		// UsuarioDBEntityImpl.NOMBRE_COLUMN_NAME, "''") +
		// DBUtils.getNativeConcatSyntax(getConnection())+" ' ' "+DBUtils.getNativeConcatSyntax(getConnection())
		// +
		// DBUtils.getNativeIfNullSintax(getConnection(),
		// UsuarioDBEntityImpl.APELLIDOS_COLUMN_NAME,"''") ,
		// DbDataType.SHORT_TEXT, 50, false);

		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, new DbColumnDef[] { IDREVDOC_FIELD,
				IDREVDOC_FIELD, ESTADO_FIELD, TITULO_FIELD,
				EXPEDIENTEUDOC_FIELD, SIGNATURAUDOC_FIELD, OBSERVACIONES_FIELD,
				FESTADO_FIELD, IDUSRGESTOR_FIELD });
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, null);

		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(ESTADO_FIELD,
						EstadoRevDoc.ABIERTA.getIdentificador()))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDUSRGESTOR_FIELD,
						UsuarioDBEntityImpl.CAMPO_ID));

		if (!StringUtils.isEmpty(revDocVO.getTitulo())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(TITULO_FIELD,
							revDocVO.getTitulo()));
		}

		if (!StringUtils.isEmpty(revDocVO.getExpedienteUdoc())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(EXPEDIENTEUDOC_FIELD,
							revDocVO.getExpedienteUdoc()));
		}

		if (!StringUtils.isEmpty(revDocVO.getSignaturaUdoc())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(SIGNATURAUDOC_FIELD,
							revDocVO.getSignaturaUdoc()));
		}

		if (!StringUtils.isEmpty(revDocVO.getObservaciones())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(OBSERVACIONES_FIELD,
							revDocVO.getObservaciones()));
		}

		if (!StringUtils.isEmpty(revDocVO.getIdUsrGestor())) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(IDUSRGESTOR_FIELD,
							revDocVO.getIdUsrGestor()));
		}

		return getDistinctVOS(qual.toString(), pairs,
				RevisionDocumentacionVO.class);
	}
}