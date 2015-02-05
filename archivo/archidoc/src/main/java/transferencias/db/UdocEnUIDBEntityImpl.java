package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputRecordSet;
import ieci.core.db.DbOutputStatement;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import transferencias.EstadoCotejo;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.db.AbstractDbOutputRecordSet;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;
import common.lang.MutableBoolean;
import common.lang.MutableInt;
import common.util.ArrayUtils;

import deposito.db.UDocEnUiDepositoDbEntityImpl;
import descripcion.db.FechaDBEntityImpl;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.UnidadDocumentalDBEntityImpl;

/**
 * Clase con los metodos encargados de recuperar y almacenar en la tabla
 * mediante la que se implementa la relacion muchos a muchos que existe entre
 * 'Unidad Documental' y 'Unidad de instalacion'
 * 
 */
public class UdocEnUIDBEntityImpl extends DBEntity implements IUdocEnUIDBEntity {

	public static final String TABLE_NAME = "ASGTUDOCENUI";

	public static final String UDOC_COLUMN_NAME = "idunidaddoc";

	public static final String UINSTALACION_COLUMN_NAME = "iduinstalacionre";

	public static final String IDRELACION_COLUMN_NAME = "idrelentrega";

	public static final String POSUDOC_COLUMN_NAME = "posudocenui";

	public static final String UDOCCOMPLETA_COLUMN_NAME = "udoccompleta";

	public static final String ESTADOCOTEJO_COLUMN_NAME = "estadocotejo";

	public static final String NOTASCOTEJO_COLUMN_NAME = "notascotejo";

	public static final String SIGNATURA_COLUMN_NAME = "signaturaudoc";

	public static final String PARTEUDOC_COLUMN_NAME = "numparteudoc";

	public static final String DESCRIPCION_CONTENIDO_COLUMN_NAME = "desccontenido";

