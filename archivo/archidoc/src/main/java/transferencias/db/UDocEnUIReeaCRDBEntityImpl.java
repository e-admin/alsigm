package transferencias.db;

import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.UnidadDocumentalDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transferencias.vos.UDocEnUIReeaCRVO;
import transferencias.vos.UnidadDocumentalVO;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;
import common.vos.MinMaxVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UDocEnUIReeaCRDBEntityImpl extends DBEntity implements
		IUDocEnUIReeaCRDBEntity {

	public static final String TABLE_NAME = "ASGTUDOCENUIREEACR";

	public static final String ID_COLUMN_NAME = "ID";
	public static final String IDUDOCORIGEN_COLUMN_NAME = "IDUDOCORIGEN";
	public static final String IDRELENTREGA_COLUMN_NAME = "IDRELENTREGA";
	public static final String IDUNIDADDOC_COLUMN_NAME = "IDUNIDADDOC";
	public static final String IDUIREEACR_COLUMN_NAME = "IDUIREEACR";
	public static final String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
	public static final String POSUDOCENUI_COLUMN_NAME = "POSUDOCENUI";
	public static final String NUMPARTE_COLUMN_NAME = "NUMPARTE";
	public static final String UDOCCOMPLETA_COLUMN_NAME = "UDOCCOMPLETA";
	public static final String ASUNTO_COLUMN_NAME = "ASUNTO";
	public static final String NUMEXP_COLUMN_NAME = "NUMEXP";
	public static final String FECHAINI_COLUMN_NAME = "FECHAINI";
	public static final String FECHAFIN_COLUMN_NAME = "FECHAFIN";
	public static final String SIGNATURAUDOCORIGEN_COLUMN_NAME = "SIGNATURAUDOCORIGEN";
	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";

	public static final String TOTALPARTES_CALCULATED_COLUMNNAME = "TOTALPARTES";

	public static final String MIN_CALCULATED_COLUMNAME = "MIN";
	public static final String MAX_CALCULATED_COLUMNAME = "MAX";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDUDOCORIGEN_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUDOCORIGEN_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef IDRELENTREGA_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDRELENTREGA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef IDUNIDADDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUNIDADDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef IDUIREEACR_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUIREEACR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);

	public static final DbColumnDef POSUDOCENUI_FIELD = new DbColumnDef(null,
			TABLE_NAME, POSUDOCENUI_COLUMN_NAME, DbDataType.SHORT_INTEGER, 4,
			true);

	public static final DbColumnDef NUMPARTE_FIELD = new DbColumnDef(null,
			TABLE_NAME, NUMPARTE_COLUMN_NAME, DbDataType.SHORT_INTEGER, 3, true);

	public static final DbColumnDef UDOCCOMPLETA_FIELD = new DbColumnDef(null,
			TABLE_NAME, UDOCCOMPLETA_COLUMN_NAME, DbDataType.SHORT_TEXT, 1,
			true);

	public static final DbColumnDef ASUNTO_FIELD = new DbColumnDef(null,
			TABLE_NAME, ASUNTO_COLUMN_NAME, DbDataType.SHORT_TEXT, 1024, false);

	public static final DbColumnDef NUMEXP_FIELD = new DbColumnDef(null,
			TABLE_NAME, NUMEXP_COLUMN_NAME, DbDataType.SHORT_TEXT, 256, true);

	public static final DbColumnDef FECHAINI_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHAINI_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, true);

	public static final DbColumnDef FECHAFIN_FIELD = new DbColumnDef(null,
			TABLE_NAME, FECHAFIN_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, true);

	public static final DbColumnDef SIGNATURAUDOCORIGEN_FIELD = new DbColumnDef(
			null, TABLE_NAME, SIGNATURAUDOCORIGEN_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef DESCRIPCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 256,
			true);

	public static final DbColumnDef getMIN_FIELD() {
		String sqlColumna = DBUtils.generateMIN(POSUDOCENUI_FIELD,
				MIN_CALCULATED_COLUMNAME);

		return new DbColumnDef(MIN_CALCULATED_COLUMNAME, sqlColumna,
				DbDataType.SHORT_INTEGER);
	}

	public static final DbColumnDef getMAX_FIELD() {
		String sqlColumna = DBUtils.generateMAX(POSUDOCENUI_FIELD,
				MAX_CALCULATED_COLUMNAME);
		return new DbColumnDef(MAX_CALCULATED_COLUMNAME, sqlColumna,
				DbDataType.SHORT_INTEGER);
	}

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public static DbColumnDef NUMPARTES_CALCULATED() {
		/*
		 * ( SELECT COUNT(ID) FROM ASGTUDOCENUIREEACR ASGTUDOCENUIREEACR1 WHERE
		 * ASGTUDOCENUIREEACR.IDUNIDADDOC = ASGTUDOCENUIREEACR1.IDUNIDADDOC ) AS
		 * NUMPARTES
		 */

		TableDef tablaCount = new TableDef(TABLE_NAME, TABLE_NAME + "1");
		DbColumnDef columnaIDUNIDADDOCCount = new DbColumnDef(tablaCount,
				IDUNIDADDOC_FIELD);
		DbColumnDef columnaIDRELENTREGA = new DbColumnDef(tablaCount,
				IDRELENTREGA_FIELD);

		String sqlColumnaNumPartes = new StringBuffer(DBUtils.ABRIR_PARENTESIS)
				.append(DBUtils.SELECT)
				.append(DBUtils.getCountDefault())
				.append(DBUtils.FROM)
				.append(tablaCount.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField1(columnaIDUNIDADDOCCount,
						IDUNIDADDOC_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField1(columnaIDRELENTREGA,
						IDRELENTREGA_FIELD)).append(DBUtils.CERRAR_PARENTESIS)
				.append(DBUtils.AS).append(TOTALPARTES_CALCULATED_COLUMNNAME)
				.toString();

		return new DbColumnDef(TOTALPARTES_CALCULATED_COLUMNNAME,
				sqlColumnaNumPartes, DbDataType.SHORT_INTEGER);
	}

	public static final DbColumnDef[] COLS_DEFS = new DbColumnDef[] { ID_FIELD,
			IDUDOCORIGEN_FIELD, IDRELENTREGA_FIELD, IDUNIDADDOC_FIELD,
			IDUIREEACR_FIELD, SIGNATURAUDOC_FIELD, POSUDOCENUI_FIELD,
			NUMPARTE_FIELD, UDOCCOMPLETA_FIELD, ASUNTO_FIELD, NUMEXP_FIELD,
			FECHAINI_FIELD, FECHAFIN_FIELD, SIGNATURAUDOCORIGEN_FIELD,
			DESCRIPCION_FIELD };

	public static final DbColumnDef[] COLS_DEFS_SELECT = (DbColumnDef[]) ArrayUtils
			.add(COLS_DEFS, NUMPARTES_CALCULATED());

	/**
	 * @param dataSource
	 */
	public UDocEnUIReeaCRDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UDocEnUIReeaCRDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	private static String ROW_QUAL(String idUdoc) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_FIELD, idUdoc)).toString();
		return qual;
	}

	private static String ROW_QUAL(String[] idsUdoc) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateInTokenField(ID_FIELD, idsUdoc)).toString();
		return qual;
	}

	private static String ROW_QUAL_UI(String idUI) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(IDUIREEACR_FIELD, idUI))
				.toString();
		return qual;
	}

	private static String ROW_QUAL_IDRELACION(String idRelacion) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(IDRELENTREGA_FIELD, idRelacion))
				.toString();
		return qual;
	}

	private static String ROW_QUAL_IDRELACION_IDUNIDADDOC(String idRelEntrega,
			String idUnidadDoc) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						idUnidadDoc)).toString();
		return qual;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#deleteById(java.lang.String)
	 */
	public void deleteById(String idUDoc) {
		deleteVO(ROW_QUAL(idUDoc), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#deleteByIdRelacion(java.lang.String)
	 */
	public void deleteByIdRelacion(String idRelacion) {
		deleteVO(ROW_QUAL_IDRELACION(idRelacion), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#deleteByIdUI(java.lang.String)
	 */
	public void deleteByIdUI(String idUI) {
		deleteVO(ROW_QUAL_UI(idUI), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getById(java.lang.String)
	 */
	public UDocEnUIReeaCRVO getById(String idUDoc) {
		return (UDocEnUIReeaCRVO) getVO(ROW_QUAL(idUDoc), TABLE_NAME,
				COLS_DEFS_SELECT, UDocEnUIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getByIdUI(java.lang.String)
	 */
	public List getByIdUI(String idUI) {
		String qual = new StringBuffer(ROW_QUAL_UI(idUI)).append(
				DBUtils.generateOrderBy(POSUDOCENUI_FIELD)).toString();
		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getUdocsSinAsignarByIdRelacion(java.lang.String)
	 */
	public List getUdocsSinAsignarByIdRelacion(String idRelacion) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						IDUIREEACR_FIELD))
				.append(DBUtils.generateOrderBy(POSUDOCENUI_FIELD)).toString();
		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getUdocsXIdUinstalacion(java.lang.String)
	 */
	public List getUdocsXIdUinstalacion(String idUiCr) {
		/*
		 * SELECT A.IDUNIDADDOC AS ID, B.TITULO AS ASUNTO FROM ASGDUDOCENUI A,
		 * ASGFELEMENTOCF B WHERE A.IDUNIDADDOC = B.ID A.IDUINSTALACION =
		 * idUnidadInstalacion ORDER BY A.POSUDOCENUI
		 */

		DbColumnDef idUnidadDocumental = new DbColumnDef("ID", TABLE_NAME,
				IDUNIDADDOC_FIELD);
		DbColumnDef idAsunto = new DbColumnDef(
				"ASUNTO",
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD);

		String tables = DBUtils
				.generateTableSet(new String[] {
						TABLE_NAME,
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
						UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL });

		DbColumnDef[] COLS = { idUnidadDocumental, idAsunto,
				UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO };

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								IDUNIDADDOC_FIELD,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUIREEACR_FIELD, idUiCr))
				.append(DBUtils.generateOrderBy(POSUDOCENUI_FIELD));

		return getVOS(qual.toString(), tables, COLS, UnidadDocumentalVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getByIdRelacion(java.lang.String)
	 */
	public List getByIdRelacion(String idRelacion) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion))
				.toString();
		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#insert(transferencias.vos.UDocEnUIReeaCRVO)
	 */
	public void insert(final UDocEnUIReeaCRVO uDocEnUIReeaCRVO) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				uDocEnUIReeaCRVO.setId(getGuid(uDocEnUIReeaCRVO.getId()));
				int posUdocEnUI = getNextOrdenUI(uDocEnUIReeaCRVO
						.getIdUIReeaCR());
				uDocEnUIReeaCRVO.setPosUdocEnUI(posUdocEnUI);
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS, uDocEnUIReeaCRVO);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLS_DEFS), inputRecord);
			}
		};
		command.execute();
	}

	private int getNextOrdenUI(String idUI) {
		int pos = getMax(ROW_QUAL_UI(idUI), TABLE_NAME, POSUDOCENUI_FIELD);
		return pos + 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#updatePosicion(java.lang.String,
	 *      int)
	 */
	public void updatePosicion(String idUdoc, int posicion) {
		updateCampoPosicion(posicion, ROW_QUAL(idUdoc));
	}

	private void updateCampoPosicion(int posicion, String qual) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(POSUDOCENUI_FIELD, new Integer(posicion));
		updateFields(qual, colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getUDocsSinUI(java.lang.String)
	 */
	public List getUDocsSinUI(String idRelacion) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						IDUIREEACR_FIELD))
				.append(DBUtils.generateOrderBy(POSUDOCENUI_FIELD)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getUDocsSinUI(java.lang.String)
	 */
	public List getUDocsSinUIOrderBySignatura(String idRelacion) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						IDUIREEACR_FIELD))
				.append(DBUtils.generateOrderBy(SIGNATURAUDOCORIGEN_FIELD))
				.toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getPosicionesMinMax(java.lang.String,
	 *      java.lang.String)
	 */
	public MinMaxVO getPosicionesMinMax(String idUI, String[] idsUdocs) {

		DbColumnDef[] COLS = new DbColumnDef[] { getMIN_FIELD(), getMAX_FIELD() };

		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(ID_FIELD, idsUdocs))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNotNullCondition(getConnection(),
						POSUDOCENUI_FIELD)).append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUIREEACR_FIELD, idUI))
				.append(DBUtils.generateGroupBy(IDUIREEACR_FIELD)).toString();

		return (MinMaxVO) getVO(qual, TABLE_NAME, COLS, MinMaxVO.class);
	}

	public void updatePosiciones(String idUI, String[] idsUdocs,
			final int incremento) {

		final String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(ID_FIELD, idsUdocs))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUIREEACR_FIELD, idUI))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbUpdateFns.incrementField(conn, TABLE_NAME,
						POSUDOCENUI_COLUMN_NAME, incremento, qual);
			}
		};
		command.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#updatePosicionElemento(java.lang.String,
	 *      java.lang.String[], int, int)
	 */
	public void updatePosicionElemento(String idUI,
			String[] idsUdocsSeleccionadas, int posicionAnterior,
			int posicionNueva) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(POSUDOCENUI_FIELD,
						posicionAnterior))
				.append(DBUtils.AND)
				.append(DBUtils.generateNotInTokenField(ID_FIELD,
						idsUdocsSeleccionadas)).append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUIREEACR_FIELD, idUI))
				.toString();

		updateCampoPosicion(posicionNueva, qual);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#updateUIReaCRAndPosUdoc(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateUIReaCRAndPosUdoc(String idUdoc, String idUI) {
		Map colsToUpdate = new HashMap();
		if (StringUtils.isNotEmpty(idUI)) {
			int posicionSiguiente = getNextOrdenUI(idUI);
			Integer posUdocEnUi = new Integer(posicionSiguiente);
			colsToUpdate.put(POSUDOCENUI_FIELD, posUdocEnUi);
		}
		colsToUpdate.put(IDUIREEACR_FIELD, idUI);
		updateFields(ROW_QUAL(idUdoc), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#updateSignatura(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateSignatura(String idUdoc, String signatura) {
		String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_FIELD, idUdoc)).toString();

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(SIGNATURAUDOC_FIELD, signatura);
		updateFields(qual, colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getCountPartes(java.lang.String,
	 *      java.lang.String)
	 */
	public int getCountPartes(String idRelEntrega, String idUdoc) {

		String countClause = new StringBuffer(DBUtils.SELECT)
				.append(DBUtils.COUNT).append(DBUtils.ABRIR_PARENTESIS)
				.append(NUMPARTE_FIELD).append(DBUtils.CERRAR_PARENTESIS)
				.toString();
		String fromClause = new StringBuffer(DBUtils.FROM).append(TABLE_NAME)
				.toString();
		String whereClause = new StringBuffer(ROW_QUAL_IDRELACION(idRelEntrega))
				.toString();

		ConsultaConnectBy consultaConnectBy = new ConsultaConnectBy();
		consultaConnectBy.setSelectCountClause(countClause);
		consultaConnectBy.setFromClause(fromClause);
		consultaConnectBy.setWhereClause(whereClause);

		return getVOCountGeneral(consultaConnectBy);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getCountUDocsSinUI(java.lang.String)
	 */
	public int getCountUDocsSinUI(String idRelacion) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateIsNullCondition(getConnection(),
						IDUIREEACR_FIELD)).toString();

		return getVOCount(qual, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getCountUDocsUI(java.lang.String)
	 */
	public int getCountUDocsUI(String idUI) {
		return getVOCount(ROW_QUAL_UI(idUI), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getCountUDocs(java.lang.String)
	 */
	public int getCountUDocs(String idRelEntrega) {
		return getVOCount(ROW_QUAL_IDRELACION(idRelEntrega), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#updateDescripcion(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateDescripcion(String idUdoc, String descripcion) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(DESCRIPCION_FIELD, descripcion);
		updateFields(ROW_QUAL(idUdoc), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getNumParteSiguiente(java.lang.String,
	 *      java.lang.String)
	 */
	public int getNumParteSiguiente(String idRelEntrega, String idUnidadDoc) {
		return getMax(
				ROW_QUAL_IDRELACION_IDUNIDADDOC(idRelEntrega, idUnidadDoc),
				TABLE_NAME, NUMPARTE_FIELD) + 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#updateIndicadorCompleta(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	public void updateIndicadorCompleta(String idRelEntrega,
			String idUnidadDoc, boolean udocCompleta) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(UDOCCOMPLETA_FIELD,
				DBUtils.getStringValue(udocCompleta));
		updateFields(
				ROW_QUAL_IDRELACION_IDUNIDADDOC(idRelEntrega, idUnidadDoc),
				colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param udocCompleta
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#updateNumParte(java.lang.String,
	 *      int)
	 */
	public void updateNumParte(String id, int numParte, boolean udocCompleta) {
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(NUMPARTE_FIELD, new Integer(numParte));
		colsToUpdate.put(UDOCCOMPLETA_FIELD,
				DBUtils.getStringValue(udocCompleta));
		updateFields(ROW_QUAL(id), colsToUpdate, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getByIdUDoc(java.lang.String,
	 *      java.lang.String)
	 */
	public List getByIdUDoc(String idRelEntrega, String idUnidadDoc) {
		DbColumnDef[] COLS_DEFS_ORDER = new DbColumnDef[] {
				SIGNATURAUDOCORIGEN_FIELD, NUMPARTE_FIELD };
		String qual = new StringBuffer(ROW_QUAL_IDRELACION_IDUNIDADDOC(
				idRelEntrega, idUnidadDoc)).append(
				DBUtils.generateOrderBy(COLS_DEFS_ORDER)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getTotalPartesRestantes(java.lang.String,
	 *      java.lang.String, java.lang.Strign[])
	 */
	public int getTotalPartesRestantes(String idRelEntrega, String idUnidadDoc,
			String[] idsEliminar) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION_IDUNIDADDOC(
				idRelEntrega, idUnidadDoc)).append(DBUtils.AND)
				.append(DBUtils.generateNotInTokenField(ID_FIELD, idsEliminar))
				.toString();

		return getVOCount(qual, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#deleteByIds(java.lang.String[])
	 */
	public void deleteByIds(String[] idsUDoc) {
		deleteVO(ROW_QUAL(idsUDoc), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUDocEnUIReeaCRDBEntity#getPartesUDocByIdsUinstalacion(java.lang.String,
	 *      java.lang.String[])
	 */
	public List getPartesUDocByIdsUinstalacion(String idRelEntrega,
			String[] idsUIs) {

		String subquery = new StringBuffer(DBUtils.SELECT)
				.append(UDocReeaCRDBEntityImpl.ID_FIELD)
				.append(DBUtils.FROM)
				.append(UDocReeaCRDBEntityImpl.TABLE_NAME)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(
						UDocReeaCRDBEntityImpl.IDUIDEPOSITO_FIELD, idsUIs))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								UDocReeaCRDBEntityImpl.IDRELENTREGA_FIELD,
								idRelEntrega)).toString();

		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDRELENTREGA_FIELD,
						idRelEntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenFieldSubQuery(
						IDUDOCORIGEN_FIELD, subquery)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	public List getUDocsByIdRelacionOrderBySignatura(String idRelacion) {
		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion)).append(
				DBUtils.generateOrderBy(SIGNATURAUDOCORIGEN_FIELD)).toString();
		return getVOS(qual, TABLE_NAME, COLS_DEFS_SELECT,
				UDocEnUIReeaCRVO.class);
	}

	public List getUDocsByIdRelacionGroupByUnidadDoc(String idRelacion) {
		/*
		 * SELECT
		 * ASGTUDOCENUIREEACR.IDRELENTREGA,ASGTUDOCENUIREEACR.IDUNIDADDOC,
		 * ASGTUDOCENUIREEACR.ASUNTO,
		 * ASGTUDOCENUIREEACR.NUMEXP,ASGTUDOCENUIREEACR
		 * .FECHAINI,ASGTUDOCENUIREEACR.FECHAFIN FROM ASGTUDOCREEACR
		 * ASGTUDOCREEACR, ASGTUDOCENUIREEACR ASGTUDOCENUIREEACR WHERE
		 * ASGTUDOCENUIREEACR.IDRELENTREGA= '0c4c555497980000000000000000005e'
		 * AND ASGTUDOCREEACR.IDUNIDADDOC = ASGTUDOCENUIREEACR.IDUNIDADDOC GROUP
		 * BY ASGTUDOCENUIREEACR.IDRELENTREGA,ASGTUDOCENUIREEACR.IDUNIDADDOC,
		 * ASGTUDOCENUIREEACR.ASUNTO, ASGTUDOCENUIREEACR.NUMEXP,
		 * ASGTUDOCENUIREEACR.FECHAINI, ASGTUDOCENUIREEACR.FECHAFIN
		 */

		DbColumnDef[] cols = new DbColumnDef[] { IDUNIDADDOC_FIELD,
				ASUNTO_FIELD, NUMEXP_FIELD, FECHAINI_FIELD, FECHAFIN_FIELD, };

		String qual = new StringBuffer(ROW_QUAL_IDRELACION(idRelacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(IDUNIDADDOC_FIELD,
						UDocReeaCRDBEntityImpl.IDUNIDADDOC_FIELD))
				.append(DBUtils.generateGroupBy(cols)).toString();

		String[] tables = new String[] {
				new TableDef(UDocReeaCRDBEntityImpl.TABLE_NAME)
						.getDeclaration(),
				new TableDef(TABLE_NAME).getDeclaration() };

		return getVOS(qual, DBUtils.generateTableSet(tables), cols,
				UDocEnUIReeaCRVO.class);
	}

}