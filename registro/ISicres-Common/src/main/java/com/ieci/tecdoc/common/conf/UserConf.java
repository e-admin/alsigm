package com.ieci.tecdoc.common.conf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

/**
 * @author 79426599
 *
 */
public class UserConf implements Serializable {

    private List fieldConf = null;
    private int personValidation = 0;
    private int showScanDlg = 0;
    private int rememberLastSelectedUnit = 0;
    private Document doc = null;
    private int idOficPref = 0;

    private Map selectUnitInBook;

	public UserConf() {
	}


    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t UserConf personValidation[");
        buffer.append(personValidation);
        buffer.append("] showScanDlg [");
        buffer.append(showScanDlg);
        buffer.append("] rememberLastSelectedUnit [");
        buffer.append(rememberLastSelectedUnit);
        buffer.append("] fieldConf [");
        buffer.append(fieldConf);
        buffer.append("]");

        return buffer.toString();
    }


	/**
	 * @return the fieldConf
	 */
	public List getFieldConf() {
		return fieldConf;
	}


	/**
	 * @param fieldConf the fieldConf to set
	 */
	public void setFieldConf(List fieldConf) {
		this.fieldConf = fieldConf;
	}

	/**
	 * @return the personValidation
	 */
	public int getPersonValidation() {
		return personValidation;
	}


	/**
	 * @param personValidation the personValidation to set
	 */
	public void setPersonValidation(int personValidation) {
		this.personValidation = personValidation;
	}


	/**
	 * @return the rememberLastSelectedUnit
	 */
	public int getRememberLastSelectedUnit() {
		return rememberLastSelectedUnit;
	}


	/**
	 * @param rememberLastSelectedUnit the rememberLastSelectedUnit to set
	 */
	public void setRememberLastSelectedUnit(int rememberLastSelectedUnit) {
		this.rememberLastSelectedUnit = rememberLastSelectedUnit;
	}


	/**
	 * @return the showScanDlg
	 */
	public int getShowScanDlg() {
		return showScanDlg;
	}


	/**
	 * @param showScanDlg the showScanDlg to set
	 */
	public void setShowScanDlg(int showScanDlg) {
		this.showScanDlg = showScanDlg;
	}


	/**
	 * @return the doc
	 */
	public Document getDoc() {
		return doc;
	}


	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public int getIdOficPref() {
		return idOficPref;
	}


	public void setIdOficPref(int idOficPref) {
		this.idOficPref = idOficPref;
	}

	/**
	 * @return el selectUnitInBook
	 */
	public Map getSelectUnitInBook() {
		return selectUnitInBook;
	}

	/**
	 * @param selectUnitInBook
	 *            el selectUnitInBook a fijar
	 */
	public void setSelectUnitInBook(Map selectUnitInBook) {
		this.selectUnitInBook = selectUnitInBook;
	}

}
