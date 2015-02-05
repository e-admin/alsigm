package com.ieci.tecdoc.common.conf;

import java.io.Serializable;

/**
 * @author 79426599
 *
 */
public class InvesicresConf implements Serializable {

	//General
    private int alarmOnIncompleteReg = 0;
    private int modifySystemDate = 0;
    private int repositorySystem = 0;
    private int populationPartition = 0;
    private int authenticationByOffice = 0;
    private int documentHashing = 0;
    //Numeración
    private String formatType0 = "";
    private String formatType1 = "";
    private String formatType2 = "";
    
    //Distribución
    private int timeOut = 0;
    private int alarmOnNewDist = 0;
    private int autoArchiving = 0;
    private int alarmOnRejected = 0;
    private int distSRegister = 0;
    private int viewAcceptedRegisters = 0;
    private int canChangeDestWithoutList = 0;
    private int autoDist = 1;
    
    //Extension
    private String validation = "";
    
	public InvesicresConf() {
	}

	
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t InvesicresConf General alarmOnIncompleteReg[");
        buffer.append(alarmOnIncompleteReg);
        buffer.append("] modifySystemDate [");
        buffer.append(modifySystemDate);
        buffer.append("] repositorySystem [");
        buffer.append(repositorySystem);
        buffer.append("] populationPartition [");
        buffer.append(populationPartition);
        buffer.append("]");
        buffer.append("\n\t\t InvesicresConf Numeration formatType0[");
        buffer.append(formatType0);
        buffer.append("] formatType1 [");
        buffer.append(formatType1);
        buffer.append("] formatType2 [");
        buffer.append(formatType2);
        buffer.append("]");
        buffer.append("\n\t\t InvesicresConf Distribution timeOut[");
        buffer.append(timeOut);
        buffer.append("] alarmOnNewDist [");
        buffer.append(alarmOnNewDist);
        buffer.append("] autoArchiving [");
        buffer.append(autoArchiving);
        buffer.append("] alarmOnRejected [");
        buffer.append(alarmOnRejected);
        buffer.append("] distSRegister [");
        buffer.append(distSRegister);
        buffer.append("] viewAcceptedRegisters [");
        buffer.append(viewAcceptedRegisters);
        buffer.append("] canChangeDestWithoutList [");
        buffer.append(canChangeDestWithoutList);
        buffer.append("] autoDist [");
        buffer.append(autoDist);
        buffer.append("]");
        buffer.append("\n\t\t InvesicresConf Extension validation[");
        buffer.append(validation);
        buffer.append("]");
        
        return buffer.toString();
    }
	
