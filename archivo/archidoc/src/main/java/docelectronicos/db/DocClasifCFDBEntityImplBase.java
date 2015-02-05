package docelectronicos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.util.StringUtils;

import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocClasificadorVO;

public abstract class DocClasifCFDBEntityImplBase extends DBEntity implements
		IDocClasifCFDBEntity {
	/** Nombre de la tabla. */
	public static final String TABLE_NAME = "ADOCCLASIFCF";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String ID_CLF_PADRE_COLUMN_NAME = "idclfpadre";
	public static final String ID_ELEMENTO_CF_COLUMN_NAME = "idelementocf";
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
	public static final DbColumnDef CAMPO_ID_ELEMENTO_CF = new DbColumnDef(
			"idobjeto", TABLE_NAME, ID_ELEMENTO_CF_COLUMN_NAME,
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
			CAMPO_ID_CLF_PADRE, CAMPO_ID_ELEMENTO_CF, CAMPO_MARCAS,
			CAMPO_ESTADO, CAMPO_DESCRIPCION };

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
	public DocClasifCFDBEntityImplBase(DbDataSource dataSource) {
		super(dataSource);
	}

	public DocClasifCFDBEntityImplBase(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de clasificadores de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados.
	 * @return Listas de clasificadores.
	 */
	public List getClasificadores(String idElementoCF, String idClfPadre,
			int[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idElementoCF))
			qual.append(
					DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
							idElementoCF)).append(" AND ");

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
					.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

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
			clasificador.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

		return clasificador;
	}

	/**
	 * Obtiene el clasificador.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @return Clasificador.
	 */
	public DocClasificadorVO getClasificadorByNombre(String idObjeto, String nombre, String idClfPadre) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre,true))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF, idObjeto))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre))

				.toString();

		DocClasificadorVO clasificador = (DocClasificadorVO) getVO(qual,
				TABLE_NAME, COL_DEFS, DocClasificadorVO.class);
		if (clasificador != null)
			clasificador.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

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
	 * Elimina los clasificadores de un elemento del cuadro de clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 */
	public void deleteClasificadores(String idElementoCF) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
						idElementoCF)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * docelectronicos.db.IDocClasifCFDBEntity#getClasificadores(java.lang.String
	 * , int[])
	 */
	public List getClasificadores(String idElementoCF, int[] estados) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idElementoCF))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
					idElementoCF));

		if ((estados != null) && (estados.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		qual.append(" ORDER BY ").append(CAMPO_NOMBRE.getQualifiedName());

		List clasificadores = getVOS(qual.toString(), TABLE_NAME, COL_DEFS,
				DocClasificadorVO.class);
		for (int i = 0; i < clasificadores.size(); i++)
			((DocClasificadorVO) clasificadores.get(i))
					.setTipoObjeto(TipoObjeto.ELEMENTO_CF);

		return clasificadores;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see docelectronicos.db.IDocClasifCFDBEntity#update(java.lang.String,
	 * int[], int)
	 */
	public void update(String idObj, int[] estadosAActualizar, int nuevoEstado) {
		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idObj))
			qual.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
					idObj));

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
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Listas de clasificadores.
	 */
	public int getCountClasificadoresByIdClfPadre(String idElementoCF,
			String idClfPadre) {
		int ret = 0;

		StringBuffer qual = new StringBuffer().append(" WHERE ");

		if (StringUtils.isNotBlank(idElementoCF))
			qual.append(
					DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF,
							idElementoCF)).append(" AND ");

		qual.append(DBUtils
				.generateEQTokenField(CAMPO_ID_CLF_PADRE, idClfPadre));

		ret = getVOCount(qual.toString(), TABLE_NAME);

		return ret;
	}

	/**
	 * {@inheritDoc}
	 * @see docelectronicos.db.IDocClasifCFDBEntity#updateIdElementocf(java.lang.String, java.lang.String, java.lang.String[])
	 */
	public void updateIdElementocf(String idElementocfAntiguo,
			String idElementoCfNuevo, String[] idsClasificadores) {

		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
			.append(DBUtils.generateEQTokenField(CAMPO_ID_ELEMENTO_CF, idElementocfAntiguo))
			.append(DBUtils.AND)
			.append(DBUtils.generateInTokenField(CAMPO_ID, idsClasificadores));

		HashMap colToUpdate = new HashMap();
		colToUpdate.put(CAMPO_ID_ELEMENTO_CF, idElementoCfNuevo);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}



}
