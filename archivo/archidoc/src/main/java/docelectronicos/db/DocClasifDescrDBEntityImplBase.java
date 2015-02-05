package docelectronicos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.util.StringUtils;

import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocClasificadorVO;

public abstract class DocClasifDescrDBEntityImplBase extends DBEntity implements
		IDocClasifDescrDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADOCCLASIFDESCR";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String ID_CLF_PADRE_COLUMN_NAME = "idclfpadre";
	public static final String ID_DESCR_COLUMN_NAME = "iddescr";
	public static final String MARCAS_COLUMN_NAME = "marcas";
	public static final String ESTADO_COLUMN_NAME = "estado";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columnas */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 128, false);
	public static final DbColumnDef CAMPO_ID_CLF_PADRE = new DbColumnDef(null,
			TABLE_NAME, ID_CLF_PADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);
	public static final DbColumnDef CAMPO_ID_DESCR = new DbColumnDef(
			"idobjeto", TABLE_NAME, ID_DESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_MARCAS = new DbColumnDef(null,
			TABLE_NAME, MARCAS_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = { CAMPO_ID, CAMPO_NOMBRE,
			CAMPO_ID_CLF_PADRE, CAMPO_ID_DESCR, CAMPO_MARCAS, CAMPO_ESTADO,
			CAMPO_DESCRIPCION };

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
	public DocClasifDescrDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocClasifDescrDBEntityImplBase(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene la lista de clasificadores de un descriptor.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados.
	 * @return Listas de clasificadores.
	 */
	public List getClasificadores(String idDescr, String idClfPadre,
			int[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idDescr))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
					.append(" AND ");

		qual.append(DBUtils
				.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre));

		if ((estados != null) && (estados.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		List clasificadores = getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				DocClasificadorVO.class);
		for (int i = 0; i < clasificadores.size(); i++)
			((DocClasificadorVO) clasificadores.get(i))
					.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return clasificadores;
	}

	/**
	 * Obtiene el clasificador.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @return Clasificador.
	 */
	public DocClasificadorVO getClasificador(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		DocClasificadorVO clasificador = (DocClasificadorVO) getVO(qual,
				TABLE_NAME, COL_DEFS, DocClasificadorVO.class);
		if (clasificador != null)
			clasificador.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return clasificador;
	}

	/**
	 * Obtiene el clasificador padre del clasificador indicado.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @return Clasificador padre.
	 */
	public abstract DocClasificadorVO getClasificadorPadre(String id);

	/**
	 * Crea un clasificador de documentos.
	 *
	 * @param clasificador
	 *            Clasificador de documentos.
	 * @return Clasificador.
	 */
	public DocClasificadorVO insertClasificador(DocClasificadorVO clasificador) {
		clasificador.setId(getGuid(clasificador.getId()));
		insertVO(TABLE_NAME, COL_DEFS, clasificador);
		return clasificador;
	}

	/**
	 * Modifica un clasificador de documentos.
	 *
	 * @param clasificador
	 *            Clasificador de documentos.
	 */
	public void updateClasificador(DocClasificadorVO clasificador) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						clasificador.getId())).toString();

		updateVO(qual, TABLE_NAME, COL_DEFS, clasificador);
	}

	/**
	 * Elimina un clasificador de documentos.
	 *
	 * @param id
	 *            Identificador del clasificador de documentos.
	 */
	public void deleteClasificador(String id) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/**
	 * Elimina los clasificadores de un descriptor.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public void deleteClasificadores(String idDescr) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * docelectronicos.db.IDocClasifDescrDBEntity#getClasificadores(java.lang
	 * .String, int[])
	 */
	public List getClasificadores(String idDescr, int[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idDescr))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr));

		if ((estados != null) && (estados.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		List clasificadores = getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				DocClasificadorVO.class);
		for (int i = 0; i < clasificadores.size(); i++)
			((DocClasificadorVO) clasificadores.get(i))
					.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return clasificadores;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see docelectronicos.db.IDocClasifDescrDBEntity#update(java.lang.String,
	 * int[], int)
	 */
	public void update(String idObj, int[] estadosAActualizar, int nuevoEstado) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idObj))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idObj));

		if ((estadosAActualizar != null) && (estadosAActualizar.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosAActualizar));

		HashMap colToUpdate = new HashMap();
		colToUpdate.put(CAMPO_ESTADO, new Integer(nuevoEstado));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);

	}

	/**
	 * Devuelve el número de clasificadores hijos del padre indicado
	 *
	 * @param idDescr
	 *            Identificador del descriptor
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Listas de clasificadores.
	 */
	public int getCountClasificadoresByIdClfPadre(String idDescr,
			String idClfPadre) {
		int ret = 0;

		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idDescr))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idDescr))
					.append(" AND ");

		qual.append(DBUtils
				.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre));

		ret = getVOCount(qual.toString(), TABLE_NAME);

		return ret;
	}

	public void deleteDescriptores(String[] idsDescriptores) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID_DESCR,
						idsDescriptores)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();
	}

	/**
	 * {@inheritDoc}
	 * @param idClfPadre
	 *
	 * @see docelectronicos.db.IDocClasifDescrDBEntity#getClasificadorByNombre(java.lang.String,
	 *      java.lang.String)
	 */
	public DocClasificadorVO getClasificadorByNombre(String idObjeto,
			String nombre, String idClfPadre) {

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_NOMBRE, nombre, true))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_DESCR, idObjeto))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre))

				.toString();

		DocClasificadorVO clasificador = (DocClasificadorVO) getVO(qual,
				TABLE_NAME, COL_DEFS, DocClasificadorVO.class);
		if (clasificador != null)
			clasificador.setTipoObjeto(TipoObjeto.DESCRIPTOR);

		return clasificador;
	}

}
