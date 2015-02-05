package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrDistregstate;

/**
 * @author LMVICENTE
 * @creationDate 29-jun-2004 12:30:45
 * @version
 * @since
 */
public class DtrFdrResults implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private ScrDistreg scrDistReg = null;
    private String sourceDescription = null;
    private String targetDescription = null;
    private boolean flowProcess = false;
    private List scrDistRegState = new ArrayList();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /**
     *  
     */
    public DtrFdrResults() {
        super();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * @return Returns the flowProcess.
     */
    public boolean isFlowProcess() {
        return flowProcess;
    }

    /**
     * @param flowProcess
     *            The flowProcess to set.
     */
    public void setFlowProcess(boolean flowProcess) {
        this.flowProcess = flowProcess;
    }

    /**
     * @return Returns the scrDistReg.
     */
    public ScrDistreg getScrDistReg() {
        return scrDistReg;
    }

    /**
     * @param scrDistReg
     *            The scrDistReg to set.
     */
    public void setScrDistReg(ScrDistreg scrDistReg) {
        this.scrDistReg = scrDistReg;
    }

    /**
     * @return Returns the scrDistRegState.
     */
    public List getScrDistRegState() {
        return scrDistRegState;
    }

    /**
     * @param scrDistRegState
     *            The scrDistRegState to set.
     */
    public void setScrDistRegState(List scrDistRegState) {
        this.scrDistRegState = scrDistRegState;
    }

    /**
     * @return Returns the sourceDescription.
     */
    public String getSourceDescription() {
        return sourceDescription;
    }

    /**
     * @param sourceDescription
     *            The sourceDescription to set.
     */
    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    /**
     * @return Returns the targetDescription.
     */
    public String getTargetDescription() {
        return targetDescription;
    }

    /**
     * @param targetDescription
     *            The targetDescription to set.
     */
    public void setTargetDescription(String targetDescription) {
        this.targetDescription = targetDescription;
    }

    public void addScrDistRegState(ScrDistregstate scr) {
        this.scrDistRegState.add(scr);
    }
    
    /**
     * toString methode: creates a String representation of the object
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin

     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DtrFdrResults[");
        buffer.append("scrDistReg = ").append(scrDistReg);
        buffer.append(", sourceDescription = ").append(sourceDescription);
        buffer.append(", targetDescription = ").append(targetDescription);
        buffer.append(", flowProcess = ").append(flowProcess);
        buffer.append(", scrDistRegState = ").append(scrDistRegState);
        buffer.append("]");
        return buffer.toString();
    }
    
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

