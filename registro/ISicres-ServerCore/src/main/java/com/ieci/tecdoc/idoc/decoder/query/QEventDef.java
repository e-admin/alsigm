package com.ieci.tecdoc.idoc.decoder.query;

import java.util.StringTokenizer;

/**
 * @author LMVICENTE
 * @creationDate 06-may-2004 16:59:24
 * @version
 * @since
 */
public class QEventDef {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private String sourceData = null;
    private int event = 0;
    private int actionType = 0;
    private int macroId = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public QEventDef() {
        this(null);
    }

    public QEventDef(String data) {
        setSourceData(data);
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("EventDef event[");
        buffer.append(event);
        buffer.append("] actionType [");
        buffer.append(actionType);
        buffer.append("] macroId [");
        buffer.append(macroId);
        buffer.append("]");

        return buffer.toString();
    }

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
     * @return Returns the macroId.
     */
    public int getMacroId() {
        return macroId;
    }
    /**
     * @param macroId The macroId to set.
     */
    public void setMacroId(int macroId) {
        this.macroId = macroId;
    }
    

    /**
     * @return Returns the actionType.
     */
    public int getActionType() {
        return actionType;
    }

    /**
     * @param actionType
     *            The actionType to set.
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    /**
     * @return Returns the event.
     */
    public int getEvent() {
        return event;
    }

    /**
     * @param event
     *            The event to set.
     */
    public void setEvent(int event) {
        this.event = event;
    }

    /**
     * @return Returns the macroid.
     */
    public int getMacroid() {
        return macroId;
    }

    /**
     * @param macroid
     *            The macroid to set.
     */
    public void setMacroid(int macroId) {
        this.macroId = macroId;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private void analize() {
        StringTokenizer tokenizer = new StringTokenizer(sourceData, ",");

        event = Integer.parseInt(tokenizer.nextToken());
        actionType = Integer.parseInt(tokenizer.nextToken());
        macroId = Integer.parseInt(tokenizer.nextToken());
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

