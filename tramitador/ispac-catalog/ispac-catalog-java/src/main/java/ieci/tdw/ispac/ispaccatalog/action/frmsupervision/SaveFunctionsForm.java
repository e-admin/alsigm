package ieci.tdw.ispac.ispaccatalog.action.frmsupervision;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Formulario para gestionar las funciones del usuario
 *
 */
public class SaveFunctionsForm extends ActionForm {
	

	private static final long serialVersionUID = 959566807529189930L;
	
	private boolean createPcd;
	private boolean monitorizeSystem;
	
	private boolean enterCatalog;
	private boolean monitoringSupervisor;
	private boolean totalSupervisor;
	
	// Funciones relacionadas con el inventario
	private boolean readProcedures;
	private boolean editProcedures;
	private boolean readStages;
	private boolean editStages;
	private boolean readTasks;
	private boolean editTasks;
	private boolean readDocTypes;
	private boolean editDocTypes;
	private boolean readTemplates;
	private boolean editTemplates;
	private boolean readSubprocesses;
	private boolean editSubprocesses;
	private boolean readSignCircuits;
	private boolean editSignCircuits;

	// Funciones relacionadas con los componentes
	private boolean readCompEntities;
	private boolean editCompEntities;
	private boolean readCompValidationTables;
	private boolean editCompValidationTables;
	private boolean readCompHierarchicalTables;
	private boolean editCompHierarchicalTables;
	private boolean readCompRules;
	private boolean editCompRules;
	private boolean readCompSearchForms;
	private boolean editCompSearchForms;
	private boolean readCompCalendars;
	private boolean editCompCalendars;
	private boolean readCompReports;
	private boolean editCompReports;
	private boolean readCompSystemVars;
	private boolean editCompSystemVars;
	private boolean readCompHelps;
	private boolean editCompHelps;

	// Funciones relacionadas con el publicador
	private boolean readPubActions;
	private boolean editPubActions;
	private boolean readPubApplications;
	private boolean editPubApplications;
	private boolean readPubConditions;
	private boolean editPubConditions;
	private boolean readPubRules;
	private boolean editPubRules;
	private boolean readPubMilestones;
	private boolean editPubMilestones;

	// Funciones relacionadas con la gestión de permisos
	private boolean readPermissions;
	private boolean editPermissions;


  /**
   * Reset all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
    super.reset(mapping, request);
  }

  /**
   * Validate all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   * @return ActionErrors A list of all errors found.
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
    ActionErrors errors = new ActionErrors();
    return errors;
  }

  public boolean getCreatePcd() {
    return createPcd;
  }
  public void setCreatePcd(boolean createPcd) {
    this.createPcd = createPcd;
  }

  public boolean getMonitorizeSystem() {
    return monitorizeSystem;
  }
  public void setMonitorizeSystem(boolean monitorizeSystem) {
    this.monitorizeSystem = monitorizeSystem;
  }

	/**
	 * @return Returns the enterCatalog.
	 */
	public boolean isEnterCatalog() {
		return enterCatalog;
	}
	/**
	 * @param enterCatalog The enterCatalog to set.
	 */
	public void setEnterCatalog(boolean enterCatalog) {
		this.enterCatalog = enterCatalog;
	}

	/**
	 * @return Returns the monitoringSupervisor.
	 */
	public boolean isMonitoringSupervisor() {
		return monitoringSupervisor;
	}
	/**
	 * @param monitoringSupervisor The monitoringSupervisor to set.
	 */
	public void setMonitoringSupervisor(boolean monitoringSupervisor) {
		this.monitoringSupervisor = monitoringSupervisor;
	}

	/**
	 * @return Returns the totalSupervisor.
	 */
	public boolean isTotalSupervisor() {
		return totalSupervisor;
	}
	/**
	 * @param totalSupervisor The totalSupervisor to set.
	 */
	public void setTotalSupervisor(boolean totalSupervisor) {
		this.totalSupervisor = totalSupervisor;
	}

	public boolean isReadProcedures() {
		return readProcedures;
	}

	public void setReadProcedures(boolean readProcedures) {
		this.readProcedures = readProcedures;
	}

