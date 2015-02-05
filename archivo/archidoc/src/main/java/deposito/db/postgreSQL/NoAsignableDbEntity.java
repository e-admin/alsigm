package deposito.db.postgreSQL;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableObject;

import common.db.DbDataSource;

import deposito.db.commonPostgreSQLServer.NoAsignablePostgreeSQLServerBaseDBEntity;

public class NoAsignableDbEntity extends
		NoAsignablePostgreeSQLServerBaseDBEntity {

	public NoAsignableDbEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public NoAsignableDbEntity(DbConnection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	// MIGRACION BD - BY PRIOR ( PROBAR CON EL BORRADO DE NO ASIGNABLE
	public List getIdsDescendientes(String idNoAsignablePadre) {
		StringBuffer tableName = new StringBuffer();
		tableName.append("connectby('asgdelemnoasig', 'id', 'idpadre', '")
				.append(idNoAsignablePadre).append("', 0)");
		tableName.append("  AS t(value text, idpadre text, level int);");
		DbColumnDef id = new DbColumnDef(null, "value", DbDataType.SHORT_TEXT,
				true);
		List ids = getVOS((String) null, tableName.toString(),
				new DbColumnDef[] { id }, MutableObject.class);
		List idList = new ArrayList();
		for (int i = 0; i < ids.size(); i++)
			idList.add((String) ((Mutable) ids.get(i)).getValue());
		return idList;
	}

}
