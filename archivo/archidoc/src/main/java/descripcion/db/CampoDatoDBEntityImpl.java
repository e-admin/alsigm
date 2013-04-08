package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
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
import common.util.StringUtils;

import descripcion.model.TipoNorma;
import descripcion.vos.CampoDatoVO;

/**
 * DBEntity para acceder a la tabla ADCAMPODATO.
 */
public class CampoDatoDBEntityImpl extends DBEntityKeyValue implements
		ICampoDatoDBEntity {

	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADCAMPODATO";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "id";
	public static final String NOMBRE_COLUMN_NAME = "nombre";
	public static final String TIPO_COLUMN_NAME = "tipo";
	public static final String TIPONORMA_COLUMN_NAME = "tiponorma";
	public static final String ID_AREA_COLUMN_NAME = "idarea";
	public static final String ID_TBL_PADRE_COLUMN_NAME = "idtblpadre";
	public static final String POS_EN_TBL_COLUMN_NAME = "posentbl";
	public static final String ETIQUETA_XML_COLUMN_NAME = "etiquetaXml";
	public static final String DESCRIPCION_COLUMN_NAME = "descripcion";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME, TIPO_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_TIPONORMA = new DbColumnDef(null,
			TABLE_NAME, TIPONORMA_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_ID_AREA = new DbColumnDef(null,
			TABLE_NAME, ID_AREA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);
	public static final DbColumnDef CAMPO_ID_TBL_PADRE = new DbColumnDef(null,
			TABLE_NAME, ID_TBL_PADRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			true);
	public static final DbColumnDef CAMPO_POS_EN_TBL = new DbColumnDef(null,
			TABLE_NAME, POS_EN_TBL_COLUMN_NAME, DbDataType.SHORT_INTEGER, false);
	public static final DbColumnDef CAMPO_ETIQUETA_XML = new DbColumnDef(null,
			TABLE_NAME, ETIQUETA_XML_COLUMN_NAME, DbDataType.SHORT_TEXT, 128,
			true);
	public static final DbColumnDef CAMPO_DESCRIPCION = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_NOMBRE, CAMPO_TIPO, CAMPO_TIPONORMA, CAMPO_ID_AREA,
			CAMPO_ID_TBL_PADRE, CAMPO_POS_EN_TBL, CAMPO_ETIQUETA_XML,
			CAMPO_DESCRIPCION };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public CampoDatoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public CampoDatoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la lista de campos de una norma.
	 * 
	 * @param tipoNorma
	 *            Tipo de norma ({@link TipoNorma}).
	 * @return Lista de campos de la ficha ({@link CampoDatoVO}).
	 */
	public List getCamposByTipoNorma(int tipoNorma) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils
						.generateEQTokenField(CAMPO_TIPONORMA, tipoNorma))
				.append(" ORDER BY ").append(CAMPO_ID);

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, CampoDatoVO.class);
	}

	/**
	 * Obtiene un CamposDatoVO
	 * 
	 * @param String
	 *            idTabla
	 * @param int posicion
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoByPosEnTbl(String idTabla, int pos) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_TBL_PADRE,
						idTabla)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_POS_EN_TBL, pos));

		return (CampoDatoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				CampoDatoVO.class);
	}

	/**
	 * Obtiene la lista de CamposDatoVO
	 * 
	 * @param String
	 *            idTabla
	 * @return una lista de CamposDatoVO
	 */
	public List getCamposDatoOrderByPosEnTbl(String idTabla) {
		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_TBL_PADRE,
						idTabla)).append(" ORDER BY ").append(CAMPO_POS_EN_TBL);

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, CampoDatoVO.class);
	}

	/**
	 * Obtiene un CampoDatoVO perteneciente al id pasado por parámetro
	 * 
	 * @param id
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDato(String id) {
		id = StringUtils.trim(id);
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_ID, id));

		return (CampoDatoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				CampoDatoVO.class);
	}

	/**
	 * Obtiene un CampoDatoVO perteneciente a la etiqueta pasada por parámetro
	 * 
	 * @param etiqueta
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoByEtiquetaXml(String etiquetaXml) {
		if (etiquetaXml != null) {
			StringBuffer qual = new StringBuffer()
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(CAMPO_ETIQUETA_XML,
							etiquetaXml, true))
					.append(DBUtils.AND)
					.append(DBUtils.generateIsNullCondition(getConnection(),
							CAMPO_ID_TBL_PADRE));

			return (CampoDatoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
					CampoDatoVO.class);
		}

		return null;
	}

	/**
	 * Obtiene un CampoDatoVO perteneciente al nombre pasado por parámetro
	 * 
	 * @param nombre
	 * @return CampoDatoVO
	 */
	public CampoDatoVO getCampoDatoPorNombre(String nombre) {
		if (nombre != null) {
			nombre = StringUtils.trim(nombre);

			StringBuffer qual = new StringBuffer().append("WHERE ").append(
					DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre, true));

			return (CampoDatoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
					CampoDatoVO.class);
		}
		return null;
	}

	/**
	 * Obtiene una lista de CampoDatoVO perteneciente al id de la tabla que se
	 * pasa por parámetro
	 * 
	 * @param idTabla
	 * @return List
	 */
	public List getCamposDatoXIdTabla(String idTabla) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID_TBL_PADRE, idTabla));

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, CampoDatoVO.class);
	}

	/**
	 * Obtiene una lista de CampoDatoVO pertenecientes a los id de areas pasados
	 * por parámetro
	 * 
	 * @param idAreas
	 * @return List
	 */
	public List getCamposDatoXArea(String[] idAreas, boolean excludeCamposTabla) {
		StringBuffer qual = new StringBuffer().append(" WHERE ").append(
				DBUtils.generateInTokenField(
						CampoDatoDBEntityImpl.CAMPO_ID_AREA, idAreas));

		if (excludeCamposTabla) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_ID_TBL_PADRE, null));
		}

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, CampoDatoVO.class);
	}

	/**
	 * Obtiene una lista de CampoDatoVO que no pertecen a ninguna tabla
	 * 
	 * @return una lista de CampoDatoVO
	 */
	public List getCamposDatoSinTabla() {

		TableDef campoDato = new TableDef(TABLE_NAME);

		JoinDefinition[] joins = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(campoDato, CAMPO_ID_AREA), new DbColumnDef(
						new TableDef(AreaDBEntityImpl.TABLE_NAME),
						AreaDBEntityImpl.CAMPO_ID)) };

		StringBuffer qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_TBL_PADRE, null))
				.append(" ORDER BY " + TABLE_NAME + "."
						+ CAMPO_NOMBRE.getName());

		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(DBUtils.generateLeftOuterJoinCondition(campoDato, joins));

		DbColumnDef columna = new DbColumnDef("nombreArea",
				AreaDBEntityImpl.TABLE_NAME, AreaDBEntityImpl.CAMPO_NOMBRE);
		DbColumnDef[] areaColumn = new DbColumnDef[1];
		areaColumn[0] = columna;

		DbColumnDef[] cols = (DbColumnDef[]) ArrayUtils.concat(areaColumn,
				COL_DEFS);

		return getVOS(qual.toString(), sqlFrom.toString(), cols,
				CampoDatoVO.class);
	}

	/**
	 * Obtiene una lista de CampoDatoVO
	 * 
	 * @return una lista de CampoDatoVO
	 */
	public List getCamposDato() {
		return getVOS("", TABLE_NAME, COL_DEFS, CampoDatoVO.class);
	}

	/**
	 * Inserta un nuevo CampoDatoVO.
	 * 
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO createCampoDato(CampoDatoVO campoDatoVO) {
		DbConnection conn = getConnection();

		try {
			campoDatoVO.setId(getGuid(campoDatoVO.getId()));
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					campoDatoVO));
			return campoDatoVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * Elimina el conjunto de campos indicado por parámetro
	 * 
	 * @param idsCamposDato
	 *            Conjunto de identificadores de campos
	 */
	public void deleteCamposDato(String[] idsCamposDato) {

		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsCamposDato))
				.toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar las fichas con ids: "
					+ idsCamposDato);

		deleteVO(qual, TABLE_NAME);

	}

	/**
	 * Actualiza el campo pasado por parametro.
	 * 
	 * @param CampoDatoVO
	 * @return CampoDatoVO
	 */
	public CampoDatoVO updateCampoDato(CampoDatoVO campoDatoVO) {
		DbConnection conn = getConnection();

		try {
			final String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID,
							campoDatoVO.getId())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, campoDatoVO),
					qual.toString());
			return campoDatoVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	public CampoDatoVO getCampoDatoByEtiquetaXml(String etiquetaXml,
			String idTabla) {

		if (StringUtils.isNotEmpty(etiquetaXml)) {
			etiquetaXml = StringUtils.trim(etiquetaXml);
			idTabla = StringUtils.trim(idTabla);
			StringBuffer qual = new StringBuffer()
					.append(DBUtils.WHERE)
					.append(DBUtils.generateEQTokenField(CAMPO_ETIQUETA_XML,
							etiquetaXml, true))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(CAMPO_ID_TBL_PADRE,
							idTabla));

			return (CampoDatoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
					CampoDatoVO.class);

		}
		return null;
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