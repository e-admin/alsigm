package common.lock.db.oracle;

import gcontrol.db.UsuarioDBEntityImpl;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;

import java.util.HashMap;

import common.db.DBUtils;
import common.db.DbDataSource;
import common.lock.db.LockDBEntityImplBase;
import common.lock.vos.LockVO;

public class LockDBEntityImpl extends LockDBEntityImplBase {

	public LockDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public LockDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public LockVO getBloqueoForUpdate(String idObj, int tipoObj, int modulo) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOOBJ, tipoObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDOBJ, idObj))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_MODULO, modulo))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSUARIO,
						UsuarioDBEntityImpl.CAMPO_ID)).append(" FOR UPDATE")
				.toString();

		// Mapeo de campos y tablas
		HashMap pairsTableNameColsDefs = new HashMap();
		pairsTableNameColsDefs.put(TABLE_NAME, COLS_DEFS_BLOQUEO);
		pairsTableNameColsDefs.put(UsuarioDBEntityImpl.TABLE_NAME,
				new DbColumnDef[] { UsuarioDBEntityImpl.CAMPO_NOMBRE,
						UsuarioDBEntityImpl.CAMPO_APELLIDOS });

		return (LockVO) getVO(qual, pairsTableNameColsDefs, LockVO.class);
	}

}
