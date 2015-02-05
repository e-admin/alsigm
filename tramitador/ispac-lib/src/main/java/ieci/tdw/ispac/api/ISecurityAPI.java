package ieci.tdw.ispac.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;


public interface ISecurityAPI
{
	public static final int FUNC_CREATEPCD 				= 1;
	public static final int FUNC_SYSTEMMONITORIZE 		= 2;
	public static final int FUNC_ENTERCATALOG 			= 3;
	public static final int FUNC_MONITORINGSUPERVISOR 	= 4;
	public static final int FUNC_TOTALSUPERVISOR		= 5;

	// Funciones relacionadas con el inventario
	public static final int FUNC_INV_PROCEDURES_READ = 6;
	public static final int FUNC_INV_PROCEDURES_EDIT = 7;
	public static final int FUNC_INV_STAGES_READ = 8;
	public static final int FUNC_INV_STAGES_EDIT = 9;
	public static final int FUNC_INV_TASKS_READ = 10;
	public static final int FUNC_INV_TASKS_EDIT = 11;
	public static final int FUNC_INV_DOCTYPES_READ = 12;
	public static final int FUNC_INV_DOCTYPES_EDIT = 13;
	public static final int FUNC_INV_TEMPLATES_READ = 14;
	public static final int FUNC_INV_TEMPLATES_EDIT = 15;
	public static final int FUNC_INV_SUBPROCESS_READ = 16;
	public static final int FUNC_INV_SUBPROCESS_EDIT = 17;
	public static final int FUNC_INV_SIGN_CIRCUITS_READ = 18;
	public static final int FUNC_INV_SIGN_CIRCUITS_EDIT = 19;

	// Funciones relacionadas con los componentes
	public static final int FUNC_COMP_ENTITIES_READ = 20;
	public static final int FUNC_COMP_ENTITIES_EDIT = 21;
	public static final int FUNC_COMP_VALIDATION_TABLES_READ = 22;
	public static final int FUNC_COMP_VALIDATION_TABLES_EDIT = 23;
	public static final int FUNC_COMP_HIERARCHICAL_TABLES_READ = 24;
	public static final int FUNC_COMP_HIERARCHICAL_TABLES_EDIT = 25;
	public static final int FUNC_COMP_RULES_READ = 26;
	public static final int FUNC_COMP_RULES_EDIT = 27;
	public static final int FUNC_COMP_SEARCH_FORMS_READ = 28;
	public static final int FUNC_COMP_SEARCH_FORMS_EDIT = 29;
	public static final int FUNC_COMP_CALENDARS_READ = 30;
	public static final int FUNC_COMP_CALENDARS_EDIT = 31;
	public static final int FUNC_COMP_REPORTS_READ = 32;
	public static final int FUNC_COMP_REPORTS_EDIT = 33;
	public static final int FUNC_COMP_SYSTEM_VARS_READ = 34;
	public static final int FUNC_COMP_SYSTEM_VARS_EDIT = 35;
	public static final int FUNC_COMP_HELPS_READ = 36;
	public static final int FUNC_COMP_HELPS_EDIT = 37;

	// Funciones relacionadas con el publicador
	public static final int FUNC_PUB_ACTIONS_READ = 38;
	public static final int FUNC_PUB_ACTIONS_EDIT = 39;
	public static final int FUNC_PUB_APPLICATIONS_READ = 40;
	public static final int FUNC_PUB_APPLICATIONS_EDIT = 41;
	public static final int FUNC_PUB_CONDITIONS_READ = 42;
	public static final int FUNC_PUB_CONDITIONS_EDIT = 43;
	public static final int FUNC_PUB_RULES_READ = 44;
	public static final int FUNC_PUB_RULES_EDIT = 45;
	public static final int FUNC_PUB_MILESTONES_READ = 46;
	public static final int FUNC_PUB_MILESTONES_EDIT = 47;

	// Funciones relacionadas con la gestión de permisos
	public static final int FUNC_PERM_READ = 48;
	public static final int FUNC_PERM_EDIT = 49;
	
	public static final int ISPAC_RIGHTS_CREATEEXP		= 1;
	public static final int ISPAC_RIGHTS_ADMINPROCEDURE	= 2;
	public static final int ISPAC_RIGHTS_EDITPROCEDURE	= 3;

	public static final int SUPERV_FOLLOWEDMODE 		= 1;
	public static final int SUPERV_TOTALMODE 			= 2;
	public static final int SUPERV_ANY		 			=-1;

	public static final int PERMISSION_INIT_EXP_PCD		= 0;
	public static final int PERMISSION_DELETE_EXP_PCD	= 1;
	public static final int PERMISSION_TYPE_READ		= 2;
	public static final int PERMISSION_TYPE_EDIT		= 3;

	public static final int PERMISSION_TPOBJ_PROCEDURE			= 1;
	/*public static final int PERMISSION_TPOBJ_PROCESS			= 2;
	public static final int PERMISSION_TPOBJ_STAGE				= 3;
	public static final int PERMISSION_TPOBJ_STAGE_TASKS		= 4;
	public static final int PERMISSION_TPOBJ_STAGE_PCD			= 5;
	public static final int PERMISSION_TPOBJ_STAGE_PCD_TASKS	= 6;
	public static final int PERMISSION_TPOBJ_TASK				= 7;
	public static final int PERMISSION_TPOBJ_TASK_PCD			= 8;*/


