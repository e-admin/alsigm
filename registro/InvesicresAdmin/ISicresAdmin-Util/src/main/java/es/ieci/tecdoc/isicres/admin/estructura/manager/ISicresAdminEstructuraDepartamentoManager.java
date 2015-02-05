package es.ieci.tecdoc.isicres.admin.estructura.manager;

import java.util.Date;

import es.ieci.tecdoc.isicres.admin.estructura.dao.Department;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Departments;
import es.ieci.tecdoc.isicres.admin.estructura.factory.ISicresAdminObjectFactory;

public class ISicresAdminEstructuraDepartamentoManager {

	public Department getDepartamento(int idDepto, String entidad) throws Exception{
		Department dep = ISicresAdminObjectFactory.createDepartment();
		dep.load(idDepto, entidad);
		return dep;
	}

	public Department getDepartamentoLite(int idDepto, String entidad) throws Exception{
		Department dep = ISicresAdminObjectFactory.createDepartment();
		dep.loadLite(idDepto, entidad);
		return dep;
	}

	public Departments getDepartamentos(int id, String entidad) throws Exception{
	    Departments depts= new Departments();
	    depts.loadLite(id, entidad);

	    return depts;
	}

	public Departments getDepartamentos(String entidad) throws Exception{
	    Departments depts= new Departments();
	    depts.loadLite(entidad);

	    return depts;
	}

	public void editarDepartamento(Department department,
			String entidad) throws Exception{

		//primero comprobamos que el usuario tiene permiso para modificar el departamento
//		UserAccess userAccess = ObjFactory.createUserAccess();
//		boolean userCanEditDept = userAccess.userCanEditDept(department.get_userConnected(), department.getId(), entidad);
//		if(userCanEditDept)
		department.set_updateDate(new Date());
		department.set_updaterId(department.get_userConnected());
		department.store(entidad);

	}



}
