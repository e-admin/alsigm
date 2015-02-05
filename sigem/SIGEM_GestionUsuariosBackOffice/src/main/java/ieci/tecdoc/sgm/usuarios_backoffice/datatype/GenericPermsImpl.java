package ieci.tecdoc.sgm.usuarios_backoffice.datatype;

import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sgm.base.db.DbDeleteFns;
import ieci.tecdoc.sgm.usuarios_backoffice.database.LdapUsersTable;

import org.apache.log4j.Logger;

public class GenericPermsImpl {
    
	protected PermsImpl _perms = new PermsImpl();
    private static final Logger _logger = Logger.getLogger(GenericPermsImpl.class);

    protected GenericPermsImpl() {}

    protected void deletePerms(int id, int dest) throws Exception {
        LdapUsersTable table = new LdapUsersTable();
        if(_logger.isDebugEnabled()) {
            _logger.debug("deletePerms");
        }
        DbDeleteFns.delete(table.getPermsTableName(), table.getDeletePermsQual(id, dest));
    }

    protected Permissions getPermissions() {
        return _perms;
    }

    protected void insertPerms() throws Exception {
    	
        DynamicTable tableInfo = new DynamicTable();
        LdapUsersTable table = new LdapUsersTable();
        if(_logger.isDebugEnabled())
            _logger.debug("insertPerms");
        try
        {
            tableInfo.setTableObject(table);
            tableInfo.setClassName(LdapUsersTable.class.getName());
            tableInfo.setTablesMethod("getPermsTableName");
            tableInfo.setColumnsMethod("getInsertPermsColumnNames");
            for(int counter = 0; counter < _perms.count(); counter++)
            {
                DynamicRow rowInfo = new DynamicRow();
                DynamicRows rowsInfo = new DynamicRows();
                rowInfo.addRow(_perms.get(counter));
                rowInfo.setClassName(PermImpl.class.getName());
                rowInfo.setValuesMethod("insertValues");
                rowsInfo.add(rowInfo);
                DynamicFns.insert(tableInfo, rowsInfo);
            }

        }
        catch(Exception e)
        {
            _logger.error(e);
            throw e;
        }
    }

    protected void updatePerm(int id, int productId, int dest)
        throws Exception
    {
        DynamicTable tableInfo = new DynamicTable();
        LdapUsersTable table = new LdapUsersTable();
        if(_logger.isDebugEnabled())
            _logger.debug("updatePerm");
        tableInfo.setTableObject(table);
        tableInfo.setClassName(LdapUsersTable.class.getName());
        tableInfo.setTablesMethod("getPermsTableName");
        tableInfo.setColumnsMethod("getUpdatePermsColumnNames");
        DynamicRow rowInfo = new DynamicRow();
        DynamicRows rowsInfo = new DynamicRows();
        rowInfo.addRow(_perms.getProductPermission(productId));
        rowInfo.setClassName(PermImpl.class.getName());
        rowInfo.setValuesMethod("updateValues");
        rowsInfo.add(rowInfo);
        DynamicFns.update(table.getUpdatePermsQual(id, dest, productId), tableInfo, rowsInfo);
    }

    protected void updatePerms(int id, int dest)
        throws Exception
    {
        if(_logger.isDebugEnabled())
            _logger.debug("updatePerms");
        updatePerm(id, 3, dest);
        updatePerm(id, 4, dest);
        updatePerm(id, 5, dest);
    }

    protected void loadPerms(int id, int dest)
        throws Exception
    {
        DynamicTable tableInfo = new DynamicTable();
        DynamicRows rowsInfo = new DynamicRows();
        DynamicRow rowInfo = new DynamicRow();
        LdapUsersTable table = new LdapUsersTable();
        if(_logger.isDebugEnabled())
            _logger.debug("loadPerms");
        try
        {
            tableInfo.setTableObject(table);
            tableInfo.setClassName(LdapUsersTable.class.getName());
            tableInfo.setTablesMethod("getPermsTableName");
            tableInfo.setColumnsMethod("getLoadPermsColumnNames");
            rowInfo.setClassName(PermImpl.class.getName());
            rowInfo.setValuesMethod("loadValues");
            rowsInfo.add(rowInfo);
            if(!DynamicFns.selectMultiple(table.getLoadPermsQual(id, dest), false, tableInfo, rowsInfo))
                AdminException.throwException(3001003L);
            for(int counter = 0; counter < rowInfo.getRowCount(); counter++)
            {
                PermImpl perm = (PermImpl)rowInfo.getRow(counter);
                perm.setId(id);
                _perms.add(perm);
            }

        }
        catch(Exception e)
        {
            _logger.error(e);
            throw e;
        }
    }

    protected void setDefaultPerms(int id, int dest)
        throws Exception
    {
        PermImpl perm = new PermImpl(id, dest, 3, 1);
        _perms.add(perm);
        perm = new PermImpl(id, dest, 5, 0);
        _perms.add(perm);
        perm = new PermImpl(id, dest, 4, 0);
        _perms.add(perm);
    }

}
