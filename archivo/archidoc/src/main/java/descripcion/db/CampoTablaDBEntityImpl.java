package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbTableDef;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntityKeyValue;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.DBException;
import common.util.ArrayUtils;

import descripcion.vos.CampoTablaVO;

/**
 * DBEntity para acceder a la tabla ADCAMPOTBL.
 */
public class CampoTablaDBEntityImpl extends DBEntityKeyValue implements
		ICampoTablaDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADCAMPOTBL";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String TIPONORMA_COLUMN_NAME = "tiponorma";
	public static final String ID_AREA_COLUMN_NAME = "idarea";
	public static final String ETIQUETA_XML_COLUMN_NAME = "etiquetaXml";
	public static final String ETIQUETA_XML_FILA_COLUMN_NAME = "etiqXmlFila";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_TIPONORMA = new DbColumnDef(null,
			TABLE_NAME, TIPONORMA_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_ID_AREA = new DbColumnDef(null,
			TABLE_NAME, ID_AREA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_ETIQUETA_XML = new DbColumnDef(null,
			TABLE_NAME, ETIQUETA_XML_COLUMN_NAME, DbDataType.SHORT_TEXT, 128,
			true);
	public static final DbColumnDef CAMPO_ETIQUETA_FILA_XML = new DbColumnDef(
			null, TABLE_NAME, ETIQUETA_XML_FILA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 128, true);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_TIPONORMA, CAMPO_ID_AREA, CAMPO_ETIQUETA_XML,
			CAMPO_ETIQUETA_FILA_XML, CAMPO_DESCRIPCION };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	private static final String ALIAS_ORDEN = "ORDEN";
	private static final String ALIAS_UNIONES = "UNIONES";

	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public CampoTablaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public CampoTablaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see descripcion.db.ICampoTablaDBEntity#getCampoTabla(java.lang.String)
	 */
	public CampoTablaVO getCampoTabla(String id) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));

		return (CampoTablaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				CampoTablaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see descripcion.db.ICampoTablaDBEntity#getCampoTabla(java.lang.String)
	 */
	public CampoTablaVO getCampoTablaPorNombre(String nombre) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre, true));

		return (CampoTablaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				CampoTablaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * descripcion.db.ICampoTablaDBEntity#getCamposTablaXArea(java.lang.String
	 * [])
	 */
	public List getCamposTablaXArea(String[] idAreas) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(
						CampoTablaDBEntityImpl.CAMPO_ID_AREA, idAreas))
				.toString();

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, CampoTablaVO.class);
	}

	/**
	 * Obtiene la lista de CamposTablaVO
	 * 
	 * @return una lista de CamposTablaVO
	 */
	public List getCamposTabla() {

		TableDef tablaCampo = new TableDef(TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(tablaCampo, CAMPO_ID_AREA), new DbColumnDef(
						new TableDef(AreaDBEntityImpl.TABLE_NAME),
						AreaDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer().append(" ORDER BY " + TABLE_NAME
				+ "." + CAMPO_NOMBRE.getName());

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils
				.generateLeftOuterJoinCondition(tablaCampo, joins));

		DbColumnDef columna = new DbColumnDef("nombreArea",
				AreaDBEntityImpl.TABLE_NAME, AreaDBEntityImpl.CAMPO_NOMBRE);
		DbColumnDef[] areaColumn = new DbColumnDef[1];
		areaColumn[0] = columna;
		DbColumnDef[] cols = (DbColumnDef[]) ArrayUtils.concat(areaColumn,
				COL_DEFS);

		return getVOS(qual.toString(), sqlFrom.toString(), cols,
				CampoTablaVO.class);
	}

	/**
	 * Inserta un nuevo CampoTablaVO.
	 * 
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO createCampoTabla(CampoTablaVO campoTablaVO) {
		DbConnection conn = getConnection();

		try {
			campoTablaVO.setId(getGuid(campoTablaVO.getId()));
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					campoTablaVO));
			return campoTablaVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * Actualiza el campo pasado por parametro.
	 * 
	 * @param CampoTablaVO
	 * @return CampoTablaVO
	 */
	public CampoTablaVO updateCampoTabla(CampoTablaVO campoTablaVO) {
		DbConnection conn = getConnection();

		try {
			String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID,
							campoTablaVO.getId())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, campoTablaVO),
					qual.toString());

			String idArea = campoTablaVO.getIdArea();
			String tipoNorma = String.valueOf(campoTablaVO.getTipoNorma());
			String idTblPadre = campoTablaVO.getId();
			DbUpdateFns.update(conn, "update adcampodato set idarea='" + idArea
					+ "',tiponorma=" + tipoNorma + " where idtblpadre='"
					+ idTblPadre + "'");

			return campoTablaVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * Elimina el conjunto de campos indicado por parámetro
	 * 
	 * @param idsCamposTabla
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposTabla(String[] idsCamposTabla) {

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(
						CampoDatoDBEntityImpl.CAMPO_ID_TBL_PADRE,
						idsCamposTabla)).toString();

		deleteVO(qual, CampoDatoDBEntityImpl.TABLE_NAME);

		qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsCamposTabla))
				.toString();

		deleteVO(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * descripcion.db.ICampoTablaDBEntity#deleteCampoTabla(java.lang.String)
	 */
	public void deleteCampoTabla(String id) {

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						CampoDatoDBEntityImpl.CAMPO_ID_TBL_PADRE, id))
				.toString();

		deleteVO(qual, CampoDatoDBEntityImpl.TABLE_NAME);

		qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		deleteVO(qual, TABLE_NAME);
	}

	public int getMaxOrdenTablaDescripcion(String idElementoCF,
			String[] idsCampos) {
		String selectAdvcTextoCF = DbSelectStatement.getSelectStmtText(
				NumeroDBEntityImpl.TABLE_NAME,
				DbUtil.getColumnNames(NumeroDBEntityImpl.CAMPO_ORDEN),
				DBUtils.WHERE
						+ DBUtils.generateInTokenField(
								NumeroDBEntityImpl.CAMPO_ID_CAMPO, idsCampos)
						+ DBUtils.AND
						+ DBUtils.generateEQTokenField(
								NumeroDBEntityImpl.CAMPO_ID_ELEMENTO,
								idElementoCF), false);
		String selectAdvcTextoLargoCF = DbSelectStatement.getSelectStmtText(
				TextoCortoDBEntityImpl.TABLE_NAME,
				DbUtil.getColumnNames(TextoCortoDBEntityImpl.CAMPO_ORDEN),
				DBUtils.WHERE
						+ DBUtils.generateInTokenField(
								TextoCortoDBEntityImpl.CAMPO_ID_CAMPO,
								idsCampos)
						+ DBUtils.AND
						+ DBUtils.generateEQTokenField(
								TextoCortoDBEntityImpl.CAMPO_ID_ELEMENTO,
								idElementoCF), false);
		String selectAdvcNumCF = DbSelectStatement.getSelectStmtText(
				TextoLargoDBEntityImpl.TABLE_NAME,
				DbUtil.getColumnNames(TextoLargoDBEntityImpl.CAMPO_ORDEN),
				DBUtils.WHERE
						+ DBUtils.generateInTokenField(
								TextoLargoDBEntityImpl.CAMPO_ID_CAMPO,
								idsCampos)
						+ DBUtils.AND
						+ DBUtils.generateEQTokenField(
								TextoLargoDBEntityImpl.CAMPO_ID_ELEMENTO,
								idElementoCF), false);

		String uniones = DBUtils.generateUNIONTokens(new String[] {
				selectAdvcTextoCF, selectAdvcTextoLargoCF, selectAdvcNumCF });
		DbTableDef table = new DbTableDef(uniones, ALIAS_UNIONES);

		try {
			return DbSelectFns.selectMaxInt(getConnection(),
					table.getAliasedName(), ALIAS_ORDEN, null);
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public CampoTablaVO getCampoTablaByEtiquetaXml(String etiquetaXml) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ETIQUETA_XML, etiquetaXml,
						true));

		return (CampoTablaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				CampoTablaVO.class);
	}

	public CampoTablaVO getCampoTablaByEtiquetaFilaXml(String etiquetaFilaXml) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ETIQUETA_FILA_XML,
						etiquetaFilaXml, true));

		return (CampoTablaVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				CampoTablaVO.class);
	}

	public DbColumnDef getKeyField() {
		return CAMPO_ID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.db.IDBEntityKeyValue#getValueField()
	 */
	public DbColumnDef getValueField() {
		return CAMPO_NOMBRE;
	}

}