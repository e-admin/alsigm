/**
 *
 */
package salas.db;

import gcontrol.db.ArchivoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.List;

import salas.vos.UsuarioArchivoSalasConsultaVO;

import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.TableDef;
import common.util.ArrayUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UsuarioArchivoSalasConsultaDBEntityImpl extends DBEntity implements
		IUsuarioArchivoSalasConsultaDBEntity {

	public static final String TABLE_NAME = "ASGSUSRCSARCH";

	public static final String IDUSUARIO_COLUMN_NAME = "IDUSRCSALA";

	public static final String IDARCHIVO_COLUMN_NAME = "IDARCHIVO";

	public static final DbColumnDef IDUSUARIO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUSUARIO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDARCHIVO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { IDUSUARIO_FIELD,
			IDARCHIVO_FIELD };

	DbColumnDef NOMBRE_ARCHIVO_FIELD = new DbColumnDef("nombreArchivo",
			new TableDef(ArchivoDbEntityImpl.TABLE_NAME,
					ArchivoDbEntityImpl.TABLE_NAME),
			ArchivoDbEntityImpl.NOMBRE_COLUMN_NAME,
			ArchivoDbEntityImpl.NOMBRE_FIELD.getDataType(), true);

	DbColumnDef[] COLS_DEF_QUERY = (DbColumnDef[]) ArrayUtils.concat(
			TABLE_COLUMNS, new DbColumnDef[] { NOMBRE_ARCHIVO_FIELD });

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
	public UsuarioArchivoSalasConsultaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UsuarioArchivoSalasConsultaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#deleteById(java.lang.String,
	 *      java.lang.String)
	 */
	public void deleteById(String idUsuario, String idArchivo) {
		deleteVO(getQualById(idUsuario, idArchivo), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#deleteByIdUsuario(java.lang.String)
	 */
	public void deleteByIdUsuario(String idUsuario) {
		deleteVO(getQualByIdUsuario(idUsuario), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#deleteByIdUsuarioYArchivos(java.lang.String,
	 *      java.lang.String[])
	 */
	public void deleteByIdUsuarioYArchivos(String idUsuario, String[] idsArchivo) {
		deleteVO(getQualByIds(idUsuario, idsArchivo), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#deleteByIdArchivo(java.lang.String)
	 */
	public void deleteByIdArchivo(String idArchivo) {
		deleteVO(getQualByIdArchivo(idArchivo), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#insertar(java.lang.String,
	 *      java.lang.String)
	 */
	public void insertar(
			UsuarioArchivoSalasConsultaVO usuarioArchvivoSalasConsultaVO) {
		insertVO(TABLE_NAME, TABLE_COLUMNS, usuarioArchvivoSalasConsultaVO);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#getArchivosByIdUsuarioSalaConsulta(java.lang.String)
	 */
	public List getArchivosByIdUsuarioSalaConsulta(String idUsuario) {
		StringBuffer qual = new StringBuffer(getQualByIdUsuario(idUsuario))
				.append(getDefaultOrderBy());

		return getDistinctVOS(qual.toString(), getJoinCondition(),
				COLS_DEF_QUERY, UsuarioArchivoSalasConsultaVO.class);
	}

	private String getQualById(String idUsuario, String idArchivo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(IDUSUARIO_FIELD, idUsuario))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(IDARCHIVO_FIELD, idArchivo));

		return qual.toString();
	}

	private String getQualByIdUsuario(String idUsuario) {
		return getQualByIds(idUsuario, null);
	}

	private String getQualByIds(String idUsuario, String[] idsArchivo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDUSUARIO_FIELD, idUsuario));

		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateInTokenField(IDARCHIVO_FIELD, idsArchivo));
		}

		return qual.toString();
	}

	private String getQualByIdArchivo(String idArchivo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDARCHIVO_FIELD, idArchivo));

		return qual.toString();
	}

	private String getJoinCondition() {
		/*
		 * SELECT ASGSUSRCSARCH.*, AGARCHIVO.NOMBRE FROM ASGSUSRCSARCH INNER
		 * JOIN AGARCHIVO ON AGARCHIVO.ID = ASGSUSRCSARCH.IDARCHIVO;
		 */

		JoinDefinition join = new JoinDefinition(IDARCHIVO_FIELD,
				ArchivoDbEntityImpl.ID_FIELD);

		StringBuffer fromSql = new StringBuffer().append(DBUtils
				.generateInnerJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { join }));

		return fromSql.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#getArchivosByIdUsuarioSalaConsultaInArchivos(java.lang.String,
	 *      java.lang.String[])
	 */
	public List getArchivosByIdUsuarioSalaConsultaInArchivos(String idUsuario,
			String[] idsArchivo) {
		StringBuffer qual = new StringBuffer(
				getQualByIds(idUsuario, idsArchivo))
				.append(getDefaultOrderBy());

		return getDistinctVOS(qual.toString(), getJoinCondition(),
				COLS_DEF_QUERY, UsuarioArchivoSalasConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.db.IUsuarioArchivoSalasConsultaDBEntity#existe(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean existe(String idUsuario, String idArchivo) {

		if (getVOCount(getQualById(idUsuario, idArchivo), TABLE_NAME) > 0) {
			return true;
		}

		return false;
	}

	private String getDefaultOrderBy() {
		return DBUtils.generateOrderBy(NOMBRE_ARCHIVO_FIELD);
	}
}
