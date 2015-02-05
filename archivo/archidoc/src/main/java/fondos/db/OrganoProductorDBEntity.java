package fondos.db;

import fondos.db.oracle.ProductorSerieDBEntityImpl;
import fondos.vos.OrganoProductorVO;
import gcontrol.db.CAOrganoDbEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

import descripcion.db.DescriptorDBEntityImpl;

/**
 * Mapeo de la tabla de organos productores: Al Obtener VO lo hara de tres
 * tablas: ASFGORGPROD, ASCAORGANO, DESCRIPTORVO,
 */
public class OrganoProductorDBEntity extends DBEntity implements
		IOrganoProductorDbEntity {

	public static final String TABLE_NAME_ORG = "ASGFORGPROD";

	public static final String IDDESCR_COLUMN_NAME = "IDDESCR";
	public static final String ENTIDAD_PRODUCTORA_COLUMN_NAME = "IDENTPRODINST";
	public static final String ID_ORG_COLUMN_NAME = "IDORG";

	public static final DbColumnDef CAMPO_IDDESCR = new DbColumnDef("id",
			TABLE_NAME_ORG, IDDESCR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_ENTIDAD_PRODUCTORA = new DbColumnDef(
			null, TABLE_NAME_ORG, ENTIDAD_PRODUCTORA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_ID_ORG = new DbColumnDef("idOrgano",
			TABLE_NAME_ORG, ID_ORG_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	private static final DbColumnDef[] COLS_DEF_ORG = new DbColumnDef[] {
			CAMPO_IDDESCR, CAMPO_ENTIDAD_PRODUCTORA, CAMPO_ID_ORG };

	public static final String COL_NAMES_ORG = DbUtil
			.getColumnNames(COLS_DEF_ORG);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_ORG;
	}

	public OrganoProductorDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public OrganoProductorDBEntity(DbConnection conn) {
		super(conn);
	}

	public List getOrgProductorXIdEntidadProductora(String idEntidadProductora) {

		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ENTIDAD_PRODUCTORA,
						idEntidadProductora)).toString();

		return selectOrganosProductores(qual);
	}

	public OrganoProductorVO getOrgProductorXIdOrgano(String idOrgano) {
		String qual = new StringBuffer("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_ID_ORG, idOrgano))
				.toString();

		return selectOrganoProductor(qual);
	}

	public OrganoProductorVO getOrgProductorXIdDescr(String idDescr) {
		String qual = new StringBuffer("WHERE ").append(
				DBUtils.generateEQTokenField(CAMPO_IDDESCR, idDescr))
				.toString();

		return selectOrganoProductor(qual);
	}

	private OrganoProductorVO selectOrganoProductor(String qual) {
		StringBuffer finalQual = new StringBuffer();
		if (qual.length() > 0)
			finalQual.append(qual).append(" AND ");
		else
			finalQual.append("WHERE ");
		finalQual.append(DBUtils.generateJoinCondition(
				DescriptorDBEntityImpl.CAMPO_ID, CAMPO_IDDESCR));

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(DescriptorDBEntityImpl.TABLE_NAME,
				DescriptorDBEntityImpl.COLS_DEFS);
		pairsTableNameColsDefs.put(TABLE_NAME_ORG, COLS_DEF_ORG);

		return (OrganoProductorVO) getVO(finalQual.toString(),
				pairsTableNameColsDefs, OrganoProductorVO.class);
	}

	private List selectOrganosProductores(String qual) {
		StringBuffer finalQual = new StringBuffer();
		if (qual.length() > 0)
			finalQual.append(qual).append(" AND ");
		else
			finalQual.append("WHERE ");
		finalQual.append(DBUtils.generateJoinCondition(
				DescriptorDBEntityImpl.CAMPO_ID, CAMPO_IDDESCR));

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(DescriptorDBEntityImpl.TABLE_NAME,
				DescriptorDBEntityImpl.COLS_DEFS);
		pairsTableNameColsDefs.put(TABLE_NAME_ORG, COLS_DEF_ORG);

		return getVOS(finalQual.toString(), pairsTableNameColsDefs,
				OrganoProductorVO.class);
	}

	private List selectOrganosProductores(String qual, String[] qualTables) {
		StringBuffer finalQual = new StringBuffer();
		if (qual.length() > 0)
			finalQual.append(qual).append(" AND ");
		else
			finalQual.append("WHERE ");
		finalQual.append(DBUtils.generateJoinCondition(
				DescriptorDBEntityImpl.CAMPO_ID, CAMPO_IDDESCR));

		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(DescriptorDBEntityImpl.TABLE_NAME,
				DescriptorDBEntityImpl.COLS_DEFS);
		pairsTableNameColsDefs.put(TABLE_NAME_ORG, COLS_DEF_ORG);
		if (qualTables != null)
			for (int i = 0; i < qualTables.length; i++)
				pairsTableNameColsDefs.put(qualTables[i], null);

		return getVOS(finalQual.toString(), pairsTableNameColsDefs,
				OrganoProductorVO.class);
	}

	/**
	 * Recupera un órgano productor a pardir de la información del sistema
	 * gestor de organización del que fue importado
	 * 
	 * @param sistGestor
	 *            Identificador del sistema gestor de organización
	 * @param idEnSistGestor
	 *            Identificador del órgano en el sistema externo gestor de
	 *            organización
	 * @return Datos del productor
	 */
	public OrganoProductorVO getOrgProductorXSistemaGestor(String sistGestor,
			String idEnSistGestor) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(
						CAOrganoDbEntityImpl.CAMPO_SISTEXTGESTOR, sistGestor))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						CAOrganoDbEntityImpl.CAMPO_IDORGSEXTGESTOR,
						idEnSistGestor)).toString();

		return selectOrganoProductor(qual);
	}

	public OrganoProductorVO insertOrgProductorVO(
			final OrganoProductorVO organismoProductorVO) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEF_ORG, organismoProductorVO);
				DbInsertFns.insert(conn, TABLE_NAME_ORG, COL_NAMES_ORG,
						inputRecord);

			}

		};

		command.execute();

		return organismoProductorVO;

	}

	public void updateOrgProductor(final OrganoProductorVO orgProductorVO) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDESCR,
						orgProductorVO.getId())).toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEF_ORG, orgProductorVO);
				DbUpdateFns.update(conn, TABLE_NAME_ORG, COL_NAMES_ORG,
						inputRecord, qual);

			}

		};

		command.execute();

	}

	// private final String getQualInner(){
	// String finalQual = new StringBuffer().append(" AND ").append(
	// DBUtils.generateJoinCondition(DescriptorDBEntityImpl.TABLE_NAME,
	// DescriptorDBEntityImpl.CAMPO_ID, TABLE_NAME_ORG, CAMPO_IDDESCR))
	//
	// .append(" AND ").append(
	// DBUtils.generateJoinCondition(CAOrganoDbEntityImpl.TABLE_NAME_ORG,
	// CAOrganoDbEntityImpl.CAMPO_ID, TABLE_NAME_ORG, CAMPO_ID_ORG))
	//
	// .toString();
	//
	// return finalQual;
	//
	// }

	/**
	 * Obtiene el número de referencias a un descriptor.
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Número de referencias.
	 */
	public int countReferencesDescriptor(String idDescriptor) {
		StringBuffer qual = new StringBuffer().append("WHERE").append(
				DBUtils.generateEQTokenField(CAMPO_IDDESCR, idDescriptor));

		return getVOCount(qual.toString(), TABLE_NAME_ORG);
	}

	/**
	 * Obtiene el número de referencias a descriptores de una lista descriptora.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Número de referencias.
	 */
	public int countReferencesDescriptorInList(String idListaDescriptora) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDDESCR,
						DescriptorDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						DescriptorDBEntityImpl.CAMPO_IDLISTA,
						idListaDescriptora));

		return getVOCount(qual.toString(), TABLE_NAME_ORG + ","
				+ DescriptorDBEntityImpl.TABLE_NAME);
	}

	/**
	 * Obtiene los órganos productores de una serie documental. Aquellos
	 * productores de la serie que son de otra naturaleza y no órganos, caso de
	 * haberlos no son devueltos
	 * 
	 * @param idSerie
	 *            Identificador de serie documental
	 * @return Lista de órganos productores de la serie
	 *         {@link OrganoProductorVO}
	 */
	public List getOrganosProductoresSerie(String idSerie) {
		StringBuffer qual = new StringBuffer("WHERE ")
				.append(DBUtils.generateEQTokenField(
						ProductorSerieDBEntityImpl.CAMPO_IDSERIE, idSerie))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDDESCR,
						ProductorSerieDBEntityImpl.CAMPO_IDPRODUCTOR));

		return selectOrganosProductores(
				qual.toString(),
				new String[] { ProductorSerieDBEntityImpl.TABLE_NAME_PRODUCTORSERIE });
	}

	public void deleteOrganosProductores(String[] organosProductores) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_IDDESCR,
						organosProductores)).toString();

		deleteVO(qual, TABLE_NAME_ORG);

	}

	protected void deleteVO(final String qual, final String TABLE_NAME) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};

		command.execute();
	}

	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar) {
		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(CAMPO_IDDESCR,
						idsAReemplazar)).toString();

		Map cols = Collections.singletonMap(CAMPO_IDDESCR, idDescriptor);

		updateFields(qual, cols, TABLE_NAME_ORG);
	}
}
