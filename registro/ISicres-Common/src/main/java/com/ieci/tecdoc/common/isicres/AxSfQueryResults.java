package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 13-may-2004 10:32:18
 * @version
 * @since
 */
public class AxSfQueryResults implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private Integer bookId = null;
    private Map results = new TreeMap();

    private int totalQuerySize = 0;
    private int currentResultsSize = 0;
    private int pageSize = 0;
    private int currentFirstRow = 0;
    private int currentLastRow = 0;
    
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public AxSfQueryResults() {
    }

    /**
     * @param bookId
     * @param totalQuerySize
     * @param pageSize
     */
    public AxSfQueryResults(Integer bookId, int totalQuerySize, int pageSize) {
        super();
        this.bookId = bookId;
        this.totalQuerySize = totalQuerySize;
        this.pageSize = pageSize;
        this.currentFirstRow = 0;
        this.currentLastRow = 0;
        
          }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public AxSfQueryResults clone(Collection r) {
        AxSfQueryResults newResults = new AxSfQueryResults();
        newResults.setResults(r);
        newResults.setTotalQuerySize(this.totalQuerySize);
        newResults.setCurrentResultsSize(this.currentResultsSize);
        newResults.setPageSize(this.pageSize);
        newResults.setCurrentFirstRow(this.currentFirstRow);
        newResults.setCurrentLastRow(this.currentLastRow);
       newResults.setBookId(this.bookId);
        return newResults;
    }
    
    public String toXML() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<AxSfQueryResults bookId='");
        buffer.append(bookId);
        buffer.append("'>");
        for (Iterator it = results.values().iterator(); it.hasNext();) {
            buffer.append(((AxSfQueryField) it.next()).toXML());
        }
        buffer.append("</AxSfQuery>");

        return buffer.toString();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("AxSfQueryResults bookId [");
        buffer.append(bookId);
        buffer.append("] pageSize [");
        buffer.append(pageSize);
        buffer.append("] totalQuerySize [");
        buffer.append(totalQuerySize);
        buffer.append("] currentResultsSize [");
        buffer.append(currentResultsSize);
        buffer.append("] currentFirstRow [");
        buffer.append(currentFirstRow);
        buffer.append("] currentLastRow [");
        buffer.append(currentLastRow);
        buffer.append("] \n");
        for (Iterator it = results.values().iterator(); it.hasNext();) {
            buffer.append("\t");
            buffer.append(it.next().toString());
            buffer.append("\n");
        }
        
        return buffer.toString();
    }

    
    /**
     * @return Returns the currentFirstRow.
     */
    public int getCurrentFirstRow() {
        return currentFirstRow;
    }

    /**
     * @param currentFirstRow
     *            The currentFirstRow to set.
     */
    public void setCurrentFirstRow(int currentFirstRow) {
        if (currentFirstRow < 1) {
            this.currentFirstRow = 1;
        } else {
            this.currentFirstRow = currentFirstRow;
        }
    }

    /**
     * @return Returns the currentLastRow.
     */
    public int getCurrentLastRow() {
        return currentLastRow;
    }

    /**
     * @param currentLastRow
     *            The currentLastRow to set.
     */
    public void setCurrentLastRow(int currentLastRow) {
        if (currentLastRow > totalQuerySize) {
            this.currentLastRow = totalQuerySize;
        } else {
            this.currentLastRow = currentLastRow;
        }
    }

     /**
     * @return Returns the currentResultsSize.
     */
    public int getCurrentResultsSize() {
        return currentResultsSize;
    }

    /**
     * @param currentResultsSize
     *            The currentResultsSize to set.
     */
    public void setCurrentResultsSize(int currentResultsSize) {
        this.currentResultsSize = currentResultsSize;
    }

    
    /**
     * @return Returns the totalQuerySize.
     */
    public int getTotalQuerySize() {
        return totalQuerySize;
    }

    /**
     * @param totalQuerySize
     *            The totalQuerySize to set.
     */
    public void setTotalQuerySize(int totalQuerySize) {
        this.totalQuerySize = totalQuerySize;
    }

    /**
     * @return Returns the querySize.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param querySize
     *            The querySize to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return Returns the bookId.
     */
    public Integer getBookId() {
        return bookId;
    }

    /**
     * @param bookId
     *            The bookId to set.
     */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * @return Returns the fields.
     */
    public Collection getResults() {
        return results.values();
    }

    public void setResults(Collection col) {
        results.clear();
        int i = 0;
        for (Iterator it=col.iterator();it.hasNext();) {
            results.put(new Integer(i++), it.next());
        }
    }

    public void setResult(Object obj) {
        results.clear();
        results.put(new Integer(0), obj);
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

