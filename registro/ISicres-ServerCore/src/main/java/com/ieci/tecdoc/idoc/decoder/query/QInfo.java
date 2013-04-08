package com.ieci.tecdoc.idoc.decoder.query;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class QInfo {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int numEvents = 0;
    private TreeMap eventDefs = new TreeMap();

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public QInfo() {
        this(null);
    }
    
    public QInfo(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * @return Returns the data.
     */
    public String getSourceData() {
        return sourceData;
    }

    /**
     * @param data
     *            The data to set.
     */
    public void setSourceData(String data) {
        if (data != null) {
            this.sourceData = data;
            analize();
        }
    }


    /**
     * @return Returns the eventDefs.
     */
    public TreeMap getEventDefs() {
        return eventDefs;
    }
    /**
     * @param eventDefs The eventDefs to set.
     */
    public void setEventDefs(TreeMap eventDefs) {
        this.eventDefs = eventDefs;
    }
    /**
     * @return Returns the numEvents.
     */
    public int getNumEvents() {
        return numEvents;
    }
    /**
     * @param numEvents The numEvents to set.
     */
    public void setNumEvents(int numEvents) {
        this.numEvents = numEvents;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Info \n\t\t\t title[");
        buffer.append(numEvents);
        buffer.append("] \n\t\t\t eventDefs [");

        Integer pos = null;
        for (Iterator it = eventDefs.keySet().iterator(); it.hasNext();) {
            pos = (Integer) it.next();
            buffer.append("\n\t\t\t\t ");
            buffer.append(pos);
            buffer.append(" ");
            buffer.append(eventDefs.get(pos).toString());
        }
        buffer.append("\n\t\t\t ]");
        
        return buffer.toString();
    }
    
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, "|");
        
        numEvents = Integer.parseInt(tokenizer.nextToken());
        
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            eventDefs.put(new Integer(i++), new QCtrlDef(tokenizer.nextToken()));
        }
        
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

