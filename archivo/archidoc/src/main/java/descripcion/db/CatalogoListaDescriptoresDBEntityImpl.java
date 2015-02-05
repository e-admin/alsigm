package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.util.ArrayUtils;

import descripcion.vos.ListaDescrVO;
import docelectronicos.db.DocFichaDBEntityImpl;

/**
 * DBEntity para acceder a la tabla ADCTLGLISTAD.
 */
public class CatalogoListaDescriptoresDBEntityImpl extends DBEntity implements
		ICatalogoListaDescriptoresDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADCTLGLISTAD";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "ID";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String TIPO_COLUMN_NAME = "TIPO";
	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";
	public static final String TIPO_DESCRIPTOR_COLUMN_NAME = "TIPODESCRIPTOR";
	public static final String ID_FICHA_DESCR_PREF_COLUMN_NAME = "IDFICHADESCRPREF";
	public static final String ID_FICHA_CLF_DOC_PREF_COLUMN_NAME = "IDFICHACLFDOCPREF";
	public static final String ID_REP_ECM_PREF_COLUMN_NAME = "IDREPECMPREF";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME, TIPO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_TIPO_DESCRIPTOR = new DbColumnDef(
			null, TABLE_NAME, TIPO_DESCRIPTOR_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_ID_FICHA_DESCR_PREF = new DbColumnDef(
			null, TABLE_NAME, ID_FICHA_DESCR_PREF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_ID_FICHA_CLF_DOC_PREF = new DbColumnDef(
			null, TABLE_NAME, ID_FICHA_CLF_DOC_PREF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_ID_REP_ECM_PREF = new DbColumnDef(
			null, TABLE_NAME, ID_REP_ECM_PREF_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, true);

	// Campo para la join con ADDESCRIPTOR
	public static final DbColumnDef CAMPO_NOMBRE_LISTA = new DbColumnDef(
			"nombreLista", TABLE_NAME, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COLS_DEFS = { CAMPO_ID, CAMPO_NOMBRE,
			CAMPO_TIPO, CAMPO_DESCRIPCION, CAMPO_TIPO_DESCRIPTOR,
			CAMPO_ID_FICHA_DESCR_PREF, CAMPO_ID_FICHA_CLF_DOC_PREF,
			CAMPO_ID_REP_ECM_PREF };

	/** Lista de nombres de columnas. */
	public static final String COL_NAMES = DbUtil.getColumnNames(COLS_DEFS);

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
	public CatalogoListaDescriptoresDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public CatalogoListaDescriptoresDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista descriptora.
	 * 
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptora(String id) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		return (ListaDescrVO) getVO(qual, TABLE_NAME, COLS_DEFS,
				ListaDescrVO.class);
	}

	/**
	 * Obtiene la lista descriptora con información extendida.
	 * 
	 * @param id
	 *            Identificador de la lista descriptora.
	 * @return Lista descriptora.
	 */
	public ListaDescrVO getListaDescriptoraExt(String id) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));

		TableDef tablaListaDescriptora = new TableDef(TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] {
				new JoinDefinition(new DbColumnDef(tablaListaDescriptora,
						CAMPO_ID_FICHA_CLF_DOC_PREF), new DbColumnDef(
						new TableDef(DocFichaDBEntityImpl.TABLE_NAME),
						DocFichaDBEntityImpl.CAMPO_ID)),
				new JoinDefinition(new DbColumnDef(tablaListaDescriptora,
						CAMPO_ID_FICHA_DESCR_PREF), new DbColumnDef(
						new TableDef(FichaDBEntityImpl.TABLE_NAME),
						FichaDBEntityImpl.CAMPO_ID)) };
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
				tablaListaDescriptora, joins));
		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] {
						new DbColumnDef(new TableDef(
								FichaDBEntityImpl.TABLE_NAME),
								FichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DESCR_PREF),
						new DbColumnDef(new TableDef(
								DocFichaDBEntityImpl.TABLE_NAME),
								DocFichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DOC) },
						COLS_DEFS);

		return (ListaDescrVO) getVO(qual.toString(), sqlFrom.toString(),
				COLS_DEF_QUERY, ListaDescrVO.class);
	}

	/**
	 * Obtiene la lista de listas descriptoras.
	 * 
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras() {
		String qual = new StringBuffer()
				.append(DBUtils.ORDER_BY)
				.append(DBUtils
						.getQualifiedColumnName(TABLE_NAME, CAMPO_NOMBRE))
				.toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ListaDescrVO.class);
	}

	/**
	 * Obtiene la lista de listas descriptoras.
	 * 
	 * @param ids
	 *            Array de identificadores de listas descriptoras.
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptoras(String[] ids) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID, ids))
				.append(DBUtils.ORDER_BY)
				.append(DBUtils
						.getQualifiedColumnName(TABLE_NAME, CAMPO_NOMBRE))
				.toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ListaDescrVO.class);
	}

	/**
	 * Obtiene la lista de listas descriptoras con información extendida.
	 * 
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptorasExt() {
		// Condición de la consulta
		StringBuffer qual = new StringBuffer();
		qual.append(" ORDER BY ")
				.append(DBUtils
						.getQualifiedColumnName(TABLE_NAME, CAMPO_NOMBRE))
				.toString();

		// Mapeo de campos y tablas
		TableDef tablaListaDescriptora = new TableDef(TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] {
				new JoinDefinition(new DbColumnDef(tablaListaDescriptora,
						CAMPO_ID_FICHA_CLF_DOC_PREF), new DbColumnDef(
						new TableDef(DocFichaDBEntityImpl.TABLE_NAME),
						DocFichaDBEntityImpl.CAMPO_ID)),
				new JoinDefinition(new DbColumnDef(tablaListaDescriptora,
						CAMPO_ID_FICHA_DESCR_PREF), new DbColumnDef(
						new TableDef(FichaDBEntityImpl.TABLE_NAME),
						FichaDBEntityImpl.CAMPO_ID)) };

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(
				tablaListaDescriptora, joins));
		DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils
				.concat(new DbColumnDef[] {
						new DbColumnDef(new TableDef(
								FichaDBEntityImpl.TABLE_NAME),
								FichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DESCR_PREF),
						new DbColumnDef(new TableDef(
								DocFichaDBEntityImpl.TABLE_NAME),
								DocFichaDBEntityImpl.CAMPO_NOMBRE_FICHA_DOC) },
						COLS_DEFS);

		return getVOS(qual.toString(), sqlFrom.toString(), COLS_DEF_QUERY,
				ListaDescrVO.class);
	}

	/**
	 * Obtiene las listas descriptoras en función del tipo de descriptores.
	 * 
	 * @param tipoDescriptor
	 *            Tipo de descriptores ({@link descripcion.model.TipoDescriptor}
	 *            ).
	 * @return Listas descriptoras.
	 */
	public List getListasDescriptorasByTipoDescriptor(int tipoDescriptor) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_DESCRIPTOR,
						tipoDescriptor)).append(DBUtils.ORDER_BY)
				.append(CAMPO_NOMBRE.getQualifiedName()).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ListaDescrVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see descripcion.db.ICatalogoListaDescriptoresDBEntity#
	 * getListasDescrByTipoDescrYFicha(int, java.lang.String)
	 */
	public List getListasDescrByTipoDescrOFicha(int tipoDescriptor,
			String idFicha) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_DESCRIPTOR,
						tipoDescriptor))
				.append(DBUtils.OR)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_FICHA_DESCR_PREF,
						idFicha)).append(DBUtils.ORDER_BY)
				.append(CAMPO_NOMBRE.getQualifiedName()).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ListaDescrVO.class);
	}

	/**
	 * Crea una lista descriptora.
	 * 
	 * @param listaDescriptora
	 *            lista descriptora.
	 * @return Lista descriptora insertada.
	 */
	public ListaDescrVO insert(final ListaDescrVO listaDescriptora) {
		if (logger.isDebugEnabled())
			logger.debug("Se va a crear la lista descriptora: "
					+ listaDescriptora);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				listaDescriptora.setId(getGuid(listaDescriptora.getId()));
				SigiaDbInputRecord row = new SigiaDbInputRecord(COLS_DEFS,
						listaDescriptora);
				DbInsertFns.insert(conn, TABLE_NAME, COL_NAMES, row);

			}
		};
		command.execute();

		return listaDescriptora;
	}

	/**
	 * Modifica la lista descriptora.
	 * 
	 * @param listaDescriptora
	 *            Lista descriptora.
	 */
	public void update(ListaDescrVO listaDescriptora) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						listaDescriptora.getId())).toString();

		if (logger.isDebugEnabled())
			logger.debug("Se va a modificar la lista descriptora: "
					+ listaDescriptora);

		updateVO(qual, TABLE_NAME, COLS_DEFS, listaDescriptora);
	}

	/**
	 * Elimina las listas descriptoras.
	 * 
	 * @param listaIds
	 *            Lista de identificadores de listas descriptoras.
	 */
	public void delete(String[] listaIds) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID, listaIds))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar las listas descriptoras con ids: "
					+ listaIds);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina la lista descriptora.
	 * 
	 * @param id
	 *            Identificador de la lista descriptora.
	 */
	public void delete(String id) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		if (logger.isDebugEnabled())
			logger.debug("Se va a eliminar la lista descriptora con id: " + id);

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see descripcion.db.ICatalogoListaDescriptoresDBEntity#getListasDescriptorasByTipos(int[])
	 */
	public List getListasDescriptorasByTipos(int[] tipos) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_TIPO, tipos))
				.append(DBUtils.generateOrderBy(CAMPO_NOMBRE)).toString();

		return getVOS(qual, TABLE_NAME, COLS_DEFS, ListaDescrVO.class);
	}

}