	public boolean isEditProcedures() {
		return editProcedures;
	}

	public void setEditProcedures(boolean editProcedures) {
		this.editProcedures = editProcedures;
	}

	public boolean isReadStages() {
		return readStages;
	}

	public void setReadStages(boolean readStages) {
		this.readStages = readStages;
	}

	public boolean isEditStages() {
		return editStages;
	}

	public void setEditStages(boolean editStages) {
		this.editStages = editStages;
	}

	public boolean isReadTasks() {
		return readTasks;
	}

	public void setReadTasks(boolean readTasks) {
		this.readTasks = readTasks;
	}

	public boolean isEditTasks() {
		return editTasks;
	}

	public void setEditTasks(boolean editTasks) {
		this.editTasks = editTasks;
	}

	public boolean isReadDocTypes() {
		return readDocTypes;
	}

	public void setReadDocTypes(boolean readDocTypes) {
		this.readDocTypes = readDocTypes;
	}

	public boolean isEditDocTypes() {
		return editDocTypes;
	}

	public void setEditDocTypes(boolean editDocTypes) {
		this.editDocTypes = editDocTypes;
	}

	public boolean isReadTemplates() {
		return readTemplates;
	}

	public void setReadTemplates(boolean readTemplates) {
		this.readTemplates = readTemplates;
	}

	public boolean isEditTemplates() {
		return editTemplates;
	}

	public void setEditTemplates(boolean editTemplates) {
		this.editTemplates = editTemplates;
	}

	public boolean isReadSubprocesses() {
		return readSubprocesses;
	}

	public void setReadSubprocesses(boolean readSubprocesses) {
		this.readSubprocesses = readSubprocesses;
	}

	public boolean isEditSubprocesses() {
		return editSubprocesses;
	}

	public void setEditSubprocesses(boolean editSubprocesses) {
		this.editSubprocesses = editSubprocesses;
	}

	public boolean isReadSignCircuits() {
		return readSignCircuits;
	}

	public void setReadSignCircuits(boolean readSignCircuits) {
		this.readSignCircuits = readSignCircuits;
	}

	public boolean isEditSignCircuits() {
		return editSignCircuits;
	}

	public void setEditSignCircuits(boolean editSignCircuits) {
		this.editSignCircuits = editSignCircuits;
	}

	public boolean isReadCompEntities() {
		return readCompEntities;
	}

	public void setReadCompEntities(boolean readCompEntities) {
		this.readCompEntities = readCompEntities;
	}

	public boolean isEditCompEntities() {
		return editCompEntities;
	}

	public void setEditCompEntities(boolean editCompEntities) {
		this.editCompEntities = editCompEntities;
	}

	public boolean isReadCompValidationTables() {
		return readCompValidationTables;
	}

	public void setReadCompValidationTables(boolean readCompValidationTables) {
		this.readCompValidationTables = readCompValidationTables;
	}

	public boolean isEditCompValidationTables() {
		return editCompValidationTables;
	}

	public void setEditCompValidationTables(boolean editCompValidationTables) {
		this.editCompValidationTables = editCompValidationTables;
	}

	public boolean isReadCompHierarchicalTables() {
		return readCompHierarchicalTables;
	}

	public void setReadCompHierarchicalTables(boolean readCompHierarchicalTables) {
		this.readCompHierarchicalTables = readCompHierarchicalTables;
	}

	public boolean isEditCompHierarchicalTables() {
		return editCompHierarchicalTables;
	}

	public void setEditCompHierarchicalTables(boolean editCompHierarchicalTables) {
		this.editCompHierarchicalTables = editCompHierarchicalTables;
	}

	public boolean isReadCompRules() {
		return readCompRules;
	}

	public void setReadCompRules(boolean readCompRules) {
		this.readCompRules = readCompRules;
	}

	public boolean isEditCompRules() {
		return editCompRules;
	}

	public void setEditCompRules(boolean editCompRules) {
		this.editCompRules = editCompRules;
	}

	public boolean isReadCompSearchForms() {
		return readCompSearchForms;
	}