	public static final int[] CATALOG_ACCESS_FUNCTIONS = new int[] {
		FUNC_ENTERCATALOG,
		FUNC_INV_PROCEDURES_READ,
		FUNC_INV_PROCEDURES_EDIT,
		FUNC_INV_STAGES_READ,
		FUNC_INV_STAGES_EDIT,
		FUNC_INV_TASKS_READ,
		FUNC_INV_TASKS_EDIT,
		FUNC_INV_DOCTYPES_READ,
		FUNC_INV_DOCTYPES_EDIT,
		FUNC_INV_TEMPLATES_READ,
		FUNC_INV_TEMPLATES_EDIT,
		FUNC_INV_SUBPROCESS_READ,
		FUNC_INV_SUBPROCESS_EDIT,
		FUNC_INV_SIGN_CIRCUITS_READ,
		FUNC_INV_SIGN_CIRCUITS_EDIT,
		FUNC_COMP_ENTITIES_READ,
		FUNC_COMP_ENTITIES_EDIT,
		FUNC_COMP_VALIDATION_TABLES_READ,
		FUNC_COMP_VALIDATION_TABLES_EDIT,
		FUNC_COMP_HIERARCHICAL_TABLES_READ,
		FUNC_COMP_HIERARCHICAL_TABLES_EDIT,
		FUNC_COMP_RULES_READ,
		FUNC_COMP_RULES_EDIT,
		FUNC_COMP_SEARCH_FORMS_READ,
		FUNC_COMP_SEARCH_FORMS_EDIT,
		FUNC_COMP_CALENDARS_READ,
		FUNC_COMP_CALENDARS_EDIT,
		FUNC_COMP_REPORTS_READ,
		FUNC_COMP_REPORTS_EDIT,
		FUNC_COMP_SYSTEM_VARS_READ,
		FUNC_COMP_SYSTEM_VARS_EDIT,
		FUNC_COMP_HELPS_READ,
		FUNC_COMP_HELPS_EDIT,
		FUNC_PUB_ACTIONS_READ,
		FUNC_PUB_ACTIONS_EDIT,
		FUNC_PUB_APPLICATIONS_READ,
		FUNC_PUB_APPLICATIONS_EDIT,
		FUNC_PUB_CONDITIONS_READ,
		FUNC_PUB_CONDITIONS_EDIT,
		FUNC_PUB_RULES_READ,
		FUNC_PUB_RULES_EDIT,
		FUNC_PUB_MILESTONES_READ,
		FUNC_PUB_MILESTONES_EDIT,
		FUNC_PERM_READ,
		FUNC_PERM_EDIT
	};
	
	public IItemCollection getFunctions(String uid) 
	throws ISPACException;
	
	public boolean isFunction(String uid, int function) 
	throws ISPACException;
	
	/**
	 * Devuelve cierto si el usuario tiene el permiso 
	 * @param respList Cadena de responsabilidad
	 * @param type Tipo de permiso a comprobar 
	 * 0-->Eliminar expediente (enviar a la papelera)
	 * 1->Iniciar expediente
	 * @param pcdId Identificador del procedimiento para el que se va a comprobar el permiso
	 * @return Cierto si el usuario tiene el permiso, falso en caso contrario
	 * @throws ISPACException
	 */
	public boolean isPermission(String respList, int type , int pcdId)
	throws ISPACException;
	
	public void changeFunctions(String uid, Map newFunctions)
	throws ISPACException;

	public void deleteFunctions(String uid)
	throws ISPACException;
		
	public void deletePermissions(int id)
    throws ISPACException;

	public void addSupervised(String uidsupervisor, String uidsupervised, int mode)
    throws ISPACException;
	
	public List getSuperviseds(String uid, int mode)
	throws ISPACException;
	
	public void deleteSuperviseds(String uid, String[] uids, int mode)
	throws ISPACException;
    
    public void addSustituciones(String uidSustituido, String[] uidSustitutos, Date fechaInicio, Date fechaFin, String descripcion)
    throws ISPACException;
    
    public void addSustituciones(int idFechSustitucion, String uidSustituido, String[] uidSustitutos)
    throws ISPACException;
    
    public void modifyFechSustitucion(int id, Date fechaInicio, Date fechaFin, String descripcion)
    throws ISPACException;
    
    public void deleteSustituciones(String[] idsSustitucionFecha)
    throws ISPACException;
    
    public boolean existPermissions(IStage stage, String resp, int[] typePermissions)
	throws ISPACException;

	public boolean existPermissions(IProcess process, String resp, int[] typePermissions)
	throws ISPACException;
	
	public boolean existPermissions(ITask task, String resp, int[] typePermissions) 
	throws ISPACException;
	
	public boolean existPermissionEditNotFollowSupervision(IStage stage,String cadenaResponsabilidad, IResponsible resp)
	throws ISPACException;
	
	public boolean existPermissionEditNotFollowSupervision(ITask task, String cadenaResponsabilidad, IResponsible resp)
	throws ISPACException;
	
	public boolean existPermissionEditNotFollowSupervision(IProcess process, String cadenaResponsabilidad, IResponsible resp)
	throws ISPACException;
			
	public IItemCollection getPermission(int typeObject, int idObject,  int[] typePermissions)
	throws ISPACException;

	/*public IItemCollection getProcessPermission(int idProcess, int[] typePermissions)
	throws ISPACException;

	public void addProcessPermission(int idProcess, IResponsible responsible, int typePermission)
	throws ISPACException;*/

	public void deletePermission(int typeObject, int idObject, String resp, int typePermission)
	throws ISPACException;
}