	public static final DbColumnDef ID_RELACION_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDRELACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef UDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, UDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef UINSTALACION_RELACION_FIELD = new DbColumnDef(
			UINSTALACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef POS_UDOC_FIELD = new DbColumnDef(
			POSUDOC_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef UDOC_COMPLETA_FIELD = new DbColumnDef(
			UDOCCOMPLETA_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef ESTADO_COTEJO_FIELD = new DbColumnDef(
			ESTADOCOTEJO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef NOTAS_COTEJO_FIELD = new DbColumnDef(
			NOTASCOTEJO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef SIGNATURA_FIELD = new DbColumnDef(
			SIGNATURA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef PARTEUDOC_FIELD = new DbColumnDef(
			PARTEUDOC_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef DESCRIPCION_CONTENIDO_FIELD = new DbColumnDef(
			DESCRIPCION_CONTENIDO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_RELACION_FIELD,
			UDOC_FIELD, UINSTALACION_RELACION_FIELD, POS_UDOC_FIELD,
			UDOC_COMPLETA_FIELD, ESTADO_COTEJO_FIELD, NOTAS_COTEJO_FIELD,
			SIGNATURA_FIELD, PARTEUDOC_FIELD, DESCRIPCION_CONTENIDO_FIELD };

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

	public UdocEnUIDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public UdocEnUIDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public boolean checkUdocsConErrores(final String idRelacion) {
		final MutableBoolean udocsConErrores = new MutableBoolean(false);
		new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(
								ESTADO_COTEJO_FIELD,
								EstadoCotejo.ERRORES.getIdentificador()))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								idRelacion));
				udocsConErrores.setValue(!DbSelectFns.rowExists(conn,
						TABLE_NAME, qual.toString()));
			}
		};
		return udocsConErrores.getValue();
	}

	public boolean checkEstadoCotejoUDocsRelacion(final String idRelacion,
			final int estadoCotejo) {
		final MutableBoolean udocsSinEstadoCotejo = new MutableBoolean(false);
		new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(
								ESTADO_COTEJO_FIELD, estadoCotejo))
						.append(" AND ")
						.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
								idRelacion));
				udocsSinEstadoCotejo.setValue(!DbSelectFns.rowExists(conn,
						TABLE_NAME, qual.toString()));
			}
		};
		return udocsSinEstadoCotejo.getValue();
	}

	/**
	 * Actualiza en la base de datos la posicion que ocupa una unidad documental
	 * dentro de una unidad de instalacion
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param udoc
	 *            Identificador de unidad documental
	 * @param uinstalacion
	 *            Identificador de unidad de instalacion
	 * @param posicion
	 *            Posicion de la unidad documental dentro de la unidad de
	 *            instalacion
	 * @throws Exception
	 */
	public void updatePosicionEnCaja(final String udoc,
			final String uinstalacion, final int posicion) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, udoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UINSTALACION_RELACION_FIELD, uinstalacion));
		Map colToUpdate = Collections.singletonMap(POS_UDOC_FIELD,
				String.valueOf(posicion));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	// TODO Sustituir las llamadas a estos metodos por llamada a updateFields.
	// Eliminar todos estos metodos
	public void updateEstadoYSignatura(final String idUDoc,
			final String[] numsPartesUDoc, final int estado,
			final String signatura) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, idUDoc))
				.append(" AND (")
				.append(DBUtils.generateORTokens(PARTEUDOC_FIELD,
						numsPartesUDoc)).append(")");
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));
		colsToUpdate.put(SIGNATURA_FIELD, signatura);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void updateEstadoCotejoUdoc(String idUDoc,
			String idUnidadInstalacion, int estado) {
		StringBuffer qual = new StringBuffer(" WHERE 1=1 ");
		if (idUDoc != null)
			qual.append(" and ").append(
					DBUtils.generateEQTokenField(UDOC_FIELD, idUDoc));
		if (idUnidadInstalacion != null)
			qual.append(" and ").append(
					DBUtils.generateEQTokenField(UINSTALACION_RELACION_FIELD,
							idUnidadInstalacion));
		Map colToUpdate = Collections.singletonMap(ESTADO_COTEJO_FIELD,
				String.valueOf(estado));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void updateEstadoYSignatura(String idUDoc, String numParteUDoc,
			int estado, String signatura) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, idUDoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(PARTEUDOC_FIELD,
						numParteUDoc));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));
		colsToUpdate.put(SIGNATURA_FIELD, signatura);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transferencias.db.IUdocEnUIDBEntity#updateEstado(java.lang.String,
	 * java.lang.String, int)
	 */
	public void updateEstado(String idUDoc, String numParteUDoc, int estado) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, idUDoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(PARTEUDOC_FIELD,
						numParteUDoc));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void updateSignatura(String idUDoc, String numParteUDoc,
			String signatura) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, idUDoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(PARTEUDOC_FIELD,
						numParteUDoc));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(SIGNATURA_FIELD, signatura);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void updateEstadoYObservacionesYSignatura(String idUDoc,
			String numParteUDoc, int estado, String observaciones,
			String signatura) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, idUDoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(PARTEUDOC_FIELD,
						numParteUDoc));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));
		colsToUpdate.put(SIGNATURA_FIELD, signatura);
		colsToUpdate.put(NOTAS_COTEJO_FIELD, observaciones);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Modifica el estado de cotejo del contenido de las unidades de
	 * instalación.
	 * 
	 * @param ids
	 *            Identificadores de las unidades de instalación.
	 * @param estado
	 *            Estado de cotejo.
	 */
	public void updateEstadoXUnidadesInstalacion(String[] ids, int estado) {
		if (!ArrayUtils.isEmpty(ids)) {
			StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
					.generateInTokenField(UINSTALACION_RELACION_FIELD, ids));

			Map colsToUpdate = new HashMap();
			colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));

			updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocEnUIDBEntity#updateEstadoCotejoUdocXIdRelacion
	 * (java.lang.String, int)
	 */
	public void updateEstadoCotejoUdocXIdRelacion(String idRelacion, int estado) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenField(ID_RELACION_FIELD, idRelacion));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf((estado)));

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	private static final DbColumnDef[] COLS_TO_SELECT = (DbColumnDef[]) ArrayUtils
			.concat(TABLE_COLUMNS, new DbColumnDef[] {
					new DbColumnDef("asunto",
							UDocRelacionDBEntityImpl.ASUNTO_FIELD),
					new DbColumnDef("totalPartes",
							UDocRelacionDBEntityImpl.NUM_PARTES_FIELD),
					new DbColumnDef("expediente",
							UDocRelacionDBEntityImpl.NUMERO_EXPEDIENTE_FIELD),
					new DbColumnDef("fechaInicio",
							UDocRelacionDBEntityImpl.FECHA_INICIO_FIELD),
					new DbColumnDef("fechaFin",
							UDocRelacionDBEntityImpl.FECHA_FIN_FIELD) });
	private static String TABLE_SET = ArrayUtils.join(new String[] {
			TABLE_NAME, UDocRelacionDBEntityImpl.TABLE_NAME }, ",");

	private List getPartesUnidadDocumental(String qual, String orderBy) {
		StringBuffer whereClause = new StringBuffer(qual);
		if (whereClause.length() > 0)
			whereClause.append(" AND ");
		else
			whereClause.append(" WHERE ");
		whereClause.append(DBUtils.generateJoinCondition(UDOC_FIELD,
				UDocRelacionDBEntityImpl.ID_FIELD));
		if (StringUtils.isNotBlank(orderBy))
			whereClause.append(" ").append(orderBy);
		return getVOS(whereClause.toString(), TABLE_SET, COLS_TO_SELECT,
				ParteUnidadDocumentalVO.class);
	}

	/**
	 * Obtiene las unidades documentales incluidas en una unidad de instalacion
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idUnidadInstalacion
	 *            Identificador de la unidad de instalacion
	 * @return Lista con las unidades documentales incluidas en la unidad de
	 *         instalacion
	 * @throws Exception
	 */
	public List fetchRowsByUInstalacion(String idUnidadInstalacion) {
		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(UINSTALACION_RELACION_FIELD,
						idUnidadInstalacion));
		StringBuffer orderBy = new StringBuffer(" order by ")
				.append(POSUDOC_COLUMN_NAME);
		return getPartesUnidadDocumental(qual.toString(), orderBy.toString());
	}

	/**
	 * Obtiene las unidades documentales incluidas en varias unidades de
	 * instalacion
	 * 
	 * @param conn
	 *            Conexion a la base de datos
	 * @param idUnidadInstalacion
	 *            Identificador de las unidades de instalacion
	 * @return Lista con las unidades documentales incluidas en las unidades de
	 *         instalacion
	 * @throws Exception
	 */
	public List fetchRowsByUInstalacion(String[] idUnidadInstalacion) {
		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateInTokenField(UINSTALACION_RELACION_FIELD,
						idUnidadInstalacion));
		StringBuffer orderBy = new StringBuffer(" order by ")
				.append(UINSTALACION_COLUMN_NAME).append(",")
				.append(POSUDOC_COLUMN_NAME);
		return getPartesUnidadDocumental(qual.toString(), orderBy.toString());
	}

	/**
	 * Obtiene las partes de una unidad documental que se encuentran asignadas
	 * 
	 * @param idUnidadDocumental
	 *            identificador de unidad documental
	 * @return Lista de partes de unidad documental
	 *         {@link ParteUnidadDocumentalVO}
	 */
	public List fetchRowsByUdoc(String idUnidadDocumental) {
		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(UDOC_FIELD, idUnidadDocumental));
		StringBuffer orderBy = new StringBuffer(" order by ")
				.append(PARTEUDOC_COLUMN_NAME);
		return getPartesUnidadDocumental(qual.toString(), orderBy.toString());
	}

	/**
	 * Obtiene la parte de una unidad documental en una determinada caja
	 * 
	 * @param idUnidadInstalacion
	 *            identificador de la unidad instalacion
	 * @param idUnidadDocumental
	 *            identificador de unidad documental
	 * @return Lista de partes de unidad documental
	 *         {@link ParteUnidadDocumentalVO}
	 */
	public IParteUnidadDocumentalVO getRowByUdocUI(String idUnidadInstalacion,
			String idUnidadDocumental) {
		StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD,
						idUnidadDocumental))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UINSTALACION_RELACION_FIELD, idUnidadInstalacion));
		StringBuffer orderBy = new StringBuffer(" order by ")
				.append(PARTEUDOC_COLUMN_NAME);
		List lista = getPartesUnidadDocumental(qual.toString(),
				orderBy.toString());
		if (lista == null || lista.size() == 0 || lista.size() > 1)
			return null;
		return (IParteUnidadDocumentalVO) lista.get(0);
	}

	/**
	 * Recupera los expedientes de una relación que están asignados a alguna
	 * caja
	 * 
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de expedientes {@link ParteUnidadDocumentalVO}
	 */
	public List fetchRowsByIdRelacion(String idRelacion) {
		StringBuffer qual = new StringBuffer("where ").append(DBUtils
				.generateEQTokenField(ID_RELACION_FIELD, idRelacion));
		return getPartesUnidadDocumental(qual.toString(), null);
	}

	/**
	 * Recupera los expedientes de una relación que están asignados a alguna
	 * caja Udocs con la posicion de su caja en la relacion
	 * 
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de expedientes {@link ParteUnidadDocumentalVO}
	 */
	public List getUdocsByIdRelacionOficinaArchivo(String idRelacion) {
		// SELECT
		// asgtudocenui.idrelentrega,asgtudocenui.idunidaddoc,iduinstalacionre,posudocenui,
		// udoccompleta,estadocotejo,notascotejo,signaturaudoc,numparteudoc,desccontenido,asunto,
		// numpartes,numexp,UInst.orden,fechaextini,fechaextfin
		// FROM asgtudocenui,asgtunidaddocre,
		// (SELECT DISTINCT asgtuinstalacionre.id, asgtuinstalacionre.orden, ''
		// as signaturauiorigen
		// FROM asgtuinstalacionre
		// WHERE asgtuinstalacionre.idrelentrega=
		// '096da14229e500000000000000000017') UInst
		// WHERE UInst.id=asgtudocenui.iduinstalacionre AND
		// asgtunidaddocre.id=asgtudocenui.idunidaddoc AND
		// asgtudocenui.idrelentrega= '096da14229e500000000000000000017'

		// select campos idUI y posEnUI de tablas ASGTUINSTALACIONRE y
		// ASGTUINSTALACIONREEA
		String selectRe = DbSelectStatement
				.getSelectStmtText(
						UnidadInstalacionDBEntityImpl.TABLE_NAME,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] {
								UnidadInstalacionDBEntityImpl.ID_FIELD,
								UnidadInstalacionDBEntityImpl.ORDEN_FIELD }),
						"WHERE "
								+ DBUtils
										.generateEQTokenField(
												UnidadInstalacionDBEntityImpl.ID_RELACION_FIELD,
												idRelacion), true);

		DbColumnDef ID_UI_FIELD = new DbColumnDef("id",
				UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD);
		String selectReEA = DbSelectStatement
				.getSelectStmtText(
						UnidadInstalacionReeaDBEntityImpl.TABLE_NAME,
						DbUtil.getColumnNamesWithAlias(new DbColumnDef[] {
								ID_UI_FIELD,
								UnidadInstalacionReeaDBEntityImpl.ORDEN_FIELD }),
						"WHERE "
								+ DBUtils
										.generateEQTokenField(
												UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD,
												idRelacion), true);

		String tablaUI = DBUtils.generateUNIONTokens(new String[] { selectRe,
				selectReEA });
		String TABLE_SET = TABLE_NAME + ","
				+ UDocRelacionDBEntityImpl.TABLE_NAME + "," + tablaUI
				+ " UInst";

		// COLS_TO_SELECT
		DbColumnDef posCaja = new DbColumnDef("PosCaja", "UInst",
				UnidadInstalacionDBEntityImpl.ORDEN_FIELD);
		DbColumnDef[] COLS = (DbColumnDef[]) ArrayUtils.concat(COLS_TO_SELECT,
				new DbColumnDef[] { posCaja,
						UDocRelacionDBEntityImpl.FECHA_INICIO_FIELD,
						UDocRelacionDBEntityImpl.FECHA_FIN_FIELD });

		// WHERE
		DbColumnDef UINST_RELACION_FIELD = new DbColumnDef(
				UINSTALACION_COLUMN_NAME, TABLE_NAME, UINSTALACION_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);

		StringBuffer qual = new StringBuffer("WHERE ");
		qual.append(
				DBUtils.generateJoinCondition(new DbColumnDef(
						UnidadInstalacionDBEntityImpl.ID_COLUMN_NAME, "UInst",
						UnidadInstalacionDBEntityImpl.ID_FIELD),
						UINST_RELACION_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UDocRelacionDBEntityImpl.ID_FIELD, UDOC_FIELD))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion));

		// ORDER BY
		qual.append(DBUtils.generateOrderBy(new DbColumnDef[] {
				UDocRelacionDBEntityImpl.ORDEN_FIELD, POS_UDOC_FIELD }));

		return getVOS(qual.toString(), TABLE_SET, COLS,
				ParteUnidadDocumentalVO.class);
	}

	/**
	 * Recupera los expedientes de una relación que están asignados a alguna
	 * caja Udocs con la posicion de su caja en la relacion
	 * 
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @return Lista de expedientes {@link ParteUnidadDocumentalVO}
	 */
	public List getUdocsByIdRelacionEntreArchivos(String idRelacion) {
		// SELECT
		// ui.idrelentrega,udocUi.idunidaddoc,udocUi.iduinstalacion,udocUI.posudocenui,
		// 0 as
		// udoccompleta,ui.estadocotejo,ui.notascotejo,udocUi.signaturaudoc,0 as
		// numparteudoc,
		// udocUi.descripcion as desccontenido,
		// 0 as numpartes,udoc.numexp,elem.titulo as asunto,ui.orden,
		// ui.signaturauiorigen,
		// FI.FECHAINI as fechaextini, FF.FECHAFIN as fechaextfin
		// FROM asgdudocenui udocUi, ASGFUNIDADDOC udoc, asgtuinstalacionreea
		// ui,
		// ASGFELEMENTOCF elem LEFT OUTER JOIN ADVCFECHACF FI ON
		// id=FI.idelementocf and FI.IDCAMPO='3'
		// LEFT OUTER JOIN ADVCFECHACF FF ON id=FF.idelementocf and
		// FF.IDCAMPO='4'
		// WHERE ui.idrelentrega= '096da14229e500000000000000000017' AND
		// ui.iduideposito=udocUI.iduinstalacion AND
		// udoc.idelementocf=udocUI.idunidaddoc AND
		// elem.id=udoc.idelementocf

		// Clausula FROM
		DbColumnDef FI_IDCAMPO = new DbColumnDef(null, "FI",
				FechaDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef FI_IDELEM = new DbColumnDef(null, "FI",
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef FF_IDCAMPO = new DbColumnDef(null, "FF",
				FechaDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef FF_IDELEM = new DbColumnDef(null, "FF",
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO);
		String idCampoFechaExtremaInicial = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaInicial();
		String idCampoFechaExtremaFinal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaFinal();
		String TABLE_SET = UDocEnUiDepositoDbEntityImpl.TABLE_NAME
				+ ","
				+ UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL
				+ ","
				+ UnidadInstalacionReeaDBEntityImpl.TABLE_NAME
				+ ","
				+ ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ " LEFT OUTER JOIN "
				+ FechaDBEntityImpl.TABLE_NAME
				+ " FI "
				+ " ON "
				+ DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
								FI_IDELEM)
				+ " AND "
				+ DBUtils.generateEQTokenField(FI_IDCAMPO,
						idCampoFechaExtremaInicial)
				+ " LEFT OUTER JOIN "
				+ FechaDBEntityImpl.TABLE_NAME
				+ " FF "
				+ " ON "
				+ DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
								FF_IDELEM)
				+ " AND "
				+ DBUtils.generateEQTokenField(FF_IDCAMPO,
						idCampoFechaExtremaFinal);

		// Clausula WHERE
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils
						.generateEQTokenField(
								UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD,
								idRelacion))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UnidadDocumentalDBEntityImpl.CAMPO_ID,
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
								UnidadDocumentalDBEntityImpl.CAMPO_ID));

		// Y columnas a generar
		DbColumnDef[] COLS = new DbColumnDef[] {
				UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
				new DbColumnDef("idUinstalacionRe",
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD),
				UDocEnUiDepositoDbEntityImpl.POSUDOCENUI_FIELD,
				UnidadInstalacionReeaDBEntityImpl.ESTADO_COTEJO_FIELD,
				UnidadInstalacionReeaDBEntityImpl.NOTAS_COTEJO_FIELD,
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				new DbColumnDef("desccontenido",
						UDocEnUiDepositoDbEntityImpl.DESCRIPCION_FIELD),
				new DbColumnDef("expediente",
						UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE),
				new DbColumnDef(
						"asunto",
						ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD),
				new DbColumnDef(
						"sigorigen",
						UnidadInstalacionReeaDBEntityImpl.SIGNATURA_ORIGEN_FIELD),
				new DbColumnDef("fechaInicio", "FI",
						FechaDBEntityImpl.CAMPO_FECHA_INICIAL),
				new DbColumnDef("fechaFin", "FF",
						FechaDBEntityImpl.CAMPO_FECHA_FINAL) };

		// 0 as udoccompleta,0 as numparteudoc,
		// 0 as numpartes,

		// ORDER BY

		return getVOS(qual.toString(), TABLE_SET, COLS,
				ParteUnidadDocumentalVO.class);
	}

	public void deleteXIdRelacion(final String idRelacionEntrega) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
						.generateEQTokenField(ID_RELACION_FIELD,
								idRelacionEntrega));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	protected void compactUI(final String unidadInstalacionID, final int fromPos) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer sql = new StringBuffer("update ")
						.append(TABLE_NAME)
						.append(" set ")
						.append(POSUDOC_COLUMN_NAME)
						.append("=")
						.append(POSUDOC_COLUMN_NAME)
						.append(" - 1 ")
						.append("where ")
						.append(DBUtils.generateEQTokenField(
								UINSTALACION_RELACION_FIELD,
								unidadInstalacionID))
						.append(" and ")
						.append(DBUtils.generateGTTokenField(POS_UDOC_FIELD,
								fromPos));
				DbInputStatement inputStatement = new DbInputStatement();
				inputStatement.create(conn, sql.toString());
				inputStatement.execute();
				inputStatement.release();
			}
		};
		command.execute();
	}

	public void deleteUdocFromUI(final String unidadInstalacionID,
			final int posUdoc) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("where ")
						.append(DBUtils.generateEQTokenField(POS_UDOC_FIELD,
								posUdoc))
						.append(" and ")
						.append(DBUtils.generateEQTokenField(
								UINSTALACION_RELACION_FIELD,
								unidadInstalacionID));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public void deleteParteUdoc(final String idUnidadDoc, final int numParteUdoc) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("where ")
						.append(DBUtils.generateEQTokenField(PARTEUDOC_FIELD,
								numParteUdoc))
						.append(" and ")
						.append(DBUtils.generateEQTokenField(UDOC_FIELD,
								idUnidadDoc));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public void updateUnidadInstalacionUdoc(String udocID, int numeroParte,
			String unidadInstalacionID, int posUdocEnUI) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, udocID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(PARTEUDOC_FIELD,
						numeroParte));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(UINSTALACION_RELACION_FIELD, unidadInstalacionID);
		colsToUpdate.put(POS_UDOC_FIELD, String.valueOf(posUdocEnUI));
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void addUdocToUI(final IParteUnidadDocumentalVO parteUdoc) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, parteUdoc);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	public void dropByUdoc(final String udocID) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("where ").append(DBUtils
						.generateEQTokenField(UDOC_FIELD, udocID));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public List getSignaturasUdoc(String idUdoc) {
		StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD, idUdoc))
				.append(" order by ").append(SIGNATURA_COLUMN_NAME);

		final ArrayList rows = new ArrayList();
		try {
			DbOutputRecordSet rowSet = new AbstractDbOutputRecordSet() {
				DbOutputRecord aRow = new DbOutputRecord() {
					public void getStatementValues(DbOutputStatement stmt)
							throws Exception {
						rows.add(stmt.getShortText(1));
					}
				};

				public DbOutputRecord newRecord() throws Exception {
					return aRow;
				}
			};
			DbSelectFns.select(getConnection(), TABLE_NAME,
					SIGNATURA_COLUMN_NAME, qual.toString(), false, rowSet);
		} catch (Exception e) {
			throw new DBException(e);
		}
		return rows;
	}

	public int getNumUdocsUbicadas(final String idRelacionEntrega) {
		final MutableInt numUdocsUbicadas = new MutableInt(0);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuffer qual = new StringBuffer("where ").append(DBUtils
						.generateEQTokenField(ID_RELACION_FIELD,
								idRelacionEntrega));
				numUdocsUbicadas.setValue(DbSelectFns.selectCount(conn,
						TABLE_NAME, qual.toString()));
			}
		};
		command.execute();
		return numUdocsUbicadas.getValue();
	}

	public void updateDescripcion(String idUnidadDocumental, int numeroParte,
			String descripcion) {
		StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(UDOC_FIELD,
						idUnidadDocumental))
				.append(" and ")
				.append(DBUtils.generateEQTokenField(PARTEUDOC_FIELD,
						numeroParte));
		Map colToUpdate = Collections.singletonMap(DESCRIPCION_CONTENIDO_FIELD,
				descripcion);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transferencias.db.IUdocEnUIDBEntity#updatePosicionEnCaja2(int,
	 * java.lang.String, int)
	 */
	public void updatePosicionEnCaja2(int posicionActualUdoc,
			String uinstalacion, int nuevaPosicion) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(POS_UDOC_FIELD,
						posicionActualUdoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UINSTALACION_RELACION_FIELD, uinstalacion));
		Map colToUpdate = Collections.singletonMap(POS_UDOC_FIELD,
				String.valueOf(nuevaPosicion));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transferencias.db.IUdocEnUIDBEntity#countUdocsEnUi(java.lang.String)
	 */
	public int countUdocsEnUi(String idUInstalacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(UINSTALACION_RELACION_FIELD,
						idUInstalacion));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocEnUIDBEntity#countPartesUdoc(java.lang.String)
	 */
	public int countPartesUdoc(String idUnidadDocumental) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(UDOC_FIELD, idUnidadDocumental));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocEnUIDBEntity#fetchRowsByUInstalacion(java.lang
	 * .String, int[])
	 */
	public List fetchRowsByUInstalacion(String idUnidadInstalacion,
			int[] posiciones) {

		StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(
						UINSTALACION_RELACION_FIELD, idUnidadInstalacion))
				.append(" AND ")
				.append(DBUtils
						.generateInTokenField(POS_UDOC_FIELD, posiciones));

		StringBuffer orderBy = new StringBuffer(" order by ")
				.append(POSUDOC_COLUMN_NAME);

		return getPartesUnidadDocumental(qual.toString(), orderBy.toString());
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see transferencias.db.IUdocEnUIDBEntity#getListaPartes(java.lang.String)
	 */
	public List getListaPartes(String idUnidadDocumental) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(UDOC_FIELD, idUnidadDocumental));

		return getPartesUnidadDocumental(qual.toString(),
				DBUtils.generateOrderBy(POS_UDOC_FIELD));

	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocEnUIDBEntity#getListaPartesByEstado(java.lang.
	 * String, int[])
	 */
	public List getListaPartesByEstado(String idUnidadDocumental, int[] estados) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(UDOC_FIELD,
						idUnidadDocumental)).append(DBUtils.AND)
				.append(DBUtils.generateORTokens(ESTADO_COTEJO_FIELD, estados));
		return getPartesUnidadDocumental(qual.toString(),
				DBUtils.generateOrderBy(POS_UDOC_FIELD));
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUdocEnUIDBEntity#getRowByUdocPosicion(java.lang.String
	 * , int)
	 */
	public IParteUnidadDocumentalVO getRowByUdocPosicion(String idUinstalacion,
			int posicion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						UINSTALACION_RELACION_FIELD, idUinstalacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(POS_UDOC_FIELD, posicion));

		return (IParteUnidadDocumentalVO) getVO(qual.toString(), TABLE_NAME,
				TABLE_COLUMNS, ParteUnidadDocumentalVO.class);
	}

	/**
	 * Obtiene la posición máxima de las unidades documentales en la unidad de
	 * instalación para la relación de entrega que se le pasa como parámetro.
	 */
	public int maxPosUdocEnUI(String idUInstalacion, String idRelacion) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						UINSTALACION_RELACION_FIELD, idUInstalacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ID_RELACION_FIELD,
						idRelacion)).toString();
		return getMax(qual, TABLE_NAME, POS_UDOC_FIELD);
	}

}