	public void setReadCompSearchForms(boolean readCompSearchForms) {
		this.readCompSearchForms = readCompSearchForms;
	}

	public boolean isEditCompSearchForms() {
		return editCompSearchForms;
	}

	public void setEditCompSearchForms(boolean editCompSearchForms) {
		this.editCompSearchForms = editCompSearchForms;
	}

	public boolean isReadCompCalendars() {
		return readCompCalendars;
	}

	public void setReadCompCalendars(boolean readCompCalendars) {
		this.readCompCalendars = readCompCalendars;
	}

	public boolean isEditCompCalendars() {
		return editCompCalendars;
	}

	public void setEditCompCalendars(boolean editCompCalendars) {
		this.editCompCalendars = editCompCalendars;
	}

	public boolean isReadCompReports() {
		return readCompReports;
	}

	public void setReadCompReports(boolean readCompReports) {
		this.readCompReports = readCompReports;
	}

	public boolean isEditCompReports() {
		return editCompReports;
	}

	public void setEditCompReports(boolean editCompReports) {
		this.editCompReports = editCompReports;
	}

	public boolean isReadCompSystemVars() {
		return readCompSystemVars;
	}

	public void setReadCompSystemVars(boolean readCompSystemVars) {
		this.readCompSystemVars = readCompSystemVars;
	}

	public boolean isEditCompSystemVars() {
		return editCompSystemVars;
	}

	public void setEditCompSystemVars(boolean editCompSystemVars) {
		this.editCompSystemVars = editCompSystemVars;
	}

	public boolean isReadCompHelps() {
		return readCompHelps;
	}

	public void setReadCompHelps(boolean readCompHelps) {
		this.readCompHelps = readCompHelps;
	}

	public boolean isEditCompHelps() {
		return editCompHelps;
	}

	public void setEditCompHelps(boolean editCompHelps) {
		this.editCompHelps = editCompHelps;
	}

	public boolean isReadPubActions() {
		return readPubActions;
	}

	public void setReadPubActions(boolean readPubActions) {
		this.readPubActions = readPubActions;
	}

	public boolean isEditPubActions() {
		return editPubActions;
	}

	public void setEditPubActions(boolean editPubActions) {
		this.editPubActions = editPubActions;
	}

	public boolean isReadPubApplications() {
		return readPubApplications;
	}

	public void setReadPubApplications(boolean readPubApplications) {
		this.readPubApplications = readPubApplications;
	}

	public boolean isEditPubApplications() {
		return editPubApplications;
	}

	public void setEditPubApplications(boolean editPubApplications) {
		this.editPubApplications = editPubApplications;
	}

	public boolean isReadPubConditions() {
		return readPubConditions;
	}

	public void setReadPubConditions(boolean readPubConditions) {
		this.readPubConditions = readPubConditions;
	}

	public boolean isEditPubConditions() {
		return editPubConditions;
	}

	public void setEditPubConditions(boolean editPubConditions) {
		this.editPubConditions = editPubConditions;
	}

	public boolean isReadPubRules() {
		return readPubRules;
	}

	public void setReadPubRules(boolean readPubRules) {
		this.readPubRules = readPubRules;
	}

	public boolean isEditPubRules() {
		return editPubRules;
	}

	public void setEditPubRules(boolean editPubRules) {
		this.editPubRules = editPubRules;
	}

	public boolean isReadPubMilestones() {
		return readPubMilestones;
	}

	public void setReadPubMilestones(boolean readPubMilestones) {
		this.readPubMilestones = readPubMilestones;
	}

	public boolean isEditPubMilestones() {
		return editPubMilestones;
	}

	public void setEditPubMilestones(boolean editPubMilestones) {
		this.editPubMilestones = editPubMilestones;
	}

	public boolean isReadPermissions() {
		return readPermissions;
	}

	public void setReadPermissions(boolean readPermissions) {
		this.readPermissions = readPermissions;
	}

	public boolean isEditPermissions() {
		return editPermissions;
	}

	public void setEditPermissions(boolean editPermissions) {
		this.editPermissions = editPermissions;
	}
	
}