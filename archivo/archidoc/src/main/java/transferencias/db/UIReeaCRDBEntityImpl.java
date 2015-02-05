/**
 *
 */
package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transferencias.model.EstadoREntrega;
import transferencias.vos.UIReeaCRVO;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.IntervalOption;
import common.util.IntervalOptions;
import common.util.IntervalRangeOption;
import common.util.IntervalSimpleOption;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UIReeaCRDBEntityImpl extends DBEntity implements IUIReeaCRDBEntity {

	public static String TABLE_NAME = "ASGTUIREEACR";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String IDRELENTREGA_COLUMN_NAME = "IDRELENTREGA";
	public static final String IDUIDEPOSITO_COLUMN_NAME = "IDUIDEPOSITO";
	public static final String SIGNATURAUI_COLUMN_NAME = "SIGNATURAUI";
	public static final String IDFORMATO_COLUMN_NAME = "IDFORMATO";
	public static final String ORDEN_COLUMN_NAME = "ORDEN";
	public static final String ESTADOCOTEJO_COLUMN_NAME = "ESTADOCOTEJO";
	public static final String NOTASCOTEJO_COLUMN_NAME = "NOTASCOTEJO";
	public static final String DEVOLUCION_COLUMN_NAME = "DEVOLUCION";
	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDRELENTREGA_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDRELENTREGA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef IDUIDEPOSITO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUIDEPOSITO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);

	public static final DbColumnDef SIGNATURAUI_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUI_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);

	public static final DbColumnDef IDFORMATO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDFORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef ORDEN_FIELD = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef ESTADOCOTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ESTADOCOTEJO_COLUMN_NAME, DbDataType.SHORT_INTEGER,
			true);

	public static final DbColumnDef NOTASCOTEJO_FIELD = new DbColumnDef(null,
			TABLE_NAME, NOTASCOTEJO_COLUMN_NAME, DbDataType.SHORT_TEXT, 256,
			true);

	public static final DbColumnDef DEVOLUCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DEVOLUCION_COLUMN_NAME, DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef DESCRIPCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 256,
			true);

	public static final DbColumnDef[] COLS_DEFS = new DbColumnDef[] { ID_FIELD,
			IDRELENTREGA_FIELD, IDUIDEPOSITO_FIELD, SIGNATURAUI_FIELD,
			IDFORMATO_FIELD, ORDEN_FIELD, ESTADOCOTEJO_FIELD,
			NOTASCOTEJO_FIELD, DEVOLUCION_FIELD, DESCRIPCION_FIELD };

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
	public UIReeaCRDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UIReeaCRDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#delete(java.lang.String)
	 */
	public void delete(String idRelacion, String idUI) {
		deleteVO(ROW_QUAL_IDRELACION_IDUI(idRelacion, idUI), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#deleteByIdRelacion(java.lang.String)
	 */
	public void deleteByIdRelacion(String idRelacion) {
		deleteVO(ROW_QUAL_IDRELACION(idRelacion), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getById(java.lang.String)
	 */
	public UIReeaCRVO getById(String idUI) {
		return (UIReeaCRVO) getVO(ROW_QUAL(idUI), TABLE_NAME, COLS_DEFS,
				UIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getByIdRelacion(java.lang.String)
	 */
	public List getByIdRelacion(String idRelacion) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion)).append(
				DBUtils.generateOrderBy(ORDEN_FIELD)).toString();
		return getVOS(qual, TABLE_NAME, COLS_DEFS, UIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#fetchRowBySignaturaYArchivo(java.lang.String,
	 *      java.lang.String)
	 */
	public UIReeaCRVO fetchRowBySignaturaYArchivo(String signatura,
			String idArchivo) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUI_FIELD,
						signatura))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDRELENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivo));

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UIReeaCRVO) getVO(qual.toString(), tables, COLS_DEFS,
				UIReeaCRVO.class);
	}

	public UIReeaCRVO fetchRowBySignatura(String signatura) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(SIGNATURAUI_FIELD, signatura));

		return (UIReeaCRVO) getVO(qual.toString(), TABLE_NAME, COLS_DEFS,
				UIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getCountByEstadosCotejo(java.lang.String,
	 *      java.lang.String[])
	 */
	public int getCountByEstadosCotejo(String idRelEntrega, int[] estadosCotejo) {
		return getVOCount(ROW_QUAL_ESTADOS(idRelEntrega, estadosCotejo),
				TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#insert(transferencias.vos.UIReeaCRVO)
	 */
	public void insert(final UIReeaCRVO uiReeaCRVO) {

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				uiReeaCRVO.setId(getGuid(uiReeaCRVO.getId()));
				int orden = getNextOrdenUI(uiReeaCRVO.getIdRelEntrega());
				uiReeaCRVO.setOrden(orden);
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, uiReeaCRVO);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), inputRecord);
			}
		};
		command.execute();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#update(transferencias.vos.UIReeaCRVO)
	 */
	public void update(UIReeaCRVO uiReeaCRVO) {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#updateCotejo(java.lang.String,
	 *      java.lang.Integer, java.lang.String, java.lang.String)
	 */
	public void updateCotejo(String idUI, Integer estadoCotejo,
			String notasCotejo, String devolucion) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ESTADOCOTEJO_FIELD, estadoCotejo);
		colsToUpdate.put(NOTASCOTEJO_FIELD, notasCotejo);
		colsToUpdate.put(DEVOLUCION_FIELD, devolucion);
		updateFields(ROW_QUAL(idUI), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#updateOrden(java.lang.String,
	 *      java.lang.Integer)
	 */
	public void updateOrden(String idUI, Integer orden) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(ORDEN_FIELD, orden);
		updateFields(ROW_QUAL(idUI), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getCountByRelacion(java.lang.String)
	 */
	public int getCountByRelacion(String idRelEntrega) {
		return getVOCount(ROW_QUAL_IDRELACION(idRelEntrega), TABLE_NAME);
	}

	private static String ROW_QUAL(String idUI) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_FIELD, idUI)).toString();
		return qual;
	};

	private static String ROW_QUAL_IDRELACION_IDUI(String idRelacion,
			String idUI) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelacion)).append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(ID_FIELD, idUI))
				.toString();
		return qual;
	};

	private static String ROW_QUAL_IDRELACION_IDUI(String idRelacion,
			String[] idUI) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelacion)).append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ID_FIELD, idUI))
				.toString();
		return qual;
	};

	private static String ROW_QUAL_IDRELACION(String idRelacion) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(IDRELENTREGA_FIELD, idRelacion))
				.toString();
		return qual;
	};

	private static String ROW_QUAL_ESTADOS(String idRelEntrega,
			int[] estadosCotejo) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(ESTADOCOTEJO_FIELD,
						estadosCotejo)).toString();
		return qual;
	}

	private int getNextOrdenUI(String idRelacion) {
		int pos = getMax(ROW_QUAL_IDRELACION(idRelacion), TABLE_NAME,
				ORDEN_FIELD);
		return pos + 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getCountUDocs(java.lang.String)
	 */
	public int getCountUDocs(String idRelacion, String idUI) {
		return getVOCount(ROW_QUAL(idUI), TABLE_NAME);
	}

	public List getUIsWithUDocsCorrectas(String idRelEntrega) {
		return getVOS(getQualUIsWithUDocsCorrectas(idRelEntrega), TABLE_NAME,
				COLS_DEFS, UIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getCountUIsWithUDocsCorrectas(java.lang.String)
	 */
	public int getCountUIsWithUDocsCorrectas(String idRelEntrega) {
		return getVOCount(getQualUIsWithUDocsCorrectas(idRelEntrega),
				TABLE_NAME);
	}

	public String getQualUIsWithUDocsCorrectas(String idRelEntrega) {
		String subquery = getQualUIsWithUDocsMayor(idRelEntrega, "1");
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateInTokenFieldSubQuery(ID_FIELD, subquery))
				.toString();

		return qual.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getUIsVacias(java.lang.String)
	 */
	public List getUIsVacias(String idRelEntrega) {
		String subquery = getQualUIsWithUDocsMayor(idRelEntrega, "0");
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateNotInTokenFieldSubQuery(ID_FIELD,
						subquery)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, UIReeaCRVO.class);
	}

	private String getQualUIsWithUDocsMayor(String idRelEntrega,
			String numUdocsUI) {
		String subquery = new StringBuffer(DBUtils.SELECT)
				.append(UDocEnUIReeaCRDBEntityImpl.IDUIREEACR_FIELD)
				.append(DBUtils.FROM)
				.append(UDocEnUIReeaCRDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						UDocEnUIReeaCRDBEntityImpl.IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils
						.generateGroupBy(UDocEnUIReeaCRDBEntityImpl.IDUIREEACR_FIELD))
				.append(DBUtils.HAVING)
				.append(DBUtils
						.getCount(UDocEnUIReeaCRDBEntityImpl.IDUIREEACR_FIELD))
				.append(Constants.MAYOR).append(numUdocsUI).toString();
		return subquery;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getCountByIdRelacion(java.lang.String)
	 */
	public int getCountByIdRelacion(String idRelEntrega) {
		return getVOCount(ROW_QUAL_IDRELACION(idRelEntrega), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#updateDescripcion(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateDescripcion(String idUI, String descripcion) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(DESCRIPCION_FIELD, descripcion);
		updateFields(ROW_QUAL(idUI), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#deleteByIdUIDeposito(java.lang.String,
	 *      java.lang.String[])
	 */
	public void deleteByIdUIDeposito(String idRelacion, String[] idsUIDeposito) {
		deleteVO(ROW_QUAL_IDRELACION_IDUI(idRelacion, idsUIDeposito),
				TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#updateSignatura(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateSignatura(String idUI, String signaturaUI) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(SIGNATURAUI_FIELD, signaturaUI);
		updateFields(ROW_QUAL(idUI), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#updateFieldEstado(java.lang.String[],
	 *      int, boolean)
	 */
	public void updateFieldEstado(String[] idsUI, int estado, boolean devuelta) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenField(ID_FIELD, idsUI));
		Map colToUpdate = new HashMap();
		colToUpdate.put(ESTADOCOTEJO_FIELD, String.valueOf(estado));
		colToUpdate.put(DEVOLUCION_FIELD, DBUtils.getStringValue(devuelta));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#updateFieldEstado(java.lang.String,
	 *      int)
	 */
	public void updateFieldEstado(String idUI, int estado) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idUI));
		Map colToUpdate = new HashMap();
		colToUpdate.put(ESTADOCOTEJO_FIELD, String.valueOf(estado));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#getByIdRelacion(java.lang.String,
	 *      common.util.IntervalOptions)
	 */
	public List getByIdRelacion(String idRelacion, IntervalOptions ordenes) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDRELENTREGA_FIELD, idRelacion));

		if ((ordenes != null) && !ordenes.isAllItems()
				&& (ordenes.getOptions().size() > 0)) {
			IntervalOption intOpt;
			qual.append(DBUtils.AND).append(DBUtils.ABRIR_PARENTESIS);
			for (int i = 0; i < ordenes.getOptions().size(); i++) {
				intOpt = (IntervalOption) ordenes.getOptions().get(i);
				if (i > 0) {
					qual.append(DBUtils.OR);
				}

				if (intOpt.getType() == IntervalOption.SIMPLE_ITEM) {
					qual.append(DBUtils.generateEQTokenField(ORDEN_FIELD,
							((IntervalSimpleOption) intOpt).getItem()));
				} else {
					qual.append(DBUtils.ABRIR_PARENTESIS);
					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMinItem())) {
						qual.append(DBUtils.generateGTEQTokenField(ORDEN_FIELD,
								((IntervalRangeOption) intOpt).getMinItem()));
					}

					if (StringUtils.isNotBlank(((IntervalRangeOption) intOpt)
							.getMaxItem())) {
						if (StringUtils
								.isNotBlank(((IntervalRangeOption) intOpt)
										.getMinItem())) {
							qual.append(DBUtils.AND);
						}
						qual.append(DBUtils.generateLTEQTokenField(ORDEN_FIELD,
								((IntervalRangeOption) intOpt).getMaxItem()));
					}
					qual.append(DBUtils.CERRAR_PARENTESIS);
				}
			}
			qual.append(DBUtils.CERRAR_PARENTESIS);
		}
		qual.append(DBUtils.generateOrderBy(ORDEN_FIELD));
		return getUnidadesInstalacion(qual.toString());
	}

	private List getUnidadesInstalacion(String qual) {
		return getVOS(qual, TABLE_NAME, COLS_DEFS, UIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#updateFieldIdUIDeposito(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateFieldIdUIDeposito(String idUI, String idUIDeposito) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(ID_FIELD, idUI));
		Map colToUpdate = new HashMap();
		colToUpdate.put(IDUIDEPOSITO_FIELD, String.valueOf(idUIDeposito));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUIReeaCRDBEntity#fetchRowBySignaturaYArchivoEnRENoValidada(java.lang.String,
	 *      java.lang.String)
	 */
	public UIReeaCRVO fetchRowBySignaturaYArchivoEnRENoValidada(
			String signatura, String idArchivo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(SIGNATURAUI_FIELD,
						signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(IDRELENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivo))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))

		;

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UIReeaCRVO) getVO(qual.toString(), tables, COLS_DEFS,
				UIReeaCRVO.class);
	}

	public UIReeaCRVO fetchRowBySignaturaYArchivoEnRENoValidadaOtraRelacion(
			String signatura, String idArchivoReceptor, String idRelacion) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(SIGNATURAUI_FIELD,
						signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(IDRELENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								RelacionEntregaDBEntityBaseImpl.CAMPO_IDARCHIVORECEPTOR,
								idArchivoReceptor))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(IDRELENTREGA_FIELD,
						idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))

		;

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UIReeaCRVO) getVO(qual.toString(), tables, COLS_DEFS,
				UIReeaCRVO.class);

	}

	public UIReeaCRVO fetchRowBySignaturaEnRENoValidada(String signatura) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(SIGNATURAUI_FIELD,
						signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(IDRELENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))

		;

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UIReeaCRVO) getVO(qual.toString(), tables, COLS_DEFS,
				UIReeaCRVO.class);
	}

	public UIReeaCRVO fetchRowBySignaturaEnRENoValidadaOtraRelacion(
			String signatura, String idRelacion) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(SIGNATURAUI_FIELD,
						signatura))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(IDRELENTREGA_FIELD,
						RelacionEntregaDBEntityBaseImpl.CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(IDRELENTREGA_FIELD,
						idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(
						RelacionEntregaDBEntityBaseImpl.CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))

		;

		String tables = DBUtils.generateTableSet(new String[] { TABLE_NAME,
				RelacionEntregaDBEntityBaseImpl.TABLE_NAME });
		return (UIReeaCRVO) getVO(qual.toString(), tables, COLS_DEFS,
				UIReeaCRVO.class);

	}

}