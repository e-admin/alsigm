package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.UsuarioPermisosBackOffice;
import ieci.tecdoc.idoc.admin.database.UsuariosPermisosTable;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.PermisosBackOfficeException;

public class UsuarioPermisosBackOfficeImpl implements UsuarioPermisosBackOffice {

	private int idUsuario;
	private int idAplicacion;
	private int tipoPerfil;
	private String nombre;
	private String nombreReal;
	private String primerApellido;
	private String segundoApellido;
	
	public UsuarioPermisosBackOfficeImpl() {
		idUsuario=-1;
		idAplicacion=-1;
		tipoPerfil=-1;
		nombre=null;
		nombreReal=null;
		primerApellido=null;
		segundoApellido=null;
	}
	
	public UsuarioPermisosBackOfficeImpl(int idUsuario, int idAplicacion) {
		this.idUsuario=idUsuario;
		this.idAplicacion=idAplicacion;
		tipoPerfil=-1;
		nombre=null;
		nombreReal=null;
		primerApellido=null;
		segundoApellido=null;
	}

	public UsuarioPermisosBackOfficeImpl(int idUsuario, int idAplicacion, int tipoPerfil, String nombre, String nombreReal, String primerApellido, String segundoApellido) {
		this.idUsuario = idUsuario;
		this.idAplicacion = idAplicacion;
		this.tipoPerfil=tipoPerfil;
		this.nombre=nombre;
		this.nombreReal=nombreReal;
		this.primerApellido=primerApellido;
		this.segundoApellido=segundoApellido;
	}



	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getNombreReal() {
		return nombreReal;
	}

	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public int getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(int tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}

	public void load(int idUsuario, int idAplicacion, String entidad) throws Exception{
	      DynamicTable tableInfo = new DynamicTable(); 
	      DynamicRows rowsInfo = new DynamicRows();
	      DynamicRow rowInfo = new DynamicRow();
	      UsuariosPermisosTable table = new UsuariosPermisosTable();

	      DbConnection dbConn = new DbConnection();
	      try {
	    	 dbConn.open(DBSessionManager.getSession(entidad));
	    	 //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");
	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(UsuariosPermisosTable.class.getName());
	         tableInfo.setTablesMethod("getTableName");
	         tableInfo.setColumnsMethod("getAllColumnNames");
	         
	         rowInfo.addRow(this);
	         rowInfo.setClassName(UsuarioPermisosBackOfficeImpl.class.getName());
	         rowInfo.setValuesMethod("loadValues");
	         rowsInfo.add(rowInfo);
	         
	         if (!DynamicFns.select(dbConn, table.getByIdUsApOrderQual(idUsuario, idAplicacion), tableInfo, rowsInfo)){
	            AdminException.throwException(UserErrorCodes.EC_USER_NOT_EXITS);
	         }
	         
	      }
	      catch (Exception e)
			{
	         throw e;
			}finally{
				dbConn.close();
			}		
	}
	
	public void store(String entidad)throws PermisosBackOfficeException, Exception{
		if(existUsuarioPermiso(idUsuario, idAplicacion, entidad)){
			update(entidad);
		}else{
			insert(entidad);
		}
	}
	
	
	public void update(String entidad) throws Exception{
		DynamicTable tableInfo = new DynamicTable(); 
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		UsuariosPermisosTable table = new UsuariosPermisosTable();
	      
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");
			tableInfo.setTableObject(table);
			tableInfo.setClassName(UsuariosPermisosTable.class.getName());
			tableInfo.setTablesMethod("getTableName");
			tableInfo.setColumnsMethod("getUpdateColumnNames");
	         
			rowInfo.addRow(this);
			rowInfo.setClassName(UsuarioPermisosBackOfficeImpl.class.getName());
			rowInfo.setValuesMethod("updateValues");
			rowsInfo.add(rowInfo);
	         
			DynamicFns.update(dbConn, table.getByIdQual(idUsuario, idAplicacion), tableInfo, rowsInfo);
	         
	      }
	    catch (Exception e){
	    	throw e;
		}finally{
			dbConn.close();
		}
	   }	
	
	public boolean existUsuarioPermiso(int idUsuario, int idAplicacion, String entidad) throws Exception{
		
		int count;
		boolean exist=false;
		UsuariosPermisosTable table = new UsuariosPermisosTable();
		
		DbConnection dbConn=new DbConnection();  
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");

			count = DbSelectFns.selectCount(dbConn, table.getTableName(), 
					table.getByIdUsApQual(idUsuario, idAplicacion));
			if (count > 0)
				exist=true;
			
		}catch(Exception e){
			throw e;
		}finally{	
			dbConn.close();
		}
		return exist;
	}	
	
	public void insert(String entidad) throws Exception{
	    DynamicTable tableInfo = new DynamicTable(); 
	    DynamicRows rowsInfo = new DynamicRows();
	    DynamicRow rowInfo = new DynamicRow();
	    UsuariosPermisosTable table = new UsuariosPermisosTable();
	
	    DbConnection dbConn=new DbConnection();
	    try{
	    	dbConn.open(DBSessionManager.getSession(entidad));
	    	//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_001", "postgres", "postgres");
	    	tableInfo.setTableObject(table);
	    	tableInfo.setClassName(UsuariosPermisosTable.class.getName());
	    	tableInfo.setTablesMethod("getTableName");
	    	tableInfo.setColumnsMethod("getInsertColumnNames");
	       
	    	rowInfo.addRow(this);
	    	rowInfo.setClassName(UsuarioPermisosBackOfficeImpl.class.getName());
	    	rowInfo.setValuesMethod("insertValues");
	    	rowsInfo.add(rowInfo);
	       
	    	DynamicFns.insert(dbConn, tableInfo, rowsInfo);
	       
	    }
	    catch (Exception e){
	       throw e;
	    }finally{
	    	dbConn.close();
	    }
	}
	
	public void delete(String entidad){
		
	}
	
	
	public Integer loadValues(DbOutputStatement statement, Integer idx) throws Exception {	
		int index = idx.intValue();		
		idUsuario = statement.getLongInteger(index++);
		nombre = statement.getShortText(index++);
		nombreReal = statement.getShortText(index++);
		primerApellido=statement.getShortText(index++);
		segundoApellido=statement.getShortText(index++);
		idAplicacion=statement.getLongInteger(index++);
		tipoPerfil=statement.getLongInteger(index++);
		return new Integer(index);
	}
	
	public Integer insertValues(DbInputStatement statement, Integer idx) throws Exception {	
		int index = idx.intValue();		
		
		statement.setLongInteger(index++, idUsuario);
		statement.setLongInteger(index++, idAplicacion);
		statement.setLongInteger(index++, tipoPerfil);
		
		return new Integer(index);
	}
	
	public Integer updateValues(DbInputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();		

		statement.setLongInteger(index++, tipoPerfil);
		
		return new Integer(index);
	} 
	
}
