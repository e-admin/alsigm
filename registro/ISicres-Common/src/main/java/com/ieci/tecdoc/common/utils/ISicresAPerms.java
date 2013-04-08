/*
 * Created on 29-dic-2004
 *
 */
package com.ieci.tecdoc.common.utils;

/**
 * @author MANUEL TAVARES
 * 
 */
public class ISicresAPerms {
    private boolean isBookAdmin = false;

    private boolean canQuery = false;

    private boolean canCreate = false;

    private boolean canModify = false;

    public ISicresAPerms() {
    }

    public void setIsBookAdmin(boolean bSet) {
        isBookAdmin = bSet;
    }

    public void setCanQuery(boolean bSet) {
        canQuery = bSet;
    }

    public void setCanCreate(boolean bSet) {
        canCreate = bSet;
    }

    public void setCanModify(boolean bSet) {
        canModify = bSet;
    }

    public boolean isBookAdmin() {
        return (isBookAdmin);
    }

    public boolean canQuery() {
        return (canQuery);
    }

    public boolean canCreate() {
        return (canCreate);
    }

    public boolean canModify() {
        return (canModify);
    }

}
