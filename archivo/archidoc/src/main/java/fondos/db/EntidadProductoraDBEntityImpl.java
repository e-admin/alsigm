package fondos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.ArrayUtils;

import descripcion.db.DescriptorDBEntityImpl;
import fondos.vos.EntidadProductoraVO;

/**
 * Implmentación de los métodos de acceso a datos referentes a entidades
 * productoras de fondos documentales
 */
public class EntidadProductoraDBEntityImpl extends DescriptorDBEntityImpl
		implements IEntidadProductoraDBEntity {

	public static final String TABLE_NAME_ENTIDAD_PRODUCTORA = "ASGFENTPRODUCTORA";

	private static final String IDDESCR_COLUMN_NAME = "IDDESCR";
	private static final String CODIGO_COLUMN_NAME = "CODIGO";
	private static final String TIPO_COLUMN_NAME = "TIPO";
	private static final String SISTGESTORORG_COLUMN_NAME = "SISTGESTORORG";
	private static final String IDENTPRODSGORG_COLUMN_NAME = "IDENTPRODSGORG";

	public static final DbColumnDef CAMPO_IDDESCR = new DbColumnDef("id",
			TABLE_NAME_ENTIDAD_PRODUCTORA, IDDESCR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_CODIGO = new DbColumnDef(null,
			TABLE_NAME_ENTIDAD_PRODUCTORA, CODIGO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef("tipoentidad",
			TABLE_NAME_ENTIDAD_PRODUCTORA, TIPO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_SISTGESTORORG = new DbColumnDef(null,
			TABLE_NAME_ENTIDAD_PRODUCTORA, SISTGESTORORG_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDENTPRODSGORG = new DbColumnDef(
			"idProductorEnSistGestor", TABLE_NAME_ENTIDAD_PRODUCTORA,
			IDENTPRODSGORG_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef[] COLS_DEFS_EPRODUCTORA = { CAMPO_IDDESCR,
			CAMPO_CODIGO, CAMPO_TIPO, CAMPO_SISTGESTORORG, CAMPO_IDENTPRODSGORG };

	private static final String COL_NAMES_EPRODUCTORA = DbUtil
			.getColumnNames(COLS_DEFS_EPRODUCTORA);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_ENTIDAD_PRODUCTORA;
	}

	public EntidadProductoraDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public EntidadProductoraDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public EntidadProductoraVO getEntidadXCodigo(final String codigo) {
		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_CODIGO, codigo))
				.toString();

		return getEntidadProductoraVO(qual);
	}

	public EntidadProductoraVO getEntidadXNombre(final String nombre) {
		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre))
				.toString();

		return getEntidadProductoraVO(qual);
	}

	public EntidadProductoraVO getEntidadXIddescr(final String idDescr) {
		String qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateTokenFieldQualified(CAMPO_IDDESCR,
						idDescr, TABLE_NAME_ENTIDAD_PRODUCTORA,
						ConstraintType.EQUAL)).toString();

		return getEntidadProductoraVO(qual);
	}

	/**
	 * Obtiene la entidad productora asociada a un organo remitente [columna
	 * IDENTPRODINST de la fila de la tabla ASGFORGPROD donde IDORG coincide con
	 * identificador del órgano al que pertenece el usuario]
	 * 
	 * @param idOrgano
	 *            Identificador de organo remitente
	 * @return {@link EntidadProductoraVO}
	 */
	public EntidadProductoraVO getEntidadXOrgano(final String idOrgano) {
		StringBuffer qual = new StringBuffer("where ")
				.append(DBUtils.generateEQTokenField(
						OrganoProductorDBEntity.CAMPO_ID_ORG, idOrgano))
				.append(" and ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDDESCR,
						OrganoProductorDBEntity.CAMPO_ENTIDAD_PRODUCTORA))
				.append(getQualInner());

		DbColumnDef[] cols = (DbColumnDef[]) ArrayUtils.concat(
				COLS_DEFS_EPRODUCTORA, COLS_DEFS);
		String fromClause = ArrayUtils.join(new String[] {
				OrganoProductorDBEntity.TABLE_NAME_ORG,
				TABLE_NAME_ENTIDAD_PRODUCTORA, TABLE_NAME }, ",");
		return (EntidadProductoraVO) getVO(qual.toString(), fromClause, cols,
				EntidadProductoraVO.class);
	}

	public void insertEntidad(final EntidadProductoraVO entidadVO) {
		final SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
				COLS_DEFS_EPRODUCTORA, entidadVO);
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				// el id de la entidad es ahora iddescr
				DbInsertFns.insert(conn, TABLE_NAME_ENTIDAD_PRODUCTORA,
						COL_NAMES_EPRODUCTORA, inputRecord);

			}

		};

		command.execute();
	}

	public void updateEntidadProductora(EntidadProductoraVO entidadVO) {
		// final SigiaDbInputRecord inputRecord =
		// new SigiaDbInputRecord(COLS_DEFS_EPRODUCTORA,entidadVO);
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDESCR,
						entidadVO.getId())).toString();

		updateVO(qual, TABLE_NAME_ENTIDAD_PRODUCTORA, COLS_DEFS_EPRODUCTORA,
				entidadVO);

	}

	public EntidadProductoraVO getEntidadProductoraVO(String qual) {
		final String finalQual = new StringBuffer(qual).append(getQualInner())
				.toString();

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS);
		pairsTableNameColsDefs.put(TABLE_NAME_ENTIDAD_PRODUCTORA,
				COLS_DEFS_EPRODUCTORA);

		return (EntidadProductoraVO) getVO(finalQual, pairsTableNameColsDefs,
				EntidadProductoraVO.class);
	}

	private List getEntidadesProductoraVO(String qual) {
		final String finalQual = new StringBuffer(qual).append(getQualInner())
				.toString();

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS);
		pairsTableNameColsDefs.put(TABLE_NAME_ENTIDAD_PRODUCTORA,
				COLS_DEFS_EPRODUCTORA);

		return (List) getVOS(finalQual, pairsTableNameColsDefs,
				EntidadProductoraVO.class);
	}

	private final String getQualInner() {
		final String finalQual = new StringBuffer()
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						DescriptorDBEntityImpl.TABLE_NAME, CAMPO_ID,
						TABLE_NAME_ENTIDAD_PRODUCTORA, CAMPO_IDDESCR))

				.toString();

		return finalQual;

	}

	public void deleteEntidad(String id) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDESCR, id))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_ENTIDAD_PRODUCTORA,
						qual.toString());

			}

		};

		command.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fondos.db.IEntidadProductoraDBEntity#getEntidadesXNombre(java.lang.String
	 * )
	 */
	public List getEntidadesXNombre(String nombre) {
		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_NOMBRE, nombre))
				.toString();

		return getEntidadesProductoraVO(qual);
	}

	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_IDDESCR,
						idsAReemplazar)).toString();

		Map cols = Collections.singletonMap(CAMPO_IDDESCR, idDescriptor);

		updateFields(qual, cols, TABLE_NAME_ENTIDAD_PRODUCTORA);
	}
}