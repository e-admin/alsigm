package docelectronicos.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.Map;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.util.MarcaUtil;

import docelectronicos.vos.UnidadDocumentalElectronicaVO;

public class UnidadDocumentalElectronicaDBEntityImpl extends DBEntity implements
		IUnidadDocumentalElectronicaDBEntity {

	public static final String TABLE_NAME = "ASGFUDOCSDF";

	public static final String IDELEMENTOCF_COLUMN_NAME = "idelementocf";

	public static final String MARCAS_BLOQUEO_COLUMN_NAME = "marcasbloqueo";

	public static final DbColumnDef IDELEMENTOCF_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDELEMENTOCF_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef MARCAS_BLOQUEO_FIELD = new DbColumnDef(
			null, TABLE_NAME, MARCAS_BLOQUEO_COLUMN_NAME,
			DbDataType.SHORT_INTEGER, false);

	public static final DbColumnDef[] TABLE_COLUMNS = { IDELEMENTOCF_FIELD,
			MARCAS_BLOQUEO_FIELD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public UnidadDocumentalElectronicaDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UnidadDocumentalElectronicaDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see docelectronicos.db.IUnidadDocumentalElectronicaDBEntity#insertRow(
	 * docelectronicos.vos.UnidadDocumentalElectronicaVO)
	 */
	public void insertRow(final UnidadDocumentalElectronicaVO udocElectronica) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, udocElectronica);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IUnidadDocumentalElectronicaDBEntity#updateMarcasBloqueo
	 * (java.lang.String, int)
	 */
	public void updateMarcasBloqueo(String idElementoCF, int marcasBlqueo) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDELEMENTOCF_FIELD, idElementoCF));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(MARCAS_BLOQUEO_FIELD, new Integer(marcasBlqueo));
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public UnidadDocumentalElectronicaVO getUDocElectronica(String idElementoCF) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDELEMENTOCF_FIELD, idElementoCF));
		return (UnidadDocumentalElectronicaVO) getVO(qual.toString(),
				TABLE_NAME, TABLE_COLUMNS, UnidadDocumentalElectronicaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * docelectronicos.db.IUnidadDocumentalElectronicaDBEntity#getMarcasBloqueo
	 * (java.lang.String)
	 */
	public int getMarcasBloqueo(String idElementoCF) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDELEMENTOCF_FIELD, idElementoCF));
		UnidadDocumentalElectronicaVO udocElectronica = (UnidadDocumentalElectronicaVO) getVO(
				qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				UnidadDocumentalElectronicaVO.class);

		if (udocElectronica != null) {
			return udocElectronica.getMarcasBloqueo();
		}
		return 0;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see docelectronicos.db.IUnidadDocumentalElectronicaDBEntity#
	 * desbloqueaUDocsElectronicas(java.lang.String[])
	 */
	public void desbloqueaUDocsElectronicas(String[] ids) {
		int marcasBloqueo = MarcaUtil.generarMarcas(new int[] {});

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenField(IDELEMENTOCF_FIELD, ids));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(MARCAS_BLOQUEO_FIELD, new Integer(marcasBloqueo));
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

	}

}
