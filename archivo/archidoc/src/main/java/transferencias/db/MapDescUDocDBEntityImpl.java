package transferencias.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import transferencias.vos.MapDescUDocVO;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;

/**
 * DBEntity para acceder a la tabla ASGTMAPDESCRUDOC
 *
 * @author alicia
 */

public class MapDescUDocDBEntityImpl extends DBEntity implements
		IMapDescUDocDBEntity

{
	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ASGTMAPDESCRUDOC";

	/** Nombre de columnas */
	public static final String ID_FICHA_COLUMN_NAME = "idficha";
	public static final String INFO_COLUMN_NAME = "info";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID_FICHA = new DbColumnDef(null,
			TABLE_NAME, ID_FICHA_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_INFO = new DbColumnDef(null,
			TABLE_NAME, INFO_COLUMN_NAME, DbDataType.LONG_TEXT, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] {
			CAMPO_ID_FICHA, CAMPO_INFO };

	/** Lista de nombres de columnas. */
	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COL_DEFS);

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
	public MapDescUDocDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public MapDescUDocDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtiene la información de una ficha.
	 *
	 * @param id
	 *            Identificador de la ficha.
	 * @return Información de mapeo de las udocs en transferencia a sus campos
	 *         descriptivos
	 */
	public MapDescUDocVO getMapDescUDoc(String idFicha) {
		StringBuffer qual = new StringBuffer().append("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID_FICHA, idFicha));

		return (MapDescUDocVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				MapDescUDocVO.class);
	}

	/**
	 * Obtiene la lista de mapeos de las udocs en transferencias a su
	 * descripción
	 *
	 * @return una lista de mapeos de las u.docs en transferencias a su
	 *         descripción
	 */
	public List getMapsDescUDoc() {
		return getVOS("", TABLE_NAME, COL_DEFS, MapDescUDocVO.class);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see gcontrol.db.IArchivoDbEntity#insert(gcontrol.vos.ArchivoVO)
	 */
	public void insert(MapDescUDocVO mapDescUDocVO) {
		insertVO(TABLE_NAME, COL_DEFS, mapDescUDocVO);
	}

	public void delete(String[] idsFicha) {
		deleteVO(getQualByIds(idsFicha), getTableName());
	}

	public void update(MapDescUDocVO mapDescUDocVO) {
		Map colToUpdate = Collections.singletonMap(CAMPO_INFO, mapDescUDocVO.getInfo());
		updateFields(getQual(mapDescUDocVO.getIdFicha()), colToUpdate, TABLE_NAME);
	}

	private String getQual(String idFicha) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID_FICHA, idFicha));

		return qual.toString();
	}


	private String getQualByIds(String[] idFicha) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE).append(DBUtils
				.generateInTokenField(CAMPO_ID_FICHA, idFicha));

		return qual.toString();
	}
}
