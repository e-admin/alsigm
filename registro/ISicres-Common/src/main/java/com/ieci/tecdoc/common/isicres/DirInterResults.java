package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ieci.tecdoc.common.invesicres.ScrAddress;
import com.ieci.tecdoc.common.invesicres.ScrPfi;
import com.ieci.tecdoc.common.invesicres.ScrPjur;

/**
 * @author LMVICENTE
 * @creationDate 05-jul-2004 14:57:41
 * @version
 * @since
 */
public class DirInterResults implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private ScrPfi pfi = null;
    private ScrPjur pjur = null;

    private int addressSize = 0;
    private List addresses = new ArrayList();
    private Map dom = new HashMap();
    private boolean permModifPersonInfo = true;

	/**
	 * @return Returns the permModifPersonInfo.
	 */
	public boolean isPermModifPersonInfo() {
		return permModifPersonInfo;
	}
	/**
	 * @param permModifPersonInfo The permModifPersonInfo to set.
	 */
	public void setPermModifPersonInfo(boolean permModifPersonInfo) {
		this.permModifPersonInfo = permModifPersonInfo;
	}
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public DirInterResults() {
        super();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public void addScrAdress(ScrAddress address, List doms) {
        if (address != null && doms != null) {
            addresses.add(address);
            dom.put(address.getId(), doms);
        }
    }
    
    /**
     * @return Returns the addresses.
     */
    public List getAddresses() {
        return addresses;
    }
    /**
     * @param addresses The addresses to set.
     */
    public void setAddresses(List addresses) {
        this.addresses = addresses;
    }
    /**
     * @return Returns the addressSize.
     */
    public int getAddressSize() {
        return addressSize;
    }
    /**
     * @param addressSize The addressSize to set.
     */
    public void setAddressSize(int addressSize) {
        this.addressSize = addressSize;
    }
    /**
     * @return Returns the dom.
     */
    public Map getDom() {
        return dom;
    }
    /**
     * @param dom The dom to set.
     */
    public void setDom(Map dom) {
        this.dom = dom;
    }
    /**
     * @return Returns the pfi.
     */
    public ScrPfi getPfi() {
        return pfi;
    }
    /**
     * @param pfi The pfi to set.
     */
    public void setPfi(ScrPfi pfi) {
        this.pfi = pfi;
    }
    /**
     * @return Returns the pjur.
     */
    public ScrPjur getPjur() {
        return pjur;
    }
    /**
     * @param pjur The pjur to set.
     */
    public void setPjur(ScrPjur pjur) {
        this.pjur = pjur;
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

