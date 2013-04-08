package descripcion.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.List;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.DBException;

import descripcion.vos.UsoObjetoVO;

public class UsoObjetoDBEntityImpl extends DBEntity implements
		IUsoObjetoDBEntity {
	/** Constantes de mapeo con la base de datos */
	public static final String TABLE_NAME = "ADUSOOBJETO";

	/** Nombre de columnas */
	public static final String ID_COLUMN_NAME = "idobj";
	public static final String TIPO_OBJETO_COLUMN_NAME = "tipoobj";
	public static final String ID_OBJ_USUARIO_COLUMN_NAME = "idobjusuario";
	public static final String TIPO_OBJ_USUARIO_COLUMN_NAME = "tipoobjusuario";

	/** Definiciones de columna */
	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO_OBJETO = new DbColumnDef(null,
			TABLE_NAME, TIPO_OBJETO_COLUMN_NAME, DbDataType.SHORT_INTEGER, 0,
			false);
	public static final DbColumnDef CAMPO_ID_OBJ_USUARIO = new DbColumnDef(
			null, TABLE_NAME, ID_OBJ_USUARIO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_TIPO_OBJ_USUARIO = new DbColumnDef(
			null, TABLE_NAME, TIPO_OBJ_USUARIO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, 0, false);

	/** Lista de columnas. */
	public static final DbColumnDef[] COL_DEFS = new DbColumnDef[] { CAMPO_ID,
			CAMPO_TIPO_OBJETO, CAMPO_ID_OBJ_USUARIO, CAMPO_TIPO_OBJ_USUARIO };

	/** Lista de columnas reducida. */
	public static final DbColumnDef[] COL_DEFS_REDUCIDA = new DbColumnDef[] {
			CAMPO_ID, CAMPO_TIPO_OBJETO, CAMPO_ID_OBJ_USUARIO,
			CAMPO_TIPO_OBJ_USUARIO };

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
	public UsoObjetoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UsoObjetoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public UsoObjetoVO create(UsoObjetoVO usoObjetoVO) {
		if (getByAllFields(usoObjetoVO) != null)
			return usoObjetoVO;

		DbConnection conn = getConnection();

		try {
			DbInsertFns.insert(conn, TABLE_NAME, DbUtil
					.getColumnNames(COL_DEFS), new SigiaDbInputRecord(COL_DEFS,
					usoObjetoVO));
			return usoObjetoVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	public UsoObjetoVO update(UsoObjetoVO usoObjetoVO) {
		DbConnection conn = getConnection();

		try {
			final String qual = new StringBuffer()
					.append(" WHERE ")
					.append(DBUtils.generateEQTokenField(CAMPO_ID_OBJ_USUARIO,
							usoObjetoVO.getIdObjUsuario())).toString();

			DbUpdateFns.update(conn, TABLE_NAME, COLUM_NAMES_LIST,
					new SigiaDbInputRecord(COL_DEFS, usoObjetoVO),
					qual.toString());
			return usoObjetoVO;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBException(e.getMessage());
		}
	}

	public void create(List list) {
		for (int i = 0; i < list.size(); i++) {
			UsoObjetoVO usoObjetoVO = (UsoObjetoVO) list.get(i);
			create(usoObjetoVO);
		}
	}

	public void deleteByIdObjUsuario(String[] idsObjUsuario) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID_OBJ_USUARIO,
						idsObjUsuario)).toString();

		if (logger.isDebugEnabled())
			logger.debug("Se van a eliminar los registros de la tabla "
					+ TABLE_NAME + " con " + ID_OBJ_USUARIO_COLUMN_NAME + " = "
					+ idsObjUsuario);

		deleteVO(qual, TABLE_NAME);
	}

	public void deleteByIdObjUsuario(String idObjUsuario) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_OBJ_USUARIO,
						idObjUsuario)).toString();
		deleteVO(qual, TABLE_NAME);
	}

	public UsoObjetoVO getByAllFields(UsoObjetoVO usoObjetoVO) {

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_OBJ_USUARIO,
						usoObjetoVO.getIdObjUsuario()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_OBJETO,
						usoObjetoVO.getTipoObj()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_OBJ_USUARIO,
						usoObjetoVO.getTipoObjUsuario()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						usoObjetoVO.getIdObj())).toString();

		return (UsoObjetoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				UsoObjetoVO.class);
	}

	public List getXIdObj(String idObj) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idObj))
				.toString();

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsoObjetoVO.class);
	}

	public List getXIdObjUsuario(String idObjUsuario) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_OBJ_USUARIO,
						idObjUsuario)).toString();

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsoObjetoVO.class);
	}

	public UsoObjetoVO getXIdObjUsuarioYTipoObj(String idObjUsuario,
			int idTipoObj) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID_OBJ_USUARIO,
						idObjUsuario))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_OBJETO,
						idTipoObj)).toString();

		return (UsoObjetoVO) getVO(qual.toString(), TABLE_NAME, COL_DEFS,
				UsoObjetoVO.class);
	}

	public List getXIdsObj(String[] idsObj) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsObj))
				.toString();

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsoObjetoVO.class);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see descripcion.db.IUsoObjetoDBEntity#getXIdObjYTipo(java.lang.String,
	 *      int)
	 */
	public List getXIdObjYTipo(String[] idsObjeto, int tipoObjeto) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_ID, idsObjeto))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_TIPO_OBJETO,
						tipoObjeto)).toString();

		return getVOS(qual.toString(), TABLE_NAME, COL_DEFS, UsoObjetoVO.class);

	}

}
