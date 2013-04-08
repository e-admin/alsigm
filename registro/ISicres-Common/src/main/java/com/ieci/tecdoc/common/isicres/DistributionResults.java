package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LMVICENTE
 * @creationDate 11-jun-2004 9:47:36
 * @version @since
 */
public class DistributionResults implements Serializable {

    /***************************************************************************
     * Attributes
     **************************************************************************/

    private int totalSize = 0;

    private Map results = new HashMap();
    
    private Map distType = new HashMap();

    private Date actualDate;
    
    private Map idocarchhdr = new HashMap();
    
    private List books = new ArrayList();

     /***************************************************************************
     * Constructors
     **************************************************************************/

    public DistributionResults() {
    }

    

    /**
     * @return Returns the getBooks.
     */
    public List getBooks() {
        return books;
    }
    /**
     * @param getBooks The getBooks to set.
     */
    public void setGetBooks(List books) {
        this.books = books;
    }
    
    /**
     * @return Returns the idocarchhdr.
     */
    public Map getIdocarchhdr() {
        return idocarchhdr;
    }
    /**
     * @param idocarchhdr The idocarchhdr to set.
     */
    public void setIdocarchhdr(Map idocarchhdr) {
        this.idocarchhdr = idocarchhdr;
    }
   
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("DistributionResults totalsize [");
        buffer.append(totalSize);
        buffer.append("] results [");
        buffer.append(results.size());
        buffer.append("]");

        return buffer.toString();
    }

    /**
     * @return Returns the results.
     */
    public Map getResults() {
        return results;
    }

    /**
     * @param results
     *            The results to set.
     */
    public void setResults(Map results) {
        this.results = results;
    }

    /**
     * @return Returns the totalSize.
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * @param totalSize
     *            The totalSize to set.
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
    ///// TODO date actual /////
    /**
     * @return Returns the actual Date.
     */
    public Date getActualDate() {
        return actualDate;
    }

    /**
     * @param actual Date
     *            The actual Date to set.
     */
    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

	/**
	 * @return the distType
	 */
	public Map getDistType() {
		return distType;
	}

	/**
	 * @param distType the distType to set
	 */
	public void setDistType(Map distType) {
		this.distType = distType;
	}
}