	/**
	 * @return Returns the formatType1.
	 */
	public String getFormatType1() {
		return formatType1;
	}
	/**
	 * @param formatType1 The formatType1 to set.
	 */
	public void setFormatType1(String formatType1) {
		this.formatType1 = formatType1;
	}
	/**
	 * @return Returns the formatType2.
	 */
	public String getFormatType2() {
		return formatType2;
	}
	/**
	 * @param formatType2 The formatType2 to set.
	 */
	public void setFormatType2(String formatType2) {
		this.formatType2 = formatType2;
	}
	/**
	 * @return Returns the alarmOnIncompleteReg.
	 */
	public int getAlarmOnIncompleteReg() {
		return alarmOnIncompleteReg;
	}
	/**
	 * @param alarmOnIncompleteReg The alarmOnIncompleteReg to set.
	 */
	public void setAlarmOnIncompleteReg(int alarmOnIncompleteReg) {
		this.alarmOnIncompleteReg = alarmOnIncompleteReg;
	}
	/**
	 * @return Returns the alarmOnNewDist.
	 */
	public int getAlarmOnNewDist() {
		return alarmOnNewDist;
	}
	/**
	 * @param alarmOnNewDist The alarmOnNewDist to set.
	 */
	public void setAlarmOnNewDist(int alarmOnNewDist) {
		this.alarmOnNewDist = alarmOnNewDist;
	}
	/**
	 * @return Returns the alarmOnRejected.
	 */
	public int getAlarmOnRejected() {
		return alarmOnRejected;
	}
	/**
	 * @param alarmOnRejected The alarmOnRejected to set.
	 */
	public void setAlarmOnRejected(int alarmOnRejected) {
		this.alarmOnRejected = alarmOnRejected;
	}
	/**
	 * @return Returns the autoArchiving.
	 */
	public int getAutoArchiving() {
		return autoArchiving;
	}
	/**
	 * @param autoArchiving The autoArchiving to set.
	 */
	public void setAutoArchiving(int autoArchiving) {
		this.autoArchiving = autoArchiving;
	}
	/**
	 * @return Returns the canChangeDestWithoutList.
	 */
	public int getCanChangeDestWithoutList() {
		return canChangeDestWithoutList;
	}
	/**
	 * @param canChangeDestWithoutList The canChangeDestWithoutList to set.
	 */
	public void setCanChangeDestWithoutList(int canChangeDestWithoutList) {
		this.canChangeDestWithoutList = canChangeDestWithoutList;
	}
	/**
	 * @return Returns the distSRegister.
	 */
	public int getDistSRegister() {
		return distSRegister;
	}
	/**
	 * @param distSRegister The distSRegister to set.
	 */
	public void setDistSRegister(int distSRegister) {
		this.distSRegister = distSRegister;
	}
	/**
	 * @return Returns the formatType.
	 */
	public String getFormatType0() {
		return formatType0;
	}
	/**
	 * @param formatType The formatType to set.
	 */
	public void setFormatType0(String formatType0) {
		this.formatType0 = formatType0;
	}
	/**
	 * @return Returns the modifySystemDate.
	 */
	public int getModifySystemDate() {
		return modifySystemDate;
	}
	/**
	 * @param modifySystemDate The modifySystemDate to set.
	 */
	public void setModifySystemDate(int modifySystemDate) {
		this.modifySystemDate = modifySystemDate;
	}
	/**
	 * @return Returns the populationPartition.
	 */
	public int getPopulationPartition() {
		return populationPartition;
	}
	/**
	 * @param populationPartition The populationPartition to set.
	 */
	public void setPopulationPartition(int populationPartition) {
		this.populationPartition = populationPartition;
	}
	/**
	 * @return Returns the authenticationByOffice.
	 */
	public int getAuthenticationByOffice() {
		return authenticationByOffice;
	}
	/**
	 * @param authenticationByOffice The authenticationByOffice to set.
	 */
	public void setAuthenticationByOffice(int authenticationByOffice) {
		this.authenticationByOffice = authenticationByOffice;
	}
	/**
	 * @return Returns the repositorySystem.
	 */
	public int getRepositorySystem() {
		return repositorySystem;
	}
	/**
	 * @param repositorySystem The repositorySystem to set.
	 */
	public void setRepositorySystem(int repositorySystem) {
		this.repositorySystem = repositorySystem;
	}
	/**
	 * @return Returns the timeOut.
	 */
	public int getTimeOut() {
		return timeOut;
	}
	/**
	 * @param timeOut The timeOut to set.
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	/**
	 * @return Returns the validation.
	 */
	public String getValidation() {
		return validation;
	}
	/**
	 * @param validation The validation to set.
	 */
	public void setValidation(String validation) {
		this.validation = validation;
	}
	/**
	 * @return Returns the viewAcceptedRegisters.
	 */
	public int getViewAcceptedRegisters() {
		return viewAcceptedRegisters;
	}
	/**
	 * @param viewAcceptedRegisters The viewAcceptedRegisters to set.
	 */
	public void setViewAcceptedRegisters(int viewAcceptedRegisters) {
		this.viewAcceptedRegisters = viewAcceptedRegisters;
	}


	public int getDocumentHashing() {
		return documentHashing;
	}


	public void setDocumentHashing(int documentHashing) {
		this.documentHashing = documentHashing;
	}
	
	/**
	 * @return Returns the autoDist.
	 */
	public int getAutoDist() {
		return autoDist;
	}
	/**
	 * @param autoDist The autoDist to set.
	 */
	public void setAutoDist(int autoDist) {
		this.autoDist = autoDist;
	}
}
