/*
 * Created on 04-ene-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.common.utils;

/**
 * @author MANUEL TAVARES
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ISicresGenPerms {

    public static final int ISUSER_PERM_CAN_CREATE_INTERESTED = 1;

    public static final int ISUSER_PERM_CAN_MODIFY_INTERESTED = 2;

    public static final int ISUSER_PERM_CAN_MODIFY_DATEREG = 4;

    public static final int ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS = 8;

    public static final int ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE = 16;

    public static final int ISUSER_PERM_CAN_SET_DATEREG = 32;

    public static final int ISUSER_PERM_CAN_ACCEPT_REGISTERS = 64;

    public static final int ISUSER_PERM_CAN_REJECT_REGISTERS = 128;

    public static final int ISUSER_PERM_CAN_ARCHIVE_REGISTERS = 256;

    public static final int ISUSER_PERM_CAN_CHANGE_DEST_REGISTERS = 512;

    public static final int ISUSER_PERM_CAN_CHANGE_DEST_REJECT_REGISTERS = 1024;

    public static final int ISUSER_PERM_CAN_SHOW_DOCUMENTS = 2048;

    public static final int ISUSER_PERM_CAN_DIST_REGISTERS = 4096;

    public static final int ISUSER_PERM_CAN_MODIFY_ISSUETYPES = 8192;
    public static final int ISUSER_PERM_CAN_MODIFY_ADMINUNITS = 16384;
    public static final int ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES = 32768;
    public static final int ISUSER_PERM_CAN_MODIFY_REPORTS = 65536;
    public static final int ISUSER_PERM_CAN_MODIFY_USERS= 131072;

    public static final int ISUSER_PERM_CAN_DELETE_DOCUMENTS = 262144;

    //reservado para futuras versiones --> public static final int ISUSER_PERM_CAN_COPIA_AUTENTICA = 524288;

    private boolean canCreatePersons = false;

    private boolean canUpdatePersons = false;

    private boolean canIntroRegDate = false;

    private boolean canUpdateRegDate = false;

    private boolean canUpdateProtectedFields = false;

    private boolean canAccessInterchange = false;

    private boolean canAcceptRegisters = false;

    private boolean canRejectRegisters = false;

    private boolean canArchiveRegisters = false;

    private boolean canChangeDestRegisters = false;

    private boolean canChangeDestRejectRegisters = false;

	private boolean canDistRegisters = false;

	private boolean canShowDocuments = false;

	private boolean canModifyIssueTypes = false;
	private boolean canModifyAdminUnits = false;
	private boolean canModifyTransportTypes = false;
	private boolean canModifyReports = false;
	private boolean canModifyUsers = false;

	private boolean canDeleteDocuments = false;

    public ISicresGenPerms() {
    }

    /**
     * @param bSet
     */
    public void setCanCreatePersons(boolean bSet) {
        canCreatePersons = bSet;
    }

    /**
     * @param bSet
     */
    public void setCanUpdatePersons(boolean bSet) {
        canUpdatePersons = bSet;
    }

    /**
     * @param bSet
     */
    public void setCanIntroRegDate(boolean bSet) {
        canIntroRegDate = bSet;
    }

    /**
     * @param bSet
     */
    public void setCanUpdateRegDate(boolean bSet) {
        canUpdateRegDate = bSet;
    }

    /**
     * @param bSet
     */
    public void setCanAccessRegInterchange(boolean bSet) {
        canAccessInterchange = bSet;
    }

    /**
     * @param bSet
     */
    public void setCanUpdateProtectedFields(boolean bSet) {
        canUpdateProtectedFields = bSet;
    }

    /**
     * @return
     */
    public boolean canCreatePersons() {
        return (canCreatePersons);
    }

    /**
     * @return
     */
    public boolean canUpdatePersons() {
        return (canUpdatePersons);
    }

    /**
     * @return
     */
    public boolean canIntroRegDate() {
        return (canIntroRegDate);
    }

    /**
     * @return
     */
    public boolean canUpdateRegDate() {
        return (canUpdateRegDate);
    }

    /**
     * @return
     */
    public boolean canUpdateProtectedFields() {
        return (canUpdateProtectedFields);
    }

    /**
     * @return
     */
    public boolean canAccessRegInterchange() {
        return (canAccessInterchange);
    }

	/**
	 * @return the canAcceptRegisters
	 */
	public boolean isCanAcceptRegisters() {
		return canAcceptRegisters;
	}

	/**
	 * @param canAcceptRegisters the canAcceptRegisters to set
	 */
	public void setCanAcceptRegisters(boolean canAcceptRegisters) {
		this.canAcceptRegisters = canAcceptRegisters;
	}

	/**
	 * @return the canArchiveRegisters
	 */
	public boolean isCanArchiveRegisters() {
		return canArchiveRegisters;
	}

	/**
	 * @param canArchiveRegisters the canArchiveRegisters to set
	 */
	public void setCanArchiveRegisters(boolean canArchiveRegisters) {
		this.canArchiveRegisters = canArchiveRegisters;
	}

	/**
	 * @return the canChangeDestRegisters
	 */
	public boolean isCanChangeDestRegisters() {
		return canChangeDestRegisters;
	}

	/**
	 * @param canChangeDestRegisters the canChangeDestRegisters to set
	 */
	public void setCanChangeDestRegisters(boolean canChangeDestRegisters) {
		this.canChangeDestRegisters = canChangeDestRegisters;
	}

	/**
	 * @return the canChangeDestRejectRegisters
	 */
	public boolean isCanChangeDestRejectRegisters() {
		return canChangeDestRejectRegisters;
	}

	/**
	 * @param canChangeDestRejectRegisters the canChangeDestRejectRegisters to set
	 */
	public void setCanChangeDestRejectRegisters(boolean canChangeDestRejectRegisters) {
		this.canChangeDestRejectRegisters = canChangeDestRejectRegisters;
	}

	/**
	 * @return the canRejectRegisters
	 */
	public boolean isCanRejectRegisters() {
		return canRejectRegisters;
	}

	/**
	 * @param canRejectRegisters the canRejectRegisters to set
	 */
	public void setCanRejectRegisters(boolean canRejectRegisters) {
		this.canRejectRegisters = canRejectRegisters;
	}

	/**
	 * @return the canDistRegisters
	 */
	public boolean isCanDistRegisters() {
		return canDistRegisters;
	}

	/**
	 * @param canDistRegisters the canDistRegisters to set
	 */
	public void setCanDistRegisters(boolean canDistRegisters) {
		this.canDistRegisters = canDistRegisters;
	}

	/**
	 * @return the canShowDocuments
	 */
	public boolean isCanShowDocuments() {
		return canShowDocuments;
	}

	/**
	 * @param canShowDocuments the canShowDocuments to set
	 */
	public void setCanShowDocuments(boolean canShowDocuments) {
		this.canShowDocuments = canShowDocuments;
	}

    public boolean isCanDeleteDocuments() {
		return canDeleteDocuments;
	}

	public void setCanDeleteDocuments(boolean canDeleteDocuments) {
		this.canDeleteDocuments = canDeleteDocuments;
	}


	public boolean isCanModifyIssueTypes() {
		return canModifyIssueTypes;
	}

	public void setCanModifyIssueTypes(boolean canModifyIssueTypes) {
		this.canModifyIssueTypes = canModifyIssueTypes;
	}

	public boolean isCanModifyAdminUnits() {
		return canModifyAdminUnits;
	}

	public void setCanModifyAdminUnits(boolean canModifyAdminUnits) {
		this.canModifyAdminUnits = canModifyAdminUnits;
	}

	public boolean isCanModifyTransportTypes() {
		return canModifyTransportTypes;
	}

	public void setCanModifyTransportTypes(boolean canModifyTransportTypes) {
		this.canModifyTransportTypes = canModifyTransportTypes;
	}

	public boolean isCanModifyReports() {
		return canModifyReports;
	}

	public void setCanModifyReports(boolean canModifyReports) {
		this.canModifyReports = canModifyReports;
	}

	public boolean isCanModifyUsers() {
		return canModifyUsers;
	}

	public void setCanModifyUsers(boolean canModifyUsers) {
		this.canModifyUsers = canModifyUsers;
	}



	//Getters necesarios para acceder desde los tags jsp

	public boolean getCanModifyIssueTypes() {
		return canModifyIssueTypes;
	}

	public boolean getCanModifyAdminUnits() {
		return canModifyAdminUnits;
	}


	public boolean getCanModifyTransportTypes() {
		return canModifyTransportTypes;
	}

	public boolean getCanModifyReports() {
		return canModifyReports;
	}

	public boolean getCanModifyUsers() {
		return canModifyUsers;
	}

}
