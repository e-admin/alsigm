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

import org.apache.log4j.Logger;

import transferencias.model.EstadoREntrega;
import transferencias.vos.UnidadInstalacionReeaVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.util.IntervalOption;
import common.util.IntervalOptions;
import common.util.IntervalRangeOption;
import common.util.IntervalSimpleOption;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;
import deposito.db.ElementoAsignableDBEntity;
import deposito.db.ElementoNoAsignableDBEntity;

public class UnidadInstalacionReeaDBEntityImpl extends DBEntity implements
		IUnidadInstalacionReeaDBEntity {

	static Logger logger = Logger
			.getLogger(UnidadInstalacionReeaDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGTUINSTALACIONREEA";

	public static final String ID_UIDEPOSITO_COLUMNNAME = "iduideposito";
	public static final String ID_RELACIONENTREGA_COLUMN_NAME = "idrelentrega";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String ID_FORMATO_COLUMN_NAME = "idformato";
	public static final String ESTADO_COTEJO_COLUMN_NAME = "estadocotejo";
	public static final String NOTAS_COTEJO_COLUMN_NAME = "notascotejo";
	public static final String DEVOLUCION_COLUMN_NAME = "devolucion";
	public static final String PATH_DEPOSITO_ORIGEN_COLUMN_NAME = "pathdepositoorigen";
	public static final String SIGNATURA_ORIGEN_COLUMN_NAME = "signaturauiorigen";
	public static final String ID_ELEMPADRE_HUECO_ORIGEN_COLUMN_NAME = "idelemapadrehuecoorigen";
	public static final String NUMORDEN_HUECO_ORIGEN_COLUMN_NAME = "numordenhuecoorigen";
	public static final String SIGNATURA_COLUMN_NAME = "signaturaui";

	public static final DbColumnDef ID_UIDEPOSITO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_UIDEPOSITO_COLUMNNAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef ID_RELACIONENTREGA_FIELD = new DbColumnDef(
			null, TABLE_NAME, ID_RELACIONENTREGA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef ORDEN_FIELD = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef ID_FORMATO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_FORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef ESTADO_COTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COTEJO_COLUMN_NAME, DbDataType.LONG_INTEGER, 64,
			false);

	public static final DbColumnDef NOTAS_COTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOTAS_COTEJO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef DEVOLUCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DEVOLUCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef PATH_DEPOSITO_ORIGEN_FIELD = new DbColumnDef(
			null, TABLE_NAME, PATH_DEPOSITO_ORIGEN_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 512, false);

	public static final DbColumnDef SIGNATURA_ORIGEN_FIELD = new DbColumnDef(
			null, TABLE_NAME, SIGNATURA_ORIGEN_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 16, false);

	public static final DbColumnDef ID_ELEMPADREHUECOORIGEN_FIELD = new DbColumnDef(
			null, TABLE_NAME, ID_ELEMPADRE_HUECO_ORIGEN_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef NUMORDEN_HUECO_ORIGEN_FIELD = new DbColumnDef(
			null, TABLE_NAME, NUMORDEN_HUECO_ORIGEN_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef SIGNATURA_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURA_COLUMN_NAME, DbDataType.SHORT_TEXT, 16, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_UIDEPOSITO_FIELD,
			ID_RELACIONENTREGA_FIELD, ORDEN_FIELD, SIGNATURA_ORIGEN_FIELD,
			SIGNATURA_FIELD, ID_FORMATO_FIELD, ESTADO_COTEJO_FIELD,
			NOTAS_COTEJO_FIELD, DEVOLUCION_FIELD, PATH_DEPOSITO_ORIGEN_FIELD,
			ID_ELEMPADREHUECOORIGEN_FIELD, NUMORDEN_HUECO_ORIGEN_FIELD };

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

	public UnidadInstalacionReeaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UnidadInstalacionReeaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public int countUIsRelacion(String idRelacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_RELACIONENTREGA_FIELD, idRelacion));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public void insertRow(final UnidadInstalacionReeaVO unidadInstalacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				// unidadInstalacion.setId(GuidManager.generateGuid(conn));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, unidadInstalacion);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	private List getUnidadesInstalacion(String qual) {
		return getVOS(qual, TABLE_NAME, TABLE_COLUMNS,
				UnidadInstalacionReeaVO.class);
	}

	public List fetchRowsByIdRelacion(String idRelacion) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacion)).append("order by ")
				.append(ORDEN_COLUMN_NAME);
		return getUnidadesInstalacion(qual.toString());
	}

	public List getUnidadesInstalacionXIdRelacionEntreArchivos(String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacion)).append(
				DBUtils.generateOrderBy(ORDEN_FIELD));

		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				UnidadInstalacionReeaVO.class);
	}

	public void deleteRow(String idUnidadInstalacion) {
		final StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_UIDEPOSITO_FIELD,
						idUnidadInstalacion));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * Actualiza el estado y flag de devolución de un conjunto de unidades de
	 * instalación.
	 * 
	 * @param idsUInstalacion
	 *            Lista de identificadores de unidad de instalacion
	 * @param estado
	 *            Nuevo estado al que se pasan la unidades de instalacion
	 * @param devuelta
	 *            Indica si las unidades de instalación están devueltas.
	 */
	public void updateFieldEstado(String[] idsUInstalacion, int estado,
			boolean devuelta) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenField(ID_UIDEPOSITO_FIELD, idsUInstalacion));
		Map colToUpdate = new HashMap();
		colToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf(estado));
		colToUpdate.put(DEVOLUCION_FIELD, DBUtils.getStringValue(devuelta));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	private UnidadInstalacionReeaVO getUnidadInstalacion(String qual) {
		return (UnidadInstalacionReeaVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS,
				UnidadInstalacionReeaVO.class);
	}

	public UnidadInstalacionReeaVO fetchRowById(String idUinstalacion) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(ID_UIDEPOSITO_FIELD, idUinstalacion));
		return getUnidadInstalacion(qual.toString());
	}

	public void updateFieldEstadoYDevolverYNotasCotejo(String idUInstalacion,
			int estado, String devolver, String notasCotejo) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(ID_UIDEPOSITO_FIELD, idUInstalacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADO_COTEJO_FIELD, String.valueOf(estado));
		colsToUpdate.put(DEVOLUCION_FIELD, devolver);
		colsToUpdate.put(NOTAS_COTEJO_FIELD, notasCotejo);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void deleteXIdRelacion(final String idRelacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				final StringBuffer qual = new StringBuffer("WHERE ")
						.append(DBUtils.generateEQTokenField(
								ID_RELACIONENTREGA_FIELD, idRelacion));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionReeaDBEntity#countUdocsConEstado(
	 * java.lang.String, int[])
	 */
	public int countUinstConEstado(String idRelacion, int[] estadosCotejo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateORTokens(
								UdocEnUIDBEntityImpl.ESTADO_COTEJO_FIELD,
								estadosCotejo));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionReeaDBEntity#fetchRowBySignatura(
	 * java.lang.String)
	 */
	public UnidadInstalacionReeaVO fetchRowBySignatura(String signatura) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(SIGNATURA_ORIGEN_FIELD, signatura));
		return getUnidadInstalacion(qual.toString());
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionReeaDBEntity#fetchRowBySignaturaYArchivo
	 * (java.lang.String, java.lang.String)
	 */
	public UnidadInstalacionReeaVO fetchRowBySignaturaYArchivo(
			String signatura, String idArchivo) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(ID_RELACIONENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivo));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionReeaVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionReeaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionReeaDBEntity#fetchRowsByIdRelacion
	 * (java.lang.String, common.util.IntervalOptions)
	 */
	public List fetchRowsByIdRelacion(String idRelacionEntrega,
			IntervalOptions ordenes) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacionEntrega));

		if ((ordenes != null) && !ordenes.isAllItems()
				&& (ordenes.getOptions().size() > 0)) {
			IntervalOption intOpt;

			qual.append(" AND (");

			for (int i = 0; i < ordenes.getOptions().size(); i++) {
				intOpt = (IntervalOption) ordenes.getOptions().get(i);

				if (i > 0)
					qual.append(DBUtils.OR);

				if (intOpt.getType() == IntervalOption.SIMPLE_ITEM)
					qual.append(DBUtils.generateEQTokenField(ORDEN_FIELD,
							((IntervalSimpleOption) intOpt).getItem()));
				else // if (intOpt.getType() == IntervalOption.ITEM_RANGE)
				{
					qual.append("(");

					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMinItem()))
						qual.append(DBUtils.generateGTEQTokenField(ORDEN_FIELD,
								((IntervalRangeOption) intOpt).getMinItem()));

					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMaxItem())) {
						if (StringUtils
								.isNotBlank(((IntervalRangeOption) intOpt)
										.getMinItem()))
							qual.append(" AND ");

						qual.append(DBUtils.generateLTEQTokenField(ORDEN_FIELD,
								((IntervalRangeOption) intOpt).getMaxItem()));
					}

					qual.append(")");
				}
			}

			qual.append(")");
		}

		qual.append(DBUtils.generateOrderBy(ORDEN_FIELD));

		return getUnidadesInstalacion(qual.toString());
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionReeaDBEntity#updateFieldSignatura
	 * (java.lang.String, java.lang.String)
	 */
	public void updateFieldSignatura(String idUInstalacion, String signatura) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_UIDEPOSITO_FIELD, idUInstalacion));
		Map colToUpdate = new HashMap();
		colToUpdate.put(SIGNATURA_FIELD, signatura);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * transferencias.db.IUnidadInstalacionReeaDBEntity#updateFieldSignatura
	 * (java.lang.String, java.lang.String)
	 */
	public void updateFieldOrden(String idRelacion, String idUInstalacion,
			int orden) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_UIDEPOSITO_FIELD,
						idUInstalacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacion));
		Map colToUpdate = new HashMap();
		colToUpdate.put(ORDEN_FIELD, new Integer(orden));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public String getIdAsignablePadreXRelacionEntrega(String idRelacionEntrega) {
		String ret = null;
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacionEntrega)).toString();

		Object uInstalacionObj = getVO(qual, TABLE_NAME,
				new DbColumnDef[] { ID_ELEMPADREHUECOORIGEN_FIELD },
				UnidadInstalacionReeaVO.class);

		if (uInstalacionObj != null) {
			UnidadInstalacionReeaVO uInstalacionRee = (UnidadInstalacionReeaVO) uInstalacionObj;
			ret = uInstalacionRee.getIdElemaPadreHuecoOrigen();
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUnidadInstalacionReeaDBEntity#getUnidadesInstalacionXIdRelacionEntreArchivosByIds(java.lang.String,
	 *      java.lang.String[])
	 */
	public List getUnidadesInstalacionXIdRelacionEntreArchivosByIds(
			String idRelacion, String[] idsUIs) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ID_UIDEPOSITO_FIELD,
						idsUIs)).append(DBUtils.generateOrderBy(ORDEN_FIELD));

		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				UnidadInstalacionReeaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUnidadInstalacionReeaDBEntity#updatePathUInstalacionReea(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updatePathUInstalacionReea(final String idNoAsignable,
			final String pathAntiguo, final String pathNuevo) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbInputStatement stmt = new DbInputStatement();

				DbColumnDef columnaIdAsignable = new DbColumnDef("id",
						new TableDef(ElementoAsignableDBEntity.TABLE_NAME,
								ElementoAsignableDBEntity.TABLE_NAME),
						ElementoAsignableDBEntity.ID_COLUMN_NAME,
						ElementoAsignableDBEntity.CAMPO_ID.getDataType(), true);

				DbColumnDef columnaIdElemPadreAsignable = new DbColumnDef(
						"idPadre", new TableDef(
								ElementoAsignableDBEntity.TABLE_NAME,
								ElementoAsignableDBEntity.TABLE_NAME),
						ElementoAsignableDBEntity.IDELEMNPADRE_COLUMN_NAME,
						ElementoAsignableDBEntity.CAMPO_IDELEMNPADRE
								.getDataType(), true);

				ConsultaConnectBy consultaConnectBy = DbUtil
						.generateNativeSQLWithConnectBy(
								getConnection(),
								new TableDef(
										ElementoNoAsignableDBEntity.TABLE_NAME),
								ElementoNoAsignableDBEntity.CAMPO_ID,
								ElementoNoAsignableDBEntity.CAMPO_IDPADRE,
								new String[] { idNoAsignable }, null);

				StringBuffer selectClause = new StringBuffer(DBUtils.SELECT)
						.append(columnaIdAsignable)
						.append(DBUtils.FROM)
						.append(ElementoAsignableDBEntity.TABLE_NAME)
						.append(DBUtils.WHERE)
						.append(DBUtils.generateInTokenFieldSubQuery(
								columnaIdElemPadreAsignable,
								consultaConnectBy.getSqlClause()));

				StringBuffer whereUpdate = new StringBuffer(DBUtils.WHERE)
						.append(DBUtils.generateInTokenFieldSubQuery(
								ID_ELEMPADREHUECOORIGEN_FIELD,
								selectClause.toString()));

				consultaConnectBy.setWhereClause(whereUpdate.toString());

				String campoUpdate = DBUtils.getNativeReplaceSyntax(conn,
						PATH_DEPOSITO_ORIGEN_FIELD, pathAntiguo, pathNuevo);

				ConsultaConnectBy consultaUpdate = DbUtil
						.generateNativeSQLUpdateRecursive(getConnection(),
								new TableDef(TABLE_NAME),
								new String[] { campoUpdate }, consultaConnectBy);

				stmt.create(conn, consultaUpdate.getSqlClause());
				stmt.execute();
				stmt.release();
			}
		};
		command.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUnidadInstalacionReeaDBEntity#fetchRowBySignaturaYArchivoEnRENoValidada(java.lang.String,
	 *      java.lang.String)
	 */
	public UnidadInstalacionReeaVO fetchRowBySignaturaYArchivoEnRENoValidada(
			String signatura, String idArchivo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(ID_RELACIONENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionReeaVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionReeaVO.class);

	}

	public UnidadInstalacionReeaVO fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
			String signatura, String idArchivoReceptor, String idRelacion) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(SIGNATURA_FIELD, signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(ID_RELACIONENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivoReceptor))
				.append(DBUtils.AND)

				.append(DBUtils.generateNEQTokenField(ID_RELACIONENTREGA_FIELD,
						idRelacion))

				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UnidadInstalacionReeaVO) getVO(qual.toString(), tables,
				TABLE_COLUMNS, UnidadInstalacionReeaVO.class);
	}
}