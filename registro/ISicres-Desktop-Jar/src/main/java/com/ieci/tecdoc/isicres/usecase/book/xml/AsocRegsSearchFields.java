package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * 
 * @author 66575267
 * 
 * @date 01/06/2009
 * 
 */
public class AsocRegsSearchFields implements Keys {

	private String fieldsName[] = { "@FLD0", "@FLD1", "@FLD2", "@FLD9" };

	private String fieldTranslate[] = { "", "1", "2", "9" };

	private String fieldsLabel[] = new String[4];

	private String fieldsType[] = { "1", "0", "2", "0" };

	private String fieldsValidation[] = { "10", "0", "0", "0" };

	private List result = new ArrayList();
	private String dataBaseType = null;

	public AsocRegsSearchFields(Locale locale, String dataBaseType) {
		this.dataBaseType = dataBaseType;
		getFieldsAsocRegLabel(locale);
		createSearchFields(locale);
	}

	private void createSearchFields(Locale locale) {

		AsocRegsFields asocRegsFields = null;
		Map operators = new HashMap();

		for (int i = 0; i < fieldsLabel.length; i++) {
			operators = getOperators(new Integer(fieldsType[i]), new Integer(
					fieldsValidation[i]), locale);
			asocRegsFields = new AsocRegsFields(fieldsName[i], fieldsLabel[i],
					new Integer(fieldsType[i]),
					new Integer(fieldsValidation[i]), fieldTranslate[i]);
			asocRegsFields.setOperators(operators);
			result.add(asocRegsFields);
		}

	}

	private void getFieldsAsocRegLabel(Locale locale) {

		for (int i = 0; i < fieldsName.length; i++) {
			String key = BOOKUSECASE_ASOCREGSSEARCH_LABEL_ROW;
			key = key + i;
			fieldsLabel[i] = RBUtil.getInstance(locale).getProperty(key);
		}

	}

	private Map getOperators(Integer type, Integer validation, Locale locale) {
		Map operator = new TreeMap();

		if (type.intValue() == 1 && validation.intValue() == 10) {
			operator.put(1 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_EQUAL_TEXT_VALUE), "=");
		}
		if (type.intValue() == 0 && validation.intValue() == 0) {
			operator.put(1 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_EQUAL_TEXT_VALUE), "=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_BEGIN_BY_VALUE), "LIKE1");
			operator.put(3 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_END_WITH_VALUE), "LIKE2");
			operator.put(4 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_LIKE_TEXT_VALUE), "LIKE");
			operator.put(5 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<>");
			operator.put(6 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_GREATER_TEXT_VALUE), ">");
			operator.put(7 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_LESSER_TEXT_VALUE), "<");
			operator.put(8 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_GREATER_EQUAL_TEXT_VALUE), ">=");
			operator.put(9 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_LESSER_EQUAL_TEXT_VALUE), "<=");
		}
		if (type.intValue() == 2 && validation.intValue() == 0) {
			operator.put(1 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_EQUAL_TEXT_VALUE), ">=AND<=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<OR>");
			operator.put(3 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_AFTER_TO_VALUE), ">23");
			operator.put(4 + RBUtil.getInstance(locale).getProperty(
					I18N_QUERY_BEFORE_TO_VALUE), "<00");
			// operator.put(5 + RBUtil.getInstance(locale).getProperty(
			// I18N_QUERY_ON_MONTH_VALUE), "MOUNTH");
			// operator.put(6 + RBUtil.getInstance(locale).getProperty(
			// I18N_QUERY_ON_YEAR_VALUE), "YEAR");
		}

		return operator;

	}

	/**
	 * @return Returns the result.
	 */
	public List getResult() {
		return result;
	}

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

}
