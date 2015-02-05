package ieci.tecdoc.sgm.usuarios_backoffice.datatype;

import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sgm.base.db.DbDeleteFns;
import ieci.tecdoc.sgm.usuarios_backoffice.database.LdapUsersTable;

import org.apache.log4j.Logger;

public class GenericProfilesImpl {

    protected UserProfilesImpl _profiles = new UserProfilesImpl();
    private static final Logger _logger = Logger.getLogger(GenericProfilesImpl.class);

    protected GenericProfilesImpl() {}

    protected UserProfiles getProfiles() {
        return _profiles;
    }

    protected void insertProfiles()
        throws Exception
    {
        DynamicTable tableInfo = new DynamicTable();
        LdapUsersTable table = new LdapUsersTable();
        if(_logger.isDebugEnabled())
            _logger.debug("insertProfiles");
        try
        {
            tableInfo.setTableObject(table);
            tableInfo.setClassName(LdapUsersTable.class.getName());
            tableInfo.setTablesMethod("getProfilesTableName");
            tableInfo.setColumnsMethod("getInsertProfilesColumnNames");
            for(int counter = 0; counter < _profiles.count(); counter++)
            {
                DynamicRow rowInfo = new DynamicRow();
                DynamicRows rowsInfo = new DynamicRows();
                rowInfo.addRow(_profiles.get(counter));
                rowInfo.setClassName(UserProfileImpl.class.getName());
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

    private void updateProfile(int id, int productId)
        throws Exception
    {
        LdapUsersTable table = new LdapUsersTable();
        DynamicTable tableInfo = new DynamicTable();
        if(_logger.isDebugEnabled())
            _logger.debug("updateProfile");
        tableInfo.setTableObject(table);
        tableInfo.setClassName(LdapUsersTable.class.getName());
        tableInfo.setTablesMethod("getProfilesTableName");
        tableInfo.setColumnsMethod("getUpdateProfilesColumnNames");
        DynamicRow rowInfo = new DynamicRow();
        DynamicRows rowsInfo = new DynamicRows();
        rowInfo.addRow(_profiles.getProductProfile(productId));
        rowInfo.setClassName(UserProfileImpl.class.getName());
        rowInfo.setValuesMethod("updateValues");
        rowsInfo.add(rowInfo);
        DynamicFns.update(table.getUpdateProfilesQual(id, productId), tableInfo, rowsInfo);
    }

    protected void updateProfiles(int id)
        throws Exception
    {
        if(_logger.isDebugEnabled())
            _logger.debug("updateProfiles");
        updateProfile(id, 3);
        updateProfile(id, 1);
        updateProfile(id, 2);
        updateProfile(id, 6);
        updateProfile(id, 4);
        updateProfile(id, 5);
    }

    protected boolean loadProfiles(int id)
        throws Exception
    {
        DynamicTable tableInfo = new DynamicTable();
        DynamicRows rowsInfo = new DynamicRows();
        DynamicRow rowInfo = new DynamicRow();
        LdapUsersTable table = new LdapUsersTable();
        boolean wasAdmin = false;
        if(_logger.isDebugEnabled())
            _logger.debug("loadProfiles");
        try
        {
            tableInfo.setTableObject(table);
            tableInfo.setClassName(LdapUsersTable.class.getName());
            tableInfo.setTablesMethod("getProfilesTableName");
            tableInfo.setColumnsMethod("getLoadProfilesColumnNames");
            rowInfo.setClassName(UserProfileImpl.class.getName());
            rowInfo.setValuesMethod("loadValues");
            rowsInfo.add(rowInfo);
            if(!DynamicFns.selectMultiple(table.getLoadProfilesQual(id), false, tableInfo, rowsInfo))
                AdminException.throwException(3001004L);
            UserProfile profile;
            for(int counter = 0; counter < rowInfo.getRowCount(); counter++)
            {
                profile = (UserProfileImpl)rowInfo.getRow(counter);
                ((UserProfileImpl)profile).setId(id);
                _profiles.add(profile);
            }

            profile = _profiles.getProductProfile(3);
            int prof = profile.getProfile();
            if(prof == 2 || prof == 3)
                wasAdmin = true;
        }
        catch(Exception e)
        {
            _logger.error(e);
            throw e;
        }
        return wasAdmin;
    }

    protected void setDefaultProfiles(int id)
        throws Exception
    {
        UserProfileImpl profile = new UserProfileImpl(id, 1, 0);
        _profiles.add(profile);
        profile = new UserProfileImpl(id, 2, 0);
        _profiles.add(profile);
        profile = new UserProfileImpl(id, 3, 1);
        _profiles.add(profile);
        profile = new UserProfileImpl(id, 4, 0);
        _profiles.add(profile);
        profile = new UserProfileImpl(id, 5, 0);
        _profiles.add(profile);
        profile = new UserProfileImpl(id, 6, 0);
        _profiles.add(profile);
    }

    protected void deleteProfiles(int id)
        throws Exception
    {
        LdapUsersTable table = new LdapUsersTable();
        if(_logger.isDebugEnabled())
            _logger.debug("deleteProfiles");
        DbDeleteFns.delete(table.getProfilesTableName(), table.getDeleteProfilesQual(id));
    }

}
