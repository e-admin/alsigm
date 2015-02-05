package gcontrol.db;

import fondos.db.ProductorSerieDBEntityImplBase;
import gcontrol.model.TipoListaControlAcceso;
import gcontrol.vos.ListaAccesoVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.ArrayUtils;

public class ListaControlAccesoDbEntityImpl extends DBEntity implements
		IListaControlAccesoDbEntity {

	/**
	 * CREATE TABLE ASCALISTCA ( ID VARCHAR2(32), NOMBRE VARCHAR2(254), TIPO
	 * NUMBER(1), DESCRIPCION VARCHAR2(254) )
	 */
	public static final String TABLE_NAME_LISTA_CONTROL = "ASCALISTCA";

	public static final String ID_COLUMN_NAME = "ID";

	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";

	public static final String TIPO_COLUMN_NAME = "TIPO";

	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME_LISTA_CONTROL, ID_COLUMN_NAME, DbDataType.SHORT_TEXT,
			32, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME_LISTA_CONTROL, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME_LISTA_CONTROL, TIPO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME_LISTA_CONTROL, DESCRIPCION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 512, false);

	// Nombre para la join con descriptor
	public static final DbColumnDef CAMPO_NOMBRE_LCA = new DbColumnDef(
			"nombreLCA", TABLE_NAME_LISTA_CONTROL, NOMBRE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef[] COLS_DEFS_LISTA_ACCESO = { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_TIPO, CAMPO_DESCRIPCION };

	public static final String COL_NAMES_LISTA_ACCESO = DbUtil
			.getColumnNames(COLS_DEFS_LISTA_ACCESO);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_LISTA_CONTROL;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public ListaControlAccesoDbEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public ListaControlAccesoDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	public ListaAccesoVO insertListaAccesoVO(final ListaAccesoVO listaAccesoVO) {
		if (listaAccesoVO.getDescripcion() != null
				&& listaAccesoVO.getDescripcion().length() > CAMPO_DESCRIPCION
						.getMaxLen())
			listaAccesoVO.setDescripcion(listaAccesoVO.getDescripcion()
					.substring(0, CAMPO_DESCRIPCION.getMaxLen()));
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				listaAccesoVO.setId(getGuid(listaAccesoVO.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_LISTA_ACCESO, listaAccesoVO);
				DbInsertFns.insert(conn, TABLE_NAME_LISTA_CONTROL,
						COL_NAMES_LISTA_ACCESO, inputRecord);

			}

		};

		command.execute();

		return listaAccesoVO;

	}

	public void deleteListaAccesoVO(String id) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_LISTA_CONTROL,
						qual.toString());

			}

		};

		command.execute();
	}

	public ListaAccesoVO getListaAccesoVOXId(final String idLista) {
		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idLista))
				.toString();

		return getListaAccesoVO(qual);

	}

	/**
	 * Obtiene las listas de control de acceso.
	 * 
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAcceso() {
		String qual = new StringBuffer().append("ORDER BY ")
				.append(CAMPO_NOMBRE.getQualifiedName()).toString();

		return getListasAccesoVO(qual);

	}

	/**
	 * Obtiene las listas de control de acceso por tipo.
	 * 
	 * @param tipo
	 *            Tipo de la lista de control de acceso (
	 *            {@link TipoListaControlAcceso}).
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAccesoByTipo(int tipo) {
		String qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO, tipo))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName())
				.toString();

		return getListasAccesoVO(qual);

	}

	public ListaAccesoVO getListaControlAccesoByNombre(String nombre) {
		String qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre))
				.toString();

		return getListaAccesoVO(qual);

	}

	public List getListadoListasControlAccesoByNombreYTipos(String nombre,
			int[] tipos) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateLikeTokenField(CAMPO_NOMBRE, nombre));
		if (!ArrayUtils.isEmpty(tipos)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateInTokenField(CAMPO_TIPO, tipos));
		}

		return getListasAccesoVO(qual.toString());

	}

	public List getListadoListasControlAccesoByNombre(String nombre) {
		String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateLikeTokenField(CAMPO_NOMBRE, nombre,
						true)).toString();

		return getListasAccesoVO(qual);
	}

	public ListaAccesoVO getListaControlAccesoByNombreYTipo(String nombre,
			int tipo) {
		String qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO, tipo))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName())
				.toString();

		return getListaAccesoVO(qual);
	}

	public void updateListaAccesoVO(ListaAccesoVO listaAcceso) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, listaAcceso.getId()));
		updateVO(qual.toString(), TABLE_NAME_LISTA_CONTROL,
				COLS_DEFS_LISTA_ACCESO, listaAcceso);
	}

	/**
	 * Elimina de la base de datos las listas de acceso indicadas
	 * 
	 * @param listasAcceso
	 *            Identificadores de lista de acceso
	 */
	public void eliminarListasAcceso(String[] listasAcceso) {
		StringBuffer qual = new StringBuffer("WHERE ").append(DBUtils
				.generateORTokens(CAMPO_ID, listasAcceso));
		deleteVO(qual.toString(), TABLE_NAME_LISTA_CONTROL);
	}

	private List getListasAccesoVO(final String qual) {
		return getVOS(qual, TABLE_NAME_LISTA_CONTROL, COLS_DEFS_LISTA_ACCESO,
				ListaAccesoVO.class);
	}

	private ListaAccesoVO getListaAccesoVO(final String qual) {
		return (ListaAccesoVO) getVO(qual, TABLE_NAME_LISTA_CONTROL,
				COLS_DEFS_LISTA_ACCESO, ListaAccesoVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gcontrol.db.IListaControlAccesoDbEntity#getListasControlAcceso(java.lang
	 * .String[])
	 */
	public List getListasControlAcceso(String[] ids) {
		String qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateORTokens(CAMPO_ID, ids))
				.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName())
				.toString();
		return getListasAccesoVO(qual);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gcontrol.db.IListaControlAccesoDbEntity#
	 * getListasControlAccesoProductoresSerie(java.lang.String)
	 */
	public List getListasControlAccesoProductoresSerie(String idSerie) {

		String subquery = new StringBuffer()
				.append(DBUtils.SELECT)
				.append(ProductorSerieDBEntityImplBase.CAMPO_LISTAACCESO)
				.append(DBUtils.FROM)
				.append(ProductorSerieDBEntityImplBase.TABLE_NAME_PRODUCTORSERIE)
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						ProductorSerieDBEntityImplBase.CAMPO_IDSERIE, idSerie))
				.toString();

		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenFieldSubQuery(CAMPO_ID,
						subquery.toString()))
				.append(DBUtils.generateOrderBy(CAMPO_NOMBRE)).toString();
		return getListasAccesoVO(qual);
	}

}
