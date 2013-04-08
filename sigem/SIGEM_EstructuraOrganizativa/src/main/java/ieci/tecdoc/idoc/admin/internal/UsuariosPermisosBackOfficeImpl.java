package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.idoc.admin.api.user.UsuarioPermisosBackOffice;
import ieci.tecdoc.idoc.admin.database.UsersTable;
import ieci.tecdoc.idoc.admin.database.UsuariosPermisosTable;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.CriterioBusqueda;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class UsuariosPermisosBackOfficeImpl {

	private ArrayList _list;
	private static final Logger _logger = Logger.getLogger(UsuariosPermisosBackOfficeImpl.class);
	
	public UsuariosPermisosBackOfficeImpl() {
		_list=new ArrayList();
	}

	public ArrayList get_list() {
		return _list;
	}

	public void set_list(ArrayList _list) {
		this._list = _list;
	}
	
	public void add(UsuarioPermisosBackOffice usuario){
		_list.add(usuario);
	}
	
	public UsuarioPermisosBackOfficeImpl get(int index){
		return (UsuarioPermisosBackOfficeImpl)_list.get(index);
	}
	
	public int count(){
		return _list.size();
	}
	
	public void loadUsuariosPermisosBackOffice (int idUsuario, String entidad) throws Exception{
		DynamicTable tableInfo = new DynamicTable(); 
		DynamicRows  rowsInfo  = new DynamicRows();
		DynamicRow   rowInfo   = new DynamicRow();
		UsuariosPermisosTable table = new UsuariosPermisosTable();
		int          counter;
		UsuarioPermisosBackOfficeImpl user;
	      
		DbConnection dbConn=new DbConnection();
	   
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");
			
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsuariosPermisosTable.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getAllColumnNames");
			
			
			rowInfo.setClassName(UsuarioPermisosBackOfficeImpl.class.getName());
			rowsInfo.add(rowInfo);
			rowInfo.setValuesMethod("loadValues");
			DynamicFns.selectMultiple(dbConn, table.getByIdQual(idUsuario), false, tableInfo, rowsInfo);
         
			_list.clear();
			for (counter = 0; counter < rowInfo.getRowCount(); counter++){
	            user = (UsuarioPermisosBackOfficeImpl)rowInfo.getRow(counter);            
	            add(user);
			}
         
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}			
		
	}
	
	public void loadUsuariosPermisosBackOffice(String entidad) throws Exception{
		DynamicTable tableInfo = new DynamicTable(); 
		DynamicRows  rowsInfo  = new DynamicRows();
		DynamicRow   rowInfo   = new DynamicRow();
		UsuariosPermisosTable table = new UsuariosPermisosTable();
		int          counter;
		UsuarioPermisosBackOfficeImpl user;
	      
		DbConnection dbConn=new DbConnection();
	   
		if (_logger.isDebugEnabled())
			_logger.debug("load");
	      
			try{
				dbConn.open(DBSessionManager.getSession(entidad));
				//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");
				
				tableInfo.setTableObject(table);
				tableInfo.setClassName(UsuariosPermisosTable.class.getName());
				tableInfo.setTablesMethod("getTableName");
				tableInfo.setColumnsMethod("getAllColumnNames");
				
				
				rowInfo.setClassName(UsuarioPermisosBackOfficeImpl.class.getName());
				rowsInfo.add(rowInfo);
				rowInfo.setValuesMethod("loadValues");
				DynamicFns.selectMultiple(dbConn, table.getLeftJoin(), false, tableInfo, 
	                                 rowsInfo);
	         
				for (counter = 0; counter < rowInfo.getRowCount(); counter++){
		            user = (UsuarioPermisosBackOfficeImpl)rowInfo.getRow(counter);
		            //user.load(user.getIdUsuario(), entidad);            
		            add(user);
	         }
	         
			}catch(Exception e){
				_logger.error(e);
	         throw e;
			}finally{
				dbConn.close();
	      }	

	}
	
	public void searchUsuariosPermisosBackOffice(CriterioBusqueda criterio, String entidad) throws Exception{
		DynamicTable tableInfo = new DynamicTable(); 
		DynamicRows  rowsInfo  = new DynamicRows();
		DynamicRow   rowInfo   = new DynamicRow();
		UsuariosPermisosTable table = new UsuariosPermisosTable();
		int          counter;
		UsuarioPermisosBackOfficeImpl user;
	      
		DbConnection dbConn=new DbConnection();
	   
		if (_logger.isDebugEnabled())
			_logger.debug("load");
	      
			try{
				dbConn.open(DBSessionManager.getSession(entidad));
				//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");
				
				tableInfo.setTableObject(table);
				tableInfo.setClassName(UsuariosPermisosTable.class.getName());
				tableInfo.setTablesMethod("getTableName");
				tableInfo.setColumnsMethod("getAllColumnNames");
	         
				rowInfo.setClassName(UsuarioPermisosBackOfficeImpl.class.getName());
				rowsInfo.add(rowInfo);
				rowInfo.setValuesMethod("loadValues");
				DynamicFns.selectMultiple(dbConn, table.getByCriterioQual(criterio), false, tableInfo, 
	                                 rowsInfo);
	         
				for (counter = 0; counter < rowInfo.getRowCount(); counter++){
		            user = (UsuarioPermisosBackOfficeImpl)rowInfo.getRow(counter);
		            //user.load(user.getIdUsuario(), entidad);            
		            add(user);
				}
				System.out.println("ssf");
	         
			}catch(Exception e){
				_logger.error(e);
				throw new Exception(e);
			}finally{
				try {
					dbConn.close();
				} catch (Exception e) {
					_logger.error(e);
					throw new Exception(e);
				}
	      }			
	}
	
	
	
}
