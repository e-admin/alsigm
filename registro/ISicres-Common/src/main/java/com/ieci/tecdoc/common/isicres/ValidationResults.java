package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author LMVICENTE
 * @creationDate 11-jun-2004 9:47:36
 * @version
 * @since
 */
public class ValidationResults implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private int totalSize = 0;
    private Collection results = new ArrayList();
    private String docColName = null;
    private String susColName = null;
    private String additionalFieldName = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public ValidationResults() {
    }

    /**
     * @return Returns the results.
     */
    public Collection getResults() {
        if (results == null) {
            results = new ArrayList();
        }
        return results;
    }

    /**
     * @param results
     *            The results to set.
     */
    public void setResults(Collection results) {
        this.results = results;
    }

    public void addResult(ValidationResultScrOrg r) {
        this.results.add(r);
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

     public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("ValidationResults totalsize [");
        buffer.append(totalSize);
        buffer.append("] results [\n");
        for (Iterator it=results.iterator();it.hasNext();) {
            buffer.append(it.next());
            buffer.append("\n");
        }
        buffer.append("\n]");
        
        return buffer.toString();
    }
	/**
	 * @return Returns the docColName.
	 */
	public String getDocColName() {
		return docColName;
	}
	/**
	 * @param docColName The docColName to set.
	 */
	public void setDocColName(String docColName) {
		this.docColName = docColName;
	}
	/**
	 * @return Returns the susColName.
	 */
	public String getSusColName() {
		return susColName;
	}
	/**
	 * @param susColName The susColName to set.
	 */
	public void setSusColName(String susColName) {
		this.susColName = susColName;
	}
	/**
	 * @return Returns the additionalFieldName.
	 */
	public String getAdditionalFieldName() {
		return additionalFieldName;
	}
	/**
	 * @param additionalFieldName The additionalFieldName to set.
	 */
	public void setAdditionalFieldName(String additionalFieldName) {
		this.additionalFieldName = additionalFieldName;
	}
}

